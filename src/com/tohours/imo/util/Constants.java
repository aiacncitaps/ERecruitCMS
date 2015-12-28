package com.tohours.imo.util;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;


public class Constants {
	public static final String SESSION_USER = "currUserObj";
	
	public static final Long RESOURCE_TYPE_INDEX = 1L;//首页资源
	public static final Long RESOURCE_TYPE_TEST = 2L;//测试资源
	public static final Long RESOURCE_TYPE_GUIDE=3L;//引导页资源
	public static final Long RESOURCE_TYPE_OBJECTIVE_ELEMENTS=4L;//十大要素
	public static final Long RESOURCE_TYPE_AIA_ELEMENTS=5L;//友邦十大要素
	private static final String API_ISP_URL = "/isp/rest/index.do?isAjax=true&account=%s&co=%s&password=%s&sys=wechat&type=0";
	
	public static final String FILE_PATH = "/attract/uploads";
	
	public static Map<Long, String> RESOURCE_TYPE = new TreeMap<Long, String>();

	public static String APP_PATH;
	
	static{
		RESOURCE_TYPE.put(RESOURCE_TYPE_INDEX, "首页资源");
		RESOURCE_TYPE.put(RESOURCE_TYPE_TEST, "测试资源");
		RESOURCE_TYPE.put(RESOURCE_TYPE_OBJECTIVE_ELEMENTS, "理想事业");
		RESOURCE_TYPE.put(RESOURCE_TYPE_GUIDE, "引导页资源");
		RESOURCE_TYPE.put(RESOURCE_TYPE_AIA_ELEMENTS, "友邦十大要素");
	}
	
	public static boolean isResourceExists(Long resourceType){
		Set<Long> keys = RESOURCE_TYPE.keySet();
		return keys.contains(resourceType);
	}
	
	public static String ispUrl(HttpServletRequest request){
		String host = request.getHeader("Host");
		if(isNotEmpty(host) && host.indexOf("uat.aia.com.cn") >= 0){
			return  "http://10.64.55.68" + API_ISP_URL;
 		} else if(isNotEmpty(host) && host.indexOf("aes.aia.com.cn") >= 0){
			return  "https://aes.aia.com.cn" + API_ISP_URL;
 		} else {
			return "http://211.144.219.243" + API_ISP_URL;
		}
	}
	private static Boolean isNotEmpty(String str){
		if(str != null && !"".equals(str)){
			return true;
		}
		return false;
	}
	public static final Boolean isTest = true;
}

