package sattoglpk;

import java.util.ArrayList;

public class ModelCreator {

	/** Creates a glpk executable model from a list of 3Sat predicates. */
	public static void createModel(ArrayList<Predicate3sat> predicates) {
		int uniquevar = 1000;
		String model = "#Model for a 3SAT problem\n";
		for (int i = 1; i < 76; ++i) {
			model += "var clause" + i + " >= 0, <= 1, integer;\n";
		}
		model += "\n#Constraints";
		for (Predicate3sat p : predicates) {
			model += "\ns.t. c"+uniquevar+": " + getVarfor(p.data1) + " + " + getVarfor(p.data2) + " + " + getVarfor(p.data3)
					+ " >= 1;";
			++uniquevar;
		}
		model += "\n\n#Objective\nmaximize anything : 1;\n";
		
		System.out.println(model);
	}

	private static String getVarfor(int x) {
		if (x > 0)
			return "clause" + x;
		else
			return "1 - clause" + Math.abs(x);
	}

}
