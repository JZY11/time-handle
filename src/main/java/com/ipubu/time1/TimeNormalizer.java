package com.ipubu.time1;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.ipubu.util.Log;

/**
 * @ClassName TimeNormalizer
 * @Description	时间表达式识别的主要工作类
 * @Author jzy
 */
public class TimeNormalizer {

	private static final long serialVersionUID = 463541045644656392L;
	private String timeBase;
	private String oldTimeBase;
	private static Pattern patterns = null;
	private String target;
	private TimeUnit[] timeToken = new TimeUnit[0];

	private boolean isPreferFuture = true;

	public TimeNormalizer() {
	}

	/**
	 * 参数为TimeExp.m文件路径
	 * 
	 * @param path
	 */
	public TimeNormalizer(final String path) {
		if (patterns == null) {
			try {
				patterns = readModel(path);
				
				patterns=Pattern.compile(("((每((周|礼拜|星期)([0-9一二三四五六七壹贰叁肆伍陆柒日末])))+和?(每?((周|礼拜|星期)([0-9一二三四五六七壹贰叁肆伍陆柒日末]))+)+)|(每(([周]{1,2}|礼拜|星期)([0-9一二三四五六七壹贰叁肆伍陆柒日末])日?)+)|每[日|天]|(过)((\\d+)天)|"
						+patterns.pattern()
						+"|((\\d+)(秒|分钟|小时|天|周)(前|后|之前|之后))|(今儿)|(明儿)|(半夜)|(期间)|(每周日)").replace("|(农历)", 
						"").replace("|((北京|那个|更长的|最终冲突的)时间)", 
						"").replace("((早|晚)?(\\d+[时点](\\d+)?分?(\\d+秒?)?)\\s*(am|AM|pm|PM)?)",
						"((早|晚)?(\\d+[时点](钟)?(\\d+)?分?(\\d+秒?)?)\\s*(am|AM|pm|PM)?)".replace("|([当前昨今明后春夏秋冬]+天)",
						"")).replace("(\\d+个?(小时|星期))|",
						"").replace("|(([零一二三四五六七八九十百千万]+|\\d+)个星期)",
						"").replace("((\\d+)个星期)|",
						"").replace("|(星期)", 
						"").replace("|((数|多|多少|好几|几|差不多|近|前|后|上|左右)星期)", 
						"").replace("((\\d[\\.\\-])?((10)|(11)|(12)|([1-9]))[\\.\\-](\\d+))|", 
						"").replace("|([当前昨今明后春夏秋冬]+天)",
						"|([当前昨今明后]+天)").replace("|(([零一二三四五六七八九十百千万]+|\\d+)周)",
						""));
				Log.logger.info("re "+patterns.pattern());
			} catch (final Exception e) {
				Log.logger.error("Exception",e);
				System.err.print("Read model error!");
			}
		}
	}


	/**
	 * 参数为TimeExp.m文件路径
	 * 
	 * @param path
	 */
	public TimeNormalizer(final String path, final boolean isPreferFuture) {
		this.isPreferFuture = isPreferFuture;
		if (patterns == null) {
			try {
				patterns = readModel(path);
				patterns=Pattern.compile(("((每((周|礼拜|星期)([0-9一二三四五六七壹贰叁肆伍陆柒日末])))+和?(每?((周|礼拜|星期)([0-9一二三四五六七壹贰叁肆伍陆柒日末]))+)+)|(每(([周]{1,2}|礼拜|星期)([0-9一二三四五六七壹贰叁肆伍陆柒日末])日?)+)|每[日|天]"+patterns.pattern()+"|((\\d)(秒|分钟|小时|天|周)(前|后|之前|之后))|(今儿)|(明儿)|(半夜)|(期间)|(每周日)").replace("|(星期)", "").replace("|(农历)", "").replace("|((北京|那个|更长的|最终冲突的)时间)", "").replace("((早|晚)?(\\d+[时点](\\d+)?分?(\\d+秒?)?)\\s*(am|AM|pm|PM)?)", "((早|晚)?(\\d+[时点](钟)?(\\d+)?分?(\\d+秒?)?)\\s*(am|AM|pm|PM)?)").replaceAll("|([当前昨今明后春夏秋冬]+天)", ""));
			} catch (final Exception e) {
				Log.logger.error("Exception",e);
				System.err.print("Read model error!");
			}
		}
	}

	/**
	 * TimeNormalizer的构造方法，根据提供的待分析字符串和timeBase进行时间表达式提取
	 * 在构造方法中已完成对待分析字符串的表达式提取工作
	 * 
	 * @param target
	 *            待分析字符串
	 * @param timeBase
	 *            给定的timeBase
	 * @return 返回值
	 */

	public TimeUnit[] parse(final String target, final String timeBase,String domain) {
		this.target = target;
		this.timeBase = timeBase;
		this.oldTimeBase = timeBase;
		// 字符串预处理
//		preHandling();
		timeToken = TimeEx(this.target, timeBase,null);
		return timeToken;
	}

	/**
	 * 同上的TimeNormalizer的构造方法，timeBase取默认的系统当前时间
	 * 
	 * @param target
	 *            待分析字符串
	 * @return 时间单元数组
	 */
	public TimeUnit[] parse(final String target, String domain) {
		this.target = target;
		this.timeBase = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(Calendar.getInstance().getTime());// TODO
		this.oldTimeBase = timeBase;
		preHandling();// 字符串预处理
		timeToken = TimeEx(this.target, timeBase,domain);
		return timeToken;
	}

	/**
	 * timeBase的get方法
	 * 
	 * @return 返回值
	 */
	public String getTimeBase() {
		return timeBase;
	}

	/**
	 * oldTimeBase的get方法
	 * 
	 * @return 返回值
	 */
	public String getOldTimeBase() {
		return oldTimeBase;
	}

	public boolean isPreferFuture() {
		return isPreferFuture;
	}

	public void setPreferFuture(final boolean isPreferFuture) {
		this.isPreferFuture = isPreferFuture;
	}

	/**
	 * timeBase的set方法
	 * 
	 * @param s
	 *            timeBase
	 */
	public void setTimeBase(final String s) {
		timeBase = s;
	}

	/**
	 * 重置timeBase为oldTimeBase
	 * 
	 */
	public void resetTimeBase() {
		timeBase = oldTimeBase;
	}

	/**
	 * 时间分析结果以TimeUnit组的形式出现，此方法为分析结果的get方法
	 * 
	 * @return 返回值
	 */
	public TimeUnit[] getTimeUnit() {
		return timeToken;
	}

	/**
	 * 待匹配字符串的清理空白符和语气助词以及大写数字转化的预处理
	 */
	private void preHandling() {
		target = StringPreHandlingModule.delKeyword(target, "\\s+"); // 清理空白符
		// Log.logger.info("清理空白符："+target);
//		target = stringPreHandlingModule.delKeyword(target, "[的]+"); // 清理语气助词
		// Log.logger.info("清理语气助词："+target);
//		target = stringPreHandlingModule.numberTranslator(target);// 大写数字转化
//		// Log.logger.info("大写数字转化："+target);
//		target = stringPreHandlingModule.wordTranslator(target); // 替换 今明天随后几天
		// Log.logger.info("替换 ："+target);
		// TODO 处理大小写标点符号
	}

	/**
	 * 有基准时间输入的时间表达式识别
	 *
	 * 这是时间表达式识别的主方法， 通过已经构建的正则表达式对字符串进行识别，并按照预先定义的基准时间进行规范化
	 * 将所有别识别并进行规范化的时间表达式进行返回， 时间表达式通过TimeUnit类进行定义
	 *
	 *
	 * @param String
	 *            输入文本字符串
	 * @param String
	 *            输入基准时间
	 * @return TimeUnit[] 时间表达式类型数组
	 * 
	 */

	private TimeUnit[] TimeEx(String tar, final String timebase,String domain) {
		
		tar = StringPreHandlingModule.holidaytrans(tar);//五一、十一转换
		tar = StringPreHandlingModule.numberTranslator(tar);// 大写数字转化
//		 Log.logger.info("替换前："+tar);
		if(StringUtil.seriesNum(tar)>15){
			return  new TimeUnit[0];
		}
		tar = StringPreHandlingModule.wordTranslator(tar,domain); // 替换 今明天随后几天
//		Log.logger.info("替换后："+tar);
		Matcher match;

		int startline = -1, endline = -1;

		final String[] temp = new String[99];
		int rpointer = 0;// 计数器，记录当前识别到哪一个字符串了
		TimeUnit[] Time_Result = null;
		match = patterns.matcher(tar);
		
		boolean startmark = true;
		while (match.find()) {		
//			Log.logger.info("match.:"+match.group());
			startline = match.start();
			if (endline == startline) // 假如下一个识别到的时间字段和上一个是相连的 @author kexm
			{
				rpointer--;
				temp[rpointer] = temp[rpointer] + match.group();// 则把下一个识别到的时间字段加到上一个时间字段去
			} else {
				if (!startmark) {
					rpointer--;
					rpointer++;
				}
				startmark = false;
				temp[rpointer] = match.group();// 记录当前识别到的时间字段，并把startmark开关关闭。这个开关貌似没用？
//				Log.logger.info("temp[rpointer] .:"+temp[rpointer] );
			}
			endline = match.end();
			rpointer++;
		}

		////////////////////////////////////////////////////////////////
		int rpointer_origion = rpointer;
		int dao = -1;
		for (int j = 0; j < rpointer - 1; j++) {
			final String time_to_time = (temp[j] + "到|至" + temp[j + 1]);
			final Pattern pt = Pattern.compile(time_to_time);
			final Matcher matcher = pt.matcher(tar);
			// while(matcher.find()){
			if (matcher.find()) {
				rpointer++;
				dao = j;
				break;
			}
		}
		// 添加一个时间段标志
		if (rpointer > rpointer_origion) {
			for (int j = rpointer - 2; j >= 0; j--) {
				if (j >= dao)
					temp[j + 1] = temp[j];
			}
		}
		// 检查时间字段的识别
		for (int j = 0; j < rpointer; j++) {
//			Log.logger.info("temp[" + j + "]:" + temp[j]);
		}


		Time_Result = new TimeUnit[rpointer];
		/**
		 * 时间上下文： 前一个识别出来的时间会是下一个时间的上下文，用于处理：周六3点到5点这样的多个时间的识别，第二个5点应识别到是周六的。
		 */
		TimePoint contextTp = new TimePoint();
		for (int j = 0; j < rpointer; j++) {
			Time_Result[j] = new TimeUnit(temp[j], this, contextTp);
			contextTp = Time_Result[j]._tp;
//			Log.logger.info("temp[j]:"+temp[j]);
		}
//		Log.logger.info("temp:"+Time_Result[0]);
		////////////////////////////////////////////////////////////////////////
		/** 过滤无法识别的字段 */
		Time_Result = filterTimeUnit(Time_Result);
		return Time_Result;
	}


}
