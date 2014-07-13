package hr.fer.zemris.java.gui.calc.core.unary;

/**
 * Sučelje koje predstavlja osnovu za unarne operacije.
 * @author Igor Smolkovič
 *
 */
public interface UnaryOperator {
	
	/**
	 * Metoda koja vraća vrijednost izračunatu konretnim unarnim operatorom.
	 * @param value vrijednost koju koristi operator.
	 * @return vrijednost nakon primjene unarnog operatora.
	 */
	double calculate(double value);
}
