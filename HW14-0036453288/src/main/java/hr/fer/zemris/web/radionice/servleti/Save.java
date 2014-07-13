package hr.fer.zemris.web.radionice.servleti;


import hr.fer.zemris.web.radionice.InconsistentDatabaseException;
import hr.fer.zemris.web.radionice.Radionica;
import hr.fer.zemris.web.radionice.RadionicaForm;
import hr.fer.zemris.web.radionice.RadioniceBaza;
import hr.fer.zemris.web.radionice.User;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet koji omogućava spremanje radionica u bazu podataka.
 * @author Igor Smolkovič
 *
 */
@WebServlet("/save")
public class Save extends HttpServlet {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 575073228547833068L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		try {
			obradi(req, resp);
		} catch (Exception ex) {
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		try {
			obradi(req, resp);
		} catch (Exception ex) {
		}
	}

	/**
	 * Metoda koja obrađuje dobiveni zahtjev.
	 * @param req request.
	 * @param resp response.
	 * @throws IOException ako je došlo do greške prilikom čitanja.
	 * @throws ServletException greška koju baca servlet.
	 */
	private void obradi(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		req.setCharacterEncoding("UTF-8");

		Object user = req.getSession().getAttribute("current.user");
		if (!(user instanceof User)) {
			req.setAttribute("poruka", "Nemate dozvolu za provođenje ove akcije.");
			req.getRequestDispatcher("/WEB-INF/pages/Greska.jsp").forward(req, resp);
			return;
		}
		
		String metoda = req.getParameter("metoda");
		if (!"Pohrani".equals(metoda)) {
			resp.sendRedirect(req.getServletContext().getContextPath()+ "/listaj");
			return;
		}

		RadioniceBaza baza = RadioniceBaza.ucitaj(
				req.getServletContext().getRealPath("/WEB-INF/baza")
		);

		req.setAttribute("opremaMap", baza.getOprema());
		req.setAttribute("publikaMap", baza.getPublika());
		req.setAttribute("trajanjeMap", baza.getTrajanje());

		RadionicaForm f = new RadionicaForm();
		f.popuniIzHttpRequesta(req);
		f.validiraj();

		if (f.imaPogresaka()) {
			req.setAttribute("zapis", f);
			req.getRequestDispatcher("/WEB-INF/pages/Formular.jsp").forward(req, resp);
			return;
		}

		Radionica r = new Radionica();
		f.popuniURadionicu(r, baza.getOprema(), baza.getPublika(), baza.getTrajanje());
		try {
			baza.snimi(r);
		} catch (InconsistentDatabaseException ex) {
			System.err.println("Spremanje nije uspjelo.");
		}
		resp.sendRedirect(req.getServletContext().getContextPath() + "/listaj");
	}
}
