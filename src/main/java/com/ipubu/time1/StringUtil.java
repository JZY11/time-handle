package com.ipubu.time1;

/**
 * @ClassName StringUtil
 * @Description		字符串工具类
 * @Author jzy
 */
public class StringUtil {

	/**
	 * 判断字符串是否为空
	 * @param 	字符串
	 * @return	返回一个boolean值
	 */
	public static boolean isEmpty(String str){
		return (str == null || str.trim().length() == 0);
	}
	
	/**
	 * 将字符串变为字符数组，然后对字符数组进行循环来判断当前的字符是否为数字
	 * @param 一个字符串对象
	 * @return
	 */
	public static int seriesNum(String str){
		if (str == null || str.trim().length() == 0) {
			return 0;
		}
		int count = 0;
		char[] chars = str.toCharArray();
		for (char c : chars) {
			if (Character.isDigit(c)) {	// 该方法用来判断指定字符是否为数字
				count++;
			}else{
				count=0;
			}
		}
		
		return count;
	}
}
