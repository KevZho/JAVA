package hr.fer.zemris.java.hw11.notepad.local;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * Apstraktni razred za localizirane akcije koji omogućava automatsko dohvaćanje
 * novog imena prilikom promjene jezika.
 * @author Igor Smolkovič
 *
 */
public abstract class AbstractLocalizableAction extends AbstractAction {

	/**
	 * UID
	 */
	private static final long serialVersionUID = -4521250261263375166L;

	/**
	 * Konstruktor.
	 * @param key kljuc po kojem se traži prijevodi.
	 * @param lp localizationProvider koji dohvaća prijevod za ključ.
	 */
	public AbstractLocalizableAction(final String key, final ILocalizationProvider lp) {
		String translation = lp.getString(key);
		putValue(Action.NAME, translation);

		lp.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				String translation = lp.getString(key);
				putValue(Action.NAME, translation);
			}
		});
	}

	/**
	 * Apstraktna metoda koja je različita kod metoda npr. Open, Save, ... 
	 */
	@Override
	public abstract void actionPerformed(ActionEvent e);

}
