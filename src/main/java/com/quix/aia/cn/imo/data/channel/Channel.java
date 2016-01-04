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
package com.quix.aia.cn.imo.data.channel;

import java.util.Date;

import com.quix.aia.cn.imo.utilities.SecurityAPI;


/**
 * <p>Channel bean class.</p> 
 * @author khyati
 * @version 1.0
 */
public class Channel {
	private int channelCode;
	private String channelName;
	private String createdBy;
	private Date creationDate;
	private String modifiedBy;
	private Date modificationDate;
	private boolean status;
	private String token; 
	
	
	/**
	 * <p>Default Constructor.Setting default values for Channel fields.</p>
	 */
	public Channel(){
		channelCode=0;
		channelName="";		
		createdBy = "";
		creationDate = null;
		modifiedBy = "";
		modificationDate = null;
		status = false;
		setToken("");
		
	}
	
	
	/**
	 * @return the channelCode
	 */
	public int getChannelCode() {
		return channelCode;
	}
	
	
	/**
	 * @param channelCode the channelCode to set
	 */
	public void setChannelCode(int channelCode) {
		this.channelCode = channelCode;
	}
	
	
	/**
	 * @return the channelName
	 */
	public String getChannelName() {
		return channelName;
	}
	
	
	/**
	 * @param channelName the channelName to set
	 */
	public void setChannelName(String channelName) {
		this.channelName = channelName;
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
	    * <p>Dynamic rows added based on Channel list size</p>
	    * @return the row as Object
	    */
	public Object getGetChannelListingTableRow(int i) {
		// TODO Auto-generated method stub
        String returnStr ;
        String modifyLink = "";
        String deleteLink = "";
        deleteLink = "javascript:confirmDelete('" + channelCode + "')";
        modifyLink = "FormManager?key=ChannelAdd&type=MODIFY&CHANNEL_CODE=" + this.channelCode;
      //  deleteLink = "/"+ctxProp+"/FormManager?key=ChannelAdd&type=DELETE&CHANNEL_CODE=" + this.channelCode;
          returnStr = "<tr > "+
        		
      			"<td  ><div align=center>"+this.channelCode+"</div></td>"+
      			"<td >"+SecurityAPI.encodeHTML(this.channelName)+"</td>"+
      			" <td ><div align=center><a href=\"" + modifyLink + "\"><img src=images/edit.png border=0></a>&nbsp;<a href=\"" + deleteLink + "\"><img src=images/delete.png border=0></a></div></td>"+
      			"</tr>"  ;
        
          
         
        	     
        return returnStr;
		
	}
	
	
	
	
	/**
	 * <p>@Override toString() method of Object class</p>
	 */
	public String toString() {
		
		
		return ("Name-> "+this.channelName + " Channel CODE-> "+this.channelCode  );
	}

}
