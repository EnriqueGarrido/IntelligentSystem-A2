import java.io.*;
import java.util.*;

public class Interface {

	static Scanner sc= new Scanner(System.in);
	static UninformedSearch uSearch= new UninformedSearch();
	public static void main(String[] args) throws FileNotFoundException {
		String inputFile;
		System.out.println("Input File: ");
		inputFile= sc.next();
		Problem prob = new Problem(inputFile);
		Strategy str=menu();
		int max= obtainValue("Max depth: ");
		int incr;
		if (str==Strategy.IDS) {
			incr= obtainValue("Increment: ");
		}else {
			incr=1;
		}
		ArrayList<Node> list=uSearch.search(prob, str, max, incr);
		for(int i = 0; i<list.size(); i++) {
			System.out.println(i+1 + "- "+list.get(list.size()-i-1).getAction());
			System.out.print(i+1 + "- \n");
			System.out.print(list.get(list.size()-i-1).getState().saveMatrix());
			System.out.println(list.get(list.size()-i-1).getAction());
		}
	}
	
	public static Strategy menu() {
		boolean select=true;
		int opt;
		Strategy str=null;
		while(select==true) {
			System.out.println("Choose a strategy: \n1-BFS \n2-DFS \n3-DLS \n4-IDS \n5-UCS");
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
			default: 
				System.out.println("Error. Write a number between 1-5");
			}
		}
		return str;
	}
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
}
