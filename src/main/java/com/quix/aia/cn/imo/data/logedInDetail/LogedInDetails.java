package com.quix.aia.cn.imo.data.logedInDetail;

import java.util.Date;

import com.quix.aia.cn.imo.utilities.SecurityAPI;

public class LogedInDetails {
	
	private int code;
	private String logedInId;
	private Date logedInDate;
	private String co;
	private int totalLogedIn;
	private int totalIPADownload;
	private String logedInName;
	private int totalContacts;
	private String userType;

	
	public LogedInDetails(){
		code=0;
		logedInId="";
		logedInDate=null;
		co="";
		totalLogedIn=0;
		totalIPADownload=0;
		logedInName="";
		userType="";
		
		
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
	


	public  Object getGetUserDetailsListingTableRow(int i) {
		// TODO Auto-generated method stub
		String returnStr = "<tr > " +

		
				"<td><div align=center>"+ SecurityAPI.encodeHTML(this.logedInId) + "</div></td>" 
				+"<td><div align=center>" + this.logedInName + "</div></td>" 
				+"<td><div align=center>" + this.totalLogedIn + "</div></td>" 
				+"<td><div align=center>" + this.totalContacts + "</div></td>" 
				+ "</tr>";

		return returnStr;
	}


	public String getUserType() {
		return userType;
	}


	public void setUserType(String userType) {
		this.userType = userType;
	}
	

}
