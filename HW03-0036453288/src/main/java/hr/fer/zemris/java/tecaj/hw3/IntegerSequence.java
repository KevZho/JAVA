package hr.fer.zemris.java.tecaj.hw3;

import java.util.Iterator;

/**
 * Razred <code>IntegerSequence</code> korisnicima pruža mogućnost
 * prolaska kroz red brojeva od početnog do završnog cijelog broja
 * uz odabrani korak pomaka.
 * 
 * @author Igor Smolkovič
 * @version 1.0
 *
 */
public class IntegerSequence implements Iterable<Integer> {

	private int start;
	private int end;
	private int step;
	

	/**
	 * Konstruktor razreda <code>IntegerSequence</code>. 
	 * 
	 * @param start Prvi element reda. 
	 * @param end Posljedni element reda.
	 * @param step Pomak između susjedna dva elementa.
	 * @throws IllegalArgumentException ako granice ili pomak nisu
	 * 			odgovarajući.
	 */
	public IntegerSequence(int start, int end, int step) {
		if(end < start && step > 0) {
			throw new IllegalArgumentException("Smjer iteriranja nije dobar");
		}
		
		if(start < end && step < 0) {
			throw new IllegalArgumentException("Smjer iteriranja nije dobar");
		}
	
		if(step == 0) {
			throw new IllegalArgumentException("Iteriranje neće završiti");
		}
		
		this.start = start;
		this.end = end;
		this.step = step;
	}


	/**
	 * Overridana metoda iterator() koja vraća instancu iteratora koji 
	 * zna iterirati kroz elemente razreda <code>IntegerSequence</code>. 
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<Integer> iterator() {
		return new IntegerSequenceIterator();
	}
	

	/**
	 * Unutarnji privatni razred <code>IntegerSequenceIterator</code>
	 * implementira sučelje Iterator<Integer>. 
	 * 
	 * @author Igor Smolkovič
	 * @version 1.0
	 *
	 */
	private class IntegerSequenceIterator implements Iterator<Integer> {
		private int current;
		private boolean last;
		
		
		/**
		 * Default konstruktor razreda IntegerSequenceIterator.
		 * 
		 */
		public IntegerSequenceIterator() {
			current = start;
			last = false;
		}
		
		
		/**
		 * Overridana metoda hasNext() koja vraća informaciju 
		 * postoji li sljedeći element.
		 * 
		 * {@inheritDoc}
		 */
		@Override
		public boolean hasNext() {
			return !last;
		}

		
		/**
		 * Overridana metoda next() vraća sljedeći element u 
		 * iteriranju.
		 * 
		 * {@inheritDoc}
		 */
		@Override
		public Integer next() {
			if(last == true) {
				throw new RuntimeException("Nema elemenata za vratiti.");
			}
			
			if(step > 0) {
				last = current < end ? false : true;
			}
			else {
				last = current > end ? false : true; 
			}
			
			int next = current;
			current += step;
		
			return next;
		}

		
		/**
		 * Overridana metoda remove() omogućava sigurno brisanje 
		 * trenutnog elementa. U ovoj implementaciji nije podržana.
		 * 
		 * {@inheritDoc}
		 */
		@Override
		public void remove() {
			final String msg = "Nije moguće brisati elemente.";
			throw new UnsupportedOperationException(msg);
		}
	}

}
