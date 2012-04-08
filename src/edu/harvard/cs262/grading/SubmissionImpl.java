package edu.harvard.cs262.grading;

import java.sql.Blob;
import java.sql.Timestamp;
import java.util.Date;

public class SubmissionImpl implements Submission {

	private final Student student;
	private final Assignment assignment;
	private final Blob contents;
	private final Timestamp timestamp;

	public SubmissionImpl(Student student, Assignment assignment,
			Blob contents) {
		this.student = student;
		this.assignment = assignment;
		this.contents = contents;
		this.timestamp = new Timestamp((new Date()).getTime());
	}

	@Override
	public Student getStudent() {
		return student;
	}

	@Override
	public Assignment getAssignment() {
		return assignment;
	}

	@Override
	public Timestamp getTimeStamp() {
		return timestamp;
	}

	@Override
	public Blob getContents() {
		return contents;
	}

}
