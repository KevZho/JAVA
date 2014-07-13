package hr.fer.zemris.bool.fimpl;

import hr.fer.zemris.bool.fimpl.iterable.ListGenerator;

import org.junit.Test;

public class ListGeneratorTest {

	@Test(expected=IllegalArgumentException.class)
	public void nullMintermList() {
		ListGenerator.getMinterms(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nullMaxtermList() {
		ListGenerator.getMaxterms(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nullDontcareList() {
		ListGenerator.getDontcares(null);
	}
	
}
