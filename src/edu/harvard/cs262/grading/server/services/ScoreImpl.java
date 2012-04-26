package edu.harvard.cs262.grading.server.services;

public class ScoreImpl implements Score {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8497228136055930585L;
	private int score;
	private int maxScore;

	public ScoreImpl(int score, int maxScore) {
		this.score = score;
		this.maxScore = maxScore;
	}

	/**
	 * @return the score as an integer
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @return the largest possible score
	 */
	public int maxScore() {
		return maxScore;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (score ^ (score >>> 32))
				+ (int) (maxScore ^ (maxScore) >>> 32);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ScoreImpl other = (ScoreImpl) obj;
		if (score != other.score)
			return false;
		if (maxScore != other.maxScore)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Score: " + score + "/" + maxScore;
	}

}
