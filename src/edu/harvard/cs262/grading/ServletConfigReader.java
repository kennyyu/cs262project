package edu.harvard.cs262.grading;

import javax.servlet.ServletContext;

public class ServletConfigReader extends ConfigReaderImpl {
	
	private static final String DEFAULT_CONFIG = "/WEB-INF/lib/config/services.config";
	
	public ServletConfigReader(ServletContext servletContext) {
		super(servletContext.getRealPath(DEFAULT_CONFIG));
	}

}
