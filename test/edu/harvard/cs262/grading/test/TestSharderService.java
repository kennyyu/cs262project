package edu.harvard.cs262.grading.test;

import static org.junit.Assert.*;
import org.junit.Test;

import edu.harvard.cs262.grading.server.services.Assignment;
import edu.harvard.cs262.grading.server.services.AssignmentImpl;
import edu.harvard.cs262.grading.server.services.MongoSubmissionStorageService;
import edu.harvard.cs262.grading.server.services.NoShardsForAssignmentException;
import edu.harvard.cs262.grading.server.services.Shard;
import edu.harvard.cs262.grading.server.services.SharderServiceServer;
import edu.harvard.cs262.grading.server.services.StudentImpl;
import edu.harvard.cs262.grading.server.services.Submission;
import edu.harvard.cs262.grading.server.services.SubmissionImpl;

public class TestSharderService {

	@Test
	public void testShard() throws Exception {
		MongoSubmissionStorageService storage = new MongoSubmissionStorageService();
		storage.init();
		storage.heartbeat();
		SharderServiceServer sharder = new SharderServiceServer(storage);
		sharder.init();
		sharder.heartbeat();

		byte[] contents = new byte[42];

		Assignment assn = new AssignmentImpl(6000L);

		Submission s0 = new SubmissionImpl(new StudentImpl(400L), assn, contents);
		Submission s1 = new SubmissionImpl(new StudentImpl(401L), assn, contents);

		storage.storeSubmission(s0);
		storage.storeSubmission(s1);

		Shard shard = sharder.generateShard(assn);
		assertTrue(shard.getShard().containsKey(400L));
		assertTrue(shard.getShard().get(400L).contains(401L));

		assertTrue(sharder.getShard(shard.shardID()).equals(shard));

		assertTrue(sharder.getShardID(assn) == shard.shardID());

		try {
			System.out.println(sharder.getShardID(new AssignmentImpl(1874L)));
			fail();
		} catch (NoShardsForAssignmentException e) {
			assertTrue(true);
		}

	}
}
