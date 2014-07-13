package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Razred <code>ValueWrapper</code> omogućava spremanje proizvoljnih tipova
 * podataka. Pruža aritmetičke operacije nad spremljenim vrijednostima uz
 * ograničenje na dozvoljene tipove podataka. Podržani tipovi za aritmetičke
 * operacije: {@link Integer}, {@link Double}, {@link String} koji se može
 * pretvoriti u {@link Integer} ili {@link Double} te null reference.
 *
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class ValueWrapper {
	/**
	 * Objekt koji je spremljen.
	 */
	private Object value;
	
	/**
	 * Konstrutor razreda <code>ValueWrapper</code> prima početnu vrijednost
	 * na koju se postavlja objekt.
	 *
	 * @param initialValue Početna vrijednost.
	 */
	public ValueWrapper(Object initialValue) {
		super();
		this.value = initialValue;
	}
	
	/**
	 * Get medoda za dohvačanje pohranjene vrijednosti.
	 *
	 * @return Vrijednost koja je pohranjena.
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Set metoda za promjenu trenutne vrijednosti.
	 *
	 * @param value Nova vrijednost koja se pohranjuje.
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Metoda koja provodi aritmetičku operaciju increment. Trenutnoj
	 * vrijednost se dodaje dobivena vrijednost.
	 *
	 * @param incValue Vrijednost koja se dodaje trenutnoj.
	 */
	public void increment(Object incValue) {
		AllowedValue type = this.getType(this.value, incValue);
		Object first = convertToType(value, type);
		Object second = convertToType(incValue, type);
		if (type == AllowedValue.INTEGER) {
			int result = (Integer) first + (Integer) second;
			this.value = Integer.valueOf(result);
		}
		if (type == AllowedValue.DOUBLE) {
			double result = (Double) first + (Double) second;
			this.value = Double.valueOf(result);
		}
	}
	
	/**
	 * Metoda koja provodi aritmetičku operaciju decrement. Trenutna 
	 * vrijednost se umanjuje za dobivenu vrijednost.
	 *
	 * @param decValue Vrijednost za koju se trenutna umanjuje.
	 */
	public void decrement(Object decValue) {
		AllowedValue type = this.getType(this.value, decValue);
		Object first = convertToType(value, type);
		Object second = convertToType(decValue, type);
		if (type == AllowedValue.INTEGER) {
			int result = (Integer) first - (Integer) second;
			this.value = Integer.valueOf(result);
		}
		if (type == AllowedValue.DOUBLE) {
			double result = (Double) first - (Double) second;
			this.value = Double.valueOf(result);
		}
	}
	
	/**
	 * Metoda koja provodi aritmetičku operaciju množenje. Trenutna 
	 * vrijednost se množi sa dobivenom vrijednošću.
	 *
	 * @param mulValue Vrijednost sa kojom se množi trenutna.
	 */
	public void multiply(Object mulValue) {
		AllowedValue type = this.getType(this.value, mulValue);
		Object first = convertToType(value, type);
		Object second = convertToType(mulValue, type);
		if (type == AllowedValue.INTEGER) {
			int result = (Integer) first * (Integer) second;
			this.value = Integer.valueOf(result);
		}
		if (type == AllowedValue.DOUBLE) {
			double result = (Double) first * (Double) second;
			this.value = Double.valueOf(result);
		}
	}
	
	/**
	 * Metoda koja provodi aritmetičku operaciju dijeljenje. Trenutna
	 * vrijednost se podijeli sa dobivenom vrijednošću.
	 *
	 * @param divValue Vrijednost sa kojom se dijeli trenutna.
	 */
	public void divide(Object divValue) {
		AllowedValue type = this.getType(this.value ,divValue);
		Object first = convertToType(value, type);
		Object second = convertToType(divValue, type);
		if (type == AllowedValue.INTEGER) {
			int result = (Integer) first / (Integer) second;
			this.value = Integer.valueOf(result);
		}
		if (type == AllowedValue.DOUBLE) {
			double result = (Double) first / (Double) second;
			this.value = Double.valueOf(result);
		}
	}
	
	/**
	 * Metoda koja uspoređuje trenutnu vrijednost s vrijednošću dobivenom
	 * kao parametar.
	 *
	 * @param withValue Vrijednost s kojom se uspoređuje trenutna. 
	 * @return 0 ako su vrijednosti iste ili su obje vrijednosti null.
	 * 			Vrijednost manju od 0 ako je trenutna vrijednost manja od
	 * 			dobivene, inače veće od 0.
	 */
	public int numCompare(Object withValue) {
		if (withValue == null && this.value == null) {
			return 0;
		}
		AllowedValue type = this.getType(this.value , withValue);
		Object first = convertToType(value, type);
		Object second = convertToType(withValue, type);
		if (type == AllowedValue.INTEGER) {
			return Integer.compare((Integer) first, (Integer) second);
		}
		else {
			return Double.compare((Double) first, (Double) second);
		}
	}
	
	/**
	 * Metoda koja određuje rezultatni tip za provođenje aritmetičke
	 * operacije. U taj tip biti će pretvoreni operandi. Dozvoljeni
	 * tipovi ulaznih operanada su Integer, String, Double, null.
	 *
	 * @param oper1 Referenca na prvi operand.
	 * @param oper2 Refrenca na drugi operand.
	 * @return AllowedValue.DOUBLE ako je rezultat u tipu Double, inače
	 * 			AllowedValue.INTEGER.
	 */
	private AllowedValue getType(Object oper1, Object oper2) {
		AllowedValue first = checkType(value);
		AllowedValue second = checkType(oper2);
		if (first == AllowedValue.DOUBLE || second == AllowedValue.DOUBLE) {
			return AllowedValue.DOUBLE;
		}
		return AllowedValue.INTEGER;
	}
	
	/**
	 * Metoda koja pretvara objekta u traženi tip. Ukoliko se dobije
	 * null referenca vraća new Integer(0). 
	 *
	 * @param o Objekt koji se pretvara u traženi tip.
	 * @param type Tip u koji se objekt pretvara.
	 * @return Objekt pretvoreni u Integer ili Double tip.
	 */
	private Object convertToType(Object o, AllowedValue type) {
		if (o == null && type == AllowedValue.INTEGER) {
			return Integer.valueOf(0);
		} else if (o == null) { // u slučaju da treba sve pretvoriti u Double
			return Double.valueOf(0);
		} else if (o instanceof String) {
			if (type == AllowedValue.INTEGER) {
				return Integer.parseInt((String) o);
			} else {
				return Double.parseDouble((String) o);
			}
		} else if (o instanceof Integer) {
			if(type == AllowedValue.DOUBLE) {
				int value = ((Integer) o).intValue();
				return Double.valueOf(value);
			} else {
				return (Integer) o;
			}
		}
		return (Double) o;
	}
	
	/**
	 * Metoda koja utvrđuje tip objekta.
	 *
	 * @param o Objekt čiji se tip određuje.
	 * @return AllowedValue.DOUBLE ako je tip Double ili može biti pretvoren
	 * 			u Double, AllowedValue.INTEGER ako je tip Integer, može biti 
	 * 			pretvoren u Integer ili je dobivena null refrenca.
	 * @throws RuntimeException ako je objekt nije tipa Integer, String, Double ili
	 * 			null referenca.
	 *
	 */
	private AllowedValue checkType(Object o) {
		if (o == null) {
			return AllowedValue.INTEGER;
		} else if (o instanceof Integer) {
			return AllowedValue.INTEGER;
		} else if (o instanceof Double) {
			return AllowedValue.DOUBLE;
		} else if (o instanceof String) {
			try {
				Integer.parseInt((String) o);
				return AllowedValue.INTEGER;
			} catch (Exception ignorable) {
			}
			try {
				Double.parseDouble((String) o);
				return AllowedValue.DOUBLE;
			} catch (Exception ignorable) {
			}
		}
		throw new RuntimeException("Illegal object type");
	}
}
