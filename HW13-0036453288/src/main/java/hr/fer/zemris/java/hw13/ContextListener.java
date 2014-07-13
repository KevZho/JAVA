package hr.fer.zemris.java.hw13;

import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Razred koji implementira ServletContextLister. Omogućava postavljanje
 * atributa koji pamti vrijeme kada je aplikacija pokrenuta.
 *
 * @author Igor Smolkovič
 *
 */
public final class ContextListener implements ServletContextListener {

	/**
	 * Referenca na ServletContext.
	 */
	private ServletContext context = null;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		context = sce.getServletContext();
		try {
			context.setAttribute("uptime", new Date());
		} catch (Exception ex) {
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		try {
			context.removeAttribute("uptime");
		} catch (Exception ex) {
		}
	}
}
