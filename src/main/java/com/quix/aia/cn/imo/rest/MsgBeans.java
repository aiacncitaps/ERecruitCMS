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
package com.quix.aia.cn.imo.rest;

/**
 * <p>Message bean class.</p>
 * 
 * @author Jay
 * @version 1.0
 *
 */
public class MsgBeans {
	String code;
	String massage ;

	/**
	 * @return the msg code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the message code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the message
	 */
	public String getMassage() {
		return massage;
	}

	/**
	 * @param massage the message Name to set
	 */
	public void setMassage(String massage) {
		this.massage = massage;
	}

	
		
}
