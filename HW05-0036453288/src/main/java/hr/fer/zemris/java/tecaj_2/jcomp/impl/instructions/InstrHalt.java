package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

/**
 * Razred <code>InstrHalt</code> implementira instrukciju koja 
 * zaustavlja rad procesora.
 * 
 * <pre>
 * halt;
 * </pre>
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class InstrHalt implements Instruction {

	/**
	 * Konstruktor razreda <code>InstrHalt</code> prima listu argumenata.
	 * 
	 * @param arguments Lista argumenata.
	 * @throws IllegalArgumentException ako lista nije prazna.
	 */
	public InstrHalt(List<InstructionArgument> arguments) {
		if(arguments.size() != 0) {
			throw new IllegalArgumentException("Expected 0 arguments");
		}
	}

	
	/**
	 * Metoda koja pokreće izvršavanje instrukcije halt.
	 * 
	 * @param computer Referenca na računalo.
	 * @return true jer se zaustavlja rad procesora.
	 */
	@Override
	public boolean execute(Computer computer) {
		return true;
	}

}
