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
	
	
	public static String wordTranslatorRev(String text, String exp) {
		
		String rules = "最近几天|这3天|这三天|这几天|这些天|三天内|3天内|最近三天|最近3天|最近|近三天|近3天";
		Pattern p = Pattern.compile(rules);
		Matcher m = p.matcher(text);
		StringBuffer sb = new StringBuffer();
		boolean result = m.find();
		while (result) {
			if (exp.contains("今天到前天")) {
				exp = exp.replaceAll("今天到前天", m.group());
			}
			result = m.find();
		}
		
		 rules = "最近一周|最近七天|最近7天|近1周|近一周|七天以内|7天以内|7天内|七天内|近7天|近七天";
		 p = Pattern.compile(rules);
		 m = p.matcher(text);
		 sb = new StringBuffer();
		 result = m.find();
		while (result) {
			if (exp.contains("今天到6天前")) {
				exp = exp.replaceAll("今天到6天前", m.group());
			}
			result = m.find();
		}
		
		rules = "这6天|最近6天|6天内|6天以内|六天以内|六天内|最近六天|这六天|近六天|近6天";
		p = Pattern.compile(rules);
		m = p.matcher(text);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			if (exp.contains("今天到5天前")) {
				exp = exp.replaceAll("今天到5天前", m.group());
			}
			result = m.find();
		}
		
		rules = "这5天|最近5天|5天内|5天以内|五天以内|五天内|最近五天|这五天|近五天|近5天";
		p = Pattern.compile(rules);
		m = p.matcher(text);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			if (exp.contains("今天到4天前")) {
				exp = exp.replaceAll("今天到4天前", m.group());
			}
			result = m.find();
		}
		
		rules = "这4天|最近4天|4天内|4天以内|四天以内|四天内|最近四天|这四天|近四天|近4天";
		p = Pattern.compile(rules);
		m = p.matcher(text);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			if (exp.contains("今天到大前天")) {
				exp = exp.replaceAll("今天到大前天", m.group());
			}
			result = m.find();
		}
		
		rules = "这2天|最近2天|2天内|2天以内|两天以内|两天内|最近两天|这两天|近两天|近2天";
		p = Pattern.compile(rules);
		m = p.matcher(text);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			if (exp.contains("今天到昨天")) {
				exp = exp.replaceAll("今天到昨天", m.group());
			}
			result = m.find();
		}
		
		
		return exp;
	}
	public static String o_wordTranslator(String text, String exp,String domian) {
		if (text.contains("礼拜") && exp.contains("星期")) {
			exp = exp.replaceAll("星期", "礼拜");
		}
		if (text.contains("礼拜天") && exp.contains("礼拜7")) {
			exp = exp.replaceAll("礼拜7", "礼拜天");
		}
		if (text.contains("礼拜日") && exp.contains("礼拜7")) {
			exp = exp.replaceAll("礼拜7", "礼拜日");
		}
		if (text.contains("星期天") && exp.contains("星期7")) {
			exp = exp.replaceAll("星期7", "星期天");
		}
		if (text.contains("星期日") && exp.contains("星期7")) {
			exp = exp.replaceAll("星期7", "星期日");
		}
		if (text.contains("周天") && exp.contains("周7")) {
			exp = exp.replaceAll("周7", "周天");
		}
		if (text.contains("周日") && exp.contains("周7")) {
			exp = exp.replaceAll("周7", "周日");
		}
		if (text.contains("周末") && exp.contains("周7")) {
			exp = exp.replaceAll("周7", "周末");
		}
		if (text.contains("周六日") && exp.contains("周6到周7")) {
			exp = exp.replaceAll("周6到周7", "周六日");
		}
		String rules = "未来几天|随后几天|未来3天|未来三天|往后三天|往后3天|接下来三天|明天过后|接下来3天";
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
		// m.appendTail(sb);
		// text = sb.toString();
		 rules = "未来四天|随后四天|未来4天|往后四天|往后4天|接下来四天|接下来4天|4天以内|4天内|四天以内|四天内|随后4天";
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
		
		 rules = "未来五天|随后五天|未来5天|往后五天|往后5天|接下来五天|接下来5天|随后5天";
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
		 rules = "未来六天|随后六天|往后六天|往后6天|接下来六天|接下来6天|未来6天|随后6天";
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
		 rules = "未来七天|随后七天|往后七天|往后7天|接下来七天|接下来7天|未来7天|随后7天|往后7天|未来一周|未来1周";
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
		
		///////
		 rules = "乐语音天气";
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
		////

		rules = "((周|星期)[1234567一二三四五六日天])((周|星期)[1234567一二三四五六日天])";
		p = Pattern.compile(rules);
		m = p.matcher(text);
		result = m.find();
		while (result) {
				exp = exp.replaceAll("和","");
			result = m.find();
		}
		
		rules = "([一二两三四五六七八九十0123456789]{1,2}[号日])([一二两三四五六七八九十0123456789]{1,2}[号日])";
		p = Pattern.compile(rules);
		m = p.matcher(text);
		result = m.find();
		while (result) {
				exp = exp.replaceAll("和","");
			result = m.find();
		}
		
		rules = "((周|星期)[一二三四五六七1234567日天])与((周|星期)[一二三四五六七1234567日天])";
		 p = Pattern.compile(rules);
		 m = p.matcher(text);
		 sb = new StringBuffer();
		 result = m.find();
		while (result) {
			exp = exp.replaceAll("到","与");
			result = m.find();
		}


		rules = "今明天|今天明天|今明天|今明两天|今明2天|今天与明天";
		p = Pattern.compile(rules);
		m = p.matcher(text);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			if (exp.contains("今天到明天")) {
				exp = exp.replaceAll("今天到明天", m.group());
			}
			// m.appendReplacement(sb, "明天到大后天");
			result = m.find();
		}
		rules = "24小时内|每天|实时";
		p = Pattern.compile(rules);
		m = p.matcher(text);
		result = m.find();
		while (result) {
			if (exp.contains("今儿")) {
				exp =  exp.replaceAll("今儿", m.group());
			}
			// m.appendReplacement(sb, "明天到大后天");
			result = m.find();
		}
		////
	
		rules = "工作日";
		p = Pattern.compile(rules);
		
		m = p.matcher(text);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			if (exp.contains("周1到周5")) {
				exp = exp.replaceAll("周1到周5", m.group());
			}
			// m.appendReplacement(sb, "明天到大后天");
			result = m.find();
		}
		////
		rules = "周6日|周末|非工作日|周六日";
		p = Pattern.compile(rules);
		m = p.matcher(text);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			if (exp.contains("周6到周7")) {
				exp = exp.replaceAll("周6到周7", m.group());
			}
			// m.appendReplacement(sb, "明天到大后天");
			result = m.find();
		}
		/////
		rules = "后儿|过两天";
		p = Pattern.compile(rules);
		
		m = p.matcher(text);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			if (exp.contains("后天")) {
				exp = exp.replaceAll("后天", m.group());
			}
			// m.appendReplacement(sb, "明天到大后天");
			result = m.find();
		}

		/////
		rules = "前儿|前儿晚";
		p = Pattern.compile(rules);
		m = p.matcher(text);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			if (exp.contains("前天")) {
				exp = exp.replaceAll("前天", m.group());
			}
			// m.appendReplacement(sb, "明天到大后天");
			result = m.find();
		}
		///
		rules = "周天儿";
		p = Pattern.compile(rules);
		
		m = p.matcher(text);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			if (exp.contains("周天")) {
				exp = exp.replaceAll("周天", m.group());
			}
			// m.appendReplacement(sb, "明天到大后天");
			result = m.find();
		}

		
		
		rules = "未来24小时|未来二十四小时";
		p = Pattern.compile(rules);
		m = p.matcher(text);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			if (exp.contains("明天")) {
				exp = exp.replaceAll("明天", m.group());
			}
			// m.appendReplacement(sb, "明天到大后天");
			result = m.find();
		}
		////
		rules = "半夜|上半夜|下半夜|午夜|黎明";
		p = Pattern.compile(rules);
		m = p.matcher(text);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			if (exp.contains("凌晨")) {
				exp = exp.replaceAll("凌晨", m.group());
			}
			// m.appendReplacement(sb, "明天到大后天");
			result = m.find();
		}
		
		rules = "明后天|明天后天|未来2天|未来两天|明后两天|明后2天|接下来两天|明天与后天";
		p = Pattern.compile(rules);
		m = p.matcher(text);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			if (exp.contains("明天到后天")) {
				exp = exp.replaceAll("明天到后天", m.group());
			}
			// m.appendReplacement(sb, "明天到大后天");
			result = m.find();
		}
		String rules_sp="(本周|这周|这星期)[1-7一二三四五六七天日]";
		Pattern p_sp = Pattern.compile(rules_sp);
		Matcher m_sp = p_sp.matcher(text);
		boolean result_sp = !m_sp.find();
		if (result_sp) {
			rules = "(本周|这周|这星期|这一周|这一星期|最近一周|这1周|这1星期|最近1周)";
			p = Pattern.compile(rules);
			m = p.matcher(text);
			sb = new StringBuffer();
			result = m.find();
			while (result) {
				if (exp.contains("周1到周7")) {
					exp = exp.replaceAll("周1到周7", m.group());
				}
				// m.appendReplacement(sb, "明天到大后天");
				result = m.find();
			}
			
		}
		
		rules = "(明年|去年|今年|([0123456789一二三四五六七八九零两千]+)年)([0123456789一二三四五六七八九零两十]+)月([0123456789一二三四五六七八九零两十]+)(日|号)到现在";
		p = Pattern.compile(rules);
		m = p.matcher(text);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			
			if (exp.contains("年")&&exp.contains("月")&&exp.contains("日")&&exp.contains("点")&&exp.contains("分")&&exp.contains("秒")) 
				exp = m.group();
			result = m.find();
		}
		
		
		rules = "(((([0123456789一二三四五六七八九零千]+)年)|去年|前年|明年|后年|今年)的?)?"+"(寒食节|七夕情人节|下元节|端午节|中秋节|清明节|中元节|圣诞节|重阳节|国庆节|大寒|谷雨|处暑|元旦|重阳|平安夜|建党节|劳动节|春分|秋分|教师节|圣诞|"
				+ "联合国日|中元|老人节|除夕|霜降|青年节|清明|大雪|小寒|万圣节|孙中山诞辰纪念日|国庆|寒露|立夏|"
				+ "建军节|中秋|小雪|端午|立春|小满|爸爸节|大暑|消费者权益日|下元|国际奥林匹克日|植树节|元宵节|小年|"
				+ "立冬|夏至|愚人节|冬至|白露|雨水|七夕|孔子诞辰|护士节|小暑|惊蛰|芒种|妇女节|澳门回归纪念日|立秋|"
				+ "春节|过年|端阳|寒食|腊八节|抗战胜利纪念日|情人节|世界艾滋病日|儿童节|父亲节|母亲节|新年|阳历年|11期间|十一期间|五一|十一|光棍节)";
		p = Pattern.compile(rules);
		m = p.matcher(text);
		sb = new StringBuffer();
		result = m.find();

		while (result) {
			if (exp.contains("年")&&exp.contains("月")&&exp.contains("日")&&exp.contains("点")&&exp.contains("分")&&exp.contains("秒")) {
//				exp = exp.replaceAll("年", m.group());
				String str="";
				if(exp.length()>(exp.indexOf('秒')+1)){
					str = exp.substring(exp.indexOf('秒')+1);
				}
				
				exp = m.group()+str;
			}
			// m.appendReplacement(sb, "明天到大后天");
			result = m.find();
		}
		//
		rules = "现在|当前";
		p = Pattern.compile(rules);
		m = p.matcher(text);
		sb = new StringBuffer();
		result = m.find();

		while (result) {
			if (exp.contains("年")&&exp.contains("月")&&exp.contains("日")&&exp.contains("点")&&exp.contains("分")&&exp.contains("秒")) {
//				exp = exp.replaceAll("年", m.group());
				String str="";
				if(exp.length()>(exp.indexOf('秒')+1)){
					str = exp.substring(exp.indexOf('秒')+1);
				}
				
				exp = m.group()+str;
			}
			// m.appendReplacement(sb, "明天到大后天");
			result = m.find();
		}
		
		
		
		///
		rules = "过去两天|过去2天|过去二天";
		p = Pattern.compile(rules);
		m = p.matcher(text);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			if (exp.contains("昨天到今天")) {
				exp = exp.replaceAll("昨天到今天", m.group());
			}
			// m.appendReplacement(sb, "明天到大后天");
			result = m.find();
		}
		
		rules = "过去3天|过去三天";
		p = Pattern.compile(rules);
		m = p.matcher(text);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			if (exp.contains("前天到今天")) {
				exp = exp.replaceAll("前天到今天", m.group());
			}
			// m.appendReplacement(sb, "明天到大后天");
			result = m.find();
		}
		
		 rules = "下周";
		 p = Pattern.compile(rules);
		 m = p.matcher(text);
		 sb = new StringBuffer();
		 result = m.find();
		while (result) {
			if (exp.contains("下周1到下周7")) {
				exp = exp.replaceAll("下周1到下周7", m.group());
			}
			result = m.find();
		}
		
		rules = "上周";
		 p = Pattern.compile(rules);
		 m = p.matcher(text);
		 sb = new StringBuffer();
		 result = m.find();
		while (result) {
			if (exp.contains("上周1到上周7")) {
				exp = exp.replaceAll("上周1到上周7", m.group());
			}
			result = m.find();
		}
		
		 rules = "(截止|截至|截止到|截至到)((周|礼拜|星期)[1-7一二三四五六日])((早上|中午|晚上|下午)?([0-9一二三四五六七八九两零]点)半?)?(为止|)";//(截止)?到(周[1-7])(止|为止)?
		 p = Pattern.compile(rules);
		 m = p.matcher(text);
		 sb = new StringBuffer();
		 result = m.find();		
		while (result) {
			if (exp.contains("今天到")) {
				exp = exp.replaceAll(exp, m.group());
			}
			result = m.find();
		}
		
		rules = "到((周|礼拜|星期)[1-7一二三四五六日])((早上|中午|晚上|下午)?([0-9一二三四五六七八九两零]点)半?)?(止|为止)";//(截止)?到(周[1-7])(止|为止)? 
		p = Pattern.compile(rules);
		 m = p.matcher(text);
		 sb = new StringBuffer();
		 result = m.find();		
		while (result) {
			System.out.println(m.group());
			if (exp.contains("今天到")) {
				exp = exp.replaceAll(exp, m.group());
			}
			result = m.find();
		}
		
		rules = "(截止|截至)(\\d+(号|日))";
		 p = Pattern.compile(rules);
		 m = p.matcher(text);
		 sb = new StringBuffer();
		 result = m.find();
		while (result) {
			if (exp.contains("今天到"+m.group(2))) {
				exp = exp.replaceAll("今天到"+m.group(2), m.group());
			}
			result = m.find();
		}
		
		rules = "到(\\d+(号|日))(止|为止)";
		 p = Pattern.compile(rules);
		 m = p.matcher(text);
		 sb = new StringBuffer();
		 result = m.find();
		while (result) {
			if (exp.contains("今天到"+m.group(2))) {
				exp = exp.replaceAll("今天到"+m.group(2), m.group());
			}
			result = m.find();
		}
		
		
		
		if("新闻".equals(domian)){
			exp = wordTranslatorRev(text, exp);
		}else 
			exp =wordTranslatorFor(text, exp);
		return exp;
	}

	public static List<TimeFormat2> normalTime_o(String text, List<TimeFormat2> ltf , String domain) {
		for (TimeFormat2 t : ltf) {
//			Log.logger.info(t.getTimeExp());
//			Log.logger.info("text:" + text);
			String tar = StringPreHandlingModule.numberTranslator(text);
//			Log.logger.info("tar:" + tar);
			String exp = t.getTimeExp();
//			Log.logger.info("exp:" + exp);
			if (!text.contains(exp)) {
//			Log.logger.info("exp:" + exp);
				exp = o_wordTranslator(text, exp,domain);
//				Log.logger.info("exp:" + exp);
//				String s = foematIntegerUtil.regexTr(exp);
				String s = FormatIntegerUtil.getExp(text, exp);
//				Log.logger.info("exps:" + s);
//				String firststr = s.substring(0, 1);
//				String endtstr = s.substring(s.length() - 1);
//				s = text.substring(text.indexOf(firststr), text.indexOf(firststr) + s.length());
				t.setTimeExp(s);
				text = text.replaceAll(s, "");
			} 
			
		}

		return ltf;
	}
	public static List<TimeFormat3> normalTime_o2(String text, List<TimeFormat2> ltf) {

		List<TimeFormat3> ltf3 = new ArrayList<TimeFormat3>();
		if(ltf==null||ltf.size()==0) return ltf3;
		
		String h1="null";String h2="null";
		String h3="null";String h4="null";
		String h5="null";
		if(ltf.size()>1){
			 h1=ltf.get(0).getTimeExp()+ltf.get(1).getTimeExp();
			 h2=ltf.get(0).getTimeExp()+"和"+ltf.get(1).getTimeExp();
			 h4=ltf.get(0).getTimeExp()+"与"+ltf.get(1).getTimeExp();
			 h3=ltf.get(0).getTimeExp()+"过"+ltf.get(1).getTimeExp();
			 h5=ltf.get(0).getTimeExp()+"儿"+ltf.get(1).getTimeExp();
		}
		if(text.contains(h1)){
			TimeFormat3 tf3=new TimeFormat3(null, null);
			List<String> ls=new ArrayList<String>();
			ls.add(ltf.get(0).toString2());
			ls.add(ltf.get(1).toString2());
			tf3.setTime(ls);
			tf3.setTimeExp(h1);
			ltf3.add(tf3);	
		}else if(text.contains(h2)){
			TimeFormat3 tf3=new TimeFormat3(null, null);
			List<String> ls=new ArrayList<String>();
			ls.add(ltf.get(0).toString2());
			ls.add(ltf.get(1).toString2());
			tf3.setTime(ls);
			tf3.setTimeExp(h2);
			ltf3.add(tf3);	
		}else if(text.contains(h4)){
			TimeFormat3 tf3=new TimeFormat3(null, null);
			List<String> ls=new ArrayList<String>();
			ls.add(ltf.get(0).toString2());
			ls.add(ltf.get(1).toString2());
			tf3.setTime(ls);
			tf3.setTimeExp(h4);
			ltf3.add(tf3);	
		}
		else if(text.contains(h3)){
			TimeFormat3 tf3=new TimeFormat3(null, null);
			List<String> ls=new ArrayList<String>();
			ls.add("[startTime="+ltf.get(0).getStartTime()+",endTime="+ltf.get(1).getEndTime()+"]");
			tf3.setTime(ls);
			tf3.setTimeExp(h3);
			ltf3.add(tf3);	
		}
		else if(text.contains(h5)){
			TimeFormat3 tf3=new TimeFormat3(null, null);
			List<String> ls=new ArrayList<String>();
			ls.add(ltf.get(1).toString2());
			tf3.setTime(ls);
			tf3.setTimeExp(h5);
			ltf3.add(tf3);	
		}
		else {
			for(TimeFormat2 tf2:ltf){
				TimeFormat3 tf3=new TimeFormat3(null, null);
				List<String> ls=new ArrayList<String>();
				ls.add(tf2.toString2());
				tf3.setTime(ls);
				tf3.setTimeExp(tf2.getTimeExp());
				ltf3.add(tf3);
			}

		}
		
		return ltf3;
	}

}
