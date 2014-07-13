package hr.fer.zemris.java.custom.scripting.exec.functions;

import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Stack;

/**
 * Funkcija koja omogućava brisanje perzistentnih parametara.
 * @author Igor Smolkovič
 *
 */
public class FunctionPersistentParamDel implements IFunction {

	/**
	 * Skida ime sa stoga i briše perzistentni parametar tog imena.
	 */
	@Override
	public void execute(Stack<Object> stack, RequestContext rc) {
		Object name = stack.pop();
		rc.removePersistentParameter(name.toString());
	}

}
