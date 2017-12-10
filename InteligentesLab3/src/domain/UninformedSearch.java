package domain;
import java.util.*;
import java.io.*;

public class UninformedSearch {
	
	public static Hashtable<String, Integer> visited = new Hashtable<String, Integer>();
	static boolean optimization = false;
	static long nNodes = 0;
	
	
	/**************************************************************************************
	 * Method name: search
	 * Description: Is in charge of find a solution
	 * @param prob -> Is the problem that we have to solve 
	 * @param strategy -> The strategy that we will use
	 * @param prof_max -> The breath that we estimate
	 * @param inc_prof -> The increment doing this strategy
	 * @return solution -> We return the solution that the problem finds
	 *************************************************************************************/
	public ArrayList<Node> search(Problem prob, Strategy strategy, int prof_max, int inc_prof) {
		int currentProf = inc_prof;
		nNodes = 0;
		ArrayList<Node> solution = null;
		while (solution == null && currentProf <= prof_max){
			solution = boundedSearch(prob, strategy, currentProf);
			currentProf = currentProf + inc_prof;
			visited.clear();
		}
		return solution;
	}
	/**************************************************************************************
	 * Method name: boundedSearch
	 * Description: 
	 * @param prob -> The problem that we have to solve
	 * @param strategy -> The strategy that we have to use
	 * @param currentProf -> The breadth that we determine
	 * @return null or solution that we have to determine
	 ***************************************************************************************/
	public static ArrayList<Node> boundedSearch(Problem prob,Strategy strategy,int currentProf){
		//Proceso de inicialización
		Frontier frontier = new Frontier();
		Node initial_node=new Node(prob.getInitState());
		Node current_node = null;
		frontier.createFrontier();
		frontier.insertNode(initial_node);
		boolean isSolution = false;
		//Bucle de búsqueda
		while(!isSolution && !frontier.isEmpty()){
		   current_node=frontier.removeFirst(); ///Seleccion de nodo
		   if(current_node.getState().isGoal()){
			   isSolution = true;
		   }else {
			   Successor successors =  new Successor();
			   ArrayList<Node> suc = successors.successors(current_node, strategy, currentProf);
			   for(int i = 0; i < suc.size(); i++) {
				   // If optimization
				   if(optimization == true) { 
					   if(checkVisited(suc.get(i), strategy)) frontier.insertNode(suc.get(i));
				// If not optimization
				   }else {
					   frontier.insertNode(suc.get(i));
				   }
			   }
			   nNodes+=suc.size();
		   }
		}
		  //Resultado 
		if(isSolution)
		   return createSolution(current_node);
		else return null;
	}// End boundedSearch
	/*************************************************************************************
	 * Method name: createSolution
	 * @param current_node -> the actual node in which we are.
	 * @return solution -> the solution that we have found
	 *************************************************************************************/
	private static ArrayList<Node> createSolution(Node current_node) {
		ArrayList<Node> solution = new ArrayList<Node>();
		while(current_node.getParent()!=null && solution!= null) {
			solution.add(current_node);
			current_node = current_node.getParent();
		}
		return solution;
	}
	
	private static boolean checkVisited(Node node, Strategy strategy) {
		String serial = node.serialize();
		if(!visited.containsKey(serial)) {
			visited.put(serial, node.getValueHash(strategy));
			return true;
		}else {
			if(visited.get(serial) > node.getValueHash(strategy)) { /****/
				visited.remove(serial);
				visited.put(serial, node.getValueHash(strategy));
				return true;
			}else {/*No nothing*/ nNodes--; return false;}
		}
	}
	
	public long nNodes() {
		return nNodes;
	}
	
	public boolean getOptimization() {
		return optimization;
	}
	public void setOptimization(boolean opt) {
		optimization = opt;
	}
}
