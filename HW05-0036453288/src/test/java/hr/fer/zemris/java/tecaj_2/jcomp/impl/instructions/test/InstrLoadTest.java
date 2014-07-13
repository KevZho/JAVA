package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions.test;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;
import hr.fer.zemris.java.tecaj_2.jcomp.Memory;
import hr.fer.zemris.java.tecaj_2.jcomp.Registers;
import hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions.InstrLoad;
import hr.fer.zemris.java.tecaj_2.jcomp.impl.test.InstructionArgumentImpl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class InstrLoadTest {

	@Test(expected=IllegalArgumentException.class)
	public void zeroArgumentsTest() {
		new InstrLoad(new ArrayList<InstructionArgument>());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void illegalFirstArgument() {
		List<InstructionArgument> list = new ArrayList<>();
		list.add(new InstructionArgumentImpl(new String("0")));
		list.add(new InstructionArgumentImpl(new String("r1")));
		new InstrLoad(list);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void illegalSecondArgument() {
		List<InstructionArgument> list = new ArrayList<>();
		list.add(new InstructionArgumentImpl(new String("r0")));
		list.add(new InstructionArgumentImpl(new String("r1")));
		new InstrLoad(list);
	}
	
	@Test
	public void loadTest() {
		Computer c = Mockito.mock(Computer.class);
		Memory m = Mockito.mock(Memory.class);
		Registers r = Mockito.mock(Registers.class);
		
		Mockito.when(c.getRegisters()).thenReturn(r);
		Mockito.when(c.getMemory()).thenReturn(m);
		Mockito.when(m.getLocation(0)).thenReturn(new Integer(10));
		
		List<InstructionArgument> list = new ArrayList<>();
		list.add(new InstructionArgumentImpl(new String("r0")));
		list.add(new InstructionArgumentImpl(new Integer(0)));
		
		InstrLoad load = new InstrLoad(list);
		boolean ret = load.execute(c);
		Assert.assertEquals(false, ret);
		
		Mockito.verify(c).getRegisters();
		Mockito.verify(c).getMemory();
		Mockito.verify(m).getLocation(0);
		Mockito.verify(r).setRegisterValue(0, new Integer(10));
	}
}
