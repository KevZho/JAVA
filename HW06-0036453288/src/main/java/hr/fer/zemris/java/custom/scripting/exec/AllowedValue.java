package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Pobrojani tip koji definira moguće tipove konačnih operanada
 * za operacije inc/dec/mul/div. Na ove tipove se operandi postave
 * prije provođenja same operacije te konačni rezultat mora biti u
 * jednom od ovih tipova.
 *
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public enum AllowedValue {
	INTEGER,
	DOUBLE,
}
