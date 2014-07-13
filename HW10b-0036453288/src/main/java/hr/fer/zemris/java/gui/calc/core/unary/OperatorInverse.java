package hr.fer.zemris.java.gui.calc.core.unary;

/**
 * Operator 1/x.
 * @author Igor Smolkovič
 *
 */
public class OperatorInverse implements UnaryOperator {

	/**
	 * Konstanta za usporedbu double brojeva.
	 */
	private static final double EPS = 1E-8;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double calculate(double value) {
		if (Math.abs(value) < EPS) {
			throw new ArithmeticException("Division by zero.");
		}
		return 1.0 / value;
	}

}
