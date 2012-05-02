package edu.harvard.cs262.grading.server.services;

public class StudentImpl implements Student {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3416718070943451888L;
	private long studentID;
	private String email;
	private String firstName;
	private String lastName;

	public StudentImpl() {
		this.studentID = 0;
		this.firstName = "";
		this.lastName = "";
		this.email = "";
	}
	
	public StudentImpl(long studentID) {
		this.studentID = studentID;
		this.firstName = "";
		this.lastName = "";
		this.email = "";
	}

	public StudentImpl(long studentID, String email, String firstName, String lastName) {
		this.studentID = studentID;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public long studentID() {
		return this.studentID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (studentID ^ (studentID >>> 32));
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

	@Override
	public String toString() {
		return "Student: " + studentID;
	}

	@Override
	public String email() {
		return this.email;
	}

	@Override
	public String firstName() {
		return this.firstName;
	}

	@Override
	public String lastName() {
		return this.lastName;
	}
}
