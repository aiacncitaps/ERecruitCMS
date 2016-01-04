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

package com.quix.aia.cn.imo.data.department;

import java.util.Date;

import com.quix.aia.cn.imo.utilities.LMSUtil;
import com.quix.aia.cn.imo.utilities.SecurityAPI;



/**
  * <p>Department bean class.</p>
 * @author khyati
 * @version 1.0
 */
public class Department {
	
	private int deptCode;
	private String deptName;
	private String createdBy;
	private Date creationDate;
	private String modifiedBy;
	private Date modificationDate;
	private boolean status;
	private String token;
	private int orderCode;
	
	
	/**
	 * <p>Default Constructor.Setting default values for Department fields.</p>
	 */
	public Department(){
		
		deptCode=0;
		deptName="";
		createdBy = "";
		creationDate = null;
		modifiedBy = "";
		modificationDate = null;
		status = false;
		token="";
	}
	
	
	/**
	 * @return the deptCode
	 */
	public int getDeptCode() {
		return deptCode;
	}
	
	/**
	 * @param deptCode the deptCode to set
	 */
	public void setDeptCode(int deptCode) {
		this.deptCode = deptCode;
	}
	
	
	/**
	 * @return the deptName
	 */
	public String getDeptName() {
		return deptName;
	}
	/**
	 * @param deptName the deptName to set
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
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
	    * <p>Dynamic rows added based on Department list size</p>
	    * @return the row as Object
	    */
	public Object getGetDepartmentListingTableRow(int i) 
	{

        String returnStr ;
        String modifyLink = "";
        String deleteLink = "";
        deleteLink = "javascript:confirmDelete('" + deptCode + "')";
        modifyLink = "FormManager?key=DepartmentAdd&type=MODIFY&DEPT_CODE=" + this.deptCode;
        String date="";
		if(this.modificationDate!=null)
			date = LMSUtil.convertDateToString(this.modificationDate);
      //  deleteLink = "/"+ctxProp+"/FormManager?key=DepartmentAdd&type=DELETE&DEPT_CODE=" + this.deptCode;
          returnStr = "<tr> "+
        		
      			"<td><div align=center>"+this.deptCode+"</div></td>"+
      			"<td>"+SecurityAPI.encodeHTML(this.deptName)+"</td>"+
      			"<td>"+SecurityAPI.encodeHTML(this.modifiedBy)+"</td>"+
				"<td>" + date + "</div></td>" +
      			" <td><div align=center><a href=\"" + modifyLink + "\"><img src=images/edit.png border=0></a>&nbsp;<a href=\"" + deleteLink + "\"><img src=images/delete.png border=0></a></div></td>"+
      			"</tr>"  ;
        
          
         
        	     
        return returnStr;
		
	}
	
	
	/**
	 * <p>@Override toString() method of Object class</p>
	 */
	public String toString() {
		
		
		return ("Name-> "+this.deptName + " DEPARTMENT CODE-> "+this.deptCode );
	}


	public int getOrderCode() {
		return orderCode;
	}


	public void setOrderCode(int orderCode) {
		this.orderCode = orderCode;
	}
	
	
}
