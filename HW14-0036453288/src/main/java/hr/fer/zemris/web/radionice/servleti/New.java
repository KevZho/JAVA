package hr.fer.zemris.web.radionice.servleti;

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
 * Servlet koji omogućava dodavanje novih radionica.
 * @author Igor Smolkovič
 *
 */
@WebServlet("/new")
public class New extends HttpServlet {

	/**
	 * UID
	 */
	private static final long serialVersionUID = -6603875867663513412L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Object user = req.getSession().getAttribute("current.user");
		if (!(user instanceof User)) {
			req.setAttribute("poruka", "Nemate dozvolu za provođenje ove akcije.");
			req.getRequestDispatcher("/WEB-INF/pages/Greska.jsp").forward(req, resp);
			return;
		}
		
		Radionica r = new Radionica();
		RadionicaForm f = new RadionicaForm();
		f.popuniIzRadionice(r);

		RadioniceBaza baza = RadioniceBaza.ucitaj(
				req.getServletContext().getRealPath("/WEB-INF/baza")
		);

		/*
		 * Zakvaći atribute.
		 */
		req.setAttribute("zapis", f);
		req.setAttribute("opremaMap", baza.getOprema());
		req.setAttribute("publikaMap", baza.getPublika());
		req.setAttribute("trajanjeMap", baza.getTrajanje());

		/*
		 * Pošalji na renderiranje.
		 */
		req.getRequestDispatcher("/WEB-INF/pages/Formular.jsp").forward(req, resp);
	}
}
