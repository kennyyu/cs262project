package edu.harvard.cs262.grading.client.web;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.harvard.cs262.grading.server.services.Assignment;
import edu.harvard.cs262.grading.server.services.AssignmentImpl;
import edu.harvard.cs262.grading.server.services.ServiceLookupUtility;
import edu.harvard.cs262.grading.server.services.Shard;
import edu.harvard.cs262.grading.server.services.SharderService;
import edu.harvard.cs262.grading.server.web.ServletConfigReader;

public class AdminGenerateShardServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -109316267304084820L;
	private SharderService sharderService;

	public void lookupServices() {

		try {
			// get reference to database service
			sharderService = (SharderService) ServiceLookupUtility
					.lookupService(
							new ServletConfigReader(this.getServletContext()),
							"SharderService");
			System.err.println("Successfully located a sharder server.");
		} catch (RemoteException e) {
			e.printStackTrace(); // To change body of catch statement use File |
									// Settings | File Templates.
		} catch (NullPointerException e) {
			e.printStackTrace(); // To change body of catch statement use File |
									// Settings | File Templates.
		}
		if (sharderService == null) {
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

		if (rawAssignmentID == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,
					"parameters not set");
		} else {

			// invoke the system to shard the assignmentID
			try {
				Long assignmentID = Long.parseLong(rawAssignmentID);
				Assignment assignment = new AssignmentImpl(assignmentID);
				Shard shard = sharderService.generateShard(assignment);

				StringBuilder responseBuilder = new StringBuilder();
				responseBuilder.append("{\"id\":");
				responseBuilder.append(shard.shardID());
				responseBuilder.append(",\"shard\":[");
				Map<Long, Set<Long>> sharding = shard.getShard();

				boolean addComma = false;
				for (Long grader : sharding.keySet()) {
					if(addComma) responseBuilder.append(",");
					else addComma = true;
					responseBuilder.append("{\"grader\":");
					responseBuilder.append(grader);
					responseBuilder.append(",\"gradees\":[");
					for (Long gradee : sharding.get(grader)) {
						responseBuilder.append(gradee);
						responseBuilder.append(",");
					}
					responseBuilder.deleteCharAt(responseBuilder.length() - 1);
					responseBuilder.append("]}");
				}
				responseBuilder.append("]}");

				response.setContentType("text/Javascript");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(responseBuilder.toString());
				
				
			} catch (NumberFormatException e) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST,
						"invalid values given");
			} catch (NullPointerException e) {
				response.sendError(
						HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"shard generation failed");
				e.printStackTrace();
			}
		}
	}

}
