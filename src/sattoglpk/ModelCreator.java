package sattoglpk;

import java.util.ArrayList;

public class ModelCreator {

	/** Creates a glpk executable model from a list of 3Sat predicates. */
	public static void createModel(ArrayList<Predicate3sat> predicates) {

		for (Predicate3sat predicate3sat : predicates) {
			System.out.println(predicate3sat.data1 + " / " + predicate3sat.data2 + " / " + predicate3sat.data3);
		}
	}

}
