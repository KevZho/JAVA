package hr.fer.zemris.java.custom.scripting.exec.functions;

import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Stack;

/**
 * Funkcija koja omogućava dohvaćanje perzistentnih parametara.
 * @author Igor Smolkovič
 *
 */
public class FunctionPersistentParamGet implements IFunction {

	/**
	 * Skida sa stoga defValue i name. Pogleda da li postoji paramtar imena
	 * name, ako postoji stavlja njegovu vrijednost na stog, inače na stog
	 * vraća defValue.
	 */
	@Override
	public void execute(Stack<Object> stack, RequestContext rc) {
		Object dv = stack.pop();
		Object name = stack.pop();
		String value = rc.getPersistentParameter(name.toString());
		stack.push(value == null ? dv : value);
	}
}
