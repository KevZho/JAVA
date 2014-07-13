package com.app.addressbook.contact;

import java.io.Serializable;

/**
 * Razred koji modelira kontakt.
 *
 * @author Igor Smolkovič
 *
 */
public class Contact  implements Serializable {

	/**
	 * UID
	 */
	private static final long serialVersionUID = -8969640316072744866L;

	/**
	 * Ime i prezime kontakta.
	 */
	private String name;

	/**
	 * Broj telefona.
	 */
	private String phone;

	/**
	 * E-Mail kontakta.
	 */
	private String email;

	/**
	 * Bilješka za kontakt.
	 */
	private String note;

	/**
	 * Link na facebook profil.
	 */
	private String facebook;

	/**
	 * Metoda za dohvaćanje imena i prezimena kontakta.
	 *
	 * @return ime i prezime kontakta
	 */
	public String getName() {
		return name;
	}

	/**
	 * Metoda za dohvaćanje telefonskog broja kontakta.
	 *
	 * @return telefonski broj kontakta
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Metoda za dohvaćanje E-Maila kontakta.
	 *
	 * @return e-mail kontakta
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Metoda za dohvaćanje bilješke kontakta.
	 *
	 * @return bilješka kontakta
	 */
	public String getNote() {
		return note;
	}

	/**
	 * Metoda za dohvaćanje linka na facebook profil.
	 *
	 * @return link na facebook profil
	 */
	public String getFacebook() {
		return facebook;
	}

	/**
	 * Konstruktor.
	 *
	 * @param name ime i prezime
	 * @param phone telefonski broj
	 * @param email e-mail
	 * @param note bilješka.
	 * @param facebook link na facebook profil
	 */
	public Contact(String name, String phone, String email, String note, String facebook) {
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.note = note;
		this.facebook = facebook;
	}
}
