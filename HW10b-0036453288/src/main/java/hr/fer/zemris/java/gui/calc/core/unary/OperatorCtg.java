package hr.fer.zemris.java.gui.calc.core.unary;

/**
 * Operator kotangens.
 * @author Igor Smolkovič
 *
 */
public class OperatorCtg implements UnaryOperator {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double calculate(double value) {
		return 1.0 / Math.tan(value);
	}

}
