package hr.fer.zemris.java.filechecking.lexical;

import java.util.HashMap;
import java.util.Map;

/**
 * Razred koji implementira tokenizator jezika za provjeru datoteka.
 * @author Igor Smolkovič
 *
 */
public class FCTokenizer {
	/**
	 * Polje znakova koje sadrži ulazni program.
	 */
	private char[] data;
	/**
	 * Kazaljka na prvi neobrađeni znak u polju <code>data</code>.
	 */
	private int curPosition;
	/**
	 * Posljednji token stvoren analizom programa.
	 */
	private FCToken currentToken;
	/**
	 * Mapa koja sadrži sve tokene koji se sastoje od samo jednog znaka.
	 */
	private static final Map<Character, FCTokenType> MAPPER;

	static {
		MAPPER = new HashMap<Character, FCTokenType>();
		MAPPER.put(Character.valueOf('{'), FCTokenType.OPEN_BRACKET);
		MAPPER.put(Character.valueOf('}'), FCTokenType.CLOSED_BRACKET);
		MAPPER.put(Character.valueOf('!'), FCTokenType.INVERT);
		MAPPER.put(Character.valueOf(':'), FCTokenType.COLON);
		MAPPER.put(Character.valueOf('/'), FCTokenType.SLASH);
		MAPPER.put(Character.valueOf('$'), FCTokenType.SUPSTITUTION);
	}
	
	/**
	 * Mapa koja sadrži ključne riječi programskog jezika.
	 */
	private static final Map<String, FCTokenType> KEYWORDS;

	static {
		KEYWORDS = new HashMap<String, FCTokenType>();
		KEYWORDS.put("def", FCTokenType.KEYWORD);
		KEYWORDS.put("terminate", FCTokenType.KEYWORD);
		KEYWORDS.put("exists", FCTokenType.KEYWORD);
		KEYWORDS.put("filename", FCTokenType.KEYWORD);
		KEYWORDS.put("format", FCTokenType.KEYWORD);
		KEYWORDS.put("fail", FCTokenType.KEYWORD);
	}

	/**
	 * Konstruktor koji prima program koji se tokenizira.
	 *
	 * @param program program koji se tokenizira.
	 */
	public FCTokenizer(String program) {
		this.data = program.toCharArray();
		this.curPosition = 0;
		extractNextToken();
	}

	/**
	 * Dohvaća trenutni token. Poziv metode ne pokreće traženje tokena već
	 * samo pročita vrijednost propertya koji čuva tu vrijednost.
	 *
	 * @return trenutni token.
	 */
	public FCToken getCurrentToken() {
		return currentToken;
	}

	/**
	 * Metoda koja dohvaća sljedeći token postavlja ga ne trenutni i
	 * vraća trenutno vrijednost.
	 *
	 * @return sljedeći token.
	 */
	public FCToken nextToken() {
		extractNextToken();
		return getCurrentToken();
	}

	/**
	 * Metoda koja dovaća sljedeći token iz izvornog programa i postavlja
	 * ga kao trenutni.
	 *
	 */
	private void extractNextToken() {
		if (currentToken != null && currentToken.getTokenType() == FCTokenType.EOF) {
			throw new FCLexicalException("No tokens available.");
		}

		// preskoci praznine ispred idućeg znaka
		skipBanks();

		// preskoci komentare
		skipComment();

		// Ako više nema znakova, generiraj token za kraj izvornog koda programa
		if (curPosition >= data.length) {
			currentToken = new FCToken(FCTokenType.EOF, null);
			return;
		}

		// Vidi je li trenutni znak neki od onih koji direktno generiraju token:
		FCTokenType mappedType = MAPPER.get(Character.valueOf(data[curPosition]));
		if (mappedType != null) {
			// Stvori takav token:
			currentToken = new FCToken(mappedType, null);
			// Postavi trenutnu poziciju na sljedeći znak:
			curPosition++;
			return;
		}

		// pokušaj detektirati string, dva oblika: i"" ili ""
		boolean detectedString = detectString();
		if(detectedString) {
			return;
		}

		//identifikatori i ključne riječi.
		if (data[curPosition] == '_' || Character.isLetter(data[curPosition])) {
			int startIndex = curPosition;
			curPosition++;
			//zanima nas duljina toga do praznine / neočekivanog znaka
			while (curPosition < data.length) {
				char c = data[curPosition];
				if (Character.isLetter(c) || Character.isDigit(c) || c == '_' || c == '.') {
					curPosition++;
					continue;
				}
				break;
			}
			int endIndex = curPosition;
			String value = new String(data, startIndex, endIndex - startIndex);
			FCTokenType keywordType = KEYWORDS.get(value);
			if (keywordType != null) {
				currentToken = new FCToken(keywordType, value);
				return;
			}
			//inače je identifikator
			currentToken = new FCToken(FCTokenType.IDN, value);
			return;
		}

		// Inače nije ništa što razumijemo:
		throw new FCLexicalException("Pronađen nedozvoljeni znak: '" + data[curPosition] + "'.");
	}

	/**
	 * Metoda koja pokušava detektirati string. Ako je string pronađen postavlja trenutni
	 * token na pronađeni string.
	 *
	 * @return true - ako je string pronađen, inače false.
	 */
	private boolean detectString() {
		// zastavica za string ignore_cases
		FCStringType type = FCStringType.NORMAL;

		if (data[curPosition] == '@' && curPosition < data.length - 1) {
			if (data[curPosition + 1] == '"') {
				type = FCStringType.FAIL;
				curPosition++;
			}
		}
		
		//ako započinje s i onda je možda string ignore_cases
		if (data[curPosition] == 'i' && curPosition < data.length - 1) {
			if (data[curPosition + 1] == '"') {
				type = FCStringType.IGNORE;
				curPosition++;
			}
		}
		// započinje s " onda je string
		if (data[curPosition] == '"') {
			int startIndex = curPosition;
			curPosition++;
			// provjera stringa radi se kod sintaksne analize
			while (curPosition < data.length) {
				if (data[curPosition] == '"') { break; } 
				curPosition++;
			}
			if (curPosition >= data.length) {
				throw new FCLexicalException("String ne završava s: \".");
			}
			int endIndex = curPosition;
			curPosition++;

			String value = new String(data, startIndex + 1, endIndex - startIndex - 1);
			if (validString(value) == false) {
				throw new FCLexicalException("String sadrži nedozvoljene znakove");
			}
			currentToken = new FCToken(FCTokenType.STRING, new FCString(type, value));
			return true;
		}
		return false;
	}

	/**
	 * Metoda koja provjera dobiveni string.
	 * @param in String koji se provjerava.
	 * @return true ako je string ispravan, inače false.
	 */
	private boolean validString(String in) {
		char[] string = in.toCharArray();
		int i = 0;
		boolean subsFlag = false;
		while (i < string.length) {
			if (string[i] == '$' && i < string.length - 1) {
				if (string[i + 1] != '{') {
					return false;
				}
				subsFlag  = true;
				i += 2;
			} else if (string[i] == '}') {
				subsFlag  = false;
				i++;
			} else if (string[i] == '\\' || string[i] == '{' || string[i] == '}' || string[i] == '$') {
				return false;
			} else {
				i++;
			}
		}
		if (subsFlag) {
			return false;
		}
		return true;
	}

	/**
	 * Metoda koja preskace komentare u programskom kodu. Izbacivanje komentare obavlje se tako dugo dok
	 * se ne dektira redak programskog koda koji ne započinje komentarom i nije prazni red.
	 */
	private void skipComment() {
		while(curPosition < data.length) {
			if (data[curPosition] != '#') {
				break;
			}
			for (; curPosition < data.length; curPosition++) {
				if (data[curPosition] == '\n') {
					break;
				}
			}
			skipBanks();
		}
	}

	/**
	 * Metoda koja traži prvi sljedeći znak koji nije praznina i postavlja kazaljku
	 * na tu poziciju.
	 */
	private void skipBanks() {
		while (curPosition < data.length) {
			char c = data[curPosition];
			if (c == ' ' || c == '\t' || c == '\r' || c == '\n') {
				curPosition++;
				continue;
			}
			break;
		}
	}
}
