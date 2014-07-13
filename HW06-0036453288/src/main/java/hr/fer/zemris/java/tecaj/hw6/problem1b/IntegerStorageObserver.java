package hr.fer.zemris.java.tecaj.hw6.problem1b;

/**
 * Sučelje <code>IntegerStorageObserver</code> propisuje metode za
 * obavještavanje o promjenama u subjektu. 
 *
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public interface IntegerStorageObserver {
	/**
	 * Metoda koja se poziva prilikom promjene stanja subjekta.
	 *
	 * @param istorage referenca na subjekt.
	 * @throws IllegalArgumentException ako je došlo do greške.
	 */
	public void valueChanged(IntegerStorageChange istorage);
}
