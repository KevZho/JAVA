package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions.test;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;
import hr.fer.zemris.java.tecaj_2.jcomp.Registers;
import hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions.InstrJump;
import hr.fer.zemris.java.tecaj_2.jcomp.impl.test.InstructionArgumentImpl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class InstrJumpTest {
	
	@Test(expected=IllegalArgumentException.class)
	public void zeroArgumentsTest() {
		new InstrJump(new ArrayList<InstructionArgument>());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void illegalArgumentTest() {
		List<InstructionArgument> list = new ArrayList<>();
		list.add(new InstructionArgumentImpl(new String("r0")));
		new InstrJump(list);
	}
	
	@Test
	public void jumpTest() {
		Computer c = Mockito.mock(Computer.class);
		Registers r = Mockito.mock(Registers.class);
		
		Mockito.when(c.getRegisters()).thenReturn(r);
		
		List<InstructionArgument> list = new ArrayList<>();
		list.add(new InstructionArgumentImpl(new Integer(10)));
		
		InstrJump jump = new InstrJump(list);
		
		boolean ret = jump.execute(c);
		Assert.assertEquals(false, ret);
		
		Mockito.verify(c).getRegisters();
		Mockito.verify(r).setProgramCounter(new Integer(10));
	}
}
