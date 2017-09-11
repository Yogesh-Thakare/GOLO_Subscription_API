package com.yogesh.dto;

/**
 * @author Yogesh Thakare
 */
public class SuccessResponseDTO extends ResponseDTO{

	public String accountIdentifier;

	public SuccessResponseDTO() {
		this.success = true;
	}

	public SuccessResponseDTO(String accountIdentifier) {
		this.accountIdentifier = accountIdentifier;
		this.success = true;
	}

}
