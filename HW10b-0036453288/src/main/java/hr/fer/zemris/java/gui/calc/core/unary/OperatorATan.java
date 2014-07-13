package hr.fer.zemris.java.gui.calc.core.unary;

/**
 * Operator arkus tangens.
 * @author Igor Smolkovič
 *
 */
public class OperatorATan implements UnaryOperator {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double calculate(double value) {
		return Math.atan(value);
	}

}
