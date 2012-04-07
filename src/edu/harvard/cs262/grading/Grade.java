package edu.harvard.cs262.grading;

import java.sql.Timestamp;

/**
 * A Grade contains a score and the grader who gave that score.
 */
public interface Grade {

	/**
	 * @return the score for this grade
	 */
	public Score getScore();

	/**
	 * @return the student who gave this grade
	 */
	public Student getGrader();

	/**
	 * @return the time this grade was submitted
	 */
	public Timestamp getTimeStamp();
}
