package hr.fer.zemris.bool.qmc;

import hr.fer.zemris.bool.Mask;
import hr.fer.zemris.bool.MaskValue;

import java.util.HashSet;
import java.util.Set;

/**
 * Razred koji implimentira strukturu koja omogućava lagano množenje
 * suma produkata u Pyne-McCluskey postupku. Također omogućava primjenu
 * pravila A * A = A.
 *
 * @author Igor Smolkovič
 *
 */
public class MaskSet {

	/**
	 * Set maski. Odmah se osigura A *A = A.
	 */
	private Set<Mask> value;

	/**
	 * Konstruktor.
	 * @param m inicijalna maska.
	 */
	public MaskSet(Mask m) {
		this.value = new HashSet<>();
		this.value.add(m);
	}

	/**
	 * Konstruktor.
	 * @param set postojeći MaskSet.
	 */
	public MaskSet(MaskSet set) {
		this.value = new HashSet<>();
		this.value.addAll(set.value);
	}

	/**
	 * Metoda koja dohvaća vrijednost koju pohranjuje MaskSet.
	 * @return set maski.
	 */
	public Set<Mask> getValue() {
		return value;
	}

	/**
	 * Metoda koja radi spajanje dva MaskSeta.
	 * @param other set s kojim se spaja.
	 */
	public void add(MaskSet other) {
		value.addAll(other.getValue());
	}

	/**
	 * Metoda koja dohvaća broj elemenata seta.
	 * @return broj elemenata seta.
	 */
	public int getSize() {
		return value.size();
	}

	/**
	 * Metoda koja koja računa sumu varijabli.
	 * @return suma varijabli.
	 */
	public int sumOfMasks() {
		int counter = 0;
		for (Mask m : value) {
			for (int i = 0; i < m.getSize(); i++) {
				if (m.getValue(i) != MaskValue.DONT_CARE) {
					counter++;
				}
			}
		}
		return counter;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + value.hashCode();
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
		MaskSet other = (MaskSet) obj;
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}
}
