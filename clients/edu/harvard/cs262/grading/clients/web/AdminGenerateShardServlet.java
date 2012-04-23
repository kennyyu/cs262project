package edu.harvard.cs262.grading.clients.web;

import java.io.IOException;
import java.rmi.RemoteException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.harvard.cs262.grading.service.Assignment;
import edu.harvard.cs262.grading.service.AssignmentImpl;
import edu.harvard.cs262.grading.service.ServiceLookupUtility;
import edu.harvard.cs262.grading.service.SharderServiceServer;
import edu.harvard.cs262.grading.service.web.ServletConfigReader;

public class AdminGenerateShardServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -109316267304084820L;
	SharderServiceServer sharderService;

	public void lookupServices() {

        try {
            // get reference to database service
        	sharderService = (SharderServiceServer) ServiceLookupUtility.lookupService(new ServletConfigReader(this.getServletContext()), "SharderService");
			System.err.println("Successfully located a sharder server.");
        } catch (RemoteException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NullPointerException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        if(sharderService == null) {
        	System.err.println("Looking up SharderService failed.");
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
		    	Long assignmentID = Long.parseLong(rawAssignmentID);
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
