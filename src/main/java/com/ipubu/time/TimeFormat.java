package com.ipubu.time;

import java.util.List;

/**
 * @ClassName TimeFormat
 * @Description		格式化时间
 * @Author jzy
 */
public class TimeFormat {

	private String startTime;
	private String endTime;
	private List<String> times;
	
	public TimeFormat(String startTime, String endTime, List<String> times) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
	}

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the times
	 */
	public List<String> getTimes() {
		return times;
	}

	/**
	 * @param times the times to set
	 */
	public void setTimes(List<String> times) {
		this.times = times;
	}
}
