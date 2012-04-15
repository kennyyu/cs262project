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
 * front end web app and the GradeStorageService. RMI is used to
 * talk to the server, and http is the is used between this
 * servlet and the web app.
 */
public class AdminGetGradesServlet extends AdminFrontEndServlet {

    // operation codes
    static final int GET_GRADES_FOR_SUBMISSION = 00;

    GradeStorageService gradeStorage;

    public void init(ServletConfig config) throws ServletException {

        super.init(config);

        try {
            // get reference to database service
            String name = registry + "/GradeStorageService";
            gradeStorage = (GradeStorageService) Naming.lookup(name);
        } catch (RemoteException e) {
            System.err.println("AdminGetGradesServlet: Could not contact registry.");
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (MalformedURLException e) {
            System.err.println("AdminGetGradesServlet: Malformed URL.");
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NotBoundException e) {
            System.err.println("AdminGetGradesServlet: Could not find GradeStorageService in registry.");
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
            opcode = (Integer) in.readObject();
            switch(opcode) {
                case GET_GRADES_FOR_SUBMISSION:
                    Submission submission = (Submission) in.readObject();
                    out.writeObject(gradeStorage.getGrade(submission));
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                            "Unrecognized operation");
                    break;
                default:
                    System.err.println("AdminGetGradesServlet: Unrecognized operation: "+opcode);
            }
        } catch (ClassNotFoundException e) {
            System.err.println("AdminGetGradesServlet: Read Object was not of expected type.");
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClassCastException e) {
            System.err.println("AdminGetGradesServlet: Read Object was not of expected type.");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid arguments for operation.");
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}