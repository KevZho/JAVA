package hr.fer.zemris.java.filechecking;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


/**
 * Razred <codeFileChecker</code> sadrži program koji provjera ispravnost
 * imena datoteke i njenog sadržaja pomoću pravila definirah programom
 * napisanim u programskom jeziku za provjeru datoteka.
 *
 * @author Igor Smolkovič
 */
public class FileChecker {

	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 *
	 * @param args Argumenti komandne linije:
	 * 				- args[0]: staza do zip datoteke,
	 * 				- args[1]: naziv datoteke koji je bio kod korisnika,
	 * 				- args[2]: staza do datoteke s programom koji opisuje provjere.
	 */
	public static void main(String[] args) {
		if (args.length != 3) {
			System.err.println("Korištenje: FileChecker <zip> <naziv> <program>");
			System.exit(-1);
		}

		Path zipPath = Paths.get(args[0]);
		if (!Files.isRegularFile(zipPath)) {
			System.err.println("Zip datoteka ne postoji.");
			System.exit(-1);
		}
		File file = new File(zipPath.toString());
		String fileName = args[1].trim();

		Path programPath = Paths.get(args[2]);
		if (!Files.isRegularFile(programPath)) {
			System.err.println("Datoteka s programom koji opisuje provjere ne postoji.");
			System.exit(-1);
		}
		
		String program = readProgram(programPath);
		FCProgramChecker checker = new FCProgramChecker(program);
		if (checker.hasErrors()) {
			System.out.println("Predani program sadrži pogreške!");
			for(String error : checker.errors()) {
				System.out.println(error);
			}
			System.out.println("Odustajem od daljnjeg rada.");
			System.exit(0);
		}

		Map<String, Object> initialData = new HashMap<>();
		initialData.put("jmbag", "0036453288");
		initialData.put("lastName", "Smolkovič");
		initialData.put("firstName", "Igor");

		FCFileVerifier verifier = new FCFileVerifier(
										file, fileName, program, initialData);
		if (!verifier.hasErrors()) {
			System.out.println("Yes! Uspjeh! Ovakav upload bi bio prihvaćen.");
		} else {
			System.out.println("Ups! Ovaj upload se odbija! Što nam još ne valja?");
			for (String error : verifier.errors()) {
				System.out.println(error);
			}
		}
	}

	/**
	 * Metoda koja učitava program iz datoteke koje opisuje provjere. Odbacuje
	 * prazne retke i komentare.
	 *
	 * @param programPath Putanja do datoteke s programom.
	 * @return Sadržaj pročitane datoteke zapisan u stringu.
	 */
	public static String readProgram(Path programPath) {
		BufferedReader reader = null;
		StringBuilder builder = new StringBuilder();
		try {
			reader = new BufferedReader(
					new InputStreamReader(new BufferedInputStream(
							new FileInputStream(programPath.toFile())),
							StandardCharsets.UTF_8
					)
			);
			String line = reader.readLine();
			while (line != null) {
				builder.append(line).append("\r\n");
				line = reader.readLine();
			}
		} catch (FileNotFoundException ignorable) { // vec je provjereno
			System.exit(-1);
		} catch (IOException e) {
			System.err.println("Došlo je do greške prilikom čitanja datoteke.");
			System.exit(-1);
		} finally {
			if (reader != null) {
				try { reader.close(); } catch (IOException ignorable) { }
			}
		}
		return builder.toString();
	}
}
