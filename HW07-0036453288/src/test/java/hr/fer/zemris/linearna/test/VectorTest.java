package hr.fer.zemris.linearna.test;

import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.IncompatibleOperandException;
import hr.fer.zemris.linearna.UnmodifiableObjectException;
import hr.fer.zemris.linearna.Vector;

import org.junit.Assert;
import org.junit.Test;

public class VectorTest {

	private IVector vector;
	
	private void init() {
		vector = new Vector(new double[]{1, 2, 3});
	}
	
	@Test
	public void parseTest() {
		IVector v = Vector.parseSimple("1 2 3");
		double[] array = v.toArray();
		Assert.assertArrayEquals("Vektor nije 1 2 3", new double[] {1.0, 2.0, 3.0}, array, 1E-8);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void parseEmpyStringTest() {
		Vector.parseSimple("");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void parseIllegalStringTest() {
		Vector.parseSimple("1 2 b");
	}
	
	@Test
	public void newInstanceTest() {
		IVector vector = new Vector(new double[] {0.0, 0, 0} );
		IVector newVector =  vector.newInstance(3);
		Assert.assertEquals("Vektor nije 0 0 0", vector, newVector);
	}
	
	@Test
	public void copyTest() {
		init();
		IVector newVector = vector.copy();
		newVector.set(0, 100);
		Assert.assertNotEquals("Vektori su isti.", vector, newVector);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void getNegativeIndexTest() {
		init();
		vector.get(-1);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void getTooBigIndexTest() {
		init();
		vector.get(vector.getDimension());
	}
	
	@Test
	public void getIndexTest() {
		init();
		Assert.assertEquals("Element nije 3.0", 3.0, vector.get(vector.getDimension()-1), 1E-8);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void setNegativeIndexTest() {
		init();
		vector.set(-1, 3);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void setTooBigIndexTest() {
		init();
		vector.set(vector.getDimension(), 3);
	}
	
	@Test
	public void setIndexTest() {
		init();
		vector.set(vector.getDimension()-1, 4);
		Assert.assertEquals("Element nije 4.0", 4.0, vector.get(vector.getDimension()-1), 1E-8);
	}
	
	@Test
	public void sharedArrayTest() {
		double[] array = new double[] {1, 2, 3};
		IVector vector = new Vector(false, true, array);
		array[array.length - 1] = 4;
		Assert.assertArrayEquals(array, vector.toArray(), 1E-8);
	}
	
	@Test(expected=UnmodifiableObjectException.class)
	public void readOnlyTest() {
		IVector vector = new Vector(true, false, new double[] {1, 2, 3});
		vector.set(0, 12);
	}
	
	@Test
	public void hashCodeTest() {
		init();
		IVector newVector = vector.copy();
		Assert.assertEquals("Vektori nemaju isti hash.", vector.hashCode(), newVector.hashCode());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nullReferenceConstructorTest() {
		new Vector(null);
	}
	
	@Test
	public void equalsTest() {
		init();
		Assert.assertTrue("Vektori nisu isti", vector.equals(vector));
		Assert.assertFalse("Vektori su isti", vector.equals(null));
		Assert.assertFalse("Vektori su isti", vector.equals("test"));
		Assert.assertFalse("Vektori su isti", vector.equals(new Vector(new double[]{1,2})));
		Assert.assertFalse("Vektori su isti", vector.equals(new Vector(new double[]{1,2,4})));
	}
	
	@Test(expected=IncompatibleOperandException.class)
	public void nFromHomogeneusTest() {
		vector = new Vector(new double[]{1});
		vector.nFromHomogeneus();
	}
	
	@Test
	public void nFromHomogeneusTest2() {
		vector = new Vector(new double[]{1, 2, 2});
		IVector v = vector.nFromHomogeneus();
		Assert.assertTrue("Vektori nisu isti", v.equals(new Vector(new double[]{0.5,1})));
	}
	
	@Test(expected=ArithmeticException.class)
	public void nFromHomogeneusDivisonByZeroTest() {
		vector = new Vector(new double[]{1, 2, 0});
		vector.nFromHomogeneus();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void copyPartFailTest() {
		init();
		vector.copyPart(-1);
	}
	
	@Test
	public void copyPartTest() {
		init();
		IVector v = vector.copyPart(4);
		Assert.assertTrue("Vektori nisu isti", v.equals(new Vector(new double[]{1,2,3,0})));
	}
	
	@Test(expected=IncompatibleOperandException.class)
	public void addFailTest() {
		init();
		vector.add(Vector.parseSimple("1 2"));
	}
	
	@Test
	public void addTest() {
		init();
		vector.add(Vector.parseSimple("1 2 3"));
		Assert.assertTrue("Vektori nisu isti", vector.equals(new Vector(new double[]{2,4,6})));
	}
	
	@Test(expected=IncompatibleOperandException.class)
	public void nAddFailTest() {
		init();
		vector.nAdd(Vector.parseSimple("1 2"));
	}
	
	@Test
	public void nAddTest() {
		init();
		IVector v = vector.nAdd(Vector.parseSimple("1 2 3"));
		Assert.assertTrue("Vektori nisu isti", v.equals(new Vector(new double[]{2,4,6})));
		Assert.assertTrue("Vektori nisu isti", vector.equals(new Vector(new double[]{1,2,3})));
	}
	
	@Test(expected=IncompatibleOperandException.class)
	public void subFailTest() {
		init();
		vector.sub(Vector.parseSimple("1 2"));
	}
	
	@Test
	public void subTest() {
		init();
		vector.sub(Vector.parseSimple("1 2 3"));
		Assert.assertTrue("Vektori nisu isti", vector.equals(new Vector(new double[]{0,0,0})));
	}
	
	@Test(expected=IncompatibleOperandException.class)
	public void nASubFailTest() {
		init();
		vector.nSub(Vector.parseSimple("1 2"));
	}
	
	@Test
	public void nASubTest() {
		init();
		IVector v = vector.nSub(Vector.parseSimple("1 2 3"));
		Assert.assertTrue("Vektori nisu isti", v.equals(new Vector(new double[]{0,0,0})));
		Assert.assertTrue("Vektori nisu isti", vector.equals(new Vector(new double[]{1,2,3})));
	}
	
	@Test
	public void normTest() {
		IVector v = Vector.parseSimple("3 4");
		Assert.assertEquals("Norma nije 5.", 5, v.norm(), 1E-8);
	}
	
	@Test
	public void toStringTest() {
		init();
		Assert.assertEquals("Ispis nije isti", "(1.000, 2.000, 3.000)", vector.toString());
	}
	
	@Test
	public void toColumnMatrixLiveTest() {	
		init();
		Assert.assertNotNull(vector.toColumnMatrix(true));
	}
	
	@Test
	public void toColumnMatrixTest() {	
		init();
		Assert.assertNotNull(vector.toColumnMatrix(false));
	}
	
	@Test
	public void toRowMatrixLiveTest() {	
		init();
		Assert.assertNotNull(vector.toRowMatrix(true));
	}
	
	@Test
	public void toRowMatrixTest() {	
		init();
		Assert.assertNotNull(vector.toRowMatrix(false));
	}
	
	@Test
	public void scalarMultiply() {
		init();
		vector.scalarMultiply(2);
		Assert.assertArrayEquals(new double[]{2,4,6}, vector.toArray(), 1E-8);
	}
	
	@Test
	public void nScalarMultiply() {
		init();
		IVector v = vector.nScalarMultiply(2);
		Assert.assertArrayEquals(new double[]{2,4,6}, v.toArray(), 1E-8);
		Assert.assertArrayEquals(new double[]{1,2,3}, vector.toArray(), 1E-8);
	}
	
	@Test(expected=ArithmeticException.class)
	public void normalizeFailTest() {
		IVector v = Vector.parseSimple("0 0 0");
		v.normalize();
	}
	
	@Test(expected=ArithmeticException.class)
	public void nNormalizeFailTest() {
		IVector v = Vector.parseSimple("0 0 0");
		v.nNormalize();
	}
	
	@Test
	public void normalizeTest() {
		IVector v = Vector.parseSimple("3 4");
		v.normalize();
		Assert.assertArrayEquals(new double[]{0.6, 0.8}, v.toArray(), 1E-8);
	}
	
	@Test
	public void nNormalizeTest() {
		IVector v = Vector.parseSimple("3 4");
		IVector v2 = v.nNormalize();
		Assert.assertArrayEquals(new double[]{0.6, 0.8}, v2.toArray(), 1E-8);
		Assert.assertArrayEquals(new double[]{3, 4}, v.toArray(), 1E-8);
	}
	
	@Test(expected=IncompatibleOperandException.class)
	public void nVectorProductFailTest() {
		init();
		vector.nVectorProduct(Vector.parseSimple("1 2"));
	}
	
	@Test(expected=IncompatibleOperandException.class)
	public void nVectorProductFailTest2() {
		IVector vector = Vector.parseSimple("1 2 3 4");
		vector.nVectorProduct(Vector.parseSimple("1 2 3"));
	}
	
	@Test
	public void nVectorProductTest() {
		IVector v = Vector.parseSimple("3 -3 1");
		IVector res = v.nVectorProduct(Vector.parseSimple("4 9 2"));
		Assert.assertEquals("Vektori nisu isti.", Vector.parseSimple("-15 -2 39") , res);
	}
	
	@Test(expected=IncompatibleOperandException.class)
	public void scalarProductFailTest() {
		init();
		vector.scalarProduct(Vector.parseSimple("1 2"));
	}
	
	@Test
	public void scalarProductTest() {
		init();
		Assert.assertEquals("Skalarni produkt nije 14", Double.valueOf(14), vector.scalarProduct(Vector.parseSimple("1 2 3")), 1E-8);
	}
	
	@Test(expected=IncompatibleOperandException.class)
	public void cosineFailTest() {
		init();
		vector.cosine(Vector.parseSimple("1 2"));
	}
	
	@Test(expected=ArithmeticException.class)
	public void cosineDivisionByZero() {
		init();
		vector.cosine(Vector.parseSimple("0 0 0"));
	}
	
	@Test
	public void cosineTest() {
		init();
		double cos = vector.cosine(Vector.parseSimple("2 3 5"));
		Assert.assertEquals("Kosinus kuta nije dobar.", 23.0/(Math.sqrt(1*1 + 2*2 + 3*3) * Math.sqrt(2*2 + 3*3 + 5*5)) ,cos, 1E-8);
	}
}
