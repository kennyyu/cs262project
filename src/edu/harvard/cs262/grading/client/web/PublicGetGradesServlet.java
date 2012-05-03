package edu.harvard.cs262.grading.client.web;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.harvard.cs262.grading.server.services.Assignment;
import edu.harvard.cs262.grading.server.services.AssignmentImpl;
import edu.harvard.cs262.grading.server.services.Grade;
import edu.harvard.cs262.grading.server.services.GradeCompilerService;
import edu.harvard.cs262.grading.server.services.ServiceLookupUtility;
import edu.harvard.cs262.grading.server.services.Submission;
import edu.harvard.cs262.grading.server.web.ServletConfigReader;

public class PublicGetGradesServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4599257324201086694L;
	private GradeCompilerService gradeServer;
    
    public void lookupServices() {
    
        try {
        	// get reference to database service
        	gradeServer = (GradeCompilerService) ServiceLookupUtility.lookupService(new ServletConfigReader(this.getServletContext()), "GradeCompilerService");
        } catch (RemoteException e) {
        	e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NullPointerException e) {
        	e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
		if(gradeServer == null) {
			System.err.println("StudentGetGradesServlet: Looking up GradeCompilerService failed.");
		}
    	
    }

    public void init(ServletConfig config) throws ServletException {

        super.init(config);
        
        lookupServices();

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    	
    	// get posted parameters
    	String rawAssignment = request.getParameter("assignment");
    	
    	// attempt to get corresponding grade
    	if(rawAssignment == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "parameters not set");
    	} else {
    	
	    	try{
	    		
	    		// check parameters and convert into proper format
		    	Long assignmentID = Long.parseLong(rawAssignment);
		    	Assignment assignment = new AssignmentImpl(assignmentID);
		    	
		    	// get submissions
		    	Map<Submission, List<Grade>> allSubmissionsAndGrades = gradeServer.getCompiledGrades(assignment);
		    	Set<Submission> submissions = allSubmissionsAndGrades.keySet();
		    	
		    	// set response headers
	    		response.setContentType("text/javascript");
	    		response.setCharacterEncoding("UTF-8");

	    		// start building response text
	    		StringBuilder responseBuilder = new StringBuilder("{submissions:[");
		    	
	    		// iterate over submissions
	    		boolean addComma = false;
	    		int gradedSubmissionsCount = 0;
		    	for(Submission s : submissions){
	    			List<Grade> grades = allSubmissionsAndGrades.get(s);
	    			ListIterator<Grade> gradeIter = grades.listIterator();
	    			if(addComma) {
	    				responseBuilder.append(",");
	    			} else {
	    				addComma = true;
	    			}
    				responseBuilder.append("[");
    	    		boolean addInnerComma = false;
	    			while(gradeIter.hasNext()) {
		    			if(addInnerComma) {
		    				responseBuilder.append(",");
		    			} else {
		    				addInnerComma = true;
		    			}
	    				Grade grade = gradeIter.next();
	    				responseBuilder.append("{score:");
	    				responseBuilder.append(grade.getScore().getScore()+"/"+grade.getScore().maxScore());
	    				responseBuilder.append("}");
	    			}
    				responseBuilder.append("]");
    				
    				if(addInnerComma) gradedSubmissionsCount++;
    				
		   		}
		    	
		    	// finish response and send
	    		responseBuilder.append("]");
	    		responseBuilder.append(",submissions_count:"+submissions.size());
	    		responseBuilder.append(",submissions_graded_count:"+gradedSubmissionsCount);
	    		responseBuilder.append("}");
	    		response.getWriter().write(responseBuilder.toString());
	    		
	    	} catch (NumberFormatException e){
	            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
	                    "invalid values given");
	    	} catch (NullPointerException e) {
	    		response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
	    				"grade retrieval failed");
	    		e.printStackTrace();
	    	}
	    
    	}
    }

}
