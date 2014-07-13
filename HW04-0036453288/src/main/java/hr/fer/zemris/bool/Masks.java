package hr.fer.zemris.bool;

import java.util.ArrayList;
import java.util.List;

/**
 * Razred <code>Masks</code> podržava metode tvornice za 
 * instanciranje listi Maski pozivom samo jedne metode.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class Masks {

	/**
	 * Privatni konstuktor razreda <code>Masks</code>.
	 * 
	 */
	private Masks() {
	}
	
	
	/**
	 * Statička metoda koja stvara listu Maski iz liste indeksa određene
	 * veličine.
	 * 
	 * @param size Veličina željene maske.
	 * @param indexes Lista indeksa iz kojih nastaju maske.
	 * @return Lista stvorenih maski.
	 */
	public static List<Mask> fromIndexes(int size, int ... indexes) {
		List<Mask> list = new ArrayList<>();
		
		for(int index : indexes) {
			list.add(Mask.fromIndex(size, index));
		}
		
		return list;
	}
	
	
	/**
	 * Statička metoda koja stvara listu Maski iz liste stringova.
	 * 
	 * @param strings Lista stringova iz kojih nastaje lista maski.
	 * @return Lista maski dobivena iz liste stringova.
	 */
	public static List<Mask> fromStrings(String ... strings) {
		List<Mask> list = new ArrayList<>();
		
		for(String s : strings) {
			list.add(Mask.parse(s));
		}
		
		return list;
	}
}
