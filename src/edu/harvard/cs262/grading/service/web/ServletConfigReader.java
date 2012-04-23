package edu.harvard.cs262.grading.service.web;

import javax.servlet.ServletContext;

import edu.harvard.cs262.grading.service.ConfigReaderImpl;

public class ServletConfigReader extends ConfigReaderImpl {
	
	private static final String DEFAULT_CONFIG = "/WEB-INF/lib/config/services.config";
	
	public ServletConfigReader(ServletContext servletContext) {
		super(servletContext.getRealPath(DEFAULT_CONFIG));
	}

}
