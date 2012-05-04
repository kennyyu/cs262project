package edu.harvard.cs262.grading.test;

import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Random;
import java.util.Set;

import javax.sql.rowset.serial.SerialException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mongodb.MongoException;

import edu.harvard.cs262.grading.server.services.Assignment;
import edu.harvard.cs262.grading.server.services.AssignmentImpl;
import edu.harvard.cs262.grading.server.services.MongoSubmissionStorageService;
import edu.harvard.cs262.grading.server.services.Student;
import edu.harvard.cs262.grading.server.services.StudentImpl;
import edu.harvard.cs262.grading.server.services.Submission;
import edu.harvard.cs262.grading.server.services.SubmissionImpl;

import static org.junit.Assert.*;

@SuppressWarnings("unused")
public class TestSubmissionStorageService {
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

	@SuppressWarnings("deprecation")
	@Test
	public void testMongoInit() throws UnknownHostException, MongoException,
			SerialException, SQLException, RemoteException {
		MongoSubmissionStorageService service = new MongoSubmissionStorageService();
		service.init();
		service.heartbeat();

		Random r = new Random();

		Student student = new StudentImpl(50);
		Assignment assn = new AssignmentImpl(50);

		String contents = "Hello World, " + r.nextInt(100) + " times.";

		byte[] b = contents.getBytes();
		Submission sub = new SubmissionImpl(student, assn, b);

		service.storeSubmission(sub);

		assertTrue(Arrays.equals(service.getLatestSubmission(student, assn).getContents(),
									contents.getBytes()));
		
		assertTrue(Arrays.equals(service.getSubmission(student, assn, sub.getTimeStamp()).getContents(),
									contents.getBytes()));
		
		// XXX: I don't know why getTimeStamp is deprecated.
		sub.getTimeStamp().setHours((sub.getTimeStamp().getHours()+1)%24);
		assertTrue(service.getSubmission(student, assn, sub.getTimeStamp()) == null);
		
		Set<Submission> submissions = service.getSubmissions(student, assn);
		Set<Submission> studentWork = service.getStudentWork(student);
		assertTrue(submissions.size() == 1);
		assertTrue(studentWork.size() == 1);
		
		// Another submission for the same (student, assn)
		Submission sub2 = new SubmissionImpl(student, assn, b);
		service.storeSubmission(sub2);
		submissions = service.getSubmissions(student, assn);
		studentWork = service.getStudentWork(student);
		assertTrue(submissions.size() == 2);
		assertTrue(studentWork.size() == 2);
		
		
		// Different student, same assignment
		Submission sub3 = new SubmissionImpl(new StudentImpl(60), assn, b);
		service.storeSubmission(sub3);
		submissions = service.getSubmissions(student, assn);
		studentWork = service.getStudentWork(student);
		assertTrue(submissions.size() == 2);
		assertTrue(studentWork.size() == 2);
		
		// Different assignment, same student
		Submission sub4 = new SubmissionImpl(student, new AssignmentImpl(55), b);
		service.storeSubmission(sub4);
		submissions = service.getSubmissions(student, assn);
		studentWork = service.getStudentWork(student);
		assertTrue(submissions.size() == 2);
		assertTrue(studentWork.size() == 3);
		
		
	}
}
