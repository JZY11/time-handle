package com.ipubu.time1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ipubu.holiday.StandardTime;
import com.ipubu.time.TimeFormat;
import com.ipubu.util.Log;

/**
 * @ClassName StringPreHandlingModule
 * @Description
 * @Author jzy
 */
public class StringPreHandlingModule {

	/**
	 * 该方法删除一字符串中所有匹配某一规则字串 可用于清理一个字符串中的空白符和语气助词
	 * 
	 * @param target
	 *            待处理字符串
	 * @param rules
	 *            删除规则
	 * @return 清理工作完成后的字符串
	 */
	public static String delKeyword(String target, String rules) {
		Pattern p = Pattern.compile(rules);
		Matcher m = p.matcher(target);
		StringBuffer sb = new StringBuffer();
		boolean result = m.find();
		while (result) {
			m.appendReplacement(sb, "");
			result = m.find();
		}
		m.appendTail(sb);
		String s = sb.toString();
//		 Log.logger.info("字符串："+target+" 的处理后字符串为：" +sb);
		return s;
	}
	private static String regxTrans(String target,String rules,String replaceStr,String s){
		if(s!=null&&"".equals(s)) return s;
		if(replaceStr==null||"".equals(replaceStr)) return null;
		
		Pattern p = Pattern.compile(rules);
		Matcher m = p.matcher(target);
		StringBuffer sb = new StringBuffer();
		
		boolean result = m.find();
		while (result) {
			Log.logger.info("m.:" + m.group());
			m.appendReplacement(sb, replaceStr);
			result = m.find();
		}
		m.appendTail(sb);
		s = sb.toString();
		
		return s;
	}
	public static String wordTranslator(String target,String domain) {
		if(target==null||"".equals(target)){return "";}
		if("下周".equals(target)){return "下周1到下周7";}else if("上周".equals(target)){return "上周1到上周7";}
		/*String  rules = "(([个1234567890]+)(分钟|小时))?(([1234567890半个]+)(秒|分钟|小时))(钱|前|后|之前|之后|以后)?";
		Pattern p = Pattern.compile(rules);
		Matcher m = p.matcher(target);
		StringBuffer sb = new StringBuffer();
		boolean result = m.find();
		

		while (result) {
			TimeFormat tfFormat = StandardTime.normTime(m.group());
			if(tfFormat!=null)
			m.appendReplacement(sb, tfFormat.getStartTime()+"到"+tfFormat.getEndTime());
			result = m.find();
		}
		m.appendTail(sb);
		String s = sb.toString();*/
		
		String  rules = "(([个1234567890]+)(分钟|小时|分))?(([1234567890半个]+)(秒|分钟|小时|分))(钱|前|后|之前|之后|以后)?";
		Pattern p = Pattern.compile(rules);
		Matcher m = p.matcher(target);
		StringBuffer sb = new StringBuffer();
		boolean result = m.find();

		while (result) {
			if (m.group().contains("小时") || m.group().contains("分钟") || m.group().contains("秒") || 
					((m.group().contains("小时") && m.group().contains("分")) || (m.group().contains("小时") && m.group().contains("分钟")) || 
							(m.group().contains("小时") && m.group().contains("秒")) || (m.group().contains("分") && m.group().contains("秒")) || 
							(m.group().contains("分钟") && m.group().contains("秒")) || (m.group().contains("小时") && m.group().contains("分") && m.group().contains("秒")) || 
							(m.group().contains("小时") && m.group().contains("分钟") && m.group().contains("秒")))) {
				TimeFormat tfFormat = StandardTime.normTime(m.group());
				if(tfFormat!=null)
					m.appendReplacement(sb, tfFormat.getStartTime()+"到"+tfFormat.getEndTime());
				result = m.find();
			}else {
				result = false;
			}
		}
		m.appendTail(sb);
		String s = sb.toString();
		
		rules = "到(周[1-7])(止|为止)";
		p = Pattern.compile(rules);
		m = p.matcher(s);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			m.appendReplacement(sb, "今天到"+m.group(1));
			result = m.find();
		}
		m.appendTail(sb);
		s = sb.toString();
		// Log.logger.info("字符串："+target+" 的处理后字符串为：" +sb);
		
		rules = "(截止|截至)(周[1-7])";
		p = Pattern.compile(rules);
		m = p.matcher(s);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			m.appendReplacement(sb, "今天到"+m.group(2));
			result = m.find();
		}
		m.appendTail(sb);
		s = sb.toString();
		// Log.logger.info("字符串："+target+" 的处理后字符串为：" +sb);
		
		
		rules = "到(\\d+(号|日))(止|为止)";
		p = Pattern.compile(rules);
		m = p.matcher(s);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			m.appendReplacement(sb, "今天到"+m.group(1));
			result = m.find();
		}
		m.appendTail(sb);
		s = sb.toString();
		
		
		rules = "(截止|截至)(\\d+(号|日))";
		p = Pattern.compile(rules);
		m = p.matcher(s);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			m.appendReplacement(sb, "今天到"+m.group(2));
			result = m.find();
		}
		m.appendTail(sb);
		s = sb.toString();
		
		
		rules = "今明天|今天明天|今明天|今明两天|今明2天|今天与明天";
		p = Pattern.compile(rules);
		m = p.matcher(s);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			Log.logger.info("m.:" + m.group());
			m.appendReplacement(sb, "今天到明天");
			result = m.find();
		}
		m.appendTail(sb);
		s = sb.toString();
//		 Log.logger.info("字符串："+target+" 的处理后字符串为：" +sb);
		
		
		rules = "工作日";
		p = Pattern.compile(rules);
		m = p.matcher(s);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
//			Log.logger.info("m.:" + m.group());
			m.appendReplacement(sb, "周1到周5");
			result = m.find();
		}
		m.appendTail(sb);
		s = sb.toString();

		rules = "周6日|周末|非工作日";
		p = Pattern.compile(rules);
		m = p.matcher(s);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			Log.logger.info("m.:" + m.group());
			m.appendReplacement(sb, "周6到周7");
			result = m.find();
		}
		m.appendTail(sb);
		s = sb.toString();
		// Log.logger.info("字符串："+target+" 的处理后字符串为：" +sb);
		
		rules = "24小时内|每天|实时";
		p = Pattern.compile(rules);
		m = p.matcher(s);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			m.appendReplacement(sb, "今儿");
			result = m.find();
		}
		m.appendTail(sb);
		s = sb.toString();
		
		//////
		rules = "后儿|过2天";
		p = Pattern.compile(rules);
		m = p.matcher(s);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			m.appendReplacement(sb, "后天");
			result = m.find();
		}
		m.appendTail(sb);
		s = sb.toString();
		// Log.logger.info("字符串："+target+" 的处理后字符串为：" +sb);
		
		rules = "前儿|前二晚";
		p = Pattern.compile(rules);
		m = p.matcher(s);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			m.appendReplacement(sb, "前天");
			result = m.find();
		}
		m.appendTail(sb);
		s = sb.toString();
		
		
		rules = "过去2天";
		p = Pattern.compile(rules);
		m = p.matcher(s);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			m.appendReplacement(sb, "昨天到今天");
			result = m.find();
		}
		m.appendTail(sb);
		s = sb.toString();
		// Log.logger.info("字符串："+target+" 的处理后字符串为：" +sb);
		
		rules = "过去3天";
		p = Pattern.compile(rules);
		m = p.matcher(s);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			m.appendReplacement(sb, "前天到今天");
			result = m.find();
		}
		m.appendTail(sb);
		s = sb.toString();
		// Log.logger.info("字符串："+target+" 的处理后字符串为：" +sb);
		
		rules = "未来24小时";
		p = Pattern.compile(rules);
		m = p.matcher(s);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			m.appendReplacement(sb, "明天");
			result = m.find();
		}
		m.appendTail(sb);
		s = sb.toString();
		// Log.logger.info("字符串："+target+" 的处理后字符串为：" +sb);

		rules = "明后天|明天后天|未来2天|明后两天|明后2天|接下来2天|明天与后天";
		p = Pattern.compile(rules);
		m = p.matcher(s);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			m.appendReplacement(sb, "明天到后天");
			result = m.find();
		}
		m.appendTail(sb);
		s = sb.toString();
		// Log.logger.info("字符串："+target+" 的处理后字符串为：" +sb);

		rules = "未来几天|随后几天|未来3天|往后3天|接下来3天|明天过后";
		p = Pattern.compile(rules);
		m = p.matcher(s);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			m.appendReplacement(sb, "今天到后天");
			result = m.find();
		}
		m.appendTail(sb);
		s = sb.toString();
		// Log.logger.info("字符串："+target+" 的处理后字符串为：" +sb);

		rules = "随后4天|未来4天|往后4天|接下来4天";
		p = Pattern.compile(rules);
		m = p.matcher(s);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			m.appendReplacement(sb, "今天到大后天");
			result = m.find();
		}
		m.appendTail(sb);
		s = sb.toString();
		// Log.logger.info("字符串："+target+" 的处理后字符串为：" +sb);

		rules = "随后5天|未来5天|往后5天|接下来5天";
		p = Pattern.compile(rules);
		m = p.matcher(s);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			m.appendReplacement(sb, "今天到4天后");
			result = m.find();
		}
		m.appendTail(sb);
		s = sb.toString();
		// Log.logger.info("字符串："+target+" 的处理后字符串为：" +sb);

		rules =  "随后6天|未来6天|这6天|往后6天|接下来6天";
		p = Pattern.compile(rules);
		m = p.matcher(s);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			m.appendReplacement(sb, "今天到5天后");
			result = m.find();
		}
		m.appendTail(sb);
		s = sb.toString();
		// Log.logger.info("字符串："+target+" 的处理后字符串为：" +sb);
		
		rules = "随后7天|未来7天|往后7天|接下来7天|未来1周";
		p = Pattern.compile(rules);
		m = p.matcher(s);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			m.appendReplacement(sb, "今天到6天后");
			result = m.find();
		}
		m.appendTail(sb);
		s = sb.toString();
		// Log.logger.info("字符串："+target+" 的处理后字符串为：" +sb);
		////
		rules = "乐语音天气";
		p = Pattern.compile(rules);
		m = p.matcher(s);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			m.appendReplacement(sb, "今天到6天后");
			result = m.find();
		}
		m.appendTail(sb);
		s = sb.toString();
		////
		rules = "周天儿";
		p = Pattern.compile(rules);
		m = p.matcher(s);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			m.appendReplacement(sb, "周7");
			result = m.find();
		}
		m.appendTail(sb);
		s = sb.toString();
		
		
		/////
		rules = "((周|星期)[1234567日天])与((周|星期)[1234567日天])";
		p = Pattern.compile(rules);
		m = p.matcher(s);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			m.appendReplacement(sb, m.group(1)+"到"+m.group(3));
			result = m.find();
		}
		m.appendTail(sb);
		s = sb.toString();
		
		
		
		rules = "礼拜";
		p = Pattern.compile(rules);
		m = p.matcher(s);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			m.appendReplacement(sb, "星期");
			result = m.find();
		}
		m.appendTail(sb);
		s = sb.toString();
		// Log.logger.info("字符串："+target+" 的处理后字符串为：" +sb);

		String rules_sp="(本周|这周|这星期)[1-7一二三四五六七天日]";
		Pattern p_sp = Pattern.compile(rules_sp);
		Matcher m_sp = p_sp.matcher(s);
		boolean result_sp = !m_sp.find();
		if (result_sp) {
		rules = "(本周|这周|这星期|这一周|这一星期|最近一周|这1周|这1星期|最近1周)";
		p = Pattern.compile(rules);
		m = p.matcher(s);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			m.appendReplacement(sb, "周1到周7");
			result = m.find();
		}
		m.appendTail(sb);
		s = sb.toString();
		// Log.logger.info("字符串："+target+" 的处理后字符串为：" +sb);
		}
		rules = "([一二两三四五六七八九十0123456789]{1,2}[号日])([一二两三四五六七八九十0123456789]{1,2}[号日])";
		p = Pattern.compile(rules);
		m = p.matcher(s);

		sb = new StringBuffer();
		result = m.find();
		while (result) {
//			// Log.logger.info("m.group(0):"+m.group(0));
//			String rules2 = "号|日";
//			Pattern p2 = p = Pattern.compile(rules2);
//			Matcher m2 = p.matcher(m.group(0));
//			StringBuffer sb2 = new StringBuffer();
//			boolean result2 = m2.find();
//			while (result2) {
//				// Log.logger.info("m2.group(0):"+m2.group(0));
//				m2.appendReplacement(sb2, "号和");
//				result2 = m2.find();
//			}
//			m2.appendTail(sb2);
//			String s2 = sb2.toString();
			
			m.appendReplacement(sb,m.group(1)+"和"+m.group(2));
			result = m.find();
		}
		// Log.logger.info("sb:"+sb);
		m.appendTail(sb);
		s = sb.toString();
		// Log.logger.info("字符串："+target+" 的处理后字符串为：" +sb);

		rules = "((周|星期)[1234567日天])((周|星期)[1234567日天])";
		p = Pattern.compile(rules);
		m = p.matcher(s);

		sb = new StringBuffer();
		result = m.find();
		while (result) {
//			// Log.logger.info("m.group(0):"+m.group(0));
//			String rules2 = "周|星期";
//			Pattern p2 = p = Pattern.compile(rules2);
//			Matcher m2 = p.matcher(m.group(0));
//			StringBuffer sb2 = new StringBuffer();
//			boolean result2 = m2.find();
//			while (result2) {
//				// Log.logger.info("m2.group(0):"+m2.group(0));
//				m2.appendReplacement(sb2, "和"+m.group());
//				result2 = m2.find();
//			}
//			m2.appendTail(sb2);
//			String s2 = sb2.toString();
//			m.appendReplacement(sb, s2);
//			result = m.find();
			m.appendReplacement(sb, m.group(1)+"和"+m.group(3));
			result = m.find();
			
		}
		// Log.logger.info("sb:"+sb);
		m.appendTail(sb);
		s = sb.toString();
		// Log.logger.info("字符串："+target+" 的处理后字符串为：" +sb);

		rules = "((((\\d+)年)|去年|前年|明年|后年|今年)的?)?"+"(下元节|端午节|中秋节|清明节|中元节|圣诞节|重阳节|国庆节|大寒|谷雨|处暑|元旦|重阳|平安夜|建党节|劳动节|春分|秋分|教师节|圣诞|"
				+ "联合国日|中元|老人节|除夕|霜降|青年节|清明|大雪|小寒|万圣节|孙中山诞辰纪念日|国庆|寒露|立夏|"
				+ "建军节|中秋|小雪|端午|立春|小满|爸爸节|大暑|消费者权益日|下元|国际奥林匹克日|植树节|元宵节|小年|"
				+ "立冬|夏至|愚人节|冬至|白露|雨水|七夕|孔子诞辰|护士节|小暑|惊蛰|芒种|妇女节|澳门回归纪念日|立秋|"
				+ "春节|过年|端阳|寒食|腊8节|抗战胜利纪念日|情人节|世界艾滋病日|儿童节|父亲节|母亲节|新年|阳历年|11期间|光棍节)";
		p = Pattern.compile(rules);
		m = p.matcher(s);
		sb = new StringBuffer();
		result = m.find();

		while (result) {
			TimeFormat tfFormat = StandardTime.normTime(m.group());
			if(tfFormat!=null)
			m.appendReplacement(sb, tfFormat.getStartTime());
			result = m.find();
		}
		m.appendTail(sb);
		s = sb.toString();
		//xianzai
		rules = "现在|当前";
		p = Pattern.compile(rules);
		m = p.matcher(s);
		sb = new StringBuffer();
		result = m.find();

		while (result) {
			TimeFormat tfFormat = StandardTime.normTime(m.group());
			if(tfFormat!=null)
			m.appendReplacement(sb, tfFormat.getStartTime());
			result = m.find();
		}
		m.appendTail(sb);
		s = sb.toString();
		
		// Log.logger.info("字符串："+target+" 的处理后字符串为：" +sb);
		////////三分钟后
		
//		rules = "每((周|星期|礼拜)([1234567]))";
//		p = Pattern.compile(rules);
//		m = p.matcher(s);
//		sb = new StringBuffer();
//		result = m.find();
//		while (result) {
//			m.appendReplacement(sb, "每周"+m.group(1));
//			result = m.find();
//		}
//		m.appendTail(sb);
//		s = sb.toString();
		
		/////
		rules = "半夜|上半夜|下半夜|午夜|黎明";
		p = Pattern.compile(rules);
		m = p.matcher(s);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			m.appendReplacement(sb, "凌晨");
			result = m.find();
		}
		m.appendTail(sb);
		s = sb.toString();
		
		rules = "下周[^1234567]";
		p = Pattern.compile(rules);
		m = p.matcher(s);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			Log.logger.info("m.:" + m.group());
			m.appendReplacement(sb, "下周1到下周7");
			result = m.find();
		}
		m.appendTail(sb);
		s = sb.toString();

		
		rules = "上周[^1234567]";
		p = Pattern.compile(rules);
		m = p.matcher(s);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			Log.logger.info("m.:" + m.group());
			m.appendReplacement(sb, "上周1到上周7");
			result = m.find();
		}
		m.appendTail(sb);
		s = sb.toString();
/////////
		
		if("新闻".equals(domain))
			return wordTranslatorRev(s);
		else 
			return wordTranslatorFor(s);
	}
	public static String wordTranslatorRev(String target) {
		
		
		String rules = "最近6天|6天以内|6天内|近6天|这6天";
		Pattern p = Pattern.compile(rules);
		Matcher m = p.matcher(target);
		StringBuffer sb = new StringBuffer();
		boolean result = m.find();
		while (result) {
			m.appendReplacement(sb, "今天到5天前");
			result = m.find();
		}
		m.appendTail(sb);
		String s = sb.toString();
		
		
		rules = "最近5天|5天以内|5天内|近5天|这5天";
		p = Pattern.compile(rules);
		m = p.matcher(s);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			m.appendReplacement(sb, "今天到4天前");
			result = m.find();
		}
		m.appendTail(sb);
		s = sb.toString();
		
		rules = "最近4天|4天以内|4天内|近4天|这4天";
		p = Pattern.compile(rules);
		m = p.matcher(s);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			m.appendReplacement(sb, "今天到大前天");
			result = m.find();
		}
		m.appendTail(sb);
		s = sb.toString();
		
	
		
		rules = "近2天|这2天|最近2天|2天内|2天以内";
		p = Pattern.compile(rules);
		m = p.matcher(s);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			m.appendReplacement(sb, "今天到昨天");
			result = m.find();
		}
		m.appendTail(sb);
		s = sb.toString();
		
		rules = "最近7天|近1周|近7天|7天内|7天以内|这7天";
		p = Pattern.compile(rules);
		 m = p.matcher(s);
		sb = new StringBuffer();
		result = m.find();

		while (result) {
			m.appendReplacement(sb, "今天到6天前");
			result = m.find();
		}
		m.appendTail(sb);
		s = sb.toString();

		rules = "最近3天|最近|这3天|这些天|这几天|最近几天|3天以内|3天内|近3天|近几天";
		p = Pattern.compile(rules);
		m = p.matcher(s);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			m.appendReplacement(sb, "今天到前天");
			result = m.find();
		}
		m.appendTail(sb);
		s = sb.toString();
		
		return s;
	}
	
	public static String wordTranslatorFor(String target) {
		String  rules = "最近7天|近1周|近7天|7天以内|7天内|这7天";
		Pattern p = Pattern.compile(rules);
		Matcher m = p.matcher(target);
		StringBuffer sb = new StringBuffer();
		boolean result = m.find();

		while (result) {
			m.appendReplacement(sb, "今天到6天后");
			result = m.find();
		}
		m.appendTail(sb);
		String s = sb.toString();
		
		rules =  "近6天|最近6天|这6天|6天内|6天以内";
		p = Pattern.compile(rules);
		m = p.matcher(s);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			m.appendReplacement(sb, "今天到5天后");
			result = m.find();
		}
		m.appendTail(sb);
		s = sb.toString();
		
		rules = "最近5天|5天内|5天以内|近5天|这5天";
		p = Pattern.compile(rules);
		m = p.matcher(s);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			m.appendReplacement(sb, "今天到4天后");
			result = m.find();
		}
		m.appendTail(sb);
		s = sb.toString();
		
		rules = "最近4天|近4天|这4天|4天以内|4天内";
		p = Pattern.compile(rules);
		m = p.matcher(s);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			m.appendReplacement(sb, "今天到大后天");
			result = m.find();
		}
		m.appendTail(sb);
		s = sb.toString();
		
		
		
		rules = "这2天|最近2天|2天内|2天以内|近2天";
		p = Pattern.compile(rules);
		m = p.matcher(s);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			m.appendReplacement(sb, "今天到明天");
			result = m.find();
		}
		m.appendTail(sb);
		s = sb.toString();
		
		
		rules = "近3天|近几天|最近3天|最近|这3天|这些天|这几天|最近几天|3天以内|3天内";
		p = Pattern.compile(rules);
		m = p.matcher(s);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			m.appendReplacement(sb, "今天到后天");
			result = m.find();
		}
		m.appendTail(sb);
		s = sb.toString();
		
		return s;
		
	}

	/**
	 * 该方法可以将字符串中所有的用汉字表示的数字转化为用阿拉伯数字表示的数字 如"这里有一千两百个人，六百零五个来自中国"可以转化为
	 * "这里有1200个人，605个来自中国" 此外添加支持了部分不规则表达方法 如两万零六百五可转化为20650
	 * 两百一十四和两百十四都可以转化为214 一六零加一五八可以转化为160+158 该方法目前支持的正确转化范围是0-99999999
	 * 该功能模块具有良好的复用性
	 * 
	 * @param target
	 *            待转化的字符串
	 * @return 转化完毕后的字符串
	 */
	public static String numberTranslator(String target) {
		if(target==null||"".equals(target)){return "";}
		Pattern p = Pattern.compile("[一二两三四五六七八九123456789]亿[一二两三四五六七八九123456789](?!(万|千|百|十))");
		Matcher m = p.matcher(target);
		StringBuffer sb = new StringBuffer();
		boolean result = m.find();
		while (result) {
			String group = m.group();
			String[] s = group.split("亿");
			long num = 0;
			if (s.length == 2) {
				num += wordToNumber(s[0]) * 100000000 + wordToNumber(s[1]) * 10000000;
			}
			m.appendReplacement(sb, Long.toString(num));
			result = m.find();
		}
		m.appendTail(sb);
		target = sb.toString();
		
		p = Pattern.compile("[一二两三四五六七八九123456789]万[一二两三四五六七八九123456789](?!(千|百|十))");
		m = p.matcher(target);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			String group = m.group();
			String[] s = group.split("万");
			long num = 0;
			if (s.length == 2) {
				num += wordToNumber(s[0]) * 10000 + wordToNumber(s[1]) * 1000;
			}
			m.appendReplacement(sb, Long.toString(num));
			result = m.find();
		}
		m.appendTail(sb);
		target = sb.toString();

		p = Pattern.compile("[一二两三四五六七八九123456789]千[一二两三四五六七八九123456789](?!(百|十))");
		m = p.matcher(target);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			String group = m.group();
			String[] s = group.split("千");
			long num = 0;
			if (s.length == 2) {
				num += wordToNumber(s[0]) * 1000 + wordToNumber(s[1]) * 100;
			}
			m.appendReplacement(sb, Long.toString(num));
			result = m.find();
		}
		m.appendTail(sb);
		target = sb.toString();

		p = Pattern.compile("[一二两三四五六七八九123456789]百[一二两三四五六七八九123456789](?!十)");
		m = p.matcher(target);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			String group = m.group();
			String[] s = group.split("百");
			long num = 0;
			if (s.length == 2) {
				num += wordToNumber(s[0]) * 100 + wordToNumber(s[1]) * 10;
			}
			m.appendReplacement(sb, Long.toString(num));
			result = m.find();
		}
		m.appendTail(sb);
		target = sb.toString();

		p = Pattern.compile("[零一二两三四五六七八九]");
		m = p.matcher(target);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			m.appendReplacement(sb, Long.toString(wordToNumber(m.group())));
			result = m.find();
		}
		m.appendTail(sb);
		target = sb.toString();
		

		p = Pattern.compile("(?<=(周|星期|礼拜))[末天日]");
		m = p.matcher(target);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			m.appendReplacement(sb, Long.toString(wordToNumber(m.group())));
			result = m.find();
		}
		m.appendTail(sb);
		target = sb.toString();

		p = Pattern.compile("(?<!(周|星期))0?[0-9]?十[0-9]?");
		m = p.matcher(target);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			String group = m.group();
			String[] s = group.split("十");
			long num = 0;
			if (s.length == 0) {
				num += 10;
			} else if (s.length == 1) {
				long ten = Long.parseLong(s[0]);
				if (ten == 0)
					num += 10;
				else
					num += ten * 10;
			} else if (s.length == 2) {
				if (s[0].equals(""))
					num += 10;
				else {
					long ten = Long.parseLong(s[0]);
					if (ten == 0)
						num += 10;
					else
						num += ten * 10;
				}
				num += Long.parseLong(s[1]);
			}
			m.appendReplacement(sb, Long.toString(num));
			result = m.find();
		}
		m.appendTail(sb);
		target = sb.toString();

		p = Pattern.compile("0?[1-9]百[0-9]?[0-9]?");
		m = p.matcher(target);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			String group = m.group();
			String[] s = group.split("百");
			long num = 0;
			if (s.length == 1) {
				long hundred = Long.parseLong(s[0]);
				num += hundred * 100;
			} else if (s.length == 2) {
				long hundred = Long.parseLong(s[0]);
				num += hundred * 100;
				num += Long.parseLong(s[1]);
			}
			m.appendReplacement(sb, Long.toString(num));
			result = m.find();
		}
		m.appendTail(sb);
		target = sb.toString();

		p = Pattern.compile("0?[1-9]千[0-9]?[0-9]?[0-9]?");
		m = p.matcher(target);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			String group = m.group();
			String[] s = group.split("千");
			long num = 0;
			if (s.length == 1) {
				long thousand = Long.parseLong(s[0]);
				num += thousand * 1000;
			} else if (s.length == 2) {
				long thousand = Long.parseLong(s[0]);
				num += thousand * 1000;
				num += Long.parseLong(s[1]);
			}
			m.appendReplacement(sb, Long.toString(num));
			result = m.find();
		}
		m.appendTail(sb);
		target = sb.toString();

		p = Pattern.compile("[0-9]+万[0-9]?[0-9]?[0-9]?[0-9]?");
		m = p.matcher(target);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			String group = m.group();
			String[] s = group.split("万");
			long num = 0;
			if (s.length == 1) {
				long tenthousand = Long.parseLong(s[0]);
				num += tenthousand * 10000;
			} else if (s.length == 2) {
				long tenthousand = Long.parseLong(s[0]);
				num += tenthousand * 10000;
				num += Long.parseLong(s[1]);
			}
			m.appendReplacement(sb, Long.toString(num));
			result = m.find();
		}
		m.appendTail(sb);
		target = sb.toString();
		
		p = Pattern.compile("[0-9]+亿[0-9]?[0-9]?[0-9]?[0-9]?[0-9]?[0-9]?[0-9]?[0-9]?");
		m = p.matcher(target);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			String group = m.group();
			String[] s = group.split("亿");
			long num = 0;
			if (s.length == 1) {
				long tenthousand = Long.parseLong(s[0]);
				num += tenthousand * 100000000;
			} else if (s.length == 2) {
				long tenthousand = Long.parseLong(s[0]);
				num += tenthousand * 100000000;
				num += Long.parseLong(s[1]);
			}
			m.appendReplacement(sb, Long.toString(num));
			result = m.find();
		}
		m.appendTail(sb);
		target = sb.toString();

		return target;
		
	}

	/**
	 * 方法numberTranslator的辅助方法，可将[零-九]正确翻译为[0-9]
	 * 
	 * @param s
	 *            大写数字
	 * @return 对应的整形数，如果不是大写数字返回-1
	 */
	private static int wordToNumber(String s) {
		if (s.equals("零") || s.equals("0"))
			return 0;
		else if (s.equals("一") || s.equals("1"))
			return 1;
		else if (s.equals("二") || s.equals("两") || s.equals("2"))
			return 2;
		else if (s.equals("三") || s.equals("3"))
			return 3;
		else if (s.equals("四") || s.equals("4"))
			return 4;
		else if (s.equals("五") || s.equals("5"))
			return 5;
		else if (s.equals("六") || s.equals("6"))
			return 6;
		else if (s.equals("七") || s.equals("天") || s.equals("日") || s.equals("末") ||s.equals("7"))
			return 7;
		else if (s.equals("八") || s.equals("8"))
			return 8;
		else if (s.equals("九") || s.equals("9"))
			return 9;
		else
			return -1;
	}

	public static int o_wordToNumber(String s) {
		if (s.equals("零") || s.equals("0"))
			return 0;
		else if (s.equals("一") || s.equals("1"))
			return 1;
		else if (s.equals("二") || s.equals("两") || s.equals("2"))
			return 2;
		else if (s.equals("三") || s.equals("3"))
			return 3;
		else if (s.equals("四") || s.equals("4"))
			return 4;
		else if (s.equals("五") || s.equals("5"))
			return 5;
		else if (s.equals("六") || s.equals("6"))
			return 6;
		else if (s.equals("七") || s.equals("天") || s.equals("日") || s.equals("末") || s.equals("7"))
			return 7;
		else if (s.equals("八") || s.equals("8"))
			return 8;
		else if (s.equals("九") || s.equals("9"))
			return 9;
		else
			return -1;
	}
}
