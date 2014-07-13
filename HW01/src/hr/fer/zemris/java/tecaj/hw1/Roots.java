package hr.fer.zemris.java.tecaj.hw1;

import java.text.DecimalFormat;

/**
 * 
 * @author Igor Smolkovič
 * 
 */

public class Roots {
	
	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 * Izračunava te na standardni izlaz ispisuje n-ti korijen
	 * kompleksnog broja. Komplesni broj i n primaju se kao 
	 * argumenti komandne linije. Korijen koji se računa mora
	 * biti barem kvadratni.
	 * 
	 * @param args Argumenti komandne linije
	 */
	public static void main(String[] args) {
		if(args.length != 3) {
			System.err.println("Please provide 3 arguments");
		}
		else if(Integer.parseInt(args[ 2 ]) < 2) {
			System.err.println("Root must be > 1");
		}
		
		double real = Double.parseDouble(args[ 0 ]);
		double imag = Double.parseDouble(args[ 1 ]);
		int roots = Integer.parseInt(args[ 2 ]);
		
		if(Math.abs(real - 0.0) < 1E-6 && Math.abs(imag - 0.0) < 1E-6) {
			System.err.println("(a + bi) = 0!!!");
			System.exit(1);
		}
		
		System.out.println("You requested calculation of "
			+ roots + ". roots. Solutions are:"
		);
		
		double r = Math.sqrt(real * real + imag * imag);
		double rn = Math.pow(r, (double)1 / roots);
		double fi = Math.atan2(imag, real);
		
		DecimalFormat df= new DecimalFormat("0.#####");
		
		for(int k = 0; k < roots; k++) {
			double real_k = rn * Math.cos((fi + k * 2 * Math.PI) / roots);
			double imag_k = rn * Math.sin((fi + k * 2 * Math.PI) / roots);
			
			System.out.print((k+1) + ") ");
			
			if(Math.abs(real_k-0) < 1E-6 && Math.abs(imag_k-0) < 1E-6) {
				System.out.println(df.format(0));
			}
			else if(Math.abs(real_k-0) < 1E-6) {
				System.out.println(df.format(imag_k) + "i");
			}
			else if(Math.abs(imag_k-0) < 1E-6) {
				System.out.println(df.format(real_k));
			}
			else if(Math.signum(imag_k) == 1.0f) {
				System.out.println(df.format(real_k) + " + " 
						+ df.format(imag_k) + "i");
			}
			else {
				System.out.println(df.format(real_k) + " - " 
						+ df.format(Math.abs(imag_k)) + "i");
			}
		}
	}
	
}
