package com.quix.aia.cn.imo.data.logedInDetail;

import java.util.Date;

public class LogedInDetails {
	
	private int code;
	private String logedInId;
	private Date logedInDate;
	private String co;
	private int totalLogedIn;
	private int totalIPADownload;
	private String logedInName;

	
	public LogedInDetails(){
		code=0;
		logedInId="";
		logedInDate=null;
		co="";
		totalLogedIn=0;
		totalIPADownload=0;
		logedInName="";
		
		
	}
	
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getLogedInId() {
		return logedInId;
	}
	public void setLogedInId(String logedInId) {
		this.logedInId = logedInId;
	}
	public Date getLogedInDate() {
		return logedInDate;
	}
	public void setLogedInDate(Date logedInDate) {
		this.logedInDate = logedInDate;
	}
	public String getCo() {
		return co;
	}
	public void setCo(String co) {
		this.co = co;
	}
	public int getTotalLogedIn() {
		return totalLogedIn;
	}
	public void setTotalLogedIn(int totalLogedIn) {
		this.totalLogedIn = totalLogedIn;
	}
	public int getTotalIPADownload() {
		return totalIPADownload;
	}
	public void setTotalIPADownload(int totalIPADownload) {
		this.totalIPADownload = totalIPADownload;
	}
	public String getLogedInName() {
		return logedInName;
	}
	public void setLogedInName(String logedInName) {
		this.logedInName = logedInName;
	}
	public int getTotalContacts() {
		return totalContacts;
	}
	public void setTotalContacts(int totalContacts) {
		this.totalContacts = totalContacts;
	}
	private int totalContacts;
	

}
