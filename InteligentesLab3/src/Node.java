import java.util.*;


public class Node implements Comparable<Node>{
	
	private Node parent;
	
	private Field state;
	private int cost;
	private Action action;
	private int depth;
	private int value;
	
	
	public Node(Node parentNode, Action nextAction) {
		this.state =  new Field(parentNode.getState(), nextAction);
		this.action = nextAction;
		this.parent = parentNode;
		////
		this.cost = parentNode.getCost()+1;
		this.depth = parentNode.getDepth()+1;
		this.value = Math.abs(1+(new Random()).nextInt()%100);
	}

	public Node(Field state) {
		this.state = state;
	}

	

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public Field getState() {
		return state;
	}

	public void setState(Field state) {
		this.state = state;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	@Override
	public int compareTo(Node other) { 
		if(this.getValue()<other.getValue()){
			return -1;
		}
		if(this.getValue()>other.getValue()){
			return 1;
		}
		return 0;
	}
	
	public String toString() {
		/*return "Node:\n" + state.saveMatrix() + "("+ state.getXt() + ", " + state.getYt() + 
				")\nCost: "+cost + "\nAction: " + action.toString() + "\nDepth: " + depth + 
				"\nValue : "+value;*/
		return "Node:\n" + state.saveMatrix() + "("+ state.getXt() + ", " + state.getYt() + 
				")\nCost: "+cost + "\nDepth: " + depth + 
				"\nValue : "+value;
	}
	
}
