package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Sučelje koje mora implementirati konkretni visitor za obilazak stabla
 * dokumenta.
 * @author Igor Smolkovič
 *
 */
public interface INodeVisitor {

	/**
	 * Visitor za obilazak TextNoda.
	 * @param node textNode.
	 */
	public void visitTextNode(TextNode node);

	/**
	 * Visitor za obilazak ForLoopNoda. Najprije obiđe forLoopNode, a nakon toga
	 * direktnu djecu.
	 * @param node forLoopNode.
	 */
	public void visitForLoopNode(ForLoopNode node);

	/**
	 * Visitor za obilazak EchoNoda.
	 * @param node echoNode.
	 */
	public void visitEchoNode(EchoNode node);

	/**
	 * Visitor za obilaza documentNoda. Pokreće obilaženje direktne djece.
	 * @param node
	 */
	public void visitDocumentNode(DocumentNode node);
}
