package hr.fer.zemris.java.tecaj.hw5.filter;

import java.io.File;

import hr.fer.zemris.java.tecaj.hw5.IFilter;

/**
 * Razred <code>SSizeFilter</code> omogućava prihvaćanje objekata
 * čija veličina ne prelazi traženu veličinu.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class SSizeFilter implements IFilter {

	/**
	 * Veličina objekta do koje se prihvaća.
	 */
	private long size;
	
	/**
	 * Konstruktor razreda <code>SSizeFilter</code> prima veličinu
	 * objekta.
	 * 
	 * @param size Veličina objekta do koje se prihvaćaju objekti.
	 */
	public SSizeFilter(long size) {
		this.size = size;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean accepts(File f) {
		if(f.length() <= size) { 
			return true;
		}
		return false;
	}

}
