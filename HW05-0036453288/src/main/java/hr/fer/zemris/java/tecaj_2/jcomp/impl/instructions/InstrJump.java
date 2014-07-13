package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;


/**
 * Razred <code>InstrJump/<code> omućava postavljanje 
 * programskog brojila na proizvoljnu vrijednost.
 * 
 * <pre>
 *   jump lokacija;
 * </pre>
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class InstrJump implements Instruction {
	/**
	 * Memorijska lokacija.
	 */
	private int location;

	
	/**
	 * Konstruktor razreda <code>InstrJump</code> prima listu argumenata.
	 * 
	 * @param arguments Lista argumenata.
	 * @throws IllegalArgumentException ako lista ne sadrži jedan element,
	 * 			ili jedini element nije memorijska lokacija.
	 */
	public InstrJump(List<InstructionArgument> arguments) {
		if(arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 arguments");
		}
		if(!arguments.get(0).isNumber()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}
		
		this.location = ((Integer)arguments.get(0).getValue()).intValue();
	}
	
	
	/**
	 * Metoda koja pokreće izvršavanje instrukcije jump.
	 * 
	 * @param computer Referenca na računalo.
	 * @return true jer nije potrebno zaustaviti rad procesora.
	 */
	@Override
	public boolean execute(Computer computer) {
		computer.getRegisters().setProgramCounter(location);
		return false;
	}

}
