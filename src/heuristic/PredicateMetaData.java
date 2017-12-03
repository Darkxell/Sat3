package heuristic;

import sattoglpk.Predicate3sat;

public class PredicateMetaData {
	
	private Predicate3sat predicate;
	private int clauseSize;
	private boolean isTrue;
	private boolean isD1False;
	private boolean isD2False;
	private boolean isD3False;
	
	public PredicateMetaData(Predicate3sat predicate) {
		this.predicate = predicate;
		clauseSize = 3;
		isTrue = false;
		isD1False = false;
		isD2False = false;
		isD3False = false;
	}
	
	@Override
	public String toString() {
		return predicate.data1 + " / " + predicate.data2 + " / " + predicate.data3;
		
	}

}
