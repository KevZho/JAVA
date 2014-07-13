package hr.fer.zemris.java.custom.scripting.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.custom.collections.ArrayBackedIndexedCollection;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.custom.scripting.tokens.*;


/**
 * Razred SmartScriptParser implementira parsiranje 
 * za strukturu dokumenta definiranu u trećem zadatku
 * HW02.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class SmartScriptParser {

	private DocumentNode docNode;
	private ObjectStack stack;
	
	/**
	 * Konstruktor razreda SmartScriptParser. Pokreće parsiranje dokumenta.
	 * 
	 * @param docBody Dokument koji je potrebno parsirati.
	 * @throws SmartScriptParserException ako je došlo do greške.
	 */
	public SmartScriptParser(String docBody) throws SmartScriptParserException {
		try {
			parseDocument(docBody);
		}
		catch (Exception e) {
			throw new SmartScriptParserException(e.getMessage());
		}
	}
	
	
	/**
	 * Metoda koja pokreće parsiranje dokumenta. Dalje poziva sve ostale 
	 * potrebne metode.
	 * 
	 * @param document Dokument koji se parsira.
	 */
	private void parseDocument(String document) {
		stack = new ObjectStack();
		stack.push(new DocumentNode());
	
		// tags sadrži dokument podijeljen na tagove i text
		ArrayBackedIndexedCollection tags = extractTagsText(document);
				
		for(int i = 0; i < tags.size(); i++) {
			String current = (String)tags.get(i);
			
			if(current.startsWith("{")) {
				this.parseTag(current);
			}
			else {
				this.addTextNode(current);
			}
		}
		
		// na stogu ostaje samo DocumentNode, inače je greška
		docNode = (DocumentNode) stack.pop();
	}
	
	
	/**
	 * Metoda koja dijeli dokument na tagove i text.
	 * 
	 * @param document Dokument koji se parsira.
	 * @return Polje tagova i texta.
	 */
	private ArrayBackedIndexedCollection extractTagsText(String document) {
		ArrayBackedIndexedCollection tags = new ArrayBackedIndexedCollection();
		
		//ako ispred {$ $} nije \
		Pattern tagPattern = Pattern.compile("(?<!\\\\)\\{\\$.*?\\$\\}",
							 Pattern.DOTALL );
		Matcher tagMatcher = tagPattern.matcher(document);
		int start = 0;
		
		while(tagMatcher.find()) {
			// tekst je prije taga
			if(start != tagMatcher.start()) {
				String text = document.substring(start, tagMatcher.start());
				//zamjena \{ s {
				text = text.replaceAll("\\\\\\{", "\\{");
				tags.add(text);
			}
			//dodaj tag i azuriraj start
			tags.add(tagMatcher.group());
			start = tagMatcher.end(); 
		}
		
		// ako je tekst nakon zadnjeg taga ili je samo tekst
		if(start < document.length()) {
				tags.add(document.substring(start));
		}
		
		return tags;
	}
	
	
	/**
	 * Metoda koja za tekst dobiven kao parametar stvara novi
	 * TextNode i dodaje ga kao dijete čvoru koji se nalazi na
	 * vrhu stoga.
	 * 
	 * @param text Tekst koji se dodaje.
	 * @throws SmartScriptParserException ako tekst nije ispravan.
	 */
	private void addTextNode(String text) throws SmartScriptParserException {
		// dodaj kao dijete elementu vrha stoga
		TextNode node = new TextNode(text);
		((Node)stack.peek()).addChildNode(node);	
	}
	
	
	/**
	 * Metoda koja parsira tagove. Utvrđuje da li je empty, end ili
	 * for tag te prema tome dalje poziva potrebne metode.
	 * 
	 * @param tag Tag koji se parsira.
	 * @throws SmartScriptParserException ako tag nije ispravan.
	 */
	private void parseTag(String tag) throws SmartScriptParserException {
		if(isEmptyTag(tag)) {
			this.parseEmptyTag(tag);
		}
		else if(isFORTag(tag)) {
			this.parseFORTag(tag);
		}
		else if(isENDTag(tag)) {
			stack.pop();
		}
		else {
			throw new SmartScriptParserException("Illegal type of tag!!");
		}
	}
	
	
	/**
	 * Metoda koja parsira empty tag. 
	 * 
	 * @param tag Tag koji se parsira.
	 * @throws SmartScriptParserException ako tag nije ispravan.
	 */
	private void parseEmptyTag(String tag) throws SmartScriptParserException {
		Pattern pattern = Pattern.compile("\\{\\$\\s*=(.*?)\\$\\}",
											Pattern.DOTALL);
		Matcher matcher = pattern.matcher(tag);
		
		//ako se ne može izvaditi sadržaj taga onda je tag sigurno krivi
		if(matcher.find()) {
			String value = matcher.group(1).trim();	
			ArrayBackedIndexedCollection tokens = tokenize(value);
		
			this.addEchoNode(tokens);
		}
		else {
			throw new SmartScriptParserException("Illegal empty tag!!");
		}
	}
		
	
	/**
	 * Metoda koja parsira FOR tag.
	 * 
	 * @param tag Tag koji se parsira.
	 * @throws SmartScriptParserException ako tag nije ispravan.
	 */
	private void parseFORTag(String tag) throws SmartScriptParserException {
		Pattern pattern = Pattern.compile("(?i)\\{\\$\\s*FOR(.*?)\\$\\}", 
											Pattern.DOTALL);
		Matcher matcher = pattern.matcher(tag);
		
		// ako ne može izvaditi sadržaj taga onda je on sigurno krivi
		if(matcher.find()) {
			String value = matcher.group(1).trim();	
			ArrayBackedIndexedCollection tokens = tokenize(value);
			
			this.addForNode(tokens);
		}
		else {
			throw new SmartScriptParserException("Illegal FOR tag!!");
		}
	}
	
	/**
	 * Metoda koja dodaje objekt razreda ForLoopNode na stog.
	 * 
	 * @param tokens Tokeni iz kojih se gradi objekt razreda ForLoopNode.
	 * @throws SmartScriptParserException ako neki od tokena nije dobar.
	 */
	private void addForNode(ArrayBackedIndexedCollection tokens) throws SmartScriptParserException {
		// for može biti samo for(i 1 2 3) ili for(i 1 2)
		if(tokens.size() > 4 || tokens.size() < 3) {
			throw new SmartScriptParserException("Too long FOR tag");
		}
		
		TokenVariable tVar = (TokenVariable)createNewToken((String)tokens.get(0));
		Token tStart = createNewToken((String)tokens.get(1));
		Token tEnd = createNewToken((String)tokens.get(2));
		Token tStep = null;
		
		if(tokens.size() == 4) {
			tStep = createNewToken((String)tokens.get(3));
		}
		
		ForLoopNode node = new ForLoopNode(tVar, tStart, tEnd, tStep);
		
		//dodaje ForLoopNode kao dijete čvora na vrhu stoga 
		// te dodaje taj čvor na stog
		((Node)stack.peek()).addChildNode(node);
		stack.push(node);
	}
	
	
	/**
	 * Metoda koja objekt razreda EchoNode u stablo.
	 * 
	 * @param tokens Tokeni koje sadrži EchoNode.
	 */
	private void addEchoNode(ArrayBackedIndexedCollection tokens) {
		ArrayBackedIndexedCollection unique = new ArrayBackedIndexedCollection();
		
		for(int i = 0; i < tokens.size(); i++) {
			String token = (String) tokens.get(i);
			if(unique.contains(token) == false) {
				unique.add(token);
			}
		}
		
		Token[] tArray = new Token[ unique.size() ];
		
		for(int i = 0; i < unique.size(); i++) {
			tArray[ i ] = createNewToken((String)tokens.get(i));
		}
		
		// dodaje EchoNode kao dijete od čvora na vrhu stoga
		EchoNode node = new EchoNode(tArray);
		((Node)stack.peek()).addChildNode(node);
	}
	
	
	/**
	 * Metoda koja ulazni string pretvara u odgovarajući Token.
	 * 
	 * @param input String koji se pretvara u token.
	 * @return Objekt koji je podrazred razreda Token.
	 * @throws SmartScriptParserException ako se ulazni string ne može 
	 * 			pretvoriti u ništa.
	 */
	private Token createNewToken(String input) throws SmartScriptParserException {
		if(isValidVariable(input)) {
			return new TokenVariable(input);
		}
		if(isValidFunction(input)) {
			return new TokenFunction(input.substring(1));
		}
		if(isOperator(input)) {
			return new TokenOperator(input);
		}
		
		try {
			return new TokenConstantInteger(Integer.parseInt(input));
		}
		catch (Exception e) {
		}
		
		try {
			return new TokenConstantDouble(Double.parseDouble(input));
		}
		catch(Exception e) {
		}
		
		if(isValidString(input)) {
			String text = input.substring(1, input.length()-1);
			text = escapeString(text);
			
			return new TokenString(text);
		}
		
		throw new SmartScriptParserException("CreateTokenException");
	}
	
	
	/**
	 * Metoda koja provodi escaping u stringu.
	 * 
	 * @param text String u kojem se provodi escaping.
	 * @return Escapani string.
	 */
	private String escapeString(String text) throws SmartScriptParserException {
		try {
			for(int i = 0; i < text.length(); i++) {
				if(text.indexOf("\\\\") != -1) {
					text = text.replaceFirst("\\\\\\\\", "\\\\");
				}
				else if(text.indexOf("\\\"") != -1) {
					text = text.replaceFirst("\\\\\"", "\"");
				}
				else if(text.indexOf("\\n") != -1) {
					text = text.replaceFirst("\\\\n", "\n");
				}
				else if(text.indexOf("\\t") != -1) {
					text = text.replaceFirst("\\\\t", "\t");
				}
			}
		}
		catch(Exception e) {
			throw new SmartScriptParserException("CreateTokenException");
		}
		
		return text;
	}
	
	/**
	 * Metoda koja tokenizira empty i for tagove.
	 * 
	 * @param tag Tag koji se tokenizira.
	 * @return Polje tokena.
	 */
	private ArrayBackedIndexedCollection tokenize(String tag) {
		ArrayBackedIndexedCollection tokens = new ArrayBackedIndexedCollection();
		
		//ako je u " ", ili posebno a nije razmak
		Pattern p = Pattern.compile("[\\s\n]*(\".*\"|[^\\s]+)[\\s\n]*", Pattern.DOTALL);
		Matcher m = p.matcher(tag);
		
		//razdvoji na razmaku
		while (m.find()) {
			String token = m.group().trim();
			
			Pattern pattern = Pattern.compile("(.*[^\\*\\+-/])(\\*|\\+-/)(.*)", 
												Pattern.DOTALL);
			Matcher matcher = pattern.matcher(token);
			
			// mozda se moze nešto odvojiti na operatoru, npr. {$= i*7 $}
			// ne odvaja unutar stringova
			if(matcher.find() && token.startsWith("\"") == false) {
				tokens.add(matcher.group(1).trim());
				tokens.add(matcher.group(2).trim());
				tokens.add(matcher.group(3).trim());
			}
			else {
				tokens.add(token);
			}
		}
	
		return tokens;
	}
	
	
	/**
	 * Metoda koja provjerava da li je tag empty tag. 
	 * 
	 * @param tag Tag koji se provjerava.
	 * @return true / false.
	 */
	private boolean isEmptyTag(String tag) {
		return tag.matches("(?sm)\\{\\$\\s*=.*?\\$\\}");
	}
	
	
	/**
	 * Metoda koja provjerava da li je tag FOR tag.
	 * 
	 * @param tag Tag koji se provjarava.
	 * @return true / false.
	 */
	private boolean isFORTag(String tag) {
		return tag.matches("(?smi)\\{\\$\\s*FOR.*?\\$\\}");
	}
	
	
	/**
	 * Metoda koja provjerava da li je tag END tag.
	 * 
	 * @param tag Tag koji se provjerava.
	 * @return true / false.
	 */
	private boolean isENDTag(String tag) {
		return tag.matches("(?smi)\\{\\$\\s*END\\s*\\$\\}");
	}
	
	
	/**
	 * Metoda koja provjerava da li je naziv varijable ispravan.
	 * 
	 * @param variable Varijabla koja se provjerava.
	 * @return true / false.
	 */
	private boolean isValidVariable(String variable) {
		return variable.matches("^[a-zA-Z]\\w*");
	}
	
	
	/**
	 * Metoda koja provjerava da li je korištenje funkcije ispravno.
	 * 
	 * @param variable Funkcija koja se provjerava.
	 * @return true / false
	 */
	private boolean isValidFunction(String variable) {
		return variable.matches("^@[a-zA-Z]\\w*");
	}
	
	
	/**
	 * Metoda koja provjerava da li je string ispravan.
	 * 
	 * @param input String koji se provjerava.
	 * @return true / false.
	 */
	private boolean isValidString(String input) {
		return input.matches("\\\".*\\\"");
	}
	
	
	/**
	 * Metoda koja provjerava da li je dobiveni ulazni string 
	 * operator.
	 * 
	 * @param operator String koji se provjerava.
	 * @return true / false.
	 */
	private boolean isOperator(String operator) {
		return operator.matches("^(\\*|\\+|-|/)");
	}
	
	
	/**
	 * Metoda koja vraća korijen stabla parsiranog dokumenta.
	 * 
	 * @return Korijen stabla parsiranog dokumenta.
	 */
	public DocumentNode getDocumentNode() {
		return docNode;
	}
}
