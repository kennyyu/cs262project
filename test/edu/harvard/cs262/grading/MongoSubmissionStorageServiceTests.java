package edu.harvard.cs262.grading;

import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Random;

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
	public void testMongoInit() throws UnknownHostException, MongoException, SerialException, SQLException, RemoteException {
		MongoSubmissionStorageService service = new MongoSubmissionStorageService();
		service.init();
		
		Random r = new Random();
		
		Student student = new StudentImpl(r.nextInt(100));
		Assignment assn = new AssignmentImpl(r.nextInt(100));
		
		String contents = "Hello World, " + r.nextInt(100) + " times.";
		
		byte[] b = contents.getBytes();
		Submission sub = new SubmissionImpl(student, assn, b);
		
		service.storeSubmission(sub);
		
		assert(service.getSubmission(student, assn).getContents().equals(contents.getBytes()));
	}
}
