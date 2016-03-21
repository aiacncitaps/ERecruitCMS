/******************************************************************************
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
* 07-May-2015       Jinatmayee         DB Operation Code
* 12-May-2015       Jinatmayee         AuditTrail Added
* 14-May-2015       Jinatmayee         BU/District/City/SSC level code Added
* 17-May-2015       Jinatmayee         CSV File Upload code Added
* 08-Jun-2015       Jinatmayee         Comments Added

******************************************************************************/

package com.quix.aia.cn.imo.mapper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.IntegerType;

import com.quix.aia.cn.imo.constants.ApplicationAttribute;
import com.quix.aia.cn.imo.constants.SessionAttributes;
import com.quix.aia.cn.imo.data.auditTrail.AuditTrail;
import com.quix.aia.cn.imo.data.bu.Bu;
import com.quix.aia.cn.imo.data.city.City;
import com.quix.aia.cn.imo.data.common.AamData;
import com.quix.aia.cn.imo.data.district.District;
import com.quix.aia.cn.imo.data.holiday.Holiday;
import com.quix.aia.cn.imo.data.holiday.Holiday;
import com.quix.aia.cn.imo.data.locale.LocaleObject;
import com.quix.aia.cn.imo.data.ssc.Ssc;
import com.quix.aia.cn.imo.data.user.User;
import com.quix.aia.cn.imo.database.HibernateFactory;
import com.quix.aia.cn.imo.utilities.ErrorObject;
import com.quix.aia.cn.imo.utilities.FileUtils;
import com.quix.aia.cn.imo.utilities.ImoUtilityData;
import com.quix.aia.cn.imo.utilities.LMSUtil;
import com.quix.aia.cn.imo.utilities.MsgObject;
import com.quix.aia.cn.imo.utilities.Pager;
import com.quix.aia.cn.imo.utilities.PathDetail;

/**
 * <p>This class defines the data operations & csv file upload </p>
 * @author Jinatmayee
 * @version 1.0
 */
public class HolidayMaintenance {
	static Logger log = Logger.getLogger(HolidayMaintenance.class.getName());
    public HolidayMaintenance()
	{
		HibernateFactory.buildIfNeeded();
	}
    
    /**
     * <p>This method checks all validations & sets values to bean</p>
     * @param holiday Holiday object
     * @param requestParameters Servlet Request Parameter
     * @return Holiday object/Error object 
     */
    
	public  Object mapForm1(Holiday holiday, HttpServletRequest requestParameters)
    {
		log.log(Level.INFO,"HolidayMaintenance --> setting values ");
		
		if(holiday == null)
        {
			holiday = new Holiday();
            return holiday;
        }
		LocaleObject localeObj = (LocaleObject)requestParameters.getSession().getAttribute(SessionAttributes.LOCALE_OBJ);
		 if(requestParameters.getParameter("name") == null || requestParameters.getParameter("name") == "")
			return new ErrorObject("Holiday Name", " field is required",localeObj);
		 
		 if(requestParameters.getParameter("startdate") == null || requestParameters.getParameter("startdate") == "")
			return new ErrorObject("Start Date", " field is required",localeObj);
		 if(requestParameters.getParameter("enddate") == null || requestParameters.getParameter("enddate") == "")
			return new ErrorObject("End Date", " field is required",localeObj);
	      
		 
		 Date stDate=LMSUtil.convertToDate1(requestParameters.getParameter("startdate").trim());
		 Date endDate=LMSUtil.convertToDate1(requestParameters.getParameter("enddate").trim());
		 if(endDate.before(stDate)){
			 return new ErrorObject("End date should be earlier than Start Date", "",localeObj);
		}
		
		 if(requestParameters.getParameter("bu").equals("0"))
	            return new ErrorObject("This is a required field:", "BU",localeObj);
		 if(requestParameters.getParameter("icons") == null || requestParameters.getParameter("icons") == "")
	            return new ErrorObject("Please Select an Icon", "",localeObj);
		 
		holiday.setHolidayName(requestParameters.getParameter("name").trim());
		if(checkDuplicateHoliday(holiday))
			 return new ErrorObject("Holiday already Exists.Duplicate Holiday not allowed.","",localeObj);
		holiday.setStartDate(stDate);
		holiday.setEndDate(endDate);
		holiday.setBuCode(Integer.parseInt(requestParameters.getParameter("bu").trim()));
		holiday.setDistrict(Integer.parseInt(requestParameters.getParameter("district").trim()));
		holiday.setCityCode(Integer.parseInt(requestParameters.getParameter("city").trim()));
		holiday.setSscCode(Integer.parseInt(requestParameters.getParameter("ssc").trim()));
		holiday.setIconSelection(requestParameters.getParameter("icons").trim());
		
		return holiday;
    }
	
	/**
	 * <p>This method performs call for insertion method for holiday 
	 *  & audit trail for holiday add 
	 *  & sets message object in request</p>
	 * @param holiday Holiday object
	 * @param req Servlet Request Parameter
	 * @return Holiday object
	 */
	public Object addHoliday(Holiday holiday, HttpServletRequest req) {
		log.log(Level.INFO,"HolidayMaintenance --> AddHoliday ");
		MsgObject msgObj = null;
		User userObj = (User)req.getSession().getAttribute("currUserObj");
        holiday.setCreationDate(new Date());
        holiday.setCreatedBy(userObj.getStaffLoginId());
        holiday.setModificationDate(new Date());
	    holiday.setModifiedBy(userObj.getStaffLoginId());
		holiday.setStatus(true);
		holiday.setToken(LMSUtil.getRendomToken());
		
		int record_created=insertHoliday(holiday);
	   
		if(record_created>0){
		    AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
	        auditTrailMaintenance.insertAuditTrail(new AuditTrail(userObj.getStaffLoginId()+"",AuditTrail.MODULE_HOLIDAY,AuditTrail.FUNCTION_CREATE,holiday.toString()));
		    msgObj = new MsgObject("The new Holiday has been successfully created.");
		}
		else{
			   msgObj = new MsgObject("The new Holiday has not been created.");
			}
		 req.setAttribute("messageObject", msgObj);
		 req.setAttribute("CacheName", "holiday");
	 return holiday;
	}
	
	/**
	 * <p>This method performs Holiday Insertion </p>
	 * @param holiday Holiday object
	 * @return Holiday creation record count 
	 */
	public  int insertHoliday(Holiday holiday) {
		
		log.log(Level.INFO,"HolidayMaintenance --> insertHoliday ");
		int record_created=0;
		Session session = null;
		Transaction tx = null;
		 try
			{
				session = HibernateFactory.openSession();
				tx= session.beginTransaction();
				record_created=(Integer) session.save(holiday);
				tx.commit();
				
			}
			catch(Exception e)
			{
				log.log(Level.SEVERE,e.getMessage());
				e.printStackTrace();
			}finally{
				try{
					HibernateFactory.close(session);
				}catch(Exception e){
					
					log.log(Level.SEVERE,e.getMessage());
					e.printStackTrace();
				}
			}
		return record_created;
		
		
		
	}

	/**
	 * <p>This method retrieves all Holidays
	 * & its used for Rest</p>
	 * @return list of Holidays
	 */
    public  ArrayList getAllHoliday() {
		
		log.log(Level.INFO,"HolidayMaintenance --> getAllHoliday ");
		Holiday holiday = null;
		Session session = null;
		Transaction tx = null;
		ArrayList listData =  new  ArrayList();
		 try
			{
				session = HibernateFactory.openSession();
				Query query= session.createQuery("FROM  Holiday where status=1");
				ArrayList holidayList = (ArrayList)query.list();
				Iterator iterator = holidayList.iterator();
				while(iterator.hasNext()){
					holiday=(Holiday) iterator.next();
					listData.add(holiday);
				}
				
			}
			catch(Exception e)
			{
				log.log(Level.SEVERE,e.getMessage());
				e.printStackTrace();
			}finally{
				try{
					HibernateFactory.close(session);
				}catch(Exception e){
					
					log.log(Level.SEVERE,e.getMessage());
					e.printStackTrace();
				}
			}
		return listData;
		
	}
	
    
    /**
     * <p>This method performs Holiday Listing</p>
     * @param req Servlet Request Parameter
     * @return Pager object
     */
	 public  Pager holidayParticularSearchListing(HttpServletRequest req)
	    {
		    log.log(Level.INFO,"HolidayMaintenance --> searching Holiday ");
	        LinkedList item = new LinkedList();
	        Holiday holiday = null;
	        ArrayList holidayList=new  ArrayList();
	        holidayList = getParticularHoliday(req);
	       
	        for(int i = 0; i < holidayList.size(); i++)
	        {
	        	holiday = new Holiday();
	        	holiday = (Holiday)holidayList.get(i);
	            item.add(holiday.getHolidayListingTableRow());
	        }
	        Pager pager = new Pager();
	        pager.setActualSize(item.size());
	        pager.setCurrentPageNumber(0);
	        pager.setMaxIndexPages(10);
	        pager.setMaxPageItems(10);
	        for(; item.size() % 10 != 0; item.add("<tr></tr>"));
	        pager.setItems(item);
	        return pager;
	    }
	
	 /**
	  * <p>This method retrieves Holidays lists based on search criteria</p>
	  * @param req Servlet Request Parameter
	  * @return Holidays lists 
	  */
	 public  ArrayList getParticularHoliday(HttpServletRequest req) {
			
			log.log(Level.INFO,"HolidayMaintenance --> getParticularHoliday ");
			Holiday holiday = null;
			ArrayList listData =  new  ArrayList();
			String name = req.getParameter("name");
			String year = req.getParameter("year");
			String bu = req.getParameter("bu");
			String district = req.getParameter("district");
			String city = req.getParameter("city");
			String ssc = req.getParameter("ssc");
			
			//To Retain Search Criteria
			Map<String,String> holMap = new HashMap<String,String>();
			if(name!=null)
			  holMap.put("name", name);
			if(year!=null)
			  holMap.put("year", year);
			if(bu!=null)
			  holMap.put("bu", bu);
			if(district!=null)
			  holMap.put("district", district);
			if(city!=null)
			  holMap.put("city", city);
			if(ssc!=null)
			  holMap.put("ssc", ssc);
			
			//req.getSession().setAttribute("HOL_SEARCH_OBJ", holMap);
			Session session = null;
			
			 try
				{
					session = HibernateFactory.openSession();
					Criteria criteria = session.createCriteria(Holiday.class);
					if(name!=null && name.length() > 0){
					   criteria.add(Restrictions.like("holidayName", name));
					}
					criteria.add(Restrictions.sqlRestriction("YEAR(START_DATE)=?", Integer.parseInt(year),IntegerType.INSTANCE));
					criteria.add(Restrictions.eq("status", true));
					
					if(bu!=null && Integer.parseInt(bu) !=0){
					  criteria.add(Restrictions.eq("buCode", Integer.parseInt(bu)));
					 }
					if(district!=null && Integer.parseInt(district) !=0){
					  criteria.add(Restrictions.eq("district", Integer.parseInt(district)));
					}
					if(city!=null && Integer.parseInt(city) !=0){
					  criteria.add(Restrictions.eq("cityCode", Integer.parseInt(city)));
					}
					if(ssc!=null && Integer.parseInt(ssc) !=0){
					  criteria.add(Restrictions.eq("sscCode", Integer.parseInt(ssc)));
					}
					ArrayList holidayList = (ArrayList) criteria.list();
					Iterator iterator = holidayList.iterator();
					while(iterator.hasNext()){
						holiday=(Holiday) iterator.next();
						listData.add(holiday);
					}
					
				}
				catch(Exception e)
				{
					log.log(Level.SEVERE,e.getMessage());
					e.printStackTrace();
				}finally{
					try{
						HibernateFactory.close(session);
					}catch(Exception e){
						
						log.log(Level.SEVERE,e.getMessage());
						e.printStackTrace();
					}
				}
			return listData;
			
		}
	
	 /**
	  * <p>This method retrieves Holiday based on holidayCode  </p>
	  * @param holiday_code
	  * @return Holiday object
	  */
	 public  Holiday getHolidayBasedOnHolidayCode(int holiday_code) {
			
			log.log(Level.INFO,"HolidayMaintenance -->Get Holiday ");
			Holiday holiday = null;
			Session session = null;
			 try
				{
				    session = HibernateFactory.openSession();
					holiday = (Holiday) session.get(Holiday.class, holiday_code);
					
				}
				catch(Exception e)
				{
					log.log(Level.SEVERE,e.getMessage());
					e.printStackTrace();
				}finally{
					try{
						HibernateFactory.close(session);
					}catch(Exception e){
						
						log.log(Level.SEVERE,e.getMessage());
						e.printStackTrace();
					}
				}
			return holiday;
			
	}
	 
	
	    /**
		 * <p>This method performs call for modify method for holiday 
		 *  & audit trail for holiday update 
		 *  & sets message object in request</p>
		 * @param holiday Holiday object
		 * @param req Servlet Request Parameter
		 * @return Holiday object
		 */
	 public Object updateHoliday(Holiday holiday, HttpServletRequest req) {
			log.log(Level.INFO,"HolidayMaintenance --> ModifyHoliday ");
			MsgObject msgObj = null;
		    User userObj = (User)req.getSession().getAttribute("currUserObj");
		    holiday.setModificationDate(new Date());
		    holiday.setModifiedBy(userObj.getStaffLoginId());
		    
			int record_updated= modifyHoliday(holiday);
			    
			if(record_updated>0){
				AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
			    auditTrailMaintenance.insertAuditTrail(new AuditTrail(userObj.getStaffLoginId()+"",AuditTrail.MODULE_HOLIDAY,AuditTrail.FUNCTION_UPDATE,holiday.toString()));
				msgObj = new MsgObject("The Holiday has been successfully updated.");	
			}
			else{
				msgObj = new MsgObject("The Holiday has not been updated.");	
			}
			req.setAttribute("messageObject", msgObj);
			 req.setAttribute("CacheName", "holiday");
		return holiday;
		}
	 
	    /**
		 * <p>This method performs Edit of Holiday </p>
		 * @param holiday Holiday object
		 * @return Holiday updation record count 
		 */
	 public  int modifyHoliday(Holiday holiday) {
			
			log.log(Level.INFO,"HolidayMaintenance -->Modify Holiday ");
			int record_updated=0;
			Session session = null;
			Transaction tx = null;
			 try
				{
				    session = HibernateFactory.openSession();
					tx = session.beginTransaction();
				    session.update(holiday);
					tx.commit();
					record_updated=1;
				}
				catch(Exception e)
				{
					log.log(Level.SEVERE,e.getMessage());
					e.printStackTrace();
				}finally{
					try{
						HibernateFactory.close(session);
					}catch(Exception e){
						
						log.log(Level.SEVERE,e.getMessage());
						e.printStackTrace();
					}
				}
			return record_updated;
			
	}
	    /**
		 * <p>This method performs call for delete method for holiday 
		 *  & audit trail for holiday delete 
		 *  & sets message object in request</p>
		 * @param holiday_code
		 * @param req Servlet Request Parameter
	     */
     public void deleteHoliday(int holiday_code,HttpServletRequest req) {
           log.log(Level.INFO,"HolidayMaintenance --> DeleteHoliday ");
			
			MsgObject msgObj = null;
			User userObj = (User)req.getSession().getAttribute("currUserObj");
			int record_deleted=deleteParticularHoliday(holiday_code,req);
			
			if(record_deleted>0){
				msgObj = new MsgObject("The Holiday has been successfully deleted.");
			}
			else{
				msgObj = new MsgObject("The Holiday has not been deleted.");
			}
			req.setAttribute("messageObject", msgObj);
			 req.setAttribute("CacheName", "holiday");
			
			}
     /**
      * <p>This method performs Delete of Holiday </p>
	  * @param holiday_code
      * @param req Servlet Request Parameter
      * @return Holiday deletion record count 
      */
	 public  int deleteParticularHoliday(int holiday_code,HttpServletRequest req) {
			
		 log.log(Level.INFO,"HolidayMaintenance --> DeleteHoliday ");
			int record_deleted=0;
			User userObj = (User)req.getSession().getAttribute("currUserObj");
			Session session = null;
			Transaction tx = null;
			 try
				{
				    session = HibernateFactory.openSession();
					tx = session.beginTransaction();
					Holiday holidayObj=(Holiday) session.get(Holiday.class, holiday_code);
					holidayObj.setStatus(false);
				    session.update(holidayObj);
					tx.commit();
					AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
				    auditTrailMaintenance.insertAuditTrail(new AuditTrail(userObj.getStaffLoginId()+"",AuditTrail.MODULE_HOLIDAY,AuditTrail.FUNCTION_DELETE,holidayObj.toString()));
					record_deleted=1;
				}
				catch(Exception e)
				{
					log.log(Level.SEVERE,e.getMessage());
					e.printStackTrace();
				}finally{
					try{
						HibernateFactory.close(session);
					}catch(Exception e){
						log.log(Level.SEVERE,e.getMessage());
						e.printStackTrace();
					}
				}
			return record_deleted;
			
	}
	 
	
	 /**
	  * <p>This method performs multiple Holidays Upload through CSV File</p>
	  * @param holidayObj Holiday object
	  * @param requestParameters Servlet Request Parameter
	 */
	 public Object cSVUpload(Holiday holidayObj,HttpServletRequest requestParameters){
		  log.log(Level.INFO,"HolidayMaintenance -->Uploading CSV File"); 
		  if(holidayObj == null)
	        {
			  holidayObj = new Holiday();
			  return holidayObj;
	        }
		  LocaleObject localeObj = (LocaleObject)requestParameters.getSession().getAttribute(SessionAttributes.LOCALE_OBJ);
//		  ResourceBundle msgProps = ResourceBundle.getBundle("configurations");
//		  String holidayPath  = msgProps.getString("HolidayPath");

			Map<String,String> configurationMap =(Map<String,String>) requestParameters.getSession().getAttribute(ApplicationAttribute.CONFIGURATION_PROPERTIES_MAP);
			String holidayPath  = configurationMap.get("HolidayPath");
		  String csv_file_name = "";
		  if(requestParameters.getSession().getAttribute("csv_file_name")!=null){
			  csv_file_name=(String)requestParameters.getSession().getAttribute("csv_file_name");
			}
			
		   requestParameters.getSession().removeAttribute("csv_file_name");
		   String tempDir = System.getProperty("java.io.tmpdir");
		   Session session= null;
  		int record_created=0;
  		StringBuffer strbuf=new StringBuffer();
  		MsgObject msgObj=null;
  		int sucessCnt=0;
  		int m=0;
		try{
			
			
   		String serverFilename = holidayPath+"/"+"HOL_" + LMSUtil.getRendomToken();

			File uploadedFolder = new File(serverFilename);
			if(!uploadedFolder.exists()){
				uploadedFolder.mkdirs();
			}
			if(csv_file_name!=null && !csv_file_name.equals("")){
				
				byte[] bytearray=(byte[]) requestParameters.getSession().getAttribute("csv_byte_session");
				if(csv_file_name.contains(".csv") || csv_file_name.contains(".CSV")){
					try {
						FileOutputStream stream = new FileOutputStream(serverFilename+"/"+csv_file_name);
					    stream.write(bytearray);
					    stream.close();
					} catch(Exception e){e.printStackTrace();}
				}
				//delete temp directory
				File temp_file = new File(tempDir+"/" + csv_file_name);
	        	FileUtils.deleteFileNFolder(temp_file);
	        	
	        	// Retrieve datas from CSV
	        	//FileInputStream fileInputStream = new FileInputStream(new File(serverFilename+"/"+csv_file_name));
	        	//BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
	        	BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(serverFilename+"/"+csv_file_name), "UTF-8"));
	            int l=0;
	        	String records = null;
	        	String holName="",stDate="",endDate="",buName="",distName="",cityName="",sscName="",icon_type="";
	        	int buCode=0,distCode=0;
	        	String cityCode="0",sscCode="0";
	        	session = HibernateFactory.openSession();
	        	ImoUtilityData imoUtilityData = new ImoUtilityData();
	        	
	        	User userObj = (User)requestParameters.getSession().getAttribute("currUserObj");
               holidayObj.setCreationDate(new Date());
       		holidayObj.setCreatedBy(userObj.getStaffLoginId());;
       		holidayObj.setModificationDate(new Date());
       		holidayObj.setModifiedBy(userObj.getStaffLoginId());;
       		holidayObj.setStatus(true);
       		holidayObj.setToken(LMSUtil.getRendomToken());

       		AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
       		boolean flag=false;
       		LMSUtil   lmsUtil = new LMSUtil();   		
	        	while ((records = bufferedReader.readLine()) != null) {
	        		
	        		m++;//no of line in csv file
	        		if(m > 1){
	        		//String data[] = records.split("\\|");
	        		String data[] = records.split("\\|", -1);
	        		l++;
	        		if(data.length == 8)
					{
						//l++;
	        			
	        			for(int i=0; i<data.length;i++)
						{
	        			
	        				if(l>0){
								if(i==0){
									holName = data[0];
									holidayObj.setHolidayName(holName);
									if(holName.equals("")){
								       flag=true;
       							   strbuf.append("Required Holiday Name at line number "+l);
       							   break;
								    }
									else if(checkDuplicateHoliday(holidayObj)){
										flag=true;
	        							   strbuf.append("Duplicate Holiday Name at line number "+l);
	        							   break;
									}
									else{
									  holidayObj.setHolidayName(holName);
									}
								}
								else if(i==1){
									stDate = data[1];
									if(stDate.equals("")){
										flag=true;
										strbuf.append("Required Start Date at line number "+l);
										break;
								    }
									else{
										
									   // holidayObj.setStartDate(LMSUtil.convertToDate1(stDate));
										if(lmsUtil.validateDDMMYYYY(stDate)){
											holidayObj.setStartDate(LMSUtil.convertToDate1(stDate));
		        							
	        						  }else{
	        							  	flag=true;
		        							strbuf.append("Date Format Invalid.Should Be DD/MM/YYYY.Line Number  "+l);
		        							break;
	        						  }
										
									}
							    }
								else if(i==2){
									endDate = data[2];
									if(endDate.equals("")){
										flag=true;
										strbuf.append("Required End Date at line number "+l);
										break;
									}
									else{ 
										if(lmsUtil.validateDDMMYYYY(endDate)){
											holidayObj.setEndDate(LMSUtil.convertToDate1(endDate));
										
										     if(holidayObj.getEndDate().before(holidayObj.getStartDate())){
										      flag=true;
										      strbuf.append("End date should be earlier than Start Date at line number "+l);
										      break;
									        }
										}
									  else{
										    flag=true;
		        							strbuf.append("Date Format Invalid.Should Be DD/MM/YYYY.Line Number  "+l);
		        							break;
									  }
										
										
									}
							    }
								else if(i==3){
									buName = data[3];
									if(buName.equals("")){
										flag=true;
										strbuf.append("Required BU at line number "+l);
										break;
									}
									else{
										buCode=imoUtilityData.getBuCodeBasedOnBuName(buName);	
										if(buCode==0){
	        								flag=true;
		        							strbuf.append("Invalid BU Name  at line number "+l);
		        							break;
	        							}
										else{
									        holidayObj.setBuCode(buCode);
									    }
									}
							    }
								else if(i==4){
									distName = data[4];
									if(distName.equals("")){
										 holidayObj.setDistrict(0);
									}
										
									else{
										  distCode=imoUtilityData.getDistrictCodeBasedOnDistrictName(distName);	
										  if(distCode==0){
		        								flag=true;
			        							strbuf.append("Invalid District Name  at line number "+l);
			        							break;
		        							}
										  else{
										    holidayObj.setDistrict(distCode);
										  }
									 }
								}
							    
								else if(i==5){
									cityName = data[5];
									if(cityName.equals("")){
										holidayObj.setCityCode(0);
									}	
										
									else{	    
										   cityCode=imoUtilityData.getCityCodeBasedOnDistrictName(cityName);
										   if(cityCode.equals("0")){
		        								flag=true;
			        							strbuf.append("Invalid City Name  at line number "+l);
			        							break;
		        							}
										   else{
											 //  holidayObj.setCityCode(cityCode);
										   }
									}
							    }
								else if(i==6){
									sscName = data[6];
									if(sscName.equals("")){
										holidayObj.setSscCode(0);
									}
									else{	
											sscCode=imoUtilityData.getSSCCodeBasedOnSSCName(sscName);
											if(sscCode.equals("0")){
		        								flag=true;
			        							strbuf.append("Invalid SSC Name  at line number "+l);
			        							break;
		        							}
											else{
											   // holidayObj.setSscCode(sscCode);
											}
									}
							    }
								else if(i==7){
									icon_type = data[7];
									if(icon_type.equals("") || !(icon_type.equals("1") || icon_type.equals("2") || icon_type.equals("3") || icon_type.equals("4") || icon_type.equals("5"))){
										flag=true;
	        							strbuf.append("Required Icon Type at line number "+l);
	        							break;
									}
									else{
									   holidayObj.setIconSelection(icon_type);
									}
							    }
	        				}		
						}
	        			
	        		if(flag==true){
       					flag=false;
       					strbuf.append("\n");
       					continue;
	        			}
       			else{
	        			//insertion done here
	        			if(userObj.getUserType().equals("AD")){
	        				record_created = insertHoliday(holidayObj);
	        				sucessCnt++;
	        			}
	        			else{
	        			if(userObj.isSscLevel()){
	        				/*if(buCode>0 && distCode>0 && cityCode>0 && sscCode>0){
	        				record_created = insertHoliday(holidayObj);
	        				sucessCnt++;
	        				}*/
	        			}
	        			else if(userObj.isCityLevel()){
	        				/*if(buCode>0 && distCode>0 && cityCode>0){
	        				record_created = insertHoliday(holidayObj);
	        				sucessCnt++;
	        				}*/
	        			}
	        			else if(userObj.isDistrictLevel()){
	        				if(buCode>0 && distCode>0){
	        				record_created = insertHoliday(holidayObj);
	        				sucessCnt++;
	        				}
	        			}
	        			else if(userObj.isBuLevel()){
	        				if(buCode>0){
	        				record_created = insertHoliday(holidayObj);
	        				sucessCnt++;
	        				}
	        			}
					 }
	        			
	        			   
	        			if(record_created>0){
	        			    auditTrailMaintenance.insertAuditTrail(new AuditTrail(userObj.getStaffLoginId()+"",AuditTrail.MODULE_HOLIDAY,AuditTrail.FUNCTION_CREATE,holidayObj.toString()));
	        			}
       		}
       			flag=false;
			}	
	        		
	          else{
	        	    strbuf.append("Due to incorrect formate or incorrect Data Error in line number  "+l +" :- ("+ records +")");
					strbuf.append("\n");
					flag=true;
			   }
					
	        }
	        		
	      }
	        	 	
			
			if(strbuf.length()>0){
				ImoUtilityData imoutill=new ImoUtilityData();
				imoutill.summaryReport(strbuf,requestParameters);
			}
		}		
	}
		catch(Exception e)
		{
			log.log(Level.SEVERE,e.getMessage());
			e.printStackTrace();
		}finally{
			try{
				//HibernateFactory.close(session);
			}catch(Exception e){
				
				log.log(Level.SEVERE,e.getMessage());
				e.printStackTrace();
			}
		}
		int failedCnt = m - sucessCnt;
		requestParameters.getSession().setAttribute("strbuf", strbuf);
		requestParameters.getSession().setAttribute("formObj",new PathDetail().getFormObj("HolidayUploadCSV"));
		//if(sucessCnt!=0){ 
		return new ErrorObject("Number of records uploaded successfully :"+sucessCnt+" <br> Number of records fail :"+(failedCnt-1)+"  ", "",localeObj);
 		/*}else{
 			return new ErrorObject("The new Holiday csv file uploaded Successfully", "");
 		}*/
}
	
	  /**
	   * <p>This method performs checking of Duplicate Holidays</p>
	   * @param holiday Holiday object
	   * @return true/false boolean value
	   */
	  public boolean checkDuplicateHoliday(Holiday holiday){
		  log.log(Level.INFO,"HolidayMaintenance --> Checking Duplicate Holiday");
			Session session = null;
			try{
				session = HibernateFactory.openSession();
				Query selectQ = session.createQuery("select  holidayCode from Holiday  where holidayName =:holidayName and holidayCode<>:holidayCode and status=1");
				selectQ.setParameter("holidayName", holiday.getHolidayName());
				selectQ.setParameter("holidayCode", holiday.getHolidayCode());
				List list = selectQ.list();
				if(list!=null && list.size() > 0)
					return true;
				else 
					return false;
			}catch(Exception e)
			{
				log.log(Level.SEVERE,e.getMessage());
				e.printStackTrace();
			}finally{
				try{
					HibernateFactory.close(session);
				}catch(Exception e){
					
					log.log(Level.SEVERE,e.getMessage());
					e.printStackTrace();
				}
			}
			return false;
			
		}
	  
	  /**
		  * <p>Method is used to get all Holiday for Rest service</p>
		  * @return  ArrayList of Holiday
		  */
	public ArrayList getAllHolidayRest(AamData aamData,String agentId) {
		// TODO Auto-generated method stub

		log.log(Level.SEVERE,"HolidayMaintenance -->  getAllHolidayRest ");
		Session session = null;
		ArrayList holidayList = new ArrayList();
		ArrayList<Holiday> list = new ArrayList<Holiday>();
		ArrayList<Bu> bulist=new ArrayList<Bu>();
		ArrayList<District> distlist=new ArrayList<District>();
		ArrayList<City> citylist=new ArrayList<City>();
		ArrayList<Ssc> ssclist=new ArrayList<Ssc>();
		
		try{
			
			session = HibernateFactory.openSession();
			session.setDefaultReadOnly(true);
			Date sdate=new Date();
			log.log(Level.SEVERE,"Start Time "+sdate.getTime());
			
			
			Query query=session.createQuery("FROM Holiday where status = 1 "
					+ " AND ( (buCode=:bucode  and district=0) "
					+ "or (buCode=:bucode and district=:distcode and  branchCode=0)"
					+ "or (buCode=:bucode and district=:distcode and branchCode=:branchCode and  cityCode='0')"
					+ "or (buCode=:bucode and district=:distcode and branchCode=:branchCode and cityCode=:citycode and sscCode='0')"
					+ "or (buCode=:bucode and district=:distcode and branchCode=:branchCode and cityCode=:citycode and sscCode=:ssccode and officeCode = '0')"
					+ "or (buCode=:bucode and district=:distcode and branchCode=:branchCode and cityCode=:citycode and sscCode=:ssccode and officeCode =:officeCode ) )");
			
			query.setParameter("bucode",aamData.getBuCode());
			query.setParameter("distcode",aamData.getDistrictCode());
			query.setParameter("branchCode",aamData.getBranchCode());
			query.setParameter("citycode", aamData.getCity());
			query.setParameter("ssccode", aamData.getSsc());
			query.setParameter("officeCode",aamData.getOfficeCode());
			
			list=(ArrayList<Holiday>) query.setCacheable(true).list();
			holidayList.addAll(list);
			
			
			Date edate=new Date();
			log.log(Level.SEVERE,"Total  Time "+(edate.getTime()- sdate.getTime()));

			}catch(Exception e)
			{
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}finally{
				try{
					session.setDefaultReadOnly(false);
					HibernateFactory.close(session);
				}catch(Exception e){
					log.log(Level.SEVERE, e.getMessage());
					e.printStackTrace();
				}
		}
		
		return holidayList;
	
	}
	
}
