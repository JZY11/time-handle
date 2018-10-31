/**
 * 
 */
package com.ipubu.holiday;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ipubu.time.TimeFormat;
import com.ipubu.time.TimeTerm;
import com.ipubu.time1.NormTime;
import com.ipubu.util.CommonDateUtils;


/**
 * @ClassName StandardTime
 * @Description
 * @Author jzy
 */
public class StandardTime {

	private static HashMap<String, HashSet<String>> holidayMap;//去年节日
	private static HashMap<String, String> holidayTimeMap;//节日
	private static HashMap<String,String> faDingHoliday = new HashMap<String, String>();//节日
	private static String[] holidays = TimeTerm.HOLIDAY;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH点mm分ss秒");// 时间格式
	private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日");// 时间格式
	static{
		holidayMap = createMap();
		Arrays.sort(holidays);
		initHmap();
		
	}
	public StandardTime() {
//		holidayMapJ = createMap();
//		holidayMapRev = createMap();
//		holidayMapFor = createMap();
//		Arrays.sort(holidays);
//		initHmap();// 初始化日期
	}

	/**
	 * 初始化节日日期对应表
	 */
	private static void initHmap() {
		Calendar cal = Calendar.getInstance();
	
		int year = cal.get(Calendar.YEAR);
		//所有节日
		for(int i= -10; i<11;i++){
			getHoliday(year + i);
			_24SolarTerms.solarTermToString(year + i,holidayMap);//24节气
		}
		
		getholidaytime();
		initfadingHoli();
	}
}
