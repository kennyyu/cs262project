package edu.harvard.cs262.grading.server.services;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Service for persistent storage of student generated grades.
 */
public interface GradeStorageService extends Remote, Service {

	/**
	 * Submit a score for grading
	 * 
	 * @param grade
	 * 			the grade for this assignment
	 * @param submission
	 *            the submission being graded
	 * @param return the Grade object
	 * @throws RemoteException
	 */
	public void submitGrade(Submission submission, Grade grade)
			throws RemoteException;

	/**
	 * Retrieve all the grades for a given submission.
	 * 
	 * @param submission
	 * @return
	 * @throws RemoteException
	 */
	public List<Grade> getGrade(Submission submission) throws RemoteException;
}
