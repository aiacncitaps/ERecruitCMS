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
* 06-May-2015       Nibedita            File Added. Code for getting Bu,district,city,ssc from database
* 13-May-2015       Jinatmayee          Code for Posted By added
* 17-May-2015       Jinatmayee          Code to get BuCode/DistCode/CityCode/SscCode based on their Name Added
* 18-May-2015       Nibedita            security added.unnecesary import deleted.
****************************************** *********************************** */
package com.quix.aia.cn.imo.utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StringType;

import com.quix.aia.cn.imo.data.announcement.Announcement;
import com.quix.aia.cn.imo.data.auditTrail.AuditTrail;
import com.quix.aia.cn.imo.data.branch.Branch;
import com.quix.aia.cn.imo.data.bu.Bu;
import com.quix.aia.cn.imo.data.channel.Channel;
import com.quix.aia.cn.imo.data.city.City;
import com.quix.aia.cn.imo.data.common.AamData;
import com.quix.aia.cn.imo.data.department.Department;
import com.quix.aia.cn.imo.data.district.District;
import com.quix.aia.cn.imo.data.egreeting.E_Greeting;
import com.quix.aia.cn.imo.data.event.Event;
import com.quix.aia.cn.imo.data.holiday.Holiday;
import com.quix.aia.cn.imo.data.interview.Interview;
import com.quix.aia.cn.imo.data.office.Office;
import com.quix.aia.cn.imo.data.presenter.Presenter;
import com.quix.aia.cn.imo.data.ssc.Ssc;
import com.quix.aia.cn.imo.data.user.User;
import com.quix.aia.cn.imo.database.HibernateFactory;
import com.quix.aia.cn.imo.mapper.AamDataMaintenance;
import com.quix.aia.cn.imo.mapper.AuditTrailMaintenance;
import com.quix.aia.cn.imo.mapper.LogsMaintenance;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;


public class ImoUtilityData {
	static Logger log = Logger.getLogger(ImoUtilityData.class.getName());
	private int code;
	private String aamTeamCode;
	private String Name;
	private String codeStr;


	public ImoUtilityData(){
		HibernateFactory.buildIfNeeded();
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	public String getAamTeamCode() {
		return aamTeamCode;
	}

	public void setAamTeamCode(String aamTeamCode) {
		this.aamTeamCode = aamTeamCode;
	}

	public String getName() {
		return Name;
	}


	public void setName(String name) {
		Name = name;
	}


	public static Map  getBU(){
		   ArrayList<ImoUtilityData> listData =  new  ArrayList<ImoUtilityData>();
		   Session session= null;
		try{
			
			session = HibernateFactory.openSession();
			ImoUtilityData imoData = null;
		 
			List list = session.createQuery("SELECT  buCode,buName  FROM  Bu where status = 1 ORDER BY  orderCode,buName" ).list();
			Iterator ite = list.iterator();
			if(list !=null  && list.size() > 0){
				  for(int i=0;i<list.size();i++){
					  if(ite.hasNext()){
						  Object [] objectBu = (Object []) ite.next();
						  imoData = new ImoUtilityData();
						  imoData.setCode((Integer)objectBu[0]);
						  imoData.setName(SecurityAPI.encodeHTML((String)objectBu[1]));
						  
						  listData.add(imoData);
						  
					  }
				  }
			}
			}catch(HibernateException e)
			{
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
				LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("IMOUtilityData",Level.SEVERE+"",errors.toString());
			}finally{
				try{
					HibernateFactory.close(session);
					
				}catch(Exception e){
					log.log(Level.SEVERE, e.getMessage());
					e.printStackTrace();
				}
		}
		Map map=new HashMap();
	    map.put("list",listData);
	    
	      return map;  
	}
	
	public static Map getDistrict(int buCode){
		 ArrayList<ImoUtilityData> listData =  new  ArrayList<ImoUtilityData>();
		 Session session= null;
		try{
			
			session = HibernateFactory.openSession();
			ImoUtilityData imoData = null;
		   
			Query  query = session.createQuery("SELECT  districtCode,districtName FROM District where buCode =:buCode and status = 1 ORDER BY orderCode,districtName" );
			query.setParameter("buCode", buCode);
			List list = query.list();
			Iterator ite = list.iterator();
			if(list !=null  && list.size() > 0){
				  for(int i=0;i<list.size();i++){
					  if(ite.hasNext()){
						  Object [] objectBu = (Object []) ite.next();
						  imoData = new ImoUtilityData();
						  imoData.setCode((Integer)objectBu[0]);
						  imoData.setName(SecurityAPI.encodeHTML((String)objectBu[1]));
						  
						  listData.add(imoData);
					  }
				  }
			}
			}catch(HibernateException e)
			{
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
				LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("IMOUtilityData",Level.SEVERE+"",errors.toString());
			}finally{
				try{
					HibernateFactory.close(session);
				}catch(Exception e){
					log.log(Level.SEVERE,e.getMessage());
					e.printStackTrace();
				}
		}
		Map map=new HashMap();
	    map.put("list",listData);
	    return map;
	}
	
	public static Map  getBranch(int districtCode){
		 ArrayList<ImoUtilityData> listData =  new  ArrayList<ImoUtilityData>();
		 Session session= null;
			try{
				
				session = HibernateFactory.openSession();
				ImoUtilityData imoData = null;
				//distCode =:districtCode and 
				Query  query = session.createQuery("SELECT Distinct  branchCode,branchFullName,orderCode FROM Branch where distCode =:districtCode and  status = 1 ORDER BY orderCode,branchCode,branchFullName" );
				query.setParameter("districtCode", districtCode);
				
				List list = query.list();
				Iterator ite = list.iterator();
				if(list !=null  && list.size() > 0){
					  for(int i=0;i<list.size();i++){
						  if(ite.hasNext()){
							  Object [] objectBu = (Object []) ite.next();
							  imoData = new ImoUtilityData();
							  imoData.setCode((Integer)objectBu[0]);
							  imoData.setName(SecurityAPI.encodeHTML((String)objectBu[1]));
							  
							  listData.add(imoData);
						  }
					  }
				}
				}catch(HibernateException e)
				{
					log.log(Level.SEVERE, e.getMessage());
					e.printStackTrace();
					LogsMaintenance logsMain=new LogsMaintenance();
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					logsMain.insertLogs("IMOUtilityData",Level.SEVERE+"",errors.toString());
				}finally{
					try{
						HibernateFactory.close(session);
					}catch(Exception e){
						log.log(Level.SEVERE, e.getMessage());
						e.printStackTrace();
					}
			}
			Map map=new HashMap();
		    map.put("list",listData);
		    return map;
	}
	
	public static Map  getCity(String co){
		 ArrayList<ImoUtilityData> listData =  new  ArrayList<ImoUtilityData>();
		 Session session= null;
			try{
				
				session = HibernateFactory.openSession();
				ImoUtilityData imoData = null;
			   
				Query query = session.createQuery("SELECT  branchName  FROM Branch where branchCode=:code" );
				query.setParameter("code", co);
				List list=query.list();
				if(list.size()>0){
				     query = session.createQuery("SELECT  cityName,cityFullName FROM City where co=:branchcode" );
						query.setParameter("branchcode", list.get(0));
						 list = query.list();
						Iterator ite = list.iterator();
						if(list !=null  && list.size() > 0){
							  for(int i=0;i<list.size();i++){
								  if(ite.hasNext()){
									  Object [] objectBu = (Object []) ite.next();
									  imoData = new ImoUtilityData();
									  imoData.setCodeStr(SecurityAPI.encodeHTML((String)objectBu[0]));
									  imoData.setName(SecurityAPI.encodeHTML((String)objectBu[1]).trim());
									  
									  listData.add(imoData);
								  }
							  }
						}
				}
				
            
				}catch(HibernateException e)
				{
					log.log(Level.SEVERE, e.getMessage());
					e.printStackTrace();
					LogsMaintenance logsMain=new LogsMaintenance();
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					logsMain.insertLogs("IMOUtilityData",Level.SEVERE+"",errors.toString());
				}finally{
					try{
						HibernateFactory.close(session);
					}catch(Exception e){
						log.log(Level.SEVERE, e.getMessage());
						e.printStackTrace();
					}
			}
			Map map=new HashMap();
		    map.put("list",listData);
		    return map;
	}
	
	public static Map getOffice(String code){
		Session session= null;
		 ArrayList<ImoUtilityData> listData =  new  ArrayList<ImoUtilityData>();
			try{
				
				session = HibernateFactory.openSession();
				ImoUtilityData imoData = null;
			   
                Query  query = session.createQuery("SELECT  officeName,officeFullName FROM Office where sscCodeStr =:sscCode " );
				query.setParameter("sscCode", code);
				List list = query.list();
				Iterator ite = list.iterator();
				if(list !=null  && list.size() > 0){
					  for(int i=0;i<list.size();i++){
						  if(ite.hasNext()){
							  Object [] objectBu = (Object []) ite.next();
							  imoData = new ImoUtilityData();
							  imoData.setCodeStr(SecurityAPI.encodeHTML((String)objectBu[0]));
							  imoData.setName(SecurityAPI.encodeHTML((String)objectBu[0])+"-"+SecurityAPI.encodeHTML((String)objectBu[1]).trim());
							  
							  listData.add(imoData);
						  }
					  }
				}
				}catch(HibernateException e)
				{
					log.log(Level.SEVERE, e.getMessage());
					e.printStackTrace();
					LogsMaintenance logsMain=new LogsMaintenance();
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					logsMain.insertLogs("IMOUtilityData",Level.SEVERE+"",errors.toString());
				}finally{
					try{
						HibernateFactory.close(session);
					}catch(Exception e){
						log.log(Level.SEVERE, e.getMessage());
						e.printStackTrace();
					}
			}
			Map map=new HashMap();
		    map.put("list",listData);
		    return map;
}
	
	
		public static Map getSSC(String cityCode){
			Session session= null;
			 ArrayList<ImoUtilityData> listData =  new  ArrayList<ImoUtilityData>();
				try{
					
					session = HibernateFactory.openSession();
					ImoUtilityData imoData = null;
				   
                    Query  query = session.createQuery("SELECT  sscName,sscFullName FROM Ssc where cityCodeStr =:code " );
					query.setParameter("code", cityCode);
					List list = query.list();
					Iterator ite = list.iterator();
					if(list !=null  && list.size() > 0){
						  for(int i=0;i<list.size();i++){
							  if(ite.hasNext()){
								  Object [] objectBu = (Object []) ite.next();
								  imoData = new ImoUtilityData();
								  imoData.setCodeStr(SecurityAPI.encodeHTML((String)objectBu[0]));
								  imoData.setName(SecurityAPI.encodeHTML((String)objectBu[0])+" - "+SecurityAPI.encodeHTML((String)objectBu[1]).trim());
								  listData.add(imoData);
							  }
						  }
					}
					}catch(HibernateException e)
					{
						log.log(Level.SEVERE, e.getMessage());
						e.printStackTrace();
						LogsMaintenance logsMain=new LogsMaintenance();
						StringWriter errors = new StringWriter();
						e.printStackTrace(new PrintWriter(errors));
						logsMain.insertLogs("IMOUtilityData",Level.SEVERE+"",errors.toString());
					}finally{
						try{
							HibernateFactory.close(session);
						}catch(Exception e){
							log.log(Level.SEVERE, e.getMessage());
							e.printStackTrace();
						}
				}
				Map map=new HashMap();
			    map.put("list",listData);
			    return map;
	}
		
	//public static Map getAgentTeam(String bu, String district, String city, String ssc,String branch){
		public static Map getAgentTeam(String city, String ssc,String branch,String office){
	 	ArrayList<ImoUtilityData> listData =  new  ArrayList<ImoUtilityData>();
		ImoUtilityData imoData = null;
		 Session session = null;
		
		try {
			session = HibernateFactory.openSession();
			
			Query query=session.createQuery("select branchName from Branch where branchCode=:code");
			query.setParameter("code",Integer.parseInt(branch));
			List list=query.list();
			if(list.size()!=0){
				
			 query=session.createQuery("select teamcode,teamName from AgentTeam where co=:co and "
					+ "city=:city and ssc=:ssc and office=:office ");
			query.setParameter("co", list.get(0));
			query.setParameter("city",city );
			query.setParameter("ssc",ssc );
			query.setParameter("office",office );
			
			list=query.list();
			Iterator ite = list.iterator();
			if(list !=null  && list.size() > 0){
				  for(int i=0;i<list.size();i++){
					  if(ite.hasNext()){
						  Object [] objectBu = (Object []) ite.next();
						  imoData = new ImoUtilityData();
						  imoData.setCodeStr(SecurityAPI.encodeHTML((String)objectBu[0]));
						  imoData.setName(SecurityAPI.encodeHTML((String)objectBu[0]).trim()+"-"+SecurityAPI.encodeHTML((String)objectBu[1]).trim());
						  listData.add(imoData);
					  }
				  }
			}
			
		}
		}catch(Exception e){
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("IMOUtilityData",Level.SEVERE+"",errors.toString());
				
		}finally{
			try{
				HibernateFactory.close(session);
			}catch(Exception e){
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
	}
		
		Map map=new HashMap();
	    map.put("list",listData);
	    return map;
	}
		
		public static String getBranchNameBaseonCode(int code){
		 	ArrayList<ImoUtilityData> listData =  new  ArrayList<ImoUtilityData>();
			ImoUtilityData imoData = null;
			 Session session = null;
			String str="";
			try {
				session = HibernateFactory.openSession();
				
				Query query=session.createQuery("select branchName from Branch where branchCode=:code");
				query.setParameter("code",code);
				List list=query.list();
				if(list.size()!=0){
					str=(String) list.get(0);
				}
			}catch(Exception e){
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
				LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("IMOUtilityData",Level.SEVERE+"",errors.toString());
					
			}finally{
				try{
					HibernateFactory.close(session);
				}catch(Exception e){
					log.log(Level.SEVERE, e.getMessage());
					e.printStackTrace();
				}
		}
			
			
		    return str;
		}
		
		private static String getAgentBranch(String branch) {
			// TODO Auto-generated method stub
			 log.log(Level.INFO,"ImoUtilitiData --> getAgentBranch");
			 Session session= null;
			 ArrayList<Branch> listBranch = new ArrayList<Branch>();
			 String branchName="";
				try
				{
					
					session = HibernateFactory.openSession();
					Branch branchbean=null;
					Query query=session.createQuery(" from Branch where branchFullName=:fullName ");
					query.setParameter("fullName", branch);
					listBranch=(ArrayList<Branch>) query.list();
					if(listBranch.size()>0){
						branchbean=listBranch.get(0);
						branchName=branchbean.getBranchName();
					}
					
					
				}
				catch(Exception e)
				{
					log.log(Level.SEVERE,e.getMessage());
					e.printStackTrace();
					LogsMaintenance logsMain=new LogsMaintenance();
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					logsMain.insertLogs("IMOUtilityData",Level.SEVERE+"",errors.toString());
				}finally{
					HibernateFactory.close(session);
				}
				
				
				return branchName;
		}
		
		private static String getAgentCity(String city) {
			// TODO Auto-generated method stub
			 log.log(Level.INFO,"ImoUtilitiData --> getAgentCity");
			 Session session= null;
			 ArrayList<City> listCity = new ArrayList<City>();
			 String name="";
				try
				{
					
					session = HibernateFactory.openSession();
					City citybean=null;
					Query query=session.createQuery(" from City where cityFullName=:fullName ");
					query.setParameter("fullName", city);
					listCity=(ArrayList<City>) query.list();
					
					if(listCity.size()>0){
						 citybean=listCity.get(0);
						 name=citybean.getCityName();
					}
					
					
				}
				catch(Exception e)
				{
					log.log(Level.SEVERE,e.getMessage());
					e.printStackTrace();
				}finally{
					HibernateFactory.close(session);
				}
				
				
				return name;
		}
		
		
		private static String getAgentSsc(String ssc) {
			// TODO Auto-generated method stub
			 log.log(Level.INFO,"ImoUtilitiData --> getAgentSsc");
			 Session session= null;
			 ArrayList<Ssc> listSsc = new ArrayList<Ssc>();
			 String name="";
				try
				{
					
					session = HibernateFactory.openSession();
					Ssc sscbean=null;
					Query query=session.createQuery(" from Ssc where sscFullName=:fullName ");
					query.setParameter("fullName", ssc);
					listSsc=(ArrayList<Ssc>) query.list();
					
					if(listSsc.size()>0){
						sscbean=listSsc.get(0);
						 name=sscbean.getSscName();
					}
					
				}
				catch(Exception e)
				{
					log.log(Level.SEVERE,e.getMessage());
					e.printStackTrace();
					LogsMaintenance logsMain=new LogsMaintenance();
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					logsMain.insertLogs("IMOUtilityData",Level.SEVERE+"",errors.toString());
				}finally{
					HibernateFactory.close(session);
				}
				
				
				return name;
		}
		
		private static String getAgentOffice(String office) {
			// TODO Auto-generated method stub
			 log.log(Level.INFO,"ImoUtilitiData --> getAgentOffice");
			 Session session= null;
			 ArrayList<Office> listOffice = new ArrayList<Office>();
			 String name="";
				try
				{
					
					session = HibernateFactory.openSession();
					Office officebean=null;
					Query query=session.createQuery(" from Office where officeFullName=:fullName ");
					query.setParameter("fullName", office.trim());
					listOffice=(ArrayList<Office>) query.list();
					
					if(listOffice.size()>0){
						officebean=listOffice.get(0);
						 name=officebean.getOfficeName();
					}
					
				}
				catch(Exception e)
				{
					log.log(Level.SEVERE,e.getMessage());
					e.printStackTrace();
					LogsMaintenance logsMain=new LogsMaintenance();
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					logsMain.insertLogs("IMOUtilityData",Level.SEVERE+"",errors.toString());
				}finally{
					HibernateFactory.close(session);
				}
				
				
				return name;
		}

		public ArrayList<Department> getAllDepartment() {
			// TODO Auto-generated method stub
			 log.log(Level.INFO,"ImoUtilitiData --> getAllDepartment");
			 ArrayList<Department> arrActivity = new ArrayList<Department>();
			 Session session= null;
				try
				{
					
					session = HibernateFactory.openSession();
					
					Query query = session.createQuery(" from Department  where status = 1 ORDER BY orderCode,deptName");
					arrActivity=(ArrayList<Department>) query.setCacheable(true).list();
					
				}
				catch(Exception e)
				{
					log.log(Level.SEVERE,e.getMessage());
					e.printStackTrace();
					LogsMaintenance logsMain=new LogsMaintenance();
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					logsMain.insertLogs("IMOUtilityData",Level.SEVERE+"",errors.toString());
				}finally{
					HibernateFactory.close(session);
				}
				
				
				return arrActivity;
			
		}
	
		public String getBuCode(int buCode) {
			// TODO Auto-generated method stub
			log.log(Level.INFO,"UtilitesMaintanence --> getBuCode");
			
			ArrayList<Bu> arrActivity = new ArrayList<Bu>();
			Session session= null;
			String buName="";
				try
				{
					
					session = HibernateFactory.openSession();
					
					Query query = session.createQuery(" from Bu where buCode=:code and status = 1");
					query.setParameter("code", buCode);
					arrActivity= (ArrayList<Bu>) query.list();
					
					Bu bu=new Bu();
					if(arrActivity.size()!=0){
						bu=(Bu)arrActivity.get(0);
						buName=SecurityAPI.encodeHTML(bu.getBuName());
					}else{
						buName="Select";
					}
					
					
				}
				catch(Exception e)
				{
					log.log(Level.SEVERE,e.getMessage());
					e.printStackTrace();
					LogsMaintenance logsMain=new LogsMaintenance();
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					logsMain.insertLogs("IMOUtilityData",Level.SEVERE+"",errors.toString());
				}finally{
					HibernateFactory.close(session);
				}

			return buName;
		}
		public String getDistCode(int distCode) {
			// TODO Auto-generated method stub
			log.log(Level.INFO,"UtilitesMaintanence --> getDistCode");
			Session session= null;
			ArrayList<District> arrActivity = new ArrayList<District>();
			String distName="";
				try
				{
					
					session = HibernateFactory.openSession();
					
					Query query = session.createQuery(" from District where districtCode=:code and status = 1");
					query.setParameter("code", distCode);
					arrActivity= (ArrayList<District>) query.list();
					
					District dist=new District();
					if(arrActivity.size()!=0){
						dist=(District)arrActivity.get(0);
						distName=SecurityAPI.encodeHTML(dist.getDistrictName());
					}else{
						distName="ALL";
					}
				}
				catch(Exception e)
				{
					log.log(Level.SEVERE,e.getMessage());
					e.printStackTrace();
					LogsMaintenance logsMain=new LogsMaintenance();
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					logsMain.insertLogs("IMOUtilityData",Level.SEVERE+"",errors.toString());
				}finally{
					HibernateFactory.close(session);
				}

			return distName;
		}
		
		
		public String getBranchCode(int distCode) {
			// TODO Auto-generated method stub
			log.log(Level.INFO,"UtilitesMaintanence --> getBranchCode");
			Session session= null;
			ArrayList<Branch> arrActivity = new ArrayList<Branch>();
			String branchName="";
				try
				{
					
					session = HibernateFactory.openSession();
					
					Query query = session.createQuery(" from Branch where branchCode=:code and status = 1");
					query.setParameter("code", distCode);
					arrActivity= (ArrayList<Branch>) query.list();
					
					Branch branch=new Branch();
					if(arrActivity.size()!=0){
						branch=(Branch)arrActivity.get(0);
						branchName=SecurityAPI.encodeHTML(branch.getBranchFullName());
					}else{
						branchName="ALL";
					}
				}
				catch(Exception e)
				{
					log.log(Level.SEVERE,e.getMessage());
					e.printStackTrace();
					LogsMaintenance logsMain=new LogsMaintenance();
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					logsMain.insertLogs("IMOUtilityData",Level.SEVERE+"",errors.toString());
				}finally{
					HibernateFactory.close(session);
				}

			return branchName;
		}
		
		public String getCityName(String cityCode) {
			// TODO Auto-generated method stub
			log.log(Level.INFO,"UtilitesMaintanence --> getCityName");
			
			ArrayList<City> arrActivity = new ArrayList<City>();
			String name="";
			Session session= null;
				try
				{
					
					session = HibernateFactory.openSession();
					
					
					Query query = session.createQuery(" from City where cityName=:code");
					query.setParameter("code", cityCode);
					arrActivity= (ArrayList<City>) query.list();
					
					City dist=new City();
					if(arrActivity.size()!=0){
						dist=(City)arrActivity.get(0);
						name=SecurityAPI.encodeHTML(dist.getCityFullName());
					}else{
						name="ALL";
					}
				}
				catch(Exception e)
				{
					log.log(Level.SEVERE,e.getMessage());
					e.printStackTrace();
				}finally{
					HibernateFactory.close(session);
				}

			return name;
		}
		public String getSscName(String sscCode) {
			// TODO Auto-generated method stub
			log.log(Level.INFO,"UtilitesMaintanence --> getSscName");
			Session session= null;
			ArrayList<Ssc> arrActivity = new ArrayList<Ssc>();
			String name="";
				try
				{
					
					session = HibernateFactory.openSession();
					
					Query query = session.createQuery(" from Ssc where sscName=:ssccode ");
					query.setParameter("ssccode", sscCode);
					arrActivity= (ArrayList<Ssc>) query.list();
					
					Ssc dist=new Ssc();
					if(arrActivity.size()!=0){
						dist=(Ssc)arrActivity.get(0);
						name=SecurityAPI.encodeHTML(dist.getSscFullName());
					}else{
						name="ALL";
					}
				}
				catch(Exception e)
				{
					log.log(Level.SEVERE,e.getMessage());
					e.printStackTrace();
					LogsMaintenance logsMain=new LogsMaintenance();
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					logsMain.insertLogs("IMOUtilityData",Level.SEVERE+"",errors.toString());
				}finally{
					HibernateFactory.close(session);
				}

			return name;
		}
		
		public String getOfficeName(String code) {
			// TODO Auto-generated method stub
			log.log(Level.INFO,"UtilitesMaintanence --> getOfficeName");
			Session session= null;
			ArrayList<Office> arrActivity = new ArrayList<Office>();
			String name="";
				try
				{
					
					session = HibernateFactory.openSession();
					
					Query query = session.createQuery(" from Office where officeName=:code ");
					query.setParameter("code", code);
					arrActivity= (ArrayList<Office>) query.list();
					
					Office dist=new Office();
					if(arrActivity.size()!=0){
						dist=(Office)arrActivity.get(0);
						name=SecurityAPI.encodeHTML(dist.getOfficeFullName());
					}else{
						name="ALL";
					}
				}
				catch(Exception e)
				{
					log.log(Level.SEVERE,e.getMessage());
					e.printStackTrace();
				}finally{
					HibernateFactory.close(session);
				}

			return name;
		}
		
		
		public String getDeptName(int department) {
			// TODO Auto-generated method stub
			log.log(Level.INFO,"UtilitesMaintanence --> getDeptName");
			
			ArrayList<Department> arrActivity = new ArrayList<Department>();
			String name="";
			Session session= null;
				try
				{
					
					session = HibernateFactory.openSession();
					
					Query query = session.createQuery(" from Department where deptCode="+department+"  and status = 1 ");
				
					arrActivity= (ArrayList<Department>) query.list();
					
					Department dist=new Department();
					if(arrActivity.size()!=0){
						dist=(Department)arrActivity.get(0);			
						name=SecurityAPI.encodeHTML(dist.getDeptName());
					}
				}
				catch(Exception e)
				{
					log.log(Level.SEVERE,e.getMessage());
					e.printStackTrace();
					LogsMaintenance logsMain=new LogsMaintenance();
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					logsMain.insertLogs("IMOUtilityData",Level.SEVERE+"",errors.toString());
				}finally{
					HibernateFactory.close(session);
				}

			return name;
		}
		/*public String getUserName(int userCode) {
			log.log(Level.INFO,"UtilitesMaintanence --> getUserName");
			ArrayList arrActivity = new ArrayList();
			String name="";
			Session session= null;
				try
				{
					session = HibernateFactory.openSession();
					
					Query query = session.createQuery(" select staffName from User where user_no="+userCode+"  and status = 1 ");
					arrActivity= (ArrayList) query.list();
					if(arrActivity.size()!=0){
						name=(String)arrActivity.get(0);			
						
					}
				}
				catch(Exception e)
				{
					log.log(Level.SEVERE,e.getMessage());
					e.printStackTrace();
				}finally{
					HibernateFactory.close(session);
				}

			return name;
		}*/
		
		public int getBuCodeBasedOnBuName(String buName) {
			ArrayList arrActivity = new ArrayList();
			int buCode=0;
			Session session= null;
				try
				{
					session = HibernateFactory.openSession();
					
					Query query= session.createQuery("select buCode from Bu where buName=:buname   and status = 1");
					query.setParameter("buname", buName);
					arrActivity= (ArrayList) query.list();
					if(arrActivity.size()>0){
						buCode=(Integer)arrActivity.get(0);			
						
					}
				}
				catch(Exception e)
				{
					log.log(Level.SEVERE,e.getMessage());
					e.printStackTrace();
					LogsMaintenance logsMain=new LogsMaintenance();
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					logsMain.insertLogs("IMOUtilityData",Level.SEVERE+"",errors.toString());
				}finally{
					HibernateFactory.close(session);
				}

			return buCode;
		}
		public int getDistrictCodeBasedOnDistrictName(String distName) {
			ArrayList arrActivity = new ArrayList();
			int distCode=0;
			Session session= null;
				try
				{
					session = HibernateFactory.openSession();
					
					Query query= session.createQuery("select districtCode from District where districtName=:distname  and status = 1");
					query.setParameter("distname", distName);
					arrActivity= (ArrayList) query.list();
					if(arrActivity.size()>0){
						distCode=(Integer)arrActivity.get(0);			
						
					}
				}
				catch(Exception e)
				{
					log.log(Level.SEVERE,e.getMessage());
					e.printStackTrace();
					LogsMaintenance logsMain=new LogsMaintenance();
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					logsMain.insertLogs("IMOUtilityData",Level.SEVERE+"",errors.toString());
				}finally{
					HibernateFactory.close(session);
				}

			return distCode;
		}
		public String getCityCodeBasedOnDistrictName(String cityName) {
			ArrayList arrActivity = new ArrayList();
			String cityCode="0";
			Session session= null;
				try
				{
					session = HibernateFactory.openSession();
					
					Query query= session.createQuery("select cityName from City where cityFullName=:cityname  ");
					query.setParameter("cityname", cityName);
					arrActivity= (ArrayList) query.list();
					if(arrActivity.size()>0){
						cityCode=(String) arrActivity.get(0);			
						
					}
				}
				catch(Exception e)
				{
					log.log(Level.SEVERE,e.getMessage());
					e.printStackTrace();
					LogsMaintenance logsMain=new LogsMaintenance();
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					logsMain.insertLogs("IMOUtilityData",Level.SEVERE+"",errors.toString());
				}finally{
					HibernateFactory.close(session);
				}

			return cityCode;
		}
		
		public int getBranchCodeBasedOnBranchName(String branchName) {
			ArrayList arrActivity = new ArrayList();
			int branchCode=0;
			Session session= null;
				try
				{
					session = HibernateFactory.openSession();
					
					Query query= session.createQuery("select branchCode from Branch where branchName=:name  and status = 1");
					query.setParameter("name", branchName);
					arrActivity= (ArrayList) query.list();
					if(arrActivity.size()>0){
						branchCode=(Integer)arrActivity.get(0);			
						
					}
				}
				catch(Exception e)
				{
					log.log(Level.SEVERE,e.getMessage());
					e.printStackTrace();
					LogsMaintenance logsMain=new LogsMaintenance();
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					logsMain.insertLogs("IMOUtilityData",Level.SEVERE+"",errors.toString());
				}finally{
					HibernateFactory.close(session);
				}

			return branchCode;
		}
		
		
		public ArrayList<Branch> getBranchCodeListBasedOnBranchName(String branchName) {
			ArrayList arrActivity = new ArrayList();
			int branchCode=0;
			Session session= null;
				try
				{
					session = HibernateFactory.openSession();
					
					Query query= session.createQuery("from Branch where branchName=:name  and status = 1");
					query.setParameter("name", branchName);
					arrActivity= (ArrayList<Branch>) query.list();
					if(arrActivity.size()>0){
						return arrActivity;
											
					}
					
				}
				catch(Exception e)
				{
					log.log(Level.SEVERE,e.getMessage());
					e.printStackTrace();
					LogsMaintenance logsMain=new LogsMaintenance();
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					logsMain.insertLogs("IMOUtilityData",Level.SEVERE+"",errors.toString());
				}finally{
					HibernateFactory.close(session);
				}

			return arrActivity;
		}
		
		public String getSSCCodeBasedOnSSCName(String sscName) {
			ArrayList arrActivity = new ArrayList();
			String sscCode="0";
			Session session= null;
				try
				{
					session = HibernateFactory.openSession();
					
					Query query= session.createQuery("select sscName from Ssc where sscFullName=:sscname");
					query.setParameter("sscname", sscName);
					arrActivity= (ArrayList) query.list();
					if(arrActivity.size()>0){
						sscCode=(String)arrActivity.get(0);			
						
					}
				}
				catch(Exception e)
				{
					log.log(Level.SEVERE,e.getMessage());
					e.printStackTrace();
					LogsMaintenance logsMain=new LogsMaintenance();
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					logsMain.insertLogs("IMOUtilityData",Level.SEVERE+"",errors.toString());
				}finally{
					HibernateFactory.close(session);
				}

			return sscCode;
		}
		
		
		public String getOfficeCodeBasedOnSSCName(String name) {
			ArrayList arrActivity = new ArrayList();
			String sscCode="0";
			Session session= null;
				try
				{
					session = HibernateFactory.openSession();
					
					Query query= session.createQuery("select officeName from Office where officeFullName=:name ");
					query.setParameter("name", name);
					arrActivity= (ArrayList) query.list();
					if(arrActivity.size()>0){
						sscCode=(String)arrActivity.get(0);			
						
					}
				}
				catch(Exception e)
				{
					log.log(Level.SEVERE,e.getMessage());
					e.printStackTrace();
					LogsMaintenance logsMain=new LogsMaintenance();
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					logsMain.insertLogs("IMOUtilityData",Level.SEVERE+"",errors.toString());
				}finally{
					HibernateFactory.close(session);
				}

			return sscCode;
		}
		
		
		public int getdeptCodeBasedOndeptName(String deptName) {
			ArrayList arrActivity = new ArrayList();
			int deptCode=0;
			Session session= null;
				try
				{
					session = HibernateFactory.openSession();
					
					Query query= session.createQuery("select deptCode from Department where deptName=:deptname  and status = 1");
					query.setParameter("deptname", deptName);
					arrActivity= (ArrayList) query.list();
					if(arrActivity.size()>0){
						deptCode=(Integer)arrActivity.get(0);			
						
					}
				}
				catch(Exception e)
				{
					log.log(Level.SEVERE,e.getMessage());
					e.printStackTrace();
					LogsMaintenance logsMain=new LogsMaintenance();
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					logsMain.insertLogs("IMOUtilityData",Level.SEVERE+"",errors.toString());
				}finally{
					HibernateFactory.close(session);
				}

			return deptCode;
		}
		public void summaryReport(StringBuffer strbuffer, HttpServletRequest req) {
			// TODO Auto-generated method stub
			//  FileWriter fw=null;
		        BufferedWriter writer=null;
		        String fileName="";
		        try{
		        	 DateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
		        	 fileName=req.getRealPath(File.separator)+"resources"+File.separator+"upload"+File.separator + "summaryReport_"+df.format(new Date())+".txt";
		        	 log.log(Level.INFO, "File Path --> "+ fileName);
				        File file = new File(fileName);  
				        
				        if ( !file.exists() ){
				        	
				            file.createNewFile();
				        }
				       // fw = new FileWriter(file);
				       // writer = new BufferedWriter( fw );
				        writer = new BufferedWriter( new OutputStreamWriter(new FileOutputStream(file), "UTF-8") );
				        writer.write(strbuffer+"");
				        writer.flush();
				        writer.close();
				      //  fw.close();
				     
				        
				        String path=("resources/upload/"+ "summaryReport_"+df.format(new Date())+".txt");
			           req.getSession().setAttribute("Summarypath", path);
	     
		        }
		        catch(Exception e)
		        {
		        
		        	log.log(Level.SEVERE,"Error : "+e.getMessage());
		        	e.printStackTrace();
		        	LogsMaintenance logsMain=new LogsMaintenance();
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					logsMain.insertLogs("IMOUtilityData",Level.SEVERE+"",errors.toString());
		        }
		        finally
		        {
		 			try{
						if(writer != null){
							writer.close();
						}
						/*if(fw!=null)
							fw.close();*/
						
					}catch(IOException ioExc){
						ioExc.printStackTrace();
						
					}
		        }
		}
		public static ArrayList<Channel>  getActiveChannels(HttpServletRequest req){
			 ArrayList<Channel> listData =  new  ArrayList<Channel>();
			 Session session= null;
				try{
				
					session = HibernateFactory.openSession();
				   HashMap<String,String> map=new HashMap<String,String>();
				   listData =  (ArrayList<Channel>) session.createQuery("FROM Channel where status = 1" ).list();
					
					
					
					if(listData !=null  && listData.size() > 0){
						  for(Channel ch:listData){ 
								  map.put(ch.getChannelCode()+"",ch.getChannelName());
							  }
						  }
					
					req.getSession().setAttribute("channelMap",map);
					
					}catch(Exception e)
					{
						log.log(Level.SEVERE, e.getMessage());
						e.printStackTrace();
						LogsMaintenance logsMain=new LogsMaintenance();
						StringWriter errors = new StringWriter();
						e.printStackTrace(new PrintWriter(errors));
						logsMain.insertLogs("IMOUtilityData",Level.SEVERE+"",errors.toString());
					} finally{
						try{
							HibernateFactory.close(session);
						}catch(Exception e){
							log.log(Level.SEVERE, e.getMessage());
							e.printStackTrace();
						}
				}
			    return listData;
		}
		
	public int getchannelCodeBasedOnchannelName(String ChannelName) {
		ArrayList arrActivity = new ArrayList();
		int channelCode=0;
		Session session= null;
			try
			{
				session = HibernateFactory.openSession();
				
				Query query= session.createQuery("select channelCode from Channel where channelName=:Name  and status = 1");
				query.setParameter("Name", ChannelName);
				arrActivity= (ArrayList) query.list();
				if(arrActivity.size()>0){
					channelCode=(Integer)arrActivity.get(0);			
					
				}
			}
			catch(Exception e)
			{
				log.log(Level.SEVERE,e.getMessage());
				e.printStackTrace();
				LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("IMOUtilityData",Level.SEVERE+"",errors.toString());
			}finally{
				HibernateFactory.close(session);
			}

		return channelCode;
	}
	
	public void getRefresh(){
		log.log(Level.INFO, "ImoUtilityDate -->  getRefresh");
		
		Session session = null;
		Transaction tx;
		List<Holiday> listHoliday=null;
		List<E_Greeting> listEGreeting=null;
		List<Interview> listInterview=null;
		List<Presenter> listPresenter=null;
		List<Announcement> listAnnouncement=null;
		List<Event> listEvent=null;
		List<Bu> listbu=null;
		List<District> listdist=null;
		List<Branch> listbranch=null;
		List<City> listcity=null;
		List<Ssc> listSsc=null;
		List<Office> listOffice=null;
		ArrayList al=new ArrayList();
		
		try{
			Query query=null;
			session = HibernateFactory.openSession();
			
			Date sdate=new Date();
			log.log(Level.INFO,"Start Time "+sdate.getTime());
		
			listbu = session.createQuery("FROM Bu where status = 1").setCacheable(true).list();		
			listdist=session.createQuery("FROM District where status = 1").setCacheable(true).list();
			listbranch=session.createQuery("FROM Branch where status = 1").setCacheable(true).list();
			listcity=session.createQuery("FROM City where status = 1").setCacheable(true).list();
			listSsc=session.createQuery("FROM Ssc where status = 1").setCacheable(true).list();
			listOffice=session.createQuery("FROM Office where status = 1").setCacheable(true).list();
			
			
			listbu = session.createQuery("FROM Bu where status = 1 order by buName").setCacheable(true).list();		
			listdist=session.createQuery("FROM District where status = 1 order by districtName").setCacheable(true).list();
			listbranch=session.createQuery("FROM Branch where status = 1 order by branchName").setCacheable(true).list();
			listcity=session.createQuery("FROM City where status = 1 order by cityName").setCacheable(true).list();
			listSsc=session.createQuery("FROM Ssc where status = 1 order by sscName").setCacheable(true).list();
			listOffice=session.createQuery("FROM Office where status = 1 order by sscName").setCacheable(true).list();
			
			for (int i = 0; i < listbu.size(); i++) {
				
				Bu bu=listbu.get(i);

//				query=  session.createQuery("FROM Holiday where status = 1  and buCode=:bucode and district=0 ");
//				query.setParameter("bucode", bu.getBuCode());
//				listHoliday=query.setCacheable(true).list();

				query=session.createQuery("FROM Interview where status = 1  and buCode=:bucode and district=0 ");
				query.setParameter("bucode", bu.getBuCode());
				listInterview=query.setCacheable(true).list();

//				listHoliday =  session.createQuery("FROM Holiday where status = 1  and buCode="+ bu.getBuCode()+" and district=0 ").setFirstResult(0).setCacheable(true).list();
//				al.add(listHoliday);
				
				listInterview=session.createQuery("FROM Interview where status = 1 and buCode="+ bu.getBuCode()+" and district=0 ").setFirstResult(0).setCacheable(true).list();
				listPresenter=session.createQuery("FROM Presenter where status = 1 and buCode="+ bu.getBuCode()+" and district=0 ").setFirstResult(0).setCacheable(true).list();
				listAnnouncement=session.createQuery("FROM Announcement where status = 1 and buCode="+ bu.getBuCode()+" and district=0 ").setFirstResult(0).setCacheable(true).list();
				listEvent=session.createQuery("FROM Event where status = 1 and buCode="+ bu.getBuCode()+" and district=0 ").setFirstResult(0).setCacheable(true).list();

				
				query=session.createQuery("FROM Presenter where status = 1  and buCode=:bucode and district=0 ");
				query.setParameter("bucode", bu.getBuCode());
				listPresenter=query.setCacheable(true).list();
				
				query=session.createQuery("FROM Announcement where status = 1 and buCode=:bucode and district=0 ");
				query.setParameter("bucode", bu.getBuCode());
				listAnnouncement=query.setCacheable(true).list();
				
				query=session.createQuery("FROM Event where status = 1  and buCode=:bucode and district=0 ");
				query.setParameter("bucode", bu.getBuCode());
				listEvent=query.setCacheable(true).list();
				
				query = session.createQuery("FROM Bu where status = 1 and buCode=:bucode ");		
				query.setParameter("bucode", bu.getBuCode());
				ArrayList<Bu> bulist=(ArrayList<Bu>) query.setCacheable(true).list();
				
				for (int j = 0; j < listdist.size(); j++) {
					
					District dist=listdist.get(j);
//					query=  session.createQuery("FROM Holiday where status = 1  and district=:distcode and branchCode=0 ");
//					query.setParameter("distcode", dist.getDistrictCode());
//					listHoliday=query.setCacheable(true).list();
					
					query=session.createQuery("FROM Interview where status = 1  and district=:distcode and branchCode=0 ");
					query.setParameter("distcode", dist.getDistrictCode());
					listInterview=query.setCacheable(true).list();
					
					query=session.createQuery("FROM Presenter where status = 1  and district=:distcode and branchCode=0 ");
					query.setParameter("distcode", dist.getDistrictCode());
					listPresenter=query.setCacheable(true).list();
					
					
					query=session.createQuery("FROM Announcement where status = 1 and district=:distcode and branchCode=0 ");
					query.setParameter("distcode", dist.getDistrictCode());
					listAnnouncement=query.setCacheable(true).list();

					query=session.createQuery("FROM Event where status = 1  and district=:distcode and branchCode=0 ");
					query.setParameter("distcode", dist.getDistrictCode());
					listEvent=query.setCacheable(true).list();
					
					
					query=session.createQuery("FROM District where status = 1 and districtCode=:distcode ");
					query.setParameter("distcode", dist.getDistrictCode());
					
					ArrayList<District> distlist=(ArrayList<District>) query.setCacheable(true).list();
					for (int a = 0; a < listbranch.size(); a++) {
						Branch branch=listbranch.get(a);
						
//						query=  session.createQuery("FROM Holiday where status = 1  and branchCode=:branch and cityCode=0 ");
//						query.setParameter("branch", branch.getBranchCode());
//						listHoliday=query.setCacheable(true).list();
						
						query=session.createQuery("FROM Interview where status = 1  and branchCode=:branch and cityCode=0 ");
						query.setParameter("branch",branch.getBranchCode());
						listInterview=query.setCacheable(true).list();
						
						query=session.createQuery("FROM Presenter where status = 1  and branchCode=:branch and cityCode=0 ");
						query.setParameter("branch",branch.getBranchCode());
						listPresenter=query.setCacheable(true).list();
						
						
						query=session.createQuery("FROM Announcement where status = 1 and branchCode=:branch and cityCode=0 ");
						query.setParameter("branch", branch.getBranchCode());
						listAnnouncement=query.setCacheable(true).list();

						query=session.createQuery("FROM Event where status = 1  and branchCode=:branch and cityCode=0 ");
						query.setParameter("branch",branch.getBranchCode());
						listEvent=query.setCacheable(true).list();
						
						
						query=session.createQuery("FROM Branch where status = 1 and branchCode=:branch ");
						query.setParameter("branch", branch.getBranchCode());
						
						
						
					
					
					for (int k = 0; k < listcity.size(); k++) {
						
						City city=listcity.get(k);
						
//						query=  session.createQuery("FROM Holiday where status = 1  and cityCode=:citycode and sscCode=0 ");
//						query.setParameter("citycode", city.getCityCode());
//						listHoliday=query.setCacheable(true).list();
						
						query=session.createQuery("FROM Interview where status = 1 and cityCode=:citycode and sscCode=0 ");
						query.setParameter("citycode", city.getCityCode());
						listInterview=query.setCacheable(true).list();
						
						query=session.createQuery("FROM Presenter where status = 1 and cityCode=:citycode and sscCode=0 ");
						query.setParameter("citycode", city.getCityCode());
						listPresenter=query.setCacheable(true).list();

						query=session.createQuery("FROM Announcement where status = 1  and cityCode=:citycode and sscCode=0 ");
						query.setParameter("citycode", city.getCityCode());
						listAnnouncement=query.setCacheable(true).list();
						
						
						query=session.createQuery("FROM Event where status = 1   and cityCode=:citycode and sscCode=0 ");
						query.setParameter("citycode", city.getCityCode());
						listEvent=query.setCacheable(true).list();
						
						query=session.createQuery("FROM City where status = 1 and  cityCode=:citycode ");
						query.setParameter("citycode", city.getCityCode());
						ArrayList<City> citylist=(ArrayList<City>) query.setCacheable(true).list();
						
						for (int l = 0; l < listSsc.size(); l++) {
						
							Ssc ssc=listSsc.get(l);
//							query=  session.createQuery("FROM Holiday where status = 1   and  sscCode=:ssccode ");
//							query.setParameter("ssccode", ssc.getSscCode());
//							listHoliday=query.setCacheable(true).list();
//							
							query=session.createQuery("FROM Interview where status = 1  and  sscCode=:ssccode ");
							query.setParameter("ssccode", ssc.getSscCode());
							listInterview=query.setCacheable(true).list();
							
							query=session.createQuery("FROM Presenter where status = 1  and  sscCode=:ssccode ");
							query.setParameter("ssccode", ssc.getSscCode());
							listPresenter=query.setCacheable(true).list();
							
							
							query=session.createQuery("FROM Announcement where status = 1  and  sscCode=:ssccode ");
							query.setParameter("ssccode", ssc.getSscCode());
							listAnnouncement=query.setCacheable(true).list();
							
							query=session.createQuery("FROM Event where status = 1   and  sscCode=:ssccode ");
							query.setParameter("ssccode", ssc.getSscCode());
							listEvent=query.setCacheable(true).list();
							
							query= session.createQuery("FROM Ssc where status = 1 and sscCode=:ssccode");
							query.setParameter("ssccode", ssc.getSscCode());
							ArrayList<Ssc> ssclist=(ArrayList<Ssc>) query.setCacheable(true).list();
							
							
							for (int m = 0; m < listOffice.size(); m++) {
								
								
								Office office=listOffice.get(m);
//								query=  session.createQuery("FROM Holiday where status = 1   and  officeCode=:code ");
//								query.setParameter("ssccode", ssc.getSscCode());
//								listHoliday=query.setCacheable(true).list();
								
								query=session.createQuery("FROM Interview where status = 1  and   officeCode=:code ");
								query.setParameter("code", office.getOfficeCode());
								listInterview=query.setCacheable(true).list();
								
								query=session.createQuery("FROM Presenter where status = 1  and  officeCode=:code ");
								query.setParameter("code", office.getOfficeCode());
								listPresenter=query.setCacheable(true).list();
								
								
								query=session.createQuery("FROM Announcement where status = 1  and   officeCode=:code ");
								query.setParameter("code",office.getOfficeCode());
								listAnnouncement=query.setCacheable(true).list();
								
								query=session.createQuery("FROM Event where status = 1   and  officeCode=:code ");
								query.setParameter("code",office.getOfficeCode());
								listEvent=query.setCacheable(true).list();
								
								query= session.createQuery("FROM Office where status = 1 and  officeCode=:code");
								query.setParameter("code", office.getOfficeCode());
								ArrayList<Office> officelist=(ArrayList<Office>) query.setCacheable(true).list();
								
								
							}// close Office for loop
							
							
							
							
							
						}// close ssc for loop
						
						
					}//close city for loop
	
				 }// branch for loop
				}//close district for loop
				
				
			}//close bu for loop
			
			listEGreeting = session.createQuery("FROM E_Greeting where status = 1").setCacheable(true).list();
			Date edate=new Date();
			log.log(Level.INFO,"Total Time "+(edate.getTime()- sdate.getTime()));
			
			session.flush();
			session.clear();
			session.close();
			
			
		}catch(Exception e)
		{
			
			log.log(Level.INFO, "ImoUtilityDate -->  getRefresh --> Exception "+e);
			e.printStackTrace();
			LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("IMOUtilityData",Level.SEVERE+"",errors.toString());
			
		}finally{
			try{
				HibernateFactory.close(session);
			}catch(Exception e){
				
				e.printStackTrace();
				
			}
	
		}
	
	}
	
	
	public void getEGreetingCache(){

		log.log(Level.INFO, "ImoUtilityDate -->  getEGreetingCache");
		
		Session session = null;
		Transaction tx;
	
		List<E_Greeting> listEGreeting=null;
	
		
		try{
			Query query=null;
			session = HibernateFactory.openSession();
			
			Date sdate=new Date();
			log.log(Level.INFO,"Start Time "+sdate.getTime());
		
			listEGreeting = session.createQuery("FROM E_Greeting where status = 1").setCacheable(true).list();
			Date edate=new Date();
			log.log(Level.INFO,"Total Time "+(edate.getTime()- sdate.getTime()));
			
			session.flush();
			session.clear();
			session.close();
			
			
		}catch(Exception e)
		{
			
			log.log(Level.INFO, "ImoUtilityDate -->  getEGreetingCache --> Exception "+e);
			e.printStackTrace();
			LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("IMOUtilityData",Level.SEVERE+"",errors.toString());
			
		}finally{
			try{
				HibernateFactory.close(session);
			}catch(Exception e){
				
				e.printStackTrace();
			}
	
		}
	
	
	}
	
	public void getEventCache(){

		log.log(Level.INFO, "ImoUtilityDate -->  getEventCache");
		
		Session session = null;
		Transaction tx;
		List<Event> listEvent=null;
		List<Bu> listbu=null;
		List<District> listdist=null;
		List<Branch> listbranch=null;
		List<City> listcity=null;
		List<Ssc> listSsc=null;
		ArrayList al=new ArrayList();
		
		try{
			Query query=null;
			session = HibernateFactory.openSession();
			
			Date sdate=new Date();
			log.log(Level.INFO,"Start Time "+sdate.getTime());
		
			listbu = session.createQuery("FROM Bu where status = 1").setCacheable(true).list();		
			listdist=session.createQuery("FROM District where status = 1").setCacheable(true).list();
			listbranch=session.createQuery("FROM Branch where status = 1").setCacheable(true).list();
			listcity=session.createQuery("FROM City ").setCacheable(true).list();
			listSsc=session.createQuery("FROM Ssc ").setCacheable(true).list();
			
			listbu = session.createQuery("FROM Bu where status = 1 order by buName").setCacheable(true).list();		
			listdist=session.createQuery("FROM District where status = 1 order by districtName").setCacheable(true).list();
			listbranch=session.createQuery("FROM Branch where status = 1 order by branchName").setCacheable(true).list();
			listcity=session.createQuery("FROM City  order by cityName").setCacheable(true).list();
			listSsc=session.createQuery("FROM Ssc   order by sscName").setCacheable(true).list();
			
			for (int i = 0; i < listbu.size(); i++) {
				
				Bu bu=listbu.get(i);
				
				query=session.createQuery("FROM Event where status = 1  and buCode=:bucode and district=0 ");
				query.setParameter("bucode", bu.getBuCode());
				listEvent=query.setCacheable(true).list();
				
				query = session.createQuery("FROM Bu where status = 1 and buCode=:bucode ");		
				query.setParameter("bucode", bu.getBuCode());
				ArrayList<Bu> bulist=(ArrayList<Bu>) query.setCacheable(true).list();
				
				for (int j = 0; j < listdist.size(); j++) {
					
					District dist=listdist.get(j);
				
					query=session.createQuery("FROM Event where status = 1  and district=:distcode and branchCode=0 ");
					query.setParameter("distcode", dist.getDistrictCode());
					listEvent=query.setCacheable(true).list();
					
					
					query=session.createQuery("FROM District where status = 1 and districtCode=:distcode ");
					query.setParameter("distcode", dist.getDistrictCode());
					ArrayList<District> distlist=(ArrayList<District>) query.setCacheable(true).list();
					
					for (int a = 0; a < listbranch.size(); a++) {
						Branch branch=listbranch.get(a);
						
						query=session.createQuery("FROM Event where status = 1  and branchCode=:branch and cityCode=:code ");
						query.setParameter("branch", branch.getBranchCode());
						query.setParameter("code", "0");
						listEvent=query.setCacheable(true).list();
						
						
						query=session.createQuery("FROM Branch where status = 1 and branchCode=:branch ");
						query.setParameter("branch",branch.getBranchCode());
						ArrayList<Branch> branchlist=(ArrayList<Branch>) query.setCacheable(true).list();
						
					
					
					for (int k = 0; k < listcity.size(); k++) {
						
						City city=listcity.get(k);
						
						
						query=session.createQuery("FROM Event where status = 1   and cityCode=:citycode and sscCode=:code ");
						query.setParameter("citycode", city.getCityName());
						query.setParameter("code", "0");
						listEvent=query.setCacheable(true).list();
						
						query=session.createQuery("FROM City where  cityName=:citycode ");
						query.setParameter("citycode", city.getCityName());
						ArrayList<City> citylist=(ArrayList<City>) query.setCacheable(true).list();
						
						for (int l = 0; l < listSsc.size(); l++) {
						
							Ssc ssc=listSsc.get(l);
							
							query=session.createQuery("FROM Event where status = 1   and  sscName=:ssccode ");
							query.setParameter("ssccode", ssc.getSscName());
							listEvent=query.setCacheable(true).list();
							
							query= session.createQuery("FROM Ssc where sscName=:ssccode");
							query.setParameter("ssccode", ssc.getSscName());
							ArrayList<Ssc> ssclist=(ArrayList<Ssc>) query.setCacheable(true).list();
							
						}// close ssc for loop
						
						
					}//close city for loop
				}// branch for loop
				}//close district for loop
				
				
			}//close bu for loop
			
			//listEGreeting = session.createQuery("FROM E_Greeting where status = 1").setCacheable(true).list();
			Date edate=new Date();
			log.log(Level.INFO,"Total Time "+(edate.getTime()- sdate.getTime()));
			
			session.flush();
			session.clear();
			session.close();
			
			
		}catch(Exception e)
		{
			
			log.log(Level.INFO, "ImoUtilityDate -->  getEventCache --> Exception "+e);
			e.printStackTrace();
			LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("IMOUtilityData",Level.SEVERE+"",errors.toString());
			
		}finally{
			try{
				HibernateFactory.close(session);
			}catch(Exception e){
				
				e.printStackTrace();
			}
	
		}
	
	
	}
	
	
	public void getAnnouncementCache(){

		log.log(Level.INFO, "ImoUtilityDate -->  getAnnouncementCache");
		
		Session session = null;
		Transaction tx;
		List<Announcement> listAnnouncement=null;
		List<Bu> listbu=null;
		List<District> listdist=null;
		List<Branch> listbranch=null;
		List<City> listcity=null;
		List<Ssc> listSsc=null;
		List<Office> listOffice=null;
		ArrayList al=new ArrayList();
		
		try{
			Query query=null;
			session = HibernateFactory.openSession();
			
			Date sdate=new Date();
			log.log(Level.INFO,"Start Time "+sdate.getTime());
		
			listbu = session.createQuery("FROM Bu where status = 1").setCacheable(true).list();		
			listdist=session.createQuery("FROM District where status = 1").setCacheable(true).list();
			listbranch=session.createQuery("FROM Branch where status = 1").setCacheable(true).list();
			listcity=session.createQuery("FROM City ").setCacheable(true).list();
			listSsc=session.createQuery("FROM Ssc").setCacheable(true).list();
			listOffice=session.createQuery("FROM Office ").setCacheable(true).list();
			
			listOffice=session.createQuery("FROM Office  order by sscName").setCacheable(true).list();
			listbu = session.createQuery("FROM Bu where status = 1 order by buName").setCacheable(true).list();		
			listdist=session.createQuery("FROM District where status = 1 order by districtName").setCacheable(true).list();
			listbranch=session.createQuery("FROM Branch where status = 1 order by branchName").setCacheable(true).list();
			listcity=session.createQuery("FROM City  order by cityName").setCacheable(true).list();
			listSsc=session.createQuery("FROM Ssc  order by sscName").setCacheable(true).list();
			
			for (int i = 0; i < listbu.size(); i++) {
				
				Bu bu=listbu.get(i);

				
				query=session.createQuery("FROM Announcement where status = 1 and buCode=:bucode and district=0 ");
				query.setParameter("bucode", bu.getBuCode());
				listAnnouncement=query.setCacheable(true).list();
				
				
				query = session.createQuery("FROM Bu where status = 1 and buCode=:bucode ");		
				query.setParameter("bucode", bu.getBuCode());
				ArrayList<Bu> bulist=(ArrayList<Bu>) query.setCacheable(true).list();
				
				for (int j = 0; j < listdist.size(); j++) {
					
					District dist=listdist.get(j);
					
					query=session.createQuery("FROM Announcement where status = 1 and district=:distcode and branchCode=0 ");
					query.setParameter("distcode", dist.getDistrictCode());
					listAnnouncement=query.setCacheable(true).list();

					query=session.createQuery("FROM District where status = 1 and districtCode=:distcode ");
					query.setParameter("distcode", dist.getDistrictCode());
					ArrayList<District> distlist=(ArrayList<District>) query.setCacheable(true).list();
					
					for (int a = 0; a < listbranch.size(); a++) {
						Branch branch=listbranch.get(a);
						
						query=session.createQuery("FROM Announcement where status = 1  and branchCode=:branch and cityCode=:code ");
						query.setParameter("branch", branch.getBranchCode());
						query.setParameter("code", "0");
						listAnnouncement=query.setCacheable(true).list();
						
						
						query=session.createQuery("FROM Branch where status = 1 and branchCode=:branch ");
						query.setParameter("branch",branch.getBranchCode());
						ArrayList<Branch> branchlist=(ArrayList<Branch>) query.setCacheable(true).list();
						
					
					for (int k = 0; k < listcity.size(); k++) {
						
						City city=listcity.get(k);
						
						
						query=session.createQuery("FROM Announcement where status = 1  and cityCode=:citycode and sscCode=:code ");
						query.setParameter("citycode", city.getCityName());
						query.setParameter("code", "0");
						listAnnouncement=query.setCacheable(true).list();
						
						query=session.createQuery("FROM City where  cityName=:citycode ");
						query.setParameter("citycode", city.getCityName());
						ArrayList<City> citylist=(ArrayList<City>) query.setCacheable(true).list();
						
						for (int l = 0; l < listSsc.size(); l++) {
						
							Ssc ssc=listSsc.get(l);
							
							query=session.createQuery("FROM Announcement where status = 1  and  sscCode=:ssccode ");
							query.setParameter("ssccode", ssc.getSscName());
							listAnnouncement=query.setCacheable(true).list();
							
							query= session.createQuery("FROM Ssc where  sscName=:ssccode");
							query.setParameter("ssccode", ssc.getSscName());
							ArrayList<Ssc> ssclist=(ArrayList<Ssc>) query.setCacheable(true).list();
							
							
							
							
							for (int m = 0; m < listOffice.size(); m++) {
								
								
								Office office=listOffice.get(m);

								query=session.createQuery("FROM Announcement where status = 1  and   officeCode=:code ");
								query.setParameter("code", office.getOfficeName());
								listAnnouncement=query.setCacheable(true).list();
								
							
								
								query= session.createQuery("FROM Office where officeName=:code");
								query.setParameter("code", office.getOfficeName());
								ArrayList<Office> officelist=(ArrayList<Office>) query.setCacheable(true).list();
								
								
							}// close Office for loop


							
						}// close ssc for loop
						
						
					}//close city for loop
					}// branch for loop
				}//close district for loop
				
				
			}//close bu for loop
			
			//listEGreeting = session.createQuery("FROM E_Greeting where status = 1").setCacheable(true).list();
			Date edate=new Date();
			log.log(Level.INFO,"Total Time "+(edate.getTime()- sdate.getTime()));
			
			session.flush();
			session.clear();
			session.close();
			
			
		}catch(Exception e)
		{
			
			log.log(Level.INFO, "ImoUtilityDate -->  getAnnouncementCache --> Exception "+e);
			e.printStackTrace();
			LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("IMOUtilityData",Level.SEVERE+"",errors.toString());
			
		}finally{
			try{
				HibernateFactory.close(session);
			}catch(Exception e){
				
				e.printStackTrace();
			}
	
		}
	
		
		
	}
	
	
	public void getPresenterCache(){
		log.log(Level.INFO, "ImoUtilityDate -->  getPresenterCache");
		
		Session session = null;
		Transaction tx;
		
		List<Presenter> listPresenter=null;
		List<Bu> listbu=null;
		List<District> listdist=null;
		List<City> listcity=null;
		List<Branch> listbranch=null;
		List<Ssc> listSsc=null;
		List<Office> listOffice=null;
		ArrayList al=new ArrayList();
		
		try{
			Query query=null;
			session = HibernateFactory.openSession();
			
			Date sdate=new Date();
			log.log(Level.INFO,"Start Time "+sdate.getTime());
		
			listbu = session.createQuery("FROM Bu where status = 1").setCacheable(true).list();		
			listdist=session.createQuery("FROM District where status = 1").setCacheable(true).list();
			listbranch=session.createQuery("FROM Branch where status = 1").setCacheable(true).list();
			listcity=session.createQuery("FROM City where status = 1").setCacheable(true).list();
			listSsc=session.createQuery("FROM Ssc where status = 1").setCacheable(true).list();
			listOffice=session.createQuery("FROM Office where status = 1").setCacheable(true).list();
			
			
			listbu = session.createQuery("FROM Bu where status = 1 order by buName").setCacheable(true).list();		
			listdist=session.createQuery("FROM District where status = 1 order by districtName").setCacheable(true).list();
			listbranch=session.createQuery("FROM Branch where status = 1 order by branchName").setCacheable(true).list();
			listcity=session.createQuery("FROM City where status = 1 order by cityName").setCacheable(true).list();
			listSsc=session.createQuery("FROM Ssc where status = 1 order by sscName").setCacheable(true).list();
			listOffice=session.createQuery("FROM Office where status = 1 order by sscName").setCacheable(true).list();
			
			
			for (int i = 0; i < listbu.size(); i++) {
				
				Bu bu=listbu.get(i);
				
				query=session.createQuery("FROM Presenter where status = 1  and buCode=:bucode and district=0 ");
				query.setParameter("bucode", bu.getBuCode());
				listPresenter=query.setCacheable(true).list();
				
				query = session.createQuery("FROM Bu where status = 1 and buCode=:bucode ");		
				query.setParameter("bucode", bu.getBuCode());
				ArrayList<Bu> bulist=(ArrayList<Bu>) query.setCacheable(true).list();
				
				for (int j = 0; j < listdist.size(); j++) {
					
					District dist=listdist.get(j);
					
					query=session.createQuery("FROM Presenter where status = 1  and district=:distcode and branchCode=0 ");
					query.setParameter("distcode", dist.getDistrictCode());
					listPresenter=query.setCacheable(true).list();
										
					query=session.createQuery("FROM District where status = 1 and districtCode=:distcode ");
					query.setParameter("distcode", dist.getDistrictCode());
					ArrayList<District> distlist=(ArrayList<District>) query.setCacheable(true).list();
					
					for (int a = 0; a < listbranch.size(); a++) {
						Branch branch=listbranch.get(a);
						
						query=session.createQuery("FROM Presenter where status = 1  and branchCode=:branch and cityCode=0 ");
						query.setParameter("branch", branch.getBranchCode());
						listPresenter=query.setCacheable(true).list();
						
						
						query=session.createQuery("FROM Branch where status = 1 and branchCode=:branch ");
						query.setParameter("branch",branch.getBranchCode());
						ArrayList<Branch> branchlist=(ArrayList<Branch>) query.setCacheable(true).list();
					
					for (int k = 0; k < listcity.size(); k++) {
						
						City city=listcity.get(k);
						
						
						query=session.createQuery("FROM Presenter where status = 1 and cityCode=:citycode and sscCode=0 ");
						query.setParameter("citycode", city.getCityCode());
						listPresenter=query.setCacheable(true).list();

						query=session.createQuery("FROM City where status = 1 and  cityCode=:citycode ");
						query.setParameter("citycode", city.getCityCode());
						ArrayList<City> citylist=(ArrayList<City>) query.setCacheable(true).list();
						
						for (int l = 0; l < listSsc.size(); l++) {
						
							Ssc ssc=listSsc.get(l);
							
							query=session.createQuery("FROM Presenter where status = 1  and  sscCode=:ssccode ");
							query.setParameter("ssccode", ssc.getSscCode());
							listPresenter=query.setCacheable(true).list();
							
							query= session.createQuery("FROM Ssc where status = 1 and sscCode=:ssccode");
							query.setParameter("ssccode", ssc.getSscCode());
							ArrayList<Ssc> ssclist=(ArrayList<Ssc>) query.setCacheable(true).list();
							
							
							for (int m = 0; m < listOffice.size(); m++) {
								
								
								Office office=listOffice.get(m);

								query=session.createQuery("FROM Presenter where status = 1  and   officeCode=:code ");
								query.setParameter("code", office.getOfficeCode());
								listPresenter=query.setCacheable(true).list();
								
							
								
								query= session.createQuery("FROM Office where status = 1 and  officeCode=:code");
								query.setParameter("code", office.getOfficeCode());
								ArrayList<Office> officelist=(ArrayList<Office>) query.setCacheable(true).list();
								
								
							}// close Office for loop
							
							
							
						}// close ssc for loop
						
						
					}//close city for loop
					}// close branch for loop
				}//close district for loop
				
				
			}//close bu for loop
			
			//listEGreeting = session.createQuery("FROM E_Greeting where status = 1").setCacheable(true).list();
			Date edate=new Date();
			log.log(Level.INFO,"Total Time "+(edate.getTime()- sdate.getTime()));
			
			session.flush();
			session.clear();
			session.close();
			
			
		}catch(Exception e)
		{
			
			log.log(Level.INFO, "ImoUtilityDate -->  getPresenterCache --> Exception "+e);
			e.printStackTrace();
			LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("IMOUtilityData",Level.SEVERE+"",errors.toString());
			
		}finally{
			try{
				HibernateFactory.close(session);
			}catch(Exception e){
				
				e.printStackTrace();
			}
	
		}
	
	
		
		
		
	}
	public void getInterviewCache(){

		log.log(Level.INFO, "ImoUtilityDate -->  getInterviewCache");
		
		Session session = null;
		Transaction tx;
	
		List<Interview> listInterview=null;
		List<Bu> listbu=null;
		List<District> listdist=null;
		List<Branch> listbranch=null;
		List<City> listcity=null;
		List<Ssc> listSsc=null;
		List<Office> listOffice=null;
		ArrayList al=new ArrayList();
		
		try{
			Query query=null;
			session = HibernateFactory.openSession();
			
			Date sdate=new Date();
			log.log(Level.INFO,"Start Time "+sdate.getTime());
		
			listbu = session.createQuery("FROM Bu where status = 1").setCacheable(true).list();		
			listdist=session.createQuery("FROM District where status = 1").setCacheable(true).list();
			listbranch=session.createQuery("FROM Branch where status = 1").setCacheable(true).list();
			listcity=session.createQuery("FROM City ").setCacheable(true).list();
			listSsc=session.createQuery("FROM Ssc ").setCacheable(true).list();
			listOffice=session.createQuery("FROM Office").setCacheable(true).list();
			
			
			listbu = session.createQuery("FROM Bu where status = 1 order by buName").setCacheable(true).list();		
			listdist=session.createQuery("FROM District where status = 1 order by districtName").setCacheable(true).list();
			listbranch=session.createQuery("FROM Branch where status = 1 order by branchName").setCacheable(true).list();
			listcity=session.createQuery("FROM City  order by cityName").setCacheable(true).list();
			listSsc=session.createQuery("FROM Ssc  order by sscName").setCacheable(true).list();
			listOffice=session.createQuery("FROM Office order by officeName").setCacheable(true).list();
			
			
			for (int i = 0; i < listbu.size(); i++) {
				
				Bu bu=listbu.get(i);

				query=session.createQuery("FROM Interview where status = 1  and buCode=:bucode and district=0 ");
				query.setParameter("bucode", bu.getBuCode());
				listInterview=query.setCacheable(true).list();

				
				query = session.createQuery("FROM Bu where status = 1 and buCode=:bucode ");		
				query.setParameter("bucode", bu.getBuCode());
				ArrayList<Bu> bulist=(ArrayList<Bu>) query.setCacheable(true).list();
				
				for (int j = 0; j < listdist.size(); j++) {
					
					District dist=listdist.get(j);
					
					query=session.createQuery("FROM Interview where status = 1  and district=:distcode and branchCode=0 ");
					query.setParameter("distcode", dist.getDistrictCode());
					listInterview=query.setCacheable(true).list();
					
					
					
					query=session.createQuery("FROM District where status = 1 and districtCode=:distcode ");
					query.setParameter("distcode", dist.getDistrictCode());
					ArrayList<District> distlist=(ArrayList<District>) query.setCacheable(true).list();
					
					for (int a = 0; a < listbranch.size(); a++) {
						Branch branch=listbranch.get(a);
						
						query=session.createQuery("FROM Interview where status = 1  and branchCode=:branch and cityCode=:code ");
						query.setParameter("branch", branch.getBranchCode());
						query.setParameter("code", "0");
						listInterview=query.setCacheable(true).list();
						
						
						query=session.createQuery("FROM Branch where status = 1 and branchCode=:branch ");
						query.setParameter("branch",branch.getBranchCode());
						ArrayList<Branch> branchlist=(ArrayList<Branch>) query.setCacheable(true).list();
					
					for (int k = 0; k < listcity.size(); k++) {
						
						City city=listcity.get(k);
						
						
						query=session.createQuery("FROM Interview where status = 1 and cityCode=:citycode and sscCode=:code ");
						query.setParameter("citycode", city.getCityName());
						query.setParameter("code", "0");
						listInterview=query.setCacheable(true).list();
						
						
						query=session.createQuery("FROM City where   cityName=:citycode ");
						query.setParameter("citycode", city.getCityName());
						ArrayList<City> citylist=(ArrayList<City>) query.setCacheable(true).list();
						
						for (int l = 0; l < listSsc.size(); l++) {
						
							Ssc ssc=listSsc.get(l);
						
							query=session.createQuery("FROM Interview where status = 1  and  sscCode=:ssccode ");
							query.setParameter("ssccode", ssc.getSscName());
							listInterview=query.setCacheable(true).list();

							query= session.createQuery("FROM Ssc where  sscName=:ssccode");
							query.setParameter("ssccode", ssc.getSscName());
							ArrayList<Ssc> ssclist=(ArrayList<Ssc>) query.setCacheable(true).list();
							
							
							for (int m = 0; m < listOffice.size(); m++) {
								
								
								Office office=listOffice.get(m);

								query=session.createQuery("FROM Interview where status = 1  and   officeCode=:code ");
								query.setParameter("code", office.getOfficeName());
								listInterview=query.setCacheable(true).list();
								
							
								
								query= session.createQuery("FROM Office where  officeName=:code");
								query.setParameter("code", office.getOfficeName());
								ArrayList<Office> officelist=(ArrayList<Office>) query.setCacheable(true).list();
								
								
							}// close Office for loop
							
						}// close ssc for loop
						
						
					}//close city for loop
					}// close branch for loop
				}//close district for loop
				
				
			}//close bu for loop
			
			//listEGreeting = session.createQuery("FROM E_Greeting where status = 1").setCacheable(true).list();
			Date edate=new Date();
			log.log(Level.INFO,"Total Time "+(edate.getTime()- sdate.getTime()));
			
		}catch(Exception e)
		{
			
			log.log(Level.INFO, "ImoUtilityDate -->  getInterviewCache --> Exception "+e);
			e.printStackTrace();
			LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("IMOUtilityData",Level.SEVERE+"",errors.toString());
			
		}finally{
			try{
				HibernateFactory.close(session);
			}catch(Exception e){
				
				e.printStackTrace();
			}
	
		}
	
	}
	
	
	public void getHolidayCache(){

		log.log(Level.INFO, "ImoUtilityDate -->  getHolidayCache");
		
		Session session = null;
		Transaction tx;
		List<Holiday> listHoliday=null;
		List<Bu> listbu=null;
		List<District> listdist=null;
		List<City> listcity=null;
		List<Ssc> listSsc=null;
		ArrayList al=new ArrayList();
		
		try{
			Query query=null;
			session = HibernateFactory.openSession();
			
			Date sdate=new Date();
			log.log(Level.INFO,"Start Time "+sdate.getTime());
		
			listbu = session.createQuery("FROM Bu where status = 1").setCacheable(true).list();		
			listdist=session.createQuery("FROM District where status = 1").setCacheable(true).list();
			listcity=session.createQuery("FROM City where status = 1").setCacheable(true).list();
			listSsc=session.createQuery("FROM Ssc where status = 1").setCacheable(true).list();
			
			listbu = session.createQuery("FROM Bu where status = 1 order by buName").setCacheable(true).list();		
			listdist=session.createQuery("FROM District where status = 1 order by districtName").setCacheable(true).list();
			
			listcity=session.createQuery("FROM City where status = 1 order by cityName").setCacheable(true).list();
			listSsc=session.createQuery("FROM Ssc where status = 1 order by sscName").setCacheable(true).list();
			
			for (int i = 0; i < listbu.size(); i++) {
				
				Bu bu=listbu.get(i);

				query=  session.createQuery("FROM Holiday where status = 1  and buCode=:bucode and district=0 ");
				query.setParameter("bucode", bu.getBuCode());
				listHoliday=query.setCacheable(true).list();

//				listHoliday =  session.createQuery("FROM Holiday where status = 1  and buCode="+ bu.getBuCode()+" and district=0 ").setFirstResult(0).setCacheable(true).list();
//				al.add(listHoliday);
				
				query = session.createQuery("FROM Bu where status = 1 and buCode=:bucode ");		
				query.setParameter("bucode", bu.getBuCode());
				ArrayList<Bu> bulist=(ArrayList<Bu>) query.setCacheable(true).list();
				
				for (int j = 0; j < listdist.size(); j++) {
					
					District dist=listdist.get(j);
					query=  session.createQuery("FROM Holiday where status = 1  and district=:distcode and cityCode=0 ");
					query.setParameter("distcode", dist.getDistrictCode());
					listHoliday=query.setCacheable(true).list();
					
					query=session.createQuery("FROM District where status = 1 and districtCode=:distcode ");
					query.setParameter("distcode", dist.getDistrictCode());
					ArrayList<District> distlist=(ArrayList<District>) query.setCacheable(true).list();
					
					
					for (int k = 0; k < listcity.size(); k++) {
						
						City city=listcity.get(k);
						
						query=  session.createQuery("FROM Holiday where status = 1  and cityCode=:citycode and sscCode=0 ");
						query.setParameter("citycode", city.getCityCode());
						listHoliday=query.setCacheable(true).list();
						
						
						
						query=session.createQuery("FROM City where status = 1 and  cityCode=:citycode ");
						query.setParameter("citycode", city.getCityCode());
						ArrayList<City> citylist=(ArrayList<City>) query.setCacheable(true).list();
						
						for (int l = 0; l < listSsc.size(); l++) {
						
							Ssc ssc=listSsc.get(l);
							query=  session.createQuery("FROM Holiday where status = 1   and  sscCode=:ssccode ");
							query.setParameter("ssccode", ssc.getSscCode());
							listHoliday=query.setCacheable(true).list();
							
							
							query= session.createQuery("FROM Ssc where status = 1 and sscCode=:ssccode");
							query.setParameter("ssccode", ssc.getSscCode());
							ArrayList<Ssc> ssclist=(ArrayList<Ssc>) query.setCacheable(true).list();
							
						}// close ssc for loop
						
						
					}//close city for loop
	
				}//close district for loop
				
				
			}//close bu for loop
			
			//listEGreeting = session.createQuery("FROM E_Greeting where status = 1").setCacheable(true).list();
			Date edate=new Date();
			log.log(Level.INFO,"Total Time "+(edate.getTime()- sdate.getTime()));
			
			session.flush();
			session.clear();
			session.close();
			
			
		}catch(Exception e)
		{
			
			log.log(Level.INFO, "ImoUtilityDate -->  getHolidayCache --> Exception "+e);
			e.printStackTrace();
			LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("IMOUtilityData",Level.SEVERE+"",errors.toString());
			
		}finally{
			try{
				HibernateFactory.close(session);
			}catch(Exception e){
				
				e.printStackTrace();
			}
	
		}

	}

	public boolean checkLevelOfCode(int buCode, int distCode, int branchCode,
			String cityCode, String sscCode, String officeCode) {
		// TODO Auto-generated method stub
		log.log(Level.INFO, "ImoUtilityDate -->  checkLevelOfCode");
		Session session = null;
		Transaction tx;
		Boolean flag=false;
		List<Ssc> ssclist=null;
		Ssc ssc=null;
		
		List<Office> officelist=null;
		Office office=null;
		
		
		List<City> citylist=null;
		City city=null;
		List<Branch> branchlist=null;
		Branch branch=null;
		List<District> distlist=null;
		District dist=null;
		List<Bu> listbu=null;
		Bu bu=null;
		
		Criteria crit=null;
		
		try{
			Query query=null;
			session = HibernateFactory.openSession();
			
			if(distCode!=0 && branchCode==0 && cityCode.equals("0") && sscCode.equals("0") && officeCode.equals("0")){
//				ssc=new Ssc();
//				crit = session.createCriteria(Ssc.class);
//				crit.add(Restrictions.eq("sscCode", sscCode));
//				crit.add(Restrictions.eq("status", true));
//				ssclist=(ArrayList<Ssc>) crit.list();
//				ssc=ssclist.get(0);
				
				dist=new District();
				crit=session.createCriteria(District.class);
				crit.add(Restrictions.eq("districtCode", distCode));
				crit.add(Restrictions.eq("status", true));
				distlist=(ArrayList<District>) crit.list();
				dist=distlist.get(0);
				if(dist.getBuCode()!=buCode){
					return true;
				}
				
				
				
			}
			if(branchCode!=0 && cityCode.equals("0") && sscCode.equals("0") && officeCode.equals("0")){
				branch=new Branch();
				crit=session.createCriteria(Branch.class);
				crit.add(Restrictions.eq("branchCode", branchCode));
				crit.add(Restrictions.eq("status", true));
				branchlist=(ArrayList<Branch>) crit.list();
				branch=branchlist.get(0);
				if(branch.getDistCode()!=distCode){
					return true;
				}
			}
			
			if(!cityCode.equals("0") && sscCode.equals("0") && officeCode.equals("0")){
				city=new City();
				crit=session.createCriteria(City.class);
				crit.add(Restrictions.eq("cityName", cityCode));
				crit.add(Restrictions.eq("co", branchCode+""));
				citylist=(ArrayList<City>) crit.list();
				city=citylist.get(0);
				if(city.getBranchCode()!=branchCode){
					return true;
				}
				
			}
			
			if(!sscCode.equals("0") && officeCode.equals("0")){
				ssc=new Ssc();
				crit=session.createCriteria(Ssc.class);
				crit.add(Restrictions.eq("sscName", sscCode));
				crit.add(Restrictions.eq("cityCodeStr", cityCode));
				crit.add(Restrictions.eq("co", branchCode+""));
				ssclist=(ArrayList<Ssc>) crit.list();
				ssc=ssclist.get(0);
				if(!ssc.getCityName().equals(cityCode)){
					return true;
				}
				
			}
			
			
			if(!officeCode.equals("0") ){
				office=new Office();
				crit=session.createCriteria(Office.class);
				crit.add(Restrictions.eq("officeName", officeCode));
				crit.add(Restrictions.eq("co", branchCode+""));
				crit.add(Restrictions.eq("sscCodeStr", sscCode+""));
				crit.add(Restrictions.eq("cityCodeStr", cityCode+""));
				
				officelist=(ArrayList<Office>) crit.list();
				office=officelist.get(0);
				if(!office.getSscName().equals(sscCode)){
					return true;
				}
				
			}
			
			
			
		}catch(Exception e){
			log.log(Level.INFO, "ImoUtilityDate -->  checkLevelOfCode --> Exception "+e.getMessage());
			e.printStackTrace();
			LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("IMOUtilityData",Level.SEVERE+"",errors.toString());
			
		}finally{
			try{
				HibernateFactory.close(session);
			}catch(Exception e){
				
				e.printStackTrace();
			}
	
		}
		
		
		
		return false;
	}
	public static Map  getBranchForLogin(int districtCode){
		 ArrayList<ImoUtilityData> listData =  new  ArrayList<ImoUtilityData>();
		 Session session= null;
			try{
				
				session = HibernateFactory.openSession();
				ImoUtilityData imoData = null;
				Query  query = session.createQuery("SELECT  Distinct branchName, branchFullName FROM Branch where status = 1 ORDER BY branchFullName" );
				List list = query.list();
				Iterator ite = list.iterator();
				if(list !=null  && list.size() > 0){
					  for(int i=0;i<list.size();i++){
						  if(ite.hasNext()){
							  Object [] objectBranch = (Object []) ite.next();
							  imoData = new ImoUtilityData();
							  imoData.setAamTeamCode((String)objectBranch[0]);
							  imoData.setName(SecurityAPI.encodeHTML((String)objectBranch[1]));
							  listData.add(imoData);
						  }
					  }
				}
				}catch(HibernateException e)
				{
					log.log(Level.SEVERE, e.getMessage());
					e.printStackTrace();
					LogsMaintenance logsMain=new LogsMaintenance();
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					logsMain.insertLogs("IMOUtilityData",Level.SEVERE+"",errors.toString());
				}finally{
					try{
						HibernateFactory.close(session);
					}catch(Exception e){
						log.log(Level.SEVERE, e.getMessage());
						e.printStackTrace();
					}
			}
			Map map=new HashMap();
		    map.put("list",listData);
		    return map;
	}
	
	
	
	
	public void DeleteRelatedDistrict(int code, HttpServletRequest req) {

		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			Query query = session
					.createQuery("from District where status=1 and buCode=:buCode");
			query.setParameter("buCode", code);

			ArrayList<District> list = new ArrayList<District>();
			list = (ArrayList<District>) query.list();

			if (list.size() != 0) {

				for (int i = 0; i < list.size(); i++) {

					District DistrictClassObj = new District();

					DistrictClassObj = list.get(i);
					DistrictClassObj.setDistrictCode(DistrictClassObj
							.getDistrictCode());
					
					DeleteRelatedBranch(DistrictClassObj.getDistrictCode(), req);
					
					DistrictClassObj.setStatus(false);
					User userObj = new User();
					userObj = (User) req.getSession().getAttribute(
							"currUserObj");

					DistrictClassObj.setModifiedBy(userObj.getStaffLoginId());
					DistrictClassObj.setModificationDate(new Date());
					session.update(DistrictClassObj);
					AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
					auditTrailMaintenance.insertAuditTrail(new AuditTrail(
							userObj.getUser_no() + "",
							AuditTrail.MODULE_BU_DISTRICT,
							AuditTrail.FUNCTION_DELETE, DistrictClassObj
									.toString()));
					tx.commit();
					// DeleteRelatedCity(DistrictClassObj.getDistrictCode(),req);
					session.flush();
				}
			}

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("IMOUtilityData",Level.SEVERE+"",errors.toString());
		} /*
		 * finally { try { HibernateFactory.close(session);
		 * 
		 * } catch (Exception e) { log.log(Level.SEVERE, e.getMessage());
		 * e.printStackTrace(); } }
		 */

	}
	
	/**
	 * <p>
	 * This method performs Delete of District related BU Delete
	 * </p>
	 * 
	 * @param BUCode
	 * @param req
	 *            Servlet Request Parameter
	 * @return
	 */
	

	public void DeleteRelatedBranch(int districtCode, HttpServletRequest req) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			Query query = session
					.createQuery("from Branch where status=1 and distCode=:districtCode");
			query.setParameter("districtCode", districtCode);

			ArrayList<Branch> list = new ArrayList<Branch>();
			list = (ArrayList<Branch>) query.list();

			if (list.size() != 0) {
				for (int i = 0; i < list.size(); i++) {
					Branch branchClassObj = new Branch();
					branchClassObj = list.get(i);
					branchClassObj.setBranchCode(branchClassObj.getBranchCode());
					
					DeleteRelatedCity(branchClassObj.getBranchCode(), req);
					
					branchClassObj.setStatus(false);
					User userObj = new User();
					userObj = (User) req.getSession().getAttribute("currUserObj");

					branchClassObj.setModifiedBy(userObj.getStaffLoginId());
					branchClassObj.setModificationDate(new Date());
					session.update(branchClassObj);
					AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
					auditTrailMaintenance.insertAuditTrail(new AuditTrail(userObj.getUser_no() + "",
									AuditTrail.MODULE_BRANCH_DISTRICT_CITY,
									AuditTrail.FUNCTION_DELETE, branchClassObj
											.toString()));
					tx.commit();
					

				}
			}

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("IMOUtilityData",Level.SEVERE+"",errors.toString());
		} /*
		 * finally { try { HibernateFactory.close(session);
		 * 
		 * } catch (Exception e) { log.log(Level.SEVERE, e.getMessage());
		 * e.printStackTrace(); } }
		 */

	}
	
	/**
	 * <p>
	 * This method performs Delete of city related to BU Delete
	 * </p>
	 * 
	 * @param BUCode
	 * @param req
	 *            Servlet Request Parameter
	 * @return
	 */
	public void DeleteRelatedCity(int branchCode, HttpServletRequest req) {
		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			Query query = session
					.createQuery("from City where status=1 and branchCode=:branchCode");
			query.setParameter("branchCode", branchCode);

			ArrayList<City> list = new ArrayList<City>();
			list = (ArrayList<City>) query.list();

			if (list.size() != 0) {
				for (int i = 0; i < list.size(); i++) {
					City CityClassObj = new City();
					CityClassObj = list.get(i);
					CityClassObj.setCityCode(CityClassObj.getCityCode());
					
					DeleteRelatedSSC(CityClassObj.getCityCode(), req);
					
					CityClassObj.setStatus(false);
					User userObj = new User();
					userObj = (User) req.getSession().getAttribute(
							"currUserObj");

					CityClassObj.setModifiedBy(userObj.getStaffLoginId());
					CityClassObj.setModificationDate(new Date());
					session.update(CityClassObj);
					AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
					auditTrailMaintenance
							.insertAuditTrail(new AuditTrail(userObj
									.getUser_no() + "",
									AuditTrail.MODULE_BU_DISTRICT_CITY,
									AuditTrail.FUNCTION_DELETE, CityClassObj
											.toString()));
					tx.commit();
					// DeleteRelatedCity(CityClassObj.getDistrictCode(),req);

				}
			}

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("IMOUtilityData",Level.SEVERE+"",errors.toString());
		} /*
		 * finally { try { HibernateFactory.close(session);
		 * 
		 * } catch (Exception e) { log.log(Level.SEVERE, e.getMessage());
		 * e.printStackTrace(); } }
		 */

	}
	
	
	/**
	 * <p>
	 * This method performs Delete of SSC related to BU Delete
	 * </p>
	 * 
	 * @param BUCode
	 * @param req
	 *            Servlet Request Parameter
	 * @return
	 */
	public void DeleteRelatedSSC(int CityCode, HttpServletRequest req) {
		Session session;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			Query query = session
					.createQuery("from Ssc where status=1 and cityCode=:cityCode");
			query.setParameter("cityCode", CityCode);

			ArrayList<Ssc> list = new ArrayList<Ssc>();
			list = (ArrayList<Ssc>) query.list();

			if (list.size() != 0) {

				for (int i = 0; i < list.size(); i++) {

					Ssc SscClassObj = new Ssc();

					SscClassObj = list.get(i);
					SscClassObj.setSscCode(SscClassObj.getSscCode());
					SscClassObj.setStatus(false);
					User userObj = new User();
					userObj = (User) req.getSession().getAttribute(
							"currUserObj");

					SscClassObj.setModifiedBy(userObj.getStaffLoginId());
					SscClassObj.setModificationDate(new Date());
					session.update(SscClassObj);
					
					DeleteRelatedOffice(SscClassObj.getSscCode(),req);
					
					AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
					auditTrailMaintenance
							.insertAuditTrail(new AuditTrail(userObj
									.getUser_no() + "",
									AuditTrail.MODULE_BU_DISTRICT_CITY_SSC,
									AuditTrail.FUNCTION_DELETE, SscClassObj
											.toString()));
					tx.commit();
					// DeleteRelatedSSC(CityClassObj.getCityCode(),req);
					session.flush();
				}
			}

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("IMOUtilityData",Level.SEVERE+"",errors.toString());
		}

	}

	public void DeleteRelatedOffice(int code, HttpServletRequest req) {
		// TODO Auto-generated method stub

		Office officeObj = new Office();
		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();
			Query query = session
					.createQuery("from Office where status=1 and officeCode=:code");
			query.setParameter("code", code);
			ArrayList<Office> list = new ArrayList<Office>();
			list = (ArrayList<Office>) query.list();

			Office officeClassObj = new Office();
			officeClassObj = list.get(0);

			officeClassObj.setOfficeCode(code);
			officeClassObj.setStatus(false);

			User userObj = (User) req.getSession().getAttribute("currUserObj");

			officeClassObj.setModifiedBy(userObj.getStaffLoginId());
			officeClassObj.setModificationDate(new Date());
			session.update(officeClassObj);
			AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
			auditTrailMaintenance.insertAuditTrail(new AuditTrail(userObj
					.getUser_no() + "", AuditTrail.MODULE_OFFICE,
					AuditTrail.FUNCTION_DELETE, officeClassObj.toString()));
			tx.commit();
			
			
			session.flush();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("IMOUtilityData",Level.SEVERE+"",errors.toString());
		} finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
				
			}
		}
	
	}

	public void insertScript(){
		
		log.log(Level.SEVERE, "*************************************************************************************");
		log.log(Level.SEVERE, "********************************* REFRESH AAM DATA SCRIPT RUN   *********************");
		log.log(Level.SEVERE, "*************************************************************************************");
		
		
		Session session = null;
		Transaction tx;
		ArrayList list=null;
		try {
			
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();
			int count=0;
			
			/*   FOR CITY */
			
			SQLQuery  query=session.createSQLQuery("SELECT DISTINCT A.CITYCODE as city,B.BRANCH_CODE as branchcode FROM AGENTER A join T_BRANCH B on A.CO=B.BRANCH_NAME").addScalar("city",new StringType()) .addScalar("branchcode", new StringType());
			List<Object[]> entities = query.list();
				for (Object[] obj:entities) {
					
					
					Criteria criteria = session.createCriteria(City.class);
					criteria.add(Restrictions.eq("cityName",obj[0]+""));
					 criteria.add(Restrictions.eq("branchCode",Integer.parseInt(obj[1]+"")));
					 
					 list=(ArrayList) criteria.list();
					 
					 if(list.size() <=0){
						 City city=new City();
							city.setCityName((String) obj[0]);
							city.setBranchCode(Integer.parseInt(obj[1]+""));
							city.setCityFullName((String) obj[0]);
							city.setCreatedBy("Admin");
							city.setCreationDate(new Date());
							city.setModifiedBy("Admin");
							city.setModificationDate(new Date());
							city.setOrderCode(0);
							city.setToken("");
							city.setStatus(true);
							
							session.save(city);
					 }
					
					
					
				}
				tx.commit();
				
				/*   FOR SSC   */
				
				query=session.createSQLQuery("SELECT DISTINCT A.SSC as ssc,C.CITY_CODE as citycode FROM AGENTER A inner JOIN T_CITY C  ON A.CITYCODE=C.CITY_NAME INNER JOIN T_BRANCH B on A.CO=B.BRANCH_NAME").addScalar("ssc",new StringType()) .addScalar("citycode", new StringType());
				
				List<Object[]> entities2 = query.list();
				for (Object[] obj:entities2) {
					
					Criteria criteria = session.createCriteria(Ssc.class);
					criteria.add(Restrictions.eq("sscName",obj[0]+""));
					 criteria.add(Restrictions.eq("cityCode",Integer.parseInt(obj[1]+"")));
					 list=(ArrayList) criteria.list();
					
					 if(list.size() <=0){
						 	Ssc ssc=new Ssc();
							ssc.setSscName((String) obj[0]);
							ssc.setCityCode(Integer.parseInt(obj[1]+""));
							ssc.setSscFullName((String) obj[0]);
							ssc.setCreatedBy("Admin");
							ssc.setCreationDate(new Date());
							ssc.setModifiedBy("Admin");
							ssc.setModificationDate(new Date());
							ssc.setOrderCode(0);
							ssc.setToken("");
							ssc.setStatus(true);
							session.save(ssc);
							
					}
					
				}
				tx.commit();
				
				
				
				/*  For office    */
				
				
				query=session.createSQLQuery("SELECT DISTINCT A.OFFCOD as office,S.SSC_CODE as ssccode  FROM AGENTER A INNER join T_SSC S ON A.SSC=S.SSC_FULLNAME inner JOIN T_CITY C  ON A.CITYCODE=C.CITY_NAME INNER JOIN T_BRANCH B on A.CO=B.BRANCH_NAME").addScalar("office",new StringType()) .addScalar("ssccode", new StringType());
				
				List<Object[]> entities3 = query.list();
				for (Object[] obj:entities3) {
					
					
					Criteria criteria = session.createCriteria(Office.class);
					criteria.add(Restrictions.eq("officeName",obj[0]+""));
					 criteria.add(Restrictions.eq("sscCode",Integer.parseInt(obj[1]+"")));
					 list=(ArrayList) criteria.list();
					 if(list.size() <=0){
							Office office=new Office();
							office.setOfficeName((String) obj[0]);
							office.setSscCode(Integer.parseInt(obj[1]+""));
							office.setOfficeFullName((String) obj[0]);
							office.setCreatedBy("Admin");
							office.setCreationDate(new Date());
							office.setModifiedBy("Admin");
							office.setModificationDate(new Date());
							office.setOrderCode(0);
							office.setToken("");
							office.setStatus(true);
							
							session.save(office);
					 }
				}
				tx.commit();
				
			
			
		}catch(Exception e){
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("IMOUtilityData",Level.SEVERE+"",errors.toString());
		}finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
		}
		
		
		log.log(Level.SEVERE, "*************************************************************************************");
		log.log(Level.SEVERE, "********************************* REFRESH AAM DATA SCRIPT FINISHED    *********************");
		log.log(Level.SEVERE, "*************************************************************************************");
		
	}
	
	
	public void updateCitySscOfficeBaseonAgenter(){
		Session session = null;
		Transaction tx;
		ArrayList list=null;
		try {
			
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();
			
			/*session.createSQLQuery("UPADTE T_CITY SET STATUS=INACTIVE WHERE CITY_NAME NOT IN (SELECT CITYCODE FROM AGENTER)").executeUpdate();
			session.createSQLQuery("UPADTE T_SSC SET STATUS=INACTIVE WHERE SSC_NAME NOT IN (SELECT SSC FROM AGENTER)").executeUpdate();
			session.createSQLQuery("UPADTE T_OFFICE SET STATUS=INACTIVE WHERE OFFICE_NAME NOT IN (SELECT OFFCOD FROM AGENTER)").executeUpdate();
			*/
			
			
			/*Query query =session.createQuery("UPDATE City set status=false where cityName not in (select city from AamData )");
			
			
			query =session.createQuery("UPDATE Ssc set status=false where sscName not in (select ssc from AamData )");
			query.executeUpdate();
			
			query =session.createQuery("UPDATE Office set status=false where officeName not in (select officeCode from AamData )");
			query.executeUpdate();
			*/
			
			SQLQuery  query=session.createSQLQuery("SELECT {s.*} FROM T_CITY {s} WHERE STATUS=1 and CITY_NAME NOT IN (SELECT CITYCODE FROM AGENTER)");
			ArrayList<City> entities = (ArrayList<City>) query.addEntity("s", City.class).list();
			
			if (entities.size()>0) {
				for (City city:entities) {
					city.setStatus(false);
					session.update(city);
				}
			}
			
			
			 query=session.createSQLQuery("SELECT {s.*} FROM T_SSC {s}  WHERE STATUS=1 and SSC_NAME NOT IN (SELECT SSC FROM AGENTER)");
				ArrayList<Ssc> sscList = (ArrayList<Ssc>) query.addEntity("s", Ssc.class).list();
				
				if (sscList.size()>0) {
					for (Ssc ssc:sscList) {
						ssc.setStatus(false);
						session.update(ssc);
					}
				}
				
				
				query=session.createSQLQuery("SELECT {s.*}  FROM T_OFFICE {s}  WHERE STATUS=1 and OFFICE_NAME NOT IN (SELECT OFFCOD FROM AGENTER)");
				ArrayList<Office> officeList = (ArrayList<Office>) query.addEntity("s", Office.class).list();
				
				if (officeList.size()>0) {
					for (Office office:officeList) {
						office.setStatus(false);
						session.update(office);
					}
				}
				
				
				
			
			
			tx.commit();
			
		}catch(Exception e){
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("IMOUtilityData",Level.SEVERE+"",errors.toString());
		}finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
		}
			
	}

	public String getCodeStr() {
		return codeStr;
	}

	public void setCodeStr(String codeStr) {
		this.codeStr = codeStr;
	}

	public District getDistrictCodeBucode(int distCode) {
		// TODO Auto-generated method stub
		log.log(Level.INFO,"UtilitesMaintanence --> getDistrictCodeBucode");
		Session session= null;
		ArrayList<District> arrActivity = new ArrayList<District>();
		District dist=null;
			try
			{
				
				session = HibernateFactory.openSession();
				
				Query query = session.createQuery(" from District where districtCode=:code and status = 1");
				query.setParameter("code", distCode);
				arrActivity= (ArrayList<District>) query.list();
				if(arrActivity.size()!=0){
					dist=(District)arrActivity.get(0);
				}
			}
			catch(Exception e)
			{
				log.log(Level.SEVERE,e.getMessage());
				e.printStackTrace();
				LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("IMOUtilityData",Level.SEVERE+"",errors.toString());
			}finally{
				HibernateFactory.close(session);
			}

		return dist;
	}
	
}
