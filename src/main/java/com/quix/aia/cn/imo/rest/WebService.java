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
 * <p>Webervice bean class.</p>
 * 
 * @author Jay
 * @version 1.0
 *
 */
public class WebService {
	
	private String moduleName;
	private String method;
	private String url;
	private String description;
	private Host host;
	
	
	/**
	 * @return the module name
	 */
	public String getModuleName() {
		return moduleName;
	}
	
	/**
	 * @param module name the module name to set
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}
	
	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}
	
	/**
	 * @return the  Url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * @return the Description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * @param Description the Description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * @return the Host Bean for host name
	 */
	public Host getHost() {
		return host;
	}
	/**
	 * @param Host bean the Host bean to set host name
	 */
	public void setHost(Host host) {
		this.host = host;
	}
	

}
