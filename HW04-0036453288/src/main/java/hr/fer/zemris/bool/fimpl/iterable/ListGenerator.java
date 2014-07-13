package hr.fer.zemris.bool.fimpl.iterable;

import hr.fer.zemris.bool.BooleanFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * Razred <code>ListGenerator</code> omogućava generiranje 
 * listi minterma, maksterma ili don't careova.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class ListGenerator {

	/**
	 * Metoda koja vraća listu minterma za funkciju predanu kao
	 * argument metode.
	 * 
	 * @param f Funkcija za koju se traži lista minterma.
	 * @return Lista minterma prikazanih kao Integer vrijednosti.
	 * @throws IllegalArgumentException ako je predana null referenca.
	 */
	public static List<Integer> getMinterms(BooleanFunction f) {
		if(f == null) {
			String msg = "ListGenerator getMinterms null pointer";
			throw new IllegalArgumentException(msg);
		}
		
		int max = (int)Math.pow(2, f.getDomain().size());
		List<Integer> list = new ArrayList<>();
		
		for(int i = 0; i < max; i++) {
			if(f.hasMinterm(i)) {
				list.add(i);
			}
		}
		
		return list;
	}
	
	
	/**
	 * Metoda koja vraća listu don't careova za funkciju predanu kao
	 * argument metode.
	 * 
	 * @param f Funkcija za koju se traži lista don't careova.
	 * @return Lista don't careova prikazanih kao Integer vrijednosti.
	 * @throws IllegalArgumentException ako je predana null referenca.
	 */
	public static List<Integer> getDontcares(BooleanFunction f) {
		if(f == null) {
			String msg = "ListGenerator getDontcares null pointer";
			throw new IllegalArgumentException(msg);
		}
		
		int max = (int)Math.pow(2, f.getDomain().size());
		List<Integer> list = new ArrayList<>();
		
		for(int i = 0; i < max; i++) {
			if(f.hasDontCare(i)) {
				list.add(i);
			}
		}
		
		return list;
	}
	
	
	/**
	 * Matoda koja vraća listu makstrema za funkciju predanu kao 
	 * argument metode.
	 * 
	 * @param f Funkcija za koju se traži lista maksterma.
	 * @return Lista maksterma prikazanih kao Integer vrijednosti.
	 * @throws IllegalArgumentException ako je predana null referenca.
	 */
	public static List<Integer> getMaxterms(BooleanFunction f) {
		if(f == null) {
			String msg = "ListGenerator getMaxterms null pointer";
			throw new IllegalArgumentException(msg);
		}
		
		int max = (int)Math.pow(2, f.getDomain().size());
		List<Integer> list = new ArrayList<>();
		
		for(int i = 0; i < max; i++) {
			if(f.hasMaxterm(i)) {
				list.add(i);
			}
		}
		
		return list;
	}
}
