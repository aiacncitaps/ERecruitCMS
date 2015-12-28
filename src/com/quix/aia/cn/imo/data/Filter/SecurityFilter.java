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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class SecurityFilter
 */
public class SecurityFilter implements Filter {

	private Logger log = Logger.getLogger(SecurityFilter.class.getName());
    /**
     * Default constructor. 
     */
    public SecurityFilter() {
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
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		
		try{
			HttpServletResponse  httpResponse = (HttpServletResponse)response;
			HttpServletRequest  httpRequest = (HttpServletRequest)request;
			
			// X-Powered-by can be set in Jboss , if cant set then set value as Blank
			httpResponse.setHeader("X-Powered-By","");
			
			// 
			httpResponse.addHeader( "X-FRAME-OPTIONS", "SAMEORIGIN" );

			String path = httpRequest.getServletPath();
			if(path != null && !(path.endsWith(".js") || path.endsWith(".css") || path.endsWith(".png")|| path.endsWith(".jpg")|| path.endsWith(".jpeg")|| path.endsWith(".gif")) )
			{
			//	System.out.println("servlet path " +httpRequest.getServletPath());
			//	System.out.println("requset uri " +httpRequest.getRequestURI());
				httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
				httpResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0
				httpResponse.setHeader("Expires", "0"); // Proxies.
				httpRequest.setCharacterEncoding("UTF-8");
				httpResponse.setHeader("X-Content-Type-Options", "");
			}

			// It can be done JBoss or web.xml. In this project we have done in web.xml
			/*
			HttpServletRequest  httpRequest = (HttpServletRequest)request;
			String sessionid = httpRequest.getSession().getId();
			// be careful overwriting: JSESSIONID may have been set with other flags
			httpResponse.setHeader("Set-Cookie", "JSESSIONID=" + sessionid + "; Secure; HttpOnly");
			*/
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE,e.getMessage());
			e.printStackTrace();
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
