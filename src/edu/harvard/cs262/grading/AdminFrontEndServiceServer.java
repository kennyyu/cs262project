package edu.harvard.cs262.grading;

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

}
