package hr.fer.zemris.java.tecaj.hw5;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Razred <code>Printer</code> omogućava ispisivanje informacija
 * o datotekama na standardni izlaz.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class Printer {
	/**
	 * Lista sadrži reference na objekte koji dohvaćaju
	 * informacije koje se ispisuju.
	 */
	private List<IPrint> fields;
	/**
	 * Mapa pamti maksimalne duljine pojedinih tipova informacija
	 * kako bi ispis mogao biti lijepo formatiran.
	 */
	private Map<Integer, Integer> maxLength;
	
	
	/**
	 * Konstruktor razreda <code>Printer</code> prima listu 
	 * referenci na objekte koji dohvaćaju informacije o 
	 * datotekama(printeri).
	 * 
	 * @param fields Lista printera.
	 */
	public Printer(List<IPrint> fields) {
		if(fields == null) {
			throw new IllegalArgumentException("Printer null reference");
		}
		
		this.fields = new ArrayList<>(fields);
		this.maxLength = new HashMap<>();
	}
	
	
	/**
	 * Metoda koja ažurira maksimalne duljine pojedinih tipova 
	 * informacija.
	 * 
	 * @param f Datoteka sa čijim se informacijama uspoređuju
	 * 			trenutno maksimalne duljine.
	 */
	public void getMaxLength(File f) {
		for(int i = 0; i < fields.size(); i++) {
			IPrint printer = fields.get(i);
			Integer value = maxLength.get(i);
			int size = printer.getValue(f).length();
			
			if(value == null) {
				maxLength.put(i, size);
			}
			else {
				int newValue = Math.max(
						value.intValue(),
						size
				);
				
				maxLength.put(i, newValue);
			}
		}
	}
	
	/**
	 * Metoda koja ispisuje zaglavlje ispisa na standardni izlaz.
	 * 
	 */
	public void printHeader() {
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < fields.size(); i++) {
			int length = maxLength.get(i).intValue();
			builder.append(String.format("+%0" + (length + 2) + "d", 0).replace("0","-"));
		}
		
		builder.append("+");
		System.out.println(builder.toString());
	}
	
	/**
	 * Metoda koja ispisuje informacije o jednoj datoteci na standardni
	 * izlaz.
	 * 
	 * @param f Datoteka čije se informacije ispisuju.
	 */
	public void printLine(File f) {
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < fields.size(); i++) {
			int length = maxLength.get(i).intValue();
			IPrint printer = fields.get(i);
			builder.append("|");
			
			if(printer.getAlignment() == AlignmentValue.LEFT) { // lijevo poravnanje
				builder.append(" ").append(printer.getValue(f));
				length -= printer.getValue(f).length();
				builder.append(String.format("%0" + (length+1) + "d", 0).replace("0"," "));
			}
			else if(printer.getAlignment() == AlignmentValue.RIGHT) { // desno poravnanje
				length -= printer.getValue(f).length();
				builder.append(String.format("%0" + (length+1) + "d", 0).replace("0"," "))
						.append(printer.getValue(f)).append(" ");
			}
			else { // inače je centrirano
				int diff = length - printer.getValue(f).length();
				int left =  diff / 2;
				int right = left;

				if(diff % 2 != 0) {
					right++;
				}

				builder.append(String.format("%0" + (left+1) + "d", 0).replace("0"," "))
					.append(printer.getValue(f))
					.append(String.format("%0" + (right+1) + "d", 0).replace("0"," "));
			}
		}
		
		builder.append("|");
		System.out.println(builder.toString());
	}
}
