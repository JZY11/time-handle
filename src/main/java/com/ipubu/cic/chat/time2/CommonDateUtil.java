package com.ipubu.cic.chat.time2;

import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * @ClassName CommonDateUtil
 * @Description
 * @Author jzy
 */
public class CommonDateUtil {

	private static String defaultDatePattern = "yyyy_MM_dd"; // 年_月_日
	public static final long ONE_MINUTE_MILLISECOND = 60000L;
	public static final long ONE_HOUR_MILLISECOND = 3600000L;
	public static final long ONE_DAY_MILLISECOND = 86400000L;
	public static final long ONE_WEEK_MILLISECOND = 604800000L;
	public static final long ONE_MONTH_MILLISECOND = 2592000000L;
	public static final long ONE_YEAR_MILLISECOND = 31536000000L;
	private static final String[] SMART_DATE_FORMATS = {"yyyy-MM-dd HH:mm:ss", "yyyy.MM.dd HH:mm:ss"
		, "yyyy-MM-dd HH:mm", "yyyy.MM.dd HH:mm", "yyyyMMddHHmmss", "yyyyMMddHHmm", "yyyy-MM-dd", "yyyy.MM.dd"
		, "yyyyMMdd" }; // 默认的若干种时间的格式
	
	public static final String[] ZODIACARRAY = {"猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊"};
	public static final String[] constellationArray = { "水瓶座", "双鱼座", "牡羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座",
		"天蝎座", "射手座", "魔羯座" };

	private static final int[] constellationEdgeDay = { 20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22 };

	public static String getDatePattern() {
		return defaultDatePattern;
	}

	public static int getYear(Date date) {
		return getCalendar(date).get(1); // Returns the value of the given calendar field
	}
	
	public static int getMonth(Date date) {
		return getCalendar(date).get(2);
	}

	public static int getDay(Date date) {
		return getCalendar(date).get(5);
	}
	
	public static int getWeek(Date date) {
		return getCalendar(date).get(7);
	}

	public static int getWeekOfFirstDayOfMonth(Date date) {
		return getWeek(getFirstDayOfMonth(date));
	}

	public static int getWeekOfLastDayOfMonth(Date date) {
		return getWeek(getLastDayOfMonth(date));
	}
	
	public static Calendar getCalendar(Date day) {
		Calendar c = Calendar.getInstance(); // getInstance方法返回一个Calendar对象(该对象为Calendar的子类对象),其日历字段已由当前日期和时间初始化了
		if (day != null)
			c.setTime(day); // 通过给定的时间来设置当前Calendar对象实例的时间
		return c;
	}
	
	public static Date getFirstDayOfMonth(Date date) {
		return getFirstCleanDay(5, date);
	}
	
	private static Date getFirstCleanDay(int datePart, Date date) {
		Calendar c = Calendar.getInstance();
		if (date != null)
			c.setTime(date);
		c.set(datePart, 1);
		return getCleanDay(c);
	}
	
	private static Date getCleanDay(Calendar c) {
		c.set(11, 0);
		c.clear(12);
		c.clear(13);
		c.clear(14);
		return c.getTime();
	}
	
	public static Date getLastDayOfMonth(Date date) {
		Calendar c = getCalendar(getFirstDayOfMonth(date));
		c.add(2, 1);
		c.add(5, -1);
		return getCleanDay(c);
	}
	
	public static Date getFirstDayOfSeason(Date date) {
		Date d = getFirstDayOfMonth(date);
		int delta = getMonth(d) % 3;
		if (delta > 0)
			d = getDateAfterMonths(d, -delta);
		return d;
	}
	
	public static Date getDateAfterMonths(Date start, int months) {
		return add(2, months, start);
	}
	
	private static Date add(int datePart, int detal, Date date) {
		Calendar c = Calendar.getInstance();
		if (date != null)
			c.setTime(date);
		c.add(datePart, detal);
		return c.getTime();
	}


}
