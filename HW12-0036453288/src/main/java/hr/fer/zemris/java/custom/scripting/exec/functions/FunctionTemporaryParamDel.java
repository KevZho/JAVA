package hr.fer.zemris.java.custom.scripting.exec.functions;

import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Stack;

/**
 * Funkcija koja omogućava brisanje privremenih parametara.
 * @author Igor Smolkovič
 *
 */
public class FunctionTemporaryParamDel implements IFunction {

	/**
	 * Sa stoga skida ime parametra te briše taj privremeni parametar.
	 */
	@Override
	public void execute(Stack<Object> stack, RequestContext rc) {
		Object name = stack.pop();
		rc.removeTemporaryParameter(name.toString());
	}

}
