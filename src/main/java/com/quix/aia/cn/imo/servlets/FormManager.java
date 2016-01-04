package com.quix.aia.cn.imo.servlets;

import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quix.aia.cn.imo.constants.RequestAttributes;
import com.quix.aia.cn.imo.constants.SessionAttributes;
import com.quix.aia.cn.imo.data.user.User;
import com.quix.aia.cn.imo.utilities.FormActionController;
import com.quix.aia.cn.imo.utilities.FormObj;
import com.quix.aia.cn.imo.utilities.PageObj;
import com.quix.aia.cn.imo.utilities.Pager;
import com.quix.aia.cn.imo.utilities.PathDetail;


public class FormManager extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PathDetail pathDetail = null;

    /**
     * Default constructor. 
     */
    public FormManager() {
    	super();
    }
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		pathDetail = new PathDetail();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String key = null;
		FormObj formDetail = null;
		String formType = null;
		request.getSession().setAttribute("StartToken", "1");
		if (request.getSession().getAttribute(SessionAttributes.FORM_OBJ) == null) {
			key = request.getParameter("key");
			FormObj origFormDetail = pathDetail.getFormObj(key);
			// we are cloning this object.
			try
			{
				formDetail = (FormObj)origFormDetail.clone();
			}
			catch(CloneNotSupportedException e)
			{
				e.printStackTrace();
			}
			
			if (formDetail != null) {
				formType = request.getParameter("type");
				if (formType == null) {
					formType = "NEW";
				}
				formDetail.setFormType(formType);
			}
			
			
			Object functionObj = FormActionController.formDataMapper(null, formDetail,request);
			if(formDetail.getFormType()!=null && formDetail.getFormType().equals("DELETE"))
				request.setAttribute(RequestAttributes.CONTENT_PAGE, request.getSession().getAttribute(SessionAttributes.PAGE_OBJ));
			else if(!formType.equals("VIEW"))
				{
				request.getSession().removeAttribute(SessionAttributes.FUNCTION_OBJ);
				request.getSession().setAttribute(SessionAttributes.FUNCTION_OBJ, functionObj);
				request.getSession().setAttribute(SessionAttributes.FORM_OBJ, formDetail);
				request.setAttribute(RequestAttributes.CONTENT_PAGE, formDetail);
				}
		} 
		else // If it is existing request
		{
			formDetail = (FormObj) request.getSession().getAttribute(SessionAttributes.FORM_OBJ);
			Object functionObj = request.getSession().getAttribute(SessionAttributes.FUNCTION_OBJ);
			Object rtnObject = FormActionController.formDataMapper(functionObj, formDetail, request);
			
			// If there is no error then set object into session
			if(rtnObject!=null && !rtnObject.getClass().toString().equals(com.quix.aia.cn.imo.utilities.ErrorObject.class.toString()))
			{
				functionObj = rtnObject;
				request.getSession().setAttribute(SessionAttributes.FUNCTION_OBJ, functionObj);
				request.setAttribute(RequestAttributes.CONTENT_PAGE, formDetail);
				request.getSession().setAttribute(RequestAttributes.CONTENT_PAGE, formDetail);
			}
			else /// If there is error
			{
				request.setAttribute(RequestAttributes.ERROR_OBJ, rtnObject);
				request.setAttribute(RequestAttributes.CONTENT_PAGE, formDetail);
			}
			
			request.getSession().setAttribute(SessionAttributes.FUNCTION_OBJ, functionObj);
			request.getSession().setAttribute(RequestAttributes.CONTENT_PAGE, formDetail);
			request.setAttribute(RequestAttributes.CONTENT_PAGE, formDetail);
			
		}
		if(request.getAttribute("CANDIDATE_REG_MSG")!=null ){
			request.setAttribute(RequestAttributes.CONTENT_PAGE, pathDetail.getPageObj("Message"));
			request.getSession().removeAttribute(SessionAttributes.FORM_OBJ);
			request.getSession().removeAttribute(SessionAttributes.FUNCTION_OBJ);
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/plain; charset=UTF-8");
			Logger log = Logger.getLogger(FormManager.class.getName());
			String actionType=request.getParameter("actionType");
			
			Object functionObj = null;
			FormObj formDetail = null;
//			String formType = null;
			User user = (User) request.getSession().getAttribute(SessionAttributes.CURR_USER_OBJ);
			functionObj = request.getSession().getAttribute(SessionAttributes.FUNCTION_OBJ);
			formDetail = (FormObj) request.getSession().getAttribute(SessionAttributes.FORM_OBJ);
		
			if (request.getSession().getAttribute(SessionAttributes.FORM_OBJ) != null) {
				if(actionType!=null && actionType.equals("MODIFY")){
					formDetail.setFormType(actionType);
				}
				
				
				if(isNextAction(request))
				{ 
					request.getSession().removeAttribute("popUpUserSelection");
					request.getSession().removeAttribute("popUpWindow");
					
					request = mapFieldsToObject(functionObj, formDetail, request);
			 
				}
				else if(isPrevAction(request))
				{ 
					request.getSession().removeAttribute("popUpUserSelection");
					request.getSession().removeAttribute("popUpWindow");
					
					request = mapFieldsToObject(functionObj, formDetail, request);
				}
				else if(isSubmitAction(request))
				{ 
					request = saveUserObject(functionObj, formDetail, request);
					
					if (formDetail.getKey().equals("GoalConfig") && request.getAttribute("errorObject") == null) {
							response.sendRedirect("FormManager?key=GoalConfig");
							return;
					}
				}else if (isModifyAction(request)) {

					functionObj = request.getSession().getAttribute("functionObj");
					request = saveUserObject(functionObj, formDetail, request);
				 
				}
			}
			
			RequestDispatcher dispatcher = null;
			dispatcher = request.getRequestDispatcher("/index.jsp");
			dispatcher.forward(request, response);
	}
	public boolean isSubmitAction(HttpServletRequest req)
	{
		for(Enumeration enum1 = req.getParameterNames(); enum1.hasMoreElements();)
		{
			String parameterName = (String)enum1.nextElement();
			if(parameterName.indexOf("SUBMIT") != -1 || req.getParameter("actionType").equals("SUBMIT"))
				return true;
		}

		return false;
	}
	
	
	public HttpServletRequest saveUserObject(Object functionObj, FormObj formDetail, HttpServletRequest req)
	{
		req.getSession().setAttribute(SessionAttributes.SAVE_USER_OBJECT,"true");
		Object rtnObject = null;
		PageObj pageObj = null;
		Pager pagerObj = null;
		rtnObject = FormActionController.formDataMapper(functionObj, formDetail, req);
		
		// If there is no error 
		if(!rtnObject.getClass().toString().equals(com.quix.aia.cn.imo.utilities.ErrorObject.class.toString()))
		{ 
			functionObj = rtnObject;
			if(formDetail.getFormType().equals("NEW"))
			{
				formDetail.setCanCreate(true);
			}
			else if(formDetail.getFormType().equals("MODIFY"))
				formDetail.setCanUpdate(true);
		
			rtnObject = FormActionController.formDataMapper(functionObj, formDetail, req);
			
			if(!rtnObject.getClass().toString().equals(com.quix.aia.cn.imo.utilities.ErrorObject.class.toString()))
			{ 
				pageObj = (PageObj)req.getSession().getAttribute(SessionAttributes.PAGE_OBJ);
				if(req.getAttribute("CANDIDATE_REG_MSG")!=null){
					req.setAttribute(RequestAttributes.CONTENT_PAGE, pathDetail.getPageObj("Message"));
				}else
				req.setAttribute(RequestAttributes.CONTENT_PAGE, pageObj);
	 
				pagerObj = (Pager)req.getAttribute(RequestAttributes.PAGING_OBJECT);
				req.setAttribute(RequestAttributes.PAGING_OBJECT, pagerObj);
				req.getSession().removeAttribute(SessionAttributes.FORM_OBJ); 
				req.getSession().removeAttribute(SessionAttributes.FUNCTION_OBJ);
	 
			} else
			{
				 
				req.setAttribute(RequestAttributes.ERROR_OBJ, rtnObject);
			 
				if(formDetail.getFormType().equals("NEW"))
					formDetail.setCanCreate(false);
				else
				if(formDetail.getFormType().equals("MODIFY"))
					formDetail.setCanUpdate(false);
				req.getSession().setAttribute(SessionAttributes.FORM_OBJ, formDetail);
				req.setAttribute(RequestAttributes.CONTENT_PAGE, formDetail);
			}
		}
		else
		{
			req.setAttribute(RequestAttributes.ERROR_OBJ, rtnObject);
			req.getSession().setAttribute(SessionAttributes.FORM_OBJ, formDetail);
			req.setAttribute(RequestAttributes.CONTENT_PAGE, formDetail);
			
		}
	
		return req;
	}
	
	public boolean isNextAction(HttpServletRequest req)
	{
		/*
		for(Enumeration enum1 = req.getParameterNames(); enum1.hasMoreElements();)
		{
			String parameterName = (String)enum1.nextElement();
			if(parameterName.indexOf("NEXT_STEP") != -1 || req.getParameter("actionType").equals("NEXT_STEP"))
				return true;
		}*/
		if(req.getParameter("actionType").equals("NEXT_STEP"))
			return true;
		return false;
	}
	
	public boolean isPrevAction(HttpServletRequest req)
	{
		/*
		for(Enumeration enum1 = req.getParameterNames(); enum1.hasMoreElements();)
		{
			String parameterName = (String)enum1.nextElement();
			if(parameterName.indexOf("PREV_STEP") != -1 || req.getParameter("actionType").equals("PREV_STEP"))
				return true;
		}
		*/
		if(req.getParameter("actionType").equals("PREV_STEP"))
			return true;
		
		return false;
	}
	
	public static boolean isModifyAction(HttpServletRequest req) {
		for (Enumeration enum1 = req.getParameterNames(); enum1
				.hasMoreElements();) {
			String parameterName = (String) enum1.nextElement();
			if (parameterName.indexOf("MODIFY") != -1
					|| req.getParameter("actionType").equals("MODIFY"))
				return true;
		}

		return false;
	}
	
	public HttpServletRequest mapFieldsToObject(Object functionObj, FormObj formDetail, HttpServletRequest req)
	{ 
		Object rtnObject = null;
		rtnObject = FormActionController.formDataMapper(functionObj, formDetail, req);
 
		if(!rtnObject.getClass().toString().equals(com.quix.aia.cn.imo.utilities.ErrorObject.class.toString()))
		{ 
			functionObj = rtnObject;
			req.getSession().setAttribute(SessionAttributes.FUNCTION_OBJ, functionObj);
			if(isNextAction(req))
			{
				if(formDetail.hasNext())
					formDetail.next();
			} else
			if(isPrevAction(req) && formDetail.hasPrev())
				formDetail.prev();
		} else
		{ 
			req.setAttribute(RequestAttributes.ERROR_OBJ, rtnObject);
		}
		req.getSession().setAttribute(SessionAttributes.FORM_OBJ, formDetail);
		req.setAttribute(RequestAttributes.CONTENT_PAGE, formDetail);
		return req;
	}


}
