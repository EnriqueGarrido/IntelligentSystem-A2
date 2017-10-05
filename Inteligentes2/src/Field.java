import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


public class Field {
	
	private int [][] field;
	private int xt, yt, k, max, size;
	
	public Field() {
		
	}//End constructor
	
	public void writeField() {
		FileGenerator fGenerator = new FileGenerator("Setup.txt");
		fGenerator.generate();
	}
	
	public void readField() {
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
		try{
			archivo = new File("Setup.txt");
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);
			String line=br.readLine();//first line
			readFirstLine(line);
			String matrix=br.readLine();//second line
			for(int s=1;s<size; s++) {
				matrix=matrix+br.readLine();
			}
			matrix= matrix.substring(1, matrix.length());
			generateValuesField(matrix);
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
				if(null != fr){
					fr.close();
				}
			}catch(Exception e2){
				e2.printStackTrace();
			}
		}
		
	}
	
	public void readFirstLine(String line){
		String[] values=new String[6];
		System.out.println(line);
		int i=0;
		for (String retval: line.split(" ")) {
		         values[i++]=retval;
		}
		xt=Integer.parseInt(values[0]);
		yt=Integer.parseInt(values[1]);
		k=Integer.parseInt(values[2]);
		max=Integer.parseInt(values[3]);
		size=Integer.parseInt(values[4]);
	}
	
	public void generateValuesField(String line){
		String[] values=new String[size*size];
		field=new int[size][size];
		System.out.println(line);
		int i=0;
		for (String retval: line.split(" ")) {
		         values[i++]=retval;
		}
		i=0;
		try{
			for(int j=0;j<size;j++){
				for(int k=0;k<size;k++){
					field[j][k]=Integer.parseInt(values[i++]);
				}
			}
		}catch(NumberFormatException ex){ // handle your exception
			ex.printStackTrace();
		}
		
	}

	public int[][] getField() {
		return field;
	}

	public void setField(int[][] field) {
		this.field = field;
	}

	public int getXt() {
		return xt;
	}

	public void setXt(int xt) {
		this.xt = xt;
	}

	public int getYt() {
		return yt;
	}

	public void setYt(int yt) {
		this.yt = yt;
	}

	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void printMatrix() {
		for(int j=0;j<size;j++){
			for(int k=0;k<size;k++){
				System.out.print(field[j][k]+ " ");
			}
			System.out.println();
		}
	}
	
	
	
}//End Field class
