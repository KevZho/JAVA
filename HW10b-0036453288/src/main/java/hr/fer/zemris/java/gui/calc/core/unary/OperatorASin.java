package hr.fer.zemris.java.gui.calc.core.unary;

/**
 * Operator arkus sinus.
 * @author Igor Smolkovič
 *
 */
public class OperatorASin implements UnaryOperator {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double calculate(double value) {
		return Math.asin(value);
	}

}
