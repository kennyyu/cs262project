package edu.harvard.cs262.grading.server.services;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.ServerAddress;

public class StudentServiceServer implements StudentService {

	private Mongo m;
	private DB db;
	private DBCollection coll;

	public StudentServiceServer() {
		m = null;
		db = null;
		coll = null;
	}

	@Override
	public void addNewStudent(long ID, String email, String firstName,
			String lastName) throws RemoteException {

		BasicDBObject doc = new BasicDBObject();

		doc.put("id", ID);
		doc.put("email", email);
		doc.put("firstName", firstName);
		doc.put("lastName", lastName);

		coll.insert(doc);
	}

	@Override
	public Set<Student> getStudents() throws RemoteException {
		BasicDBObject query = new BasicDBObject();

		DBCursor results = coll.find(query);

		Set<Student> students = new LinkedHashSet<Student>();

		for (DBObject result : results) {
			students.add(new StudentImpl((Long) result.get("id"),
					(String) result.get("email"), (String) result
							.get("firstName"), (String) result.get("lastName")));
		}

		return students;
	}

	@Override
	public Student getStudent(long ID) throws RemoteException {
		BasicDBObject query = new BasicDBObject();

		query.put("id", ID);

		DBObject result = coll.findOne(query);

		return new StudentImpl(ID, (String) result.get("email"),
				(String) result.get("firstName"),
				(String) result.get("lastName"));
	}

	@Override
	public void init() throws Exception {
		ConfigReader config = new ConfigReaderImpl();
		List<String> servers = config.getRegistryLocations("StudentServiceDB");
		List<ServerAddress> addrs = new ArrayList<ServerAddress>();
		for (String server : servers)
		{
			int split = server.indexOf(":");
			String host = server.substring(0, split);
			int port = Integer.parseInt(server.substring(split+1));
			addrs.add(new ServerAddress(host, port));
		}
		m = new Mongo(addrs);
		db = m.getDB("dgs");
		coll = db.getCollection("students");
	}

	public static void main(String[] args) {

		try {
			StudentService obj = new StudentServiceServer();
			obj.init();
			StudentService stub = (StudentService) UnicastRemoteObject
					.exportObject(obj, 0);

			// Bind the remote object's stub in the registry
			Registry registry = LocateRegistry.getRegistry();

			// check for registry update command
			boolean forceUpdate = false;
			for (int i = 0, len = args.length; i < len; i++)
				if (args[i].equals("--update"))
					forceUpdate = true;

			if (forceUpdate) {
				registry.rebind("StudentService", stub);
			} else {
				registry.bind("StudentService", stub);
			}

			System.err.println("StudentService running");

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
