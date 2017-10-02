public class mainClass {
	
	public static void main(String[] args) {
		// TO-DO 
		// - Try catch for the standard input
		
		generateFile();
		Field field = new Field();
		field.readField();
		System.out.println("Xt: " + field.getXt());
		System.out.println("Yt: " + field.getYt());
		System.out.println("K: " + field.getK());
		System.out.println("Max: " + field.getMax());
		System.out.println("Size: " + field.getSize());
		field.printMatrix();
	}
	
	private static void generateFile() {
		FileGenerator fGenerator = new FileGenerator("Setup.txt");
		fGenerator.generate();
	}
	
}//End mainClass