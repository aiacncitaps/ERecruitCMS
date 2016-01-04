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
package com.quix.aia.cn.imo.data.office;

import java.util.Date;

import com.quix.aia.cn.imo.utilities.LMSUtil;
import com.quix.aia.cn.imo.utilities.SecurityAPI;

public class Office {
	
	
	private int officeCode;
	private String officeName;
	private String createdBy;
	private Date creationDate;
	private String modifiedBy;
	private Date modificationDate;
	private boolean status;
	private int sscCode;
	private String token;
	private String sscName;
	private int branchCode;
    private String officeFullName;
    private int buCode;
	private int districtCode;
	private int cityCode;
	private int orderCode;
	private String co;
	private String cityCodeStr;
	private String sscCodeStr;
	private float mancode;
	
	
	
    
    public String getCo() {
		return co;
	}


	public void setCo(String co) {
		this.co = co;
	}


	public String getCityCodeStr() {
		return cityCodeStr;
	}


	public void setCityCodeStr(String cityCodeStr) {
		this.cityCodeStr = cityCodeStr;
	}


	public String getSscCodeStr() {
		return sscCodeStr;
	}


	public void setSscCodeStr(String sscCodeStr) {
		this.sscCodeStr = sscCodeStr;
	}


	public Office(){
    	
    	buCode=0;
    	districtCode=0;
    	officeCode=0;
	 	setSscCode(0);
		officeName="";		
		createdBy = "";
		creationDate = null;
		modifiedBy = "";
		modificationDate = null;
		status = false;
		setToken("");
		branchCode=0;
        officeFullName="";
        sscName="";
        cityCode=0;
        co="";
        cityCodeStr="";
        sscCodeStr="";
        mancode=0;
    }
    
    
	public String getSscName() {
		return sscName;
	}

	public void setSscName(String sscName) {
		this.sscName = sscName;
	}

	public int getOfficeCode() {
		return officeCode;
	}
	public void setOfficeCode(int officeCode) {
		this.officeCode = officeCode;
	}
	public String getOfficeName() {
		return officeName;
	}
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
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
	public int getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(int branchCode) {
		this.branchCode = branchCode;
	}
	public String getOfficeFullName() {
		return officeFullName;
	}
	public void setOfficeFullName(String officeFullName) {
		this.officeFullName = officeFullName;
	}



	public int getBuCode() {
		return buCode;
	}



	public void setBuCode(int buCode) {
		this.buCode = buCode;
	}



	public int getDistrictCode() {
		return districtCode;
	}



	public void setDistrictCode(int districtCode) {
		this.districtCode = districtCode;
	}



	public  Object getGetOfficeListingTableRow(int i) {
		// TODO Auto-generated method stub
		 String returnStr ;
	        String modifyLink = "";
	        String deleteLink = "";
	        String date="";
			if(this.modificationDate!=null)
				date = LMSUtil.convertDateToString(this.modificationDate);
			
	        deleteLink = "javascript:confirmDelete('" + officeCode + "')";
	        modifyLink = "FormManager?key=OFFICEAdd&type=MODIFY&OFFICE_CODE=" + this.officeCode;
	       // deleteLink = "/"+ctxProp+"/FormManager?key=SSCAdd&type=DELETE&SSC_CODE=" + this.sscCode;
	          returnStr = "<tr> "+
	        		
	                  "<td  ><div align=center>"+this.officeName+"</div></td>"+
	                  "<td >"+SecurityAPI.encodeHTML(this.officeFullName)+"</td>"+
	      			"<td >"+SecurityAPI.encodeHTML(LMSUtil.blankIfNull(this.sscName))+"</td>"+
	      			"<td><div align=center>" + this.modifiedBy + "</div></td>" +
					"<td><div align=center>" + date + "</div></td>" +
	      			" <td ><div align=center><a href=\"" + modifyLink + "\"><img src=images/edit.png border=0></a>&nbsp;<a href=\"" + deleteLink + "\"><img src=images/delete.png border=0></a></div></td>"+
	      			"</tr>"  ;
	        
	          
	        return returnStr;
	}



	public int getSscCode() {
		return sscCode;
	}



	public void setSscCode(int sscCode) {
		this.sscCode = sscCode;
	}


	public int getCityCode() {
		return cityCode;
	}


	public void setCityCode(int cityCode) {
		this.cityCode = cityCode;
	}


	public int getOrderCode() {
		return orderCode;
	}


	public void setOrderCode(int orderCode) {
		this.orderCode = orderCode;
	}


	public float getMancode() {
		return mancode;
	}


	public void setMancode(float mancode) {
		this.mancode = mancode;
	}


}
