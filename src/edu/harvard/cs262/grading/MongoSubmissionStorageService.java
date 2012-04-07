package edu.harvard.cs262.grading;

import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.Set;

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

		info.put("studentID", submission.getStudentID());
		info.put("assignmentID", submission.getAssignmentID());
		info.put("timestamp", submission.getTimeStamp());
		info.put("contents", submission.getContents());

		doc.put("info", info);

		coll.insert(doc);
	}

	@Override
	public Submission getSubmission(StudentID studentID,
			AssignmentID assignmentID) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Submission> getStudentWork(StudentID studentID)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Submission> getAllSubmissions(AssignmentID assignmentID)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
