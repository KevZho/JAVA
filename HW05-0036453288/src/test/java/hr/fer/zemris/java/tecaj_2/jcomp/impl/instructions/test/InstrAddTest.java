package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions.test;


import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;
import hr.fer.zemris.java.tecaj_2.jcomp.Registers;
import hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions.InstrAdd;
import hr.fer.zemris.java.tecaj_2.jcomp.impl.test.InstructionArgumentImpl;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class InstrAddTest {

	
	@Test
	public void addTest() {
		Computer c = Mockito.mock(Computer.class);
		Registers r = Mockito.mock(Registers.class);
		
		Mockito.when(c.getRegisters()).thenReturn(r);
		Mockito.when(c.getRegisters()).thenReturn(r);
		Mockito.when(r.getRegisterValue(1)).thenReturn(new Integer(10));
		Mockito.when(r.getRegisterValue(2)).thenReturn(new Integer(20));
		
		List<InstructionArgument> list = new ArrayList<>();
		list.add(new InstructionArgumentImpl("r0"));
		list.add(new InstructionArgumentImpl("r1"));
		list.add(new InstructionArgumentImpl("r2"));
		
		InstrAdd add = new InstrAdd(list);
		boolean ret =  add.execute(c);
		
		Assert.assertEquals(false, ret);
		
		Mockito.verify(c, Mockito.times(3)).getRegisters();
		Mockito.verify(r).getRegisterValue(1);
		Mockito.verify(r).getRegisterValue(2);
		Mockito.verify(r).setRegisterValue(0, new Integer(30));
	}
	
	@Test
	public void addTest2() {
		Computer c = Mockito.mock(Computer.class);
		Registers r = Mockito.mock(Registers.class);
		
		Mockito.when(c.getRegisters()).thenReturn(r);
		Mockito.when(c.getRegisters()).thenReturn(r);
		Mockito.when(r.getRegisterValue(11)).thenReturn(new Integer(-10));
		Mockito.when(r.getRegisterValue(12)).thenReturn(new Integer(20));
		
		List<InstructionArgument> list = new ArrayList<>();
		list.add(new InstructionArgumentImpl("r10"));
		list.add(new InstructionArgumentImpl("r11"));
		list.add(new InstructionArgumentImpl("r12"));
		
		InstrAdd add = new InstrAdd(list);
		
		boolean ret =  add.execute(c);	
		Assert.assertEquals(false, ret);
		
		Mockito.verify(c, Mockito.times(3)).getRegisters();
		Mockito.verify(r).getRegisterValue(11);
		Mockito.verify(r).getRegisterValue(12);
		Mockito.verify(r).setRegisterValue(10, new Integer(10));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void oneArgumentTest() {
		List<InstructionArgument> list = new ArrayList<>();
		list.add(new InstructionArgumentImpl("r0"));
		
		new InstrAdd(list);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void illegalFirstArgumentTest() {
		List<InstructionArgument> list = new ArrayList<>();
		list.add(new InstructionArgumentImpl("0"));
		list.add(new InstructionArgumentImpl("r1"));
		list.add(new InstructionArgumentImpl("r2"));
		
		new InstrAdd(list);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void illegalSecondArgumentTest() {
		List<InstructionArgument> list = new ArrayList<>();
		list.add(new InstructionArgumentImpl("r0"));
		list.add(new InstructionArgumentImpl("1"));
		list.add(new InstructionArgumentImpl("r2"));
		
		new InstrAdd(list);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void illegalThirdArgumentTest() {
		List<InstructionArgument> list = new ArrayList<>();
		list.add(new InstructionArgumentImpl("r0"));
		list.add(new InstructionArgumentImpl("r1"));
		list.add(new InstructionArgumentImpl("2"));
		
		new InstrAdd(list);
	}
}
