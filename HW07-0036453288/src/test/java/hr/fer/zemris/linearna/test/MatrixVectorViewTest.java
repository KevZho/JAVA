package hr.fer.zemris.linearna.test;

import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.LinAlgDefaults;
import hr.fer.zemris.linearna.MatrixVectorView;

import org.junit.Assert;
import org.junit.Test;


public class MatrixVectorViewTest {
	
	@Test
	public void copyRowMatrixTest() {
		IMatrix m1 = new MatrixVectorView(true, LinAlgDefaults.defaultVector(2));
		IMatrix m2 = m1.copy();
		m2.set(0, 0, -1000);
		Assert.assertNotEquals("Matrice su iste", m1, m2);
	}
	
	@Test
	public void copyColMatrixTest() {
		IMatrix m1 = new MatrixVectorView(false, LinAlgDefaults.defaultVector(2));
		IMatrix m2 = m1.copy();
		Assert.assertEquals("Matrice nisu iste", m1, m2);
	}
	
	@Test
	public void newInstanceTest() {
		IMatrix m1 = new MatrixVectorView(false, LinAlgDefaults.defaultVector(2));
		IMatrix m2 = m1.newInstance(2, 2);
		Assert.assertEquals("Matrice nisu iste.", m2, LinAlgDefaults.defaultMatrix(2, 2));
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void getNegativeRowIndexTest() {
		IMatrix m1 = new MatrixVectorView(false, LinAlgDefaults.defaultVector(2));
		m1.get(-1, 0);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void getNegativeColIndexTest() {
		IMatrix m1 = new MatrixVectorView(false, LinAlgDefaults.defaultVector(2));
		m1.get(0, -1);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void getTooBigRowIndexTest() {
		IMatrix m1 = new MatrixVectorView(false, LinAlgDefaults.defaultVector(2));
		m1.get(m1.getRowsCount(), 0);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void getTooBigColIndexTest() {
		IMatrix m1 = new MatrixVectorView(false, LinAlgDefaults.defaultVector(2));
		m1.get(0, m1.getColsCount());
	}
	
	
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void setNegativeRowIndexTest() {
		IMatrix m1 = new MatrixVectorView(false, LinAlgDefaults.defaultVector(2));
		m1.set(-1, 0, -1);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void setNegativeColIndexTest() {
		IMatrix m1 = new MatrixVectorView(false, LinAlgDefaults.defaultVector(2));
		m1.set(0, -1, -1);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void setTooBigRowIndexTest() {
		IMatrix m1 = new MatrixVectorView(false, LinAlgDefaults.defaultVector(2));
		m1.set(m1.getRowsCount(), 0, -1);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void setTooBigColIndexTest() {
		IMatrix m1 = new MatrixVectorView(false, LinAlgDefaults.defaultVector(2));
		m1.set(0, m1.getColsCount(), -1);
	}
	
	@Test
	public void setRowMatrixTest() {
		IMatrix m1 = new MatrixVectorView(true, LinAlgDefaults.defaultVector(2));
		m1.set(1, 0, 1);
	}
	
	@Test
	public void setColMatrixTest() {
		IMatrix m1 = new MatrixVectorView(false, LinAlgDefaults.defaultVector(2));
		m1.set(0, 1, 1);
	}
}
