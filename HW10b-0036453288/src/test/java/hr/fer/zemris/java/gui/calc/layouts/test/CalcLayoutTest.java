package hr.fer.zemris.java.gui.calc.layouts.test;


import javax.swing.JButton;
import javax.swing.JLabel;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

import org.junit.Test;

public class CalcLayoutTest {

	@Test(expected=IllegalArgumentException.class)
	public void addSameConstraints() {
		CalcLayout layout = new CalcLayout();
		layout.addLayoutComponent(new JLabel(), new RCPosition(1, 1));
		layout.addLayoutComponent(new JLabel(), new RCPosition(1, 1));
	}

	@Test(expected=IllegalArgumentException.class)
	public void illegalConstraintTest() {
		CalcLayout layout = new CalcLayout();
		layout.addLayoutComponent(new JLabel(), new RCPosition(-1, 1));
	}

	@Test(expected=IllegalArgumentException.class)
	public void illegalConstraintTest2() {
		CalcLayout layout = new CalcLayout();
		layout.addLayoutComponent(new JLabel(), new RCPosition(1, -1));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void illegalConstraintTest3() {
		CalcLayout layout = new CalcLayout();
		layout.addLayoutComponent(new JLabel(), new RCPosition(1, 8));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void illegalConstraintTest4() {
		CalcLayout layout = new CalcLayout();
		layout.addLayoutComponent(new JLabel(), Integer.valueOf(1));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void illegalConstraintTest5() {
		CalcLayout layout = new CalcLayout();
		layout.addLayoutComponent(new JLabel(), "1.2");
	}
	
	@Test
	public void stringConstraintsTest() {
		CalcLayout layout = new CalcLayout();
		layout.addLayoutComponent(new JLabel(), "1,1");
		layout.addLayoutComponent(new JButton(), "2,1");
		layout.addLayoutComponent(new JButton(), "3,1");
	}
	
	@Test
	public void removeComponentTest() {
		CalcLayout layout = new CalcLayout();
		JLabel label = new JLabel();
		layout.addLayoutComponent(label, new RCPosition(1, 1));
		layout.removeLayoutComponent(label);
	}
}
