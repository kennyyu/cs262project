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
import edu.harvard.cs262.grading.server.services.NoShardsForAssignmentException;
import edu.harvard.cs262.grading.server.services.ServiceLookupUtility;
import edu.harvard.cs262.grading.server.services.Shard;
import edu.harvard.cs262.grading.server.services.SharderService;
import edu.harvard.cs262.grading.server.web.ServletConfigReader;

public class AdminReviewShardServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -952195365293520107L;
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

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// get posted parameters
		String rawAssignmentID = request.getParameter("assignmentID");

		// attempt to get corresponding grade
		if (rawAssignmentID == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,
					"parameters not set");
		} else {

			// try to convert parameters into usable format
			try {
				Long assignmentID = Long.parseLong(rawAssignmentID);
				Assignment assignment = new AssignmentImpl(assignmentID);

				Shard shard = sharderService.getShard(sharderService
						.getShardID(assignment));
				StringBuilder responseBuilder = new StringBuilder();
				responseBuilder.append("{shard:[");
				Map<Long, Set<Long>> sharding = shard.getShard();

				for (Long grader : sharding.keySet()) {
					responseBuilder.append("{grader:");
					responseBuilder.append(grader);
					responseBuilder.append(",gradees:[");
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
						"grade retrieval failed");
				e.printStackTrace();
			} catch (NoShardsForAssignmentException e) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST,
				"assignment has no shards");
			}

		}
	}
}
