package hr.fer.zemris.java.custom.collections;


/**
 * Razred ArrayBackedIndexedCollection implementira
 * strukturu polje.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */

public class ArrayBackedIndexedCollection {
	
	private int size;
	private int capacity;
	private Object[] elements;
	
	
	/**
	 * Konstruktor koji alocira spremnik na početni kapacitet 16.
	 * 
	 */
	public ArrayBackedIndexedCollection() {
		this.elements = new Object[ 16 ];
		this.capacity = 16;
		this.size = 0;
	}
	
	
	/**
	 * Konstruktor koji alocira spremnik na proizvoljni početni kapacitet
	 * dobiven kao parametar metode.
	 * 
	 * @param initialCapacity Željeni početni kapacitet.
	 * @throws IllegalArgumentException ako se kao argument preda kapacitet manje od 1.
	 */
	public ArrayBackedIndexedCollection(int initialCapacity) throws IllegalArgumentException {
		if(initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		
		this.elements = new Object[ initialCapacity ];
		this.capacity = initialCapacity;
		this.size = 0;
	}
	
	
	/**
	 * Metoda koja ispituje da li je spremnik prazan.
	 * 
	 * @return true ako polje ne sadrži objekte, inače false.
	 */
	public boolean isEmpty() {
		return this.size == 0;
	}
	
	
	/**
	 * Metoda koja vraća broj trenutno spremljenih elemenata u spremniku.
	 * 
	 * @return Broj trenutno spremljenih elemenata u spremniku.
	 */
	public int size() {
		return this.size;
	}
	
	
	/**
	 * Metoda koja dodaje novi element dobiven kao parametar na prvo
	 * slobodno mjesto.
	 * 
	 * @param value Novi objekt koji se dodaje u spremnik.
	 * @throws IllegalArgumentException ako je value null referenca.
	 */
	public void add(Object value) throws IllegalArgumentException {
		if(value == null) {
			throw new IllegalArgumentException();
		}
		
		if(this.size == this.capacity) { 
			this.increaseSize();
		}
		
		this.elements[ this.size ] = value;
		this.size++;
	}
	
	
	/**
	 * Metoda koja povećava kapacitet spremnika za dva puta.
	 * 
	 */
	private void increaseSize()  {
		Object[] temp = new Object[ this.capacity * 2];
			
		for(int i = 0; i < this.size; i++) {
			temp[ i ] = this.elements[ i ];
		}
		
		this.elements = temp;
		this.capacity *= 2;
	}
	
	
	/**
	 * Metoda koja dohvaća referencu na element spremnika na poziciji 
	 * index dobivenoj kao parametar.
	 * 
	 * @param index Pozicija elementa koji se želi dohvatiti.
	 * @return Referenca elementa na poziciji index.
	 * @throws IndexOutOfBoundsException ako indeks nije ispravan;
	 * 			index < 0 || index > (size-1).
	 */
	public Object get(int index) throws IndexOutOfBoundsException {
		if(index < 0 || index > (this.size-1)) {
			throw new IndexOutOfBoundsException();
		}
		
		return this.elements[ index ];
	}
	
	
	/**
	 * Metoda koja briše element spremnika na poziciji index dobivenoj kao
	 * parametar. 
	 * 
	 * @param index Pozicija elementa koji se briše.
 	 * @throws IndexOutOfBoundsException ako index nije ispravn;
 	 * 			index < 0 || index > (size-1).
	 */
	public void remove(int index) throws IndexOutOfBoundsException {
		if(index  < 0 || index > (this.size-1)) {
			throw new IndexOutOfBoundsException();
		}
		
		if(index < (this.size-1)) {
			for(int i = index + 1; i < this.size; i++) {
				this.elements[ index++ ] = this.elements[ i ];
			}
		}
		
		this.size--;
	}
	
	
	/**
	 * Metoda koja dodaje element u spremnik na poziciji index dobivenoj kao 
	 * parametar.
	 * 
	 * @param value Vrijednost novog elementa koji se ubacuje.
	 * @param position Pozicija na koju se ubacuje novi element.
	 * @throws IndexOutOfBoundsException ako pozicija nije ispravna; 
	 * 			position < 0 || position > size.
	 */
	public void insert(Object value, int position) throws IndexOutOfBoundsException {
		if(position < 0 && position > this.size) {
			throw new IndexOutOfBoundsException();
		}
		
		if(position == this.size) { 
			this.add(value); 
			return;
		}
		
		if(this.size == this.capacity){
			this.increaseSize();
		}
		
		for(int i = this.size-1; i >= position; i--) {
			this.elements[ i + 1] = this.elements[ i ];
		}
		
		this.elements[ position ] = value; 
		this.size++;	
	}
	
	
	/**
	 * Metoda koja dohvaća index elementa u spremniku sa vrijednoću value
	 * dobivenoj kao parametar.
	 * 
	 * @param value Vrijednost elementa čiji index se traži.
	 * @return Index elementa s vrijednošću value.
	 */
	public int indexOf(Object value) {
		for(int i = 0; i < this.size; i++) {
			if(this.elements[i].equals(value)){
				return i;
			}
		}
		
		return -1;
	}
	
	
	/**
	 * Metoda ispituje postajanje elementa s vrijednošću value unutar 
	 * spremnika.
	 * 
	 * @param value Vrijednost elementa čije se postojanje ispituje.
	 * @return true ako spremnik sadrži element s vrijednošću value, inače false.
	 */
	public boolean contains(Object value) {
		if(this.indexOf(value) == -1) {
			return false;
		}
		
		return true;
	}
	
	
	/**
	 * Metoda koja briše sve elemente iz spremnika.
	 * 
	 */
	public void clear() {
		this.elements = new Object[ 16 ];
		this.size = 0;
	}	
}
