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
 * 17-Feb-2016               Maunish             GroupCandidateDetail file added 
 ***************************************************************************** */

package com.quix.aia.cn.imo.data.group;


/**
 * <p>
 * GroupCandidateDetail bean class.
 * </p>
 * 
 * @author Maunish
 * @version 1.0
 */
public class GroupCandidateDetail {

	private int groupCandidateCode;
	private int addressCode;
	private int groupCode;
	private String iosAddressCode;
	private String objectid;
	
	/**
	 * <p>
	 * Default Constructor.Setting default values for GroupCandidateDetail fields.
	 * </p>
	 */
	public GroupCandidateDetail() {
		groupCandidateCode = 0;
		addressCode = 0;
		groupCode = 0;
		iosAddressCode = "";
		objectid = "";
	}

	/**
	 * @return the groupCandidateCode
	 */
	public int getGroupCandidateCode() {
		return groupCandidateCode;
	}

	/**
	 * @param groupCandidateCode the groupCandidateCode to set
	 */
	public void setGroupCandidateCode(int groupCandidateCode) {
		this.groupCandidateCode = groupCandidateCode;
	}

	/**
	 * @return the addressCode
	 */
	public int getAddressCode() {
		return addressCode;
	}

	/**
	 * @param addressCode the addressCode to set
	 */
	public void setAddressCode(int addressCode) {
		this.addressCode = addressCode;
	}

	/**
	 * @return the groupCode
	 */
	public int getGroupCode() {
		return groupCode;
	}

	/**
	 * @param groupCode the groupCode to set
	 */
	public void setGroupCode(int groupCode) {
		this.groupCode = groupCode;
	}

	/**
	 * @return the iosAddressCode
	 */
	public String getIosAddressCode() {
		return iosAddressCode;
	}

	/**
	 * @param iosAddressCode the iosAddressCode to set
	 */
	public void setIosAddressCode(String iosAddressCode) {
		this.iosAddressCode = iosAddressCode;
	}

	/**
	 * @return the objectid
	 */
	public String getObjectid() {
		return objectid;
	}

	/**
	 * @param objectid the objectid to set
	 */
	public void setObjectid(String objectid) {
		this.objectid = objectid;
	}
	
	
}
