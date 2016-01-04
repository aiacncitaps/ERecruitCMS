/*******************************************************************************
* -----------------------------------------------------------------------------
* <br>
* <p><b>Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.</b> 
* <br>
* <br>
* This SOURCE CODE FILE, which has been provided by Quix as part
* of a Quix Creations product for use ONLY by licensed users of the product,
* includes CONFIDENTIAL and PROPRIETARY information of Quix Creations.
* <br>
* USE OF THIS SOFTWARE IS GOVERNED BY THE TERMS AND CONDITIONS
* OF THE LICENSE STATEMENT AND LIMITED WARRANTY FURNISHED WITH
* THE PRODUCT.<br>
* <br>
* </p>
* -----------------------------------------------------------------------------
* <br>
* <br>
* Modification History:
* Date              Developer          Change Description
* 11-May-2015       Nibedita            copyright and security code added
****************************************** *********************************** */
package com.quix.aia.cn.imo.data.announcement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.quix.aia.cn.imo.data.locale.LocaleObject;
import com.quix.aia.cn.imo.utilities.ImoUtilityData;
import com.quix.aia.cn.imo.utilities.LMSUtil;
import com.quix.aia.cn.imo.utilities.SecurityAPI;

/**
 * <p>Announcement bean class.</p>
 * 
 * @author Nibedita
 * @version 1.0
 *
 */
public class Announcement {
	private int annoucement_code;
	private String subject;
	private Date publishedDate;
	private Date expDate;
	private String msgType;
	private String message;
	private int buCode;
	private String cityCode;
	private String sscCode;
	private String attachmentPath;
	private String createdBy;
	private Date creationDate;
	private String modifiedBy;
	private Date modificationDate;
	private boolean status;
	private int district;
	private String token;
	private String bu;
	private String dist;
	private String city;
	private String ssc;
	private String buName;
	private String distName;
	private String cityName;
	private String sscName;
	private String userName;
	private int branchCode;
	private String branchName;
	 private String officeCode;
	  private String officeName;
	
	
	
	 public String getOfficeCode() {
		return officeCode;
	}

	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public int getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(int branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	/**
	  * <p>Default Constructor.Setting default values for announcement fields.</p>
	  */
	public Announcement(){
		
		annoucement_code = 0;
		subject = "";
		publishedDate = null;
		expDate = null;
		msgType = "";
		message = "";
		buCode = 0;
		cityCode = "";
		sscCode = "";
		attachmentPath = "";
		createdBy = "";
		creationDate = null;
		modifiedBy = "";
		modificationDate = null;
		status=false;
		district=0;
		token="";
		bu = "";
		dist = "";
		city = "";
		ssc = "";
		branchCode=0;
		branchName="";
		officeCode="";
		officeName="";
		
	}

	/**
	 * @return the annoucement_code
	 */
	public int getAnnoucement_code() {
		return annoucement_code;
	}

	/**
	 * @param annoucement_code the annoucement_code to set
	 */
	public void setAnnoucement_code(int annoucement_code) {
		this.annoucement_code = annoucement_code;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the publishedDate
	 */
	public Date getPublishedDate() {
		return publishedDate;
	}

	/**
	 * @param publishedDate the publishedDate to set
	 */
	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}

	/**
	 * @return the expDate
	 */
	public Date getExpDate() {
		return expDate;
	}

	/**
	 * @param expDate the expDate to set
	 */
	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}

	/**
	 * @return the msgType
	 */
	public String getMsgType() {
		return msgType;
	}

	/**
	 * @param msgType the msgType to set
	 */
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the buCode
	 */
	public int getBuCode() {
		return buCode;
	}

	/**
	 * @param buCode the buCode to set
	 */
	public void setBuCode(int buCode) {
		this.buCode = buCode;
	}

	/**
	 * @return the cityCode
	 */
	public String getCityCode() {
		return cityCode;
	}

	/**
	 * @param cityCode the cityCode to set
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	/**
	 * @return the sscCode
	 */
	public String getSscCode() {
		return sscCode;
	}

	/**
	 * @param sscCode the sscCode to set
	 */
	public void setSscCode(String sscCode) {
		this.sscCode = sscCode;
	}

	/**
	 * @return the attachmentPath
	 */
	public String getAttachmentPath() {
		return attachmentPath;
	}

	/**
	 * @param attachmentPath the attachmentPath to set
	 */
	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return the modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * @return the modificationDate
	 */
	public Date getModificationDate() {
		return modificationDate;
	}

	/**
	 * @param modificationDate the modificationDate to set
	 */
	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	/**
	 * @return the status
	 */
	public boolean isStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

	/**
	 * @return the district
	 */
	public int getDistrict() {
		return district;
	}

	/**
	 * @param district the district to set
	 */
	public void setDistrict(int district) {
		this.district = district;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the bu
	 */
	public String getBu() {
		return bu;
	}

	/**
	 * @param bu the bu to set
	 */
	public void setBu(String bu) {
		this.bu = bu;
	}

	/**
	 * @return the dist
	 */
	public String getDist() {
		return dist;
	}

	/**
	 * @param dist the dist to set
	 */
	public void setDist(String dist) {
		this.dist = dist;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the ssc
	 */
	public String getSsc() {
		return ssc;
	}

	/**
	 * @param ssc the ssc to set
	 */
	public void setSsc(String ssc) {
		this.ssc = ssc;
	}

	/**
	 * @return the buName
	 */
	public String getBuName() {
		return buName;
	}

	/**
	 * @param buName the buName to set
	 */
	public void setBuName(String buName) {
		this.buName = buName;
	}

	/**
	 * @return the distName
	 */
	public String getDistName() {
		return distName;
	}

	/**
	 * @param distName the distName to set
	 */
	public void setDistName(String distName) {
		this.distName = distName;
	}

	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * @return the sscName
	 */
	public String getSscName() {
		return sscName;
	}

	/**
	 * @param sscName the sscName to set
	 */
	public void setSscName(String sscName) {
		this.sscName = sscName;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		
		return ("Subject-> "+subject + " Announcement Code-> "+annoucement_code );
	}
	
	/**
	 * <p>Dynamic rows added based Announcement list size</p>
	 * @param i    each row number
	 * @param lastElement last element index
	 * @return  row as string
	 */
	public String getGetAnnouncementListingTableRow(LocaleObject localeObj)
    {
		String publishDt = "",expDt = "",postedOn = "";
		if(publishedDate!=null)
			publishDt = LMSUtil.convertDateToString(this.publishedDate);	
		if(expDate!=null)
			expDt = LMSUtil.convertDateToString(this.expDate);
		if(creationDate!=null)
			postedOn = LMSUtil.convertDateToString(this.creationDate);
		
		 String modifyLink = "";
	     String deleteLink = "javascript:confirmDelete('" + annoucement_code + "')";
		String returnStr = "",announceStatus = "";
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		Date todayDate = new Date();
		try {
			if(sdf1.parse(sdf1.format(todayDate)).before(this.expDate)){
				announceStatus = "Running";
				modifyLink = "<a href=\" FormManager?key=EopAnnouncementAdd&type=MODIFY&annoucementCode=" + this.annoucement_code + "\"><img src=images/edit.png border=0></a>";
			}else if(sdf1.parse(sdf1.format(todayDate)).equals(this.expDate)){
				announceStatus = "Running";
				modifyLink = "<a href=\" FormManager?key=EopAnnouncementAdd&type=MODIFY&annoucementCode=" + this.annoucement_code + "\"><img src=images/edit.png border=0></a>";
			}
			else{
				announceStatus = "Expired";
				//modifyLink = "<a href=\" FormManager?key=EopAnnouncementAdd&type=MODIFY&annoucementCode=" + this.annoucement_code + "\"><img src=images/edit.png border=0></a>";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 String date="";
			if(this.modificationDate!=null)
				date = LMSUtil.convertDateToString(this.modificationDate);
			
	    returnStr ="<tr> "+
		"<td>"+SecurityAPI.encodeHTML(this.subject)+"</td>"+
		"<td>"+SecurityAPI.encodeHTML(this.msgType)+"</td>"+
		"<td>"+SecurityAPI.encodeHTML(publishDt)+"</td>"+
		"<td>"+SecurityAPI.encodeHTML(expDt)+"</td>"+
		"<td>"+SecurityAPI.encodeHTML(localeObj.getTranslatedText(announceStatus))+"</td>"+
		"<td>"+SecurityAPI.encodeHTML(LMSUtil.blankIfNull(this.buName))+"</td>"+
		"<td>"+SecurityAPI.encodeHTML(LMSUtil.blankIfNull(this.distName))+"</td>"+
		"<td>"+SecurityAPI.encodeHTML(LMSUtil.blankIfNull(this.branchName))+"</td>"+
		"<td>"+SecurityAPI.encodeHTML(LMSUtil.blankIfNull(this.cityName))+"</td>"+
		"<td>"+SecurityAPI.encodeHTML(LMSUtil.blankIfNull(this.sscName))+"</td>"+
		"<td>"+SecurityAPI.encodeHTML(LMSUtil.blankIfNull(this.officeName))+"</td>"+
		"<td>"+SecurityAPI.encodeHTML(LMSUtil.blankIfNull(this.createdBy))+"</td>"+
		//"<td>"+SecurityAPI.encodeHTML(postedOn)+"</td>"+
		"<td>"+SecurityAPI.encodeHTML(this.modifiedBy)+"</td>"+
		"<td> " + date + "</div></td>" +
		"<td><div align=center>"+modifyLink+"&nbsp;<a href=\"" + deleteLink + "\"><img src=images/delete.png border=0></a></div></td>"+
		"</tr>";
		return returnStr;
    }
}
