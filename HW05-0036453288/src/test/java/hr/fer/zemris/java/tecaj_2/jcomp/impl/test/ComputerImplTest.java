package hr.fer.zemris.java.tecaj_2.jcomp.impl.test;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.impl.ComputerImpl;

import org.junit.Assert;
import org.junit.Test;

public class ComputerImplTest {

	@Test
	public void test() {
		Computer c = new ComputerImpl(16, 256);
		
		Assert.assertNotNull(c);
		Assert.assertNotNull(c.getMemory());
		Assert.assertNotNull(c.getRegisters());
	}
}
