/**
 * 
 */
package com.ipubu.util;

import com.ipubu.time.StringPreHandlingModule;

/**
 * @ClassName HandlingPercentSign
 * @Description  该类是用来处理百分号的,如：百分之十五  -> 15%
 * @Author jzy
 */
public class HandlingPercentSign {
	public static String setence = "我就想要知道年底能不能下降百分之十五";

	public static void main(String[] args) {
		handlingPercentSign(setence);
	}
	
	public static String handlingPercentSign(String setence){
		setence = StringPreHandlingModule.chineseNumeralsToArabiaNumbers(setence);
		if (setence.contains("百分之")) {
			setence.replaceAll("百分之", "%");
		}
		if ("百%".equals(setence)) {
			setence = "100%";
		}
		return setence;
	}
}
