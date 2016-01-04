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
* 20-May-2015       Abinas            pojo created for goal breakdown
* 08-June-2015       Abinas            doc comment added
****************************************** *********************************** */
package com.quix.aia.cn.imo.data.goalsetting;
import java.util.Date;
/**
 * <p>Goal Break Down bean class.</p>
 * 
 * @author Abinas
 * @version 1.0
 *
 */
public class GoalBreakDown {
	private int goal_breakdown_id;
	private String agent_code;
	private int year;
	private int dec,jan,feb,mar,apr,may,june,july,aug,sept,oct,nov;
	private int total;
	private String created_by;
	private Date created_date;
	private String modified_by;
	private Date modified_date;
	private String phase_name;
	/**
	  * <p>Default Constructor.Setting default values for goalbreakdown fields.</p>
	  */
	public GoalBreakDown(){
		goal_breakdown_id=0;
		agent_code="";
		year=0;
		dec=0;
		jan=0;
		feb=0;
		mar=0;
		apr=0;
		may=0;
		june=0;
		july=0;
		aug=0;
		sept=0;
		oct=0;
		nov=0;
		
		total = 0;
		
		
		created_by="";
		created_date=null;
		modified_by = "";
		modified_date = null;
		
		phase_name="";
	}


	/**
	 * @return the phase_name
	 */
	public String getPhase_name() {
		return phase_name;
	}



	/**
	 * @param phase_name the phase_name to set
	 */



	public void setPhase_name(String phase_name) {
		this.phase_name = phase_name;
	}






	/**
	 * @return the goal_breakdown_id
	 */

	public int getGoal_breakdown_id() {
		return goal_breakdown_id;
	}


	/**
	 * @param goal_breakdown_id the goal_breakdown_id to set
	 */




	public void setGoal_breakdown_id(int goal_breakdown_id) {
		this.goal_breakdown_id = goal_breakdown_id;
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
	 * @return the dec
	 */


	public int getDec() {
		return dec;
	}

	/**
	 * @param dec the dec to set
	 */




	public void setDec(int dec) {
		this.dec = dec;
	}




	/**
	 * @return the jan
	 */



	public int getJan() {
		return jan;
	}



	/**
	 * @param jan the jan to set
	 */



	public void setJan(int jan) {
		this.jan = jan;
	}





	/**
	 * @return the feb
	 */


	public int getFeb() {
		return feb;
	}


	/**
	 * @param feb the feb to set
	 */




	public void setFeb(int feb) {
		this.feb = feb;
	}




	/**
	 * @return the mar
	 */



	public int getMar() {
		return mar;
	}



	/**
	 * @param mar the mar to set
	 */



	public void setMar(int mar) {
		this.mar = mar;
	}





	/**
	 * @return the Apr
	 */


	public int getApr() {
		return apr;
	}


	/**
	 * @param apr the apr to set
	 */




	public void setApr(int apr) {
		this.apr = apr;
	}






	/**
	 * @return the may
	 */

	public int getMay() {
		return may;
	}




	/**
	 * @param may the may to set
	 */


	public void setMay(int may) {
		this.may = may;
	}





	/**
	 * @return the june
	 */


	public int getJune() {
		return june;
	}



	/**
	 * @param june the june to set
	 */



	public void setJune(int june) {
		this.june = june;
	}





	/**
	 * @return the july
	 */


	public int getJuly() {
		return july;
	}


	/**
	 * @param july the july to set
	 */




	public void setJuly(int july) {
		this.july = july;
	}





	/**
	 * @return the august
	 */


	public int getAug() {
		return aug;
	}


	/**
	 * @param august the august to set
	 */




	public void setAug(int aug) {
		this.aug = aug;
	}





	/**
	 * @return the sept
	 */


	public int getSept() {
		return sept;
	}




	/**
	 * @param sept the sept to set
	 */


	public void setSept(int sept) {
		this.sept = sept;
	}



	/**
	 * @return the oct
	 */




	public int getOct() {
		return oct;
	}


	/**
	 * @param oct the oct to set
	 */




	public void setOct(int oct) {
		this.oct = oct;
	}





	/**
	 * @return the nov
	 */


	public int getNov() {
		return nov;
	}





	/**
	 * @param nov the nov to set
	 */

	public void setNov(int nov) {
		this.nov = nov;
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
	 * @return the created by
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
	 * @return the created date
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
	 * @return the modified by
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
	 * @return the modified date
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
