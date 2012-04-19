package edu.harvard.cs262.grading;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StudentSubmissionsServlet extends AdminFrontEndServlet{

	SubmissionStorageService submissionStorage;
	
	public void lookupServices() {
		
		try {
			// get reference to database service
			Registry registry = LocateRegistry.getRegistry();
			submissionStorage = (SubmissionStorageService) registry.lookup("SubmissionStorageService");
			System.err.println("Successfully located submission storage service.");
		} catch (RemoteException e) {
			System.err.println("AdminGetSubmissionsServlet: Could not contact registry.");
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		} catch (NotBoundException e) {
			System.err.println("AdminGetSubmissionsServlet: Could not find SubmissionStorageService in registry.");
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	    	
	}

	public void init(ServletConfig config) throws ServletException {

		super.init(config);
	        
		lookupServices();

	}
	
	// passed id for student and actual submission
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	// get posted parameters
    	String rawStudent = request.getParameter("student");
    	String rawAssignment = request.getParameter("assignment");
    }

}
