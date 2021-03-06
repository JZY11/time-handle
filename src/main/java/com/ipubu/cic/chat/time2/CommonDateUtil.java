package com.ipubu.cic.chat.time2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.ipubu.time1.NormTime;
import com.ipubu.util.Log;

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
	
	public static Date getFirstDayOfMonth() {
		return getFirstDayOfMonth(null);
	}
	
	private static Date getFirstCleanDay(int datePart, Date date) {
		Calendar c = Calendar.getInstance();
		if (date != null)
			c.setTime(date);
		c.set(datePart, 1);
		return getCleanDay(c);
	}
	
	public static Date getCleanDay(Date day) {
		return getCleanDay(getCalendar(day));
	}
	
	private static Date getCleanDay(Calendar c) {
		c.set(11, 0);
		c.clear(12);
		c.clear(13);
		c.clear(14);
		return c.getTime();
	}
	
	public static String formatDate(Date date, String format) {
		if (date == null)
			date = new Date();
		if (format == null)
			format = getDatePattern();
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}
	
	public static String formatDate(Date date) {
		long offset = System.currentTimeMillis() - date.getTime();
		String pos = "前";
		if (offset < 0L) {
			pos = "后";
			offset = -offset;
		}
		if (offset >= 31536000000L) {
			return formatDate(date, getDatePattern());
		}
		if (offset >= 5184000000L) {
			return ((offset + 1296000000L) / 2592000000L) + "个月" + pos;
		}
		if (offset > 604800000L) {
			return ((offset + 302400000L) / 604800000L) + "周" + pos;
		}
		if (offset > 86400000L) {
			return ((offset + 43200000L) / 86400000L) + "天" + pos;
		}
		if (offset > 3600000L) {
			return ((offset + 1800000L) / 3600000L) + "小时" + pos;
		}
		if (offset > 60000L) {
			return ((offset + 30000L) / 60000L) + "分钟" + pos;
		}
		return (offset / 1000L) + "秒" + pos;
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
	
	public static Date getFirstDayOfSeason() {
		return getFirstDayOfMonth(null);
	}
	
	public static Date getFirstDayOfYear(Date date) { // 获取一年之中的第一天
		return makeDate(getYear(date), 1, 1);
	}
	
	public static Date makeDate(int year, int month, int day) {
		Calendar c = Calendar.getInstance();
		getCleanDay(c);
		c.set(1, year);
		c.set(2, month - 1);
		c.set(5, day);
		return c.getTime();
	}
	
	public static Date getFirstDayOfYear() {
		return getFirstDayOfYear(new Date());
	}
	
	public static Date getDateAfterWeeks(Date start, int weeks) {
		return getDateAfterMs(start, weeks * 604800000L);
	}
	
	public static Date getDateAfterMs(Date start, long ms) {
		return new Date(start.getTime() + ms);
	}
	
	public static Date getDateAfterYears(Date start, int years) {
		return add(1, years, start);
	}
	
	public static Date getDateAfterDays(Date start, int days) {
		return getDateAfterMs(start, days * 86400000L);
	}
	
	public static long getPeriodNum(Date start, Date end, long msPeriod) {
		return (getIntervalMs(start, end) / msPeriod);
	}
	
	public static long getIntervalMs(Date start, Date end) {
		return (end.getTime() - start.getTime());
	}
	
	public static int getIntervalDays(Date start, Date end) {
		return (int) getPeriodNum(start, end, 86400000L);
	}
	
	public static int getIntervalWeeks(Date start, Date end) {
		return (int) getPeriodNum(start, end, 604800000L);
	}
	
	public static boolean before(Date base, Date date) {
		return ((date.before(base)) || (date.equals(base)));
	}

	public static boolean after(Date base, Date date) {
		return ((date.after(base)) || (date.equals(base)));
	}
	
	public static Date max(Date date1, Date date2) {
		if (date1.getTime() > date2.getTime()) {
			return date1;
		}
		return date2;
	}

	public static Date min(Date date1, Date date2) {
		if (date1.getTime() < date2.getTime()) {
			return date1;
		}
		return date2;
	}

	public static boolean inPeriod(Date start, Date end, Date date) {
		return ((((end.after(date)) || (end.equals(date)))) && (((start.before(date)) || (start.equals(date)))));
	}
	
	public static String date2Zodica(Date time) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		return year2Zodica(c.get(1));
	}
	
	public static String year2Zodica(int year) {
		return ZODIACARRAY[(year % 12)];
	}

	public static String date2Constellation(Date time) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		int month = c.get(2);
		int day = c.get(5);
		if (day < constellationEdgeDay[month]) {
			--month;
		}
		if (month >= 0) {
			return constellationArray[month];
		}

		return constellationArray[11];
	}
	
	public static String year2Zodica(String timeExp,String domain) {
		 List<String> tlist= NormTime.getNormTime(timeExp,domain);
		 
		 if(tlist.size()!=0){
			String time = tlist.get(0).split(",")[1];
			String time2 = time.split("=")[1].split(" ")[0];
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date;
			int year = -1;
			try {
				date = formatter.parse(time2);
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				year = cal.get(cal.YEAR);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return ZODIACARRAY[(year % 12)]+" "+year;
		}else{
			return null;
		}
	}
	
	public static void main(String[] args) {
		Log.logger.info(year2Zodica(1973));
		Log.logger.info(date2Zodica(new Date()));
		Log.logger.info(date2Constellation(makeDate(1973, 5, 12)));
		Log.logger.info(Calendar.getInstance() == Calendar.getInstance());
		Log.logger.info(getCleanDay(new Date()));
		Log.logger.info(new Date());
		Calendar c = Calendar.getInstance();
		c.set(5, 1);
		
		Log.logger.info(getFirstDayOfMonth());
		Log.logger.info(getLastDayOfMonth(makeDate(1996, 2, 1)));

		Log.logger.info(formatDate(makeDate(2009, 5, 1)));
		Log.logger.info(formatDate(makeDate(2010, 5, 1)));
		Log.logger.info(formatDate(makeDate(2010, 12, 21)));
		Log.logger.info(before(makeDate(2009, 5, 1), new Date()));
		Log.logger.info(after(makeDate(2009, 5, 1), new Date()));
		Log.logger.info(inPeriod(makeDate(2009, 11, 24), makeDate(2009, 11, 30), makeDate(2009, 11, 25)));
	}

}
