package hr.fer.zemris.java.tecaj.hw15.model;

/**
 * Struktura koja sadrži podatke o opciji za koju se može glasati u pojedinoj
 * anketi.
 * 
 * @author Igor Smolkovič
 * 
 */
public class PollOptionsData {

	/**
	 * Id.
	 */
	private long id;

	/**
	 * Naziv opcije.
	 */
	private String optionTitle;

	/**
	 * Link koji sadrži opcija.
	 */
	private String optionLink;

	/**
	 * Broj glasova za opciju.
	 */
	private int votesCount;

	/**
	 * Metoda koja dohvaća broj glasova.
	 * 
	 * @return broj glasova.
	 */
	public int getVotesCount() {
		return votesCount;
	}

	/**
	 * Metoda koja mijenja broj glasova.
	 * 
	 * @param votesCount
	 *            novi broj glasova.
	 */
	public void setVotesCount(int votesCount) {
		this.votesCount = votesCount;
	}

	/**
	 * Metoda koja dohvaća id.
	 * 
	 * @return id.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Metoda koja dohvaća naziv opcije.
	 * 
	 * @return naziv opcije.
	 */
	public String getOptionTitle() {
		return optionTitle;
	}

	/**
	 * Metoda koja mijenja naziv opcije.
	 * 
	 * @param optionTitle
	 *            novi naziv opcije.
	 */
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}

	/**
	 * Metoda koja dohvaća link.
	 * 
	 * @return link.
	 */
	public String getOptionLink() {
		return optionLink;
	}

	/**
	 * Metoda koja mijenja link.
	 * 
	 * @param optionLink
	 *            novi link.
	 */
	public void setOptionLink(String optionLink) {
		this.optionLink = optionLink;
	}

	/**
	 * Konstruktor.
	 * 
	 * @param id
	 *            id opcije.
	 * @param optionTitle
	 *            naslov opcije.
	 * @param optionLink
	 *            link.
	 * @param votesCount
	 *            broj glasova.
	 */
	public PollOptionsData(long id, String optionTitle, String optionLink,
			int votesCount) {
		this.id = id;
		this.optionTitle = optionTitle;
		this.optionLink = optionLink;
		this.votesCount = votesCount;
	}
}
