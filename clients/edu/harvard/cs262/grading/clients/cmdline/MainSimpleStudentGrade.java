package edu.harvard.cs262.grading.clients.cmdline;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.harvard.cs262.grading.service.Assignment;
import edu.harvard.cs262.grading.service.AssignmentImpl;
import edu.harvard.cs262.grading.service.Grade;
import edu.harvard.cs262.grading.service.GradeCompilerService;
import edu.harvard.cs262.grading.service.Score;
import edu.harvard.cs262.grading.service.ScoreImpl;
import edu.harvard.cs262.grading.service.Student;
import edu.harvard.cs262.grading.service.StudentImpl;
import edu.harvard.cs262.grading.service.Submission;
import edu.harvard.cs262.grading.service.SubmissionImpl;

public class MainSimpleStudentGrade {

	public static void main(String[] args) throws Exception {
		if (args.length != 6) {
			System.err.println("usage: graderID submitterID assignmentID string score string");
			System.exit(-1);
		}
		
		// get an instance of a remote server
		Registry registry = LocateRegistry.getRegistry();
		GradeCompilerService s = (GradeCompilerService)
			registry.lookup("GradeCompilerService");
		
		// prepare data
		Student grader = new StudentImpl(Long.parseLong(args[0]));
		Student submitter = new StudentImpl(Long.parseLong(args[1]));
		Assignment assignment = new AssignmentImpl(Long.parseLong(args[2]));
		byte contents[] = args[3].getBytes();
		Submission submission = new SubmissionImpl(submitter, assignment, contents);
		Score score = new ScoreImpl(Integer.parseInt(args[4]),100);
		String comments = args[5];
		
		// remote calls
		Grade grade = s.storeGrade(grader, submission, score, comments);
		System.out.println(grade.getGrader());
		System.out.println(grade.getScore());
		System.out.println(grade.getComments());
		System.out.println(grade.getTimeStamp());
		
		// get all grades
		Map<Submission, List<Grade>> grades = s.getCompiledGrades(assignment);
		System.out.println(grades);
		
		// get all graders
		Set<Student> graders = s.getGraders(submission);
		System.out.println(graders);
	}

}
