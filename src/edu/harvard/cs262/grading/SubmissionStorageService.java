package edu.harvard.cs262.grading;

/**
 * Service for persistent storage of student work submission.
 */
public interface SubmissionStorageService {
	/*
	bool put(studentID, workID, workObject)
	workObject get(studentID, workID)
	Set<(workID, workObject)> getAllWork(studentID)
	Set<studentID, workObject> getSubmissions(workID)
	*/
	public void storeSubmission(Submission submission);
}
