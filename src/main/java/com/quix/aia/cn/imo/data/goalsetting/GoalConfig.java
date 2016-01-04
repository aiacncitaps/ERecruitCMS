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
* 10-Jul-2015       Hemraj            pojo created for goal configuration
****************************************** *********************************** */
package com.quix.aia.cn.imo.data.goalsetting;
import java.util.Date;
/**
 * <p>Goal Configuration bean class.</p>
 * 
 * @author Hemraj
 * @version 1.0
 *
 */
public class GoalConfig {
	private int goal_config_id;
	private int potentialCandidate;
	private int eopRegistration;
	private int firstInterview;
	private int eopAttendance;
	private int ccAssessment;
	private int companyInterview;
	private int passALE;
	private int attendedTraining;
	private int signContract;
	private String created_by;
	private Date created_date;
	
	/**
	  * <p>Default Constructor.Setting default values for goalconfiguration fields.</p>
	  */
	public GoalConfig()
	{
		goal_config_id=0;
		potentialCandidate=0;
		eopRegistration=0;
		firstInterview=0;
		eopAttendance=0;
		ccAssessment=0;
		companyInterview=0;
		passALE=0;
		attendedTraining=0;
		signContract = 0;
		created_by="";
		created_date=null;
	}

	/**
	 * @return the goal_config_id
	 */
	public int getGoal_config_id() {
		return goal_config_id;
	}

	/**
	 * @param goal_config_id the goal_config_id to set
	 */
	public void setGoal_config_id(int goal_config_id) {
		this.goal_config_id = goal_config_id;
	}

	
	/**
	 * @return the potentialCandidate
	 */
	public int getPotentialCandidate() {
		return potentialCandidate;
	}

	/**
	 * @param potentialCandidate the potentialCandidate to set
	 */
	public void setPotentialCandidate(int potentialCandidate) {
		this.potentialCandidate = potentialCandidate;
	}

	/**
	 * @return the eopRegistration
	 */
	public int getEopRegistration() {
		return eopRegistration;
	}

	/**
	 * @return the signContract
	 */
	public int getSignContract() {
		return signContract;
	}

	/**
	 * @param signContract the signContract to set
	 */
	public void setSignContract(int signContract) {
		this.signContract = signContract;
	}

	/**
	 * @param eopRegistration the eopRegistration to set
	 */
	public void setEopRegistration(int eopRegistration) {
		this.eopRegistration = eopRegistration;
	}

	/**
	 * @return the firstInterview
	 */
	public int getFirstInterview() {
		return firstInterview;
	}

	/**
	 * @param firstInterview the firstInterview to set
	 */
	public void setFirstInterview(int firstInterview) {
		this.firstInterview = firstInterview;
	}

	/**
	 * @return the eopAttendance
	 */
	public int getEopAttendance() {
		return eopAttendance;
	}

	/**
	 * @param eopAttendance the eopAttendance to set
	 */
	public void setEopAttendance(int eopAttendance) {
		this.eopAttendance = eopAttendance;
	}

	/**
	 * @return the ccAssessment
	 */
	public int getCcAssessment() {
		return ccAssessment;
	}

	/**
	 * @param ccAssessment the ccAssessment to set
	 */
	public void setCcAssessment(int ccAssessment) {
		this.ccAssessment = ccAssessment;
	}

	/**
	 * @return the companyInterview
	 */
	public int getCompanyInterview() {
		return companyInterview;
	}

	/**
	 * @param companyInterview the companyInterview to set
	 */
	public void setCompanyInterview(int companyInterview) {
		this.companyInterview = companyInterview;
	}

	/**
	 * @return the passALE
	 */
	public int getPassALE() {
		return passALE;
	}

	/**
	 * @param passALE the passALE to set
	 */
	public void setPassALE(int passALE) {
		this.passALE = passALE;
	}

	/**
	 * @return the attendedTraining
	 */
	public int getAttendedTraining() {
		return attendedTraining;
	}

	/**
	 * @param attendedTraining the attendedTraining to set
	 */
	public void setAttendedTraining(int attendedTraining) {
		this.attendedTraining = attendedTraining;
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

}
