package hr.fer.zemris.java.tecaj.hw4;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Razred <code>NamesCounter</code> omogućava brojanje pojavljivanja
 * pojedinog imena učitanog sa tipkovnice.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class NamesCounter {

	/**
	 * Metoda koja se poziva prilikom pokretanja programa. Učitava te na standardni
	 * izlaz ispisuje učitana imena i broj pojavljinja za svako ime.
	 * 
	 * @param args Argumenti komandne linije.
	 */
	public static void main(String[] args) {
		Map<String, Integer> map = null;
		try {
			map = readNames();
		}
		catch(IOException e) {
			System.err.println("Error while reading names.");
			System.exit(-1);
		}
		
		for(Map.Entry<String, Integer> e : map.entrySet()) {
			System.out.println(e.getKey() + " " + e.getValue());
		}
	}
	
	/**
	 * Metoda koja učitava imena te broji njihova pojavljivanja.
	 * 
	 * @return Mapa učitanih imena.
	 * @throws IOException Ako je došlo do pogreške tijekom učitavanja.
	 */
	private static Map<String, Integer> readNames() throws IOException {
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(new BufferedInputStream(System.in), 
						StandardCharsets.UTF_8)
		);
		
		Map<String, Integer> map = new HashMap<>();
		while(true) {
			System.out.print("Name: ");
			String name = reader.readLine();
			
			if(name == null) {
				System.exit(-1);
			}
			else if(name.trim().compareToIgnoreCase("quit") == 0) {
				break;
			}
			else {
				name = name.trim();
				int value = map.containsKey(name) ? map.get(name) : 0;
				map.put(name, value + 1);
			}
		}
		
		return map;
	}
}
