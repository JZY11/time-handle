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
		weekNum(2018,10,30);
		dayNum(2018,28);
		add(2018,10,30,1);
		getDaysBetween();
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
	
	/**
	 * 计算某一个月的天数是多少
	 * @param 
	 * @return
	 */
	public static void weekNum(int year,int month,int day){
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month-1);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		
		/*计算某一天是该年的第几个星期*/ 
		int weekNumberOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
		System.out.println(year+"年"+month+"月"+day+"日是这年中的第"+weekNumberOfYear+"个星期"); 
		
		/*计算某一天是该月的第几个星期*/
		int weekNumberOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);
		System.out.println(year+"年"+month+"月"+day+"日是这个月中的第"+weekNumberOfMonth+"个星期");
	}
	
	/**
	 * 计算一年中的第几星期是几号
	 * @param 
	 * @return
	 */
	public static void dayNum(int year,int week){ 
		calendar.clear();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.WEEK_OF_YEAR, week);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);  
	    System.out.println(df.format(calendar.getTime())); 
	}
	
	/**
	 * 查询显示当前的后几天，前几天等
	 * @param 
	 * @return
	 */
	public static void add(int year,int month,int day,int num){ 
		calendar.clear();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month-1);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		Date date = calendar.getTime();
		
		calendar.add(Calendar.DATE, num);
		date=calendar.getTime();  
        System.out.println(sdf.format(date)); 
	}
	
	/**
	 * 计算两个任意时间中间相隔的天数
	 * @param 
	 * @return
	 */
	public static void getDaysBetween (){
		Calendar day1 = Calendar.getInstance();
		Calendar day2 = Calendar.getInstance();
		day1.set(2018,10,2);
		day2.set(2018,10,30);
		if (day1.after(day2)) {
			Calendar swap = day1;  
            day1 = day2; 
            day2 = swap;
		}
		
		int days = day2.get(Calendar.DAY_OF_YEAR) - day1.get(Calendar.DAY_OF_YEAR);
		int y2 = day2.get(Calendar.YEAR);
		if (day1.get(Calendar.YEAR) != y2) {
			day1 = (Calendar) day1.clone();
			do {
				days += day1.getActualMaximum(Calendar.DAY_OF_YEAR);//得到当年的实际天数
				day1.add(Calendar.YEAR, 1);
			} while (day1.get(Calendar.YEAR) != y2);
		}
		System.out.println("相隔"+days+"天"); 
	}
}
