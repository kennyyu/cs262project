package edu.harvard.cs262.grading.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.harvard.cs262.grading.server.services.ConfigReaderImpl;

public class TestConfigReader {

	ConfigReaderImpl cr;
	
	@Before
	public void setUp() throws Exception {
		cr = new ConfigReaderImpl("config/test.config");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConfigReader() {
		assertEquals("No Service Z", cr.getRegistryLocations("Z").isEmpty(), true);
		assertEquals("Service A", cr.getRegistryLocations("A").size(), 2);
		assertEquals("Service A index 0", cr.getRegistryLocations("A").get(0), "192.168.56.52:1000");
		assertEquals("Service A index 1", cr.getRegistryLocations("A").get(1), "192.168.56.52:0900");
		assertEquals("Service B", cr.getRegistryLocations("B").size(), 2);
		assertEquals("Service B index 0", cr.getRegistryLocations("B").get(0), "192.168.56.52:0800");
		assertEquals("Service B index 1", cr.getRegistryLocations("B").get(1), "192.168.56.52:0700");
	}

}
