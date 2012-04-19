package edu.harvard.cs262.grading;

import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Blob;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class SharderServiceServer implements SharderService {

	static int GRADERS_PER_SUBMISSION = 2;
	
	private Mongo m;
	private DB db;
	private DBCollection coll;

	public void init() throws UnknownHostException, MongoException {
		m = new Mongo();
		db = m.getDB("mydb");
		coll = db.getCollection("shards");
		

		
	}
	
	private SubmissionStorageService getStorage() throws RemoteException{
		ConfigReader cfg = new ConfigReaderImpl();
		List<String> registryNames = cfg.getService("SubmissionStorageService");
		for (int j = 0; j < registryNames.size(); j++) {
			try {
				Registry registry = LocateRegistry.getRegistry(registryNames.get(j));
				SubmissionStorageService storage = (SubmissionStorageService) registry.lookup("SubmissionStorageService");
				return storage;
			} catch (RemoteException e) {
				if (j + 1 == registryNames.size())
					throw e;
			} catch (NotBoundException e) {
				if (j + 1 == registryNames.size()) {
					System.err.println("Looking up SubmissionStorageService failed");
					System.exit(-1);
				}
			}
		}
		System.err.println("Looking up SubmissionStorageService failed");
		System.exit(-1);
		return null;
	}
	
	private int getNextShardID() {
		BasicDBObject query = new BasicDBObject();
		
		DBCursor results = coll.find(query);

		int nextID = 0;
		for (DBObject result : results) {
			if (((Integer) result.get("id")) > nextID) nextID = (Integer) result.get("id");
		}
		
		return nextID + 1;
	}
	
	private Shard readShard(int ShardID) {
		BasicDBObject query = new BasicDBObject();
		query.put("id", ShardID);
		
		ShardImpl shard = new ShardImpl();
		
		DBCursor results = coll.find(query);

		for (DBObject result : results) {
			shard.addGrader((Student)result.get("grader"), (Student)result.get("gradee"));
		}
		
		return shard;
	}
	
	private void writeShard(Shard shard) {
		Map<Student, Set<Student>> sharding = shard.getShard();

		BasicDBObject doc = new BasicDBObject();

		doc.put("name", "MongoDB");
		doc.put("type", "database");
		doc.put("count", 1);
		
		for (Student grader : sharding.keySet()) {
			for (Student gradee : sharding.get(grader)) {

				BasicDBObject info = new BasicDBObject();
	
				info.put("id", shard.shardID());
				info.put("grader", grader);
				info.put("gradee", gradee);
	
				doc.put("info", info);
	
				coll.insert(doc);
			}	
		}
	}
	
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
		SubmissionStorageService storage = getStorage();
		Set<Submission> submissions = storage.getAllSubmissions(assignment);
		
		//Compile list of students
		Set<Student> students = new LinkedHashSet<Student>();
		for (Submission s : submissions) {
			students.add(s.getStudent());
		}
		

		
		//Randomly assign graders. We could be smarter.
		Shard shard = assign(students, submissions);
		
		writeShard(shard);
		return shard;
		
	}

	@Override
	public Shard getShard(int shardID) throws RemoteException {
		return readShard(shardID);
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
