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
package com.quix.aia.cn.imo.data.version;

import java.util.Date;


public class Version {
	private int ver_code;
	private String ver_no;
	private Date ver_date;

	public Version(){
		ver_code=0;
		ver_no = "";
		ver_date = null;
	}

	public int getVer_code() {
		return ver_code;
	}

	public void setVer_code(int ver_code) {
		this.ver_code = ver_code;
	}

	public String getVer_no() {
		return ver_no;
	}

	public void setVer_no(String ver_no) {
		this.ver_no = ver_no;
	}

	public Date getVer_date() {
		return ver_date;
	}

	public void setVer_date(Date ver_date) {
		this.ver_date = ver_date;
	}
}
