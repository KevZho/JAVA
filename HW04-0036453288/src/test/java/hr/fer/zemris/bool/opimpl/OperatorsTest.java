package hr.fer.zemris.bool.opimpl;

import hr.fer.zemris.bool.BooleanOperator;
import hr.fer.zemris.bool.BooleanSource;
import hr.fer.zemris.bool.BooleanValue;
import hr.fer.zemris.bool.BooleanVariable;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class OperatorsTest {

	@Test(expected=IllegalArgumentException.class)
	public void orEmptyTest() {
		new BooleanOperatorOR(new ArrayList<BooleanSource>());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void andEmptyTest() {
		new BooleanOperatorOR(new ArrayList<BooleanSource>());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void notEmptyTest() {
		new BooleanOperatorOR(new ArrayList<BooleanSource>());
	}
	
	@Test
	public void oneOperandORTest() {
		BooleanVariable varA = new BooleanVariable("A");
		List<BooleanSource> list = new ArrayList<>();
		list.add(varA);
		BooleanOperator opr =  new BooleanOperatorOR(list);
		
		Assert.assertEquals(BooleanValue.FALSE, opr.getValue());
	}
	
	@Test
	public void oneOperandANDTest() {
		BooleanVariable varA = new BooleanVariable("A");
		List<BooleanSource> list = new ArrayList<>();
		list.add(varA);
		BooleanOperator opr =  new BooleanOperatorAND(list);
		
		Assert.assertEquals(BooleanValue.FALSE, opr.getValue());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void BooleanOperatorNullPointer() {
		new BooleanOperatorAND(null);
	}
}
