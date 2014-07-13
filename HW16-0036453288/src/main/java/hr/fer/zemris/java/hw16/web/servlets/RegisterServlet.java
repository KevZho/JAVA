package hr.fer.zemris.java.hw16.web.servlets;

import hr.fer.zemris.java.hw16.dao.DAOProvider;
import hr.fer.zemris.java.hw16.form.BlogUserForm;
import hr.fer.zemris.java.hw16.model.BlogUser;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet koji omogućava registraciju novih korisnika.
 * 
 * @author Igor Smolkovič
 * 
 */
@WebServlet("/servleti/register")
public class RegisterServlet extends HttpServlet {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 8150334125813782770L;

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
	 * Metoda koja obrađuje podatke. Provjerava podatke unesene na formi. Ako
	 * nešto ne štima vraća na ponovni upis podataka.
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void obradi(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * Ako si dobio odustani vrati se na main i resetiraj zastavicu
		 * register.
		 */
		String metoda = req.getParameter("metoda");
		if ("Odustani".equals(metoda)) {
			req.getSession().setAttribute("register", null);
			resp.sendRedirect(req.getServletContext().getContextPath()
					+ "/servleti/main");
			return;
		}

		/*
		 * Ako nisu podaci već bili uneseni generiraj novi formular.
		 */
		if (req.getSession().getAttribute("register") == null) {

			/*
			 * Generiraj novi formular.
			 */
			BlogUserForm fm = new BlogUserForm();
			BlogUser u = new BlogUser();
			fm.popuniIzBlogUser(u);

			/*
			 * Zakvaći atribute i pošalji na renderiranje prazni formular.
			 */
			req.getSession().setAttribute("register", true);
			req.setAttribute("zapis", fm);
			req.getRequestDispatcher("/WEB-INF/pages/Register.jsp").forward(
					req, resp);
			return;

		} else { // inače provjeri podatke koje si dobio.
			String nick = req.getParameter("nick");
			/*
			 * Tu sad ide provjera da li korisnik već postoji.
			 */
			BlogUser usr = DAOProvider.getDAO().getBlogUser(nick);
			boolean nickUsed = false;
			if (usr != null) {
				nickUsed = true;
			}
			BlogUserForm fm = new BlogUserForm();
			fm.popuniIzHttpRequesta(req);
			fm.validiraj(nickUsed);

			/*
			 * Imaš greške vrati se na upis podataka i pokaži što je krivo.
			 */
			if (fm.imaPogresaka()) {
				req.setAttribute("zapis", fm);
				req.getRequestDispatcher("/WEB-INF/pages/Register.jsp")
						.forward(req, resp);
				return;
			}

			/*
			 * Spremi u bazu i označi da je registracija gotova.
			 */
			req.getSession().setAttribute("register", null);
			BlogUser u = new BlogUser();
			fm.popuniUBlogUser(u);
			/**
			 * U bazu spremi hash.
			 */
			String hash = SHACrypto.hashValue(u.getPasswordHash());
			u.setPasswordHash(hash);

			DAOProvider.getDAO().addBlogUser(u);
			resp.sendRedirect(req.getContextPath() + "/servleti/main");
		}
	}
}
