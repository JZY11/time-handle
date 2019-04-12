package com.ipubu.cic.chat;

import java.util.Date;

import com.ipubu.time1.TimeNormalizer;
import com.ipubu.time1.TimePoint;

/**
 * <p>
 * 时间语句分析
 * <p>
 * @Description
 * @Author jzy
 */
public class TimeUnit {

	/**
	 * 目标字符串
	 */
	public String Time_Expression = null;
	public String time_Norm = "";
	public int[] time_full;
	public int[] time_origin;
	
	private Date date;
	private Boolean isAllDayTime = true;
	private Boolean isTimePoint = false;
	private Boolean isDate = false;
	private boolean isFirstTimeSolveContext = true;
	
	TimeNormalizer normalizer = null;
	public TimePoint _tp=new TimePoint();
	public TimePoint _tp_origin=new TimePoint();
}
