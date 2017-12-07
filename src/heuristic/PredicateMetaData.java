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
	
	public void updateDatas(int i, DataCountValues values) {
		if(predicate.data1 == i || predicate.data2 == i || predicate.data3 == i) {
			isTrue = true;
			clauseSize -=1;
			if(!isD1False) values.removeCount(predicate.data1);
			if(!isD2False) values.removeCount(predicate.data2);
			if(!isD3False) values.removeCount(predicate.data3);
		}
		else if (predicate.data1 == -i) {
			clauseSize -=1;
			isD1False = true;
			values.removeCount(predicate.data1);
		}
		else if (predicate.data2 == -i) {
			clauseSize -=1;
			isD2False = true;
			values.removeCount(predicate.data2);
		}
		else if (predicate.data3 == -i) {
			clauseSize -=1;
			isD3False = true;
			values.removeCount(predicate.data3);
		}
	}
	
	public void reverseUpdateDatas(int i, DataCountValues values) {
		if(predicate.data1 == i || predicate.data2 == i || predicate.data3 == i) {
			isTrue = false;
			clauseSize +=1;
			if(!isD1False) values.addCount(predicate.data1);
			if(!isD2False) values.addCount(predicate.data2);
			if(!isD3False) values.addCount(predicate.data3);
		}
		if (predicate.data1 == -i) {
			clauseSize +=1;
			isD1False = false;
			values.addCount(predicate.data1);
		}
		if (predicate.data2 == -i) {
			clauseSize +=1;
			isD2False = false;
			values.addCount(predicate.data2);
		}
		if (predicate.data3 == -i) {
			clauseSize +=1;
			isD3False = false;
			values.addCount(predicate.data3);
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
