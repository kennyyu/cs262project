package edu.harvard.cs262.grading.client.cmdline;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import edu.harvard.cs262.grading.server.services.Assignment;
import edu.harvard.cs262.grading.server.services.AssignmentImpl;
import edu.harvard.cs262.grading.server.services.Student;
import edu.harvard.cs262.grading.server.services.StudentImpl;
import edu.harvard.cs262.grading.server.services.Submission;
import edu.harvard.cs262.grading.server.services.SubmissionReceiverService;

public class MainSimpleStudentSubmission {

	public static void main(String[] args) throws Exception {
		if (args.length != 3) {
			System.err.println("usage: studentID assignmentID string");
			System.exit(-1);
		}
		// get an instance of a remote server
		Registry registry = LocateRegistry.getRegistry();
		SubmissionReceiverService s = (SubmissionReceiverService) registry
				.lookup("SubmissionReceiverService");

		// prepare data
		Student student = new StudentImpl(Long.parseLong(args[0]));
		Assignment assignment = new AssignmentImpl(Long.parseLong(args[1]));
		byte contents[] = args[2].getBytes();

		// remote call
		Submission submission = s.submit(student, assignment, contents);

		System.out.println(submission.getStudent());
		System.out.println(submission.getAssignment());
		System.out.println(submission.getContents());
		System.out.println(submission.getTimeStamp());
	}

}
