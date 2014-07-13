package hr.fer.zemris.java.gui.calc.core.binary;


/**
 * Operator dijeljenja.
 * @author Igor Smolkovič
 *
 */
public class OperatorDiv implements BinaryOperator {

	/**
	 * Konstanta za usporedbu double brojeva.
	 */
	private static final double EPS = 1E-8;

	/**
	 * {@inheritDoc}
	 * @throws ArithmeticException ako je došlo do dijeljenja s nulom.
	 */
	@Override
	public double calculate(double first, double second) {
		if (Math.abs(second) < EPS) {
			throw new ArithmeticException("Division by zero");
		}
		return first / second;
	}

}
