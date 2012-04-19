package edu.harvard.cs262.grading;

import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class MongoGradeStorageService implements GradeStorageService {

	private Mongo m;
	private DB db;
	private DBCollection coll;
	
	public void init() throws UnknownHostException, MongoException {
		m = new Mongo();
		db = m.getDB("mydb");
		coll = db.getCollection("testCollection"); //change collection name?
	}
	
	@Override
	public void submitGrade(Student grader, Submission submission, Score score)
			throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Grade> getGrade(Submission submission) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	

	// TODO (kennyu): Is this right?  How would we know?
	public static void main(String[] args) {
		try {
			MongoGradeStorageService obj = new MongoGradeStorageService();
			GradeStorageService stub = (GradeStorageService) UnicastRemoteObject
					.exportObject(obj, 0);

			// Bind the remote object's stub in the registry
			Registry registry = LocateRegistry.getRegistry();
			registry.bind("GradeStorageService", stub);
			
			System.err.println("MongoGradeStorageService running");
			
		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}


}
