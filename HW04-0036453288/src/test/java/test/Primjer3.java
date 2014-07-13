package test;

import java.util.Arrays;

import hr.fer.zemris.bool.BooleanFunction;
import hr.fer.zemris.bool.BooleanVariable;
import hr.fer.zemris.bool.Masks;
import hr.fer.zemris.bool.fimpl.MaskBasedBF;

public class Primjer3 {
	public static void main(String[] args) {
		BooleanVariable varA = new BooleanVariable("A");
		BooleanVariable varB = new BooleanVariable("B");
		BooleanVariable varC = new BooleanVariable("C");
		BooleanVariable varD = new BooleanVariable("D");
		
		
		BooleanFunction f1 = new MaskBasedBF(
				"f1",
				Arrays.asList(varA, varB, varC, varD),
				true,
				Masks.fromStrings("00x0", "1xx1"),
				Masks.fromStrings("10x0")
		);
		
		for(Integer i : f1.mintermIterable()) { // Ispis: 0, 2, 9, 11, 13, 15
			System.out.println("Imam minterm: "+i);
		}
		
		for(Integer i : f1.maxtermIterable()) { // Ispis: 1, 3, 4, 5, 6, 7, 12, 14
			System.out.println("Imam maxterm: "+i);
		}
		
		for(Integer i : f1.dontcareIterable()) { // Ispis: 8, 10
			System.out.println("Imam dontcare: "+i);
		}

	}
}
