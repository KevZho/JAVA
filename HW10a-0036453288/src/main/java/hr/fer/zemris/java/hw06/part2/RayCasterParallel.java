package hr.fer.zemris.java.hw06.part2;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

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
 * Razred koji sadrži paralelnu implementaciju ray castera.
 * @author Igor Smolkovič
 *
 */
public class RayCasterParallel {

	/**
	 * Konstanta za usporedbu sličnosti double brojeva.
	 */
	private static final double EPS = 10E-4;

	/**
	 * Metoda koja se poziva prilikom pokretanja računala.
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
	 * Razred koji rekruzivno dijeli posao dok je dovoljno veliki, a onda prelazi na slijedno
	 * izvođenje.
	 * @author Igor Smolkovič
	 *
	 */
	public static class Job extends RecursiveAction {

		/**
		 * UID
		 */
		private static final long serialVersionUID = -7374172335831968612L;

		/**
		 * Broj piksela u stupcu.
		 */
		private int height;

		/**
		 * Broj piksela u retku.
		 */
		private int width;

		/**
		 * Horizontalna sirina promatranog podrucja.
		 */
		private double horizontal;

		/**
		 * Vertikalna visina promatranog podrucja.
		 */
		private double vertical;

		/**
		 * Os x.
		 */
		private Point3D xAxis;

		/**
		 * Os y.
		 */
		private Point3D yAxis;

		/**
		 * Položaj gornje lijeve točke u ravnini.
		 */
		private Point3D screenCorner;

		/**
		 * Položaj promatrača.
		 */
		private Point3D eye;

		/**
		 * Polje za crvene boje.
		 */
		private short[] red;

		/**
		 * Polje za zelene boje.
		 */
		private short[] green;

		/**
		 * Polje za plave boje.
		 */
		private short[] blue;

		/**
		 * Scena.
		 */
		private Scene scene; 

		/**
		 * Koliko maksimalno mora biti redaka da bi se izvodilo paralelno.
		 */
		private static final int TRESHOLD = 16;

		/**
		 * Prvi redak od kojeg počinje izračun.
		 */
		private int yMin;
		/**
		 * Zadnji redak do kojeg ide izračun.
		 */
		private int yMax;


		/**
		 * Konstruktor.
		 * @param height broj piksela u stupcu.
		 * @param width broj piksela u retku.
		 * @param horizontal sirina promatranog podrucja.
		 * @param vertical visina promatranoc podrucja.
		 * @param xAxis os x.
		 * @param yAxis os y.
		 * @param screenCorner pozicija elementa (0,0) u ravnini.
		 * @param eye polozaj promatraca.
		 * @param red polje za crvene boje.
		 * @param green polje za zelene boje.
		 * @param blue polje za plave boje.
		 * @param scene scena koja se promatra.
		 * @param yMin indeks prvog retka od kojeg se računa.
		 * @param yMax indeks posljednjeg retka od kojeg se računa.
		 */
		public Job(int height, int width, double horizontal, double vertical,
				Point3D xAxis, Point3D yAxis, Point3D screenCorner,
				Point3D eye, short[] red, short[] green, short[] blue,
				Scene scene, int yMin, int yMax) {
			super();
			this.height = height;
			this.width = width;
			this.horizontal = horizontal;
			this.vertical = vertical;
			this.xAxis = xAxis;
			this.yAxis = yAxis;
			this.screenCorner = screenCorner;
			this.eye = eye;
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.scene = scene;
			this.yMin = yMin;
			this.yMax = yMax;
		}

		/**
		 * Metoda koja raspoređuje posao.
		 */
		@Override
		protected void compute() {
			if (yMax - yMin + 1 <= TRESHOLD) {
				computeDirect(); // izvodi se sljedno.
				return;
			}
			/**
			 * Izvodi se paralelno.
			 */
			invokeAll(
				new Job(height, width, horizontal, vertical, xAxis, yAxis, screenCorner, eye, red, green, blue, scene, yMin, yMin + (yMax - yMin) / 2),
				new Job(height, width, horizontal, vertical, xAxis, yAxis, screenCorner, eye, red, green, blue, scene, yMin + (yMax - yMin) / 2 + 1, yMax)
			);

		}

		/**
		 * Metoda koja radi sljedno računje presjecišta zrake i objekata u sceni.
		 */
		private void computeDirect() {
			short[] rgb = new short[3];
			for (int y = yMin; y <= yMax; y++) {
				for (int x = 0; x < width; x++) {
					int offset = y * width + x;
					double first = horizontal * (double) x / (double)(width - 1);
					double second = vertical * (double) y / (double)(height - 1);
					Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply(first)).sub(yAxis.scalarMultiply(second));

					Ray ray = Ray.fromPoints(eye, screenPoint);
					tracer(scene, ray, rgb, eye);

					red[offset] = rgb[0] > 255 ? 255 : rgb[0];
					green[offset] = rgb[1] > 255 ? 255 : rgb[1];
					blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
				}
			}
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

				long t0 = System.currentTimeMillis();

				System.out.println("Započinjem izračune...");
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];

				Point3D og = view.sub(eye).normalize();
				Point3D vuv = viewUp.normalize();

				Point3D yAxis = vuv.sub(og.scalarMultiply(og.scalarProduct(vuv))).normalize();
				Point3D xAxis = og.vectorProduct(yAxis).normalize();
				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2)).add(yAxis.scalarMultiply(vertical / 2));
				Scene scene = RayTracerViewer.createPredefinedScene();

				ForkJoinPool pool = new ForkJoinPool();
				pool.invoke(new Job(height, width, horizontal, vertical, xAxis, yAxis, screenCorner, eye, red, green, blue, scene, 0, width - 1));
				pool.shutdown();

				long t1 = System.currentTimeMillis();
				System.out.println("Vrijeme: " + (t1 - t0) + " ms");

				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}

		};

	}
}
