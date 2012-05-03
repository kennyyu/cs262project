package edu.harvard.cs262.grading.server.services;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * Represents a sharding of the work, mapping graders to gradees.
 */
public interface Shard extends Serializable {

	/**
	 * @return the unique ID for this shard
	 */
	public int shardID();

	/**
	 * @return a mapping from grader to gradees
	 */
	public Map<Long, Set<Long>> getShard();

	/**
	 * Get the graders for a particular student
	 * 
	 * @param student
	 * @return the set of grader IDs for a particular student
	 */
	public Set<Long> getGraders(Student student);
	
	/**
	 * Add a gradee for this grader
	 * @param grader
	 * @param gradee
	 */
	public void addGrader(Long grader, Long gradee);

}
