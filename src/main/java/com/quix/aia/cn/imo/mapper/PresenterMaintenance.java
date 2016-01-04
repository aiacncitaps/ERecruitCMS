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
* 15-May-2015       Abinas           Presenter functionality code added
* 08-June-2015       Abinas            doc comment added          
* 14-August-2015    Maunish            Modified 
* 09-Sept-2015         Nibedita           file uploaded to db
****************************************** *********************************** */
package com.quix.aia.cn.imo.mapper;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.IntegerType;

import com.quix.aia.cn.imo.constants.ApplicationAttribute;
import com.quix.aia.cn.imo.constants.RequestAttributes;
import com.quix.aia.cn.imo.constants.SessionAttributes;
import com.quix.aia.cn.imo.data.auditTrail.AuditTrail;
import com.quix.aia.cn.imo.data.bu.Bu;
import com.quix.aia.cn.imo.data.category.PresenterCategory;
import com.quix.aia.cn.imo.data.city.City;
import com.quix.aia.cn.imo.data.common.AamData;
import com.quix.aia.cn.imo.data.district.District;
import com.quix.aia.cn.imo.data.locale.LocaleObject;
import com.quix.aia.cn.imo.data.presenter.Presenter;
import com.quix.aia.cn.imo.data.presenter.PresenterMaterial;
import com.quix.aia.cn.imo.data.ssc.Ssc;
import com.quix.aia.cn.imo.data.user.User;
import com.quix.aia.cn.imo.database.HibernateFactory;
import com.quix.aia.cn.imo.utilities.ErrorObject;
import com.quix.aia.cn.imo.utilities.FileUtils;
import com.quix.aia.cn.imo.utilities.KeyObjPair;
import com.quix.aia.cn.imo.utilities.LMSUtil;
import com.quix.aia.cn.imo.utilities.MsgObject;
import com.quix.aia.cn.imo.utilities.Pager;
import com.quix.aia.cn.imo.utilities.PathDetail;

/**
 * <p>This class is for CRUD operations &  file upload </p>
 * @author Abinas
 * @version 1.0
 */

public class PresenterMaintenance 
{
      static Logger log = Logger.getLogger(PresenterMaintenance.class.getName());
      /**
       * <p>This method used to get all active presenter category</p>
       * @param requestParameters Servlet Request Parameter
       * @return presenter category list
       */
	  public ArrayList getAllPresenterCategory(HttpServletRequest request)
		{
		  log.log(Level.INFO,"PresenterMaintenance --> get all active presenter category ");
			   ArrayList<PresenterCategory> arrPresenterCategory = new ArrayList();
			   Session session = HibernateFactory.openSession();
		        try
		        {
					Query query=session.createQuery("from PresenterCategory where status=:status");
						query.setParameter("status", 1);
						arrPresenterCategory=(ArrayList<PresenterCategory>) query.list();
		        }
		        catch(Exception e)
		        {
		        	log.log(Level.SEVERE,e.getMessage());
		            e.printStackTrace();
		        }finally{
		        	HibernateFactory.close(session);
		        }
		        return arrPresenterCategory;
		}
	  /**
       * <p>This method checks all validations & sets values to bean</p>
       * @param presenter Presenter object
       * @param requestParameters Servlet Request Parameter
       * @return Presenter Object
       */
	  public Object mapForm1(Presenter presenter, HttpServletRequest requestParameters)
		{
		  log.log(Level.INFO,"PresenterMaintenance --> mapping all presnter field ");
			if(presenter == null)
			{
				presenter = new Presenter();
				return presenter;
			}
			LocaleObject localeObj = (LocaleObject)requestParameters.getSession().getAttribute(SessionAttributes.LOCALE_OBJ);
			User userObj = (User)requestParameters.getSession().getAttribute("currUserObj");
			
			String presenter_cat_code=requestParameters.getParameter("presenter_category_code");
			String presenter_name=requestParameters.getParameter("name");
			String startdate=requestParameters.getParameter("startdate");
			String enddate=requestParameters.getParameter("enddate");
			String desc=requestParameters.getParameter("desc");
			String bu=requestParameters.getParameter("bu");
			String visibility=requestParameters.getParameter("presenter_visibility");
			
			if(presenter_cat_code.equals("%"))
				return new ErrorObject("Presenter Category", " field is required",localeObj);
			if(presenter_name == null || presenter_name.length() == 0)
				return new ErrorObject("Presenter Name", " field is required",localeObj);
			if(startdate == null || startdate.length() == 0)
				return new ErrorObject("Start Date", " field is required",localeObj);
			if(enddate == null || enddate.length() == 0)
				return new ErrorObject("Expiry Date", " field is required",localeObj);
			if(desc == null || desc.length() == 0)
				return new ErrorObject("Presenter Description", " field is required",localeObj);
			if(visibility == null || visibility.equals("%"))
				return new ErrorObject("Presenter Visibility", " field is required",localeObj);
			if(bu== null || bu.equals("0"))
				return new ErrorObject("BU", " field is required",localeObj);
			
			presenter.setCategory(Integer.parseInt(presenter_cat_code));
			presenter.setPresenterName(presenter_name);
			presenter.setStart_date(LMSUtil.convertToDate1(startdate));
			presenter.setExpiry_date(LMSUtil.convertToDate1(enddate));
			presenter.setPresenterDescription(desc);
			presenter.setVisibility(visibility);
			
			presenter.setBuCode(Integer.parseInt(requestParameters.getParameter("bu")));
			presenter.setDistCode(Integer.parseInt(requestParameters.getParameter("district")));
			presenter.setBranchCode(Integer.parseInt(requestParameters.getParameter("branch")));
			presenter.setCityCode(Integer.parseInt(requestParameters.getParameter("city")));
			presenter.setSscCode(Integer.parseInt(requestParameters.getParameter("ssc")));
			presenter.setOfficeCode(Integer.parseInt(requestParameters.getParameter("office")));
			
			
			
			
			if(presenter.getExpiry_date().before(presenter.getStart_date()) || presenter.getExpiry_date().equals(presenter.getStart_date())){
				 return new ErrorObject(" ", "Expired Date should be after Start Date",localeObj);
			}
			presenter.setStatus(true);
			presenter.setToken(LMSUtil.getRendomToken());return presenter;
		}
	  /**
       * <p>This method is for creating new Presenter</p>
       * @param presenter Presenter object
       * @param requestParameters Servlet Request Parameter
       * @return Presenter Object
       */
	  public Presenter insertPresenter(Presenter presenter, HttpServletRequest request)
		{
		  log.log(Level.INFO,"PresenterMaintenance --> crrating presenter");
			User user = (User) request.getSession().getAttribute(SessionAttributes.CURR_USER_OBJ);
			AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
			String msg="The new Presenter has not been successfully created.";
			String tempDir = System.getProperty("java.io.tmpdir");
			FileOutputStream stream=null;
			String thumbnail_file_name="";
			String material_file_name="";
//			ResourceBundle msgProps = ResourceBundle.getBundle("configurations");
//			String PresenterPath  = msgProps.getString("PresenterPath");
			Map<String,String> configurationMap =(Map<String,String>) request.getSession().getAttribute(ApplicationAttribute.CONFIGURATION_PROPERTIES_MAP);
			String PresenterPath  = configurationMap.get("PresenterPath");
			Session session = HibernateFactory.openSession();
			Transaction tx = session.beginTransaction();
			presenter.setCreated_by(user.getStaffLoginId());
			presenter.setCreated_date(new Date());
			presenter.setModified_by(user.getStaffLoginId());
			presenter.setModified_date(new Date());
			try{
				
				if(request.getSession().getAttribute("thumbnail_file_name")!=null){
					 thumbnail_file_name=(String)request.getSession().getAttribute("thumbnail_file_name");
				}
				if(request.getSession().getAttribute("material_file_name")!=null){
					 material_file_name=(String)request.getSession().getAttribute("material_file_name");
				}
			
				Query query=session.createQuery("from Presenter where presenterName=:presenterName and status=:status");
				query.setParameter("presenterName", presenter.getPresenterName().trim());
				query.setParameter("status", true);
				ArrayList<Presenter> arrActivity=(ArrayList<Presenter>) query.list();
	        	if(arrActivity.size()>0)
	        	{
	        		msg="The Presenter already exist.";
	        	}
	        	else
	        	{	
	        		String serverFilename = "";
					serverFilename = PresenterPath+"/"+presenter.getPresenterName();

					File uploadedFolder = new File(serverFilename);
					if(!uploadedFolder.exists()){
						uploadedFolder.mkdirs();
					}
					
					//upload thumbnail file
					if(thumbnail_file_name!=null && !thumbnail_file_name.equals("")){
						byte[] bytearray=(byte[]) request.getSession().getAttribute("thumbnail_byte_session");
						BufferedImage imag;
						try {
							imag = ImageIO.read(new ByteArrayInputStream(bytearray));
							if(thumbnail_file_name.contains(".png") || thumbnail_file_name.contains(".PNG") )
								ImageIO.write(imag, "png", new File(serverFilename,thumbnail_file_name));
							else if(thumbnail_file_name.contains(".jpg") || thumbnail_file_name.contains(".JPG"))
								ImageIO.write(imag, "jpg", new File(serverFilename,thumbnail_file_name));
							else if(thumbnail_file_name.contains(".gif") || thumbnail_file_name.contains(".GIF"))
									ImageIO.write(imag, "gif", new File(serverFilename,thumbnail_file_name));
						} catch (IOException e) {
							e.printStackTrace();
						}
						//delete temp directory
						File temp_file = new File(tempDir+"/" + thumbnail_file_name);
				        	FileUtils.deleteFileNFolder(temp_file);
				        	presenter.setImage_fileName(thumbnail_file_name);
				        	presenter.setImage_fileLocation((serverFilename+"/"+thumbnail_file_name).substring(8));
				        	serverFilename="";
					}
					//upload material
					if(material_file_name!=null && !material_file_name.equals("")){
						serverFilename = PresenterPath+"/"+presenter.getPresenterName();
						byte[] bytearray=(byte[]) request.getSession().getAttribute("material_byte_session");
						if(material_file_name.contains(".pdf") || material_file_name.contains(".PDF")){
							try {
								 stream = new FileOutputStream(serverFilename+"/"+material_file_name);
							    stream.write(bytearray);
							    stream.close();
							} catch(Exception e){e.printStackTrace();}
						}
						//delete temp directory
						File temp_file = new File(tempDir+"/" + material_file_name);
			        	FileUtils.deleteFileNFolder(temp_file);
			        	presenter.setFileName(material_file_name);
			        	presenter.setFileLocation((serverFilename+"/"+material_file_name).substring(8));
				}
					presenter.setCreated_by(user.getStaffLoginId());
					presenter.setCreated_date(new Date());
	        		session.save(presenter);
	        		
	        		  if(request.getSession().getAttribute("thumbnail_file_name")!=null){
	      					if(thumbnail_file_name.contains(".jpg") || thumbnail_file_name.contains(".JPG")
	      						|| thumbnail_file_name.contains(".png") || thumbnail_file_name.contains(".PNG")
	      						|| thumbnail_file_name.contains(".gif") || thumbnail_file_name.contains("GIF")){
	      		    	 String field_name = (String)request.getSession().getAttribute("thumbnail_field_name");
	      		     	 storeMaterialToDB(session,presenter.getPresenterCode(),"INSERT",request,"thumbnail_byte_session",thumbnail_file_name,field_name);
	      					 }
		      			  request.getSession().removeAttribute("thumbnail_file_name");// removing uploaded file details from session after file is uploaded
		      			 request.getSession().removeAttribute("thumbnail_byte_session");//
		      		 	 request.getSession().removeAttribute("thumbnail_field_name");//
	      		    }
	        		  
	        		  if(request.getSession().getAttribute("material_file_name")!=null){
	        			  if(material_file_name.contains(".pdf") || material_file_name.contains(".PDF")){ 
	      		    	 String field_name = (String)request.getSession().getAttribute("material_field_name");
	      		    	 	storeMaterialToDB(session,presenter.getPresenterCode(),"INSERT",request,"material_byte_session",material_file_name,field_name);
	        			  }
	      			  request.getSession().removeAttribute("material_file_name");// removing uploaded file details from session after file is uploaded
	      			 request.getSession().removeAttribute("material_byte_session");//
	      		 	 request.getSession().removeAttribute("material_field_name");//
	      		    }
	        		  
	        		tx.commit();
	        		msg="The new Presenter has been successfully created.";
	        		
	        		//auditTrailMaintenance.insertAuditTrail(new AuditTrail(String.valueOf(user.getUserCode()), AuditTrail.MODULE_CATEGORY, AuditTrail.FUNCTION_CREATE, "Action on:T_CATEGORY,CATEGORY_ID:"+activity_id));
	        		auditTrailMaintenance.insertAuditTrail(new AuditTrail(user.getStaffLoginId()+"", AuditTrail.MODULE_PRESENTER, AuditTrail.FUNCTION_CREATE, "SUCCESS,Presenter_code:"+presenter.getPresenterCode()));
	        		Pager pager=retrieveActivityListing(getAllPresenter(request));
	        		
	        		request.getSession().setAttribute("pager",pager );
	        		request.setAttribute("pager", pager);
	        		request.getSession().setAttribute("pageObj",new PathDetail().getPageObj("PresenterMaintenance"));
	        	}

			}catch(Exception e){
        		auditTrailMaintenance.insertAuditTrail(new AuditTrail(user.getStaffLoginId()+"", AuditTrail.MODULE_PRESENTER, AuditTrail.FUNCTION_CREATE, "FAILED,presenter_code:"+presenter.getPresenterCode()));
				log.log(Level.SEVERE,e.getMessage());
				e.printStackTrace();
			}try{
				HibernateFactory.close(session);
			}catch(Exception e){
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
			
			MsgObject msgObj = new MsgObject(msg);
			request.setAttribute(RequestAttributes.MESSAGE_OBJ, msgObj);
			request.setAttribute("CacheName", "Presenter");
			//ArrayList arr=getAllPresenter(request);
			//Pager pager=retrieveActivityListing(arr);
		//	request.setAttribute("pager", pager);
			return presenter;
		}
	  /**
       * <p>This method is for update  Presenter</p>
       * @param presenter Presenter object
       * @param requestParameters Servlet Request Parameter
       * @return Presenter Object
       */
	  public Presenter updatePresenter(Presenter presenter,HttpServletRequest request)
		{
		  log.log(Level.INFO,"PresenterMaintenance --> update presenter");
			User user = (User) request.getSession().getAttribute(SessionAttributes.CURR_USER_OBJ);
			AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
			int i=0;
			String msg="The Presenter has not been successfully updated.";
			String tempDir = System.getProperty("java.io.tmpdir");
			FileOutputStream stream=null;
			String thumbnail_file_name="";
			String material_file_name="";
//			ResourceBundle msgProps = ResourceBundle.getBundle("configurations");
//			String PresenterPath  = msgProps.getString("PresenterPath");
			Map<String,String> configurationMap =(Map<String,String>) request.getSession().getAttribute(ApplicationAttribute.CONFIGURATION_PROPERTIES_MAP);
			String PresenterPath  = configurationMap.get("PresenterPath");
			String serverFilename = "";
    		String filePath="";
    		Session session = HibernateFactory.openSession();
			Transaction tx = session.beginTransaction();
			presenter.setModified_by(user.getStaffLoginId());
    		presenter.setModified_date(new Date());
			try{

				if(request.getSession().getAttribute("thumbnail_file_name")!=null){
					 thumbnail_file_name=(String)request.getSession().getAttribute("thumbnail_file_name");
				}
				if(request.getSession().getAttribute("material_file_name")!=null){
					 material_file_name=(String)request.getSession().getAttribute("material_file_name");
				}
			
				String paramater="";
				paramater = "  presenterCode!=:presenterCode and presenterName=:presenterName and status=:status";
				
				Query queryDup=session.createQuery("from Presenter where  "+paramater+"");
				queryDup.setParameter("presenterCode", presenter.getPresenterCode());
				queryDup.setParameter("presenterName", presenter.getPresenterName());
				queryDup.setParameter("status", true);
				ArrayList<Presenter> arrActivity=(ArrayList<Presenter>) queryDup.list();
				
	        	//if(arrActivity.size()>0)
	        	//{
	        //		msg="The Presenter already exist.";
	        //	}
	        //	else
	        //	{	
	        		ArrayList<Presenter> list = new ArrayList<Presenter>();
	        		Query query=session.createQuery("from Presenter where presenterCode=:presenterCode");
	        		query.setParameter("presenterCode", presenter.getPresenterCode());
	        		list = (ArrayList<Presenter>) query.list();

	        		Presenter presenterObj = new Presenter();
	        		presenterObj = list.get(0);
	        		String oldPresenter = presenterObj.getPresenterName();
	        		String new_presenter=presenter.getPresenterName();

	        		
	        		//upload thumbnail file
					if(thumbnail_file_name!=null && !thumbnail_file_name.equals("")){
						//chk for new name or old name
						if(!oldPresenter.equals(new_presenter)){//if new one then create new presenter and delete old one
								serverFilename = PresenterPath+"/"+new_presenter;
								File uploadedFolder = new File(serverFilename);
								if(!uploadedFolder.exists()){
									uploadedFolder.mkdirs();
								}
								FileUtils.deleteFileNFolder(new File(PresenterPath+"/"+oldPresenter));
			        	}else{//if its same then only delete file and store new file
			        		serverFilename = PresenterPath+"/"+oldPresenter+"/"+presenterObj.getImage_fileName();

							File uploadedFolder = new File(serverFilename);
							if(uploadedFolder.exists()){
								FileUtils.deleteFileNFolder(uploadedFolder);
							}
			        	}
						//end 
						
						serverFilename = PresenterPath+"/"+new_presenter;
						File uploadedFolder = new File(serverFilename);
						if(!uploadedFolder.exists()){
							uploadedFolder.mkdirs();
						}
						byte[] bytearray=(byte[]) request.getSession().getAttribute("thumbnail_byte_session");
						BufferedImage imag;
						try {
							imag = ImageIO.read(new ByteArrayInputStream(bytearray));
							if(thumbnail_file_name.contains(".png") || thumbnail_file_name.contains(".PNG") )
								ImageIO.write(imag, "png", new File(serverFilename,thumbnail_file_name));
							else if(thumbnail_file_name.contains(".jpg") || thumbnail_file_name.contains(".JPG"))
								ImageIO.write(imag, "jpg", new File(serverFilename,thumbnail_file_name));
							else if(thumbnail_file_name.contains(".gif") || thumbnail_file_name.contains(".GIF"))
									ImageIO.write(imag, "gif", new File(serverFilename,thumbnail_file_name));
						} catch (IOException e) {
							e.printStackTrace();
						}
						//delete temp directory
						File temp_file = new File(tempDir+"/" + thumbnail_file_name);
				        	FileUtils.deleteFileNFolder(temp_file);
				        	presenter.setImage_fileName(thumbnail_file_name);
				        	presenter.setImage_fileLocation((serverFilename+"/"+thumbnail_file_name).substring(8));
				        	serverFilename="";
					}else{
						
						FileUtils.renameFolder(PresenterPath +"/"+ oldPresenter, new_presenter);
						presenter.setImage_fileName(presenterObj.getImage_fileName());
						presenter.setImage_fileLocation(presenter.getImage_fileLocation());
					}
					//upload material
					if(material_file_name!=null && !material_file_name.equals("")){
						//chk for new name or old name
						if(!oldPresenter.equals(new_presenter)){//if new one then create new presenter and delete old one
								serverFilename = PresenterPath+"/"+new_presenter;
								File uploadedFolder = new File(serverFilename);
								if(!uploadedFolder.exists()){
									uploadedFolder.mkdirs();
								}
								FileUtils.deleteFileNFolder(new File(PresenterPath+"/"+oldPresenter));
			        	}else{//if its same then only delete file and store new file
			        		serverFilename = PresenterPath+"/"+oldPresenter+"/"+presenterObj.getFileName();

							File uploadedFolder = new File(serverFilename);
							if(uploadedFolder.exists()){
								FileUtils.deleteFileNFolder(uploadedFolder);
							}
			        	}
						//end 
						
						serverFilename = PresenterPath+"/"+new_presenter;
						File uploadedFolder = new File(serverFilename);
						if(!uploadedFolder.exists()){
							uploadedFolder.mkdirs();
						}
						byte[] bytearray=(byte[]) request.getSession().getAttribute("material_byte_session");
						if(material_file_name.contains(".pdf") || material_file_name.contains(".PDF")){
							try {
								 stream = new FileOutputStream(serverFilename+"/"+material_file_name);
							    stream.write(bytearray);
							    stream.close();
							} catch(Exception e){e.printStackTrace();}
						}
						//delete temp directory
						File temp_file = new File(tempDir+"/" + material_file_name);
			        	FileUtils.deleteFileNFolder(temp_file);
			        	presenter.setFileName(material_file_name);
			        	presenter.setFileLocation((serverFilename+"/"+material_file_name).substring(8));
				}else{
					FileUtils.renameFolder(PresenterPath +"/"+ oldPresenter, presenter.getPresenterName());
					presenter.setFileName(presenterObj.getFileName());
					presenter.setFileLocation(presenter.getFileLocation());
				}

					
	        		presenter.setModified_by(user.getStaffLoginId());
	        		presenter.setModified_date(new Date());
	    			
	        		//presenterObj.setFileName(file_name);
	        		//presenterObj.setFileLocation(file_path.replace(oldPresenter, presenter_name));
	        		//category.setHash_code((int)(Math.random() * 1000000000));
	        		HibernateFactory.close(session);
	        		session = HibernateFactory.openSession();
					tx= session.beginTransaction();
	        		session.update(presenter); 
	        		  if(request.getSession().getAttribute("thumbnail_file_name")!=null){
	      					if(thumbnail_file_name.contains(".jpg") || thumbnail_file_name.contains(".JPG")
	      						|| thumbnail_file_name.contains(".png") || thumbnail_file_name.contains(".PNG")
	      						|| thumbnail_file_name.contains(".gif") || thumbnail_file_name.contains("GIF")){
	      		    	 String field_name = (String)request.getSession().getAttribute("thumbnail_field_name");
	      		     	 storeMaterialToDB(session,presenter.getPresenterCode(),"UPDATE",request,"thumbnail_byte_session",thumbnail_file_name,field_name);
	      					 }
		      			  request.getSession().removeAttribute("thumbnail_file_name");// removing uploaded file details from session after file is uploaded
		      			 request.getSession().removeAttribute("thumbnail_byte_session");//
		      		 	 request.getSession().removeAttribute("thumbnail_field_name");//
	      		    }
	        		  
	        		  if(request.getSession().getAttribute("material_file_name")!=null){
	        			  if(material_file_name.contains(".pdf") || material_file_name.contains(".PDF")){ 
	      		    	 String field_name = (String)request.getSession().getAttribute("material_field_name");
	      		    	 	storeMaterialToDB(session,presenter.getPresenterCode(),"UPDATE",request,"material_byte_session",material_file_name,field_name);
	        			  }
	      			  request.getSession().removeAttribute("material_file_name");// removing uploaded file details from session after file is uploaded
	      			 request.getSession().removeAttribute("material_byte_session");//
	      		 	 request.getSession().removeAttribute("material_field_name");//
	      		    }
	        		  
	        		tx.commit();

	        		auditTrailMaintenance.insertAuditTrail(new AuditTrail(user.getStaffLoginId()+"", AuditTrail.MODULE_PRESENTER, AuditTrail.FUNCTION_UPDATE, "SUCCESS,Presenter_code:"+presenter.getPresenterCode()));
	        		msg="The Presnter has been successfully updated.";
	        //	}
			}catch(Exception e){
				log.log(Level.SEVERE,e.getMessage());
				auditTrailMaintenance.insertAuditTrail(new AuditTrail(user.getStaffLoginId()+"", AuditTrail.MODULE_PRESENTER, AuditTrail.FUNCTION_UPDATE, "FAILED,Presenter_code:"+presenter.getPresenterCode()));
				e.printStackTrace();
			}try{
				HibernateFactory.close(session);
			}catch(Exception e){
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
			MsgObject msgObj = new MsgObject(msg);
			request.setAttribute(RequestAttributes.MESSAGE_OBJ, msgObj);
			request.setAttribute("CacheName", "Presenter");
			Pager pager=retrieveActivityListing(getAllPresenter(request));
			request.getSession().setAttribute("pager", pager);
    		request.setAttribute("pager", pager);
    		request.getSession().setAttribute("pageObj",new PathDetail().getPageObj("PresenterMaintenance"));
			return presenter;
		}
	
		
	  /**
       * <p>This method is for listing all Presenter</p>
       * @param presenter list
       * @return Pager Object
       */
		public Pager retrieveActivityListing( List arrPresenter)
		{
			log.log(Level.INFO,"PresenterMaintenance --> presnter listing");
			LinkedList item = new LinkedList();
			Presenter presenterObj=new Presenter();
			try
			{
				for(int i = 0; i < arrPresenter.size(); i++)
				{
					presenterObj = (Presenter)arrPresenter.get(i);
					item.add(presenterObj.getPresenterListingTableRow(i));
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
		/**
	       * <p>This method is for search  Presenter</p>
	       * @param requestParameters Servlet Request Parameter
	       * @return Presenter List
	       */

		public ArrayList searchPresenter(HttpServletRequest req) {
			log.log(Level.INFO,"PresenterMaintenance --> search presenter ");
			ArrayList arrPresenter = new ArrayList();
			
			String presenterName=req.getParameter(NAME_PARAM);
			String category=req.getParameter("presenter_category");
			String month = req.getParameter(MONTH_PARAM);
			String year = req.getParameter(YEAR_PARAM);
			String bu = req.getParameter(BU_PARAM);
			String district = req.getParameter(DISTRICT_PARAM);
			String city = req.getParameter(CITY_PARAM);
			String ssc = req.getParameter(SSC_PARAM);
			String office = req.getParameter("office");
			String branchCode=req.getParameter("branchCode");
			Session session = HibernateFactory.openSession();
			try
			{
				Criteria crit = session.createCriteria(Presenter.class);
				if(presenterName!=null && presenterName.length() > 0)
					crit.add(Restrictions.like("presenterName", presenterName,MatchMode.ANYWHERE));
				if(category!=null && Integer.parseInt(category) !=0)
					crit.add(Restrictions.eq("category", Integer.parseInt(category)));
				if(month!=null)
					crit.add(Restrictions.sqlRestriction("MONTH(START_DATE)=?", Integer.parseInt(month),IntegerType.INSTANCE));
				if(year!=null)
					crit.add(Restrictions.sqlRestriction("YEAR(START_DATE)=?", Integer.parseInt(year),IntegerType.INSTANCE));
				if(bu!=null && Integer.parseInt(bu) !=0)
					crit.add(Restrictions.eq("buCode", Integer.parseInt(bu)));
				if(district!=null && Integer.parseInt(district) !=0)
					crit.add(Restrictions.eq("distCode", Integer.parseInt(district)));
				
				if(branchCode!=null && Integer.parseInt(branchCode) !=0)
					crit.add(Restrictions.eq("branchCode", Integer.parseInt(branchCode)));
				
				if(city!=null && Integer.parseInt(city) !=0)
					crit.add(Restrictions.eq("cityCode", Integer.parseInt(city)));
				
				if(office!=null && Integer.parseInt(office) !=0)
					crit.add(Restrictions.eq("officeCode", Integer.parseInt(office)));
				
				if(ssc!=null && Integer.parseInt(ssc) !=0)
					crit.add(Restrictions.eq("sscCode", Integer.parseInt(ssc)));
				crit.add(Restrictions.eq("status", true));
			
				List l=crit.list();
				Iterator it=l.iterator();
			
				while(it.hasNext())
				{
					Presenter presenter=(Presenter)it.next();
					arrPresenter.add(presenter);
					
				}
			}
			catch(Exception e)
			{
				log.log(Level.SEVERE,e.getMessage());
				e.printStackTrace();
			}try{
				HibernateFactory.close(session);
			}catch(Exception e){
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
			return arrPresenter;
		}
/**
	       * <p>This method is for delete  Presenter</p>
	       * @param presenter code
	       * @param requestParameters Servlet Request Parameter
	       * @return void
	       */
		  public  void deletePresenter(String  presenter_code, HttpServletRequest request)
		    {
			  log.log(Level.INFO,"PresenterMaintenance --> delete presenter ");
			  AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
			  User user = (User) request.getSession().getAttribute(SessionAttributes.CURR_USER_OBJ);
			  Session session = HibernateFactory.openSession();
				Transaction tx = session.beginTransaction();
			  String msg="";
			  Presenter presenter=new Presenter();
		        try
		        {
		    		presenter = (Presenter)session.get(Presenter.class,Integer.parseInt(presenter_code));
		    		presenter.setStatus(false);
		    		session.update(presenter);
		    		tx.commit();
		    		
		    		
		    		/*ResourceBundle msgProps = ResourceBundle.getBundle("configurations");
					String ModulePath  = msgProps.getString("PresenterPath");
					String filePath = ModulePath+"/"+presenterName;

				    File file = new File(filePath);
				    FileUtils.deleteFileNFolder(file);*/
				    msg="The Presenter has been successfully deleted.";
				    auditTrailMaint.insertAuditTrail(new AuditTrail(user.getStaffLoginId()+"", AuditTrail.MODULE_PRESENTER, AuditTrail.FUNCTION_DELETE, "SUCCESS,Presenter_code:"+presenter_code));
				    
				    
		        }
		        catch(Exception e)
		        {
		        	msg="The Presenter has not been successfully deleted.";
		        	auditTrailMaint.insertAuditTrail(new AuditTrail(user.getStaffLoginId()+"", AuditTrail.MODULE_PRESENTER, AuditTrail.FUNCTION_DELETE, "FAILED,Presenter_code:"+presenter_code));
		        	 log.log(Level.SEVERE,e.getMessage());	
		        	 e.printStackTrace();
		        
		        }try{
					HibernateFactory.close(session);
				}catch(Exception e){
					log.log(Level.SEVERE, e.getMessage());
					e.printStackTrace();
				}
		      
		       MsgObject msgObj = new MsgObject(msg);
		       request.setAttribute("CacheName", "Presenter");
		       request.setAttribute(RequestAttributes.MESSAGE_OBJ, msgObj);
		      
		       Pager pager=retrieveActivityListing(getAllPresenter(request));
		       request.getSession().setAttribute("pager", pager);
       			request.setAttribute("pager", pager);
       			request.getSession().setAttribute("pageObj",new PathDetail().getPageObj("PresenterMaintenance"));
		    }
	/**
	       * <p>This method is for getting detail of presenter</p>
	       * @param presenter code 
	       * @return Presenter Object
	       */  
		  public Presenter getPresenterDetail(int presenterCode)
			{
			  log.log(Level.INFO,"PresenterMaintenance --> get presneter detail ");
				Presenter presenter = new Presenter();
				Session session = HibernateFactory.openSession();
				try{
				presenter = (Presenter)session.get(Presenter.class,presenterCode);
				}catch(Exception e)
				{
					log.log(Level.SEVERE, e.getMessage());
					e.printStackTrace();
				}finally{
					try{
						HibernateFactory.close(session);
						
					}catch(Exception e){
						log.log(Level.SEVERE, e.getMessage());
						e.printStackTrace();
					}
			}
				
				return presenter;
			}
		  /**
	       * <p>This method is for getting all  Presenter</p>
		 * @param requestParamters 
	       * @return Presenter List
	       */		  
		  public ArrayList getAllPresenter(HttpServletRequest req) {

				log.log(Level.INFO, "PresenterMaintanence --> getAllPresenter");
				ArrayList arrActivity = new ArrayList();
				Session session = null;
				int month=Calendar.getInstance().get(Calendar.MONTH)+1;
				int year= Calendar.getInstance().get(Calendar.YEAR);
				User userObj = (User)req.getSession().getAttribute("currUserObj");
				try {

					session = HibernateFactory.openSession();
					Criteria crit = session.createCriteria(Presenter.class);
					crit.add(Restrictions.sqlRestriction("MONTH(START_DATE)=?",month,IntegerType.INSTANCE));
					crit.add(Restrictions.sqlRestriction("YEAR(START_DATE)=?", year,IntegerType.INSTANCE));
					crit.add(Restrictions.eq("status", true));
					
					if(userObj.isBuLevel() && userObj.getBuCode()!=0){
						crit.add(Restrictions.eq("buCode", userObj.getBuCode()));
					}
					if(userObj.isDistrictLevel()){
						crit.add(Restrictions.eq("distCode", userObj.getDistrict()));
					}
					if(userObj.isBranchLevel()){
						crit.add(Restrictions.eq("branchCode", userObj.getBranchCode()));
					}
					if(userObj.isCityLevel()){
						crit.add(Restrictions.eq("cityCode", userObj.getCityCode()));
					}
					if(userObj.isSscLevel()){
						crit.add(Restrictions.eq("sscCode", userObj.getSscCode()));
					}
					if(userObj.isOfficeLevel()){
						crit.add(Restrictions.eq("officeCode", userObj.getOfficeCode()));
					}
					
					
					
					arrActivity = (ArrayList) crit.list();

				} catch (Exception e) {
					log.log(Level.SEVERE, e.getMessage());
					e.printStackTrace();
				} finally {
					HibernateFactory.close(session);
				}

				return arrActivity;
			}

		  /**
			  * <p>Method is used to get all Presenter for Rest service</p>
			  * @return  ArrayList of Presenter
			  */
  public ArrayList getAllPresenterRest(AamData aamData,String agentId) {
		// TODO Auto-generated method stub
	  log.log(Level.SEVERE,"PresenterMaintenance -->  getAllPresenterRest ");
		Session session = null;
		ArrayList presenterList = new ArrayList();
		ArrayList<Presenter> list = new ArrayList<Presenter>();
		ArrayList<Bu> bulist=new ArrayList<Bu>();
		ArrayList<District> distlist=new ArrayList<District>();
		ArrayList<City> citylist=new ArrayList<City>();
		ArrayList<Ssc> ssclist=new ArrayList<Ssc>();
		
		try{
			
			session = HibernateFactory.openSession();
			Date sdate=new Date();
			log.log(Level.SEVERE,"Start Time "+sdate.getTime());
			
//			Criteria crit = session.createCriteria(Ssc.class);
//			crit.add(Restrictions.eq("sscName", aamData.getSsc()));
//			crit.add(Restrictions.eq("status", true));
//			ssclist=(ArrayList<Ssc>) crit.list();
//			Ssc ssc=ssclist.get(0);
//			
//			Query query=session.createQuery("FROM City where status = 1 and cityCode=:citycode ");
//			query.setParameter("citycode", ssc.getCityCode());
//			citylist=(ArrayList<City>) query.setCacheable(true).list();
//			City city=citylist.get(0);
//			
//			query=session.createQuery("FROM District where status = 1 and districtCode=:distcode ");
//			query.setParameter("distcode", city.getBranchCode());
//			distlist=(ArrayList<District>) query.setCacheable(true).list();
//			District dist=distlist.get(0);
//			
//			query = session.createQuery("FROM Bu where status = 1 and buCode=:bucode ");		
//			query.setParameter("bucode", dist.getBuCode());
//			bulist=(ArrayList<Bu>) query.setCacheable(true).list();
//			Bu bu=bulist.get(0);

//			Query query=session.createQuery("FROM Presenter where status = 1 and buCode=:bucode  and district=0 ");
//			query.setParameter("bucode",aamData.getBuCode());
//			list=(ArrayList<Presenter>) query.setCacheable(true).list();
//			presenterList.addAll(list);
//			
//			query=session.createQuery("FROM Presenter where status = 1  and district=:distcode and  branchCode=0 ");
//			query.setParameter("distcode",aamData.getDistrictCode());
//			list=(ArrayList<Presenter>) query.setCacheable(true).list();
//			presenterList.addAll(list);
//			
//			
//			query=session.createQuery("FROM Presenter where status = 1  and branchCode=:branchCode and  cityCode=0 ");
//			query.setParameter("branchCode",aamData.getBranchCode());
//			list=(ArrayList<Presenter>) query.setCacheable(true).list();
//			presenterList.addAll(list);
//			
//			
//			query=session.createQuery("FROM Presenter where status = 1  and cityCode=:citycode and sscCode=0 ");
//			query.setParameter("citycode", aamData.getCityCode());
//			list=(ArrayList<Presenter>) query.setCacheable(true).list();
//			presenterList.addAll(list);
//			
//			
//			query=session.createQuery("FROM Presenter where status = 1 and  sscCode=:ssccode ");
//			query.setParameter("ssccode", aamData.getSscCode());
//			list=(ArrayList<Presenter>) query.setCacheable(true).list();
//			presenterList.addAll(list);
			
			Query query=session.createQuery("FROM Presenter where status = 1  and "
					+ "( (buCode=:bucode  and district=0) "
					+ "or (district=:distcode and  branchCode=0)"
					+ "or (branchCode=:branchCode and  cityCode='0')"
					+ "or (cityCode=:citycode and sscCode='0')"
					+ "or (sscCode=:ssccode and officeCode = '0')"
					+ "or (officeCode =:officeCode )  )");
			
			query.setParameter("bucode",aamData.getBuCode());
			query.setParameter("distcode",aamData.getDistrictCode());
			query.setParameter("branchCode",aamData.getBranchCode());
			query.setParameter("citycode", aamData.getCity());
			query.setParameter("ssccode", aamData.getSsc());
			query.setParameter("officeCode", aamData.getOfficeCode());
			list=(ArrayList<Presenter>) query.setCacheable(true).list();
			presenterList.addAll(list);
			
			Date edate=new Date();
			log.log(Level.SEVERE,"Total  Time "+(edate.getTime()- sdate.getTime()));

			}catch(Exception e)
			{
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}finally{
				try{
					HibernateFactory.close(session);
					
				}catch(Exception e){
					log.log(Level.SEVERE, e.getMessage());
					e.printStackTrace();
				}
		}
		
		return presenterList;
	}
  /**
	  * <p>Method is used to save presenter material details in db </p>
	  * @param  session 
	  * @param  pCode presenter code
	  * @param  op (INSERT/UPDATE) 
	  * @param  HttpServletRequest requestParameters 
	  * @param  pCode 
	  * @param  byteSession 
	  * @param   field_name 
	  */
  public void storeMaterialToDB(Session session,int pCode,String op,HttpServletRequest requestParameters,String byteSession,String file_name,String field_name){
		
		try{
		
	    	if(op.equals("UPDATE")){
	    		Query deleteQ = session.createQuery("delete PresenterMaterial where presenterCode =:presenterCode and fieldName =:fieldName");
	    		deleteQ.setParameter("presenterCode", pCode);
	    		deleteQ.setParameter("fieldName", field_name);
		    	deleteQ.executeUpdate(); 
	    	}
	    	PresenterMaterial preMat = null;
			 byte[] bytearray=(byte[]) requestParameters.getSession().getAttribute(byteSession);
		   if(pCode>0){
			    	preMat = new PresenterMaterial();
			    	preMat.setPresenterCode(pCode);
			    	preMat.setMaterialName(file_name);
			    	preMat.setFieldName(field_name);
			    	preMat.setMaterial(bytearray);
			    	session.save(preMat);
			    }
			    	log.log(Level.INFO,"---PresenterMaintenance Upload Material  Done---");
		}catch(Exception e){
			e.printStackTrace();
			log.log(Level.SEVERE, e.getMessage());
		}
	}
  /**
	  * <p>Method is used to get Presenter material based on presenter code </p>
	  * @param  presenterCode 
	  * @return  PresenterMaterial object
	  */
	public PresenterMaterial getPresenterMaterial(int presenterCode)
	{
		log.log(Level.INFO,"PresenterMaintenance ---> getPresenterMaterial ");
		 Session session = null;
		 PresenterMaterial mat = null;
		try{
		session = HibernateFactory.openSession();
		Query selectQ = session.createQuery("select materialName,material from PresenterMaterial where presenterCode = "+presenterCode+" and fieldName = 'filename2'");
		List list = selectQ.list();
		
		if(list!=null && list.size() > 0){
				mat = new PresenterMaterial();
			   Object [] objectDoc = (Object []) list.get(0);
			   mat.setMaterialName(( String)objectDoc[0]);
			   mat.setMaterial( ( byte[] )objectDoc[1]);
		}else{
			log.log(Level.INFO,"PresenterMaintenance ---> getPresenterMaterial : No Material Found ");
		}
	
		}catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}finally{
			try{
				HibernateFactory.close(session);
				
			}catch(Exception e){
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
		}
			return mat;
	}
		  public static final String PRESENTER_CATEGORY_PARAM="presenter_category_code";
		  public static final String NAME_PARAM="name";
		  public static final String START_DATE_PARAM="startdate";
		  public static final String EXPIRY_DATE_PARAM="enddate";
		  public static final String DESC_PARAM="desc";
		public static final String BU_PARAM = "bu";
		public static final String DISTRICT_PARAM = "district";
		public static final String CITY_PARAM = "city";
		public static final String SSC_PARAM = "ssc";
		public static final String MONTH_PARAM = "month";
		public static final String YEAR_PARAM = "year";
		public static final String CREATED_BY_PARAM = "created_by";
		public static final String PRESENTER_VISIBILITY_PARAM="presenter_visibility";
		 public static final KeyObjPair MONTH_VALUE_PAIR[] = {
		        new KeyObjPair("01", "January"), new KeyObjPair("02", "February"), new KeyObjPair("03", "March"), new KeyObjPair("04", "April"), new KeyObjPair("05", "May"), new KeyObjPair("06", "June"), new KeyObjPair("07", "July"), new KeyObjPair("08", "August"), new KeyObjPair("09", "September"), new KeyObjPair("10", "October"), 
		        new KeyObjPair("11", "November"), new KeyObjPair("12", "December")
		    };




		
	
	
}
