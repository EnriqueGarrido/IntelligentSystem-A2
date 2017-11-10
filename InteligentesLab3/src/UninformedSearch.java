import java.util.*;
import java.io.*;

/***********************************************************************************************************
 * Class name: Main
 * Description: in this class all computations of the executions of the problem are performed, including
 * the main method with an example of a performed action on the field
 * @author Adrian Ollero Jimenez, Enrique Garrido Pozo, Pablo Mora Herreros 
 * Subject: Intelligent Systems
 * Group: A2
 **********************************************************************************************************/
public class UninformedSearch {
	
	public ArrayList<Node> search(Problem prob, Strategy strategy, int prof_max, int inc_prof) {
		int currentProf = inc_prof;
		ArrayList<Node> solution = new ArrayList<Node>();
		while (currentProf <= prof_max){
			solution = boundedSearch(prob, strategy, 0);
			currentProf = prof_max + inc_prof;
		}
		return solution;
	}

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
			   ArrayList<Node> suc = successors.successors(current_node, strategy);
			   for(int i = 0; i < suc.size(); i++) {
				   frontier.insertNode(suc.get(i));
			   }
		   }
		}
		  //Resultado 
		if(isSolution)
		   return createSolution(current_node);
		else return null;
	}// End boundedSearch
	
	private static ArrayList<Node> createSolution(Node current_node) {
		ArrayList<Node> solution = new ArrayList<Node>();
		while(current_node.getParent()!=null && solution!= null) {
			solution.add(current_node);
			current_node = current_node.getParent();
		}
		return solution;
	}
}
