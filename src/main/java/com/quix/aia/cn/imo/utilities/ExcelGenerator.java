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
* 17-June-2015       Nibedita          file added

****************************************** *********************************** */
package com.quix.aia.cn.imo.utilities;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jxls.XLSTransformer;

import com.quix.aia.cn.imo.data.event.Event;
import com.quix.aia.cn.imo.data.event.EventCandidate;
import com.quix.aia.cn.imo.data.interview.Interview;
import com.quix.aia.cn.imo.mapper.LogsMaintenance;

public class ExcelGenerator {
	
	static Logger log = Logger.getLogger(ExcelGenerator.class.getName());
	 /**
	  * <p>set map.call excel creation method</P>
	  * @param attendanceList
	  * @param event
	  * @param templDir excel template location
	  * @param outPutDir file stored location
	  * @return file path as String
	  */
	 public String GenerateEopRegistrationReport(Collection attendanceList,Event event, String templDir, String outPutDir)
	    {
	    	try
	    	{
	        Map beans = new HashMap();
	       
	        if(attendanceList.size()==0)
	        	attendanceList.add(new EventCandidate());
	        beans.put("class", event);
	        beans.put("attendee", attendanceList);
	        
	        if ( ! outPutDir.endsWith("/") && !outPutDir.endsWith("\\") )
	        	outPutDir +="/";
	        
	        if ( ! templDir.endsWith("/") && !templDir.endsWith("\\") )
	        	templDir +="/";
	        
	        
	        return GenerateExcelReport(beans, templDir + "EopRegistrationListReport.xls", outPutDir);
	    	}
	    	catch(Exception e)
	    	{
	    		log.log(Level.SEVERE, e.getMessage());
	    		e.printStackTrace();
	    		LogsMaintenance logsMain=new LogsMaintenance();
	    		StringWriter errors = new StringWriter();
	    		e.printStackTrace(new PrintWriter(errors));
	    		logsMain.insertLogs("ExcelGenerator",Level.SEVERE+"",errors.toString());
	    		return null;
	    	}
	    	catch(Throwable e)
	        {
	    		log.log(Level.SEVERE, e.getMessage());
	            e.printStackTrace();
	            LogsMaintenance logsMain=new LogsMaintenance();
	        	StringWriter errors = new StringWriter();
	        	e.printStackTrace(new PrintWriter(errors));
	        	logsMain.insertLogs("ExcelGenerator",Level.SEVERE+"",errors.toString());
	            return null;
	        }
	        
	    }
	 
	 /**
	  * <p>set map.call excel creation method</P>
	  * @param attendanceList
	  * @param event
	  * @param templDir excel template location
	  * @param outPutDir file stored location
	  * @return file path as String
	  */
	 public String GenerateInterviewRegistrationReport(Collection attendanceList,Interview event, String templDir, String outPutDir)
	    {
	    	try
	    	{
	        Map beans = new HashMap();
	        if(attendanceList.size()==0)
	        	attendanceList.add(new EventCandidate());
	        beans.put("class", event);
	        beans.put("attendee", attendanceList);
	        
	        if ( ! outPutDir.endsWith("/") && !outPutDir.endsWith("\\") )
	        	outPutDir +="/";
	        
	        if ( ! templDir.endsWith("/") && !templDir.endsWith("\\") )
	        	templDir +="/";
	        
	        
	        return GenerateExcelReport(beans, templDir + "InterviewRegistrationListReport.xls", outPutDir);
	    	}
	    	catch(Exception e)
	    	{
	    		log.log(Level.SEVERE, e.getMessage());
	    		e.printStackTrace();
	    		LogsMaintenance logsMain=new LogsMaintenance();
	    		StringWriter errors = new StringWriter();
	    		e.printStackTrace(new PrintWriter(errors));
	    		logsMain.insertLogs("ExcelGenerator",Level.SEVERE+"",errors.toString());
	    		return null;
	    	}
	    	catch(Throwable e)
	        {
	    		log.log(Level.SEVERE, e.getMessage());
	            e.printStackTrace();
	            LogsMaintenance logsMain=new LogsMaintenance();
	        	StringWriter errors = new StringWriter();
	        	e.printStackTrace(new PrintWriter(errors));
	        	logsMain.insertLogs("ExcelGenerator",Level.SEVERE+"",errors.toString());
	            return null;
	        }
	        
	    }
	 /**
	  * <p>Generate excel file and store in specified location</p>
	  * @param beans
	  * @param templatePath excel template location
	  * @param ouputDir  file stored location
	  * @return file location as string
	  */
	    public static  String GenerateExcelReport(Map beans, String templatePath, String ouputDir)
	    {
	        XLSTransformer transformer = new XLSTransformer();
	        Date currDate = new Date();
	        try
	        {
	        	if ( !ouputDir.endsWith("/") && ouputDir.trim().length() > 0 ) {
	                ouputDir += "/";
	            }
	        	
	            new File(ouputDir).mkdirs();
	            log.log(Level.INFO,"before transform");
	            transformer.transformXLS(templatePath, beans, ouputDir + currDate.getTime() + ".xls");
	            log.log(Level.INFO,"after transform");
	            return "resources/userFiles/" + currDate.getTime() + ".xls";
	           
	        } 
	        catch(Exception e)
	        {
	           log.log(Level.SEVERE, e.getMessage());
	           e.printStackTrace();
	           LogsMaintenance logsMain=new LogsMaintenance();
	       	StringWriter errors = new StringWriter();
	       	e.printStackTrace(new PrintWriter(errors));
	       	logsMain.insertLogs("ExcelGenerator",Level.SEVERE+"",errors.toString());
	        }
	        catch(Throwable e)
	        {
	        	log.log(Level.SEVERE, e.getMessage());
	            e.printStackTrace();
	            LogsMaintenance logsMain=new LogsMaintenance();
	        	StringWriter errors = new StringWriter();
	        	e.printStackTrace(new PrintWriter(errors));
	        	logsMain.insertLogs("ExcelGenerator",Level.SEVERE+"",errors.toString());
	        }
	        
	        return "#";
	    }
	  
}
