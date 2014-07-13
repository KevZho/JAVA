package hr.fer.zemris.java.gui.calc.core.binary;

/**
 * Operator zbrajanja.
 * @author Igor Smolkovič
 *
 */
public class OperatorAdd implements BinaryOperator {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double calculate(double first, double second) {
		return first + second;
	}

}
