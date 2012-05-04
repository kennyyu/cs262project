package edu.harvard.cs262.grading.server.services;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Represents a student submission.
 */
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
	public byte[] getContents();
}
