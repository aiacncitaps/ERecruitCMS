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
package com.quix.aia.cn.imo.data.locale;

import java.util.Map;

public class LocaleObject {
	private String key;
	private Map objMap;
	
	public LocaleObject()
	{
		
	}
	public LocaleObject(String key,Map objMap )
	{
		this.key= key;
		this.objMap = objMap;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Map getObjMap() {
		return objMap;
	}

	public void setObjMap(Map objMap) {
		this.objMap = objMap;
	}
	public String getTranslatedText(String key)
	{
		String translatedValue = (String)this.objMap.get(key);
		
		if(translatedValue != null)
			return translatedValue;
		else
			return key;
		
	}
}
