package hr.fer.zemris.java.tecaj.hw5.filter;

import java.io.File;

import hr.fer.zemris.java.tecaj.hw5.IFilter;

/**
 * Razred <code>InverseFilter</code> omogućava vraćanje
 * suprotne informacije o prihvačanju datoteke nego bi
 * inače neki filter vratio.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class InverseFilter implements IFilter {

	/**
	 * Filter koji se invertira.
	 */
	private IFilter filter;
	
	/**
	 * Konstruktor razreda <code>InverseFilter</code> prima 
	 * referencu na neku implementaciju filtera.
	 * 
	 * @param filter Filter koji se invertira.
	 */
	public InverseFilter(IFilter filter) {
		this.filter = filter;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean accepts(File f) {
		boolean ret = filter.accepts(f);
		return !ret;
	}

}
