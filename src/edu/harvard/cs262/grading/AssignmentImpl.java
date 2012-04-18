package edu.harvard.cs262.grading;

public class AssignmentImpl implements Assignment {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int assignmentID;
	
	static int counter = 0;
	
	public AssignmentImpl() {
		this.assignmentID = counter++;
	}
	
	public AssignmentImpl(int assignmentID) {
		this.assignmentID = assignmentID;
	}
	
	@Override
	public int assignmentID() {
		return this.assignmentID;
	}

}
