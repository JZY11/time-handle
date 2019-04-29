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
	
	/**
	 * 获得指定时间那天的某个小时（24小时制）的整点时间
	 * 
	 * @param date
	 * @param hourIn24
	 * @return
	 */
	public static Date getSpecificHourInTheDay(final Date date, int hourIn24) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, hourIn24);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	
	
	/**
	 * 对时间中的分钟向上取整
	 * 
	 * @param date
	 * @param round
	 *            取整的值
	 * @return
	 */
	public static Date roundMin(final Date date, int round) {
		if (round > 60 || round < 0) {
			round = 0;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int min = c.get(Calendar.MINUTE);
		if ((min % round) >= (round / 2)) {
			min = round * (min / (round + 1));
		} else {
			min = round * (min / round);
		}
		c.set(Calendar.MINUTE, min);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}
	
	/**
	 * 得到本周周一
	 */
	public static Date getFirstDayOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day_of_week = calendar.get(calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0) {
			day_of_week = 7;
		}
		calendar.add(calendar.DATE, -day_of_week + 1);
		return calendar.getTime();
	}
}
