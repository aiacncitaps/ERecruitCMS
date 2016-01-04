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

import java.util.Date;

public class InterviewCandidateMaterial
{
	private int materialCode;
	private int candidateCode;
	
	private String materialDescrption;
	private String materialFileName;
	private Date createdDate;
	private int interviewCode;
	private byte[] formContent;
	
	
	
	
	public InterviewCandidateMaterial()
	{
		candidateCode = 0;
		materialCode = 0;
		materialDescrption = "";
		materialFileName = "";
		createdDate = new Date();
	}
	
	
	public int getInterviewCode() {
		return interviewCode;
	}

	public void setInterviewCode(int interviewCode) {
		this.interviewCode = interviewCode;
	}
	
	
	
	/**
	 * @return the materialCode
	 */
	public int getMaterialCode() {
		return materialCode;
	}

	/**
	 * @param materialCode the materialCode to set
	 */
	public void setMaterialCode(int materialCode) {
		this.materialCode = materialCode;
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
	 * @return the materialDescrption
	 */
	public String getMaterialDescrption() {
		return materialDescrption;
	}
	/**
	 * @param materialDescrption the materialDescrption to set
	 */
	public void setMaterialDescrption(String materialDescrption) {
		this.materialDescrption = materialDescrption;
	}
	/**
	 * @return the materialFileName
	 */
	public String getMaterialFileName() {
		return materialFileName;
	}
	/**
	 * @param materialFileName the materialFileName to set
	 */
	public void setMaterialFileName(String materialFileName) {
		this.materialFileName = materialFileName;
	}
	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	public byte[] getFormContent() {
		return formContent;
	}


	public void setFormContent(byte[] formContent) {
		this.formContent = formContent;
	}
	
	
	
}
