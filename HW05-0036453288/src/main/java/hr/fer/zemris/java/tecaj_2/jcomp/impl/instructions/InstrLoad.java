package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

/**
 * Razred <code>InstrLoad</code> implementira instrukciju koja uzima
 * sadržaj memorijske lokacije u pohranjuje istu u registar.
 * 
 * <pre>
 * load rX, memorijskaAdresa
 * </pre>
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class InstrLoad implements Instruction {
	
	/**
	 * Indeks registra.
	 */
	private int registerIndex;
	/**
	 * Memorijska lokacija.
	 */
	private int memoryLocation;
	
	
	/**
	 * Konstruktor razreda <code>InstrLoad</code> prima listu argumenata.
	 * 
	 * @param arguments Lista arugmenata.
	 * @throws IllegalArgumentException ako broj argumenata nije 2,
	 * 			prvi argument nije registar, drugi argument nije adresa
	 * 			(int broj).
	 */
	public InstrLoad(List<InstructionArgument> arguments) {
		if(arguments.size() != 2) {
			throw new IllegalArgumentException("Expected 2 arguments");
		}
		if(!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}
		if(!arguments.get(1).isNumber()) {
			throw new IllegalArgumentException("Type mismatch for argument 1!");
		}

		this.registerIndex = ((Integer)arguments.get(0).getValue()).intValue();
		this.memoryLocation = ((Integer)arguments.get(1).getValue()).intValue();
	}
	
	
	/**
	 * Metoda koja pokreće izvršavanje instrukcije load.
	 * 
	 * @param computer Referenca na računalo.
	 * @return false jer nije potrebno zaustaviti rad procesora.
	 */
	@Override
	public boolean execute(Computer computer) {
		Object value = computer.getMemory().getLocation(memoryLocation);
		computer.getRegisters().setRegisterValue(
				registerIndex, 
				value
		);
		
		return false;
	}

}
