package hr.fer.zemris.java.tecaj.hw5;

import java.io.File;

/**
 * Sučelje <code>IPrint</code> omogućava dohvaćanje informacija o
 * datotekama. Sadržaj koji se dohvaća definira se u razredu koji
 * implementira ovo sučelje.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public interface IPrint {

	/**
	 * Metoda koja dohvaća string reprezentaciju informacije
	 * koja se traži od ulazne datoteke.
	 * 
	 * @param f Datoteka na temelju koje se traži informacija.
	 * @return String reprezentacija tražene informacije.
	 */
	public String getValue(File f);
	
	
	/**
	 * Metoda koja vraća informaciju o tome kakvo se poravnanje 
	 * traži prilikom ispisa informacije na standardni izlaz.
	 * 
	 * @return Tip poravnanja. 
	 */
	public AlignmentValue getAlignment();
}
