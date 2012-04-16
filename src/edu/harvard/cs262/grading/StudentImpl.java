package edu.harvard.cs262.grading;

public class StudentImpl implements Student {

	private int studentID;
	
	public StudentImpl() {
		this.studentID = 0;
	}
	
	public StudentImpl(int studentID) {
		this.studentID = studentID;
	}

	public int studentID() {
		return this.studentID;
	}

}
