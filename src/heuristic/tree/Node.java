package heuristic.tree;

public class Node {
	
	private Node father;
	private int value; //valeur absolue
	private boolean leafTrueContradiction;
	private boolean leafFalseContradiction;
	
	public Node(int value) {
		this(value, null);
	}
	
	public Node(int value, Node father) {
		this.value = value;
		this.father = father;
		leafTrueContradiction = false;
		leafFalseContradiction = false;
	}

	public boolean isLeafTrueContradiction() {
		return leafTrueContradiction;
	}

	public void setLeafTrueContradiction(boolean leafTrueContradiction) {
		this.leafTrueContradiction = leafTrueContradiction;
	}

	public boolean isLeafFalseContradiction() {
		return leafFalseContradiction;
	}

	public void setLeafFalseContradiction(boolean leafFalseContradiction) {
		this.leafFalseContradiction = leafFalseContradiction;
	}

	public Node getFather() {
		return father;
	}

	public int getValue() {
		return value;
	}
	
	public Node drawParentRec(Node node) {
		if (node == null) {
			System.out.println(); 
			return null;
		}
		System.out.print(node.value + " - ");
		return drawParentRec(node.father);
	}
	
	

}
