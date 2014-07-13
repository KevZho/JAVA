package hr.fer.zemris.java.hw11.notepad;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;


/**
 * Razred koji omogućava manipuliranje datotekama.
 * @author Igor Smolkovič
 *
 */
public class JNotepadFile {

	/**
	 * Metoda koja omogućava spremanje u datoteku.
	 * @param text tekst koji se sprema u datoteku.
	 * @param f datoteka u koju se sprema.
	 * @return true ako je spremanje uspjelo, inače false.
	 */
	public static boolean saveToFile(String text, File f) {
		try {
			Files.write(f.toPath(), text.getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	/**
	 * Metoda koja omogućava čitanje datoteke.
	 * @param filePath putanja do datoteke koja se čita.
	 * @return pročitani string iz datoteke.
	 */
	public static String readFromFile(Path filePath) {
		byte[] data;
		try {
			data = Files.readAllBytes(filePath);
		} catch(Exception ex) {
			return null;
		}
		return new String(data, StandardCharsets.UTF_8);
	}
}
