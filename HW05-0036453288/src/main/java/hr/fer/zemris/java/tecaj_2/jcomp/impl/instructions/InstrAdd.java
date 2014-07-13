package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

/**
 * Razred <code>InstrAdd</code> omogućava zbrajanje sadržaja 
 * dvaju registara.
 * 
 * <pre>
 *   add r2, r1, r3;
 * </pre>
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class InstrAdd implements Instruction {
	/**
	 * Indeks prvog registra.
	 */
	private int registerIndex1;
	/**
	 * Indeks drugog registra.
	 */
	private int registerIndex2;
	/**
	 * Indeks treceg registra.
	 */
	private int registerIndex3;
	
	
	/**
	 * Konstruktor razreda <code>InstrAdd</code> prima listu argumenata.
	 * 
	 * @param arguments Lista arugmenata;
	 * @throws IllegalArgumentException ako lista ne sadrži 3 elementa te
	 * 			ako svi tri nisu registri.
	 */
	public InstrAdd(List<InstructionArgument> arguments) {
		if(arguments.size() != 3) {
			throw new IllegalArgumentException("Expected 3 arguments");
		}
		if(!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}
		if(!arguments.get(1).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 1!");
		}
		if(!arguments.get(2).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 2!");
		}

		this.registerIndex1 = ((Integer)arguments.get(0).getValue()).intValue();
		this.registerIndex2 = ((Integer)arguments.get(1).getValue()).intValue();
		this.registerIndex3 = ((Integer)arguments.get(2).getValue()).intValue();
	}
	
	
	/**
	 * Metoda koja pokreće izvršavanje instrukcije add.
	 * 
	 * @param computer Referenca na računalo.
	 * @return false jer nije potrebno zaustaviti rad procesora.
	 */
	@Override
	public boolean execute(Computer computer) {
		Object value1 = computer.getRegisters().getRegisterValue(registerIndex2);
		Object value2 = computer.getRegisters().getRegisterValue(registerIndex3);
		
		computer.getRegisters().setRegisterValue(
				registerIndex1,
				Integer.valueOf(
						((Integer)value1).intValue() + ((Integer)value2).intValue()
				)
		);
		
		return false;
	}

}
