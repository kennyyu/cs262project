package edu.harvard.cs262.grading.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.harvard.cs262.grading.server.services.Assignment;
import edu.harvard.cs262.grading.server.services.AssignmentImpl;
import edu.harvard.cs262.grading.server.services.MongoSubmissionStorageService;
import edu.harvard.cs262.grading.server.services.Student;
import edu.harvard.cs262.grading.server.services.StudentImpl;
import edu.harvard.cs262.grading.server.services.Submission;
import edu.harvard.cs262.grading.server.services.SubmissionReceiverService;
import edu.harvard.cs262.grading.server.services.SubmissionReceiverServiceServer;
import edu.harvard.cs262.grading.server.services.SubmissionStorageService;
import static org.junit.Assert.*;

public class SubmissionReceiverServiceTests {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testSubmit() throws Exception {
		SubmissionStorageService storage = new MongoSubmissionStorageService();
		storage.init();
		
		// generate a test submission
		Student student = new StudentImpl(5L, "waldo@eecs.harvard.edu", "Jim", "Waldo");
		Assignment assignment = new AssignmentImpl(1L, "first assignment");
		byte[] contents = (new String("my homework")).getBytes();
		
		// generate a sandboxed SubmissionReceiverService
		SubmissionReceiverService service = new SubmissionReceiverServiceServer(storage);
		
		// submit the submission to the receiver service
		Submission submission = service.submit(student, assignment, contents);
		
		// assert that the SubmissionStorageService received the storage
		assertEquals(submission, storage.getLatestSubmission(student, assignment));
	}

}
