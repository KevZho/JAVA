package hr.fer.zemris.java.tecaj_2.jcomp.impl.test;

import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;


/**
 * Razred <code>InstructionArgumentImpl</code> omogućava spremanje
 * objekata koji su registri, memorijske lokacije ili stringovi.
 * Pomoćni razred koji se koristi jedino u testovima.
 *
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class InstructionArgumentImpl implements InstructionArgument {

	/**
	 * Vrijednost objekta koji je spremljen.
	 */
	private Object value;
	
	
	/**
	 * Konstruktor koji prima string, koristi se za registre i 
	 * stringove.
	 * 
	 * @param value Vrijednost koja se pohranjuje u objektu.
	 */
	public InstructionArgumentImpl(String value) {
		this.value = value;
	}
	
	/**
	 * Konstruktor koji prima vrijednosti tipa {@lin Integer}.
	 * 
	 * @param value Vrijednost koja se pohranjuje u objektu.
	 */
	public InstructionArgumentImpl(Integer value) {
		this.value = value;
	}
	
	/**
	 * Metoda koja ispituje da li je u objektu spremljen 
	 * registar.
	 *
	 * @return true ako objekt sprema registar, inače false.
	 */
	@Override
	public boolean isRegister() {
		if (!(value instanceof String)) {
			return false;
		}
		
		return ((String)value).matches("r([0-9]|[1-9][0-9]+)");
	}

	
	/**
	 * Metoda koja ispituje da li je u objektu spremljen
	 * string.
	 * 
	 * @return true ako objekt sprema string, inače false.
	 */
	@Override
	public boolean isString() {
		if (!(value instanceof String)) {
			return false;
		}
		
		if(isRegister()) {
			return false;
		}
		
		return true;
	}

	
	/**
	 * Metoda koja ispituje da li objekt sprema Integer
	 * vrijednost.
	 * 
	 * @return true ako objekt sprema string, inače false.
	 */
	@Override
	public boolean isNumber() {
		if(value instanceof Integer) {
			return true;
		}
		
		return false;
	}

	
	/**
	 * Metoda koja dohvaća pravu vrijednost onoga što 
	 * objekt sprema.
	 * 
	 * @return instancu tipa {@link Integer} ako sprema Integer,
	 * 			ili indeks registra, inače instancu tipa String.
	 */
	@Override
	public Object getValue() {
		if(this.isNumber()) {
			return (Integer) value;
		}
		if(this.isRegister()) {
			return Integer.parseInt(((String)value).substring(1));
		}
		
		return (String) value;
	}

}
