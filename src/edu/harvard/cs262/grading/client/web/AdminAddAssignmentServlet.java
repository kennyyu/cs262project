package edu.harvard.cs262.grading.client.web;

import java.io.IOException;
import java.rmi.RemoteException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.harvard.cs262.grading.server.services.AssignmentServiceServer;
import edu.harvard.cs262.grading.server.services.ServiceLookupUtility;
import edu.harvard.cs262.grading.server.web.ServletConfigReader;

public class AdminAddAssignmentServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2895803517594443809L;

	private AssignmentServiceServer assignmentService;

	public void lookupServices() {

		try {
			// get reference to database service
			assignmentService = (AssignmentServiceServer) ServiceLookupUtility
					.lookupService(
							new ServletConfigReader(this.getServletContext()),
							"AssignmentService");
			System.err.println("Successfully located an assignment server.");
		} catch (RemoteException e) {
			e.printStackTrace(); // To change body of catch statement use File |
									// Settings | File Templates.
		} catch (NullPointerException e) {
			e.printStackTrace(); // To change body of catch statement use File |
									// Settings | File Templates.
		}
		if (assignmentService == null) {
			System.err.println("Looking up AssignmentService failed.");
		}

	}

	public void init(ServletConfig config) throws ServletException {

		super.init(config);

		lookupServices();

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// get posted parameters (may have to update parameter names)
		String rawAssignmentID = request.getParameter("assignmentID");
		String rawAssignmentDescription = request
				.getParameter("assignmentDescription");

		if (rawAssignmentID == null || rawAssignmentDescription == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,
					"parameters not set");
		} else {

			// invoke the system to shard the assignmentID
			try {
				Long assignmentID = Long.parseLong(rawAssignmentID);
				assignmentService.addNewAssignment(assignmentID,
						rawAssignmentDescription);
			} catch (NumberFormatException e) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST,
						"invalid values given");
			} catch (NullPointerException e) {
				response.sendError(
						HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"assignment addition failed");
				e.printStackTrace();
			}
		}
	}

}
