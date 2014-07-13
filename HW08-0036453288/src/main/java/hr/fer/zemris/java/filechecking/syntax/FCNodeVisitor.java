package hr.fer.zemris.java.filechecking.syntax;

import hr.fer.zemris.java.filechecking.syntax.node.DefStatement;
import hr.fer.zemris.java.filechecking.syntax.node.ExistsNode;
import hr.fer.zemris.java.filechecking.syntax.node.FailNode;
import hr.fer.zemris.java.filechecking.syntax.node.FileNameNode;
import hr.fer.zemris.java.filechecking.syntax.node.FormatNode;
import hr.fer.zemris.java.filechecking.syntax.node.ProgramNode;
import hr.fer.zemris.java.filechecking.syntax.node.TerminateStatement;

/**
 * Apstraktini posjetitelj naredbi programskog jezika za provjeru ispravnosti datoteke.
 * @author Igor Smolkovič
 *
 */
public interface FCNodeVisitor {

	/**
	 * Obrada naredbe def.
	 * @param node def naredba.
	 */
	public void visit(DefStatement node);
	
	/**
	 * Obrada naredbe terminate.
	 * @param node terminate naredba.
	 */
	public void visit(TerminateStatement node);
	
	/**
	 * Obrada naredbe exists.
	 * @param node exists naredba.
	 */
	public void visit(ExistsNode node);
	
	/**
	 * Obrada naredbe format.
	 * @param node format naredba.
	 */
	public void visit(FormatNode node);
	
	/**
	 * Obrada naredbe fail.
	 * @param node fail naredba.
	 */
	public void visit(FailNode node);
	
	/**
	 * Obrada naredbe filename.
	 * @param node filename naredba.
	 */
	public void visit(FileNameNode node);
	
	/**
	 * Obrada čvora koji sadrži niz naredbi.
	 * @param node čvor koji sadrži niz naredbi.
	 */
	public void visit(ProgramNode node);
}
