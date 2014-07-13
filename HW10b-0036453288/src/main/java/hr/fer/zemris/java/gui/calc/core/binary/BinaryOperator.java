package hr.fer.zemris.java.gui.calc.core.binary;

/**
 * Sučelje koje definira osnovu za binarne operatore.
 * @author Igor Smolkovič
 *
 */
public interface BinaryOperator {

	/**
	 * Metoda koja provodi operaciju koju specificira binarni operator.
	 * @param first prvi operand.
	 * @param second drugi operand.
	 * @return vrijednost operacije.
	 */
	double calculate(double first, double second);
}
