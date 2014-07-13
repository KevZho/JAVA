package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

/**
 * Razred <code>InstrMove</code> omogućava kopiranje jednog registra 
 * u drugi.
 * 
 * <pre>
 * 	move r1, r2; kopira sadržaj r2 u r1.
 * </pre>
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class InstrMove implements Instruction {
	/**
	 * Indeks prvog registra.
	 */
	private int registerIndex1;
	/**
	 * Indeks drugog registra.
	 */
	private int registerIndex2;

	
	/**
	 * Konstruktor razreda <code>InstrMove</code> prima listu argumenata.
	 * 
	 * @param arguments Lista argumenata.
	 * @throws IllegalArgumentException ako lista ne sadrži 2 elemenata 
	 * 			ili oba argumenata nisu registri.
	 */
	public InstrMove(List<InstructionArgument> arguments) {
		if(arguments.size() != 2) {
			throw new IllegalArgumentException("Expected 2 arguments");
		}
		if(!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}
		if(!arguments.get(1).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 1!");
		}
		
		this.registerIndex1 = ((Integer)arguments.get(0).getValue()).intValue();
		this.registerIndex2 = ((Integer)arguments.get(1).getValue()).intValue();
	}
	
	
	/**
	 * Metoda koja pokreće izvršavanje instrukcije move.
	 * 
	 * @param computer Referenca na računalo.
	 * @return false jer nije potrebno zaustaviti rad procesora.
	 */
	@Override
	public boolean execute(Computer computer) {
		Object value = computer.getRegisters().getRegisterValue(registerIndex2);
		computer.getRegisters().setRegisterValue(
				registerIndex1, 
				value
		);
		
		return false;
	}

}
