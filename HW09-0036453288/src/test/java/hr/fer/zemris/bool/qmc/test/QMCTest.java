package hr.fer.zemris.bool.qmc.test;

import hr.fer.zemris.bool.BooleanFunction;
import hr.fer.zemris.bool.BooleanVariable;
import hr.fer.zemris.bool.Mask;
import hr.fer.zemris.bool.Masks;
import hr.fer.zemris.bool.fimpl.IndexedBF;
import hr.fer.zemris.bool.fimpl.MaskBasedBF;
import hr.fer.zemris.bool.qmc.QMCMinimizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class QMCTest {

	/**
	 * Napomena:
	 *
	 * Primjetiti da ako funkcija nema don't care dio zahtijeva new ArrayList<Integer>()
	 * tj. ne može se predati null. Ako zatreba u .jar datoteci ima i source od
	 * korištene biblioteke.
	 */
	private BooleanVariable varA = new BooleanVariable("A");
	private BooleanVariable varB = new BooleanVariable("B");
	private BooleanVariable varC = new BooleanVariable("C");
	private BooleanVariable varD = new BooleanVariable("D");
	
	@Test
	public void function1Test() {
		BooleanFunction bf = new IndexedBF(
				"f1",
				Arrays.asList(varA, varB, varC, varD),
				true,
				Arrays.asList(0, 1, 4, 5, 11, 15),
				new ArrayList<Integer>()
				);
		
		boolean zelimoDobitiProdukte = true;
		MaskBasedBF[] fje = QMCMinimizer.minimize(bf, zelimoDobitiProdukte);
		Assert.assertEquals("Nije 1 minimalni oblik", 1, fje.length);
		
		Set<Mask> expected = Masks.fromString("0x0x", "1x11");
		Set<Mask> calculated = new HashSet<>(fje[0].getMasks());
		Assert.assertEquals("Dobivene funkcije nisu iste", expected, calculated);
	}
	
	@Test
	public void function2Test() {
		BooleanFunction bf = new IndexedBF(
				"f1",
				Arrays.asList(varA, varB, varC, varD),
				true,
				Arrays.asList(0, 1, 4, 5, 9, 11, 15),
				new ArrayList<Integer>()
				);
		
		boolean zelimoDobitiProdukte = true;
		MaskBasedBF[] fje = QMCMinimizer.minimize(bf, zelimoDobitiProdukte);
		Assert.assertEquals("Nisu 2 minimalna oblika", 2, fje.length);
		
		Set<Mask> expected1 = Masks.fromString("0x0x", "1x11", "10x1");
		Set<Mask> expected2 = Masks.fromString("0x0x", "1x11", "x001");
		Set<Set<Mask>> expected = new HashSet<>();
		expected.add(expected1);
		expected.add(expected2);
			
		Assert.assertTrue("Dobivene fukcije nisu iste.", expected.contains(new HashSet<>(fje[0].getMasks())));
		Assert.assertTrue("Dobivene fukcije nisu iste.", expected.contains(new HashSet<>(fje[1].getMasks())));
	}
	
	@Test
	public void function3Test() {
		BooleanFunction bf = new IndexedBF(
				"f1",
				Arrays.asList(varA, varB, varC, varD),
				true,
				Arrays.asList(0, 1, 4, 5, 9, 15),
				Arrays.asList(11)
				);
		
		boolean zelimoDobitiProdukte = true;
		MaskBasedBF[] fje = QMCMinimizer.minimize(bf, zelimoDobitiProdukte);
		Assert.assertEquals("Nisu 2 minimalna oblika", 2, fje.length);
		
		Set<Mask> expected1 = Masks.fromString("0x0x", "1x11", "10x1");
		Set<Mask> expected2 = Masks.fromString("0x0x", "1x11", "x001");
		Set<Set<Mask>> expected = new HashSet<>();
		expected.add(expected1);
		expected.add(expected2);
			
		Assert.assertTrue("Dobivene fukcije nisu iste.", expected.contains(new HashSet<>(fje[0].getMasks())));
		Assert.assertTrue("Dobivene fukcije nisu iste.", expected.contains(new HashSet<>(fje[1].getMasks())));
	}
	
	@Test
	public void function4Test() {
		BooleanFunction bf = new IndexedBF(
				"f1",
				Arrays.asList(varA, varB, varC, varD),
				true,
				Arrays.asList(4, 5, 6, 7, 8, 9, 10, 11, 13, 14),
				new ArrayList<Integer>()
		);
		boolean zelimoDobitiProdukte = true;
		MaskBasedBF[] fje = QMCMinimizer.minimize(bf, zelimoDobitiProdukte);
		Assert.assertEquals("Nisu 4 minimalna oblika", 4, fje.length);
	}
	
	@Test
	public void function5Test() {
		BooleanFunction bf = new IndexedBF(
				"f1",
				Arrays.asList(varA, varB, varC, varD),
				true,
				Arrays.asList(4, 5, 7, 9, 10, 11, 13, 14),
				Arrays.asList(6, 8)
		);
		boolean zelimoDobitiProdukte = true;
		MaskBasedBF[] fje = QMCMinimizer.minimize(bf, zelimoDobitiProdukte);
		Assert.assertEquals("Nisu 4 minimalna oblika", 4, fje.length);
	}
	
	@Test
	public void function6Test() {
		BooleanFunction bf = new IndexedBF(
				"f1",
				Arrays.asList(varA, varB, varC, varD),
				true,
				Arrays.asList(0,1,4,5,11,15),
				Arrays.asList(2,6,10)
		);
		boolean zelimoDobitiProdukte = true;
		MaskBasedBF[] fje = QMCMinimizer.minimize(bf, zelimoDobitiProdukte);
		Assert.assertEquals("Nije 1 minimalni oblik", 1, fje.length);
		Set<Mask> expected = Masks.fromString("0x0x", "1x11");
		Assert.assertEquals("Dobivene funkcije nisu iste.", expected, new HashSet<>(fje[0].getMasks()));
	}
	
	@Test
	public void function7Test() {
		BooleanFunction bf = new IndexedBF(
				"f1",
				Arrays.asList(varA, varB, varC, varD),
				true,
				Arrays.asList(0,1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15),
				new ArrayList<Integer>()
		);
		boolean zelimoDobitiProdukte = true;
		MaskBasedBF[] fje = QMCMinimizer.minimize(bf, zelimoDobitiProdukte);
		Assert.assertEquals("Nije 1 minimalni oblik", 1, fje.length);
		Set<Mask> expected = Masks.fromString("xxxx");
		Assert.assertEquals("Dobivene funkcije nisu iste.", expected, new HashSet<>(fje[0].getMasks()));
	}
	
	@Test
	public void function8Test() {
		BooleanFunction bf = new IndexedBF(
				"f1",
				Arrays.asList(varA, varB, varC, varD),
				true,
				Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14),
				new ArrayList<Integer>()
		);
		boolean zelimoDobitiProdukte = true;
		MaskBasedBF[] fje = QMCMinimizer.minimize(bf, zelimoDobitiProdukte);
		Assert.assertEquals("Nije 6 minimalnih oblika", 6, fje.length);
	}
	
	@Test
	public void function9Test() {
		BooleanFunction bf = new IndexedBF(
				"f1",
				Arrays.asList(varA, varB, varC, varD),
				true,
				Arrays.asList(0,1, 2, 3, 4, 6, 7, 8, 9, 11, 12, 13, 14, 15),
				new ArrayList<Integer>()
		);
		boolean zelimoDobitiProdukte = true;
		MaskBasedBF[] fje = QMCMinimizer.minimize(bf, zelimoDobitiProdukte);
		Assert.assertEquals("Nije 8 minimalnih oblika", 6, fje.length);
	}
	
	@Test
	public void function10Test() {
		BooleanFunction bf = new IndexedBF(
				"f1",
				Arrays.asList(varA, varB, varC, varD),
				true,
				Arrays.asList(0, 1, 2, 3, 4, 5, 6, 9, 10, 11, 12, 13, 14),
				new ArrayList<Integer>()
		);
		boolean zelimoDobitiProdukte = true;
		MaskBasedBF[] fje = QMCMinimizer.minimize(bf, zelimoDobitiProdukte);
		Assert.assertEquals("Nije 6 minimalnih oblika", 6, fje.length);
	}
	
	@Test
	public void function11Test() {
		BooleanFunction bf = new IndexedBF(
				"f1",
				Arrays.asList(varA, varB, varC, varD),
				true,
				Arrays.asList(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15),
				new ArrayList<Integer>()
		);
		boolean zelimoDobitiProdukte = true;
		MaskBasedBF[] fje = QMCMinimizer.minimize(bf, zelimoDobitiProdukte);
		Assert.assertEquals("Nije 10 minimalnih oblika", 6, fje.length);
	}
	
	@Test
	public void function12Test() {
		BooleanFunction bf = new IndexedBF(
				"f1",
				Arrays.asList(varA, varB, varC, varD),
				true,
				Arrays.asList(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14),
				new ArrayList<Integer>()
		);
		boolean zelimoDobitiProdukte = true;
		MaskBasedBF[] fje = QMCMinimizer.minimize(bf, zelimoDobitiProdukte);
		Assert.assertEquals("Nije 10 minimalnih oblika", 6, fje.length);
	}
	
	@Test
	public void function13Test() {
		BooleanFunction bf = new IndexedBF(
				"f1",
				Arrays.asList(varA, varB, varC, varD),
				true,
				Arrays.asList(1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14),
				new ArrayList<Integer>()
		);
		boolean zelimoDobitiProdukte = true;
		MaskBasedBF[] fje = QMCMinimizer.minimize(bf, zelimoDobitiProdukte);
		Assert.assertEquals("Nije 4 minimalnih oblika", 6, fje.length);
	}
	
	@Test
	public void maxtermFunction1Test() {
		BooleanFunction bf = new IndexedBF(
				"f1",
				Arrays.asList(varA, varB, varC, varD),
				false,
				Arrays.asList(0,1,2,3,4,6,7,12,13,15),
				new ArrayList<Integer>()
		);
		boolean zelimoDobitiProdukte = false;
		MaskBasedBF[] fje = QMCMinimizer.minimize(bf, zelimoDobitiProdukte);
		Assert.assertEquals("Nisu 2 minimalna oblika", 2, fje.length);
	}
	
	@Test
	public void function14Test() {
		BooleanFunction bf = new IndexedBF(
				"f1",
				Arrays.asList(varA, varB, varC, varD),
				true,
				Arrays.asList(5, 9, 12, 15),
				Arrays.asList(2,7,8,10,13)
		);
		boolean zelimoDobitiProdukte = true;
		MaskBasedBF[] fje = QMCMinimizer.minimize(bf, zelimoDobitiProdukte);
		Assert.assertEquals("Nije jedan minimalni oblik.", 1, fje.length);
		Set<Mask> expected = Masks.fromString("1x0x", "x1x1");
		Assert.assertEquals("Rješenje nije isto", expected, new HashSet<>(fje[0].getMasks()));
	}
}
