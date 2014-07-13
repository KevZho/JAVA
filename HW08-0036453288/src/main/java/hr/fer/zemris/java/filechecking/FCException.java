package hr.fer.zemris.java.filechecking;

/**
 * Razred koji omogućava bacanje iznimki nastalih tijekom leksičke,
 * sintaksne analize ili tijekom izvođenja programa za provjeru 
 * ispravnosti datoteke.
 *
 * @author Igor Smolkovič
 *
 */
public class FCException extends RuntimeException {
	
	/**
	 * UID
	 */
	private static final long serialVersionUID = -467757618531426717L;

	/**
	 * Konstuktor.
	 */
	public FCException() {
	}
	
	/**
	 * Konstruktor koji prima opis pogreške.
	 * @param msg Opis pogreške
	 */
	public FCException(String msg) {
		super(msg);
	}
}
