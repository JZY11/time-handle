package com.ipubu.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ipubu.time1.StringPreHandlingModule;
import com.ipubu.time1.TimeFormat3;

/**
 * @ClassName TimeAnalys
 * @Description
 * @Author jzy
 */
public class TimeAnalys {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private String timeExp;
	private String starttime;
	private String startflag;
	private String endtime;
	private String endflag;
	private boolean qust =false;
	private boolean outOfMonth =false;
	private int notqust = -1;
	
	public boolean isOutOfMonth() {
		return outOfMonth;
	}
	public boolean isQust() {
		return qust;
	}
	public int getnotQust() {
		return notqust;
	}
	public List<TimeFormat3> analysPross(List<TimeFormat3> ltf3){
		if(ltf3.size()!=1) return ltf3;
		
		getParameter(ltf3);
	 	if(!this.starttime.split(" ")[0].equals(this.endtime.split(" ")[0]))
	 		return ltf3;
		
		String timeExpnum = StringPreHandlingModule.numberTranslator(timeExp);
		Calendar cal = Calendar.getInstance();
	 	try {
		Date date = sdf.parse(this.endtime);
		
		cal.setTime(date);
		
		} catch (ParseException e) {
			e.printStackTrace();
		}
	 	int day = cal.get(cal.DAY_OF_MONTH);
	 	
	 	
	 	
	 	//星期
	 	String rule = "(周|礼拜|星期)([1234567天日])";
	 	Pattern p = Pattern.compile(rule);
	 	Matcher m = p.matcher(timeExpnum);
	 	if(m.find()){
	 		if(isAfter(cal)){
	 			cal.add(cal.WEEK_OF_YEAR, 1);
	 			this.notqust = 1;
	 		}
	 	}
	 	//今天
	 	rule = "今天";
	 	p = Pattern.compile(rule);
	 	m = p.matcher(timeExpnum);
	 	if(m.find()){
	 		if("今天".equals(timeExpnum))return ltf3;
	 		if(isAfter(cal)){
	 			this.qust = true;
	 		}
	 		return ltf3;
	 	}
	 	
	 	
	 	
	 	//日
	 	rule = "(\\d+)(日|号)";
	 	p = Pattern.compile(rule);
	 	m = p.matcher(timeExpnum);
	 	if(!m.find()){
	 		if(isAfter(cal)){
	 			cal.add(cal.DAY_OF_YEAR, 1);
	 			this.notqust = 2;
	 		}
	 	}else {
			if(Integer.valueOf(m.group(1))!=day)
				outOfMonth = true;
		}
	 	
	 	
		//月
		rule = "(\\d+)月";
		p = Pattern.compile(rule);
		m = p.matcher(timeExpnum);
		if(!m.find()){
			if(isAfter(cal)){
				cal.add(cal.MONTH, 1);
				this.notqust = 3;
			}
		}
		//年
		rule = "(\\d+)年";
		p = Pattern.compile(rule);
		m = p.matcher(timeExpnum);
		if(!m.find()){
			if(isAfter(cal)){
				cal.add(cal.YEAR, 1);
				this.notqust = 4;
			}
		}
		
		
		this.endtime = sdf.format(cal.getTime());
		this.starttime = sdf.format(cal.getTime());
		List<TimeFormat3> ltf = new ArrayList<TimeFormat3>();
		TimeFormat3 tf3 = new TimeFormat3(null, null);
		List<String> ls=new ArrayList<String>();
		ls.add("[startTime="+this.starttime + " " +this.startflag +",endTime="+this.endtime + " " + this.endflag);
		tf3.setTime(ls);
		tf3.setTimeExp(this.timeExp);
		ltf.add(tf3);	
		
		return ltf;
	}
	
	private static boolean isAfter(Calendar cal){
		if (Calendar.getInstance().getTime().after(cal.getTime())) 
			return true;
		else 
			return false;
	}
}
