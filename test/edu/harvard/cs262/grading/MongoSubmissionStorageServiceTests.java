package edu.harvard.cs262.grading;

import java.net.UnknownHostException;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mongodb.MongoException;

import static org.junit.Assert.*;

public class MongoSubmissionStorageServiceTests {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMongoInit() throws UnknownHostException, MongoException, SerialException, SQLException {
		MongoSubmissionStorageService service = new MongoSubmissionStorageService();
		service.init();
		
		Student student = new StudentImpl();
		Assignment assn = new AssignmentImpl(0);
		
		byte[] b = new byte[42];
		Submission sub = new SubmissionImpl(student, assn, b);
		
		service.storeSubmission(sub);
		
		assertTrue(true);
	}
}
