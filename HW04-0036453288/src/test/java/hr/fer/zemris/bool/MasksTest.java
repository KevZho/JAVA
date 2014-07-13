package hr.fer.zemris.bool;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class MasksTest {

	@Test
	public void fromStringsTest() {
		List<Mask> masks = Masks.fromStrings("0x1x", "1110", "x00x");
		Assert.assertEquals(3, masks.size());
	}
	
	@Test
	public void fromIndexesTest() {
		List<Mask> masks = Masks.fromIndexes(3, 0, 1, 7);
		Assert.assertEquals(3, masks.size());
	}
}
