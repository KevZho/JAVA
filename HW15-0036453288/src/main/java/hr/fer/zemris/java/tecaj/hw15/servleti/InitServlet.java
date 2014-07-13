package hr.fer.zemris.java.tecaj.hw15.servleti;

import hr.fer.zemris.java.tecaj.hw15.dao.DAOProvider;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet koji inicijalizira tablice ako vec ne postoje.
 * @author Igor Smolkovič
 *
 */
@WebServlet("/init")
public class InitServlet extends HttpServlet {

	/**
	 * UID
	 */
	private static final long serialVersionUID = -5063710691291560015L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		if (!DAOProvider.getDao().checkIfPollExist("Glasanje za omiljeni bend:")) {
			long id = DAOProvider.getDao().addPoll(
					"Glasanje za omiljeni bend:",
					"Od sljedećih bendova koji vam je najdraži? Kliknite na link kako biste glasali!"
			);
			if (id  != -1) {
				DAOProvider.getDao().addPollOption("The Beatles", 
						"http://www.geocities.com/~goldenoldies/TwistAndShout-Beatles.mid", Long.valueOf(id), 150);
				DAOProvider.getDao().addPollOption("The Platters",
						"http://www.geocities.com/~goldenoldies/SmokeGetsInYourEyes-Platters-ver2.mid", Long.valueOf(id), 60);
				DAOProvider.getDao().addPollOption("The Beach Boys",
						"http://www.geocities.com/~goldenoldies/SurfinUSA-BeachBoys.mid", Long.valueOf(id), 150);
				DAOProvider.getDao().addPollOption(
						"The Four Seasons", "http://www.geocities.com/~goldenoldies/BigGirlsDontCry-FourSeasons.mid", Long.valueOf(id), 22);
				DAOProvider.getDao().addPollOption("The Marcels", "http://www.geocities.com/~goldenoldies/Bluemoon-Marcels.mid", Long.valueOf(id), 33);

				DAOProvider.getDao().addPollOption("The Everly Brothers",
						"http://www.geocities.com/~goldenoldies/All.I.HaveToDoIsDream-EverlyBrothers.mid", Long.valueOf(id), 25);
				DAOProvider.getDao().addPollOption("The Mamas And The Papas",
						"http://www.geocities.com/~goldenoldies/CaliforniaDreaming-Mamas-Papas.mid", Long.valueOf(id), 20);
			}
		}
		
		if (!DAOProvider.getDao().checkIfPollExist("Glasanje za omiljeni Ferrari.")) {
			long id2 = DAOProvider.getDao().addPoll(
					"Glasanje za omiljeni Ferrari.",
					"Od sljedećih Ferrarija vam je najdraži? Kliknite na link kako biste glasali!"
			);
			if (id2 != -1) {
				DAOProvider.getDao().addPollOption("F430",
						"http://en.wikipedia.org/wiki/Ferrari_F430", Long.valueOf(id2), 10);
				DAOProvider.getDao().addPollOption("F355",
						"http://en.wikipedia.org/wiki/Ferrari_F355", Long.valueOf(id2), 18);
				DAOProvider.getDao().addPollOption("612 Scaglietti",
						"http://en.wikipedia.org/wiki/Ferrari_612_Scaglietti", Long.valueOf(id2), 20);
				DAOProvider.getDao().addPollOption("LaFerrari",
						"http://en.wikipedia.org/wiki/LaFerrari", Long.valueOf(id2), 30);
				DAOProvider.getDao().addPollOption("F40",
						"http://en.wikipedia.org/wiki/Ferrari_F40", Long.valueOf(id2), 32);
				DAOProvider.getDao().addPollOption("250 GTO", 
						"http://en.wikipedia.org/wiki/Ferrari_250_GTO", Long.valueOf(id2), 20);
			}
		}
		/**
		 * Preusmjeri na index.
		 */
		resp.sendRedirect(req.getContextPath() + "/index.html");
	}
}
