package hr.fer.zemris.java.hw11.jdraw.test;

import java.awt.Color;
import java.awt.Point;

import org.junit.Assert;
import org.junit.Test;

import hr.fer.zemris.java.hw11.jvdraw.go.Line;

public class LineTest {

	@Test
	public void dummyTest() {
		Line l = new Line("Line", new Point(0,1), new Point(1,1), Color.BLACK);
		Assert.assertNotNull(l);
	}
}
