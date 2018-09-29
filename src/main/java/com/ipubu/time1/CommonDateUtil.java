package com.ipubu.time1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.ipubu.util.Log;

/**
 * @ClassName CommonDateUtil
 * @Description	日期工具类（来自公司公共项目）
 * @Author jzy
 */
public class CommonDateUtil {

	private static String defaultDatePattern = "yyyy-MM-dd";
	public static final long ONE_MINUTE_MILLISECOND = 60000L;
	public static final long ONE_HOUR_MILLISECOND = 3600000L;
	public static final long ONE_DAY_MILLISECOND = 86400000L;
	public static final long ONE_WEEK_MILLISECOND = 604800000L;
	public static final long ONE_MONTH_MILLISECOND = 2592000000L;
	public static final long ONE_YEAR_MILLISECOND = 31536000000L;
	/**
	 * 多种日期格式的数组
	 */
	private static final String[] SMART_DATE_FORMATS = { "yyyy-MM-dd HH:mm:ss", "yyyy.MM.dd HH:mm:ss",
			"yyyy-MM-dd HH:mm", "yyyy.MM.dd HH:mm", "yyyyMMddHHmmss", "yyyyMMddHHmm", "yyyy-MM-dd", "yyyy.MM.dd",
			"yyyyMMdd" };

	/**
	 * 含有所有生肖（zodiac）的一个数组
	 */
	public static final String[] zodiacArray = { "猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊" };

	/**
	 * 含有所有星座（constellation）的一个数组
	 */
	public static final String[] constellationArray = { "水瓶座", "双鱼座", "牡羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座",
			"天蝎座", "射手座", "魔羯座" };

	private static final int[] constellationEdgeDay = { 20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22 };

	public static String getDatePattern() {
		return defaultDatePattern;
	}
	
	/**
	 * 获取年份
	 * @param  传入一个日期
	 * @return
	 */
	public static int getYear(Date date) {
		return getCalendar(date).get(1);	// int get(int field) 此方法返回给定日历字段的值  1表示获取年份
	}

	/**
	 * 获取月份
	 * @param  传入一个日期
	 * @return
	 */
	public static int getMonth(Date date) {
		return getCalendar(date).get(2);	// int get(int field) 此方法返回给定日历字段的值  2表示获取月份
	}


	public static Date getCleanDay(Date day) {
		return getCleanDay(getCalendar(day));
	}

	/**
	 * 该方法主要就是将日历的时间设置为给定的日期 （Date）
	 * @param 	参数为：具体的一个日期
	 * @return	返回：一个日历对象
	 */
	public static Calendar getCalendar(Date day) {
		Calendar c = Calendar.getInstance();
		if (day != null)
			c.setTime(day);	// 方法设置日历的时间为给定的日期 Date   public final void setTime(Date date)
		return c;
		
		/**
		 * Calendar 的 month 从 0 开始，也就是全年 12 个月由 0 ~ 11 进行表示。
		*	而 Calendar.DAY_OF_WEEK 定义值如下，每周从 SUNDAY 开始，从 1 ~ 7：
		*	Calendar.SUNDAY = 1
		*	Calendar.MONDAY = 2
		*	Calendar.TUESDAY = 3
		*	Calendar.WEDNESDAY = 4
		*	Calendar.THURSDAY = 5
		*	Calendar.FRIDAY = 6
		*	Calendar.SATURDAY = 7
		 */
	}
}
