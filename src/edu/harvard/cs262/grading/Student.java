package edu.harvard.cs262.grading;

import java.io.Serializable;

/**
 * Represents a Student.
 */
public interface Student extends Serializable {

	/**
	 * @return the unique ID identifying this student
	 */
	public int studentID();

}
