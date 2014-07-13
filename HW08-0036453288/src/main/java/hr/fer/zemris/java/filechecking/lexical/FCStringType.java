package hr.fer.zemris.java.filechecking.lexical;

/**
 * Pobrojani tip koji definira tipove stringa.
 *
 * @author Igor Smolkovič
 *
 */
public enum FCStringType {
	/** String koji ne razlikuje velika i mala slova. */
	IGNORE,
	/** String koji predstavlja poruku neuspjeha. **/
	FAIL,
	/** Običan string **/
	NORMAL,
}
