package hr.fer.zemris.java.hw11.notepad.local;

/**
 * Sučelje koje mora implementirati svaki subjekt koji sadrži prijevode.
 * @author Igor Smolkovič
 *
 */
public interface ILocalizationProvider {

	/**
	 * Metoda koja omogućava dohvaćanje prijevoda za neki ključ.
	 * @param key ključ za koji se traži prijevod.
	 * @return prijevod na traženom ključu.
	 */
	public String getString(String key);

	/**
	 * Metoda koja dodaje novog listenera.
	 * @param l listner koji se dodaje.
	 */
	public void addLocalizationListener(ILocalizationListener l);

	/**
	 * Metoda koja briše listenera.
	 * @param l listener koji se briše.
	 */
	public void removeLocalizationListener(ILocalizationListener l);
}
