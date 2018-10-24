/**
 * 
 */
package com.ipubu.time;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName StringPreHandlingModule
 * @Description
 * @Author jzy
 */
public class StringPreHandlingModule {

	public static String ss = "把我的五十六亿三千万还给我"; // 一亿三可以匹配到，但是一亿三千万就匹配不到了
	public static void main(String[] args) {
		chineseNumeralsToArabiaNumbers(ss);
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
	public static String chineseNumeralsToArabiaNumbers(String target) {
		if (target == null || "".equals(target)) {
			return "";
		}
		Pattern p = Pattern.compile("[一二两三四五六七八九123456789]亿[一二两三四五六七八九123456789](?!(万|千|百|十))");
		Matcher m = p.matcher(target);			// (?!pattern)：正向否定预查，在任何不匹配pattern的字符串开始处匹配查找字符串这是一个非获取匹配，也就是说，该匹配不需要获取供以后使用
		StringBuffer sb = new StringBuffer();	// 例如:“Windows(?!95|98|NT|2000)”能匹配“Windows3.1”中的“Windows”，但不能匹配“Windows2000”中的“Windows”
		boolean result = m.find();
		while (result) {
			String group = m.group();  // group是针对（）来说的，group（0）就是指的整个串，group（1） 指的是第一个括号里的东西，group（2）指的第二个括号里的东西
			System.out.println(group);
			String[] s = group.split("亿");
			long num = 0;
			if (s.length == 2) {
				num += wordToNumber(s[0]) * 100000000 + wordToNumber(s[1]) * 10000000;
			}
			m.appendReplacement(sb, Long.toString(num));
			result = m.find();
			/**
			 * Matcher 类同时提供了四个将匹配子串替换成指定字符串的方法：replaceAll()、replaceFirst()、appendReplacement()、appendTail()
			 * 		   appendReplacement(StringBuffer sb, String replacement) 将当前匹配子串替换为指定字符串，并且将替换后的子串以及其之前到上次匹配子串之后的字符串段添加到一个 StringBuffer 对象里，
			 * 		        而 appendTail(StringBuffer sb) 方法则将最后一次匹配工作后剩余的字符串添加到一个 StringBuffer 对象里。
			 */
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
		
		
		p = Pattern.compile("(百分之)([一二三四五六七八九十0-9]+)");
		m = p.matcher(target);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			System.out.println(m.group(2));
			if (m.group(2).length() == 2) {
				char[] charArray = m.group(2).toCharArray();
				int first = wordToNumber(String.valueOf(charArray[0]));
				int second = wordToNumber(String.valueOf(charArray[1]));
				int account = first + second;
				String all = String.valueOf(account);
				m.appendReplacement(sb, all);
			}
			m.appendReplacement(sb, Long.toString(wordToNumber(m.group(2))));
			result = m.find();
		}
		m.appendTail(sb);
		target = sb.toString();
		
		p = Pattern.compile("(?<=(周|星期|礼拜))[末天日]"); // (?<!pattern):反向否定预查，与正向否定预查类似，只是方向相反
		m = p.matcher(target);						  // 例如“(?<!95|98|NT|2000)Windows”能匹配“3.1Windows”中的“Windows”，但不能匹配“2000Windows”中的“Windows”。
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			m.appendReplacement(sb, Long.toString(wordToNumber(m.group())));
			result = m.find();
		}
		m.appendTail(sb);
		target = sb.toString();
		
		return target;
	}	
}
