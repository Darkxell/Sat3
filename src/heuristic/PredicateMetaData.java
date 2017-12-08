package heuristic;

import sattoglpk.Predicate3sat;

public class PredicateMetaData {
	
	private Predicate3sat predicate;
	private int clauseSize;
	private boolean isTrue;
	private boolean isD1True;
	private boolean isD2True;
	private boolean isD3True;
	private boolean isD1False;
	private boolean isD2False;
	private boolean isD3False;

	public PredicateMetaData(Predicate3sat predicate) {
		this.predicate = predicate;
		clauseSize = 3;
		isTrue = false;
		isD1True = false;
		isD2True = false;
		isD3True = false;
		isD1False = false;
		isD2False = false;
		isD3False = false;
	}
	
	@Override
	public String toString() {
		return predicate.data1 + " / " + predicate.data2 + " / " + predicate.data3;
		
	}
	
	public void updateDatas(int i, DataCountValues values) {
		if(predicate.data1 == i) {
			if(!isTrue) {
				values.removeCount(predicate.data1);
				if(!isD2False) values.removeCount(predicate.data2);
				if(!isD3False) values.removeCount(predicate.data3);
			}
			isTrue = true;
			clauseSize -=1;
			isD1True = true;
		}
		if (predicate.data2 == i) {
			if(!isTrue) {
				if(!isD1False) values.removeCount(predicate.data1);
				values.removeCount(predicate.data2);
				if(!isD3False) values.removeCount(predicate.data3);
			}
			isTrue = true;
			clauseSize -=1;
			isD2True = true;
		}
		if (predicate.data3 == i) {
			if(!isTrue) {
				if(!isD1False) values.removeCount(predicate.data1);
				if(!isD2False) values.removeCount(predicate.data2);
				values.removeCount(predicate.data3);
			}
			isTrue = true;
			clauseSize -=1;
			isD3True = true;
		}
		if (predicate.data1 == -i) {
			clauseSize -=1;
			isD1False = true;
			if(!isTrue) values.removeCount(predicate.data1);
		}
		if (predicate.data2 == -i) {
			clauseSize -=1;
			isD2False = true;
			if(!isTrue) values.removeCount(predicate.data2);
		}
		if (predicate.data3 == -i) {
			clauseSize -=1;
			isD3False = true;
			if(!isTrue) values.removeCount(predicate.data3);
		}
	}
	
	public void reverseUpdateDatas(int i, DataCountValues values) {
		if(predicate.data1 == i) {
			if (!isD2True && !isD3True) isTrue = false;
			isD1True = false;
			clauseSize +=1;
			if(!isTrue) {
				values.addCount(predicate.data1);
				if(!isD2False) values.addCount(predicate.data2);
				if(!isD3False) values.addCount(predicate.data3);
			}
			
		}
		if(predicate.data2 == i) {
			if (!isD1True && !isD3True) isTrue = false;
			isD2True = false;
			clauseSize +=1;
			if(!isTrue) {
				if(!isD1False) values.addCount(predicate.data1);
				values.addCount(predicate.data2);
				if(!isD3False) values.addCount(predicate.data3);
			}
			
		}
		if(predicate.data3 == i) {
			if (!isD2True && !isD1True) isTrue = false;
			isD3True = false;
			clauseSize +=1;
			if(!isTrue) {
				if(!isD1False) values.addCount(predicate.data1);
				if(!isD2False) values.addCount(predicate.data2);
				values.addCount(predicate.data3);
			}
			
		}
		if (predicate.data1 == -i) {
			clauseSize +=1;
			isD1False = false;
			if(!isTrue) values.addCount(predicate.data1);
		}
		if (predicate.data2 == -i) {
			clauseSize +=1;
			isD2False = false;
			if(!isTrue) values.addCount(predicate.data2);
		}
		if (predicate.data3 == -i) {
			clauseSize +=1;
			isD3False = false;
			if(!isTrue) values.addCount(predicate.data3);
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
