package hr.fer.zemris.java.tecaj.hw15.servleti;

import hr.fer.zemris.java.tecaj.hw15.dao.DAOProvider;
import hr.fer.zemris.java.tecaj.hw15.model.PollOptionsData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet koji priprema rezultate glasanja za prikaz korisniku.
 * 
 * @author Igor Smolkovič
 * 
 */
@SuppressWarnings("serial")
@WebServlet("/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		List<PollOptionsData> results = null;
		try {
			Long pollId = (Long) req.getSession().getAttribute("pollID");
			results = DAOProvider.getDao().getPollOptions(pollId);
		} catch (Exception ex) {
			results = new ArrayList<>();
		}

		Collections.sort(results, new Comparator<PollOptionsData>() {

			@Override
			public int compare(PollOptionsData o1, PollOptionsData o2) {
				return -1
						* Integer.compare(o1.getVotesCount(),
								o2.getVotesCount());
			}
		});

		List<PollOptionsData> best = new ArrayList<>();
		if (results.size() != 0) {
			int firstCount = results.get(0).getVotesCount();
			for (PollOptionsData d : results) {
				if (d.getVotesCount() == firstCount) {
					best.add(d);
				}
			}
		}

		req.setAttribute("rezultati", results);
		req.setAttribute("najbolji", best);

		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req,
				resp);
	}
}
