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
}
