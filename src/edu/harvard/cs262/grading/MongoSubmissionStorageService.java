package edu.harvard.cs262.grading;

import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.LinkedHashSet;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Set;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;

public class MongoSubmissionStorageService implements SubmissionStorageService {

	private Mongo m;
	private DB db;
	private DBCollection coll;

	public void init() throws UnknownHostException, MongoException {
		m = new Mongo();
		db = m.getDB("mydb");
		coll = db.getCollection("submissions");
	}

	@Override
	public void storeSubmission(Submission submission) {
		// XXX: Active question: can Mongo's "put" method really accept /any/
		// objects?
		// How does that work? Surely they must be serializable?

		BasicDBObject doc = new BasicDBObject();

		doc.put("name", "MongoDB");
		doc.put("type", "database");
		doc.put("count", 1);

		BasicDBObject info = new BasicDBObject();

		info.put("studentID", submission.getStudent().studentID());
		info.put("assignmentID", submission.getAssignment().assignmentID());
		info.put("timestamp", submission.getTimeStamp());
		info.put("contents", submission.getContents());

		doc.put("info", info);

		coll.insert(doc);
	}

	@Override
	public Submission getSubmission(Student student, Assignment assignment)
			throws RemoteException {

		BasicDBObject query = new BasicDBObject();
		query.put("studentID", student);
		query.put("assignmentID", assignment);
		
		DBObject info = coll.findOne(query);
		
		Submission submission = new SubmissionImpl(student, assignment, (byte[]) info.get("contents"));
		
		return submission;
	}

	@Override
	public Set<Submission> getStudentWork(Student student)
			throws RemoteException {

		BasicDBObject query = new BasicDBObject();
		query.put("studentID", student);
		
		DBCursor results = coll.find(query);
		
		Set<Submission> submissions = new LinkedHashSet<Submission>();
		
		for (DBObject result : results) {
			Submission submission = new SubmissionImpl(student, 
					(Assignment) result.get("assignmentID"), 
					(byte[]) result.get("contents"));
			submissions.add(submission);
		}
		
		return submissions;
	}

	@Override
	public Set<Submission> getAllSubmissions(Assignment assignment)
			throws RemoteException {

		BasicDBObject query = new BasicDBObject();
		query.put("assignmentID", assignment);
		
		DBCursor results = coll.find(query);
		
		Set<Submission> submissions = new LinkedHashSet<Submission>();
		
		for (DBObject result : results) {
			Submission submission = new SubmissionImpl(
					(Student) result.get("studentID"), 
					assignment, 
					(byte[]) result.get("contents"));
			submissions.add(submission);
		}
		
		return submissions;
	}

	// TODO (kennyu): Is this right?  How would we know?
	public static void main(String[] args) {
		try {
			MongoSubmissionStorageService obj = new MongoSubmissionStorageService();
			obj.init();
			SubmissionStorageService stub = (SubmissionStorageService) UnicastRemoteObject
					.exportObject(obj, 0);

			// Bind the remote object's stub in the registry
			Registry registry = LocateRegistry.getRegistry();
			registry.bind("SubmissionStorageService", stub);
			
			System.err.println("MongoSubmissionStorageService running");
			
		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}

}
