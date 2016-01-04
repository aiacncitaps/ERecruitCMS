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
* 20-May-2015       Abinas            pojo created for Individual Goal
* 08-June-2015       Abinas            doc comment added
****************************************** *********************************** */
package com.quix.aia.cn.imo.data.goalsetting;

import java.util.Date;
/**
 * <p>Individual Goal bean class.</p>
 * 
 * @author Abinas
 * @version 1.0
 *
 */
public class Individual_Goal {
	private int individual_id;
	private String agent_code;
	private int year;
	
	
	private int ga;
	private int ha;
	private int gen_y;
	private int sa;
	private int total;
	
	private String goal_status;
	private String approve_reject;
	
	private String created_by;
	private Date created_date;
	private String modified_by;
	private Date modified_date;
	
	 /**
	  * <p>Default Constructor.Setting default values for Individual_Goal fields.</p>
	  */
	public Individual_Goal(){
		individual_id=0;
		agent_code="";
		year=0;

		ga = 0;
		ha=0;
		gen_y = 0;
		sa = 0;
		total = 0;

		goal_status="";
		
		created_by="";
		created_date=null;
		modified_by = "";
		modified_date = null;
	}





	/**
	 * @return the goal_status
	 */

	public String getGoal_status() {
		return goal_status;
	}




	/**
	 * @param goal_status the goal_status to set
	 */


	public void setGoal_status(String goal_status) {
		this.goal_status = goal_status;
	}




	/**
	 * @return the approve_reject
	 */


	public String getApprove_reject() {
		return approve_reject;
	}



	/**
	 * @param approve_reject the approve_reject to set
	 */



	public void setApprove_reject(String approve_reject) {
		this.approve_reject = approve_reject;
	}


	/**
	 * @return the individual_id
	 */




	public int getIndividual_id() {
		return individual_id;
	}



	/**
	 * @param individual_id the individual_id to set
	 */



	public void setIndividual_id(int individual_id) {
		this.individual_id = individual_id;
	}



	/**
	 * @return the agent_code
	 */




	public String getAgent_code() {
		return agent_code;
	}



	/**
	 * @param agent_code the agent_code to set
	 */



	public void setAgent_code(String agent_code) {
		this.agent_code = agent_code;
	}




	/**
	 * @return the year
	 */


	public int getYear() {
		return year;
	}



	/**
	 * @param year the year to set
	 */



	public void setYear(int year) {
		this.year = year;
	}




	/**
	 * @return the ga
	 */


	public int getGa() {
		return ga;
	}


	/**
	 * @param ga the ga to set
	 */




	public void setGa(int ga) {
		this.ga = ga;
	}



	/**
	 * @return the ha
	 */



	public int getHa() {
		return ha;
	}



	/**
	 * @param ha the agent_codhae to set
	 */



	public void setHa(int ha) {
		this.ha = ha;
	}



	/**
	 * @return the gen_y
	 */



	public int getGen_y() {
		return gen_y;
	}



	/**
	 * @param gen_y the gen_y to set
	 */



	public void setGen_y(int gen_y) {
		this.gen_y = gen_y;
	}




	/**
	 * @return the sa
	 */


	public int getSa() {
		return sa;
	}




	/**
	 * @param sa the sa to set
	 */


	public void setSa(int sa) {
		this.sa = sa;
	}




	/**
	 * @return the total
	 */


	public int getTotal() {
		return total;
	}



	/**
	 * @param total the total to set
	 */



	public void setTotal(int total) {
		this.total = total;
	}




	/**
	 * @return the created_by
	 */


	public String getCreated_by() {
		return created_by;
	}



	/**
	 * @param created_by the created_by to set
	 */



	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}




	/**
	 * @return the created_date
	 */


	public Date getCreated_date() {
		return created_date;
	}




	/**
	 * @param created_date the created_date to set
	 */


	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}




	/**
	 * @return the modified_by
	 */


	public String getModified_by() {
		return modified_by;
	}




	/**
	 * @param modified_by the modified_by to set
	 */


	public void setModified_by(String modified_by) {
		this.modified_by = modified_by;
	}





	/**
	 * @return the modified_date
	 */


	public Date getModified_date() {
		return modified_date;
	}



	/**
	 * @param modified_date the modified_date to set
	 */



	public void setModified_date(Date modified_date) {
		this.modified_date = modified_date;
	}
	
	

	
}
