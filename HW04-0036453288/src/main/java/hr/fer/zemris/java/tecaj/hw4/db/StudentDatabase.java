package hr.fer.zemris.java.tecaj.hw4.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Razred <code>StudentDatabase</code> sprema podatke o studentima
 * te omogućava dohvaćenje podataka prema JMBAG-u ili prema nekom
 * kriteriju filtriranja.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class StudentDatabase {

	private Map<String, StudentRecord> db;
	
	/**
	 * Konstruktor razreda <code>StudentDatabase</code>.
	 * 
	 * @param list Lista stringova koji sadrže zapise o studentima.
	 */
	public StudentDatabase(List<String> list) {
		if(list == null) {
			String msg = "Empty students list";
			throw new IllegalArgumentException(msg);
		}
		
		db = new HashMap<String, StudentRecord>();
		
		for(String line : list) {
			try {
				String[] parts = splitLine(line);
				StudentRecord record = new StudentRecord(
						parts[ 0 ], 
						parts[ 1 ], 
						parts[ 2 ], 
						Integer.parseInt(parts[ 3 ])
				);
				
				if(db.containsKey(parts[ 0 ])) {
					System.err.println("Record already exists " + line);
				}
				else {
					db.put(parts[ 0 ], record);
				}
				
			}
			catch(Exception e) {
				System.err.println("Failed to store record: "+ line);
			}
		}
	}
	
	/**
	 * Metoda koja tokenizira liniju koja predstavlja zapis o
	 * studentu na JMBAG, prezime, ime i ocjenu.
	 * 
	 * @param line String zapis koji sadrži podatke o studentu.
	 * @return Polje stringova koji sadrže jmbag, ime, prezime i ocjenu.
	 */
	private String[] splitLine(String line) {
		String[] array = line.split("\t+");
		
		if(array.length != 4) {
			String msg = "Not enought field";
			throw new IllegalArgumentException(msg);
		}
		else if(!array[ 0 ].matches("\\d+")) {
			throw new IllegalArgumentException("Invalid JMBAG");
		}
		
		return array;
	}
	
	/**
	 * Metoda koja omogućava dohvaćanje studenta prema 
	 * JMBAG-u.
	 * 
	 * @param jmbag JMBAG studenta.
	 * @return Zapis studenta za traženi jmbaga ako postoj, inače null.
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return db.get(jmbag);
	}
	
	/**
	 * Metoda koja omogućava dohvaćanje studenata prema
	 * nekom filtru.
	 * 
	 * @param filter Referenca na filtar.
	 * @return Lista studenata koji zadovoljavaju kriterije.
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> list = new ArrayList<>();
		
		for(StudentRecord record : db.values()) {
			if(filter.accepts(record)) {
				list.add(record);
			}
		}
		
		return list;
	}
}
