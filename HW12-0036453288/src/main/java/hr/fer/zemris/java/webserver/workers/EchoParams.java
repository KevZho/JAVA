package hr.fer.zemris.java.webserver.workers;

import java.util.Set;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Razred koji sadrži implementaciju workera koji vraća HTML tablicu
 * dobivenih parametara.
 * @author Igor Smolkovič
 *
 */
public class EchoParams implements IWebWorker {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void processRequest(RequestContext context) {
		context.setMimeType("text/html");
		context.write("<html><body>");
		context.write("<table border=\"1\" align=\"center\">");

		Set<String> params = context.getParameterNames();
		context.write("<tr><td>Ime parametra</td><td>Vrijednost parametra</td></tr>");
		for (String name : params) {
			String value = context.getParameter(name);
			context.write("<tr>");
			context.write(String.format("<td><b>%s</b></td>", name));
			context.write(String.format("<td><b>%s</b></td>", value));
			context.write("</tr>");
		}
		context.write("</table>");
		context.write("</body></html>");
	}
}
