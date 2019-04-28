package com.ipubu.cic.chat.time2;

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
}
