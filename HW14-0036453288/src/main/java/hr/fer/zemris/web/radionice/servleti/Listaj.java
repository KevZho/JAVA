package hr.fer.zemris.web.radionice.servleti;

import hr.fer.zemris.web.radionice.Radionica;
import hr.fer.zemris.web.radionice.RadioniceBaza;

import java.io.IOException;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet koji generirali listu postojećih radionica.
 * Ako čitanje baza nije uspjelo vraća praznu listu umjesto
 * da sruši aplikaciju.
 * @author Igor Smolkovič
 *
 */
@WebServlet("/listaj")
public class Listaj extends HttpServlet {

	/**
	 * UID
	 */
	private static final long serialVersionUID = -559136116220938690L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		/**
		 * Stvori prostor za radionica. Set jer imamo komparator.
		 */
		Set<Radionica> radioniceList = new TreeSet<>(new ComparatorListaj());
		try {
			RadioniceBaza baza = RadioniceBaza.ucitaj(
					req.getServletContext().getRealPath("/WEB-INF/baza")
			);

			Map<Long, Radionica> radioniceMap = baza.getRadionice();
			for (Entry<Long, Radionica> entry : radioniceMap.entrySet()) {
				radioniceList.add(entry.getValue());
			}
		} catch (Exception ex) {
			System.err.println("Došlo je do greške prilikom generiranja popisa radionica.");
		}

		/**
		 * Postavi atribut radionice za Listaj.jsp i odi na ispis.
		 */
		req.setAttribute("radionice", radioniceList);
		req.getRequestDispatcher("/WEB-INF/pages/Listaj.jsp").forward(req, resp);
	}

	/**
	 * Komparator za sortiranje radionica najprije prema datumu održavanja,
	 * nakon toga prema imenu.
	 * @author Igor Smolkovič
	 *
	 */
	private static class ComparatorListaj implements Comparator<Radionica>, Serializable {

		/**
		 * UID
		 */
		private static final long serialVersionUID = 4914911329779663280L;

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int compare(Radionica o1, Radionica o2) {
			int date = o1.getDatum().compareTo(o2.getDatum());
			if (date != 0) {
				return date;
			}
			return o1.getNaziv().compareTo(o2.getNaziv());
		}
	} 
}
