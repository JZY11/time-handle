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
	private static void getholidaytime(){
		holidayTimeMap = new HashMap<String, String>();
		for(Entry<String, HashSet<String>> str:holidayMap.entrySet()){
			for(String time:holidayMap.get(str.getKey())){
				holidayTimeMap.put(time.split("日")[0]+"日", str.getKey());
			}
		}
	}
	
	private static void getHoliday(int year){
		
		TimeUtils lauar = new TimeUtils(year, 1, 1);
		int size = holidays.length;
		while (size > 0) {
			String lh = lauar.getLunarHoliday();// 获取农历节日
			String h = lauar.getHoliday();// 获取公历节日
//			String s = lauar.getSoralTerm();// 获取节气
			HashSet<String> set =null;
			if (holidayMap.containsKey(lh)) {
				String value = sdf.format(lauar.getCalendar().getTime());
				if(holidayMap.get(lh) == null){
					set = new HashSet<String>();
				}else{
					set = holidayMap.get(lh);
				}
				set.add(value);
				holidayMap.put(lh, set);
				size--;
				
				if("春节".equals(lh)){
					if(holidayMap.get("过年") == null){
						set = new HashSet<String>();
					}else{
						set = holidayMap.get("过年");
					}
					set.add(value);
					holidayMap.put("过年", set);
				}
				if("端午".equals(lh)){
					if(holidayMap.get("端阳") == null){
						set = new HashSet<String>();
					}else{
						set = holidayMap.get("端阳");
					}
					set.add(value);
					holidayMap.put("端阳", set);
				}
				}
			
			if (holidayMap.containsKey(h)) {
				String value = sdf.format(lauar.getCalendar().getTime());
				if(holidayMap.get(h) == null){
					set = new HashSet<String>();
				}else{
					set = holidayMap.get(h);
				}
				
				if("元旦".equals(h)){
					if(holidayMap.get("新年") == null){
						set = new HashSet<String>();
					}else{
						set = holidayMap.get("新年");
					}
					set.add(value);
					holidayMap.put("新年", set);
				}
				set.add(value);
				holidayMap.put(h, set);
				size--;
			}

//			if (Hmap.containsKey(s)) {
//				String value = sdf.format(lauar.getCalendar().getTime());
//				Hmap.put(s, value);
//				size--;
//			}
			lauar.nextDay();}
		}
		
	// 节日转化为时间
	private static TimeFormat holidayDate(String holiday) {
		
		holiday = holiday.replaceAll("11期间", "国庆节");
		String date = null;
		String rlues = "((\\d+)年)|去年|前年|明年|后年|今年";
		Pattern p = Pattern.compile(rlues);
		Matcher m = p.matcher(holiday);
		Calendar cal = Calendar.getInstance();
		boolean result = m.find();
		if(result){
			if("去年".equals(m.group())){
				date = anysHoliday(holiday.replaceAll("去年", "").replaceAll("的", ""), cal.get(Calendar.YEAR) - 1);
			}else if("前年".equals(m.group())){
				date = anysHoliday(holiday.replaceAll("前年", "").replaceAll("的", ""), cal.get(Calendar.YEAR) - 2);
			}else if("明年".equals(m.group())){
				date = anysHoliday(holiday.replaceAll("明年", "").replaceAll("的", ""), cal.get(Calendar.YEAR) + 1);
			}else if("后年".equals(m.group())){
				date = anysHoliday(holiday.replaceAll("后年", "").replaceAll("的", ""), cal.get(Calendar.YEAR) + 2);
			}else if("今年".equals(m.group())){
				date = anysHoliday(holiday.replaceAll("今年", "").replaceAll("的", ""), cal.get(Calendar.YEAR));
			}else{
				date = anysHoliday(holiday.replace(m.group(), "").replaceAll("的", ""), Integer.valueOf(m.group().replace("年", "")));
			}
		}else{
			date = anysHoliday(holiday);
		}

		TimeFormat tf = null;
		if (date != null)
			tf = new TimeFormat(date, date, null);
		return tf;
	}
	//指明了是哪一年的节日
	private static String anysHoliday(String holiday,int year){
		if(holiday==null||"".equals(holiday)) return null;
		String date = null;
		if (!holidayMap.containsKey(holiday) && holiday.contains("节")) {
			holiday = holiday.replace("节", "");
		}
		if(holidayMap.containsKey(holiday)){
		for(String time:holidayMap.get(holiday)){
			if(time.startsWith(String.valueOf(year)))
			date = time;
		}}
		return date;
	}
}
