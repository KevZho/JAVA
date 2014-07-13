package hr.fer.zemris.java.gui.calc.core.unary;

/**
 * Operator dekadski logaritam.
 * @author Igor Smolkovič
 *
 */
public class OperatorLog implements UnaryOperator {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double calculate(double value) {
		return Math.log10(value);
	}

}
