/**
 * 
 */
package com.ipubu.time;

import java.util.Arrays;

/**
 * @ClassName TimeTerm
 * @Description
 * @Author jzy
 */
public class TimeTerm {

	public final static String[] HOLIDAY = {"春节", "元宵节", "端午",
		"七夕", "中元", "中秋", "重阳", "腊8节", "小年", "除夕", "元旦", "情人节", "妇女节","下元",
		"植树节", "消费者权益日", "愚人节", "劳动节", "青年节", "护士节", "儿童节", "建党节", "建军节",
		"爸爸节", "教师节", "孔子诞辰", "国庆","国际奥林匹克日","世界艾滋病日", "老人节", "联合国日", "孙中山诞辰纪念日", "澳门回归纪念日",
		"平安夜","万圣节","圣诞","抗战胜利纪念日","寒食","龙抬头","光棍节","新年"};
	public final static String[] NEAR = {"最近","最初","附近"};
	public final static String[] START = {"除"};
	public final static String[] MIDDLE = {"除"};
	public final static String[] END = {"底","末","下旬"};
	public final static String[] NOW = {"现在","当下","当前","今","本"};
	public final static String[] LAST = {"过去","前"};
	public final static String[] UNIT = {"年","月","周","日","时","分","秒"};
	public final static String[] POPULARFESTIVAL = {"五一","十一","六一","五四"};
	
	static{
		Arrays.sort(HOLIDAY);  // Arrays里面有一个sort是针对数组排序的
	}
}
