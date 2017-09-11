package com.yogesh.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Yogesh Thakare
 */
@JsonIgnoreProperties
public class Message {

    private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
