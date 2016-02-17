package com.quix.aia.cn.imo.data.city_dist;

import java.util.Date;

import com.quix.aia.cn.imo.utilities.LMSUtil;
import com.quix.aia.cn.imo.utilities.SecurityAPI;

public class City_Dist {
	
	private int cityDistCode;
	private int distCode;
	private String cityCode;
	private String cityName;
	private String createdBy;
	private Date creationDate;
	private boolean status;
	private String distName;
	private String cityFullName;
	
	
	public City_Dist(){
		cityDistCode=0;
		distCode=0;
		cityCode="";
		cityName="";
		createdBy = "";
		creationDate = null;
		distName="";
		cityFullName="";
	}
	
	
	
	public int getCityDistCode() {
		return cityDistCode;
	}
	public void setCityDistCode(int cityDistCode) {
		this.cityDistCode = cityDistCode;
	}
	public int getDistCode() {
		return distCode;
	}
	public void setDistCode(int distCode) {
		this.distCode = distCode;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
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


	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}



	public Object getGetCityListingTableRow(int i) {
		// TODO Auto-generated method stub
		String returnStr ;
        String deleteLink = "";

        deleteLink = "javascript:confirmDelete('" + this.cityDistCode + "')";
       
            returnStr = "<tr> "+
        		
                  "<td  ><div align=center>"+this.distName	+"</div></td>"+
                  "<td >"+SecurityAPI.encodeHTML(this.cityName)+"</td>"+
      			"<td ><div align=center><a href=\"" + deleteLink + "\"><img src=images/delete.png border=0></a></div></td>"+
      			"</tr>"  ;
        
          
         return returnStr;
        	     
	}



	public String getDistName() {
		return distName;
	}



	public void setDistName(String distName) {
		this.distName = distName;
	}



	public String getCityFullName() {
		return cityFullName;
	}



	public void setCityFullName(String cityFullName) {
		this.cityFullName = cityFullName;
	}



	
	

}
