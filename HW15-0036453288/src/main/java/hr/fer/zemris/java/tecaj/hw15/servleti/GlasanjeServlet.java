package hr.fer.zemris.java.tecaj.hw15.servleti;

import hr.fer.zemris.java.tecaj.hw15.dao.DAOProvider;
import hr.fer.zemris.java.tecaj.hw15.model.PollData;
import hr.fer.zemris.java.tecaj.hw15.model.PollOptionsData;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet koji omogućava pripremu podataka za stranicu koja prikazuje popis
 * opcija za koje je moguće glasati.
 * 
 * @author Igor Smolkovič
 * 
 */
@SuppressWarnings("serial")
@WebServlet("/glasanje")
public class GlasanjeServlet extends HttpServlet {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Long pollId = Long.valueOf((String) req.getParameter("pollID"));
		PollData poll = DAOProvider.getDao().getPoll(pollId);

		List<PollOptionsData> options = DAOProvider.getDao().getPollOptions(
				pollId);

		/**
		 * Postavi pollID u session tako da servlet koji priprema rezultate zna
		 * za koji poll gledati.
		 */
		req.getSession().setAttribute("pollID", pollId);

		/**
		 * Ovo je za stranicu koja ispisuje.
		 */
		req.setAttribute("poll", poll);
		req.setAttribute("options", options);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
}
