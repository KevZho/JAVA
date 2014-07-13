package hr.fer.zemris.java.webserver;


import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Razred koji omogućava generiranje HTTP odgovora.
 * @author Igor Smolkovič
 *
 */
public class RequestContext {

	/**
	 * Referenca na outputStream.
	 */
	private OutputStream outputStream;

	/**
	 * Referenca na trenutni charset.
	 */
	private Charset charset;

	/**
	 * Referenca na trenutni encoding.
	 */
	private String encoding = "UTF-8";

	/**
	 * Trenutni status kod.
	 */
	private int statusCode = 200;

	/**
	 * Trenutni statusText.
	 */
	private String statusText = "OK";

	/**
	 * Trenutni mimeType.
	 */
	private String mimeType = "text/html";

	/**
	 * Mapa koja pamti parametre.
	 */
	private Map<String, String> parameters;

	/**
	 * Mapa koja pamti privremene parametre.
	 */
	private Map<String, String> temporarayParameters;

	/**
	 * Mapa koja pamti perzistentne parametre.
	 */
	private Map<String, String> persistentParameters;

	/**
	 * Lista cookia.
	 */
	private List<RCCookie> outputCookies;

	/**
	 * Zastavica koja označava da li je već generiran header.
	 */
	private boolean headerGenerated = false;
	
	/**
	 * Konstruktor.
	 * @param outputStream output stream.
	 * @param parameters mapa parametara.
	 * @param persistentParameters mapa perzistentnih parametra.
	 * @param outputCookies lista cookia.
	 */
	public RequestContext(OutputStream outputStream,
			Map<String, String> parameters,
			Map<String, String> persistentParameters,
			List<RCCookie> outputCookies) {
		super();
		if (outputStream == null) {
			throw new IllegalArgumentException("outputStream == null");
		}
		this.outputStream = outputStream;
		this.parameters = parameters != null ? parameters : new HashMap<String, String>();
		this.persistentParameters = persistentParameters != null ? persistentParameters : new HashMap<String, String>();
		this.outputCookies = outputCookies != null ? outputCookies : new ArrayList<RCCookie>();
		this.temporarayParameters = new HashMap<>();
	}

	/**
	 * Metoda koja omogućava promjenu encodinga. Nakon što je generiran header
	 * promjena nije mogućava.
	 * @param encoding novi encoding.
	 */
	public void setEncoding(String encoding) {
		if (headerGenerated) { return; }
		this.encoding = encoding;
	}

	/**
	 * Metoda koja omogućava promjenu status coda. Nakon što je generiran header
	 * promjena nije mogućava.
	 * @param statusCode novi status code.
	 */
	public void setStatusCode(int statusCode) {
		if (headerGenerated) { return; }
		this.statusCode = statusCode;
	}

	/**
	 * Metoda koja omogućava promjenu status texta. Nakon što je generiran header
	 * promjena nije moguća.
	 * @param statusText novi status text.
	 */
	public void setStatusText(String statusText) {
		if (headerGenerated) { return; }
		this.statusText = statusText;
	}

	/**
	 * Metoda koja omogućava promjenu mime typa. Nakon što je generiran header
	 * promjena više nije moguća.
	 * @param mimeType
	 */
	public void setMimeType(String mimeType) {
		if (headerGenerated) { return; }
		this.mimeType = mimeType;
	}

	/**
	 * Metoda koja omogućava dohvaćanje parametra.
	 * @param name parametar koji se dohvaća.
	 * @return vrijednost koju pamti parametar.
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}

	/**
	 * Metoda koja omogućava dohvaćanje imena svih parametara.
	 * @return set imena parametara.
	 */
	public Set<String> getParameterNames() {
		return new HashSet<String>(parameters.keySet());
	}

	/**
	 * Metoda koja omogućava dohvaćanje perzistentnih parametara.
	 * @param name parametar koji se dohvaća.
	 * @return vrijednost koju pamti parametar.
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}

	/**
	 * Metoda koja omogućava dohvaćanje imena svih perzistentnih parametara.
	 * @return set imena svih perzistentnih parametara.
	 */
	public Set<String> getPersistentParameters() {
		return new HashSet<String>(persistentParameters.keySet());
	}

	/**
	 * Metoda koja omogućava postavljanje perzistentnih parametara.
	 * @param name ime parametra koji se postavlja.
	 * @param value vrijednost parametra koji se postavlja.
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}

	/**
	 * Metoda koja omogućava brisanje perzistentnih parametara.
	 * @param name ime parmetra koji se briše.
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}

	/**
	 * Metoda koja omogućava dohvaćanje privremenih parametara.
	 * @param name ime parametra koji se dohvaća.
	 * @return vrijednost parametra koji je tražen.
	 */
	public String getTemporaryParameter(String name) {
		return temporarayParameters.get(name);
	}

	/**
	 * Metoda koja omogućava dohvaćanje imena svih privremenih parametara.
	 * @return set imena svih privremenih parametara.
	 */
	public Set<String> getTemporaryParameters() {
		return new HashSet<String>(temporarayParameters.keySet());
	}

	/**
	 * Metoda koja omogućava postavljanje privremenih parametara.
	 * @param name ime parametra koji se postavlja.
	 * @param value vrijednost na koju se postavlja parametar.
	 */
	public void setTemporaryParameter(String name, String value) {
		temporarayParameters.put(name, value);
	}

	/**
	 * Metoda koja omogućava brisanje privremenih parametara.
	 * @param name ime paramtra koji se briše.
	 */
	public void removeTemporaryParameter(String name) {
		temporarayParameters.remove(name);
	}

	/**
	 * Metoda koja u outputstream zapisuje polje bajtova.
	 * @param data polje bajtova koje se zapisuje.
	 * @return referenca na objekt tipa RequestContext.
	 */
	public RequestContext write(byte[] data) {
		if (!headerGenerated) { writeHeader(); }
		return write(data, 0, data.length);
	}
	
	public RequestContext write(byte[] data, int offset, int length) {
		if (!headerGenerated) { writeHeader(); }
		try {
			outputStream.write(data, offset, length);
		} catch (IOException e) {
			try { throw new IOException("Write failed"); } catch (IOException ignorable) { }
		}
		return this;
	}

	/**
	 * Metoda koja omogućava zapisivanje stringa u output stream.
	 * @param text string koji se zapisuje.
	 * @return referenca na objekt tipa RequestContext.
	 */
	public RequestContext write(String text) {
		if (!headerGenerated) { writeHeader(); }
		byte[] data = text.getBytes(charset);
		return write(data);
	}

	/**
	 * Metoda koja u outputstream zapisuje header.
	 */
	private void writeHeader() {
		charset = Charset.forName(encoding);
		headerGenerated = true;
		StringBuilder builder = new StringBuilder();
		builder.append("HTTP/1.1 ").append(statusCode).append(" ").append(statusText).append("\r\n");
		builder.append("Content-Type: ").append(mimeType);
		if (mimeType.startsWith("text/")) {
			builder.append("; charset=").append(encoding);
		}
		builder.append("\r\n");

		for (RCCookie cookie : outputCookies) {
			builder.append(createCookieLine(cookie));
		}
		builder.append("\r\n");
		write(builder.toString().getBytes(StandardCharsets.ISO_8859_1));
	}

	/**
	 * Metoda koja omogućava kreiranje dijela zaglavlja koji čini cookie.
	 * @param cookie cookie na temelju koje se generira linije headera.
	 * @return linija headera nastala na temelju dobivenih cookia. 
	 */
	private String createCookieLine(RCCookie cookie) {
		StringBuilder builder = new StringBuilder();
		String name = cookie.getName();
		String value = cookie.getValue();

		builder.append("Set-Cookie: ");
		builder.append(name).append("=\"").append(value).append("\"");

		String domain = cookie.getDomain();
		if (domain != null) {
			builder.append("; Domain=").append(domain);
		}

		String path = cookie.getPath();
		if (path != null) {
			builder.append("; Path=").append(path);
		}

		Integer maxAge = cookie.getMaxAge();
		if (maxAge != null) {
			builder.append("; Max-Age=").append(maxAge);
		}
		Boolean httpOnly = cookie.getHttpOnly();
		if (httpOnly) {
			builder.append("; HttpOnly");
		}
		builder.append("\r\n");

		return builder.toString();
	}

	/**
	 * Metoda koja omogućava dodavanje cookia.
	 * @param rcCookie cookie koji se dodaje.
	 */
	public void addRCCookie(RCCookie rcCookie) {
		outputCookies.add(rcCookie);
	}

	/**
	 * Razred koji sadrži podatke za cookie.
	 * @author Igor Smolkovič
	 *
	 */
	public static class RCCookie {

		/**
		 * Ime.
		 */
		private String name;

		/**
		 * Vrijednost.
		 */
		private String value;

		/**
		 * Domena.
		 */
		private String domain;

		/**
		 * Putanja.
		 */
		private String path;

		/**
		 * Vrijeme života.
		 */
		private Integer maxAge;

		/**
		 * Ako se cookie može koristit samo za HTTP protokol.
		 */
		private boolean httpOnly;
		
		/**
		 * Konstruktor.
		 * @param name ime.
		 * @param value vrijednost.
		 * @param maxAge vrijeme života.
		 * @param domain domena.
		 * @param path putanja.
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain,
				String path) {
			super();
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
		}

		/**
		 * Konstruktor.
		 * @param name ime.
		 * @param value vrijednost.
		 * @param maxAge vrijeme života.
		 * @param domain domena.
		 * @param path putanja.
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain,
				String path, Boolean httpOnly) {
			this(name, value, maxAge, domain, path);
			this.httpOnly = httpOnly;
		}
		
		/**
		 * Metoda za dohvaćanje imena.
		 * @return ime koje pamti cookie.
		 */
		public String getName() {
			return name;
		}

		/**
		 * Metoda za dohvaćanje vrijednosti.
		 * @return vrijednost koju pamti cookie.
		 */
		public String getValue() {
			return value;
		}

		/**
		 * Metoda za dohvaćanje domene.
		 * @return domena koju pamti cookie.
		 */
		public String getDomain() {
			return domain;
		}

		/**
		 * Metoda za dohvaćanje putanje.
		 * @return putanja koju pamti cookie.
		 */
		public String getPath() {
			return path;
		}

		/**
		 * Metoda za dohvaćanje vremena života.
		 * @return vrijeme života.
		 */
		public Integer getMaxAge() {
			return maxAge;
		}
		
		/**
		 * Metoda koja vraća da li je cookie http only.
		 * @return true ako je httpOnly, inače false.
		 */
		public boolean getHttpOnly() {
			return httpOnly;
		}
	}
}
