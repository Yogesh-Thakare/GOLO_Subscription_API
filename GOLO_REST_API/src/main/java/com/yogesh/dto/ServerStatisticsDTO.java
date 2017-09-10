package com.yogesh.dto;

/**
 * @author Yogesh Thakare
 */
public class ServerStatisticsDTO {
	
		private String startedSince;
		private String time;
	    private String state;

	    public String getState() {
	        return state;
	    }

	    public void setState(String status) {
	        this.state = status;
	    }

		public String getStartedSince() {
			return startedSince;
		}

		public void setStartedSince(String startedSince) {
			this.startedSince = startedSince;
		}

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}


	   

}
