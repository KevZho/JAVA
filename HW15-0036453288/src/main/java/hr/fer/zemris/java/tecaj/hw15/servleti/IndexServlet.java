package hr.fer.zemris.java.tecaj.hw15.servleti;

import hr.fer.zemris.java.tecaj.hw15.dao.DAOProvider;
import hr.fer.zemris.java.tecaj.hw15.model.PollData;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet koji priprema podatke za ispisivanje o anketama za koje se može
 * glasati.
 * 
 * @author Igor Smolkovič
 * 
 */
@WebServlet("/index.html")
public class IndexServlet extends HttpServlet {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 2618878559475099536L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<PollData> ankete = DAOProvider.getDao().getAvailablePolls();
		req.setAttribute("ankete", ankete);
		req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
	}
}
