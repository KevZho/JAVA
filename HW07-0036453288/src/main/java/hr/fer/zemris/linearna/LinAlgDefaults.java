package hr.fer.zemris.linearna;

/**
 * Factory razred koji instancira defaultne matrice i vektore.
 *
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class LinAlgDefaults {

	/**
	 * Factory metoda za koja instancira matricu veličine rows x cols sa 
	 * svim elementima postavljenim na 0.
	 *
	 * @param rows Broj redaka.
	 * @param cols Broj stupaca.
	 * @return Matrica veličine rows x cols.
	 */
	public static IMatrix defaultMatrix(int rows, int cols) {
		return new Matrix(rows, cols);
	}

	/**
	 * Factory metoda koja instancira vektor tražene dimenzije sa svim
	 * elementima postavljenim na 0.
	 *
	 * @param dimension Dimenzija vektora.
	 * @return Vektor tražene dimenzije.
	 */
	public static IVector defaultVector(int dimension) {
		return new Vector(new double[dimension]);
	}
}
