package edu.harvard.cs262.grading.server.services;

public class NoShardsForAssignmentException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6181663360092761487L;

	public NoShardsForAssignmentException(Assignment assignment) {
		super("No shards have been generated for assignment " + assignment.assignmentID());
	}

}
