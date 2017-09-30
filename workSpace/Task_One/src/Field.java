
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
		FieldReader fReader = new FieldReader("Setup.txt", this);
		fReader.readField();
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
	
	
	
}//End Field class
