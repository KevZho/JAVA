package hr.fer.zemris.java.tecaj_2.jcomp.impl;

import hr.fer.zemris.java.tecaj_2.jcomp.Registers;

/**
 * Razred <code>RegistersImpl</code> omogućava rad sa registrima
 * računala. Implementira sučelje <code>Registers</code>.Na 
 * raspolaganju su registri opće namjene, programsko brojilo 
 * te zastavica.
 * 
 * <pre>
 * Registers reg = new RegistersImpl(16);
 * </pre>
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class RegistersImpl implements Registers {

	/**
	 * Polje koje pamti sadržaj registara.
	 */
	private Object[] registers;
	/**
	 * Broj registara.
	 */
	private int regsLen;
	/**
	 * Vrijednost programskog brojila.
	 */
	private int programCounter;
	/**
	 * Dostupna zastavica.
	 */
	boolean flag;
	
	
	/**
	 * Konstruktor razreda <code>RegistersImpl</code> prima broj registara.
	 * 
	 * @param regsLen Broj registara procesora.
	 * @throws IllegalArgumentException ako traženi broj registara nije dobar.
	 */
	public RegistersImpl(int regsLen) {
		if(regsLen < 0) {
			throw new IllegalArgumentException("RegistersImpl illegal regsLen");
		}
		
		this.registers = new Object[ regsLen ];
		this.regsLen = regsLen;
		this.programCounter = 0; 
		this.flag = false; 
	}
	
	
	/**
	 * Metoda koja dohvaća objekt spremljen u traženom registru.
	 * 
	 * @param index Index traženog registra.
	 * @return Objekt pohranjen u traženom registru.
	 * @throws IndexOutOfBoundsException ako index registra nije dobar.
	 */
	@Override
	public Object getRegisterValue(int index) {
		if(index < 0 || index > regsLen - 1) {
			throw new IndexOutOfBoundsException("RegistersImpl getRegisterValue");
		}
		
		return registers[ index ];
	}

	
	/**
	 * Metoda koja zapisuje dobivenu vrijednost u traženi registar.
	 * 
	 * @param index Indeks traženog registra.
	 * @param value Vrijednost koja se zapisuje u registar.
	 * @throws IndexOutOfBoundsException ako index nije dobar.
	 * @throws NullPointerException ako je predani objekt null referenca.
	 */
	@Override
	public void setRegisterValue(int index, Object value) {
		if(index < 0 || index > regsLen - 1) {
			throw new IndexOutOfBoundsException("RegistersImpl setRegisterValue");
		}
		if(value == null) {
			throw new NullPointerException("RegistersImpl setRegisterValue");
		}
		
		registers[ index ] = value;
	}

	
	/**
	 * Metoda koja dohvaća vrijednost programskog brojila.
	 * 
	 * @return Vrijednost programskog brojila.
	 */
	@Override
	public int getProgramCounter() {
		return programCounter;
	}

	
	/**
	 * Metoda koja postavlja programsko brojilo na željenu vrijednost.
	 * 
	 * @param value Vrijednost na koju se postavlja programsko brojilo.
	 */
	@Override
	public void setProgramCounter(int value) {
		programCounter = value;
	}

	
	/**
	 * Metoda koja povećava vrijednost programskog brojila za jedan.
	 */
	@Override
	public void incrementProgramCounter() {
		programCounter++;
	}

	
	/**
	 * Metoda koja dohvaća vrijednost zastavice.
	 * 
	 * @return Vrijednost zastavice.
	 */
	@Override
	public boolean getFlag() {
		return flag;
	}

	
	/**
	 * Metoda koja postavlja zastavicu na željenu vrijednost.
	 * 
	 * @param value Vrijednost na koju se postavlja zastavica.
	 */
	@Override
	public void setFlag(boolean value) {
		flag = value;
	}

}
