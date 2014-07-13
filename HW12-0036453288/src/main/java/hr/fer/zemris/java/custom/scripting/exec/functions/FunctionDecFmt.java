package hr.fer.zemris.java.custom.scripting.exec.functions;

import hr.fer.zemris.java.webserver.RequestContext;

import java.text.DecimalFormat;
import java.util.Stack;

/**
 * Funkcija koja omogućava formatiranje ispisa. 
 * @author Igor Smolkovič
 *
 */
public class FunctionDecFmt implements IFunction {

	/**
	 * {@inheritDoc}
	 * Skida sa stoga format i value te na stog vraća formatirani value.
	 */
	@Override
	public void execute(Stack<Object> stack, RequestContext rc) {
		Object format = stack.pop();
		Object value = stack.pop();
		DecimalFormat df = new DecimalFormat(format.toString());
		Object result = df.format(value);
		stack.push(result);
	}
}
