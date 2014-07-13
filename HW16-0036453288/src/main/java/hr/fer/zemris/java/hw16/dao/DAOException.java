package hr.fer.zemris.java.hw16.dao;

/**
 * Iznimka koja se baca ako dođe do pogreške u komunikaciji
 * s bazom.
 * @author Igor Smolkovič
 *
 */
public class DAOException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	public DAOException(String message) {
		super(message);
	}
}