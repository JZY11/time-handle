package com.ipubu.time1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ipubu.grammar.util.Constants;
import com.ipubu.time.StringPreHandlingModule;
import com.ipubu.util.FileUtil;
import com.ipubu.util.Log;
import com.ipubu.util.StringUtil;
import com.ipubu.util.TimeAnalys;

/**
 * @ClassName NormTime
 * @Description
 * @Author jzy
 */
public class NormTime {

	private static Set<String> timeD=new HashSet<String>();//存放的是提醒 时间段的词  上午、下午、等
	private static Map<String,String> cityZone = Collections.synchronizedMap(new TreeMap<String,String>());
	static {
		try {
//			Constants.setDataDir("d:/Chat/data_multi_0.66/");
			List<String> timelist=FileUtil.readStringFromFile(Constants.dataDir+"timeSlot.txt");
			for(String s:timelist){
				timeD.add(s.trim());
			}
			
			List<String> citylist=FileUtil.readStringFromFile(Constants.dataDir+"cityZone.txt");
			for(String s:citylist){
				String [] strs=s.split("\t");
				cityZone.put(strs[0].toLowerCase(), strs[1]);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static Set<String> getTimeD(){
		return timeD;
	}
	
	public static Map<String,String> getCityZone(){
		return cityZone;
	}

	public static List<String> getNormTime(String timeExp,String domain){
		List<TimeFormat2> ltf = StdTime2.normalTime(timeExp,domain);
		List<TimeFormat2> ltf2 = StdTime2.normalTime_o(timeExp, ltf,"");
		List<TimeFormat3> ltf3=StdTime2.normalTime_o2(timeExp,ltf2);
		List<String> normtime = new ArrayList<String>();
		if ("提醒".equals(domain)) {
			TimeAnalys ta = new TimeAnalys();
			List<TimeFormat3> ltf33 = ta.analysPross(ltf3);
			for (TimeFormat3 t : ltf33) {
				normtime = t.getTime();
			}
		} else {
			for (TimeFormat3 t : ltf3) {
				normtime = t.getTime();
			}
		}
		return normtime;

	}
	///
	
	public static List<String> getNormTimeSource(String timeExp,String domain){
		List<TimeFormat2> ltf = StdTime2.normalTime(timeExp,domain);
		List<TimeFormat2> ltf2 = StdTime2.normalTime_o(timeExp, ltf,"");
		List<TimeFormat3> ltf3=StdTime2.normalTime_o2(timeExp,ltf2);
		List<String> normtime = new ArrayList<String>();
			for (TimeFormat3 t : ltf3) {
				normtime = t.getTime();
			}
		return normtime;

	}
	
	
	
	
	
	/**
	 * 把从时间解析出的时间 提取到list中 便于操作
	 * @param ttf
	 * @return
	 */
	public static List<String> parseTime(String ttf){
		List<String> list=new ArrayList<String>();
		
		ttf=ttf.replaceAll("\\[", "").replaceAll("\\]", "");
		String [] strs=ttf.split(",");
		String[] str1=strs[0].split("=")[1].split(" ");
		for(String s:str1){
			list.add(s.replace("-", "").replace(":", "-"));
		}
		String[] str2=strs[1].split("=")[1].split(" ");
		for(String s:str2){
			list.add(s.replace("-", "").replace(":", "-"));
		}
		return list; 
	}
	/**
	 * 把从时间解析出的时间 提取到list中 便于操作
	 * @param ttf
	 * @return
	 */
	public static List<String> parseTime1(String ttf){
		List<String> list=new ArrayList<String>();
		
		ttf=ttf.replaceAll("\\[", "").replaceAll("\\]", "");
		String [] strs=ttf.split(",");
		String[] str1=strs[0].split("=")[1].split(" ");
		for(String s:str1){
			list.add(s.replace(":", "-"));
		}
		String[] str2=strs[1].split("=")[1].split(" ");
		for(String s:str2){
			list.add(s.replace(":", "-"));
		}
		return list; 
	}
	
	/**
	 * 解析 提醒领域的 每天 每周几
	 * @param str
	 * @return
	 */
	public static String parseRepeat(String str){ 
		String st=StringPreHandlingModule.chineseNumeralsToArabiaNumbers(str);
		String nums=StringUtil.getNum(st);
		char [] ch =nums.toCharArray();
		StringBuffer repeat=new StringBuffer("");
		if(st.contains("到")){
			if(ch.length>0&& ch.length==1){
				repeat.append("W"+ch[0]);
			}else if (ch.length>1){
				repeat.append("W"+ch[0]+"<W"+ch[1]);
			}
		}else if(ch.length<=4&& ch.length>0){
			for(char c:ch){
				repeat.append("W"+c);
			}
		}else if(st.contains("每天")|| st.contains("每日")){
			repeat.append("EVERYDAY");
		}
		return repeat.toString();
	}

	public static boolean judgeTime(String value){
		boolean flag=true;
		String year="";
		String year1="";
		String month="";
		String month1="";
		String day="";
		String day1="";
		String exp="";
		String regex="";
		Pattern p;
		Matcher m;
		value=StringPreHandlingModule.chineseNumeralsToArabiaNumbers(value);
		
		//号月日或号号
		regex="([0123456789一二三四五六七八九十]+)(((号|日)?[到和与])|号|日)((([0123456789一二三四五六七八九十]+)(月|-))?([0123456789一二三四五六七八九十]+)(号|日)?)?";
		p=Pattern.compile(regex);
		m=p.matcher(value);
		if(m.find()){
			exp=m.group();
			day=m.group(1);
			month1=m.group(7);
			day1=m.group(9);
		}
		
		//月日
				regex="([0123456789一二三四五六七八九十]+)(月|-)([0123456789一二三四五六七八九十]+)(号|日|)?";
				p=Pattern.compile(regex);
				m=p.matcher(value);
				if(m.find()){
					exp=m.group();
					month=m.group(1);
					day=m.group(3);
				}
				
				
				//月日到月日
				regex="([0123456789一二三四五六七八九十]+)(月|-)([0123456789一二三四五六七八九十]+)(((号|日)?[到和与])|号|日)(([0123456789一二三四五六七八九十]+)(月|-))?([0123456789一二三四五六七八九十]+)(号|日)?";
				p=Pattern.compile(regex);
				m=p.matcher(value);
				if(m.find()){
					exp=m.group();
					month=m.group(1);
					day=m.group(3);
					month1=m.group(8);
					day1=m.group(10);
				}
				
		//年月日都有
		regex="([0123456789一二三四五六七八九十]+)(年|-)([0123456789一二三四五六七八九十]+)(月|-)([0123456789一二三四五六七八九十]+)(号|日|)?";
		p=Pattern.compile(regex);
		m=p.matcher(value);
		if(m.find()){
			exp=m.group();
			year=m.group(1);
			month=m.group(3);
			day=m.group(5);
			
		}
		
		//年月日到月日或号
		regex="([0123456789一二三四五六七八九十]+)(年|-)([0123456789一二三四五六七八九十]+)(月|-)([0123456789一二三四五六七八九十]+)(((号|日)?[到和与])|号|日)(([0123456789一二三四五六七八九十]+)(下月|下个月|次月|月|-))?([0123456789一二三四五六七八九十]+)(号|日)?";
		p=Pattern.compile(regex);
		m=p.matcher(value);
		if(m.find()){
			exp=m.group();
			year=m.group(1);
			month=m.group(3);
			day=m.group(5);
			month1=m.group(10);
			day1=m.group(12);
			
		}
		Calendar cl=Calendar.getInstance();
		int year_now=cl.get(Calendar.YEAR);
		int month_now=cl.get(Calendar.MONTH)+1;
		int day_now=cl.get(Calendar.DAY_OF_MONTH);
		
		Log.logger.info("当前年月日:"+Integer.toString(year_now)+Integer.toString(month_now)+Integer.toString(day_now));
		if("".equals(day)||(day==null)||"".equals(exp)){
			return flag;
		}  
		
		if(exp.contains("年")||exp.contains("月")||exp.contains("日")||exp.contains("号")||exp.contains("-")){
			if("".equals(year)||(year==null)){
				year=Integer.toString(year_now);
			}
			if(year.length()==2){
				year="20"+year;
			}
			if("".equals(year1)||(year1==null)){
				year1=year;
			}else if(year1.length()==2){
				year1="20"+year1;
			}
			if("".equals(month)||(month==null)){
				month=Integer.toString(month_now);
			}
			if(!"".equals(day)&&!(day==null)){
				if(Integer.parseInt(day)>getMaxDay(Integer.parseInt(year), Integer.parseInt(month))||Integer.parseInt(day)<1 ||Integer.parseInt(month)<1||Integer.parseInt(month)>12){
					flag=false;
				}
				if("".equals(month1)||(month1==null)){
					month1=month;
				}
				if(!"".equals(day1)&&!(day1==null)){
					if(Integer.parseInt(day1)>getMaxDay(Integer.parseInt(year1), Integer.parseInt(month1))||Integer.parseInt(day1)<1 ||Integer.parseInt(month1)<1||Integer.parseInt(month1)>12){
						flag=false;
					}
				}else{
					if(Integer.parseInt(month1)<1||Integer.parseInt(month1)>12)flag=false;
				}
			}
		}

		
		Log.logger.info("时间最终结果exp:	"+exp);
		Log.logger.info("时间最终结果year:	"+year);
		Log.logger.info("时间最终结果month:	"+month);
		Log.logger.info("时间最终结果day:	"+day);
		Log.logger.info("时间最终结果month1:	"+month1);
		Log.logger.info("时间最终结果day1:	"+day1);
		return flag;
	}
	
	public static int getMaxDay(int year,int month){
		int maxday;
		if(month==1||month==3||month==5||month==7||month==8||month==10||month==12){
			maxday=31;
		}else if(month==4||month==6||month==9||month==11){
			maxday=30;
		}else{
			if(year%4==0){
				maxday=29;
			}else{
				maxday=28;
			}
		}
		return maxday;
	}
	public static void main(String[] args) {
//		List<String> normtime=getNormTime("三分钟后");
//		for(String str:normtime){
//			Log.logger.info("normtime:"+str);
//			Log.logger.info(":");
//		}
		String ct=NormTime.getCityZone().get("pacific");
		System.out.println(ct);
	}

}
