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

package com.quix.aia.cn.imo.data.category;

import java.util.Date;

import com.quix.aia.cn.imo.utilities.SecurityAPI;


/**
 * <p>PresenterCategory bean class.</p> 
 * @author khyati
 * @version 1.0
 */


public class PresenterCategory {
	
	
	private int presenterCategoryCode;
	private String presenterCategoryName;
	private String createdBy;
	private Date creationDate;
	private String modifiedBy;
	private Date modificationDate;
	private boolean status;
	private String token;
	
	
	/**
	 * <p>Default Constructor.Setting default values for PresenterCategory fields.</p>
	 */
	
	public PresenterCategory(){
		setToken("");
		presenterCategoryCode=0;
		presenterCategoryName="";		
		createdBy = "";
		creationDate = null;
		modifiedBy = "";
		modificationDate = null;
		status = false;
	}
	
	
	/**
	 * @return the presenterCategoryCode
	 */
	public int getPresenterCategoryCode() {
		return presenterCategoryCode;
	}
	
	/**
	 * @param presenterCategoryCode the presenterCategoryCode to set
	 */
	public void setPresenterCategoryCode(int presenterCategoryCode) {
		this.presenterCategoryCode = presenterCategoryCode;
	}
	
	
	/**
	 * @return the presenterCategoryName
	 */
	public String getPresenterCategoryName() {
		return presenterCategoryName;
	}
	
	
	/**
	 * @param presenterCategoryName the presenterCategoryName to set
	 */
	public void setPresenterCategoryName(String presenterCategoryName) {
		this.presenterCategoryName = presenterCategoryName;
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
	 * @param creationDate  the creationDate  to set
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
	 * @param modifiedBy  the modifiedBy  to set
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
	 * @param modificationDate  the modificationDate  to set
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
	 * @param status  the status  to set
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
	 * @param token  the token  to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	
	
	/**
	 * <p>@Override toString() method of Object class</p>
	 */
	public String toString() {
		
		
		return ("PRESENTER CATEGORY CODE-> "+this.presenterCategoryCode  +"PRESENTER CATEGORY NAME-> "+this.presenterCategoryName);
	}
	
	 /**
	    * <p>Dynamic rows added based on PresenterCategory list size</p>
	    * @return the row as Object
	    */
	
			public Object getGetPresenterCategoryListingTableRow(int i) {
				// TODO Auto-generated method stub
		        String returnStr ;
		        String modifyLink = "";
		        String deleteLink = "";
		        deleteLink = "javascript:confirmDelete('" + presenterCategoryCode + "')";
		        modifyLink = "FormManager?key=PresenterCategoryAdd&type=MODIFY&PRESENTER_CATEGORY_CODE=" + this.presenterCategoryCode;
		       // deleteLink = "/"+ctxProp+"/FormManager?key=PresenterCategoryAdd&type=DELETE&PRESENTER_CATEGORY_CODE=" + this.presenterCategoryCode;
		          returnStr = "<tr> "+
		        		
		      			"<td><div align=center>"+this.presenterCategoryCode+"</div></td>"+
		      			"<td>"+SecurityAPI.encodeHTML(this.presenterCategoryName)+"</td>"+
		      			" <td><div align=center><a href=\"" + modifyLink + "\"><img src=images/edit.png border=0></a>&nbsp;<a href=\"" + deleteLink + "\"><img src=images/delete.png border=0></a></div></td>"+
		      			"</tr>"  ;
		        
		          
		         
		        	     
		        return returnStr;
				
			}
	

}
