package hr.fer.zemris.bool;

/**
 * Sučelje <code>NamedBooleanSource</code> omogućava korištenje
 * imenovanih izvora.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public interface NamedBooleanSource extends BooleanSource {

	/**
	 * Metoda koja vraća ime izvora.
	 * 
	 * @return Ime izvora.
	 */
	public String getName();
}
