package hr.fer.zemris.java.hw13;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet koji omogućava pripremu podataka za stranicu koja prikazuje popis
 * bendova za koje je moguće glasati.
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
		String fileName = req.getServletContext().getRealPath(
				"/WEB-INF/glasanje-definicija.txt");

		List<Glas> bendovi = new ArrayList<>();
		try {
			List<String> lines = ServletUtil.readLines(fileName);
			for (String line : lines) {
				String[] parts = line.split("\t");
				String id = parts[0].trim();
				String name = parts[1].trim();
				bendovi.add(new Glas(
						String.format("glasanje-glasaj?id=%s", id), name));
			}
		} catch (Exception e) {
		}
		req.setAttribute("bendovi", bendovi);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(
				req, resp);
	}

	/**
	 * Statička klasa koja implementira strukturu koja pamti ime benda i link za
	 * glasanje.
	 *
	 * @author Igor Smolkovič
	 *
	 */
	public static class Glas {
		String link;
		String name;

		/**
		 * Konstruktor.
		 *
		 * @param link
		 *            link za glasanje.
		 * @param name
		 *            ime benda.
		 */
		public Glas(String link, String name) {
			super();
			this.link = link;
			this.name = name;
		}

		/**
		 * Metoda za dohvaćanja linka.
		 *
		 * @return link.
		 */
		public String getLink() {
			return link;
		}

		/**
		 * Metoda za dohvaćanje imena benda.
		 *
		 * @return ime benda.
		 */
		public String getName() {
			return name;
		}
	}
}
