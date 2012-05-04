package edu.harvard.cs262.grading.server.services;

import java.util.List;

/**
 * Reads in the configuration file of service locations
 */
public interface ConfigReader {

	/**
	 * @param service
	 *            The name of the service to lookup in the config file
	 * @return the list of registries containing this service
	 */
	public List<String> getRegistryLocations(String service);

}
