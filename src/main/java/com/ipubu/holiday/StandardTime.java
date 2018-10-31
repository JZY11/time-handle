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
	
	//未明确是哪一年的节日
	private static String anysHoliday(String holiday){
		if(holiday==null||"".equals(holiday)) return null;
		String date = null;
		Calendar cal = Calendar.getInstance();
		
		date = anysHoliday(holiday, cal.get(Calendar.YEAR));
		if(date !=null){
		try {
			Date time = new SimpleDateFormat("yyyy年MM月dd日")
					.parse(date);
			
			cal.setTime(time);
			cal.add(cal.MINUTE, 3);
			if (Calendar.getInstance().getTime().after(cal.getTime())) {
				date = anysHoliday(holiday, cal.get(Calendar.YEAR) + 1);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}

		return date;
	}

	// 文字转换为标准时间
	/**
	 * @param unit
	 *            text修饰的时间单位,如"最近几天",单位是"天".支持年月日时分.与Calendar类中的单位标识已知
	 * @param position
	 *            时间轴区间标识,0标识当前,1标识未来,-1标识过去
	 * @param movelength
	 *            偏移的长度
	 * @return
	 */
	private TimeFormat moveTime(int unit, int position, int movelength) {
		// 时间推移的长度
		TimeFormat tf = null;
		Calendar cal = Calendar.getInstance();
		String nowTime = sdf.format(cal.getTime());
		cal.add(unit, (movelength - 1) * position);
		String moveTime = sdf.format(cal.getTime());
		if (position == -1)
			tf = new TimeFormat(moveTime, nowTime, null);
		else
			tf = new TimeFormat(nowTime, moveTime, null);
		return tf;
	}

	// 主要函数
	public  static TimeFormat normTime(String text) {
		TimeFormat tf = null;
		try {
			tf = holidayDate(text);
			if (tf == null)
				tf = weekTime(text);
			if (tf == null)
				tf = day(text);
			if (tf == null)
				tf = moveTime(text);
		} catch (Exception e) {
			e.getMessage();
		}
		return tf;
	}

	private static TimeFormat day(String text) {
		TimeFormat tf = null;
		text = text.trim();
		String[] h = { "51", "61", "71", "81", "91", "11期间" };
		for (int i = 0; i < h.length; i++) {
			String e = h[i];
			if (text.equals(e)) {
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.MONTH, i + 4);
				cal.set(Calendar.DAY_OF_MONTH, 1);
				String moveTime = sdf.format(cal.getTime());
				tf = new TimeFormat(moveTime, moveTime, null);
			}
		}
		return tf;
	}
	private static Date transTime(String text) {
 		Date end = null;
		//小时
// 		if(text.contains("1个小时")||text.contains("1个半小时"))
// 		{
// 			text = text.replace("1个", "");
// 		}
// 		
 		String rules = "([0123456789半个]+)小时(之前|以前|前|之后|后|以后|钱)?";
 		Pattern p =  Pattern.compile(rules);
 		Matcher m = p.matcher(text);
 		boolean result = m.find();
 		if(result){
 			double value = getNum(m.group(1));
 			if(m.group().contains("前")||m.group().contains("钱"))
 				end = CommonDateUtils.getAfterDate(end,value,"小时");
 			else
 				end = CommonDateUtils.getBeforeDate(null,value,"小时");
 		}
		
		//分钟
		rules = "([0123456789]+)(分钟 |分)(之前|以前|前|之后|后|以后)?";
		p =  Pattern.compile(rules);
		m = p.matcher(text);
		if(m.find()){
			double value = getNum(m.group(1));
			if(m.group().contains("前"))
			end = CommonDateUtils.getAfterDate(end,value,"分钟");
			else
			end = CommonDateUtils.getBeforeDate(end,value,"分钟");
		}
		//秒
		rules = "(\\d+)秒(之前|以前|前|之后|后|以后)?";
		p =  Pattern.compile(rules);
		m = p.matcher(text);
		if(m.find()){
			if(m.group().contains("前"))
			end = CommonDateUtils.getAfterDate(end,(double)Integer.valueOf(m.group(1)),
					"秒");
			else
				
				end = CommonDateUtils.getBeforeDate(end,(double)Integer.valueOf(m.group(1)),
						"秒");
		}
		
		return end;
	}
	private static double getNum(String mp){
		double value = -1.0;
		if(mp.contains("半")){
			if(!"".equals(mp.replaceAll("个", "").replaceAll("半", "")))
				value = Integer.valueOf(mp.replaceAll("个", "").replaceAll("半", "")) + 0.5;
			else
				value = 0.5;
		}else
			value = Integer.valueOf(mp.replace("个", ""));
		
		return value;
	}

	private static TimeFormat moveTime(String text) {
		TimeFormat tf = null;
		Date start = new Date(System.currentTimeMillis());
		String starttime = null;
		String endtime = null;
		if ("现在".equals(text)||"当前".equals(text)) {
			starttime = sdf.format(start);
			endtime = sdf.format(start);
		} else {
			Date end = transTime(text);
			starttime = sdf.format(start);
			endtime = sdf.format(end);
		}
		tf = new TimeFormat(starttime, endtime, null);
		return tf;
	}	
	private static TimeFormat weekTime(String text) {
		TimeFormat tf = null;
		// 处理 感恩节、父亲节、母亲节等这类的节日
		if (text.trim().contains("节")) {
			String[] s = { "感恩节", "父亲节", "母亲节" };
			for (int j = 0; j < s.length; j++) {
				Calendar cal = Calendar.getInstance();
				if (text.equals(s[0])) {
					cal.set(Calendar.MONTH, 10);
					cal.set(Calendar.WEEK_OF_MONTH, 4);
					cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
				} else if (text.equals(s[1])) {
					cal.set(Calendar.MONTH, 5);
					cal.set(Calendar.WEEK_OF_MONTH, 3);
					cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
				} else if (text.equals(s[2])) {
					cal.set(Calendar.MONTH, 4);
					cal.set(Calendar.WEEK_OF_MONTH, 2);
					cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
				}
				String moveTime = sdf.format(cal.getTime());
				tf = new TimeFormat(moveTime, moveTime, null);

			}
		}
		return tf;
	}
}
