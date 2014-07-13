package hr.fer.zemris.web.radionice;

/**
 * Razred koji impelemtira iznimku koja se baca ako podaci koji se žele
 * zapisati u bazu nisu ispravni.
 * @author Igor Smolkovič
 *
 */
public class InconsistentDatabaseException extends RuntimeException {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 7669387166901361378L;

	/**
	 * Konstruktor.
	 */
	public InconsistentDatabaseException() {
		super();
	}

	/**
	 * Konstruktor koji prima poruku.
	 * @param msg poruka.
	 */
	public InconsistentDatabaseException(String msg) {
		super(msg);
	}

	/**
	 * Konstruktork koji prima cause.
	 * @param cause cause
	 */
	public InconsistentDatabaseException(Throwable cause) {
		super(cause);
	}
}
