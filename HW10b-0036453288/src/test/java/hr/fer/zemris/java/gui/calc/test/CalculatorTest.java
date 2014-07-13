package hr.fer.zemris.java.gui.calc.test;

import org.junit.Assert;
import org.junit.Test;

import hr.fer.zemris.java.gui.calc.CalculatorCore;
import hr.fer.zemris.java.gui.calc.core.KeyType;
import hr.fer.zemris.java.gui.calc.core.binary.OperatorAdd;
import hr.fer.zemris.java.gui.calc.core.binary.OperatorDiv;
import hr.fer.zemris.java.gui.calc.core.binary.OperatorMul;
import hr.fer.zemris.java.gui.calc.core.binary.OperatorNthPow;
import hr.fer.zemris.java.gui.calc.core.binary.OperatorNthRoot;
import hr.fer.zemris.java.gui.calc.core.binary.OperatorSub;
import hr.fer.zemris.java.gui.calc.core.unary.OperatorACos;
import hr.fer.zemris.java.gui.calc.core.unary.OperatorACtg;
import hr.fer.zemris.java.gui.calc.core.unary.OperatorASin;
import hr.fer.zemris.java.gui.calc.core.unary.OperatorATan;
import hr.fer.zemris.java.gui.calc.core.unary.OperatorCos;
import hr.fer.zemris.java.gui.calc.core.unary.OperatorCtg;
import hr.fer.zemris.java.gui.calc.core.unary.OperatorExp;
import hr.fer.zemris.java.gui.calc.core.unary.OperatorInverse;
import hr.fer.zemris.java.gui.calc.core.unary.OperatorLn;
import hr.fer.zemris.java.gui.calc.core.unary.OperatorLog;
import hr.fer.zemris.java.gui.calc.core.unary.OperatorPow10;
import hr.fer.zemris.java.gui.calc.core.unary.OperatorSin;
import hr.fer.zemris.java.gui.calc.core.unary.OperatorTan;

public class CalculatorTest {

	@Test
	public void initTest() {
		CalculatorCore core = new CalculatorCore();
		Assert.assertEquals("Rezultat nije 0", Double.valueOf(0), Double.valueOf(core.getDisplayValue()), 1E-8);
	}
	
	@Test
	public void numberInputTest() {
		CalculatorCore core = new CalculatorCore();
		core.keyPressed(null, null, "1", KeyType.NUMBER);
		core.keyPressed(null, null, "2", KeyType.NUMBER);
		core.keyPressed(null, null, "3", KeyType.NUMBER);
		Assert.assertEquals("Rezultat nije 123", Double.valueOf(123), Double.valueOf(core.getDisplayValue()), 1E-8);
	}
	
	@Test
	public void changeSignTest() {
		CalculatorCore core = new CalculatorCore();
		core.keyPressed(null, null, "1", KeyType.NUMBER);
		core.keyPressed(null, null, "2", KeyType.NUMBER);
		core.keyPressed(null, null, "3", KeyType.NUMBER);
		core.keyPressed(null, null, "+/-", KeyType.DOT_SIGN);
		Assert.assertEquals("Rezulat nije -123", Double.valueOf(-123), Double.valueOf(core.getDisplayValue()), 1E-8);
	}
	
	@Test
	public void emptyStackTest() {
		CalculatorCore core = new CalculatorCore();
		core.keyPressed(null, null, "pop", KeyType.PUSH_POP);
		Assert.assertEquals("Na ekranu nije -ERROR-", "-ERROR-", core.getDisplayValue());
	}
	
	@Test
	public void pushTest() {
		CalculatorCore core = new CalculatorCore();
		core.keyPressed(null, null, "1", KeyType.NUMBER);
		core.keyPressed(null, null, "2", KeyType.NUMBER);
		core.keyPressed(null, null, "push", KeyType.PUSH_POP);
		core.keyPressed(null, null, "2", KeyType.NUMBER);
		core.keyPressed(null, null, ".", KeyType.DOT_SIGN);
		core.keyPressed(null, null, ".", KeyType.DOT_SIGN);
		core.keyPressed(null, null, "1", KeyType.NUMBER);
		core.keyPressed(null, null, "+/-", KeyType.DOT_SIGN);
		core.keyPressed(null, null, "pop", KeyType.PUSH_POP);
		Assert.assertEquals("Na ekranu nije 12", Double.valueOf(12),  Double.valueOf(core.getDisplayValue()), 1E-8);
	}
	
	@Test
	public void binaryOpetorsTest() {
		CalculatorCore core = new CalculatorCore();
		core.keyPressed(null, null, "1", KeyType.NUMBER);
		core.keyPressed(new OperatorAdd(), null, "+", KeyType.BINARY);
		core.keyPressed(null, null, "2", KeyType.NUMBER);
		core.keyPressed(new OperatorSub(), null, "-", KeyType.BINARY);
		core.keyPressed(null, null, "1", KeyType.NUMBER);
		core.keyPressed(new OperatorMul(), null, "*", KeyType.BINARY);
		core.keyPressed(null, null, "4", KeyType.NUMBER);
		core.keyPressed(new OperatorDiv(), null, "/", KeyType.BINARY);
		core.keyPressed(null, null, "4", KeyType.NUMBER);
		core.keyPressed(null, null, "=", KeyType.BINARY);
		Assert.assertEquals("Na ekranu nije 2", Double.valueOf(2), Double.valueOf(core.getDisplayValue()));
	}
	
	@Test
	public void divisionByZeroTest() {
		CalculatorCore core = new CalculatorCore();
		core.keyPressed(null, null, "1", KeyType.NUMBER);
		core.keyPressed(new OperatorDiv(), null, "/", KeyType.BINARY);
		core.keyPressed(null, null, "0", KeyType.NUMBER);
		core.keyPressed(null, null, "=", KeyType.BINARY);
		Assert.assertEquals("Na zaslonu nije poruka greške.", "-ERROR-", core.getDisplayValue());
	}
	
	@Test
	public void ntRootPowTest() {
		CalculatorCore core = new CalculatorCore();
		core.keyPressed(null, null, "2", KeyType.NUMBER);
		core.keyPressed(new OperatorNthPow(), null, "x^n", KeyType.BINARY);
		core.keyPressed(null, null, "2", KeyType.NUMBER);
		core.keyPressed(null, null, "=", KeyType.BINARY);
		core.keyPressed(null, null, "Inv", KeyType.INVERT);
		core.keyPressed(new OperatorNthRoot(), null, "x^n", KeyType.BINARY);
		core.keyPressed(null, null, "2", KeyType.NUMBER);
		core.keyPressed(null, null, "=", KeyType.BINARY);
		Assert.assertEquals("Na zaslonu nije 2.", Double.valueOf(2), Double.valueOf(core.getDisplayValue()), 1E-8);
	}
	
	@Test
	public void sinTest() {
		CalculatorCore core = new CalculatorCore();
		core.keyPressed(null, null, "1", KeyType.NUMBER);
		core.keyPressed(null, null, ".", KeyType.DOT_SIGN);
		core.keyPressed(null, null, "2", KeyType.NUMBER);
		core.keyPressed(null, new OperatorSin(), "sin", KeyType.UNARY);
		Assert.assertEquals("Nije 0.9320391...", Double.valueOf(Math.sin(1.2)), Double.valueOf(core.getDisplayValue()), 1E-8);
	}
	
	@Test
	public void cosTest() {
		CalculatorCore core = new CalculatorCore();
		core.keyPressed(null, null, "1", KeyType.NUMBER);
		core.keyPressed(null, null, ".", KeyType.DOT_SIGN);
		core.keyPressed(null, null, "2", KeyType.NUMBER);
		core.keyPressed(null, new OperatorCos(), "cos", KeyType.UNARY);
		Assert.assertEquals("Nije 0.362357...", Double.valueOf(Math.cos(1.2)), Double.valueOf(core.getDisplayValue()), 1E-8);
	}
	
	@Test
	public void tanTest() {
		CalculatorCore core = new CalculatorCore();
		core.keyPressed(null, null, "1", KeyType.NUMBER);
		core.keyPressed(null, null, ".", KeyType.DOT_SIGN);
		core.keyPressed(null, null, "2", KeyType.NUMBER);
		core.keyPressed(null, new OperatorTan(), "tan", KeyType.UNARY);
		Assert.assertEquals("Nije 2.572151...", Double.valueOf(Math.tan(1.2)), Double.valueOf(core.getDisplayValue()), 1E-8);
	}
	
	@Test
	public void ctgTest() {
		CalculatorCore core = new CalculatorCore();
		core.keyPressed(null, null, "1", KeyType.NUMBER);
		core.keyPressed(null, null, ".", KeyType.DOT_SIGN);
		core.keyPressed(null, null, "2", KeyType.NUMBER);
		core.keyPressed(null, new OperatorCtg(), "ctg", KeyType.UNARY);
		Assert.assertEquals("Nije 0.38877...", Double.valueOf(1.0 / Math.tan(1.2)), Double.valueOf(core.getDisplayValue()), 1E-8);
	}
	
	@Test
	public void inverseTest() {
		CalculatorCore core = new CalculatorCore();
		core.keyPressed(null, null, "2", KeyType.NUMBER);
		core.keyPressed(null, new OperatorInverse(), "1/x", KeyType.UNARY);
		Assert.assertEquals("Nije 0.5...", Double.valueOf(1.0 / 2.0), Double.valueOf(core.getDisplayValue()), 1E-8);
	}
	
	@Test
	public void logTest() {
		CalculatorCore core = new CalculatorCore();
		core.keyPressed(null, null, "2", KeyType.NUMBER);
		core.keyPressed(null, new OperatorLog(), "log", KeyType.UNARY);
		Assert.assertEquals("Nije 0.310299...", Double.valueOf(Math.log10(2.0)), Double.valueOf(core.getDisplayValue()), 1E-8);
	}
	
	@Test
	public void lnTest() {
		CalculatorCore core = new CalculatorCore();
		core.keyPressed(null, null, "3", KeyType.NUMBER);
		core.keyPressed(null, new OperatorLn(), "ln", KeyType.UNARY);
		Assert.assertEquals("Nije 1.0986122...", Double.valueOf(Math.log(3.0)), Double.valueOf(core.getDisplayValue()), 1E-8);
	}

	@Test
	public void asinTest() {
		CalculatorCore core = new CalculatorCore();
		core.keyPressed(null, null, ".", KeyType.DOT_SIGN);
		core.keyPressed(null, null, "5", KeyType.NUMBER);
		core.keyPressed(null, null, "Inv", KeyType.INVERT);
		core.keyPressed(null, new OperatorASin(), "sin", KeyType.UNARY);
		Assert.assertEquals("Nije 0.52359...", Double.valueOf(Math.asin(0.5)), Double.valueOf(core.getDisplayValue()), 1E-8);
	}
	
	@Test
	public void acosTest() {
		CalculatorCore core = new CalculatorCore();
		core.keyPressed(null, null, ".", KeyType.DOT_SIGN);
		core.keyPressed(null, null, "5", KeyType.NUMBER);
		core.keyPressed(null, null, "Inv", KeyType.INVERT);
		core.keyPressed(null, new OperatorACos(), "cos", KeyType.UNARY);
		Assert.assertEquals("Nije 1.04638...", Double.valueOf(Math.acos(0.5)), Double.valueOf(core.getDisplayValue()), 1E-8);
	}
	
	@Test
	public void atanTest() {
		CalculatorCore core = new CalculatorCore();
		core.keyPressed(null, null, ".", KeyType.DOT_SIGN);
		core.keyPressed(null, null, "5", KeyType.NUMBER);
		core.keyPressed(null, null, "Inv", KeyType.INVERT);
		core.keyPressed(null, new OperatorATan(), "tan", KeyType.UNARY);
		Assert.assertEquals("Nije 0.43648...", Double.valueOf(Math.atan(0.5)), Double.valueOf(core.getDisplayValue()), 1E-8);
	}
	
	@Test
	public void actgTest() {
		CalculatorCore core = new CalculatorCore();
		core.keyPressed(null, null, "2", KeyType.NUMBER);
		core.keyPressed(null, null, "Inv", KeyType.INVERT);
		core.keyPressed(null, new OperatorACtg(), "ctg", KeyType.UNARY);
		Assert.assertEquals("Nije 0.43647...", Double.valueOf(Math.atan(1.0 / 2.0)), Double.valueOf(core.getDisplayValue()), 1E-8);
	}
	
	@Test
	public void exTest() {
		CalculatorCore core = new CalculatorCore();
		core.keyPressed(null, null, "6", KeyType.NUMBER);
		core.keyPressed(null, null, "Inv", KeyType.INVERT);
		core.keyPressed(null, new OperatorExp(), "ln", KeyType.UNARY);
		Assert.assertEquals("Nije 403.428...", Double.valueOf(Math.exp(6)), Double.valueOf(core.getDisplayValue()), 1E-8);
	}
	
	@Test
	public void pow10Test() {
		CalculatorCore core = new CalculatorCore();
		core.keyPressed(null, null, "3", KeyType.NUMBER);
		core.keyPressed(null, null, "Inv", KeyType.INVERT);
		core.keyPressed(null, new OperatorPow10(), "log", KeyType.UNARY);
		Assert.assertEquals("Nije 1000...", Double.valueOf(1000), Double.valueOf(core.getDisplayValue()), 1E-8);
	}
	
	@Test
	public void divisionByZero2Test() {
		CalculatorCore core = new CalculatorCore();
		core.keyPressed(null, new OperatorInverse(), "1/x", KeyType.UNARY);
		Assert.assertEquals("Nije -ERROR-", "-ERROR-", core.getDisplayValue());
	}
	
	@Test
	public void resTest() {
		CalculatorCore core = new CalculatorCore();
		core.keyPressed(null, null, "1", KeyType.NUMBER);
		core.keyPressed(null, null, "push", KeyType.PUSH_POP);
		core.keyPressed(null, null, "res", KeyType.CLR_RES);
		core.keyPressed(null, null, "pop", KeyType.PUSH_POP);
		Assert.assertEquals("Nije -ERROR-", "-ERROR-", core.getDisplayValue());
	}
	
	@Test
	public void clrTest() {
		CalculatorCore core = new CalculatorCore();
		core.keyPressed(null, null, "1", KeyType.NUMBER);
		core.keyPressed(null, null, "push", KeyType.PUSH_POP);
		core.keyPressed(null, null, "clr", KeyType.CLR_RES);
		core.keyPressed(null, null, "pop", KeyType.PUSH_POP);
		Assert.assertEquals("Nije 1", Double.valueOf(1), Double.valueOf(core.getDisplayValue()));
	}
	
	@Test
	public void test1() {
		CalculatorCore core = new CalculatorCore();
		core.keyPressed(null, null, "3", KeyType.NUMBER);
		core.keyPressed(null, null, "Inv", KeyType.INVERT);
		core.keyPressed(null, null, "Inv", KeyType.INVERT);
		core.keyPressed(new OperatorNthPow(), null, "x^n", KeyType.BINARY);
		core.keyPressed(null, null, "2", KeyType.NUMBER);
		core.keyPressed(null, null, "x^n", KeyType.BINARY);
		Assert.assertEquals("Nije 9", Double.valueOf(9), Double.valueOf(core.getDisplayValue()));
	}
	
	@Test
	public void errorTest() {
		CalculatorCore core = new CalculatorCore();
		core.keyPressed(null, new OperatorInverse(), "1/x", KeyType.UNARY);
		core.keyPressed(null, null, ".", KeyType.DOT_SIGN);
		Assert.assertEquals("Nije -ERROR-", "-ERROR-", core.getDisplayValue());
	}
	
	@Test
	public void errorTest2() {
		CalculatorCore core = new CalculatorCore();
		core.keyPressed(null, new OperatorInverse(), "1/x", KeyType.UNARY);
		core.keyPressed(null, null, "+/-", KeyType.DOT_SIGN);
		Assert.assertEquals("Nije -ERROR-", "-ERROR-", core.getDisplayValue());
	}
	
	@Test
	public void errorTest3() {
		CalculatorCore core = new CalculatorCore();
		core.keyPressed(null, new OperatorInverse(), "1/x", KeyType.UNARY);
		core.keyPressed(new OperatorAdd(), null, "+", KeyType.BINARY);
		Assert.assertEquals("Nije -ERROR-", "-ERROR-", core.getDisplayValue());
	}
	
	@Test
	public void errorTest4() {
		CalculatorCore core = new CalculatorCore();
		core.keyPressed(null, new OperatorInverse(), "1/x", KeyType.UNARY);
		core.keyPressed(null, null, "push", KeyType.PUSH_POP);
		Assert.assertEquals("Nije -ERROR-", "-ERROR-", core.getDisplayValue());
	}
	
	@Test
	public void errorTest5() {
		CalculatorCore core = new CalculatorCore();
		core.keyPressed(null, new OperatorInverse(), "1/x", KeyType.UNARY);
		core.keyPressed(null, new OperatorSin(), "sin", KeyType.UNARY);
		Assert.assertEquals("Nije -ERROR-", "-ERROR-", core.getDisplayValue());
	}
	
	@Test
	public void chainTest() {
		CalculatorCore core = new CalculatorCore();
		core.keyPressed(null, null, "1", KeyType.NUMBER);
		core.keyPressed(new OperatorAdd(), null, "-", KeyType.BINARY);
		core.keyPressed(null, null, "2", KeyType.NUMBER);
		core.keyPressed(null, null, "=", KeyType.BINARY);
		core.keyPressed(null, null, "=", KeyType.BINARY);
		core.keyPressed(null, null, "=", KeyType.BINARY);
		Assert.assertEquals("Nije 7", Double.valueOf(7), Double.valueOf(core.getDisplayValue()));
	}
	
	@Test
	public void chainTest2() {
		CalculatorCore core = new CalculatorCore();
		core.keyPressed(null, null, "7", KeyType.NUMBER);
		core.keyPressed(new OperatorSub(), null, "-", KeyType.BINARY);
		core.keyPressed(null, null, "2", KeyType.NUMBER);
		core.keyPressed(null, null, "=", KeyType.BINARY);
		core.keyPressed(null, null, "=", KeyType.BINARY);
		core.keyPressed(null, null, "=", KeyType.BINARY);
		Assert.assertEquals("Nije 1", Double.valueOf(1), Double.valueOf(core.getDisplayValue()));
	}
	
	@Test
	public void test3() {
		CalculatorCore core = new CalculatorCore();
		core.keyPressed(null, null, "7", KeyType.NUMBER);
		core.keyPressed(new OperatorSub(), null, "-", KeyType.BINARY);
		core.keyPressed(null, null, ".", KeyType.DOT_SIGN);
		core.keyPressed(null, null, "5", KeyType.NUMBER);
		core.keyPressed(null, null, "=", KeyType.BINARY);
		Assert.assertEquals("Nije 6.5", Double.valueOf(6.5), Double.valueOf(core.getDisplayValue()));
	}
	
	@Test
	public void test4() {
		CalculatorCore core = new CalculatorCore();
		core.keyPressed(null, null, "+/-", KeyType.DOT_SIGN);
		core.keyPressed(null, null, "7", KeyType.NUMBER);
		core.keyPressed(null, null, "+/-", KeyType.DOT_SIGN);
		core.keyPressed(null, null, "+/-", KeyType.DOT_SIGN);
		core.keyPressed(new OperatorSub(), null, "-", KeyType.BINARY);
		core.keyPressed(null, null, ".", KeyType.DOT_SIGN);
		core.keyPressed(null, null, "5", KeyType.NUMBER);
		core.keyPressed(null, null, "+/-", KeyType.DOT_SIGN);
		core.keyPressed(null, null, "+/-", KeyType.DOT_SIGN);
		core.keyPressed(null, null, "=", KeyType.BINARY);
		Assert.assertEquals("Nije 6.5", Double.valueOf(6.5), Double.valueOf(core.getDisplayValue()));
	}
	
	@Test
	public void calcWithStack() {
		CalculatorCore core = new CalculatorCore();
		core.keyPressed(null, null, "7", KeyType.NUMBER);
		core.keyPressed(new OperatorAdd(), null, "+", KeyType.BINARY);
		core.keyPressed(null, null, "5", KeyType.NUMBER);
		core.keyPressed(null, null, "=", KeyType.BINARY);
		core.keyPressed(null, null, "push", KeyType.PUSH_POP);
		core.keyPressed(new OperatorAdd(), null, "+", KeyType.BINARY);
		core.keyPressed(null, null, "5", KeyType.NUMBER);
		core.keyPressed(new OperatorAdd(), null, "+", KeyType.BINARY);
		core.keyPressed(null, null, "pop", KeyType.PUSH_POP);
		core.keyPressed(null, null, "=", KeyType.BINARY);
		Assert.assertEquals("Nije 29", Double.valueOf(29), Double.valueOf(core.getDisplayValue()));
	}
}
