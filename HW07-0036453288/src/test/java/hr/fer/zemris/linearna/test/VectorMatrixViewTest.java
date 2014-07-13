package hr.fer.zemris.linearna.test;

import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.IncompatibleOperandException;
import hr.fer.zemris.linearna.LinAlgDefaults;
import hr.fer.zemris.linearna.VectorMatrixView;

import org.junit.Assert;
import org.junit.Test;

public class VectorMatrixViewTest {
	
	@Test
	public void copyTest() {
		IVector v1 = new VectorMatrixView(LinAlgDefaults.defaultMatrix(3, 1));
		IVector v2 = v1.copy();
		Assert.assertEquals("Vektori nisu isti.", v2, v1);
	}
	
	@Test
	public void columnMatrixTest() {
		IVector v1 = new VectorMatrixView(LinAlgDefaults.defaultMatrix(1, 3));
		IVector v2 = LinAlgDefaults.defaultVector(3);
		Assert.assertEquals("Vektori nisu isti.", v2, v1);
	}
	
	@Test
	public void newInstanceTest() {
		IVector v1 = new VectorMatrixView(LinAlgDefaults.defaultMatrix(1, 3));
		IVector v2 = v1.newInstance(3);
		Assert.assertEquals("Vektori nisu isti.", v2, v1);
	}
	
	@Test(expected=IncompatibleOperandException.class)
	public void vectorMatrixConstructorFailTest() {
		new VectorMatrixView(LinAlgDefaults.defaultMatrix(2, 3));
	}
	
	@Test(expected=IncompatibleOperandException.class)
	public void vectorMatrixConstructorFailTest2() {
		new VectorMatrixView(LinAlgDefaults.defaultMatrix(3, 2));
	}
	
	@Test
	public void setRowMatrixTest() {
		IVector v1 = new VectorMatrixView(LinAlgDefaults.defaultMatrix(1, 3));
		IVector v2 = v1.newInstance(3);
		v1.set(1, 1);
		Assert.assertNotEquals("Vektori su isti.", v2, v1);
	}
	
	@Test
	public void setColumnMatrixTest() {
		IVector v1 = new VectorMatrixView(LinAlgDefaults.defaultMatrix(3, 1));
		IVector v2 = v1.newInstance(3);
		v1.set(1, 1);
		Assert.assertNotEquals("Vektori su isti.", v2, v1);
	}
}
