package edu.harvard.cs262.grading.server.services;

/**
 * Umbrella interface for services
 * 
 * @author lpearson05
 * 
 */
public interface Service {

	/**
	 * Sets up the service (i.e., initializes private fields). Should be called
	 * before any other methods.
	 * 
	 * @throws Exception
	 */
	public void init() throws Exception;

}
