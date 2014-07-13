package hr.fer.zemris.java.tecaj_06.rays;

/**
 * Razred koji implementira sferu.
 * @author Igor Smolkovič
 *
 */
public class Sphere extends GraphicalObject {

	/**
	 * Centar kugle.
	 */
	private Point3D center;

	/**
	 * Radiju kugle.
	 */
	private double radius;

	/**
	 * Faktor difuzne komponente crvene boje.
	 */
	private double kdr;

	/**
	 * Faktor difuzne komponente zelene boje.
	 */
	private double kdg;

	/**
	 * Faktor difuzne komponente plave boje.
	 */
	private double kdb;

	/**
	 * Faktor reflektirajuce komponente crvene boje.
	 */
	private double krr;

	/**
	 * Faktor reflektirajuce komponente zelene boje.
	 */
	private double krg;

	/**
	 * Faktor reflektirajuce komponente plave boje.
	 */
	private double krb;

	/**
	 * Indeks loma.
	 */
	private double krn;

	/**
	 * Konstanta za usporedbu double brojeva.
	 */
	private static final double EPS = 0.005;

	/**
	 * Konstruktor.
	 * @param center centar sfere.
	 * @param radius radijus.
	 * @param kdr faktor difuzne komponente crvene boje.
	 * @param kdg faktor difuzne komponente zelene boje.
	 * @param kdb faktor difuzne komponente plave boje.
	 * @param krr faktor reflektirajuce komponente crvene boje.
	 * @param krg faktor reflektirajuce komponente zelene boje.
	 * @param krb faktor reflektirajuce komponente plave boje.
	 * @param krn indeks loma.
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg,
			double kdb, double krr, double krg, double krb, double krn) {
		super();
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {
		/**
		 * Preuzeto iz knjige za IRG.
		 */
		double a = ray.direction.scalarProduct(ray.direction);
		double b = ray.direction.scalarMultiply(2).scalarProduct(ray.start.sub(this.center));
		double c = ray.start.sub(this.center).scalarProduct(ray.start.sub(this.center)) - radius * radius;

		double determinant = b * b - 4 * a * c;
		if (determinant < 0) {
			return null;
		}

		double lambda1 = (-b + Math.sqrt(determinant)) / (2 * a);
		double lambda2 = (-b - Math.sqrt(determinant)) / (2 * a);

		if (Math.abs(lambda1 - lambda2) > EPS && lambda1 > 0 && lambda2 > 0) {
			double lambda = Math.min(lambda1, lambda2);
			return calcIntersection(ray, lambda, true);
		} else if (Math.abs(lambda1 - lambda2) > EPS) { // ovo je unutarnje sjeciste.
			RayIntersection first = calcIntersection(ray, lambda1, false);
			RayIntersection second = calcIntersection(ray, lambda2, false);

			if (first.getDistance() > second.getDistance()) {
				return second;
			}
			return first;
		} else if (Math.abs(lambda1 - lambda2) < EPS) { // ako su ista
			return calcIntersection(ray, lambda1, true);
		}

		return null;
	}

	/**
	 * Metoda koja instancira dobiveno presjeciste.
	 * @param ray zraka za koju se traži presjecište.
	 * @param lambda lambda dobiven kod izračuna presjecišta.
	 * @param outer true ako je vanjsko sjecište, inače false.
	 * @return instanca razreda RayIntersection.
	 */
	private RayIntersection calcIntersection(Ray ray, double lambda, boolean outer) {
		Point3D intersection = ray.start.add(ray.direction.scalarMultiply(lambda));
		Point3D diff = intersection.sub(ray.start);

		return new RayIntersection(intersection, diff.norm(), outer) {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public Point3D getNormal() {
				return getPoint().sub(center);
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public double getKrr() {
				return krr;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public double getKrn() {
				return krn;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public double getKrg() {
				return krg;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public double getKrb() {
				return krb;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public double getKdr() {
				return kdr;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public double getKdg() {
				return kdg;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public double getKdb() {
				return kdb;
			}
		};
	}

}
