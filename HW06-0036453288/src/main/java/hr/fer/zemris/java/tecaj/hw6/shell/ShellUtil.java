package hr.fer.zemris.java.tecaj.hw6.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Razred <code>ShellUtil</code> pruža metode za pisanje
 * i čitanje iz predviđenih ulaza/izlaza. Ulazi/izlaz definirani
 * su u razredu <code>MyShell</code>. Zapisuje se na bufferirani 
 * standardni izlaz i čita s bufferiranog standardnog ulaza.
 *
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class ShellUtil {

	/**
	 * Metoda koja zapisuje omogućava zapisivanje String vrijednosti na dobiveni
	 * izlaz.
	 *
	 * @param out Izlaz na koji se zapisuje.
	 * @param line String vrijednost koja se zapisuje na izlaz.
	 */
	public static void writeToShell(BufferedWriter out, String line) {
		try {
			out.write(line);
			out.flush();
		} catch (IOException e) {
		}
	}
	
	/**
	 * Metoda koja omogućava dohvaćanje podataka od korisnika s predviđenog
	 * ulaza.
	 *
	 * @param in Ulaz s kojeg se čitaju podaci.
	 * @return String vrijednost koja je pročitana s ulaza.
	 */
	public static String readFromShell(BufferedReader in) {
		String input = null;
		try {
			input = in.readLine();
		} catch (IOException e) {
		}
		return input;
	}
}
