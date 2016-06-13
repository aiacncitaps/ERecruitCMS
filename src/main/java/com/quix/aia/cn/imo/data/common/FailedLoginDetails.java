package com.quix.aia.cn.imo.data.common;

import java.util.Date;

public class FailedLoginDetails {
	
	private int code;
	private String loginId;
	private Date lastLoginDate;
	private String co;
	private int failedCount;
	private boolean status;
	
	
	public FailedLoginDetails(){
		code=0;
		loginId="";
		lastLoginDate=null;
		co="0";
		failedCount=0;
		status=false;
	}
	
	
	
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public Date getLastLoginDate() {
		return lastLoginDate;
	}
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	public String getCo() {
		return co;
	}
	public void setCo(String co) {
		this.co = co;
	}
	public int getFailedCount() {
		return failedCount;
	}
	public void setFailedCount(int failedCount) {
		this.failedCount = failedCount;
	}




	public boolean isStatus() {
		return status;
	}




	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
	

}
