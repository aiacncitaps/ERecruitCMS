package com.quix.aia.cn.imo.data.common;

import java.util.Date;

public class RestForm {
	
	private String agentId;
	private String co;
	private String announcementCode;
	private String candidateCode;
	private String interviewCode;
	private String egreetingCode;
	private String branchCode;
	private String eventCode;
	private String material;
	private String dateTime;
	private String interviewType;
	private String presenterCode;
	private String perAgentId;
	private String recruiterAgentCode;
	private String psw;
	private String branch;
	private String versionNo;
	private String appType;
	private String pageNo;
	private String candidateAgentCode;
	private String ccTestResult;
	private String ccTestResultDate;
	private String interviewResult;
	private String recruitmentPlan;
	private String remarks;
	private Date passTime;
	private Date ccTestResultupdateDate;
	private int addressCode;
	
	

	
	
	
	public RestForm(){
		agentId="";
		co="";
		announcementCode="";
		candidateCode="";
		interviewCode="";
		egreetingCode="";
		branchCode="";
		eventCode="";
		material="";
		dateTime="";
		interviewType="";
		presenterCode="";
		perAgentId="";
		recruiterAgentCode="";
		psw="";
		branch="";
		versionNo="";
		appType="";
		pageNo="";
		candidateAgentCode="";
		ccTestResult="";
		ccTestResultDate="";
		interviewResult="";
		recruitmentPlan="";
		remarks="";
		passTime=null;
		ccTestResultupdateDate=null;
		addressCode=0;
		
		
		
		
	}
	
	
	public String getInterviewResult() {
		return interviewResult;
	}

	public void setInterviewResult(String interviewResult) {
		this.interviewResult = interviewResult;
	}

	public String getRecruitmentPlan() {
		return recruitmentPlan;
	}

	public void setRecruitmentPlan(String recruitmentPlan) {
		this.recruitmentPlan = recruitmentPlan;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getPassTime() {
		return passTime;
	}

	public void setPassTime(Date passTime) {
		this.passTime = passTime;
	}

	
	
	public String getCcTestResult() {
		return ccTestResult;
	}

	public void setCcTestResult(String ccTestResult) {
		this.ccTestResult = ccTestResult;
	}

	public String getCcTestResultDate() {
		return ccTestResultDate;
	}

	public void setCcTestResultDate(String ccTestResultDate) {
		this.ccTestResultDate = ccTestResultDate;
	}

	
	public String getPerAgentId() {
		return perAgentId;
	}

	public void setPerAgentId(String perAgentId) {
		this.perAgentId = perAgentId;
	}

	public String getRecruiterAgentCode() {
		return recruiterAgentCode;
	}

	public void setRecruiterAgentCode(String recruiterAgentCode) {
		this.recruiterAgentCode = recruiterAgentCode;
	}

	
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getCo() {
		return co;
	}
	public void setCo(String co) {
		this.co = co;
	}

	public String getAnnouncementCode() {
		return announcementCode;
	}

	public void setAnnouncementCode(String announcementCode) {
		this.announcementCode = announcementCode;
	}
	
	public String getCandidateCode() {
		return candidateCode;
	}

	public void setCandidateCode(String candidateCode) {
		this.candidateCode = candidateCode;
	}

	public String getInterviewCode() {
		return interviewCode;
	}

	public void setInterviewCode(String interviewCode) {
		this.interviewCode = interviewCode;
	}

	public String getEgreetingCode() {
		return egreetingCode;
	}

	public void setEgreetingCode(String egreetingCode) {
		this.egreetingCode = egreetingCode;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getInterviewType() {
		return interviewType;
	}

	public void setInterviewType(String interviewType) {
		this.interviewType = interviewType;
	}

	public String getPresenterCode() {
		return presenterCode;
	}

	public void setPresenterCode(String presenterCode) {
		this.presenterCode = presenterCode;
	}

	public String getPsw() {
		return psw;
	}

	public void setPsw(String psw) {
		this.psw = psw;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public String getCandidateAgentCode() {
		return candidateAgentCode;
	}

	public void setCandidateAgentCode(String candidateAgentCode) {
		this.candidateAgentCode = candidateAgentCode;
	}


	public Date getCcTestResultupdateDate() {
		return ccTestResultupdateDate;
	}


	public void setCcTestResultupdateDate(Date ccTestResultupdateDate) {
		this.ccTestResultupdateDate = ccTestResultupdateDate;
	}


	public int getAddressCode() {
		return addressCode;
	}


	public void setAddressCode(int addressCode) {
		this.addressCode = addressCode;
	}

}
