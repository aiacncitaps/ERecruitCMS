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
* 15-May-2015       Abinas            pojo created for Presenter
* 08-June-2015       Abinas            doc comment added
****************************************** *********************************** */

package com.quix.aia.cn.imo.data.presenter;
import com.quix.aia.cn.imo.utilities.SecurityAPI;

import java.util.Date;

import com.quix.aia.cn.imo.utilities.LMSUtil;
/**
 * <p>Presenter bean class.</p>
 * 
 * @author Abinas
 * @version 1.0
 *
 */
public class Presenter {
	private int presenterCode;
	private String presenterName;
	private int category;
	private String presenterDescription;
	private Date start_date;
	private Date expiry_date;
	private String visibility;
	
	private int buCode;
	private int distCode;
	private int cityCode;
	private int sscCode;
	
	private String image_fileName;
	private String image_fileLocation;
	private String fileName;
	private String fileLocation;
	
	private String created_by;
	private Date created_date;
	private String modified_by;
	private Date modified_date;
	
	private String buName;
	private String distName;
	private String cityName;
	private String sscName;
	private String userName;
	private String catName;
	 private int officeCode;
	  private String officeName;
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

	private boolean status;
	private String token;
	private int branchCode;
	private String branchName;
	
	
	
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
	  * <p>Default Constructor.Setting default values for presenter fields.</p>
	  */

	public Presenter(){
		presenterCode=0;
		presenterName="";
		presenterDescription="";
		category=0;
		start_date=null;
		expiry_date=null;
		visibility="%";
		officeCode=0;
		officeName="";
		buCode = 0;
		distCode=0;
		cityCode = 0;
		sscCode = 0;
		 image_fileName="";
		image_fileLocation="";
		 fileName="";
		 fileLocation="";
		
		created_by="";
		created_date=null;
		modified_by = "";
		modified_date = null;
		
		status=false;
		token="";
		branchCode=0;
		branchName="";
		
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
	 * @return the presenter category name
	 */

	public String getCatName() {
		return catName;
	}

	/**
	 * @param cat name the cat name to set
	 */

	public void setCatName(String catName) {
		this.catName = catName;
	}


	/**
	 * @return the user name
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param user name the user name to set
	 */

	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the visibility
	 */
	public String getVisibility() {
		return visibility;
	}

	/**
	 * @param visibility the visibility to set
	 */

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	/**
	 * @return the category
	 */

	public int getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */

	public void setCategory(int category) {
		this.category = category;
	}


	/**
	 * @return the presenter description
	 */
	public String getPresenterDescription() {
		return presenterDescription;
	}
	/**
	 * @param presenterDescription the presenter description to set
	 */

	public void setPresenterDescription(String presenterDescription) {
		this.presenterDescription = presenterDescription;
	}

	/**
	 * @return the start date
	 */
	public Date getStart_date() {
		return start_date;
	}

	/**
	 * @param start_date the start date to set
	 */
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	/**
	 * @return the expiry date
	 */

	public Date getExpiry_date() {
		return expiry_date;
	}
	/**
	 * @param expiry_date the expiry date to set
	 */

	public void setExpiry_date(Date expiry_date) {
		this.expiry_date = expiry_date;
	}

	/**
	 * @return the bu code
	 */
	public int getBuCode() {
		return buCode;
	}
	/**
	 * @param buCode the bu code to set
	 */

	public void setBuCode(int buCode) {
		this.buCode = buCode;
	}

	/**
	 * @return the dist code
	 */
	public int getDistCode() {
		return distCode;
	}

	/**
	 * @param distCode the district code to set
	 */
	public void setDistCode(int distCode) {
		this.distCode = distCode;
	}
	/**
	 * @return the city code
	 */

	public int getCityCode() {
		return cityCode;
	}
	/**
	 * @param cityCode the city code to set
	 */

	public void setCityCode(int cityCode) {
		this.cityCode = cityCode;
	}

	/**
	 * @return the ssc code
	 */
	public int getSscCode() {
		return sscCode;
	}

	/**
	 * @param sscCode the ssc code to set
	 */
	public void setSscCode(int sscCode) {
		this.sscCode = sscCode;
	}
	/**
	 * @return the thumbnail file name
	 */

	public String getImage_fileName() {
		return image_fileName;
	}

	/**
	 * @param image_fileName the image_fileName to set
	 */
	public void setImage_fileName(String image_fileName) {
		this.image_fileName = image_fileName;
	}
	/**
	 * @return the thumbnail file location
	 */

	public String getImage_fileLocation() {
		return image_fileLocation;
	}

	/**
	 * @param image_fileLocation the image_fileLocation to set
	 */
	public void setImage_fileLocation(String image_fileLocation) {
		this.image_fileLocation = image_fileLocation;
	}

	/**
	 * @return the pdf file location
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
	 * @return the created by
	 */
	public String getCreated_by() {
		return created_by;
	}
	/**
	 * @param created_by the created_by to set
	 */

	public void setCreated_by(String created_by ) {
		this.created_by = created_by;
	}
	/**
	 * @return the created date
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
	 * @return the modify by
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
	 * @return the modify date
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
	 * @return the presenter code
	 */	
	public int getPresenterCode() {
		return presenterCode;
	}
	/**
	 * @param presenterCode the presenterCode to set
	 */
	public void setPresenterCode(int presenterCode) {
		this.presenterCode = presenterCode;
	}
	/**
	 * @return the presenter name
	 */
	public String getPresenterName() {
		return presenterName;
	}
	/**
	 * @param presenterName the presenterName to set
	 */
	public void setPresenterName(String presenterName) {
		this.presenterName = presenterName;
	}

	/**
	 * @return the file name
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
	 * <p>Dynamic rows added based presenter list size</p>
	 * @param i    each row number
	 * @return  row as string
	 */

	public Object getPresenterListingTableRow(int i) 
	{
		String returnStr ;
		String modifyLink = "";
		String deleteLink = "";
		String row ="";
		 String startdate ="";
	        String enddate ="";
	        String posted_date ="";
	        String date="";
			if(this.modified_date!=null)
				date = LMSUtil.convertDateToString(this.modified_date);
		 if(start_date!=null)
			 startdate = LMSUtil.convertDateToString(start_date);
	        if(expiry_date!=null)
	        	enddate = LMSUtil.convertDateToString(expiry_date);
	        if(created_date!=null)
	        	posted_date = LMSUtil.convertDateToString(created_date);
		//if(i%2 == 0)
		//	row = "style='background: #9A9090'";
		//String cell = "style='background: #9A9090'";
	        String cell="";
		Date todayDate = new Date();
		if(todayDate.before(this.expiry_date))
		modifyLink = "<a href=\" FormManager?key=PresenterAdminister&type=MODIFY&presenterCode=" + this.presenterCode+ "\"><img src=images/edit.png border=0></a>";
		else
			modifyLink="&nbsp;";
		deleteLink="javascript:deletePresenter('"+this.presenterCode+"')";
		returnStr = "<tr "+row+"'> "+

      			"<td  '"+cell+"'>"+SecurityAPI.encodeHTML(this.presenterName)+"</td>"+
      			"<td  '"+cell+"'>"+SecurityAPI.encodeHTML(this.catName+"")+"</td>"+
      			"<td  '"+cell+"' align=center>"+SecurityAPI.encodeHTML(startdate)+"</td>"+
      			"<td  '"+cell+"' align=center>"+SecurityAPI.encodeHTML(enddate)+"</td>"+
      			"<td '"+cell+"'>"+SecurityAPI.encodeHTML(LMSUtil.blankIfNull(this.buName))+"</td>"+
      			"<td '"+cell+"'>"+SecurityAPI.encodeHTML(LMSUtil.blankIfNull(this.distName))+"</td>"+
      			"<td '"+cell+"'>"+SecurityAPI.encodeHTML(LMSUtil.blankIfNull(this.branchName))+"</td>"+
      			"<td '"+cell+"'>"+SecurityAPI.encodeHTML(LMSUtil.blankIfNull(this.cityName))+"</td>"+
      			"<td '"+cell+"'>"+SecurityAPI.encodeHTML(LMSUtil.blankIfNull(this.sscName))+"</td>"+
      			"<td '"+cell+"'>"+SecurityAPI.encodeHTML(LMSUtil.blankIfNull(this.officeName))+"</td>"+
      			"<td '"+cell+"' align=center>"+SecurityAPI.encodeHTML(posted_date)+"</td>"+
      			"<td '"+cell+"'>"+SecurityAPI.encodeHTML(this.modified_by)+"</td>"+
				"<td '"+cell+"'>" + date + "</div></td>" +
      			" <td '"+cell+"' align=center>"+modifyLink+"&nbsp;<a href=\"" + deleteLink + "\"><img src=images/delete.png border=0></a></div></td>"+
      			"</tr>"  ;

		return returnStr;

	}


	/**
	 * @return the bu name
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
	 * @return the district name
	 */

	public String getDistName() {
		return distName;
	}

	/**
	 * @param distName the distName to set
	 */

	public void setDistName(String distName) {
		this.distName = distName;
	}

	/**
	 * @return the city name
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
	 * @return the ssc name
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

	
	
}
