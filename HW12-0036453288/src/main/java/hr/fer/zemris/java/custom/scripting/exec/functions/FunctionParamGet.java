package hr.fer.zemris.java.custom.scripting.exec.functions;

import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Stack;

/**
 * Funkcija koja omogućava dohvaćanje parametra.
 * @author Igor Smolkovič
 *
 */
public class FunctionParamGet implements IFunction {

	/**
	 * Skida dva parametra s stoga defValue i name. Ako ne postoji parametar naziva
	 * name vraća defValue na stog, inače vrijednost koju ima parametar name.
	 */
	@Override
	public void execute(Stack<Object> stack, RequestContext rc) {
		Object dv = stack.pop();
		Object name = stack.pop();
		String value = rc.getParameter(name.toString());
		stack.push(value == null ? dv : value);
	}

}
