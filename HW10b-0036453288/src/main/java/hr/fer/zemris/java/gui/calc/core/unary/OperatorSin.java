package hr.fer.zemris.java.gui.calc.core.unary;

/**
 * Operator sinus.
 * @author Igor Smolkovič
 *
 */
public class OperatorSin implements UnaryOperator {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double calculate(double value) {
		return Math.sin(value);
	}

}
