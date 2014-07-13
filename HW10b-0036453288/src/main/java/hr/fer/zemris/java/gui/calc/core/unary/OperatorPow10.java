package hr.fer.zemris.java.gui.calc.core.unary;

/**
 * Operator 10^x.
 * @author Igor Smolkovič
 *
 */
public class OperatorPow10 implements UnaryOperator {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double calculate(double value) {
		return Math.pow(10, value);
	}

}
