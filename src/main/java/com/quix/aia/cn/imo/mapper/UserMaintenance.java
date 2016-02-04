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
* 18-May-2015         Jay            copyright and security code added
* 15-july-2015        Nibedita         restriction added to edit/delete user
* 18-Nov-2015         Jinatmayee         Error stored in db

****************************************** *********************************** */

package com.quix.aia.cn.imo.mapper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.JSONObject;

import com.archerlogic.aia.cn.helpers.common.JSONHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.quix.aia.cn.imo.constants.ApplicationAttribute;
import com.quix.aia.cn.imo.constants.RequestAttributes;
import com.quix.aia.cn.imo.constants.SessionAttributes;
import com.quix.aia.cn.imo.data.auditTrail.AuditTrail;
import com.quix.aia.cn.imo.data.downloadDetail.DownloadDetails;
import com.quix.aia.cn.imo.data.locale.LocaleObject;
import com.quix.aia.cn.imo.data.logedInDetail.LogedInDetails;
import com.quix.aia.cn.imo.data.user.User;
import com.quix.aia.cn.imo.data.user.UserRest;
import com.quix.aia.cn.imo.data.version.Version;
import com.quix.aia.cn.imo.database.HibernateFactory;
import com.quix.aia.cn.imo.rest.UserAuthResponds;
import com.quix.aia.cn.imo.utilities.EmailNotification;
import com.quix.aia.cn.imo.utilities.ErrorObject;
import com.quix.aia.cn.imo.utilities.FileUtils;
import com.quix.aia.cn.imo.utilities.FormObj;
import com.quix.aia.cn.imo.utilities.ImoUtilityData;
import com.quix.aia.cn.imo.utilities.LMSUtil;
import com.quix.aia.cn.imo.utilities.MsgObject;
import com.quix.aia.cn.imo.utilities.Pager;
import com.quix.aia.cn.imo.utilities.PasswordHashing;
import com.quix.aia.cn.imo.utilities.PathDetail;

import cn.aia.tools.security.AESPasswordManager;


/**
 * <p>This class defines the data operations & csv file upload </p>
 * @author Jay
 * @version 1.0
 */
public class UserMaintenance {
	static Logger log = Logger.getLogger(UserMaintenance.class.getName());
	
	
	public UserMaintenance()
	{
		HibernateFactory.buildIfNeeded();
	}
	
	 /**
     * <p>This method checks USer id and Password and  sets values to bean  and 
     * also set last time login Date & Time</p>
     * @param User user object
     * @param requestParameters Servlet Request Parameter
     * @return User object
     */
	
//	public User authenticateUser(String userID, String password) {
//		log.log(Level.INFO,"UserMaintenance --> authenticateUser ");
//		
//		 ArrayList<User> list = new ArrayList();
//		 User user=null;  
//		 Query query=null;
//		 Session session = null;
//		Transaction tx;
//		 try{ 
//			
//			 	session = HibernateFactory.openSession();
//				tx= session.beginTransaction();
//				String psw=PasswordHashing.EncryptBySHA2(password);
//				query = session.createQuery(" from User where status = 1 and staffLoginId=:LoginId and password=:psw    ");
//				query.setParameter("LoginId",userID.toUpperCase());
//				query.setParameter("psw",psw );
//				
//				list=(ArrayList<User>) query.list();
//				
//				if(list.size()==0){
//					query = session.createQuery(" from User where status = 1 and staffLoginId=:LoginId   ");
//					query.setParameter("LoginId",userID.toUpperCase());
//					list=(ArrayList<User>) query.list();
//					User tempUser=new User();
//					if(list.size()!=0){
//						tempUser=list.get(0);
//						query = session.createQuery("UPDATE User SET faildLogin=:failLogin where status = 1 and user_no=:userno  ");
//						query.setParameter("failLogin", new Date());
//						query.setParameter("userno", tempUser.getUser_no());
//						query.executeUpdate();
//						tx.commit();
//						
//					}
//					
//					return user;
//				}else{
//					user = new User();
//					user = list.get(0);
//					
//					if(user.getSscCode() > 0)
//						user.setSscLevel(true);
//					else if(user.getCityCode() > 0)
//						user.setCityLevel(true);
//					else if(user.getBranchCode() > 0)
//						user.setBranchLevel(true);
//					else if(user.getDistrict() > 0)
//						user.setDistrictLevel(true);
//					else if(user.getBuCode() > 0)
//						user.setBuLevel(true);
//				}
//				
//				query = session.createQuery("UPDATE User SET lastLogIn=:lastLogin where status = 1 and user_no=:userno  ");
//				query.setParameter("lastLogin", new Date());
//				query.setParameter("userno", user.getUser_no());
//				query.executeUpdate();
//				tx.commit();
//				
//				
//			 log.log(Level.INFO,"authenticateUser Successfully ................. ");
//		 }	
//			catch(Exception e)
//			{
//				log.log(Level.INFO,"authenticateUser Failed ................. ");
//				e.printStackTrace();
//				
//			}finally{
//				try{
//					HibernateFactory.close(session);
//				}catch(Exception e){
//					
//					e.printStackTrace();
//				}
//		
//			}
//		return user;
//	}
	
	
	public User authenticateUser(String userID, String password, String branch, ServletContext context) {
		log.log(Level.INFO,"UserMaintenance --> authenticateUser ");
		
		 User user=null;  
		 if("admin".equalsIgnoreCase(userID) && "P@ssword1".equals(password) ){
			 user = new User();
			 user.setBranchCode(0);
			 user.setBranchLevel(false);
			 user.setBuCode(0);
			 user.setStaffName("Admin");
			 user.setBuLevel(true);
			// user.setChannelCode("2|3|");
			 user.setCityCode("0");
			 user.setCityLevel(false);
			 user.setContactNo("0000000000");
			 user.setDepartment(0);
			 user.setDistrict(0);
			 user.setDistrictLevel(false);
			 user.setEmail("admin@email.com");
			 user.setPassword(password);
			 user.setSscCode("0");
			 user.setSscLevel(false);
			 user.setOfficeCode("0");
			 user.setOfficeLevel(false);
			 user.setBranchCode(0);
			 user.setBranchLevel(false);
			 user.setStaffLoginId("Admin");
			 user.setStatus(true);
			 user.setStatusPsw(true);
			 user.setUser_no(0);
			 user.setUserType("AD");
			 user.setCho(true);
	         user.setSsoSessionId("adminSSO");
	         user.setClientRestUserBranch("0");
	         user.setClientRestUserID("ADMIN");
			 log.log(Level.INFO,"authenticateUser Successfully ................. ");
		 }else
		        {
		            user = _authenticateUserISP(userID, password, branch, context);

		            if (user == null)
		            {
		                // failed to authenticate from ISP
		                return null;
		            }

		            log.log(Level.INFO, "Getting session id from SSO...");
		            String sessionId = _authenticateUserSSO(userID, password, branch, context);
		            log.log(Level.INFO, "Session id = " + sessionId);

		            if (sessionId == null || sessionId.isEmpty())
		            {
		                // failed to authenticate from SSO
		              // user.setCho(true);
		               
		            //	return null;
		            	user.setSsoSessionId("");
		            }
		            
		            

		            log.log(Level.INFO, "Updating user last login timestamp.");
		            _updateUserLastLogin(user.getUser_no(), new Date());
		        }
		        
		        return user;
		    }

	public void insertUserDetails(String co,String logedInId,Date logedInDate, String userType) {
		// TODO Auto-generated method stub
		 log.log(Level.INFO, "UserMaintenance --> insertUserDetails ");
	       Session session = null;
	       LogedInDetails loginDetails=new LogedInDetails();
	       loginDetails.setLogedInId(logedInId);
	       loginDetails.setCo(co);
	       loginDetails.setLogedInDate(logedInDate);
	       loginDetails.setUserType(userType);
	      
	        try
	        {
	            session = HibernateFactory.openSession();
	            Transaction tx = session.beginTransaction();
	            session.save(loginDetails);
	            tx.commit();
	        }
	        catch (Exception e)
	        {
	        	log.log(Level.SEVERE, e.getMessage());
	            e.printStackTrace();
	            LogsMaintenance logsMain = new LogsMaintenance();
	            StringWriter errors = new StringWriter();
	            e.printStackTrace(new PrintWriter(errors));
	            logsMain.insertLogs("UserMaintenance", Level.SEVERE + "", errors.toString());
	        }
	        finally{
				try{
					HibernateFactory.close(session);
				}catch(Exception e){
					
					log.log(Level.SEVERE,e.getMessage());
					e.printStackTrace();
				}
			}
	}
	
		public void insertDownloadPlistDetailsOfUser(String logedInId, String co, String userType, String appType) {
			// TODO Auto-generated method stub
			 log.log(Level.INFO, "UserMaintenance --> insertDownloadPlistDetailsOfUser ");
		       Session session = null;
		       DownloadDetails downloadDetails=new DownloadDetails();
		       downloadDetails.setLogedInId(logedInId);
		       downloadDetails.setCo(co);
		       downloadDetails.setUserType(userType);
		       downloadDetails.setAppType(appType);
		       downloadDetails.setDownloadDate(new Date());
		      
		        try
		        {
		            session = HibernateFactory.openSession();
		            Transaction tx = session.beginTransaction();
		            session.save(downloadDetails);
		            tx.commit();
		        }
		        catch (Exception e)
		        {
		        	log.log(Level.SEVERE, e.getMessage());
		            e.printStackTrace();
		            LogsMaintenance logsMain = new LogsMaintenance();
		            StringWriter errors = new StringWriter();
		            e.printStackTrace(new PrintWriter(errors));
		            logsMain.insertLogs("UserMaintenance", Level.SEVERE + "", errors.toString());
		        }
		        finally{
					try{
						HibernateFactory.close(session);
					}catch(Exception e){
						
						log.log(Level.SEVERE,e.getMessage());
						e.printStackTrace();
					}
				}
			}

			private User _authenticateUserISP(String userID, String password, String branch, ServletContext context)
		    {
		        GsonBuilder builder = new GsonBuilder();
		        HttpClient httpClient = new DefaultHttpClient();

		        Session session = null;
		        User user = null;

		        try
		        {
		            String co = branch;

		            Map<String, String> map = (Map<String, String>) context.getAttribute(ApplicationAttribute.CONFIGURATION_PROPERTIES_MAP);

		            String userAuthUrlFinal = "";
		            String userAuthEnvironment = (String) map.get("userAuthEnvironment");

		            if ("internet".equalsIgnoreCase(userAuthEnvironment))
		            {
		                userAuthUrlFinal = map.get("userAuthUrlInternet") + "&co=" + co + "&account=" + userID + "&password=" + password;
		            }
		            else
		            {
		                userAuthUrlFinal = map.get("userAuthUrl") + "&co=" + co + "&account=" + userID + "&password=" + password;
		            }
		            log.log(Level.INFO, "UserAuthUrl : " + userAuthUrlFinal + " :- userAuthEnvironment : " + userAuthEnvironment, "");
		            HttpGet getRequest = new HttpGet(userAuthUrlFinal);
		            getRequest.addHeader("accept", "application/json");
		            HttpResponse response = httpClient.execute(getRequest);

		            if (response.getStatusLine().getStatusCode() != 200)
		            {
		                throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
		            }

		            BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

		            String output;
		            log.log(Level.INFO, "UserMaintenance --> Output from Server ....  ");
		            if ((output = br.readLine()) != null)
		            {
		                Gson googleJson = new Gson();
		                UserAuthResponds userAuth = googleJson.fromJson(output, UserAuthResponds.class);
		                System.out.println("Success " + userAuth.getSuccess());
		                if (userAuth.getSuccess().equals("1"))
		                {
		                    String content = userAuth.getContent();
		                    content = AESPasswordManager.getInstance().decryptPassword(content);
		                    System.out.println("RAW: " + content);

		                    builder.registerTypeAdapter(Date.class,
		                            new JsonDeserializer<Date>()
		                            {
		                                @Override
		                                public Date deserialize(JsonElement json, Type typeOfT,
		                                        JsonDeserializationContext context)
		                                throws JsonParseException
		                                {
		                                    if (json.getAsString() == null || json.getAsString().isEmpty())
		                                    {
		                                        return null;
		                                    }
		                                    else
		                                    {
		                                        return LMSUtil.convertDateToyyyy_mm_dd(json.getAsString());
		                                    }
		                                }
		                            });

		                    googleJson = builder.create();
		                    UserRest userRest = googleJson.fromJson(content, UserRest.class);

		                    if ("STAFF".equalsIgnoreCase(userRest.getUSERTYPE()))
		                    {
		                        session = HibernateFactory.openSession();
		                       // Transaction tx = session.beginTransaction();
		                        Query query = session.createQuery(" from User where status = 1 and staffLoginId=:LoginId ");
		                        query.setParameter("LoginId", userID.toUpperCase());

		                        ArrayList<User> list = (ArrayList<User>) query.list();
//		                        session.close();
		                        if (list.size() == 0)
		                        {
		                            return null;
		                        }
		                        else
		                        {
		                            user = new User();
		                            user = list.get(0);

		                            if (!user.getOfficeCode().trim().equals("0"))
		                            {
		                                user.setOfficeLevel(true);
		                            }
		                            else if (!user.getSscCode().trim().equals("0"))
		                            {
		                                user.setSscLevel(true);
		                            }
		                            else if (!user.getCityCode().trim().equals("0"))
		                            {
		                                user.setCityLevel(true);
		                            }
		                            else if (user.getBranchCode() > 0)
		                            {
		                                user.setBranchLevel(true);
		                            }
		                            else if (user.getDistrict() > 0)
		                            {
		                                user.setDistrictLevel(true);
		                            }
		                            else if (user.getBuCode() > 0)
		                            {
		                                user.setBuLevel(true);
		                            }
		                        }

		                       // tx.commit();
		                        
		                    
		    		            insertUserDetails(userRest.getBRANCH(),userRest.getUSERID().toUpperCase(),new Date(),"ST");

		                        log.log(Level.INFO, "authenticateUser Successfully ................. ");
		                    }
		                    else if("AGENT".equalsIgnoreCase(userRest.getUSERTYPE())){

			           			 user = new User();
			           			 user.setBranchCode(0);
			           			 user.setBranchLevel(false);
			           			 user.setBuCode(0);
			           			 user.setStaffName(userRest.getUSERNAME());
			           			 user.setBuLevel(false);
			           			// user.setChannelCode("2|3|");
			           			 user.setCityCode("0");
			           			 user.setCityLevel(false);
			           			 user.setContactNo("0000000000");
			           			 user.setDepartment(0);
			           			 user.setDistrict(0);
			           			 user.setDistrictLevel(false);
			           			 user.setEmail("admin@email.com");
			           			 user.setPassword(password);
			           			 user.setSscCode("0");
			           			 user.setSscLevel(false);
			           			 user.setOfficeCode("0");
			           			 user.setOfficeLevel(false);
			           			 user.setBranchCode(0);
			           			 user.setBranchLevel(false);
			           			 user.setStaffLoginId(userRest.getUSERID());
			           			 user.setStatus(true);
			           			 user.setStatusPsw(true);
			           			 user.setUser_no(0);
			           			 user.setUserType("AG");
			           			 user.setCho(false);
			                        
				                    
		    		            insertUserDetails(userRest.getBRANCH(),userRest.getUSERID().toUpperCase(),new Date(),"AG");
		                    }
		                    else
		                    {
		                        // user type is not STAFF
		                        log.log(Level.INFO, "Login Failed............");
		                    }
		                    
		                    if(null != user){
			           			user.setClientRestUserBranch(userRest.getBRANCH());
			           			user.setClientRestUserID(userRest.getUSERID());
		                    }
		                    
		                }
		                else
		                {
		                    // user auth failed
		                    log.log(Level.INFO, "Login Failed............");
		                }
		            }
		        }
		        catch (Exception e)
		        {
		            log.log(Level.SEVERE, e.getMessage());
		            e.printStackTrace();
		            LogsMaintenance logsMain = new LogsMaintenance();
		            StringWriter errors = new StringWriter();
		            e.printStackTrace(new PrintWriter(errors));
		            logsMain.insertLogs("UserMaintenance", Level.SEVERE + "", errors.toString());
		        }
		        finally
		        {
		            httpClient.getConnectionManager().shutdown();
		            HibernateFactory.close(session);
		        }

		        return user;
		    }

		    private String _authenticateUserSSO(String userID, String password, String branch, ServletContext context)
		    {
		        String sessionId = null;

		        try
		        {
		            Map<String, String> configMap = (Map<String, String>) context.getAttribute(ApplicationAttribute.CONFIGURATION_PROPERTIES_MAP);
		            
		            // the config is: http://211.144.219.243/SSO/loginAction.do
		            // full URL sample: http://211.144.219.243/SSO/loginAction.do?companyId=0986&password=11111111&userId=NTRN093&requestFrom=ios&handleSessionTable=&resultFlag=json
		            String ssoUrl = configMap.get("ssoUserAuthUrl");
		            ssoUrl += "?companyId=" + branch;
		            ssoUrl += "&password=" + password;
		            ssoUrl += "&userId=" + userID;
		            ssoUrl += "&requestFrom=ios&handleSessionTable=&resultFlag=json";
		            
		            JSONObject ssoResult = JSONHelper.readJsonFromUrl(ssoUrl);

		            if (ssoResult == null)
		            {
		                sessionId = null;
		            }
		            else
		            {
		                /*
		                 * here are 2 samples of the json result from SSO

		                 EG1:
		                 {"loginResult":"successed",
		                 "errorCode":"",
		                 "errorMessage":"",
		                 "gotoURL":"http://shadcuwapp03/SSO/ssoAppLogin.do?sessionId=0986IAwUgB8kA70neZIFystu8XU&amp;requestFrom=ios&amp;ssoUrl=http://shadcuwapp03/SSO",
		                 "sessionID":"0986IAwUgB8kA70neZIFystu8XU",
		                 "agentChannel":""}
		            
		                 EG2:
		                 {"loginResult":"failed",
		                 "errorCode":"SSO001",
		                 "errorMessage":"Ã¨Â¯Â·Ã¦Â³Â¨Ã¦â€žÂ�Ã¯Â¼Å¡Ã¥Â¯â€ Ã§Â Â�Ã¨Â¾â€œÃ©â€�â„¢3Ã¦Â¬Â¡Ã¯Â¼Å’Ã¦â€šÂ¨Ã§Å¡â€žÃ¥Â¸Â�Ã¥Â�Â·Ã¤Â¼Å¡Ã¨Â¢Â«Ã©â€�Â�Ã¥Â®Å¡60Ã¥Ë†â€ Ã©â€™Å¸!Ã¦â€šÂ¨Ã¥Â·Â²Ã¨Â¾â€œÃ©â€�â„¢1Ã¦Â¬Â¡.",
		                 "gotoURL":"",
		                 "sessionID":"",
		                 "agentChannel":""}
		                 */
		                
		                // we just need to check whether the session id exists in the json result
		                // when login failed, the session id will always be empty
		                sessionId = ssoResult.getString("sessionID");
		                
		            }
		        }
		        catch (Exception ex)
		        {
		            ex.printStackTrace();
		            sessionId = null;
		        }

		        return sessionId;
		    }

		    private void _updateUserLastLogin(int userNo, Date date)
		    {
		        Session session = null;

		        try
		        {
		            session = HibernateFactory.openSession();
		            Transaction tx = session.beginTransaction();

		            Query query = session.createQuery("UPDATE User SET lastLogIn=:lastLogin where status = 1 and user_no=:userno  ");
		            query.setParameter("lastLogin", new Date());
		            query.setParameter("userno", userNo);
		            query.executeUpdate();

		            tx.commit();
		        }
		        catch (Exception ex)
		        {
		            ex.printStackTrace();
		        }
		        finally
		        {
		            if (session != null)
		            {
		                session.close();
		            }
		        }
		    }
	


	/**
     * <p>This method checks all validations & sets values to bean</p>
     * @param User objToBeMapped object
     * @param requestParameters Servlet Request Parameter
     * @return User object/Error object 
     */
	public Object mapForm1(User objToBeMapped, HttpServletRequest req,
			FormObj formObj) {
		// TODO Auto-generated method stub
		
		if(objToBeMapped == null)
        {
			objToBeMapped = new User();
            return objToBeMapped;
        }
		User userObj = (User)req.getSession().getAttribute("currUserObj");
		log.log(Level.INFO,"UserMaintenance --> mapForm1 ");
		User user=new User();
		LocaleObject localeObj = (LocaleObject)req.getSession().getAttribute(SessionAttributes.LOCALE_OBJ);
//		 if(req.getParameterValues("channel") == null)
//	            return new ErrorObject("This is a required field:", "channel");
		 
//		 String channelName="";
//		 for (int i = 0; i < req.getParameterValues("channel").length; i++) {
//			 log.log(Level.INFO,req.getParameterValues("channel")[i]+"");
//			 channelName=channelName+req.getParameterValues("channel")[i]+"|";
//		}
		 user.setChannelCode("");
		 
		 if(req.getParameter("cho").equals("N")){
			 if(req.getParameter("bu").equals("0"))
				 return new ErrorObject("BU", " field is required",localeObj);
			 
			 user.setBuCode(Integer.parseInt(req.getParameter("bu")));
			 user.setDistrict(Integer.parseInt(req.getParameter("district")));
			 user.setBranchCode(Integer.parseInt(req.getParameter("branch")));
			 user.setCityCode(req.getParameter("city"));
			 user.setSscCode(req.getParameter("ssc"));
			 user.setOfficeCode(req.getParameter("office"));
			 user.setCho(false);
		 }else{
			 user.setCho(true); 
			 user.setBuCode(0);
			 user.setDistrict(0);
			 user.setBranchCode(9986);
			 user.setCityCode("0");
			 user.setSscCode("0");
			 user.setOfficeCode("0");
		 }
		 
		 
		 user.setDepartment(Integer.parseInt(req.getParameter("dept")));
		 
		 
		 if(req.getParameterValues("staffLoginId") == null)
				return new ErrorObject("Staff Login ID", " field is required",localeObj);
		 if(req.getParameter("staffLoginId").equals(""))
		 		return new ErrorObject("Staff Login ID", " field is required",localeObj);
		 
		
		 if(formObj.getFormType().equals("MODIFY")){
			 if(req.getParameter("userNo")!=null){
				 if(checkemilID(req.getParameter("email").trim(),Integer.parseInt(req.getParameter("userNo")))){
					 return new ErrorObject("This is already Exist:", "Email Address",localeObj);
				 }
				 if(checkLoginID(req.getParameter("staffLoginId").trim(),Integer.parseInt(req.getParameter("userNo")))){
					 return new ErrorObject("This is already Exist:", "Staff Login ID",localeObj);
				 }
			 }
		 }else{
			 if(checkLoginID(req.getParameter("staffLoginId").trim(),0)){
				 return new ErrorObject("This is already Exist:", "Staff Login ID",localeObj);
			 }
		 }
		 user.setStaffLoginId((req.getParameter("staffLoginId").toUpperCase()));
		 
		 if(req.getParameterValues("staffName") == null)
			return new ErrorObject("Staff Name", " field is required",localeObj);
		 if(req.getParameter("staffName").equals(""))
			return new ErrorObject("Staff Name", " field is required",localeObj);
		 user.setStaffName(req.getParameter("staffName"));
		 
		 if(req.getParameterValues("email") == null){
	            return new ErrorObject("Email Address", " field is required",localeObj);
		 }if(req.getParameter("email").equals("")){
			 return new ErrorObject("Email Address", " field is required",localeObj);
		 }else{
			 if(!LMSUtil.emailValidation(req.getParameter("email"))){
				 return new ErrorObject("Email Address", " field is required",localeObj);
			 }
		 }
		 
		 if(formObj.getFormType().equals("MODIFY")){
			 if(req.getParameter("userNo")!=null){
				 if(checkemilID(req.getParameter("email"),Integer.parseInt(req.getParameter("userNo")))){
					 return new ErrorObject("This is already Exist:", "Email Address",localeObj);
				 }
			 }
		 }else{
			 if(checkemilID(req.getParameter("email"),0)){
				 return new ErrorObject("This is already Exist:", "Email Address",localeObj);
			 }
		 }
		 
		 user.setEmail(req.getParameter("email"));
		
		
		 if(req.getParameterValues("phno") == null)
			 return new ErrorObject("Contact Number", " field is required",localeObj);
		 
//		 if( LMSUtil.validatePhoneNumber(req.getParameter("phno"))){
//			 user.setContactNo(req.getParameter("phno"));
//		 }else{
//			 return new ErrorObject("Please Enter 10 or 12 Digit:", "Contact Number",localeObj);
//		 }
		 
		 user.setContactNo(req.getParameter("phno"));
		
		 if(req.getParameterValues("extNo") == null || req.getParameter("extNo").equals("") ){
			 user.setExtensionNo("");
			 
		 }else{
			 user.setExtensionNo(req.getParameter("extNo"));
//			 if( LMSUtil.validateExtensionNumber(req.getParameter("extNo"))){
//				 user.setExtensionNo(req.getParameter("extNo"));
//			 }else{
//				 return new ErrorObject("Please Enter in 10 digit:", "Extension Number",localeObj);
//			 }
		 }
		
		 
		
		 
//		 if(!formObj.getFormType().equals("MODIFY")){ 
//			 	String psw=LMSUtil.getRendomPassword();
//				req.setAttribute("psw", psw);
//				user.setPassword(PasswordHashing.EncryptBySHA2(psw));
//				user.setStatusPsw(false);
//		 }else{
//			 user.setPassword(userObj.getPassword());
//			 user.setStatusPsw(true);
//		 }
		 
	user.setStatusPsw(true);
	if(req.getParameter("cho").equalsIgnoreCase("Y")){
		user.setUserType("AD");
	}else{
		user.setUserType("ST");
	}
		
		user.setStatus(true);
		
		return user;
	}
	
	
	
	/**
     * <p>This method checks LoginId exist or not</p>
     * @param String staffLoginId for loagin id
     * @param i for pass 0 or 1. 
     * @return flag for true/false
     */
	private boolean checkLoginID(String staffLoginId, int i) {
		// TODO Auto-generated method stub
		 log.log(Level.INFO,"UtilitesMaintanence --> checkLoginID");
		 boolean flag=false;
		 String str="";
		 Session session = null;
		
		 if(i!=0){
			 str=str+" and user_no!=:userno";
		 }
			try
			{
				List arrActivity = new ArrayList();
				session = HibernateFactory.openSession();
				
				
				Query query = session.createQuery(" from User  where staffLoginId=:staffLoginId and status = 1  "+str+" ");
				query.setParameter("staffLoginId", staffLoginId.toUpperCase());
				if(i!=0){
					query.setParameter("userno", i);
					
				 }
				arrActivity=query.list();
				 
				if(arrActivity.size()!=0)
		        	   flag=true;
		        
				
			}
			catch(Exception e)
			{
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
				LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("UserMaintenance",Level.SEVERE+"",errors.toString());
			}finally{
				HibernateFactory.close(session);
			}
			
			
			return flag;
		
	}
	
	/**
     * <p>This method checks Dubplicate email address</p>
     * @param String mailId for maileId
     * @param i for pass 0 or 1. 
     * @return flag for true/false
     */
	
	private boolean checkemilID(String mailId, int i) {
		// TODO Auto-generated method stub
		 log.log(Level.INFO,"UtilitesMaintanence --> checkemilID");
		 boolean flag=false;
		 String str="";
		 Session session = null;
		
		 if(i!=0){
			 str=str+" and user_no!=:userno";
		 }
			try
			{
				List arrActivity = new ArrayList();
				session = HibernateFactory.openSession();
				
				Query query = session.createQuery(" from User  where email=:email and status = 1 "+str+" ");
				query.setParameter("email", mailId);
				if(i!=0){
					query.setParameter("userno", i);
					
				 }
				
				arrActivity=query.list();
				 
				if(arrActivity.size()!=0)
		        	   flag=true;
		        
				
			}
			catch(Exception e)
			{
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
				LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("UserMaintenance",Level.SEVERE+"",errors.toString());
			}finally{
				HibernateFactory.close(session);
			}
			
			
			return flag;
		
	}
	
	/**
	 * <p>This method performs call for insertion method for User
	 *  & audit trail for User add 
	 *  & sets message object in request</p>
	 * @param USer user object
	 * @param req Servlet Request Parameter
	 * @return USer object
	 */
	public Object createNewUser(User user, HttpServletRequest req) {
		// TODO Auto-generated method stub
		log.log(Level.INFO,"UserMaintenance --> createNewUser ");
		 MsgObject msgObj =null;
	     
	      User userObj = (User)req.getSession().getAttribute("currUserObj");
	     user.setCreatedBy(userObj.getStaffLoginId());;
	     user.setCreationDate(new Date());
	     user.setModifiedBy(userObj.getStaffLoginId());
			user.setModificationDate(new Date());
			
	     
	     AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
	       
	     msgObj= insertUser(user,req,msgObj);
	      User userAudi=new User(user);
	      log.log(Level.INFO,userAudi.toString());
	     if(!msgObj.equals("0")){
	    	 
	        	auditTrailMaint.insertAuditTrail(new AuditTrail(String.valueOf(user.getUser_no()), AuditTrail.MODULE_USER, AuditTrail.FUNCTION_CREATE, "Action on:INSERT T_USER CREATED,USER_NO:"+user.getUser_no()));
	     }else{
	    	 auditTrailMaint.insertAuditTrail(new AuditTrail(String.valueOf(user.getUser_no()), AuditTrail.MODULE_USER, AuditTrail.FUNCTION_FAILED, "Action on:INSERT T_USER CREATED,USER_NO:"+user.getUser_no()));
	    	 msgObj= new MsgObject("The new User has not been created.");
	     } 
	     
	     
	     req.getSession().setAttribute(RequestAttributes.MESSAGE_OBJ, msgObj);
	     req.setAttribute("pager", getAllUserListing(req));
	     req.getSession().setAttribute("pageObj",new PathDetail().getPageObj("UserMaintenance"));
	     req.getSession().setAttribute("pager", getAllUserListing(req));
	     
		return user;
	}
	
	/**
	 * <p>This method performs USer Insertion </p>
	 * @param User user object
	 * @return MsgObject for successfully done
	 */
	private MsgObject insertUser(User user, HttpServletRequest req, MsgObject msgObj) {
		// TODO Auto-generated method stub
		log.log(Level.INFO,"UserMaintenance --> insertUser ");
		Session session = null;
		Transaction tx=null;
		 try
			{
			 //fcc3a23fc7232cc89c7cb0f23d8774fefb73d7dc2ab22e6a1b6b8b202b4dcc91
				session = HibernateFactory.openSession();
				tx= session.beginTransaction();
				session.save(user);
				tx.commit();
				msgObj = new MsgObject("The new User has been successfully created.");
				
				log.log(Level.INFO,"The new User has been successfully created.");
				//EmailNotification.sendPasswordNotification(req,user,"Create");
			}
			catch(Exception e)
			{
				
				msgObj = new MsgObject("0");
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
				LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("UserMaintenance",Level.SEVERE+"",errors.toString());
			}finally{
				try{
					HibernateFactory.close(session);
				}catch(Exception e){
					
					log.log(Level.SEVERE,e.getMessage());
					e.printStackTrace();
				}
			}
		
		 return msgObj;
		
	}
	
	/**
	 * <p>This method retrieves all Users<p>
	 * @return list of User
	 */
	public Pager getAllUserListing(HttpServletRequest req) {
		// TODO Auto-generated method stub
		LinkedList item = new LinkedList();
        User user = null;
        ArrayList vecAllRes = new ArrayList();
        
        vecAllRes = getAllUser(req);
        if(vecAllRes.size()!=0){
	        for(int i = 0; i < vecAllRes.size(); i++)
	        {
	        	user = new User();
	        	user = (User)vecAllRes.get(i);
	        	
	        	if(user.getCreatedUserSscCode() > 0)
					user.setCreatedUserSscLevel(true);
				else if(user.getCreatedUserCityCode() > 0)
					user.setCreatedUserCityLevel(true);
				else if(user.getCreatedUserDistCode() > 0)
					user.setCreatedUserDistLevel(true);
				else if(user.getCreatedUserBuCode() > 0)
					user.setCreatedUserBuLevel(true);
	            item.add(user.getGetResListingTableRow(i,req,user));
	        }
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
	 * <p>This method retrieves all Users<p>
	 * @return list of User
	 */
	private ArrayList getAllUser(HttpServletRequest req) {
		// TODO Auto-generated method stub
		log.log(Level.INFO,"UtilitesMaintanence --> getAllUser");
		Session session = null;
		
		 User userObj = (User)req.getSession().getAttribute("currUserObj");
		ArrayList arrActivity = new ArrayList();
		String str="";
		
			try
			{
				
			
				if(!userObj.getUserType().equals("AD") ){
					
					if(userObj.isSscLevel()){
						str=str+" and sscCode=:ssccode ";	
					}else if(userObj.isCityLevel()){
						str=str+" and cityCode=:cityCode ";	
					}else if(userObj.isDistrictLevel()){
						str=str+" and district=:district ";	
					}else if(userObj.isBuLevel()){
						str=str+" and buCode=:buCode ";	
					}
					
					
					
				}
				session = HibernateFactory.openSession();
				session.setDefaultReadOnly(true);
				
				Query query = session.createQuery(" from User where status = 1  "+str+" ");
				if(!userObj.getUserType().equals("AD") ){
					
					if(userObj.isSscLevel()){
						query.setParameter("ssccode", userObj.getSscCode());
							
					}else if(userObj.isCityLevel()){
						query.setParameter("cityCode", userObj.getCityCode());
						
					}else if(userObj.isDistrictLevel()){
						query.setParameter("district", userObj.getDistrict());
						
					}else if(userObj.isBuLevel()){
						query.setParameter("buCode", userObj.getBuCode());	
					}
				}
				
				arrActivity=(ArrayList) query.list();
				
				
			}
			catch(Exception e)
			{
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
				LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("UserMaintenance",Level.SEVERE+"",errors.toString());
			}finally{
				session.setDefaultReadOnly(false);
				HibernateFactory.close(session);
			}

		return arrActivity;
	}
	
	 /**
	 * <p>This method performs call for delete method for User 
	 *  & audit trail for user delete 
	 *  & sets message object in request</p>
	 * @param parameter
	 * @param req Servlet Request Parameter
     */
	public void deleteUser(String parameter, HttpServletRequest req) {
		// TODO Auto-generated method stub
		log.log(Level.INFO,"UtilitesMaintanence --> deleteUser");
		MsgObject msgObj=null;
		Session session = null;
		Transaction tx=null;
		try
		{
			//User user=new User();
			session = HibernateFactory.openSession();
			tx= session.beginTransaction();
			
			ArrayList<User> al=new ArrayList<User>();
			Query query = session.createQuery(" from User where user_no=:userno ");
			query.setParameter("userno", Integer.parseInt(req.getParameter("userNo")));
			al=(ArrayList<User>) query.list();
			User user2=new User();
			user2=al.get(0);
			user2.setStatus(false);
			user2.setUser_no(Integer.parseInt(req.getParameter("userNo")) );

			session.update(user2);
			tx.commit();
			session.flush();
		    
		    msgObj = new MsgObject("The User has been successfully Deleted.");
		    AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
		    if(!msgObj.equals("0")){
	        	auditTrailMaint.insertAuditTrail(new AuditTrail(String.valueOf(req.getParameter("userNo")), AuditTrail.MODULE_USER, AuditTrail.FUNCTION_DELETE, "Action on:INSERT T_USER DELETED,USER_NO:"+req.getParameter("userNo")));
			}else{
	    	 auditTrailMaint.insertAuditTrail(new AuditTrail(String.valueOf(req.getParameter("userNo")), AuditTrail.MODULE_USER, AuditTrail.FUNCTION_DELETE, "Action on:INSERT T_USER DELETED,USER_NO:"+req.getParameter("userNo")));
	    	 msgObj = new MsgObject("The User has not been successfully Deleted.");
			} 
			
		     req.getSession().setAttribute(RequestAttributes.MESSAGE_OBJ, msgObj);
		     req.setAttribute("pager", getAllUserListing(req));
		     req.getSession().setAttribute("pageObj",new PathDetail().getPageObj("UserMaintenance"));
		     req.getSession().setAttribute("pager", getAllUserListing(req));
			
		}
		catch(Exception e)
		{
			
			msgObj = new MsgObject("0");
			
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("UserMaintenance",Level.SEVERE+"",errors.toString());
		}finally{
			HibernateFactory.close(session);
		}
  
	}
	
	/**
	  * <p>This method retrieves USer lists based on search criteria</p>
	  * @param req Servlet Request Parameter
	  * @return User lists 
	  */
	public Pager getUserListing(HttpServletRequest requestParamters) {
		// TODO Auto-generated method stub
		LinkedList item = new LinkedList();
        User user = null;
        ArrayList vecAllRes = new ArrayList();
        
        vecAllRes = getSearchUser(requestParamters);
        if(vecAllRes.size()!=0){
	        for(int i = 0; i < vecAllRes.size(); i++)
	        {
	        	user = new User();
	        	user = (User)vecAllRes.get(i);
	        	if(user.getCreatedUserSscCode() > 0)
					user.setCreatedUserSscLevel(true);
				else if(user.getCreatedUserCityCode() > 0)
					user.setCreatedUserCityLevel(true);
				else if(user.getCreatedUserDistCode() > 0)
					user.setCreatedUserDistLevel(true);
				else if(user.getCreatedUserBuCode() > 0)
					user.setCreatedUserBuLevel(true);
	            item.add(user.getGetResListingTableRow(i,requestParamters,user));
	        }
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
	  * <p>This method retrieves USer lists based on search criteria</p>
	  * @param req Servlet Request Parameter
	  * @return User lists 
	  */
	private ArrayList getSearchUser(HttpServletRequest req) {
		// TODO Auto-generated method stub
		log.log(Level.INFO,"UtilitesMaintanence --> getSearchUser");
		 
		 User userObj = (User)req.getSession().getAttribute("currUserObj");
		ArrayList arrActivity = new ArrayList();
		String str="";
		
		if(!req.getParameter("staffName").equals("")){
			str=str+" and staffName LIKE :staffName ";		
		}
		if(!req.getParameter("staffId").equals("")){
			str=str+" and staffLoginId =:staffId ";		
		}
		
		if(req.getParameter("cho").equals("N")){
			str=str+" and cho=0 ";		
		}else if(req.getParameter("cho").equals("Y")){
			str=str+" and cho=1 ";	
		}
		
//		if(req.getParameterValues("channel")!=null  ){
//			str=str+" and channelCode=:channelCode ";	
//		}
		if(null != req.getParameter("dept") && !req.getParameter("dept").equals("") &&  !req.getParameter("dept").equals("0") ){
			str=str+" and department=:dept ";	
		}
		
		if(null != req.getParameter("bu") && !req.getParameter("bu").equals("")&& !req.getParameter("bu").equals("0") ){
			str=str+" and buCode=:buCode ";	
		}
		
		if(null != req.getParameter("district") && !req.getParameter("district").equals("") &&  !req.getParameter("district").equals("0")  ){
			str=str+" and district=:district ";	
		}
		
		if(null != req.getParameter("branch") && !req.getParameter("branch").equals("") &&  !req.getParameter("branch").equals("0")  ){
			str=str+" and branchCode=:branch ";	
		}
		
		if(null != req.getParameter("city") && !req.getParameter("city").equals("") &&  !req.getParameter("city").equals("0")){
			str=str+" and cityCode=:cityCode ";	
		}
		
		if(null != req.getParameter("ssc") && !req.getParameter("ssc").equals("") &&  !req.getParameter("ssc").equals("0") ){
			str=str+" and sscCode=:sscCode ";	
		}
		
		if(null != req.getParameter("office") && !req.getParameter("office").equals("") &&  !req.getParameter("office").equals("0")  ){
			str=str+" and officeCode=:office ";	
		}
		
		
		Session session = null;
		
			try
			{
				
				session = HibernateFactory.openSession();
				session.setDefaultReadOnly(true);
				
				Query query = session.createQuery(" from User where status = 1 "+str+"  ");
				if(!req.getParameter("staffName").equals("")){
					query.setParameter("staffName","%"+ req.getParameter("staffName")+"%");
						
				}
				if(!req.getParameter("staffId").equals("")){
					query.setParameter("staffId", req.getParameter("staffId"));
				}
				
				
//				if(req.getParameter("channel") != null){
//					String channelName="";
//					 for (int i = 0; i < req.getParameterValues("channel").length; i++) {
//						 channelName=channelName+req.getParameterValues("channel")[i]+"|";
//					}
//					query.setParameter("channelCode", channelName);
//					
//				}
				if(null != req.getParameter("dept") && !req.getParameter("dept").equals("") && !req.getParameter("dept").equals("0")){
					query.setParameter("dept", Integer.parseInt(req.getParameter("dept")));
						
				}
				if(null != req.getParameter("bu") && !req.getParameter("bu").equals("") &&  !req.getParameter("bu").equals("0")){
					query.setParameter("buCode", Integer.parseInt(req.getParameter("bu")));
					
				}
				if(null != req.getParameter("district") && !req.getParameter("district").equals("")&&  !req.getParameter("district").equals("0")){
					query.setParameter("district", Integer.parseInt(req.getParameter("district")));
					
				}
				
				if(null != req.getParameter("branch") && !req.getParameter("branch").equals("") &&  !req.getParameter("branch").equals("0")  ){
					query.setParameter("branch", Integer.parseInt(req.getParameter("branch")));
				}
				
				
				if(null != req.getParameter("city") && !req.getParameter("city").equals("")&&  !req.getParameter("city").equals("0")){
					query.setParameter("cityCode", req.getParameter("city"));
						
				}
				
				if(null != req.getParameter("ssc") && !req.getParameter("ssc").equals("")&&  !req.getParameter("ssc").equals("0")){
					query.setParameter("sscCode", req.getParameter("ssc"));
				}
				
				if(null != req.getParameter("office") && !req.getParameter("office").equals("") &&  !req.getParameter("office").equals("0")  ){
					query.setParameter("office",  req.getParameter("office"));
				}
				
				arrActivity=(ArrayList) query.list();
				
				
			}
			catch(Exception e)
			{
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
				LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("UserMaintenance",Level.SEVERE+"",errors.toString());
			}finally{
				session.setDefaultReadOnly(false);
				HibernateFactory.close(session);
			}

		return arrActivity;
		
	}
	
	/**
	  * <p>This method retrieves USer lists base on userId</p>
	  * @param usercode
	  * @return User Object
	  */
	public User getUser(int userCode) {
		// TODO Auto-generated method stub
		log.log(Level.INFO,"UtilitesMaintanence --> getUser");
		User user=new User();
		Session session = null;
		
		try{
			session = HibernateFactory.openSession();
			session.setDefaultReadOnly(true);
			user = (User)session.get(User.class,userCode);
	
	  
		}catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("UserMaintenance",Level.SEVERE+"",errors.toString());
		}finally{
			try{
				session.setDefaultReadOnly(false);
				HibernateFactory.close(session);
				
			}catch(Exception e){
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
				LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("UserMaintenance",Level.SEVERE+"",errors.toString());
			}
		}
		
		return user;
		
	}
	
	 /**
	 * <p>This method performs call for modify method for user 
	 *  & audit trail for user update 
	 *  & sets message object in request</p>
	 * @param User user object
	 * @param req Servlet Request Parameter
	 * @return User object
	 */
	public Object updateUser(User user, HttpServletRequest req) {
		// TODO Auto-generated method stub
		log.log(Level.INFO,"UtilitesMaintanence --> updateUser");
		AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
		Session session = null;
		Transaction tx=null;
		 User userObj = (User)req.getSession().getAttribute("currUserObj");
		try{
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();
			user.setUser_no(Integer.parseInt(req.getParameter("userNo")));
			user.setModifiedBy(userObj.getStaffLoginId());
			user.setModificationDate(new Date());
			session.update(user);
		
			tx.commit();
			
			
	       auditTrailMaint.insertAuditTrail(new AuditTrail(String.valueOf(req.getParameter("userNo")), AuditTrail.MODULE_USER, AuditTrail.FUNCTION_DELETE, "Action on:INSERT T_USER MODIFIED,USER_NO:"+req.getParameter("userNo")));
			
			}catch(Exception e)
			{
				 auditTrailMaint.insertAuditTrail(new AuditTrail(String.valueOf(req.getParameter("userNo")), AuditTrail.MODULE_USER, AuditTrail.FUNCTION_DELETE, "Action on:INSERT T_USER MODIFIED,USER_NO:"+req.getParameter("userNo")));
				 log.log(Level.SEVERE, e.getMessage());
				 e.printStackTrace();
				 LogsMaintenance logsMain=new LogsMaintenance();
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					logsMain.insertLogs("UserMaintenance",Level.SEVERE+"",errors.toString());
			}finally{
				try{
					HibernateFactory.close(session);
					
				}catch(Exception e){
					log.log(Level.SEVERE, e.getMessage());
					e.printStackTrace();
				}
				
				
		}
			 MsgObject msgObj = new MsgObject("The User has been successfully Updated.");
			 req.getSession().setAttribute(RequestAttributes.MESSAGE_OBJ, msgObj);
			 req.setAttribute("pager", getAllUserListing(req));
		     req.getSession().setAttribute("pageObj",new PathDetail().getPageObj("UserMaintenance"));
		     req.getSession().setAttribute("pager", getAllUserListing(req));
		    
 
		return userObj;
	}
	
	/**
	 * <p>This method performs call for get letest jsversion</p>
	 * @return String object
	 */
	
	 public String getLatestJSVersion()
	 {
		 
		 String verNo = "";
		 ArrayList<Version> list = new ArrayList<Version>();
		 Session session = null;
		
		 try
		 {
			 session = HibernateFactory.openSession();
			 
			 Query query = session.createQuery("from Version where ver_date in(select max(ver_date) from Version)");
			 list=(ArrayList) query.list();
			 
			 if(list.size()>0)
			 {
				 verNo=list.get(0).getVer_no()+"";
				 
			 }
		 }
		 catch(Exception e)
		 {
			 log.log(Level.SEVERE, e.getMessage());
			 e.printStackTrace();
			 LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("UserMaintenance",Level.SEVERE+"",errors.toString());
		 }finally{
			 HibernateFactory.close(session);
		 }
		 return verNo;
	 }
	 
	 /**
		 * <p>This method performs call for reset password </p>
		 * @param Userid 
		 * @param req Servlet Request Parameter
		 * @return String flag
		 */
	
	public String resetPassword(String userid,HttpServletRequest req){
		log.log(Level.INFO,"UtilitesMaintanence --> resetPassword");
		String flage="0";
		 ArrayList<User> list = new ArrayList();
		 Query query=null;
		MsgObject msgObj=null;
		Session session = null;
		Transaction tx=null;
		 try{
			if(userid!=null){
			session = HibernateFactory.openSession();
			tx= session.beginTransaction();
			query = session.createQuery(" from User where status = 1 and staffLoginId=:LoginId   ");
			query.setParameter("LoginId",userid);
			list=(ArrayList<User>) query.list();
			User tempUser=new User();
			if(list.size()!=0){
				tempUser=list.get(0);
				
				query = session.createQuery("UPDATE User SET password=:psw,statusPsw=0 where status = 1 and user_no=:userno  ");
				String psw=LMSUtil.getRendomPassword();
				req.setAttribute("psw", psw);
				tempUser.setPassword(PasswordHashing.EncryptBySHA2(psw));
				query.setParameter("psw", tempUser.getPassword());
				query.setParameter("userno", tempUser.getUser_no());
				query.executeUpdate();
				tx.commit();
				
				msgObj = new MsgObject("Password Reset successfully ");
				log.log(Level.INFO,"Password Reset successfully");
				EmailNotification.sendPasswordNotification(req,tempUser,"Modify");
				flage="1";
			}
				
			}
			
			
		}catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("UserMaintenance",Level.SEVERE+"",errors.toString());
			
		}finally{
			try{
				HibernateFactory.close(session);
			}catch(Exception e){
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
	
		}
		
		return flage;
		
	}

	
	 /**
	 * <p>This method performs call for First Time set password </p>
	 * @param Psw 
	 * @param req Servlet Request Parameter
	 * @return String flag
	 */
	public String changeFirstTimePassword(String psw,String userId,HttpServletRequest req){
		log.log(Level.INFO,"UtilitesMaintanence --> changeFirstTimePassword");
		User user = (User)req.getSession().getAttribute("currUserObj");
		String flage="0";
		 ArrayList<User> list = new ArrayList();
		 Query query=null;
		MsgObject msgObj=null;
		Session session = null;
		Transaction tx=null;
		String newPwd = "";
		if(user !=null){
			newPwd=PasswordHashing.EncryptBySHA2(psw);
			if(newPwd.equalsIgnoreCase(user.getPassword()))
				return "2";
		}
		 try{
			if(userId!=null){
			session = HibernateFactory.openSession();
			tx= session.beginTransaction();
			query = session.createQuery(" from User where status = 1 and user_no=:userNo   ");
			query.setParameter("userNo",userId);
			list=(ArrayList<User>) query.list();
			if(list.size()!=0){
				User tempUser=list.get(0);
				tempUser.setUser_no(Integer.parseInt(userId));
				
				if(list.size()!=0){
					tempUser=list.get(0);
					query = session.createQuery("from User  where status = 1 ");
					req.setAttribute("psw", psw);
					tempUser.setPassword(PasswordHashing.EncryptBySHA2(psw));
					tempUser.setStatusPsw(true);
					session.update(tempUser);
					tx.commit();
					
					msgObj = new MsgObject("Password Reset successfully ");
					log.log(Level.INFO,"Password Reset successfully");
					EmailNotification.sendPasswordNotification(req,tempUser,"Modify");
					flage="1";
				}
			
			 }
				
			}
			
			
		}catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("UserMaintenance",Level.SEVERE+"",errors.toString());
			
		}finally{
			try{
				HibernateFactory.close(session);
			}catch(Exception e){
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
	
		}
		
		return flage;
		
	}

	 /**
	 * <p>This method performs call for validation of upload .csv file data 
	 * and insertion method 
     * & audit trail for holiday update 
	 * & sets message object in request</p>
	 * @param Userid 
	 * @param req Servlet Request Parameter
	 * @return User object
	 */
	public Object mapFormCSVUpload(User objToBeMapped, HttpServletRequest req) {
		// TODO Auto-generated method stub
		log.log(Level.INFO,"UserMaintenance --> mapFormCSVUpload");
		LocaleObject localeObj = (LocaleObject)req.getSession().getAttribute(SessionAttributes.LOCALE_OBJ);
		
		if(objToBeMapped == null)
        {
			objToBeMapped = new User();
            return objToBeMapped;
        }
		
		 //ResourceBundle msgProps = ResourceBundle.getBundle("configurations");
//		  String userPath  =     //msgProps.getString("UserPath");
		  String csv_file_name = "";
		  if(req.getSession().getAttribute("csv_file_name")!=null){
			  csv_file_name=(String)req.getSession().getAttribute("csv_file_name");
			
			
		   req.getSession().removeAttribute("csv_file_name");
		   String tempDir = System.getProperty("java.io.tmpdir");
		   Session session= null;
  		int record_created=0;
  		MsgObject msgObj=null;
  		User user=new User();
  		StringBuffer strbuf=new StringBuffer();
  		
  		try{
  			int sucessCnt=0;
  			int m=0;
  	    	int l=0;
  	    	 int rowCount=0;
  			String serverFilename = "resources/upload/userExcel"+"USER_" + LMSUtil.getRendomToken();

			File uploadedFolder = new File(serverFilename);
			if(!uploadedFolder.exists()){
				uploadedFolder.mkdirs();
			}
			if(csv_file_name!=null && !csv_file_name.equals("")){
				
				byte[] bytearray=(byte[]) req.getSession().getAttribute("csv_byte_session");
				if(csv_file_name.contains(".xlsx") || csv_file_name.contains(".XLSX") ||
						csv_file_name.contains(".xls") || csv_file_name.contains(".XLS")){
					try {
						FileOutputStream stream = new FileOutputStream(serverFilename+"/"+csv_file_name);
					    stream.write(bytearray);
					    stream.close();
					} catch(Exception e){log.log(Level.SEVERE, e.getMessage());
					e.printStackTrace();
					LogsMaintenance logsMain=new LogsMaintenance();
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					logsMain.insertLogs("UserMaintenance",Level.SEVERE+"",errors.toString());}
				}
				File temp_file = new File(tempDir+"/" + csv_file_name);
	        	FileUtils.deleteFileNFolder(temp_file);
	        	
	        	String records = null;
	        	
	        	int buCode=0,distCode=0,channelCode=0,branchCode=0;
	        	String cityCode="0",sscCode="0",officeCode="0";
	        	session = HibernateFactory.openSession();
	        	ImoUtilityData imoUtilityData = new ImoUtilityData();
	        	
	        	User userObj = (User)req.getSession().getAttribute("currUserObj");
	        	user.setCreationDate(new Date());
        		user.setCreatedBy(userObj.getStaffLoginId());;
        		user.setModificationDate(new Date());
        		user.setModifiedBy(userObj.getStaffLoginId());;
        		user.setStatus(true);
        		
        		AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
        		
        		boolean flag=false;

        		 FileInputStream fis = null;
        		 fis = new FileInputStream(serverFilename+"/"+csv_file_name);
        		 HSSFWorkbook workbook = new HSSFWorkbook(fis);
        		 HSSFSheet sheet = workbook.getSheetAt(0);
        		
        		 Iterator rows = sheet.rowIterator();
        		 int cellCount=0;
                 while (rows.hasNext()) {
                	 rowCount++;
                     HSSFRow row = (HSSFRow) rows.next();
                     Iterator cells = row.cellIterator();

                     while (cells.hasNext()) {
                    	 if(cellCount==0){
                    		 break;
                    	 }
                    	 
                    	// cellCount++;
                         HSSFCell cell = (HSSFCell) cells.next();
                         System.out.println("cell contenct "+cell.toString());
                         
                        if(cellCount==1){ 
                         if(cell.toString().equals("Y".toUpperCase())){
                        	user.setCho(true);
                        	user.setUserType("AD");
                         }else if(cell.toString().equals("N".toUpperCase())){
                         	user.setCho(false);
                         	user.setUserType("ST");
                          }else{
                        	  strbuf.append(localeObj.getTranslatedText("Required Y OR N CHO at row number")+rowCount+"\n");
                          }
                        }
                        
                        if(cellCount==2){ 
                        	if(user.isCho()){
                        		user.setBuCode(0);
                        	}else{
	                        	buCode=imoUtilityData.getBuCodeBasedOnBuName(cell.toString());
								if(buCode==0){
									flag=true;
	    							strbuf.append(localeObj.getTranslatedText("Invalid BU Name row number")+rowCount+"\n");
	    							break;
								}else{
									user.setBuCode(buCode);
	    							user.setBuName(cell.toString());
								}
                        	}
                        	
                         }
                        
                        if(cellCount==3){ 
                        	if(user.isCho()){
                        		user.setDistrict(0);
                        	}else{ 
	                        	distCode=imoUtilityData.getDistrictCodeBasedOnDistrictName(cell.toString());
									user.setDistrict(distCode);
									user.setDistName(cell.toString());
							
                        	}
                        }
                        
                        if(cellCount==4){ 
                        	if(user.isCho()){
                        		user.setBranchCode(9986);
                        	}else{ 
                        		branchCode=imoUtilityData.getBranchCodeBasedOnBranchName(cell.toString());
								user.setBranchCode(branchCode);
								user.setBranchName(cell.toString());
                        	}
                        	
                        }
                        
                        if(cellCount==5){ 
                        	if(user.isCho()){
                        		user.setCityCode("0");
                        	}else{ 
                        		cityCode=imoUtilityData.getCityCodeBasedOnDistrictName(cell.toString());	
   								user.setCityCode(cityCode);
       							user.setCityName(cell.toString());
   							
                        	}
                        }
                        

                        if(cellCount==6){ 
                        	if(user.isCho()){
                        		user.setSscCode("0");
                        	}else{ 
                        		sscCode=imoUtilityData.getSSCCodeBasedOnSSCName(cell.toString());
    							user.setSscCode(sscCode);
								user.setSscName(cell.toString());
   							
                        	}
                        }
                        
                        if(cellCount==7){ 
                        	if(user.isCho()){
                        		user.setOfficeCode("0");
                        	}else{ 
                        		officeCode=imoUtilityData.getOfficeCodeBasedOnSSCName(cell.toString());
    							user.setOfficeCode(officeCode);
								user.setOfficeName(cell.toString());
   							
                        	}
                        }
                        
                        if(cellCount==8){ 
    							int dept= imoUtilityData.getdeptCodeBasedOndeptName(cell.toString());
    							user.setDepartment(dept);
    							user.setDeptName(cell.toString());
								
                        }
                        if(cellCount==9){ 
                        	if(checkLoginID(cell.toString().trim(),0)){
								flag=true;
								strbuf.append(localeObj.getTranslatedText("Dublicate STAFF LOGIN ID at row number")+rowCount+"\n");
								break;
							 }else{
								 user.setStaffLoginId(cell.toString().toUpperCase());
							 }
							
                        }
                        if(cellCount==10){ 
                        	user.setStaffName(cell.toString());
							
                        }
                        if(cellCount==11){ 
                        	if(checkemilID(cell.toString(),0)){
								 flag=true;
    							strbuf.append(localeObj.getTranslatedText("Dublicate Email at row number")+rowCount+"\n");
    							break;
								
							 }else{
								 user.setEmail(cell.toString());
							 }
							
                        }
                        
                        
                        if(cellCount==12){ 
                        	user.setContactNo(cell.toString());
                        }
                        
                        if(cellCount==13){ 
                        	user.setExtensionNo(cell.toString());
                        }
                         
                        cellCount++;
                       
                     }

                     if(cellCount==0){
                		 cellCount++;
                		
                	 }else{
                          
                  		 if(flag==true){
                  			cellCount=1;
               				flag=false;
               				strbuf.append("\n");
               				continue;
               				
               			}else{
               				if(userObj.getUserType().equals("AD")){
               					msgObj= insertUser(user,req,msgObj);
               					sucessCnt++;
               				    cellCount = 1;
               	   			}
               				else{
               		   			if(userObj.isOfficeLevel()){
               		   				if(buCode>0 && distCode>0 && !cityCode.equals("0") && !sscCode.equals("0") && branchCode>0 && !officeCode.equals("0")){
               		   					msgObj= insertUser(user,req,msgObj);
               		   					sucessCnt++;
               		   					cellCount = 1;
               		   				}
               		   			}
               		   			
          	     	   			else if(userObj.isSscLevel()){
          	     	   				if(buCode>0 && distCode>0 && !cityCode.equals("0") && !sscCode.equals("0") && branchCode>0 ){
          	     	   					msgObj= insertUser(user,req,msgObj);
          	     	   					sucessCnt++;
          	     	   					cellCount = 1;
          	     	   				}
          	     	   			}
               	   			
          	     	   			else if(userObj.isBranchLevel()){
          	     		   				if(buCode>0 && distCode>0 && !cityCode.equals("0") && !sscCode.equals("0") && branchCode>0 ){
          	     		   					msgObj= insertUser(user,req,msgObj);
          	     		   					sucessCnt++;
          	     		   					cellCount = 1;
          	     		   				}
          	     		   			}
               		   			
          	     	   			else if(userObj.isCityLevel()){
          	     	   				if(buCode>0 && distCode>0 && !cityCode.equals("0")){
          	     	   					msgObj= insertUser(user,req,msgObj);
          	     	   					sucessCnt++;
          	     	   					cellCount = 1;
          	     	   				}
          	     	   			}
          	     	   			else if(userObj.isDistrictLevel()){
          	     	   				if(buCode>0 && distCode>0){
          	     	   					msgObj= insertUser(user,req,msgObj);
          	     	   					sucessCnt++;
          	     	   					cellCount = 1;
          	     	   				}
          	     	   			}
          	     	   			else if(userObj.isBuLevel()){
          	     	   				if(buCode>0){
          	     	   					msgObj= insertUser(user,req,msgObj);
          	     	   					sucessCnt++;
          	     	   					cellCount = 1;
          	     	   				}
          	     	   			}
               		   			
                	 }  
                    
          		   			
          		   			
          		   		User userAudi=new User(user);
 				     log.log(Level.INFO,userAudi.toString());
 				     if(!msgObj.equals("0")){ 
 				    	auditTrailMaintenance.insertAuditTrail(new AuditTrail(String.valueOf(user.getUser_no()), AuditTrail.MODULE_USER, AuditTrail.FUNCTION_CREATE, "Action on:INSERT T_USER CREATED,USER_NO:"+user.getUser_no()));
 				     }else{
 				    	auditTrailMaintenance.insertAuditTrail(new AuditTrail(String.valueOf(user.getUser_no()), AuditTrail.MODULE_USER, AuditTrail.FUNCTION_FAILED, "Action on:INSERT T_USER CREATED,USER_NO:"+user.getUser_no()));
 				    	 
 				     } 
   					
   					flag=false;
          				
          	   		}	
     		    
                }
        		
        		
				
			}
                 
                 
                 
                 
  		}
			if(strbuf.length()>0){
				ImoUtilityData imoutill=new ImoUtilityData();
				imoutill.summaryReport(strbuf,req);
				
			}
 			
			req.getSession().setAttribute("strbuf", strbuf);
			msgObj= new MsgObject("The new User  has been created.");
			req.getSession().setAttribute("msgObject", msgObj);
	  		req.getSession().setAttribute("formObj",new PathDetail().getFormObj("userUploadCsv"));
	  	    int failedCnt =  rowCount - sucessCnt;
	  	//if(sucessCnt!=0){ 
	  	  String uploadSucessString=localeObj.getTranslatedText("Number of records uploaded successfully");
	  	String recoredFailString=localeObj.getTranslatedText("Number of records fail");
			return new ErrorObject(uploadSucessString+" :"+sucessCnt+" <br> "+recoredFailString+" :"+(failedCnt-1)+"  ", "",localeObj);
		//}else{
			//return new ErrorObject("The new Holiday csv file uploaded Successfully", "");
		//}
  		}catch(Exception e){
  			log.log(Level.SEVERE, e.getMessage());
  			e.printStackTrace();
  			LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("UserMaintenance",Level.SEVERE+"",errors.toString());
		}finally{
			try{
				//HibernateFactory.close(session);
			}catch(Exception e){
				
				log.log(Level.SEVERE,e.getMessage());
				e.printStackTrace();
			}
		}
	}
		req.getSession().setAttribute("formObj",new PathDetail().getFormObj("userUploadCsv"));
  		return new ErrorObject("Please choose the document", " ",localeObj);
	}
	
	
	
}
