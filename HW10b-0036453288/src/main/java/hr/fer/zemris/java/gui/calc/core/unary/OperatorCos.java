package hr.fer.zemris.java.gui.calc.core.unary;

/**
 * Operator kosinus.
 * @author Igor Smolkovič
 *
 */
public class OperatorCos implements UnaryOperator {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double calculate(double value) {
		return Math.cos(value);
	}

}
