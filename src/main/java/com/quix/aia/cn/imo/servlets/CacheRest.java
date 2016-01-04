package com.quix.aia.cn.imo.servlets;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.quix.aia.cn.imo.utilities.ImoUtilityData;


/**
 * Servlet implementation class CacheRest
 */
public class CacheRest extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(CacheRest.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CacheRest() {
        super();
        // TODO Auto-generated constructor stub	
		
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Logger log = Logger.getLogger(CacheRest.class.getName());
		String moduleName="";
		
		try{
			ImoUtilityData imoutill=new ImoUtilityData();
			if(request.getParameter("moduleName")!=null ){
				moduleName=request.getParameter("moduleName");
				
				// *******    For specific module  ********
 				
//				if(moduleName.equals("holiday")){
//					imoutill.getHolidayCache(); }else
				 if(moduleName.equals("Announcement")){
					imoutill.getAnnouncementCache();	
				}else if(moduleName.equals("EOP")){
					imoutill.getEventCache();
				}else if(moduleName.equals("Interview")){
					imoutill.getInterviewCache();
				}else if(moduleName.equals("Presenter")){
					imoutill.getPresenterCache();
				}else if(moduleName.equals("EGreeting")){
					imoutill.getEGreetingCache();
				}else{
					
					//  *****   For all module  ********* 
					//imoutill.getHolidayCache();
					imoutill.getAnnouncementCache();
					imoutill.getEventCache();
					imoutill.getInterviewCache();
					imoutill.getPresenterCache();
					imoutill.getEGreetingCache();
				}
				
			}
			
		}catch(Exception e){
			log.log(Level.INFO, "CacheRest -->  doPost Method"); 
			e.printStackTrace();
			
		}
		
		
	}

}
