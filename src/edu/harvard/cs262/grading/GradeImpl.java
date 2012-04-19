package edu.harvard.cs262.grading;

import java.sql.Timestamp;

public class GradeImpl implements Grade {

	private final Score score;
	private final Student grader;
	private final Timestamp time;
	
	public GradeImpl(Score score, Student grader, Timestamp time) {
		this.score = score;
		this.grader = grader;
		this.time = time;
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
		return time;
	}

}
