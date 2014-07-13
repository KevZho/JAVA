package hr.fer.zemris.java.tecaj.hw4.db;

/**
 * Razred <code><LastNameFilter</code> implementira sučelje 
 * <code>IFilter</code>. Omogućava filtriranje studenata 
 * prema prezimenu. 
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class LastNameFilter implements IFilter {

	public String mask;
	
	/**
	 * Konstruktor razreda <code>LastNameFilter</code>.
	 * 
	 * @param mask Maska prema kojoj se filtriraju prezimena.
	 */
	public LastNameFilter(String mask) {
		this.mask = mask.replaceAll("\\*", ".*");
	}
	
	/**
	 * Overridana metoda accepts koja odgovara na pitanje
	 * zadovolja li prezime studenta masku po kojoj se filtrira.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public boolean accepts(StudentRecord record) {
		String lastName = record.getLastName();
		return lastName.matches(mask);
	}

}
