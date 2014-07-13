package hr.fer.zemris.java.tecaj_2.jcomp.impl;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.ExecutionUnit;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;

/**
 * Razred <code>ExecutionUnitImpl</code> prestavlja implementaciju
 * upravljčkog sklopa računala. Pseudokod algoritma:
 * 
 * <pre>
 *   stavi program counter na 0
 *   ponavljaj do beskonačnosti
 *   | dohvati instrukciju iz memorije s lokacije program countera
 *   | uvećaj program counter za 1
 *   | izvrši instrukciju; ako je instrukcija vratila true, prekini petlju
 *   kraj petlje
 *   vrati true
 * </pre>
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class ExecutionUnitImpl implements ExecutionUnit {
	
	/**
	 * Metoda koja izvodi program zapisan u memoriji računala.
	 * 
	 * @param computer Referenca na računalo.
	 * @return true ako je program regularno ravršio, inače false.
	 */
	@Override
	public boolean go(Computer computer) {
		try {
			computer.getRegisters().setProgramCounter(0);
			boolean status = false;
			
			while(status == false) {
				int pc = computer.getRegisters().getProgramCounter();
				Instruction instruction = (Instruction) computer.getMemory().getLocation(pc);
	
				computer.getRegisters().incrementProgramCounter();
				status = instruction.execute(computer);
			}
			return true;
		} catch(Exception e) {
			return false;
		}
	}

}
