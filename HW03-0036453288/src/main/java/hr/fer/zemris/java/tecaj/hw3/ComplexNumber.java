package hr.fer.zemris.java.tecaj.hw3;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.*;

/**
 * Razred <code>ComplexNumber</code> pruža potporu za rad sa kompleksnim
 * brojevima.
 * 
 * 
 * @author Igor Smolkovič
 * @version 1.0
 * 
 */
public class ComplexNumber {
	
	/**
	 * Realni dio kompleksnog broja.
	 */
	private double real;
	
	/**
	 * Imaginarni dio kompleksnog broja.
	 */
	private double imaginary;
	
	/**
	 * Epsilon vrijednost za usporedbu sličnosti double brojeva.
	 */
	private static double EPS = 1E-8;
	
	
	/**
	 * Konstruktor razreda ComplexNumber.
	 * 
	 * @param real Realni dio komplesnog broja.
	 * @param imaginary Imaginarni dio komplesnog broja.
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}
	
	
	/**
	 * Factory metoda koja stvara instancu razreda ComplexNumber iz samo 
	 * realnog dijela kompleksnog broja.
	 * 
	 * @param real Realni dio komplesnog broja.
	 * @return Instanca razreda ComplexNumber nastala iz realnog dijela 
	 * 			kompleksnog broja.
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}

	
	/**
	 * Factory metoda koja stvara instancu razreda ComplexNumber iz samo
	 * imaginarnog dijela kompleksnog broja.
	 * 
	 * @param imaginary Imaginarni dio kompleksnog broja.
	 * @return Instanca razreda ComplexNumber nastala iz imaginarnog dijela
	 * 			kompleksnog broja.
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}
	
	
	/**
	 * Factory metoda koja stvara instancu razreda ComplexNumber iz apsolutne
	 * vrijednosti i argumenta kompleksnog broja.
	 * 
	 * @param magnitude Apsolutna vrijednost kompleksnog broja.
	 * @param angle Argument kompleksnog broja.
	 * @return Instanca razreda ComplexNumber nastala iz apsolutne vrijednosti
	 * 			i argumenta kompleksnog broja.
	 * 
	 * @throws IllegalArgumentException ako je apsulutna vrijednost kompleksnog 
	 * 			broja negativna.
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		if(magnitude < 0.0) {
			throw new IllegalArgumentException("Negative magnitude.");
		}
		
		final double real = cos(angle) * magnitude;
		final double imaginary = sin(angle) * magnitude;
		
		return new ComplexNumber(real, imaginary);
	}

	
	/**
	 * Factory metoda koja stvara intancu razreda ComplexNumber iz dobivenog
	 * stringa.
	 * 
	 * @param s String iz kojeg se gradi instanca razreda ComplexNumber.
	 * @return Instanca razreda ComplexNumber nastala iz dobivenog stringa.
	 * @throws IllegalArgumentException ako je došlo do greške u parsiranju.
	 */
	public static ComplexNumber parse(String s) {
		s = s.replaceAll(" ", "");
		
		if(s.isEmpty()) {
			throw new IllegalArgumentException("Illegal argument: " + s);
		}

		//regex za oblik bi | a
		String regex = "([-]?(\\d+(\\.\\d+)?)?)i|([-]?\\d+(\\.\\d+)?)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(s);
		
		if(matcher.matches()) {
			if(matcher.group(1) != null) {
				final double imaginary = parseImaginary(matcher.group(1));
				return new ComplexNumber(0, imaginary);
			}
			
			final double real = Double.parseDouble(matcher.group(4));
			return new ComplexNumber(real, 0);
		} 
		
		//regex za oblike a+bi | a-bi
		regex = "([-]?\\d+(\\.\\d+)?)([-+]?(\\d+(\\.\\d+)?)?)i";
		pattern = Pattern.compile(regex);
		matcher = pattern.matcher(s);
		
		if(matcher.matches()) {
			final double real = Double.parseDouble(matcher.group(1));
			final double imaginary = parseImaginary(matcher.group(3));
			return new ComplexNumber(real, imaginary);
		}

		throw new IllegalArgumentException("Illegal argument: " + s);
	}
	
	
	/**
	 * Privatna statička metoda koja  služi a pretvaranje string vrijednosti
	 * imaginarnog dijela kompleksnog broja u odgovarajuću double vrijednost.
	 * Ulazni string ne sadrži imaginarnu jedinicu na kraju. 
	 * 
	 * @param imag String vrijednost imaginarnog dijela kompleksnog broja.
	 * @return Double vrijednost imaginarnog dijela kompleksnog broja.
	 */
	private static double parseImaginary(String imag) {
		//ako je i ili +i
		if(imag.matches("\\+$|$")) {
			return 1.0;
		}
		//ako je -i
		if(imag.matches("\\-$")) {
			return -1.0;
		}
		//oblik broj + i
		return Double.parseDouble(imag);
	}
	
	
	/**
	 * Metoda koja dohvaća realni dio kompleksnog broja.
	 * 
	 * @return Realni dio kompleksnog broja.
	 */
	public double getReal() {
		return real;
	}
	
	
	/**
	 * Metoda koja dohvaća imaginarni dio kompleksnog broja.
	 * 
	 * @return Imaginarni dio kompleksnog broja.
	 */
	public double getImaginary() {
		return imaginary;
	}

	
	/**
	 * Metoda koja dohvaća apsolutnu vrijednost kompleksnog
	 * broja.
	 * 
	 * @return Apsolutna vrijednost kompleksnog broja.
	 */
	public double getMagnitude() {
		final double magnitude = sqrt(real * real + imaginary * imaginary);
		return magnitude;
	}

	
	/**
	 * Metoda koja dohvaća argument kompleksnog broja.
	 * 
	 * @return Argument kompleksnog broja.
	 */
	public double getAngle() {
		final double angle = atan2(imaginary, real);
		return angle;
	}
	
	
	/**
	 * Metoda koja obavlja zbrajanje kompleksnih brojeva. Trenutni kompleksni
	 * broj se zbraja s dobivenim.
	 * 
	 * @param c Kompleksni broj koji se dodaje trenutnom.
	 * @return Instanca razreda ComplexNumber koja sadrži rezultat zbrajanja.
	 * @throws IllegalArgumentException ako je argument null referenca.
	 * 
	 */
	public ComplexNumber add(ComplexNumber c) {
		if(c == null) {
			throw new IllegalArgumentException("ComplexNumber add null");
		}
		
		final double real = this.real + c.getReal();
		final double imaginary = this.imaginary + c.getImaginary();
		
		return new ComplexNumber(real, imaginary);
	}
	
	
	/**
	 * Metoda koja obavlja uduzimanje kompleksnih brojeva. Umanjenik je trenutni
	 * kompleksni broj.
	 * 
	 * @param c Kompleksni broj umanjitelj.
	 * @return Instanca razreda ComplexNumber koja sadrži rezultat oduzimanja.
	 * @throws IllegalArgumentException ako je argument null referenca.
	 */
	public ComplexNumber sub(ComplexNumber c) {
		if(c == null) {
			throw new IllegalArgumentException("ComplexNumber sub null");
		}
		
		final double real = this.real - c.getReal();
		final double imaginary = this.imaginary - c.getImaginary();
		
		return new ComplexNumber(real, imaginary);
	}
	
	
	/**
	 * Metoda koja obavlja množenje kompleksnih brojeva. Množenik je 
	 * trenutni kompleksni broj.
	 * 
	 * @param c Kompleksni broj množitelj.
	 * @return Instanca razreda ComplexNumber koja sadrži rezultat množenja.
	 * @throws IllegalArgumentException ako je argument null referenca.
	 */
	public ComplexNumber mul(ComplexNumber c) {
		if(c == null) {
			throw new IllegalArgumentException("ComplexNumber mul null");
		}
		
		final double cReal = c.getReal();
		final double cImag = c.getImaginary();
		
		final double real = this.real * cReal - this.imaginary * cImag;
		final double imaginary = this.imaginary * cReal + this.real * cImag;
		
		return new ComplexNumber(real, imaginary);
	}
	
	
	/**
	 * Metoda koja obavlja djeljenje kompleksnih brojeva. Djeljenik je 
	 * trenutni kompleksni broj.
	 * 
	 * @param c Kompleksni broj djelitelj.
	 * @return Instanca razreda ComplexNumber koja sadrži rezultat djeljenja.
	 * 
	 * @throws IllegalArgumentException ako argument null referenca.
	 * @throws ArithmeticException ako je došlo do dijeljena s nulom.
	 */
	public ComplexNumber div(ComplexNumber c) {
		if(c == null) {
			throw new IllegalArgumentException("ComplexNumber div null");
		}
		
		final double cReal = c.getReal();
		final double cImag = c.getImaginary();
		final double denum = c.real * c.real + c.imaginary * c.imaginary;
		
		if(denum < EPS) {
			throw new ArithmeticException("Division by zero");
		}
		
		final double real = this.real * cReal + this.imaginary * cImag;
		final double imag = this.imaginary * cReal - this.real * cImag;
		
		return new ComplexNumber(real / denum, imag / denum);
	}
	
	
	/**
	 * Metoda koja obavlja potrenciranje kompleksnih brojeva. Trenutni
	 * kompleksni broj potencira se na n-tu potenciju prema de Moivreovoj
	 * formuli: <br><code>z^n = r^n(cos(n*fi) + isin(n*fi))</code>.
	 * 
	 * @param n Potencija na koju se potencira; n >= 0.
	 * @return Instanca razreda ComplexNumber koja sadrži rezultat potenciranja.
	 * @throws IllegalArgumentException ako je n < 0.
	 */
	public ComplexNumber power(int n) {
		if(n < 0) {
			throw new IllegalArgumentException("ComplexNumber power n < 0");
		}
		if(n == 0) {
			return new ComplexNumber(1.0, 0);
		}
		
		final double angle = this.getAngle();
		double magnitude = this.getMagnitude();
		
		magnitude = pow(magnitude, n);
		final double real = magnitude * cos(angle * n);
		final double imag = magnitude * sin(angle * n);
		
		return new ComplexNumber(real, imag);
	}
	
	
	/**
	 * Metoda koja obavlja korijenovanje kompleksnog broja. Trenutni kompleksni
	 * broj se korijenuje prema de Moivreovoj formuli za korijenovanje: <br><code>
	 * z^(1/n) = r^(1/n)(cos((fi + 2kp)i/n)+isin((fi + 2kp)i/n)), k=0,1,..,n-1.
	 * </code>
	 * 
	 * @param n Korijen koji se računa; n > 0.
	 * @return Polje ComplexNumber koje sadrži n korijena.
	 * @throws IllegalArgumentException ako je n < 1.
	 */
	public ComplexNumber[] root(int n) {
		if(n < 1) {
			throw new IllegalArgumentException("ComplexNumber root n < 1");
		}
		if(n == 1) {
			ComplexNumber[] roots = { this };
			return roots;
		}
		
		final double angle = this.getAngle();
		double magnitude = this.getMagnitude();
		
		magnitude = pow(magnitude, (double)1 / n);
		ComplexNumber[] roots = new ComplexNumber[ n ];
		
		for(int i = 0; i < n; i++) {
			final double real_k = magnitude * cos((angle + i * 2 * PI) / n);
			final double imag_k = magnitude * sin((angle + i * 2 * PI) / n);
			
			roots[ i ] = new ComplexNumber(real_k, imag_k);
		}
		
		return roots;
	}
	
	
	/**
	 * Overridana metoda toString() koja vraća string reprezentaciju
	 * kompleksnog broja.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append(real);
	
		if(signum(imaginary) == 1.0f) {
			builder.append("+");
		}
		
		builder.append(imaginary + "i");
		
		return builder.toString();
	}
}
