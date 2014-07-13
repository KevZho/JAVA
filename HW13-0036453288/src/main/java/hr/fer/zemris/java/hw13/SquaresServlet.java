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
 * Servlet koji omogućava generiranje kvadrata u intervalu od [a, b]. Duljina
 * intervala je uvjek 20. Broj a je uvjek manji od b. Ako je b preveliki
 * postavlja se na a + 20.
 *
 * @author Igor Smolkovič
 *
 */
@SuppressWarnings("serial")
@WebServlet("/squares")
public class SquaresServlet extends HttpServlet {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Integer a = null;
		Integer b = null;

		try {
			a = Integer.valueOf(req.getParameter("a"));
		} catch (Exception e) {
			a = 0;
		}
		try {
			b = Integer.valueOf(req.getParameter("b"));
		} catch (Exception e) {
			b = 20;
		}
		if (a > b) {
			int t = b;
			b = a;
			a = t;
		}
		if (b > a + 20) {
			b = a + 20;
		}

		List<Par> lista = new ArrayList<>();

		for (int i = a; i < b; i++) {
			lista.add(new Par(i, i * i));
		}
		req.setAttribute("parovi", lista);
		req.getRequestDispatcher("/WEB-INF/pages/squares.jsp").forward(req, resp);
	}

	/**
	 * Razred koji omogućava pamćenje broja i njegovog kvadrata.
	 *
	 * @author Igor Smolkovič
	 *
	 */
	public static class Par {
		int broj;
		int vrijednost;

		/**
		 * Metoda za dohvaćanje broja.
		 *
		 * @return broj.
		 */
		public int getBroj() {
			return broj;
		}

		/**
		 * Metoda za dohvaćanje potencije broja.
		 *
		 * @return potencija broja.
		 */
		public int getVrijednost() {
			return vrijednost;
		}

		/**
		 * Konstruktor.
		 *
		 * @param broj
		 *            broj koji se kvadrira..
		 * @param vrijednost
		 *            kvadrat broja.
		 */
		public Par(int broj, int vrijednost) {
			super();
			this.broj = broj;
			this.vrijednost = vrijednost;
		}
	}

}
