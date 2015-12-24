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
* 18-May-2015         Jay            copyright and security code added
****************************************** *********************************** */

package com.quix.aia.cn.imo.data.user;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.quix.aia.cn.imo.utilities.LMSUtil;
import com.quix.aia.cn.imo.utilities.SecurityAPI;

/**
 * 
 * @author Jay
 * @version 1.0
 */
public class User {
	
	private int user_no;
	private String channelCode;
	private int buCode;
	private String cityCode;
	private String sscCode;
	private String officeCode;
	private String createdBy;
	private Date creationDate;
	private String modifiedBy;
	private Date modificationDate;
	private boolean status;
	private int department;
	private String staffLoginId;
	private String email;
	private String contactNo;
	private String password;
	private String staffName;
	private String userType;
	private String token;
	private int district;
	private String extensionNo;
	private boolean buLevel;
	private boolean districtLevel;
	private boolean branchLevel;
	private boolean cityLevel;
	private boolean sscLevel;
	private boolean officeLevel;
	private Date lastLogIn;
	private Date faildLogin;
	private boolean statusPsw;
	private int branchCode;
	private String branchName;
	private String officeName;
	private boolean cho;
	private String ssoSessionId;

	private String buName;
	private String distName;
	private String cityName;
	private String sscName;
	private String deptName;
    
	private int createdUserBuCode;
	private int createdUserDistCode;
	private int createdUserbBranchCode;
	private int createdUserCityCode;
	private int createdUserSscCode;
	private int createdUserOfficeCode;
	
	private boolean createdUserBuLevel;
	private boolean createdUserDistLevel;
	private boolean createdUserBranchLevel;
	private boolean createdUserCityLevel;
	private boolean createdUserSscLevel;
	private boolean createdUserOfficeLevel;
	/**
	 * <p>Default Constructor.Setting default values for Holiday fields.</p>
	 */
	public User(){
		
		user_no = 0;
		channelCode = "";
		department = 0;
		staffLoginId = "";
		email = "";
		extensionNo="";
		password = "";
		buCode = 0;
		cityCode = "";
		sscCode = "";
		createdBy = "";
		creationDate = null;
		modifiedBy = "";
		modificationDate = null;
		status = false;
		staffName="";
		userType="";
		token="";
		district=0;
		lastLogIn=null;
		faildLogin=null;
		createdUserBuCode=0;
		createdUserDistCode=0;
		createdUserCityCode=0;
		createdUserSscCode=0;
		createdUserbBranchCode=0;
		createdUserOfficeCode=0;
		
		branchCode=0;
		branchName="";
		officeCode="";
		cho=false;
	}
	
	/**
	 * <p>@Override toString() method of Object class</p>
	 */
	public  User(User user){
//		String no,String chancode,String dept,String autoLevel,String staffId,String email,String extesionno,String bucode,
//	    String city,String ssc,String createdby,String modify
		this.user_no=user.getUser_no();
		this.channelCode=user.getChannelCode();
		this.department = user.getDepartment();
		this.staffLoginId = user.getStaffLoginId();
		this.email = user.getEmail();
		this.extensionNo=user.getExtensionNo();
		this.buCode = user.getBuCode();
		this.cityCode = user.getCityCode();
		this.sscCode = user.getSscCode();
		this.createdBy = user.getCreatedBy();
		this.modifiedBy =user.getModifiedBy();
		
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
	
	/**
	 * @return the DistName
	 */
	public String getDistName() {
		return distName;
	}
	/**
	 * @param DistName the DistName to set
	 */
	public void setDistName(String distName) {
		this.distName = distName;
	}
	/**
	 * @return the CityName
	 */
	public String getCityName() {
		return cityName;
	}
	/**
	 * @param CityName the CityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	/**
	 * @return the SScName
	 */
	public String getSscName() {
		return sscName;
	}
	/**
	 * @param SScName the SScName to set
	 */
	public void setSscName(String sscName) {
		this.sscName = sscName;
	}

	/**
	 * @return the DeptName
	 */
	public String getDeptName() {
		return deptName;
	}
	/**
	 * @param DeptName the DeptName to set
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * @return the BuName
	 */
	public String getBuName() {
		return buName;
	}
	/**
	 * @param BuName the BuName to set
	 */
	public void setBuName(String buName) {
		this.buName = buName;
	}

	/**
	 * @return the BuLevel ture or false 
	 */
	public boolean isBuLevel() {
		return buLevel;
	}
	/**
	 * @param BuLevel the BuLevel to set
	 */
	public void setBuLevel(boolean buLevel) {
		this.buLevel = buLevel;
	}
	/**
	 * @return the DistrictLevel ture or false 
	 */
	public boolean isDistrictLevel() {
		return districtLevel;
	}
	/**
	 * @param DistrictLevel the DistrictLevel to set
	 */
	public void setDistrictLevel(boolean districtLevel) {
		this.districtLevel = districtLevel;
	}
	/**
	 * @return the CityLevel ture or false 
	 */
	public boolean isCityLevel() {
		return cityLevel;
	}
	/**
	 * @param CityLevel the CityLevel to set
	 */
	public void setCityLevel(boolean cityLevel) {
		this.cityLevel = cityLevel;
	}
	/**
	 * @return the SscLevel ture or false 
	 */
	public boolean isSscLevel() {
		return sscLevel;
	}
	/**
	 * @param SscLevel the SscLevel to set
	 */
	public void setSscLevel(boolean sscLevel) {
		this.sscLevel = sscLevel;
	}
	/**
	 * @return the Userno 
	 */
	public int getUser_no() {
		return user_no;
	}
	
	/**
	 * @param Userno the Userno to set
	 */
	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}
	/**
	 * @return the ChannelCode 
	 */
	public String getChannelCode() {
		return channelCode;
	}
	/**
	 * @param ChannelCode the ChannelCode to set
	 */
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	/**
	 * @return the BuCode 
	 */
	public int getBuCode() {
		return buCode;
	}
	/**
	 * @param BuCode the BuCode to set
	 */
	public void setBuCode(int buCode) {
		this.buCode = buCode;
	}
	/**
	 * @return the CityCode 
	 */
	public String getCityCode() {
		return cityCode;
	}
	/**
	 * @param CityCode the CityCode to set
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	/**
	 * @return the SscCode 
	 */
	public String getSscCode() {
		return sscCode;
	}
	/**
	 * @param SscCode the SscCode to set
	 */
	public void setSscCode(String sscCode) {
		this.sscCode = sscCode;
	}
	/**
	 * @return the CreatedBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param CreatedBy the CreatedBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @return the CreationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}
	/**
	 * @param CreationDate the CreationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	/**
	 * @return the ModifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}
	/**
	 * @param ModifiedBy the ModifiedBy to set
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	/**
	 * @return the ModificationDate
	 */
	public Date getModificationDate() {
		return modificationDate;
	}
	/**
	 * @param ModificationDate the ModificationDate to set
	 */
	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}
	/**
	 * @return the Status true or false
	 */
	public boolean isStatus() {
		return status;
	}
	/**
	 * @param Status the Status to set
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}
	/**
	 * @return the Department
	 */
	public int getDepartment() {
		return department;
	}
	
	/**
	 * @param Department the Department to set
	 */
	public void setDepartment(int department) {
		this.department = department;
	}
	/**
	 * @return the StaffLoginId
	 */
	public String getStaffLoginId() {
		return staffLoginId;
	}
	/**
	 * @param StaffLoginId the StaffLoginId to set
	 */
	public void setStaffLoginId(String staffLoginId) {
		this.staffLoginId = staffLoginId;
	}
	/**
	 * @return the Email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * @param Email the Email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the ContactNo
	 */
	public String getContactNo() {
		return contactNo;
	}
	/**
	 * @param ContactNo the ContactNo to set
	 */
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	/**
	 * @return the Password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param Password the Password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * @return the StaffName
	 */
	public String getStaffName() {
		return staffName;
	}
	
	/**
	 * @param StaffName the StaffName to set
	 */
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	/**
	 * @return the UserType
	 */
	public String getUserType() {
		return userType;
	}
	
	/**
	 * @param UserType the UserType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	/**
	 * @return the Token
	 */
	public String getToken() {
		return token;
	}
	
	/**
	 * @param Token the Token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * @return the District
	 */
	public int getDistrict() {
		return district;
	}
	
	/**
	 * @param District the District to set
	 */
	public void setDistrict(int district) {
		this.district = district;
	}
	/**
	 * @return the ExtensionNo
	 */
	public String getExtensionNo() {
		return extensionNo;
	}
	
	/**
	 * @param ExtensionNo the ExtensionNo to set
	 */
	public void setExtensionNo(String extensionNo) {
		this.extensionNo = extensionNo;
	}
	
	
	 /**
	 * @return the createdUserBuCode
	 */
	public int getCreatedUserBuCode() {
		return createdUserBuCode;
	}

	/**
	 * @param createdUserBuCode the createdUserBuCode to set
	 */
	public void setCreatedUserBuCode(int createdUserBuCode) {
		this.createdUserBuCode = createdUserBuCode;
	}

	/**
	 * @return the createdUserDistCode
	 */
	public int getCreatedUserDistCode() {
		return createdUserDistCode;
	}

	/**
	 * @param createdUserDistCode the createdUserDistCode to set
	 */
	public void setCreatedUserDistCode(int createdUserDistCode) {
		this.createdUserDistCode = createdUserDistCode;
	}

	/**
	 * @return the createdUserCityCode
	 */
	public int getCreatedUserCityCode() {
		return createdUserCityCode;
	}

	/**
	 * @param createdUserCityCode the createdUserCityCode to set
	 */
	public void setCreatedUserCityCode(int createdUserCityCode) {
		this.createdUserCityCode = createdUserCityCode;
	}

	/**
	 * @return the createdUserSscCode
	 */
	public int getCreatedUserSscCode() {
		return createdUserSscCode;
	}

	/**
	 * @param createdUserSscCode the createdUserSscCode to set
	 */
	public void setCreatedUserSscCode(int createdUserSscCode) {
		this.createdUserSscCode = createdUserSscCode;
	}

	/**
	 * @return the createdUserBuLevel
	 */
	public boolean isCreatedUserBuLevel() {
		return createdUserBuLevel;
	}

	/**
	 * @param createdUserBuLevel the createdUserBuLevel to set
	 */
	public void setCreatedUserBuLevel(boolean createdUserBuLevel) {
		this.createdUserBuLevel = createdUserBuLevel;
	}

	/**
	 * @return the createdUserDistLevel
	 */
	public boolean isCreatedUserDistLevel() {
		return createdUserDistLevel;
	}

	/**
	 * @param createdUserDistLevel the createdUserDistLevel to set
	 */
	public void setCreatedUserDistLevel(boolean createdUserDistLevel) {
		this.createdUserDistLevel = createdUserDistLevel;
	}

	/**
	 * @return the createdUserCityLevel
	 */
	public boolean isCreatedUserCityLevel() {
		return createdUserCityLevel;
	}

	/**
	 * @param createdUserCityLevel the createdUserCityLevel to set
	 */
	public void setCreatedUserCityLevel(boolean createdUserCityLevel) {
		this.createdUserCityLevel = createdUserCityLevel;
	}

	/**
	 * @return the createdUserSscLevel
	 */
	public boolean isCreatedUserSscLevel() {
		return createdUserSscLevel;
	}

	/**
	 * @param createdUserSscLevel the createdUserSscLevel to set
	 */
	public void setCreatedUserSscLevel(boolean createdUserSscLevel) {
		this.createdUserSscLevel = createdUserSscLevel;
	}

	/**
	    * <p>Dynamic rows added based on USer list size</p>
	    * @return the row as string
	    */
	public Object getGetResListingTableRow(int i, HttpServletRequest req,User user) {
		 // TODO Auto-generated method stub
	
       String returnStr ;
       String modifyLink = "";
       String deleteLink = "";
       String row ="";
       User userObj = (User)req.getSession().getAttribute("currUserObj");
	        boolean modifyFlg = false;
	    
	        if(userObj.getUserType().equals("AD"))
	        	modifyFlg = true;
	        else if(user.getUser_no() == userObj.getUser_no())
	        	modifyFlg = true;
	        else if(userObj.isBuLevel()){
	        	//if(user.isCreatedUserDistLevel() || user.isCreatedUserCityLevel() || user.isCreatedUserSscLevel() || user.isCreatedUserBranchLevel() || user.isCreatedUserOfficeLevel())
	        	if(user.getDistrict()!=0 || !user.getCityCode().equals("0") || user.getBranchCode()!=0 || !user.getSscCode().equals("0") || !user.getOfficeCode().equals("0"))	
	        	modifyFlg = true;
	        }
	       /* else if(userObj.isDistrictLevel()){
	        	if(user.isCreatedUserDistLevel() || user.isCreatedUserCityLevel() || user.isCreatedUserSscLevel())
	        		modifyFlg = true;
	        }
	        else if(userObj.isCityLevel()){
	        	if(user.isCreatedUserCityLevel()  || user.isCreatedUserSscLevel())
	        		modifyFlg = true;
	        }
	        else if(userObj.isSscLevel()){
	        	if(user.isCreatedUserSscLevel())
	        		modifyFlg = true;
	        }
	       */
	        String date="";
			if(this.modificationDate!=null)
				date = LMSUtil.convertDateToString(this.modificationDate);
			
//       HashMap<String, String> map=null;
//        if( req.getSession().getAttribute("channelMap")!=null){
//       	map= (HashMap<String, String>) req.getSession().getAttribute("channelMap");
//        }  
//        String name="";
//       
//        String[] temp=this.channelCode.split("\\|");
//
//        for (int j = 0; j < temp.length; j++) { 
//       	 if(map.get(temp[j])!=null){
//       		 name=name+map.get(temp[j])+", ";
//       	 }
//			
//		}

     // this.channelCode =this.channelCode.replaceAll("1", "Life").replaceAll("2", "LifeTime").replaceAll("3", "Rest");
	        if(i%2 == 0)
	         row = "";
	        String cell = "";
	        if(modifyFlg){
	        	modifyLink = "FormManager?key=UserAdd&type=MODIFY&userNo=" + this.user_no;
	            modifyLink = "<a href=\" FormManager?key=UserAdd&type=MODIFY&userNo=" + this.user_no + "\"><img src=images/edit.png border=0></a>";
	            //deleteLink = "FormManager?key=UserAdd&type=DELETE&userNo=" + this.user_no;
	            deleteLink = "<a href= javascript:confirmDelete('" + this.user_no + "')><img src=images/delete.png border=0></a>";
	        }
       
     
       
         returnStr = "<tr "+row+"'> "+
       		"<td '"+cell+"'>"+SecurityAPI.encodeHTML(this.staffLoginId)+"</td>"+
     			//"<td  '"+cell+"'>"+SecurityAPI.encodeHTML(name)+"</td>"+
     			"<td '"+cell+"'>"+SecurityAPI.encodeHTML(LMSUtil.blankIfNull(this.buName))+"</td>"+
     			"<td  '"+cell+"'>"+SecurityAPI.encodeHTML(LMSUtil.blankIfNull(this.distName))+"</td>"+
     			"<td  '"+cell+"'>"+SecurityAPI.encodeHTML(LMSUtil.blankIfNull(this.branchName))+"</td>"+
     			"<td  '"+cell+"'>"+SecurityAPI.encodeHTML(LMSUtil.blankIfNull(this.cityName))+"</td>"+
     			"<td '"+cell+"'>"+SecurityAPI.encodeHTML(LMSUtil.blankIfNull(this.sscName))+"</td>"+
     			"<td '"+cell+"'>"+SecurityAPI.encodeHTML(LMSUtil.blankIfNull(this.officeName))+"</td>"+
     			"<td '"+cell+"'>"+SecurityAPI.encodeHTML(LMSUtil.blankIfNull(this.deptName))+"</td>"+
     			"<td '"+cell+"'>"+SecurityAPI.encodeHTML(this.staffName)+"</td>"+
     			"<td '"+cell+"'>"+SecurityAPI.encodeHTML(this.email)+"</td>"+
     			"<td '"+cell+"'>"+SecurityAPI.encodeHTML(this.contactNo)+"</td>"+
     			"<td '"+cell+"'>"+SecurityAPI.encodeHTML(this.extensionNo)+"</td>"+
     			"<td '"+cell+"'>"+SecurityAPI.encodeHTML(this.modifiedBy)+"</td>"+
				"<td '"+cell+"'>" + date + "</div></td>" +
     			" <td '"+cell+"'><div align=center>"+modifyLink+"&nbsp;"+deleteLink+"</div></td>"+
     			"</tr>"  ;
       
         
        
       	     
       return returnStr;
		
	}
	/**
	    * <p>Dynamic Header rows added based on USer list size</p>
	    * @return the row as string
	    */
	public String getGetResListingTableHdr() {
		// TODO Auto-generated method stub
        return "<tr bgcolor=#FDEDED >" +
        		
		"<th>Staff Login ID</th>"+
		"<th>Channel</th>"+
		"<th>BU</th>"+
		"<th>District</th>"+
		"<th>Branch</th>"+
		"<th>City</th>"+
		"<th>SSC</th>"+
		"<th>Department</th>"+
		"<th>Authorised Level</th>"+
		"<th>Name</th>"+
		"<th>Email Address</th>"+
		"<th>Contact Number</th>"+
		"<th>Extension Number</th>"+
		"<th>Action</th>"+
		 "</tr>";  
		              
        	
	}
	/**
	 * @return the LastLogIn
	 */
	public Date getLastLogIn() {
		return lastLogIn;
	}
	/**
	 * @param LastLogIn the LastLogIn to set
	 */
	public void setLastLogIn(Date lastLogIn) {
		this.lastLogIn = lastLogIn;
	}
	/**
	 * @return the FaildLogin Date
	 */
	public Date getFaildLogin() {
		return faildLogin;
	}
	/**
	 * @param FaildLogin Date the FaildLogin Date to set
	 */
	public void setFaildLogin(Date faildLogin) {
		this.faildLogin = faildLogin;
	}

	/**
	 * @return the StatusPsw true or false
	 */
	public boolean isStatusPsw() {
		return statusPsw;
	}

	/**
	 * @param StatusPsw the StatusPsw to set
	 */
	public void setStatusPsw(boolean statusPsw) {
		this.statusPsw = statusPsw;
	}

	public boolean isBranchLevel() {
		return branchLevel;
	}

	public void setBranchLevel(boolean branchLevel) {
		this.branchLevel = branchLevel;
	}

	public int getCreatedUserbBranchCode() {
		return createdUserbBranchCode;
	}

	public void setCreatedUserbBranchCode(int createdUserbBranchCode) {
		this.createdUserbBranchCode = createdUserbBranchCode;
	}

	public boolean isCreatedUserBranchLevel() {
		return createdUserBranchLevel;
	}

	public void setCreatedUserBranchLevel(boolean createdUserBranchLevel) {
		this.createdUserBranchLevel = createdUserBranchLevel;
	}

	public String getOfficeCode() {
		return officeCode;
	}

	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

	public boolean isOfficeLevel() {
		return officeLevel;
	}

	public void setOfficeLevel(boolean officeLevel) {
		this.officeLevel = officeLevel;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public boolean isCho() {
		return cho;
	}

	public void setCho(boolean cho) {
		this.cho = cho;
	}

	public boolean isCreatedUserOfficeLevel() {
		return createdUserOfficeLevel;
	}

	public void setCreatedUserOfficeLevel(boolean createdUserOfficeLevel) {
		this.createdUserOfficeLevel = createdUserOfficeLevel;
	}

	public int getCreatedUserOfficeCode() {
		return createdUserOfficeCode;
	}

	public void setCreatedUserOfficeCode(int createdUserOfficeCode) {
		this.createdUserOfficeCode = createdUserOfficeCode;
	}

	/**
	 * @return the ssoSessionId
	 */
	public String getSsoSessionId() {
		return ssoSessionId;
	}

	/**
	 * @param ssoSessionId the ssoSessionId to set
	 */
	public void setSsoSessionId(String ssoSessionId) {
		this.ssoSessionId = ssoSessionId;
	}
	
	
	

}
