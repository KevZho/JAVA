package hr.fer.zemris.java.custom.scripting.exec;


import org.junit.Assert;
import org.junit.Test;


public class ValueWrapperTest {
	private ValueWrapper wrapper = new ValueWrapper(new Integer(123));

	@Test
	public void getValueTest() {
		Assert.assertEquals("Vrijednost nije new Integer(123)", new Integer(123), wrapper.getValue());
	}
	
	@Test
	public void setvalueTest() {
		wrapper.setValue("test");
		Assert.assertEquals("Vrijednost nije string \"test\"", "test", wrapper.getValue());
	}
	
	@Test
	public void nullValueIncrementWithIntegerTest() {
		wrapper.setValue(null);
		wrapper.increment(5);
		Assert.assertEquals("Nije pohranjena vrijednost 5", Integer.valueOf(5), wrapper.getValue());
	}
	
	@Test
	public void nullValueIncrementWithDoubleTest() {
		wrapper.setValue(null);
		wrapper.increment(5.0);
		Assert.assertEquals("Nije pohranjena vrijednost 5.0", Double.valueOf(5), wrapper.getValue());
	}
	
	@Test
	public void nullValueIncremntWithStringIntegerTest() {
		wrapper.setValue(null);
		wrapper.increment("123");
		Assert.assertEquals("Nije pohranjena vrijednost 123", Integer.valueOf(123), wrapper.getValue());
	}
	
	@Test
	public void nullValueIncremntWithStringDoubleTest() {
		wrapper.setValue(null);
		wrapper.increment("123.0");
		Assert.assertEquals("Nije pohranjena vrijednost 123.0", Double.valueOf(123), wrapper.getValue());
	}
	
	@Test
	public void nullValueIncremntWithStringDoubleExponentialTest() {
		wrapper.setValue(null);
		wrapper.increment("1.0E6");
		Assert.assertEquals("Nije pohranjena vrijednost 1.0E6", Double.valueOf(1.0E6), wrapper.getValue());
	}
	
	@Test
	public void IntegerValueIncrementWithNullTest() {
		wrapper.setValue(10);
		wrapper.increment(null);
		Assert.assertEquals("Nije pohranjena vrijednost 10", Integer.valueOf(10), wrapper.getValue());
	}
	
	@Test
	public void IntegerValueIncrementWithIntegerTest() {
		wrapper.setValue(10);
		wrapper.increment(10);
		Assert.assertEquals("Nije pohranjena vrijednost 20", Integer.valueOf(20), wrapper.getValue());
	}
	
	@Test
	public void IntegerValueIncrementWithDoubleTest() {
		wrapper.setValue(10);
		wrapper.increment(10.0);
		Assert.assertEquals("Nije pohranjena vrijednost 20.0", Double.valueOf(20), wrapper.getValue());
	}
	
	@Test
	public void IntegerValueIncrementWithStringIntegerTest() {
		wrapper.setValue(10);
		wrapper.increment("10");
		Assert.assertEquals("Nije pohranjena vrijednost 20.0", Integer.valueOf(20), wrapper.getValue());
	}
	
	@Test
	public void IntegerValueIncrementStringDoubleTest() {
		wrapper.setValue(10);
		wrapper.increment("10.0");
		Assert.assertEquals("Nije pohranjena vrijednost 20.0", Double.valueOf(20), wrapper.getValue());
	}
	
	@Test
	public void IntegerValueIncrementWithStringDoubleExponentialTest() {
		wrapper.setValue(10);
		wrapper.increment("1E6");
		Assert.assertEquals("Nije pohranjena vrijednost 10 + 1E6", Double.valueOf(10 + 1E6), wrapper.getValue());
	}
	
	@Test
	public void DoubleValueIncrementWithNullTest() {
		wrapper.setValue(10.0);
		wrapper.increment(null);
		Assert.assertEquals("Nije pohranjena vrijednost 10.0", Double.valueOf(10), wrapper.getValue());
	}
	
	@Test
	public void DoubleValueIncrementWithIntegerTest() {
		wrapper.setValue(10.0);
		wrapper.increment(10);
		Assert.assertEquals("Nije pohranjena vrijednost 20.0", Double.valueOf(20), wrapper.getValue());
	}
	
	@Test
	public void DoubleValueIncrementWithDoubleTest() {
		wrapper.setValue(10.0);
		wrapper.increment(10.0);
		Assert.assertEquals("Nije pohranjena vrijednost 20.0", Double.valueOf(20), wrapper.getValue());
	}
	
	@Test
	public void DoubleValueIncrementWithStringIntegerTest() {
		wrapper.setValue(10.0);
		wrapper.increment("10");
		Assert.assertEquals("Nije pohranjena vrijednost 20.0", Double.valueOf(20), wrapper.getValue());
	}
	
	@Test
	public void DoubleValueIncrementWithStringDoubleTest() {
		wrapper.setValue(10.0);
		wrapper.increment("10.0");
		Assert.assertEquals("Nije pohranjena vrijednost 20.0", Double.valueOf(20), wrapper.getValue());
	}
	
	@Test
	public void DoubleValueIncrementWithStringDoubleExponentialTest() {
		wrapper.setValue(10.0);
		wrapper.increment("1E6");
		Assert.assertEquals("Nije pohranjena vrijednost 10.0 + 1E6", Double.valueOf(10 + 1E6), wrapper.getValue());
	}
	
	@Test(expected=RuntimeException.class)
	public void IntegerValueIncrementWithArrayTest() {
		wrapper.setValue(123);
		wrapper.increment(new byte[] {10, 20});
	}
	
	@Test(expected=RuntimeException.class)
	public void IntegerValueIncrementWithIllegalStringTest() {
		wrapper.setValue(123);
		wrapper.increment("ab");
	}
	
	@Test
	public void IntegerValuedecrementWithIntegerTest() {
		wrapper.setValue(10);
		wrapper.decrement(1);
		Assert.assertEquals("Nije pohranjena vrijednost 9", Integer.valueOf(9), wrapper.getValue());
	}
	
	@Test
	public void IntegerValueDecrementWithDoubleTest() {
		wrapper.setValue(10);
		wrapper.decrement(1.0);
		Assert.assertEquals("Nije pohranjena vrijednost 9", Double.valueOf(9), wrapper.getValue());
	}
	
	@Test
	public void IntegerValueMulWithIntegerTest() {
		wrapper.setValue(10);
		wrapper.multiply(10);
		Assert.assertEquals("Nije pohranjena vrijednost 100", Integer.valueOf(100), wrapper.getValue());
	}
	
	@Test
	public void IntegerValueMulWithDoubleTest() {
		wrapper.setValue(10);
		wrapper.multiply(10.0);
		Assert.assertEquals("Nije pohranjena vrijednost 100", Double.valueOf(100), wrapper.getValue());
	}
	
	@Test
	public void IntegerValueDivWithIntegerTest() {
		wrapper.setValue(10);
		wrapper.divide(10);
		Assert.assertEquals("Nije pohranjena vrijednost 100", Integer.valueOf(1), wrapper.getValue());
	}
	
	@Test
	public void IntegerValueDivWithDoubleTest() {
		wrapper.setValue(10);
		wrapper.divide(10.0);
		Assert.assertEquals("Nije pohranjena vrijednost 100", Double.valueOf(1), wrapper.getValue());
	}
	
	@Test
	public void nullCompareWithNull() {
		wrapper.setValue(null);
		Assert.assertEquals("Vrijednost usporedbe nije 0.", 0, wrapper.numCompare(null));
	}
	
	@Test
	public void nullCompareWithInteger() {
		wrapper.setValue(null);
		Assert.assertTrue("Vrijednost usporedbe nije < 0", wrapper.numCompare(12) < 0);
	}
	
	@Test
	public void integerCompareWithNull() {
		wrapper.setValue(12);
		Assert.assertTrue("Vrijednost usporedbe nije > 0", wrapper.numCompare(null) > 0);
	}
	
	@Test
	public void nullCompareWithDouble() {
		wrapper.setValue(null);
		Assert.assertTrue("Vrijednost usporedbe nije < 0", wrapper.numCompare(12.0) < 0);
	}
	
	@Test
	public void DoubleCompareWithNull() {
		wrapper.setValue(12.0);
		Assert.assertTrue("Vrijednost usporedbe nije > 0", wrapper.numCompare(null) > 0);
	}
}
