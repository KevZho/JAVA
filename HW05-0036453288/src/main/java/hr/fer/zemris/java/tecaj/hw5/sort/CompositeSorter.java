package hr.fer.zemris.java.tecaj.hw5.sort;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Razred <code>CompositeSorter</code> omogućava ulančavanje
 * više tipova sortiranaj.
 * 
 * @author Igor Smolkovič
 *
 */
public class CompositeSorter implements Comparator<File>, Serializable {

	/**
	 *  Serializable class version number.
	 */
	private static final long serialVersionUID = -5712321059475704627L;
	
	/**
	 * Lista komparatora za sortiranje.
	 */
	private List<Comparator<File>> list;
	
	
	/**
	 * Konstruktor razreda <code>CompositeSorter</code> prima listu
	 * komparatora za sortiranje.
	 * 
	 * @param list
	 */
	public CompositeSorter(List<Comparator<File>> list) {
		this.list = new ArrayList<>(list);
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compare(File o1, File o2) {
		for(Comparator<File> c : list) {
			int ret = c.compare(o1, o2);
			if(ret != 0) {
				return ret;
			}
		}
		
		return 0;
	}

}
