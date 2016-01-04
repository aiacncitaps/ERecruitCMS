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
* 30-June-2015       Jay            copyright and security code added
****************************************** *********************************** */
package com.quix.aia.cn.imo.data.announcement;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>Announcement bean class.</p>
 * 
 * @author Nibedita
 * @version 1.0
 *
 */
public class Emp {
	private int emp_code;
	private String subject;
	
	 /**
	  * <p>Default Constructor.Setting default values for announcement fields.</p>
	  */
	public Emp(){
		
		emp_code = 0;
		subject = "";
		
		
	}

	/**
	 * @return the annoucement_code
	 */
	public int getEmp_code() {
		return emp_code;
	}

	/**
	 * @param annoucement_code the annoucement_code to set
	 */
	public void setEmp_code(int emp_code) {
		this.emp_code = emp_code;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the publishedDate
	 */
	
}
