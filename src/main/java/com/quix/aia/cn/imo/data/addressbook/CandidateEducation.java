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
 * 14-July-2015               Maunish             CandidateEducation file added 
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
public class CandidateEducation implements Serializable {

	private CandidateEducationId educationId;
	private String iosAddressCode;
	private Date startDate;
	private Date endDate;
	private String education;
	private String school;
	private String educationLevel;
	private String witness;
	private String witnessContactNo;

	/**
	 * <p>
	 * Default Constructor.Setting default values for CandidateEducation
	 * fields.
	 * </p>
	 */
	public CandidateEducation() {
		educationId = null;
		iosAddressCode = null;
		startDate = null;
		education = null;
		school = null;
		endDate = null;
		educationLevel = null;
		witness = null;
		witnessContactNo = null;
	}

	/**
	 * @return the educationId
	 */
	public CandidateEducationId getEducationId() {
		return educationId;
	}

	/**
	 * @param educationId
	 *            the educationId to set
	 */
	public void setEducationId(CandidateEducationId educationId) {
		this.educationId = educationId;
	}

	/**
	 * @return the iosAddressCode
	 */
	public String getIosAddressCode() {
		return iosAddressCode;
	}

	/**
	 * @param iosAddressCode
	 *            the iosAddressCode to set
	 */
	public void setIosAddressCode(String iosAddressCode) {
		this.iosAddressCode = iosAddressCode;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
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
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the education
	 */
	public String getEducation() {
		return education;
	}

	/**
	 * @param education
	 *            the education to set
	 */
	public void setEducation(String education) {
		this.education = education;
	}

	/**
	 * @return the school
	 */
	public String getSchool() {
		return school;
	}

	/**
	 * @param school
	 *            the school to set
	 */
	public void setSchool(String school) {
		this.school = school;
	}

	/**
	 * @return the educationLevel
	 */
	public String getEducationLevel() {
		return educationLevel;
	}

	/**
	 * @param educationLevel
	 *            the educationLevel to set
	 */
	public void setEducationLevel(String educationLevel) {
		this.educationLevel = educationLevel;
	}

	/**
	 * @return the witness
	 */
	public String getWitness() {
		return witness;
	}

	/**
	 * @param witness
	 *            the witness to set
	 */
	public void setWitness(String witness) {
		this.witness = witness;
	}

	/**
	 * @return the witnessContactNo
	 */
	public String getWitnessContactNo() {
		return witnessContactNo;
	}

	/**
	 * @param witnessContactNo
	 *            the witnessContactNo to set
	 */
	public void setWitnessContactNo(String witnessContactNo) {
		this.witnessContactNo = witnessContactNo;
	}

	/**
	 * <p>
	 * finalize method for garbage collection
	 * </p>
	 */
	@Override
	public void finalize() {
		educationId = null;
		iosAddressCode = null;
		startDate = null;
		education = null;
		school = null;
		endDate = null;
		educationLevel = null;
		witness = null;
		witnessContactNo = null;
	}

}
