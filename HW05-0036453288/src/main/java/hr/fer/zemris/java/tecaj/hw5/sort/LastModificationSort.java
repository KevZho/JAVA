package hr.fer.zemris.java.tecaj.hw5.sort;

import java.io.File;
import java.io.Serializable;
import java.util.Comparator;

/**
 * Razred <code>LastModificationSort</code> omogućava sortiranje 
 * prema zadnjem vremenu promjene objekta. Ranije mijenjani objekti
 * dolaze prije.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class LastModificationSort implements Comparator<File>, Serializable {

	/**
	 *  Serializable class version number.
	 */
	private static final long serialVersionUID = 2405403405649096490L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compare(File o1, File o2) {
		return Long.compare(o1.lastModified(), o2.lastModified());
	}

}
