package hr.fer.zemris.linearna.demo;

import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.Matrix;
import hr.fer.zemris.linearna.VectorMatrixView;

/**
 * Razred <code>Prog4</code> sadrži demo primjer koji omogućava 
 * izračunavanje baricentričnih koordinata ako se na dobivene
 * vrhove trokuta i točku T gleda kao sustav linearnih jednadžbi
 * oblika:
 *
 * <pre>
 * Ax * t1 + Bx * t2 + Cx * t3 = Tx
 * Ay * t1 + By * t2 + Cy * t3 = Ty
 * Az * t1 + Bz * t2 + Cz * t3 = Tz
 * </pre>
 *
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class Prog4 {

	/**
	 * Epsilon za usporedbu double brojeva.s
	 */
	private static final double EPS = 1E-8;

	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 *
	 * @param args Argumenti komandne linije.
	 */
	public static void main(String[] args) {
		IMatrix m = null;
		IMatrix m2 = null;
		try {
			m  = Matrix.parseSimple("1 5 3 | 0 0 8 | 0 0 0");
			m2 = Matrix.parseSimple("3 | 4 | 0");
		} catch (Exception e) {
			System.out.println("Matrice nisu ispravno zadane.");
			System.exit(-1);;
		}
		try {
			if (Math.abs(m.determinant()) < EPS) {
				int index = findRow(m);
				if (index == -1) {
					System.err.println("Unesena matrice se ne može prilagoditi da je determinanta != 0");
					System.exit(-1);
				}
				m = modifyMatrix(m, index);
				m2.set(index, 0, 1);
			}
			IVector res = new VectorMatrixView(m.nInvert().nMultiply(m2));
			System.out.println("Rješenje je: " + res.toString());
		} catch (Exception e) {
			System.err.println("Došlo je do greške prilikom traženja rješenja");
			System.exit(-1);
		}
	}

	/**
	 * Metoda koja traži u koji red treba postaviti sve jedinice
	 * u dobivenoj matrici kako bi determinanta bila različita od
	 * nule.
	 *
	 * @param m Matrica u kojo se traži koji red treba izmjeniti.
	 * @return Index retka koji se mora izmjeniti kako bi determinanta
	 * 			bila različita od nule.
	 */
	private static int findRow(IMatrix m) {
		for(int i = 0; i < m.getRowsCount(); i++) {
			IMatrix current = modifyMatrix(m.copy(), i);
			if (Math.abs(current.determinant()) > EPS) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Metoda koja mijenja matricu tako da u red "i" postavi sve jedinice.
	 *
	 * @param m Matrica koja se mijenja.
	 * @param i Index retka koji se mijenja.
	 * @return Matrica nakon promjena.
	 */
	private static IMatrix modifyMatrix(IMatrix m, int i) {
		for (int j = 0; j < m.getColsCount(); j++) {
			m.set(i, j, 1);
		}
		return m;
	}
}
