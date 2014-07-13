package hr.fer.zemris.java.tecaj_2.jcomp;


import java.io.FileNotFoundException;
import java.nio.file.Paths;

import hr.fer.zemris.java.tecaj_2.jcomp.impl.ComputerImpl;
import hr.fer.zemris.java.tecaj_2.jcomp.impl.ExecutionUnitImpl;
import hr.fer.zemris.java.tecaj_2.jcomp.parser.InstructionCreatorImpl;
import hr.fer.zemris.java.tecaj_2.jcomp.parser.ProgramParser;

/**
 * Razred <code>Simulator</code> omogućava izvršavanje 
 * programa zapisani u ulaznoj datoteci. Kao jedini argument
 * komandne linije potrebno je postaviti lokaciju datoteke iz
 * koje se čita program. Primer:
 * 
 * <pre>
 * 	examples/prim1.txt
 * </pre>
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class Simulator {
	
	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 * 
	 * @param args Arugmenti komandne linije; args[0] lokacija dototeke.
	 */
	public static void main(String[] args) {
		if(args.length != 1) {
			System.err.println("Expected 1 argument");
			System.exit(-1);
		}
		
		// Stvori računalo s 256 memorijskih lokacija i 16 registara
		Computer comp = new ComputerImpl(256, 16);
		
		// Stvori objekt koji zna stvarati primjerke instrukcija
		InstructionCreator creator = new InstructionCreatorImpl(
		"hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions"
		);
			
		// Napuni memoriju računala programom iz datoteke; instrukcije stvaraj
		// uporabom predanog objekta za stvaranje instrukcija
		try {
			ProgramParser.parse(
					Paths.get(args[ 0 ]).toString(),
					comp,
					creator
			);
		} catch (FileNotFoundException e) {
			System.err.println("File not found.");
			System.exit(-1);
		} catch (Exception e) {
			System.exit(-1);
		}
		
		// Stvori izvršnu jedinicu
		ExecutionUnit exec = new ExecutionUnitImpl();
		
		// Izvedi program
		exec.go(comp);
	}
}
