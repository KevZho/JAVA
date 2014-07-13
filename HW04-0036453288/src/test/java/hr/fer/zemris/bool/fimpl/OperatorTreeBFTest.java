package hr.fer.zemris.bool.fimpl;

import hr.fer.zemris.bool.BooleanConstant;
import hr.fer.zemris.bool.BooleanFunction;
import hr.fer.zemris.bool.BooleanOperator;
import hr.fer.zemris.bool.BooleanVariable;
import hr.fer.zemris.bool.opimpl.BooleanOperators;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class OperatorTreeBFTest {

	private BooleanFunction createFunction() {
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		BooleanOperator izraz1 = BooleanOperators.or(
				BooleanConstant.FALSE,
				varC,
				BooleanOperators.and(varA, BooleanOperators.not(varB))
		);
		
		BooleanFunction f1 = new OperatorTreeBF(
				"f1",
				Arrays.asList(varA, varB, varC),
				izraz1);
		
		return f1;
	}
	
	@Test
	public void mintermMaxtermDontcareTest() {
		BooleanFunction f1 = createFunction();
		
		Assert.assertEquals(true, f1.hasMinterm(4));
		Assert.assertEquals(true, f1.hasMaxterm(0));
		Assert.assertEquals(false, f1.hasMinterm(0));
		Assert.assertEquals(false, f1.hasMaxterm(4));
		Assert.assertEquals(false, f1.hasMinterm(8));
		
		Assert.assertEquals("f1", f1.getName());
		Assert.assertEquals(3, f1.getDomain().size());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nullPointerConstructorTest() {
		new OperatorTreeBF("f1", Arrays.asList(new BooleanVariable("A")), null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nullPointerConstructorTest2() {
		new OperatorTreeBF("f1", null, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void emptyName() {
		new OperatorTreeBF("", null, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void illegalDomainTest() {
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanOperator izraz1 = BooleanOperators.or(
				BooleanConstant.TRUE,
				BooleanOperators.and(varA, BooleanOperators.not(varB))
		);
		
		new OperatorTreeBF(
			"f1",
			Arrays.asList(varA),
			izraz1
		);
	}
	
	@Test
	public void iterableTest() {
		BooleanFunction f1 = createFunction();
		Assert.assertNotEquals(null, f1.mintermIterable());
		Assert.assertNotEquals(null, f1.maxtermIterable());
		Assert.assertNotEquals(null, f1.dontcareIterable());
	}
}
