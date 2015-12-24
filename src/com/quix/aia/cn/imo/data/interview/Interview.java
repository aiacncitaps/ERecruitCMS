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
package com.quix.aia.cn.imo.data.interview;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.quix.aia.cn.imo.data.locale.LocaleObject;
import com.quix.aia.cn.imo.utilities.LMSUtil;
import com.quix.aia.cn.imo.utilities.SecurityAPI;
/**
 * 
 * @author Jinatmayee
 * @version 1.0
 */
public class Interview {
	
	
	private int interview_code;
	private String interviewType;
	private String interviewSessionName;
	private Date interviewDate;
	private Date StartTime;
	private Date EndTime;
	private String location;
	private String interviewMaterial;
	private int estimatedCondidates;
	private int buCode;
	private String cityCode;
	private String sscCode;
	private String createdBy;
	private Date creationDate;
	private String modifiedBy;
	private Date modificationDate;
	private boolean status;
	private String token;
	private int district;
	private Boolean isRegistered;
	private String buName;
	private String distName;
	private String cityName;
	private String sscName;
	private String organizer;
	
	private String attachmentPath;
	private String attachmentName;
	private int registeredCount;
	private int attendeeCount;
	private int branchCode;
	private String branchName;
	 private String officeCode;
	  private String officeName;
		private Integer calendarServiceError;
	  
		
	/**
		 * @return the calendarServiceError
		 */
		public Integer getCalendarServiceError() {
			return calendarServiceError;
		}


		/**
		 * @param calendarServiceError the calendarServiceError to set
		 */
		public void setCalendarServiceError(Integer calendarServiceError) {
			this.calendarServiceError = calendarServiceError;
		}


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
	 * <p>Default Constructor.Setting default values for Interview fields.</p>
	 */
	public Interview(){
		
		
		interview_code = 0;
		interviewType = "";
		interviewSessionName = "";
		interviewDate = null;
		StartTime = null;
		EndTime = null;
		location = "";
		interviewMaterial = "";
		estimatedCondidates = 0;
		buCode = 0;
		cityCode = "";
		sscCode = "";
		createdBy = "";
		creationDate = null;
		modifiedBy = "";
		modificationDate = null;
		status = false;
		setToken("");
		setDistrict(0);
		attachmentPath = "";
		attachmentName = "";
		registeredCount=0;
		attendeeCount=0;
		branchCode=0;
		branchName="";
		officeCode="";
		officeName="";
	}
	

	/**
	 * <p>@Override toString() method of Object class</p>
	 */
	public String toString() {
		
		
		return ("INTERVIEW CODE-> "+this.interview_code  +" INTERVIEW TYPE-> "+this.interviewType + " INTERVIEW SESSION NAME-> "+this.interviewSessionName
				+ " INTERVIEW DATE-> "+this.interviewDate + " START TIME-> "+this.StartTime + " END TIME-> "+this.EndTime
				+ " LOCATION-> "+this.location + " INTERVIEW MATERIAL-> "+this.interviewMaterial + " ESTIMATED CANDIDATES-> "+this.estimatedCondidates
				+ " BU-> "+this.buCode + " DISTRICT-> "+this.district+ " CITY-> "+this.cityCode + " SSC-> "+this.sscCode
				+ " CREATED BY-> "+this.createdBy
				);
	}
	
	
	 /**
	 * @return the interview_code
	 */
	public int getInterview_code() {
		return interview_code;
	}

	/**
	 * @param interview_code the interview_code to set
	 */
	public void setInterview_code(int interview_code) {
		this.interview_code = interview_code;
	}

	/**
	 * @return the interviewType
	 */
	public String getInterviewType() {
		return interviewType;
	}

	/**
	 * @param interviewType the interviewType to set
	 */
	public void setInterviewType(String interviewType) {
		this.interviewType = interviewType;
	}

	/**
	 * @return the interviewSessionName
	 */
	public String getInterviewSessionName() {
		return interviewSessionName;
	}

	/**
	 * @param interviewSessionName the interviewSessionName to set
	 */
	public void setInterviewSessionName(String interviewSessionName) {
		this.interviewSessionName = interviewSessionName;
	}

	/**
	 * @return the interviewDate
	 */
	public Date getInterviewDate() {
		return interviewDate;
	}

	/**
	 * @param interviewDate the interviewDate to set
	 */
	public void setInterviewDate(Date interviewDate) {
		this.interviewDate = interviewDate;
	}

	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return StartTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		StartTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return EndTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		EndTime = endTime;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the interviewMaterial
	 */
	public String getInterviewMaterial() {
		return interviewMaterial;
	}

	/**
	 * @param interviewMaterial the interviewMaterial to set
	 */
	public void setInterviewMaterial(String interviewMaterial) {
		this.interviewMaterial = interviewMaterial;
	}

	/**
	 * @return the estimatedCondidates
	 */
	public int getEstimatedCondidates() {
		return estimatedCondidates;
	}

	/**
	 * @param estimatedCondidates the estimatedCondidates to set
	 */
	public void setEstimatedCondidates(int estimatedCondidates) {
		this.estimatedCondidates = estimatedCondidates;
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
	 * @return the organizer
	 */
	public String getOrganizer() {
		return organizer;
	}

	/**
	 * @param organizer the organizer to set
	 */
	public void setOrganizer(String organizer) {
		this.organizer = organizer;
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
	 * @return the attachmentName
	 */
	public String getAttachmentName() {
		return attachmentName;
	}

	/**
	 * @param attachmentName the attachmentName to set
	 */
	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	/**
	 * @return the registeredCount
	 */
	public int getRegisteredCount() {
		return registeredCount;
	}


	/**
	 * @param registeredCount the registeredCount to set
	 */
	public void setRegisteredCount(int registeredCount) {
		this.registeredCount = registeredCount;
	}


	/**
	 * @return the attendeeCount
	 */
	public int getAttendeeCount() {
		return attendeeCount;
	}


	/**
	 * @param attendeeCount the attendeeCount to set
	 */
	public void setAttendeeCount(int attendeeCount) {
		this.attendeeCount = attendeeCount;
	}


	/**
	 * <p>Dynamic rows added based on interview list size</p>
	 * @param localeObj 
	 * @return the row as string
	 */
	public String getInterviewListingTableRow(LocaleObject localeObj)
    {
	 
		SimpleDateFormat ra = new SimpleDateFormat("HH:mm a");
		
//	   	String modifyLink = "";
	   	Date todayDate = new Date();  
	   	String modifyLink = "<a href=\" FormManager?key=InterviewAdd&type=MODIFY&interview_code=" + this.interview_code + "\"><img src=images/edit.png border=0></a>";
        String deleteLink = "<a href=\"javascript:confirmDelete('" + interview_code + "')\"><img src=images/delete.png border=0></a>";
        String resyncLink = "<a href=\"FormManager?key=InterviewAdd&type=RESYNC&interview_code=" + this.interview_code + "\"><img src=images/sync.png border=0></a>";
        String viewAttendanceLink = "ContentManager?key=InterviewAttendanceDetails&interview_code=" + interview_code;
        String returnStr = "";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		try {
			
			if(attendeeCount>0){
				deleteLink = "";
			}
			
			if(sdf1.parse(sdf1.format(todayDate)).equals(this.interviewDate)){
					Date crtTime=new Date(System.currentTimeMillis());
					Date stTime=this.StartTime;
					crtTime=ra.parse(ra.format(crtTime));
					stTime=ra.parse(ra.format(stTime));
					if(crtTime.after(stTime))
					{
						modifyLink = "";
						deleteLink = "";
					}
					 
				
			}else	if(sdf1.parse(sdf1.format(todayDate)).after(this.interviewDate)){
				modifyLink = "";
				deleteLink = "";
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String interview_date ="";
		
		if(interviewDate!=null)
			interview_date = LMSUtil.convertDateToString(interviewDate);
		String time1= "";
		if(StartTime!=null){
			
		   time1=LMSUtil.converDateIntoHHMMAMPM(StartTime);
		}
		String time2= "";
		if(EndTime!=null){
			
		   time2=LMSUtil.converDateIntoHHMMAMPM(EndTime);
		}
		
		 String date="";
			if(this.modificationDate!=null)
				date = LMSUtil.convertDateToString(this.modificationDate);
			
			String actionLinks = modifyLink + "&nbsp;" + deleteLink;
	        if (this.isStatus() == false)
	        {
	            // deleted interview do not have any update or delete actions
	            actionLinks = "";
	        }
	        
	        if (this.getCalendarServiceError()!= null && this.getCalendarServiceError() > 0)
	        {
	            actionLinks += "&nbsp;" + resyncLink;
	        }
			
			
	        returnStr = "<tr>"
	                + "<td>" + "<div align=center><a href=\"" + viewAttendanceLink + "\">" + SecurityAPI.encodeHTML(interviewSessionName) + "</a></div></td>"
	                + "<td>" + SecurityAPI.encodeHTML(localeObj.getTranslatedText(interviewType + " Interview")) + "</td>"
	                + "<td>" + "<div align=center><a href=\"" + viewAttendanceLink + "\">" + SecurityAPI.encodeHTML(interview_date) + "</a></div></td>"
	                + "<td>" + SecurityAPI.encodeHTML(time1) + "</td>"
	                + "<td>" + SecurityAPI.encodeHTML(time2) + "</td>"
	                + "<td>" + SecurityAPI.encodeHTML(LMSUtil.blankIfNull(buName)) + "</td>"
	                + "<td>" + SecurityAPI.encodeHTML(LMSUtil.blankIfNull(distName)) + "</td>"
	                + "<td>" + SecurityAPI.encodeHTML(LMSUtil.blankIfNull(branchName)) + "</td>"
	                + "<td>" + SecurityAPI.encodeHTML(LMSUtil.blankIfNull(cityName)) + "</td>"
	                + "<td>" + SecurityAPI.encodeHTML(LMSUtil.blankIfNull(sscName)) + "</td>"
	                + "<td>" + SecurityAPI.encodeHTML(LMSUtil.blankIfNull(officeName)) + "</td>"
	                + "<td>" + SecurityAPI.encodeHTML(this.modifiedBy) + "</td>"
	                + "<td >" + date + "</div></td>"
	                + "<td>" + "<div align=center>" + SecurityAPI.encodeHTML(String.valueOf(this.registeredCount)) + "</div></td>"
	                + "<td>" + "<div align=center>" + SecurityAPI.encodeHTML(String.valueOf(this.attendeeCount)) + "</div></td>"
	                + "<td>" + "<div align=center>" + actionLinks + "</div></td>"
	                + "</tr>";
        
        return returnStr;
    }


	public Boolean getIsRegistered() {
		return isRegistered;
	}


	public void setIsRegistered(Boolean isRegistered) {
		this.isRegistered = isRegistered;
	}
}
