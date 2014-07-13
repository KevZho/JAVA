package hr.fer.zemris.java.tecaj.hw6.problem1a;

/**
 * Promatrač <code>DoubleValue</code> ispisuje dvostruku vrijednost
 * vrijednosti pohranjene u <code>IntegerStorage</code>.
 *
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class DoubleValue implements IntegerStorageObserver {
	/**
	 * Zastavica koja označava da je promatrač pozvan dva
	 * puta od početka pretplate.
	 */
	private boolean secondTime;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {
		if (istorage == null) {
			throw new IllegalArgumentException("Null pointer");
		}
		System.out.println("Double value: " + 2 * istorage.getValue());
		if (secondTime) {
			istorage.removeObserver(this);
		}
		secondTime = true;
	}
}
