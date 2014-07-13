package hr.fer.zemris.java.custom.collections;


/**
 * Razred ObjectStack implementira strukturu stog.
 * Prilagođava razred ArrayBackedIndexedCollection da može
 * raditi kao stog.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */

public class ObjectStack {
	
	private ArrayBackedIndexedCollection adaptee;
	
	/**
	 * Konstruktor koji stvara novi adaptee, objekt razreda
	 * ArrayBackedIndexedCollection za 16 elemenata.
	 * 
	 */
	public ObjectStack() {
		adaptee = new ArrayBackedIndexedCollection();
	}
	
	
	/**
	 * Konstruktor koji stvara novi adapter, objekt razreda
	 * ArrayBackedIndexedCollection za proizvoljan broj elemenata;
	 * n >= 1;
	 * 
	 * @param initialCapacity Kapacitet koji se početno postavlja.
	 * @throws IllegalArgumentException ako nije dobar početni kapacitet.
	 */
	public ObjectStack(int initialCapacity) throws IllegalArgumentException {
		if(initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		
		adaptee = new ArrayBackedIndexedCollection(initialCapacity);
	}
	
	/**
	 * Metoda koja ispituje da li je stog prazan.
	 * 
	 * @return true ako je prazan, inače false.
	 */
	public boolean isEmpty() {
		return adaptee.isEmpty();
	}
	
	
	/**
	 * Metoda koja vraća broj elemenata koji su trenutno na stogu.
	 * 
	 * @return Broj elemenata koji su trenutno na stogu.
	 */
	public int size() {
		return adaptee.size();
	}
	
	
	/**
	 * Metoda koja dodaje element na stog dobiven kao parametar.
	 * 
	 * @param value Novi element koji se dodaje na stog.
	 * @throws IllegalArgumentException ako je value null referenca.
	 */
	public void push(Object value) throws IllegalArgumentException {
		if(value == null) {
			throw new IllegalArgumentException();
		}
		
		adaptee.add(value);
	} 
	
	
	/**
	 * Metoda koja uzima jedan element sa stoga.
	 * 
	 * @return Element sa vrha stoga.
	 * @throws EmptyStackException ako je stog prazan.
	 */
	public Object pop() throws EmptyStackException{
		int size = adaptee.size();
		
		if(size != 0) {
			Object value = this.peek();
			adaptee.remove(size - 1);

			return value;
		}
		else {
			throw new EmptyStackException();
		}
	} 
	
	
	/**
	 * Metoda koja vraća referencu na element koji se nalazi na 
	 * vrhu stoga.
	 * 
	 * @return Element sa vrha stoga.
	 * @throws EmptyStackException ako je stog prazan.
	 */
	public Object peek() throws EmptyStackException {
		int size = adaptee.size();
		
		if(size != 0) {
			return adaptee.get(size-1);
		}
		else {
			throw new EmptyStackException();
		}
	}
	
	
	/**
	 * Metoda koja briše sve elemente sa stoga.
	 * 
	 */
	public void clear() {
		adaptee.clear();
	}
}
