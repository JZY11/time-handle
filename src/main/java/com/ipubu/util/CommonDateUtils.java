package com.ipubu.util;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ipubu.time.StringPreHandlingModule;

/**
 * @ClassName CommonDateUtils
 * @Description   通用日期工具
 * @Author jzy
 */
public class CommonDateUtils {

	private static String defaultDatePattern = "yyyy-MM-dd HH:mm:ss";
	public static final long ONE_MINUTE_MILLISECOND = 60000L;
	public static final long ONE_HOUR_MILLISECOND = 3600000L;
	public static final long ONE_DAY_MILLISECOND = 86400000L;
	public static final long ONE_WEEK_MILLISECOND = 604800000L;
	
	public final static String[] HOLIDAY = { "小寒", "大寒", "立春", "雨水", "惊蛰",
		"春分", "清明", "谷雨", "立夏", "小满", "芒种", "夏至", "小暑", "大暑", "立秋", "处暑",
		"白露", "秋分", "寒露", "霜降", "立冬", "小雪", "大雪", "冬至", "春节", "元宵", "端午",
		"七夕", "中元", "中秋", "重阳", "腊8节", "小年", "除夕", "元旦", "情人节", "妇女节","下元",
		"植树节", "消费者权益日", "愚人节", "劳动节", "青年节", "护士节", "儿童节", "建党节", "建军节",
		"爸爸节", "教师节", "孔子诞辰", "国庆","国际奥林匹克日","世界艾滋病日", "老人节", "联合国日", "孙中山诞辰纪念", "澳门回归纪念",
		"平安夜","万圣节","圣诞","抗战胜利纪念日","感恩节", "父亲节", "母亲节"};
	//时间类型格式化
	public static String formattype(String type){
		// Todo
		
		return null;
	}
}
