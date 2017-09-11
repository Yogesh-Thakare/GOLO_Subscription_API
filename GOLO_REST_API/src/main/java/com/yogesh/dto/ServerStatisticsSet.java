package com.yogesh.dto;

import java.util.List;

/**
 * @author Yogesh Thakare
 */
public class ServerStatisticsSet {
	
	private List<ServerStatisticsDTO> overview;

	public List<ServerStatisticsDTO> getOverview() {
		return overview;
	}

	public void setOverview(List<ServerStatisticsDTO> overview) {
		this.overview = overview;
	}

}
