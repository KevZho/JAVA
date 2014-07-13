package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

/**
 * Razred <code>InstrTestEquals</code> ispituje sadrže 
 * li registri isti sadržaj.
 * 
 * <pre>
 * 	 testEquals r0, r1;
 * </pre>
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class InstrTestEquals implements Instruction {
	/**
	 * Indeks prvog registra.
	 */
	private int registerIndex1;
	/**
	 * Indeks drugog registra.
	 */
	private int registerIndex2;

	
	/**
	 * Konstruktor razreda <code>InstrTestEquals</code> prima listu 
	 * argumenata.
	 * 
	 * @param arguments Lista argumenata.
	 * @throws IllegalArgumentException ako lista ne sadrži 2 elementa
	 * 			ili oba elementa nisu registri.
	 */
	public InstrTestEquals(List<InstructionArgument> arguments) {
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
	 * Metoda koja pokreće izvršavanje instrukcije testEquals. Postavlja
	 * zastavicu na true ako su sadržaji isti, inače false.
	 * 
	 * @param computer Referenca na računalo.
	 * @return false jer nije potrebno zaustaviti rad procesora.
	 */
	@Override
	public boolean execute(Computer computer) {
		Object value1 = computer.getRegisters().getRegisterValue(registerIndex1);
		Object value2 = computer.getRegisters().getRegisterValue(registerIndex2);
		
		boolean flag = value1.equals(value2);
		computer.getRegisters().setFlag(flag);
		
		return false;
	}

}
