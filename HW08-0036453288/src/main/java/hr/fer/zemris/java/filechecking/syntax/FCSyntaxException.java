package hr.fer.zemris.java.filechecking.syntax;

import hr.fer.zemris.java.filechecking.FCException;

/**
 * Iznimka koja se baca ako je došlo do greške prilikom provođenja
 * sintaksne analize programa.
 *
 * @author Igor Smolkovič
 *
 */
public class FCSyntaxException extends FCException {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 4519090244049143700L;

	/**
	 * Konstruktor.
	 */
	public FCSyntaxException() {
	}

	/**
	 * Konstruktor koji prima i opis pogreške.
	 *
	 * @param msg opis pogreške.
	 */
	public FCSyntaxException(String msg) {
		super(msg);
	}
}
