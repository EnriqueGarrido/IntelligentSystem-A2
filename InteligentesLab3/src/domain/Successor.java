package domain;
import java.io.IOException;
import java.util.*;
//import java.io.*;


public class Successor {
	
	static boolean [] positions= new boolean[4]; //Array to key if there is possible to move to a direction [N, W, E, S]
	
	//////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////
	/*public static void createNodes(Field f) {
		nodes(new Node(f), 0, 0, 0);
	}
	
	public static void nodes(Node parent, int cost, int depth, int value) {
		ArrayList<Action> actions=createActions(parent.getState());
		ArrayList<Node> successors = successors(parent.getState(), actions);
		Iterator<Node> it = successors.iterator();
		//Node node = new Node(parent, f, cost, act, depth, value);
		while(it.hasNext()) {
			//nodes(parent, it.next(), cost+1, it2.next(), depth+1, 0);
			Node node = it.next();
			node.setCost(cost+1);
			node.setDepth(depth+1);
			node.setValue(0);
		}
	}*/
	
	/********************************************************************************
	 * Method name: successors
	 * Description: takes a field and a set of actions and obtains all new fields
	 * where the set of actions has been performed
	 * @param parentNode -> the parent field (state)
	 * @param actions -> set of actions that can be performed in the field
	 * @return a list with all successors states from the current one
	 ********************************************************************************/
	public ArrayList<Node> successors(Node parentNode, Strategy strategy, int prof) {
		ArrayList<Action> actions = createActions(parentNode.getState());
		ArrayList<Node> successors = new ArrayList<Node>();
		Iterator<Action> it = actions.iterator();
		while(it.hasNext() && prof>parentNode.getDepth()) {			//Obtains the inmediate successors
			Action nextAction = it.next();
			Node newNode = new Node(parentNode, nextAction, strategy);
				successors.add(newNode);
		}
		return successors;
	}//End successsors
	
	/********************************************************************************
	 * Method name: createActions
	 * Description: is in charge of all tasks related to the actions creation
	 * obtaining the adjacent positions where the tractor can move and all the 
	 * possible sand distributions and combine them all
	 * @param field -> current state of the field and the tractor position
	 * @return an ArrayList with all the possible actions
	 ********************************************************************************/
	private static ArrayList<Action> createActions(Field field) {
		ArrayList <int[]> adjacentPositions, sandMovements;
		ArrayList <Action> actions =  new ArrayList<Action>();
		sandMovements= new ArrayList<int[]>();
		checkPositions(field);
		adjacentPositions=moveTractor(field);
		moveSand(field, sandMovements);
		removeMax(sandMovements, field);
		createActions(adjacentPositions, sandMovements, actions);
		return actions;
	}//End successor
	
	/***********************************************************************************************
	 * Method name: removeMax
	 * Description: take the set of distributions and applies the maximum restriction, removing from
	 * the original set all those that violates this restriction, which is that any cell cannot have
	 * more sand than the value stated as maximum
	 * @param sandMovements -> set of sand movements no taking into account the maximum restriction
	 * @param field -> current state
	 ***********************************************************************************************/
	private static void removeMax(ArrayList<int[]> sandMovements, Field field) {
		ArrayList<int[]> aux = new ArrayList<int[]>();
		for(int i = 0; i< sandMovements.size(); i++) {
			int s [] = sandMovements.get(i);
			if( (s[1]!=0 && s[1]+field.getNumber(field.getXt()-1, field.getYt()) > field.getMax()) || //North
				(s[2]!=0 && s[2]+field.getNumber(field.getXt(), field.getYt()-1) > field.getMax()) || //West
				(s[3]!=0 && s[3]+field.getNumber(field.getXt(), field.getYt()+1) > field.getMax()) || //East
				(s[4]!=0 && s[4]+field.getNumber(field.getXt()+1, field.getYt()) > field.getMax())	  //South V
			) 
				aux.add(s);
		}
		sandMovements.removeAll(aux);
	}//End removeMax
	
	/********************************************************************************
	 * Method name: moveTractor
	 * Description: checks whether the tractor can move to each of the four directions
	 * of the field, for that change the values of a boolean array format as 
	 * [N, W, E, S], and that saves a true if it is able to move and false otherwise
	 * @param field -> current state
	 * @return an ArrayList with all the possible movements of the tractor, which
	 * are, the adjacent positions of the current one
	 *******************************************************************************/
	private static ArrayList<int[]> moveTractor(Field field) {
	ArrayList <int[]> adjacent= new ArrayList<int[]>();
	//NORTH		
		if (positions[0]==true) {
			int [] vectorn= new int[2];
			vectorn[0]= field.getXt()-1;
			vectorn[1]= field.getYt();
			adjacent.add(vectorn);
		}
	//WEST
		if (positions[1]==true) {
			int [] vectorw= new int[2];
			vectorw[0]= field.getXt();
			vectorw[1]= field.getYt()-1;
			adjacent.add(vectorw);
		}
	//EAST
		if (positions[2]==true) {
			int [] vectore= new int[2];
			vectore[0]= field.getXt();
			vectore[1]= field.getYt()+1;
			adjacent.add(vectore);
		}
	//SOUTH
		if (positions[3]==true) {
			int [] vectors= new int[2];
			vectors[0]= field.getXt()+1;
			vectors[1]= field.getYt();
			adjacent.add(vectors);
		}
		return adjacent;
	}//End moveTractor

	/*******************************************************************************
	 * Method name: printAdjacent
	 * @param adjacent -> list of available adjacent positions
	 * @param field -> current state of the field
	 *******************************************************************************/
	public static void printAdjacent (ArrayList<int[]> adjacent, Field field) {
		Iterator <int[]> it;
		it=adjacent.iterator();
		System.out.println("-----ADJACENT POSITIONS-----");
		while(it.hasNext()) {
			int [] vector= it.next();
			System.out.println(vector[0] +" "+ vector[1] + "----->" +field.getNumber(vector[0], vector[1]));
		}
	}//End printAdjacent
	
	/******************************************************************************
	 * Method name: moveSand
	 * Description: begins the recursive algorithm that gets all possible sand
	 * distributions in the field
	 * @param field -> current state
	 * @param sandMovements -> set of all possible distributions of sand 
	 * from the current position
	 ******************************************************************************/
	private static void moveSand(Field field, ArrayList<int[]> sandMovements) {
		int [] distribution =  new int[5]; //[pos actual, norte, oeste, este sur]7
		////////////////////////////////Moving //////////////////////////////////////
		int q = field.getNumber(field.getXt(), field.getYt());
		if(q > field.getK()) distribution[0]=field.getNumber(field.getXt(), field.getYt()) - field.getK();
		else distribution[0]=0;
		loop(0, distribution, sandMovements, field);
	}//End moveSand
	
	/*******************************************************************************
	 * Method name: loop
	 * Description: recursive algorithm that combines the quantity of sand available in
	 * the current position of the tractor so we obtain the different possible distributions
	 * @param position -> pointer to the element that is losing sand
	 * @param distribution -> array where the quantity of sand moved is format as [D, N, W, E, S]
	 * where D is the sand not moved
	 * @param solution -> list where a distribution is saved when it is computed
	 * @param field -> current state
	 *********************************************************************************/
	private static void loop(int position, int [] distribution, ArrayList<int[]> solution, Field field) {
		int[] auxDistribution;
		int nextPos;
		auxDistribution = distribution.clone();
			if(distribution[0]==0) solution.add(distribution.clone());
		if(position < distribution.length) {
			nextPos = nextPosAvailable(position, distribution, field);
			for(int j=distribution[position]; j>0; j--) {	
				if(nextPos < distribution.length) {
					auxDistribution[position]--;
					auxDistribution[nextPos]++; 
					loop(nextPos, auxDistribution, solution, field);
				}
			}
		}else return;
	}//End loop
	
	/*************************************************************************************
	 * Method name: nextPosAvailable
	 * Description: computes which is the next positions that can store values checking if
	 * there is adjacent position in each of the four directions
	 * @param position -> current pointer in distribution
	 * @param distribution -> array with format [D, N, W, E, S] where the amount of sand
	 * moved is stored
	 * @param field -> current state
	 * @return the index of the next available position or 5 if there is no more positions
	 *************************************************************************************/
	private static int nextPosAvailable(int position, int [] distribution, Field field) {
		for(int i = position+1; i<distribution.length; i++) {
			if(positions[i-1]) { //Checks if current position is a valid position
				if(i-1 == 0 && field.getNumber(field.getXt()-1, field.getYt())+distribution[1]<field.getMax()) //North
					return i;
				if(i-1 == 1 && field.getNumber(field.getXt(), field.getYt()-1)+distribution[2]<field.getMax()) //West
					return i;
				if(i-1 == 2 && field.getNumber(field.getXt(), field.getYt()+1)+distribution[3]<field.getMax()) //East
					return i;
				if(i-1 == 3 && field.getNumber(field.getXt()+1, field.getYt())+distribution[4]<field.getMax()) //South
					return i;
			}
		}
		return 5; //Key to say there's no more places to place sand
	}//End nextPosAvailable
	
	/************************************************************************************
	 * Method name: createActions
	 * Description: combines all adjacent positions where the tractor can move with all
	 * sand distributions
	 * @param adjacentPositions -> set of coordinates where the tractor can move
	 * @param sandMovements -> set of all possible distributions of sand
	 * @param actions -> list where all actions are being stored
	 ************************************************************************************/
	private static void createActions(ArrayList<int[]> adjacentPositions, ArrayList<int[]> sandMovements,
									  ArrayList<Action> actions) {
		for(int i=0; i<adjacentPositions.size(); i++) {
			for(int j=0; j<sandMovements.size(); j++) {
				actions.add(new Action(adjacentPositions.get(i), sandMovements.get(j)));
			}
		}
	}//End createActions
	
	/*************************************************************************************
	 * Method name: printAction
	 * Description: print the set of actions that has been computed
	 * @param actions -> set of actions
	 *************************************************************************************/
	public static void printActions(ArrayList<Action> actions) {
		Iterator<Action> it = actions.iterator();
		System.out.println("\n-----ACTIONS-----      A  N  W  E  S");
		while(it.hasNext()) {
			System.out.println(it.next());
		}
	}//End printActions
	
	/**************************************************************************************
	 * Method name: checkPositions
	 * Description: checks if the tractor can move to each of the four directions
	 * and set value in positions to true if there is available position in that direction
	 * and set value to false if there is no position. positions is format as [N, W, E, S]
	 * @param field -> current state
	 *************************************************************************************/
	private static void checkPositions(Field field) {
		//NORTH		
			if (field.getXt() > 0 && field.getXt() < field.getSizeR()) {
				positions[0]= true;
			}else {
				positions[0]=false;
			}
		//WEST
			if (field.getYt() > 0 && field.getYt() < field.getSizeC()) {
				positions[1]=true;
			}else {
				positions[1]=false;
			}
		//EAST
			if (field.getYt() < field.getSizeC()-1) {
				positions[2]=true;
			}else {
				positions[2]=false;
			}
		//SOUTH
			if (field.getXt() < field.getSizeR()-1) {
				positions[3]=true;
			}else {
				positions[3]=false;
			}
	}//End checkPositions
	

	
}//End Successor
