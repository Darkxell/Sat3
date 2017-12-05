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
	
	public void updateDatas(int i) {
		if(predicate.data1 == i || predicate.data2 == i || predicate.data3 == i) {
			isTrue = true;
		}
		if (predicate.data1 == -i) {
			clauseSize -=1;
			isD1False = true;
		}
		if (predicate.data2 == -i) {
			clauseSize -=1;
			isD2False = true;
		}
		if (predicate.data3 == -i) {
			clauseSize -=1;
			isD3False = true;
		}
	}
	
	public int getUnitary() {
		if(clauseSize == 1) {
			if(isD1False && isD2False) return predicate.data3;
			if(isD2False && isD3False) return predicate.data1;
			if(isD3False && isD1False) return predicate.data2;
		}
		return 0;
	}

	public boolean isTrue() {
		return isTrue;
	}

	public int getClauseSize() {
		return clauseSize;
	}
	

}
