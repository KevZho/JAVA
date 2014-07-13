package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions.test;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;
import hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions.InstrHalt;
import hr.fer.zemris.java.tecaj_2.jcomp.impl.test.InstructionArgumentImpl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class InstrHaltTest {

	@Test(expected=IllegalArgumentException.class)
	public void illegalArgumentsTest() {
		List<InstructionArgument> list = new ArrayList<>();
		list.add(new InstructionArgumentImpl(new String("test")));
		new InstrHalt(list);
	}
	
	@Test
	public void haltTest() {
		Computer c = Mockito.mock(Computer.class);
		InstrHalt halt = new InstrHalt(new ArrayList<InstructionArgument>());
		boolean ret = halt.execute(c);
		Assert.assertTrue(ret);
	}
}
