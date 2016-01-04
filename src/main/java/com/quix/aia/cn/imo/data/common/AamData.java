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
package com.quix.aia.cn.imo.data.common;

import java.util.Date;

/**
 * @author Maunish Soni <maunish.soni@quix.com.sg>
 */
public class AamData implements java.io.Serializable {

   // private Integer id;
    private String agentCode;
    private String agentName;
    private String leaderCode;
    private Integer buCode;
    private String bu;
    private Integer districtCode;
    private String district;
    private Integer branchCode;
    private String branch;
    private Integer cityCode;
    private String city;
    private Integer sscCode;
    private String ssc;
    private String agentType;
    private String agentStatus;
    private String teamCode;
    private String teamName;
    private String officeCode;
    private String officeName;
    private String agentId;
    private String agentSex;
    private Date contractDate;
    private String level;
    private String birthDate;
    private String branchFulleName;
    private String tel;
    private String levelName;

    public AamData() {
		//id = null;
		agentCode = null;
		agentName = null;
		leaderCode = null;
		buCode = 0;
		bu = null;
		districtCode = 0;
		district = null;
		branchCode = 0;
		branch = null;
		cityCode = 0;
		city = null;
		sscCode = 0;
		ssc = null;
		agentType = null;
		agentStatus = null;
		teamCode = null;
		teamName = null;
		officeCode = "0";
		officeName = null;
		agentId = null;
		agentSex = null;
		contractDate = null;
		level = null;
		birthDate = null;
		branchFulleName= null;
	}

	/**
	 * @return the id
	 *//*
	public Integer getId() {
		return id;
	}

	*//**
	 * @param id the id to set
	 *//*
	public void setId(Integer id) {
		this.id = id;
	}
*/
	/**
	 * @return the agentCode
	 */
	public String getAgentCode() {
		return agentCode;
	}

	/**
	 * @param agentCode the agentCode to set
	 */
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	/**
	 * @return the agentName
	 */
	public String getAgentName() {
		return agentName;
	}

	/**
	 * @param agentName the agentName to set
	 */
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	/**
	 * @return the leaderCode
	 */
	public String getLeaderCode() {
		return leaderCode;
	}

	/**
	 * @param leaderCode the leaderCode to set
	 */
	public void setLeaderCode(String leaderCode) {
		this.leaderCode = leaderCode;
	}

	/**
	 * @return the buCode
	 */
	public Integer getBuCode() {
		return buCode;
	}

	/**
	 * @param buCode the buCode to set
	 */
	public void setBuCode(Integer buCode) {
		this.buCode = buCode;
	}

	/**
	 * @return the bu
	 */
	public String getBu() {
		return bu;
	}

	/**
	 * @param bu the bu to set
	 */
	public void setBu(String bu) {
		this.bu = bu;
	}

	/**
	 * @return the districtCode
	 */
	public Integer getDistrictCode() {
		return districtCode;
	}

	/**
	 * @param districtCode the districtCode to set
	 */
	public void setDistrictCode(Integer districtCode) {
		this.districtCode = districtCode;
	}

	/**
	 * @return the district
	 */
	public String getDistrict() {
		return district;
	}

	/**
	 * @param district the district to set
	 */
	public void setDistrict(String district) {
		this.district = district;
	}

	/**
	 * @return the branchCode
	 */
	public Integer getBranchCode() {
		return branchCode;
	}

	/**
	 * @param branchCode the branchCode to set
	 */
	public void setBranchCode(Integer branchCode) {
		this.branchCode = branchCode;
	}

	/**
	 * @return the branch
	 */
	public String getBranch() {
		return branch;
	}

	/**
	 * @param branch the branch to set
	 */
	public void setBranch(String branch) {
		this.branch = branch;
	}

	/**
	 * @return the cityCode
	 */
	public Integer getCityCode() {
		return cityCode;
	}

	/**
	 * @param cityCode the cityCode to set
	 */
	public void setCityCode(Integer cityCode) {
		this.cityCode = cityCode;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the sscCode
	 */
	public Integer getSscCode() {
		return sscCode;
	}

	/**
	 * @param sscCode the sscCode to set
	 */
	public void setSscCode(Integer sscCode) {
		this.sscCode = sscCode;
	}

	/**
	 * @return the ssc
	 */
	public String getSsc() {
		return ssc;
	}

	/**
	 * @param ssc the ssc to set
	 */
	public void setSsc(String ssc) {
		this.ssc = ssc;
	}

	/**
	 * @return the agentType
	 */
	public String getAgentType() {
		return agentType;
	}

	/**
	 * @param agentType the agentType to set
	 */
	public void setAgentType(String agentType) {
		this.agentType = agentType;
	}

	/**
	 * @return the agentStatus
	 */
	public String getAgentStatus() {
		return agentStatus;
	}

	/**
	 * @param agentStatus the agentStatus to set
	 */
	public void setAgentStatus(String agentStatus) {
		this.agentStatus = agentStatus;
	}

	/**
	 * @return the teamCode
	 */
	public String getTeamCode() {
		return teamCode;
	}

	/**
	 * @param teamCode the teamCode to set
	 */
	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}

	/**
	 * @return the teamName
	 */
	public String getTeamName() {
		return teamName;
	}

	/**
	 * @param teamName the teamName to set
	 */
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	/**
	 * @return the officeCode
	 */
	public String getOfficeCode() {
		return officeCode;
	}

	/**
	 * @param officeCode the officeCode to set
	 */
	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

	/**
	 * @return the officeName
	 */
	public String getOfficeName() {
		return officeName;
	}

	/**
	 * @param officeName the officeName to set
	 */
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	/**
	 * @return the agentId
	 */
	public String getAgentId() {
		return agentId;
	}

	/**
	 * @param agentId the agentId to set
	 */
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	/**
	 * @return the agentSex
	 */
	public String getAgentSex() {
		return agentSex;
	}

	/**
	 * @param agentSex the agentSex to set
	 */
	public void setAgentSex(String agentSex) {
		this.agentSex = agentSex;
	}

	/**
	 * @return the contractDate
	 */
	public Date getContractDate() {
		return contractDate;
	}

	/**
	 * @param contractDate the contractDate to set
	 */
	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}

	/**
	 * @return the level
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(String level) {
		this.level = level;
	}

	/**
	 * @return the birthDate
	 */
	public String getBirthDate() {
		return birthDate;
	}

	/**
	 * @param birthDate the birthDate to set
	 */
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * @return the branchFulleName
	 */
	public String getBranchFulleName() {
		return branchFulleName;
	}

	/**
	 * @param branchFulleName the branchFulleName to set
	 */
	public void setBranchFulleName(String branchFulleName) {
		this.branchFulleName = branchFulleName;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	
	

}
