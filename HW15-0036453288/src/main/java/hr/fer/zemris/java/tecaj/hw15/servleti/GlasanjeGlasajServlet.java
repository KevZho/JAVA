package hr.fer.zemris.java.tecaj.hw15.servleti;

import java.io.IOException;

import hr.fer.zemris.java.tecaj.hw15.dao.DAOProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet koji se koristi za bilježenje glasova korisnika.
 * 
 * @author Igor Smolkovič
 * 
 */
@SuppressWarnings("serial")
@WebServlet("/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {

	/**
	 * {@inheritDoc}
	 * 
	 * @throws IOException
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		try {
			Long pollId = (Long) req.getSession().getAttribute("pollID");

			Long id = Long.valueOf((String) req.getParameter("id"));

			/**
			 * Provedi glasanje
			 */
			Integer count = DAOProvider.getDao().getOptionCount(pollId, id);
			if (count != -1) {
				count++;
				DAOProvider.getDao().updateOptionCount(pollId, id, count);
			}
		} catch (Exception ex) {
		}

		/**
		 * Preusmjeri na servlet koji priprema podatke za ispis.
		 */
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}
}
