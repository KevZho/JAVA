package hr.fer.zemris.java.hw11.notepad.local;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Singleton razred koji vraća prijevode pojedinih komponenti na trenutnom jeziku.
 * @author Igor Smolkovič
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

	/**
	 * Singleton.
	 */
	private static final LocalizationProvider INSTANCE = new LocalizationProvider();

	/**
	 * Trenutno postavljeni jezik.
	 */
	private String language;

	/**
	 * Objekt koji sadrži prijevode za trenutno odabrani jezik.
	 */
	private ResourceBundle bundle;

	/**
	 * Konstruktor koji postavlja početni jezik engleski.
	 */
	public LocalizationProvider() {
		language = "en";
		setLanguage(language);
	}

	/**
	 * Statička metoda za dohvat instance.
	 * @return instanca razreda <code>LocalizationProvider</code>
	 */
	public static LocalizationProvider getInstance() {
		return INSTANCE;
	}

	/**
	 * Metoda koja postavlja traženi jezik.
	 * @param language oznaka za jezik koji se želi postaviti.
	 */
	public void setLanguage(String language) {
		try {
			Locale locale = Locale.forLanguageTag(language);
			bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.notepad.prijevodi", locale);
			fire();
		} catch (Exception ex) {
		}
		this.language = language;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getString(String key) {
		try {
			byte[] data = bundle.getString(key).getBytes(StandardCharsets.ISO_8859_1);
			return new String(data, StandardCharsets.UTF_8);
		} catch (Exception ex) {
			return "?" + key;
		}
	}
}
