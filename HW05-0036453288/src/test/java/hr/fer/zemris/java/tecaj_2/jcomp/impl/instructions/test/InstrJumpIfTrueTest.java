package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions.test;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;
import hr.fer.zemris.java.tecaj_2.jcomp.Registers;
import hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions.InstrJumpIfTrue;
import hr.fer.zemris.java.tecaj_2.jcomp.impl.test.InstructionArgumentImpl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class InstrJumpIfTrueTest {

	@Test(expected=IllegalArgumentException.class)
	public void zeroArgumentsTest() {
		new InstrJumpIfTrue(new ArrayList<InstructionArgument>());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void illegalArgumentTest() {
		List<InstructionArgument> list = new ArrayList<>();
		list.add(new InstructionArgumentImpl(new String("r0")));
		new InstrJumpIfTrue(list);
	}
	
	@Test
	public void jumpIfTrueTest() {
		Computer c = Mockito.mock(Computer.class);
		Registers r = Mockito.mock(Registers.class);
		
		Mockito.when(c.getRegisters()).thenReturn(r);
		Mockito.when(r.getFlag()).thenReturn(false);
		
		List<InstructionArgument> list = new ArrayList<>();
		list.add(new InstructionArgumentImpl(new Integer(10)));
		
		InstrJumpIfTrue jump = new InstrJumpIfTrue(list);
		
		boolean ret = jump.execute(c);
		Assert.assertEquals(false, ret);
		
		Assert.assertEquals(new Integer(0), (Integer)c.getRegisters().getProgramCounter());
	}
	
	@Test
	public void jumpIfTrueTest2() {
		Computer c = Mockito.mock(Computer.class);
		Registers r = Mockito.mock(Registers.class);
		
		Mockito.when(c.getRegisters()).thenReturn(r);
		Mockito.when(r.getFlag()).thenReturn(true);
		Mockito.when(r.getProgramCounter()).thenReturn(new Integer(10));
		
		List<InstructionArgument> list = new ArrayList<>();
		list.add(new InstructionArgumentImpl(new Integer(10)));
		
		InstrJumpIfTrue jump = new InstrJumpIfTrue(list);
		
		boolean ret = jump.execute(c);
		Assert.assertEquals(false, ret);
		
		Mockito.verify(c, Mockito.times(2)).getRegisters();
		Mockito.verify(r).getFlag();
		Mockito.verify(r).setProgramCounter(new Integer(10));
		
		Assert.assertEquals(new Integer(10), (Integer)c.getRegisters().getProgramCounter());
	}
}
