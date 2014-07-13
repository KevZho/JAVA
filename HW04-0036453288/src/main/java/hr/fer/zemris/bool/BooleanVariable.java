package hr.fer.zemris.bool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Razred <code>BooleanVariable</code> implementira sučelje
 * <code>NamedBooleanSource</code> te omogućava pohranjivanje
 * imenovanih boolean varijabli.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class BooleanVariable implements NamedBooleanSource {

	private BooleanValue value;
	private String name;
	
	
	/**
	 * Konstruktor razreda <code>BooleanVariable</code>.
	 * 
	 * @param name Ime varijable.
	 * @throws IllegalArgumentException ako je ime prazan string.
	 */
	public BooleanVariable(String name) {
		if(name.isEmpty()) {
			String msg = "BooleanVariable empty name.";
			throw new IllegalArgumentException(msg);
		}
		
		this.name = name;
		this.value = BooleanValue.FALSE;
	}

	
	/**
	 * Overridana metoda getValue() koja vraća 
	 * vrijednost varijable.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public BooleanValue getValue() {
		return this.value;
	}
	
	
	/**
	 * Set metoda koja omogućava postavljanje vrijednosti 
	 * trenutne varijable.
	 * 
	 * @param value Vrijednost na koju se postavlja varijabla.
	 * @throws IllegalAccessException ako je predana null referenca.
	 */
	public void setValue(BooleanValue value) {
		if(value == null) {
			String msg = "BooleanVariable setValue null pointer";
			throw new IllegalArgumentException(msg);
		}
		
		this.value = value;
	}

	
	/**
	 * Overridana metoda getDomain() koja vraća domenu varijable.
	 * Domena je variabla sama.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public List<BooleanVariable> getDomain() {
		List<BooleanVariable> list = new ArrayList<>();
		list.add(this);
		
		return Collections.unmodifiableList(list);
	}
	
	
	/**
	 * Overridana metoda getName() koja vraća ime varijable.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return this.name;
	}

}
