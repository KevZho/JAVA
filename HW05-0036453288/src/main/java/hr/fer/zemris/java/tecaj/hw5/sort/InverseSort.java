package hr.fer.zemris.java.tecaj.hw5.sort;

import java.io.File;
import java.io.Serializable;
import java.util.Comparator;

/**
 * Razred <code>InverseSort</code> omogućava invertiranje
 * onoga što bi inače neki komparator vratio.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class InverseSort implements Comparator<File>, Serializable {
	
	/**
	 *  Serializable class version number.
	 */
	private static final long serialVersionUID = 7661148085929500890L;
	
	/**
	 * Komparator koji se invertira.
	 */
	private Comparator<File> comparator;
	
	
	/**
	 * Konstruktor razreda <code>InverseSort</code> prima komparator
	 * koji se invertira.
	 * 
	 * @param comparator Komparator koji se invertira.
	 */
	public InverseSort(Comparator<File> comparator) {
		this.comparator = comparator;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compare(File o1, File o2) {
		int ret = comparator.compare(o1, o2);
		return -1 * ret;
	}

}
