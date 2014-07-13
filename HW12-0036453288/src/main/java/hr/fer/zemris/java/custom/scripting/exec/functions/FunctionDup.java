package hr.fer.zemris.java.custom.scripting.exec.functions;

import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Stack;

/**
 * Funkcija koja omogućava dupliciranje vrha stoga.
 * @author Igor Smolkovič
 *
 */
public class FunctionDup implements IFunction {

	/**
	 * Pokreće dupliciranje vrha stoga.
	 */
	@Override
	public void execute(Stack<Object> stack, RequestContext rc) {
		Object top = stack.peek();
		stack.push(top);
	}

}
