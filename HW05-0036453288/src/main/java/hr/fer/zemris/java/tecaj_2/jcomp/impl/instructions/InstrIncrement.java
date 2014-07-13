package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

/**
 * Razred <code>InstrIncrement</code> omogućava povećavanje sadržaja
 * registra za jedan.
 * 
 * <pre>
 *   increment r1;
 * </pre>
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class InstrIncrement implements Instruction {
	/**
	 * Indeks registra.
	 */
	private int registerIndex;
	
	
	/**
	 * Konstruktor razreda <code>InstrIncrement</code> prima listu
	 * argumenata.
	 * 
	 * @param arguments Lista argumenata.
	 * @throws IllegalArgumentException ako lista ne sadrži jedan element
	 * 			ili jedini element nije registar.
	 */
	public InstrIncrement(List<InstructionArgument> arguments) {
		if(arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 arguments");
		}
		if(!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}
		
		this.registerIndex = ((Integer)arguments.get(0).getValue()).intValue();
	}
	
	
	/**
	 * Metoda koja pokreće izvršavanje instrukcije increment.
	 * 
	 * @param computer Referenca na računalo.
	 * @return false jer nije potrebno zaustaviti rad procesora.
	 */
	@Override
	public boolean execute(Computer computer) {
		Object value = computer.getRegisters().getRegisterValue(registerIndex);
		computer.getRegisters().setRegisterValue(
				registerIndex, 
				((Integer)value).intValue() + 1
		);
		
		return false;
	}

}
