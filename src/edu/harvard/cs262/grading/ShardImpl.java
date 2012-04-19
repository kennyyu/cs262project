package edu.harvard.cs262.grading;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class ShardImpl implements Shard {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2985077449389912337L;
	private Map<Student, Set<Student>> sharding;
	
	public ShardImpl() {
		sharding = new LinkedHashMap<Student, Set<Student>>();
	}
	
	/*
	public ShardImpl(int shardID) {
		this.shardID = shardID;
		sharding = new LinkedHashMap<Student, Set<Student>>();
	}
	*/
	
	public void addGrader(Student grader, Student gradee) {
		if (sharding.containsKey(grader)) {
			sharding.get(grader).add(gradee);
		} else {
			Set<Student> gradeeSet = new LinkedHashSet<Student>();
			gradeeSet.add(gradee);
			sharding.put(grader, gradeeSet);
		}
	}

	@Override
	public Set<Student> getGraders(Student student) {
		Set<Student> graderSet = new LinkedHashSet<Student>();
		
		for (Student grader : sharding.keySet()) {
			if (sharding.get(grader).contains(student)) {
				graderSet.add(grader);
			}
		}
		return graderSet;
	}

	@Override
	public Map<Student, Set<Student>> getShard() {
		return sharding;
	}

	@Override
	public int shardID() {
		return this.hashCode();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((sharding == null) ? 0 : sharding.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShardImpl other = (ShardImpl) obj;
		if (sharding == null) {
			if (other.sharding != null)
				return false;
		} else if (!sharding.equals(other.sharding))
			return false;
		return true;
	}

}
