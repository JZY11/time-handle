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


//定义一个二维数组，第一维数组存储的是20世纪的节气C值，第二维数组存储的是21世纪的节气C值,0到23个，依次代表立春、雨水...大寒节气的C值
	private static final double[][] CENTURY_ARRAY =
		{{4.6295,19.4599,6.3826,21.4155,5.59,20.888,6.318,21.86,6.5,22.2,7.928,23.65,8.35,
			23.95,8.44,23.822,9.098,24.218,8.218,23.08,7.9,22.6,6.11,20.84}
		,{3.87,18.73,5.63,20.646,4.81,20.1,5.52,21.04,5.678,21.37,7.108,22.83,
			7.5,23.13,7.646,23.042,8.318,23.438,7.438,22.36,7.18,21.94,5.4055,20.12}};

}
