package edu.harvard.cs262.grading;

import java.sql.Blob;
import java.sql.Timestamp;

public interface Submission {
	public StudentId getStudentID();
	public AssignmentId getAssignmentID();
	public Timestamp getTimeStamp();

	public Blob getContents();
}
