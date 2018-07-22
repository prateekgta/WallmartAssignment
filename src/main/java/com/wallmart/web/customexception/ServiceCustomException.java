package com.wallmart.web.customexception;

import org.apache.log4j.Logger;

/*
 * Exception class used by service layer.
 */
public class ServiceCustomException extends RuntimeException{

	
	private static final long serialVersionUID = 984585597585723282L;
	final static Logger logger = Logger.getLogger(DataBaseCustomException.class);

	public ServiceCustomException(String message) {
	        super(message);
	        logger.error("Error Stack from Service Custom Exception" + message);
	    }
}
