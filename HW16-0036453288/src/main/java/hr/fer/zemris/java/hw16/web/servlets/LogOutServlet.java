package hr.fer.zemris.java.hw16.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet koji omogućava odjavu korisnika.
 * 
 * @author Igor Smolkovič
 * 
 */
@WebServlet("/servleti/logout")
public class LogOutServlet extends HttpServlet {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 7173669682171469759L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.getSession().invalidate();
		resp.sendRedirect(req.getServletContext().getContextPath()
				+ "/servleti/main");
	}
}
