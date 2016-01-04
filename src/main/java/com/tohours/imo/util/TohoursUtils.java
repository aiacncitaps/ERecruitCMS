package com.tohours.imo.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.IOUtils;
import org.nutz.http.Http;
import org.nutz.http.Response;
import org.nutz.lang.Mirror;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.alibaba.druid.util.StringUtils;

public class TohoursUtils {

	public static Map<String, String> map = new HashMap<String, String>();

	/**
	 * 获得目前的年份和月份的组合
	 * 
	 * @return
	 */
	public static String getYearMonth() {
		return getYearMonth(0);
	}

	/**
	 * 获得年份和月份的组合，可用n调整月份
	 * 
	 * @param n
	 * @return
	 */
	public static String getYearMonth(int n) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, n);
		String yearMonth = calendar.get(Calendar.YEAR) + "年"
				+ formatNum(calendar.get(Calendar.MONTH) + 1) + "月";
		return yearMonth;
	}

	public static String getCurrentYear() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR) + "";
	}

	public static Long nullToZero(Long num) {
		Long rv = 0L;
		if (num != null) {
			rv = num;
		}
		return rv;
	}

	/**
	 * 将10以下的数字补0
	 * 
	 * @return
	 */
	public static String formatNum(int n) {
		return n < 10 ? "0" + n : n + "";
	}

	/**
	 * 输入流转String
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static String inputStream2String(InputStream in, String charsetName)
			throws IOException {
		StringWriter writer = new StringWriter();
		IOUtils.copy(in, writer, charsetName);
		String theString = writer.toString();
		return theString;
	}

	public static String inputStream2String(InputStream in) throws IOException {
		return inputStream2String(in, "UTF-8");
	}

	/**
	 * 生成随机号
	 * 
	 * @param sLen
	 * @return
	 */
	public static String randomKey(int sLen) {
		String base;
		String temp;
		int i;
		int p;

		base = "1234567890abcdefghijklmnopqrstuvwxyz";
		temp = "";
		for (i = 0; i < sLen; i++) {
			p = (int) (Math.random() * 37);
			if (p > 35)
				p = 35;
			temp += base.substring(p, p + 1);
		}
		return temp;
	}

	/**
	 * http请求
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static String httpGet(String path, String charsetName)
			throws IOException {
		String rv = null;
		URL url = null;
		HttpURLConnection httpConnection = null;
		InputStream input = null;
		try {
			url = new URL(path);
			httpConnection = (HttpURLConnection) url.openConnection();
			input = httpConnection.getInputStream();
			rv = TohoursUtils.inputStream2String(input, charsetName);
		} finally {
			if (input != null) {
				input.close();
			}
		}
		return rv;
	}

	/**
	 * 
	 * @param path
	 * @param charsetName
	 * @param param
	 * @return
	 * @throws IOException
	 */
	public static String httpPost(String path, String param, String charsetName)
			throws IOException {
		String rv = null;
		URL url = null;
		HttpURLConnection conn = null;
		InputStream input = null;
		try {
			url = new URL(path);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "plain/text");
			conn.setRequestProperty("User-Agent", "Tohours Shake Project");
			OutputStream os = conn.getOutputStream();
			os.write(param.getBytes(charsetName));
			os.flush();
			os.close();
			input = conn.getInputStream();
			rv = TohoursUtils.inputStream2String(input, charsetName);
		} finally {
			if (input != null) {
				input.close();
			}
		}
		return rv;
	}

	/**
	 * 
	 * @param path
	 * @param charsetName
	 * @param param
	 * @return
	 * @throws IOException
	 */
	public static String httpsPost(String path, String param, String charsetName)
			throws IOException {
		String rv = null;
		URL url = null;
		HttpsURLConnection conn = null;
		InputStream input = null;
		try {
			url = new URL(path);
			conn = (HttpsURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "plain/text");
			conn.setRequestProperty("User-Agent", "Tohours Project");
			OutputStream os = conn.getOutputStream();
			os.write(param.getBytes(charsetName));
			os.flush();
			os.close();
			input = conn.getInputStream();
			rv = TohoursUtils.inputStream2String(input, charsetName);
		} finally {
			if (input != null) {
				input.close();
			}
		}
		return rv;
	}

	public static String httpPost(String path, String param) throws IOException {
		if (path.indexOf("https") >= 0) {
			return httpsPost(path, param, "UTF-8");
		} else {
			return httpPost(path, param, "UTF-8");
		}
	}

	/**
	 * 
	 * @param path
	 * @param charsetName
	 * @return
	 * @throws IOException
	 */
	public static String httpsGet(String path, String charsetName)
			throws IOException {
		String rv = null;
		URL url = null;
		HttpsURLConnection httpsConnection = null;
		InputStream input = null;
		try {
			url = new URL(path);
			httpsConnection = (HttpsURLConnection) url.openConnection();
			input = httpsConnection.getInputStream();
			rv = TohoursUtils.inputStream2String(input, charsetName);
		} finally {
			if (input != null) {
				input.close();
			}
		}
		return rv;
	}

	public static String httpGet(String path) throws IOException {
		if (path.indexOf("https") >= 0) {
			return httpsGet(path, "UTF-8");
		} else {
			return httpGet(path, "UTF-8");
		}
	}

	/**
	 * 转换日期
	 * 
	 * @param strDate
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static Date string2date(String strDate, String format)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(strDate);
	}

	/**
	 * 转换日期
	 * 
	 * @param strDate
	 * @return
	 * @throws ParseException
	 */
	public static Date string2date(String strDate) throws ParseException {
		return string2date(strDate, "yyyy-MM-dd");
	}

	public static String nullToSpace(Object obj) {
		return obj == null ? "" : obj.toString();
	}

	/**
	 * 将字符串转换成xml文档
	 * 
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	public static Document loadXMLFromString(String xml) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(xml));
		return builder.parse(is);
	}

	public static String monthFirstDay() {
		return monthFirstDay(0);
	}

	public static String monthFirstDay(int n) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, n);
		return calendar.get(Calendar.YEAR) + "-"
				+ formatNum(calendar.get(Calendar.MONTH) + 1) + "-01";
	}

	public static boolean isEmpty(String str) {
		return str == null || "".equals(str.trim());
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static void httpTest() {
		Response response = Http.get("https://www.baidu.com/");
		System.out.println(response.getStatus());
		System.out.println(response.getContent());
		System.out.println("Detail:" + response.getDetail());
		System.out.println(response.getEncodeType());
		System.out.println(response.getProtocal());
		System.out.println(response.getCookie());
		System.out.println(response.getHeader());
		// System.out.println(Json.toJson(response));
	}

	public static void mirrorTest() {
		Mirror<?> mirror = Mirror.me(TohoursUtils.class);
		Method[] sm = mirror.getStaticMethods();

		for (Method f : sm) {
			System.out.println(f);
		}
	}

	/**
	 * 获取扩展名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileExt(String fileName) {
		if (null == fileName) {
			return "";
		}
		return fileName.replaceAll("^.*\\.", "");
	}

	public static String addPercent(String key) {
		if (StringUtils.isEmpty(key) == false) {
			return "%" + key + "%";
		} else {
			return key;
		}
	}

	public static String dealNull(String str) {
		if (StringUtils.isEmpty(str) || "null".equals(str)) {
			return "";
		} else {
			return str;
		}
	}

	/**
	 * 转换日期
	 */
	public static String date2string(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return simpleDateFormat.format(date);
	}

	/**
	 * 转换日期
	 */
	public static String date2string(Date date, String format) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		return simpleDateFormat.format(date);
	}
}
