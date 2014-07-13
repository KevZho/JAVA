package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Razred DocumentNode nasljeđuje razred Node te predstavlja
 * razred čija instanca je korijen stabla.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class DocumentNode extends Node {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitDocumentNode(this);
	}
}
