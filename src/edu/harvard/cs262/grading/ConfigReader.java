package edu.harvard.cs262.grading;

import java.util.List;

public interface ConfigReader {
	
	/**
	 * @param service
	 * @return the list of registries containing this service
	 */
	public List<String> getService(String service);
	
}
