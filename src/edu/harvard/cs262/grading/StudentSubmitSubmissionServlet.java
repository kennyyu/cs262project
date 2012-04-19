package edu.harvard.cs262.grading;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StudentSubmitSubmissionServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7755775296767098218L;
	private SubmissionStorageService submissionStorage;
	
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

    	// get posted parameters (may have to update parameter names)
    	String rawStudent = request.getParameter("uid");
    	String rawAssignment = request.getParameter("assignment");
    	String rawSubmission = request.getParameter("submission");
    	
    	if(rawStudent == null || rawSubmission == null || rawAssignment == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "parameters not set");
    	} else {
    		
    		Submission submission;

	    	// try to convert parameters into usable format
	    	try{
		    	Integer studentID = Integer.parseInt(rawStudent);
		    	Integer assignmentID = Integer.parseInt(rawAssignment);
		    	Assignment assignment = new AssignmentImpl(assignmentID);
    	    	Student student = new StudentImpl(studentID);
		    	
		    	submission = new SubmissionImpl(student, assignment, rawSubmission.getBytes());
	    		submissionStorage.storeSubmission(submission);

	        	response.setContentType("text/Javascript");
	        	response.setCharacterEncoding("UTF-8");
	        	response.getWriter().write("Succesfully submitted submission for assignment "+assignmentID+".");
		    	
	    	} catch (NumberFormatException e){
	            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
	                    "invalid values given");
	    	} catch (NullPointerException e) {
	    		response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
	    				"submission upload failed");
	    		e.printStackTrace();
	    	}	
    	}
    }
}

