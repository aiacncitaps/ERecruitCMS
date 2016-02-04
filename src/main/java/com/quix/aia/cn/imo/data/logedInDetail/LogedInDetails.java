package com.quix.aia.cn.imo.data.logedInDetail;

import java.util.Date;

import com.quix.aia.cn.imo.utilities.SecurityAPI;

public class LogedInDetails {

	private int code;
	private String logedInId;
	private Date logedInDate;
	private String co;
	private int totalLogedIn;
	private int totalDownloadsOfERecruitmentApp;
	private int totalDownloadsOfEOPApp;
	private String logedInName;
	private int totalContacts;
	private String userType;
	private String branchName;
	private String sDate;
	private String eDate;

	public LogedInDetails() {
		code = 0;
		logedInId = "";
		logedInDate = null;
		co = "";
		totalLogedIn = 0;
		totalDownloadsOfERecruitmentApp = 0;
		totalDownloadsOfEOPApp = 0;
		logedInName = "";
		userType = "";
		sDate="";
		eDate="";
		branchName="";
		

	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the logedInId
	 */
	public String getLogedInId() {
		return logedInId;
	}

	/**
	 * @param logedInId
	 *            the logedInId to set
	 */
	public void setLogedInId(String logedInId) {
		this.logedInId = logedInId;
	}

	/**
	 * @return the logedInDate
	 */
	public Date getLogedInDate() {
		return logedInDate;
	}

	/**
	 * @param logedInDate
	 *            the logedInDate to set
	 */
	public void setLogedInDate(Date logedInDate) {
		this.logedInDate = logedInDate;
	}

	/**
	 * @return the co
	 */
	public String getCo() {
		return co;
	}

	/**
	 * @param co
	 *            the co to set
	 */
	public void setCo(String co) {
		this.co = co;
	}

	/**
	 * @return the totalLogedIn
	 */
	public int getTotalLogedIn() {
		return totalLogedIn;
	}

	/**
	 * @param totalLogedIn
	 *            the totalLogedIn to set
	 */
	public void setTotalLogedIn(int totalLogedIn) {
		this.totalLogedIn = totalLogedIn;
	}

	/**
	 * @return the totalDownloadsOfERecruitmentApp
	 */
	public int getTotalDownloadsOfERecruitmentApp() {
		return totalDownloadsOfERecruitmentApp;
	}

	/**
	 * @param totalDownloadsOfERecruitmentApp
	 *            the totalDownloadsOfERecruitmentApp to set
	 */
	public void setTotalDownloadsOfERecruitmentApp(
			int totalDownloadsOfERecruitmentApp) {
		this.totalDownloadsOfERecruitmentApp = totalDownloadsOfERecruitmentApp;
	}

	/**
	 * @return the totalDownloadsOfEOPApp
	 */
	public int getTotalDownloadsOfEOPApp() {
		return totalDownloadsOfEOPApp;
	}

	/**
	 * @param totalDownloadsOfEOPApp
	 *            the totalDownloadsOfEOPApp to set
	 */
	public void setTotalDownloadsOfEOPApp(int totalDownloadsOfEOPApp) {
		this.totalDownloadsOfEOPApp = totalDownloadsOfEOPApp;
	}

	/**
	 * @return the logedInName
	 */
	public String getLogedInName() {
		return logedInName;
	}

	/**
	 * @param logedInName
	 *            the logedInName to set
	 */
	public void setLogedInName(String logedInName) {
		this.logedInName = logedInName;
	}

	/**
	 * @return the totalContacts
	 */
	public int getTotalContacts() {
		return totalContacts;
	}

	/**
	 * @param totalContacts
	 *            the totalContacts to set
	 */
	public void setTotalContacts(int totalContacts) {
		this.totalContacts = totalContacts;
	}

	/**
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * @param userType
	 *            the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Object getGetUserDetailsListingTableRow(int i) {
		// TODO Auto-generated method stub
		String returnStr;
		
		 returnStr = "<tr > " +
				 "<td><div align=center>" + SecurityAPI.encodeHTML(this.branchName) + "</div></td>" + 
		"<td><div align=center>" + SecurityAPI.encodeHTML(this.logedInId) + "</div></td>" + 
		"<td><div align=center>" + this.logedInName + "</div></td>" + 
		"<td><div align=center>" + this.totalLogedIn + "</div></td>" + 
		"<td><div align=center>" + this.totalContacts + "</div></td>" +
		"<td><div align=center>" + this.totalDownloadsOfERecruitmentApp + "</div></td>" +
		"<td><div align=center>" + this.totalDownloadsOfEOPApp + "</div></td>" + 
		"</tr>";

		return returnStr;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getsDate() {
		return sDate;
	}

	public void setsDate(String sDate) {
		this.sDate = sDate;
	}

	public String geteDate() {
		return eDate;
	}

	public void seteDate(String eDate) {
		this.eDate = eDate;
	}

}
