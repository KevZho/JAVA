package hr.fer.zemris.java.hw13;

import hr.fer.zemris.java.hw13.ServletUtil.Rezultati;

import java.io.IOException;
import java.util.ArrayList;
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

		/**
		 * Učitaj datoteke i pripremi listu rezultata.
		 */
		String fileRez = req.getServletContext().getRealPath(
				"/WEB-INF/glasanje-rezultati.txt");
		String fileDef = req.getServletContext().getRealPath(
				"/WEB-INF/glasanje-definicija.txt");

		List<String> definicije = ServletUtil.readLines(fileDef);
		List<String> rezultati = ServletUtil.readLines(fileRez);
		List<Rezultati> rez = ServletUtil.dohvatiRezultate(definicije,
				rezultati);
		req.setAttribute("rezultati", rez);

		/**
		 * Potraži najveći broj glasova.
		 */
		int maxVotes = rez.get(0).getVotes();
		for (int i = 0; i < rez.size(); i++) {
			if (rez.get(i).getVotes() > maxVotes) {
				maxVotes = rez.get(i).getVotes();
			}
		}
		/**
		 * Generiraj listu najboljih bendova.
		 */
		List<Rezultati> najbolji = new ArrayList<>();
		for (Rezultati r : rez) {
			if (r.getVotes() == maxVotes) {
				najbolji.add(r);
			}
		}

		req.setAttribute("najbolji", najbolji);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
}
