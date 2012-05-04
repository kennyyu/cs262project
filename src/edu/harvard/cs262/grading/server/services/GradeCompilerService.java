package edu.harvard.cs262.grading.server.services;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Service for compiling student generated grades.
 */
public interface GradeCompilerService extends Service {

	/**
	 * Submit a score for grading
	 * 
	 * @param grader
	 *            the person grading this assignment
	 * @param submission
	 *            the submission being graded
	 * @param score
	 *            the score for the submission
	 * @param comments
	 *            the grader's comment for the submission
	 * @return the Grade object
	 * @throws InvalidGraderForStudentException
	 *             this exception is thrown when the grader is not listed as one
	 *             of the graders for the submission
	 * @throws RemoteException
	 */
	public Grade storeGrade(Student grader, Submission submission, Score score,
			String comments) throws RemoteException,
			InvalidGraderForStudentException, NoShardsForAssignmentException;

	/**
	 * Get the list of graders for a given submission.
	 * 
	 * @param submission
	 * 				the submission that was graded
	 * @return the list of graders for the submission
	 * @throws RemoteException
	 */
	public Set<Student> getGraders(Submission submission)
			throws RemoteException;

	/**
	 * Return a mapping of submissions to grades for that submission for any
	 * given assignment
	 * 
	 * @param assignment
	 * @return the mapping of submissions to grades for that submission
	 * @throws RemoteException
	 */
	public Map<Submission, List<Grade>> getCompiledGrades(Assignment assignment)
			throws RemoteException;

}
