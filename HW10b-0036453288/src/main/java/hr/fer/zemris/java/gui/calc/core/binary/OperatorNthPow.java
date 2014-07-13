package hr.fer.zemris.java.gui.calc.core.binary;

/**
 * Operator n-ta potencija od x.
 * @author Igor Smolkovič
 *
 */
public class OperatorNthPow implements BinaryOperator {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double calculate(double first, double second) {
		return Math.pow(first, second);
	}

}
