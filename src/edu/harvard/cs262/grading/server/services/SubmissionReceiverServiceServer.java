package edu.harvard.cs262.grading.server.services;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * A MongoDB implementation of the SubmissionReceiverService interface
 */
public class SubmissionReceiverServiceServer implements
		SubmissionReceiverService {

	private ConfigReader config;
	private SubmissionStorageService storage;
	private boolean sandbox;

	public SubmissionReceiverServiceServer() {
		config = new ConfigReaderImpl();
		sandbox = false;
		storage = null;
	}

	public SubmissionReceiverServiceServer(SubmissionStorageService storage) {
		config = new ConfigReaderImpl();
		sandbox = true;
		this.storage = storage;
	}

	@Override
	public void init() throws Exception {
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

		if (sandbox) {
			storage.storeSubmission(submission);
			return submission;
		} else {
			SubmissionStorageService storage = (SubmissionStorageService) ServiceLookupUtility
					.lookupService(config, "SubmissionStorageService");
			if (storage == null) {
				System.err
						.println("Looking up SubmissionStorageService failed.");
				return null;
			} else {
				storage.storeSubmission(submission);
				return submission;
			}
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
