import java.util.LinkedList;
import java.util.PriorityQueue;


public class Frontier {
	
	///////////////////////////////////////////////////// LINKED LIST ///////////////////////////////////////////////////
	private LinkedList<Node> frontier;
	
	/**************************************************************************************************
	 * Method: getFrontier
	 * Description: It gets the frontier
	 * @return frontier -> the frontier in each moment of the tree
	 **************************************************************************************************/
	public LinkedList<Node> getFrontier() {
		return frontier;
	}
    /***************************************************************************************************
     * Method: setFrontier
     * Description: You can set a new frontier with this method
     * @param frontier -> the frontier that you are going to change the value
     ***************************************************************************************************/
	public void setFrontier(LinkedList<Node> frontier) {
		this.frontier = frontier;
	}
    /*****************************************************************************************************
     * Method: createFrontier
     * Description: This method is in charge of build a list with all frontiers
     *****************************************************************************************************/
	public void createFrontier() {
		frontier = new LinkedList<Node>();
	}
	/*****************************************************************************************************
	 * Method: insertNode
	 * Description: You can insert a new node to the frontier
	 * @param node -> Is the node that you are going to insert to the list
	 *****************************************************************************************************/
	public void insertNode(Node node) {
		frontier.add(node);
	}
	/*******************************************************************************************************
	 * Method: removeFirst
	 * Description: You can remove a node in the frontier
	 ******************************************************************************************************/
	public void removeFirst() {
		frontier.removeFirst();
	}
	/*******************************************************************************************************
	 * Method: isEmpty
	 * Description: You can prove if the frontier is empty
	 *******************************************************************************************************/
	public void isEmpty() {
		frontier.isEmpty();
	}
	
	/////////////////////////////////////////////////////// QUEUES //////////////////////////////////////////////////
	
	private PriorityQueue<Node> queue;
	/**************************************************************************************************
	 * Method: getFrontier
	 * Description: It gets the frontier
	 * @return frontier -> the frontier in each moment of the tree
	 **************************************************************************************************/
	public PriorityQueue<Node> getFrontierQ() {
		return queue;
	}
	/*****************************************************************************************************
     * Method: createFrontier
     * Description: This method is in charge of build a list with all frontiers
     *****************************************************************************************************/
	public void createFrontierQ() {
		queue = new PriorityQueue<Node>();
		
	}
	/*****************************************************************************************************
	 * Method: insertNode
	 * Description: You can insert a new node to the frontier
	 * @param node -> Is the node that you are going to insert to the list
	 *****************************************************************************************************/
	public void insertNodeQ(Node node) {
		queue.add(node);
	}
	/******************************************************************************************************
	 * Method: removeFirstQ
	 * Description: You can remove the node in the frontier
	 ******************************************************************************************************/
	public void removeFirstQ() {
		queue.peek();
	}
	/******************************************************************************************************
	 * Method: isEmptyQ
	 * Description: Is a method to prove that the list is empty or not
	 *******************************************************************************************************/
	public void isEmptyQ() {
		queue.isEmpty();
	}
	
}
