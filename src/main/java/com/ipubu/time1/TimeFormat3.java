package com.ipubu.time1;

import java.util.List;

/**
 * @ClassName TimeFormat3
 * @Description
 * @Author jzy
 */
public class TimeFormat3 {

	private List<String> time;
	private String timeExp;
	public TimeFormat3(List<String> time, String timeExp) {
		super();
		this.time = time;
		this.timeExp = timeExp;
	}
	/**
	 * @return the time
	 */
	public List<String> getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(List<String> time) {
		this.time = time;
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
	
	public String toString() {
		String str="";
		for(String s:time){
			str=str+"\r"+s;
		}
		str=str+"-"+timeExp;
		return str;
	}
}
