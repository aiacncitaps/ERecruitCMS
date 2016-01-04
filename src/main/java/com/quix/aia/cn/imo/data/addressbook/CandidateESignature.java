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
 * 15-July-2015               Maunish             CandidateESignature file added 
 ***************************************************************************** */

package com.quix.aia.cn.imo.data.addressbook;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * CandidateESignature bean class.
 * </p>
 * 
 * @author Maunish
 * @version 1.0
 */
public class CandidateESignature implements Serializable  {

	private CandidateESignatureId eSignatureId;
	private String iosAddressCode;
	private String branch;
	private String serviceDepartment;
	private String city;
	private String agentId;
	private Boolean atachedWithInsuranceCo;
	private Boolean contactWithAia;
	private Boolean takenPspTest;
	private String candidateName;
	private Date applicationDate;
	private byte[] eSignaturePhoto;

	/**
	 * <p>
	 * Default Constructor.Setting default values for CandidateESignature
	 * fields.
	 * </p>
	 */
	public CandidateESignature() {
		eSignatureId = null;
		iosAddressCode = null;
		branch = null;
		city = null;
		agentId = null;
		serviceDepartment = null;
		atachedWithInsuranceCo = null;
		contactWithAia = null;
		takenPspTest = null;
		candidateName = null;
		applicationDate=null;
		eSignaturePhoto = null;
	}

	/**
	 * @return the eSignatureId
	 */
	public CandidateESignatureId geteSignatureId() {
		return eSignatureId;
	}

	/**
	 * @param eSignatureId
	 *            the eSignatureId to set
	 */
	public void seteSignatureId(CandidateESignatureId eSignatureId) {
		this.eSignatureId = eSignatureId;
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
	 * @return the branch
	 */
	public String getBranch() {
		return branch;
	}

	/**
	 * @param branch
	 *            the branch to set
	 */
	public void setBranch(String branch) {
		this.branch = branch;
	}

	/**
	 * @return the serviceDepartment
	 */
	public String getServiceDepartment() {
		return serviceDepartment;
	}

	/**
	 * @param serviceDepartment
	 *            the serviceDepartment to set
	 */
	public void setServiceDepartment(String serviceDepartment) {
		this.serviceDepartment = serviceDepartment;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the agentId
	 */
	public String getAgentId() {
		return agentId;
	}

	/**
	 * @param agentId
	 *            the agentId to set
	 */
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	/**
	 * @return the candidateName
	 */
	public String getCandidateName() {
		return candidateName;
	}

	/**
	 * @param candidateName
	 *            the candidateName to set
	 */
	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	/**
	 * @return the atachedWithInsuranceCo
	 */
	public Boolean getAtachedWithInsuranceCo() {
		return atachedWithInsuranceCo;
	}

	/**
	 * @param atachedWithInsuranceCo
	 *            the atachedWithInsuranceCo to set
	 */
	public void setAtachedWithInsuranceCo(Boolean atachedWithInsuranceCo) {
		this.atachedWithInsuranceCo = atachedWithInsuranceCo;
	}

	/**
	 * @return the contactWithAia
	 */
	public Boolean getContactWithAia() {
		return contactWithAia;
	}

	/**
	 * @param contactWithAia
	 *            the contactWithAia to set
	 */
	public void setContactWithAia(Boolean contactWithAia) {
		this.contactWithAia = contactWithAia;
	}

	/**
	 * @return the takenPspTest
	 */
	public Boolean getTakenPspTest() {
		return takenPspTest;
	}

	/**
	 * @param takenPspTest
	 *            the takenPspTest to set
	 */
	public void setTakenPspTest(Boolean takenPspTest) {
		this.takenPspTest = takenPspTest;
	}

	/**
	 * @return the eSignaturePhoto
	 */
	public byte[] geteSignaturePhoto() {
		return eSignaturePhoto;
	}

	/**
	 * @param eSignaturePhoto
	 *            the eSignaturePhoto to set
	 */
	public void seteSignaturePhoto(byte[] eSignaturePhoto) {
		this.eSignaturePhoto = eSignaturePhoto;
	}

	/**
	 * @return the applicationDate
	 */
	public Date getApplicationDate() {
		return applicationDate;
	}

	/**
	 * @param applicationDate
	 *            the applicationDate to set
	 */
	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}

	/**
	 * <p>
	 * finalize method for garbage collection
	 * </p>
	 */
	@Override
	public void finalize() {
		eSignatureId = null;
		iosAddressCode = null;
		branch = null;
		city = null;
		agentId = null;
		serviceDepartment = null;
		atachedWithInsuranceCo = null;
		contactWithAia = null;
		takenPspTest = null;
		candidateName = null;
		applicationDate=null;
		eSignaturePhoto = null;
	}

}
