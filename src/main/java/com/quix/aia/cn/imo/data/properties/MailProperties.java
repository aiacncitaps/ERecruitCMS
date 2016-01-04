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
 * 14-August-2015               Maunish             MailProperties file added 
 ***************************************************************************** */

package com.quix.aia.cn.imo.data.properties;

/**
 * <p>
 * MailProperties bean class.
 * </p>
 * 
 * @author Maunish
 * @version 1.0
 */
public class MailProperties {

	private Integer mailCode;
	private String mailKey;
	private String mailValue;

	/**
	 * <p>
	 * Default Constructor.Setting default values for CandidateEducationId
	 * fields.
	 * </p>
	 */
	public MailProperties() {
		mailCode = null;
		mailKey = null;
		mailValue = null;
	}
	
	/**
	 * @return the mailCode
	 */
	public Integer getMailCode() {
		return mailCode;
	}



	/**
	 * @param mailCode the mailCode to set
	 */
	public void setMailCode(Integer mailCode) {
		this.mailCode = mailCode;
	}



	/**
	 * @return the mailKey
	 */
	public String getMailKey() {
		return mailKey;
	}



	/**
	 * @param mailKey the mailKey to set
	 */
	public void setMailKey(String mailKey) {
		this.mailKey = mailKey;
	}



	/**
	 * @return the mailValue
	 */
	public String getMailValue() {
		return mailValue;
	}



	/**
	 * @param mailValue the mailValue to set
	 */
	public void setMailValue(String mailValue) {
		this.mailValue = mailValue;
	}



	/**
	 * <p>
	 * finalize method for garbage collection
	 * </p>
	 */
	@Override
	public void finalize() {
		mailCode = null;
		mailKey = null;
		mailValue = null;
	}

}
