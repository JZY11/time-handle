package com.ipubu.cic.chat.time2;

import java.util.Date;

import com.ipubu.cic.chat.TimePoint;
import com.ipubu.time1.TimeNormalizer;

/**
 * 时间语句分析
 * @ClassName TimeUnit
 * @Description
 * @Author jzy
 */
public class TimeUnit {
	
	/** 目标字符串 */
	public String time_Expression;
	public String Time_Norm;
	public int[] time_full;
	public int[] time_original;
	public Date date;
	private Boolean isAllDayTime = true;
	private Boolean isTimePoint = false;
	private Boolean isDate = false;
	private boolean isFirstTimeSolveContext = true;
	
	TimeNormalizer normalizer = null;
	public TimePoint _tp=new TimePoint();
	public TimePoint _tp_origin=new TimePoint();
}
