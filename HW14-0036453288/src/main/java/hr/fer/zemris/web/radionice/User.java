package hr.fer.zemris.web.radionice;

import java.io.Serializable;

/**
 * Razred koji implementira strukturu koja omogućava pamćenje
 * podataka za login korisnika.
 * @author Igor Smolkovič
 *
 */
public class User implements Serializable {

	/**
	 * UID
	 */
	private static final long serialVersionUID = -7895091663705674013L;

	/**
	 * Login korisnika.
	 */
	private String login;

	/**
	 * Zaporka korisnika.
	 */
	private String zaporka;

	/**
	 * Ime korisnika.
	 */
	private String ime;

	/**
	 * Prezime korisnika.
	 */
	private String prezime;

	/**
	 * Metoda za dohvaćanje imena za login.
	 * @return ime za login.
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Metoda za dohvaćanje zaporke.
	 * @return zaporka za login.
	 */
	public String getZaporka() {
		return zaporka;
	}

	/**
	 * Metoda za dohvaćanje imena korisnika.
	 * @return ime korisnika.
	 */
	public String getIme() {
		return ime;
	}

	/**
	 * Metoda za dohvaćanje prezimena korisnika.
	 * @return prezime korisnika.
	 */
	public String getPrezime() {
		return prezime;
	}

	/**
	 * Konstruktor.
	 * @param login ime za login korisnika.
	 * @param zaporka zaporka za login korisnika.
	 * @param ime ime korisnika.
	 * @param prezime prezime korisnika.
	 */
	public User(String login, String zaporka, String ime, String prezime) {
		super();
		this.login = login;
		this.zaporka = zaporka;
		this.ime = ime;
		this.prezime = prezime;
	}
}
