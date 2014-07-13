package hr.fer.zemris.java.tecaj.hw5.filter;

import java.io.File;

import hr.fer.zemris.java.tecaj.hw5.IFilter;

/**
 * Razred <code>ExtensionFilter</code> omogućava prihvaćanje
 * samo onih objekata koji imaju ekstenziju.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class ExtensionFilter implements IFilter {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean accepts(File f) {
		String name = f.getName();
		return name.matches(".+\\..+$");
	}

}
