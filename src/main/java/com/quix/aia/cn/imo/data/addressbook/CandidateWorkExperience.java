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
 * 14-July-2015               Maunish             CandidateWorkExperience file added 
 ***************************************************************************** */

package com.quix.aia.cn.imo.data.addressbook;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * CandidateWorkExperience bean class.
 * </p>
 * 
 * @author Maunish
 * @version 1.0
 */
public class CandidateWorkExperience implements Serializable  {

	private CandidateWorkExperienceId workExperienceId;
	private String iosAddressCode;
	private Date startDate;
	private Date endDate;
	private String unit;
	private String position;
	private String occupation;
	private String witness;
	private String witnessContactNo;
	private String income;

	/**
	 * <p>
	 * Default Constructor.Setting default values for CandidateWorkExperience
	 * fields.
	 * </p>
	 */
	public CandidateWorkExperience() {
		workExperienceId = null;
		iosAddressCode = null;
		startDate = null;
		unit = null;
		position = null;
		endDate = null;
		occupation = null;
		witness = null;
		witnessContactNo = null;
		income = null;
	}

	/**
	 * @return the workExperienceId
	 */
	public CandidateWorkExperienceId getWorkExperienceId() {
		return workExperienceId;
	}

	/**
	 * @param workExperienceId
	 *            the workExperienceId to set
	 */
	public void setWorkExperienceId(CandidateWorkExperienceId workExperienceId) {
		this.workExperienceId = workExperienceId;
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
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * @param unit
	 *            the unit to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * @return the occupation
	 */
	public String getOccupation() {
		return occupation;
	}

	/**
	 * @param occupation
	 *            the occupation to set
	 */
	public void setOccupation(String occupation) {
		this.occupation = occupation;
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
	 * @return the income
	 */
	public String getIncome() {
		return income;
	}

	/**
	 * @param income
	 *            the income to set
	 */
	public void setIncome(String income) {
		this.income = income;
	}

	/**
	 * <p>
	 * finalize method for garbage collection
	 * </p>
	 */
	@Override
	public void finalize() {
		workExperienceId = null;
		iosAddressCode = null;
		startDate = null;
		unit = null;
		position = null;
		endDate = null;
		occupation = null;
		witness = null;
		witnessContactNo = null;
		income = null;
	}

}
