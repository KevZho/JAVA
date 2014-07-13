package hr.fer.zemris.java.tecaj.hw6.problem1b;

import java.nio.file.Paths;

/**
 * Razred <code>ObserverExample</code> služi za provjeru ispravnosti
 * implementiranih promatrača i subjekta.
 *
 * <p>
 * Program ne koristi nikakve argumente komandne linije.
 * </p>
 *
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class ObserverExample {

	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 *
	 * @param args Argumenti komandne linije.
	 */
	public static void main(String[] args) {
		IntegerStorage istorage = new IntegerStorage(20);
		IntegerStorageObserver observer = new SquareValue();
		istorage.addObserver(observer);
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue());
		istorage.addObserver(new LogValue(Paths.get("./log1.txt")));
		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);
		
		//istorage.removeObserver(observer);

		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
	}
}
