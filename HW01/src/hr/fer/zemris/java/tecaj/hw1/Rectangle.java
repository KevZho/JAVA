package hr.fer.zemris.java.tecaj.hw1;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * 
 * @author Igor Smolkovič
 * 
 */

public class Rectangle {

	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 * Izračunava i na standardni izlaz ispisuje opseg i 
	 * površinu pravokutnika. Širina i visina primaju se 
	 * preko argumenata komandne linije ili upitom korisnika. 
	 * 
	 * @param args Argumenti komandne linije
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String msg1 = "Please provide width: ";
		String msg2 = "Nothing was given.";
		String msg3 = "Width is negative.";
		
		String msg4 = "Please provide height: ";
		String msg5 = "Height is negative.";
		
		double width = 0, height = 0;
		
		if(args.length == 0) {
			width = readNumber(msg1, msg2, msg3);
			height = readNumber(msg4, msg2, msg5);
		}
		else if(args.length != 2) {
			System.err.println("Invalid number of arguments was provided.");
			System.exit(1);
		}
		else {
			width = Double.parseDouble(args[ 0 ]);
			height = Double.parseDouble(args[ 1 ]);
			
			if(width < 0.0 || height < 0.0) {
				System.err.println("Width or height is negative."); 
				System.exit(1);
			}
		}
		
		double area = width * height;
		double perimeter = 2 * height + 2 * width;

		DecimalFormat df = new DecimalFormat("0.0");
		
		System.out.println("You have specified a rectangle with width " 
				+ df.format((int)width) + " and height " 
				+ df.format((int) height)+ ". Its area is "
				+ df.format(area) + " and " + 
				"its perimeter is " + df.format(perimeter) + "."
		);
	}
	
	/**
	 * Metoda koja dohvaća širinu ili visinu upitom korisnika.
	 * 
	 * @param msg1 Poruka: koji parametar se traži
	 * @param msg2 Poruka: ništa nije uneseno
	 * @param msg3 Poruka: parametar je negativan
	 * 
	 * @return Nenegativna double vrijednost
	 * @throws IOException
	 */
	static double readNumber(String msg1, String msg2, String msg3) 
	throws IOException {
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(new BufferedInputStream(System.in))
		);
		
		while(true) {
			System.out.print(msg1);
			String line = reader.readLine();
			
			if(line == null) { 
				System.exit(1); 
			}
			else if(line.isEmpty()) {
				System.err.println(msg2);
			}
			else if(Double.parseDouble(line.trim()) < 0.0) {
				System.err.println(msg3);
			}
			else {
				double number = Double.parseDouble(line.trim());
				return number;
			}
		}
	}
}
