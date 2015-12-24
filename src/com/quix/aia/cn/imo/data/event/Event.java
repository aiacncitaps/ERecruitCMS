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
* 08-June-2015       Nibedita           copyright,comments added
****************************************** *********************************** */
package com.quix.aia.cn.imo.data.event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.quix.aia.cn.imo.utilities.LMSUtil;
import com.quix.aia.cn.imo.utilities.SecurityAPI;
/**
 * <p>Event bean class.</p>
 * 
 * @author Nibedita
 * @version 1.0
 *
 */
public class Event {

	private int event_code;
	private String eventType;
	private String eventName;
	private String topic;
	private Date eventDate;
	private Date startTime;
	private Date endTime;
	private String eopDescription;
	private String location;
	private String speaker;
	private int estimatedCandidates;
	private String openTo;
	private String openToRegistration;
	private int organizer;
	private int buCode;
	private String cityCode;
	private String sscCode;
	private String agentTeam;
	private String profilePath;
	private String topicPath;
	private String specialGrListingPath;
	private String createdBy;
	private Date creationDate;
	private String modifiedBy;
	private Date modificationDate;
	private boolean status;
	private String token;
	private int district;
	private String buName;
	private String distName;
	private String cityName;
	private String sscName;
	private String userName;
	private int registeredCount;
	private int attendeeCount;
	private int completedCount;
	private String oraganiserStr;
	private String timeStart;
	private String timeEnd;
	private String eventDateStr;
	private String openToSpGrp;
	private String publicUrl;
	private int branchCode;
	private String branchName;
	private Boolean isRegistered;
	private String officeCode;
	private String officeName;
	private String attendedStatus;
	private Integer calendarServiceError;
	
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
	  * <p>Default Constructor.Setting default values for Event fields.</p>
	  */
	public Event(){
		
		event_code = 0;
		eventType = "";
		eventName = "";
		topic = "";
		eventDate = null;
		startTime = null;
		endTime = null;
		eopDescription = "";
		location = "";
		speaker = "";
		estimatedCandidates = 0;
		openTo = "";
		openToRegistration = "";
		organizer = 0;
		agentTeam = "";
		profilePath = "";
		topicPath = "";
		specialGrListingPath = "";
		buCode = 0;
		cityCode = "";
		sscCode = "";
		createdBy = "";
		creationDate = null;
		modifiedBy = "";
		modificationDate = null;
		status = false;
		token="";
		district=0;
		buName = "";
		distName = "";
		cityName = "";
		sscName = "";
		userName = "";
		registeredCount =0;
		attendeeCount = 0;
		completedCount = 0;
		oraganiserStr = "";
		timeStart = "";
		timeEnd = "";
		eventDateStr = "";
		openToSpGrp = "";
		publicUrl = "";
		branchCode=0;
		branchName="";
		officeCode="";
		officeName="";
		
	}
	
	/**
	 * @return the event_code
	 */
	public int getEvent_code() {
		return event_code;
	}

	/**
	 * @param event_code the event_code to set
	 */
	public void setEvent_code(int event_code) {
		this.event_code = event_code;
	}

	/**
	 * @return the eventType
	 */
	public String getEventType() {
		return eventType;
	}

	/**
	 * @param eventType the eventType to set
	 */
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	/**
	 * @return the eventName
	 */
	public String getEventName() {
		return eventName;
	}

	/**
	 * @param eventName the eventName to set
	 */
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	/**
	 * @return the topic
	 */
	public String getTopic() {
		return topic;
	}

	/**
	 * @param topic the topic to set
	 */
	public void setTopic(String topic) {
		this.topic = topic;
	}

	/**
	 * @return the eventDate
	 */
	public Date getEventDate() {
		return eventDate;
	}

	/**
	 * @param eventDate the eventDate to set
	 */
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the eopDescription
	 */
	public String getEopDescription() {
		return eopDescription;
	}

	/**
	 * @param eopDescription the eopDescription to set
	 */
	public void setEopDescription(String eopDescription) {
		this.eopDescription = eopDescription;
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
	 * @return the speaker
	 */
	public String getSpeaker() {
		return speaker;
	}

	/**
	 * @param speaker the speaker to set
	 */
	public void setSpeaker(String speaker) {
		this.speaker = speaker;
	}

	/**
	 * @return the estimatedCandidates
	 */
	public int getEstimatedCandidates() {
		return estimatedCandidates;
	}

	/**
	 * @param estimatedCandidates the estimatedCandidates to set
	 */
	public void setEstimatedCandidates(int estimatedCandidates) {
		this.estimatedCandidates = estimatedCandidates;
	}

	/**
	 * @return the openTo
	 */
	public String getOpenTo() {
		return openTo;
	}

	/**
	 * @param openTo the openTo to set
	 */
	public void setOpenTo(String openTo) {
		this.openTo = openTo;
	}

	/**
	 * @return the openToRegistration
	 */
	public String getOpenToRegistration() {
		return openToRegistration;
	}

	/**
	 * @param openToRegistration the openToRegistration to set
	 */
	public void setOpenToRegistration(String openToRegistration) {
		this.openToRegistration = openToRegistration;
	}

	/**
	 * @return the organizer
	 */
	public int getOrganizer() {
		return organizer;
	}

	/**
	 * @param organizer the organizer to set
	 */
	public void setOrganizer(int organizer) {
		this.organizer = organizer;
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
	 * @return the agentTeam
	 */
	public String getAgentTeam() {
		return agentTeam;
	}

	/**
	 * @param agentTeam the agentTeam to set
	 */
	public void setAgentTeam(String agentTeam) {
		this.agentTeam = agentTeam;
	}

	/**
	 * @return the profilePath
	 */
	public String getProfilePath() {
		return profilePath;
	}

	/**
	 * @param profilePath the profilePath to set
	 */
	public void setProfilePath(String profilePath) {
		this.profilePath = profilePath;
	}

	/**
	 * @return the topicPath
	 */
	public String getTopicPath() {
		return topicPath;
	}

	/**
	 * @param topicPath the topicPath to set
	 */
	public void setTopicPath(String topicPath) {
		this.topicPath = topicPath;
	}

	/**
	 * @return the specialGrListingPath
	 */
	public String getSpecialGrListingPath() {
		return specialGrListingPath;
	}

	/**
	 * @param specialGrListingPath the specialGrListingPath to set
	 */
	public void setSpecialGrListingPath(String specialGrListingPath) {
		this.specialGrListingPath = specialGrListingPath;
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
	 * @return the completedCount
	 */
	public int getCompletedCount() {
		return completedCount;
	}

	/**
	 * @param completedCount the completedCount to set
	 */
	public void setCompletedCount(int completedCount) {
		this.completedCount = completedCount;
	}

	/**
	 * @return the oraganiserStr
	 */
	public String getOraganiserStr() {
		return oraganiserStr;
	}

	/**
	 * @param oraganiserStr the oraganiserStr to set
	 */
	public void setOraganiserStr(String oraganiserStr) {
		this.oraganiserStr = oraganiserStr;
	}

	/**
	 * @return the timeStart
	 */
	public String getTimeStart() {
		return timeStart;
	}

	/**
	 * @param timeStart the timeStart to set
	 */
	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}

	/**
	 * @return the timeEnd
	 */
	public String getTimeEnd() {
		return timeEnd;
	}

	/**
	 * @param timeEnd the timeEnd to set
	 */
	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

	/**
	 * @return the eventDateStr
	 */
	public String getEventDateStr() {
		return eventDateStr;
	}

	/**
	 * @param eventDateStr the eventDateStr to set
	 */
	public void setEventDateStr(String eventDateStr) {
		this.eventDateStr = eventDateStr;
	}

	/**
	 * @return the openToSpGrp
	 */
	public String getOpenToSpGrp() {
		return openToSpGrp;
	}

	/**
	 * @param openToSpGrp the openToSpGrp to set
	 */
	public void setOpenToSpGrp(String openToSpGrp) {
		this.openToSpGrp = openToSpGrp;
	}

	/**
	 * @return the publicUrl
	 */
	public String getPublicUrl() {
		return publicUrl;
	}

	/**
	 * @param publicUrl the publicUrl to set
	 */
	public void setPublicUrl(String publicUrl) {
		this.publicUrl = publicUrl;
	}

	/**
	 * @return the attendedStatus
	 */
	public String getAttendedStatus() {
		return attendedStatus;
	}

	/**
	 * @param attendedStatus the attendedStatus to set
	 */
	public void setAttendedStatus(String attendedStatus) {
		this.attendedStatus = attendedStatus;
	}

	public Boolean getIsRegistered() {
		return isRegistered;
	}

	public void setIsRegistered(Boolean isRegistered) {
		this.isRegistered = isRegistered;
	}
	
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

	@Override
   public String toString() {
		
		return ("Event Name-> "+eventName + " Event Code-> "+event_code );
	}

	/**
	 * <p>Dynamic rows added based schedule list size</p>
	 * @param i    each row number
	 * @param lastElement last element index
	 * @return row as string
	 */
	
	public String getGetEventListingTableRow()
    {
		String eventDt = "",organiserValue = "",returnStr = "",publicRegStatus="";
		if(this.eventDate!=null)
			eventDt = LMSUtil.convertDateToString(this.eventDate);	
		Date todayDate = new Date();
		String modifyLink = "<a href=\" FormManager?key=EopScheduleAdd&type=MODIFY&eventCode=" + this.event_code + "\"><img src=images/edit.png border=0></a>";
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat ra = new SimpleDateFormat("HH:mm a");
		try {
			
			if(sdf1.parse(sdf1.format(todayDate)).equals(this.eventDate)){
				Date crtTime=new Date(System.currentTimeMillis());
				Date stTime=this.startTime;
				crtTime=ra.parse(ra.format(crtTime));
				stTime=ra.parse(ra.format(stTime));
				if(crtTime.after(stTime))
				{
					modifyLink = "";
				}
				 
			
			}else if(sdf1.parse(sdf1.format(todayDate)).after(this.eventDate)){
					modifyLink = "";
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String date="";
		if(this.modificationDate!=null)
			date = LMSUtil.convertDateToString(this.modificationDate);
		
	    String deleteLink = "javascript:confirmDelete('" + event_code + "')";
	    String viewAttendanceLink = "ContentManager?key=EopAttendanceDetails&eventCode=" + this.event_code;
	    
	    if("eop".equals(this.eventType))
      	  	 this.eventType = "EOP";
        else if("companyevent".equals(this.eventType))
        	this.eventType = "Company Event";
        else if("networking".equals(this.eventType))
        	this.eventType = "Networking";
        else if("training".equals(this.eventType))
        	this.eventType = "Training";
	    
		if(this.organizer == 1)
			organiserValue = "CHO";
		else if(this.organizer == 2)
			organiserValue = "BU";
		else if(this.organizer == 3)
			organiserValue = "DISTRICT";
		else if(this.organizer == 4)
			organiserValue = "CITY";
		else if(this.organizer == 5)
			organiserValue = "SSC";
		else if(this.organizer == 6)
			organiserValue = "Agent Team";
		else if(this.organizer == 7)
			organiserValue = "Branch";
		else if(this.organizer == 8)
			organiserValue = "Office";
		
		if(openToRegistration!=null && openToRegistration.equals("Y"))
			publicRegStatus = "Yes";
		else
			publicRegStatus = "No";
		
		//* The action buttons
        String actionLinks = modifyLink + "&nbsp;<a href=\"" + deleteLink + "\"><img src=images/delete.png border=0></a>";
        if (this.status == false)
        {
            // deleted event do not have the edit and delete link
            actionLinks = "";
        }
        
        if (this.getCalendarServiceError() != null && this.getCalendarServiceError() > 0)
        {
            actionLinks += "&nbsp;<a href=\"FormManager?key=EopScheduleAdd&type=RESYNC&eventCode=" + this.getEvent_code() + "\"><img src=images/sync.png border=0></a>";
        }
        
        returnStr = "<tr> "
                + "<td>" + "<div align=center><a href=\"" + viewAttendanceLink + "\">" + SecurityAPI.encodeHTML(this.eventName) + "</a></div></td>"
                + "<td>" + SecurityAPI.encodeHTML(this.eventType) + "</td>"
                + "<td>" + SecurityAPI.encodeHTML(eventDt) + "</td>"
                + "<td>" + SecurityAPI.encodeHTML(LMSUtil.blankIfNull(this.buName)) + "</td>"
                + "<td>" + SecurityAPI.encodeHTML(LMSUtil.blankIfNull(this.distName)) + "</td>"
                + "<td>" + SecurityAPI.encodeHTML(LMSUtil.blankIfNull(this.branchName)) + "</td>"
                + "<td>" + SecurityAPI.encodeHTML(LMSUtil.blankIfNull(this.cityName)) + "</td>"
                + "<td>" + SecurityAPI.encodeHTML(LMSUtil.blankIfNull(this.sscName)) + "</td>"
                + "<td>" + SecurityAPI.encodeHTML(LMSUtil.blankIfNull(this.officeName)) + "</td>"
                + "<td>" + SecurityAPI.encodeHTML(organiserValue) + "</td>"
                + "<td>" + SecurityAPI.encodeHTML(publicRegStatus) + "</td>"
                + "<td>" + SecurityAPI.encodeHTML(this.speaker) + "</td>"
                + "<td>" + SecurityAPI.encodeHTML(String.valueOf(this.registeredCount)) + "</td>"
                + "<td>" + SecurityAPI.encodeHTML(String.valueOf(this.attendeeCount)) + "</td>"
                + "<td >" + SecurityAPI.encodeHTML(this.modifiedBy) + "</td>"
                + "<td >" + date + "</div></td>"
                + "<td><div align=center>" + actionLinks + "</div></td>"
                + "</tr>";
        return returnStr;
    }
	 

	
	

}
