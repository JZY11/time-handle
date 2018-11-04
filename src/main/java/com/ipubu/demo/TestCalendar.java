package com.ipubu.demo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @ClassName TestCalendar
 * @Description
 * @Author jzy
 */
public class TestCalendar {

	static Calendar calendar = Calendar.getInstance();
	
	public static void main(String[] args) {
		getTest();
		maxDay(2018,10);
	}
	
	public static void getTest(){
		calendar.setTime(new Date());
		
		/*获取今天的日期*/
		System.out.println("今天的日期是：" + calendar.get(Calendar.DAY_OF_MONTH));
		
		/*获取十天后的日期*/
		calendar.clear(); // 避免继承当前系统的时间
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)+11);
		System.out.println("十天之后的日期是："+calendar.get(Calendar.DAY_OF_MONTH)); 
	}
	
	/**
	 * 计算某一个月的天数是多少
	 * @param 
	 * @return
	 */
	public static void maxDay(int year,int month){ 
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH,month-1);//默认1月为0月 
		int dayNumber = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		System.out.println(year+"年"+month+"月"+"的最大天数是："+dayNumber);
	}
	
}
