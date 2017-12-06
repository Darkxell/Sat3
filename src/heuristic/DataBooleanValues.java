package heuristic;

import java.util.HashMap;
import java.util.Map;

public class DataBooleanValues {
	
	private Map<Integer, Boolean> values = new HashMap<Integer,Boolean>();
	
	public DataBooleanValues() {
		
	}
	
	public void addValue(int value, boolean bool) {
		values.put(value, bool);
	}
	
	public boolean isValuePresent(int value) {
		return values.get(value) != null;
	}
	
	public boolean getBool(int value) {
		return values.get(value);
	}

}
