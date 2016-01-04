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
 * 06-August-2015    		  Maunish            Added 
 ***************************************************************************** */

package com.quix.aia.cn.imo.data.addressbook;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * CandidateEducation bean class.
 * </p>
 * 
 * @author Maunish
 * @version 1.0
 */
public class ContractDetail {

	private String branchCode;
	private String candidateAgentCode;
	private String candidateNric;
	private String recruiterAgentCode;
	private String recruitmentType;
	private Date contractDate;

	/**
	 * <p>
	 * Default Constructor.Setting default values for ContractDetail
	 * fields.
	 * </p>
	 */
	public ContractDetail() {
		branchCode = null;
		candidateAgentCode = null;
		candidateNric = null;
		recruiterAgentCode = null;
		recruitmentType = null;
		contractDate = null;
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
	 * @return the candidateAgentCode
	 */
	public String getCandidateAgentCode() {
		return candidateAgentCode;
	}

	/**
	 * @param candidateAgentCode
	 *            the candidateAgentCode to set
	 */
	public void setCandidateAgentCode(String candidateAgentCode) {
		this.candidateAgentCode = candidateAgentCode;
	}

	/**
	 * @return the candidateNric
	 */
	public String getCandidateNric() {
		return candidateNric;
	}

	/**
	 * @param candidateNric
	 *            the candidateNric to set
	 */
	public void setCandidateNric(String candidateNric) {
		this.candidateNric = candidateNric;
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
	 * @return the recruitmentType
	 */
	public String getRecruitmentType() {
		return recruitmentType;
	}

	/**
	 * @param recruitmentType
	 *            the recruitmentType to set
	 */
	public void setRecruitmentType(String recruitmentType) {
		this.recruitmentType = recruitmentType;
	}

	/**
	 * @return the contractDate
	 */
	public Date getContractDate() {
		return contractDate;
	}

	/**
	 * @param contractDate
	 *            the contractDate to set
	 */
	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}

	/**
	 * <p>
	 * finalize method for garbage collection
	 * </p>
	 */
	@Override
	public void finalize() {
		branchCode = null;
		candidateAgentCode = null;
		candidateNric = null;
		recruiterAgentCode = null;
		recruitmentType = null;
		contractDate = null;
	}

}
