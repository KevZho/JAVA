package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions.test;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;
import hr.fer.zemris.java.tecaj_2.jcomp.Registers;
import hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions.InstrEcho;
import hr.fer.zemris.java.tecaj_2.jcomp.impl.test.InstructionArgumentImpl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class InstrEchoTest {
	
	@Test(expected=IllegalArgumentException.class)
	public void zeroArgumentsTest() {
		new InstrEcho(new ArrayList<InstructionArgument>());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void illegalFirstArgumentTest() {
		List<InstructionArgument> list = new ArrayList<>();
		list.add(new InstructionArgumentImpl("0"));
		new InstrEcho(list);
	}
	
	@Test
	public void echoTest() {
		Computer c = Mockito.mock(Computer.class);
		Registers r = Mockito.mock(Registers.class);
		
		Mockito.when(c.getRegisters()).thenReturn(r);
		Mockito.when(r.getRegisterValue(0)).thenReturn(new String("Test"));
		
		List<InstructionArgument> list = new ArrayList<>();
		list.add(new InstructionArgumentImpl("r0"));
		InstrEcho echo = new InstrEcho(list);
	
		boolean ret = echo.execute(c);
		Assert.assertEquals(false, ret);
		
		Mockito.verify(c).getRegisters();
		Mockito.verify(r).getRegisterValue(0);
	}
}
