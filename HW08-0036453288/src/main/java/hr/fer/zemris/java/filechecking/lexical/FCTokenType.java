package hr.fer.zemris.java.filechecking.lexical;

/**
 * Pobrojani tip koji definira moguće tipove tokena koji se koriste u
 * leksičkoj analizi.
 *
 * @author Igor Smolkovič
 *
 */
public enum FCTokenType {
	/** Ključna riječ u programu **/
	KEYWORD,
	/** Označava da završava rad programa. **/
	IDN,
	/** Otvorena zagrada. **/
	OPEN_BRACKET,
	/** Zatvorena zagrada. **/
	CLOSED_BRACKET,
	/** Invertira rezultat testa znak !. **/
	INVERT,
	/** Dolar znak za supstituciju $. **/
	SUPSTITUTION,
	/** Dvotočka. **/
	COLON,
	/** Kosa crta. **/
	SLASH,
	/** Datoteka. **/
	FILE,
	/** Direktorij. **/
	FOLDER,
	/** Kraj programa. **/
	EOF,
	/** String. **/
	STRING
}
