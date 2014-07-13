package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Razred koji sadrži testove za SmartScriptEngine.
 * @author Igor Smolkovič
 *
 */
public class SmartScriptEngineDemo {

	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 * @param args argumenti komandne linije. Ne koriste se.
	 */
	public static void main(String[] args) {
		test1();
		test2();
		test3();
		test4();
	}

	/**
	 * Osnovni test.
	 */
	public static void test1() {
		String documentBody = readFromDisk("./webroot/scripts/osnovni.smscr");
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RCCookie>();

		new SmartScriptEngine(
				new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies)
		).execute();
	}

	/**
	 * Test2. Test operatora.
	 */
	public static void test2() {
		String documentBody = readFromDisk("./webroot/scripts/zbrajanje.smscr");
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RCCookie>();

		parameters.put("a", "4");
		parameters.put("b", "2");

		new SmartScriptEngine(
				new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies)
		).execute();
	}

	/**
	 * Test3.
	 */
	public static void test3() {
		String documentBody = readFromDisk("./webroot/scripts/brojPoziva.smscr");
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RCCookie>();

		persistentParameters.put("brojPoziva", "3");

		RequestContext rc = new RequestContext(System.out, parameters, persistentParameters, cookies);
		new SmartScriptEngine(
				new SmartScriptParser(documentBody).getDocumentNode(), rc
		).execute();

		System.out.println("Vrijednost u mapi: " + rc.getPersistentParameter("brojPoziva"));
	}

	/**
	 * Test4. Fibonaccijevi brojevi.
	 */
	public static void test4() {
		String documentBody = readFromDisk("./webroot/scripts/fibonacci.smscr");
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RCCookie>();

		new SmartScriptEngine(
				new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies)
		).execute();
	}

	/**
	 * Metoda koja učitava datoteku i vraća string.
	 * @param path putanja do datoteke koja se čita.
	 * @return string reprezentacija datoteke.
	 */
	private static String readFromDisk(String path) {
		byte[] data;
		try {
			data = Files.readAllBytes(Paths.get(path));
		} catch(Exception ex) {
			return null;
		}
		return new String(data, StandardCharsets.UTF_8);
	}
}
