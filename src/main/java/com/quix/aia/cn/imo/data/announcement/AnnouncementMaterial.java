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
* Date                       Developer          Change Description
* 09-Sept-2015         Nibedita              File added
****************************************** *********************************** */
package com.quix.aia.cn.imo.data.announcement;

public class AnnouncementMaterial {
	private int materialCode;
	private int annoucementCode;
	private String materialName;
	private String fieldName;
	private byte[] material;
	
	public AnnouncementMaterial() {
		materialCode = 0;
		annoucementCode = 0;
		materialName = "";
		fieldName = "";
	}

	/**
	 * @return the materialCode
	 */
	public int getMaterialCode() {
		return materialCode;
	}

	/**
	 * @param materialCode the materialCode to set
	 */
	public void setMaterialCode(int materialCode) {
		this.materialCode = materialCode;
	}

	/**
	 * @return the annoucementCode
	 */
	public int getAnnoucementCode() {
		return annoucementCode;
	}

	/**
	 * @param annoucementCode the annoucementCode to set
	 */
	public void setAnnoucementCode(int annoucementCode) {
		this.annoucementCode = annoucementCode;
	}

	/**
	 * @return the materialName
	 */
	public String getMaterialName() {
		return materialName;
	}

	/**
	 * @param materialName the materialName to set
	 */
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @param fieldName the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * @return the material
	 */
	public byte[] getMaterial() {
		return material;
	}

	/**
	 * @param material the material to set
	 */
	public void setMaterial(byte[] material) {
		this.material = material;
	}


	
}
