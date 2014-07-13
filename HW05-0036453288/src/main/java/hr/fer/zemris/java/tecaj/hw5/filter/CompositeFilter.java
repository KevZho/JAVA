package hr.fer.zemris.java.tecaj.hw5.filter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw5.IFilter;

/**
 * Razred <code>CompositeFilter</code> omogućava kombiniranje 
 * više tipova filtera.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class CompositeFilter implements IFilter {

	/**
	 * Lista filtera.
	 */
	private List<IFilter> list;
	
	
	/**
	 * Konstruktor razreda <code>CompositeFilter</code> prima
	 * listu filtera.
	 * @param filters Lista filtera.
	 */
	public CompositeFilter(List<IFilter> filters) {
		this.list = new ArrayList<>(filters);
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean accepts(File f) {
		for(IFilter filter : list) {
			boolean ret = filter.accepts(f);
			
			if(ret == false) {
				return false;
			}
		}
		
		return true;
	}

}
