package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions.test;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;
import hr.fer.zemris.java.tecaj_2.jcomp.Registers;
import hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions.InstrIncrement;
import hr.fer.zemris.java.tecaj_2.jcomp.impl.test.InstructionArgumentImpl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class InstrIncrementTest {
	
	@Test(expected=IllegalArgumentException.class)
	public void zeroArgumentsTest() {
		new InstrIncrement(new ArrayList<InstructionArgument>());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void illegalFirstArgumentTest() {
		List<InstructionArgument> list = new ArrayList<>();
		list.add(new InstructionArgumentImpl("0"));
		new InstrIncrement(list);
	}
	
	@Test
	public void incrementTest() {
		Computer c = Mockito.mock(Computer.class);
		Registers r = Mockito.mock(Registers.class);
		
		Mockito.when(c.getRegisters()).thenReturn(r);
		Mockito.when(r.getRegisterValue(0)).thenReturn(new Integer(5));
		
		List<InstructionArgument> list = new ArrayList<>();
		list.add(new InstructionArgumentImpl(new String("r0")));
		
		InstrIncrement increment = new InstrIncrement(list);
		boolean ret = increment.execute(c);
		Assert.assertEquals(false, ret);
		
		Mockito.verify(c, Mockito.times(2)).getRegisters();
		Mockito.verify(r).getRegisterValue(0);
		Mockito.verify(r).setRegisterValue(0, new Integer(6));
	}
}
