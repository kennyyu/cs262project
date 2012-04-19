package edu.harvard.cs262.grading;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Service for sharding work out to students.
 */
public interface SharderService extends Remote {

	/**
	 * Starts the service.
	 * 
	 * @throws Exception
	 */
	public void init(boolean sandbox) throws Exception;

	/**
	 * Generate a new sharding for the given assignment
	 * 
	 * @return the sharding
	 * @throws RemoteException
	 */
	public Shard generateShard(Assignment assignment) throws RemoteException;

	/**
	 * Retrieve a specific sharding
	 * 
	 * @param shardID
	 * @return the sharding
	 * @throws RemoteException
	 */
	public Shard getShard(int shardID) throws RemoteException;
	
	public int getShardID(Assignment assignment) throws RemoteException;
}
