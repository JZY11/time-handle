package com.ipubu.time1;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ipubu.grammar.util.Constants;
import com.ipubu.util.Log;

/**
 * @ClassName StdTime2
 * @Description
 * @Author jzy
 */
public class StdTime2 {

	/**
	 * @param text
	 * @return 句子中匹配到的时间，并标准化返回
	 */
	public static List<TimeFormat2> normalTime(String text,String domain) {

		String path = TimeNormalizer.class.getResource("").getPath();
		String classPath = path.substring(0, path.length() - 1);
		// TimeNormalizer normalizer = new TimeNormalizer(classPath +
		// "/TimeExp2.m");
		TimeNormalizer normalizer = new TimeNormalizer(
				Constants.dataDir+"time/TimeExp2.m");
//		TimeNormalizer normalizer = new TimeNormalizer("E:\\chat0.67\\Chat\\dialog\\src\\main\\java\\com\\lenovo\\nlu\\dialog\\time1/TimeExp2.m");
		
		normalizer.parse(text,domain);// 抽取时间
		

		
		TimeUnit[] unit = normalizer.getTimeUnit();
		List<TimeFormat2> ltf = new ArrayList<TimeFormat2>();
		if (unit.length > 2) {
			
			String rules = "(([个1234567890一二三四五六七八九零两十]+)(分钟|小时|秒|分))?(([1234567890半个一二三四五六七八九零两十百]+)(秒|分钟|小时|分))(钱|前|后|之前|之后|以后)?";
			Pattern p = Pattern.compile(rules);
			Matcher m = p.matcher(text);
			boolean result = false;
			result = m.find();
			
			for (int i = 0; i < unit.length; i++) {
				if (i + 2 < unit.length) {
					String s1 = null;
					String s2 = null;
					String s3 = null;
					String date = comTim(unit[i].getTime(), unit[i].Time_Expression);
					if("新闻".equals(domain)){
						s1 = comTim(unit[i].getTime(), unit[i].Time_Expression) + " " + unit[i].getIsAllDayTime() + " " + unit[i].getIsTimePoint() + " " +unit[i].getIsDate();
						s2 = comTim(unit[i + 1].getTime(), unit[i + 1].Time_Expression)+ " " + unit[i + 1].getIsAllDayTime()  + " " + unit[i + 1].getIsTimePoint() + " " +unit[i + 1].getIsDate();
						s3 = comTim(unit[i + 2].getTime(), unit[i + 2].Time_Expression)+ " " + unit[i + 2].getIsAllDayTime()  + " " + unit[i + 2].getIsTimePoint() + " " +unit[i + 2].getIsDate();
							
					}else{
					s1 = DateUtil.formatDateDefault(unit[i].getTime()) + " " + unit[i].getIsAllDayTime() + " " + unit[i].getIsTimePoint() + " " +unit[i].getIsDate();
					s2 = DateUtil.formatDateDefault(unit[i + 1].getTime())+ " " + unit[i + 1].getIsAllDayTime()  + " " + unit[i + 1].getIsTimePoint() + " " +unit[i + 1].getIsDate();
					s3 = comTim(unit[i + 2].getTime(),unit[i + 2].getIsTimePoint())+ " " + unit[i + 2].getIsAllDayTime()  + " " + unit[i + 2].getIsTimePoint() + " " +unit[i + 2].getIsDate();
					}
					if (s1.equals(s2)&&!result) {
						if("新闻".equals(domain)){
							TimeFormat2 tf = new TimeFormat2(null, null, null);
							tf.setStartTime(s3);
							tf.setEndTime(s1);
							tf.setTimeExp(unit[i].Time_Expression + "到" + unit[i + 2].Time_Expression);
							ltf.add(tf);
						}else{
						TimeFormat2 tf = new TimeFormat2(null, null, null);
						tf.setStartTime(s1);
						tf.setEndTime(s3);
						tf.setTimeExp(unit[i].Time_Expression + "到" + unit[i + 2].Time_Expression);
						ltf.add(tf);}
					}else if(result){
						TimeFormat2 tf = new TimeFormat2(null, null, null);
//						if("天，周".contains(m.group(2))){
//							unit[i].setIsDate(true);
//							unit[i + 2].setIsDate(true);
//							unit[i].setIsTimePoint(false);
//							unit[i + 2].setIsTimePoint(false);
//							 s1 = DateUtil.formatDateDefault(unit[i].getTime())+ " " + unit[i].getIsAllDayTime()  + " " + unit[i].getIsTimePoint() + " " +unit[i].getIsDate();
//							 s3 = DateUtil.formatDateDefault(unit[i + 2].getTime()) + " " + unit[i + 2].getIsAllDayTime() + " " + unit[i + 2].getIsTimePoint() + " " +unit[i + 2].getIsDate();
//						}else{
							unit[i].setIsTimePoint(true);
							unit[i + 2].setIsTimePoint(true);
							s1 = DateUtil.formatDateDefault(unit[i].getTime())+ " " + unit[i].getIsAllDayTime()  + " " + unit[i].getIsTimePoint() + " " +unit[i].getIsDate();
							s3 = DateUtil.formatDateDefault(unit[i + 2].getTime())+ " " + unit[i + 2].getIsAllDayTime()  + " " + unit[i + 2].getIsTimePoint() + " " +unit[i + 2].getIsDate();
//						}
						tf.setStartTime(s1);
						tf.setEndTime(s3);
						tf.setTimeExp(m.group());
						ltf.add(tf);
					}
					else {
						TimeFormat2 tf = new TimeFormat2(null, null, null);
						tf.setStartTime(s1);
						tf.setEndTime(s1);
						tf.setTimeExp(unit[i].Time_Expression);
						ltf.add(tf);
						tf = new TimeFormat2(null, null, null);
						tf.setStartTime(s2);
						tf.setEndTime(s2);
						tf.setTimeExp(unit[i + 1].Time_Expression);
						ltf.add(tf);
						tf = new TimeFormat2(null, null, null);
						tf.setStartTime(s3);
						tf.setEndTime(s3);
						tf.setTimeExp(unit[i + 2].Time_Expression);
						ltf.add(tf);
					}
					i = i + 2;
				} else {
					String s5 = null;
					if("新闻".equals(domain)){
					s5 = comTim(unit[i].getTime(), unit[i].Time_Expression)+ " " + unit[i].getIsAllDayTime()  + " " + unit[i].getIsTimePoint() + " " +unit[i].getIsDate();
					}else{
					s5 = DateUtil.formatDateDefault(unit[i].getTime())+ " " + unit[i].getIsAllDayTime()  + " " + unit[i].getIsTimePoint() + " " +unit[i].getIsDate();
					}
					TimeFormat2 tf = new TimeFormat2(null, null, null);
					tf.setStartTime(s5);
					tf.setEndTime(s5);
					tf.setTimeExp(unit[i].Time_Expression);
					ltf.add(tf);
				}
			}
		} else {
			for (int i = 0; i < unit.length; i++) {
				String s4 = null;
				if("新闻".equals(domain)){
				String date = comTim(unit[i].getTime(), unit[i].Time_Expression);
				s4 = date + " " + unit[i].getIsAllDayTime()  + " " + unit[i].getIsTimePoint() + " " +unit[i].getIsDate();
				}else{
				s4 = DateUtil.formatDateDefault(unit[i].getTime())+ " " + unit[i].getIsAllDayTime()  + " " + unit[i].getIsTimePoint() + " " +unit[i].getIsDate();
				}
				TimeFormat2 tf = new TimeFormat2(null, null, null);
					tf.setStartTime(s4);
					tf.setEndTime(s4);
					if(text.contains(unit[i].Time_Expression+"整"))
					tf.setTimeExp(unit[i].Time_Expression+"整");
					else
						tf.setTimeExp(unit[i].Time_Expression);
				ltf.add(tf);
			}

		}

		return ltf;
	}

	
	public static String comTim(Date date,String stmp) {
		if(date==null||stmp==null) return null;
		
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		if(Calendar.getInstance().getTime().before(date)){
//			if((stmp.contains("星期")||stmp.contains("礼拜")||stmp.contains("周"))&&!stmp.startsWith("本")&&!stmp.startsWith("这"))
//			{
//				cal.add(cal.DAY_OF_YEAR,-7);
//			}else 
				if((stmp.contains("日")||stmp.contains("号"))&&!stmp.contains("月")){
				cal.add(cal.MONTH, -1);
			}
			
		}
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
		
	}
	
	public static String comTim(Date date,boolean flag ) {
		if(date==null) return null;
		
		Calendar cal = Calendar.getInstance();
		cal.add(cal.MINUTE, 3);
		cal.setTime(date);
		
		if(Calendar.getInstance().getTime().after(date)&&!flag){
				cal.add(cal.MONTH,1);
		}
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
		
	}
	
	public static String wordTranslatorFor(String text, String exp) {
	
		String rules = "最近几天|这3天|这三天|这几天|这些天|三天内|3天内|最近三天|最近3天|最近";
		Pattern p = Pattern.compile(rules);
		Matcher m = p.matcher(text);
		StringBuffer sb = new StringBuffer();
		boolean result = m.find();
		while (result) {
			if (exp.contains("今天到后天")) {
				exp = exp.replaceAll("今天到后天", m.group());
			}
			// m.appendReplacement(sb, "明天到大后天");
			result = m.find();
		}
		
		 rules = "最近七天|最近7天|近1周|近一周|七天以内|近7天|近七天|这7天|这七天";
		 p = Pattern.compile(rules);
		 m = p.matcher(text);
		 sb = new StringBuffer();
		 result = m.find();
		while (result) {
			if (exp.contains("今天到6天后")) {
				exp = exp.replaceAll("今天到6天后", m.group());
			}
			result = m.find();
		}
		
		rules =  "这6天|6天内|6天以内|这六天|近六天|近6天|最近6天|最近六天";
		p = Pattern.compile(rules);
		m = p.matcher(text);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			if (exp.contains("今天到5天后")) {
				exp = exp.replaceAll("今天到5天后", m.group());
			}
			result = m.find();
		}
	
		rules =  "这5天|5天内|5天以内|这五天|近五天|近5天|最近5天|最近五天";
		p = Pattern.compile(rules);
		m = p.matcher(text);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			if (exp.contains("今天到4天后")) {
				exp = exp.replaceAll("今天到4天后", m.group());
			}
			result = m.find();
		}

		rules = "这4天|4天以内|4天内|最近四天|四天内|四天以内|这四天|最近4天|近四天|近4天";
		p = Pattern.compile(rules);
		m = p.matcher(text);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			if (exp.contains("今天到大后天")) {
				exp = exp.replaceAll("今天到大后天", m.group());
			}	
			result = m.find();
		}
		
		rules = "这2天|最近2天|2天内|2天以内|这两天|最近两天|两天内|两天以内|近两天|近2天";
		p = Pattern.compile(rules);
		m = p.matcher(text);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			if (exp.contains("今天到明天")) {
				exp = exp.replaceAll("今天到明天", m.group());
			}
			result = m.find();
		}
		
		return exp;
	}
}
