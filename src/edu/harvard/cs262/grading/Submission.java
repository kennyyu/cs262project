package edu.harvard.cs262.grading;

import java.sql.Blob;
import java.sql.Timestamp;

public interface Submission {

	/**
	 * @return the student ID for this submission
	 */
	public StudentID getStudentID();

	/**
	 * @return the assignment ID for this submission
	 */
	public AssignmentID getAssignmentID();

	/**
	 * @return the timestamp ID for this submission
	 */
	public Timestamp getTimeStamp();

	/**
	 * @return the contents of this submission as a blob
	 */
	public Blob getContents();
}
