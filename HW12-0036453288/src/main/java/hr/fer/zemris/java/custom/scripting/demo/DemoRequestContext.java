package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Metoda koja služi za provjeru razreda RequestContext.
 * Preuzeto i zadaće.
 * @author Igor Smolkovič
 *
 */
public class DemoRequestContext {

	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 * @param args argumenti komandne linije.
	 * @throws IOException ako je došlo do greške prilikom pisanja.
	 */
	public static void main(String[] args) throws IOException {
		demo1("primjer1.txt", "ISO-8859-2");
		demo1("primjer2.txt", "UTF-8");
		demo2("primjer3.txt", "UTF-8");
	}

	/**
	 * Metoda demo1.
	 * @param filePath putanja do datoteke u koju se zapisuje.
	 * @param encoding encoding.
	 * @throws IOException ako je došlo do greške prilikom pisanja.
	 */
	private static void demo1(String filePath, String encoding) throws IOException {
		OutputStream os = Files.newOutputStream(Paths.get(filePath));

		RequestContext rc = new RequestContext(os, new HashMap<String, String>(),
									new HashMap<String, String>(),
									new ArrayList<RequestContext.RCCookie>());
		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");

		// Only at this point will header be created and written...
		rc.write("Čevapčići i Šiščevapčići.");

		os.close();
	}

	/**
	 * Metoda demo2.
	 * @param filePath putanja do datoteke u koju se zapisuje.
	 * @param encoding encoding.
	 * @throws IOException ako je došlo do greške prilikom pisanja.
	 */
	private static void demo2(String filePath, String encoding) throws IOException {
		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		RequestContext rc = new RequestContext(os, new HashMap<String, String>(),
									new HashMap<String, String>(),
									new ArrayList<RequestContext.RCCookie>());

		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		rc.addRCCookie(new RCCookie("korisnik", "perica", 3600, "127.0.0.1", "/"));
		rc.addRCCookie(new RCCookie("zgrada", "B4", null, null, "/"));

		// Only at this point will header be created and written...
		rc.write("Čevapčići i Šiščevapčići.");

		os.close();
	}
}