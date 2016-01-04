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
package com.quix.aia.cn.imo.data.ssc;

import java.util.Date;

import com.quix.aia.cn.imo.utilities.LMSUtil;
import com.quix.aia.cn.imo.utilities.SecurityAPI;


/**
* <p>Ssc bean class.</p>
 * @author khyati
 * @version 1.0
 */
public class Ssc {
	private int sscCode;
	private String sscName;
	private String createdBy;
	private Date creationDate;
	private String modifiedBy;
	private Date modificationDate;
	private boolean status;
	private int cityCode;
	private String token;
	private String cityName;
	private int branchCode;
    private String sscFullName;
    private int orderCode;
    private String co;
    private String cityCodeStr;
    

	
	
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



	public int getBranchCode() {
		return branchCode;
	}



	public void setBranchCode(int branchCode) {
		this.branchCode = branchCode;
	}



	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}


	
	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}



	private int buCode;
	private int districtCode;
	
	
	
	/**
	 * <p>Default Constructor.Setting default values for SSC fields.</p>
	 */
	 public Ssc(){
		 
		 
		 	sscCode=0;
		 	cityCode=0;
			sscName="";		
			createdBy = "";
			creationDate = null;
			modifiedBy = "";
			modificationDate = null;
			status = false;
			setToken("");
			branchCode=0;
			buCode=0;
			districtCode=0;
            sscFullName="";
            co="";
            cityCodeStr="";

	 }
	 
	 

	    public String getSscFullName() {
	        return sscFullName;
	    }



	    public void setSscFullName(String sscFullName) {
	        this.sscFullName = sscFullName;
	    }
	    

	    
	 /**
		 * <p>Constructor.Setting SSC object values for SSC fields.</p>
		 * @param Ssc object
		 */
	 public Ssc(Ssc ssc){
			
			this.cityCode = ssc.getCityCode();
			}
	
	 
	 /**
		 * @return the sscCode
		 */
	public int getSscCode() {
		return sscCode;
	}
	
	/**
	 * @param sscCode the sscCode to set
	 */
	public void setSscCode(int sscCode) {
		this.sscCode = sscCode;
	}
	
	
	/**
	 * @return the sscName
	 */
	public String getSscName() {
		return sscName;
	}
	
	
	/**
	 * @param sscName the sscName to set
	 */
	public void setSscName(String sscName) {
		this.sscName = sscName;
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
	 * @return the cityCode
	 */
	public int getCityCode() {
		return cityCode;
	}
	
	
	/**
	 * @param cityCode the cityCode to set
	 */
	public void setCityCode(int cityCode) {
		this.cityCode = cityCode;
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
	    * <p>Dynamic rows added based on SSC list size</p>
	    * @return the row as Object
	    */
	public Object getGetSscListingTableRow(int i) {
		// TODO Auto-generated method stub
        String returnStr ;
        String modifyLink = "";
        String deleteLink = "";
        String date="";
		if(this.modificationDate!=null)
			date = LMSUtil.convertDateToString(this.modificationDate);
        deleteLink = "javascript:confirmDelete('" + sscCode + "')";
        modifyLink = "FormManager?key=SSCAdd&type=MODIFY&SSC_CODE=" + this.sscCode;
       // deleteLink = "/"+ctxProp+"/FormManager?key=SSCAdd&type=DELETE&SSC_CODE=" + this.sscCode;
          returnStr = "<tr> "+
        		
                  "<td  ><div align=center>"+this.sscName+"</div></td>"+
                  "<td >"+SecurityAPI.encodeHTML(this.sscFullName)+"</td>"+
      			"<td >"+SecurityAPI.encodeHTML(LMSUtil.blankIfNull(this.cityName))+"</td>"+
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
		
		
		return ("SSC CODE-> "+this.sscCode + " SSC NAME-> "+this.sscName + " CITY CODE-> "+this.cityCode );
	}



	public int getOrderCode() {
		return orderCode;
	}



	public void setOrderCode(int orderCode) {
		this.orderCode = orderCode;
	}


	
}
