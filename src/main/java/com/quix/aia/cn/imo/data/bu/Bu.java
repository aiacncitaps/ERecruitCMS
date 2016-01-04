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
 * Date                       Developer           Description
 *                           khyati             created
 * 15-may-2015               khyati             Added Copy rights and security 
 ***************************************************************************** */

package com.quix.aia.cn.imo.data.bu;

import java.util.Date;

import com.quix.aia.cn.imo.utilities.LMSUtil;
import com.quix.aia.cn.imo.utilities.SecurityAPI;


/**
 * <p>Bu bean class.</p>
 * @author khyati
 * @version 1.0
 */
public class Bu {
	private int buCode;
	private String buName;
	private String createdBy;
	private Date creationDate;
	private String modifiedBy;
	private Date modificationDate;
	private boolean status;
	private String token;
	private int orderCode;

	
	/**
	 * <p>Default Constructor.Setting default values for BU fields.</p>
	 */
	public Bu() {
		setToken("");
		buCode = 0;
		buName = "";
		createdBy = "";
		creationDate = null;
		modifiedBy = "";
		modificationDate = null;
		status = false;
		orderCode=0;
	}

	
	/**
	 * @return the buCode
	 */
	public int getBuCode() {
		return buCode;
	}
	

	
	
	/**
	 * @param buCode the buCode to set
	 */
	public void setBuCode(int buCode) {
		this.buCode = buCode;
	}

	
	/**
	 * @return the buName
	 */
	public String getBuName() {
		return buName;
	}

	
	/**
	 * @param buName the buName to set
	 */
	public void setBuName(String buName) {
		this.buName = buName;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	
	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	
	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	
	/**
	 * @return the modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}

	
	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	
	/**
	 * @return the modificationDate
	 */
	public Date getModificationDate() {
		return modificationDate;
	}

	
	/**
	 * @param modificationDate the modificationDate to set
	 */
	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	
	/**
	 * @return the status
	 */
	public boolean isStatus() {
		return status;
	}

	
	/**
	 * @param status the status to set
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

	
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	
	/**
	 * <p>@Override toString() method of Object class</p>
	 */
	
	public String toString() {

		return ("Name-> " + this.buName + " BU CODE-> " + this.buCode);
	}
	
	
	

	 /**
	    * <p>Dynamic rows added based on BU list size</p>
	    * @return the row as Object
	    */
	
	public Object getGetBUListingTableRow(int i) {
		// TODO Auto-generated method stub

		String returnStr;
		String modifyLink = "";
		String deleteLink = "";
		deleteLink = "javascript:confirmDelete('" + buCode + "')";
		String date="";
		if(this.modificationDate!=null)
			date = LMSUtil.convertDateToString(this.modificationDate);
		
		modifyLink = "FormManager?key=BUAdd&type=MODIFY&BU_CODE=" + this.buCode;
		// deleteLink =
		// "/"+ctxProp+"/FormManager?key=BUAdd&type=DELETE&BU_CODE=" +
		// this.buCode;
		deleteLink = "javascript:confirmDelete('" + buCode + "')";
		returnStr = "<tr > " +

		"<td><div align=center>" + this.buCode + "</div></td>" + "<td>"
				+ SecurityAPI.encodeHTML(this.buName) + "</td>" 
				+"<td><div align=center>" + this.modifiedBy + "</div></td>" 
				+"<td><div align=center>" + date + "</div></td>" 
				+ " <td><div align=center><a href=\"" + modifyLink
				+ "\"><img src=images/edit.png border=0></a>&nbsp;<a href=\""
				+ deleteLink
				+ "\"><img src=images/delete.png border=0></a></div></td>"
				+ "</tr>";

		return returnStr;

	}


	public int getOrderCode() {
		return orderCode;
	}


	public void setOrderCode(int orderCode) {
		this.orderCode = orderCode;
	}

}
