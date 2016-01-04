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
* 20-May-2015       Abinas            pojo created for History
* 08-June-2015       Abinas            doc comment added
****************************************** *********************************** */
package com.quix.aia.cn.imo.data.goalsetting;

import com.quix.aia.cn.imo.utilities.SecurityAPI;
import java.util.Date;
import com.quix.aia.cn.imo.utilities.LMSUtil;
/**
 * <p>History bean class.</p>
 * 
 * @author Abinas
 * @version 1.0
 *
 */
public class History {
	private int history_id;
	private Date history_date;
	private String goal_status;
	private String agent_code;
	
	
	private int ga;
	private int ha;
	private int gen_y;
	private int sa;
	private int total;
	/**
	 * @return the history_id
	 */
	public int getHistory_id() {
		return history_id;
	}
	/**
	 * @param history_id the history_id to set
	 */
	public void setHistory_id(int history_id) {
		this.history_id = history_id;
	}
	/**
	 * @return the history_date
	 */
	public Date getHistory_date() {
		return history_date;
	}
	/**
	 * @param history_date the history_date to set
	 */
	public void setHistory_date(Date history_date) {
		this.history_date = history_date;
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
	 * @param ha the ha to set
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
	  * <p>Default Constructor.Setting default values for history fields.</p>
	  */
	public History(){
		history_id=0;
		history_date=null;
		goal_status="";
		agent_code="";

		ga = 0;
		ha=0;
		gen_y = 0;
		sa = 0;
		total = 0;

		
	}
	/**
	 * <p>Dynamic rows added based history list size</p>
	 * @param i    each row number
	 * @return  row as string
	 */
	public Object getHistoryListingTableRow(int i) 
	{
		String returnStr="";
		String row ="";
		 String historyDate ="";
		 if(history_date!=null)
			 historyDate = LMSUtil.convertDateToddmmyyyyhhmmss(history_date);
	        
		//if(i%2 == 0)
		//	row = "style='background: #9A9090'";
		//String cell = "style='background: #9A9090'";
		 String cell="";
		returnStr = "<tr "+row+"'> "+

      			"<td  '"+cell+"'>"+SecurityAPI.encodeHTML(historyDate)+"</td>"+
      			"<td  '"+cell+"'>"+SecurityAPI.encodeHTML(this.agent_code+"")+"</td>"+
      			"<td  '"+cell+"'>"+SecurityAPI.encodeHTML(this.goal_status)+"</td>"+
      			"<td  '"+cell+"'>"+SecurityAPI.encodeHTML(ga+"")+"</td>"+
      			"<td  '"+cell+"'>"+SecurityAPI.encodeHTML(ha+"")+"</td>"+
      			"<td  '"+cell+"'>"+SecurityAPI.encodeHTML(gen_y+"")+"</td>"+
      			"<td  '"+cell+"'>"+SecurityAPI.encodeHTML(sa+"")+"</td>"+
      			"<td  '"+cell+"'>"+SecurityAPI.encodeHTML(total+"")+"</td>"+
      			"</tr>"  ;

		return returnStr;

	}





	
}
