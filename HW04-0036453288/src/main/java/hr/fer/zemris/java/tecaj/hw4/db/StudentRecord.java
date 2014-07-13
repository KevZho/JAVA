package hr.fer.zemris.java.tecaj.hw4.db;


/**
 * Razred <code>StudentRecord</code> pohranjuje podatke o studentima.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class StudentRecord {

	private String jmbag;
	private String lastName;
	private String firstName;
	private int finalGrade;
	
	/**
	 * Konstruktor razreda <code>StudentRecord</code>.
	 * 
	 * @param jmbag JMBAG studenta.
	 * @param lastName Ime studenta.
	 * @param firstName Prezime studenta.
	 * @param finalGrade Konačna ocjena studenta.
	 */
	public StudentRecord(String jmbag, String lastName, String firstName,
			int finalGrade) {
		
		if(jmbag.isEmpty()) {
			String msg = "Empty jmbag.";
			throw new IllegalArgumentException(msg);
		}
		
		if(lastName.isEmpty()) {
			String msg = "Empty lastName.";
			throw new IllegalArgumentException(msg);
		}
		
		if(firstName.isEmpty()) {
			String msg = "Empty firstName.";
			throw new IllegalArgumentException(msg);
		}
		
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}

	/**
	 * Get metoda koja vraća JMBAG studenta.
	 * 
	 * @return String reprezentacija JMBAG-a.
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Get metoda koja vraća prezime studenta.
	 * 
	 * @return Prezime studenta.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Get metoda koja vraća ime studenta.
	 * 
	 * @return Ime studenta.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Get metoda koja vraća konačnu ocjenu studenta.
	 * 
	 * @return Konačna ocjena; 1-5.
	 */
	public int getFinalGrade() {
		return finalGrade;
	}
	
	/**
	 * Overridana metoda equals() koja uspoređuje dva studenta prema
	 * JMBAG-u.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if( !(obj instanceof StudentRecord)) {
			return false;
		}
		
		StudentRecord student = (StudentRecord) obj;
		return this.jmbag.equals(student.jmbag);
	}
	
	/**
	 * Overridana metoda hashCode().
	 * 
	 *{@inheritDoc}
	 *
	 */
	@Override
	public int hashCode() {
		return this.jmbag.hashCode();
	}
}
