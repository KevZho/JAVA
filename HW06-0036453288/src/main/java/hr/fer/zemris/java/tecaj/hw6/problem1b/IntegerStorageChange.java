package hr.fer.zemris.java.tecaj.hw6.problem1b;


/**
 * Razred <code>IntegerStorageChange</code> omogućava pamćenje vrijednosti
 * koja je bila pohranjena u subjektu prije izmjene. 
 *
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class IntegerStorageChange {

	/**
	 * Referenca na <code>IntegerStorage</code>.
	 */
	private IntegerStorage storage;
	/**
	 * Vrijednost koja je bila prije promjene.
	 */
	private int beforeValue;
	/**
	 * Trenutna vrijednost
	 */
	private int currentValue;
	
	/**
	 * Konstruktor razreda <code>IntegerStorageChange</code> prima referencu na
	 * subjekt, vrijednost koja je bila prije promjene te novu vrijednost.
	 *
	 * @param storage Referenca na subjekt.
	 * @param beforeValue Vrijednost prije promjene.
	 * @param currentValue Vrijednost nakon promjene.
	 */
	public IntegerStorageChange(IntegerStorage storage, int beforeValue,
			int currentValue) {
		super();
		this.storage = storage;
		this.beforeValue = beforeValue;
		this.currentValue = currentValue;
	}

	/**
	 * Get metoda za dohvaćanje reference na subjekt.
	 *
	 * @return Referenca na subjekt.
	 */
	public IntegerStorage getStorage() {
		return storage;
	}
	
	/**
	 * Get metoda za dohvaćanje vrijednosti koja je bila prije
	 * trenutne u subjektu.
	 *
	 * @return Vrijednost koju je bila prije trenutne u subjektu.
	 */
	public int getBeforeValue() {
		return beforeValue;
	}
	
	/**
	 * Get metoda za dohvaćanje trenutne vrijednosti u subjektu.
	 *
	 * @return Trenutna vrijednost u subjektu.
	 */
	public int getCurrentValue() {
		return currentValue;
	}	
}
