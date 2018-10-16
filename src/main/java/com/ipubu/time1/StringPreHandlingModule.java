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
}
