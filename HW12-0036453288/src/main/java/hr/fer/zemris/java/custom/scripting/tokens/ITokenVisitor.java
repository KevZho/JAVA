package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Sučelje koje mora implementirati svaki visitor za obilazak tokena.
 * @author Igor Smolkovič
 *
 */
public interface ITokenVisitor {

	/**
	 * Posjet tokena koji pamti double vrijednost.
	 * @param token tokenConstantDouble.
	 */
	public void visitTokenConstantDouble(TokenConstantDouble token);

	/**
	 * Posjet tokena koji pamti integer vrijednost.
	 * @param token tokenConstantInteger.
	 */
	public void visitTokenConstantInteger(TokenConstantInteger token);

	/**
	 * Posjet tokena koji pamti ime funkcije.
	 * @param token tokenFunction.
	 */
	public void visitTokenFunction(TokenFunction token);
	
	/**
	 * Posjet tokena koji pamti string.
	 * @param token tokenString.
	 */
	public void visitTokenString(TokenString token);
	
	/**
	 * Posjet tokena koji pamti ime varijable.
	 * @param token tokenVariable.
	 */
	public void visitTokenVariable(TokenVariable token);
	
	/**
	 * Posjet tokena koji pamti operator.
	 * @param token tokenOperator.
	 */
	public void visitTokenOperator(TokenOperator token);
}
