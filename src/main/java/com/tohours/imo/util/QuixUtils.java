package com.tohours.imo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.druid.util.StringUtils;

public class QuixUtils {
	/**
	 * 去除空格
	 * @param str
	 * @return
	 */
	public static String dealBlank(String str){
		if(StringUtils.isEmpty(str)){
			return "";
		} else {
			return str.trim();
		}
		
	}
	/**
	 * 转换日期
	 * @param strDate
	 * @param format
	 * @return
	 * @throws ParseException 
	 */
	public static Date string2date(String strDate, String format) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(strDate);
	}
	
	/**
	 * 转换日期
	 * @param strDate
	 * @return
	 * @throws ParseException 
	 */
	public static Date string2date(String strDate) throws ParseException{
		return string2date(strDate, "yyyy-MM-dd");
	}
	
	/**
	 * 将字符串转化成字符串数组
	 * @param str
	 * @return
	 */
	public static String[] string2array(String str){
		String[] arrStr=new String[str.length()];
		for (int i = 0; i < str.length(); i++) {
			arrStr[i]=str.charAt(i)+"";
		}
		return arrStr;
	}
}
