package com.quix.aia.cn.imo.data.logs;

import java.util.Date;

public class Logs {
	
	private int logsId;
	private String fileName;
	private Date logDate;
	private String level;
	private String msg;
	
	
	public Logs(){
		logsId=0;
		fileName="";
		logDate=null;
		level="";
		msg="";
		
		
	}
	
	
	
	
	
	public int getLogsId() {
		return logsId;
	}
	public void setLogsId(int logsId) {
		this.logsId = logsId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Date getLogDate() {
		return logDate;
	}
	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	

}
