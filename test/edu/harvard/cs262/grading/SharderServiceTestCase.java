package edu.harvard.cs262.grading;

import static org.junit.Assert.*;
import org.junit.Test;

public class SharderServiceTestCase {

	@Test
	public void testShard() throws Exception {
		MongoSubmissionStorageService storage = new MongoSubmissionStorageService();
		storage.init();
		SharderServiceServer sharder = new SharderServiceServer();
		sharder.init(true);
		
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
