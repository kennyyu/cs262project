package edu.harvard.cs262.grading;

/**
 * Interface for grades. This contains no data about the submission.
 */
public interface Score {

	/**
	 * @return the score as an integer
	 */
	public int getScore();

	/**
	 * @return the largest possible score
	 */
	public int maxScore();
}
