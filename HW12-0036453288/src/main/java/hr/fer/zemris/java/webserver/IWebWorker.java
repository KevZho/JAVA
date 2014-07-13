package hr.fer.zemris.java.webserver;

/**
 * Sučelje koje moraju implementirati svi web workeri.
 * @author Igor Smolkovič
 *
 */
public interface IWebWorker {

	/**
	 * Metoda koja se poziva kod izvršavanja web workera.
	 * @param context request u koji zapisuje worker.
	 */
	public void processRequest(RequestContext context);
}
