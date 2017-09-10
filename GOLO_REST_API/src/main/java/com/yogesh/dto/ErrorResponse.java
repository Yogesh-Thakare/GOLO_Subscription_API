package com.yogesh.dto;

/**
 * @author Yogesh Thakare
 */
public class ErrorResponse extends Response {

	public ErrorCode errorCode;
	public String message;

	public ErrorResponse(ErrorCode errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
		this.success = false;
	}

	public enum ErrorCode {

		UNKNOWN_ERROR,
		
	}

}
