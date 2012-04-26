package edu.harvard.cs262.grading.server.services;

import java.util.List;

public interface ConfigReader {

	/**
	 * @param service
	 * @return the list of registries containing this service
	 */
	public List<String> getRegistryLocations(String service);

}
