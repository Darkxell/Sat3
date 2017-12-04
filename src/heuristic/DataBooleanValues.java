package heuristic;

import java.util.HashMap;
import java.util.Map;

public class DataBooleanValues {
	
	private Map<Integer, Boolean> values = new HashMap<Integer,Boolean>();
	private Map<Integer, Boolean> used = new HashMap<Integer,Boolean>();
	
	public DataBooleanValues() {
		
	}
	
	public void addValue(int value, boolean bool) {
		values.put(value, bool);
	}
	
	public void delValue(int value) {
		if(this.isValuePresent(value)) {
			used.put(value, values.get(value));
		}
		values.remove(value);
	}
	
	public boolean isValuePresent(int value) {
		return values.get(value) != null;
	}
	
	public boolean getBool(int value) {
		return values.get(value);
	}

}
