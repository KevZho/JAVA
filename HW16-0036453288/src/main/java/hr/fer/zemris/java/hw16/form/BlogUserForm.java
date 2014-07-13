package hr.fer.zemris.java.hw16.form;

import hr.fer.zemris.java.hw16.model.BlogUser;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * String verzija razreda BlogUser. Koristi se za validaciju forme za
 * registraciju novog korisnika.
 * 
 * @author Igor Smolkovič
 * 
 */
public class BlogUserForm {

	private String firstName;

	private String lastName;

	private String nick;

	private String email;

	private String password;

	// Map<property,tekst-greske>
	Map<String, String> greske = new HashMap<String, String>();

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Map<String, String> getGreske() {
		return greske;
	}

	public void setGreske(Map<String, String> greske) {
		this.greske = greske;
	}

	public BlogUserForm() {
	}

	/**
	 * Metoda koja dohvaća pogrešku za traženo ime.
	 * 
	 * @param ime
	 *            ime za koje se dohvaća pogreške.
	 * @return pogreška za traženo ime.
	 */
	public String dohvatiPogresku(String ime) {
		return greske.get(ime);
	}

	/**
	 * Ispituje da li postoje pogreške.
	 * 
	 * @return <code>true</code> ako postoje pogreške, inače false.
	 */
	public boolean imaPogresaka() {
		return !greske.isEmpty();
	}

	/**
	 * Metoda koja ispituje da li postoji pogreška za traženo ime.
	 * 
	 * @param ime
	 *            ime za koje se isputuje postojanje pogreške.
	 * @return pogreška pridjeljenja traženom imenu.
	 */
	public boolean imaPogresku(String ime) {
		return greske.containsKey(ime);
	}

	/**
	 * Metoda koja uzima podatke iz dobivenog HttpRequesta.
	 * 
	 * @param req
	 *            request iz kojeg se uzimaju podaci.
	 */
	public void popuniIzHttpRequesta(HttpServletRequest req) {
		this.firstName = pripremi(req.getParameter("firstName"));
		this.lastName = pripremi(req.getParameter("lastName"));
		this.email = pripremi(req.getParameter("email"));
		this.nick = pripremi(req.getParameter("nick"));
		this.password = pripremi(req.getParameter("password"));
	}

	/**
	 * Metoda prilagođava dobiveni string. Osigurava da je minimalno što postoji
	 * prazan string.
	 * 
	 * @param s
	 *            string koji se priprema.
	 * @return prazan string ako je dobiven null, inače dobiveni string.
	 * @throws UnsupportedEncodingException
	 */
	private String pripremi(String s) {
		if (s == null) {
			return "";
		}
		return s.trim();
	}

	/**
	 * Metoda koja popunjava radionicu na temelju trenutnih podataka.
	 * 
	 * @param r
	 *            radionica u koju se premaju podaci.
	 * @param oprema
	 *            mapa dostupne opreme.
	 * @param publika
	 *            mapa dostupne publike.
	 * @param trajanje
	 *            mapa dostuspnih trajanja.
	 */
	public void popuniUBlogUser(BlogUser u) {
		u.setFirstName(this.firstName);
		u.setLastName(this.lastName);
		u.setEmail(this.email);
		u.setNick(this.nick);
		u.setPasswordHash(this.password);
	}

	/**
	 * Metoda koja provjerava ispravnost unesenih podataka.
	 */
	public void validiraj(boolean nickUsed) {
		if (!provjeriEmail(this.email)) {
			greske.put("email", "E-mail nije ispravan");
		}
		if (nick.isEmpty()) {
			greske.put("nick", "Nadimak mora biti zadan.");
		} else if (nickUsed) {
			greske.put("nick", "Nadimak se već koristi.");
		}
	}

	/**
	 * Metoda koja popunjava formu iz BlogUser objekta.
	 * 
	 * @param u
	 *            objekt iz kojeg se čitaju podaci.
	 */
	public void popuniIzBlogUser(BlogUser u) {
		this.firstName = u.getFirstName();
		this.lastName = u.getLastName();
		this.email = u.getEmail();
		this.nick = u.getNick();
		this.password = u.getPasswordHash();
	}

	/**
	 * Metoda koja provjerava ispravnost emaila.
	 * 
	 * @param email
	 *            email koji se provjerava.
	 * @return <code>true</code> ako je ispravan, inače false.
	 */
	private boolean provjeriEmail(String email) {
		return email
				.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$");
	}
}
