package hr.fer.zemris.java.hw11.notepad.local;

import java.awt.event.ActionEvent;

/**
 * Razred koji implementira lokaliziranu akciju.
 * @author Igor Smolkovič
 *
 */
public class LocalizableAction extends AbstractLocalizableAction {

	/**
	 * UID
	 */
	private static final long serialVersionUID = -8550667374570047516L;

	public LocalizableAction(String key, ILocalizationProvider lp) {
		super(key, lp);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
	}

}
