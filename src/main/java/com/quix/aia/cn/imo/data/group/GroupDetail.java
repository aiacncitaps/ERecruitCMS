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
 * 17-Feb-2016               Maunish             GroupDetail file added 
 ***************************************************************************** */

package com.quix.aia.cn.imo.data.group;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * GroupDetail bean class.
 * </p>
 * 
 * @author Maunish
 * @version 1.0
 */
public class GroupDetail {

	private Integer groupCode;
	private String agentCode;
	private String branchCode;
	private String groupName;
	private String groupDescription;
	private Date groupCreatedDate;
	private Date groupModifiedDate;
	private boolean groupIsDelete;
	private byte[] groupImage;
	
	private Set<GroupCandidateDetail> groupContactData;

	/**
	 * <p>
	 * Default Constructor.Setting default values for GroupDetail fields.
	 * </p>
	 */
	public GroupDetail() {
		groupCode = 0;
		agentCode = null;
		agentCode = "";
		branchCode = "";
		groupName = "";
		groupDescription = "";
		groupCreatedDate = null;
		groupModifiedDate = null;
		groupIsDelete = false;
		groupImage = null;
		
		groupContactData = new HashSet();
	}

	/**
	 * @return the groupCode
	 */
	public Integer getGroupCode() {
		return groupCode;
	}

	/**
	 * @param groupCode the groupCode to set
	 */
	public void setGroupCode(Integer groupCode) {
		this.groupCode = groupCode;
	}

	/**
	 * @return the agentCode
	 */
	public String getAgentCode() {
		return agentCode;
	}

	/**
	 * @param agentCode the agentCode to set
	 */
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	/**
	 * @return the branchCode
	 */
	public String getBranchCode() {
		return branchCode;
	}

	/**
	 * @param branchCode the branchCode to set
	 */
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the groupDescription
	 */
	public String getGroupDescription() {
		return groupDescription;
	}

	/**
	 * @param groupDescription the groupDescription to set
	 */
	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
	}

	/**
	 * @return the groupCreatedDate
	 */
	public Date getGroupCreatedDate() {
		return groupCreatedDate;
	}

	/**
	 * @param groupCreatedDate the groupCreatedDate to set
	 */
	public void setGroupCreatedDate(Date groupCreatedDate) {
		this.groupCreatedDate = groupCreatedDate;
	}

	/**
	 * @return the groupModifiedDate
	 */
	public Date getGroupModifiedDate() {
		return groupModifiedDate;
	}

	/**
	 * @param groupModifiedDate the groupModifiedDate to set
	 */
	public void setGroupModifiedDate(Date groupModifiedDate) {
		this.groupModifiedDate = groupModifiedDate;
	}

	/**
	 * @return the groupIsDelete
	 */
	public boolean isGroupIsDelete() {
		return groupIsDelete;
	}

	/**
	 * @param groupIsDelete the groupIsDelete to set
	 */
	public void setGroupIsDelete(boolean groupIsDelete) {
		this.groupIsDelete = groupIsDelete;
	}

	/**
	 * @return the groupImage
	 */
	public byte[] getGroupImage() {
		return groupImage;
	}

	/**
	 * @param groupImage the groupImage to set
	 */
	public void setGroupImage(byte[] groupImage) {
		this.groupImage = groupImage;
	}

	/**
	 * @return the groupContactData
	 */
	public Set<GroupCandidateDetail> getGroupContactData() {
		return groupContactData;
	}

	/**
	 * @param groupContactData the groupContactData to set
	 */
	public void setGroupContactData(Set<GroupCandidateDetail> groupContactData) {
		this.groupContactData = groupContactData;
	}
	
}
