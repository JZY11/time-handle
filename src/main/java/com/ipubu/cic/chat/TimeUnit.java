package com.ipubu.cic.chat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ipubu.time1.RangeTimeEnum;
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
		norm_setCurRelated();
		norm_sethour();
		norm_setminute();
		norm_setsecond();
		norm_setTotal();
		modifyTimeBase();
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
		for (int i = 0; i < 6; i++)
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
			calendar.add(Calendar.DATE, -day);
		}
		
		rule = "[过|](\\d+(?=天[以之]?后))";
		pattern = Pattern.compile(rule);
		matcher = pattern.matcher(Time_Expression);
		if (matcher.find()) {
			flag[2] = true;
			int day = Integer.parseInt(matcher.group());
			calendar.add(Calendar.DATE, day);
		}
		
		rule = "(过)((\\d+)(天|日))";
		pattern = Pattern.compile(rule);
		matcher = pattern.matcher(Time_Expression);
		if (matcher.find()) {
			flag[2] = true;
			int day = Integer.parseInt(matcher.group(1));
			calendar.add(Calendar.DATE, day);
		}
		
		rule = "\\d+(?=周[以之]?前)";
		pattern = Pattern.compile(rule);
		matcher = pattern.matcher(Time_Expression);
		if (matcher.find()) {
			flag[1] = true;
			int week = Integer.parseInt(matcher.group());
			calendar.add(Calendar.WEEK_OF_YEAR, -week);
		}
		
		rule = "\\d+(?=周[以之]?后)";
		pattern = Pattern.compile(rule);
		matcher = pattern.matcher(Time_Expression);
		if (matcher.find()) {
			flag[1] = true;
			int week = Integer.parseInt(matcher.group());
			calendar.add(Calendar.WEEK_OF_YEAR, week);
		}
		
		rule = "\\d+(?=[个|]月[以之]?前)";
		pattern = Pattern.compile(rule);
		matcher = pattern.matcher(Time_Expression);
		if (matcher.find()) {
			flag[1] = true;
			int month = Integer.parseInt(matcher.group());
			calendar.add(Calendar.MONTH, -month);
		}
		
		rule = "\\d+(?=[个|]月[以之]?后)";
		pattern = Pattern.compile(rule);
		matcher = pattern.matcher(Time_Expression);
		if (matcher.find()) {
			flag[1] = true;
			int month = Integer.parseInt(matcher.group());
			calendar.add(Calendar.MONTH, month);
		}
		
		rule = "\\d+(?=年[以之]?前)";
		pattern = Pattern.compile(rule);
		matcher = pattern.matcher(Time_Expression);
		if (matcher.find()) {
			flag[0] = true;
			int year = Integer.parseInt(matcher.group());
			calendar.add(Calendar.YEAR, -year);
		}
		
		rule = "\\d+(?=年[以之]?后)";
		pattern = Pattern.compile(rule);
		matcher = pattern.matcher(Time_Expression);
		if (matcher.find()) {
			flag[0] = true;
			int year = Integer.parseInt(matcher.group());
			calendar.add(Calendar.YEAR, year);
		}
		
		String s = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(calendar.getTime());
		String[] time_constructs = s.split("-");
		if (flag[0] || flag[1] || flag[2]) {
			_tp.tunit[0] = Integer.parseInt(time_constructs[0]);
		}
		if (flag[1] || flag[2]) {
			_tp.tunit[1] = Integer.parseInt(time_constructs[1]);
		}
		if (flag[1] || flag[2]) {
			_tp.tunit[2] = Integer.parseInt(time_constructs[2]);
		}
	}
	
	/**
	 * 设置当前时间相关的时间表达式
	 * 
	 * @param 
	 * @return
	 */
	public void norm_setCurRelated() {
		String[] time_grid = new String[6];
		time_grid=normalizer.getOldTimeBase().split("-");
		int[] ini = new int[6];
		for (int i = 0; i < ini.length; i++) {
			ini[i] = Integer.parseInt(time_grid[i]);
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(ini[0], ini[1]-1, ini[2], ini[3], ini[4], ini[5]);
		calendar.getTime();
		
		boolean[] flag = {false,false,false};//观察时间表达式是否因当前相关时间表达式而改变时间
		
		String rule = "前年";
		Pattern pattern = Pattern.compile(rule);
		Matcher matcher = pattern.matcher(Time_Expression);
		if (matcher.find()) {
			flag[0] = true;
			calendar.add(Calendar.YEAR, -2);
		}
		
		rule = "去年";
		pattern = Pattern.compile(rule);
		matcher = pattern.matcher(Time_Expression);
		if (matcher.find()) {
			flag[0] = true;
			calendar.add(Calendar.YEAR, -1);
		}
		
		rule = "今年";
		pattern = Pattern.compile(rule);
		matcher = pattern.matcher(Time_Expression);
		if (matcher.find()) {
			flag[0] = true;
			calendar.add(Calendar.YEAR, 0);
		}
		
		rule = "明年";
		pattern = Pattern.compile(rule);
		matcher = pattern.matcher(Time_Expression);
		if (matcher.find()) {
			flag[0] = true;
			calendar.add(Calendar.YEAR, 1);
		}
		
		rule="后年";
		pattern=Pattern.compile(rule);
		matcher=pattern.matcher(Time_Expression);
		if(matcher.find()) {
			flag[0] = true;
			calendar.add(Calendar.YEAR, 2);
		}
		
		rule = "上[个|]月";
		pattern = Pattern.compile(rule);
		matcher = pattern.matcher(Time_Expression);
		if (matcher.find()) {
			flag[1] = true;
			calendar.add(Calendar.MONTH, -1);
		}
		
		rule = "(本|这个)月";
		pattern = Pattern.compile(rule);
		matcher = pattern.matcher(Time_Expression);
		if (matcher.find()) {
			flag[1] = true;
			calendar.add(Calendar.MONTH, 0);
		}
		
		rule = "下[个|]月";
		pattern = Pattern.compile(rule);
		matcher = pattern.matcher(Time_Expression);
		if (matcher.find()) {
			flag[1] = true;
			calendar.add(Calendar.MONTH, 1);
		}
		
		rule="大前天";
		pattern=Pattern.compile(rule);
		matcher=pattern.matcher(Time_Expression);
		if(matcher.find()) {
			flag[2] = true;
			calendar.add(Calendar.DATE, -3);
		}
		
		rule = "(?<!大)前天";
		pattern=Pattern.compile(rule);
		matcher=pattern.matcher(Time_Expression);
		if(matcher.find()) {
			flag[2] = true;
			calendar.add(Calendar.DATE, -2);
		}
		
		rule="昨";
		pattern=Pattern.compile(rule);
		matcher=pattern.matcher(Time_Expression);
		if(matcher.find()) {
			flag[2] = true;
			calendar.add(Calendar.DATE, -1);
		}
		
		rule = "今(?!年)";
		pattern = Pattern.compile(rule);
		matcher = pattern.matcher(Time_Expression);
		if (matcher.find()) {
			flag[2] = true;
			calendar.add(Calendar.DATE, 0);
		}
		
		rule = "明(?!年)";
		pattern = Pattern.compile(rule);
		matcher = pattern.matcher(Time_Expression);
		if (matcher.find()) {
			flag[2] = true;
			calendar.add(Calendar.DATE, 1);
		}
		
		rule = "(?<!大)后天";
		pattern = Pattern.compile(rule);
		matcher = pattern.matcher(Time_Expression);
		if (matcher.find()) {
			flag[2] = true;
			calendar.add(Calendar.DATE, 2);
		}
		
		rule = "大后天";
		pattern=Pattern.compile(rule);
		matcher=pattern.matcher(Time_Expression);
		if(matcher.find()) {
			flag[2] = true;
			calendar.add(Calendar.DATE, 3);
		}
		
		rule="(?<=(上上(周|星期|礼拜)))[1-7]";
		pattern=Pattern.compile(rule);
		matcher=pattern.matcher(Time_Expression);
		if(matcher.find()) {
			flag[2] = true;
			int week = Integer.parseInt(matcher.group());
			if(week == 7)
				week = 1;
			else 
				week++;
			calendar.add(Calendar.WEEK_OF_MONTH, -2);
			calendar.set(Calendar.DAY_OF_WEEK, week);
		}
		
		rule="(?<=((?<!下)下(周|星期)))[1-7]";
		pattern=Pattern.compile(rule);
		matcher=pattern.matcher(Time_Expression);
		if(matcher.find()) {
			flag[2] = true;
			int week = Integer.parseInt(matcher.group());
			if(week == 7)
				week = 1;
			else 
				week++;
			calendar.add(Calendar.WEEK_OF_MONTH, 1);
			calendar.set(Calendar.DAY_OF_WEEK, week);
		}
		
		rule="(?<=(下下(周|星期)))[1-7]";
		pattern=Pattern.compile(rule);
		matcher=pattern.matcher(Time_Expression);
		if(matcher.find()) {
			flag[2] = true;
			int week = Integer.parseInt(matcher.group());
			if(week == 7)
				week = 1;
			else 
				week++;
			calendar.add(Calendar.WEEK_OF_MONTH, 2);
			calendar.set(Calendar.DAY_OF_WEEK, week);
		}
		
		rule="(?<=((?<!(上|下))(周|星期)))[1-7]";
		pattern=Pattern.compile(rule);
		matcher=pattern.matcher(Time_Expression);
		if(matcher.find()) {
			flag[2] = true;
			int week = Integer.parseInt(matcher.group());
			if(week == 7)
				week = 1;
			else 
				week++;
			calendar.add(Calendar.WEEK_OF_MONTH, 0);
			calendar.set(Calendar.DAY_OF_WEEK, week);
			/**处理未来时间倾向 @author kexm*/
			//preferFutureWeek(week, calendar);
		}
		
		String s = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(calendar.getTime());
		String[] timeConstructs = s.split("-");
		if(flag[0]||flag[1]||flag[2]){
			_tp.tunit[0] = Integer.parseInt(timeConstructs[0]);
		}
		if(flag[1]||flag[2])
			_tp.tunit[1] = Integer.parseInt(timeConstructs[1]);
		if(flag[2])
			_tp.tunit[2] = Integer.parseInt(timeConstructs[2]);
		
	}
	
	/**
	 * 时 - 规范化方法
	 * @param 
	 * @return
	 */
	public void norm_sethour() {
		String rule = "(?<!(周|星期))([0-2]?[0-9])(?=(时|点))(前|之前|以前|后|之后|以后)?";
		Pattern pattern = Pattern.compile(rule);
		Matcher matcher = pattern.matcher(Time_Expression);
		if (matcher.find()) {
			_tp.tunit[3] = Integer.parseInt(matcher.group());
			isTimePoint = true;
			isAllDayTime = false;
		}
	}
	
	/**
	 * 分钟 - 规范化方法
	 * 该字段识别时间表达式单元的分字段
	 * @param 
	 * @return
	 */
	public void norm_setminute() {
		String rule = "([0-5]?[0-9](?=分(?!钟)))|((?<=((?<!小)[点时]))[0-5]?[0-9](?!刻))";
		Pattern pattern = Pattern.compile(rule);
		Matcher matcher = pattern.matcher(Time_Expression);
		if (matcher.find()) {
			_tp.tunit[4] = Integer.parseInt(matcher.group());
			isAllDayTime = false;
			isTimePoint = true;
		}
	}
	
	/**
	 * 秒 - 规范化方法
	 * 该字段识别时间表达式单元的分字段
	 * @param 
	 * @return
	 */
	public void norm_setsecond() {
		/*
		 * 添加了省略“分”说法的时间
		 * 如17点15分32
		 */
		
		String rule = "([0-5]?[0-9](?=秒))|((?<=分)[0-5]?[0-9])";
		Pattern pattern = Pattern.compile(rule);
		Matcher matcher = pattern.matcher(Time_Expression);
		if (matcher.find()) {
			_tp.tunit[5] = Integer.parseInt(matcher.group());
			isAllDayTime = false;
		}
	}
	
	/**
	 * 特殊形式的规范化方法
	 * 如：xx:yy:zz
	 * 该方法识别特殊形式的时间表达式单元的各个字段
	 * @param 
	 * @return
	 */
	public void norm_setTotal() {
		String rule;
		Pattern pattern;
		Matcher matcher;
		String[] tmp_parser;
		String tmp_target;
		
		rule = "(?<!(周|星期))([0-2]?[0-9]):[0-5]?[0-9]:[0-5][0-9]";
		pattern = Pattern.compile(rule);
		matcher = pattern.matcher(Time_Expression);
		if (matcher.find()) {
			tmp_parser = new String[3];
			tmp_target = matcher.group();
			tmp_parser = tmp_target.split(":");
			_tp.tunit[3]=Integer.parseInt(tmp_parser[0]);
			_tp.tunit[4]=Integer.parseInt(tmp_parser[1]);
			_tp.tunit[5]=Integer.parseInt(tmp_parser[2]);
			isAllDayTime = false;
		} else {
			rule="(?<!(周|星期))([0-2]?[0-9]):[0-5]?[0-9]";
			pattern=Pattern.compile(rule);
			matcher=pattern.matcher(Time_Expression);
			if (matcher.find()) {
				tmp_parser=new String[2];
				tmp_target=matcher.group();
				tmp_parser=tmp_target.split(":");
				_tp.tunit[3]=Integer.parseInt(tmp_parser[0]);
				_tp.tunit[4]=Integer.parseInt(tmp_parser[1]);
				isAllDayTime = false;
			}
		}
		
		/*
		 * 增加了:固定形式时间表达式的
		 * 中午,午间,下午,午后,晚上,傍晚,晚间,晚,pm,PM
		 * 的正确时间计算，规约同上
		 */
		rule = "(中午)|(午间)";
		pattern = Pattern.compile(rule);
		matcher = pattern.matcher(Time_Expression);
		if (matcher.find()) {
			if (_tp.tunit[3] > 0 && _tp.tunit[3] <= 10) {
				_tp.tunit[3] += 12;
			}
			if (_tp.tunit[3] == -1) { /** 增加了对没有明确时间点，但只写了"中午/午间"这种情况的处理 */
				_tp.tunit[3] = RangeTimeEnum.noon.getHourTime();
			}
			isAllDayTime = false;
		}
		
		rule = "(下午)|(午后)|(pm)|(PM)";
		pattern = Pattern.compile(rule);
		matcher = pattern.matcher(Time_Expression);
		if(matcher.find()){
			if(_tp.tunit[3] >= 0 && _tp.tunit[3] <= 11)
				_tp.tunit[3] += 12;
			if(_tp.tunit[3] == -1) /**增加对没有明确时间点，只写了“中午/午间”这种情况的处理 */
				_tp.tunit[3] = RangeTimeEnum.afternoon.getHourTime(); 
			/**处理倾向于未来时间的情况  */
			//preferFuture(3);
			isAllDayTime = false;
		}
		
		rule = "晚";
		pattern = Pattern.compile(rule);
		matcher = pattern.matcher(Time_Expression);
		if(matcher.find()){
			if(_tp.tunit[3] >= 1 && _tp.tunit[3] <= 11) {
				_tp.tunit[3] += 12;
			} else if(_tp.tunit[3] == 12) {
				_tp.tunit[3] = 0;
			}
			if(_tp.tunit[3] == -1) { /**增加对没有明确时间点，只写了“中午/午间”这种情况的处理 */
				_tp.tunit[3] = RangeTimeEnum.night.getHourTime(); 
			}
			/**处理倾向于未来时间的情况  */
			//preferFuture(3);
			isAllDayTime = false;
		}
		
		rule = "[0-9]?[0-9]?[0-9]{2}-((10)|(11)|(12)|([1-9]))-((?<!\\d))([0-3][0-9]|[1-9])";
		pattern = Pattern.compile(rule);
		matcher = pattern.matcher(Time_Expression);
		if(matcher.find()) {
			tmp_parser = new String[3];
			tmp_target = matcher.group();
			tmp_parser = tmp_target.split("-");
			_tp.tunit[0] = Integer.parseInt(tmp_parser[0]);
			_tp.tunit[1] = Integer.parseInt(tmp_parser[1]);
			_tp.tunit[2] = Integer.parseInt(tmp_parser[2]);
		}
		
		rule = "((10)|(11)|(12)|([1-9]))/((?<!\\d))([0-3][0-9]|[1-9])/[0-9]?[0-9]?[0-9]{2}";
		pattern = Pattern.compile(rule);
		matcher = pattern.matcher(Time_Expression);
		if(matcher.find()) {
			tmp_parser = new String[3];
			tmp_target = matcher.group();
			tmp_parser = tmp_target.split("/");
			_tp.tunit[1] = Integer.parseInt(tmp_parser[0]);
			_tp.tunit[2] = Integer.parseInt(tmp_parser[1]);
			_tp.tunit[0] = Integer.parseInt(tmp_parser[2]);
		}
		
		/*
		 * 增加了:固定形式时间表达式 年.月.日 的正确识别
		 * add by jzy
		 */
		rule = "[0-9]?[0-9]?[0-9]{2}\\.((10)|(11)|(12)|([1-9]))\\.((?<!\\d))([0-3][0-9]|[1-9])";
		pattern = Pattern.compile(rule);
		matcher = pattern.matcher(Time_Expression);
		if (matcher.find()) {
			tmp_parser = new String[3];
			tmp_target = matcher.group();
			tmp_parser = tmp_target.split("\\.");
			_tp.tunit[0] = Integer.parseInt(tmp_parser[0]);
			_tp.tunit[1] = Integer.parseInt(tmp_parser[1]);
			_tp.tunit[2] = Integer.parseInt(tmp_parser[2]);
		}
	}
	
	
	/**
	 * 
	 * 该方法用于更新timeBase使之具有上下文关联性
	 * @param 
	 * @return
	 */
	public void modifyTimeBase() {
		String [] time_grid=new String[6];
		time_grid=normalizer.getTimeBase().split("-");
		
		String s = "";
		if(_tp.tunit[0] != -1)
			s += Integer.toString(_tp.tunit[0]);
		else
			s += time_grid[0];
		for(int i = 1; i < 6; i++){
			s += "-";
			if(_tp.tunit[i] != -1)
				s += Integer.toString(_tp.tunit[i]);
			else
				s += time_grid[i];
		}
		normalizer.setTimeBase(s);
	}
	
	
}
