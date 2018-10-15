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

}
