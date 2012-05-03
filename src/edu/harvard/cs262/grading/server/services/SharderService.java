package edu.harvard.cs262.grading.server.services;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.Set;

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
	 * @param assignment
	 * @return the sharding
	 * @throws RemoteException
	 */
	public Shard generateShard(Assignment assignment) throws RemoteException;

	/**
	 * Store a given sharding for the given assignment
	 * 
	 * @param assignment
	 * @param gradermap
	 *            a map from graders to sets of gradees
	 * @return the ID of the shard
	 * @throws RemoteException
	 */
	public long putShard(Assignment assignment, Map<Long, Set<Long>> gradermap)
			throws RemoteException;

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
	public int getShardID(Assignment assignment) throws RemoteException,
			NoShardsForAssignmentException;
}
