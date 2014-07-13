package hr.fer.zemris.java.hw16.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * Implementacija JPAEMFProvidera.
 * 
 * @author Igor Smolkovič
 * 
 */
public class JPAEMFProvider {

	public static EntityManagerFactory emf;

	public static EntityManagerFactory getEmf() {
		return emf;
	}

	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}