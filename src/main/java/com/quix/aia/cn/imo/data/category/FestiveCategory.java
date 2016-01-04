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
* Date              Developer          Change Description
* 07-May-2015       Jay          
* 
****************************************** *********************************** */
package com.quix.aia.cn.imo.data.category;

import java.util.Date;

import com.quix.aia.cn.imo.utilities.LMSUtil;
import com.quix.aia.cn.imo.utilities.SecurityAPI;

/**
 * <p>FestiveCategory bean class.</p>
 * @author khyati
 * @version 1.0
 */
public class FestiveCategory {
	
	private int festiveCategoryCode;
	private String FestiveCategoryName;
	private String createdBy;
	private Date creationDate;
	private String modifiedBy;
	private Date modificationDate;
	private boolean status;
	private String token;
	private int count;
	
	
	
	/**
	 * <p>Default Constructor.Setting default values for FestiveCategory fields.</p>
	 */
	public FestiveCategory(){
		setToken("");
		festiveCategoryCode=0;
		FestiveCategoryName="";		
		createdBy = "";
		creationDate = null;
		modifiedBy = "";
		modificationDate = null;
		status = false;
	}
	
	

	/**
	 * @return the festiveCategoryCode
	 */
	public int getfestiveCategoryCode() {
		return festiveCategoryCode;
	}
	
	
	/**
	 * @param festiveCategoryCode the festiveCategoryCode to set
	 */
	public void setfestiveCategoryCode(int festiveCategoryCode) {
		this.festiveCategoryCode = festiveCategoryCode;
	}
	

	/**
	 * @return the FestiveCategoryName
	 */
	public String getFestiveCategoryName() {
		return FestiveCategoryName;
	}
	
	/**
	 * @param FestiveCategoryName the FestiveCategoryName to set
	 */
	public void setFestiveCategoryName(String FestiveCategoryName) {
		this.FestiveCategoryName = FestiveCategoryName;
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
		
		
		return ("FESTIVECATEGORY CODE-> "+this.festiveCategoryCode  +"FESTIVECATEGORY NAME-> "+this.FestiveCategoryName);
	}
	
	
	 /**
	    * <p>Dynamic rows added based on FestiveCategory list size</p>
	    * @return the row as Object
	    */
	
	public Object getGetFestiveCategoryListingTableRow(int i) {
		// TODO Auto-generated method stub
        String returnStr ;
        String modifyLink = "";
        String deleteLink = "";
        deleteLink = "javascript:confirmDelete('" + festiveCategoryCode + "','"+count+"')";
        String date="";
		if(this.modificationDate!=null)
			date = LMSUtil.convertDateToString(this.modificationDate);
		
        modifyLink = "FormManager?key=FestiveCategoryAdd&type=MODIFY&FESTIVE_CATEGORY_CODE=" + this.festiveCategoryCode;
       // deleteLink = "/"+ctxProp+"/FormManager?key=FestiveCategoryAdd&type=DELETE&FESTIVE_CATEGORY_CODE=" + this.festiveCategoryCode;
          returnStr = "<tr> "+
        		
      			"<td ><div align=center>"+this.festiveCategoryCode+"</div></td>"+
      			"<td >"+SecurityAPI.encodeHTML(this.FestiveCategoryName)+"</td>"+
      			"<td >"+SecurityAPI.encodeHTML(this.modifiedBy)+"</td>"+
				"<td >" + date + "</div></td>" +
      			" <td ><div align=center><a href=\"" + modifyLink + "\"><img src=images/edit.png border=0></a>&nbsp;<a href=\"" + deleteLink + "\"><img src=images/delete.png border=0></a></div></td>"+
      			"</tr>"  ;
        
          
         
        	     
        return returnStr;
		
	}



	public int getCount() {
		return count;
	}



	public void setCount(int count) {
		this.count = count;
	}

	
	
	
	

}
