package com.ipubu.time1;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.ipubu.util.Log;

/**
 * @ClassName DateUtil
 * @Description	日期处理工具类
 * @Author jzy
 */
public class DateUtil extends CommonDateUtil{

	/**
	 * 是否是今天
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isToday(final Date date) {
		return isTheDay(date, new Date());
	}

	/**
	 * 是否是指定日期
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static boolean isTheDay(final Date date, final Date day) {
		return date.getTime() >= dayBegin(day).getTime() && date.getTime() <= dayEnd(day).getTime();
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
	 * 得到本周周一
	 *
	 * @return Date
	 */
	public static Date getFirstDayOfWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		c.add(Calendar.DATE, -day_of_week + 1);
		return c.getTime();
	}
}
