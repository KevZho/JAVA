package hr.fer.zemris.java.tecaj.hw15.dao;

import hr.fer.zemris.java.tecaj.hw15.model.PollData;
import hr.fer.zemris.java.tecaj.hw15.model.PollOptionsData;

import java.util.List;

/**
 * Sučelje DAO.
 * @author Igor Smolkovič
 *
 */
public interface DAO {

	/**
	 * Dohvaća sve moguće ankete iz baze podataka, ali samo id i title.
	 * 
	 * @return lista mogućih glasanja.
	 */
	public List<PollData> getAvailablePolls();

	/**
	 * Dohvaća željenu anketu iz baze podataka.
	 * 
	 * @param id
	 *            id ankete koja se dohvaća.
	 * @return vraća null ako anketa ne postoji, inače vraća zapis iz baze.
	 */
	public PollData getPoll(long id);

	/**
	 * Dodaje novu anketu u tablicu anketa.
	 * 
	 * @param title
	 *            naziv ankete.
	 * @param message
	 *            poruka ankete.
	 * @return id dodane ankete.
	 */
	public long addPoll(String title, String message);

	/**
	 * 
	 * @param id
	 * @return
	 */
	public List<PollOptionsData> getPollOptions(long id);

	/**
	 * Metoda koja omogućava dodavanje opcija za koje se glaza za pojedinu anketu.
	 * 
	 * @param title
	 *            naziv opcije.
	 * @param link
	 *            link.
	 * @param pollID
	 *            id ankete kojoj pripada.
	 * @param votes
	 *            početni broj glasova.
	 * @return id stvorene opcije.
	 */
	public long addPollOption(String title, String link, long pollID, int votes);

	/**
	 * Metoda koja vraća broj glasova za pojedinu opciju.
	 * 
	 * @param pollID
	 *            id ankete.
	 * @param optionID
	 *            id opcije.
	 * @return broj glasova za traženu opciju.
	 */
	public Integer getOptionCount(long pollID, long optionID);

	/**
	 * Metoda koja osvježava broj glasova pojedine opcije.
	 * 
	 * @param pollID
	 *            id ankete kojoj pripada.
	 * @param optionID
	 *            id opcije.
	 * @param count
	 *            novi broj glasova.
	 */
	public void updateOptionCount(long pollID, long optionID, int count);
	
	/**
	 * Provjerava da li postoji anketa danog imena.
	 * @param title ime ankete koja se provjerava.
	 * @return true ako postoji, inače false.
	 */
	boolean checkIfPollExist(String title);
}
