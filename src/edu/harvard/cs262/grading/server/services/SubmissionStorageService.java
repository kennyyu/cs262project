package edu.harvard.cs262.grading.server.services;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Set;

/**
 * Service for persistent storage of student work submission.
 */
public interface SubmissionStorageService extends Service, Remote {

	/**
	 * Places the submission into persistent storage.
	 * 
	 * @param submission
	 * @throws RemoteException
	 */
	public void storeSubmission(Submission submission) throws RemoteException;

	/**
	 * Get the submission for the given student and assignment
	 * 
	 * @param student
	 * @param assignment
	 * @return the requested submission
	 * @throws RemoteException
	 */
	public Submission getSubmission(Student student, Assignment assignment,
			Timestamp timestamp) throws RemoteException;

	/**
	 * Get the latest submission for the student and assignment
	 * @param student
	 * @param assignment
	 * @return the submission
	 * @throws RemoteException
	 */
	public Submission getLatestSubmission(Student student, Assignment assignment)
			throws RemoteException;

	/**
	 * Return all submissions for a given assignment by a student
	 * @param student
	 * @param assignment
	 * @return the set of submissions
	 * @throws RemoteException
	 */
	public Set<Submission> getSubmissions(Student student, Assignment assignment)
			throws RemoteException;

	/**
	 * Return a set of all submitted assignments for this student
	 * 
	 * @param student
	 * @return a set of all submitted assignments for this student
	 * @throws RemoteException
	 */
	public Set<Submission> getStudentWork(Student student)
			throws RemoteException;

	/**
	 * Return a set of all submitted assignments for the given assignment ID
	 * 
	 * @param assignment
	 * @return all submissions for the assignment
	 * @throws RemoteException
	 */
	public Set<Submission> getAllSubmissions(Assignment assignment)
			throws RemoteException;
}
