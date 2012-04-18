package edu.harvard.cs262.grading;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Timestamp;

public interface Submission extends Serializable {

	/**
	 * @return the student for this submission
	 */
	public Student getStudent();

	/**
	 * @return the assignment for this submission
	 */
	public Assignment getAssignment();

	/**
	 * @return the timestamp for this submission
	 */
	public Timestamp getTimeStamp();

	/**
	 * @return the contents of this submission as a blob
	 */
	public Blob getContents();
}
