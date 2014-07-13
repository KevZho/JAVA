package hr.fer.zemris.java.custom.scripting.exec;

import java.util.EmptyStackException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ObjectMultistackTest {

	private ObjectMultistack ms;
	
	@Before
	public void init() {
		ms = new ObjectMultistack();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void pushNullKeyTest() {
		ms.push(null, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void pushNullValue() {
		ms.push("key", null);
	}
	
	@Test
	public void pushTest() {
		ms.push("key", new ValueWrapper("test"));
		Assert.assertNotNull("Dodana je null referenca.", ms.peek("key"));
	}
	
	@Test
	public void isEmptyTest() {
		Assert.assertEquals("Object stack sadrži elemente.", true, ms.isEmpty());
	}
	
	@Test
	public void notEmptyTest() {
		ms.push("key", new ValueWrapper("test"));
		Assert.assertEquals("Object stack ne sadrži elemente.", false, ms.isEmpty());
	}
	
	@Test(expected=EmptyStackException.class)
	public void peekEmptyStackTest() {
		ms.peek("key");
	}
	
	@Test(expected=EmptyStackException.class)
	public void popEmptyStackTest() {
		ms.pop("key");
	}

	@Test
	public void peekTest() {
		ms.push("key", new ValueWrapper("test"));
		Assert.assertEquals("Pohranjena vrijednost nije \"test\".", "test", ms.peek("key").getValue());
	}
	
	@Test
	public void popTest() {
		ms.push("key", new ValueWrapper("test"));
		Assert.assertEquals("Pohranjena vrijednost nije \"test\".", "test", ms.pop("key").getValue());
	}
}
