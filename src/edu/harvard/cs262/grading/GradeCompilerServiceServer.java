package edu.harvard.cs262.grading;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.harvard.cs262.grading.Assignment;
import edu.harvard.cs262.grading.Grade;
import edu.harvard.cs262.grading.GradeCompilerService;
import edu.harvard.cs262.grading.Score;
import edu.harvard.cs262.grading.Student;
import edu.harvard.cs262.grading.Submission;


public class GradeCompilerServiceServer implements GradeCompilerService {

	private GradeCompilerService server;
	
	public GradeCompilerServiceServer(){
		server = null;
	}
	
	@Override
	public void init() throws Exception {
		// TODO Auto-generated method stub
		// copy the same logic as lookupStorageService in SubmissionReceiver?
	}

	@Override
	public Grade storeGrade(Student grader, Submission submission, Score score)
			throws RemoteException {
		// TODO Auto-generated method stub
		GradeStorageService gradeStorageService = new GradeStorageServiceServer();
		/*
		gradeStorageService.init();
		gradeStorageService.submitGrade(grader,submission,score);
		*/
		return null;
	}

	@Override
	public Set<Student> getGraders(Submission submission)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Submission, List<Grade>> getCompiledGrades(Assignment assignment)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
