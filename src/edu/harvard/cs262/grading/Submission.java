package edu.harvard.cs262.grading;

import java.sql.Blob;
import java.sql.Timestamp;

public interface Submission {

	/**
	 * @return the student ID for this submission
	 */
	public Student getStudentID();

	/**
	 * @return the assignment ID for this submission
	 */
	public Assignment getAssignmentID();

	/**
	 * @return the timestamp ID for this submission
	 */
	public Timestamp getTimeStamp();

	/**
	 * @return the contents of this submission as a blob
	 */
	public Blob getContents();
}
