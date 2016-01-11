/*******************************************************************************
* -----------------------------------------------------------------------------
* <br>
* <p><b>Copyright (c) 2014 Quix Creation Pte. Ltd. All Rights Reserved.</b> 
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
* 19-May-2015       Bhaumik               File Added
***************************************************************************** */
package com.quix.aia.cn.imo.servlets;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quix.aia.cn.imo.constants.ApplicationAttribute;
import com.quix.aia.cn.imo.constants.RequestAttributes;
import com.quix.aia.cn.imo.constants.SessionAttributes;
import com.quix.aia.cn.imo.data.addressbook.AddressBook;
import com.quix.aia.cn.imo.data.auditTrail.AuditTrail;
import com.quix.aia.cn.imo.data.interview.InterviewCandidateMaterial;
import com.quix.aia.cn.imo.data.locale.LocaleObject;
import com.quix.aia.cn.imo.data.user.User;
import com.quix.aia.cn.imo.mapper.AddressBookMaintenance;
import com.quix.aia.cn.imo.mapper.ApplicationFormPDFMaintenance;
import com.quix.aia.cn.imo.mapper.InterviewAttendanceMaintenance;
import com.quix.aia.cn.imo.mapper.InterviewMaintenance;
import com.quix.aia.cn.imo.mapper.UserMaintenance;
import com.quix.aia.cn.imo.utilities.ErrorObject;
import com.quix.aia.cn.imo.utilities.LMSUtil;
import com.quix.aia.cn.imo.utilities.PageActionController;
import com.quix.aia.cn.imo.utilities.PageObj;
import com.quix.aia.cn.imo.utilities.Pager;
import com.quix.aia.cn.imo.utilities.PathDetail;

/**
 * <p>This Servlet is behaves as controller and call specific class for executing business logic.</p>
 * 
 * @author Bhaumik
 * @version 1.0
 *
 */
public class ContentManager extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PathDetail pathDetail = null;
	private ServletContext context;
	static Logger log = Logger.getLogger(ContentManager.class.getName());
	//private FilterConfig filterConfig;
	/**
	 * <p>Servlet is initialized.</p> 
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
		pathDetail = new PathDetail();
		context = config.getServletContext();
		
		
	}

	/**
	 * Public constructor created.
	 */
    public ContentManager() {
        // TODO Auto-generated constructor stub
    }


    /**
     * <p>This method executes all HTTP Get operation from browser. It checks for key parameter and based on that specific business logic executed.</p>
     * 
     *  @param request Servlet Request Parameter
     *  @param response Servlet Response Parameter
     *  @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String key = request.getParameter("key");
		 if(key!=null && key.equals("Download3rdInterviewRegistrations")){
			 InterviewMaintenance intvwMain = new InterviewMaintenance();
			 String downloadPath =intvwMain.getRegisteredCandidatesFor3rdInterviewSession(request);
			 intvwMain.downloadFile(downloadPath, request, response);
			 return;
		 }
		 
		 if(key!=null && key.equals("Download2ndInterviewRegistrations")){
			 InterviewMaintenance intvwMain = new InterviewMaintenance();
			 String downloadPath =intvwMain.getRegisteredCandidatesFor2ndInterviewSession(request);
			 intvwMain.downloadFile(downloadPath, request, response);
			 return;
		 }
		 
		 if(key!=null && key.equals("applicatonForm")){
			 
			 AddressBookMaintenance addressMain=new AddressBookMaintenance();
			 
			 	InterviewAttendanceMaintenance interviewMaint=new InterviewAttendanceMaintenance();
			 	String interviewCandidateCode=interviewMaint.getInterviewCandidateCode(Integer.parseInt(request.getParameter("candidateCode")));
				AddressBook addressbook=new AddressBook();
				addressbook.setAddressCode(Integer.parseInt(interviewCandidateCode));
				addressbook=addressMain.getAddressBook(addressbook);
				ApplicationFormPDFMaintenance applicatonPdfMain=new ApplicationFormPDFMaintenance();
				InterviewCandidateMaterial material=new InterviewCandidateMaterial();
				
				material.setInterviewCode(Integer.parseInt(request.getParameter("interviewCode")));
				material=applicatonPdfMain.pdf(request,addressbook);
			 
				if(material!=null){
					applicatonPdfMain.insertPdf(request,material);
					response.setContentLength((int)material.getFormContent().length);
					 response.setHeader("Content-Disposition","attachment; filename="+material.getMaterialFileName());
					 response.getOutputStream().write(material.getFormContent(), 0,material.getFormContent().length);
					 response.getOutputStream().flush();
				}
			 
			 /*InterviewMaintenance intvwMain = new InterviewMaintenance();
			 intvwMain.downloadFile(downloadPath, request, response);*/
			 return;
		 }
			 

	        User user = (User)request.getSession().getAttribute(SessionAttributes.CURR_USER_OBJ);
	        pathDetail = new PathDetail();
	     
	        PageObj pageObj = pathDetail.getPageObj(key);
	        Pager pager = null;
	       
	        request.getSession().removeAttribute("formObj");
	        request.getSession().setAttribute("pageObj", pageObj);
	        request.setAttribute("contentPage", pageObj);
	        String forwardPage="";
	       
            	 forwardPage = "/index.jsp";
            	//request.getSession().setAttribute("oldToken", token);
          
	        
	    
	        if (key != null)
			{
	        	 if(request.getParameter("key").equals("login"))
	             {
	        		 request.getSession().invalidate();
               
	                 forwardPage = "/login.jsp";
	             }else{
	            	 request.getSession().setAttribute(SessionAttributes.PAGE_OBJ,pageObj);
					 request.setAttribute(RequestAttributes.CONTENT_PAGE, pageObj);
					 request.getSession().setAttribute(RequestAttributes.CONTENT_PAGE, pageObj);
					 request.getSession().removeAttribute("formObj");
					 if(request.getParameter("pager.offset") != null)
		                {
		                    pager = (Pager)request.getSession().getAttribute("pager");
		                    request.setAttribute("pager", pager);
		                } 
					 else
		                if(request.getAttribute("pager") == null)
		                {
		                	if(key.equalsIgnoreCase("ViewEapplicationForm")){
		                		pager = (Pager)request.getSession().getAttribute("pager");
			                    request.setAttribute("pager", pager);
			                    request.getSession().setAttribute("pager", pager);
		                	}else{
			                    pager = PageActionController.dataListing(pageObj, request);
			                    request.setAttribute("pager", pager);
			                    request.getSession().setAttribute("pager", pager);
		                	}
		                    if(request.getAttribute("pageObj") != null)
		                        pageObj = (PageObj)request.getSession().getAttribute("pageObj");
		                }
						
				}
			}
	        RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPage);
	        dispatcher.forward(request, response);
	        
	        
	}

	
	 /**
     * <p>This method executes all HTTP Post operation from browser. It checks for key parameter and based on that specific business logic executed.</p>
     * 
     *  @param request Servlet Request Parameter
     *  @param response Servlet Response Parameter
     *  @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain; charset=UTF-8");
		PathDetail pathDetail = new PathDetail();
    	String key = request.getParameter("key");
    	 request.getSession().removeAttribute("Token");
    	 LocaleObject localeObj = new LocaleObject();
    	if(key != null)
        {
    		
    		
        } else
        {
             PageObj pageObj = (PageObj)request.getSession().getAttribute("pageObj");
             Pager pager = null;
            if(request.getSession().getAttribute(SessionAttributes.CURR_USER_OBJ) == null)
            {
                if(request.getParameter("userID") != null && request.getParameter("password") != null)
                {
                	try{
                    	request.getSession().invalidate();
                    	}catch(Exception e){};
                    	
                    String userID = request.getParameter("userID");
                    String password = request.getParameter("password");
                    String branch = request.getParameter("branch");
                    
                	String locale=request.getParameter("SelectLang");

                	// Create Location Map
					Map localeMap = (Map)context.getAttribute(ApplicationAttribute.LOCALE_MAP);

					
					localeObj.setKey(locale);
					if(locale.equalsIgnoreCase("CN"))
					{
						localeObj.setObjMap((Map)localeMap.get("CN"));
					}
					else
					{
						localeObj.setObjMap((Map)localeMap.get("EN"));
					}
					request.getSession().setAttribute(SessionAttributes.LOCALE_OBJ,localeObj);

                  // Authenticate User
                    UserMaintenance userMaintenance = new UserMaintenance();
                    User user = userMaintenance.authenticateUser(userID, password, branch, getServletContext());
                    
                    if(user != null)
	                    {
	                        boolean activeFlag = false;
	                        if(!activeFlag)
	                        {
	                        	 pageObj = null;
	                        	 request.getSession().setAttribute(SessionAttributes.CURR_USER_OBJ, user);
	                        	 request.getSession().setAttribute(ApplicationAttribute.CONFIGURATION_PROPERTIES_MAP, getServletContext().getAttribute(ApplicationAttribute.CONFIGURATION_PROPERTIES_MAP));
	                        	 request.getSession().setAttribute(ApplicationAttribute.MAIL_PROPERTIES_MAP, getServletContext().getAttribute(ApplicationAttribute.MAIL_PROPERTIES_MAP));
	                           
 	                            if("AG".equalsIgnoreCase(user.getUserType())){
 	                            	pageObj = pathDetail.getPageObj("plistDownload");
 	                            }
 	                            else{
 	                            	pageObj = pathDetail.getPageObj("home");
 	                            }
	   	                          
 	                            request.getSession().setAttribute("pageObj", pageObj);
 	                            request.setAttribute("contentPage", pageObj);
 	                            pathDetail = null;

	                            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
	                            dispatcher.forward(request, response);
	                            
	                        } else
	                        {
	                            ErrorObject errObject = new ErrorObject("Access Denied!!! Sorry, your account is currently logged in.", "",localeObj);
	                            request.setAttribute("errorObject", errObject);
	                            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
	                            dispatcher.forward(request, response);
	                            
	                        }
		                  
	                    }else
	                    {
	                        ErrorObject errObject = new ErrorObject("Access Denied!!! Invalid Login ID or Password provided.", "",localeObj);
	                        request.setAttribute("errorObject", errObject);
	                        RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
	                        dispatcher.forward(request, response);
	                        
	                    }
	                
                } else
                {
                    ErrorObject errObject = new ErrorObject("Access Denied!!! Invalid Login ID or Password provided.", "",localeObj);
                    request.setAttribute("errorObject", errObject);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
                    dispatcher.forward(request, response);
                    
                }
            }
            else
            {
            	  pageObj = null;
                pageObj = (PageObj)request.getSession().getAttribute("pageObj");
                
                if(request.getAttribute("pager") == null)
                {
                     pager = PageActionController.searchListing(pageObj, request);
                     request.setAttribute("pager", pager);
                    request.getSession().setAttribute("pager", pager);
                }
                pageObj = (PageObj)request.getSession().getAttribute("pageObj");
                pathDetail = null;
                request.getSession().setAttribute("pageObj", pageObj);
                request.setAttribute("contentPage", pageObj);
                request.getSession().setAttribute("contentPage", pageObj);
                RequestDispatcher dispatcher=null;
                String token=LMSUtil.getRendomToken(); 
             	 request.getSession().setAttribute("Token", token);
                 dispatcher = request.getRequestDispatcher("/index.jsp");
                dispatcher.forward(request, response);
                
            }
        }
	}
}
