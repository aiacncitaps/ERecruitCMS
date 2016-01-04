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




package com.quix.aia.cn.imo.data.city;

import java.util.Date;

import com.quix.aia.cn.imo.utilities.LMSUtil;
import com.quix.aia.cn.imo.utilities.SecurityAPI;



/**
 * <p>City bean class.</p>
 * @author khyati
 * @version 1.0
 */
public class City {
	
	private int cityCode;
	private String cityName;
	private String createdBy;
	private Date creationDate;
	private String modifiedBy;
	private Date modificationDate;
	private boolean status;
	private int branchCode;
	private String token;
	private int district;
	private String distName;
    private String cityFullName;
    private int orderCode;
    private String discFullName;
    private String regCode;
    private String co;
    
    
    
	
	public int getDistrict() {
		return district;
	}

	public void setDistrict(int district) {
		this.district = district;
	}

	public String getDistName() {
		return distName;
	}

	public void setDistName(String distName) {
		this.distName = distName;
	}

	private int buCode;
	private String branchName;
	

	
	/**
	 * @return the districtName
	 */
	public String getBranchName() {
		return branchName;
	}

	/**
	 * @param districtName the districtName to set
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}


	
	
	/**
	 * <p>Default Constructor.Setting default values for City fields.</p>
	 */
	public City(){
		
		cityCode=0;
		cityName="";		
		createdBy = "";
		creationDate = null;
		modifiedBy = "";
		modificationDate = null;
		status = false;
		token="";
		branchCode=0;
		buCode=0;
		distName="";
		district=0;
        cityFullName="";
        orderCode=0;
        regCode="";
        co="";
	}
	
	
	/**
	 * <p>Constructor.Setting districtCode values for districtCode fields.</p>
	 */
	
	 public City(City city){
			
			this.branchCode = city.getBranchCode();
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
	 * @return the districtCode
	 */
	public int getBranchCode() {
		return branchCode;
	}
	
	
	/**
	 * @param districtCode the districtCode to set
	 */
	public void setBranchCode(int branchCode) {
		this.branchCode = branchCode;
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
	
    public String getCityFullName() {
        return cityFullName;
    }

    public void setCityFullName(String cityFullName) {
        this.cityFullName = cityFullName;
    }
    

	
	/**
	    * <p>Dynamic rows added based on City list size</p>
	    * @return the row as Object
	    */
	public Object getGetCityListingTableRow(int i) {
		// TODO Auto-generated method stub
        String returnStr ;
        String modifyLink = "";
        String deleteLink = "";
        String date="";
		if(this.modificationDate!=null)
			date = LMSUtil.convertDateToString(this.modificationDate);
		
        deleteLink = "javascript:confirmDelete('" + this.cityName + "','"+this.co+"')";
       
        modifyLink = "FormManager?key=CITYAdd&type=MODIFY&CITY_CODE=" + this.cityName+"&CO"+this.co;
       // deleteLink = "/"+ctxProp+"/FormManager?key=CITYAdd&type=DELETE&CITY_CODE=" + this.cityCode;
          returnStr = "<tr> "+
        		
                  "<td  ><div align=center>"+this.cityName+"</div></td>"+
                  "<td >"+SecurityAPI.encodeHTML(this.cityFullName)+"</td>"+
      			"<td >"+SecurityAPI.encodeHTML(LMSUtil.blankIfNull(this.branchName))+"</td>"+
      			"<td >"+SecurityAPI.encodeHTML(LMSUtil.blankIfNull(discFullName))+"</td>"+
      			"<td ><div align=center><a href=\"" + modifyLink + "\"><img src=images/edit.png border=0></a>&nbsp;<a href=\"" + deleteLink + "\"><img src=images/delete.png border=0></a></div></td>"+
      			"</tr>"  ;
        
          
         
        	     
        return returnStr;
		
	}
	
	/**
	 * <p>@Override toString() method of Object class</p>
	 */
	public String toString() {
		
		
		return ("CITY CODE-> "+this.cityCode + " CITY NAME-> "+this.cityName + " Branch CODE-> "+this.branchCode );
	}

	public int getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(int orderCode) {
		this.orderCode = orderCode;
	}

	public String getDiscFullName() {
		return discFullName;
	}

	public void setDiscFullName(String discFullName) {
		this.discFullName = discFullName;
	}

	public String getRegCode() {
		return regCode;
	}

	public void setRegCode(String regCode) {
		this.regCode = regCode;
	}

	public String getCo() {
		return co;
	}

	public void setCo(String co) {
		this.co = co;
	}
	

}
