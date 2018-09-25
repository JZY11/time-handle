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

	// 中文数字转阿拉伯数字
	public static String numReplace(String text) {
		String[] zh = { "三十一", "三十", "二十九", "二十八", "二十七", "二十六", "二十五", "二十四",
				"二十三", "二十二", "二十一", "二十", "十九", "十八", "十七", "十六", "十五", "二十四",
				"十三", "十二", "十一", "十", "九", "八", "七", "六", "五", "四", "三", "二",
				"一", "零" };
		if (text != null) {
			for (int i = 0; i < zh.length; i++) {
				String s = (31-i)+"";
				text = text.replaceAll(zh[i], s);
			}
		}
		return text;
	}

	// 中文数字转阿拉伯数字
	public static int chineseNumber2Int(String chineseNumber) {
		int result = 0;
		int temp = 1;// 存放一个单位的数字如：十万
		int count = 0;// 判断是否有chArr
		char[] cnArr = new char[] { '一', '二', '三', '四', '五', '六', '七', '八', '九' };
		char[] chArr = new char[] { '十', '百', '千', '万', '亿' };
		for (int i = 0; i < chineseNumber.length(); i++) {
			boolean b = true;// 判断是否是chArr
			char c = chineseNumber.charAt(i);
			for (int j = 0; j < cnArr.length; j++) {// 非单位，即数字
				if (c == cnArr[j]) {
					if (0 != count) {// 添加下一个单位之前，先把上一个单位值添加到结果中
						result += temp;
						temp = 1;
						count = 0;
					}
					// 下标+1，就是对应的值
					temp = j + 1;
					b = false;
					break;
				}
			}
			if (b) {// 单位{'十','百','千','万','亿'}
				for (int j = 0; j < chArr.length; j++) {
					if (c == chArr[j]) {
						switch (j) {
						case 0:
							temp *= 10;
							break;
						case 1:
							temp *= 100;
							break;
						case 2:
							temp *= 1000;
							break;
						case 3:
							temp *= 10000;
							break;
						case 4:
							temp *= 100000000;
							break;
						default:
							break;
						}
						count++;
					}
				}
			}
			if (i == chineseNumber.length() - 1) {// 遍历到最后一个字符
				result += temp;
			}
		}
		return result;
	}
}
