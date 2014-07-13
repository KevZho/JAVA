package hr.fer.zemris.bool;

import java.util.List;

/**
 * Sučelje <code>BooleanSource</code> predstavlja svaki 
 * izvor koji može vratiti ispravnu Boolean vrijednost.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public interface BooleanSource {

	/**
	 * Metoda koja vraća vrijednost izvora.
	 * 
	 * @return BooleanValue vrijednost izvora.
	 */
	public BooleanValue getValue();
	
	
	/**
	 * Metoda koja vraća domenu izvora.
	 * 
	 * @return Domena izvora.
	 */
	public List<BooleanVariable> getDomain();
}
