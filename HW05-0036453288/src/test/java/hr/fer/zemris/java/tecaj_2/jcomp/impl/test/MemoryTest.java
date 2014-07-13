package hr.fer.zemris.java.tecaj_2.jcomp.impl.test;


import hr.fer.zemris.java.tecaj_2.jcomp.Memory;
import hr.fer.zemris.java.tecaj_2.jcomp.impl.MemoryImpl;

import org.junit.Assert;
import org.junit.Test;

public class MemoryTest {

	@Test(expected=IllegalArgumentException.class)
	public void illegalArgumentConstructorTest() {
		new MemoryImpl(-1);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void getIndexOutOfBoundTest() {
		Memory m = new MemoryImpl(10);
		m.getLocation(10);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void getIndexOutOfBoundNegativeIndexTest() {
		Memory m = new MemoryImpl(10);
		m.getLocation(-1);
	}
	
	@Test(expected=NullPointerException.class)
	public void setNullPointerTest() {
		Memory m = new MemoryImpl(10);
		m.setLocation(0, null);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void setIndexOutOfBoundTest() {
		Memory m = new MemoryImpl(10);
		m.setLocation(10, new String("test"));
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void setIndexOutOfBoundNegativeIndexTest() {
		Memory m = new MemoryImpl(10);
		m.setLocation(-1, new String("test"));
	}
	
	@Test
	public void memoryTest() {
		Memory m = new MemoryImpl(4);
		m.setLocation(1, new String("test"));
		Assert.assertEquals(new String("test"), m.getLocation(1));
	}
}
