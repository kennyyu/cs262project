package edu.harvard.cs262.grading.server.services;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Service for receiving student submissions.
 */
public interface SubmissionReceiverService extends Remote, Service {

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
	Submission submit(Student student, Assignment assignment, byte[] contents)
			throws RemoteException;
}
