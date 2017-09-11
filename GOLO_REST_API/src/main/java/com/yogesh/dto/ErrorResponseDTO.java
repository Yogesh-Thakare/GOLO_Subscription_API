package com.yogesh.dto;

/**
 * @author Yogesh Thakare
 */
public class ErrorResponseDTO extends ResponseDTO 
{
	public ErrorCode errorCode;
	public String message;

	public ErrorResponseDTO(ErrorCode errorCode, String message) 
	{
		this.errorCode = errorCode;
		this.message = message;
		this.success = false;
	}

	public enum ErrorCode 
	{
		INVALID_COMMAND,
		UNKNOWN_ERROR,
	}
}
