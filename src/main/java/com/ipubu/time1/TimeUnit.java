package com.ipubu.time1;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ipubu.util.Log;

/**
 * @ClassName TimeUnit
 * @Description	时间语句分析
 * @Author jzy
 */
public class TimeUnit {

	/**
	 * 目标字符串
	 */
	public String Time_Expression=null;
	public String Time_Norm="";
	public int[] time_full;
	public int[] time_origin;
	private Date time;
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
	 * @param n
	 */
	
	public TimeUnit(String exp_time, TimeNormalizer n)
	{
		Time_Expression=exp_time;
		normalizer = n;
		Time_Normalization();
	}
	
	/**
	 * 时间表达式单元构造方法
     * 该方法作为时间表达式单元的入口，将时间表达式字符串传入
     * @param exp_time 时间表达式字符串 
	 * @param n
	 * @param contextTp 上下文时间
	 */
	
	public TimeUnit(String exp_time, TimeNormalizer n, TimePoint contextTp)
	{
		Time_Expression=exp_time;
		normalizer = n;
		_tp_origin = contextTp;
		Time_Normalization();
	}
	
	/**
	 * return the accurate time object
	 */
    public Date getTime() {
        return time;
    }
    
}
