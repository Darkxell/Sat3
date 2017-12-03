package heuristic;

import java.util.ArrayList;
import java.util.List;

import sattoglpk.CnfConvertor;
import sattoglpk.Predicate3sat;

public class HeuristicSolver {
	
	public static List<PredicateMetaData> predicatesMetaDatas = new ArrayList<PredicateMetaData>();
	public static DataCountValues dataCount = new DataCountValues();
	public static DataBooleanValues dataBool = new DataBooleanValues();
	public static boolean isSatisfiable;
	public static boolean isDone;

	public static void main(String[] args) {
		CnfConvertor.main(args);
		
		for(Predicate3sat pred : CnfConvertor.predicates) {
			dataCount.addCount(pred.data1);
			dataCount.addCount(pred.data2);
			dataCount.addCount(pred.data3);
			predicatesMetaDatas.add(new PredicateMetaData(pred));
		}
		
		/*for(PredicateMetaData pmd : predicatesMetaDatas) {
			System.out.println(pmd);
		}*/
		
		while(!isDone) {
			//TODO : parcourir l'arbre DPLL
			//TODO : heuritsique HL
		}

	}

}
