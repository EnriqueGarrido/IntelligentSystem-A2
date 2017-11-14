
public class Problem {
	
	private Field initState;
	private Field goalState; 
	/***************************************************************************
	 * Method name: Problem
	 * Description: Constructor of the class problem
	 * @param file -> Is the input of the program, the file that we are going
	 * to read
	 **************************************************************************/
	public Problem(String file) {
		initState = new Field(file);
		goalState = new Field(initState);
	}
	/****************************************************************************
	 * Method name: getGoalState
	 * Description: This is the objective of our function, the state that we want
	 * to arrive
	 * @return goalState -> The objective of our function
	 *****************************************************************************/
	public Field getGoalState() {
		return goalState;
	}
	/******************************************************************************
	 * Method name: getInitState
	 * Description: This function is to know the initial s
	 * @return initState -> The initial state of our program
	 *****************************************************************************/
	public Field getInitState() {
		return initState;
	}

	
	
}
