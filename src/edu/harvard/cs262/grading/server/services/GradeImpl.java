package edu.harvard.cs262.grading.server.services;

import java.sql.Timestamp;
import java.util.Date;

public class GradeImpl implements Grade {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1144414253427338461L;
	private final Score score;
	private final Student grader;
	private final String comments;
	private final Timestamp timestamp;
	
	public GradeImpl(Score score, Student grader, String comments) {
		this.score = score;
		this.grader = grader;
		this.comments = comments;
		this.timestamp = new Timestamp((new Date()).getTime());
	}
	
	public GradeImpl(Score score, Student grader, String comments, Timestamp timestamp) {
		this.score = score;
		this.grader = grader;
		this.comments = comments;
		this.timestamp = timestamp; 
	}
	
	@Override
	public Score getScore() {
		return score;
	}

	@Override
	public Student getGrader() {
		return grader;
	}

	@Override
	public String getComments() {
		return comments;
	}

	@Override
	public Timestamp getTimeStamp() {
		return timestamp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((grader == null) ? 0 : grader.hashCode());
		result = prime * result + ((score == null) ? 0 : score.hashCode());
		result = prime * result
				+ ((timestamp == null) ? 0 : timestamp.hashCode());
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
		GradeImpl other = (GradeImpl) obj;
		if (grader == null) {
			if (other.grader != null)
				return false;
		} else if (!grader.equals(other.grader))
			return false;
		if (score == null) {
			if (other.score != null)
				return false;
		} else if (!score.equals(other.score))
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Grade. " + score + ", " + grader + ", " + timestamp;
	}

}
