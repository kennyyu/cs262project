package edu.harvard.cs262.grading.server.services;

import java.rmi.RemoteException;

/**
 * Service for sharding work out to students.
 */
public interface SharderService extends Service {

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

	/**
	 * Return the shard ID for a given assignment
	 * 
	 * @param assignment
	 * @return the shard ID
	 * @throws RemoteException
	 */
	public int getShardID(Assignment assignment) throws RemoteException;
}
