package hr.fer.zemris.java.hw11.notepad.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * Razred koji implementira lokalizacijski provider za prozor.
 * @author Igor Smolkovič
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

	/**
	 * Konstruktor.
	 * @param provider provider na koje se spaja prozor.
	 * @param frame prozor koji se spaja na provider.
	 */
	public FormLocalizationProvider(ILocalizationProvider provider, JFrame frame) {
		super(provider);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}

			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}
		});
	}
}
