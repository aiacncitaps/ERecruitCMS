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
package com.quix.aia.cn.imo.data.branch;

import java.util.Date;

import com.quix.aia.cn.imo.utilities.LMSUtil;
import com.quix.aia.cn.imo.utilities.SecurityAPI;

public class Branch {
	
	private int branchCode;
	private String branchName;
	private String createdBy;
	private Date creationDate;
	private String modifiedBy;
	private Date modificationDate;
	private boolean status;
	private String token;
	private int buCode;
	
	private int distCode;
	private String distName;
    private String branchFullName;
    private String branchMulti;
    private int orderCode;

	
	
public Branch(){
		
		branchCode=0;
		branchName="";		
		createdBy = "";
		creationDate = null;
		modifiedBy = "";
		modificationDate = null;
		status = false;
		token="";
		branchCode=0;
		distCode=0;
		distName="";
		branchFullName="";
		orderCode=0;
			
	}
	
	
	
		public String getBranchFullName() {
		    return branchFullName;
		}
		
		public void setBranchFullName(String branchFullName) {
		    this.branchFullName = branchFullName;
		}




	public int getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(int branchCode) {
		this.branchCode = branchCode;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getModificationDate() {
		return modificationDate;
	}
	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getBuCode() {
		return buCode;
	}
	public void setBuCode(int buCode) {
		this.buCode = buCode;
	}
	public int getDistCode() {
		return distCode;
	}
	public void setDistCode(int distCode) {
		this.distCode = distCode;
	}
	public String getDistName() {
		return distName;
	}
	public void setDistName(String distName) {
		this.distName = distName;
	}




	public Object getGetBranchListingTableRow(int i) {
		// TODO Auto-generated method stub
        String returnStr ;
        String modifyLink = "";
        String deleteLink = "";
        String date="";
		if(this.modificationDate!=null)
			date = LMSUtil.convertDateToString(this.modificationDate);
		
        deleteLink = "javascript:confirmDelete('" + branchCode + "')";
       
        modifyLink = "FormManager?key=BRANCHAdd&type=MODIFY&BRANCH_CODE=" + this.branchCode;
       // deleteLink = "/"+ctxProp+"/FormManager?key=CITYAdd&type=DELETE&CITY_CODE=" + this.cityCode;
          returnStr = "<tr> "+
                  "<td  ><div align=center>"+this.branchName+"</div></td>"+
                  "<td >"+SecurityAPI.encodeHTML(this.branchFullName)+"</td>"+
      		
      			"<td >"+SecurityAPI.encodeHTML(LMSUtil.blankIfNull(this.distName))+"</td>"+
      			"<td><div align=center>" + this.modifiedBy + "</div></td>" +
				"<td><div align=center>" + date + "</div></td>" +
      			"<td ><div align=center><a href=\"" + modifyLink + "\"><img src=images/edit.png border=0></a>&nbsp;<a href=\"" + deleteLink + "\"><img src=images/delete.png border=0></a></div></td>"+
      			"</tr>"  ;
        
          
         
        	     
        return returnStr;
	}



	public String getBranchMulti() {
		return branchMulti;
	}



	public void setBranchMulti(String branchMulti) {
		this.branchMulti = branchMulti;
	}



	public int getOrderCode() {
		return orderCode;
	}



	public void setOrderCode(int orderCode) {
		this.orderCode = orderCode;
	}
	
	
	
	

}
