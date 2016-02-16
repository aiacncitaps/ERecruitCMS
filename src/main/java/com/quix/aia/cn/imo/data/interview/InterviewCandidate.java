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
* 16-July-2015       Hemraj          Interview Attendance Bean

****************************************** *********************************** */
package com.quix.aia.cn.imo.data.interview;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.quix.aia.cn.imo.data.addressbook.AddressBook;
import com.quix.aia.cn.imo.data.locale.LocaleObject;
import com.quix.aia.cn.imo.data.user.User;
import com.quix.aia.cn.imo.mapper.AddressBookMaintenance;
import com.quix.aia.cn.imo.mapper.InterviewAttendanceMaintenance;
import com.quix.aia.cn.imo.utilities.KeyObjPair;
import com.quix.aia.cn.imo.utilities.LMSUtil;
import com.quix.aia.cn.imo.utilities.SecurityAPI;

public class InterviewCandidate
{
	private int candidateCode;
	private int  interviewCode;
	private String candidateName;
	private String servicingAgent;
	private String serviceingAgentName;
	private String agentName;
	private String nric;
	private int buCode;
	private int branchCode;
	private String officeCode;
	private String cityCode;
	private String sscCode;
	private int districtCode;
	private String buName;
	private String distName;
	private String officeName;
	private String branchName;
    private String statusStr;
    private Integer noOfRegisteredInterview;
    private String passedStatus;
    private Date interviewDate;
    private String interviewSessionName;
    private String teamName;
    private String strMaterialCode;
    private String uploadBy;
	
	
	public int getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(int branchCode) {
		this.branchCode = branchCode;
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
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
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
	private String ccTestResult;
	private String recruitmentScheme;
	private int p100;
	private String interviewResult;
	private String remarks;
	private boolean status;
	private String token;
	private String fileNameList;
	private String interviewCandidateCode;
	private String ccResult;
	private String interviwer_name;
	
	public InterviewCandidate()
	{
		candidateCode = 0;
		interviewCode = 0;
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
		interviewResult = "";
		status = false;
		token = "";
		dobStr = "";
		ccTestResult="";
		recruitmentScheme="";
		p100=0;
		interviewResult="";
		remarks="";
		nric = "";
		fileNameList="";
		interviewCandidateCode="";
		ccResult="";
		branchCode=0;
		officeCode="";
		branchName="";
		officeName="";
		noOfRegisteredInterview = 0;
		passedStatus = "N";
		interviwer_name="";
		interviewSessionName="";
		serviceingAgentName="";
		teamName = "";
	}
	
	
	
	public String getInterviwer_name() {
		return interviwer_name;
	}
	public void setInterviwer_name(String interviwer_name) {
		this.interviwer_name = interviwer_name;
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
	 * @param dobStr the dobStr to set
	 */
	public void setDobStr(String dobStr) {
		this.dobStr = dobStr;
	}
	
	/**
	 * @return the interviewCode
	 */
	public int getInterviewCode() {
		return interviewCode;
	}
	/**
	 * @param interviewCode the interviewCode to set
	 */
	public void setInterviewCode(int interviewCode) {
		this.interviewCode = interviewCode;
	}
	/**
	 * @return the ccTestResult
	 */
	public String getCcTestResult() {
		return ccTestResult;
	}
	/**
	 * @param ccTestResult the ccTestResult to set
	 */
	public void setCcTestResult(String ccTestResult) {
		this.ccTestResult = ccTestResult;
	}
	/**
	 * @return the recruitmentScheme
	 */
	public String getRecruitmentScheme() {
		return recruitmentScheme;
	}
	/**
	 * @param recruitmentScheme the recruitmentScheme to set
	 */
	public void setRecruitmentScheme(String recruitmentScheme) {
		this.recruitmentScheme = recruitmentScheme;
	}
	/**
	 * @return the p100
	 */
	public int getP100() {
		return p100;
	}
	/**
	 * @param p100 the p100 to set
	 */
	public void setP100(int p100) {
		this.p100 = p100;
	}
	/**
	 * @return the interviewResult
	 */
	public String getInterviewResult() {
		return interviewResult;
	}
	/**
	 * @param interviewResult the interviewResult to set
	 */
	public void setInterviewResult(String interviewResult) {
		this.interviewResult = interviewResult;
	}
	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	 * @return the interviewCandidateCode
	 */
	public String getInterviewCandidateCode() {
		return interviewCandidateCode;
	}
	
	/**
	 * @param interviewCandidateCode the interviewCandidateCode to set
	 */
	public void setInterviewCandidateCode(String interviewCandidateCode) {
		this.interviewCandidateCode = interviewCandidateCode;
	}
	
	
	/**
	 * @return the fileNameList
	 */
	public String getFileNameList() {
		return fileNameList;
	}
	/**
	 * @param fileNameList the fileNameList to set
	 */
	public void setFileNameList(String fileNameList) {
		this.fileNameList = fileNameList;
	}
	
	
	/**
	 * @return the ccResult
	 */
	public String getCcResult() {
		return ccResult;
	}
	/**
	 * @param ccResult the ccResult to set
	 */
	public void setCcResult(String ccResult) {
		this.ccResult = ccResult;
	}
	
	/**
	 * @return the noOfRegisteredInterview
	 */
	public Integer getNoOfRegisteredInterview() {
		return noOfRegisteredInterview;
	}
	
	/**
	 * @return the passedStatus
	 */
	public String getPassedStatus() {
		return passedStatus;
	}
	/**
	 * @param passedStatus the passedStatus to set
	 */
	public void setPassedStatus(String passedStatus) {
		this.passedStatus = passedStatus;
	}
	/**
	 * @param noOfRegisteredInterview the noOfRegisteredInterview to set
	 */
	public void setNoOfRegisteredInterview(Integer noOfRegisteredInterview) {
		this.noOfRegisteredInterview = noOfRegisteredInterview;
	}
	public String getGetAttendanceListingTableRow(LocaleObject localeObj, User userObj)
    {
		String dob = "",gen = "",returnStr = "";
		if(this.dob!=null)
			dob = LMSUtil.convertDateToString(this.dob);
		this.dobStr = dob;
		
		if("F".equalsIgnoreCase(this.gender))
			gen = localeObj.getTranslatedText("Female");
		else if("M".equalsIgnoreCase(this.gender))
			gen = localeObj.getTranslatedText("Male");
		
		this.gender =  gen;
		
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
		String modifyLink = "ContentManager?key=ViewCandidateInterviewDetail&interviewCode=" + this.interviewCode +"&candidateCode=" + this.candidateCode;
		String applicationFormLink="ContentManager?key=ViewEapplicationForm&interviewCode=" + this.interviewCode +"&candidateCode=" + this.candidateCode;
		//		String uploadLink = "FormManager?key=ViewCandidateInterviewDetail&type=MODIFY&interviewCode=" + this.interviewCode +"&candidateCode=" + this.candidateCode;
		String uploadLink = "ContentManager?key=InterviewCandidateUpload&type=MODIFY&interviewCode=" + this.interviewCode +"&candidateCode=" + this.candidateCode;
//		String deleteLink = "javascript:confirmDelete('" + candidateCode + "')";
		
		String ur="",no="",pr="",se="";
		
		String[] fetchFields={"addressCode","ccTestResult"};
		String[] conditionFieldName={"addressCode"};
		String[] conditionFieldValue={interviewCandidateCode};
		List<Object []> list = new AddressBookMaintenance().getAddressBookSelectedField(fetchFields, conditionFieldName, conditionFieldValue);
		
		if(null != list && !list.isEmpty()){
			Object[] objs = list.get(0);
			this.ccTestResult =(String) objs[1];
		}
		
		this.ccTestResult=(null == this.ccTestResult)?"":this.ccTestResult;
		if(this.ccTestResult.equals("Urgent"))
			ur="selected";
		if(this.ccTestResult.equals("Normal"))
			no="selected";
		if(this.ccTestResult.equals("Prudent"))
			pr="selected";
		else
			se="selected";
			
		String completeStrCombo = "<select  id="+this.candidateCode+"_ccStatus  name="+this.candidateCode+"_ccStatus style='border:1px solid #CCCCCC;padding:5px 5px;height:30px;width:100%;' >"+
				"<option value='' "+se+">"+localeObj.getTranslatedText("Select")+"</option>"+
				"<option value='Urgent' "+ur+">"+localeObj.getTranslatedText("Urgent Recruit")+"</option>"+
				"<option value='Normal' "+no+">"+localeObj.getTranslatedText("Normal Recruit")+"</option>"+
				"<option value='Prudent' "+pr+">"+localeObj.getTranslatedText("Caution Recruit")+"</option>"+
          "</select>";
		
		String p100Text = "<input type=text class=text maxlength=4 size=10 id="+this.candidateCode+"_p100  name="+this.candidateCode+"_p100 value="+SecurityAPI.encodeHTML(this.p100+"")+" />";
		
		String result="",pass="",fail="",sele="";
		
		if(this.interviewResult.equals("P"))
		{
			result = "PASS";
			pass="selected";
		}
		else if(this.interviewResult.equals("F"))
		{
			result = "FAIL";
			fail="selected";
		}
		else
		{
			sele="selected";
		}
		
		String ga="",ha="",sa="";
		if(this.recruitmentScheme.equals("GA")){
			ga="selected";
		}else if(this.recruitmentScheme.equals("HA")){
			ha="selected";
		}else if(this.recruitmentScheme.equals("SA")){
			sa="selected";
		}
		String interviewResultText = "<select  id="+this.candidateCode+"_interviewResult  name="+this.candidateCode+"_interviewResult style='border:1px solid #CCCCCC;padding:5px 5px;height:30px;width:100%;' >"+
				"<option value='' "+sele+">"+localeObj.getTranslatedText("Select")+"</option>"+
				"<option value='P' "+pass+">"+localeObj.getTranslatedText("PASS")+"</option>"+
				"<option value='F' "+fail+">"+localeObj.getTranslatedText("FAIL")+"</option>"+
          "</select>";
		
		String  recuritmentScheme="<select  id="+this.candidateCode+"_recuritmentScheme  name="+this.candidateCode+"_recuritmentScheme style='border:1px solid #CCCCCC;padding:5px 5px;height:30px;width:100%;' >"+
				/*"<option value='GA' "+ga+">"+localeObj.getTranslatedText("GA")+"</option>"+*/
				"<option value='HA' "+ha+">"+localeObj.getTranslatedText("HA")+"</option>"+
				"<option value='SA' "+sa+">"+localeObj.getTranslatedText("SA")+"</option>"+
          "</select>";
		
		String agentName="",agencyLeadCode="";;
	
		if(this.agentName!=null && !this.agentName.equals("null")){
			agentName=this.agentName;
			this.agentName = agentName;
			
		}
		
		if(this.agencyLeaderCode!=null && !this.agencyLeaderCode.equals("null")){
			agencyLeadCode=this.agencyLeaderCode;
			this.agencyLeaderCode = agencyLeadCode;
			
		}
		
//		String interviewResultText = "<input type=text class=text maxlength=10 size=10 id="+this.candidateCode+"_interviewResult  name="+this.candidateCode+"_interviewResult value='"+SecurityAPI.encodeHTML(result)+"' />";
		
		String remarksText = "<input type=text class=text maxlength=30 size=10 id="+this.candidateCode+"_remarks  name="+this.candidateCode+"_remarks value='"+SecurityAPI.encodeHTML(this.remarks)+"' />";
		
		ccResult = ccResult==null?"":ccResult;
		String name="";
		if(this.interviwer_name.length()>0){
			name=this.interviwer_name;
		}else{
			name=userObj.getStaffName();
		}
		
		String sourcOfRefereal=InterviewAttendanceMaintenance.SOURCE_0F_REFERRAL(this.sourceOfReferal);
		if(sourcOfRefereal!=null){
			sourcOfRefereal=localeObj.getTranslatedText(sourcOfRefereal);
			this.sourceOfReferal=sourcOfRefereal;
		}
		//this.sourceOfReferal
		String Interviwer_name = "<input type=text class=text maxlength=30 size=10 id="+this.candidateCode+"_interviewer_name  name="+this.candidateCode+"_interviewer_name value='"+SecurityAPI.encodeHTML(name)+"' />";
		
		
		
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
				/*"<td><div style='word-break: break-all;word-wrap: break-word;white-space: pre-line;'>"+SecurityAPI.encodeHTML(agencyLeaderNm)+"</div></td>"+*/
				"<td><div style='word-break: break-all;word-wrap: break-word;white-space: pre-line;'>"+SecurityAPI.encodeHTML(this.teamName)+"</div></td>"+
				"<td><div style='word-break: break-all;word-wrap: break-word;white-space: pre-line;'>"+SecurityAPI.encodeHTML(sourcOfRefereal)+"</div></td>"+
				"<td><div style='word-break: break-all;word-wrap: break-word;white-space: pre-line;'>"+SecurityAPI.encodeHTML(String.valueOf(this.age))+"</div></td>"+
				"<td><div>"+SecurityAPI.encodeHTML(dob)+"</div></td>"+
				"<td><div style='word-break: break-all;word-wrap: break-word;white-space: pre-line;'>"+SecurityAPI.encodeHTML(String.valueOf(gen))+"</div></td>"+
				"<td><div style='text-align:center'>"+SecurityAPI.encodeHTML(String.valueOf(this.contactNumber))+"</div></td>"+
				"<td style='padding:5px 5px' ><div style='text-align:center'>"+completeStrCombo+"</div></td>"+
				
				
				//"<td><div style='text-align:center'>"+SecurityAPI.encodeHTML(this.recruitmentScheme==null?"":this.recruitmentScheme)+"</div></td>"+
				"<td><div style='text-align:center'>"+recuritmentScheme+"</div></td>"+
				
				"<td><div style='text-align:center'><a href=\"" + modifyLink + "\">"+localeObj.getTranslatedText("View")+"</a></div></td>"+
				"<td><div style='text-align:center'><a href=\"" + applicationFormLink + "\" target=_blank>"+localeObj.getTranslatedText("View")+"</a></div></td>"+
				
				"<td><div style='text-align:center'><a href=\"" + modifyLink + "\" >"+localeObj.getTranslatedText("View")+" | "+localeObj.getTranslatedText("Upload")+"</a></div></td>"+
				"<td><div style='text-align:center'>"+p100Text+"</div></td>"+
				"<td><div style='text-align:center'>"+interviewResultText+"</div></td>"+
				"<td><div style='text-align:center'>"+remarksText+"</div></td>"+
				"<td><div style='text-align:center'>"+Interviwer_name+"</div></td>"+
		   "</tr>";
		return returnStr;
    }
	public String getStatusStr() {
		return statusStr;
	}
	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}
	public Date getInterviewDate() {
		return interviewDate;
	}
	public void setInterviewDate(Date interviewDate) {
		this.interviewDate = interviewDate;
	}
	public String getInterviewSessionName() {
		return interviewSessionName;
	}
	public void setInterviewSessionName(String interviewSessionName) {
		this.interviewSessionName = interviewSessionName;
	}
	public String getServiceingAgentName() {
		return serviceingAgentName;
	}
	public void setServiceingAgentName(String serviceingAgentName) {
		this.serviceingAgentName = serviceingAgentName;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getStrMaterialCode() {
		return strMaterialCode;
	}
	public void setStrMaterialCode(String strMaterialCode) {
		this.strMaterialCode = strMaterialCode;
	}
	public String getUploadBy() {
		return uploadBy;
	}
	public void setUploadBy(String uploadBy) {
		this.uploadBy = uploadBy;
	}
	
}
