package edu.harvard.cs262.grading;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MainSimpleStudentGrade {

	public static void main(String[] args) throws Exception {
		// get an instance of a remote server
		Registry registry = LocateRegistry.getRegistry();
		GradeCompilerService s = (GradeCompilerService)
			registry.lookup("GradeCompilerService");
		
		// prepare data
		Student grader = new StudentImpl();
		Student submitter = new StudentImpl(1);
		Assignment assignment = new AssignmentImpl();
		byte contents[] = {(byte) 0, (byte) 1, (byte) 2, (byte) 3};
		Submission submission = new SubmissionImpl(submitter, assignment, contents);
		Score score = new ScoreImpl(5,10);
		
		// remote call
		Grade grade = s.storeGrade(grader, submission, score);
		
		System.out.println(grade.getGrader());
		System.out.println(grade.getScore());
		System.out.println(grade.getTimeStamp());
	}

}
