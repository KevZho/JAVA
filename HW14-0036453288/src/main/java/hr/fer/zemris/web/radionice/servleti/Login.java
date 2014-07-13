package hr.fer.zemris.web.radionice.servleti;

import hr.fer.zemris.web.radionice.User;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet kojo omogućava logiranje korisnika.
 * @author Igor Smolkovič
 *
 */
@WebServlet("/login")
public class Login extends HttpServlet {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 1834775817916044044L;

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
	 * Metoda koja obrađuje dobiveni zahtjev.
	 * @param req request.
	 * @param resp response.
	 * @throws ServletException  ako servlet baci grešku.
	 * @throws IOException ako je došlo do greške kod čitanja ulaza / izlaza.
	 */
	private void obradi(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("greska", false);
		if (req.getParameter("username") != null) {
			String username = (String) req.getParameter("username");
			String password = (String) req.getParameter("password");
			User korisnik = provjeri(username, password);
			if (korisnik != null) {
				req.getSession().setAttribute("current.user", korisnik);
				resp.sendRedirect(req.getServletContext().getContextPath() + "/listaj");
				return;
			} else {
				req.setAttribute("greska", true);
				req.getRequestDispatcher("/WEB-INF/pages/Login.jsp").forward(req, resp);
				return;
			}
		} else {
			req.getRequestDispatcher("/WEB-INF/pages/Login.jsp").forward(req, resp);
		}
	}

	/**
	 * Metoda koja provjerava unesene podatke za logiranje.
	 * @param username korisničko ime
	 * @param password lozinka.
	 * @return novog korisnika ako je sve uredu, inače null.
	 */
	private User provjeri(String username, String password) {
		if (username.compareTo("aante") == 0 && password.compareTo("tajna") == 0) {
			return new User("aante", "tajna", "Ante", "Anić");
		}
		return null;
	}
}
