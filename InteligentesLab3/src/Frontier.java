import java.util.LinkedList;
import java.util.PriorityQueue;


public class Frontier {
	
	///////////////////////////////////////////////////// LINKED LIST ///////////////////////////////////////////////////
	private LinkedList<Node> frontier;
	
	
	public LinkedList<Node> getFrontier() {
		return frontier;
	}

	public void setFrontier(LinkedList<Node> frontier) {
		this.frontier = frontier;
	}

	public void createFrontier() {
		frontier = new LinkedList<Node>();
		
	}
	
	public void insertNode(Node node) {
		frontier.add(node);
	}
	
	public void removeFirst() {
		frontier.removeFirst();
	}
	
	public void isEmpty() {
		frontier.isEmpty();
	}
	
	/////////////////////////////////////////////////////// QUEUES //////////////////////////////////////////////////
	
	private PriorityQueue<Node> queue;
	
	public PriorityQueue<Node> getFrontierQ() {
		return queue;
	}

	public void createFrontierQ() {
		queue = new PriorityQueue<Node>();
		
	}
	
	public void insertNodeQ(Node node) {
		queue.add(node);
	}
	
	public void removeFirstQ() {
		queue.peek();
	}
	
	public void isEmptyQ() {
		queue.isEmpty();
	}
	
}
