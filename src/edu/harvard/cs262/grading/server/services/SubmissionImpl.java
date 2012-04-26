package edu.harvard.cs262.grading.server.services;

import java.sql.Timestamp;
import java.util.Date;

public class SubmissionImpl implements Submission {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3891098591063077329L;
	private final Student student;
	private final Assignment assignment;
	private final byte[] contents;
	private final Timestamp timestamp;

	// Take a timestamp
	public SubmissionImpl(Student student, Assignment assignment, byte[] contents, Timestamp timestamp) {
		this.student = student;
		this.assignment = assignment;
		this.contents = contents;
		this.timestamp = timestamp;
	}
	
	// Auto-generate a timestamp
	public SubmissionImpl(Student student, Assignment assignment, byte[] contents) {
		this(student, assignment, contents, new Timestamp((new Date()).getTime()));
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
	public byte[] getContents() {
		return contents;
	}

	@Override
	public int hashCode() {
		return student.hashCode() + assignment.hashCode() + contents.hashCode() + timestamp.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SubmissionImpl other = (SubmissionImpl) obj;
		if (student.equals(other.student) &&
			assignment.equals(other.assignment) &&
			timestamp.equals(other.timestamp) &&
			contents.equals(other.contents))
			return true;
		return false;
	}
	
	@Override
	public String toString() {
		return student + ", " + assignment + ", " + timestamp + ", " + contents;
	}

}
