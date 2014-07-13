package hr.fer.zemris.java.gui.calc;

import java.util.EmptyStackException;
import java.util.Stack;

import hr.fer.zemris.java.gui.calc.core.CalcState;
import hr.fer.zemris.java.gui.calc.core.KeyType;
import hr.fer.zemris.java.gui.calc.core.binary.BinaryOperator;
import hr.fer.zemris.java.gui.calc.core.unary.UnaryOperator;

/**
 * Razred koji implementira kalkulatora u obliku stroja stanja.
 *
 * <pre>
 * Svaki put kad je na GUI-u pritisnuta tipka poziva se metoda keyPressed(String key) koja postavlja
 * stroj stanja u neko od stanja. Prilikom svake promjene kalkulatora GUI dohvaća trenutno stanje
 * metodom getDisplayValue().
 * </pre>
 *
 * @author Igor Smolkovič
 *
 */
public class CalculatorCore {

	/**
	 * Trenutno na kalkulatoru.
	 */
	private String currentValue;

	/**
	 * Prethodno na kalkulatoru.
	 */
	private String previousValue;

	/**
	 * Drugi operand.
	 */
	private String secondOperand;

	/**
	 * Stanje kalkulatora.
	 */
	private CalcState state;

	/**
	 * Gledaju li se inverzne operacije.
	 */
	private boolean invert;

	/**
	 * Stog kalkulatora.
	 */
	private Stack<String> stack;

	/**
	 * Aritmetička operacija.
	 */
	private BinaryOperator operator;

	/**
	 * Konstruktor.
	 */
	public CalculatorCore() {
		this.state = CalcState.INPUT;
		this.stack = new Stack<>();
		this.currentValue = "0";
	}

	public void keyPressed(BinaryOperator bin, UnaryOperator unry, String key, KeyType type) {
		/**
		 * Ako je prethodno postavljeno stanje chain koje omogućava više puta za
		 * redom tiskanje =, provjerava da li je dobiven '=' inače vraća kalkulator u mod normalno.
		 */
		if (state == CalcState.CHAIN && !key.equals("=")) {
			state = CalcState.CLEAR;
		}
		if (type == KeyType.NUMBER) {
			numberPressed(key);
		} else if (type == KeyType.DOT_SIGN) {
			signDotPressed(key);
		} else if (type == KeyType.CLR_RES) {
				clrResetPressed(key);
		} else if (type == KeyType.PUSH_POP) {
			pushPopPressed(key);
		} else if (type == KeyType.INVERT) {
			invertPressed();
		} else if (type == KeyType.BINARY) {
			binaryOperatorPressed(bin, key);
		} else {
			unaryOperatorPressed(unry);
		}
	}
	
	/**
	 * Metoda koja vraća trenutno stanje kalkulatora.
	 *
	 * @return trenutno stanje kalkulatora.
	 */
	public String getDisplayValue() {
		return currentValue;
	}

	/**
	 * Metoda koja obrađuje gumbe koji sadrže znamenke.
	 * @param key gumb koji se obrađuje.
	 */
	private void numberPressed(String key) {
		/**
		 * U slučaju da treba obrisati ekran prije sljedećeg broja. Npr. bilo je pritisnuto 1 + 2 +.
		 */
		if (state == CalcState.CLEAR || state == CalcState.ERROR) {
			clearCurrentState();
		}
		/**
		 * U slučaju da je već nula na kalkulatoru nije dozvoljeno upisivanje novih.
		 */
		if (key.equals("0") && currentValue.equals("0")) {
		} else if (currentValue.equals("0") && !key.equals("0")) {
			currentValue = key; // Ako je gore nula, nije bilo ., onda zamijeni 0 s novim brojem
		} else {
			currentValue += key; // Inače samo dodaj na kraj.
		}
	}

	/**
	 * Metoda koja čisti trenutno stanje ekrana.
	 */
	private void clearCurrentState() {
		currentValue = "0";
		state = CalcState.INPUT;
	}

	/**
	 * Metoda koja obrađuje decimalu točku i promjenu predznaka.
	 *
	 * @param key gumb koji se obrađuje.
	 */
	private void signDotPressed(String key) {
		/**
		 * Zabrani stavljanje točke i promjenu predznaka ako je na ekranu -ERROR-.
		 */
		if (state == CalcState.ERROR) { return; }
		/**
		 * Ako je novi unos i bila je pritisnuta ., onda treba postaviti 0 na ekran
		 * kako bi se mogla dodati točka.
		 */
		if (state == CalcState.CLEAR) {
			clearCurrentState();
		}
		if (key.equals(".")) {
			if (currentValue.indexOf(".") != -1) { return; }
			currentValue += key;
			return;
		}
		if (currentValue.startsWith("-")) {
			currentValue = currentValue.substring(1);
		} else if (!currentValue.equals("0") && currentValue.length() != 0) {
			/**
			 * Ne može se dozvoliti -0.
			 */
			currentValue = "-" + currentValue;
		}
	}

	/**
	 * Metoda koja obrađuje cls i res komandne.
	 * @param key gumb koji se obrađuje.
	 */
	private void clrResetPressed(String key) {
		clearCurrentState();
		if (key.equals("res")) {
			stack.clear();
			previousValue = null;
			secondOperand = null;
		}
	}

	/**
	 * Metoda koja obrađuje push i pop operacije.
	 * @param key
	 */
	private void pushPopPressed(String key) {
		/**
		 * Zabrani push i pop ako je na ekranu -ERR0R-.
		 */
		if (state == CalcState.ERROR) { return; }
		if (key.equals("pop")) {
			try {
				currentValue = stack.pop();
			} catch (EmptyStackException e) {
				setErrorMsg();
			}
			/**
			 * Ako je kalkulator bio u modu unosa i uzeto je nešto sa stoga, stavi
			 * ga u stanje input koje više ne očekuje broj ili . koja briše sadržaj ekrana.
			 */
			if (state == CalcState.CLEAR) {
				state = CalcState.INPUT;
			}
			return;
		}
		stack.push(currentValue);
	}

	/**
	 * Metoda koja obrađuje komandu inv. Razredi <code>ReverseOperations</code> i 
	 * <code>RegularOperations</code> imaju konkretne implementacije svih operatora koji
	 * se izmjenjuju. Svaki razred je jedna implementacija strategije.
	 */
	private void invertPressed() {
		invert = !invert;
	}

	/**
	 * Metoda koja na ekran postavlja poruku greške.
	 */
	private void setErrorMsg() {
		currentValue = "-ERROR-";
		state = CalcState.ERROR;
	}

	/**
	 * Metoda koja obrađuje binarne operatore.
	 * @param operator operator koji se primjenjuje.
	 * @param key gumb koji je bio pritisnut.
	 */
	private void binaryOperatorPressed(BinaryOperator operator, String key) {
		/**
		 * Ako je greška onda onda nema efekta.
		 */
		if (state == CalcState.ERROR) { return; }
		/**
		 * Ako još nema niti jednog operanda, zapiši ga i zapamti operator.
		 */
		if (previousValue == null) {
			previousValue = currentValue;
			this.operator = operator;
			state = CalcState.CLEAR;
			return;
		}
		/**
		 * Ako je došlo do promjene operatora prije unosa novog broja, zamijeni operator.
		 */
		if (state == CalcState.CLEAR) {
			this.operator = operator;
			return;
		}
		/**
		 * Inače je došao normalan binarni operator te izračunaj vrijednost i stavi na ekran.
		 */
		try {
			/**
			 * Treba zapamtiti drugi operand jer kad se provodi lančano tiskanje entera je potreban.
			 * Operacija +=.
			 */
			if (state != CalcState.CHAIN) {
				secondOperand = currentValue;
			}
			/**
			 * Ako je chain mode onda treba postaviti trenutni na drugi operand.
			 */
			if (state == CalcState.CHAIN) {
				currentValue = secondOperand;
			}
			/**
			 * Stavi u previousValue izračunati rezultat te taj isti za ispis na ekran.
			 */
			previousValue = String.valueOf(this.operator.calculate(
					Double.valueOf(previousValue), Double.valueOf(currentValue)));
			currentValue = previousValue;
			/**
			 * Ako je = onda je moguć chain mode.
			 */
			if (key.equals("=")) {
				state = CalcState.CHAIN;
			} else {
				this.operator = operator;
				state = CalcState.CLEAR;
			}
		} catch (Exception e) {
			setErrorMsg(); // Postavi poruku greške ako je došlo do greške prilikom računanja.
		}
	}

	/**
	 * Metoda koja obrađuje unarne operatore.
	 * @param operator operator koji se primjenjuje.
	 */
	private void unaryOperatorPressed(UnaryOperator operator) {
		try {
			currentValue = String.valueOf(operator.calculate(Double.valueOf(currentValue)));
		} catch (ArithmeticException e) {
			setErrorMsg();
		} catch (Exception e) {
			setErrorMsg();
		}
	}
}
