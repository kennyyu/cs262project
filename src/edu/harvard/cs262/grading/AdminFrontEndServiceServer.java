package edu.harvard.cs262.grading;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 4/12/12
 * Time: 10:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class AdminFrontEndServiceServer implements AdminFrontEndService {

    private SubmissionStorageService submissionServer;
    private GradeStorageService gradeServer;

    /**
     * Needs to lookup persistent grade storage service and
     * persistent submission storage service.
     *
     * @throws Exception
     */
    public void init() throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Attempts to locate grade and submission storage services
     */
    public void lookupStorageServices() throws Exception {
    }

    public String getGrades() throws RemoteException {
        return "some grades";
    }

    public String getSubmissions() throws RemoteException {
        return "some submissions";
    }

    public AdminFrontEndServiceServer(){};

    public static void main(String args[]) {

        try {

            // export remote server object
            AdminFrontEndServiceServer obj = new AdminFrontEndServiceServer();
            AdminFrontEndService stub =
                    (AdminFrontEndService) UnicastRemoteObject.exportObject(obj, 0);

            // service's stub in the RMI registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("AdmissionFrontEndServiceServer", stub);

        } catch (RemoteException e) {
            System.err.println("Server exception, failed to export AdminFrontEndService "+e.toString());
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (AlreadyBoundException e) {
            System.err.println("Server exception, service already bound AdminFrontEndServer "+e.toString());
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

}
