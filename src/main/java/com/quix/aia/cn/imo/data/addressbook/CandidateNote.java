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
 * 23-July-2015               Maunish             CandidateNote file added 
 ***************************************************************************** */

package com.quix.aia.cn.imo.data.addressbook;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * CandidateNote bean class.
 * </p>
 * 
 * @author Maunish
 * @version 1.0
 */
public class CandidateNote implements Serializable  {

	private CandidateNoteId noteId;
	private Integer addressCode;
	private String iosNoteCode;
	private String activityType;
	private Date activityDate;
	private String description;
	private Boolean activityStatus;
	private Boolean isDelete;
	private Boolean status;
	private Boolean isSystem;

	/**
	 * <p>
	 * Default Constructor.Setting default values for CandidateNote
	 * fields.
	 * </p>
	 */
	public CandidateNote() {
		noteId = null;
		addressCode = null;
		iosNoteCode = null;
		activityType = null;
		activityDate = null;
		description = null;
		activityStatus = null;
		isDelete = null;
		status = true;
		status = false;
	}

	/**
	 * @return the noteId
	 */
	public CandidateNoteId getNoteId() {
		return noteId;
	}

	/**
	 * @param noteId
	 *            the noteId to set
	 */
	public void setNoteId(CandidateNoteId noteId) {
		this.noteId = noteId;
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
	 * @return the iosNoteCode
	 */
	public String getIosNoteCode() {
		return iosNoteCode;
	}

	/**
	 * @param iosNoteCode
	 *            the iosNoteCode to set
	 */
	public void setIosNoteCode(String iosNoteCode) {
		this.iosNoteCode = iosNoteCode;
	}

	/**
	 * @return the activityType
	 */
	public String getActivityType() {
		return activityType;
	}

	/**
	 * @param activityType
	 *            the activityType to set
	 */
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	
	/**
	 * @return the activityDate
	 */
	public Date getActivityDate() {
		return activityDate;
	}

	/**
	 * @param activityDate
	 *            the activityDate to set
	 */
	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the activityStatus
	 */
	public Boolean getActivityStatus() {
		return activityStatus;
	}

	/**
	 * @param activityStatus
	 *            the activityStatus to set
	 */
	public void setActivityStatus(Boolean activityStatus) {
		this.activityStatus = activityStatus;
	}
	
	/**
	 * @return the isDelete
	 */
	public Boolean getIsDelete() {
		return isDelete;
	}

	/**
	 * @param isDelete the isDelete to set
	 */
	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}
	
	/**
	 * @return the status
	 */
	public Boolean getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	/**
	 * @return the isSystem
	 */
	public Boolean getIsSystem() {
		return isSystem;
	}

	/**
	 * @param isSystem the isSystem to set
	 */
	public void setIsSystem(Boolean isSystem) {
		this.isSystem = isSystem;
	}

	/**
	 * <p>
	 * finalize method for garbage collection
	 * </p>
	 */
	@Override
	public void finalize() {
		noteId = null;
		iosNoteCode = null;
		activityType = null;
		activityDate = null;
		description = null;
		activityStatus = null;
		isDelete = null;
		status = null;
		isSystem = null;
	}

}
