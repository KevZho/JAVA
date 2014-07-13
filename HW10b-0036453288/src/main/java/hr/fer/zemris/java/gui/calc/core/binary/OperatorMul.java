package hr.fer.zemris.java.gui.calc.core.binary;

/**
 * Operator množenja.
 * @author Igor Smolkovič
 *
 */
public class OperatorMul implements BinaryOperator {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double calculate(double first, double second) {
		return first * second;
	}

}
