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
package com.quix.aia.cn.imo.data.egreeting;

import java.util.Date;

import com.quix.aia.cn.imo.utilities.LMSUtil;
import com.quix.aia.cn.imo.utilities.SecurityAPI;


/**
 * <p>E_Greeting   bean class.</p>
 * @author khyati
 * @version 1.0
 */
public class E_Greeting 
{
	private int EGreetingCode;
	private String EGreetingName;
	private int festive_category;
	
	private String fileName;
	private String fileLocation;
	
	private String created_by;
	private Date created_date;
	private String modified_by;
	private Date modified_date;
	private boolean status;
	private String token;
	private String userName;
	private String catName;
	private E_GreetingMaterial eGreetingMaterial;
	
	
	
	/**
	 * <p>Default Constructor.Setting default values for E_Greeting fields.</p>
	 */

	public E_Greeting()
	{
		EGreetingCode=0;
		EGreetingName="";
		festive_category=0;
		fileName="";
		fileLocation="";
		created_by="";
		created_date=null;
		modified_by = "";
		modified_date = null;
		status = false;
		token="";
		eGreetingMaterial = new E_GreetingMaterial();
	}
	
	
	
	/**
	 * @return the catName
	 */
	public String getCatName() {
		return catName;
	}


	/**
	 * @param catName the catName to set
	 */
	public void setCatName(String catName) {
		this.catName = catName;
	}
	
	
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}


	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
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
	 * @return the EGreetingCode
	 */
	public int getEGreetingCode() {
		return EGreetingCode;
	}



	/**
	 * @param EGreetingCode the EGreetingCode to set
	 */
	public void setEGreetingCode(int eGreetingCode) {
		EGreetingCode = eGreetingCode;
	}



	/**
	 * @return the EGreetingName
	 */
	public String getEGreetingName() {
		return EGreetingName;
	}



	/**
	 * @param EGreetingName the EGreetingName to set
	 */
	public void setEGreetingName(String eGreetingName) {
		EGreetingName = eGreetingName;
	}



	/**
	 * @return the festive_category
	 */
	public int getFestive_category() {
		return festive_category;
	}



	/**
	 * @param festive_category the festive_category to set
	 */
	public void setFestive_category(int festive_category) {
		this.festive_category = festive_category;
	}



	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}



	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}



	/**
	 * @return the fileLocation
	 */
	public String getFileLocation() {
		return fileLocation;
	}



	/**
	 * @param fileLocation the fileLocation to set
	 */
	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}



	/**
	 * @return the created_by
	 */
	public String getCreated_by() {
		return created_by;
	}



	/**
	 * @param created_by the created_by to set
	 */
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}



	/**
	 * @return the created_date
	 */
	public Date getCreated_date() {
		return created_date;
	}



	/**
	 * @param created_date the created_date to set
	 */
	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}



	/**
	 * @return the modified_by
	 */
	public String getModified_by() {
		return modified_by;
	}



	/**
	 * @param modified_by the modified_by to set
	 */
	public void setModified_by(String modified_by) {
		this.modified_by = modified_by;
	}



	/**
	 * @return the modified_date
	 */
	public Date getModified_date() {
		return modified_date;
	}


	/**
	 * @param modified_date the modified_date to set
	 */
	public void setModified_date(Date modified_date) {
		this.modified_date = modified_date;
	}


	/**
	 * @return the eGreetingMaterial
	 */
	public E_GreetingMaterial geteGreetingMaterial() {
		return eGreetingMaterial;
	}



	/**
	 * @param eGreetingMaterial the eGreetingMaterial to set
	 */
	public void seteGreetingMaterial(E_GreetingMaterial eGreetingMaterial) {
		this.eGreetingMaterial = eGreetingMaterial;
	}



	/**
	    * <p>Dynamic rows added based on eGreeting list size</p>
	    * @return the row as Object
	    */

	public Object getGeteGreetingListingTableRow(int i) {
		// TODO Auto-generated method stub
        String returnStr ;
        String modifyLink = "";
        String deleteLink = "";
        deleteLink = "javascript:confirmDelete('" + EGreetingCode + "')";
        String row ="";
        String posted_date ="";
        String date="";
		if(this.modified_date!=null)
			date = LMSUtil.convertDateToString(this.modified_date);
        if(created_date!=null)
        	posted_date = LMSUtil.convertDateToString(created_date);
       
        if(i%2 == 0)
         row = "";
        String cell = "";
        modifyLink = "FormManager?key=E_GreetingAdd&type=MODIFY&E_GREETING_ID=" + this.EGreetingCode;
      //  deleteLink="javascript:deleteEgreeting('"+this.EGreetingCode+"','"+SecurityAPI.encodeHTML(EGreetingName)+"')";
          returnStr = "<tr "+row+"'> "+
        		
      			
      			"<td '"+cell+"'>"+SecurityAPI.encodeHTML(this.EGreetingName)+"</td>"+
      			"<td  '"+cell+"'>"+SecurityAPI.encodeHTML(this.catName+"")+"</td>"+
      			"<td '"+cell+"'>"+SecurityAPI.encodeHTML(posted_date)+"</td>"+
      			"<td '"+cell+"'>"+SecurityAPI.encodeHTML(LMSUtil.blankIfNull(this.created_by))+"</td>"+
      			"<td '"+cell+"'>"+SecurityAPI.encodeHTML(this.modified_by)+"</td>"+
				"<td '"+cell+"'>" + date + "</div></td>" +
      			" <td '"+cell+"'><div align=center><a href=\"" + modifyLink + "\"><img src=images/edit.png border=0></a>&nbsp;<a href=\"" + deleteLink + "\"><img src=images/delete.png border=0></a></div></td>"+
      			"</tr>"  ;
        
          
         
        	     
        return returnStr;
		
	}


	/**
	 * <p>@Override toString() method of Object class</p>
	 */
	public String toString() {
		
		
		return ("EGREETING CODE-> "+this.EGreetingCode + " EGREETING NAME-> "+this.EGreetingName + " FESTIVE CATEGORY CODE-> "+this.festive_category );
	}
	

	
	
}
