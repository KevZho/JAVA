package hr.fer.zemris.java.custom.scripting.exec.functions;

import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Stack;

/**
 * Funkcija koja omogućava zamjenu vrijednosti.
 * @author Igor Smolkovič
 *
 */
public class FunctionSwap implements IFunction {

	/**
	 * Obavlja zamjenu zadnja dva parametra na stogu.
	 */
	@Override
	public void execute(Stack<Object> stack, RequestContext rc) {
		Object first = stack.pop();
		Object second = stack.pop();
		stack.push(first);
		stack.push(second);
	}
}
