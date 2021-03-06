package domain;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Field {
	
	private String path; //the place where the file is situated
	private int [][] field; //the state of the floor with the quantities of sand
	private int xt; //The coordinate x
	private int yt; //The coordinate y
	private int k; //The quantity of sand we want in each position
	private int max; //The maximum number of sand in each position
	private int sizec; //number of columns
	private int sizer; //number of rows
	
	
	
	/*************************************************************************************
	 * Method name: Field
	 * Description: Constructor class for the field from a file
	 * @param path -> the place where the file is situated
	 ************************************************************************************/
	public Field(String path) {
		this.path = path;
		readField();
		checkAmount();
	}//End constructor
	
	/*************************************************************************************
	 * Method name: Field
	 * Description: Constructor class for the field from a current Field and an action
	 * that is performed to create an additional state field
	 * @param f -> parent field
	 * @param action -> action to be performed
	 ************************************************************************************/
	public Field(Field f, Action action) {
		this.xt = f.getXt();
		this.yt = f.getYt();
		this.k = f.getK();
		this.max = f.getMax();
		this.sizec = f.getSizeC();
		this.sizer = f.getSizeR();
		this.field = new int[sizer][sizec];

		int[][] aux = f.getField();
		for(int i =0; i<this.field.length; i++) {
			for(int j=0; j<this.field[1].length; j++) {
				this.field[i][j] = aux[i][j];
			}
		}
		action.perform(this);
	}
	//Constructor para el goal
	public Field(Field f) {
		generateGoal(f);
	}
	
	/**************************************************************************************************
	 * Method name: readField
	 * Description: it will get file's contents. Then, in other methods we will analyze the information.
	 ***************************************************************************************************/
	public void readField() {
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
		try{
			archivo = new File(path);
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);
			String line=br.readLine();//first line
			readFirstLine(line);
			String matrix=br.readLine();//second line
			for(int s=1;s<sizer; s++) {
				matrix=matrix+br.readLine();
			}
			matrix= matrix.substring(1, matrix.length());
			generateValuesField(matrix);
		}catch (Exception e) {
			System.out.println("Error in file format. Check file: \""+e.getMessage()+"\"");
			System.exit(0);
		}finally{
			try{
				if(null != fr){
					fr.close();
				}
			}catch(Exception e2){
				e2.printStackTrace();
			}
		}
		
	}//End readField
	/************************************************************************************************************************
	 * Method name: generateGoal
	 * Description: It generates the matrix with all numbers sets to k
	 **********************************************************************************************************************/
	public void generateGoal(Field f) {
		field = new int[f.getSizeR()][f.getSizeC()];
		for(int i = 0; i< field.length; i++)
			for(int j =0; j<field[i].length; j++)
				field[i][j] = f.getK();
	}//End generateGoal
	
	/*************************************************************************************
	 * Method name: readFirstLine
	 * Description: it will catch the values for the variables xt, xy, k, max, sicec and sicer
	 * and inform about some errors checking the values
	 * @param line -> the string with the information we will catch for each variable
	 * @throws Exception
	 ************************************************************************************/
	public void readFirstLine(String line)throws Exception{
		String[] values=new String[6];
		int i=0;
		for (String retval: line.split(" ")) {
		         values[i++]=retval;
		}
		xt=Integer.parseInt(values[0]);
		yt=Integer.parseInt(values[1]);
		k=Integer.parseInt(values[2]);
		max=Integer.parseInt(values[3]);
		sizec=Integer.parseInt(values[4]);
		sizer=Integer.parseInt(values[5]);
		if(xt<0 || xt>=sizer || yt<0 || yt>=sizec || k<1 || max<1 || sizer<1 || sizec<1) 
			throw new Exception("First line format is incorrect");
		try {
			if (max<=k) {
				throw new Exception();
			}
		}catch(Exception e) {
			System.out.println("Max must be greater than k");
			System.exit(0);
		}
	}//End readFirstLine
	
	/*************************************************************************************
	 * Method name: generateValuesField
	 * Description: it will catch the sand values for the field.
	 * @param line -> the string with the information we will catch for the quantities of sand
	 * @throws Exception
	 ************************************************************************************/
	public void generateValuesField(String line) throws Exception{
		String[] values=new String[sizec*sizer];
		field=new int[sizer][sizec];
		try{
			int i=0;
			for (String retval: line.split(" ")) {
			         values[i++]=retval;
			         if(Integer.parseInt(retval)<0 || Integer.parseInt(retval)>max) throw new Exception();
			}
			i=0;
			for(int j=0;j<sizer;j++)
				for(int k=0;k<sizec;k++)
					field[j][k]=Integer.parseInt(values[i++]);
		}catch(Exception ex){ // handle your exception
			System.out.println("Error reading field. Check file. : " + ex.getMessage());
			System.exit(0);
		}
	}//End generateValuesField

	/*************************************************************************************
	 * Method name: printMatrix
	 * Description: it will print the current state of the field
	 ************************************************************************************/
	public void printMatrix() {
		for(int j=0;j<sizer;j++){
			for(int k=0;k<sizec;k++){
				System.out.print(field[j][k]+ " ");
			}
			System.out.println();
		}
	}//End printMatrix
	
	/*************************************************************************************
	 * Method name: getNumber
	 * Description: it returns the quantity of sand in that position
	 * @param xt -> The coordinate x
	 * @param xy -> The coordinate y
	 ************************************************************************************/
	public int getNumber(int xt, int yt) {
		return field[xt][yt];
	}//End getNumber
	
	/*************************************************************************************
	 * Method name: setNumber
	 * Description: it returns the new quantity of sand in that position
	 * @param xt -> The coordinate x
	 * @param xy -> The coordinate y
	 * @param quantity -> The quantity of sand which that position have
	 ************************************************************************************/
	public void setNumber(int xt, int yt, int quantity) {
		field[xt][yt] =quantity;
	}//End setNumber
	
	/*************************************************************************************
	 * Method name: saveMatrix
	 * Description: it saves in a string the current state of the field
	 ************************************************************************************/
	public String saveMatrix() {
		String matrix="";
		for(int j=0;j<sizer;j++){
			matrix = matrix + "\t";
			for(int k=0;k<sizec;k++){
				if(j == xt && k == yt)
					matrix= matrix +"|"+field[j][k]+ "|\t";
				else
					matrix= matrix +" "+field[j][k]+ "\t";
			}
			matrix=matrix + "\n";
		}
		return matrix;
	}//End saveMatrix
	
	/********************************************************************************************
	 * Method name: isGoal
	 * Description: Compare both matrix to know if the matrix is goal or not
	 * @param field -> one matrix to compare with the other
	 * @return true -> if the field is goal it returns true
	 *******************************************************************************************/
	public boolean isGoal() {
		//int [][] aux = f.getField();
		for(int i =0; i< field.length; i++)
			for(int j =0; j < field[0].length; j++)
				if(this.field[i][j] != k) return false;
		return true;
	}
	/*********************************************************************************************
	 * Method name: compareField
	 * Description: it compares the parent node with the other matrix to prove that the parent
	 * @param parent -> The matrix that we have to prove that is the father
	 * @return true -> if the matrix is the parent of the other child is true
	 *********************************************************************************************/
	public boolean compareField(Field parent) {
		for(int i = 0; i < sizer; i++) 
			for(int j = 0; j < sizec; j++) 
				if(parent.getField()[i][j] != field[i][j]) return false;
		return true;
	}
	
	/*********************************************************************************************
	 * 
	 *********************************************************************************************/
	public void checkAmount(){
		double amount = 0;
		double tocheck = 0f;
		for(int i  = 0; i< field.length; i++) 
			for(int j = 0; j < field[0].length; j++) 
				amount += field[i][j];
		tocheck = (double)amount/((double)sizer*(double)sizec);
		if(tocheck != k) {
			System.out.println("There is no possible solution as total sand amount cannot be uniformly distributed");
			System.exit(0);
		}
	}
	
	public int h() {
		int h=0;
		for(int i=0; i<field.length; i++)
			for(int j=0; j<field[i].length; j++)
				if(field[i][j]!=k) h++;
		return h;
	}
	/*************************************************************************************
	 * Method name: getField
	 * Description: it gets the field
	 * @return the field
	 ************************************************************************************/
	public int[][] getField() {
		return field;
	}//End getField

	/*************************************************************************************
	 * Method name: setField
	 * Description: it sets the field
	 ************************************************************************************/
	public void setField(int[][] field) {
		this.field = field;
	}//End setField

	/*************************************************************************************
	 * Method name: getXt
	 * Description: it gets the coordinate x where the tractor is situated
	 * @return the coordinate x
	 ************************************************************************************/
	public int getXt() {
		return xt;
	}//End getXt

	/*************************************************************************************
	 * Method name: setXt
	 * Description: it sets the coordinate x
	 ************************************************************************************/
	public void setXt(int xt) {
		this.xt = xt;
	}//End setXt

	/*************************************************************************************
	 * Method name: getYt
	 * Description: it gets the coordinate y where the tractor is situated
	 * @return the coordinate y
	 ************************************************************************************/
	public int getYt() {
		return yt;
	}//End getYt

	/*************************************************************************************
	 * Method name: setYt
	 * Description: it sets the coordinate y
	 ************************************************************************************/
	public void setYt(int yt) {
		this.yt = yt;
	}//End setYt

	/*************************************************************************************
	 * Method name: getK
	 * Description: it gets the k value
	 * @return quantity of sand we want in each position (k)
	 ************************************************************************************/
	public int getK() {
		return k;
	}//End getK

	/*************************************************************************************
	 * Method name: setK
	 * Description: it sets k value
	 ************************************************************************************/
	public void setK(int k) {
		this.k = k;
	}//End setK

	/*************************************************************************************
	 * Method name: getMax
	 * Description: it gets the max value
	 * @return maximum quantity of sand (max)
	 ************************************************************************************/
	public int getMax() {
		return max;
	}//End getMax

	/*************************************************************************************
	 * Method name: setMax
	 * Description: it sets the max value
	 ************************************************************************************/
	public void setMax(int max) {
		this.max = max;
	}//End setMax

	/*************************************************************************************
	 * Method name: getSizeR
	 * Description: it gets the number of rows
	 * @return number of rows (sizer)
	 ************************************************************************************/
	public int getSizeR() {
		return sizer;
	}//End getSizeR

	/*************************************************************************************
	 * Method name: setSizeR
	 * Description: it sets the number of rows
	 ************************************************************************************/
	public void setSizeR(int size) {
		this.sizer = size;
	}//End setSizeR
	
	/*************************************************************************************
	 * Method name: getSizeC
	 * Description: it gets the number of columns
	 * @return number of columns (sizec)
	 ************************************************************************************/
	public int getSizeC() {
		return sizec;
	}//End getSizeC
	
	/*************************************************************************************
	 * Method name: setSizec
	 * Description: it sets the number of columns
	 ************************************************************************************/
	public void setSizec(int size) {
		this.sizec=size;
	}//End setSizec
	
}//End Field class


