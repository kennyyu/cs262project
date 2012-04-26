package edu.harvard.cs262.grading.server.services;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * A Grade contains a score and the grader who gave that score.
 */
public interface Grade extends Serializable {

	/**
	 * @return the score for this grade
	 */
	public Score getScore();

	/**
	 * @return the student who gave this grade
	 */
	public Student getGrader();

	/**
	 * @return the comments associated with this grade
	 */
	public String getComments();

	/**
	 * @return the time this grade was submitted
	 */
	public Timestamp getTimeStamp();
}
