package edu.harvard.cs262.grading;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class ServiceLookupUtility {

	public static Service lookupService(ConfigReader reader, String serviceName) throws RemoteException, NullPointerException {

		Service service = null;

		List<String> locations = reader.getRegistryLocations(serviceName);

		for (int j = 0, total = locations.size(); j < total; j++) {

			String location = locations.get(j);

			try {
				Registry registry = LocateRegistry.getRegistry(location);
				service = (Service) registry.lookup("SubmissionStorageService");
				break;

			} catch (AccessException e) {

				System.err.println("Access denied to local registry.");
				e.printStackTrace();

			} catch (RemoteException e) {

				System.err.println("Could not contact registry at "+ location +".");
				e.printStackTrace();

				if (j + 1 == total) throw e;

			} catch (NotBoundException e) {

				System.err.println("Service "+ serviceName +" not found in registry at "+location +".");
				e.printStackTrace();

			}

		}

		return service;

	}

}
