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

	
}
