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
		boolean nextBranch = true;
		
		//parcours de l'arbre tant qu'on a pas soit la solution, soit parcouru tout l'arbre
		while(!isDone) {
			//TODO : parcourir l'arbre DPLL
			//TODO : heuristique HL
			boolean contrad = false;
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
			if(currentNode.getFather() != null) {
				if(nextBranch) {
					currentNode.getFather().setLeafTrue(currentNode);
				}
				else {
					currentNode.getFather().setLeafFalse(currentNode);
				}
			}
			
			//si la valeur du noeud est positive, on associe sa valeur absolue � true, false sinon
			nextBranch = currentValue>0;
			dataBool.addValue(Math.abs(currentValue), nextBranch);
			
			
			//on met � jour les pr�dicats pour consid�rer la nouvelle valeur. 
			//si un pr�dicat est enti�rement faux, alors on a une contradiction
			for(PredicateMetaData pred : predicatesMetaDatas) {
				pred.updateDatas(currentValue);
				if(pred.isTrue()) predicatesTrue.add(pred);
				if(pred.getClauseSize() == 0 && !pred.isTrue()) contrad = true;
			}
			
			//si tout les pr�dicats sont vrais, alors la requ�te est satisfiable
			if(predicatesMetaDatas.size() == predicatesTrue.size()) {
				isDone = true;
				isSatisfiable = true;
			}
			
			//si on remonte � la racine apr�s avoir des contradictions des deux c�t�s, alors la requ�te n'est pas satisfiable
			//(= toute les feuilles contradictoires)
			if(currentNode.isLeafTrueContradiction() && currentNode.isLeafFalseContradiction() && currentNode.getFather() == null) {
				isDone = true;
				isSatisfiable = false;
			}
			
			//si on a une contradiction, il faut remonter l'arbre
			if(contrad) {
				for(PredicateMetaData pred : predicatesMetaDatas){
					if(pred.isTrue()) predicatesTrue.remove(pred);
					pred.updateDatas(currentValue); //do method
				}
				currentNode = currentNode.getFather();
			}
			
			
		}
		
		//T'as vraiment besoin d'un commentaire pour comprendre �a ?
		if(isSatisfiable) System.out.println("Requ�te satisfiable");
		else System.out.println("Requ�te non satisfiable");

	}

}
