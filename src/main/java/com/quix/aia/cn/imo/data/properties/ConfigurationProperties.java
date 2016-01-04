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
 * 14-August-2015               Maunish             ConfigurationProperties file added 
 ***************************************************************************** */

package com.quix.aia.cn.imo.data.properties;

/**
 * <p>
 * ConfigurationProperties bean class.
 * </p>
 * 
 * @author Maunish
 * @version 1.0
 */
public class ConfigurationProperties {

	private Integer configurationCode;
	private String configurationKey;
	private String configurationValue;

	/**
	 * <p>
	 * Default Constructor.Setting default values for CandidateEducationId
	 * fields.
	 * </p>
	 */
	public ConfigurationProperties() {
		configurationCode = null;
		configurationKey = null;
		configurationValue = null;
	}
	
	/**
	 * @return the configurationCode
	 */
	public Integer getConfigurationCode() {
		return configurationCode;
	}



	/**
	 * @param configurationCode the configurationCode to set
	 */
	public void setConfigurationCode(Integer configurationCode) {
		this.configurationCode = configurationCode;
	}



	/**
	 * @return the configurationKey
	 */
	public String getConfigurationKey() {
		return configurationKey;
	}



	/**
	 * @param configurationKey the configurationKey to set
	 */
	public void setConfigurationKey(String configurationKey) {
		this.configurationKey = configurationKey;
	}



	/**
	 * @return the configurationValue
	 */
	public String getConfigurationValue() {
		return configurationValue;
	}



	/**
	 * @param configurationValue the configurationValue to set
	 */
	public void setConfigurationValue(String configurationValue) {
		this.configurationValue = configurationValue;
	}



	/**
	 * <p>
	 * finalize method for garbage collection
	 * </p>
	 */
	@Override
	public void finalize() {
		configurationCode = null;
		configurationKey = null;
		configurationValue = null;
	}

}
