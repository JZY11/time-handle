package com.ipubu.time1;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ipubu.util.Log;

/**
 * @ClassName TimeUnit
 * @Description	时间语句分析
 * @Author jzy
 */
public class TimeUnit {

	/**
	 * 目标字符串
	 */
	public String Time_Expression=null;
	public String Time_Norm="";
	public int[] time_full;
	public int[] time_origin;
	private Date time;
	private Boolean isAllDayTime = true;
	private Boolean isTimePoint = false;
	private Boolean isDate = false;
	private boolean isFirstTimeSolveContext = true;
	
	TimeNormalizer normalizer = null;
	public TimePoint _tp=new TimePoint();
	public TimePoint _tp_origin=new TimePoint();

	/**
	 * 时间表达式单元构造方法
     * 该方法作为时间表达式单元的入口，将时间表达式字符串传入
     * @param exp_time 时间表达式字符串 
	 * @param n
	 */
	
	public TimeUnit(String exp_time, TimeNormalizer n)
	{
		Time_Expression=exp_time;
		normalizer = n;
		Time_Normalization();
	}
	
	/**
	 * 时间表达式单元构造方法
     * 该方法作为时间表达式单元的入口，将时间表达式字符串传入
     * @param exp_time 时间表达式字符串 
	 * @param n
	 * @param contextTp 上下文时间
	 */
	
	public TimeUnit(String exp_time, TimeNormalizer n, TimePoint contextTp)
	{
		Time_Expression=exp_time;
		normalizer = n;
		_tp_origin = contextTp;
		Time_Normalization();
	}
	
	/**
	 * return the accurate time object
	 */
    public Date getTime() {
        return time;
    }
    
	/**
     *年-规范化方法
     *
     *该方法识别时间表达式单元的年字段
     * 
     */
	public void norm_setyear()
	{
		/**假如只有两位数来表示年份*/
		String rule="[0-9]{2}(?=年)";
		Pattern pattern=Pattern.compile(rule);
		Matcher match=pattern.matcher(Time_Expression);
		if(match.find())
		{
			_tp.tunit[0]=Integer.parseInt(match.group());
			if(_tp.tunit[0] >= 0 && _tp.tunit[0] < 100){
				if(_tp.tunit[0]<30) /**30以下表示2000年以后的年份*/
					_tp.tunit[0] += 2000;
				else/**否则表示1900年以后的年份*/
					_tp.tunit[0] += 1900;
			}
			
		}
		/**不仅局限于支持1XXX年和2XXX年的识别，可识别三位数和四位数表示的年份*/
		rule="[0-9]?[0-9]{3}(?=年)";
		
		pattern=Pattern.compile(rule);
		match=pattern.matcher(Time_Expression);
		if(match.find())/**如果有3位数和4位数的年份，则覆盖原来2位数识别出的年份*/
		{
			_tp.tunit[0]=Integer.parseInt(match.group());
		}
	}
	/**
     *月-规范化方法
     *
     *该方法识别时间表达式单元的月字段
     * 
     */
	public void norm_setmonth()
	{
		String rule="((10)|(11)|(12)|([1-9]))(?=月)";
		Pattern pattern=Pattern.compile(rule);
		Matcher match=pattern.matcher(Time_Expression);
		if(match.find())
		{
			_tp.tunit[1]=Integer.parseInt(match.group());
			
			/**处理倾向于未来时间的情况  @author kexm*/
			//preferFuture(1);
		}
	}
	
	/**
     *月-日 兼容模糊写法
     *
     *该方法识别时间表达式单元的月、日字段
     *
     *add by kexm
     * 
     */
	public void norm_setmonth_fuzzyday()
	{
		String rule="((10)|(11)|(12)|([1-9]))(月|\\.|\\-)([0-3][0-9]|[1-9])";
		Pattern pattern=Pattern.compile(rule);
		Matcher match=pattern.matcher(Time_Expression);
		if(match.find())
		{
			String matchStr = match.group();
			Pattern p = Pattern.compile("(月|\\.|\\-)");
			Matcher m = p.matcher(matchStr);
			if(m.find()){
				int splitIndex = m.start();
				String month = matchStr.substring(0, splitIndex);
				String date = matchStr.substring(splitIndex+1);
				
				_tp.tunit[1]=Integer.parseInt(month);
				_tp.tunit[2]=Integer.parseInt(date);
				
				/**处理倾向于未来时间的情况  @author kexm*/
//				//preferFuture(1);
			}
		}	
	}
}
