package hr.fer.zemris.java.tecaj.hw3;

import static java.lang.Math.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * Razred <code>ComplexNumberTest</code> sadrži unit testove za
 * provjeru razreda <code>CompleNumber</code>.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class ComplexNumberTest {

	@Test
	public void defaultConstructorTest() {
		ComplexNumber c1 = new ComplexNumber(3, 4);
		
		Assert.assertTrue(abs(c1.getReal() - 3) < 1E-8);
		Assert.assertTrue(abs(c1.getImaginary() - 4) < 1E-8);
	}
	
	@Test
	public void fromRealTest() {
		ComplexNumber c1 = ComplexNumber.fromReal(5);
		
		Assert.assertTrue(abs(c1.getReal()) - 5 < 1E-8);
		Assert.assertTrue(abs(c1.getImaginary() - 0) < 1E-8);
	}
	
	@Test
	public void fromImaginaryTest() {
		ComplexNumber c1 = ComplexNumber.fromImaginary(5.12);
		
		Assert.assertTrue(abs(c1.getReal()) - 0 < 1E-8);
		Assert.assertTrue(abs(c1.getImaginary() - 5.12) < 1E-8);
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void negativeMagnitude() {
		ComplexNumber.fromMagnitudeAndAngle(-1, 0.12);
	}
	
	@Test
	public void fromMagnitudeTest() {
		ComplexNumber c1 = ComplexNumber.fromMagnitudeAndAngle(5, Math.atan2(4, 3));
	
		Assert.assertTrue(abs(c1.getReal() - 3) < 1E-8);
		Assert.assertTrue(abs(c1.getImaginary() - 4) < 1E-8);
	}
	
	@Test
	public void parseTestOnlyReal() {
		ComplexNumber c1 = ComplexNumber.parse("12.123");
		Assert.assertTrue(abs(c1.getReal() - 12.123) < 1E-8);
		Assert.assertTrue(abs(c1.getImaginary() - 0) < 1E-8);
	}
	
	@Test
	public void parseTestOnlyImaginary() {
		ComplexNumber c2 = ComplexNumber.parse("42.785i");
		Assert.assertTrue(abs(c2.getReal() - 0) < 1E-8);
		Assert.assertTrue(abs(c2.getImaginary() - 42.785) < 1E-8);
	}
	
	@Test
	public void parseTestOnlyI() {
		ComplexNumber c3 = ComplexNumber.parse("i");
		Assert.assertTrue(abs(c3.getReal() - 0) < 1E-8);
		Assert.assertTrue(abs(c3.getImaginary() - 1) < 1E-8);
	}
	
	@Test
	public void parseTestOnlyMinusI() {
		ComplexNumber c4 = ComplexNumber.parse("-i");
		Assert.assertTrue(abs(c4.getReal() - 0) < 1E-8);
		Assert.assertTrue(abs(c4.getImaginary() + 1) < 1E-8);
	}
	
	@Test
	public void parseTestAPlusBi() {
		ComplexNumber c5 = ComplexNumber.parse("127-255i");
		Assert.assertTrue(abs(c5.getReal() - 127) < 1E-8);
		Assert.assertTrue(abs(c5.getImaginary() + 255) < 1E-8);
	}
	

	@Test(expected=IllegalArgumentException.class)
	public void failParseTest() {
		ComplexNumber.parse("12a");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void failParseTestEmpyString() {
		ComplexNumber.parse("");
	}
	
	@Test
	public void addTest() {
		ComplexNumber c1 = new ComplexNumber(8, 6);
		ComplexNumber c2 = new ComplexNumber(3, 2);
		
		ComplexNumber c3 = c1.add(c2);
		Assert.assertTrue(abs(c3.getReal()) - 11 < 1E-8);
		Assert.assertTrue(abs(c3.getImaginary() - 8) < 1E-8);
	}
	
	@Test
	public void subTest() {
		ComplexNumber c1 = new ComplexNumber(8, 6);
		ComplexNumber c2 = new ComplexNumber(3, 2);
		
		ComplexNumber c3 = c1.sub(c2);
		Assert.assertTrue(abs(c3.getReal() - 5) < 1E-8);
		Assert.assertTrue(abs(c3.getImaginary() - 4) < 1E-8);
	}
	
	@Test
	public void mulTest() {
		ComplexNumber c1 = new ComplexNumber(2, 2);
		ComplexNumber c2 = new ComplexNumber(3, -2);
		
		ComplexNumber c3 = c1.mul(c2);
		Assert.assertTrue(abs(c3.getReal() - 10) < 1E-8);
		Assert.assertTrue(abs(c3.getImaginary() - 2) < 1E-8);
	}
	
	@Test
	public void divlTest() {
		ComplexNumber c1 = new ComplexNumber(2, 2);
		ComplexNumber c2 = new ComplexNumber(3, -2);
		
		ComplexNumber c3 = c1.div(c2);
		Assert.assertTrue(abs(c3.getReal() - 2.0/13) < 1E-8);
		Assert.assertTrue(abs(c3.getImaginary() - 10.0/13) < 1E-8);
	}
	
	@Test(expected=ArithmeticException.class)
	public void divFailTest() {
		new ComplexNumber(2, 2).div(new ComplexNumber(0, 0));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void failPowerTest() {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		c1.power(-1);
	}
	
	@Test
	public void powerZeroTest() {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		ComplexNumber c2 = c1.power(0);
		Assert.assertTrue(abs(c2.getReal() - 1) < 1E-8);
		Assert.assertTrue(abs(c2.getImaginary() - 0) < 1E-8);
	}
	
	@Test
	public void powerTest() {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		ComplexNumber c2 = c1.power(4);
		Assert.assertTrue(abs(c2.getReal() + 119) < 1E-8);
		Assert.assertTrue(abs(c2.getImaginary() + 120) < 1E-8);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void failRootTest() {
		ComplexNumber c1 = new ComplexNumber(3, 3);
		c1.root(-1);
	}
	
	@Test
	public void firstRootTest() {
		ComplexNumber c1 = new ComplexNumber(3, 3);
		ComplexNumber c2 = c1.root(1)[ 0 ];
		
		Assert.assertTrue(abs(c2.getReal() - 3) < 1E-8);
		Assert.assertTrue(abs(c2.getImaginary() - 3) < 1E-8);
	}
	
	@Test
	public void rootTest() {
		ComplexNumber c1 = new ComplexNumber(1, 0);
		ComplexNumber c2 = c1.root(6)[ 4 ];
		
		Assert.assertTrue(abs(c2.getReal() + 1.0/2) < 1E-8);
		Assert.assertTrue(abs(c2.getImaginary() + sqrt(3)/2.0) < 1E-8);
	}
	
	@Test
	public void testToString() {
		ComplexNumber c1 = new ComplexNumber(2, 2);
		Assert.assertTrue(c1.toString().equals("2.0+2.0i"));
	}
	
	@Test
	public void testToString2() {
		ComplexNumber c1 = new ComplexNumber(4.12, -12.5);
		Assert.assertTrue(c1.toString().equals("4.12-12.5i"));
	}
	
	public void testToStringZero() {
		ComplexNumber c1 = new ComplexNumber(0, 0);
		Assert.assertEquals(c1.toString(), "0.0+0.0i");
	}
		
	@Test(expected=ArithmeticException.class)
	public void bigFailTest() {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		ComplexNumber c2 = ComplexNumber.parse("2.5-3i");
		ComplexNumber c3 = c1.add(ComplexNumber.fromMagnitudeAndAngle(2, 1.57))
		.div(c2).power(3).root(2)[1].add(new ComplexNumber(1.234, 2.132231))
		.power(2).sub(new ComplexNumber(4, 2)).div(new ComplexNumber(0, 0));
		
		System.out.println(c3);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nullPointerAddTest() {
		new ComplexNumber(2,3).add(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nullPointerSubTest() {
		new ComplexNumber(2,3).sub(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nullPointerMulTest() {
		new ComplexNumber(2,3).mul(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nullPointerDivTest() {
		new ComplexNumber(2,3).div(null);
	}
}
