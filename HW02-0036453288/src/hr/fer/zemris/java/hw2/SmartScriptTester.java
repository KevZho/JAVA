package hr.fer.zemris.java.hw2;

import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.custom.scripting.parser.*;


/**
 * Razred SmartScriptTest sadrži main metodu koja pokreće parsiranje
 * dokumenta.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class SmartScriptTester {

	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 * 
	 * @param args Argumenti komandne linije.
	 */
	public static void main(String[] args) {
		String docBody = "This is sample text.\r\n"
				+ "{$ FOR i 0 10 1 		$}\r\n"
				+ "	This is {$= i $}-th time this message is generated.\r\n"
				+ "{$END$}\r\n"
				+ "{$FOR i 0 10 2 $}\r\n"
				+ "	sin({$= i $}^2) = {$= i i * @sin \"0.000\" @decfmt $}\r\n"
				+ "{$END$}\r\n";
				
		SmartScriptParser parser = null;
		
		try {
			parser = new SmartScriptParser(docBody);
		} 
		catch(SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} 
		catch(Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		System.out.println(originalDocumentBody); // should write something like original
												 // content of docBody
	}

	/**
	 * Metoda koja iz dobivenog stabla rekonstruira dokument koji je bio
	 * parsiran.
	 * 
	 * @param node Korijen stabla iz kojeg se rekonstruira dokument.
	 * @return Dokument rekonstruiran iz stabla.
	 */
	private static String createOriginalDocumentBody(Node node) {
		String  docBody = "";
		
		if(node instanceof DocumentNode) {
			for(int i = 0; i < node.numberOfChildren(); i++) {
				docBody += createOriginalDocumentBody(node.getChild(i));
			}
		}
		else if(node instanceof ForLoopNode) {
			docBody += "{$ FOR " + node.asText() + " $}";
				
			for(int i = 0; i < node.numberOfChildren(); i++) {
				docBody += createOriginalDocumentBody(node.getChild(i));
			}
			
			docBody += "{$ END $}";
		}
		else if(node instanceof EchoNode) {
			docBody += "{$= " + node.asText() + "$}";
		}
		else if(node instanceof TextNode) {
			docBody += node.asText();
		}
		else {
			return "";
		}
		
		return docBody;
	}
}
