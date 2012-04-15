package edu.harvard.cs262.grading;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;


/**
 * Servlet is the middle communication layer between Administrative
 * front end web app and the SubmissionStorageService. RMI is used to
 * talk to the server, and http is the is used between this
 * servlet and the web app.
 */
public class AdminGetSubmissionsServlet extends AdminFrontEndServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 11L;
	// operation codes
    static final int GET_SUBMISSION = 00;
    static final int GET_ALL_FOR_ASSIGNMENT = 01;
    static final int GET_ALL_FOR_STUDENT = 02;

    SubmissionStorageService submissionStorage;

    public void init(ServletConfig config) throws ServletException {

        super.init(config);

        try {
            // get reference to database service
            String name = registry + "/SubmissionStorageService";
            submissionStorage = (SubmissionStorageService) Naming.lookup(name);
        } catch (RemoteException e) {
            System.err.println("AdminGetSubmissionsServlet: Could not contact registry.");
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (MalformedURLException e) {
            System.err.println("AdminGetSubmissionsServlet: Malformed URL.");
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NotBoundException e) {
            System.err.println("AdminGetSubmissionsServlet: Could not find GradeStorageService in registry.");
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // use ObjectStream to send objects between web front and servers
        ObjectInputStream in = new ObjectInputStream(request.getInputStream());
        ObjectOutputStream out = new ObjectOutputStream(response.getOutputStream());

        // read in Objects from front end
        Integer opcode;
        try {
            Assignment assignment;
            Student student;
            opcode = (Integer) in.readObject();
            switch(opcode) {
                case GET_SUBMISSION:
                    Object param1 = in.readObject();
                    if(param1 instanceof Student) {
                        student = (Student) param1;
                        assignment = (Assignment) in.readObject();

                    } else if (param1 instanceof Assignment) {
                        assignment = (Assignment) param1;
                        student = (Student) in.readObject();
                    } else {
                        System.err.println("AdminGetSubmissionsServlet: Type mismatch for get submission");
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                                "Invalid Parameters for get submission");
                        break;
                    }
                    out.writeObject(submissionStorage.getSubmission(student, assignment));
                    break;
                case GET_ALL_FOR_ASSIGNMENT:
                    assignment = (Assignment) in.readObject();
                    out.writeObject(submissionStorage.getAllSubmissions(assignment));
                    break;
                case GET_ALL_FOR_STUDENT:
                    student = (Student) in.readObject();
                    out.writeObject(submissionStorage.getStudentWork(student));
                    break;
                default:
                    System.err.println("AdminGetSubmissionsServlet: Unrecognized operation: "+opcode);
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                            "Unrecognized operation");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("AdminGetSubmissionsServlet: Read Object was not of expected type.");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid arguments for operation.");
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClassCastException e) {
            System.err.println("AdminGetSubmissionsServlet: Read Object was not of expected type.");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid arguments for operation.");
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}
