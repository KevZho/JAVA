package test;

import hr.fer.zemris.java.filechecking.lexical.FCTokenizer;
import hr.fer.zemris.java.filechecking.syntax.FCParser;

public class TestParser {

	public static void main(String[] args) {
		String program = "filename i\"${jmbag}-dz1.zip\"";
		program += "format zip { exists f \"build.xml\" }";
		
		FCTokenizer tokenizer = new FCTokenizer(program);
		new FCParser(tokenizer);
	}
}
