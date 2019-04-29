package com.ipubu.cic.chat.time2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.ipubu.util.Log;

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
	
	/**
	 * 处理相对日期的相对运算(当前只支持周、日)
	 */
	public static Date getRelativeTime(final Date date, final int calUnit, final int relative){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(calUnit, relative);
		return calendar.getTime();
	}
	
	/**
	 * 默认的时间格式化
	 */
	public static String formatDateDefault(Date date){
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return DateUtil.formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 检验日期格式字符串是否符合format结构
	 * 
	 * 主要的逻辑是先把字符parse为该format的Date对象，再将Date对象按照format转化为String。如果此时的String与初始时的字符串一致的话，则日期符合format。
	 * 
	 * 之所以用来回双重逻辑校验，是因为假如把一个非法字符串parse为某format格式的Date对象是不一定会报错的。比如 2015-06-29 13:12:121，明显不符合yyyy-MM-dd
	 * HH:mm:ss，但是可以正常parse成Date对象，但时间变为了2015-06-29 13:14:01。增加多一重校验则可检测出这个问题。
	 * 
	 * @param strDateTime
     * @param format 日期格式
     * @return boolean
	 */
	public static boolean checkDateFormatAndValite(String strDateTime, String format){
		if (strDateTime == null || strDateTime.length() == 0) {
			return false;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		try {
			Date date = dateFormat.parse(strDateTime);
			String str = dateFormat.format(date);
			 Log.logger.info("func<checkDateFormatAndValite> strDateTime<" + strDateTime + "> format<" + format +
                     "> str<" + str + ">");
			 
			 if (str.equals(strDateTime)) {
				return true;
			} else {
				return false;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	private final static List<Integer> TIMEUNITS = new ArrayList<Integer>();
	
	static {
		TIMEUNITS.add(Calendar.YEAR);
		TIMEUNITS.add(Calendar.MONTH);
		TIMEUNITS.add(Calendar.DATE);
		TIMEUNITS.add(Calendar.HOUR);
		TIMEUNITS.add(Calendar.MINUTE);
		TIMEUNITS.add(Calendar.SECOND);
	}
}
