package edu.harvard.cs262.grading;

import java.io.Serializable;

public class StudentImpl implements Student, Serializable {

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
