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


package com.quix.aia.cn.imo.data.district;

import java.util.Date;

import com.quix.aia.cn.imo.utilities.LMSUtil;
import com.quix.aia.cn.imo.utilities.SecurityAPI;

/**
* <p>District  bean class.</p>
 * @author khyati
 * @version 1.0
 */
public class District {
	
	private int districtCode;
	private String districtName;
	private String createdBy;
	private Date creationDate;
	private String modifiedBy;
	private Date modificationDate;
	private boolean status;
	private int buCode;
	private String token;
	private int orderCode;
	
	private String buName;
	
	
	
	/**
	 * <p>Default Constructor.Setting default values for District fields.</p>
	 */
	public District(){
		
		districtCode=0;
		districtName="";
		createdBy = "";
		creationDate = null;
		modifiedBy = "";
		modificationDate = null;
		status = false;
		buCode=0;
		token="";
		orderCode=0;
	}
	
	
	
	
	
    public District(District district){
		
	this.buCode = district.getBuCode();
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
	 * @return the districtCode
	 */
	public int getDistrictCode() {
		return districtCode;
	}
	
	/**
	 * @param districtCode the districtCode to set
	 */
	public void setDistrictCode(int districtCode) {
		this.districtCode = districtCode;
	}
	
	
	/**
	 * @return the districtName
	 */
	public String getDistrictName() {
		return districtName;
	}
	
	
	/**
	 * @param districtName the districtName to set
	 */
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
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
	    * <p>Dynamic rows added based on District list size</p>
	    * @return the row as Object
	    */
	public Object getGetDistrictListingTableRow(int i) {
		// TODO Auto-generated method stub
        String returnStr ;
        String modifyLink = "";
        String deleteLink = "";
        deleteLink = "javascript:confirmDelete('" + districtCode + "')";
        String buName="";
       /* //For all BU NAME 
        for (Iterator iterator = buList.iterator(); iterator.hasNext();) 
        {
            Bu object = (Bu) iterator.next();
            if(object.getBuCode() == buCode)
            buName = object.getBuName();
        }
        //
*/        
        String date="";
		if(this.modificationDate!=null)
			date = LMSUtil.convertDateToString(this.modificationDate);
		
       
        modifyLink = "FormManager?key=DISTRICTAdd&type=MODIFY&DISTRICT_CODE=" + this.districtCode;
     //   deleteLink = "/"+ctxProp+"/FormManager?key=DISTRICTAdd&type=DELETE&DISTRICT_CODE=" + this.districtCode;
          returnStr = "<tr > "+
        		
      			"<td  ><div align=center>"+this.districtCode+"</div></td>"+
      			"<td >"+SecurityAPI.encodeHTML(this.districtName)+"</td>"+
      			"<td >"+SecurityAPI.encodeHTML(LMSUtil.blankIfNull(this.buName))+"</td>"+
      			"<td><div align=center>" + this.modifiedBy + "</div></td>" +
				"<td><div align=center>" + date + "</div></td>" +
      		    " <td ><div align=center><a href=\"" + modifyLink + "\"><img src=images/edit.png border=0></a>&nbsp;<a href=\"" + deleteLink + "\"><img src=images/delete.png border=0></a></div></td>"+
      			"</tr>"  ;
        
          
         
        	     
        return returnStr;
		
	}
	
	
	/**
	 * <p>@Override toString() method of Object class</p>
	 */
	public String toString() {
		
		
		return ("DISTRICT CODE-> "+this.districtCode + " DISTRICT NAME-> "+this.districtName + " DISTRICT CODE-> "+this.districtCode );
	}





	public int getOrderCode() {
		return orderCode;
	}





	public void setOrderCode(int orderCode) {
		this.orderCode = orderCode;
	}
	

}
