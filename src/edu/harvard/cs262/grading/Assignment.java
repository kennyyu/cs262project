package edu.harvard.cs262.grading;

import java.io.Serializable;

/**
 * Represents an assignment.
 */
public interface Assignment extends Serializable {

	/**
	 * @return the unique ID identifying this assignment
	 */
	public int assignmentID();
}