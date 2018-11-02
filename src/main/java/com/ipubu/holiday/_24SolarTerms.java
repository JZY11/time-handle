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

/**
*
* @param year 年份
* @param name 节气的名称
* @return 返回节气是相应月份的第几天
*/
	public static int getSolarTermNum(int year,String name){

		double centuryValue = 0;//节气的世纪值，每个节气的每个世纪值都不同
		name = name.trim().toUpperCase();
		int ordinal = SolarTermsEnum.valueOf(name).ordinal();

		int centuryIndex = -1;
		if(year>=1901 && year<=2000){//20世纪
			centuryIndex = 0;
		} else if(year>=2001 && year <= 2100){//21世纪
			centuryIndex = 1;
		} else {
			throw new RuntimeException("不支持此年份："+year+"，目前只支持1901年到2100年的时间范围");
		}
		centuryValue = CENTURY_ARRAY[centuryIndex][ordinal];
		int dateNum = 0;
		/**
		 * 计算 num =[Y*D+C]-L这是传说中的寿星通用公式
		 * 公式解读：年数的后2位乘0.2422加C(即：centuryValue)取整数后，减闰年数
		 */
		int y = year%100;//步骤1:取年分的后两位数
		if(year%4 == 0 && year%100 !=0 || year%400 ==0){//闰年
			if(ordinal == SolarTermsEnum.XIAOHAN.ordinal() || ordinal == SolarTermsEnum.DAHAN.ordinal()
					|| ordinal == SolarTermsEnum.LICHUN.ordinal() || ordinal == SolarTermsEnum.YUSHUI.ordinal()){
				//注意：凡闰年3月1日前闰年数要减一，即：L=[(Y-1)/4],因为小寒、大寒、立春、雨水这两个节气都小于3月1日,所以 y = y-1
				y = y-1;//步骤2
			}
		}
		dateNum = (int)(y*D+centuryValue)-(int)(y/4);//步骤3，使用公式[Y*D+C]-L计算
		dateNum += specialYearOffset(year,name);//步骤4，加上特殊的年分的节气偏移量
		return dateNum;
	}

}
