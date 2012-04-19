package edu.harvard.cs262.grading;

public class StudentImpl implements Student {
	
	private long studentID;
	
	public StudentImpl() {
		this.studentID = 0;
	}
	
	public StudentImpl(long studentID) {
		this.studentID = studentID;
	}

	public long studentID() {
		return this.studentID;
	}

}
