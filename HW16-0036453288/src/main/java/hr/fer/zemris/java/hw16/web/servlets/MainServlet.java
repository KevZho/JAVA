package hr.fer.zemris.java.hw16.web.servlets;

import hr.fer.zemris.java.hw16.dao.DAOProvider;
import hr.fer.zemris.java.hw16.model.BlogUser;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Glavni servlet.
 * 
 * @author Igor Smolkovič
 * 
 */
@WebServlet("/servleti/main")
public class MainServlet extends HttpServlet {

	/**
	 * UID
	 */
	private static final long serialVersionUID = -8547478610134552065L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		obradi(req, resp);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		obradi(req, resp);
	}

	/**
	 * Glavna metoda servleta. Priprema listu korisnika te prikazuje formu za
	 * logiranje.
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void obradi(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.getSession().setAttribute("register", null);
		if (req.getParameter("metoda") != null) {
			checkLogin(req);
		} else {
			req.getSession().setAttribute("greska", null);
		}
		/*
		 * Pripremi listu autora za prikaz na index.jsp
		 */
		List<BlogUser> users = DAOProvider.getDAO().getAllUsers();
		req.setAttribute("users", users);
		req.getRequestDispatcher("/WEB-INF/pages/Index.jsp").forward(req, resp);
	}

	/**
	 * Metoda koja provjerava unese podatke za logiranje.
	 * 
	 * @param req
	 */
	private void checkLogin(HttpServletRequest req) {
		/*
		 * Ako je korisnik kliknuo na Prijava
		 */
		if (req.getParameter("metoda").equals("Prijava")) {
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			/**
			 * Izračunaj sha1 sažetak.
			 */
			password = SHACrypto.hashValue(password);

			/*
			 * Provjeri da li postoji username
			 */
			req.getSession().setAttribute("current.user.nick", username);
			BlogUser usr = DAOProvider.getDAO().getBlogUser(username);
			if (usr != null) {
				/*
				 * Ako je password ispravan.
				 */
				if (password.equals(usr.getPasswordHash())) {
					req.getSession().setAttribute("current.user.fn",
							usr.getFirstName());
					req.getSession().setAttribute("current.user.ln",
							usr.getLastName());
					req.getSession().setAttribute("current.user.id",
							usr.getId());
				} else {
					req.getSession().setAttribute("greska", true);
				}
			} else {
				req.getSession().setAttribute("greska", true);
			}
		}
	}
}
