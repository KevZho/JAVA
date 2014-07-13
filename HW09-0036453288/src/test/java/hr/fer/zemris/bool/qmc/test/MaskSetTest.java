package hr.fer.zemris.bool.qmc.test;

import hr.fer.zemris.bool.Mask;
import hr.fer.zemris.bool.qmc.MaskSet;

import org.junit.Assert;
import org.junit.Test;

public class MaskSetTest {

	@Test
	public void unionTest() {
		MaskSet set1 = new MaskSet(Mask.parse("0x0x"));
		MaskSet set2 = new MaskSet(Mask.parse("1x0x"));
		set1.add(set2);
		Assert.assertEquals("Nisu unutra dva elementa.",2, set1.getSize());
	}

	@Test
	public void unionSameTest() {
		MaskSet set1 = new MaskSet(Mask.parse("0x0x"));
		MaskSet set2 = new MaskSet(set1);
		Assert.assertEquals("Nije unutra jedna element.",1, set2.getSize());
	}
	
	@Test
	public void equals1Test() {
		MaskSet set1 = new MaskSet(Mask.parse("0x0x"));
		Assert.assertFalse(set1.equals(null));
		Assert.assertFalse(set1.equals(Integer.valueOf(1)));
		MaskSet set2 = new MaskSet(Mask.parse("0x0x"));
		Assert.assertTrue(set1.equals(set2));
	}
	
	@Test
	public void equals2Test() {
		MaskSet set1 = new MaskSet(Mask.parse("0x0x"));
		Assert.assertFalse(set1.equals(Integer.valueOf(1)));
		MaskSet set2 = new MaskSet(Mask.parse("0x0x"));
		Assert.assertTrue(set1.equals(set2));
	}
	
	@Test
	public void equals3Test() {
		MaskSet set1 = new MaskSet(Mask.parse("0x0x"));
		MaskSet set2 = new MaskSet(Mask.parse("0x0x"));
		Assert.assertTrue(set1.equals(set2));
		Assert.assertTrue(set1.equals(set1));
	}
}
