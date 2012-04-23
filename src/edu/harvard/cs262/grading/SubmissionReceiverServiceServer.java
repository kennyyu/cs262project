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
	//private final static int NUM_LOOKUP_RETRIES = 1;
	//private final static int TIME_TO_SLEEP = 0;
	//private SubmissionStorageService server;

	public SubmissionReceiverServiceServer() {
		config = new ConfigReaderImpl();
		//server = null;
	}

	/**
	 * Start the service.
	 */
	public void init() throws Exception {
		//lookupService();
	}

	/**
	 * Attempts to find the SubmissionStorageService, and dies after
	 * NUM_LOOKUP_RETRIES.
	 */
	/*
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
	} */

	@Override
	public Submission submit(Student student, Assignment assignment,
			byte[] contents) throws RemoteException {
		
		Submission submission = new SubmissionImpl(student, assignment, contents);
		SubmissionStorageService storage = (SubmissionStorageService) ServiceLookupUtility.lookupService(config, "SubmissionStorageService");
		if(storage == null) {
			System.err.println("Looking up SubmissionStorageService failed.");
			System.exit(-1);
			return null;
		} else {
			storage.storeSubmission(submission);
			return submission;
		}
		
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
			obj.init();
			SubmissionReceiverService stub = (SubmissionReceiverService) UnicastRemoteObject
					.exportObject(obj, 0);

			// Bind the remote object's stub in the registry
			Registry registry = LocateRegistry.getRegistry();
			
			// check for registry update command
			boolean forceUpdate = false;
			for(int i = 0, len = args.length; i < len; i++)
				if(args[i].equals("--update")) forceUpdate = true;

			if(forceUpdate) {
				registry.rebind("SubmissionReceiverService", stub);
			} else {
				registry.bind("SubmissionReceiverService", stub);
			}
			
			System.err.println("SubmissionReceiverService running");
			
		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
		
		return;	// done
		
	}

}
