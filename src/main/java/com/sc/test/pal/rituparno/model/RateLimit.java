/**
 * 
 */
package com.sc.test.pal.rituparno.model;

/**
 * @author rituparno.pal@gmail.com
 *
 */
public class RateLimit {

	private String apiKey;
	
	private int limitCount;
	
	private String timePeriod;

	/**
	 * 
	 */
	public RateLimit() {
	
	}

	/**
	 * @return the apiKey
	 */
	public String getApiKey() {
		return apiKey;
	}

	/**
	 * @param apiKey the apiKey to set
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	
	/**
	 * @return the limitCount
	 */
	public int getLimitCount() {
		return limitCount;
	}

	/**
	 * @param limitCount the limitCount to set
	 */
	public void setLimitCount(int limitCount) {
		this.limitCount = limitCount;
	}

	/**
	 * @return the timePeriod
	 */
	public String getTimePeriod() {
		return timePeriod;
	}

	/**
	 * @param timePeriod the timePeriod to set
	 */
	public void setTimePeriod(String timePeriod) {
		this.timePeriod = timePeriod;
	}
	
	
}
