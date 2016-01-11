package com.quix.aia.cn.imo.data.downloadDetail;

import java.util.Date;

public class DownloadDetails {

	private int code;
	private String logedInId;
	private Date downloadDate;
	private String appType;
	private String co;
	private String userType;

	public DownloadDetails() {
		code = 0;
		logedInId = "";
		downloadDate = null;
		appType = "";
		co = "";
		userType = "";
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
	 * @return the downloadDate
	 */
	public Date getDownloadDate() {
		return downloadDate;
	}

	/**
	 * @param downloadDate
	 *            the downloadDate to set
	 */
	public void setDownloadDate(Date downloadDate) {
		this.downloadDate = downloadDate;
	}

	/**
	 * @return the appType
	 */
	public String getAppType() {
		return appType;
	}

	/**
	 * @param appType
	 *            the appType to set
	 */
	public void setAppType(String appType) {
		this.appType = appType;
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

}
