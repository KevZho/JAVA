package hr.fer.zemris.java.tecaj.hw15.test;

import hr.fer.zemris.java.tecaj.hw15.model.PollOptionsData;

import org.junit.Assert;
import org.junit.Test;

public class ServletTest {

	@Test
	public void dummy() {
		PollOptionsData data = new PollOptionsData(1L, "Naslov", "Link", 10);
		Assert.assertNotNull(data);
	}
}
