package hr.fer.zemris.java.tecaj_2.jcomp.impl.test;

import hr.fer.zemris.java.tecaj_2.jcomp.Registers;
import hr.fer.zemris.java.tecaj_2.jcomp.impl.RegistersImpl;

import org.junit.Assert;
import org.junit.Test;

public class RegistersTest {

	@Test(expected=IllegalArgumentException.class)
	public void illegalArgumentConstructorTest() {
		new RegistersImpl(-1);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void getRegisterIndexOutOfBoundTest() {
		Registers r = new RegistersImpl(2);
		r.getRegisterValue(2);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void getRegisterIndexOutOfBoundNegativeIndexTest() {
		Registers r = new RegistersImpl(2);
		r.getRegisterValue(-1);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void setRegisterIndexOutOfBoundTest() {
		Registers r = new RegistersImpl(2);
		r.setRegisterValue(2, new String("test"));
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void setRegisterIndexOutOfBoundNegativeIndexTest() {
		Registers r = new RegistersImpl(2);
		r.setRegisterValue(-1, new String("test"));
	}
	
	@Test(expected=NullPointerException.class)
	public void setRegisterNullPointerTest() {
		Registers r = new RegistersImpl(2);
		r.setRegisterValue(0, null);
	}
	
	@Test
	public void getSetFlagTest() {
		Registers r = new RegistersImpl(2);
		Assert.assertFalse(r.getFlag());
		
		r.setFlag(true);
		Assert.assertTrue(r.getFlag());
		
		r.setFlag(false);
		Assert.assertFalse(r.getFlag());
	}
	
	@Test
	public void getSetIncrementPCTest() {
		Registers r = new RegistersImpl(2);
		Assert.assertEquals(0, r.getProgramCounter());
		
		r.incrementProgramCounter();
		Assert.assertEquals(1, r.getProgramCounter());
		
		r.setProgramCounter(10);
		Assert.assertEquals(10, r.getProgramCounter());
	}
	
	@Test
	public void getSetRegisterValue() {
		Registers r = new RegistersImpl(2);
		r.setRegisterValue(0, new String("test1"));
		r.setRegisterValue(1, new String("test2"));
		
		Assert.assertEquals(new String("test1"), r.getRegisterValue(0));
		Assert.assertEquals(new String("test2"), r.getRegisterValue(1));
	}
	
}
