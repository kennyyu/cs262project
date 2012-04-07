package edu.harvard.cs262.grading;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

/**
 * Service for persistent storage of student work submission.
 */
public interface SubmissionStorageService extends Remote {

	/**
	 * Start the service.
	 * @throws Exception
	 */
	public void init() throws Exception;

	/**
	 * Places the submission into persistent storage.
	 * 
	 * @param submission
	 * @return true on success
	 * @throws RemoteException
	 */
	public void storeSubmission(Submission submission) throws RemoteException;

	/**
	 * Get the submission for the given student and assignment
	 * 
	 * @param studentID
	 * @param assignmentID
	 * @return the requested submission
	 * @throws RemoteException
	 */
	public Submission getSubmission(StudentID studentID,
			AssignmentID assignmentID) throws RemoteException;

	/**
	 * Return a set of all submitted assignments for this student
	 * 
	 * @param studentID
	 * @return a set of all submitted assignments for this student
	 * @throws RemoteException
	 */
	public Set<Submission> getStudentWork(StudentID studentID)
			throws RemoteException;

	/**
	 * Return a set of all submitted assignments for the given assignment ID
	 * 
	 * @param assignmentID
	 * @return
	 * @throws RemoteException
	 */
	public Set<Submission> getAllSubmissions(AssignmentID assignmentID)
			throws RemoteException;
}
