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

public class AdminGenerateShardServlet extends AdminFrontEndServlet {

	SharderServiceServer sharderService;

	public void lookupServices() {
		try {
			// get reference to database service
			Registry registry = LocateRegistry.getRegistry();
			sharderService = (SharderServiceServer) registry.lookup("SharderServiceServer");
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
	
	// passed assignment id for sharding
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	// get posted parameters (may have to update parameter names)
    	String rawAssignmentID = request.getParameter("assignmentID");
    	
    	if(rawAssignmentID == null) {
    		response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "parameters not set");
    	} else {

	    	// invoke the system to shard the assignmentID
	    	try{
		    	Integer assignmentID = Integer.parseInt(rawAssignmentID);
		    	Assignment assignment = new AssignmentImpl(assignmentID);
		    	sharderService.generateShard(assignment);
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
