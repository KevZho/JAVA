package hr.fer.zemris.java.tecaj.hw15.model;

/**
 * Struktura koja pamti podatke o anketi.
 * 
 * @author Igor Smolkovič
 * 
 */
public class PollData {

	/**
	 * Id.
	 */
	private long id;

	/**
	 * Naslov.
	 */
	private String title;

	/**
	 * Poruka.
	 */
	private String message;

	/**
	 * Metoda za promjenu naslov ankete.
	 * 
	 * @param title
	 *            novi naslov ankete.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Metoda za promjenu poruke ankete.
	 * 
	 * @param message
	 *            nova poruka ankete.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Metoda koja dohvaća id ankete.
	 * 
	 * @return
	 */
	public long getId() {
		return id;
	}

	/**
	 * Metoda koja dohvaća naslov ankete.
	 * 
	 * @return naslov ankete.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Metoda koja dohvaća poruku ankete.
	 * 
	 * @return poruka ankete.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Konstruktor.
	 * 
	 * @param id
	 *            id ankete
	 * @param title
	 *            naslov ankete.
	 * @param message
	 *            poruka uz anketu.
	 */
	public PollData(long id, String title, String message) {
		super();
		this.id = id;
		this.title = title;
		this.message = message;
	}
}
