package edu.harvard.cs262.grading.client.web;

import java.io.IOException;
import java.rmi.RemoteException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.harvard.cs262.grading.server.services.AssignmentStorageService;
import edu.harvard.cs262.grading.server.services.ServiceLookupUtility;
import edu.harvard.cs262.grading.server.web.ServletConfigReader;

public class AdminAddAssignmentServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2895803517594443809L;

	private AssignmentStorageService assignmentService;

	public void lookupServices() {

		try {
			// get reference to database service
			assignmentService = (AssignmentStorageService) ServiceLookupUtility
					.lookupService(
							new ServletConfigReader(this.getServletContext()),
							"AssignmentStorageService");
			System.err.println("Successfully located an assignment server.");
		} catch (RemoteException e) {
			e.printStackTrace(); // To change body of catch statement use File |
									// Settings | File Templates.
		} catch (NullPointerException e) {
			e.printStackTrace(); // To change body of catch statement use File |
									// Settings | File Templates.
		}
		if (assignmentService == null) {
			System.err.println("Looking up AssignmentStorageService failed.");
		}

	}

	public void init(ServletConfig config) throws ServletException {

		super.init(config);

		lookupServices();

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// get posted parameters (may have to update parameter names)
		String rawDescription = request.getParameter("description");

		if (rawDescription == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,
					"parameters not set");
		} else {

			int assignmentID = assignmentService.getAssignments().size();

			assignmentService.addNewAssignment(assignmentID, rawDescription);

			// generate assignment ID
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(String.valueOf(assignmentID));

		}

	}

}
