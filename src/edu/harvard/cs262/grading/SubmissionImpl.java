/**
 * 
 */
package edu.harvard.cs262.grading;

import java.sql.Blob;
import java.sql.Timestamp;
import java.util.Date;

public class SubmissionImpl implements Submission {

	private Student studentID;
	private Assignment assignmentID;
	private Blob contents;
	private Timestamp timestamp;

	public SubmissionImpl(Student studentID, Assignment assignmentID,
			Blob contents) {
		this.studentID = studentID;
		this.assignmentID = assignmentID;
		this.contents = contents;
		this.timestamp = new Timestamp((new Date()).getTime());
	}

	@Override
	public Student getStudentID() {
		return studentID;
	}

	@Override
	public Assignment getAssignmentID() {
		return assignmentID;
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
