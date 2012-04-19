package edu.harvard.cs262.grading;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class SubmissionReceiverServiceServer implements
		SubmissionReceiverService {

	private ConfigReader config;
	private final static int NUM_LOOKUP_RETRIES = 1;
	private final static int TIME_TO_SLEEP = 0;
	private SubmissionStorageService server;

	public SubmissionReceiverServiceServer() {
		config = new ConfigReaderImpl();
		server = null;
	}

	/**
	 * Start the service.
	 */
	public void init() throws Exception {
		lookupService();
	}

	/**
	 * Attempts to find the SubmissionStorageService, and dies after
	 * NUM_LOOKUP_RETRIES.
	 */
	private void lookupService() throws RemoteException {
		List<String> registryNames = config.getService("SubmissionStorageService");
		if (registryNames.size() == 0) {
			System.err.println("No bindings for SubmissionStorageService");
			System.exit(-1);
		}
		
		for (int j = 0; j < registryNames.size(); j++) {
			for (int i = 0; i < NUM_LOOKUP_RETRIES; i++) {
				try {
					Registry registry = LocateRegistry.getRegistry(registryNames.get(j));
					SubmissionStorageService s = (SubmissionStorageService) registry
							.lookup("SubmissionStorageService");
					server = s; // update our new server to reflect the new registry
					return;
				} catch (RemoteException e) {
					if (i + 1 == NUM_LOOKUP_RETRIES && j == registryNames.size())
						throw e;
				} catch (NotBoundException e) {
					if (i + 1 == NUM_LOOKUP_RETRIES && j == registryNames.size()) {
						System.err
								.println("Looking up SubmissionStorageService failed");
						System.exit(-1);
					}
				}
	
				try {
					Thread.sleep(TIME_TO_SLEEP);
				} catch (InterruptedException e) {
					// empty;
				}
			}
		}
	}

	@Override
	public Submission submit(Student student, Assignment assignment,
			byte[] contents) throws RemoteException {
		Submission submission = new SubmissionImpl(student, assignment, contents);
		List<String> submissionStorageServiceNames = config.getService("SubmissionStorageService");
		for (int j = 0; j < submissionStorageServiceNames.size(); j++) {
			try {
				Registry registry = LocateRegistry.getRegistry(submissionStorageServiceNames.get(j));
				SubmissionStorageService storage = (SubmissionStorageService) registry.lookup("SubmissionStorageService");
				storage.storeSubmission(submission);
				return submission;
			} catch (RemoteException e) {
				if (j + 1 == submissionStorageServiceNames.size())
					throw e;
			} catch (NotBoundException e) {
				if (j + 1 == submissionStorageServiceNames.size()) {
					System.err.println("Looking up SubmissionStorageService failed");
					System.exit(-1);
				}
			}
		}
		return submission;
		
		/*
		try {
			server.storeSubmission(submission);
			return submission;
		} catch (RemoteException e) {
			lookupService(); // retry looking up the service
			server.storeSubmission(submission);
			return submission;
		} */
	}

	public static void main(String[] args) {
		try {
			SubmissionReceiverServiceServer obj = new SubmissionReceiverServiceServer();
			SubmissionReceiverService stub = (SubmissionReceiverService) UnicastRemoteObject
					.exportObject(obj, 0);

			// Bind the remote object's stub in the registry
			Registry registry = LocateRegistry.getRegistry();
			//obj.init();
			//System.out.println("finished init!");
			registry.bind("SubmissionReceiverService", stub);
			
			System.out.println("SubmissionReceiverService running");
			
		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}

}
