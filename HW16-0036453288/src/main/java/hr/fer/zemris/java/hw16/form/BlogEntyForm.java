package hr.fer.zemris.java.hw16.form;

import hr.fer.zemris.java.hw16.model.BlogEntry;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * String verzija BlogEntry-a. Služi za validaciju forme.
 * 
 * @author Igor Smolkovič
 * 
 */
public class BlogEntyForm {

	private String id;
	private String title;
	private String text;

	// Map<property,tekst-greske>
	Map<String, String> greske = new HashMap<String, String>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String, String> getGreske() {
		return greske;
	}

	public void setGreske(Map<String, String> greske) {
		this.greske = greske;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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

	public BlogEntyForm() {
	}

	/**
	 * Metoda koja uzima podatke iz dobivenog HttpRequesta.
	 * 
	 * @param req
	 *            request iz kojeg se uzimaju podaci.
	 */
	public void popuniIzHttpRequesta(HttpServletRequest req) {
		this.title = pripremi(req.getParameter("title"));
		this.text = pripremi(req.getParameter("text"));
		this.id = pripremi(req.getParameter("id"));
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
	public void popuniUBlogEntry(BlogEntry e) {
		e.setTitle(this.title);
		e.setText(this.text);
	}

	/**
	 * Metoda koja provjerava ispravnost unesenih podataka.
	 */
	public void validiraj() {
		if (this.title.isEmpty()) {
			greske.put("title", "Naslov mora biti zadan");
		}
	}

	/**
	 * Metoda koja popunja formu iz BlogEntry objekta.
	 * 
	 * @param e
	 *            objekt iz kojeg se čita.
	 */
	public void popuniIzBlogEntry(BlogEntry e) {
		this.text = e.getText();
		this.title = e.getTitle();
		if (e.getId() != null) {
			this.id = String.valueOf(e.getId());
		} else {
			this.id = "";
		}
	}

}
