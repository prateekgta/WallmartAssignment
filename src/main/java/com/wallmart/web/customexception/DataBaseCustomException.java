package com.wallmart.web.customexception;

import org.apache.log4j.Logger;

/*
 * Exception class used by database layer.
 */
public class DataBaseCustomException extends RuntimeException{

	private static final long serialVersionUID = 1443514818044211003L;
	final static Logger logger = Logger.getLogger(DataBaseCustomException.class);

	public DataBaseCustomException(Throwable e) {
		super("DataBase is not available");
		logger.error("Error Stack from DataBaseCustomException", e);
	}

}
