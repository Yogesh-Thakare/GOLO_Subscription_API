package com.yogesh.exception;

/**
 * @author Yogesh Thakare
 */
public class OverviewNotAvailableException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public OverviewNotAvailableException(String message) 
	{
		super(message);
	}
}
