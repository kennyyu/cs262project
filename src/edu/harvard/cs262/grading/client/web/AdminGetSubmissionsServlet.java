package edu.harvard.cs262.grading.client.web;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.harvard.cs262.grading.server.services.Assignment;
import edu.harvard.cs262.grading.server.services.AssignmentImpl;
import edu.harvard.cs262.grading.server.services.ServiceLookupUtility;
import edu.harvard.cs262.grading.server.services.Student;
import edu.harvard.cs262.grading.server.services.StudentImpl;
import edu.harvard.cs262.grading.server.services.Submission;
import edu.harvard.cs262.grading.server.services.SubmissionStorageService;
import edu.harvard.cs262.grading.server.web.ServletConfigReader;

/**
 * Servlet is the middle communication layer between Administrative front end
 * web app and the SubmissionStorageService. RMI is used to talk to the server,
 * and http is the is used between this servlet and the web app.
 */
public class AdminGetSubmissionsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 11L;

	private SubmissionStorageService submissionStorage;

	public void lookupServices() {

		try {
			// get reference to database service
			submissionStorage = (SubmissionStorageService) ServiceLookupUtility
					.lookupService(
							new ServletConfigReader(this.getServletContext()),
							"SubmissionStorageService");
		} catch (RemoteException e) {
			e.printStackTrace(); // To change body of catch statement use File |
									// Settings | File Templates.
		} catch (NullPointerException e) {
			e.printStackTrace(); // To change body of catch statement use File |
									// Settings | File Templates.
		}
		if (submissionStorage == null) {
			System.err.println("Could not find SubmissionStorageService");
		}

	}

	public void init(ServletConfig config) throws ServletException {

		super.init(config);

		lookupServices();

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// get posted parameters
		String rawStudent = request.getParameter("student");
		String rawAssignment = request.getParameter("assignment");

		// build response
		StringBuilder responseBuilder = new StringBuilder();

		// attempt to get submission for corresponding parameters
		if (rawStudent != null && rawAssignment != null) {

			Submission submission = null;

			// try to convert parameters into usable format
			try {
				Long studentID = Long.parseLong(rawStudent);
				Long assignmentID = Long.parseLong(rawAssignment);
				Student student = new StudentImpl(studentID);
				Assignment assignment = new AssignmentImpl(assignmentID);

				// get submission
				submission = submissionStorage.getLatestSubmission(student,
						assignment);

			} catch (NumberFormatException e) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST,
						"invalid values given");
			} catch (NullPointerException e) {
				response.sendError(
						HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"submission retrieval failed");
				e.printStackTrace();
			}

			responseBuilder.append("{submissions:[");
			if (submission != null) {
				responseBuilder.append("{student:");
				responseBuilder.append(submission.getStudent().studentID());
				responseBuilder.append(",contents:");
				responseBuilder.append(submission.getContents().toString());
				responseBuilder.append("}");
			}
			responseBuilder.append("]}");

			response.setContentType("text/Javascript");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(responseBuilder.toString());

		} else if (rawAssignment != null) {

			Set<Submission> submissions = null;

			// try to convert parameters into usable format
			try {
				Long assignmentID = Long.parseLong(rawAssignment);
				Assignment assignment = new AssignmentImpl(assignmentID);

				// get submission
				submissions = submissionStorage.getAllSubmissions(assignment);

			} catch (NumberFormatException e) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST,
						"invalid values given");
			} catch (NullPointerException e) {
				response.sendError(
						HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"submission retrieval failed");
				e.printStackTrace();
			}

			responseBuilder.append("{submissions:[");
			if (submissions != null) {
				Iterator<Submission> submissionIter = submissions.iterator();
				while (submissionIter.hasNext()) {
					Submission submission = submissionIter.next();
					responseBuilder.append("{student:");
					responseBuilder.append(submission.getStudent().studentID());
					responseBuilder.append(",contents:");
					responseBuilder.append(submission.getContents());
					responseBuilder.append("}");
				}
			}
			responseBuilder.append("]}");

			response.setContentType("text/Javascript");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(responseBuilder.toString());

		} else if (rawStudent != null) {

			Set<Submission> submissions = null;

			// try to convert parameters into usable format
			try {
				Long studentID = Long.parseLong(rawStudent);
				Student student = new StudentImpl(studentID);

				// get submission
				submissions = submissionStorage.getStudentWork(student);

			} catch (NumberFormatException e) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST,
						"invalid values given");
			} catch (NullPointerException e) {
				response.sendError(
						HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"submission retrieval failed");
				e.printStackTrace();
			}

			responseBuilder.append("{submissions:[");
			if (submissions != null) {
				Iterator<Submission> submissionIter = submissions.iterator();
				while (submissionIter.hasNext()) {
					Submission submission = submissionIter.next();
					responseBuilder.append("{student:");
					responseBuilder.append(submission.getStudent().studentID());
					responseBuilder.append(",contents:");
					responseBuilder.append(submission.getContents());
					responseBuilder.append("}");
				}
			}
			responseBuilder.append("]}");

			response.setContentType("text/Javascript");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(responseBuilder.toString());

		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,
					"parameter(s) not set");
		}

	}
}