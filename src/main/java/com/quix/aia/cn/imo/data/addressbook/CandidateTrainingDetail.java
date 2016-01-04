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
 * 24-July-2015               Maunish             File added 
 ***************************************************************************** */

package com.quix.aia.cn.imo.data.addressbook;

import java.util.Date;

/**
 * <p>
 * CandidateTrainingResult bean class.
 * </p>
 * 
 * @author Maunish
 * @version 1.0
 */
public class CandidateTrainingDetail{

	private Integer detailCode;
	private String courseCode;
	private String courseName;
	private String curriculamCode;
	private String courseType;
	private Date startDate;
	private Date creationDate;

	/**
	 * <p>
	 * Default Constructor.Setting default values for TrainingResult fields.
	 * </p>
	 */
	public CandidateTrainingDetail() {
		detailCode = null;
		courseCode = null;
		courseName = null;
		curriculamCode = null;
		courseType = null;
		startDate = null;
		creationDate = null;
	}

	/**
	 * @return the detailCode
	 */
	public Integer getDetailCode() {
		return detailCode;
	}

	/**
	 * @param detailCode
	 *            the detailCode to set
	 */
	public void setDetailCode(Integer detailCode) {
		this.detailCode = detailCode;
	}

	/**
	 * @return the courseCode
	 */
	public String getCourseCode() {
		return courseCode;
	}

	/**
	 * @param courseCode
	 *            the courseCode to set
	 */
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	/**
	 * @return the courseName
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * @param courseName
	 *            the courseName to set
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * @return the curriculamCode
	 */
	public String getCurriculamCode() {
		return curriculamCode;
	}

	/**
	 * @param curriculamCode
	 *            the curriculamCode to set
	 */
	public void setCurriculamCode(String curriculamCode) {
		this.curriculamCode = curriculamCode;
	}

	/**
	 * @return the courseType
	 */
	public String getCourseType() {
		return courseType;
	}

	/**
	 * @param courseType
	 *            the courseType to set
	 */
	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
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
	
	@Override
	public void finalize() {
		detailCode = null;
		courseCode = null;
		courseName = null;
		curriculamCode = null;
		courseType = null;
		startDate = null;
		creationDate = null;
	}
}