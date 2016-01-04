/*******************************************************************************
 * -----------------------------------------------------------------------------
 * <br>
 * <p><b>Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.</b> 
 * <br>
 * <br>
 * This SOURCE CODE FILE, which has been provided by Quix as part
 * of Quix Creations product for use ONLY by licensed users of the product,
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
 * Date                       Developer           Description
 * -----------------------------------------------------------------------------                          
 * 24-July-2015               Maunish             File added 
 ***************************************************************************** */

package com.quix.aia.cn.imo.data.addressbook;

import java.util.Date;

/**
 * <p>
 * CandidateFirstInterview bean class.
 * </p>
 * 
 * @author Maunish
 * @version 1.0
 */
public class CandidateFirstInterview{

	private Integer firstInterviewCode;
	private String agentId;
	private String candidateCode;
	private String interviewResult;
	private String recruitmentPlan;
	private String remarks;
	private Date passTime;

	/**
	 * <p>
	 * Default Constructor.Setting default values for CandidateFirstInterview fields.
	 * </p>
	 */
	public CandidateFirstInterview() {
		firstInterviewCode = null;
		agentId = null;
		candidateCode = null;
		interviewResult = null;
		recruitmentPlan = null;
		remarks = null;
		passTime = null;
	}

	/**
	 * @return the firstInterviewCode
	 */
	public Integer getFirstInterviewCode() {
		return firstInterviewCode;
	}

	/**
	 * @param firstInterviewCode
	 *            the firstInterviewCode to set
	 */
	public void setFirstInterviewCode(Integer firstInterviewCode) {
		this.firstInterviewCode = firstInterviewCode;
	}
	
	/**
	 * @return the agentId
	 */
	
	public String getAgentId() {
		return agentId;
	}

	/**
	 * @param firstInterviewCode
	 *            the firstInterviewCode to set
	 */
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	/**
	 * @return the candidateCode
	 */
	public String getCandidateCode() {
		return candidateCode;
	}

	/**
	 * @param firstInterviewCode
	 *            the firstInterviewCode to set
	 */
	public void setCandidateCode(String candidateCode) {
		this.candidateCode = candidateCode;
	}

	/**
	 * @return the interviewResult
	 */
	public String getInterviewResult() {
		return interviewResult;
	}

	/**
	 * @param interviewResult
	 *            the interviewResult to set
	 */
	public void setInterviewResult(String interviewResult) {
		this.interviewResult = interviewResult;
	}

	/**
	 * @return the recruitmentPlan
	 */
	public String getRecruitmentPlan() {
		return recruitmentPlan;
	}

	/**
	 * @param recruitmentPlan
	 *            the recruitmentPlan to set
	 */
	public void setRecruitmentPlan(String recruitmentPlan) {
		this.recruitmentPlan = recruitmentPlan;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the passTime
	 */
	public Date getPassTime() {
		return passTime;
	}

	/**
	 * @param passTime
	 *            the passTime to set
	 */
	public void setPassTime(Date passTime) {
		this.passTime = passTime;
	}

	@Override
	public void finalize() {
		firstInterviewCode = null;
		agentId = null;
		candidateCode = null;
		interviewResult = null;
		recruitmentPlan = null;
		remarks = null;
		passTime = null;
	}
}