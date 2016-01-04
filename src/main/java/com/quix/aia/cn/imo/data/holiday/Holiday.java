/******************************************************************************
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
* 08-June-2015       Jinatmayee         Copy Rights & Comments Added
* 09-June-2015       Jinatmayee         Hard coded css removed
******************************************************************************/
package com.quix.aia.cn.imo.data.holiday;

import java.util.Date;
import com.quix.aia.cn.imo.utilities.LMSUtil;
import com.quix.aia.cn.imo.utilities.SecurityAPI;
/**
 * 
 * @author Jinatmayee
 * @version 1.0
 */
public class Holiday {

	private int holidayCode;
	private String holidayName;
	private Date startDate;
	private Date endDate;
	private int buCode;
	private int cityCode;
	private String createdBy;
	private Date creationDate;
	private String modifiedBy;
	private Date modificationDate;
	private boolean status;
	private String token;
	private int district;
	private int sscCode;
	private String iconSelection;
	private int branchCode;
	private String branchName;
	
	
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

	private String buName;
	private String distName;
	private String cityName;
	private String sscName;
	private String postedBy;
	
	/**
	 * <p>Default Constructor.Setting default values for Holiday fields.</p>
	 */
	public Holiday(){
		
		holidayCode=0;
		holidayName="";
		startDate=new Date();
		endDate=new Date();
		buCode = 0;
		cityCode = 0;
		createdBy = "";
		creationDate = new Date();
		modifiedBy = "";
		modificationDate = new Date();
		status = false;
		token="";
		district=0;
		sscCode=0;
		iconSelection="";
		branchCode=0;
		branchName="";
		
	}
	
	
	/**
	 * <p>@Override toString() method of Object class</p>
	 */
	public String toString() {
		
		
		return ("HOLIDAY CODE-> "+this.holidayCode  +"HOLIDAY NAME-> "+this.holidayName + "START DATE-> "+this.startDate + "END DATE-> "+this.endDate
				+ "BU-> "+this.buCode + "DISTRICT-> "+this.district+ "CITY-> "+this.cityCode + "SSC-> "+this.sscCode
				+ "ICON SELECTION-> "+this.iconSelection+ "POSTED BY-> "+this.createdBy
				);
	}


	/**
	 * @return the holidayCode
	 */
	public int getHolidayCode() {
		return holidayCode;
	}

	/**
	 * @param holidayCode the holidayCode to set
	 */
	public void setHolidayCode(int holidayCode) {
		this.holidayCode = holidayCode;
	}

	/**
	 * @return the holidayName
	 */
	public String getHolidayName() {
		return holidayName;
	}

	/**
	 * @param holidayName the holidayName to set
	 */
	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	public int getCityCode() {
		return cityCode;
	}

	/**
	 * @param cityCode the cityCode to set
	 */
	public void setCityCode(int cityCode) {
		this.cityCode = cityCode;
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
	 * @return the sscCode
	 */
	public int getSscCode() {
		return sscCode;
	}

	/**
	 * @param sscCode the sscCode to set
	 */
	public void setSscCode(int sscCode) {
		this.sscCode = sscCode;
	}

	/**
	 * @return the iconSelection
	 */
	public String getIconSelection() {
		return iconSelection;
	}

	/**
	 * @param iconSelection the iconSelection to set
	 */
	public void setIconSelection(String iconSelection) {
		this.iconSelection = iconSelection;
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
	 * @return the postedBy
	 */
	public String getPostedBy() {
		return postedBy;
	}

	/**
	 * @param postedBy the postedBy to set
	 */
	public void setPostedBy(String postedBy) {
		this.postedBy = postedBy;
	}
	
   /**
    * <p>Dynamic rows added based on holiday list size</p>
    * @return the row as string
    */
	
		public String getHolidayListingTableRow()
		    {
			 
			    String modifyLink = "";
		        modifyLink = "FormManager?key=EopHolidayAdd&type=MODIFY&holiday_code=" + holidayCode;
		        String deleteLink = "";
		        deleteLink = "javascript:confirmDelete('" + holidayCode + "')";
		        String returnStr = "";
		        String row = "";
		        String cell ="";
		       
		        String start_date ="";
		        String end_date ="";
		        String posted_date ="";
		        if(startDate!=null)
		        	start_date = LMSUtil.convertDateToString(startDate);
		        if(endDate!=null)
		        	end_date = LMSUtil.convertDateToString(endDate);
		        if(creationDate!=null)
		        	posted_date = LMSUtil.convertDateToString(creationDate);
		        
		        returnStr ="<tr>"+
		        "<td>"+SecurityAPI.encodeHTML(holidayName)+"</td>"+
		        "<td><div align=center>"+SecurityAPI.encodeHTML(start_date)+"</div></td>"+
		        "<td><div align=center>"+SecurityAPI.encodeHTML(end_date)+"</div></td>"+
		        "<td>"+SecurityAPI.encodeHTML(LMSUtil.blankIfNull(buName))+"</td>"+
	  			"<td>"+SecurityAPI.encodeHTML(LMSUtil.blankIfNull(distName))+"</td>"+
	  			"<td>"+SecurityAPI.encodeHTML(LMSUtil.blankIfNull(branchName))+"</td>"+
	  			"<td>"+SecurityAPI.encodeHTML(LMSUtil.blankIfNull(cityName))+"</td>"+
	  			"<td>"+SecurityAPI.encodeHTML(LMSUtil.blankIfNull(sscName))+"</td>"+
		        "<td><div align=center>"+SecurityAPI.encodeHTML(posted_date)+"</div></td>"+
		        "<td><div align=center>"+SecurityAPI.encodeHTML(LMSUtil.blankIfNull(postedBy))+"</div></td>"+
		        "<td>"+"<div align=center><a href=\"" + modifyLink + "\"><img src=images/edit.png border=0></a>&nbsp;<a href=\"" + deleteLink + "\"><img src=images/delete.png border=0></a></div></td>"+
		       
		        "</tr>";
		        
		        return returnStr;
		    }
}
