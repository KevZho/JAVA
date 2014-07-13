package hr.fer.zemris.java.webserver;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * Razred koji sadrži implementaciju HTTP servera.
 * @author Igor Smolkovič
 *
 */
public class SmartHttpServer {

	/**
	 * IP adresa poslužitelja.
	 */
	private String address;

	/**
	 * Port na kojem radi poslužitelj.
	 */
	private int port;

	/**
	 * Broj worker threadova.
	 */
	private int workerThreads;

	/**
	 * Duljina sessiona za usera.
	 */
	private int sessionTimeout;

	/**
	 * Mapa koja sadrži mapiranje dokument, mimeType.
	 */
	private Map<String, String> mimeTypes = new HashMap<String, String>();

	/**
	 * Referenca na serverThread.
	 */
	private ServerThread serverThread;

	/**
	 * Referenca na threadPool.
	 */
	private ExecutorService threadPool;

	/**
	 * Lokacija root foldera.
	 */
	private Path documentRoot;

	/**
	 * Mapa web workera.
	 */
	private Map<String,IWebWorker> workersMap = new HashMap<>();

	/**
	 * Mapa koja pamti sjednice.
	 */
	private Map<String, SessionMapEntry> sessions = 
			new ConcurrentHashMap<String, SmartHttpServer.SessionMapEntry>();

	/**
	 * Random generator za generiranje SID-a.
	 */
	private Random sessionRandom = new Random();

	/**
	 * Referenca na cleanUpThread.
	 */
	private CleanUpThread cleanThread;
	
	/**
	 * Slova koja se koriste u SID-u.
	 */
	private static final String SESSION_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	/**
	 * Konstruktor.
	 * @param configFile konfiguracijska datoteka.
	 */
	public SmartHttpServer(String configFile) {
		Properties properties = new Properties();
		InputStreamReader reader = null;
		try {
			reader = new InputStreamReader(
					new FileInputStream(new File(configFile)),
					StandardCharsets.UTF_8);
			properties.load(reader);
			address = properties.get("server.address").toString();
			port = Integer.parseInt(properties.getProperty("server.port"));
			workerThreads = Integer.parseInt(properties.getProperty("server.workerThreads"));
			sessionTimeout = Integer.parseInt(properties.getProperty("session.timeout"));

			String pathRoot = properties.getProperty("server.documentRoot");
			documentRoot = Paths.get(pathRoot);
			String mimePath = properties.get("server.mimeConfig").toString();
			readMimeTypes(mimePath);

			String workersPath = properties.get("server.workers").toString();
			readWebWorkser(workersPath);
		} catch (IOException e) {
			reportError();
		} catch (Exception e) {
			reportError();
		} finally {
			if (reader != null) {
				try { reader.close(); } catch (IOException ignorable) { }
			}
		}
		serverThread = new ServerThread();
	}

	private void reportError() {
		System.err.println("Greška prilikom čitanja server config datoteke.");
		System.exit(-1);
	}
	
	/**
	 * Metoda koja čita mapiranja datoteka na mimeTipove.
	 * @param mimePath lokacija datoteke s mime tipovima.
	 * @throws IOException ako je došlo do greške prilikom čitanja.
	 */
	private void readMimeTypes(String mimePath) throws IOException {
		List<String> lines = Files.readAllLines(
			Paths.get(mimePath),
			StandardCharsets.UTF_8
		);
		for (String line : lines) {
			if (line.startsWith("#") || line.isEmpty()) { continue; }
			String key = line.split("=")[0].trim();
			String value = line.split("=")[1].trim();
			mimeTypes.put(key, value);
		}
	}

	/**
	 * Metoda koja čita web workere iz properties datoteke.
	 * @param workersPath putanja do .properties datoteke iz koje se čita.
	 * @throws IOException ako je došlo do greške prilikom čitanja.
	 * @throws ClassNotFoundException ako ne postoji traženi web worker.
	 * @throws InstantiationException ako nije uspješno stvoren web worker.
	 * @throws IllegalAccessException ako nije uspješno stvoren web worker.
	 */
	public void readWebWorkser(String workersPath) throws IOException, ClassNotFoundException,
												InstantiationException, IllegalAccessException {
		List<String> lines = Files.readAllLines(
				Paths.get(workersPath),
				StandardCharsets.UTF_8
		);
		for (String line : lines) {
			if (line.startsWith("#") || line.isEmpty()) { continue; }
			String path = line.split("=")[0].trim();
			String fqcn = line.split("=")[1].trim();
			Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
			Object newObject = referenceToClass.newInstance();
			IWebWorker iww = (IWebWorker)newObject;
			if (!workersMap.containsKey(path)) {
				workersMap.put(path, iww);
			} else {
				throw new RuntimeException("Dva ista web workera.");
			}
		}
	}

	/**
	 * Metoda koja pokreće server.
	 */
	protected synchronized void start() {
		if (serverThread == null) {
			serverThread = new ServerThread();
		}
		serverThread.start();
		threadPool = Executors.newFixedThreadPool(workerThreads);
		System.out.println(String.format("Server started: %s at port %d", address, port));
		cleanThread = new CleanUpThread();
		cleanThread.start();
	}

	/**
	 * Metoda koja zaustavlja server.
	 */
	protected synchronized void stop() {
		serverThread.stopServer();
		cleanThread.stopThread();
		threadPool.shutdown();
	}

	/**
	 * Razred koji sadrži podatke o sjednici.
	 * @author Igor Smolkovič
	 *
	 */
	private static class SessionMapEntry {
		String sid;
		long validUntil;
		Map<String, String> map;
	}


	/**
	 * Dretva koja briše navežeće sjednice.
	 * @author Igor Smolkovič
	 *
	 */
	private class CleanUpThread extends Thread {
		/**
		 * Zastavica koja označava da je server još startani.
		 */
		private boolean serverAlive = true;

		/**
		 * Broj minuta koliko dretva čeka prije brisanja sjednica.
		 */
		private final static int WAIT_TIME = 1000*60*5;

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void run() {
			System.out.println("CleanUp thread started");
			while (serverAlive) {
				try {
					Thread.sleep(WAIT_TIME);
					Set<String> keys = sessions.keySet();
					for (String key : keys) {
						if (sessions.get(key).validUntil > new Date().getTime()) {
							sessions.remove(key);
						}
					}
				} catch (InterruptedException e) {
				}
			}
		}

		/**
		 * Metoda koja zaustavlja CleanUp dretvu.
		 */
		public void stopThread() {
			serverAlive = false;
			sessions.clear();
		}
	}

	/**
	 * Razred koji implementira glavnu dretvu servera.
	 * @author Igor Smolkovič
	 *
	 */
	protected class ServerThread extends Thread {

		/**
		 * Zastavica koja označava da server može primati nove klijente.
		 */
		private boolean alive = true;

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void run() {
			ServerSocket serverSocket = null;
			while (true) {
				try {
					serverSocket = new ServerSocket(port);
					break;
				} catch (IOException ex) {
				}
			}
			while (alive) {
				try {
					Socket client = serverSocket.accept();
					ClientWorker cw = new ClientWorker(client);
					threadPool.submit(cw);	
				} catch (IOException e) {
				}
			}
			try {
				serverSocket.close();
			} catch (IOException e) {
			}
		}

		/**
		 * Metoda koja serveru dojavljuje da više ne prima klijente.
		 */
		public void stopServer() {
			alive = false;
		}
	}

	private class ClientWorker implements Runnable {

		/**
		 * Klijent soket.
		 */
		private Socket csocket;

		/**
		 * Referenca na soket input stream.
		 */
		private PushbackInputStream istream;

		/**
		 * Referenca na soket output stream.
		 */
		private OutputStream ostream;

		/**
		 * Verzija HTTP protokola.
		 */
		private String version;

		/**
		 * Metoda dobivena u zahtjevu.
		 */
		private String method;

		/**
		 * Parametri dobiveni u zahtjevu.
		 */
		private Map<String, String> params = new HashMap<String, String>();

		/**
		 * Permanentni parametri dobiveni u zahtjevu.
		 */
		private Map<String, String> permParams = null;

		/**
		 * Lista cookia.
		 */
		private List<RCCookie> outputCookies = new ArrayList<RCCookie>();

		/**
		 * SID.
		 */
		private String SID;

		/**
		 * Konstruktor.
		 * @param csocket referenca na klijent socket.
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}

		/**
		 * Metoda koja obrađuje klijenta. Podržava skripte, web workere te vraćanje
		 * datoteka.
		 */
		@Override
		public void run() {
			/**
			 * Preuzmi input i output stream od klijent socketa.
			 */
			try {
				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = csocket.getOutputStream();
			} catch (IOException e) {
			}
			/**
			 * Pročitaj zahtjev.
			 */
			List<String> request = readRequest();
			/**
			 * Ako zahtjev nije dobar vrati bad request.
			 */
			if (request.size() < 1) {
				returnErrorMsg(400, "Bad Request");
				return;
			}

			System.out.println("Dobiven zahtjev");
			for (String line : request) {
				System.out.println(line);
			}

			/**
			 * Parsiraj prvu liniju zahtjeva.
			 */
			String firstLine = request.get(0);
			String requestedPath = null;
			method = null; version = null;
			try {
				method = firstLine.split(" ")[0].trim();
				requestedPath = firstLine.split(" ")[1].trim();
				version = firstLine.split(" ")[2].trim();
			} catch (Exception e) {
			}
			/**
			 * Ako zahtjev nije bio GET i verzija protokolona nije 1.0 ili 1.1
			 * vrati bad request.
			 */
			boolean valid = checkFirstLine(method, version);
			if (!valid) {
				returnErrorMsg(400, "Bad Request");
				return;
			}

			/**
			 * Odvoji path od parametara.
			 */
			String path = null; String paramString = null;
			try {
				path = requestedPath.split("\\?")[0].trim();
				paramString = requestedPath.split("\\?")[1].trim();
			} catch (Exception e) {
			}

			/**
			 * Kreiranje, provjera i osvježavanje sjednica.
			 */
			String sidCandidate =	checkSession(request);
			if (sidCandidate == null) {
				generateSessionEntry();
				System.err.println("Novi klijent, generiran SID: " + SID);
			} else {
				SessionMapEntry entry = sessions.get(sidCandidate);
				if (entry == null) { 
					generateSessionEntry(); 
				} else if (entry.validUntil < new Date().getTime()) {
					/**
					 * Ako postoji zapis, a prestari je generiraj novi.
					 */
					generateSessionEntry();
				} else {
					/**
					 * Updejtaj vrijeme života i postavi permParametre.
					 */
					SID = entry.sid;
					entry.validUntil = new Date().getTime() + sessionTimeout * 1000;
					permParams = entry.map;
					System.err.println("Klijen pronađen: " + SID);
				}
			}

			/**
			 * Parsiranje parametara dobivenih u pathu.
			 */
			parseParameters(paramString);

			/**
			 * Osiguraj se da je path dobro parsiran.
			 */
			if (path == null) {
				returnErrorMsg(400, "Bad Request");
				return;
			}
			
			/**
			 * Omogućuje izvršavanje convention-over-configuration tipa workera.
			 */
			if (path.startsWith("/ext/")) {
				executeWebWorkerForName(path);
				return;
			}

			/**
			 * Omogućuje izvršavanje configuration-based workera.
			 */
			if (isPathWebWorker(path)) {
				executeWebWorker(path);
				return;
			}

			/**
			 * Provjera tražene datoteke.
			 */
			Path file = documentRoot.resolve("." + path);
			if (!file.startsWith(documentRoot)) {
				returnErrorMsg(403, "Forbidden");
				return;
			}
			if (!Files.exists(file) || !Files.isReadable(file)) {
				returnErrorMsg(404, "Not Found");
				return;
			}

			/**
			 * Omogućuje izvršavanje .smscr skripti.
			 */
			if (isFileScript(path)) {
				executeScript(file);
				return;
			}

			/**
			 * Određivanje mime tipa.
			 */
			String mime = getMimeType(file.getFileName().toString());
			/**
			 * Stvaranje novog odgovora.
			 */
			RequestContext rc = new RequestContext(ostream, params, permParams, outputCookies);
			rc.setMimeType(mime);

			/**
			 * Vraća traženu datoteku.
			 */
			returnFile(file, rc);
		}

		/**
		 * Metoda koja generira i dodaje sjednicu u mapu sjednica.
		 */
		public void generateSessionEntry() {
			SID = generateSID();
			SessionMapEntry entry = new SessionMapEntry();
			entry.sid = SID;
			permParams = new HashMap<>();
			/**
			 * sessionTimeout je u sekundama, a getTime vraća milisekunde
			 */
			entry.validUntil = new Date().getTime() + sessionTimeout * 1000;
			entry.map = permParams;
			sessions.put(SID, entry);
			outputCookies.add(new RequestContext.RCCookie("sid", SID, null, address	, "/", true));
		}

		/**
		 * Metoda koja geneira novi SID duljine 20.
		 * @return SID duljine 20 slova.
		 */
		private String generateSID() {
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < 20; i++) {
				builder.append(SESSION_LETTERS.charAt(sessionRandom.nextInt(SESSION_LETTERS.length())));
			}
			return builder.toString();
		}

		/**
		 * Metoda koja traži sid u cookiju.
		 * @param request request u kojem se pregledavaju cookiji.
		 */
		private String checkSession(List<String> request) {
			try {
				Pattern p = Pattern.compile("^.*sid=\"(.*)\".*");
				for (String line : request) {
					if (!line.startsWith("Cookie")) { continue; }
					Matcher m = p.matcher(line);
					if (m.find()) {
						String sidCandidate = m.group(1);
						return sidCandidate;
					}
				}
			} catch (Exception ex) {
			}
			return null;
		}

		/**
		 * Metoda koja pokreće web workera prema imenu razreda kojem pripada.
		 * @param name ime worketa koji se želi izvršiti
		 */
		private void executeWebWorkerForName(String name) {
			name = name.substring(5);
			String fqcn = "hr.fer.zemris.java.webserver.workers." + name.trim();
			Class<?> referenceToClass = null;
			IWebWorker webWorker = null;
			try {
				referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
				webWorker = (IWebWorker) referenceToClass.newInstance();
				webWorker.processRequest(
						new RequestContext(
								ostream,
								params,
								permParams,
								outputCookies)
				);
				socketClose();
				return;
			} catch (InstantiationException e) {
			} catch (IllegalAccessException e) {
			} catch (ClassNotFoundException e) {
			}
			returnErrorMsg(404, "Not Found");
		}

		/**
		 * Metoda koja provjerava da li je dobivena putanja do nekog web workera.
		 * @param path
		 * @return
		 */
		private boolean isPathWebWorker(String path) {
			if (workersMap.containsKey(path.trim())) {
				return true;
			}
			return false;
		}

		/**
		 * Metoda koja pokreće web workera. Provjera je već napravljena tako da se metoda
		 * može sigurno pokrenuti.
		 * @param name ime weworkera
		 */
		private synchronized void executeWebWorker(String name) {
			workersMap.get(name.trim()).processRequest(
					new RequestContext(
							ostream,
							params,
							permParams,
							outputCookies
					)
			);
			socketClose();
		}

		/**
		 * Metoda koja izvršava skriptu i vraća rezultat izvođenja.
		 * @param file putanja do skripte koja se izvršava.
		 */
		private synchronized void executeScript(Path file) {
			/**
			 * readAllBytes može pročitati do 2GB. Skripte su male datoteke i ne treba
			 * bufferirano čitanje.
			 */
			byte[] data;
			try {
				data = Files.readAllBytes(file);
				RequestContext rc = new RequestContext(ostream, params, permParams, outputCookies);
				rc.setMimeType("text/plain");
				new SmartScriptEngine(
						new SmartScriptParser(new String(data, StandardCharsets.UTF_8)).getDocumentNode(),
						rc
				).execute();
				socketClose();

			} catch (IOException e) {
				returnErrorMsg(404, "Not Found");
			}
		}

		/**
		 * Metoda koja provjerava da li je dobivena skripta.
		 * @param fileName ime datoteke uzeto iz putanje.
		 * @return true ako je skripta, inače false.
		 */
		private boolean isFileScript(String fileName) {
			return fileName.endsWith(".smscr");
		}

		/**
		 * Metoda koja na temelju imena datoteke određuje mime type.
		 * @param fileName ime datoteke za koju se određuje mime type.
		 * @return mime tip dobiven iz imena datoteke.
		 */
		private String getMimeType(String fileName) {
			try {
				int position = fileName.lastIndexOf('.');
				if (position != -1) {
					String mime = mimeTypes.get(fileName.substring(position + 1));
					return mime;
				}
			} catch (Exception e) {
			}
			return "application/octet-stream";
		}

		/**
		 * Metoda koja vraća poruku greške klijentu.
		 * @param status status poruke.
		 * @param message poruka.
		 */
		public void returnErrorMsg(int status, String message) {
			RequestContext rc = new RequestContext(ostream, null, null, null);
			rc.setStatusCode(status);
			rc.setStatusText(message);
			rc.write(message);
			socketClose();
		}

		/**
		 * Metoda koja vraća traženu datoteku.
		 * @param path putanja do datoteke iz koje se čita.
		 * @param rc request context u koji se zapisuju podaci.
		 */
		public void returnFile(Path path, RequestContext rc) {
			BufferedInputStream stream = null;
			try {
				stream = new BufferedInputStream(new FileInputStream(path.toFile()));
				byte[] buffer = new byte[1024];
				int read = -1;
				while ((read = stream.read(buffer)) != -1) {
					rc.write(buffer, 0, read);
				}
				socketClose();
			} catch (IOException e) {
			} finally {
				if (stream != null) {
					try { stream.close(); } catch (IOException ignorable) { }
				}
			}
		}

		/**
		 * Metoda koja zatvara soket.
		 */
		public void socketClose() {
			while (true) {
				try {
					ostream.flush();
					csocket.close();
					break;
				} catch (IOException ignorable) { 
				}
			}
		}

		/**
		 * Metoda koja iz putanje uzima parametre.
		 * @param paramString dio putanje iz kojeg se uzimaju parametri
		 */
		private void parseParameters(String paramString) {
			try {
				String[] parts = paramString.split("&");
				for (String part : parts) {
					String key = part.split("=")[0].trim();
					String value = part.split("=")[1].trim();
					params.put(key, value);
				}
			} catch (Exception e) {
			}
		}

		/**
		 * Metoda koja provjerava da je dobiven GET zahtjev i da je verzija protokola
		 * 1.1 ili 1.0.
		 * @param method metoda koja je dobivna u zahtjevu.
		 * @param version verzija protokola dobivena u zahtjevu.
		 * @return true ako je sve ispravno, inače false.
		 */
		private boolean checkFirstLine(String method, String version) {
			if (method == null || version == null) { return false; }
			if (method.compareTo("GET") != 0) { return false; }
			if (version.compareTo("HTTP/1.1") != 0 && version.compareTo("HTTP/1.0") != 0) {
				return false; 
			}
			return true;
		}

		/**
		 * Metoda koja čita trenutni zahtjev i stvara listu pročitanih redova.
		 * @return lista pročitanih redova iz zahtjeva.
		 * @throws IOException 
		 */
		private List<String> readRequest() {
			List<String> list = new ArrayList<String>();
			BufferedReader reader = null; 
			try {
				reader = new BufferedReader(
						new InputStreamReader(
								istream,
								StandardCharsets.UTF_8)
				);
				String line;
				while ((line = reader.readLine()) != null) {
					/**
					 * Dobiven je zadnji "\r\n".
					 */
					if (line.isEmpty()) { break; }
					list.add(line);
				}
			} catch (IOException e) {
			}
			return list;
		}
	}

	/**
	 * Metoda koja se poziva prilikom pokretanja servera.
	 * @param args argumenti komadne linije. args[0] je lokacija config datoteke servera.
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Očekivan parametar: <config.file>");
			return;
		}
		try {
			SmartHttpServer server = new SmartHttpServer(args[0]);
			server.start();
		} catch (Exception e) {
			System.err.println("Pokretanje servera nije uspjelo.");
		}
	}
}
