package edu.harvard.cs262.grading;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MainSimpleStudentSubmission {

	public static void main(String[] args) throws Exception {
		// get an instance of a remote server
		Registry registry = LocateRegistry.getRegistry();
		SubmissionReceiverService s = (SubmissionReceiverService) 
			registry.lookup("SubmissionReceiverService");
		
		// prepare data
		Student student = new StudentImpl();
		Assignment assignment = new AssignmentImpl(0);
		byte contents[] = {(byte) 0, (byte) 1, (byte) 2, (byte)3};
		
		// remote call
		Submission submission = s.submit(student, assignment, contents);
		
		System.out.println(submission.getStudent());
		System.out.println(submission.getAssignment());
		System.out.println(submission.getContents());
		System.out.println(submission.getTimeStamp());
	}
	
}
