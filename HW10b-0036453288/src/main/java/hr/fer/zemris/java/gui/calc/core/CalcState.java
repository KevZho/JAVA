package hr.fer.zemris.java.gui.calc.core;

/**
 * Enum koji definira stanja kalkulatora.
 *
 * @author Igor Smolkovič
 *
 */
public enum CalcState {
	/**
	 * Sljedeći znak briše sadržaj ekrana.
	 */
	ERROR,
	/**
	 * Sljedeći broj briše sadržaj ekrana.
	 */
	CLEAR,
	/**
	 * Normalan unos.
	 */
	INPUT,
	/**
	 * Omogućava više puta za redom tiskanje =.
	 */
	CHAIN
}
