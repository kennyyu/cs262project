package edu.harvard.cs262.grading.clients.web;

import java.rmi.Remote;

/**
 * Administrative front end service.
 */
public interface AdminFrontEndService extends Remote {

	/**
	 * Starts the service.
	 * 
	 * @throws Exception
	 */
	public void init() throws Exception;

}
