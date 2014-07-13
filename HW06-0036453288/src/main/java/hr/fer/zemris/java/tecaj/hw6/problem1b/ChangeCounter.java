package hr.fer.zemris.java.tecaj.hw6.problem1b;

/**
 * Promatrač <code>ChangeCounter</code> broji i ispisuje broj promjena
 * vrijednosti spremljene u subjektu od početka pretplate.
 *
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class ChangeCounter implements IntegerStorageObserver {
	/**
	 * Broj promjena vrijednosti spremljene u <code>IntegerStorageChange</code>.
	 */
	private int counter;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		if (istorage == null) {
			throw new IllegalArgumentException("Null pointer");
		}
		
		counter++;
		System.out.println("Number of value changes since trackign: " + counter);
	}
}
