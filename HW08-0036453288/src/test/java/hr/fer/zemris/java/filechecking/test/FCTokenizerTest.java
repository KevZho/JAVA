package hr.fer.zemris.java.filechecking.test;

import hr.fer.zemris.java.filechecking.lexical.FCLexicalException;
import hr.fer.zemris.java.filechecking.lexical.FCString;
import hr.fer.zemris.java.filechecking.lexical.FCStringType;
import hr.fer.zemris.java.filechecking.lexical.FCToken;
import hr.fer.zemris.java.filechecking.lexical.FCTokenType;
import hr.fer.zemris.java.filechecking.lexical.FCTokenizer;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class FCTokenizerTest {

	private List<FCToken> getTokens(String program) {
		FCTokenizer tokenizer = new FCTokenizer(program);
		List<FCToken> tokens = new ArrayList<>();
		
		while (true) {
			FCToken token = tokenizer.getCurrentToken();
			tokens.add(token);
			if (token.getTokenType() == FCTokenType.EOF) {
				break;
			}
			tokenizer.nextToken();
		};
		return tokens;
	}

	@Test
	public void defTest() {
		String program = "def var \"putanja do datoteke\"";
		List<FCToken> list = getTokens(program);
		Assert.assertEquals("Nisu generirana cetiri tokena.", 4, list.size());
		Assert.assertEquals("Prvi token nije DEFINE", FCTokenType.KEYWORD, list.get(0).getTokenType());
		Assert.assertEquals("Drugi token nije IDN", FCTokenType.IDN, list.get(1).getTokenType());
		Assert.assertEquals("Treci token nije STRING", FCTokenType.STRING, list.get(2).getTokenType());
		Assert.assertEquals("Zadnji token nije EOF", FCTokenType.EOF, list.get(3).getTokenType());
	}
	
	@Test
	public void ignoreCasesString() {
		String program = "exists f i\"datoteka.txt\"";
		List<FCToken> list = getTokens(program);
		Assert.assertEquals("Nisu generirana cetiri tokena.", 4, list.size());
		Assert.assertEquals("Prvi token nije CHECK", FCTokenType.KEYWORD, list.get(0).getTokenType());
		Assert.assertEquals("Drugi token nije IDN", FCTokenType.IDN, list.get(1).getTokenType());
		Assert.assertEquals("Treci token nije STRING", FCTokenType.STRING, list.get(2).getTokenType());
		FCString value = (FCString)list.get(2).getValue();
		Assert.assertEquals("Vrijednost stringa nije datoteka.txt", "datoteka.txt", value.getValue());
		Assert.assertEquals("Zadnji token nije EOF", FCTokenType.EOF, list.get(3).getTokenType());
	}
	
	@Test(expected=FCLexicalException.class)
	public void failTest() {
		String program = "exists f i\"datoteka.txt\" 1";
		getTokens(program);
	}
	
	@Test
	public void Test() {
		String program = "format izip";
		List<FCToken> list = getTokens(program);
		Assert.assertEquals("Nisu generirana tri tokena.", 3, list.size());
		Assert.assertEquals("Prvi token nije CHECK", FCTokenType.KEYWORD, list.get(0).getTokenType());
		Assert.assertEquals("Drugi token nije IDN", FCTokenType.IDN, list.get(1).getTokenType());
		Assert.assertEquals("Zadnji token nije EOF", FCTokenType.EOF, list.get(2).getTokenType());
	}
	
	@Test
	public void commentTest() {
		String program = "# comment \n#comment";
		List<FCToken> list = getTokens(program);
		Assert.assertEquals("Nije generiran jedan token.", 1, list.size());
		Assert.assertEquals("Jedini token nije EOF", FCTokenType.EOF, list.get(0).getTokenType());
	}
	
	@Test(expected=FCLexicalException.class)
	public void TwoEOFTest() {
		String program = "";
		FCTokenizer tokenizer = new FCTokenizer(program);
		tokenizer.nextToken();
		tokenizer.nextToken();
	}
	
	@Test(expected=FCLexicalException.class)
	public void invalidStringTest2() {
		String program = "fail @\"\\string";
		getTokens(program);
	}
	
	@Test
	public void defTest2() {
		String program = "def ivar \"super\"";
		List<FCToken> list = getTokens(program);
		Assert.assertEquals("Nisu generirana cetiri tokena.", 4, list.size());
		Assert.assertEquals("Prvi token nije DEFINE", FCTokenType.KEYWORD, list.get(0).getTokenType());
		Assert.assertEquals("Drugi token nije IDN", FCTokenType.IDN, list.get(1).getTokenType());
		Assert.assertEquals("Treci token nije STRING", FCTokenType.STRING, list.get(2).getTokenType());
		Assert.assertEquals("Zadnji token nije EOF", FCTokenType.EOF, list.get(3).getTokenType());
	}
	
	@Test
	public void errorMsgTest() {
		String program = "fail @\"Poruka pogreške\" { fail @\"Poruka pogreške2\" }";
		List<FCToken> list = getTokens(program);
		Assert.assertEquals("Prvi token nije KEYWORD", FCTokenType.KEYWORD, list.get(0).getTokenType());
		Assert.assertEquals("Drugi token nije STRING", FCTokenType.STRING, list.get(1).getTokenType());
		Assert.assertEquals("Treci token nije {", FCTokenType.OPEN_BRACKET, list.get(2).getTokenType());
	}
	
	@Test(expected=FCLexicalException.class)
	public void illegalStringTest() {
		String program = "fail @\"Poruka pogreške";
		getTokens(program);
	}
	
	@Test(expected=FCLexicalException.class)
	public void illegalString_BRACKETS_Test() {
		String program = "fail @\"Poruka pogreške${\"";
		getTokens(program);
	}
	
	@Test(expected=FCLexicalException.class)
	public void illegalString_DOLAR_Test() {
		String program = "fail @\"Poruka pogreške$a\"";
		getTokens(program);
	}
	
	@Test
	public void stringSubsTest() {
		String program = "fail @\"Poruka pogreške${a}\"";
		getTokens(program);
	}
	
	@Test
	public void FCStrintToStringTest() {
		FCString fcString = new FCString(FCStringType.NORMAL, "test");
		Assert.assertEquals("Vrijednosti nije String(test)","test", fcString.toString());
	}
}
