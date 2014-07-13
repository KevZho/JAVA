package hr.fer.zemris.web.radionice.servleti;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Metoda koja omogućava odjavu korisnika.
 * @author Igor Smolkovič
 *
 */
@WebServlet("/logout")
public class Logout extends HttpServlet {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 4574749808999919540L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.getSession().invalidate();
		resp.sendRedirect(req.getServletContext().getContextPath() + "/listaj");
	}
}
