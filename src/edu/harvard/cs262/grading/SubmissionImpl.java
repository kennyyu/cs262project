/**
 * 
 */
package edu.harvard.cs262.grading;

import java.sql.Blob;
import java.sql.Timestamp;
import java.util.Date;

public class SubmissionImpl implements Submission {
	
	private StudentID studentID;
	private AssignmentID assignmentID;
	private Blob contents;
	private Timestamp timestamp;
	
	public SubmissionImpl(StudentID studentID, AssignmentID assignmentID, Blob contents) {
		this.studentID = studentID;
		this.assignmentID = assignmentID;
		this.contents = contents;
		this.timestamp = new Timestamp((new Date()).getTime());
	}

	@Override
	public StudentID getStudentID() {
		return studentID;
	}

	@Override
	public AssignmentID getAssignmentID() {
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
