package hr.fer.zemris.java.filechecking.lexical;

/**
 * Razred koji pohranjuje string vrijednost. 
 *
 * @author Igor Smolkovič
 *
 */
public class FCString {
	/**
	 * Vrijednost FCStringa.
	 */
	private String value;
	/**
	 * Tip stringa.
	 */
	private FCStringType stringType;

	/**
	 * Konstruktor.
	 * @param stringType tip stringa.
	 * @param value vrijednost koju pamti FCString.
	 */
	public FCString(FCStringType stringType, String value) {
		this.stringType = stringType;
		this.value = value;
	}

	/**
	 * Dohvaća pohranju vrijednost.
	 *
	 * @return pohranjena vrijednost.
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Dohvaća tip stringa.
	 *
	 * @return tip stringa.
	 */
	public FCStringType getStringType() {
		return stringType;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return value;
	}
}
