package hr.fer.zemris.java.tecaj.hw5.sort;

import java.io.File;
import java.io.Serializable;
import java.util.Comparator;

/**
 * Razred <code>SizeSort</code> omogućava sortiranje prema 
 * veličini objekta. Manji objekti dolaze prije većih.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class SizeSort implements Comparator<File>, Serializable {

	/**
	 *  Serializable class version number.
	 */
	private static final long serialVersionUID = -4449669116310140039L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compare(File o1, File o2) {
		return Long.compare(o1.length(), o2.length());
	}

}
