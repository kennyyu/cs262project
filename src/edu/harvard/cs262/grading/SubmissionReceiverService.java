package edu.harvard.cs262.grading;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Blob;

/**
 * Service for receiving student submissions.
 */
public interface SubmissionReceiverService extends Remote {

	/**
	 * Start the service.
	 * 
	 * @throws Exception
	 */
	public void init() throws Exception;

	/**
	 * Submits the contents for the specified student and assignment number.
	 * 
	 * @param student
	 * @param assignment
	 * @param contents
	 *            a Blob object containing the contents of the submission
	 * @return A submission object on success, containing the timestamp.
	 * @throws RemoteException
	 */
	Submission submit(Student student, Assignment assignment, Blob contents)
			throws RemoteException;
}
