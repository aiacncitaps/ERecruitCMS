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
 * 17-July-2015               Maunish             CandidateFamilyInfoId file added 
 ***************************************************************************** */

package com.quix.aia.cn.imo.data.addressbook;

import java.io.Serializable;

/**
 * <p>
 * CandidateFamilyInfo bean class.
 * </p>
 * 
 * @author Maunish
 * @version 1.0
 */
public class CandidateFamilyInfoId implements Serializable  {

	private Integer addressCode;
	private String iosAddressCode;

	/**
	 * <p>
	 * Default Constructor.Setting default values for CandidateFamilyInfoId fields.
	 * </p>
	 */
	public CandidateFamilyInfoId() {
		addressCode = null;
		iosAddressCode = null;
	}

	/**
	 * @return the addressCode
	 */
	public Integer getAddressCode() {
		return addressCode;
	}

	/**
	 * @param addressCode
	 *            the addressCode to set
	 */
	public void setAddressCode(Integer addressCode) {
		this.addressCode = addressCode;
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
	 * <p>
	 * finalize method for garbage collection
	 * </p>
	 */
	@Override
	public void finalize() {
		addressCode = null;
		iosAddressCode = null;
	}

}
