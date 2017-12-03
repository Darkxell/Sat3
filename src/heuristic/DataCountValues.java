package heuristic;

import java.util.HashMap;
import java.util.Map;

public class DataCountValues {
	
	private Map<Integer, Integer> counts = new HashMap<Integer,Integer>();
	
	public DataCountValues() {
		
	}
	
	public void addCount(int value) {
		if(this.isPresent(value)) {
			counts.put(value, counts.get(value)+1);
		}
		else {
			counts.put(value, 1);
		}
	}
	
	public boolean isPresent(int value) {
		return counts.containsKey(value);
	}
	
	public int getMoreFrequent() {
		int key = 0;
		int value = 0;
		for(int i : counts.keySet()) {
			if (counts.get(i) > value) {
				key = i;
				value = counts.get(i);
			}
		}		
		return key;
	}

}