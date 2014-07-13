package hr.fer.zemris.bool.fimpl;

import hr.fer.zemris.bool.BooleanFunction;
import hr.fer.zemris.bool.BooleanValue;
import hr.fer.zemris.bool.BooleanVariable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class IndexedBFTest {
	
	private BooleanFunction createFunction(boolean minterm) {
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		BooleanFunction f1 = new IndexedBF(
				"f1",
				Arrays.asList(varB, varC, varA),
				minterm,
				Arrays.asList(3,5,6),
				Arrays.asList(1)
		);
		
		
		return f1;
	}
	
	@Test
	public void iterableTest() {
		BooleanFunction f1 = createFunction(true);
		
		Iterable<Integer> iterable = f1.mintermIterable();
		Assert.assertNotEquals(null, iterable);
		List<Integer> list = new ArrayList<>();
		
		for(Integer i : iterable) {
			list.add(i);
		}
		
		Assert.assertEquals(new Integer(3), list.get(0));
		Assert.assertEquals(new Integer(5), list.get(1));
		Assert.assertEquals(new Integer(6), list.get(2));
		
		iterable = f1.dontcareIterable();
		Assert.assertNotEquals(null, iterable);
		list.clear();
		
		for(Integer i : iterable) {
			list.add(i);
		}
		
		Assert.assertEquals(new Integer(1), list.get(0));
		Assert.assertNotEquals(null, f1.maxtermIterable());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void emptyNameTest() {
		new IndexedBF("", null, true, null, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nullDomainTest() {
		new IndexedBF("f1", null, true, null, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nullIndexesTest() {
		new IndexedBF("f1", Arrays.asList(new BooleanVariable("A")),
				true, null, null
		);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nullDontcaresTest() {
		new IndexedBF("f1", Arrays.asList(new BooleanVariable("A")),
				true, Arrays.asList(1,2),
				null
		);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void indexOverlapTest() {
		new IndexedBF("f1", 
				Arrays.asList(new BooleanVariable("A")),
				true,
				Arrays.asList(0,1),
				Arrays.asList(0)
		);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void bigIndexTest() {
		new IndexedBF("f1", Arrays.asList(new BooleanVariable("A")),
				true,
				Arrays.asList(0,1),
				Arrays.asList(3)
		);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void negativeIndexTest() {
		new IndexedBF("f1", Arrays.asList(new BooleanVariable("A")),
				true,
				Arrays.asList(0,1),
				Arrays.asList(-3)
		);
	}
	
	@Test
	public void mintermFunctionTest() {
		BooleanFunction f1 = createFunction(true);
		
		Assert.assertEquals("f1", f1.getName());
		Assert.assertEquals(3, f1.getDomain().size());
		
		Assert.assertEquals(true, f1.hasMinterm(5));
		Assert.assertEquals(false, f1.hasMaxterm(5));
		Assert.assertEquals(false, f1.hasDontCare(5));
		
		Assert.assertEquals(true, f1.hasMaxterm(2));
		Assert.assertEquals(false, f1.hasMinterm(2));
		Assert.assertEquals(false, f1.hasDontCare(2));

		Assert.assertEquals(true, f1.hasDontCare(1));
		Assert.assertEquals(false, f1.hasMaxterm(1));
		Assert.assertEquals(false, f1.hasMinterm(1));
	}
	
	@Test
	public void maxtermFunctionTest() {
		BooleanFunction f1 = createFunction(false);
	
		Assert.assertEquals(true, f1.hasMinterm(2));
		Assert.assertEquals(false, f1.hasMaxterm(2));
		Assert.assertEquals(false, f1.hasDontCare(2));
		
		Assert.assertEquals(true, f1.hasMaxterm(5));
		Assert.assertEquals(false, f1.hasMinterm(5));
		Assert.assertEquals(false, f1.hasDontCare(5));

		Assert.assertEquals(true, f1.hasDontCare(1));
		Assert.assertEquals(false, f1.hasMaxterm(1));
		Assert.assertEquals(false, f1.hasMinterm(1));
	}
	
	@Test
	public void getValueMintermSumTest() {
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		
		BooleanFunction f1 = new IndexedBF(
				"f1",
				Arrays.asList(varB, varC, varA),
				true,
				Arrays.asList(3,5,6),
				Arrays.asList(1)
		);
		
		Assert.assertEquals(BooleanValue.FALSE, f1.getValue());
		
		varA.setValue(BooleanValue.TRUE);
		Assert.assertEquals(BooleanValue.DONT_CARE, f1.getValue());
		
		varA.setValue(BooleanValue.FALSE);
		varC.setValue(BooleanValue.TRUE);
		varB.setValue(BooleanValue.TRUE);
		Assert.assertEquals(BooleanValue.TRUE, f1.getValue());
		
		varA.setValue(BooleanValue.DONT_CARE);
		Assert.assertEquals(BooleanValue.TRUE, f1.getValue());
	}
	
	@Test
	public void getValueMaxtermSumTest() {
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		
		BooleanFunction f1 = new IndexedBF(
				"f1",
				Arrays.asList(varB, varC, varA),
				false,
				Arrays.asList(3,5,6),
				Arrays.asList(1)
		);
		
		Assert.assertEquals(BooleanValue.TRUE, f1.getValue());
		
		varA.setValue(BooleanValue.TRUE);
		Assert.assertEquals(BooleanValue.DONT_CARE, f1.getValue());
		
		varB.setValue(BooleanValue.TRUE);
		Assert.assertEquals(BooleanValue.FALSE, f1.getValue());
		
		
		varC.setValue(BooleanValue.FALSE);
		varB.setValue(BooleanValue.DONT_CARE);
		Assert.assertEquals(BooleanValue.FALSE, f1.getValue());
	}
}
