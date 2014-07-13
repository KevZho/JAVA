package hr.fer.zemris.java.tecaj.hw5.sort;

import java.io.File;
import java.io.Serializable;
import java.util.Comparator;

/**
 * Razred <code>LengthSort</code> omogućava sortiranje prema 
 * duljini imena objekta. Manja imena dolaze prije.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class LengthSort implements Comparator<File>, Serializable {

	/**
	 *  Serializable class version number.
	 */
	private static final long serialVersionUID = 4516194613214274924L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compare(File o1, File o2) {
		return Integer.compare(
					o1.getName().length(),
					o2.getName().length()
				);
	}

}
