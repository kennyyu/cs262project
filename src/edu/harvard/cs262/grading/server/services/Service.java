package edu.harvard.cs262.grading.server.services;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Umbrella interface for services
 */
public interface Service extends Remote {

	/**
	 * Sets up the service (i.e., initializes private fields). Should be called
	 * before any other methods.
	 * 
	 * @throws Exception
	 */
	public void init() throws Exception;
	
	/**
	 * Tests if the connection to the remote object is good.
	 * @throws Exception
	 */
	public void heartbeat() throws RemoteException;

}
