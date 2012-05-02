package edu.harvard.cs262.grading.server.services;

import java.io.Serializable;

/**
 * Represents a Student.
 */
public interface Student extends Serializable {

	/**
	 * @return the unique ID identifying this student
	 */
	public long studentID();

	/**
	 * @return the student's e-mail address
	 */
	public String email();

	/**
	 * @return the student's first name
	 */
	public String firstName();

	/**
	 * @return the student's last name
	 */
	public String lastName();

}
