package edu.harvard.cs262.grading.server.services;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class SubmissionReceiverServiceServer implements
		SubmissionReceiverService {

	private ConfigReader config;

	public SubmissionReceiverServiceServer() {
		config = new ConfigReaderImpl();
	}

	@Override
	public void init() throws RemoteException {
	}
	
	/**
	 * Attempts to find the SubmissionStorageService, and dies after
	 * NUM_LOOKUP_RETRIES.
	 */
	
	@Override
	public Submission submit(Student student, Assignment assignment,
			byte[] contents) throws RemoteException {

		Submission submission = new SubmissionImpl(student, assignment,
				contents);
		SubmissionStorageService storage = (SubmissionStorageService) ServiceLookupUtility
				.lookupService(config, "SubmissionStorageService");
		if (storage == null) {
			System.err.println("Looking up SubmissionStorageService failed.");
			return null;
		} else {
			storage.storeSubmission(submission);
			return submission;
		}
	}

	public static void main(String[] args) {

		try {
			SubmissionReceiverServiceServer obj = new SubmissionReceiverServiceServer();
			obj.init();
			SubmissionReceiverService stub = (SubmissionReceiverService) UnicastRemoteObject
					.exportObject(obj, 0);

			// Bind the remote object's stub in the registry
			Registry registry = LocateRegistry.getRegistry();

			// check for registry update command
			boolean forceUpdate = false;
			for (int i = 0, len = args.length; i < len; i++)
				if (args[i].equals("--update"))
					forceUpdate = true;

			if (forceUpdate)
				registry.rebind("SubmissionReceiverService", stub);
			else
				registry.bind("SubmissionReceiverService", stub);

			System.err.println("SubmissionReceiverService running");

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
