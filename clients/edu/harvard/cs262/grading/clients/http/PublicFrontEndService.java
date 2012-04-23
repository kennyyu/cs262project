package edu.harvard.cs262.grading.clients.http;

import java.rmi.Remote;

/**
 * Front end service for seeing submissions and grades.
 */
public interface PublicFrontEndService extends Remote {

	/**
	 * Starts the service.
	 * 
	 * @throws Exception
	 */
	public void init() throws Exception;
}
