package hr.fer.zemris.linearna.demo;

import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.Vector;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Razred <code>Prog5</code> sadržid demo primjer koji omogućava učitavanje i
 * izračunavanje 2D ili 3D reflektiranih vektora.
 *
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class Prog5 {

	/**
	 * Regex za ispitivanje ispravnosti unesenog vektora
	 */
	private static final String REGEX = "\\d+(\\.\\d+)?\\s+\\d+(\\.\\d+)?|\\d+(\\.\\d+)?\\s+\\d+(\\.\\d+)?\\s+\\d+(\\.\\d+)?";

	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 *
	 * @param args Argumenti komandne linije.
	 */
	public static void main(String[] args) {
		IVector v1 = null;
		IVector v2 = null;
		try {
			v1 = readVector("v1");
			v2 = readVector("v2");
			IVector res = v1.nNormalize().scalarMultiply(2)
					.scalarMultiply(v1.nNormalize()
					.scalarProduct(v2)).sub(v2);

			System.out.println("Reflektirani vektor je: " + res);
		} catch (IOException e) {
			System.err.println("Došlo je do greške prilikom čitanja vektora");
		} catch (Exception e) {
			System.err.println("Došlo je do greške prilikom izračuna reflektiranog vektora.");
		}
	}

	/**
	 * Metoda koja učitava vektor sa komandne linije.
	 *
	 * @param name Ime vektora koji se učitava.
	 * @return instanca razreda IVector dobivana parsiranjem unosa.
	 * @throws IOException ako je došlo do greške prilikom čitanja vektora.
	 */
	private static IVector readVector(String name) throws IOException {
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(new BufferedInputStream(System.in),
						StandardCharsets.UTF_8)
		);
		IVector retValue = null;
		while (true) {
			System.out.printf("Unesite vektor %s (2D ili 3D vektor):%n", name);
			String line = reader.readLine();
			if (line == null) {
				System.exit(-1);
			} else if (line.trim().matches(REGEX)) {
				try {
					retValue = Vector.parseSimple(line.trim());
					break;
				} catch (Exception e) {
					continue;
				}
			} else {
				System.err.println("Moguće unijeti samo 2D ili 3D vektor.");
			}
		}
		return retValue;
	}
}
