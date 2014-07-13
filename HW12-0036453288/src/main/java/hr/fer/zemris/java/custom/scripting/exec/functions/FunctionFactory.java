package hr.fer.zemris.java.custom.scripting.exec.functions;

import hr.fer.zemris.java.custom.scripting.tokens.TokenFunction;

/**
 * Razred koji sadrži metodu tvornicu za instanciranje konkretnih funkcija.
 * @author Igor Smolkovič
 *
 */
public class FunctionFactory {

	/**
	 * Metoda koja instancira konkretne funkcije.
	 * @param token token na temelju koje se instancira funkcija.
	 * @return instanca stvorene funkcije.
	 */
	public static IFunction create(TokenFunction token) {
		if (token.getName().compareTo("sin") == 0) {
			return new FunctionSin();
		} 
		else if (token.getName().compareTo("decfmt") == 0) {
			return new FunctionDecFmt();
		} else if (token.getName().compareTo("dup") == 0) {
			return new FunctionDup();
		} else if (token.getName().compareTo("swqp") == 0) {
			return new FunctionSwap();
		} else if (token.getName().compareTo("setMimeType") == 0) {
			return new FunctionSetMimeType();
		} else if (token.getName().compareTo("paramGet") == 0) {
			return new FunctionParamGet();
		} else if (token.getName().compareTo("pparamGet") == 0) {
			return new FunctionPersistentParamGet();
		} else if (token.getName().compareTo("pparamSet") == 0) {
			return new FunctionPersistentParamSet();
		} else if (token.getName().compareTo("pparamDel") == 0) {
			return new FunctionPersistentParamDel();
		} else if (token.getName().compareTo("tparamGet") == 0) {
			return new FunctionTemporaryParamGet();
		} else if (token.getName().compareTo("tparamSet") == 0) {
			return new FunctionTemporaryParamSet();
		} else if (token.getName().compareTo("tparamDel") == 0) {
			return new FunctionTemporaryParamDel();
		}
		return null;
	}
}
