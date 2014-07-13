package hr.fer.zemris.java.custom.collections;


/**
 * Razred EmptyStackException implementira iznimku koja se 
 * baca u slučaju da je stog prazan.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */

public class EmptyStackException extends RuntimeException {

	private static final long serialVersionUID = 5019575938076748881L;

	/**
	 * Konstuktor razreda EmptyStackException.
	 */
	public EmptyStackException() {
		super("Empty stack!");
	}
}
