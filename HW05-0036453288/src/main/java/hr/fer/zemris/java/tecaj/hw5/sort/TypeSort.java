package hr.fer.zemris.java.tecaj.hw5.sort;

import java.io.File;
import java.io.Serializable;
import java.util.Comparator;

/**
 * Razred <code>TypeSort</code> omogućava sortiranje prema 
 * tipu objekta. Direktoriji dolaze prije datoteka.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class TypeSort implements Comparator<File>, Serializable {

	/**
	 *  Serializable class version number.
	 */
	private static final long serialVersionUID = -7610011424859087737L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compare(File o1, File o2) {
		if(o1.isFile() && o2.isDirectory()) {
			return 1;
		}
		if(o1.isDirectory() && o2.isFile()) {
			return -1;
		}
		return 0;
	}

}
