package heuristic;

import sattoglpk.Predicate3sat;

public class PredicateMetaData {
	
	private Predicate3sat predicate;
	private boolean isTrue;
	private boolean isD1True;
	private boolean isD2True;
	private boolean isD3True;
	private boolean isD1False;
	private boolean isD2False;
	private boolean isD3False;

	public PredicateMetaData(Predicate3sat predicate) {
		this.predicate = predicate;
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
		return predicate.data1 + " / " + predicate.data2 + " / " + predicate.data3 + "(" + isD1True + "/" + isD2True + "/" + isD3True + "/" + isD1False + "/" + isD2False + "/" + isD3False + ")";
		
	}
	
	public void updateDatas(int i, DataCountValues values) {
		if(predicate.data1 == i) {
			if(!isTrue) {
				values.removeCount(predicate.data1);
				if(!isD2False) values.removeCount(predicate.data2);
				if(!isD3False) values.removeCount(predicate.data3);
			}
			isTrue = true;
			isD1True = true;
			isD1False = false;
		}
		if (predicate.data2 == i) {
			if(!isTrue) {
				if(!isD1False) values.removeCount(predicate.data1);
				values.removeCount(predicate.data2);
				if(!isD3False) values.removeCount(predicate.data3);
			}
			isTrue = true;
			isD2True = true;
			isD2False = false;
		}
		if (predicate.data3 == i) {
			if(!isTrue) {
				if(!isD1False) values.removeCount(predicate.data1);
				if(!isD2False) values.removeCount(predicate.data2);
				values.removeCount(predicate.data3);
			}
			isTrue = true;
			isD3True = true;
			isD3False = false;
		}
		if (predicate.data1 == -i) {
			isD1False = true;
			isD1True = false;
			if(!isTrue) values.removeCount(predicate.data1);
		}
		if (predicate.data2 == -i) {
			isD2False = true;
			isD2True = false;
			if(!isTrue) values.removeCount(predicate.data2);
		}
		if (predicate.data3 == -i) {
			isD3False = true;
			isD3True = false;
			if(!isTrue) values.removeCount(predicate.data3);
		}
	}
	
	public void reverseUpdateDatas(int i, DataCountValues values) {
		if(predicate.data1 == i) {
			if (!isD2True && !isD3True) isTrue = false;
			isD1True = false;
			if(!isTrue) {
				values.addCount(predicate.data1);
				if(!isD2False) values.addCount(predicate.data2);
				if(!isD3False) values.addCount(predicate.data3);
			}
			
		}
		if(predicate.data2 == i) {
			if (!isD1True && !isD3True) isTrue = false;
			isD2True = false;
			if(!isTrue) {
				if(!isD1False) values.addCount(predicate.data1);
				values.addCount(predicate.data2);
				if(!isD3False) values.addCount(predicate.data3);
			}
			
		}
		if(predicate.data3 == i) {
			if (!isD2True && !isD1True) isTrue = false;
			isD3True = false;
			if(!isTrue) {
				if(!isD1False) values.addCount(predicate.data1);
				if(!isD2False) values.addCount(predicate.data2);
				values.addCount(predicate.data3);
			}
			
		}
		if (predicate.data1 == -i) {
			isD1False = false;
			if(!isTrue) values.addCount(predicate.data1);
		}
		if (predicate.data2 == -i) {
			isD2False = false;
			if(!isTrue) values.addCount(predicate.data2);
		}
		if (predicate.data3 == -i) {
			isD3False = false;
			if(!isTrue) values.addCount(predicate.data3);
		}
	}
	
	public int getUnitary() {
		if((isD1False && isD2False && !isD3False && !isD3True) || (isD1False && isD3False && !isD2False && !isD2True) || (isD3False && isD2False && !isD1False && !isD1True)) {
			if(isD1False && isD2False) return predicate.data3;
			if(isD2False && isD3False) return predicate.data1;
			if(isD3False && isD1False) return predicate.data2;
		}
		return 0;
	}

	public boolean isTrue() {
		return isTrue;
	}
	
	public boolean isFalse() {
		return isD1False && isD2False && isD3False;
	}
	
	

}
