package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions.test;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;
import hr.fer.zemris.java.tecaj_2.jcomp.Registers;
import hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions.InstrTestEquals;
import hr.fer.zemris.java.tecaj_2.jcomp.impl.test.InstructionArgumentImpl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class InstrTestEqualsTest {

	@Test(expected=IllegalArgumentException.class)
	public void oneArgumentTest() {
		List<InstructionArgument> list = new ArrayList<>();
		list.add(new InstructionArgumentImpl("r0"));
		
		new InstrTestEquals(list);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void illegalFirstArgumentTest() {
		List<InstructionArgument> list = new ArrayList<>();
		list.add(new InstructionArgumentImpl("0"));
		list.add(new InstructionArgumentImpl("r1"));
		
		new InstrTestEquals(list);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void illegalSecondArgumentTest() {
		List<InstructionArgument> list = new ArrayList<>();
		list.add(new InstructionArgumentImpl("r0"));
		list.add(new InstructionArgumentImpl("1"));
		
		new InstrTestEquals(list);
	}
	
	@Test
	public void testEqualsFalseTest() {
		Computer c = Mockito.mock(Computer.class);
		Registers r = Mockito.mock(Registers.class);
		
		Mockito.when(c.getRegisters()).thenReturn(r);
		Mockito.when(r.getRegisterValue(0)).thenReturn(new String("100"));
		Mockito.when(r.getRegisterValue(1)).thenReturn(new String("200"));
		
		List<InstructionArgument> list = new ArrayList<>();
		list.add(new InstructionArgumentImpl("r0"));
		list.add(new InstructionArgumentImpl("r1"));
		
		InstrTestEquals equals = new InstrTestEquals(list);
		boolean ret = equals.execute(c);
		Assert.assertEquals(false, ret);
		
		Mockito.verify(c, Mockito.times(3)).getRegisters();
		Mockito.verify(r).getRegisterValue(0);
		Mockito.verify(r).getRegisterValue(1);
		Mockito.verify(r).setFlag(false);
	}
	
	@Test
	public void testEqualsTrueTest() {
		Computer c = Mockito.mock(Computer.class);
		Registers r = Mockito.mock(Registers.class);
		
		Mockito.when(c.getRegisters()).thenReturn(r);
		Mockito.when(r.getRegisterValue(0)).thenReturn(new String("100"));
		Mockito.when(r.getRegisterValue(1)).thenReturn(new String("100"));
		
		List<InstructionArgument> list = new ArrayList<>();
		list.add(new InstructionArgumentImpl("r0"));
		list.add(new InstructionArgumentImpl("r1"));
		
		InstrTestEquals equals = new InstrTestEquals(list);
		boolean ret = equals.execute(c);
		Assert.assertEquals(false, ret);
		
		Mockito.verify(c, Mockito.times(3)).getRegisters();
		Mockito.verify(r).getRegisterValue(0);
		Mockito.verify(r).getRegisterValue(1);
		Mockito.verify(r).setFlag(true);
	}
}
