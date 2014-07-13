package hr.fer.zemris.java.tecaj.hw6.problem1a;

/**
 * Promatrač <code>SquareValue</code> ispisuje kvadrat {@link Integer} broja 
 * pohranjenog u <code>IntegerStorage</code>.
 *
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class SquareValue implements IntegerStorageObserver {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {
		if (istorage == null) {
			throw new IllegalArgumentException("Null pointer");
		}
		System.out.println("square is " + istorage.getValue() * istorage.getValue());
	}
}
