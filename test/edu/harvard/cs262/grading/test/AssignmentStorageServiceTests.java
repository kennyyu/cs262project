package edu.harvard.cs262.grading.test;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.harvard.cs262.grading.server.services.Assignment;
import edu.harvard.cs262.grading.server.services.AssignmentImpl;
import edu.harvard.cs262.grading.server.services.AssignmentStorageService;
import edu.harvard.cs262.grading.server.services.AssignmentStorageServiceServer;
import edu.harvard.cs262.grading.server.services.Student;
import edu.harvard.cs262.grading.server.services.StudentService;
import edu.harvard.cs262.grading.server.services.StudentServiceServer;

public class AssignmentStorageServiceTests {

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testAssignment() throws Exception {
		AssignmentStorageService service = new AssignmentStorageServiceServer();
		service.init();
		
		service.addNewAssignment(0, "Description1");
		service.addNewAssignment(1, "Description2");
		
		Assignment assn0 = new AssignmentImpl(0, "Description1");
		Assignment assn1 = new AssignmentImpl(1, "Description2");
		
		Set<Assignment> assignments = service.getAssignments();
		
		assertTrue(assignments.size() == 2);
		assertTrue(assignments.contains(assn0));
		assertTrue(assignments.contains(assn1));
	}

	@After
	public void tearDown() throws Exception {
	}

}
