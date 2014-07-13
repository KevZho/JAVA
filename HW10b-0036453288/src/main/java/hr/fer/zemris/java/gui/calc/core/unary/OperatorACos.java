package hr.fer.zemris.java.gui.calc.core.unary;

/**
 * Operator arkus kosinus.
 * @author Igor Smolkovič
 *
 */
public class OperatorACos implements UnaryOperator {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double calculate(double value) {
		return Math.acos(value);
	}

}
