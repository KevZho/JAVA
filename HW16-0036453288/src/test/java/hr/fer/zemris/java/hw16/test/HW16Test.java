package hr.fer.zemris.java.hw16.test;

import hr.fer.zemris.java.hw16.model.BlogUser;

import org.junit.Assert;
import org.junit.Test;

/**
 * Testovi.
 * 
 * @author Igor Smolkovič
 * 
 */
public class HW16Test {

	@Test
	public void test1() {
		BlogUser user = new BlogUser();
		Assert.assertNotNull(user);
	}
}
