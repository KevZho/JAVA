package hr.fer.zemris.java.hw16.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.hw16.dao.DAO;
import hr.fer.zemris.java.hw16.dao.DAOException;
import hr.fer.zemris.java.hw16.model.BlogComment;
import hr.fer.zemris.java.hw16.model.BlogEntry;
import hr.fer.zemris.java.hw16.model.BlogUser;

/**
 * Implementacija DAO-a za konkretni problem.
 * 
 * @author Igor Smolkovič
 * 
 */
public class JPADAOImpl implements DAO {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(
				BlogEntry.class, id);
		return blogEntry;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BlogEntry getBlogEntries(String nick) throws DAOException {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BlogUser getBlogUser(String nick) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();

		@SuppressWarnings("unchecked")
		List<BlogUser> users = (List<BlogUser>) em
				.createQuery("select b from BlogUser as b where b.nick=:nick")
				.setParameter("nick", nick).getResultList();
		if (users.size() == 0) {
			return null;
		}
		return users.get(0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addBlogUser(BlogUser u) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		em.persist(u);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<BlogUser> getAllUsers() throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();

		@SuppressWarnings("unchecked")
		List<BlogUser> users = (List<BlogUser>) em.createQuery("from BlogUser")
				.getResultList();
		return users;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<BlogEntry> getBlogEntriesForNick(String nick)
			throws DAOException {

		EntityManager em = JPAEMProvider.getEntityManager();
		BlogUser user = getBlogUser(nick);
		if (user == null) {
			return new ArrayList<BlogEntry>();
		}

		@SuppressWarnings("unchecked")
		List<BlogEntry> entries = (List<BlogEntry>) em
				.createQuery(
						"select b from BlogEntry as b where b.creator=:user")
				.setParameter("user", user).getResultList();
		return entries;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addUpdateBlogEntry(BlogEntry e, boolean update) {
		EntityManager em = JPAEMProvider.getEntityManager();
		if (update) {
			em.merge(e);
		} else {
			em.persist(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addComment(BlogComment comment) {
		EntityManager em = JPAEMProvider.getEntityManager();
		em.persist(comment);
	}
}