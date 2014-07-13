package hr.fer.zemris.java.custom.scripting.exec.functions;

import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Stack;

/**
 * Funkcija koja omogućava postavljanje privremenih parametara.
 * @author Igor Smolkovič
 *
 */
public class FunctionTemporaryParamSet implements IFunction {

	/**
	 * Sa stoga skida ime i vrijednost, te dodaje novi privremeni
	 * parametar.
	 */
	@Override
	public void execute(Stack<Object> stack, RequestContext rc) {
		Object name = stack.pop();
		Object value = stack.pop();
		rc.setTemporaryParameter(name.toString(), value.toString());
	}
}
