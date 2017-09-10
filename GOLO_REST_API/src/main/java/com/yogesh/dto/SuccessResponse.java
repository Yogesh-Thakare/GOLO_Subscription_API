package com.yogesh.dto;

/**
 * @author Yogesh Thakare
 */
public class SuccessResponse extends Response{

	public String accountIdentifier;

	public SuccessResponse() {
		this.success = true;
	}

	public SuccessResponse(String accountIdentifier) {
		this.accountIdentifier = accountIdentifier;
		this.success = true;
	}

}
