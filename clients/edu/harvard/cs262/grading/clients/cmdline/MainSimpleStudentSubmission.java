package edu.harvard.cs262.grading.clients.cmdline;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import edu.harvard.cs262.grading.service.Assignment;
import edu.harvard.cs262.grading.service.AssignmentImpl;
import edu.harvard.cs262.grading.service.Student;
import edu.harvard.cs262.grading.service.StudentImpl;
import edu.harvard.cs262.grading.service.Submission;
import edu.harvard.cs262.grading.service.SubmissionReceiverService;

public class MainSimpleStudentSubmission {

	public static void main(String[] args) throws Exception {
		if (args.length != 3) {
			System.err.println("usage: studentID assignmentID string");
			System.exit(-1);
		}
		// get an instance of a remote server
		Registry registry = LocateRegistry.getRegistry();
		SubmissionReceiverService s = (SubmissionReceiverService) 
			registry.lookup("SubmissionReceiverService");
		
		// prepare data
		Student student = new StudentImpl(Integer.parseInt(args[0]));
		Assignment assignment = new AssignmentImpl(Integer.parseInt(args[1]));
		byte contents[] = args[2].getBytes();
		
		// remote call
		Submission submission = s.submit(student, assignment, contents);
		
		System.out.println(submission.getStudent());
		System.out.println(submission.getAssignment());
		System.out.println(submission.getContents());
		System.out.println(submission.getTimeStamp());
	}
	
}
