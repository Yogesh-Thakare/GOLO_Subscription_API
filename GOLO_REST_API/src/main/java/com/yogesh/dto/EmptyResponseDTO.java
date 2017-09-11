package com.yogesh.dto;

/**
 * @author Yogesh Thakare
 */
public class EmptyResponseDTO extends ServerStatisticsSetDTO{
	
	public ErrorCode errorCode;
	public String message;

	public EmptyResponseDTO(ErrorCode errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}

	public enum ErrorCode 
	{
		STATS_NOT_AVAILABLE
	}

}
