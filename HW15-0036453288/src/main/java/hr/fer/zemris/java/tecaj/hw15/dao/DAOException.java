package hr.fer.zemris.java.tecaj.hw15.dao;

public class DAOException extends RuntimeException {

	/**
	 * UID
	 */
	private static final long serialVersionUID = -1124309309406970434L;

	public DAOException() {
	}

	public DAOException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	public DAOException(String message) {
		super(message);
	}

	public DAOException(Throwable cause) {
		super(cause);
	}
}
