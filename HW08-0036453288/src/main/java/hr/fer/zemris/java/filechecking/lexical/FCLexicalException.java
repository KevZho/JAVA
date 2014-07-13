package hr.fer.zemris.java.filechecking.lexical;

import hr.fer.zemris.java.filechecking.FCException;

/**
 * Iznimka koja se baca ako je došlo do greške prilikom provedbe leksičke
 * analize programa.
 *
 * @author Igor Smolkovič
 *
 */
public class FCLexicalException extends FCException {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 8638607286995286439L;

	/**
	 * Konstruktor.
	 */
	public FCLexicalException() {
	}

	/**
	 * Konstruktor koji prima i opis pogreške.
	 *
	 * @param msg opis pogreške.
	 */
	public FCLexicalException(String msg) {
		super(msg);
	}
}
