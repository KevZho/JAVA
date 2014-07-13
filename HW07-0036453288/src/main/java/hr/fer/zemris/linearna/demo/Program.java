package hr.fer.zemris.linearna.demo;

import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.Matrix;

public class Program {

	public static void main(String[] args) {
		IMatrix m = Matrix.parseSimple("1 2 3 | 2 1 3 | 1 2 10");
		System.out.println(m.determinant());

	}

}
