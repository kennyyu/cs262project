package edu.harvard.cs262.grading.client.web;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminAddAssignmentServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2895803517594443809L;

	public void lookupServices() {

	}

	public void init(ServletConfig config) throws ServletException {

		super.init(config);

		lookupServices();

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// TODO: implement

	}

}
