package hr.fer.zemris.java.custom.scripting.exec.functions;

import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Stack;

/**
 * Funkcija koja omogućava dohvaćanje privremenih parametara.
 * @author Igor Smolkovič
 *
 */
public class FunctionTemporaryParamGet implements IFunction {

	/**
	 * Skida sa stoga defValue i name. Ako postoji parametar imena
	 * name vraća njegovu vrijednost na stog, inače na stog vraća defValue.
	 */
	@Override
	public void execute(Stack<Object> stack, RequestContext rc) {
		Object dv = stack.pop();
		Object name = stack.pop();
		String value = rc.getTemporaryParameter(name.toString());
		stack.push(value == null ? dv : value);
	}
}
