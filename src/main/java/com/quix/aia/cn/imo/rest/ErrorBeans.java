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

import java.util.ArrayList;

/**
 * <p>Error bean class. For Rest Service</p>
 * 
 * @author Jay
 * @version 1.0
 *
 */
public class ErrorBeans {
	ArrayList<MsgBeans> error = new ArrayList<MsgBeans>();

	/**
	 * @return the Error list
	 */
	public ArrayList<MsgBeans> getError() {
		return error;
	}

	/**
	 * @param error the List of error to set
	 */
	public void setError(ArrayList<MsgBeans> error) {
		this.error = error;
	}
	
	
}
