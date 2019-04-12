package com.ipubu.cic.chat;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ipubu.time1.TimeNormalizer;

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
	public TimePoint _tp = new TimePoint();
	public TimePoint _tp_origin = new TimePoint();
	
	
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
	
	
	
	
	/**
     *时间表达式规范化的入口
     *
     *时间表达式识别后，通过此入口进入规范化阶段，
     *具体识别每个字段的值
     * 
     */
	public void Time_Normalization() {
		norm_setyear();
	}


	/**
	 * 年 - 规范化方法
	 * @param 
	 * @return
	 */
	public void norm_setyear() {
		/** 加入只有两位数来表示年份 */
		String rule = "[0-9]{2}(?=年)"; // 表示两个数字后边紧跟着的就是"年"
		Pattern pattern = Pattern.compile(rule);
		Matcher matcher = pattern.matcher(Time_Expression);
		
		if (matcher.find()) {
			_tp.tunit[0] = Integer.parseInt(matcher.group()); // 将string转化为int
			if (_tp.tunit[0] >= 0 && _tp.tunit[0] < 100) {
				if (_tp.tunit[0] < 30) { // 30以下表示2000年以后的年份
					_tp.tunit[0] += 2000;
				} else { // 否则表示1900年以后的年份
					_tp.tunit[0] += 1900;
				}
			}
		}
	}
}
