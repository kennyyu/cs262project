package edu.harvard.cs262.grading;

public class AssignmentImpl implements Assignment {

	private long assignmentID;
	
	static long counter = 0;
	
	public AssignmentImpl() {
		this.assignmentID = counter++;
	}
	
	public AssignmentImpl(int assignmentID) {
		this.assignmentID = assignmentID;
	}
	
	@Override
	public long assignmentID() {
		return this.assignmentID;
	}

}
