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
 * 14-July-2015               Maunish             CandidateFamilyInfo file added 
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
public class CandidateFamilyInfo implements Serializable {

	private CandidateFamilyInfoId familyInfoId;
	private String iosAddressCode;
	private String name;
	private String unit;
	private String position;
	private String relationship;
	private String occupation;
	private String phoneNo;

	/**
	 * <p>
	 * Default Constructor.Setting default values for CandidateFamilyInfo fields.
	 * </p>
	 */
	public CandidateFamilyInfo() {
		familyInfoId = null;
		iosAddressCode = null;
		name = null;
		unit = null;
		position = null;
		relationship = null;
		occupation = null;
		phoneNo = null;
	}

	/**
	 * @return the familyInfoId
	 */
	public CandidateFamilyInfoId getFamilyInfoId() {
		return familyInfoId;
	}

	/**
	 * @param familyInfoId
	 *            the familyInfoId to set
	 */
	public void setFamilyInfoId(CandidateFamilyInfoId familyInfoId) {
		this.familyInfoId = familyInfoId;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the relationship
	 */
	public String getRelationship() {
		return relationship;
	}

	/**
	 * @param relationship
	 *            the relationship to set
	 */
	public void setRelationship(String relationship) {
		this.relationship = relationship;
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
	 * @return the phoneNo
	 */
	public String getPhoneNo() {
		return phoneNo;
	}

	/**
	 * @param phoneNo
	 *            the phoneNo to set
	 */
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	
	/**
	 * <p>
	 * finalize method for garbage collection
	 * </p>
	 */
	@Override
	public void finalize() {
		familyInfoId = null;
		iosAddressCode = null;
		name = null;
		unit = null;
		position = null;
		relationship = null;
		occupation = null;
		phoneNo = null;
	}

}
