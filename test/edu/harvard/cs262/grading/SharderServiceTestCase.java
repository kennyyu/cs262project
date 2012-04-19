package edu.harvard.cs262.grading;

import static org.junit.Assert.*;

import java.net.UnknownHostException;
import java.rmi.RemoteException;

import org.junit.Test;

import com.mongodb.MongoException;

public class SharderServiceTestCase {

	@Test
	public void testShard() throws RemoteException, UnknownHostException, MongoException {
		MongoSubmissionStorageService storage = new MongoSubmissionStorageService();
		storage.init();
		SharderServiceServer sharder = new SharderServiceServer();
		sharder.init();
		
		byte[] contents = new byte[42];
		
		Assignment assn = new AssignmentImpl(0);
		
		Submission s0 = new SubmissionImpl(new StudentImpl(0), assn, contents);
		Submission s1 = new SubmissionImpl(new StudentImpl(1), assn, contents);
		
		storage.storeSubmission(s0);
		storage.storeSubmission(s1);
		
		Shard shard = sharder.generateShard(assn);
		
		assertTrue(true);
		
		assertTrue(shard.getShard().containsKey(new StudentImpl(0)));
		assertTrue(shard.getShard().get(new StudentImpl(0)).contains(new StudentImpl(1)));
	}
}
