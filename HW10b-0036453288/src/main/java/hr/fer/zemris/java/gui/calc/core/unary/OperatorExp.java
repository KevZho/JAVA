package hr.fer.zemris.java.gui.calc.core.unary;

/**
 * Operator eksponencijalna funkcija.
 * @author Igor Smolkovič
 *
 */
public class OperatorExp implements UnaryOperator {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double calculate(double value) {
		return Math.exp(value);
	}

}
