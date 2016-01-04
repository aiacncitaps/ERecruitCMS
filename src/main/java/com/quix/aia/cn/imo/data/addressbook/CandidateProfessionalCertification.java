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
 * 14-July-2015               Maunish             CandidateProfessionalCertification file added 
 ***************************************************************************** */

package com.quix.aia.cn.imo.data.addressbook;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * CandidateProfessionalCertification bean class.
 * </p>
 * 
 * @author Maunish
 * @version 1.0
 */
public class CandidateProfessionalCertification implements Serializable  {

	private CandidateProfessionalCertificationId professionalCertificationId;
	private String iosAddressCode;
	private String certificateName;
	private String charterAgency;
	private Date charterDate;

	/**
	 * <p>
	 * Default Constructor.Setting default values for CandidateProfessionalCertification
	 * fields.
	 * </p>
	 */
	public CandidateProfessionalCertification() {
		professionalCertificationId = null;
		iosAddressCode = null;
		certificateName = null;
		charterAgency = null;
		charterDate = null;
	}

	/**
	 * @return the professionalCertificationId
	 */
	public CandidateProfessionalCertificationId getProfessionalCertificationId() {
		return professionalCertificationId;
	}

	/**
	 * @param professionalCertificationId
	 *            the professionalCertificationId to set
	 */
	public void setProfessionalCertificationId(CandidateProfessionalCertificationId professionalCertificationId) {
		this.professionalCertificationId = professionalCertificationId;
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
	 * @return the certificateName
	 */
	public String getCertificateName() {
		return certificateName;
	}

	/**
	 * @param certificateName
	 *            the certificateName to set
	 */
	public void setCertificateName(String certificateName) {
		this.certificateName = certificateName;
	}

	/**
	 * @return the charterAgency
	 */
	public String getCharterAgency() {
		return charterAgency;
	}

	/**
	 * @param charterAgency
	 *            the charterAgency to set
	 */
	public void setCharterAgency(String charterAgency) {
		this.charterAgency = charterAgency;
	}

	/**
	 * @return the charterDate
	 */
	public Date getCharterDate() {
		return charterDate;
	}

	/**
	 * @param charterDate
	 *            the charterDate to set
	 */
	public void setCharterDate(Date charterDate) {
		this.charterDate = charterDate;
	}

	/**
	 * <p>
	 * finalize method for garbage collection
	 * </p>
	 */
	@Override
	public void finalize() {
		professionalCertificationId = null;
		iosAddressCode = null;
		certificateName = null;
		charterAgency = null;
		charterDate = null;
	}

}
