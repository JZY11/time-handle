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

}
