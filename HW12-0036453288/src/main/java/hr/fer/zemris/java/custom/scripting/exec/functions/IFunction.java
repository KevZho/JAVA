package hr.fer.zemris.java.custom.scripting.exec.functions;

import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Stack;

/**
 * Sučelje koje predstavlja osnovu za izvršavanje funkcija.
 * @author Igor Smolkovič
 *
 */
public interface IFunction {

	/**
	 * Metoda koja pokreće izvršavanje neke funkcije.
	 * @param stack referenca na privjeremeni stog.
	 * @param rc referenca na RequestContext.
	 */
	public void execute(Stack<Object> stack, RequestContext rc);
}
