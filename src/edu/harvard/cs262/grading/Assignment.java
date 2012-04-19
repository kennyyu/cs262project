package edu.harvard.cs262.grading;

import java.io.Serializable;

/**
 * Represents an assignment.
 */
public interface Assignment {

	/**
	 * @return the unique ID identifying this assignment
	 */
	public long assignmentID();
}