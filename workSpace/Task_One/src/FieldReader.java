import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;
import java.util.Scanner;

public class FieldReader {

	private String fileName;
	private FileReader fileReader;
	private Field field;
	private int [][] fieldContent;
	private Scanner read;
	private File file;
	
	public FieldReader(String fileName, Field field) {
		this.fileName = fileName;
		this.field = field;
	}
	
	public void readField() {
		try {
			fileReader = new FileReader(fileName);
			file = new File(fileName);
			readFirstLine();
			readFieldContent();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	private void readFirstLine() {
		String line;
		String[] lineSplit;
		try {
			read = new Scanner(file);
			while(read.hasNext()) {
				line = read.nextLine();
				lineSplit = line.split(" ");
				/////
				for(int i = 0; i< lineSplit.length; i++)
					System.out.print(lineSplit[i] + " ");
				//////
				field.setXt(Integer.parseInt(lineSplit[0]));
				field.setYt(Integer.parseInt(lineSplit[1]));
				field.setK(Integer.parseInt(lineSplit[2]));
				field.setMax(Integer.parseInt(lineSplit[3]));
				field.setSize(Integer.parseInt(lineSplit[4]));
				//If columns and rows are different numbers save next line
				//read.nextInt();
				//read.nextLine();
			}
		} catch (FileNotFoundException e) {
			e.getMessage();
		}
		
	}
	
	private void readFieldContent() {
		fieldContent = new int[field.getSize()][field.getSize()];
		for(int i =0; i<field.getSize(); i++) {
			for(int j =0; j<field.getSize(); j++) {
				read.next();
				fieldContent[i][j] = read.nextInt();
			}
		}
		field.setField(fieldContent);
	}
}//End fieldReader
