package hr.fer.zemris.java.tecaj.hw5.sort;

import java.io.File;
import java.io.Serializable;
import java.util.Comparator;

/**
 * Razred <code>NameSort</code> omogućava sortiranje prema imenu
 * objekta. Leksikografski manja imena dolaze prije.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class NameSort implements Comparator<File>, Serializable {

	/**
	 *  Serializable class version number.
	 */
	private static final long serialVersionUID = 8315449945919263095L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compare(File o1, File o2) {
		return o1.getName().compareTo(o2.getName());
	}

}
