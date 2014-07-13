package hr.fer.zemris.java.gui.layouts;

/**
 * Razred koji definira ograničenaj za CalcLayout.
 * @author Igor Smolkovič
 *
 */
public class RCPosition {

	/**
	 * Broj retka.
	 */
	private int row;
	/**
	 * Broj retka.
	 */
	private int column;

	/**
	 * Konstruktor.
	 * @param row broj retka.
	 * @param column Broj stupca.
	 */
	public RCPosition(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}

	/**
	 * Metoda koja dohvaća redak.
	 * @return redak.
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Metoda koja dohvaća stupac.
	 * @return stupac.
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
 			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		RCPosition other = (RCPosition) obj;
		if (column != other.column) {
			return false;
		}
		if (row != other.row) {
			return false;
		}
		return true;
	}
}
