package hr.fer.zemris.java.sorters.test;

import hr.fer.zemris.java.sorters.QSortParallel2;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

public class QSortParallelTest {

	int[] generateNumbers(int size) {
		Random rand = new Random();
		int[] data = new int[size];
		
		for (int i = 0; i < size; i++) {
			data[i] = rand.nextInt();
		}
		return data;
	}
	
	@Test
	public void smallSortTest() {
		int data[] = generateNumbers(15000);
		QSortParallel2.sort(data);
		Assert.assertTrue(QSortParallel2.isSorted(data));
	}
	
	@Test
	public void mediumSortTest() {
		int data[] = generateNumbers(150000);
		QSortParallel2.sort(data);
		Assert.assertTrue(QSortParallel2.isSorted(data));
	}
	
	@Test
	public void bigSortTest() {
		int data[] = generateNumbers(1500000);
		QSortParallel2.sort(data);
		Assert.assertTrue(QSortParallel2.isSorted(data));
	}
	
	@Test
	public void testIsSorted() {
		int[] data = {1, 2, 4, 3};
		Assert.assertFalse(QSortParallel2.isSorted(data));
	}
}
