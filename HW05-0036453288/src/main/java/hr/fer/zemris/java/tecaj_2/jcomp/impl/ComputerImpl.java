package hr.fer.zemris.java.tecaj_2.jcomp.impl;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Memory;
import hr.fer.zemris.java.tecaj_2.jcomp.Registers;

/**
 * Razred <code>ComputerImpl</code> predstavlja implementaciju
 * podatkovnog dijela računala. Implementira sučelje 
 * <code>Computer</code>.
 * 
 * <pre>
 * Computer comp = new ComputerImpl(256, 16);
 * </pre>
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class ComputerImpl implements Computer {

	/**
	 * Referenca na memoriju računala.
	 */
	private Memory memory;
	/**
	 * Referenca na registre računala.
	 */
	private Registers registers;
	
	
	/**
	 * Konstruktor razreda <code>ComputerImpl</code> prima broj 
	 * memorijskih lokacija i željeni broj registara.
	 * 
	 * @param memSize Broj memorijskih lokacija.
	 * @param regsLen Željeni broj registara.
	 */
	public ComputerImpl(int memSize, int regsLen) {
		this.memory = new MemoryImpl(memSize);
		this.registers = new RegistersImpl(regsLen);
	}

	
	/**
	 * Metoda koja vraća registre računala.
	 * 
	 * @return Registri računala.
	 */
	@Override
	public Registers getRegisters() {
		return registers;
	}

	
	/**
	 * Metoda koja vraća memoriju računala.
	 * 
	 * @return Memorija računala.
	 */
	@Override
	public Memory getMemory() {
		return memory;
	}

}
