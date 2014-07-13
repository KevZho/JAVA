package hr.fer.zemris.java.tecaj_2.jcomp.impl;

import hr.fer.zemris.java.tecaj_2.jcomp.Memory;

/**
 * Razred <code>MemoryImpl</code> implementira memoriju računala.
 * Implementira sučelje <code>Memory</code>. Omogućava spremanje 
 * objekata proizvoljnog tipa {@link Object}. 
 * 
 * <pre>
 * Memory m = new MemoryImpl(256);
 * </pre>
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class MemoryImpl implements Memory {

	/**
	 * Polje koje pamti sadržaj memorijskih lokacija.
	 */
	private Object[] memory;
	/**
	 * Veličina memorije.
	 */
	private int memorySize;
	
	
	/**
	 * Konstruktor razreda <code>MemoryImpl</code> prima broj 
	 * memorijskih lokacija.
	 * 
	 * @param size Broj memorijskih lokacija.
	 * @throws IllegalArgumentException ako veličina nije odgovarajuća.
	 */
	public MemoryImpl(int size) {
		if(size < 0 )  {
			throw new IllegalArgumentException("MemoryImpl illegal size.");
		}
		
		this.memory = new Object[ size ];
		this.memorySize = size;
	}

	
	/**
	 * Metoda koja postavlja vrijednost na zadanu lokaciju
	 * 
	 * @param location Memorijska lokacija na koju se sprema.
	 * @param value Vrijednost koja se sprema na lokaciju.
	 * @throws IndexOutOfBoundsException ako index nije dobar
	 * @throws NullPointerException ako je predani objekt null referenca.
	 */
	@Override
	public void setLocation(int location, Object value) {
		if(location < 0 || location > memorySize -1) {
			throw new IndexOutOfBoundsException("MemoryImpl setLocation");
		}
		if(value == null) {
			throw new NullPointerException("MemoryImpl setLocation");
		}
		
		memory[ location ] = value;
	}

	
	/**
	 * Metoda koja dohvaća vrijednost sa zadane lokacije.
	 * 
	 * @param location Lokacija iz koje se čita.
	 * @return Vrijednost na traženoj lokaciji.
	 * @throws IndexOutOfBoundsException ako lokacija nije odgovarajuća.
	 */
	@Override
	public Object getLocation(int location) {
		if(location < 0 || location > memorySize - 1) {
			throw new IndexOutOfBoundsException("MemoryImpl getLocation");
		}
		
		return memory[ location ];
	}

}
