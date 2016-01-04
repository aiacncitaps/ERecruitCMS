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
* 08-June-2015       Nibedita           copyright,comments added
****************************************** *********************************** */
package com.quix.aia.cn.imo.data.event;
/**
 * <p>SpecialGroup bean class.</p>
 * 
 * @author Nibedita
 * @version 1.0
 *
 */
public class SpecialGroup {
	private int code;
	private int  eventCode;
	private String agencyUnit;
	 /**
	  * <p>Default Constructor.Setting default values for SpecialGroup fields.</p>
	  */
	public SpecialGroup(){
		code = 0;
		eventCode = 0;
		agencyUnit = "";
	}
	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}
	/**
	 * @return the eventCode
	 */
	public int getEventCode() {
		return eventCode;
	}
	/**
	 * @param eventCode the eventCode to set
	 */
	public void setEventCode(int eventCode) {
		this.eventCode = eventCode;
	}
	/**
	 * @return the agencyUnit
	 */
	public String getAgencyUnit() {
		return agencyUnit;
	}
	/**
	 * @param agencyUnit the agencyUnit to set
	 */
	public void setAgencyUnit(String agencyUnit) {
		this.agencyUnit = agencyUnit;
	}
	
}
