package hr.fer.zemris.java.custom.scripting.exec.functions;

import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Stack;

/**
 * Funkcija sinus.
 * @author Igor Smolkovič
 *
 */
public class FunctionSin implements IFunction {

	/**
	 * Skida vrijednost sa stoga i vraća na stog sinus od te vrijednosti.
	 * Pretpostavlja da je vrijednost u stupnjevima te ju pretvara u
	 * radijane.
	 */
	@Override
	public void execute(Stack<Object> stack, RequestContext rc) {
		Object operand = stack.pop();
		Double value = Double.valueOf(operand.toString());
		/**
		 * Dobivena je vrijednost u stupnjevima.
		 */
		Double result = Math.sin(Math.toRadians(value));
		stack.push(result);
	}
}
