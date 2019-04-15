package com.ipubu.cic.chat;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ipubu.time1.TimeNormalizer;

/**
 * <p>
 * 时间语句分析
 * <p>
 * @Description
 * @Author jzy
 */
public class TimeUnit {

	/**
	 * 目标字符串
	 */
	public String Time_Expression = null;
	public String time_Norm = "";
	public int[] time_full;
	public int[] time_origin;
	
	private Date date;
	private Boolean isAllDayTime = true;
	private Boolean isTimePoint = false;
	private Boolean isDate = false;
	private boolean isFirstTimeSolveContext = true;
	
	TimeNormalizer normalizer = null;
	public TimePoint _tp = new TimePoint();
	public TimePoint _tp_origin = new TimePoint();
	
	
	/**
	 * 时间表达式单元构造方法
	 * 该方法作为时间表达式单元的入口，将时间表达式字符串传入
	 * @param exp_time 时间表达式字符串
	 * @param n TimeNormalizer(定时器)
	 */
	public TimeUnit(String exp_time, TimeNormalizer n) {
		super();
		Time_Expression = exp_time;
		normalizer = n;
		Time_Normalization();
	}
	
	
	
	
	/**
     *时间表达式规范化的入口
     *
     *时间表达式识别后，通过此入口进入规范化阶段，
     *具体识别每个字段的值
     * 
     */
	public void Time_Normalization() {
		norm_setyear();	// 年份规范化
		norm_setmonth(); // 月份规范化
		norm_setday(); // 日规范化
		norm_setmonth_fuzzyday();
		norm_setBaseRelated();
	}


	/**
	 * 年 - 规范化方法
	 * @param 
	 * @return
	 */
	public void norm_setyear() {
		/** 加入只有两位数来表示年份 */
		String rule = "[0-9]{2}(?=年)"; // 表示两个数字后边紧跟着的就是"年"
		Pattern pattern = Pattern.compile(rule);
		Matcher matcher = pattern.matcher(Time_Expression);
		
		if (matcher.find()) {
			_tp.tunit[0] = Integer.parseInt(matcher.group()); // 将string转化为int
			if (_tp.tunit[0] >= 0 && _tp.tunit[0] < 100) {
				if (_tp.tunit[0] < 30) { // 30以下表示2000年以后的年份
					_tp.tunit[0] += 2000;
				} else { // 否则表示1900年以后的年份
					_tp.tunit[0] += 1900;
				}
			}
		}
		
		/** 不仅局限于支持1xxx年和2xxx年的识别，可识别三位数和四位数表示的年份 */
		rule = "[0-9]?[0-9]{3}(?=年)";
		pattern = Pattern.compile(rule);
		matcher = pattern.matcher(Time_Expression);
		if (matcher.find()) { // 如果有三位或者四位的年份，则覆盖原来2位数识别出的年份
			_tp.tunit[0] = Integer.parseInt(matcher.group());
		}
	}
	
	
	/**
	 * 月份 - 规范化方法
	 * @param 
	 * @return
	 */
	public void norm_setday() {
		String rule = "([0-9]|10|11|12)(?=月)"; // 表示月份：1-12 数字后紧跟着"月"
		Pattern pattern = Pattern.compile(rule);
		Matcher matcher = pattern.matcher(Time_Expression);
		
		if (matcher.find()) {
			_tp.tunit[1] = Integer.parseInt(matcher.group());
			
			/** 处理倾向于未来时间的处理 */
			// preferFuture(1);
		}
	}
	
	
	/**
	 * 日 - 规范化方法
	 * @param 
	 * @return
	 */
	public void norm_setmonth() {
		String rule = "((?<!\\d))([0-3][0-9]))(?=日|号)"; // 表示日：xx数字后紧跟着"日或者号"
		Pattern pattern = Pattern.compile(rule);
		Matcher matcher = pattern.matcher(Time_Expression);
		
		if (matcher.find()) {
			_tp.tunit[2] = Integer.parseInt(matcher.group());
			
			/** 处理倾向于未来时间的处理 */
			// preferFuture(1);
		}
	}
	
	/**
	 * 月-日兼容模糊写法
	 * 
	 * 该方法识别时间表达式单元的月、日字段
	 * 
	 * @param 
	 * @return
	 */
	public void norm_setmonth_fuzzyday() {
		String rule = "([0-9]|10|11|12)(月|\\.|\\-)([0-3][0-9]|[1-9])"; // 注意：\\.|\\-  不用转义
		Pattern pattern = Pattern.compile(rule);
		Matcher matcher = pattern.matcher(Time_Expression);
		
		if (matcher.find()) {
			String matchStr = matcher.group();
			Pattern p = Pattern.compile("(月|.|-)");
			Matcher m = p.matcher(matchStr);
			
			if (m.find()) {
				int splitIndex = m.start();
				String month = matchStr.substring(0, splitIndex);
				String date = matchStr.substring(splitIndex+1);
				_tp.tunit[1] = Integer.parseInt(month);
				_tp.tunit[2] = Integer.parseInt(date);
			}
		}
	}
	
	
	/**
	 * 设置以上文时间为基准的时间偏移计算
	 * 
	 * @param 
	 * @return
	 */
	public void norm_setBaseRelated() {
		String[] time_grid = new String[6]; // 定义一个容量为6的字符串数组
		time_grid = normalizer.getTimeBase().split("-");
		
		int[] ini = new int[6];
		for (int i = 0; i < 6; i++) {
			ini[i] = Integer.parseInt(time_grid[1]);
			
			Calendar calendar = Calendar.getInstance(); // 定义一个日历对象实例
			calendar.setFirstDayOfWeek(Calendar.MONDAY); // 设置一周的第一天为周一
			calendar.set(ini[0], ini[1]-1, ini[2], ini[3], ini[4], ini[5]); // 设置日历对象的 年、月、日、时、分、秒
			calendar.getTime(); // Date对象
			
			boolean[] flag = {false,false,false};//观察时间表达式是否因当前相关时间表达式而改变时间
			
			String rule = "\\d+(?=天[以之]前)";
			Pattern pattern = Pattern.compile(rule);
			Matcher matcher = pattern.matcher(Time_Expression);
			if (matcher.find()) {
				flag[2] = true;
				int day = Integer.parseInt(matcher.group());
				calendar.add(calendar.DATE, -day);
			}
			
			rule = "[过|](\\d+(?=天[以之]?后))";
			pattern = Pattern.compile(rule);
			matcher = pattern.matcher(Time_Expression);
			if (matcher.find()) {
				flag[2] = true;
				int day = Integer.parseInt(matcher.group());
				calendar.add(calendar.DATE, day);
			}
			
			rule = "(过)((\\d+)(天|日))";
			pattern = Pattern.compile(rule);
			matcher = pattern.matcher(Time_Expression);
			if (matcher.find()) {
				flag[2] = true;
				int day = Integer.parseInt(matcher.group(1));
				calendar.add(calendar.DATE, day);
			}
			
			rule = "\\d+(?=周[以之]?前)";
			pattern = Pattern.compile(rule);
			matcher = pattern.matcher(Time_Expression);
			if (matcher.find()) {
				flag[1] = true;
				int week = Integer.parseInt(matcher.group());
				calendar.add(calendar.WEEK_OF_MONTH, -week);
			}
			
		}
	}
	
	
}
