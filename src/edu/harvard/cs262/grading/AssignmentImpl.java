package edu.harvard.cs262.grading;

public class AssignmentImpl implements Assignment {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int assignmentID;
	
	/*
	static int counter = 0;
	public AssignmentImpl() {
		this.assignmentID = counter++;
	}
	*/
	
	public AssignmentImpl(int assignmentID) {
		this.assignmentID = assignmentID;
	}
	
	@Override
	public long assignmentID() {
		return this.assignmentID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + assignmentID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AssignmentImpl other = (AssignmentImpl) obj;
		if (assignmentID != other.assignmentID)
			return false;
		return true;
	}

}
