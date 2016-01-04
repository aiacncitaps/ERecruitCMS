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
* Date             			 Developer          Change Description
* 06-Aug-2015       Hemraj Rathod        Application created
* 18-Nov-2015               Nibedita          Error stored in db
******************************************************************************/
package com.quix.aia.cn.imo.mapper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.quix.aia.cn.imo.constants.SessionAttributes;
import com.quix.aia.cn.imo.data.addressbook.AddressBook;
import com.quix.aia.cn.imo.data.common.AamData;
import com.quix.aia.cn.imo.data.locale.LocaleObject;
import com.quix.aia.cn.imo.database.HibernateFactory;
import com.quix.aia.cn.imo.utilities.KeyObjPair;
import com.quix.aia.cn.imo.utilities.LMSUtil;
import com.quix.aia.cn.imo.utilities.MsgObject;
import com.quix.aia.cn.imo.utilities.Pager;


/**
 * <p>This class defines the data operations & csv file upload </p>
 * @author Jinatmayee
 * @version 1.0
 */
public class CCTestMaintenance {
    static Logger log = Logger.getLogger(CCTestMaintenance.class.getName());
    
	public CCTestMaintenance()
	{
		HibernateFactory.buildIfNeeded();
	}
	
	
/**
 * <p>This method performs Interview Listing</p>
 * @param req Servlet Request Parameter
 * @return Pager object
 */
public   Pager cctestAllSearchListing(HttpServletRequest req)
{
	LocaleObject localeObj = (LocaleObject)req.getSession().getAttribute(SessionAttributes.LOCALE_OBJ);
	LinkedList item = new LinkedList();
    AddressBook interview = null;
    ArrayList interviewList=new  ArrayList();
    interviewList = getAllCCTest(req);
    req.getSession().setAttribute("CC_TEST_AGENT_LIST", interviewList);
   
    for(int i = 0; i < interviewList.size(); i++)
    {
    	interview = new AddressBook();
    	interview = (AddressBook)interviewList.get(i);
    	item.add(interview.getGetCCTestResultRow(localeObj));
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
 * <p>This method performs Interview Listing</p>
 * @param req Servlet Request Parameter
 * @return Pager object
 */
public   Pager cctestSearchListing(HttpServletRequest req,String agentId)
{
	
	LocaleObject localeObj = (LocaleObject)req.getSession().getAttribute(SessionAttributes.LOCALE_OBJ);
	LinkedList item = new LinkedList();
    AddressBook interview = null;
    ArrayList interviewList=new  ArrayList();
    interviewList = getCCTest(req,agentId);
    req.getSession().setAttribute("CC_TEST_AGENT_LIST", interviewList);
   
    for(int i = 0; i < interviewList.size(); i++)
    {
    	interview = new AddressBook();
    	interview = (AddressBook)interviewList.get(i);
    	item.add(interview.getGetCCTestResultRow(localeObj));
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
 * <p>This method retrieves Interview lists based on search criteria</p>
 * @param req Servlet Request Parameter
 * @return Interview lists
 */


public  ArrayList getAllCCTest(HttpServletRequest req) 
{

	log.log(Level.INFO,"InterviewMaintenance --> getSearchedInterview ");
	AddressBook interview = null;
	ArrayList<AddressBook> listData =  new  ArrayList<AddressBook>();

	String agent_code = req.getParameter("agent_code");

	Map<String,String> intMap = new HashMap<String,String>();
	if(agent_code!=null)
		intMap.put("agent_code", agent_code);

	LogsMaintenance logsMain=new LogsMaintenance();
	req.getSession().setAttribute("INT_SEARCH_OBJ", intMap);
	Session session = null;
	try
	{
		session = HibernateFactory.openSession();   
		/*Criteria criteria = session.createCriteria(AddressBook.class);
		if(agent_code!=null && agent_code.length() > 0){
			criteria.add(Restrictions.like("agentId", agent_code));
		}

		ArrayList interviewList = (ArrayList) criteria.list();*/
		
		Query query=session.createQuery("select addressCode,name,ccTestResult,ccTestResultDate from AddressBook where agentId=:agentId");
		query.setParameter("agentId", agent_code);
		ArrayList<Object[]> interviewList=(ArrayList<Object[]>) query.list();
		
		for (int i = 0; i < interviewList.size(); i++) {
			Object obj[] = interviewList.get(i);
			interview = new AddressBook();
			interview.setAddressCode((Integer)obj[0]);
			interview.setName((String)obj[1]);
			interview.setCcTestResult((String)obj[2]);
			Date dt = (Date)obj[3];
			interview.setCcTestResultDate(dt);
			listData.add(interview);
		}
		
		/*Iterator iterator = interviewList.iterator();
		while(iterator.hasNext()){
			AddressBook addressBook=(AddressBook) iterator.next();
			listData.add(addressBook);
		}*/
	}
	catch(Exception e)
	{
		log.log(Level.SEVERE,e.getMessage());
		e.printStackTrace();
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		logsMain.insertLogs("CCTestMaintenance",Level.SEVERE+"",errors.toString());
	}finally{
		try{
			HibernateFactory.close(session);
		}catch(Exception e){

			log.log(Level.SEVERE,e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("CCTestMaintenance",Level.SEVERE+"",errors.toString());
		}
	}
	return listData;
}

/**
 * <p>This method retrieves Interview lists based on search criteria</p>
 * @param req Servlet Request Parameter
 * @return Interview lists
 */
public  ArrayList getCCTest(HttpServletRequest req,String agentId) 
{

	log.log(Level.INFO,"InterviewMaintenance --> getSearchedInterview ");
	AddressBook interview = null;
	ArrayList listData =  new  ArrayList();

	String agent_code = agentId;

	Map<String,String> intMap = new HashMap<String,String>();
	if(agent_code!=null)
		intMap.put("agent_code", agent_code);

	LogsMaintenance logsMain=new LogsMaintenance();
	req.getSession().setAttribute("INT_SEARCH_OBJ", intMap);
	Session session = null;
	try
	{
		session = HibernateFactory.openSession();   
		/*Criteria criteria = session.createCriteria(AddressBook.class);
		if(agent_code!=null && agent_code.length() > 0){
			criteria.add(Restrictions.like("agentId", agent_code));
		}

		ArrayList interviewList = (ArrayList) criteria.list();*/
		Query query=session.createQuery("select addressCode,name,ccTestResult,ccTestResultDate from AddressBook where agentId=:agentId");
		query.setParameter("agentId", agent_code);
		ArrayList<Object[]> interviewList=(ArrayList<Object[]>) query.list();
		
		for (int i = 0; i < interviewList.size(); i++) {
			Object obj[] = interviewList.get(i);
			interview = new AddressBook();
			interview.setAddressCode((Integer)obj[0]);
			interview.setName((String)obj[1]);
			interview.setCcTestResult((String)obj[2]);
			Date dt = (Date)obj[3];
			interview.setCcTestResultDate(dt);
			listData.add(interview);
		}
		/*Iterator iterator = interviewList.iterator();
		while(iterator.hasNext()){
			AddressBook addressBook=(AddressBook) iterator.next();
			listData.add(addressBook);
		}*/
	}
	catch(Exception e)
	{
		log.log(Level.SEVERE,e.getMessage());
		e.printStackTrace();
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		logsMain.insertLogs("CCTestMaintenance",Level.SEVERE+"",errors.toString());
	}finally{
		try{
			HibernateFactory.close(session);
		}catch(Exception e){

			log.log(Level.SEVERE,e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("CCTestMaintenance",Level.SEVERE+"",errors.toString());
		}
	}
	return listData;
}


/**
 * <p>Updating Candidate Complete Status</p>
 * @param requestParameters  Servlet Request Parameter
 * @return pager
 */
public Pager updateCCTestStatus(HttpServletRequest requestParameters)
{
	Session session = null;
	String status = "N";
	AddressBook candidate = null;
	@SuppressWarnings("unchecked")
	ArrayList<AddressBook>  attendanceList = (ArrayList<AddressBook>)requestParameters.getSession().getAttribute("CC_TEST_AGENT_LIST");
	requestParameters.getSession().removeAttribute("CC_TEST_AGENT_LIST");
	LogsMaintenance logsMain=new LogsMaintenance();
	try{
		session = HibernateFactory.openSession();
		Transaction tx = session.beginTransaction();
		for(AddressBook candidateObj : attendanceList)
		{
			candidate = (AddressBook)session.get(AddressBook.class,candidateObj.getAddressCode());
			
			String _ccStatus =  requestParameters.getParameter(candidateObj.getAddressCode()+"_ccStatus");
			_ccStatus = _ccStatus == null ? "":_ccStatus;
			
			String _interviewResult =  requestParameters.getParameter(candidateObj.getAddressCode()+"_ccResultDate");
			_interviewResult = _interviewResult == null ? "":_interviewResult;
			
			Date dt =null;
			if(!_interviewResult.equals(""))
				dt  = LMSUtil.convertStringToDate(_interviewResult);
			if(!_ccStatus.equals("") && !_interviewResult.equals(""))
			{	
				candidate.setCcTestResult(_ccStatus);
				candidate.setCcTestResultDate(dt);
				session.update(candidate);
			}
		}
		
		tx.commit();
		status = "Y";
		log.log(Level.INFO,"---Address Book Updated Successfully--- ");
	}catch(Exception e)
	{
		log.log(Level.SEVERE, e.getMessage());
		e.printStackTrace();
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		logsMain.insertLogs("CCTestMaintenance",Level.SEVERE+"",errors.toString());
	}finally{
		try{
			HibernateFactory.close(session);
			
		}catch(Exception e){
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("CCTestMaintenance",Level.SEVERE+"",errors.toString());
		}
}
	MsgObject msgObj = null;
	 if(status.equals("Y"))
		 msgObj = new MsgObject("Address Book Updated Successfully.");
	 else
		 msgObj = new MsgObject("The Address Book has not been Updated."); 
	 
	requestParameters.setAttribute("messageObject", msgObj);
	String agentId = "";
	if(requestParameters.getSession().getAttribute("INT_SEARCH_OBJ")!=null){
		Map<String,String> holMap = (Map)requestParameters.getSession().getAttribute("INT_SEARCH_OBJ");
		agentId = holMap.get("agent_code");
	}

		return cctestSearchListing(requestParameters,agentId);
}






public static final KeyObjPair MONTH_VALUE_PAIR[] = {
    new KeyObjPair("01", "January"), new KeyObjPair("02", "February"), new KeyObjPair("03", "March"), new KeyObjPair("04", "April"), new KeyObjPair("05", "May"), new KeyObjPair("06", "June"), new KeyObjPair("07", "July"), new KeyObjPair("08", "August"), new KeyObjPair("09", "September"), new KeyObjPair("10", "October"), 
    new KeyObjPair("11", "November"), new KeyObjPair("12", "December")
};

}
