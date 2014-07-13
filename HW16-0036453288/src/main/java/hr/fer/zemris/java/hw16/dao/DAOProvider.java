package hr.fer.zemris.java.hw16.dao;

import hr.fer.zemris.java.hw16.dao.jpa.JPADAOImpl;

/**
 * Singleton koji omogućava dohvat konkretnog DAO-a.
 * 
 * @author Igor Smolkovič
 * 
 */
public class DAOProvider {

	private static DAO dao = new JPADAOImpl();

	public static DAO getDAO() {
		return dao;
	}
}