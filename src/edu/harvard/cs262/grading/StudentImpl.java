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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + studentID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentImpl other = (StudentImpl) obj;
		if (studentID != other.studentID)
			return false;
		return true;
	}
	
}
