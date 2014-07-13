package hr.fer.zemris.web.radionice;

import java.util.Set;
import java.util.TreeSet;

/**
 * Razred koji implementira radionicu.
 * @author Igor Smolkovič
 *
 */
public class Radionica implements Comparable<Radionica> {

	/**
	 * Id radionice.
	 */
	private Long id;

	/**
	 * Naziv radionice.
	 */
	private String naziv;

	/**
	 * Datum kada se održava.
	 */
	private String datum;

	/**
	 * Oprema koja se koristi.
	 */
	private Set<Opcija> oprema;

	/**
	 * Trajanje radionce.
	 */
	private Opcija trajanje;

	/**
	 * Tip/ tipovi publike.
	 */
	private Set<Opcija> publika;

	/**
	 * Maksimalni broj polaznika [10, 50].
	 */
	private Integer maksPolaznika;

	/**
	 * Email.
	 */
	private String email;

	/**
	 * Dodatne informacije.
	 */
	private String dopuna;

	/**
	 * Get metoda za dohvaćanje id-a.
	 * @return id radionice.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Metoda za postavljanje novog id-a.
	 * @param id novi id.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Get metoda za dohvaćanje naziva radionice.
	 * @return naziv radionice.
	 */
	public String getNaziv() {
		return naziv;
	}

	/**
	 * Metoda za postavljanje novog naziva radionce.
	 * @param naziv novi naziv radionice.
	 */
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	/**
	 * Get metoda za dohvaćanje vremena održavanja radionice.
	 * @return vrijeme održavanja radionice.
	 */
	public String getDatum() {
		return datum;
	}

	/**
	 * Metoda za promjenu datuma radionice.
	 * @param datum novi datum radionice.
	 */
	public void setDatum(String datum) {
		this.datum = datum;
	}

	/**
	 * Get metoda za dohvaćanje opreme.
	 * @return
	 */
	public Set<Opcija> getOprema() {
		if (oprema == null) {
			return new TreeSet<>();
		}
		return oprema;
	}

	/**
	 * Metoda za promjenu opreme koja se koristi na radionic.
	 * @param oprema nova oprema koja se koristi na radionici.
	 */
	public void setOprema(Set<Opcija> oprema) {
		this.oprema = oprema;
	}

	/**
	 * Get metoda za dohvaćanje trajanja radionice.
	 * @return trajanje radionice.
	 */
	public Opcija getTrajanje() {
		return trajanje;
	}

	/**
	 * Set metoda za postaljanje trajanja radionice.
	 * @param trajanje novo trajanje radionice.
	 */
	public void setTrajanje(Opcija trajanje) {
		this.trajanje = trajanje;
	}

	/**
	 * Get metoda za dohvaćanje tipova publike.
	 * @return tipovi publike.
	 */
	public Set<Opcija> getPublika() {
		if (publika == null) {
			return new TreeSet<>();
		}
		return publika;
	}

	/**
	 * Set metoda za promjenu tipova publike.
	 * @param publika novi tipovi publike.
	 */
	public void setPublika(Set<Opcija> publika) {
		this.publika = publika;
	}

	/**
	 * Get metoda za dohvaćanje maksimalnog broja polaznika.
	 * @return maks broj polaznika.
	 */
	public Integer getMaksPolaznika() {
		return maksPolaznika;
	}

	/**
	 * Metoda za promjenu maksimalnog broja polaznika.
	 * @param maksPolaznika novi maksimalni broj polaznika.
	 */
	public void setMaksPolaznika(Integer maksPolaznika) {
		this.maksPolaznika = maksPolaznika;
	}

	/**
	 * Metoda za dohvaćanje emaila.
	 * @return email.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Metoda za promjenu emaila.
	 * @param email novi email.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Metoda za dohvat dopune.
	 * @return
	 */
	public String getDopuna() {
		return dopuna;
	}

	/**
	 * Metoda za promjenu dopune.
	 * @param dopuna nova dopuna.
	 */
	public void setDopuna(String dopuna) {
		this.dopuna = dopuna;
	}

	/**
	 * Konstruktor.
	 * @param id id radionice.
	 * @param naziv naziv radionice.
	 * @param datum datum održavanja radionice.
	 * @param oprema oprema koja se koristi na radionici.
	 * @param trajanje trajanje radionice.
	 * @param publika tipovi publike prisutne na radionici.
	 * @param maksPolaznika maksimalni broj polaznika.
	 * @param email email za kontakt.
	 * @param dopuna dodatne informacije o radionici.
	 */
	public Radionica(Long id, String naziv, String datum, Set<Opcija> oprema,
			Opcija trajanje, Set<Opcija> publika, Integer maksPolaznika,
			String email, String dopuna) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.datum = datum;
		this.oprema = oprema;
		this.trajanje = trajanje;
		this.publika = publika;
		this.maksPolaznika = maksPolaznika;
		this.email = email;
		this.dopuna = dopuna;
	}

	/**
	 * Default konstruktor.
	 */
	public Radionica() {
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(Radionica o) {
		if (this.id == null) {
			if (o.id == null) return 0;
			return -1;
		} else if (o.id == null) {
			return 1;
		}
		return this.id.compareTo(o.id);
	}
}
