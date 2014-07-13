package hr.fer.zemris.java.hw13.test;

import hr.fer.zemris.java.hw13.ServletUtil.Rezultati;

import org.junit.Assert;
import org.junit.Test;

public class ServletTest {

	@Test
	public void dummy() {
		Rezultati rez = new Rezultati("The Beatles", "http://...", 10, 0);
		Assert.assertNotNull(rez);
		Assert.assertEquals("ID nije 0", Integer.valueOf(0), rez.getId());
	}
}
