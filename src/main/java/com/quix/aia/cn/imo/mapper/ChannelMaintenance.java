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
 * Date                       Developer           Description
 * 1-May-2015                khyati             Add,update,delete,search functionality
 * 15-may-2015               khyati             Added Copy rights and security 
 * 10-june-2015              khyati             documention added.
 ***************************************************************************** */

package com.quix.aia.cn.imo.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.ParseConversionEvent;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.quix.aia.cn.imo.constants.SessionAttributes;
import com.quix.aia.cn.imo.data.auditTrail.AuditTrail;
import com.quix.aia.cn.imo.data.bu.Bu;
import com.quix.aia.cn.imo.data.channel.Channel;
import com.quix.aia.cn.imo.data.department.Department;
import com.quix.aia.cn.imo.data.locale.LocaleObject;
import com.quix.aia.cn.imo.data.user.User;
import com.quix.aia.cn.imo.database.HibernateFactory;
import com.quix.aia.cn.imo.utilities.ErrorObject;
import com.quix.aia.cn.imo.utilities.FormObj;
import com.quix.aia.cn.imo.utilities.LMSUtil;
import com.quix.aia.cn.imo.utilities.MsgObject;
import com.quix.aia.cn.imo.utilities.Pager;


/**
 * <p>
 * This class defines the data operations-Add,Update,Delete,search.
 * </p>
 * 
 * @author khyati
 * @version 1.0
 */



public class ChannelMaintenance {
	static Logger log = Logger.getLogger(BUMaintenance.class.getName());

	
	
	
	/**
	 * <p>
	 * This method performs listing of default search result of Channel Record 
	 * </p>
	 * 
	 * @param
	 * @return  pager
	 */
	
	public Pager getAllChannelListing() {

		LinkedList item = new LinkedList();
		Channel channelObj = null;
		ArrayList vecAllRes = new ArrayList();

		vecAllRes = getAllChannel();
		if (vecAllRes.size() != 0) {
			for (int i = 0; i < vecAllRes.size(); i++) {
				channelObj = new Channel();
				channelObj = (Channel) vecAllRes.get(i);
				item.add(channelObj.getGetChannelListingTableRow(i));
			}
		}
		Pager pager = new Pager();
		pager.setActualSize(item.size());
		pager.setCurrentPageNumber(0);
		pager.setMaxIndexPages(10);
		pager.setMaxPageItems(10);

		for (; item.size() % 10 != 0; item.add("<tr></tr>"))
			;
		pager.setItems(item);
		return pager;
	}

	
	
	/**
	 * <p>
	 * This is sub method of default search listing method.
	 * </p>
	 * 
	 * @param
	 * @return ArrayList
	 */
	public ArrayList getAllChannel() {

		log.log(Level.INFO, "ChannelMaintanence --> getAllChannel");
		ArrayList arrActivity = new ArrayList();
		Session session = null;

		try {

			session = HibernateFactory.openSession();

			Query query = session
					.createQuery(" from Channel where status = 1 ");
			arrActivity = (ArrayList) query.list();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		} finally {
			HibernateFactory.close(session);
		}

		return arrActivity;
	}

	/**
	 * <p>
	 * This method performs call for insertion method for Channel & audit trail for
	 * Channel add & sets message object in request
	 * </p>
	 * 
	 * @param Channel class object
	 * @param requestParameters Servlet Request Parameter
	 * @return Channel ChannelClassObj
	 */
	public Object createNewChannel(Channel ChannelClassObj,
			HttpServletRequest requestParameters) {

		log.log(Level.INFO, "ChannelMaintenance --> createNewChannel ");
		MsgObject msgObj = null;

		User userObj = (User) requestParameters.getSession().getAttribute(
				"currUserObj");
		ChannelClassObj.setCreatedBy(userObj.getStaffLoginId());
		ChannelClassObj.setCreationDate(new Date());
		ChannelClassObj.setModifiedBy(userObj.getStaffLoginId());
		ChannelClassObj.setModificationDate(new Date());
		int status = insertChannel(ChannelClassObj);
		

		if (status != 0) {
			AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
			auditTrailMaintenance.insertAuditTrail(new AuditTrail(userObj
					.getUser_no() + "", AuditTrail.MODULE_CHANNEL,
					AuditTrail.FUNCTION_CREATE, ChannelClassObj.toString()));
			msgObj = new MsgObject(
					"The new Channel has been successfully created.");
		} else {
			msgObj = new MsgObject("The new Channel has not been created.");
		}
		requestParameters.setAttribute("messageObject", msgObj);
		requestParameters.setAttribute("pager", getAllChannelListing());
		requestParameters.getSession().setAttribute("pager",
				getAllChannelListing());
		return ChannelClassObj;
	}

	/**
	 * <p>
	 * This method performs insert of Channel
	 * </p>
	 * 
	 * @param Channel Class Object
	 * @return int key 0 or 1
	 */
	public int insertChannel(Channel ChannelClassObj) {
		Integer key = 0;
		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			key = (Integer) session.save(ChannelClassObj);
			tx.commit();
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
		}
		return key;
	}


	/**
	 * <p>
	 * This method performs edit view of Channel.
	 * </p>
	 * 
	 * @param  int ChannelCode
	 * @return Channel class object
	 */
	

	public Channel getChannel(int ChannelCode) {
		Channel ChannelClassObj = new Channel();
		Session session = null;
		;

		try {
			session = HibernateFactory.openSession();

			ChannelClassObj = (Channel) session.get(Channel.class, ChannelCode);

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
		}

		return ChannelClassObj;
	}

	/**
	 * <p>
	 * This method performs update view of Channel.
	 * </p>
	 * @param  Channel class object
	 * @param  requestParameters Servlet Request Parameter
	 * @return Channel class object
	 */

	public Channel updateChannel(Channel ChannelClassObj,
			HttpServletRequest requestParameters) {
		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			User userObj = (User) requestParameters.getSession().getAttribute(
					"currUserObj");
			ChannelClassObj.setChannelCode(Integer.parseInt(requestParameters
					.getParameter("channelCode")));
			ChannelClassObj.setModifiedBy(userObj.getStaffLoginId());
			ChannelClassObj.setModificationDate(new Date());

			session.update(ChannelClassObj);
			AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
			auditTrailMaintenance.insertAuditTrail(new AuditTrail(userObj
					.getUser_no() + "", AuditTrail.MODULE_CHANNEL,
					AuditTrail.FUNCTION_UPDATE, ChannelClassObj.toString()));
			tx.commit();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}

		}
		MsgObject msgObj = new MsgObject(
				"The  Channel has been successfully Updated.");
		requestParameters.setAttribute("messageObject", msgObj);
		requestParameters.setAttribute("pager", getAllChannelListing());
		requestParameters.getSession().setAttribute("pager",
				getAllChannelListing());
		return ChannelClassObj;
	}

	/**
	 * <p>
	 * This method performs Delete of Channel.
	 * </p>
	 * 
	 * @param  int ChannelCode
	 * @param  requestParameters  Servlet Request Parameter
	 * @return 
	 */
	public void deleteChannel(int ChannelCode, HttpServletRequest req) {

		Channel ChannelObj = new Channel();
		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			Query query = session
					.createQuery("from Channel where status=1 and channelCode=:channelCode");
			query.setParameter("channelCode", ChannelCode);
			ArrayList<Channel> list = new ArrayList<Channel>();
			list = (ArrayList<Channel>) query.list();

			Channel ChannelClassObj = new Channel();
			ChannelClassObj = list.get(0);

			ChannelClassObj.setChannelCode(ChannelCode);
			ChannelClassObj.setStatus(false);
			User userObj = new User();
			userObj = (User) req.getSession().getAttribute("currUserObj");

			ChannelClassObj.setModifiedBy(userObj.getStaffLoginId());
			ChannelClassObj.setModificationDate(new Date());
			session.update(ChannelClassObj);
			AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
			auditTrailMaintenance.insertAuditTrail(new AuditTrail(userObj
					.getUser_no() + "", AuditTrail.MODULE_CHANNEL,
					AuditTrail.FUNCTION_DELETE, ChannelClassObj.toString()));
			tx.commit();
			DeleteRelatedUSER(ChannelCode, req);
			session.flush();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
		}
		MsgObject msgObj = new MsgObject(
				"The  Channel has been successfully Deleted.");
		req.setAttribute("messageObject", msgObj);
		req.setAttribute("pager", getAllChannelListing());
		req.getSession().setAttribute("pager", getAllChannelListing());

	}

	/**
	 * <p>
	 * This is particular search listing method based of search criteria.
	 * </p>
	 * 
	 * @param  requestParamters Servlet Request Parameter
	 * @return Pager
	 */
	public Pager ChannelSearchListing(HttpServletRequest requestParamters) {

		LinkedList item = new LinkedList();
		Channel ChannelClassObj = null;
		ArrayList vecAllRes = new ArrayList();

		vecAllRes = getSearchChannel(requestParamters);
		if (vecAllRes.size() != 0) {
			for (int i = 0; i < vecAllRes.size(); i++) {
				ChannelClassObj = new Channel();
				ChannelClassObj = (Channel) vecAllRes.get(i);
				item.add(ChannelClassObj.getGetChannelListingTableRow(i));
			}
		}
		Pager pager = new Pager();
		pager.setActualSize(item.size());
		pager.setCurrentPageNumber(0);
		pager.setMaxIndexPages(10);
		pager.setMaxPageItems(10);
		for (; item.size() % 10 != 0; item.add("<tr></tr>"))
			;
		pager.setItems(item);
		return pager;

	}
	
	

	/**
	 * <p>
	 * This is sub method for particular search listing method based of search criteria.
	 * </p>
	 * 
	 * @param  requestParameters Servlet Request Parameter
	 * @return ArrayList
	 */
	private ArrayList getSearchChannel(HttpServletRequest req) {

		log.log(Level.INFO, "ChannelMaintanence --> getSearchChannel");
		Session session = null;
		Transaction tx;
		ArrayList arrActivity = new ArrayList();
		String str = "";
		if (!req.getParameter("name").equals("")) {
			str = str + " and channelName like :name ";
		}

		try {

			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			Query query = session.createQuery(" from Channel where status = 1 "
					+ str + " ");
			if (!req.getParameter("name").equals("")) {
				query.setParameter("name", "%"+req.getParameter("name")+"%");

			}

			arrActivity = (ArrayList) query.list();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		} finally {
			HibernateFactory.close(session);
		}

		return arrActivity;

	}
	
	

	/**
	 * <p>
	 * This method checks all validations & sets values to bean
	 * </p>
	 * 
	 * @param Channel class object
	 * @param requestParameters  Servlet Request Parameter
	 * @return Channel object/Error object
	 */

	public Object mapForm1(Channel channelobj,
			HttpServletRequest requestParameters) {
		LocaleObject localeObj = (LocaleObject)requestParameters.getSession().getAttribute(SessionAttributes.LOCALE_OBJ);
		if (channelobj == null) {
			channelobj = new Channel();
			return channelobj;
		}

		FormObj formObj = (FormObj) requestParameters.getSession()
				.getAttribute("formObj");

		if (requestParameters.getParameterValues("name")[0] == null
				|| requestParameters.getParameterValues("name")[0].length() == 0)
		return new ErrorObject("Channel Name", " field is required",localeObj);
		log.log(Level.INFO, "ChannelMaintenance --> mapForm1 ");
		String name = requestParameters.getParameterValues("name")[0].trim();
		String channelCode = requestParameters
				.getParameterValues("channelCode")[0].trim();
		channelCode = channelCode == null || channelCode.equals("") ? "0"
				: channelCode;
		int count = validatename(name, Integer.parseInt(channelCode));
		if (count != 0) {
			return new ErrorObject("�Channel� Already exists�", "",localeObj);
		}

		Channel ChannelclassObj = new Channel();
		ChannelclassObj.setChannelName(requestParameters.getParameter("name"));
		ChannelclassObj.setStatus(true);
		ChannelclassObj.setToken(LMSUtil.getRendomToken());

		return ChannelclassObj;
	}
	
	
	

	/**
	 * <p>
	 * This method performs validation of duplicate name
	 * </p>
	 * 
	 * @param name
	 * @param ChannelCode
	 * @return integer count value of name available in database.
	 */
	private int validatename(String name, int ChannelCode) {
		int count = 0;
		Session session = null;

		try {
			session = HibernateFactory.openSession();

			Query query = session
					.createQuery("select count(*) from Channel where status=1 and channelName=:channelName and channelCode!=:channelCode");
			query.setParameter("channelName", name);
			query.setParameter("channelCode", ChannelCode);
			count = (Integer) query.uniqueResult();

			session.flush();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
		}

		return count;
	}

	
	/**
	 * <p>
	 * This method performs Delete of User based on deleted Channel
	 * </p>
	 * 
	 * @param  int ChannelCode
	 * @param  requestParameters Servlet Request Parameter
	 * @return 
	 */
	
	private void DeleteRelatedUSER(int ChannelCode, HttpServletRequest req) {
		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();
			
		
			
			Query query1 = session
					.createQuery("from User where status=1");
			ArrayList<User> list1 = new ArrayList<User>();
			list1 = (ArrayList<User>) query1.list();
			if (list1.size() != 0) {

				for (int i = 0; i < list1.size(); i++)
				{
					User UserClassObj = new User();
                    UserClassObj = list1.get(i);
                    String str = UserClassObj.getChannelCode();
                    int user_no = UserClassObj.getUser_no();
                  
                   
                 
                   int user_channalcode;
                   StringTokenizer obj_StringTokenizer = new StringTokenizer(str,"|");
                   int Counttoken = obj_StringTokenizer.countTokens();
            	   String code = null;
                   if(Counttoken!=0) 
                   {
			            	   
			            	  
			            	   
			            	   if(Counttoken > 1)
			            	   {
			            		   while (obj_StringTokenizer.hasMoreElements()) 
			            		   {
			            		   code =  (String) obj_StringTokenizer.nextElement();
			            		   user_channalcode=Integer.parseInt(code);
			            		   
			            		   if(ChannelCode == user_channalcode)
			                        {
			            			   String join ="";
			            			   ArrayList c_code = new ArrayList();
			            			   StringTokenizer obj_StringTokenizer1 = new StringTokenizer(str,"|");
			            			   while(obj_StringTokenizer1.hasMoreTokens()) 
			            			   {
			            				   c_code.add(obj_StringTokenizer1.nextToken());
			            			   }
		                        	   
			            			   for (int i1=0; i1<c_code.size(); i1++) {
			            				   String val = (String) c_code.get(i1);
			            				  
			            				   int match=Integer.parseInt(val);
			            				   if (match == ChannelCode) {
			            					   c_code.remove(i1);
			            				     break;
			            				   }
			            				  } 

			            			     int c_size=c_code.size();
		                        		 
		                        		 for(int j=0;j<c_size;j++)
		                        		 {
		                        		
		                        			join =join+c_code.get(j)+"|";
		                        
		                        		 }
		                        		 String ch_code=join.trim();
		                        		 UserClassObj.setUser_no(UserClassObj.getUser_no());
		              					UserClassObj.setStatus(true);
		              					UserClassObj.setChannelCode(ch_code);

		              					User userObj = (User) req.getSession().getAttribute(
		              							"currUserObj");

		              					UserClassObj.setModifiedBy(userObj.getStaffLoginId());
		              					UserClassObj.setModificationDate(new Date());
		              					session.update(UserClassObj);
		              					AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
		              					auditTrailMaintenance
		              							.insertAuditTrail(new AuditTrail(userObj
		              									.getUser_no() + "",
		              									AuditTrail.MODULE_CHANNEL_USER,
		              									AuditTrail.FUNCTION_DELETE, UserClassObj
		              											.toString()));
		              					tx.commit();

		              					session.flush();
		                        		 
			            			   
			                        }
			            		   }   
			            	   }
			            	   else
			            	   {
			            		   code =  (String) obj_StringTokenizer.nextElement();
			            		   user_channalcode=Integer.parseInt(code);
			            		   if(ChannelCode== user_channalcode)
			                        {
			            			   UserClassObj.setUser_no(UserClassObj.getUser_no());
		             				   UserClassObj.setStatus(false);

		             					User userObj = (User) req.getSession().getAttribute(
		             							"currUserObj");

		             					UserClassObj.setModifiedBy(userObj.getStaffLoginId());
		             					UserClassObj.setModificationDate(new Date());
		             					session.update(UserClassObj);
		             					AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
		             					auditTrailMaintenance
		             							.insertAuditTrail(new AuditTrail(userObj
		             									.getUser_no() + "",
		             									AuditTrail.MODULE_CHANNEL_USER,
		             									AuditTrail.FUNCTION_DELETE, UserClassObj
		             											.toString()));
		             					tx.commit();

		             					session.flush();
			            	   }
                   }
                   
                   
                   }
				}
			}
		}
		

		 catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
		}

	
	
			}
	
	
	
	public static final String Name = "name";

}
