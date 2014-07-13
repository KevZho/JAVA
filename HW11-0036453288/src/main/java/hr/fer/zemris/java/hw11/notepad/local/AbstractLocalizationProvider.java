package hr.fer.zemris.java.hw11.notepad.local;

import java.util.ArrayList;
import java.util.List;

/**
 * Razred koji implementira apstraktni lokalizacijski provider.
 * @author Igor Smolkovič
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

	/**
	 * Lista listenera.
	 */
	private List<ILocalizationListener> listeners;

	/**
	 * Konstruktor.
	 */
	public AbstractLocalizationProvider() {
		listeners = new ArrayList<>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract String getString(String key);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		if (l == null) {
			throw new IllegalArgumentException("Null reference.");
		}
		if (!listeners.contains(l)) {
			listeners.add(l);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		if (l == null) {
			throw new IllegalArgumentException("Null reference.");
		}
		listeners.remove(l);
	}

	/**
	 * Metoda koja dojavljuje promatračima da je došlo do promjene.
	 */
	public void fire() {
		for (ILocalizationListener l : listeners) {
			l.localizationChanged();
		}
	}
}
