package hr.fer.zemris.java.tecaj.hw6.problem1b;


/**
 * Promatrač <code>SquareValue</code> ispisuje kvadrat {@link Integer} broja 
 * pohranjenog u subjektu.
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
	public void valueChanged(IntegerStorageChange istorage) {
		if (istorage == null) {
			throw new IllegalArgumentException("Null pointer");
		}
		int value = istorage.getCurrentValue();
		System.out.println("square is " + value * value);
	}
}
