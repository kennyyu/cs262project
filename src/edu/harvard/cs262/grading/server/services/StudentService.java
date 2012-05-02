package edu.harvard.cs262.grading.server.services;

import java.rmi.RemoteException;
import java.util.Set;

public interface StudentService extends Service {
	
	/**
	 * Add a new student
	 * 
	 * @param ID
	 *            a unique identifier for the student
	 * @param email
	 * @param firstName
	 * @param lastName
	 * @throws RemoteException
	 */
	public void addNewStudent(long ID, String email, String firstName, String lastName) throws RemoteException;

	/**
	 * Retrieve the existing students
	 * 
	 * @return a set of assignments
	 * @throws RemoteException
	 */
	public Set<Student> getStudents() throws RemoteException;

	/**
	 * Retrieve a student by ID
	 * 
	 * @param ID
	 * 				the ID of the student to retrieve
	 * @return a set of assignments
	 * @throws RemoteException
	 */
	public Student getStudent(long ID) throws RemoteException;
}
