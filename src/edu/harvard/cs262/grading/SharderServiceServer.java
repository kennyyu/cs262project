package edu.harvard.cs262.grading;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class SharderServiceServer implements SharderService {

	SubmissionStorageService storage;
	
	static int GRADERS_PER_SUBMISSION = 2;
	
	Map<Integer, Shard> shards;
	
	
	public Shard assign(Set<Student> students, Set<Submission> submissions) throws RemoteException {
		ShardImpl shard = new ShardImpl();

		Random rand = new Random();
		Map<Student, Integer> canStillGrade = new LinkedHashMap<Student, Integer>();
		for (Student s : students) {
			canStillGrade.put(s, GRADERS_PER_SUBMISSION);
		}
		
		for (Submission s : submissions) {
			for (int i = 0; i < GRADERS_PER_SUBMISSION; i++) {
				Student grader = s.getStudent();
				if (canStillGrade.isEmpty()) throw new RemoteException("Can't find enough graders");
				if ((canStillGrade.size() == 1) && canStillGrade.containsKey(s.getStudent())) {
					return assign(students, submissions);
				}
				while (grader.equals(s.getStudent()))
					grader = (Student) canStillGrade.keySet().toArray()[rand.nextInt(canStillGrade.size())];
				shard.addGrader(grader, s.getStudent());
				if (canStillGrade.get(grader) == 1) {
					canStillGrade.remove(grader);
				} else {
					canStillGrade.put(grader, canStillGrade.get(grader) - 1);
				}				
			}
		}
		return shard;
	}
	@Override
	public Shard generateShard(Assignment assignment) throws RemoteException {
		Set<Submission> submissions = storage.getAllSubmissions(assignment);
		
		//Compile list of students
		Set<Student> students = new LinkedHashSet<Student>();
		for (Submission s : submissions) {
			students.add(s.getStudent());
		}
		

		
		//Randomly assign graders. We could be smarter.
		Shard shard = assign(students, submissions);
		
		shards.put(shard.shardID(), shard);
		return shard;
		
	}

	@Override
	public Shard getShard(int shardID) throws RemoteException {
		return shards.get(shardID);
	}

	@Override
	public void init() throws Exception {
		//TODO: Find a submission storage server
		
		shards = new LinkedHashMap<Integer, Shard>();
	}
	
	public static void main(String[] args) {
		try {
			SharderServiceServer obj = new SharderServiceServer();
			SubmissionStorageService stub = (SubmissionStorageService) UnicastRemoteObject
					.exportObject(obj, 0);

			// Bind the remote object's stub in the registry
			Registry registry = LocateRegistry.getRegistry();
			registry.bind("SharderService", stub);
		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}

}
