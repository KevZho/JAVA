package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

/**
 * Razred <code>InstrEcho</code> dohvaća sadržaj registra i ispisuje
 * ga na standardni izlaz.
 * 
 * <pre>
 * echo rX;
 * </pre>
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class InstrEcho implements Instruction {
	/**
	 * Indeks procesora.
	 */
	private int registerIndex;
	
	
	/**
	 * Konstruktor razreda <code>InstrEcho</code> prima listu argumenata.
	 * 
	 * @param arguments Lista argumenata.
	 * @throws IllegalArgumentException ako veličina liste nije 1 ili 
	 * 			prvi argument nije registar.
	 */
	public InstrEcho(List<InstructionArgument> arguments) {
		if(arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument");
		}
		if(!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}
		
		this.registerIndex = ((Integer)arguments.get(0).getValue()).intValue();
	}
	
	
	/**
	 * Metoda koja pokreće izvršavanje instrukcije echo. 
	 * 
	 * @param computer Referenca na računalo.
	 * @return false jer nije potrebno zaustaviti rad procesora.
	 */
	@Override
	public boolean execute(Computer computer) {
		Object value = computer.getRegisters().getRegisterValue(registerIndex);
		System.out.print(value);
		
		return false;
	}

}
