package hr.fer.zemris.java.gui.calc.core.unary;

/**
 * Operator tangens.
 * @author Igor Smolkovič
 *
 */
public class OperatorTan implements UnaryOperator {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double calculate(double value) {
		return Math.tan(value);
	}

}
