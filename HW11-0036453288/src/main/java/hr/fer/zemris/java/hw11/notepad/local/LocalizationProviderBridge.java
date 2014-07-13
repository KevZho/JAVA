package hr.fer.zemris.java.hw11.notepad.local;

/**
 * Razred koji implementira most između prozora i pravog lokalizacijskog
 * providera.
 * @author Igor Smolkovič
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	/**
	 * Zastavica koja označava da je prozor promatrač od susbjekta.
	 */
	private boolean connected = false;

	/**
	 * Subjekt.
	 */
	private ILocalizationProvider provider;
	
	/**
	 * Lista promatrača.
	 */
	private ILocalizationListener listener;

	/**
	 * Konstruktor.
	 * @param provider referenca na subjekt.
	 */
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		super();
		this.provider = provider;
	}

	/**
	 * Metoda koja spaja prozor kao promatrač subjekta.
	 */
	public void connect() {
		if (connected) { return; }
		listener = (new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				fire();
			}
		});
		provider.addLocalizationListener(listener);
		connected = true;
	}

	/**
	 * Metoda koja odbspaja prozor od subjekta.
	 */
	public void disconnect() {
		if (!connected) { return; }
		provider.removeLocalizationListener(listener);
		connected = false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getString(String key) {
		return provider.getString(key);
	}

}
