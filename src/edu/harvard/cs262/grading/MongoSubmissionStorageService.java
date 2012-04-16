package edu.harvard.cs262.grading;

import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.sql.Blob;
import java.util.LinkedHashSet;
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
		coll = db.getCollection("testCollection");
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

		info.put("studentID", submission.getStudent());
		info.put("assignmentID", submission.getAssignment());
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
		
		Submission submission = new SubmissionImpl(student, assignment, (Blob) info.get("contents"));
		
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
					(Blob) result.get("contents"));
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
					(Blob) result.get("contents"));
			submissions.add(submission);
		}
		
		return submissions;
	}

	public static void main(String[] args) {
		// TODO : add registry stuff
	}

}
