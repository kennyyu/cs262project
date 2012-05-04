package edu.harvard.cs262.grading.server.services;

public class AssignmentImpl implements Assignment {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6532018502443527393L;
	private long assignmentID;

	private String description;

	public AssignmentImpl() {
		this.assignmentID = 0;
		this.description = "";
	}

	public AssignmentImpl(long ID) {
		this(ID, "");
	}

	public AssignmentImpl(long long1, String desc) {
		this.assignmentID = long1;
		this.description = desc;
	}

	@Override
	public long assignmentID() {
		return this.assignmentID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		// XXX added cast to int as a hack...
		result = (int) (prime * result + assignmentID);
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

	@Override
	public String toString() {
		return "Assignment: " + assignmentID;
	}

	@Override
	public String description() {
		return description;
	}

}
