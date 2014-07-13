package hr.fer.zemris.java.tecaj_2.jcomp.impl.test;

import java.nio.file.Paths;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.ExecutionUnit;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionCreator;
import hr.fer.zemris.java.tecaj_2.jcomp.impl.ComputerImpl;
import hr.fer.zemris.java.tecaj_2.jcomp.impl.ExecutionUnitImpl;
import hr.fer.zemris.java.tecaj_2.jcomp.parser.InstructionCreatorImpl;
import hr.fer.zemris.java.tecaj_2.jcomp.parser.ProgramParser;

import org.junit.Assert;
import org.junit.Test;

public class ExecutionUnitImplTest {

	@Test
	public void test() {
		Computer comp = new ComputerImpl(256, 16);
		
		InstructionCreator creator = new InstructionCreatorImpl(
		"hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions"
		);

		try {
			ProgramParser.parse(
					Paths.get("./examples/prim1.txt").toString(),
					comp,
					creator
			);
		} catch (Exception e) {
			Assert.fail();
		}
			
		ExecutionUnit exec = new ExecutionUnitImpl();
		boolean ret = exec.go(comp);
		Assert.assertTrue(ret);
	}
	
	@Test
	public void test2() {
		Computer comp = new ComputerImpl(256, 16);
		
		InstructionCreator creator = new InstructionCreatorImpl(
		"hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions"
		);

		try {
			ProgramParser.parse(
					Paths.get("./examples/prim1Fail.txt").toString(),
					comp,
					creator
			);
		} catch (Exception e) {
		}
			
		ExecutionUnit exec = new ExecutionUnitImpl();
		boolean ret = exec.go(comp);
		Assert.assertFalse(ret);
	}
}
