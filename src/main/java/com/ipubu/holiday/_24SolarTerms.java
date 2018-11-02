package com.ipubu.holiday;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @ClassName _24SolarTerms
 * @Description  24节气
 * @Author jzy
 * 注：程序中使用到的计算节气公式、节气世纪常量等相关信息参照http://www.360doc.com/content/11/0106/22/5281066_84591519.shtml，
 * 程序的运行得出的节气结果绝大多数是正确的，有少数部份是有误差的
 */
public class _24SolarTerms {

	private static final  double D = 0.2422;
	private final static Map<String,Integer[]> INCREASE_OFFSETMAP = new HashMap<String, Integer[]>();//+1偏移
	private final static Map<String,Integer[]> DECREASE_OFFSETMAP = new HashMap<String, Integer[]>();//-1偏移

	/**24节气**/
	private static enum SolarTermsEnum {
		LICHUN,//--立春
		YUSHUI,//--雨水
		JINGZHE,//--惊蛰
		CHUNFEN,//春分
		QINGMING,//清明
		GUYU,//谷雨
		LIXIA,//立夏
		XIAOMAN,//小满
		MANGZHONG,//芒种
		XIAZHI,//夏至
		XIAOSHU,//小暑
		DASHU,//大暑
		LIQIU,//立秋
		CHUSHU,//处暑
		BAILU,//白露
		QIUFEN,//秋分
		HANLU,//寒露
		SHUANGJIANG,//霜降
		LIDONG,//立冬
		XIAOXUE,//小雪
		DAXUE,//大雪
		DONGZHI,//冬至
		XIAOHAN,//小寒
		DAHAN;//大寒
	}

}
