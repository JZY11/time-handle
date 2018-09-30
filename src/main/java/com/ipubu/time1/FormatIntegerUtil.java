package com.ipubu.time1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName FoematIntegerUtil
 * @Description
 * @Author jzy
 */
public class FormatIntegerUtil {

	// static String[] units = { "", "十", "百", "千", "万", "十万", "百万", "千万", "亿",
		// "十亿", "百亿", "千亿", "万亿" };
		static String[] units = { "", "十", "", "", "万", "十万", "百万", "千万", "亿", "十亿", "百亿", "千亿", "万亿" };
		static char[] numArray = { '零', '一', '二', '三', '四', '五', '六', '七', '八', '九' };

		/**
		 * 阿拉伯数字转大写数字 1--> 一
		 * 
		 * @param num
		 * @return
		 */
		private static String foematInteger(String num) {
			// char[] val = String.valueOf(num).toCharArray();
			char[] val;
			char[] val2 = num.toCharArray();
			
			StringBuilder sb = new StringBuilder();
//			int len2 = val2.length;
//			if(len2>1& '0'== val2[0]){
//				 val=new char[len2-1];
//				for(int i=0;i<len2-1;i++){
//					val[i]=val2[i+1];
//				}
//			}else{
//				val=val2;
//			}
			val=deleteFristZero(val2);
			int len = val.length;
			if (len == 1 & '0' == val[0]) {
				return "零";
			} else {
				for (int i = 0; i < len; i++) {
					String m = val[i] + "";

					int n = Integer.valueOf(m);
//					Log.logger.info("n:" + n);
					boolean isZero = n == 0;
					String unit = units[(len - 1) - i];
//					Log.logger.info("unit:" + unit);
					if (isZero) {
						if ('0' == val[len - 1]) {
							// not need process if the last digital bits is 0
							continue;
						} else {
							// no unit for 0
							sb.append(numArray[n]);
//							Log.logger.info("numArray :" + numArray[n]);
						}
					} else {
						sb.append(numArray[n]);// Log.logger.info("numArray[n]:"+numArray[n]);
						sb.append(unit);
					}

				}
			}
			return sb.toString();
		}
}
