package heuristic;

import java.util.ArrayList;
import java.util.List;

import heuristic.tree.Node;
import sattoglpk.CnfConvertor;
import sattoglpk.Predicate3sat;

public class HeuristicSolver {
	
	public static List<PredicateMetaData> predicatesMetaDatas = new ArrayList<PredicateMetaData>();
	public static List<PredicateMetaData> predicatesTrue = new ArrayList<PredicateMetaData>();
	public static DataCountValues dataCount = new DataCountValues();
	public static DataBooleanValues dataBool = new DataBooleanValues();
	public static Node currentNode;
	public static boolean isSatisfiable;
	public static boolean isDone;

	public static void main(String[] args) {
		CnfConvertor.main(args);
		
		//on initialise les metadatas des pr�dicats
		for(Predicate3sat pred : CnfConvertor.predicates) {
			dataCount.addCount(pred.data1);
			dataCount.addCount(pred.data2);
			dataCount.addCount(pred.data3);
			predicatesMetaDatas.add(new PredicateMetaData(pred));
		}
		
		/*for(PredicateMetaData pmd : predicatesMetaDatas) {
			System.out.println(pmd);
		}*/
		
		currentNode = null;
		
		//parcours de l'arbre tant qu'on a pas soit la solution, soit parcouru tout l'arbre
		while(!isDone) {
			//TODO : parcourir l'arbre DPLL
			//TODO : heuristique HL
			int unitary = 0;
			int currentValue = 0;
			
			//recherche d'une valeur unitaire
			for(PredicateMetaData pred : predicatesMetaDatas) {
				unitary = pred.getUnitary();
			}
			
			//si elle existe, on la prend, sinon on prend la valeur la plus fr�quente
			if(unitary !=0) {
				currentValue = unitary;
			}
			else {
				currentValue = dataCount.getMoreFrequent();
			}
			
			//on cr�e le noeud de l'arbre
			currentNode = new Node(Math.abs(currentValue), currentNode);
			
			//si la valeur du noeud est positive, on associe sa valeur absolue � true, false sinon
			boolean nextBranch = currentValue>0;
			dataBool.addValue(Math.abs(currentValue), nextBranch);
			
			//on met � jour les pr�dicats pour consid�rer la nouvelle valeur
			for(PredicateMetaData pred : predicatesMetaDatas) {
				pred.updateDatas(currentValue);
				if(pred.isTrue()) predicatesTrue.add(pred);
			}
			
			//si tout les pr�dicats sont vrais, alors la requ�te est satisfiable
			if(predicatesMetaDatas.size() == predicatesTrue.size()) {
				isDone = true;
				isSatisfiable = true;
			}
			
			//si on remonte � la racine apr�s avoir des contradictions des deux c�t�s, alors la requ�te n'est pas satisfiable
			//(= toute les feuilles contradictoires)
			if(currentNode.isLeafTrueContradiction() && currentNode.isLeafFalseContradiction()) {
				isDone = true;
				isSatisfiable = false;
			}
			
		}
		
		//T'as vraiment besoin d'un commentaire pour comprendre �a ?
		if(isSatisfiable) System.out.println("Requ�te satisfiable");
		else System.out.println("Requ�te non satisfiable");

	}

}
