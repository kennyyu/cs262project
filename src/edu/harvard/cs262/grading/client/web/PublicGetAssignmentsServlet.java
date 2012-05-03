package edu.harvard.cs262.grading.client.web;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.harvard.cs262.grading.server.services.Assignment;
import edu.harvard.cs262.grading.server.services.AssignmentStorageService;
import edu.harvard.cs262.grading.server.services.ServiceLookupUtility;
import edu.harvard.cs262.grading.server.web.ServletConfigReader;

public class PublicGetAssignmentsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 970835324318648315L;
	private AssignmentStorageService assignmentStorage;

	public void lookupServices() {

		try {
			// get reference to database service
			assignmentStorage = (AssignmentStorageService) ServiceLookupUtility
					.lookupService(
							new ServletConfigReader(this.getServletContext()),
							"AssignmentStorageService");
		} catch (RemoteException e) {
			e.printStackTrace(); // To change body of catch statement use File |
									// Settings | File Templates.
		} catch (NullPointerException e) {
			e.printStackTrace(); // To change body of catch statement use File |
									// Settings | File Templates.
		}
		if (assignmentStorage == null) {
			System.err.println("Looking up AssignmentStorageService failed.");
		}

	}

	public void init(ServletConfig config) throws ServletException {

		super.init(config);

		lookupServices();

	}

	/**
	 * on success: returns all assignments as JSON
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// get assignments from storage services
		Set<Assignment> assignments = assignmentStorage.getAssignments();
		
		// build response
		response.setContentType("text/Javascript");
		response.setCharacterEncoding("UTF-8");
		
		StringBuilder json = new StringBuilder("{\"assignments\":[");
		boolean addComma = false;
		for(Assignment a : assignments) {
			if(addComma) {
				json.append(",");
			} else {
				addComma = true;
			}
			json.append("{\"id\":");
			json.append(a.assignmentID());
			json.append(",\"description\":\"");
			json.append(a.description());
			json.append("\"}");
 		}
		json.append("]}");
		
		response.getWriter().write(json.toString());
		
	}
	
}
