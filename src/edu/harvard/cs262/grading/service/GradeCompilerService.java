package edu.harvard.cs262.grading.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Service for compiling student generated grades.
 */
public interface GradeCompilerService extends Remote, Service {

	/**
	 * Submit a score for grading
	 * 
	 * @param grader
	 *            the person grading this assignment
	 * @param submission
	 *            the submission being graded
	 * @param score
	 *            the score for the submission
	 * @param return the Grade object
	 * @throws RemoteException
	 */
	public Grade storeGrade(Student grader, Submission submission, Score score, String comments)
			throws RemoteException;

	/**
	 * Get the list of graders for a given submission.
	 * 
	 * @param submission
	 * @return
	 * @throws RemoteException
	 */
	public Set<Student> getGraders(Submission submission)
			throws RemoteException;

	/**
	 * Return a mapping of submissions to grades for that submission for any
	 * given assignment
	 * 
	 * @param assignment
	 * @return
	 * @throws RemoteException
	 */
	public Map<Submission, List<Grade>> getCompiledGrades(Assignment assignment)
			throws RemoteException;

}
