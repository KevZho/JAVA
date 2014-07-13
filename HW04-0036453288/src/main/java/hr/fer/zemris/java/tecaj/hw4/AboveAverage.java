package hr.fer.zemris.java.tecaj.hw4;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Razred <code>AboveAverage</code> omogućava učitavanje i ispis svih 
 * učitanih brojeva u uzlaznom redosljedu čija je vrijednost barem
 * 20 % veća od prosjeka.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class AboveAverage {

	/**
	 * Metoda koja se poziva prilikom pokretanja programa. Izračunava prosjek
	 * brojeva te na standardni izlaz ispisuje u uzlaznom redosljedu one čija
	 * je vrijednost barem 20 % veća od prosjeka. 
	 * 
	 * @param args Arguementi komandne linije.
	 */
	public static void main(String[] args) {
		List<Double> list = null;
		
		try {
			list = readNumbers();
		}
		catch(IOException e) {
			System.err.println("Error while reading numbers.");
			System.exit(-1);
		}
		
		if(list.isEmpty()) {
			System.err.println("Empty list.");
			System.exit(-1);
		}
		
		calculateAndPrint(list);
	}

	/**
	 * Metoda koja izračunava projesjek i ispisuje samo one decimalne brojeve
	 * čija je vrijednost barem 20% veća od prosjeka brojeva.
	 * 
	 * @param list Lista decimalnih brojeva.
	 */
	private static void calculateAndPrint(List<Double> list) {
		double sum = 0;
		for(Double e : list) {
			sum += e.longValue();
		}
		
		double valid = 1.2 * sum / list.size();
		System.out.println(sum / list.size());
		System.out.println(valid);
		
		Collections.sort(list);
		
		for(Double e : list) {
			if(e.doubleValue() >= valid) {
				System.out.println(e);
			}
 		}
	}

	/**
	 * Metoda koja učitava decimalne brojeve sve do pojave unosa 
	 * <code>quiz</code>.
	 * 
	 * @return Lista učitanih decimalnih brojeva.
	 * @throws IOException Ako je došlo do greške tijekom unosa brojeva.
	 */
	private static List<Double> readNumbers() throws IOException {
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(new BufferedInputStream(System.in), 
						StandardCharsets.UTF_8)
		);
		
		List<Double> list = new ArrayList<>(); 
		while(true) {
			System.out.print("Number: ");
			String line = reader.readLine();
			
			if(line == null) {
				System.exit(-1);
			}
			
			try {
				double value = Double.parseDouble(line.trim());
				list.add(value);
			}
			catch(IllegalArgumentException e) {
				if(line.trim().compareToIgnoreCase("quit") == 0) {
					break;
				}
				
				System.err.println("Illegal input: " + line);
				System.exit(-1);
			}
		}
		
		return list;
	}
}
