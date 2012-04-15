package edu.harvard.cs262.grading;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 4/12/12
 * Time: 4:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class AdminFrontEndServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 01L;
	String registry;    // path to RMI registry

    public void init(ServletConfig config) throws ServletException {

        super.init(config);

        // locate RMI registry
        registry = config.getInitParameter("registry");

        // default to localhost
        if(registry == null) registry = "//localhost";

    }

}
