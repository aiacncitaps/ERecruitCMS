/*******************************************************************************
 * -----------------------------------------------------------------------------
 * <br>
 * <p><b>Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.</b> 
 * <br>
 * <br>
 * This SOURCE CODE FILE, which has been provided by Quix as part
 * of Quix Creations product for use ONLY by licensed users of the product,
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
 * -----------------------------------------------------------------------------                          
 * 16-June-2015               Hemraj             Address Book file added                       
 * 22-June-2015               Maunish            Modified                     
 * 23-July-2015               Maunish            Modified 
 * 06-August-2015    		  Maunish            Modified 
 ***************************************************************************** */

package com.quix.aia.cn.imo.data.addressbook;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.quix.aia.cn.imo.data.locale.LocaleObject;
import com.quix.aia.cn.imo.utilities.LMSUtil;
import com.quix.aia.cn.imo.utilities.SecurityAPI;

/**
 * <p>
 * AddressBook bean class.
 * </p>
 * 
 * @author khyati
 * @version 1.0
 */
public class AddressBook implements Serializable {

	private Integer addressCode;
	private String iosAddressCode;
	private String agentId;
	private String name;
	private String gender;
	private Date birthDate;

	private String createdBy;
	private Date creationDate;
	private String modifiedBy;
	private Date modificationDate;
	private String recruitmentProgressStatus;

	private String referalSource;
	private String group;
	private String residentialAddress1;
	private String residentialAddress2;
	private String residentialAddress3;
	private String residentialPostalCode;
	private String registeredAddress1;
	private String registeredAddress2;
	private String registeredAddress3;
	private String registeredPostalCode;
	private String fixedLineNO;
	private String mobilePhoneNo;
	private String officePhoneNo;
	private String marritalStatus;
	private String education;
	private String workingYearExperience;
	private String yearlyIncome;
	private String presentWorkCondition;
	private String purchasedAnyInsurance;
	private String outlookApperance;
	private String recommendedRecruitmentScheme;
	private String salesExperience;
	private String remarks;
	private String weibo;
	private String weChat;

	private String idType;
	private Integer age;
	private String birthPlace;
	private String eMailId;
	private String race;
	private String reJoin;
	private String qq;
	private Date lastContactedDate;
	private String recruitedBy;

	private String nric;
	private String talentProfile;
	private String natureOfBusiness;
	private byte[] candidatePhoto;
	private Date interviewCompletionDate;
	private Boolean deleteStatus;

	private String branchCode;
	private String candidateAgentCode;
	private String recruitmentType;
	private Date contractDate;

	private String ccTestResult;
	private Date ccTestResultDate;
	private byte[] qrCode;
	private String newComerSource;

	private Set<CandidateFamilyInfo> candidateFamilyInfos;
	private Set<CandidateWorkExperience> candidateWorkExperiences;
	private Set<CandidateEducation> candidateEducations;
	private Set<CandidateProfessionalCertification> candidateProfessionalCertifications;
	private Set<CandidateESignature> candidateESignatures;
	private Set<CandidateGroup> candidateGroups;
	private Set<CandidateNote> candidateNotes;
	private String finalprogress;
	
	private byte[] presenterImg;
	private Date presenterDate;
	private String interviewResult;
	private String passTime;
	
	public String getInterviewResult() {
		return interviewResult;
	}

	public void setInterviewResult(String interviewResult) {
		this.interviewResult = interviewResult;
	}

	public String getPassTime() {
		return passTime;
	}

	public void setPassTime(String passTime) {
		this.passTime = passTime;
	}

	private String co;

	public byte[] getPresenterImg() {
		return presenterImg;
	}

	public void setPresenterImg(byte[] presenterImg) {
		this.presenterImg = presenterImg;
	}

	public Date getPresenterDate() {
		return presenterDate;
	}

	public void setPresenterDate(Date presenterDate) {
		this.presenterDate = presenterDate;
	}

	/**
	 * <p>
	 * Default Constructor.Setting default values for AddressBook fields.
	 * </p>
	 */
	public AddressBook() {
		addressCode = 0;
		iosAddressCode = null;
		agentId = null;
		name = null;
		gender = null;
		birthDate = null;

		createdBy = null;
		creationDate = null;
		modifiedBy = null;
		modificationDate = null;
		recruitmentProgressStatus = null;

		referalSource = null;
		group = null;
		residentialAddress1 = "";
		residentialAddress2 = "";
		residentialAddress3 = "";
		residentialPostalCode = "";
		registeredAddress1 = "";
		registeredAddress2 = "";
		registeredAddress3 = "";
		registeredPostalCode = "";
		fixedLineNO = null;
		mobilePhoneNo = null;
		officePhoneNo = null;
		marritalStatus = null;
		education = null;
		workingYearExperience = null;
		yearlyIncome = null;
		presentWorkCondition = null;
		purchasedAnyInsurance = null;
		outlookApperance = null;
		recommendedRecruitmentScheme = null;
		salesExperience = null;
		remarks = null;
		weibo = null;
		weChat = null;
		finalprogress="";
		idType = null;
		age = null;
		birthPlace = null;
		eMailId = null;
		race = null;
		reJoin = null;
		qq = null;
		lastContactedDate = null;
		recruitedBy = null;

		nric = null;
		talentProfile = null;
		natureOfBusiness = null;
		candidatePhoto = null;
		interviewCompletionDate = null;
		deleteStatus = false;

		branchCode = null;
		candidateAgentCode = null;
		recruitmentType = null;
		contractDate = null;

		ccTestResult = null;
		ccTestResultDate = null;
		qrCode=null;
		newComerSource=null;
		co = null;

		candidateFamilyInfos = null;
		candidateWorkExperiences = null;
		candidateEducations = null;
		candidateProfessionalCertifications = null;
		candidateESignatures = null;
		candidateGroups = null;
		candidateNotes = null;
	}

	/**
	 * @return the addressCode
	 */
	public Integer getAddressCode() {
		return addressCode;
	}

	/**
	 * @param iosAddressCode
	 *            the iosAddressCode to set
	 */
	public void setAddressCode(Integer addressCode) {
		this.addressCode = addressCode;
	}

	/**
	 * @return the iosAddressCode
	 */
	public String getIosAddressCode() {
		return iosAddressCode;
	}

	/**
	 * @param addressCode
	 *            the addressCode to set
	 */
	public void setIosAddressCode(String iosAddressCode) {
		this.iosAddressCode = iosAddressCode;
	}

	/**
	 * @return the agentId
	 */
	public String getAgentId() {
		return agentId;
	}

	/**
	 * @param agentId
	 *            the agentId to set
	 */
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return null!=gender ? gender.trim() : gender;
	}

	/**
	 * @param gender
	 *            the gender to set
	 */
	public void setGender(String gender) {
		this.gender = null!=gender ? gender.trim() : gender;
	}

	/**
	 * @return the birthDate
	 */
	public Date getBirthDate() {
		return birthDate;
	}

	/**
	 * @param birthDate
	 *            the birthDate to set
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
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
	 * @param creationDate
	 *            the creationDate to set
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
	 * @param modifiedBy
	 *            the modifiedBy to set
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
	 * @param modificationDate
	 *            the modificationDate to set
	 */
	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	/**
	 * @return the recruitmentProgressStatus
	 */
	public String getRecruitmentProgressStatus() {
		return recruitmentProgressStatus;
	}

	/**
	 * @param recruitmentProgressStatus
	 *            the recruitmentProgressStatus to set
	 */
	public void setRecruitmentProgressStatus(String recruitmentProgressStatus) {
		this.recruitmentProgressStatus = recruitmentProgressStatus;
	}

	/**
	 * @return the referalSource
	 */
	public String getReferalSource() {
		return referalSource;
	}

	/**
	 * @param referalSource
	 *            the referalSource to set
	 */
	public void setReferalSource(String referalSource) {
		this.referalSource = referalSource;
	}

	/**
	 * @return the group
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * @param group
	 *            the group to set
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * @return the residentialAddress1
	 */
	public String getResidentialAddress1() {
		return residentialAddress1;
	}

	/**
	 * @param residentialAddress1
	 *            the residentialAddress1 to set
	 */
	public void setResidentialAddress1(String residentialAddress1) {
		this.residentialAddress1 = residentialAddress1;
	}

	/**
	 * @return the residentialAddress2
	 */
	public String getResidentialAddress2() {
		return residentialAddress2;
	}

	/**
	 * @param residentialAddress2
	 *            the residentialAddress2 to set
	 */
	public void setResidentialAddress2(String residentialAddress2) {
		this.residentialAddress2 = residentialAddress2;
	}

	/**
	 * @return the residentialAddress3
	 */
	public String getResidentialAddress3() {
		return residentialAddress3;
	}

	/**
	 * @param residentialAddress3
	 *            the residentialAddress3 to set
	 */
	public void setResidentialAddress3(String residentialAddress3) {
		this.residentialAddress3 = residentialAddress3;
	}

	/**
	 * @return the residentialPostalCode
	 */
	public String getResidentialPostalCode() {
		return residentialPostalCode;
	}

	/**
	 * @param residentialPostalCode
	 *            the residentialPostalCode to set
	 */
	public void setResidentialPostalCode(String residentialPostalCode) {
		this.residentialPostalCode = residentialPostalCode;
	}

	/**
	 * @return the registeredAddress1
	 */
	public String getRegisteredAddress1() {
		return registeredAddress1;
	}

	/**
	 * @param registeredAddress1
	 *            the registeredAddress1 to set
	 */
	public void setRegisteredAddress1(String registeredAddress1) {
		this.registeredAddress1 = registeredAddress1;
	}

	/**
	 * @return the registeredAddress2
	 */
	public String getRegisteredAddress2() {
		return registeredAddress2;
	}

	/**
	 * @param registeredAddress2
	 *            the registeredAddress2 to set
	 */
	public void setRegisteredAddress2(String registeredAddress2) {
		this.registeredAddress2 = registeredAddress2;
	}

	/**
	 * @return the registeredAddress3
	 */
	public String getRegisteredAddress3() {
		return registeredAddress3;
	}

	/**
	 * @param registeredAddress3
	 *            the registeredAddress3 to set
	 */
	public void setRegisteredAddress3(String registeredAddress3) {
		this.registeredAddress3 = registeredAddress3;
	}

	/**
	 * @return the registeredPostalCode
	 */
	public String getRegisteredPostalCode() {
		return registeredPostalCode;
	}

	/**
	 * @param registeredPostalCode
	 *            the registeredPostalCode to set
	 */
	public void setRegisteredPostalCode(String registeredPostalCode) {
		this.registeredPostalCode = registeredPostalCode;
	}

	/**
	 * @return the fixedLineNO
	 */
	public String getFixedLineNO() {
		return fixedLineNO;
	}

	/**
	 * @param fixedLineNO
	 *            the fixedLineNO to set
	 */
	public void setFixedLineNO(String fixedLineNO) {
		this.fixedLineNO = fixedLineNO;
	}

	/**
	 * @return the mobilePhoneNo
	 */
	public String getMobilePhoneNo() {
		return mobilePhoneNo;
	}

	/**
	 * @param mobilePhoneNo
	 *            the mobilePhoneNo to set
	 */
	public void setMobilePhoneNo(String mobilePhoneNo) {
		this.mobilePhoneNo = mobilePhoneNo;
	}

	/**
	 * @return the officePhoneNo
	 */
	public String getOfficePhoneNo() {
		return officePhoneNo;
	}

	/**
	 * @param officePhoneNo
	 *            the officePhoneNo to set
	 */
	public void setOfficePhoneNo(String officePhoneNo) {
		this.officePhoneNo = officePhoneNo;
	}

	/**
	 * @return the marritalStatus
	 */
	public String getMarritalStatus() {
		return marritalStatus;
	}

	/**
	 * @param marritalStatus
	 *            the marritalStatus to set
	 */
	public void setMarritalStatus(String marritalStatus) {
		this.marritalStatus = marritalStatus;
	}

	/**
	 * @return the education
	 */
	public String getEducation() {
		return education;
	}

	/**
	 * @param education
	 *            the education to set
	 */
	public void setEducation(String education) {
		this.education = education;
	}

	/**
	 * @return the workingYearExperience
	 */
	public String getWorkingYearExperience() {
		return workingYearExperience;
	}

	/**
	 * @param workingYearExperience
	 *            the workingYearExperience to set
	 */
	public void setWorkingYearExperience(String workingYearExperience) {
		this.workingYearExperience = workingYearExperience;
	}

	/**
	 * @return the yearlyIncome
	 */
	public String getYearlyIncome() {
		return yearlyIncome;
	}

	/**
	 * @param yearlyIncome
	 *            the yearlyIncome to set
	 */
	public void setYearlyIncome(String yearlyIncome) {
		this.yearlyIncome = yearlyIncome;
	}

	/**
	 * @return the presentWorkCondition
	 */
	public String getPresentWorkCondition() {
		return presentWorkCondition;
	}

	/**
	 * @param presentWorkCondition
	 *            the presentWorkCondition to set
	 */
	public void setPresentWorkCondition(String presentWorkCondition) {
		this.presentWorkCondition = presentWorkCondition;
	}

	/**
	 * @return the purchasedAnyInsurance
	 */
	public String getPurchasedAnyInsurance() {
		return purchasedAnyInsurance;
	}

	/**
	 * @param purchasedAnyInsurance
	 *            the purchasedAnyInsurance to set
	 */
	public void setPurchasedAnyInsurance(String purchasedAnyInsurance) {
		this.purchasedAnyInsurance = purchasedAnyInsurance;
	}

	/**
	 * @return the outlookApperance
	 */
	public String getOutlookApperance() {
		return outlookApperance;
	}

	/**
	 * @param outlookApperance
	 *            the outlookApperance to set
	 */
	public void setOutlookApperance(String outlookApperance) {
		this.outlookApperance = outlookApperance;
	}

	/**
	 * @return the recommendedRecruitmentScheme
	 */
	public String getRecommendedRecruitmentScheme() {
		return recommendedRecruitmentScheme;
	}

	/**
	 * @param recommendedRecruitmentScheme
	 *            the recommendedRecruitmentScheme to set
	 */
	public void setRecommendedRecruitmentScheme(String recommendedRecruitmentScheme) {
		this.recommendedRecruitmentScheme = recommendedRecruitmentScheme;
	}

	/**
	 * @return the salesExperience
	 */
	public String getSalesExperience() {
		return salesExperience;
	}

	/**
	 * @param salesExperience
	 *            the salesExperience to set
	 */
	public void setSalesExperience(String salesExperience) {
		this.salesExperience = salesExperience;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the weibo
	 */
	public String getWeibo() {
		return weibo;
	}

	/**
	 * @param weibo
	 *            the weibo to set
	 */
	public void setWeibo(String weibo) {
		this.weibo = weibo;
	}

	/**
	 * @return the weChat
	 */
	public String getWeChat() {
		return weChat;
	}

	/**
	 * @param weChat
	 *            the weChat to set
	 */
	public void setWeChat(String weChat) {
		this.weChat = weChat;
	}

	/**
	 * @return the idType
	 */
	public String getIdType() {
		return idType;
	}

	/**
	 * @param idType
	 *            the idType to set
	 */
	public void setIdType(String idType) {
		this.idType = idType;
	}

	/**
	 * @return the age
	 */
	public Integer getAge() {
		return age;
	}

	/**
	 * @param age
	 *            the age to set
	 */
	public void setAge(Integer age) {
		this.age = age;
	}

	/**
	 * @return the birthPlace
	 */
	public String getBirthPlace() {
		return birthPlace;
	}

	/**
	 * @param birthPlace
	 *            the birthPlace to set
	 */
	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	/**
	 * @return the eMailId
	 */
	public String geteMailId() {
		return eMailId;
	}

	/**
	 * @param eMailId
	 *            the eMailId to set
	 */
	public void seteMailId(String eMailId) {
		this.eMailId = eMailId;
	}

	/**
	 * @return the race
	 */
	public String getRace() {
		return race;
	}

	/**
	 * @param race
	 *            the race to set
	 */
	public void setRace(String race) {
		this.race = race;
	}

	/**
	 * @return the reJoin
	 */
	public String getReJoin() {
		return reJoin;
	}

	/**
	 * @param reJoin
	 *            the reJoin to set
	 */
	public void setReJoin(String reJoin) {
		this.reJoin = reJoin;
	}

	/**
	 * @return the qq
	 */
	public String getQq() {
		return qq;
	}

	/**
	 * @param qq
	 *            the qq to set
	 */
	public void setQq(String qq) {
		this.qq = qq;
	}

	/**
	 * @return the lastContactedDate
	 */
	public Date getLastContactedDate() {
		return lastContactedDate;
	}

	/**
	 * @param lastContactedDate
	 *            the lastContactedDate to set
	 */
	public void setLastContactedDate(Date lastContactedDate) {
		this.lastContactedDate = lastContactedDate;
	}

	/**
	 * @return the recruitedBy
	 */
	public String getRecruitedBy() {
		return recruitedBy;
	}

	/**
	 * @param recruitedBy
	 *            the recruitedBy to set
	 */
	public void setRecruitedBy(String recruitedBy) {
		this.recruitedBy = recruitedBy;
	}

	/**
	 * @return the nric
	 */
	public String getNric() {
		return nric;
	}

	/**
	 * @param nric
	 *            the nric to set
	 */
	public void setNric(String nric) {
		this.nric = nric;
	}

	/**
	 * @return the talentProfile
	 */
	public String getTalentProfile() {
		return talentProfile;
	}

	/**
	 * @param talentProfile
	 *            the talentProfile to set
	 */
	public void setTalentProfile(String talentProfile) {
		this.talentProfile = talentProfile;
	}

	/**
	 * @return the natureOfBusiness
	 */
	public String getNatureOfBusiness() {
		return natureOfBusiness;
	}

	/**
	 * @param natureOfBusiness
	 *            the natureOfBusiness to set
	 */
	public void setNatureOfBusiness(String natureOfBusiness) {
		this.natureOfBusiness = natureOfBusiness;
	}

	/**
	 * @return the candidatePhoto
	 */
	public byte[] getCandidatePhoto() {
		return candidatePhoto;
	}

	/**
	 * @param candidatePhoto
	 *            the candidatePhoto to set
	 */
	public void setCandidatePhoto(byte[] candidatePhoto) {
		this.candidatePhoto = candidatePhoto;
	}

	/**
	 * @return the deleteStatus
	 */
	public Boolean getDeleteStatus() {
		return deleteStatus;
	}

	/**
	 * @param deleteStatus
	 *            the deleteStatus to set
	 */
	public void setDeleteStatus(Boolean deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	/**
	 * @return the interviewCompletionDate
	 */
	public Date getInterviewCompletionDate() {
		return interviewCompletionDate;
	}

	/**
	 * @param interviewCompletionDate
	 *            the interviewCompletionDate to set
	 */
	public void setInterviewCompletionDate(Date interviewCompletionDate) {
		this.interviewCompletionDate = interviewCompletionDate;
	}

	/**
	 * @param branchCode
	 *            the branchCode to set
	 */
	public String getBranchCode() {
		return branchCode;
	}

	/**
	 * @return the branchCode
	 */
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	/**
	 * @param candidateAgentCode
	 *            the candidateAgentCode to set
	 */
	public String getCandidateAgentCode() {
		return candidateAgentCode;
	}

	/**
	 * @return the candidateAgentCode
	 */
	public void setCandidateAgentCode(String candidateAgentCode) {
		this.candidateAgentCode = candidateAgentCode;
	}

	/**
	 * @param recruitmentType
	 *            the recruitmentType to set
	 */
	public String getRecruitmentType() {
		return recruitmentType;
	}

	/**
	 * @return the recruitmentType
	 */
	public void setRecruitmentType(String recruitmentType) {
		this.recruitmentType = recruitmentType;
	}

	/**
	 * @param contractDate
	 *            the contractDate to set
	 */
	public Date getContractDate() {
		return contractDate;
	}

	/**
	 * @return the contractDate
	 */
	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}

	/**
	 * @return the candidateFamilyInfos
	 */
	public Set<CandidateFamilyInfo> getCandidateFamilyInfos() {
		return candidateFamilyInfos;
	}

	/**
	 * @return the ccTestResult
	 */
	public String getCcTestResult() {
		return ccTestResult;
	}

	/**
	 * @param ccTestResult
	 *            the ccTestResult to set
	 */
	public void setCcTestResult(String ccTestResult) {
		this.ccTestResult = ccTestResult;
	}

	/**
	 * @return the ccTestResultDate
	 */
	public Date getCcTestResultDate() {
		return ccTestResultDate;
	}

	/**
	 * @param ccTestResultDate
	 *            the ccTestResultDate to set
	 */
	public void setCcTestResultDate(Date ccTestResultDate) {
		this.ccTestResultDate = ccTestResultDate;
	}
	
	/**
	 * @return the qrCode
	 */
	public byte[] getQrCode() {
		return qrCode;
	}

	/**
	 * @param qrCode the qrCode to set
	 */
	public void setQrCode(byte[] qrCode) {
		this.qrCode = qrCode;
	}
	
	/**
	 * @return the newComerSource
	 */
	public String getNewComerSource() {
		return newComerSource;
	}

	/**
	 * @param newComerSource the newComerSource to set
	 */
	public void setNewComerSource(String newComerSource) {
		this.newComerSource = newComerSource;
	}
	
	/**
	 * @return the co
	 */
	public String getCo() {
		return co;
	}

	/**
	 * @param co the co to set
	 */
	public void setCo(String co) {
		this.co = co;
	}

	/**
	 * @param candidateFamilyInfos
	 *            the candidateFamilyInfos to set
	 */
	public void setCandidateFamilyInfos(Set<CandidateFamilyInfo> candidateFamilyInfos) {
		this.candidateFamilyInfos = candidateFamilyInfos;
	}

	/**
	 * @return the candidateWorkExperiences
	 */
	public Set<CandidateWorkExperience> getCandidateWorkExperiences() {
		return candidateWorkExperiences;
	}

	/**
	 * @param candidateWorkExperiences
	 *            the candidateWorkExperiences to set
	 */
	public void setCandidateWorkExperiences(Set<CandidateWorkExperience> candidateWorkExperiences) {
		this.candidateWorkExperiences = candidateWorkExperiences;
	}

	/**
	 * @return the candidateEducations
	 */
	public Set<CandidateEducation> getCandidateEducations() {
		return candidateEducations;
	}

	/**
	 * @param candidateEducations
	 *            the candidateEducations to set
	 */
	public void setCandidateEducations(Set<CandidateEducation> candidateEducations) {
		this.candidateEducations = candidateEducations;
	}

	/**
	 * @return the candidateProfessionalCertifications
	 */
	public Set<CandidateProfessionalCertification> getCandidateProfessionalCertifications() {
		return candidateProfessionalCertifications;
	}

	/**
	 * @param candidateProfessionalCertifications
	 *            the candidateProfessionalCertifications to set
	 */
	public void setCandidateProfessionalCertifications(
			Set<CandidateProfessionalCertification> candidateProfessionalCertifications) {
		this.candidateProfessionalCertifications = candidateProfessionalCertifications;
	}

	/**
	 * @return the candidateESignatures
	 */
	public Set<CandidateESignature> getCandidateESignatures() {
		return candidateESignatures;
	}

	/**
	 * @param candidateESignatures
	 *            the candidateESignatures to set
	 */
	public void setCandidateESignatures(Set<CandidateESignature> candidateESignatures) {
		this.candidateESignatures = candidateESignatures;
	}

	/**
	 * @return the candidateGroups
	 */
	public Set<CandidateGroup> getCandidateGroups() {
		return candidateGroups;
	}

	/**
	 * @param candidateGroups
	 *            the candidateGroups to set
	 */
	public void setCandidateGroups(Set<CandidateGroup> candidateGroups) {
		this.candidateGroups = candidateGroups;
	}

	/**
	 * @return the candidateNotes
	 */
	public Set<CandidateNote> getCandidateNotes() {
		return candidateNotes;
	}

	/**
	 * @param candidateNotes
	 *            the candidateNotes to set
	 */
	public void setCandidateNotes(Set<CandidateNote> candidateNotes) {
		this.candidateNotes = candidateNotes;
	}

	@Override
	public void finalize() {
		addressCode = null;
		agentId = null;
		iosAddressCode = null;
		name = null;
		birthDate = null;

		createdBy = null;
		creationDate = null;
		modifiedBy = null;
		modificationDate = null;
		recruitmentProgressStatus = null;

		referalSource = null;
		group = null;
		residentialAddress1 = null;
		residentialAddress2 = null;
		residentialAddress3 = null;
		residentialPostalCode = null;
		registeredAddress1 = null;
		registeredAddress2 = null;
		registeredAddress3 = null;
		registeredPostalCode = null;
		fixedLineNO = null;
		mobilePhoneNo = null;
		officePhoneNo = null;
		marritalStatus = null;
		education = null;
		workingYearExperience = null;
		yearlyIncome = null;
		presentWorkCondition = null;
		purchasedAnyInsurance = null;
		outlookApperance = null;
		recommendedRecruitmentScheme = null;
		salesExperience = null;
		remarks = null;
		weibo = null;
		weChat = null;

		idType = null;
		age = null;
		birthPlace = null;
		eMailId = null;
		race = null;
		reJoin = null;
		qq = null;
		lastContactedDate = null;
		recruitedBy = null;

		nric = null;
		talentProfile = null;
		natureOfBusiness = null;
		candidatePhoto = null;
		interviewCompletionDate = null;
		deleteStatus = null;

		branchCode = null;
		candidateAgentCode = null;
		recruitmentType = null;
		contractDate = null;
		qrCode=null;
		newComerSource=null;
		co = null;

		candidateFamilyInfos.clear();
		candidateFamilyInfos = null;
		candidateWorkExperiences.clear();
		candidateWorkExperiences = null;
		candidateEducations.clear();
		candidateEducations = null;
		candidateProfessionalCertifications.clear();
		candidateProfessionalCertifications = null;
		candidateESignatures.clear();
		candidateESignatures = null;
		candidateGroups.clear();
		candidateGroups = null;
		candidateNotes.clear();
		candidateNotes = null;
	}

	@Override
	public String toString() {

		return ("Address Code-> " + addressCode + " Agent Id -> " + agentId);
	}

	public String getGetCCTestResultRow(LocaleObject localeObj) {
		String returnStr = "";
		
		
		
		String ur = "", no = "", pr = "", se = "";
		if (ccTestResult == null)
			se = "selected";
		else {
			if (this.ccTestResult.equals("Urgent"))
				ur = "selected";
			else if (this.ccTestResult.equals("Normal"))
				no = "selected";
			else if (this.ccTestResult.equals("Prudent"))
				pr = "selected";
			else
				se = "selected";
		}
		String completeStrCombo = "<select  id=" + this.addressCode + "_ccStatus  name=" + this.addressCode
				+ "_ccStatus style='border:1px solid #CCCCCC;padding:5px 5px;height:30px;width:100%;' >"
				+ "<option value='' " + se + ">"+localeObj.getTranslatedText("Select")+"</option>" + "<option value='Urgent' " + ur + ">"+localeObj.getTranslatedText("Urgent")+"</option>"
				+ "<option value='Normal' " + no + ">"+localeObj.getTranslatedText("Normal")+"</option>" + "<option value='Prudent' " + pr
				+ ">"+localeObj.getTranslatedText("Prudent")+"</option>" + "</select>";

		String result = "";
		if (ccTestResultDate != null)
			result = LMSUtil.convertDateToString(ccTestResultDate);

		String interviewResultText = "<input type=text class='text datepicker' maxlength=10 size=10 id="
				+ this.addressCode + "_ccResultDate  name=" + this.addressCode + "_ccResultDate value='"
				+ SecurityAPI.encodeHTML(result) + "' />";

		returnStr = "<tr> " + "<td><div style='word-break: break-all;word-wrap: break-word;white-space: pre-line;'>"
				+ SecurityAPI.encodeHTML(this.name) + "</div></td>"
				+ "<td style='padding:5px 5px' ><div style='text-align:center'>" + completeStrCombo + "</div></td>"
				+ "<td><div style='text-align:center'>" + interviewResultText + "</div></td>" + "</tr>";
		return returnStr;
	}

	public String getFinalprogress() {
		return finalprogress;
	}

	public void setFinalprogress(String finalprogress) {
		this.finalprogress = finalprogress;
	}
}