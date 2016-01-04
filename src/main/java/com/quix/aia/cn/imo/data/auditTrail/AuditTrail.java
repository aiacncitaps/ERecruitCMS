// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AuditTrail.java
/*******************************************************************************
* -----------------------------------------------------------------------------
* <br>
* <p><b>Copyright (c) 2014 Quix Creation Pte. Ltd. All Rights Reserved.</b> 
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
* 23-March-2015    Jay Gagnani         added code for access control matrix.
* 22-June-2015 	   Maunish Soni        Modified.
***************************************************************************** */

package com.quix.aia.cn.imo.data.auditTrail;

import java.util.Date;


public class AuditTrail
{

    public AuditTrail()
    {
        userId = "";
        moduleName = "";
        action = "";
        time = "";
        userName = "";
        itemName = "";
    }

    public AuditTrail(String userId, String moduleName, String action, String itemName)
    {
        this.userId = userId;
        this.moduleName = moduleName;
        this.action = action;
        this.itemName = itemName;
    }


    public String getAction()
    {
        return action;
    }

    public String getModuleName()
    {
        return moduleName;
    }


    public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setAction(String string)
    {
        action = string;
    }

    public void setModuleName(String string)
    {
        moduleName = string;
    }


    

    public String getTime()
    {
        return time;
    }

  

    public void setTime(String string)
    {
        time = string;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String string)
    {
        userName = string;
    }

    public static String getAuditTrailHdr()
    {
        return "<tr bgcolor=#FDEDED class=text><td width=9% background=/images/head.jpg><div align=center><b>User ID</b></div></td><td width=10% background=/images/head.jpg><div align=center><b>Date</b></div></td><td width=10% background=/images/head.jpg><div align=center><b>Time</b></div></td><td width=10% background=/images/head.jpg><div align=center><b>Module Name</b></div></td><td width=10% background=/images/head.jpg><div align=center><b>Item Name</b></div></td><td width=10% background=/images/head.jpg><div align=center><b>Action</b></div></td></tr>";
    }

    public String getAuditTrailRow()
    {
        return "<tr><td>" + userName + "</td>" + "<td>" + date + "</td>" + "<td>" + time + "</td>" + "<td>" + moduleName + "</td>" + "<td>" + itemName + "</td>" + "<td>" + action + "</td>" + "</tr>";
    }

    public String getItemName()
    {
        return itemName;
    }

    public void setItemName(String string)
    {
        itemName = string;
    }
    
    public int getLog_code() {
		return log_code;
	}

	public void setLog_code(int log_code) {
		this.log_code = log_code;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	private int log_code;
    private String userId;
    private String moduleName;
    private String action;
    private String itemName;
    private String time;
    private Date date;
    private String userName;
    public static String FUNCTION_CREATE = "CREATED";
    public static String FUNCTION_REST = "RESTCALL";
    public static String FUNCTION_FAIL = "RESTCALLFAILED";
    public static String FUNCTION_LOGIN = "USER LOGIN";
    public static String FUNCTION_ACCESS = "ACCESS";
    public static String FUNCTION_LOGOUT = "LOGOUT";
    public static String FUNCTION_UPDATE = "UPDATED";
    public static String FUNCTION_DELETE = "DELETED";
    public static String FUNCTION_DOWNLOAD= "DOWNLOADED";
    public static String FUNCTION_SUCCESS= "SUCCESS";
    public static String FUNCTION_FAILED= "FAILED";
    
  
    public static String MODULE_USER = "USER";
    public static String MODULE = "MODULE";
    public static String POST = "POST UPDATE";
    public static String SAML = "SAML";
    public static String SECURITY_POLICY_MODULE = "SECURITY POLICY";
    public static String MODULE_ANNOUNCEMENT = "ANNOUNCEMENT";
    public static String MODULE_APPLICATIONFORM = "APPLICATIONFORM";
    public static String MODULE_BU = "BU";
    public static String MODULE_GOAL_CONFIG = "Goal Config";
    
    public static String MODULE_DEPARTMENT = "DEPARTMENT";
    public static String MODULE_CHANNEL = "CHANNEL";
    public static String MODULE_PRESENTER_CATEGORY = "PRESENTER_CATEGORY";
    public static String MODULE_FESTIVE_CATEGORY = "FESTIVE_CATEGORY";
    public static String MODULE_SSC = "SSC";
    public static String MODULE_OFFICE = "OFFICE";
    public static String MODULE_HOLIDAY = "HOLIDAY";
    public static String MODULE_CITY = "CITY";
    public static String MODULE_BRANCH = "BRANCH";
    public static String MODULE_DISTRICT = "DISTRICT";
    public static String MODULE_PRESENTER = "PRESENTER";
    public static String MODULE_CITY_SSC = "CITY_SSC";
    public static String MODULE_DISTRICT_CITY = "DISTRICT_CITY";
    public static String MODULE_DISTRICT_CITY_SSC = "DISTRICT_CITY_SSC";
    public static String MODULE_BU_DISTRICT = "BU_DISTRICT";
    public static String MODULE_BU_USER = "BU_USER";
    public static String MODULE_DISTRICT_USER = "DISTRICT_USER";
    public static String MODULE_CITY_USER = "CITY_USER";
    public static String MODULE_SSC_USER = "SSC_USER";
    public static String MODULE_BU_ANNOUNCEMENT = "BU_ANNOUNCEMENT";
    public static String MODULE_BU_SCHEDULE_EVENT = "BU_SCHEDULE_EVENT";
    public static String MODULE_CITY_SCHEDULE_EVENT = "CITY_SCHEDULE_EVENT";
    public static String MODULE_SSC_SCHEDULE_EVENT = "SSC_SCHEDULE_EVENT";
    public static String MODULE_DISTRICT_SCHEDULE_EVENT = "DISTRICT_SCHEDULE_EVENT";
    public static String MODULE_DISTRICT_INTERVIEW = "DISTRICT_INTERVIEW";
    public static String MODULE_BU_INTERVIEW = "BU_INTERVIEW";
    public static String MODULE_CITY_INTERVIEW = "CITY_INTERVIEW";
    public static String MODULE_SSC_INTERVIEW = "SSC_INTERVIEW";
    public static String MODULE_DISTRICT_ANNOUNCEMENT = "DISTRICT_ANNOUNCEMENT";
    public static String MODULE_CITY_ANNOUNCEMENT = "CITY_ANNOUNCEMENT";
    public static String MODULE_SSC_ANNOUNCEMENT = "SSC_ANNOUNCEMENT";
    public static String MODULE_BU_HOLIDAY = "BU_HOLIDAY";
    public static String MODULE_BU_PRESENTER = "BU_PRESENTER";
    public static String MODULE_DISTRICT_PRESENTER = "DISTRICT_PRESENTER";
    public static String MODULE_BRANCH_PRESENTER = "BRANCH_PRESENTER";
    public static String MODULE_CITY_PRESENTER = "CITY_PRESENTER";
    public static String MODULE_SSC_PRESENTER = "SSC_PRESENTER";
    
    public static String MODULE_DISTRICT_HOLIDAY = "DISTRICT_HOLIDAY";
    public static String MODULE_CITY_HOLIDAY = "CITY_HOLIDAY";
    public static String MODULE_SSC_HOLIDAY = "SSC_HOLIDAY";
    public static String MODULE_BU_DISTRICT_CITY = "BU_DISTRICT_CITY";
    public static String MODULE_BRANCH_DISTRICT_CITY = "BRANCH_DISTRICT_CITY";
    public static String MODULE_BU_DISTRICT_CITY_SSC = "BU_DISTRICT_CITY_SSC";
    public static String MODULE_INTERVIEW = "INTERVIEW	";
    public static String MODULE_EOP = "EOP";
    public static String MODULE_E_GREETING = "E_GREETING";
    public static String MODULE_PRESENTERCategory_PRESENTER = "PRESENTER-CATEGORY_PRESENTER";
    public static String MODULE_DEPARTMENT_USER = "DEPARTMENT_USER";
    public static String MODULE_CHANNEL_USER = "CHANNEL_USER";
    public static String MODULE_EOP_REG = "EOP_REG";
    public static String MODULE_INTERVIEW_REG = "INTERVIEW_REG";
    public static String MODULE_INSERT_CC_TEST = "INSERT_CC_TEST";
    public static String MODULE_ADDRESS_BOOK = "ADDRESS_BOOK";
    public static String MODULE_TRAINING = "TRAINING";
}
