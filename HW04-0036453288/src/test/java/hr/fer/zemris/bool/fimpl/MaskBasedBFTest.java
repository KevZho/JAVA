package hr.fer.zemris.bool.fimpl;

import hr.fer.zemris.bool.BooleanFunction;
import hr.fer.zemris.bool.BooleanValue;
import hr.fer.zemris.bool.BooleanVariable;
import hr.fer.zemris.bool.Masks;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class MaskBasedBFTest {

	private BooleanFunction createFunction(Boolean minterm) {
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		BooleanVariable varD = new BooleanVariable("D");
		
		
		BooleanFunction f1 = new MaskBasedBF(
				"f1",
				Arrays.asList(varA, varB, varC, varD),
				minterm,
				Masks.fromStrings("00x0", "1xx1"),
				Masks.fromStrings("10x0")
		);
		
		return f1;
	}
	
	@Test
	public void iterableTest() {
		BooleanFunction f1 = createFunction(true);
		
		Assert.assertEquals("f1", f1.getName());
		Assert.assertEquals(4, f1.getDomain().size());
		Assert.assertNotEquals(null, f1.dontcareIterable());
		Assert.assertNotEquals(null, f1.mintermIterable());
		Assert.assertNotEquals(null, f1.maxtermIterable());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void emptyNameTest() {
		new MaskBasedBF(
				"",
				null,
				true,
				null,
				null
		);
	}

	@Test(expected=IllegalArgumentException.class)
	public void nullDomainTest() {
		new MaskBasedBF(
				"f1",
				null,
				true,
				null,
				null
		);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nullMasksTest() {
		new MaskBasedBF(
				"f1",
				Arrays.asList(new BooleanVariable("A")),
				true,
				null,
				null
		);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nullDontcaresTest() {
		new MaskBasedBF(
				"f1",
				Arrays.asList(new BooleanVariable("A")),
				true,
				Masks.fromStrings("00x0", "1xx1"),
				null
		);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void illegalMaskTest() {
		new MaskBasedBF(
				"f1",
				Arrays.asList(new BooleanVariable("A")),
				true,
				Masks.fromStrings("00x0", "1xx1"),
				Masks.fromStrings("0x1")
		);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void illegalMaskTest2() {
		new MaskBasedBF(
				"f1",
				Arrays.asList(new BooleanVariable("A")),
				true,
				Masks.fromStrings("0"),
				Masks.fromStrings("0x1")
		);
	}
	
	@Test
	public void mintermFunctionTest() {
		BooleanFunction f1 = createFunction(true);
		
		Assert.assertTrue(f1.hasMinterm(0));
		Assert.assertFalse(f1.hasMaxterm(0));
		Assert.assertFalse(f1.hasDontCare(0));
		
		Assert.assertFalse(f1.hasMinterm(3));
		Assert.assertTrue(f1.hasMaxterm(3));
		Assert.assertFalse(f1.hasDontCare(3));
		
		
		Assert.assertFalse(f1.hasMinterm(8));
		Assert.assertFalse(f1.hasMaxterm(8));
		Assert.assertTrue(f1.hasDontCare(8));
	}
	
	@Test
	public void maxtermFunctionTest() {
		BooleanFunction f1 = createFunction(false);
		
		Assert.assertTrue(f1.hasMinterm(3));
		Assert.assertFalse(f1.hasMaxterm(3));
		Assert.assertFalse(f1.hasDontCare(3));
		
		Assert.assertFalse(f1.hasMinterm(0));
		Assert.assertTrue(f1.hasMaxterm(0));
		Assert.assertFalse(f1.hasDontCare(0));
		
		
		Assert.assertFalse(f1.hasMinterm(8));
		Assert.assertFalse(f1.hasMaxterm(8));
		Assert.assertTrue(f1.hasDontCare(8));
		Assert.assertFalse(f1.hasDontCare(800));
	}
	
	@Test
	public void getValueMintermSumTest() {
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		BooleanVariable varD = new BooleanVariable("D");
		
		
		BooleanFunction f1 = new MaskBasedBF(
				"f1",
				Arrays.asList(varA, varB, varC, varD),
				true,
				Masks.fromStrings("00x0", "1xx1"),
				Masks.fromStrings("10x0")
		);
		
		Assert.assertEquals(BooleanValue.TRUE, f1.getValue());
		varA.setValue(BooleanValue.TRUE);
		Assert.assertEquals(BooleanValue.DONT_CARE, f1.getValue());
		
		varA.setValue(BooleanValue.FALSE);
		varC.setValue(BooleanValue.TRUE);
		varD.setValue(BooleanValue.TRUE);
		Assert.assertEquals(BooleanValue.FALSE, f1.getValue());
		
		varB.setValue(BooleanValue.DONT_CARE);
		varA.setValue(BooleanValue.TRUE);
		varC.setValue(BooleanValue.FALSE);
		varD.setValue(BooleanValue.TRUE);
		Assert.assertEquals(BooleanValue.TRUE, f1.getValue());
	}
	
	@Test
	public void getValueMaxtermProduct() {
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		BooleanVariable varD = new BooleanVariable("D");
		
		
		BooleanFunction f1 = new MaskBasedBF(
				"f1",
				Arrays.asList(varA, varB, varC, varD),
				false,
				Masks.fromStrings("00x0", "1xx1"),
				Masks.fromStrings("10x0")
		);
		
		Assert.assertEquals(BooleanValue.FALSE, f1.getValue());
		
		varA.setValue(BooleanValue.TRUE);
		Assert.assertEquals(BooleanValue.DONT_CARE, f1.getValue());
		
		varA.setValue(BooleanValue.FALSE);
		varC.setValue(BooleanValue.TRUE);
		varD.setValue(BooleanValue.TRUE);
		Assert.assertEquals(BooleanValue.TRUE, f1.getValue());
		
		varA.setValue(BooleanValue.DONT_CARE);
		varB.setValue(BooleanValue.DONT_CARE);
		Assert.assertEquals(BooleanValue.FALSE, f1.getValue());
	}
}
