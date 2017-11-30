package domain;
import java.io.*;
import java.util.*;

public class Interface {

	static Scanner sc= new Scanner(System.in);
	static UninformedSearch uSearch= new UninformedSearch();
	
	/***********************************************************************************************************
	 * Class name: Main
	 * Description: in this class all computations of the executions of the problem are performed, including
	 * the main method with an example of a performed action on the field
	 * @author Adrian Ollero Jimenez, Enrique Garrido Pozo, Pablo Mora Herreros 
	 * Subject: Intelligent Systems
	 * Group: A2
	 **********************************************************************************************************/
	public static void main(String[] args) throws IOException {
		double timeF, timeI;
		String path= "Output.txt";
		File file=new File(path);
		BufferedWriter br;
		br= new BufferedWriter(new FileWriter(file));
		String inputFile;
		System.out.println("Input File: ");
		inputFile= sc.next();
		Problem prob = new Problem(inputFile);
		//while(true) {
			Strategy str=menu();
			int max= obtainValue("Max depth: ");
			int incr;
			if (str==Strategy.IDS) {
				incr= obtainValue("Increment: ");
			}else {
				incr=max;
			}
			askOptimization();
			timeI = System.currentTimeMillis();
			ArrayList<Node> list=uSearch.search(prob, str, max, incr);
			timeF = System.currentTimeMillis();
			System.out.println("Time: " + (timeF-timeI) + "ms");
			if(list!=null) {
				br.write("Strategy: "+str.toString());
				br.newLine();
				br.write("n-\t(X, Y)[N, W, E, S]");
				br.newLine();
				for(int i = 0; i<list.size(); i++) {
					System.out.println(i+1 + "- "+list.get(list.size()-i-1).getAction());
					br.write(i+1 + "-\t"+list.get(list.size()-i-1).getAction().getActionRepresentation());
					br.newLine();
					//br.write(i+1 + "- "+list.get(list.size()-i-1).getAction());
					//System.out.print("\n");
					//br.write("\n");
					System.out.print(list.get(list.size()-i-1).getState().saveMatrix());
					//br.write(list.get(list.size()-i-1).getState().saveMatrix());
					//br.newLine();
					//br.newLine();
				}
				br.write("Cost: " + list.get(0).getCost());
				br.newLine();
				br.write("Time: "+ (timeF-timeI) + "ms");
				br.newLine();
				br.write("Depth: "+list.get(0).getDepth());
				br.newLine();
				br.write("Spatial Complexity: " + uSearch.nNodes() + " nodes generated");
				br.newLine();
				br.write("Optimization: "+ (uSearch.getOptimization()? "Yes":"No"));
				br.newLine();
			}else {
				System.out.println("No solution could be found. Check max depth");
				br.write("No solution could be found. Check max depth");
			}
			br.close();
		//}
	}
	/**************************************************************************************
	 * Class name: menu
	 * Description: This is a menu that we use to choose the strategy that we will use.
	 * @return str -> It returns the strategy that we will use to aplicate later
	 **************************************************************************************/
	public static Strategy menu() {
		boolean select=true;
		int opt;
		Strategy str=null;
		while(select==true) {
			System.out.println("Choose a strategy: \n1-BFS \n2-DFS \n3-DLS \n4-IDS \n5-UCS \n6-A*");
			opt=sc.nextInt();
			switch(opt) {
			case 1:
				str=Strategy.BFS;
				select=false;
				break;
			case 2:
				str=Strategy.DFS;
				select=false;
				break;
			case 3: 
				str= Strategy.DLS;
				select=false;
				break;
			case 4:
				str= Strategy.IDS;
				select=false;
				break;
			case 5:
				str= Strategy.UCS;
				select=false;
				break;
			case 6:
				str = Strategy.A_STAR;
				select = false;
				break;
			default: 
				System.out.println("Error. Write a number between 1-5");
			}
		}
		return str;
	}
	/*********************************************************************************
	 * Method: obtainValue
	 * Description: This is method to check that the number that we are going to 
	 * introduce is not a negative number
	 * @param cadena -> The program we will ask to us about what the program wants
	 * @return value -> The value that we want
	 ********************************************************************************/
	public static int obtainValue(String cadena) {
		System.out.println(cadena);
		int value=sc.nextInt();
		while (value <= 0){					
			System.out.println("Error. Choose another number");	
			System.out.print(cadena);
			value=sc.nextInt();
		}
		return value;
	}
	
	public static void askOptimization() {
		boolean ask = true;
		while(ask) {
			System.out.print("Optimization? (Y/N) ");
			switch(sc.next()) {
				case "Y": case "YES": case "yes": case "y": case "Yes":
					uSearch.setOptimization(true); ask = false; break;
				case "N": case "NO": case "no": case "n": case "No":
					uSearch.setOptimization(false); ask = false; break;
				default: System.out.println("No correct ");
			}
		}
		
	}
}
