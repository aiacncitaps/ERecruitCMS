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
* 16-June-2015       Nibedita          Eop Attendance Bean

****************************************** *********************************** */
package com.quix.aia.cn.imo.data.event;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.quix.aia.cn.imo.data.locale.LocaleObject;
import com.quix.aia.cn.imo.mapper.InterviewAttendanceMaintenance;
import com.quix.aia.cn.imo.utilities.LMSUtil;
import com.quix.aia.cn.imo.utilities.SecurityAPI;

public class EventCandidate {
	private int candidateCode;
	private int  eventCode;
	private String candidateName;
	private String servicingAgent;
	private String agentName;
	private int buCode;
	private String cityCode;
	private String sscCode;
	private int districtCode;
	private String buName;
	private String distName;
	private String cityName;
	private String sscName;
	private String agencyLeaderCode;
	private String agencyLeaderName;
	private String sourceOfReferal;
	private int age;
	private Date dob;
	private String dobStr;
	private String gender;
	private String contactNumber;
	private Date timeIn;
	private String completeStatus;
	private boolean status;
	private String token;
	private int branchCode;
	private String branchName;
	private String eventCandidateCode;
    private String officeCode;
    private String officeName;
    private String statusStr;
    private String attendanceStatus;
    private Integer noOfRegisteredEvents;
    private String weChat;
    private String mailId;
    private String nric;
    private String education;
    private String teamName;
	  
	
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
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public EventCandidate(){
		candidateCode = 0;
		branchCode=0;
		branchName="";
		eventCode = 0;
		candidateName = "";
		servicingAgent = "";
		agentName = "";
		buCode = 0;
		cityCode = "";
		sscCode = "";
		districtCode = 0;
		buName = "";
		distName = "";
		cityName = "";
		sscName = "";
		agencyLeaderCode = "";
		agencyLeaderName = "";
		sourceOfReferal = "";
		age = 0;
		gender = "";
		contactNumber = "";
		completeStatus = "";
		status = false;
		token = "";
		dobStr = "";
		officeCode="";
		officeName="";
		attendanceStatus = null;
		noOfRegisteredEvents=0;
		weChat = "";
		mailId = "";
		nric = "";
		education = "";
		teamName = "";
	}
	/**
	 * @return the candidateCode
	 */
	public int getCandidateCode() {
		return candidateCode;
	}
	/**
	 * @param candidateCode the candidateCode to set
	 */
	public void setCandidateCode(int candidateCode) {
		this.candidateCode = candidateCode;
	}
	
	/**
	 * @return the eventCode
	 */
	public int getEventCode() {
		return eventCode;
	}
	/**
	 * @param eventCode the eventCode to set
	 */
	public void setEventCode(int eventCode) {
		this.eventCode = eventCode;
	}
	/**
	 * @return the candidateName
	 */
	public String getCandidateName() {
		return candidateName;
	}
	/**
	 * @param candidateName the candidateName to set
	 */
	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}
	/**
	 * @return the servicingAgent
	 */
	public String getServicingAgent() {
		return servicingAgent;
	}
	/**
	 * @param servicingAgent the servicingAgent to set
	 */
	public void setServicingAgent(String servicingAgent) {
		this.servicingAgent = servicingAgent;
	}
	/**
	 * @return the agentName
	 */
	public String getAgentName() {
		return agentName;
	}
	/**
	 * @param agentName the agentName to set
	 */
	public void setAgentName(String agentName) {
		this.agentName = agentName;
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
	 * @return the districtCode
	 */
	public int getDistrictCode() {
		return districtCode;
	}
	/**
	 * @param districtCode the districtCode to set
	 */
	public void setDistrictCode(int districtCode) {
		this.districtCode = districtCode;
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
	 * @return the agencyLeaderCode
	 */
	public String getAgencyLeaderCode() {
		return agencyLeaderCode;
	}
	/**
	 * @param agencyLeaderCode the agencyLeaderCode to set
	 */
	public void setAgencyLeaderCode(String agencyLeaderCode) {
		this.agencyLeaderCode = agencyLeaderCode;
	}
	/**
	 * @return the agencyLeaderName
	 */
	public String getAgencyLeaderName() {
		return agencyLeaderName;
	}
	/**
	 * @param agencyLeaderName the agencyLeaderName to set
	 */
	public void setAgencyLeaderName(String agencyLeaderName) {
		this.agencyLeaderName = agencyLeaderName;
	}
	/**
	 * @return the sourceOfReferal
	 */
	public String getSourceOfReferal() {
		return sourceOfReferal;
	}
	/**
	 * @param sourceOfReferal the sourceOfReferal to set
	 */
	public void setSourceOfReferal(String sourceOfReferal) {
		this.sourceOfReferal = sourceOfReferal;
	}
	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}
	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}
	/**
	 * @return the dob
	 */
	public Date getDob() {
		return dob;
	}
	/**
	 * @param dob the dob to set
	 */
	public void setDob(Date dob) {
		this.dob = dob;
	}
	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}
	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	/**
	 * @return the contactNumber
	 */
	public String getContactNumber() {
		return contactNumber;
	}
	/**
	 * @param contactNumber the contactNumber to set
	 */
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	/**
	 * @return the timeIn
	 */
	public Date getTimeIn() {
		return timeIn;
	}
	/**
	 * @param timeIn the timeIn to set
	 */
	public void setTimeIn(Date timeIn) {
		this.timeIn = timeIn;
	}
	/**
	 * @return the completeStatus
	 */
	public String getCompleteStatus() {
		return completeStatus;
	}
	/**
	 * @param completeStatus the completeStatus to set
	 */
	public void setCompleteStatus(String completeStatus) {
		this.completeStatus = completeStatus;
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
	 * <p>Override toString()</p>
	 */
	
	@Override
	   public String toString() {
			
			return ("Candidate Name-> "+ candidateName + " Servicing Agent-> "+servicingAgent );
		}
	/**
	 * @return the dobStr
	 */
	public String getDobStr() {
		return dobStr;
	}
	
	/**
	 * @return the noOfRegisteredEvents
	 */
	public Integer getNoOfRegisteredEvents() {
		return noOfRegisteredEvents;
	}
	/**
	 * @param noOfRegisteredEvents the noOfRegisteredEvents to set
	 */
	public void setNoOfRegisteredEvents(Integer noOfRegisteredEvents) {
		this.noOfRegisteredEvents = noOfRegisteredEvents;
	}
	/**
	 * @param dobStr the dobStr to set
	 */
	public void setDobStr(String dobStr) {
		this.dobStr = dobStr;
	}
	public String getGetAttendanceListingTableRow(LocaleObject localeObj)
    {
		String dob = "",gen = "",timeInStr = "",returnStr = "";
		SimpleDateFormat format = new SimpleDateFormat("hh:mm");
		if(this.dob!=null)
			dob = LMSUtil.convertDateToString(this.dob);
		this.dobStr = dob;
		
		if(this.timeIn!=null)
			//timeInStr = LMSUtil.convertDateToString(this.timeIn);
		timeInStr = format.format(this.timeIn);
		if("F".equalsIgnoreCase(this.gender))
			gen = "Female";
		else if("M".equalsIgnoreCase(this.gender))
			gen = "Male";
		
		this.gender =  gen;
		
		String ySelect = "",nSelect = "" ;
		if("Y".equalsIgnoreCase(this.completeStatus))
			ySelect = "selected";
		else if("N".equalsIgnoreCase(this.completeStatus))
			nSelect = "selected";
		this.completeStatus = ("Y".equalsIgnoreCase(this.completeStatus)? "Yes" : "No");
		if(this.contactNumber !=null && this.contactNumber.length() > 0){
			 StringBuffer buf = new StringBuffer(this.contactNumber);
			if(this.contactNumber.length()>3){
				 buf.setCharAt(3, '*');
			}
			if(this.contactNumber.length()>4){
				 buf.setCharAt(4, '*');		
			}
			if(this.contactNumber.length()>5){
				buf.setCharAt(5, '*');
			}
			if(this.contactNumber.length()>6){
				buf.setCharAt(6, '*');
			}
			
			 this.contactNumber = buf.toString();
		}
		
		
        String sourcOfRefereal=InterviewAttendanceMaintenance.SOURCE_0F_REFERRAL(this.sourceOfReferal);
        if(sourcOfRefereal!=null){
        	this.sourceOfReferal=localeObj.getTranslatedText(sourcOfRefereal);
        }

		
		String modifyLink = "FormManager?key=EopCandidateReg&type=MODIFY&eventCode=" + this.eventCode +"&candidateCode=" + this.candidateCode;
		String completeStrCombo = "<select  id="+this.candidateCode+"_completeSts  name="+this.candidateCode+"_completeSts style=border: 1px solid #CCCCCC; padding: 10px 10px;height: 45px;>"+
										"<option value=N  "+nSelect+">No</option>"+
										"<option value=Y  "+ySelect+">Yes</option>"+
						          "</select>";
		//TIME IN COMBO
		if(this.timeIn==null)
			nSelect="selected";
		else
			ySelect="selected";
		String timeinCombo = "<select  id="+this.candidateCode+"_timeIn  name="+this.candidateCode+"_timeIn style=border: 1px solid #CCCCCC; padding: 10px 10px;height: 45px;>"+
				"<option value=N  "+nSelect+">"+localeObj.getTranslatedText("No")+" </option>"+
				"<option value=Y  "+ySelect+">"+localeObj.getTranslatedText("Yes")+"</option>"+
          "</select>";
		String deleteLink = "javascript:confirmDelete('" + candidateCode + "')";				                                        		
			if(  this.agentName==null){
				this.agentName="";
			}	
			if(this.agencyLeaderCode==null){
				this.agencyLeaderCode="";
			}
	    returnStr ="<tr> "+
		 "<td><div style='word-break: break-all;word-wrap: break-word;white-space: pre-line;'>"+SecurityAPI.encodeHTML(this.candidateName)+"</div></td>"+
		"<td><div style='word-break: break-all;word-wrap: break-word;white-space: pre-line;'>"+SecurityAPI.encodeHTML(this.servicingAgent)+"</div></td>"+
		"<td><div style='word-break: break-all;word-wrap: break-word;white-space: pre-line;'>"+SecurityAPI.encodeHTML(this.agentName)+"</div></td>"+
		"<td><div style='word-break: break-all;word-wrap: break-word;white-space: pre-line;'>"+SecurityAPI.encodeHTML(LMSUtil.blankIfNull(this.buName))+"</div></td>"+
		"<td><div style='word-break: break-all;word-wrap: break-word;white-space: pre-line;'>"+SecurityAPI.encodeHTML(LMSUtil.blankIfNull(this.distName))+"</div></td>"+
		"<td><div style='word-break: break-all;word-wrap: break-word;white-space: pre-line;'>"+SecurityAPI.encodeHTML(LMSUtil.blankIfNull(this.branchName))+"</div></td>"+
		"<td><div style='word-break: break-all;word-wrap: break-word;white-space: pre-line;'>"+SecurityAPI.encodeHTML(LMSUtil.blankIfNull(this.cityName))+"</div></td>"+
		"<td><div style='word-break: break-all;word-wrap: break-word;white-space: pre-line;'>"+SecurityAPI.encodeHTML(LMSUtil.blankIfNull(this.officeName))+"</div></td>"+
		"<td><div style='word-break: break-all;word-wrap: break-word;white-space: pre-line;'>"+SecurityAPI.encodeHTML(LMSUtil.blankIfNull(this.sscName))+"</div></td>"+
		"<td><div style='word-break: break-all;word-wrap: break-word;white-space: pre-line;'>"+SecurityAPI.encodeHTML(this.agencyLeaderCode)+"</div></td>"+
		/*"<td><div style='word-break: break-all;word-wrap: break-word;white-space: pre-line;'>"+SecurityAPI.encodeHTML(this.agencyLeaderName)+"</div></td>"+*/
		"<td><div style='word-break: break-all;word-wrap: break-word;white-space: pre-line;'>"+SecurityAPI.encodeHTML(this.teamName)+"</div></td>"+
		"<td><div style='word-break: break-all;word-wrap: break-word;white-space: pre-line;'>"+SecurityAPI.encodeHTML(this.sourceOfReferal)+"</div></td>"+
		"<td><div style='word-break: break-all;word-wrap: break-word;white-space: pre-line;'>"+SecurityAPI.encodeHTML(String.valueOf(this.age))+"</div></td>"+
		"<td><div>"+SecurityAPI.encodeHTML(dob)+"</div></td>"+
		"<td><div style='word-break: break-all;word-wrap: break-word;white-space: pre-line;'>"+SecurityAPI.encodeHTML(localeObj.getTranslatedText(String.valueOf(gen)))+"</div></td>"+
		"<td><div>"+SecurityAPI.encodeHTML(String.valueOf(this.contactNumber))+"</div></td>"+
		"<td><div align=center><a href=\"" + modifyLink + "\"><img src=images/edit.png border=0></a>&nbsp;<a href=\"" + deleteLink + "\"><img src=images/delete.png border=0></a></div></td>"+
		"<td>"+timeinCombo+"</td>"+
//		"<td>"+completeStrCombo+"</td>"+
		"</tr>";
		return returnStr;
    }
	public int getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(int branchCode) {
		this.branchCode = branchCode;
	}
	public String getEventCandidateCode() {
		return eventCandidateCode;
	}
	public void setEventCandidateCode(String eventCandidateCode) {
		this.eventCandidateCode = eventCandidateCode;
	}
	public String getStatusStr() {
		return statusStr;
	}
	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}
	/**
	 * @return the attendanceStatus
	 */
	public String getAttendanceStatus() {
		return attendanceStatus;
	}
	/**
	 * @param attendanceStatus the attendanceStatus to set
	 */
	public void setAttendanceStatus(String attendanceStatus) {
		this.attendanceStatus = attendanceStatus;
	}
	/**
	 * @return the weChat
	 */
	public String getWeChat() {
		return weChat;
	}
	/**
	 * @param weChat the weChat to set
	 */
	public void setWeChat(String weChat) {
		this.weChat = weChat;
	}
	
	/**
	 * @return the mailId
	 */
	public String getMailId() {
		return mailId;
	}
	/**
	 * @param mailId the mailId to set
	 */
	public void setMailId(String mailId) {
		this.mailId = mailId;
	}
	/**
	 * @return the nric
	 */
	public String getNric() {
		return nric;
	}
	/**
	 * @param nric the nric to set
	 */
	public void setNric(String nric) {
		this.nric = nric;
	}
	/**
	 * @return the education
	 */
	public String getEducation() {
		return education;
	}
	/**
	 * @param education the education to set
	 */
	public void setEducation(String education) {
		this.education = education;
	}
	/**
	 * @return the teamName
	 */
	public String getTeamName() {
		return teamName;
	}
	/**
	 * @param teamName the teamName to set
	 */
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
}
