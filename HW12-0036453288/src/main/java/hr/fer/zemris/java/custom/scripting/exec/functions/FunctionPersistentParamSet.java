package hr.fer.zemris.java.custom.scripting.exec.functions;

import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Stack;

/**
 * Funkcija koja omogućava postavljanje perzistentnih parametara.
 * @author Igor Smolkovič
 *
 */
public class FunctionPersistentParamSet implements IFunction {

	/**
	 * Skida sa stoga ime parametra i njegovu vrijednost te ga zapisuje.
	 */
	@Override
	public void execute(Stack<Object> stack, RequestContext rc) {
		Object name = stack.pop();
		Object value = stack.pop();
		rc.setPersistentParameter(name.toString(), value.toString());
	}

}
