package hr.fer.zemris.java.hw11.notepad.local;

/**
 * Sučelje koje mora implementirati svaki promatrač.
 * @author Igor Smolkovič
 *
 */
public interface ILocalizationListener {

	/**
	 * Metoda koja dojavljuje da je jezik promijenjen.
	 */
	void localizationChanged();
}
