package edu.harvard.cs262.grading;

import java.sql.Timestamp;
import java.util.Date;

public class GradeImpl implements Grade {

	private final Score score;
	private final Student grader;
	private final Timestamp timestamp;
	
	public GradeImpl(Score score, Student grader) {
		this.score = score;
		this.grader = grader;
		this.timestamp = new Timestamp((new Date()).getTime());
	}
	
	public GradeImpl(Score score, Student grader, Timestamp timestamp) {
		this.score = score;
		this.grader = grader;
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

}
