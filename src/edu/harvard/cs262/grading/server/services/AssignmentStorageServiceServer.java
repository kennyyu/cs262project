package edu.harvard.cs262.grading.server.services;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedHashSet;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class AssignmentStorageServiceServer implements AssignmentStorageService {

	private Mongo m;
	private DB db;
	private DBCollection coll;

	@Override
	public void addNewAssignment(long ID, String desc) throws RemoteException {

		BasicDBObject doc = new BasicDBObject();

		doc.put("id", ID);
		doc.put("desc", desc);

		coll.insert(doc);
	}

	@Override
	public Set<Assignment> getAssignments() throws RemoteException {
		BasicDBObject query = new BasicDBObject();

		DBCursor results = coll.find(query);

		Set<Assignment> assignments = new LinkedHashSet<Assignment>();

		for (DBObject result : results) {
			assignments.add(new AssignmentImpl((Long) result.get("id"),
					(String) result.get("desc")));
		}

		return assignments;
	}

	@Override
	public void init() throws Exception {
		m = new Mongo();
		db = m.getDB("dgs");
		coll = db.getCollection("assignments");
	}

	public static void main(String[] args) {

		try {
			AssignmentStorageService obj = new AssignmentStorageServiceServer();
			obj.init();
			AssignmentStorageService stub = (AssignmentStorageService) UnicastRemoteObject
					.exportObject(obj, 0);

			// Bind the remote object's stub in the registry
			Registry registry = LocateRegistry.getRegistry();

			// check for registry update command
			boolean forceUpdate = false;
			for (int i = 0, len = args.length; i < len; i++)
				if (args[i].equals("--update"))
					forceUpdate = true;

			if (forceUpdate) {
				registry.rebind("AssignmentStorageService", stub);
			} else {
				registry.bind("AssignmentStorageService", stub);
			}

			System.err.println("AssignmentService running");

		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}

	}

	@Override
	public void heartbeat() throws RemoteException {
		// TODO Auto-generated method stub

	}

}
