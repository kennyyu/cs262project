package edu.harvard.cs262.grading.client.web;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.harvard.cs262.grading.server.services.Assignment;
import edu.harvard.cs262.grading.server.services.AssignmentStorageService;
import edu.harvard.cs262.grading.server.services.Grade;
import edu.harvard.cs262.grading.server.services.GradeStorageService;
import edu.harvard.cs262.grading.server.services.ServiceLookupUtility;
import edu.harvard.cs262.grading.server.services.Student;
import edu.harvard.cs262.grading.server.services.StudentImpl;
import edu.harvard.cs262.grading.server.services.Submission;
import edu.harvard.cs262.grading.server.services.SubmissionStorageService;
import edu.harvard.cs262.grading.server.web.ServletConfigReader;

// Will return grades for all the submissions from given student

// not sure how to fix this serialization error

public class StudentGetGradesServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8258290257175670745L;
	private GradeStorageService gradeStorage;
	private SubmissionStorageService submissionStorage;
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

		try {
			// get reference to database service
			gradeStorage = (GradeStorageService) ServiceLookupUtility
					.lookupService(
							new ServletConfigReader(this.getServletContext()),
							"GradeStorageService");
		} catch (RemoteException e) {
			e.printStackTrace(); // To change body of catch statement use File |
									// Settings | File Templates.
		} catch (NullPointerException e) {
			e.printStackTrace(); // To change body of catch statement use File |
									// Settings | File Templates.
		}
		if (gradeStorage == null) {
			System.err.println("Looking up GradeStorageService failed.");
		}

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
		String rawStudent = request.getParameter("uid");

		// attempt to get corresponding grade
		if (rawStudent == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,
					"parameters not set");
		} else {

			// try to convert parameters into usable format
			try {
				Integer studentID = Integer.parseInt(rawStudent);
				Student student = new StudentImpl(studentID);

				Set<Assignment> allAssignments = assignmentStorage
						.getAssignments();

				// iterate over assignments
				StringBuilder responseBuilder = new StringBuilder(
						"{\"grades\":[");
				boolean addComma = false;
				for (Assignment assignment : allAssignments) {
					if (addComma) {
						responseBuilder.append(",");
					} else {
						addComma = true;
					}

					responseBuilder.append("{\"assignmentID\":");
					responseBuilder.append(assignment.assignmentID());
					responseBuilder.append(",\"assignmentDescription\":\"");
					responseBuilder.append(assignment.description());
					responseBuilder.append("\"");

					Submission submission = submissionStorage
							.getLatestSubmission(student, assignment);

					// check if the student has a submission for the assignment
					if (submission == null) {
						responseBuilder
								.append(",\"submissionTimestamp\":\"\",\"grades\":[]}");
					} else {
						List<Grade> grades = gradeStorage.getGrade(submission);
						responseBuilder.append(",\"submissionTimestamp\":\"");
						responseBuilder.append(submission.getTimeStamp());
						responseBuilder.append("\",\"grades\":[");
						boolean addInnerComma = false;
						if (grades != null) {
							ListIterator<Grade> gradeIter = grades
									.listIterator();
							while (gradeIter.hasNext()) {
								if (addInnerComma) {
									responseBuilder.append(",");
								} else {
									addInnerComma = true;
								}
								Grade grade = gradeIter.next();
								responseBuilder.append("{\"grader\":");
								responseBuilder.append(grade.getGrader()
										.studentID());
								responseBuilder.append(",\"score\":\"");
								responseBuilder.append(grade.getScore()
										.getScore()
										+ "/"
										+ grade.getScore().maxScore());
								responseBuilder.append("\"}");
							}
						}
						responseBuilder.append("]}"); // end assignment
					}
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
