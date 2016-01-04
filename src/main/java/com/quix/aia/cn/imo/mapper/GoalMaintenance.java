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
*20-May-2015       Abinas           Goal settings  functionality code added
*08-June-2015       Abinas            doc comment added
*18-Nov-2015            Nibedita          Error stored in db
****************************************** *********************************** */
package com.quix.aia.cn.imo.mapper;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;



import com.quix.aia.cn.imo.constants.RequestAttributes;
import com.quix.aia.cn.imo.data.goalsetting.GoalBreakDown;
import com.quix.aia.cn.imo.data.goalsetting.GoalConfig;
import com.quix.aia.cn.imo.data.goalsetting.History;
import com.quix.aia.cn.imo.data.goalsetting.Individual_Goal;
import com.quix.aia.cn.imo.data.user.User;
import com.quix.aia.cn.imo.database.HibernateFactory;
//import com.quix.aia.cn.imo.data.category.Category;
import com.quix.aia.cn.imo.utilities.EmailNotification;
import com.quix.aia.cn.imo.utilities.LMSUtil;
import com.quix.aia.cn.imo.utilities.MsgObject;
import com.quix.aia.cn.imo.utilities.Pager;

/**
 * <p>This class is for CRUD operations </p>
 * @author Abinas
 * @version 1.0
 */
public class GoalMaintenance 
{
      static Logger log = Logger.getLogger(GoalMaintenance.class.getName());
/**
      * <p>This method used to insert individual goal of agents</p>
      * @param agent_code agent code
      * @param ga GA  
      * @param ha  HA
      * @param gen_y GEN_Y
      * @param sa SA
      * @param for_year year
      * @param requestParameters Servlet Request Parameter
      * @return Map Object
      */
      public Map insertIndividualGoal(int agent_code,int ga,int ha,int gen_y,int sa,int for_year,HttpServletRequest request)
		{
    	  log.log(Level.INFO,"GoalMaintenance --> insert individual goal ");
		  User userObj = (User)request.getSession().getAttribute("currUserObj");
			Map map = new HashMap();
			String msg="";
			Individual_Goal obj=new Individual_Goal();
			Session session = HibernateFactory.openSession();
			Transaction tx = session.beginTransaction();
			int total=0;
			total=ga+ha+gen_y+sa;
			try{
				obj.setAgent_code(agent_code+"");
				obj.setYear(for_year);
				obj.setGa(ga);
				obj.setHa(ha);
				obj.setGen_y(gen_y);
				obj.setSa(sa);
				obj.setTotal(ga+ha+gen_y+sa);
				obj.setGoal_status("Pending");
				obj.setCreated_by(userObj.getStaffLoginId());
        		obj.setCreated_date(new Date());
        		Query query=session.createQuery("from Individual_Goal where agent_code=:agent_code");
				query.setParameter("agent_code", agent_code);
				ArrayList<Individual_Goal> arrActivity=(ArrayList<Individual_Goal>) query.list();
	        	if(arrActivity.size()>0)
	        	{
	        		obj.setIndividual_id(((Individual_Goal)arrActivity.get(0)).getIndividual_id());
	        		obj.setModified_by(userObj.getStaffLoginId());
	        		obj.setModified_date(new Date());
	        		HibernateFactory.close(session);
	        		session = HibernateFactory.openSession();
					tx= session.beginTransaction();
	        		session.update(obj);
	        		msg="The Individual Goal is updated successfully.";
	        	}
	        	else{
	        		
	        		session.save(obj);
	        		msg="Individual Goal is submitted successfully";
	        	}
        		tx.commit();
        		History historyObj=new History();
        		historyObj.setHistory_date(new Date());
        		historyObj.setGoal_status("Pending");
        		historyObj.setAgent_code(agent_code+"");
        		
        		historyObj.setGa(ga);
        		historyObj.setHa(ha);
        		historyObj.setGen_y(gen_y);
        		historyObj.setSa(sa);
        		historyObj.setTotal(ga+ha+gen_y+sa);
        		session.save(historyObj);
        		tx.commit();
        		//insert into breakdown table
        		 query=session.createQuery("from GoalBreakDown where agent_code=:agent_code");
				query.setParameter("agent_code", agent_code);
				ArrayList<Individual_Goal> arrActivity1=(ArrayList<Individual_Goal>) query.list();
	        	if(arrActivity1.size()>0)
	        	{
	        		query=session.createQuery("delete from GoalBreakDown where agent_code=:agent_code");
					query.setParameter("agent_code", agent_code);
					query.executeUpdate();
					tx.commit();
	        	}
        		int[] arrPhase=null;
        		ArrayList phase__arr=getMonthValues(total,new Date(),agent_code,for_year,arrPhase,session);
        		HibernateFactory.close(session);
        		session = HibernateFactory.openSession();
				tx= session.beginTransaction();
				for(int i=0;i<phase__arr.size();i++){
					session.save(phase__arr.get(i));
					tx.commit();
				}
        		//tx.commit();
        		
			}catch(Exception e){
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				LogsMaintenance logsMain=new LogsMaintenance();
				logsMain.insertLogs("GoalMaintenance",Level.SEVERE+"",errors.toString());
				
			}finally{
				HibernateFactory.close(session);
			}
		//	EmailNotification.sendToLeaderNotification(request,userObj);
			map.put("msg", msg);
			return map;
		}
      
      
      private ArrayList getMonthValues(int total, Date date,int agent_code,int for_year,int[] arrPhase,Session session) {
    	  ArrayList phase_arr=new ArrayList();
    	  int phase_9=0,phase_8=0,phase_7=0,phase_6=0,phase_5=0,phase_4=0,phase_3=0,phase_2=0,phase_1=0;
    	  //transforamtion formula
    	  
    	  if(arrPhase==null){
    		  
    		  Map<String, GoalConfig> map = new HashMap();
        	  GoalConfigMaintenance goalConfigMaintenance = new GoalConfigMaintenance();
        	  map = goalConfigMaintenance.getConfigData();

        	  GoalConfig goalConfig = new GoalConfig();
        	  goalConfig = (GoalConfig)map.get("goalConfigation");
        	  
		    	phase_9=total;
//		    	phase_8=Math.round(((float)total*100)/80);
//		  		phase_7=Math.round(((float)phase_8*100)/80);
//		  		phase_6=Math.round(((float)phase_7*100)/80);
//		  		phase_5=Math.round(((float)phase_6*100)/80);
//		  		phase_4=Math.round(((float)phase_5*100)/80);
//		  		phase_3=Math.round(((float)phase_4*100)/80);
//		  		phase_2=Math.round(((float)phase_3*100)/80);
//		  		phase_1=Math.round(((float)phase_2*100)/80);
		  		
		  		phase_8=Math.round(((float)total*100)/goalConfig.getAttendedTraining());
		  		phase_7=Math.round(((float)phase_8*100)/goalConfig.getPassALE());
		  		phase_6=Math.round(((float)phase_7*100)/goalConfig.getCompanyInterview());
		  		phase_5=Math.round(((float)phase_6*100)/goalConfig.getCcAssessment());
		  		phase_4=Math.round(((float)phase_5*100)/goalConfig.getEopAttendance());
		  		phase_3=Math.round(((float)phase_4*100)/goalConfig.getFirstInterview());
		  		phase_2=Math.round(((float)phase_3*100)/goalConfig.getEopRegistration());
		  		phase_1=Math.round(((float)phase_2*100)/goalConfig.getPotentialCandidate());
    	  }else{
	    		  phase_1=arrPhase[0];
	    		  phase_2=arrPhase[1];
	    		  phase_3=arrPhase[2];
	    		  phase_4=arrPhase[3];
	    		  phase_5=arrPhase[4];
	    		  phase_6=arrPhase[5];
	    		  phase_7=arrPhase[6];
	    		  phase_8=arrPhase[7];
	    		  phase_9=arrPhase[8];
    	  }
  		//all phases values added to list to run loop and get monthly value
  		phase_arr.add(phase_1);phase_arr.add(phase_2);phase_arr.add(phase_3);phase_arr.add(phase_4);
  		phase_arr.add(phase_5);phase_arr.add(phase_6);phase_arr.add(phase_7);phase_arr.add(phase_8);phase_arr.add(phase_9);
  		ArrayList phase_arr_1=new ArrayList();
  		
  		//added to keep  past month values
		 Query query=session.createQuery("from GoalBreakDown where agent_code=:agent_code");
		 query.setParameter("agent_code", agent_code);
		 Map<String,GoalBreakDown> map = new HashMap<String,GoalBreakDown>();
		 ArrayList gList=(ArrayList) query.list();
		if(gList!=null && gList.size() > 0){
			for(int j =0;j<gList.size();j++){
				map.put("phase_"+(j+1),(GoalBreakDown)gList.get(j));
			}
		}
  		//phase wise loop start
  		for(int i=0;i<phase_arr.size();i++){
  		     GoalBreakDown obj=new GoalBreakDown();
			
  		     if(map!=null && map.size() > 0){
  		    	if(map.get("phase_"+(i+1)) !=null) {
  		    		obj = map.get("phase_"+(i+1));
  		    	}
  		     }
//inside loop month value is calculated for each month and set to breakdown object and then object is added into one list
  	    	int dec=0,jan=0,feb=0,mar=0,apr=0,may=0,jun=0,jul=0,aug=0,sept=0,oct=0,nov=0;
  	    	String created_month="";
  	    	
//  	   	 obj= (GoalBreakDown)goal_list.get(i);
  	    	created_month=(Integer.parseInt((LMSUtil.convertDateToString(date)).substring(3,5))+1)+"";
  			System.out.println(created_month);
  			int extra=0,result=0,divider=0,extra_month=0;
  			 total=(Integer)phase_arr.get(i);
  			if(created_month.equals("12"))
				 total = total - (obj.getDec() + obj.getJan() + obj.getFeb() + obj.getMar() + obj.getApr() + obj.getMay() + + obj.getJune() +  obj.getJuly() + obj.getAug() + obj.getSept() + obj.getOct());
  			if(created_month.equals("11"))
 				 total = total - (obj.getDec() + obj.getJan() + obj.getFeb() + obj.getMar() + obj.getApr() + obj.getMay() + + obj.getJune() +  obj.getJuly() + obj.getAug() + obj.getSept());
  			 if(created_month.equals("10"))
  				 total = total - (obj.getDec() + obj.getJan() + obj.getFeb() + obj.getMar() + obj.getApr() + obj.getMay() + + obj.getJune() +  obj.getJuly() + obj.getAug());
  			 if(created_month.equals("9"))
  				 total = total - (obj.getDec() + obj.getJan() + obj.getFeb() + obj.getMar() + obj.getApr() + obj.getMay() + + obj.getJune() + obj.getJuly());
  			 if(created_month.equals("8"))
  				 total = total - (obj.getDec() + obj.getJan() + obj.getFeb() + obj.getMar() + obj.getApr() + obj.getMay() + + obj.getJune());
  			 if(created_month.equals("7"))
  				 total = total - (obj.getDec() + obj.getJan() + obj.getFeb() + obj.getMar() + obj.getApr() + obj.getMay());
  			 if(created_month.equals("6"))
  				 total = total - (obj.getDec() + obj.getJan() + obj.getFeb() + obj.getMar() + obj.getApr());
  			 if(created_month.equals("5"))
  				 total = total - (obj.getDec() + obj.getJan() + obj.getFeb() + obj.getMar());
  			 if(created_month.equals("4"))
  				 total = total - (obj.getDec() + obj.getJan() + obj.getFeb());
  			 if(created_month.equals("3"))
  				 total = total - (obj.getDec() + obj.getJan());
  			if(created_month.equals("2"))
 				 total = total - obj.getDec();
  			if(created_month.equals("1"))
				 total = total - 0;

  			 
  			 
  			 divider=(12-Integer.parseInt(created_month))+1;
  			System.out.println(divider);
  			result=total/divider;
  			extra=total-(result*divider);
  			System.out.println(extra);
  			if(created_month.equals("1")){
  				if(extra!=0){
  					extra_month=extra/extra;
  					}
  				dec=result;jan=result;feb=result;mar=result;apr=result;may=result;jun=result;jul=result;aug=result;sept=result;oct=result;nov=result+extra_month;
  				extra--;
  				if(extra!=0){
  					dec=result;jan=result;feb=result;mar=result;apr=result;may=result;jun=result;jul=result;aug=result;sept=result;oct=result+extra_month;
  					extra--;
  				} if(extra!=0)	{
  					dec=result;jan=result;feb=result;mar=result;apr=result;may=result;jun=result;jul=result;aug=result;sept=result+extra_month;
  					extra--;
  				} if(extra!=0)	{
  					dec=result;jan=result;feb=result;mar=result;apr=result;may=result;jun=result;jul=result;aug=result+extra_month;
  						extra--;
  				} if(extra!=0)	{
  					dec=result;jan=result;feb=result;mar=result;apr=result;may=result;jun=result;jul=result+extra_month;
  					extra--;
  				} if(extra!=0)	{
  					dec=result;jan=result;feb=result;mar=result;apr=result;may=result;jun=result+extra_month;
  					extra--;
  				} if(extra!=0)	{
  					dec=result;jan=result;feb=result;mar=result;apr=result;may=result+extra_month;
  					extra--;
  				} if(extra!=0){	
  					dec=result;jan=result;feb=result;mar=result;apr=result+extra_month;
  					extra--;
  				}if(extra!=0){	
  					dec=result;jan=result;feb=result;mar=result+extra_month;
  					extra--;
  				}if(extra!=0){	
  					dec=result;jan=result;feb=result+extra_month;
  					extra--;
  				}if(extra!=0){	
  					dec=result;jan=result+extra_month;
  					extra--;
  				}
  				if(extra!=0){	
  					dec=result+extra_month;
  					extra--;
  				}
  			}
  			if(created_month.equals("2")){
  				if(extra!=0){
  					extra_month=extra/extra;
  					}
  				jan=result;feb=result;mar=result;apr=result;may=result;jun=result;jul=result;aug=result;sept=result;oct=result;nov=result+extra_month;
  				extra--;
  				if(extra!=0){
  					jan=result;feb=result;mar=result;apr=result;may=result;jun=result;jul=result;aug=result;sept=result;oct=result+extra_month;
  					extra--;
  				} if(extra!=0)	{
  					jan=result;feb=result;mar=result;apr=result;may=result;jun=result;jul=result;aug=result;sept=result+extra_month;
  					extra--;
  				} if(extra!=0)	{
  					jan=result;feb=result;mar=result;apr=result;may=result;jun=result;jul=result;aug=result+extra_month;
  						extra--;
  				} if(extra!=0)	{
  					jan=result;feb=result;mar=result;apr=result;may=result;jun=result;jul=result+extra_month;
  					extra--;
  				} if(extra!=0)	{
  					jan=result;feb=result;mar=result;apr=result;may=result;jun=result+extra_month;
  					extra--;
  				} if(extra!=0)	{
  					jan=result;feb=result;mar=result;apr=result;may=result+extra_month;
  					extra--;
  				} if(extra!=0){	
  					jan=result;feb=result;mar=result;apr=result+extra_month;
  					extra--;
  				}if(extra!=0){	
  					jan=result;feb=result;mar=result+extra_month;
  					extra--;
  				}if(extra!=0){	
  					jan=result;feb=result+extra_month;
  					extra--;
  				}if(extra!=0){	
  					jan=result+extra_month;
  					extra--;
  				}
  				
  			}
  			if(created_month.equals("3")){
  				if(extra!=0){
  					extra_month=extra/extra;
  					}
  				feb=result;mar=result;apr=result;may=result;jun=result;jul=result;aug=result;sept=result;oct=result;nov=result+extra_month;
  				extra--;
  				if(extra!=0){
  					feb=result;mar=result;apr=result;may=result;jun=result;jul=result;aug=result;sept=result;oct=result+extra_month;
  					extra--;
  				} if(extra!=0)	{
  					feb=result;mar=result;apr=result;may=result;jun=result;jul=result;aug=result;sept=result+extra_month;
  					extra--;
  				} if(extra!=0)	{
  					feb=result;mar=result;apr=result;may=result;jun=result;jul=result;aug=result+extra_month;
  						extra--;
  				} if(extra!=0)	{
  					feb=result;mar=result;apr=result;may=result;jun=result;jul=result+extra_month;
  					extra--;
  				} if(extra!=0)	{
  					feb=result;mar=result;apr=result;may=result;jun=result+extra_month;
  					extra--;
  				} if(extra!=0)	{
  					feb=result;mar=result;apr=result;may=result+extra_month;
  					extra--;
  				} if(extra!=0){	
  					feb=result;mar=result;apr=result+extra_month;
  					extra--;
  				}if(extra!=0){	
  					feb=result;mar=result+extra_month;
  					extra--;
  				}if(extra!=0){	
  					feb=result+extra_month;
  					extra--;
  				}
  			}
  			if(created_month.equals("4")){
  				if(extra!=0){
  					extra_month=extra/extra;
  					}
  				mar=result;apr=result;may=result;jun=result;jul=result;aug=result;sept=result;oct=result;nov=result+extra_month;
  				extra--;
  				if(extra!=0){
  					mar=result;apr=result;may=result;jun=result;jul=result;aug=result;sept=result;oct=result+extra_month;
  					extra--;
  				} if(extra!=0)	{
  					mar=result;apr=result;may=result;jun=result;jul=result;aug=result;sept=result+extra_month;
  					extra--;
  				} if(extra!=0)	{
  					mar=result;apr=result;may=result;jun=result;jul=result;aug=result+extra_month;
  						extra--;
  				} if(extra!=0)	{
  					mar=result;apr=result;may=result;jun=result;jul=result+extra_month;
  					extra--;
  				} if(extra!=0)	{
  					mar=result;apr=result;may=result;jun=result+extra_month;
  					extra--;
  				} if(extra!=0)	{
  					mar=result;apr=result;may=result+extra_month;
  					extra--;
  				} if(extra!=0){	
  					mar=result;apr=result+extra_month;
  					extra--;
  				}if(extra!=0){	
  					mar=result+extra_month;
  					extra--;
  				}
  			}
  			if(created_month.equals("5")){
  				if(extra!=0){
  					extra_month=extra/extra;
  					}
  				apr=result;may=result;jun=result;jul=result;aug=result;sept=result;oct=result;nov=result+extra_month;
  				extra--;
  				if(extra!=0){
  					apr=result;may=result;jun=result;jul=result;aug=result;sept=result;oct=result+extra_month;
  					extra--;
  				} if(extra!=0)	{
  					apr=result;may=result;jun=result;jul=result;aug=result;sept=result+extra_month;
  					extra--;
  				} if(extra!=0)	{
  						apr=result;may=result;jun=result;jul=result;aug=result+extra_month;
  						extra--;
  				} if(extra!=0)	{
  					apr=result;may=result;jun=result;jul=result+extra_month;
  					extra--;
  				} if(extra!=0)	{
  					apr=result;may=result;jun=result+extra_month;
  					extra--;
  				} if(extra!=0)	{
  					apr=result;may=result+extra_month;
  					extra--;
  				} if(extra!=0){	
  					apr=result+extra_month;
  				}
  			}
  			if(created_month.equals("6")){
  				if(extra!=0){
  					extra_month=extra/extra;
  					}
  				may=result;jun=result;jul=result;aug=result;sept=result;oct=result;nov=result+extra_month;
  				extra--;
  				if(extra!=0){
  					may=result;jun=result;jul=result;aug=result;sept=result;oct=result+extra_month;
  					extra--;
  				} if(extra!=0)	{
  					may=result;jun=result;jul=result;aug=result;sept=result+extra_month;
  					extra--;
  				} if(extra!=0)	{
  					may=result;jun=result;jul=result;aug=result+extra_month;
  						extra--;
  				} if(extra!=0)	{
  					may=result;jun=result;jul=result+extra_month;
  					extra--;
  				} if(extra!=0)	{
  					may=result;jun=result+extra_month;
  					extra--;
  				} if(extra!=0)	{
  					may=result+extra_month;
  					extra--;
  				}
  			}
  			if(created_month.equals("7")){
  				if(extra!=0){
  					extra_month=extra/extra;
  					}
  				jun=result;jul=result;aug=result;sept=result;oct=result;nov=result+extra_month;
  				extra--;
  				if(extra!=0){
  					jun=result;jul=result;aug=result;sept=result;oct=result+extra_month;
  					extra--;
  				} if(extra!=0)	{
  					jun=result;jul=result;aug=result;sept=result+extra_month;
  					extra--;
  				} if(extra!=0)	{
  					jun=result;jul=result;aug=result+extra_month;
  						extra--;
  				} if(extra!=0)	{
  					jun=result;jul=result+extra_month;
  					extra--;
  				} if(extra!=0)	{
  					jun=result+extra_month;
  					extra--;
  				} 
  			}
  			if(created_month.equals("8")){
  				if(extra!=0){
  					extra_month=extra/extra;
  					}
  				jul=result;aug=result;sept=result;oct=result;nov=result+extra_month;
  				extra--;
  				if(extra!=0){
  					jul=result;aug=result;sept=result;oct=result+extra_month;
  					extra--;
  				} if(extra!=0)	{
  					jul=result;aug=result;sept=result+extra_month;
  					extra--;
  				} if(extra!=0)	{
  					jul=result;aug=result+extra_month;
  						extra--;
  				} if(extra!=0)	{
  					jul=result+extra_month;
  					extra--;
  				}	
  				
  			}
  			if(created_month.equals("9")){
  				if(extra!=0){
  					extra_month=extra/extra;
  					}
  				aug=result;sept=result;oct=result;nov=result+extra_month;
  				extra--;
  				if(extra!=0){
  					aug=result;sept=result;oct=result+extra_month;
  					extra--;
  				} if(extra!=0)	{
  					aug=result;sept=result+extra_month;
  					extra--;
  				} if(extra!=0)	{
  					aug=result+extra_month;
  						extra--;
  				} 
  			}
  			if(created_month.equals("10")){
  				if(extra!=0){
  					extra_month=extra/extra;
  					}
  				sept=result;oct=result;nov=result+extra_month;
  				extra--;
  				if(extra!=0){
  					sept=result;oct=result+extra_month;
  					extra--;
  				} if(extra!=0)	{
  					sept=result+extra_month;
  					extra--;
  				} 
  			}
  			if(created_month.equals("11")){
  				if(extra!=0){
  					extra_month=extra/extra;
  					}
  				oct=result;nov=result+extra_month;
  				extra--;
  				if(extra!=0){
  					oct=result+extra_month;
  					extra--;
  				} 
  				
  			}
  			if(created_month.equals("12")){
  				if(extra!=0){
  					extra_month=extra/extra;
  					}
  				nov=result+extra_month;
  				extra--;
  				
  			}//end of each month calculation
  			obj.setAgent_code(agent_code+"");
  			obj.setYear(for_year);
  			obj.setTotal((Integer)phase_arr.get(i));
  			obj.setPhase_name("phase_"+(i+1));
  			
  			if(created_month.equals("12")){
  	  			obj.setNov(nov);
  	  			}
  			if(created_month.equals("11")){
  	  			obj.setOct(oct);obj.setNov(nov);
  	  			}
  			if(created_month.equals("10")){
  				obj.setSept(sept);obj.setOct(oct);obj.setNov(nov);
  	  			}
  			if(created_month.equals("9")){
  				obj.setAug(aug);obj.setSept(sept);obj.setOct(oct);obj.setNov(nov);
  	  			}
  			if(created_month.equals("8")){
  				obj.setJuly(jul);obj.setAug(aug);obj.setSept(sept);obj.setOct(oct);obj.setNov(nov);
  	  			}
  			if(created_month.equals("7")){
  				obj.setJune(jun);obj.setJuly(jul);obj.setAug(aug);obj.setSept(sept);obj.setOct(oct);obj.setNov(nov);
  	  			}
  			if(created_month.equals("6")){
  				obj.setMay(may);obj.setJune(jun);obj.setJuly(jul);obj.setAug(aug);obj.setSept(sept);obj.setOct(oct);obj.setNov(nov);
  	  			}
  			if(created_month.equals("5")){
  				obj.setApr(apr);obj.setMay(may);obj.setJune(jun);obj.setJuly(jul);obj.setAug(aug);obj.setSept(sept);obj.setOct(oct);obj.setNov(nov);
  	  			}
  			if(created_month.equals("4")){
  				obj.setMar(mar);obj.setApr(apr);obj.setMay(may);obj.setJune(jun);obj.setJuly(jul);obj.setAug(aug);obj.setSept(sept);obj.setOct(oct);obj.setNov(nov);
  	  			}
  			if(created_month.equals("3")){
  				obj.setFeb(feb);obj.setMar(mar);obj.setApr(apr);obj.setMay(may);obj.setJune(jun);obj.setJuly(jul);obj.setAug(aug);obj.setSept(sept);obj.setOct(oct);obj.setNov(nov);
  	  			}
  			if(created_month.equals("2")){
  				obj.setJan(jan);obj.setFeb(feb);obj.setMar(mar);obj.setApr(apr);obj.setMay(may);obj.setJune(jun);obj.setJuly(jul);obj.setAug(aug);obj.setSept(sept);obj.setOct(oct);obj.setNov(nov);
  	  			}
  			if(created_month.equals("1")){
  	  			obj.setDec(dec);obj.setJan(jan);obj.setFeb(feb);obj.setMar(mar); obj.setApr(apr);obj.setMay(may);obj.setJune(jun);obj.setJuly(jul);obj.setAug(aug);obj.setSept(sept);obj.setOct(oct);obj.setNov(nov);
  	  			
  	  			}
  			phase_arr_1.add(obj);
  		}
    		return phase_arr_1;
    	}
	/**
       * <p>This method used to get individual goal of agents</p>
       * @param requestParameters Servlet Request Parameter
       * @return void
       */
	public Individual_Goal getIndividualGoal(HttpServletRequest requestParamters) {
		log.log(Level.INFO,"GoalMaintenance --> get individual goal");
		Individual_Goal obj = new Individual_Goal();
		Session session = HibernateFactory.openSession();
		try{
			Query query=session.createQuery("from Individual_Goal where agent_code=:agent_code");
			query.setParameter("agent_code", 123);//temp agent code
			ArrayList<Individual_Goal> goal_list=(ArrayList<Individual_Goal>) query.list();
			if(goal_list.size()>0)
    		obj = goal_list.get(0);
		}catch(Exception e){
			log.log(Level.SEVERE,e.getMessage());
			e.printStackTrace();
		}finally{
			HibernateFactory.close(session);
		}
		requestParamters.setAttribute("goal_object", obj);
		return obj;
	}
	/**
    * <p>This method used to insert goal breakdown of  agents</p>
    * @param agent_code agent code
    * @param arr_phase phase value  
    * @param for_year year
    * @param requestParameters Servlet Request Parameter
    * @return Map Object
    */
	 public Map insertGoalBreakDown(int agent_code,int for_year,int[] arrPhase,HttpServletRequest request){
		 log.log(Level.INFO,"GoalMaintenance --> insert goal breakdown ");
		  Session session=null;
		  Transaction tx=null;
		  Map map = new HashMap();
		  User userObj = (User)request.getSession().getAttribute("currUserObj");
		   session = HibernateFactory.openSession();
			 tx = session.beginTransaction();
			String msg="";
		  try{
			  Query query=session.createQuery("from GoalBreakDown where agent_code=:agent_code");
				query.setParameter("agent_code", agent_code);
				ArrayList<Individual_Goal> arrActivity=(ArrayList<Individual_Goal>) query.list();
				HibernateFactory.close(session);
				session = HibernateFactory.openSession();
				tx = session.beginTransaction();
					 
	        	if(arrActivity.size()>0)
	        	{
	        		/* query=session.createQuery("delete from GoalBreakDown where agent_code=:agent_code");
					query.setParameter("agent_code", agent_code);
					query.executeUpdate();
					tx.commit();*/
	        		 ArrayList phase__arr=getMonthValues(0,new Date(),agent_code,for_year,arrPhase,session);
		        		HibernateFactory.close(session);
		        		session = HibernateFactory.openSession();
						tx= session.beginTransaction();
						for(int i=0;i<phase__arr.size();i++){
							session.update(phase__arr.get(i));
							tx.commit();
						}
	        	}else{
	        	
					 ArrayList phase__arr=getMonthValues(0,new Date(),agent_code,for_year,arrPhase,session);
		        		HibernateFactory.close(session);
		        		session = HibernateFactory.openSession();
						tx= session.beginTransaction();
						for(int i=0;i<phase__arr.size();i++){
							session.save(phase__arr.get(i));
							tx.commit();
						}
	        	}
			
			  tx.commit();
			  msg="Yearly Goal Breakdown is updated successfully";
			  
		  }catch(Exception e){
				log.log(Level.SEVERE,e.getMessage());
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				LogsMaintenance logsMain=new LogsMaintenance();
				logsMain.insertLogs("GoalMaintenance",Level.SEVERE+"",errors.toString());
			}finally{
				HibernateFactory.close(session);
			}
			map.put("msg", msg);
			return map;
	  }
	/**
     * <p>This method used to get yearly goal breakdown of agents</p>
     * @param requestParameters Servlet Request Parameter
     * @return void
     */
		public void getGoalBreakDown(HttpServletRequest requestParamters) {
			log.log(Level.INFO,"GoalMaintenance --> get goal breakdown");
			GoalBreakDown obj = new GoalBreakDown();
			Session session = HibernateFactory.openSession();
			ArrayList<GoalBreakDown> goal_list=null;
			try{
				Query query=session.createQuery("from GoalBreakDown where agent_code=:agent_code");
				query.setParameter("agent_code", 123);//temp agent code
				 goal_list=(ArrayList<GoalBreakDown>) query.list();
				//if(goal_list.size()>0)
	    		//obj = goal_list.get(0);
			}catch(Exception e){
				log.log(Level.SEVERE,e.getMessage());
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				LogsMaintenance logsMain=new LogsMaintenance();
				logsMain.insertLogs("GoalMaintenance",Level.SEVERE+"",errors.toString());
			}finally{
				HibernateFactory.close(session);
			}
			requestParamters.setAttribute("breakDownobject", goal_list);
		}
		/**
	      * <p>This method used to insert monthly goal breakdown  of agents</p>
	      * @param agent_code agent code
	      * @param arr_phase_1 phase value  
	      * @param for_year year
	      * @param requestParameters Servlet Request Parameter
	      * @return Map Object
	      */
		public Map insertMonthlyGoalBreakDown(int agent_code,int for_year,int[] arrPhase_1,HttpServletRequest request){
			log.log(Level.INFO,"GoalMaintenance --> insert monthly goal breakdown");
			  Session session=null;
			  Transaction tx=null;
			  Map map = new HashMap();
			  User userObj = (User)request.getSession().getAttribute("currUserObj");
			   session = HibernateFactory.openSession();
				 tx = session.beginTransaction();
				String msg="";
			  try{
				  Query query=session.createQuery("from GoalBreakDown where agent_code=:agent_code");
					query.setParameter("agent_code", agent_code);
					ArrayList<Individual_Goal> arrActivity=(ArrayList<Individual_Goal>) query.list();
					HibernateFactory.close(session);
					  session = HibernateFactory.openSession();
						 tx = session.beginTransaction();
		        	if(arrActivity.size()>0)
		        	{
		        		/* query=session.createQuery("delete from GoalBreakDown where agent_code=:agent_code");
						query.setParameter("agent_code", agent_code);
						query.executeUpdate();
						tx.commit();*/
		        	}
		        	HibernateFactory.close(session);
					  session = HibernateFactory.openSession();
						 tx = session.beginTransaction();
						 query=session.createQuery("UPDATE GoalBreakDown SET dec=:dec,jan=:jan,feb=:feb,mar=:mar,apr=:apr,may=:may,june=:june,july=:july,aug=:aug,sept=:sept,oct=:oct,nov=:nov,total=:total where phase_name=:phase_name and agent_code=:agent_code");
						 for(int i=0;i<arrPhase_1.length;i+=12){
							 query.setParameter("dec", arrPhase_1[i]);
							 query.setParameter("jan", arrPhase_1[i+1]);
							 query.setParameter("feb", arrPhase_1[i+2]);
							 query.setParameter("mar", arrPhase_1[i+3]);
							 query.setParameter("apr", arrPhase_1[i+4]);
							 query.setParameter("may", arrPhase_1[i+5]);
							 query.setParameter("june", arrPhase_1[i+6]);
							 query.setParameter("july", arrPhase_1[i+7]);
							 query.setParameter("aug", arrPhase_1[i+8]);
							 query.setParameter("sept", arrPhase_1[i+9]);
							 query.setParameter("oct", arrPhase_1[i+10]);
							query.setParameter("nov", arrPhase_1[i+11]);
							query.setParameter("total", arrPhase_1[i] +arrPhase_1[i+1]+arrPhase_1[i+2]+arrPhase_1[i+3]+arrPhase_1[i+4]+arrPhase_1[i+5]+arrPhase_1[i+6]+arrPhase_1[i+7]+arrPhase_1[i+8]+arrPhase_1[i+9]+arrPhase_1[i+10]+arrPhase_1[i+11]);
							if(i>=0 && i<=11)
							query.setParameter("phase_name", "phase_1");
							else if(i>=12 && i<=23)
								query.setParameter("phase_name", "phase_2");
							else if(i>=24 && i<=35)
								query.setParameter("phase_name", "phase_3");
							else if(i>=36 && i<=47)
								query.setParameter("phase_name", "phase_4");
							else if(i>=48 && i<=59)
								query.setParameter("phase_name", "phase_5");
							else if(i>=60 && i<=71)
								query.setParameter("phase_name", "phase_6");
							else if(i>=72 && i<=83)
								query.setParameter("phase_name", "phase_7");
							else if(i>=84 && i<=95)
								query.setParameter("phase_name", "phase_8");
							else if(i>=96 && i<=107)
								query.setParameter("phase_name", "phase_9");
							query.setParameter("agent_code", agent_code);
								query.executeUpdate();
								tx.commit();
						 }
							
				  
				  msg="Monthly Goal Breakdown is updated successfully";
				  
			  }catch(Exception e){
					log.log(Level.SEVERE,e.getMessage());
					e.printStackTrace();
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					LogsMaintenance logsMain=new LogsMaintenance();
					logsMain.insertLogs("GoalMaintenance",Level.SEVERE+"",errors.toString());
				}finally{
					HibernateFactory.close(session);
				}
				map.put("msg", msg);
				return map;
		  }
	
				/**
			      * <p>This method used to reset goal breakdown of agents</p>
			      * @param requestParameters Servlet Request Parameter
			      * @return void
			      */
				public void getResetGoalBreakDown(HttpServletRequest requestParamters,Individual_Goal obj) {
					log.log(Level.INFO,"GoalMaintenance -->reset monthly breakdown");
					  Session session=null;
					  Transaction tx=null;
					  Map map = new HashMap();
					 
					   session = HibernateFactory.openSession();
					   tx = session.beginTransaction();
					int[] arrPhase=null;
	        		ArrayList phase__arr=getMonthValues(obj.getTotal(),new Date(),0,0,arrPhase,session);
	        		requestParamters.setAttribute("breakDownobject", phase__arr);
					
				}
				/**
			      * <p>This method used to get yearly teamgoalof agents</p>
			      * @param requestParameters Servlet Request Parameter
			      * @return Map Object
			      */
		public void getYearTeamGoal(HttpServletRequest requestParamters) {
			log.log(Level.INFO,"GoalMaintenance --> get year year teamgoal ");
			Individual_Goal obj = new Individual_Goal();
			Session session = HibernateFactory.openSession();
			ArrayList<Individual_Goal> goal_list=null;
			String parameter="";
			if(requestParamters.getParameter("agent_code")!=null  && !requestParamters.getParameter("agent_code").equals("%")){
				parameter="where agent_code=:agent_code " ;
			}
			if(requestParamters.getParameter("sort_by")!=null ){
				parameter=parameter+" order by "+requestParamters.getParameter("sort_by");
			}
			try{
				Query query=session.createQuery("from Individual_Goal "+parameter+"");//need to put condition to get those agents which are under leader
				if(requestParamters.getParameter("agent_code")!=null  && !requestParamters.getParameter("agent_code").equals("%")){
					query.setParameter("agent_code", requestParamters.getParameter("agent_code"));
				}
				 goal_list=(ArrayList<Individual_Goal>) query.list();
			}catch(Exception e){
				log.log(Level.SEVERE,e.getMessage());
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				LogsMaintenance logsMain=new LogsMaintenance();
				logsMain.insertLogs("GoalMaintenance",Level.SEVERE+"",errors.toString());
			}finally{
				HibernateFactory.close(session);
			}
			requestParamters.setAttribute("year_team_goal_object", goal_list);
			
		}
		
		/**
	      * <p>This method used to approve individual goal of agents</p>
	      * @param individual_id individual_id id
	      * @param agent_code agent_code
	      * @param ga  GA  
	      * @param ha  HA
	      * @param gen_y GEN_Y
	      * @param sa SA
	      * @param total total
	      * @param requestParameters Servlet Request Parameter
	      * @return Map Object
	      */
		public Map ApproveRejectAgent(int individual_id,int agent_code,int ga,int ha,int gen_y,int sa,int total,String status,HttpServletRequest request)
		{
			log.log(Level.INFO,"GoalMaintenance --> approve agent ");
		  User userObj = (User)request.getSession().getAttribute("currUserObj");
			Map map = new HashMap();
			String msg="";
			Session session = HibernateFactory.openSession();
			Transaction tx=null;
			try{
				Query query=session.createQuery("UPDATE Individual_Goal set goal_status=:goal_status , approve_reject=:approve_reject where individual_id=:individual_id");
				query.setParameter("goal_status", status);
				query.setParameter("approve_reject", "1");
				query.setParameter("individual_id", individual_id);
				query.executeUpdate();
				msg="The agent is "+ status;
				
				tx = session.beginTransaction();
				History historyObj=new History();
        		historyObj.setHistory_date(new Date());
        		historyObj.setGoal_status(status);
        		historyObj.setAgent_code(agent_code+"");
        		
        		historyObj.setGa(ga);
        		historyObj.setHa(ha);
        		historyObj.setGen_y(gen_y);
        		historyObj.setSa(sa);
        		historyObj.setTotal(ga+ha+gen_y+sa);
        		session.save(historyObj);
        		tx.commit();
			}catch(Exception e){
				log.log(Level.SEVERE,e.getMessage());
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				LogsMaintenance logsMain=new LogsMaintenance();
				logsMain.insertLogs("GoalMaintenance",Level.SEVERE+"",errors.toString());
			}finally{
				HibernateFactory.close(session);
			}
			//EmailNotification.sendToAgentNotification(request,userObj);
			map.put("msg", msg);
			return map;
		}
		
		/**
	      * <p>This method used to get all history </p>
	      * @param requestParameters Servlet Request Parameter
	      * @return list of agent's history
	      */
		 public ArrayList getAllHistory(HttpServletRequest request)
			{
			 log.log(Level.INFO,"GoalMaintenance --> history of goal settings ");
			 String agent_code=request.getParameter("agent_code");
				   ArrayList<History> arrHistory = new ArrayList();
				   Session session = HibernateFactory.openSession();
			        try
			        {
						Query query=session.createQuery("from History where agent_code=:agent_code");
						query.setParameter("agent_code", agent_code);
						arrHistory=(ArrayList<History>) query.list();
			        }
			        catch(Exception e)
			        {
			        	log.log(Level.SEVERE,e.getMessage());
			            e.printStackTrace();
			            StringWriter errors = new StringWriter();
						e.printStackTrace(new PrintWriter(errors));
						LogsMaintenance logsMain=new LogsMaintenance();
						logsMain.insertLogs("GoalMaintenance",Level.SEVERE+"",errors.toString());
			        }try{
						HibernateFactory.close(session);
					}catch(Exception e){
						log.log(Level.SEVERE, e.getMessage());
						e.printStackTrace();
						StringWriter errors = new StringWriter();
						e.printStackTrace(new PrintWriter(errors));
						LogsMaintenance logsMain=new LogsMaintenance();
						logsMain.insertLogs("GoalMaintenance",Level.SEVERE+"",errors.toString());
					}
			        return arrHistory;
			}
	      /**
	      * <p>This method used to history listing</p>
	     
	      * @param arrPHistory list of history
	      * @return pager Object
	      */
			public Pager retrieveHistoryListing( List arrPHistory)
			{
				log.log(Level.INFO,"GoalMaintenance --> listing of history  ");
				LinkedList item = new LinkedList();
				History historyObj=new History();
				try
				{
					for(int i = 0; i < arrPHistory.size(); i++)
					{
						historyObj = (History)arrPHistory.get(i);
						item.add(historyObj.getHistoryListingTableRow(i));
					}
				}catch(Exception e)
				{
					e.printStackTrace();
				}
				Pager pager = new Pager();
				pager.setActualSize(item.size());
				pager.setCurrentPageNumber(0);
				pager.setMaxIndexPages(10);
				pager.setMaxPageItems(10);
				//pager.setTableHeader(presenterObj.getPresenterListingTableHdr());
				for(; item.size() % 10 != 0; item.add("<tr></tr>"));
				pager.setItems(item);
				return pager;
			}
}//end of class
