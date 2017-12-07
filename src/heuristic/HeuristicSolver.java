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
		
		//on initialise les metadatas des prédicats
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
		int currentValue = 0;
		
		//parcours de l'arbre tant qu'on a pas soit la solution, soit parcouru tout l'arbre
		while(!isDone) {
			//TODO : parcourir l'arbre DPLL
			//TODO : heuristique HL
			boolean same = false;
			boolean contrad = false;
			int unitary = 0;
			
			
			//si la branche est contradictoire des deux côtés, on remonte
			if(currentNode != null && currentNode.isLeafTrueContradiction() && currentNode.isLeafFalseContradiction()) {
				contrad = true;
				same = true;
				if(dataBool.getBool(currentNode.getValue())) currentValue = currentNode.getValue();
				else currentValue = -currentNode.getValue();
				System.out.println("on remonte");
			}			
			//si la branche est contradictoire que d'un côté, on teste l'autre côté
			else if(currentNode != null && ((currentNode.isLeafTrueContradiction() && nextBranch) || (currentNode.isLeafFalseContradiction() && !nextBranch))) {
				if(currentNode.isLeafTrueContradiction()) currentValue = -currentNode.getValue();
				else currentValue = currentNode.getValue();
				same = true;
				System.out.println("on stagne");
			}
			//sinon on recherche la valeur selon l'heuristique HL
			else {
				System.out.println("on descend");
				//recherche d'une valeur unitaire
				for(PredicateMetaData pred : predicatesMetaDatas) {
					unitary = pred.getUnitary();
				}
				
				
				//si elle existe, on la prend, sinon on prend la valeur la plus fréquente
				if(unitary !=0) {
					currentValue = unitary;
				}
				else {
					currentValue = dataCount.getMoreFrequent();
				}
			}
			
			System.out.println(currentValue);
			
			//on ne crée un noeud que s'il n'existe pas déjà
			if(!same) {
				//on crée le noeud de l'arbre
				currentNode = new Node(Math.abs(currentValue), currentNode);
				if(currentNode.getFather() != null) {
					if(nextBranch) {
						currentNode.getFather().setLeafTrue(currentNode);
					}
					else {
						currentNode.getFather().setLeafFalse(currentNode);
					}
				}
			}
			
			
			//seulement si on compte descendre
			if(!contrad) {
				//si la valeur du noeud est positive, on associe sa valeur absolue à true, false sinon
				nextBranch = currentValue>0;
				dataBool.addValue(Math.abs(currentValue), nextBranch);
				
				
				//on met à jour les prédicats pour considérer la nouvelle valeur. 
				//si un prédicat est entièrement faux, alors on a une contradiction
				for(PredicateMetaData pred : predicatesMetaDatas) {
					pred.updateDatas(currentValue, dataCount);
					if(pred.isTrue()) predicatesTrue.add(pred);
					if(pred.getClauseSize() == 0 && !pred.isTrue()) contrad = true;
				}
			}
			
						
			//si tout les prédicats sont vrais, alors la requête est satisfiable
			if(predicatesMetaDatas.size() == predicatesTrue.size()) {
				isDone = true;
				isSatisfiable = true;
			}
			
			
			//si on remonte à la racine après avoir des contradictions des deux côtés, alors la requête n'est pas satisfiable
			//(= toute les feuilles contradictoires)
			if(currentNode.isLeafTrueContradiction() && currentNode.isLeafFalseContradiction() && currentNode.getFather() == null) {
				isDone = true;
				isSatisfiable = false;
			}
			
			
			//si on a une contradiction, il faut remonter l'arbre
			if(contrad) {
				dataBool.removeValue(Math.abs(currentValue));
				for(PredicateMetaData pred : predicatesMetaDatas){
					if(pred.isTrue()) predicatesTrue.remove(pred);
					pred.reverseUpdateDatas(currentValue, dataCount);
				}
				currentNode = currentNode.getFather();
				if(!currentNode.isLeafFalseContradiction() && currentNode.getLeafFalse() != null) {
					currentNode.setLeafFalseContradiction(true);
				}
				else if(!currentNode.isLeafTrueContradiction() && currentNode.getLeafTrue() != null) {
					currentNode.setLeafTrueContradiction(true);
				}
			}
			
			
		}
		
		//T'as vraiment besoin d'un commentaire pour comprendre ça ?
		if(isSatisfiable) System.out.println("Requête satisfiable");
		else System.out.println("Requête non satisfiable");

	}

}
