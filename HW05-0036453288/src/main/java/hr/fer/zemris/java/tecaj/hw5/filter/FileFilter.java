package hr.fer.zemris.java.tecaj.hw5.filter;

import java.io.File;

import hr.fer.zemris.java.tecaj.hw5.IFilter;

/**
 * Razred <code>FileFilter</code> omogućava prihvaćanje
 * samo onih objekata koji su datoteke.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class FileFilter implements IFilter {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean accepts(File f) {
		if(!f.isDirectory()) {
			return true;
		}
		return false;
	}

}
