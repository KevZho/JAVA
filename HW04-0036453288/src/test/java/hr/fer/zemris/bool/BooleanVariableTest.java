package hr.fer.zemris.bool;

import org.junit.Test;

public class BooleanVariableTest {

	@Test(expected=IllegalArgumentException.class)
	public void empyStringTest() {
		new BooleanVariable("");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void setValueNullReferenceTest() {
		BooleanVariable var = new BooleanVariable("A");
		var.setValue(null);
	}
}
