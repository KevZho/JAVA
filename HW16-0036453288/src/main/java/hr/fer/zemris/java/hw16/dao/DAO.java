package hr.fer.zemris.java.hw16.dao;

import java.util.List;

import hr.fer.zemris.java.hw16.model.BlogComment;
import hr.fer.zemris.java.hw16.model.BlogEntry;
import hr.fer.zemris.java.hw16.model.BlogUser;

public interface DAO {

	/**
	 * Dohvaća entry sa zadanim <code>id</code>-em. Ako takav entry ne postoji,
	 * vraća <code>null</code>.
	 * 
	 * @param id
	 *            ključ zapisa
	 * @return entry ili <code>null</code> ako entry ne postoji
	 * @throws DAOException
	 *             ako dođe do pogreške pri dohvatu podataka
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;

	/**
	 * Metoda koja dohvaća sve blogove za korisnika koji koristi dani nadimak.
	 * 
	 * @param nick
	 *            nadimak korisnik.
	 * @return lista bloga.
	 * @throws DAOException
	 *             ako dođe do pogreške pri dohvatu podataka.
	 */
	public BlogEntry getBlogEntries(String nick) throws DAOException;

	/**
	 * Dohvaća korisnika za traženi nick.
	 * 
	 * @param nick
	 *            nick za koji se dohvaćaju podaci o korisniku.
	 * @return instancu BlogUser ako je pronađen inače null.
	 * @throws DAOException
	 *             ako dođe do pogreške pri dohvatu podataka.
	 */
	public BlogUser getBlogUser(String nick) throws DAOException;

	/**
	 * Dohvaća sve autore blogova.
	 * 
	 * @return lista autora.
	 * @throws DAOException
	 *             ako dođe do pogreške pri dohvatu podataka.
	 */
	public List<BlogUser> getAllUsers() throws DAOException;

	/**
	 * Dodaje novog korisnika u bazu podataka. Novi korisnik će biti dodan samo
	 * ako traženi nick već nije iskorišten.
	 * 
	 * @param u
	 *            korisnik koji se dodaje u bazu.
	 * @return true ako je uspjelo dodavanje, inače false.
	 * @throws DAOException
	 *             ako nije uspjelo dodavanje u bazu.
	 */
	public void addBlogUser(BlogUser u) throws DAOException;

	/**
	 * Dohvaća listu postova za traženog autora.
	 * 
	 * @param nick
	 *            autor za kojeg se dohvaćaju postovi.
	 * @return lista postova traženog autora.
	 * @throws DAOException
	 *             ako dođe do pogreške pri dohvatu podataka.
	 */
	public List<BlogEntry> getBlogEntriesForNick(String nick)
			throws DAOException;

	/**
	 * Omogućava dodavanje ili osvježavanje bloga.
	 * 
	 * @param e
	 *            unos koji se dodaje ili osvježava.
	 * @param update
	 *            true ako se radi osvježavanje, inače false.
	 */
	public void addUpdateBlogEntry(BlogEntry e, boolean update);

	/**
	 * Metoda koja pridružuje komentar blogu.
	 * 
	 * @param comment
	 *            komentar koji se dodaje.
	 * @param e
	 *            blok kojemu se pridružuje komentar.
	 */
	public void addComment(BlogComment comment);

}