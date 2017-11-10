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
public class Main {

	/**********************************************************************************
	 * Method name: main
	 * Description: everything starts here. The successor function is called. Also
	 * there is an example of a performed action where the action to be performed 
	 * in the current state of the field is chosen randomly
	 * @param args
	 * @throws IOException
	 **********************************************************************************/
		// THINGS TO ASK
		// - No moving nothing is allowed when amount is higher or equal than K?
	
	/*public static void main(String[] args) throws IOException {		
		ArrayList<Action> actions;
		Field field = new Field("Setup.txt");
		System.out.println("-----ATTRIBUTES----- \nXt: " + field.getXt());
		System.out.println("Yt: " + field.getYt());
		System.out.println("K: " + field.getK());
		System.out.println("Max: " + field.getMax());
		System.out.println("Size column: " + field.getSizeC());
		System.out.println("Size Row: " + field.getSizeR() + "\n-----MATRIX-----");
		field.printMatrix();
		
		//Here we obtain all possible actions that can be performed//
		actions=createActions(field);
		
		/////////////////////////////////// EXAMPLE OF AN ACTION APPLIED ////////////////////////////////////////
		System.out.println("\n--------PERMFORM AN ACTION (Example randomly chosen)------");
		Random rd = new Random(System.currentTimeMillis());
		int chosen = rd.nextInt(actions.size());
		actions.get(chosen).perform(field);
		String nextMov= " Performing the action: "+actions.get(chosen) +"\n Xt: " + field.getXt() + "\n Yt: " + field.getYt() 
				+ "\n K: " + field.getK() + "\n Max: " + field.getMax() + "\n Size column: " + field.getSizeC() + "\n Size Row: " 
				+ field.getSizeR() + "\n -----MATRIX-----";
		System.out.println(nextMov);
		field.printMatrix();
		String matrix=field.saveMatrix();
		field.generateOutput(nextMov, matrix);
		/////////////////////////////////////// EXAMPLE OF ALL SUCCESSORS //////////////////////////////////////	
		ArrayList<Field> successors = successors(field, actions);
		printSuccessors(successors);
	}//End main

*/	
	/*public static void main(String[] args) {
		Node firstNode;
		Field field = new Field("Setup.txt");
		Frontier frontier = new Frontier();
		frontier.createFrontier();
		firstNode = new Node(field);
		Successor successors = new Successor();
		ArrayList<Node> suc = successors.successors(firstNode);

		long listTI, listTF, queueTI, queueTF;										//-------------------------------------
		////////////////////////////////////////// LINKED LIST /////////////////////////
		System.out.println("--------------------- LIST ------------------");
		System.out.println("------- Parent -----\n" + suc.get(0).getParent());
		listTI = System.nanoTime();													//-------------------------------------
		for(int i = 0; i< suc.size(); i++) {
			//System.out.println("------- Parent -----\n" + suc.get(i).getParent());
			//System.out.println("---- Sucesores ----");
			//System.out.println(suc.get(i)+"\n---");
			frontier.insertNode(suc.get(i));
		}
		Collections.sort(frontier.getFrontier());
		listTF = System.nanoTime() - listTI;										//-------------------------------------
		for(int i = 0; i< suc.size(); i++) {
			System.out.println("----");
			System.out.println(frontier.getFrontier().get(i));
		}
		////////////////////////////////////////// QUEUE //////////////////////////////
		System.out.println("---------------------- QUEUE -----------------");
		System.out.println("------- Parent -----\n" + suc.get(0).getParent());
		queueTI = System.nanoTime();												//-------------------------------------
		Frontier frontier2 = new Frontier();
		frontier2.createFrontierQ();
		for(int i = 0; i< suc.size(); i++) {
			frontier2.insertNodeQ(suc.get(i));
		}
		queueTF = System.nanoTime()- queueTI;										//-------------------------------------
		Iterator<Node> it = frontier2.getFrontierQ().iterator();
		while(it.hasNext()) {
			System.out.println("----");
			System.out.println(frontier2.getFrontierQ().poll());
		}
		///////////////////////////////////// Time comparison /////////////////////////
		System.out.println("----------- TIME -----------");
		System.out.print("Time of list: ");
		System.out.print(listTF);
		System.out.print("ms\nTime of queue: ");
		System.out.print(queueTF);
		System.out.println("ms\nBetter? "+ (listTF<queueTF ? "LinkedList":"Priority Queue"));
	}*/
	
	/*public static void main(String[] args) {
		LinkedList<Node> lista = new LinkedList<Node>();
		PriorityQueue<Node> queue = new PriorityQueue<Node>();
		LinkedList<Node> lList = new LinkedList<Node>();
		Field f = new Field("Setup.txt");
		for (int i =0; i<25000000; i++) {
			lList.add(new Node(f));
			
		}
		System.out.println("Done");
	}*/
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void main(String[] args) {
		Problem prob = new Problem("Setup.txt");
		ArrayList<Node> sol = search(prob, 0, 20, 1);
		for(int i = 0; i<sol.size(); i++) {
			System.out.println(i+1 + "- "+sol.get(sol.size()-i-1).getAction());
			System.out.print(i+1 + "- \n");
			System.out.print(sol.get(sol.size()-i-1).getState().saveMatrix());
			System.out.println(sol.get(sol.size()-i-1).getAction());
		}
		/*PriorityQueue<Node> q= new PriorityQueue<Node>();
		for(;;) {
			q.add(new Node(new Field(prob.getInitState())));
		}*/
	}
	/*
	 * Busqueda (Prob,estrategia,Prof_Max,Inc_Prof): Solución o Nada.
  		Prof_Actual<-Inc_Prof
  		Solución<- Ninguna
  		Mientras (not Solución) and (Prof_Act=< Prof_Max):
    	Solución= Busqueda_Acotada(Prob,estrategia,Prof_Actual)
     	Prof_Actual <- Prof_Actual + Inc_Prof
  		Fin_Mientras
  		devolver Solución
	 */
	
	
	public static ArrayList<Node> search(Problem prob, int strategy, int prof_max, int inc_prof) {
		int currentProf = inc_prof;
		ArrayList<Node> solution = new ArrayList<Node>();
		while (currentProf <= prof_max){
			solution = boundedSearch(prob, 0, 0);
			currentProf = prof_max + inc_prof;
		}
		return solution;
	}

	public static ArrayList<Node> boundedSearch(Problem prob,int strategy,int currentProf){
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
			   ArrayList<Node> suc = successors.successors(current_node);
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
		while(current_node.getParent()!=null) {
			solution.add(current_node);
			current_node = current_node.getParent();
		}
		return solution;
	}
}
