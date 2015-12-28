package com.tohours.imo.module;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.mvc.Mvcs;

public abstract class BaseModule {
	
	/** 注入同名的一个ioc对象 */
	@Inject protected Dao dao;
	/**
	 * 返回request对象
	 * @return
	 */
	protected static HttpServletRequest getRequest() {
		return Mvcs.getReq();
	}
	/**
	 * 返回rsponse对象
	 * @return
	 */
	protected static HttpServletResponse getResponse() {
		return Mvcs.getResp();
	}

}
