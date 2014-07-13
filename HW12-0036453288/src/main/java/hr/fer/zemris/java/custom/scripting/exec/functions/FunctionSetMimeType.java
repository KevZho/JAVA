package hr.fer.zemris.java.custom.scripting.exec.functions;

import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Stack;

/**
 * Funkcija koja omogućava promjenu mimeTypa.
 * @author Igor Smolkovič
 *
 */
public class FunctionSetMimeType implements IFunction {

	/**
	 * Skida type sa stoga i postavlja RequestContext-u novi mime Type.
	 */
	@Override
	public void execute(Stack<Object> stack, RequestContext rc) {
		Object value = stack.pop();
		rc.setMimeType(value.toString());
	}

}
