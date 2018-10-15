package com.ipubu.time1;

/**
 * @ClassName TimeFormat2
 * @Description
 * @Author jzy
 */
public class TimeFormat2 {

	private String startTime;	// 起始时间
	private String endTime;		// 终止时间
	private String timeExp;
	
	public TimeFormat2(String startTime, String endTime, String timeExp) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.timeExp = timeExp;
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
	 * @return the timeExp
	 */
	public String getTimeExp() {
		return timeExp;
	}
	/**
	 * @param timeExp the timeExp to set
	 */
	public void setTimeExp(String timeExp) {
		this.timeExp = timeExp;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[startTime="+ startTime + ",endTime=" + endTime
				+ "]" ;
	}
	
	public String toString2() {
		return "[startTime="+ startTime + ",endTime=" + endTime
				+ "]" ;
	}
}
