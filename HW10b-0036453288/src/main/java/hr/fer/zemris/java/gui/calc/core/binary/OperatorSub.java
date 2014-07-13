package hr.fer.zemris.java.gui.calc.core.binary;

/**
 * Operator oduzimanja.
 * @author Igor Smolkovič
 *
 */
public class OperatorSub implements BinaryOperator {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double calculate(double first, double second) {
		return first - second;
	}

}
