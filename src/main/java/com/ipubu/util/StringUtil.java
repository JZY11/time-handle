package com.ipubu.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ipubu.grammar.util.Constants;

/**
 * @ClassName StringUtil
 * @Description
 * @Author jzy
 */
public final class StringUtil {

	public static List<String> stopWords = new ArrayList<String>(); // 声明及初始化一个filed(变量)
	
	// 下面是一个静态块，加载到这个类时就会执行，比该类的构造方法都要先执行
	static{
		if (stopWords.size() == 0 || stopWords == null) {
			try {
				stopWords=FileUtil.readStringFromFile(Constants.dataDir+"stopWords.txt");
					Collections.sort(stopWords, new Comparator<String>(){
						public int compare(String o1, String o2) {
							return Integer.compare(o2.length(), o1.length());
						}
					});
			} catch (IOException e) {
				Log.logger.error("IOException",e);
			}
		}
	}
	
	public static String regYear = "([0-9]{2,4}年|[一二三四五六七八九零]{2,4}年)";
    public static String regMonth = "([1-9]月|10月|11月|12月|[一二三四五六七八九十]月|十一月|十二月)";
	public static String regDay = "([1-2]?[0-9][日号]|30[日号]|31[日号]" +
			 "|[一二三四五六七八九十][日号]|二?十[一二三四五六七八九]?[日号]|三十[日号]|三十一[日号])";
	public static String regweekday = "((上上|上|本|下|下下)?(周|礼拜|星期)(一|二|三|四|五|六|日|天|末|1|2|3|4|5|6|7)?)";

    public static String regMonthDay = "(" + regMonth + regDay + ")";
    public static String regYearMonth = "(" + regYear + regMonth + ")";

    public static String regYearMonthDay = "(" + regYear + regMonth + regDay + ")";

    public static String regDate = "(" + regYearMonthDay + "|"
            + regYearMonth + "|" + regMonthDay + "|"  // + regYear + "|"
            + regMonth + "|" + regDay + ")";

	public static boolean hasMatch(String text, String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(text);
		return m.find();
	}
	
	public static String getFirstMatched(String text, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(text);
        if (m.find())
            return m.group();
        else
            return null;
    }

	public static String toSqlString(String s){
		if(s!=null)
			return "'" + s + "'";
		return null;
	}
	public static List<List<String>> getAllMatchedDetail(String text, String regex) {
        Pattern pat = Pattern.compile(regex);
        Matcher mat = pat.matcher(text);
        List<List<String>> result = new LinkedList<List<String>>();

        while (mat.find()) {
            List<String> element = new ArrayList<String>(mat.groupCount());
            for (int i = 1; i <= mat.groupCount(); i++) {
                element.add(mat.group(i));
            }
            result.add(element);
        }

        return result;
    }

	public static List<String> getAllmatched(String text, String regex) {
		Pattern pat = Pattern.compile(regex);
		Matcher mat = pat.matcher(text);
		List<String> result = new LinkedList<String>();
		while (mat.find()) {
			result.add(mat.group(0));
		}

		return result;
	}
}
