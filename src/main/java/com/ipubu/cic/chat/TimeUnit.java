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
	
	
	/**
	 * 时间表达式单元构造方法
	 * 该方法作为时间表达式单元的入口，将时间表达式字符串传入
	 * @param exp_time 时间表达式字符串
	 * @param n TimeNormalizer(定时器)
	 */
	public TimeUnit(String exp_time, TimeNormalizer n) {
		super();
		Time_Expression = exp_time;
		normalizer = n;
		Time_Normalization();
	}
}
