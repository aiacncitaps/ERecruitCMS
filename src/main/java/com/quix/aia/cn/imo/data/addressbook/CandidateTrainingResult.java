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
 * CandidateTrainingResult bean class.
 * </p>
 * 
 * @author Maunish
 * @version 1.0
 */
public class CandidateTrainingResult{

	private Integer resultCode;
	private String perAgentName;
	private String perAgentId;
	private String recruiterAgentCode;
	private String branchCode;
	private String courseType;
	private Date passTime;
	private Date creationDate;
	private String trainingResult;

	/**
	 * <p>
	 * Default Constructor.Setting default values for TrainingResult fields.
	 * </p>
	 */
	public CandidateTrainingResult() {
		resultCode = null;
		perAgentName = null;
		perAgentId = null;
		recruiterAgentCode = null;
		branchCode = null;
		courseType = null;
		passTime = null;
		creationDate = null;
		trainingResult=null;
	}

	/**
	 * @return the resultCode
	 */
	public Integer getResultCode() {
		return resultCode;
	}

	/**
	 * @param resultCode
	 *            the resultCode to set
	 */
	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}

	/**
	 * @return the perAgentName
	 */
	public String getPerAgentName() {
		return perAgentName;
	}

	/**
	 * @param perAgentName
	 *            the perAgentName to set
	 */
	public void setPerAgentName(String perAgentName) {
		this.perAgentName = perAgentName;
	}

	/**
	 * @return the perAgentId
	 */
	public String getPerAgentId() {
		return perAgentId;
	}

	/**
	 * @param perAgentId
	 *            the perAgentId to set
	 */
	public void setPerAgentId(String perAgentId) {
		this.perAgentId = perAgentId;
	}

	/**
	 * @return the recruiterAgentCode
	 */
	public String getRecruiterAgentCode() {
		return recruiterAgentCode;
	}

	/**
	 * @param recruiterAgentCode
	 *            the recruiterAgentCode to set
	 */
	public void setRecruiterAgentCode(String recruiterAgentCode) {
		this.recruiterAgentCode = recruiterAgentCode;
	}

	/**
	 * @return the branchCode
	 */
	public String getBranchCode() {
		return branchCode;
	}

	/**
	 * @param branchCode
	 *            the branchCode to set
	 */
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	/**
	 * @return the courseType
	 */
	public String getCourseType() {
		return courseType;
	}

	/**
	 * @param courseType
	 *            the courseType to set
	 */
	public void setCourseType(String courseType) {
		this.courseType = courseType;
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

	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate
	 *            the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	/**
	 * @return the trainingResult
	 */
	public String getTrainingResult() {
		return trainingResult;
	}

	/**
	 * @param trainingResult the trainingResult to set
	 */
	public void setTrainingResult(String trainingResult) {
		this.trainingResult = trainingResult;
	}

	@Override
	public void finalize() {
		resultCode = null;
		perAgentName = null;
		perAgentId = null;
		recruiterAgentCode = null;
		branchCode = null;
		courseType = null;
		passTime = null;
		creationDate = null;
		trainingResult=null;
	}
}