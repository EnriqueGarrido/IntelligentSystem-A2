
public class Problem {
	
	private Field initState;
	private Field goalState; 

	public Problem(String file) {
		initState = new Field(file);
		goalState = new Field(initState);
	}
	
	public Field getGoalState() {
		return goalState;
	}

	public Field getInitState() {
		return initState;
	}

	
	
}
