package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

/**
 * Razred <code>InstrIinput</code> omogućava čitanje {@link Integer} 
 * broja s tipkovnice i spremanje na predviđenu lokaciju.
 * 
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class InstrIinput implements Instruction {
	/**
	 * Lokacija na koju se sprema.
	 */
	private int location;

	
	/**
	 * Konstruktor razreda <code>InstrIinput</code> prima listu argumenata.
	 * 
	 * @param arguments Lista arugmenata.
	 * @throws IllegalArgumentException ako lista ne sadrži 1 element ili 
	 * 			jedini element nije memorijska lokacija.
	 */
	public InstrIinput(List<InstructionArgument> arguments) {
		if(arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument");
		}
		if(!arguments.get(0).isNumber()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}
		
		this.location = ((Integer)arguments.get(0).getValue()).intValue();
	}
	
	
	/**
	 * Metoda koja pokreće izvršavanje instrukcije Iinput. Postavlja
	 * zastavicu na true ako je učitan {@link Integer} broj, inače
	 * false.
	 * 
	 * @param computer Referenca na računalo.
	 * @return false jer nije potrebno zaustaviti rad procesora.
	 */
	@Override
	public boolean execute(Computer computer) {
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(new BufferedInputStream(System.in), 
						StandardCharsets.UTF_8)
		);
	
		try {
			String line = reader.readLine();
			if(line == null) {
				throw new IllegalArgumentException();
			}
			int value = Integer.parseInt(line.trim());

			computer.getMemory().setLocation(
					location,
					value
			);
			computer.getRegisters().setFlag(true);
		} catch (IOException e) {
			computer.getRegisters().setFlag(false);
		} catch (Exception e) {
			computer.getRegisters().setFlag(false);
		}
		
		return false;
	}

}
