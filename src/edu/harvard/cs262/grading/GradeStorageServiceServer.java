package edu.harvard.cs262.grading;

import java.rmi.RemoteException;
import java.util.List;

public class GradeStorageServiceServer implements GradeStorageService {

	@Override
	public void init() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void submitGrade(Student grader, Submission submission, Score score)
			throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Grade> getGrade(Submission submission) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
