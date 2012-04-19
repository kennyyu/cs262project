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

}
