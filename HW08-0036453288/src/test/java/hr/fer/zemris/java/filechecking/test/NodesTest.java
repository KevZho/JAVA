package hr.fer.zemris.java.filechecking.test;

import hr.fer.zemris.java.filechecking.syntax.node.ProgramNode;

import org.junit.Test;

public class NodesTest {

	@Test(expected=IllegalArgumentException.class)
	public void programNodeAddNullReference() {
		ProgramNode node = new ProgramNode();
		node.addStatement(null);
	}
}
