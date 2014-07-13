package hr.fer.zemris.java.gui.calc.core.binary;

/**
 * Operator n-ti korijen iz x.
 * @author Igor Smolkovič
 *
 */
public class OperatorNthRoot implements BinaryOperator {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double calculate(double first, double second) {
		return Math.pow(first, 1.0 / second);
	}

}
