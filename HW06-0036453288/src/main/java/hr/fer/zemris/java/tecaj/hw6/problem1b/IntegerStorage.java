package hr.fer.zemris.java.tecaj.hw6.problem1b;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Subjekt <code>IntegerStorage</code> pruža sučelje za prijavu i odjavu
 * promatrača, sadrži informacije koje dohvaćaju promatrači i obavještava
 * promatrače o promjenama. 
 *
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class IntegerStorage {
	/**
	 * Integer vrijednost koja je pohranjena.
	 */
	private int value;
	/**
	 * Lista promatrača.
	 */
	private List<IntegerStorageObserver> observers;
	
	/**
	 * Konstruktor razreda <code>IntegerStorage</code> prima početnu
	 * vrijednost.
	 *
	 * @param initialValue
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
	}
	
	/**
	 * Metoda koja dodaje novog promatrača u listu promatrača. Promatrač je dodan samo
	 * ako već ne postoji u listi.
	 *
	 * @param observer Promatrač koji se dodaje u listu promatrača.
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if (observers == null) {
			observers =  new CopyOnWriteArrayList<>();
		}

		if (!observers.contains(observer)) {
			observers.add(observer);
		}
	}
	
	/**
	 * Metoda koja briše promatrača iz liste. Promatrač je obrisan samo ako
	 * postoji u listi.
	 *
	 * @param observer Promatrač koji se briše iz liste promatrača.
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		if (observers.contains(observer)) {
			observers.remove(observer);
		}
	}
	
	/**
	 * Metoda koja prazni listu promatrača.
	 */
	public void clearObservers() {
		observers.clear();
	}
	
	/**
	 * Metoda koja dohvaća trenutno pohranjenu {@link Integer} vrijednost.
	 *
	 * @return Trenutno pohranjena {@link Integer} vrijednost.
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Metoda koja mijenja trenutno pohranjenu {@link Integer} vrijednost i 
	 * obavještava promatrače o promjenama.
	 *
	 * @param value Nova vrijednost koja se pohranjuje.
	 */
	public void setValue(int value) {
		// Only if new value is different than the current value:
		if (this.value!=value) {
		// Update current value
			IntegerStorageChange change = new IntegerStorageChange(
					this,
					this.value,
					value
			);
			this.value = value;
			System.out.print("Provided new value: " + value + ", ");
			// Notify all registered observers
			if (observers!=null) {
				for (IntegerStorageObserver observer : observers) {
					observer.valueChanged(change);
				}
			}
		}
	}
}
