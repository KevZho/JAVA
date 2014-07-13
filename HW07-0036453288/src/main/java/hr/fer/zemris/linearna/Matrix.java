package hr.fer.zemris.linearna;

import java.util.Arrays;

/**
 * Razred <code>Matrix</code> sadrži implementaciju osnovnog tipa matrice.
 *
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class Matrix extends AbstractMatrix {

	protected double[][] elements;
	protected int rows;
	protected int cols;

	/**
	 * Konstruktor koji prima broj redaka i stupa i instancira matricu
	 * koja sadrži na svim mjestima nule.
	 *
	 * @param rows Broj redaka.
	 * @param cols Broj stupaca.
	 */
	public Matrix(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		this.elements = new double[rows][cols];
	}

	/**
	 * Pomoćni konstruktor koji prima polje elemenata te može preuzeti
	 * referencu na to polje ili napraviti kopiranje.
	 *
	 * @param rows Broj redaka.
	 * @param cols broj stupaca.
	 * @param elements Polje elemenata.
	 * @param reuse true nije potrebno kopiranje, inače se provodi kopiranje.
	 */
	public Matrix(int rows, int cols, double[][] elements, boolean reuse) {
		super();
		if (elements == null) {
			throw new IllegalArgumentException();
		}
		if(reuse) {
			this.elements = elements;
		} else {
			this.elements = new double[rows][cols];
			for(int i = 0; i < rows; i++) {
				this.elements[i] = Arrays.copyOf(elements[i], elements[i].length);
			}
		}
		this.cols = cols;
		this.rows = rows;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getRowsCount() {
		return rows;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getColsCount() {
		return cols;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double get(int row, int col) {
		if (row < 0 || row >= rows) {
			throw new IndexOutOfBoundsException();
		}
		if (col < 0 || col >= cols) {
			throw new IndexOutOfBoundsException();
		}
		return this.elements[row][col];
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMatrix set(int row, int col, double value) {
		if (row < 0 || row >= rows) {
			throw new IndexOutOfBoundsException();
		}
		if (col < 0 || col >= cols) {
			throw new IndexOutOfBoundsException();
		}
		this.elements[row][col] = value;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMatrix copy() {
		return new Matrix(rows, cols, elements, false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMatrix newInstance(int rows, int cols) {
		return new Matrix(rows, cols);
	}

	/**
	 * Metoda koja parsira string i stvara matricu na temelju dobivenog 
	 * sadržaja.
	 *
	 * @param s String koji se parsira.
	 * @return Matrica dobivena iz stringa.
	 */
	public static IMatrix parseSimple(String s) {
		String[] rowsArray = s.split("\\s*\\|\\s*");
		int rows = rowsArray.length;
		int cols = -1;
		int rowIndex = 0;
		double[][] array = null;
		for (String row : rowsArray) {
			String[] elements = row.split("\\s+");
			if (cols == -1) {
				cols = elements.length;
				array = new double[rows][cols];
			}
			for (int i = 0; i < elements.length; i++) {
				try {
					array[rowIndex][i] = Double.parseDouble(elements[i]);
				} catch (Exception e) {
					throw new IllegalArgumentException();
				}
			}
			rowIndex++;
		}
		return new Matrix(rows, cols, array, false);
	}
}
