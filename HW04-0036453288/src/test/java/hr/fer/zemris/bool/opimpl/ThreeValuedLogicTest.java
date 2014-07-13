package hr.fer.zemris.bool.opimpl;

import hr.fer.zemris.bool.BooleanValue;

import org.junit.Assert;
import org.junit.Test;

public class ThreeValuedLogicTest {

	@Test
	public void notTest() {
		BooleanValue value = BooleanValue.TRUE;
		Assert.assertEquals(BooleanValue.FALSE, 
				ThreeValuedLogic.OPERATION.not(value));
		
		value = BooleanValue.FALSE;
		Assert.assertEquals(BooleanValue.TRUE, 
				ThreeValuedLogic.OPERATION.not(value));
		
		value = BooleanValue.DONT_CARE;
		Assert.assertEquals(BooleanValue.DONT_CARE, 
				ThreeValuedLogic.OPERATION.not(value));
	}
	
	@Test
	public void andTest() {
		BooleanValue first = BooleanValue.TRUE;
		BooleanValue second = BooleanValue.TRUE;
		Assert.assertEquals(BooleanValue.TRUE, 
				ThreeValuedLogic.OPERATION.and(first, second));
		
		first = BooleanValue.FALSE;
		second = BooleanValue.TRUE;
		Assert.assertEquals(BooleanValue.FALSE, 
				ThreeValuedLogic.OPERATION.and(first, second));
		
		first = BooleanValue.TRUE;
		second = BooleanValue.FALSE;
		Assert.assertEquals(BooleanValue.FALSE, 
				ThreeValuedLogic.OPERATION.and(first, second));
		
		first = BooleanValue.FALSE;
		second = BooleanValue.FALSE;
		Assert.assertEquals(BooleanValue.FALSE, 
				ThreeValuedLogic.OPERATION.and(first, second));
		
		first = BooleanValue.FALSE;
		second = BooleanValue.DONT_CARE;
		Assert.assertEquals(BooleanValue.FALSE, 
				ThreeValuedLogic.OPERATION.and(first, second));
		
		first = BooleanValue.DONT_CARE;
		second = BooleanValue.FALSE;
		Assert.assertEquals(BooleanValue.FALSE, 
				ThreeValuedLogic.OPERATION.and(first, second));
		
		first = BooleanValue.TRUE;
		second = BooleanValue.DONT_CARE;
		Assert.assertEquals(BooleanValue.DONT_CARE, 
				ThreeValuedLogic.OPERATION.and(first, second));
		
		first = BooleanValue.DONT_CARE;
		second = BooleanValue.TRUE;
		Assert.assertEquals(BooleanValue.DONT_CARE, 
				ThreeValuedLogic.OPERATION.and(first, second));
		
		
		first = BooleanValue.DONT_CARE;
		second = BooleanValue.DONT_CARE;
		Assert.assertEquals(BooleanValue.DONT_CARE, 
				ThreeValuedLogic.OPERATION.and(first, second));
	}
	
	@Test
	public void orTest() {
		BooleanValue first = BooleanValue.TRUE;
		BooleanValue second = BooleanValue.TRUE;
		Assert.assertEquals(BooleanValue.TRUE, 
				ThreeValuedLogic.OPERATION.or(first, second));
		
		first = BooleanValue.FALSE;
		second = BooleanValue.TRUE;
		Assert.assertEquals(BooleanValue.TRUE, 
				ThreeValuedLogic.OPERATION.or(first, second));
		
		first = BooleanValue.TRUE;
		second = BooleanValue.FALSE;
		Assert.assertEquals(BooleanValue.TRUE, 
				ThreeValuedLogic.OPERATION.or(first, second));
		
		first = BooleanValue.FALSE;
		second = BooleanValue.FALSE;
		Assert.assertEquals(BooleanValue.FALSE, 
				ThreeValuedLogic.OPERATION.or(first, second));
		
		first = BooleanValue.FALSE;
		second = BooleanValue.DONT_CARE;
		Assert.assertEquals(BooleanValue.DONT_CARE, 
				ThreeValuedLogic.OPERATION.or(first, second));
		
		first = BooleanValue.DONT_CARE;
		second = BooleanValue.FALSE;
		Assert.assertEquals(BooleanValue.DONT_CARE, 
				ThreeValuedLogic.OPERATION.or(first, second));
		
		first = BooleanValue.TRUE;
		second = BooleanValue.DONT_CARE;
		Assert.assertEquals(BooleanValue.TRUE, 
				ThreeValuedLogic.OPERATION.or(first, second));
		
		first = BooleanValue.DONT_CARE;
		second = BooleanValue.TRUE;
		Assert.assertEquals(BooleanValue.TRUE, 
				ThreeValuedLogic.OPERATION.or(first, second));
		
		
		first = BooleanValue.DONT_CARE;
		second = BooleanValue.DONT_CARE;
		Assert.assertEquals(BooleanValue.DONT_CARE, 
				ThreeValuedLogic.OPERATION.or(first, second));
	}
}
