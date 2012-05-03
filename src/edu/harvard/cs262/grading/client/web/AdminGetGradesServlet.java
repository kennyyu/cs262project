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
import edu.harvard.cs262.grading.server.services.AssignmentImpl;
import edu.harvard.cs262.grading.server.services.Grade;
import edu.harvard.cs262.grading.server.services.GradeStorageService;
import edu.harvard.cs262.grading.server.services.ServiceLookupUtility;
import edu.harvard.cs262.grading.server.services.Student;
import edu.harvard.cs262.grading.server.services.StudentService;
import edu.harvard.cs262.grading.server.services.Submission;
import edu.harvard.cs262.grading.server.services.SubmissionStorageService;
import edu.harvard.cs262.grading.server.web.ServletConfigReader;

/**
 * Servlet is the middle communication layer between Administrative front end
 * web app and the GradeStorageService. RMI is used to talk to the server, and
 * http is the is used between this servlet and the web app.
 */
public class AdminGetGradesServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5522723969145795538L;

	private GradeStorageService gradeService;
	private SubmissionStorageService submissionStorage;
	private StudentService studentService;

	public void lookupServices() {

		try {
			// get reference to database service
			gradeService = (GradeStorageService) ServiceLookupUtility
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
		if (gradeService == null) {
			System.err.println("Looking up GradeStorageService failed.");
		}

		try {
			// get reference to database service
			studentService = (StudentService) ServiceLookupUtility
					.lookupService(
							new ServletConfigReader(this.getServletContext()),
							"StudentService");
		} catch (RemoteException e) {
			e.printStackTrace(); // To change body of catch statement use File |
									// Settings | File Templates.
		} catch (NullPointerException e) {
			e.printStackTrace(); // To change body of catch statement use File |
									// Settings | File Templates.
		}
		if (studentService == null) {
			System.err.println("Looking up StudentService failed.");
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
			System.err.println("Looking up SubmissionStorageService failed.");
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
		if (rawAssignment == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,
					"parameters not set");
		} else {

			// try to convert parameters into usable format
			try {
				Long assignmentID = Long.parseLong(rawAssignment);
				Assignment assignment = new AssignmentImpl(assignmentID,"");
				
				// get students
				Set<Student> students = studentService.getStudents();

				// XXX: This needs to be double-checked to sure that we get the
				// grade pertaining to the latest
				// submission

	    		// start building response text
	    		StringBuilder responseBuilder = new StringBuilder("{\"submissions\":[");
		    	
	    		// iterate over students
	    		boolean addComma = false;
		    	for(Student student : students){
	    			if(addComma) {
	    				responseBuilder.append(",");
	    			} else {
	    				addComma = true;
	    			}

					Submission submission = submissionStorage
							.getLatestSubmission(student, assignment);
    				responseBuilder.append("{\"student\":");
    				responseBuilder.append(student.studentID());
    				responseBuilder.append(",\"timestamp\":\"");
    				responseBuilder.append(submission == null? "" : submission.getTimeStamp());
    				responseBuilder.append("\",\"grades\":[");
    				if(submission != null) {	// get grades for submission
    	    			List<Grade> grades = gradeService.getGrade(submission);
    	    			ListIterator<Grade> gradeIter = grades.listIterator();
	    	    		boolean addInnerComma = false;
		    			while(gradeIter.hasNext()) {
			    			if(addInnerComma) {
			    				responseBuilder.append(",");
			    			} else {
			    				addInnerComma = true;
			    			}
		    				Grade grade = gradeIter.next();
		    				responseBuilder.append("{\"grader\":");
		    				responseBuilder.append(grade.getGrader().studentID());
							responseBuilder.append(",\"score\":\"");
							responseBuilder.append(grade.getScore().getScore()
									+ "/" + grade.getScore().maxScore());
							responseBuilder.append("\"}");
		    			}
    				}
    				responseBuilder.append("]}");
    				
		   		}
		    	
		    	// finish response and send
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
