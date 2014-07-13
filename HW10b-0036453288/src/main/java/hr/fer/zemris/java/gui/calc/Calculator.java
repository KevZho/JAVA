package hr.fer.zemris.java.gui.calc;

import hr.fer.zemris.java.gui.calc.core.KeyType;
import hr.fer.zemris.java.gui.calc.core.binary.BinaryOperator;
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
import hr.fer.zemris.java.gui.calc.core.unary.UnaryOperator;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Razred koji implementira Calculator GUI i pruža vežu između
 * GUI-a i samog stroja stanja.
 * @author Igor Smolkovič
 *
 */
public class Calculator extends JFrame {

	/**
	 * UID
	 */
	private static final long serialVersionUID = -5148492242213114522L;

	/**
	 * Stroj stanja kalkulator.
	 */
	private CalculatorCore core;

	/**
	 * Zaslon kalkulatora.
	 */
	private JLabel display;

	/**
	 * Zastavica koja označava da je inverse uključen.
	 */
	private boolean invert;

	/**
	 * Konstruktor.
	 */
	public Calculator() {
		initGUI();
	}

	/**
	 * Metoda koja inicijalizira GUI.
	 */
	private void initGUI() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		JPanel panel = new JPanel(new CalcLayout(10));

		core = new CalculatorCore();
		display = new JLabel(core.getDisplayValue());

		display.setHorizontalAlignment(JTextField.RIGHT);
		display.setBackground(Color.orange);
		display.setOpaque(true);
		panel.add(display, new RCPosition(1, 1));

		/**
		 * Dodaje brojeve na panel.
		 */
		addNumbers(panel);

		/**
		 * Dodaje binarne operatore na panel.
		 */
		addBinaryOperators(panel);

		/**
		 * Dodaje unarne operatore na panel.
		 */
		addUnaryOperators(panel);

		/**
		 * Dodaje tipke clr, res, push, pop, Inv.
		 */
		addCalcModifiers(panel);

		/**
		 * Dodaje panel u prozor.
		 */
		getContentPane().add(panel);

		/**
		 * Postavlja preferiranju veličinu prozora.
		 */
		pack();
	}

	/**
	 * Metoda koja dodaje komandne cls, res, push, pop i Inv na panel te im postavlja
	 * action listenere.
	 *
	 * @param panel panel nakoji se dodaju komandne (gumbi).
	 */
	private void addCalcModifiers(JPanel panel) {
		JButton clr = new JButton("clr");
		clr.addActionListener(new ClrResAction());
		panel.add(clr, new RCPosition(1, 7));

		JButton res = new JButton("res");
		res.addActionListener(new ClrResAction());
		panel.add(res, new RCPosition(2, 7));

		JButton push = new JButton("push");
		push.addActionListener(new PushPopAction());
		panel.add(push, new RCPosition(3, 7));

		JButton pop = new JButton("pop");
		pop.addActionListener(new PushPopAction());
		panel.add(pop, new RCPosition(4, 7));

		JCheckBox inv = new JCheckBox("Inv");
		inv.addActionListener(new InvertAction());
		panel.add(inv, new RCPosition(5, 7));
	}

	/**
	 * Metoda koja dodaje unarne operatore na panel te im postavlja action listenere.
	 *
	 * @param panel panel na koji se dodaju gumbi.
	 */
	private void addUnaryOperators(JPanel panel) {
		JButton dot = new JButton(".");
		dot.addActionListener(new DotSignAction());
		panel.add(dot, new RCPosition(5, 5));

		JButton changeSign = new JButton("+/-");
		changeSign.addActionListener(new DotSignAction());
		panel.add(changeSign, new RCPosition(5, 4));

		JButton inverse = new JButton("1/x");
		inverse.addActionListener(new UnaryOperatorAction(new OperatorInverse(), new OperatorInverse()));
		panel.add(inverse, new RCPosition(2, 1));

		JButton log = new JButton("log");
		log.addActionListener(new UnaryOperatorAction(new OperatorLog(), new OperatorPow10()));
		panel.add(log, new RCPosition(3, 1));

		JButton ln = new JButton("ln");
		ln.addActionListener(new UnaryOperatorAction(new OperatorLn(), new OperatorExp()));
		panel.add(ln, new RCPosition(4, 1));

		JButton sin = new JButton("sin");
		sin.addActionListener(new UnaryOperatorAction(new OperatorSin(), new OperatorASin()));
		panel.add(sin, new RCPosition(2, 2));

		JButton cos = new JButton("cos");
		cos.addActionListener(new UnaryOperatorAction(new OperatorCos(), new OperatorACos()));
		panel.add(cos, new RCPosition(3, 2));

		JButton tan = new JButton("tan");
		tan.addActionListener(new UnaryOperatorAction(new OperatorATan(), new OperatorATan()));
		panel.add(tan, new RCPosition(4, 2));

		JButton ctg = new JButton("ctg");
		ctg.addActionListener(new UnaryOperatorAction(new OperatorCtg(), new OperatorACtg()));
		panel.add(ctg, new RCPosition(5, 2));
	}

	/**
	 * Metoda koja dodaje binarne operatore na panel te im postavlja action listenere.
	 *
	 * @param panel panel na koji se dodaju gumbi.
	 */
	private void addBinaryOperators(JPanel panel) {
		JButton mul = new JButton("*");
		mul.addActionListener(new BinaryOperatorAction(new OperatorMul()));
		panel.add(mul, new RCPosition(3, 6));

		JButton div = new JButton("/");
		div.addActionListener(new BinaryOperatorAction(new OperatorDiv()));
		panel.add(div, new RCPosition(2, 6));

		JButton add = new JButton("+");
		add.addActionListener(new BinaryOperatorAction(new OperatorAdd()));
		panel.add(add, new RCPosition(5, 6));

		JButton sub = new JButton("-");
		sub.addActionListener(new BinaryOperatorAction(new OperatorSub()));
		panel.add(sub, new RCPosition(4, 6));

		JButton eq = new JButton("=");
		eq.addActionListener(new BinaryOperatorAction(null));
		panel.add(eq, new RCPosition(1, 6));

		JButton xn = new JButton("x^n");
		xn.addActionListener(new BinaryOperatorAction(new OperatorNthPow(), new OperatorNthRoot()));
		panel.add(xn, new RCPosition(5, 1));
	}

	/**
	 * Metoda koja dodaje brojave na panel te im dodaje action listenere.
	 *
	 * @param panel panel na koji se dodaju brojevi.
	 */
	private void addNumbers(JPanel panel) {
		JButton bt7 = new JButton("7");
		bt7.addActionListener(new NumberAction());
		panel.add(bt7, new RCPosition(2, 3));

		JButton bt4 = new JButton("4");
		bt4.addActionListener(new NumberAction());
		panel.add(bt4, new RCPosition(3, 3));

		JButton bt1 = new JButton("1");
		bt1.addActionListener(new NumberAction());
		panel.add(bt1, new RCPosition(4, 3));

		JButton bt0 = new JButton("0");
		bt0.addActionListener(new NumberAction());
		panel.add(bt0, new RCPosition(5, 3));

		JButton bt8 = new JButton("8");
		bt8.addActionListener(new NumberAction());
		panel.add(bt8, new RCPosition(2, 4));

		JButton bt5 = new JButton("5");
		bt5.addActionListener(new NumberAction());
		panel.add(bt5, new RCPosition(3, 4));

		JButton bt2 = new JButton("2");
		bt2.addActionListener(new NumberAction());
		panel.add(bt2, new RCPosition(4, 4));

		JButton bt9 = new JButton("9");
		bt9.addActionListener(new NumberAction());
		panel.add(bt9, new RCPosition(2, 5));

		JButton bt6 = new JButton("6");
		bt6.addActionListener(new NumberAction());
		panel.add(bt6, new RCPosition(3, 5));

		JButton bt3 = new JButton("3");
		bt3.addActionListener(new NumberAction());
		panel.add(bt3, new RCPosition(4, 5));
	}

	/**
	 * Razred koji impelemtira actio listener za push i pop operacije.
	 * @author Igor Smolkovič
	 *
	 */
	public class PushPopAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String key = ((JButton) e.getSource()).getText();
			core.keyPressed(null, null, key, KeyType.PUSH_POP);
			String state = core.getDisplayValue();
			display.setText(state);
		}

	}

	/**
	 * Razred koji implementira action listener za clr i res funkcije.
	 * @author Igor Smolkovič
	 *
	 */
	public class ClrResAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String key = ((JButton) e.getSource()).getText();
			core.keyPressed(null, null, key, KeyType.CLR_RES);
			String state = core.getDisplayValue();
			display.setText(state);
		}

	}
	/**
	 * Razred koji implementira action listener za decimalnu točku i 
	 * promjenu predznaka.
	 * @author Igor Smolkovič
	 *
	 */
	public class DotSignAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String key = ((JButton) e.getSource()).getText();
			core.keyPressed(null, null, key, KeyType.DOT_SIGN);
			String state = core.getDisplayValue();
			display.setText(state);
		}

	}

	/**
	 * Razred koji implementira action listener za brojeve.
	 * @author Igor Smolkovič
	 *
	 */
	public class NumberAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String key = ((JButton) e.getSource()).getText();
			core.keyPressed(null, null, key, KeyType.NUMBER);
			String state = core.getDisplayValue();
			display.setText(state);
		}
	}

	/**
	 * Razred koji implementira action listener za promjenu operatora.
	 * @author Igor Smolkovič
	 *
	 */
	public class InvertAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			invert = !invert;
			core.keyPressed(null, null, "Inv", KeyType.INVERT);
		}

	}

	/**
	 * Razred koji implementira action listener za unarne operatore.
	 * @author Igor Smolkovič
	 *
	 */
	public class UnaryOperatorAction implements ActionListener {

		/**
		 * Regularni opperator.
		 */
		private UnaryOperator regular;

		/**
		 * Inverzni operator.
		 */
		private UnaryOperator inverse;

		/**
		 * Konstruktor.
		 * @param regular regularni operator.
		 * @param inverse inverzni operator.
		 */
		public UnaryOperatorAction(UnaryOperator regular, UnaryOperator inverse) {
			super();
			this.regular = regular;
			this.inverse = inverse;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String key = ((JButton) e.getSource()).getText();
			if (invert) {
				core.keyPressed(null, inverse, key, KeyType.UNARY);
			} else {
				core.keyPressed(null, regular, key, KeyType.UNARY);
			}
			String state = core.getDisplayValue();
			display.setText(state);
		}

	}

	/**
	 * Razred koji implementira action listener za binarne operatore.
	 * @author Igor Smolkovič
	 *
	 */
	public class BinaryOperatorAction implements ActionListener {
		/**
		 * Obični operator.
		 */
		private BinaryOperator operator;
		/**
		 * Obrnuti operator, koristi se samo kod x^n i n-ti korijen iz x.
		 */
		private BinaryOperator reverse;

		/**
		 * Konstruktor.
		 * @param operator regularni operator.
		 */
		public BinaryOperatorAction(BinaryOperator operator) {
			super();
			this.operator = operator;
		}

		/**
		 * Konstruktor.
		 * @param regular regularni operator.
		 * @param reverse obrnuti operator.
		 */
		public BinaryOperatorAction(BinaryOperator regular, BinaryOperator reverse) {
			this.operator = regular;
			this.reverse = reverse;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String key = ((JButton) e.getSource()).getText();
			if (operator instanceof OperatorNthPow) {
				if (invert) {
					core.keyPressed(reverse, null, key, KeyType.BINARY);
				} else {
					core.keyPressed(operator, null, key, KeyType.BINARY);
				}
			} else {
				core.keyPressed(operator, null, key, KeyType.BINARY);
			}
			String state = core.getDisplayValue();
			display.setText(state);
		}
	}

	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 *
	 * @param args argumenti komandne linije. Program ne koristi argumente komandne linije.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Calculator().setVisible(true);
			}
		});
	}
}
