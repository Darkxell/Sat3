package heuristic;

import java.util.HashMap;
import java.util.Map;

public class DataCountValues {
	
	//contient des valeurs relatives
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
	
	public void removeCount(int value) {
		counts.put(value, counts.get(value)-1);
	}
	
	public int get(int value) {
		if(isPresent(value)) return counts.get(value);
		return 0;
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
