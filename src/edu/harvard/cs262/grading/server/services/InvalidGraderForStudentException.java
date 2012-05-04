package edu.harvard.cs262.grading.server.services;

/**
 * Exception for when a student is not permitted to grade 
 * another student.
 */
public class InvalidGraderForStudentException extends Exception {

	private static final long serialVersionUID = -364781688236283628L;

	public InvalidGraderForStudentException(Student grader, Student submitter,
			Shard shard) {
		super("Student " + grader + " is not allowed to grade student "
				+ submitter + " for this sharding: " + shard);
	}

}
