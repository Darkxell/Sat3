package heuristic;

import java.util.HashMap;
import java.util.Map;

public class DataBooleanValues {
	
	//ne contient que les valeurs absolues
	private Map<Integer, Boolean> values = new HashMap<Integer,Boolean>();
	
	public DataBooleanValues() {
		
	}
	
	public void addValue(int value, boolean bool) {
		values.put(value, bool);
	}
	
	public void removeValue(int value) {
		values.remove(value);
	}
	
	public boolean isValuePresent(int value) {
		return values.get(value) != null;
	}
	
	public boolean getBool(int value) {
		return values.get(value);
	}
	
	@Override
	public String toString() {
		String toReturn = "";
		for(int i : values.keySet()) {
			toReturn += i + " : " + values.get(i) + " / ";
		}
		return toReturn;
	}

	public int getLength() {
		return values.size();
	}
	
}
