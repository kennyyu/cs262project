package edu.harvard.cs262.grading;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminReviewShardServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -952195365293520107L;
	SharderServiceServer sharderService;

	public void lookupServices() {
		try {
			// get reference to database service
			Registry registry = LocateRegistry.getRegistry();
			sharderService = (SharderServiceServer) registry
					.lookup("SharderServiceServer");
			System.err
					.println("Successfully located submission storage service.");
		} catch (RemoteException e) {
			System.err
					.println("AdminGetSubmissionsServlet: Could not contact registry.");
			e.printStackTrace(); // To change body of catch statement use File |
									// Settings | File Templates.
		} catch (NotBoundException e) {
			System.err
					.println("AdminGetSubmissionsServlet: Could not find SubmissionStorageService in registry.");
			e.printStackTrace(); // To change body of catch statement use File |
									// Settings | File Templates.
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
				Integer assignmentID = Integer.parseInt(rawAssignmentID);
				Assignment assignment = new AssignmentImpl(assignmentID);

				Shard shard = sharderService.getShard(sharderService
						.getShardID(assignment));
				StringBuilder responseBuilder = new StringBuilder();
				responseBuilder.append("{shard:[");
				Map<Student, Set<Student>> sharding = shard.getShard();

				for (Student grader : sharding.keySet()) {
					responseBuilder.append("{grader:");
					responseBuilder.append(grader.studentID());
					responseBuilder.append(",gradees:[");
					for (Student gradee : sharding.get(grader)) {
						responseBuilder.append(gradee.studentID());
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
			}

		}
	}
}
