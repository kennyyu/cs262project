package edu.harvard.cs262.grading.server.services;

import java.io.Serializable;

/**
 * Represents an assignment, e.g. problem set 4.
 */
public interface Assignment extends Serializable {

	/**
	 * @return the unique ID identifying this assignment
	 */
	public long assignmentID();

	/**
	 * @return the description of this assignment
	 */
	public String description();
}