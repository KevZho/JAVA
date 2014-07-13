package hr.fer.zemris.java.filechecking.test;

import hr.fer.zemris.java.filechecking.FCProgramChecker;
import hr.fer.zemris.java.filechecking.lexical.FCTokenizer;
import hr.fer.zemris.java.filechecking.syntax.FCParser;
import hr.fer.zemris.java.filechecking.syntax.FCSyntaxException;

import org.junit.Assert;
import org.junit.Test;

public class ParserTest {
	
	@Test
	public void parseSimple() {
		String program = InputPrograms.getSimpleProgram();
		FCProgramChecker checker = new FCProgramChecker(program);
		Assert.assertFalse(checker.hasErrors());
	}
	
	@Test
	public void parseComplex() {
		String program = InputPrograms.getComplexProgram();
		FCProgramChecker checker = new FCProgramChecker(program);
		Assert.assertFalse(checker.hasErrors());
	}
	
	@Test
	public void parseProgramWithErrorsSimple() {
		String program = "filename ime_datoteke";
		FCProgramChecker checker = new FCProgramChecker(program);
		Assert.assertTrue(checker.hasErrors());
	}
	
	@Test
	public void parseProgramWithErrors() {
		String program = "filename i\"datoteka.txt\" format rar { fail @\"poruka\" }";
		FCProgramChecker checker = new FCProgramChecker(program);
		Assert.assertFalse(checker.errors().isEmpty());
	}
	
	@Test
	public void parseProgram() {
		String program = InputPrograms.getProgram();
		FCProgramChecker checker = new FCProgramChecker(program);
		Assert.assertFalse(checker.hasErrors());
	}
	
	@Test(expected=FCSyntaxException.class)
	public void emptyStackTest() {
		String program = "}";
		FCTokenizer tokenizer = new FCTokenizer(program);
		new FCParser(tokenizer);
	}
	
	@Test(expected=FCSyntaxException.class)
	public void illegalKeywordTest() {
		String program = "test";
		FCTokenizer tokenizer = new FCTokenizer(program);
		new FCParser(tokenizer);
	}
}
