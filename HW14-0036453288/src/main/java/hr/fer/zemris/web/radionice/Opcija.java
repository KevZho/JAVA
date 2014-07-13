package hr.fer.zemris.web.radionice;

/**
 * Razred koji implementira opciju koje su spremljene u bazi.
 * @author Igor Smolkovič
 *
 */
public class Opcija implements Comparable<Opcija> {

	/**
	 * Id opcije.
	 */
	private String id;

	/**
	 * Vrijednost opcije.
	 */
	private String vrijednost;

	/**
	 * Konstruktor.
	 * @param id id opcije.
	 * @param vrijednost vrijednost opcije.
	 * @throws NumberFormatException ako se id ne može pretvoriti u long ili ako id nije
	 * 			veći od nula.
	 */
	public Opcija(String id, String vrijednost) throws NumberFormatException {
		super();
		this.id = id;
		/**
		 * Provjera id-a
		 */
		long lId = Long.parseLong(id);
		if (lId <= 0) {
			throw new NumberFormatException("id < 1");
		}
		this.vrijednost = vrijednost;
	}

	/**
	 * Get metoda za dohvaćanje vrijednosti opcije.
	 * @return vrijednost opcije.
	 */
	public String getVrijednost() {
		return vrijednost;
	}

	/**
	 * Set metoda za postavljanje vrijednosti opcije.
	 * @param vrijednost vrijednost opcije.
	 */
	public void setVrijednost(String vrijednost) {
		this.vrijednost = vrijednost;
	}

	/**
	 * Get metoda za dohvaćanje id-a opcije.
	 * @return id opcije.
	 */
	public String getId() {
		return id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(Opcija o) {
		if (this.id == null) {
			if (o.id == null) { return 0; }
			return -1;
		} else if (o.id == null) {
			return 1;
		}
		return this.id.compareTo(o.id);
	}
}
