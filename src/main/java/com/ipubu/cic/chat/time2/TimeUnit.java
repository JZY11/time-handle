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
	
	
	/**
	 * 时间表达式单元构造方法
	 * 该方法作为时间表达式单元的入口，将时间表达式字符串传入
	 * @param exp_time 时间表达式字符串
	 * @param tm
	 */
	public TimeUnit (String exp_time, TimeNormalizer tm) {
		time_Expression = exp_time;
		normalizer = tm;
		Time_Normalization();
		
	}
	
	/**
     *时间表达式规范化的入口
     *
     *时间表达式识别后，通过此入口进入规范化阶段，
     *具体识别每个字段的值
     * 
     */
	public void Time_Normalization() {
		// TODO
	}
}
