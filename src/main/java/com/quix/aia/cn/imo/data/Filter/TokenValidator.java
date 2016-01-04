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
* 07-May-2015       Jay          
* 
****************************************** *********************************** */
package com.quix.aia.cn.imo.data.Filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quix.aia.cn.imo.utilities.LMSUtil;

/**
 * Servlet Filter implementation class TokenValidator
 */
public class TokenValidator implements Filter {

    /**
     * Default constructor. 
     */
    public TokenValidator() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request1, ServletResponse response1,
			  FilterChain chain) throws IOException, ServletException {
		
			  HttpServletRequest request = (HttpServletRequest) request1;
			  HttpServletResponse response = (HttpServletResponse) response1;

			  if(request.getMethod().equalsIgnoreCase("POST")){
				  
				  String token1 = request.getParameter("token");
				  String token2 = (String)request.getSession().getAttribute("Token");
				 System.out.println("token1 "+token1+ "   token2  "+token2);
				  if(request.getSession().getAttribute("Token")!=null){

						  if (!token1.equalsIgnoreCase(token2)) {
						   response.sendRedirect("view.jsp");
						  } else {
							  String token=LMSUtil.getRendomToken(); 
		                     	 request.getSession().setAttribute("Token", token);
		                     	 chain.doFilter(request1, response1);
						   
						  }

				  }else{
					 String token=LMSUtil.getRendomToken(); 
                 	 request.getSession().setAttribute("Token", token);
					  chain.doFilter(request1, response1);
				  }
		       }
			  else
			  {
				  if(request.getParameter("key")!=null && request.getParameter("key").equals("ViewEapplicationForm")){
					  String token = (String)request.getSession().getAttribute("Token");
					  request.getSession().setAttribute("Token", token);
					  
				  }else{
					  String token=LMSUtil.getRendomToken(); 
              	 	request.getSession().setAttribute("Token", token);
				  }
				  chain.doFilter(request1, response1);
			  }
			  
			  
			 }


	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
