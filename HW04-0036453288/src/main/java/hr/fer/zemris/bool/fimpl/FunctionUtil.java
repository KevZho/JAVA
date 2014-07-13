package hr.fer.zemris.bool.fimpl;

import java.util.List;

import hr.fer.zemris.bool.BooleanValue;
import hr.fer.zemris.bool.BooleanVariable;

/**
 * Razred koji sadrži pomoćne funkcije koje se koriste 
 * u više drugih razreda.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class FunctionUtil {

	/**
	 * Statička metoda koja pretvara index u binarni string duljine
	 * size.
	 * 
	 * @param size Duljina izlaznog stringa.
	 * @param index Index koji se pretvara u binarni string.
	 * @return Binarni string nastao od indexa duljine size, 
	 * 			null ako pretvorba nije uspješna.
	 */
	public static String convertToBinaryString(int size, int index) {
		String binaryString = Integer.toBinaryString(index);			
		int zeros = size - binaryString.length();
	
		if(size < binaryString.length()) {
			return null;
		}
		
		StringBuilder builder = new StringBuilder();
		
		for(int i = 0; i < zeros; i++) {
			builder.append("0");
		}
	
		builder.append(binaryString);
		return builder.toString();
	}
	
	/**
	 * Metoda koja pretvara vrijednosti varijabli domene u integer
	 * vrijednost.
	 * 
	 * @return Vraća integer vrijednost(index) varijabli domene. Ako postoji
	 * 			x(don't care) onda vraća -1. 
	 */
	public static int convertInputToInt(List<BooleanVariable> domain) {
		StringBuilder builder = new StringBuilder();
		
		for(int i = 0; i < domain.size(); i++) {
			BooleanValue current = domain.get(i).getValue();
			
			if(current == BooleanValue.TRUE) {
				builder.append("1");
			}
			else if(current == BooleanValue.FALSE) {
				builder.append("0");
			}
			else {
				return -1;
			}
		}
		
		return Integer.parseInt(builder.toString(), 2);
	}
}
