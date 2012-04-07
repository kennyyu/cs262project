package edu.harvard.cs262.grading;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Service for persistent storage of student generated grades.
 */
public interface GradeStorageService extends Remote {

	/**
	 * Starts the service.
	 * 
	 * @throws Exception
	 */
	public void init() throws Exception;

	/**
	 * Submit a score for grading
	 * 
	 * @param grader
	 *            the person grading this assignment
	 * @param submission
	 *            the submission being graded
	 * @param score
	 *            the score for the submission
	 * @param return the Grade object
	 * @throws RemoteException
	 */
	public void submitGrade(Student grader, Submission submission, Score score)
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
