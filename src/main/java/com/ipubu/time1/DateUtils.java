package com.ipubu.time1;

import java.util.Calendar;
import java.util.Date;

import com.ipubu.util.Log;

/**
 * @ClassName DateUtils
 * @Description
 * @Author jzy
 */
public class DateUtils {

	// 日期转化为大小写
    public static String dataToUpper(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        int year = ca.get(Calendar.YEAR);
        int month = ca.get(Calendar.MONTH) + 1;
        int day = ca.get(Calendar.DAY_OF_MONTH);
        return numToUpper(year) + "年" + monthToUppder(month) + "月" + dayToUppder(day) + "日";
    }

    /***
     * <b>function:</b> 将数字转化为大写
     * @createDate 2010-5-27 上午10:28:12
     * @param num 数字
     * @return 转换后的大写数字
     */
    public static String numToUpper(int num) {
        // String u[] = {"零","壹","贰","叁","肆","伍","陆","柒","捌","玖"};
        //String u[] = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        String u[] = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        char[] str = String.valueOf(num).toCharArray();
        String rstr = "";
        for (int i = 0; i < str.length; i++) {
            rstr = rstr + u[Integer.parseInt(str[i] + "")];
        }
        return rstr;
    }

    /***
     * <b>function:</b> 月转化为大写
     * @createDate 2010-5-27 上午10:41:42
     * @param month 月份
     * @return 返回转换后大写月份
     */
    public static String monthToUppder(int month) {
        if (month < 10) {
            return numToUpper(month);
        } else if (month == 10) {
            return "十";
        } else {
            return "十" + numToUpper(month - 10);
        }
    }

}
