package domain;



public class Node implements Comparable<Node>{
	
	private Node parent;
	
	private Field state;
	private int cost;
	private Action action;
	private int depth;
	private int value;
	
	/**********************************************************************************************************
	 * Method name: Node
	 * Description: Constructor of the class
	 * @param parentNode -> a node that is the parent of the other node 
	 * @param nextAction -> the action that to the node will be applicated
	 **********************************************************************************************************/
	public Node(Node parentNode, Action nextAction, Strategy strategy) {
		this.state =  new Field(parentNode.getState(), nextAction);
		this.action = nextAction;
		this.parent = parentNode;
		this.cost = parentNode.getCost()+action.getActionCost();
		this.depth = parentNode.getDepth()+1;
		if(strategy == Strategy.BFS)
			this.value = depth;
		else if(strategy == Strategy.DFS || strategy == Strategy.DLS || strategy == Strategy.IDS)
			this.value = -depth;
		else if(strategy == Strategy.UCS)
			this.value = cost;
		else if(strategy == Strategy.A_STAR)
			this.value = cost + state.h();
	}
	/************************************************************************************************************
	 * Method name: Node
	 * Description: Another constructor of the class
	 * @param state -> the actual state of the node
	 ************************************************************************************************************/
	public Node(Field state) {
		this.state = state;
	}
    /**************************************************************************************************************
     * Method name: getParent
     * Description: The method to know the parent of a node
     * @return parent -> The actual parent of the node
     ***************************************************************************************************************/
	public Node getParent() {
		return parent;
	}
    /***************************************************************************************************************
     * Method name: setParent
     * Description: You can change the parent with this method
     * @param parent -> the parent of the node
     ****************************************************************************************************************/
	public void setParent(Node parent) {
		this.parent = parent;
	}
    /*****************************************************************************************************************
     * Method name: getState
     * Description: To know the state of the node
     * @return node -> the state of the node
     *****************************************************************************************************************/
	public Field getState() {
		return state;
	}
    /******************************************************************************************************************
     * Method name: setState
     * Description: To modify the state of a node
     * @param state -> the state of the node
     ******************************************************************************************************************/
	public void setState(Field state) {
		this.state = state;
	}
    /*******************************************************************************************************************
     * Method name: getCost
     * Description: To know the cost of a node
     * @return cost -> the cost of a node
     ********************************************************************************************************************/
	public int getCost() {
		return cost;
	}
    /********************************************************************************************************************
     * Method name: setCost
     * Description: To modify the cost of a node
     * @param cost -> the cost of a node
     ********************************************************************************************************************/
	public void setCost(int cost) {
		this.cost = cost;
	}
    /********************************************************************************************************************
     * Method name: getAction
     * Description: To know the cost of the action
     * @return action -> the action of the node
     ********************************************************************************************************************/
	public Action getAction() {
		return action;
	}
    /*******************************************************************************************************************
     * Method name: setAction
     * Description: To modify the action of a node
     * @param action -> the action of the node
     ********************************************************************************************************************/
	public void setAction(Action action) {
		this.action = action;
	}
    /********************************************************************************************************************
     * Method name: getDepth
     * Description: To get the depth of the node
     * @return action -> the action of the node
     ********************************************************************************************************************/
	public int getDepth() {
		return depth;
	}
    /*********************************************************************************************************************
     * Method name: setDepth
     * Description: To modify the depth of the node
     * @param depth -> the depth of the node
     ********************************************************************************************************************/
	public void setDepth(int depth) {
		this.depth = depth;
	}
    /**********************************************************************************************************************
     * Method name: getValue
     * Description: To get the value of the node
     * @return value -> the value of the node
     **********************************************************************************************************************/
	public int getValue() {
		return value;
	}
    /***********************************************************************************************************************
     * Method name: setValue
     * Description: To modify the value of the node
     * @param value -> the value of the node
     ***********************************************************************************************************************/
	public void setValue(int value) {
		this.value = value;
	}
	@Override
	/***************************************************************************************************************************
	 *Method name: compareTo
	 *Description: To compare one node to another node
	 *@param other -> a node to compare witb another 
	 **************************************************************************************************************************/
	public int compareTo(Node other) { 
		if(this.getValue()<other.getValue()){
			return -1;
		}
		if(this.getValue()>other.getValue()){
			return 1;
		}
		return 0;
	}
	/****************************************************************************************************************************
	 * Method name: toString
	 * Description: To transform all attributes in a string to print that
	 ****************************************************************************************************************************/
	public String toString() {
		/*return "Node:\n" + state.saveMatrix() + "("+ state.getXt() + ", " + state.getYt() + 
				")\nCost: "+cost + "\nAction: " + action.toString() + "\nDepth: " + depth + 
				"\nValue : "+value;*/
		return "Node:\n" + state.saveMatrix() + "("+ state.getXt() + ", " + state.getYt() + 
				")\nCost: "+cost + "\nDepth: " + depth + 
				"\nValue : "+value;
	}
	
	public String serialize() {
		String serialized="@";
		int[][] s = state.getField();
		for(int i=0; i<s.length; i++)
			for(int j=0; j<s[i].length; j++)
				serialized+=s[i][j];
		serialized+="@"+state.getXt()+state.getYt();
		return serialized;
	}
	
	public int getValueHash(Strategy strategy) {
		if(strategy == Strategy.BFS || strategy == Strategy.DFS || strategy == Strategy.DLS || strategy == Strategy.IDS)
			return cost;
		else
			return value;
	}
	
}
