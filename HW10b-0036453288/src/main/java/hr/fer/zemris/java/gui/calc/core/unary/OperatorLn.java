package hr.fer.zemris.java.gui.calc.core.unary;

/**
 * Operator prirodni logaritam.
 * @author Igor Smolkovič
 *
 */
public class OperatorLn implements UnaryOperator {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double calculate(double value) {
		return Math.log(value);
	}

}
