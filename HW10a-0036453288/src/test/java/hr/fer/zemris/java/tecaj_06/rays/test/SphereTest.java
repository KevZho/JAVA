package hr.fer.zemris.java.tecaj_06.rays.test;

import hr.fer.zemris.java.tecaj_06.rays.Point3D;
import hr.fer.zemris.java.tecaj_06.rays.Ray;
import hr.fer.zemris.java.tecaj_06.rays.RayIntersection;
import hr.fer.zemris.java.tecaj_06.rays.Sphere;

import org.junit.Assert;
import org.junit.Test;

public class SphereTest {


	@Test
	public void outerIntersect() {
		Sphere sphere = new Sphere(new Point3D(), 1, 0, 0, 0, 0, 0, 0, 0);
		Ray ray = Ray.fromPoints(new Point3D(10, 0, 0), new Point3D(9, 0, 0));
		
		RayIntersection intersection = sphere.findClosestRayIntersection(ray);
		Assert.assertNotNull(intersection);
		
		Assert.assertEquals("Udaljenost nije 9", Double.valueOf(9), intersection.getDistance(), 1E-8);
		Assert.assertTrue(intersection.isOuter());
	}
	
	@Test
	public void innerIntersect() {
		Sphere sphere = new Sphere(new Point3D(), 1, 0, 0, 0, 0, 0, 0, 0);
		Ray ray = Ray.fromPoints(new Point3D(0, 0, 0), new Point3D(9, 0, 0));
		
		RayIntersection intersection = sphere.findClosestRayIntersection(ray);
		Assert.assertNotNull(intersection);
		
		Assert.assertEquals("Udaljenost nije 1", Double.valueOf(1), intersection.getDistance(), 1E-8);
		Assert.assertFalse(intersection.isOuter());
	}
		
	@Test
	public void noIntersection() {
		Sphere sphere = new Sphere(new Point3D(), 1, 0, 0, 0, 0, 0, 0, 0);
		Ray ray = Ray.fromPoints(new Point3D(5, 5, 0), new Point3D(5, 5, 2));
		
		RayIntersection intersection = sphere.findClosestRayIntersection(ray);
		Assert.assertNull(intersection);
	}
	
	@Test
	public void tangentTest() {
		Sphere sphere = new Sphere(new Point3D(), 1, 0, 0, 0, 0, 0, 0, 0);
		Ray ray = Ray.fromPoints(new Point3D(10, 1, 0), new Point3D(9, 1, 0));
		
		RayIntersection intersection = sphere.findClosestRayIntersection(ray);
		Assert.assertNotNull(intersection);
	}
}
