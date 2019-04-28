package com.ipubu.cic.chat.time2;

import java.util.Calendar;
import java.util.Date;

/**
 * @ClassName DateUtil
 * @Description	时间工具类
 * @Author jzy
 */
public class DateUtil extends CommonDateUtil{

	/** 是否是今天 */
	public static boolean isToday(final Date date) {
		return isTheDay(date, new Date());
	}
	
	/** 是否是指定日期 */
	public static boolean isTheDay(final Date date, final Date day) {
		return date.getTime() >= dayBegin(day).getTime() && date.getTime() <= dayEnd(day).getTime();
	}
	
	
	/**
	 * 获取指定时间的那天 00:00:00.000 的时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date dayBegin(final Date date) {
		return getSpecificHourInTheDay(date, 0);
	}
	
	
	/**
	 * 获取指定时间的那天 23:59:59.999 的时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date dayEnd(final Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c.getTime();
	}
}
