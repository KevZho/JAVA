package hr.fer.zemris.java.tecaj.hw5.sort;

import java.io.File;
import java.io.Serializable;
import java.util.Comparator;

/**
 * Razred <code>ExecutionSort</code> omogućava sortiranje prema
 * izvršivosti. Datoteke koje se mogu izvršavati dolaze prije.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class ExecutionSort implements Comparator<File>, Serializable {

	private Comparator<File> comparator;
	
	/**
	 * Konstruktor razreda <code>ExecutionSort</code> stvara comparator za usporedbu
	 * po tipu koji stavlja datoteke ispred direktorija.
	 * 
	 */
	public ExecutionSort() {
		this.comparator = new InverseSort(new TypeSort());
	}
	
	/**
	 *  Serializable class version number.
	 */
	private static final long serialVersionUID = -6134078811083567625L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compare(File o1, File o2) {
		// postavi datoteke ispred direktorija
		int ret = comparator.compare(o1, o2);
		
 		// pretpostavka komparatora: direktoriji se ne mogu izvršavati
		if(ret == -1 && o1.canExecute()) {
			return -2;
		}
		if(ret == 0 && o1.canExecute() && !o2.canExecute()) {
			return -1;
		}	
		
		return ret;
	}

}
