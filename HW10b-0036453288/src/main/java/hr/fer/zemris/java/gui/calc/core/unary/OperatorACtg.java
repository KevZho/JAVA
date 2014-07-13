package hr.fer.zemris.java.gui.calc.core.unary;

/**
 * Operator arkurs kotangens.
 * @author Igor Smolkovič
 *
 */
public class OperatorACtg implements UnaryOperator {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double calculate(double value) {
		return Math.atan(1.0 / value);
	}

}
