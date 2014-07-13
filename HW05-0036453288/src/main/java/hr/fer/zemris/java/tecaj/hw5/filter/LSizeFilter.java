package hr.fer.zemris.java.tecaj.hw5.filter;

import java.io.File;

import hr.fer.zemris.java.tecaj.hw5.IFilter;

/**
 * Razred <code>LSizeFilter</code> omogućava prihvaćanje 
 * objekata čije ime ne prelazi zadanu veličinu.
 * 
 * @author Igor Smolkovič
 *
 */
public class LSizeFilter implements IFilter {

	/**
	 * Veličina imena objekta s kojom se uspoređuje.
	 */
	private int size;
	
	
	/**
	 * Konstruktor razreda <code>LSizeFilter</code> prima
	 * veličinu imena objekta s kojom se uspoređuje.
	 * 
	 * @param size Veličina imena za filter.
	 */
	public LSizeFilter(int size) {
		this.size = size;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean accepts(File f) {
		if(f.getName().length() <= size) {
			return true;
		}
		return false;
	}

}
