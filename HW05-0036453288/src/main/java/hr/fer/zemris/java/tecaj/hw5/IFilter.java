package hr.fer.zemris.java.tecaj.hw5;

import java.io.File;

/**
 * Sučelje <code>IFilter</code> omogućava filtriranje
 * datoteka prema kriteriju koji je definiran u razredu
 * koji implementira ovo sučelje.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public interface IFilter {

	/**
	 * Metoda koja vraća informaciju o tome zadovalja li ulazna
	 * datoteka kriterije filtera.
	 * 
	 * @param f Datoteka koja se ispituje.
	 * @return true datoteka se prihvaća, inače false.
	 */
	public boolean accepts(File f);
}
