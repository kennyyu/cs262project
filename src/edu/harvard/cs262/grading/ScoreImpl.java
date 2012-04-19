package edu.harvard.cs262.grading;

public class ScoreImpl implements Score {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8497228136055930585L;
	private int score;
	private int maxScore;
	
	public ScoreImpl(int score, int maxScore)
	{
		this.score = score;
		this.maxScore = maxScore;
	}
	
	/**
	 * @return the score as an integer
	 */
	public int getScore()
	{
		return score;
	}

	/**
	 * @return the largest possible score
	 */
	public int maxScore()
	{
		return maxScore;
	}
	
}
