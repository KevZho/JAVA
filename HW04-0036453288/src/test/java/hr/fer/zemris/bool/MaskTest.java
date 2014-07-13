package hr.fer.zemris.bool;

import org.junit.Assert;
import org.junit.Test;

public class MaskTest {

	@Test
	public void fromIndexTest() {
		Mask m3 = Mask.fromIndex(5, 11);
		
		Assert.assertEquals(MaskValue.ZERO, m3.getValue(0));
		Assert.assertEquals(MaskValue.ONE, m3.getValue(1));
		Assert.assertEquals(MaskValue.ZERO, m3.getValue(2));
		Assert.assertEquals(MaskValue.ONE, m3.getValue(3));
		Assert.assertEquals(MaskValue.ONE, m3.getValue(4));
	}
	
	@Test
	public void parseTest() {
		Mask m2 = Mask.parse("0x1x");
		
		Assert.assertEquals(MaskValue.ZERO, m2.getValue(0));
		Assert.assertEquals(MaskValue.DONT_CARE, m2.getValue(1));
		Assert.assertEquals(MaskValue.ONE, m2.getValue(2));
		Assert.assertEquals(MaskValue.DONT_CARE, m2.getValue(3));
	}
	
	@Test
	public void numberOfZerosTest() {
		Mask m2 = Mask.parse("0x1x0");
		Assert.assertEquals(2, m2.getNumberOfZeros());
	}
	
	@Test
	public void numberOfZerosOnes() {
		Mask m2 = Mask.parse("0x1x0111");
		Assert.assertEquals(4, m2.getNumberOfOnes());
	}
	
	@Test
	public void sizeTest() {
		Mask m2 = Mask.parse("000111");
		Assert.assertEquals(6, m2.getSize());
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void getValueIndexSizeTest() {
		Mask m2 = Mask.parse("111");
		m2.getValue(3);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void negativeIndexTets() {
		Mask m2 = Mask.parse("111");
		m2.getValue(-1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void emptyStringParseTest() {
		Mask.parse("");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void IllegalCharTest() {
		Mask.parse("z");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void WrongSizeFromIndexTest() {
		Mask.fromIndex(2, 5);
	}
	
	@Test
	public void combineTest() {
		Mask value = Mask.combine(Mask.parse("0x00"), Mask.parse("0x01"));
		
		Assert.assertEquals(MaskValue.ZERO, value.getValue(0));
		Assert.assertEquals(MaskValue.DONT_CARE, value.getValue(1));
		Assert.assertEquals(MaskValue.ZERO, value.getValue(2));
		Assert.assertEquals(MaskValue.DONT_CARE, value.getValue(3));
	}
	
	@Test
	public void combineTest1() {
		Mask value = Mask.combine(Mask.parse("0x0x"), Mask.parse("0x01"));
		
		Assert.assertEquals(null, value);
	}
	
	@Test
	public void moreGeneralTest() {
		Mask value = Mask.parse("10x1x");
		boolean moreGeneral = value.isMoreGeneralThan(Mask.parse("x0110"));
		
		Assert.assertFalse(moreGeneral);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nullReferenceInMaskTest() {
		MaskValue[] array = {null, MaskValue.ONE, null };
		new Mask(array);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void fromIndexNegativeTest() {
		Mask.fromIndex(2, -1);
	}
	
	@Test
	public void isMoreGeneralNullTest() {
		Mask m = Mask.parse("11x");
		Assert.assertFalse(m.isMoreGeneralThan(null));
	}
	
	@Test
	public void isMoreGeneralSameTest() {
		Mask m = Mask.parse("11x");
		Assert.assertFalse(m.isMoreGeneralThan(Mask.parse("11x")));
	}
	
	@Test
	public void isMoreGeneralLengthTest() {
		Mask m = Mask.parse("11x");
		Assert.assertFalse(m.isMoreGeneralThan(Mask.parse("11")));
	}

	@Test
	public void isMoreGeneralFalseTest() {
		Mask m = Mask.parse("11x");
		Assert.assertFalse(m.isMoreGeneralThan(Mask.parse("1x0")));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void combineNullTest() {
		Mask.combine(Mask.parse("11x"), null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void combineNullTest1() {
		Mask.combine(null, Mask.parse("11x"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void combineLengthTest1() {
		Mask.combine(Mask.parse("11"), Mask.parse("11x"));
	}
	
	@Test
	public void combineTest3() {
		Assert.assertEquals(null, Mask.combine(Mask.parse("x0"), Mask.parse("xx")));
	}
	
	@Test
	public void combineTest4() {
		Assert.assertEquals(null, Mask.combine(Mask.parse("xxx"), Mask.parse("xxx")));
	}
	
	@Test
	public void moreGeneralTrueTest() {
		Assert.assertTrue(Mask.parse("x000").isMoreGeneralThan(Mask.parse("1000")));
	}
}
