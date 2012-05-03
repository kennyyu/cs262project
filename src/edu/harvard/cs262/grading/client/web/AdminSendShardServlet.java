package edu.harvard.cs262.grading.client.web;

import java.io.IOException;
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.harvard.cs262.grading.server.services.ServiceLookupUtility;
import edu.harvard.cs262.grading.server.services.Shard;
import edu.harvard.cs262.grading.server.services.SharderService;
import edu.harvard.cs262.grading.server.services.StudentService;
import edu.harvard.cs262.grading.server.web.ServletConfigReader;

public class AdminSendShardServlet extends HttpServlet {

	public static String STAFF_EMAIL = "smuller@fas.harvard.edu";
	public static String COURSE = "CS262";

	/**
	 * 
	 */
	private static final long serialVersionUID = -952195365293520107L;
	private SharderService sharderService;
	private StudentService studentService;
	private boolean sandbox;

	public AdminSendShardServlet(boolean sandbox) {
		this.sandbox = sandbox;
	}

	public void lookupServices() {

		try {
			// get reference to database service
			sharderService = (SharderService) ServiceLookupUtility
					.lookupService(
							new ServletConfigReader(this.getServletContext()),
							"SharderService");
			System.err.println("Successfully located a sharder server.");

			// get reference to student service
			studentService = (StudentService) ServiceLookupUtility
					.lookupService(
							new ServletConfigReader(this.getServletContext()),
							"StudentService");
			System.err.println("Successfully located a student server.");
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

	public void sendShard(Shard shard) throws IOException {
		for (Entry<Long, Set<Long>> e : shard.getShard().entrySet()) {
			PrintStream stdin;
			if (sandbox) {
				// Send e-mails to stdout
				stdin = System.out;
			} else {
				Process sendmail = Runtime.getRuntime().exec(
						"/usr/bin/sendmail -t");
				stdin = new PrintStream(sendmail.getOutputStream());
			}

			// Print headers
			stdin.println("From: " + STAFF_EMAIL);
			stdin.println("To: "
					+ studentService.getStudent(e.getKey()).email());
			stdin.println("Subject: Your " + COURSE + " Grading Assignment");
			stdin.println();

			stdin.println("You will be grading the assignments for the students with the IDs listed below:");
			for (Long ID : e.getValue()) {
				stdin.println(ID);
			}
			stdin.println();

			stdin.println("Thanks,");
			stdin.println("The " + COURSE + " course staff");

			if (!sandbox)
				stdin.close();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// get posted parameters
		String rawShardID = request.getParameter("shardID");

		// attempt to get corresponding grade
		if (rawShardID == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,
					"parameters not set");
		} else {

			// try to convert parameters into usable format
			try {
				Integer shardID = Integer.parseInt(rawShardID);

				Shard shard = sharderService.getShard(shardID);

				sendShard(shard);

				response.setContentType("text/Javascript");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write("grading assignments sent");

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
