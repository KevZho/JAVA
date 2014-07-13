package hr.fer.zemris.java.hw13;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet koji omogućava prikaz smješnih priča svaki puta u sa drugom bojom
 * teksta.
 *
 * @author Igor Smolkovič
 *
 */
@SuppressWarnings("serial")
@WebServlet("/stories/funny.jsp")
public class StoriesServlet extends HttpServlet {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Random rand = new Random();
		String[] colors = new String[] {"Tan", "Violet", "Purple", "Lime", "Gray"};
		String pickedCol = colors[rand.nextInt(colors.length)];

		req.getSession().setAttribute("pickedFgCol", pickedCol);
		req.getRequestDispatcher("/WEB-INF/pages/funny.jsp").forward(req, resp);
	}
}
