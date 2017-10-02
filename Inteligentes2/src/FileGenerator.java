import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Random; 

public class FileGenerator {

	private FileWriter fileWriter;
	private Scanner readKey = new Scanner(System.in);
	private Random rand = new Random(System.currentTimeMillis());
	private String fileName;
	private int size;
	private int max;
	
	public FileGenerator(String fileName) {
		this.fileName = fileName;
	}
	
	public void generate() {
		try {
			fileWriter = new FileWriter(fileName);
			askDimension();
			generateFirstLine();
			generateField();
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.toString();
		}
	}
	
	private void askDimension() {
		System.out.print("Dimension of the field (NxN): ");
		size = readKey.nextInt();
		System.out.print("Max of sand in a box: ");
		max = readKey.nextInt();
	}
	
	private void generateFirstLine() {
		int xt, yt, k;
		//MIRAR PORQUE SIEMPRE SALE EN ALEATORIO UNO MENOS
		xt = rand.nextInt(size);
		yt = rand.nextInt(size);
		k = rand.nextInt(max);
		
		try {
			fileWriter.write(xt + " " + yt + " " + k + " " + max + " " + size + " " + size + "\r\n");
		} catch (IOException e) {
			e.toString();
		}
	}
	
	private void generateField() {
		try {
			for(int i=0; i<size; i++) {
				for(int j=0; j<size; j++) {
					fileWriter.write(" " + rand.nextInt(max+1));
				}
				fileWriter.write("\r\n");
			}
		}catch(Exception e) {
			e.toString();
		}
		
	}
}//End file
