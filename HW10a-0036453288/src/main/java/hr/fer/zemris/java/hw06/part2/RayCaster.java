package hr.fer.zemris.java.hw06.part2;

import hr.fer.zemris.java.tecaj_06.rays.GraphicalObject;
import hr.fer.zemris.java.tecaj_06.rays.IRayTracerProducer;
import hr.fer.zemris.java.tecaj_06.rays.IRayTracerResultObserver;
import hr.fer.zemris.java.tecaj_06.rays.LightSource;
import hr.fer.zemris.java.tecaj_06.rays.Point3D;
import hr.fer.zemris.java.tecaj_06.rays.Ray;
import hr.fer.zemris.java.tecaj_06.rays.RayIntersection;
import hr.fer.zemris.java.tecaj_06.rays.RayTracerViewer;
import hr.fer.zemris.java.tecaj_06.rays.Scene;

/**
 * Implementacija slijednog ray castera.
 * @author Igor Smolkovič
 *
 */
public class RayCaster {

	/**
	 * Konstanta za usporedbu sličnosti double brojeva.
	 */
	private static final double EPS = 10E-4;

	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 * @param args Argumenti komandne linije. Ne koriste se.
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(),
				new Point3D(10, 0, 0),
				new Point3D(0, 0, 0),
				new Point3D(0, 0, 10),
				20, 20);
	}

	/**
	 * Razred koji računa boje za pojedine točke ekrena.
	 * @return instaca razreda IRayTracerProducer.
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {

			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp,
					double horizontal, double vertical, int width, int height,
					long requestNo, IRayTracerResultObserver observer) {
				System.out.println("Započinjem izračune...");
				long t0 = System.currentTimeMillis();

				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];

				Point3D og = view.sub(eye).normalize();
				Point3D vuv = viewUp.normalize();

				Point3D yAxis = vuv.sub(og.scalarMultiply(og.scalarProduct(vuv))).normalize();
				Point3D xAxis = og.vectorProduct(yAxis).normalize();
				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2)).add(yAxis.scalarMultiply(vertical / 2));

				Scene scene = RayTracerViewer.createPredefinedScene();

				short[] rgb = new short[3];
				int offset = 0;
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						double first = horizontal * (double) x / (double)(width - 1);
						double second = vertical * (double) y / (double)(height - 1);
						Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply(first)).sub(yAxis.scalarMultiply(second));

						Ray ray = Ray.fromPoints(eye, screenPoint);
						tracer(scene, ray, rgb, eye);

						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
						offset++;
					}
				}

				long t1 = System.currentTimeMillis();
				System.out.println("Vrijeme: " + (t1 - t0) + " ms");

				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}

			/**
			 * Metoda koja pronalazi najbliže presjecište zrake i nekog objekta, ako je pronađeno
			 * računa boju u toj točki.
			 * @param scene scena u kojoj se promatraju objekti.
			 * @param ray zraka za koju se traži presjecište.
			 * @param rgb polje u koje se spremaju komponente boje.
			 * @param eye polozaj promatraca.
			 */
			private void tracer(Scene scene, Ray ray, short[] rgb, Point3D eye) {
				/**
				 * Boja pozadine.
				 */
				rgb[0] = rgb[1] = rgb[2] = 0;

				/**
				 * Pronađi sjeciste s najblizim objektom.
				 */
				RayIntersection s = findIntersection(scene, ray);
				if (s == null) {
					return;
				}

				rgb[0] = rgb[1] = rgb[2] = 15;

				/**
				 * Odredi boju.
				 */
				for (LightSource source : scene.getLights()) {
					Ray lightRay = Ray.fromPoints(s.getPoint(), source.getPoint());
					RayIntersection sPrime = findIntersection(scene, lightRay);
					if (sPrime != null) { // ako postoji
						double sDistance = s.getPoint().sub(source.getPoint()).norm();
						double sPrimeDist = sPrime.getPoint().sub(source.getPoint()).norm();
						if (Math.abs(sDistance - sPrimeDist) < EPS) {
							Point3D n = sPrime.getNormal().normalize();
							Point3D l = source.getPoint().sub(sPrime.getPoint());
							double id = Math.max(n.scalarProduct(l.normalize()), 0);

							Point3D v = eye.sub(sPrime.getPoint()).normalize();
							Point3D r = n.scalarMultiply(2).scalarMultiply(l.scalarProduct(n)).sub(l).normalize();
							double ir = Math.max(Math.pow(r.scalarProduct(v), sPrime.getKrn()), 0);

							rgb[0] += source.getR() * (sPrime.getKdr() * id + sPrime.getKrr() * ir);
							rgb[1] += source.getG() * (sPrime.getKdg() * id + sPrime.getKrg() * ir);
							rgb[2] += source.getB() * (sPrime.getKdb() * id + sPrime.getKrb() * ir);
						}
					}
				}
			}

			/**
			 * Metoda koja traži najbliže presjecište zrake i bilo kojeg objekta u sceni.
			 * @param scene scena u kojoj se promatraju objekti.
			 * @param ray zraka za koju se traži presjecište.
			 * @return najbliže presjecište ako postoji, inače null.
			 */
			private RayIntersection findIntersection(Scene scene, Ray ray) {
				RayIntersection intersection = null;
				for (GraphicalObject s : scene.getObjects()) {
					RayIntersection local = s.findClosestRayIntersection(ray);
					if (local != null) {
						if (intersection == null) {
							intersection = local;
						} else {
							if (local.getDistance() < intersection.getDistance()) {
								intersection = local;
							}
						}
					}
				}
				return intersection;
			}
		};
 	}
}
