package hr.fer.zemris.java.filechecking.lexical;

/**
 * Razred koji modelira token ulaznog programa.
 *
 * @author Igor Smolkovič
 *
 */
public class FCToken {

	/**
	 * Vrsta tokena.
	 */
	private FCTokenType tokenType;
	/**
	 * Vrijednost tokena.
	 */
	private Object value;

	/**
	 * Konstruktor koji prima tip tokena i vrijednost.
	 *
	 * @param tokenType tip tokena
	 * @param value vrijednost tokena
	 */
	public FCToken(FCTokenType tokenType, Object value) {
		super();
		this.tokenType = tokenType;
		this.value = value;
	}

	/**
	 * Dohvaća vrstu tokena.
	 *
	 * @return vrsta tokena.
	 */
	public FCTokenType getTokenType() {
		return tokenType;
	}

	/**
	 * Dohvaća vrijednost tokena.
	 * @return vrijednost tokena ili null ako nije pridružena vrijednost.
	 */
	public Object getValue() {
		return value;
	}
}
