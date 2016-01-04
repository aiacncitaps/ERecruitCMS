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
* Date                       Developer          Change Description
* 5-January-2015       khyati                  Added Copy rights
* 20-Aug-2015          Nibedita               Email Notification to eop/interview registered candidates
* 25-Aug-2015          Nibedita               unnecessary code removed
***************************************************************************** */


package com.quix.aia.cn.imo.utilities;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.druid.sql.visitor.functions.Ltrim;
import com.quix.aia.cn.imo.data.addressbook.AddressBook;
import com.quix.aia.cn.imo.data.common.AamData;
import com.quix.aia.cn.imo.data.event.Event;
import com.quix.aia.cn.imo.data.event.EventCandidate;
import com.quix.aia.cn.imo.data.event.EventMaterial;
import com.quix.aia.cn.imo.data.interview.Interview;
import com.quix.aia.cn.imo.data.interview.InterviewCandidate;
import com.quix.aia.cn.imo.data.interview.InterviewMaterial;
import com.quix.aia.cn.imo.data.user.User;
import com.quix.aia.cn.imo.mapper.AamDataMaintenance;
import com.quix.aia.cn.imo.mapper.AddressBookMaintenance;
import com.quix.aia.cn.imo.mapper.EopMaintenance;
import com.quix.aia.cn.imo.mapper.InterviewMaintenance;
import com.quix.aia.cn.imo.mapper.LogsMaintenance;
import com.quix.aia.cn.imo.mapper.PropertiesMaintenance;


public class EmailNotification {

    
	private static Logger log = Logger.getLogger(EmailNotification.class.getName());
    
	private static java.util.ResourceBundle contextProps;
	private static Map<String, String> mailProps;
	private static String HOST = null;
	private static String FROM = null;
	private static String USERNAME = null;
	private static String PASSWORD = null;
	private static String AUTH = null;
	private static String DEBUG = null;
	private static String PORT = null;
	private static String isSSL = null;
	private static String STRTTL = null;
	
	
	/*static
	{
		try
		{
//			contextProps = java.util.ResourceBundle.getBundle("mail");
			mailProps = PropertiesMaintenance.fetchMailProperties();
			HOST = mailProps.get("host");
			FROM = mailProps.get("from");
			USERNAME = mailProps.get("username");
			PASSWORD = mailProps.get("password");
			AUTH = mailProps.get("auth");
			DEBUG = mailProps.get("debug");
			PORT= mailProps.get("port");
			
			isSSL= mailProps.get("isSSL");
			STRTTL= mailProps.get("starttls");
            log.log(Level.INFO, "E Mail Properties Loaded"); 
		}
		catch(Exception  e)
		{
		//	log.error(e.getMessage());
			log.log(Level.SEVERE, e.getMessage()); 
			e.printStackTrace();
			LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("EmailNotification",Level.SEVERE+"",errors.toString());
		}
	}*/
	    
	
	static
	{
		try
		{
			contextProps = java.util.ResourceBundle.getBundle("mail");
			HOST = contextProps.getString("host");
			FROM = contextProps.getString("from");
			USERNAME = contextProps.getString("username");
			PASSWORD = contextProps.getString("password");
			AUTH = contextProps.getString("auth");
			DEBUG = contextProps.getString("debug");
			PORT= contextProps.getString("port");
			
			isSSL= contextProps.getString("isSSL");
			STRTTL= contextProps.getString("starttls");
            log.log(Level.INFO, "E Mail Properties Loaded"); 
		}
		catch(Exception  e)
		{
		//	log.error(e.getMessage());
			log.log(Level.SEVERE, e.getMessage()); 
			e.printStackTrace();
		}
	}
	    public EmailNotification()
	    {
	    }
	  
	  
	 public static Session getProps()
	    {
	    	 Properties props = System.getProperties();
	         
	         props.put("mail.smtp.host", HOST);
	         props.put("mail.smtp.port", PORT);
	         props.put("mail.smtp.user", USERNAME);
	         props.put("mail.smtp.password", PASSWORD);
	         
	         
	         if(AUTH != null && AUTH.trim().equalsIgnoreCase("true"))
	        	props.put("mail.smtp.auth","true");
	         else
	        	props.put("mail.smtp.auth","false");
		        
	         if(DEBUG != null && DEBUG.trim().equalsIgnoreCase("true"))
	        	props.put("mail.debug", "true");
	        
	         if(STRTTL != null && STRTTL.trim().equalsIgnoreCase("true"))
	        	props.put("mail.smtp.starttls.enable","true");
	        
	         if(isSSL != null && isSSL.trim().equalsIgnoreCase("true"))
	        	props.put("mail.smtp.EnableSSL.enable","true");
	         
	         Session session = null;
	         if(USERNAME != null && USERNAME.trim().length() > 0)
	        	 session = Session.getInstance(props, new MyPasswordAuthenticator(USERNAME, PASSWORD));
	         else
	        	 session = Session.getDefaultInstance(props, null);

	         return session;
	    }

	 
	public static void sendPasswordNotification(HttpServletRequest req, User user,String str) {
		// TODO Auto-generated method stub
		log.log(Level.INFO, "EmailNotification sendPasswordNotification"); 
		String emailMsg ="";
		
		try{
			String toMailId="";
			User userObj=(User)req.getSession().getAttribute("currUserObj");
				if(str.equals("Modify")){
					emailMsg = "Reset Password has been successfully \n User Name :- "+user.getStaffLoginId()+" is  \n  Password :-  "+req.getAttribute("psw")+"";
					toMailId=user.getEmail();
				}else{
					emailMsg = " User has been created Successfully \n User Name :- "+user.getStaffLoginId()+"   \n Password :-  "+req.getAttribute("psw")+"";
					toMailId=userObj.getEmail();
				}
			   
			 	Session session = null;
	            session = getProps();
	            MimeMessage msg = new MimeMessage(session);
		          
	            msg.setFrom(new InternetAddress(FROM));
	            msg.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(toMailId, false));
	            msg.setHeader("Content-Type", "text/plain; charset=UTF-8");
	            if(str.equals("Modify")){
	            	msg.setSubject("Reset Password");
	            }else{
	            	msg.setSubject("New User Create");
	            }
	            msg.setText(emailMsg,"UTF-8");
	            if(str.equals("Modify")){
	            	if(user.getEmail().length() > 0  )
	    	            Transport.send(msg);
	            }else{
	            	if(userObj.getEmail().length() > 0  )
	    	            Transport.send(msg);
	            }
	          
	            log.log(Level.INFO, "sending succesfull"); 
		          
	           
	            
	            
		}
        catch(Exception e)
        {
        	log.log(Level.SEVERE, e.getMessage()); 
       
            e.printStackTrace();
            LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("EmailNotification",Level.SEVERE+"",errors.toString());
        }
		
	}
	//goal settings detail send  to leader
	public static void sendToLeaderNotification(HttpServletRequest req, User user) {
		// TODO Auto-generated method stub
		log.log(Level.INFO, "EmailNotification goal settings of agent "); 
		String emailMsg ="";
		
		try{
			String toMailId="";
			User userObj=(User)req.getSession().getAttribute("currUserObj");
					emailMsg = "";//"Reset Password  of  User Name :- "+user.getStaffLoginId()+" is  \n  Passwored :-  "+req.getAttribute("psw")+"";
					toMailId=userObj.getEmail();
			   
			 	Session session = null;
	            session = getProps();
	            Message msg = new MimeMessage(session);
		          
	            msg.setFrom(new InternetAddress(FROM));
	            msg.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(toMailId, false));
	            msg.setText(emailMsg);
	    	    Transport.send(msg);
	          
	            log.log(Level.INFO, "sending succesfull"); 
		}
        catch(Exception e)
        {
        	log.log(Level.SEVERE, e.getMessage()); 
            e.printStackTrace();
            LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("EmailNotification",Level.SEVERE+"",errors.toString());
        }
		
	}
	//APROVE/REJECT NOTIFICATION TO AGENTS
	public static void sendToAgentNotification(HttpServletRequest req, User user) {
		// TODO Auto-generated method stub
		log.log(Level.INFO, "EmailNotification goal settings of agent "); 
		String emailMsg ="";
		
		try{
			String toMailId="";
			User userObj=(User)req.getSession().getAttribute("currUserObj");
					emailMsg = "";//"Reset Password  of  User Name :- "+user.getStaffLoginId()+" is  \n  Passwored :-  "+req.getAttribute("psw")+"";
					toMailId=userObj.getEmail();
			   
			 	Session session = null;
	            session = getProps();
	            Message msg = new MimeMessage(session);
		          
	            msg.setFrom(new InternetAddress(FROM));
	            msg.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(toMailId, false));
	            msg.setText(emailMsg);
	    	    Transport.send(msg);
	          
	            log.log(Level.INFO, "sending succesfull"); 
		}
        catch(Exception e)
        {
        	log.log(Level.SEVERE, e.getMessage()); 
            e.printStackTrace();
            LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("EmailNotification",Level.SEVERE+"",errors.toString());
        }
		
	}

	public static void sendEopRegEmailNotification(EventCandidate candidate,String emailId, AamData aamData) {
		log.log(Level.INFO, "Eop Registration Notification "); 
		String emailMsg ="";
		try{
			EopMaintenance eopMain = new EopMaintenance();
			AddressBookMaintenance addressBookMaintenance = new AddressBookMaintenance();
			AddressBook addressBook = new AddressBook();
			addressBook.setAddressCode(Integer.parseInt(candidate.getEventCandidateCode()));
			addressBook = addressBookMaintenance.getAddressBook(addressBook);
			Event event = eopMain.getEvent(candidate.getEventCode());
			EventMaterial profileMaterial=eopMain.getEventMaterial(candidate.getEventCode(), "Profile");
			EventMaterial topicMaterial=eopMain.getEventMaterial(candidate.getEventCode(),null);
			String eventDate = event.getEventDate()!=null ? LMSUtil.converDateIntoDD_MMM_YYYY(event.getEventDate()).replaceAll("-", " ") : "";
			String startTime = event.getStartTime()!=null?LMSUtil.converDateIntoHHMMAMPM(event.getStartTime()):"";
			//String endTime = event.getEndTime()!=null?LMSUtil.converDateIntoHHMMAMPM(event.getEndTime()):"";
			String pFileName = "",tFileName="";
			
			if(event.getProfilePath()!=null && event.getProfilePath().length() > 0){
				pFileName = event.getProfilePath().substring(event.getProfilePath().lastIndexOf('/') + 1);
			 }
			if(event.getTopicPath()!=null && event.getTopicPath().length() > 0){
				tFileName = event.getTopicPath().substring(event.getTopicPath().lastIndexOf('/') + 1);
			 }
			
			String gender = "";
			if("F".equalsIgnoreCase(candidate.getGender().trim()))
				gender = "小姐";
			else
				gender="先生";
			
			String genderAgent = "";
			if("F".equalsIgnoreCase(aamData.getAgentSex().trim()))
				genderAgent = "小姐";
			else
				genderAgent="先生";
			 	Session session = null;
	            session = getProps();
	            MimeMessage msg = new MimeMessage(session);
		          
	            msg.setFrom(new InternetAddress(FROM));
	        
	            msg.setSubject("EOP 注册成功","UTF-8");
	            emailMsg ="尊敬的  " +candidate.getCandidateName() + gender+","+"\n\n"+
    					"恭喜您报名成功我们的精彩活动，不见不散哦！\n\n"+
    					"活动名称："+event.getEventName()+"\n"+
    					"活动日期："+eventDate+"\n"+
    					"活动开始时间："+startTime+"\n"+
    					//"结束 时间:"+endTime+"\n"+
    					"主讲人：" +event.getSpeaker()+"\n"+
    					"地点：" + event.getLocation()+"\n"+
    					"描述：" + event.getEopDescription()+"\n"+
    					"附件："+ pFileName + ", " + tFileName +"\n\n"+
//    					"报名的 : \n\n\n"+myString.replaceAll("^\\s+|\\s+$", "")
    					"此为系统邮件，请勿直接回复。若有需要请联系您的营销员 "+aamData.getAgentName().replaceAll("^\\s+|\\s+$", "")+" "+genderAgent +", "+ aamData.getTel().trim() + " \n\n"+
						"祝您：身体健康 万事如意"+"\n\n"
						+ "AIA CHINA" ; 
	            log.log(Level.INFO, "Mail Text : "+emailMsg);

				// create the message part 
			    MimeBodyPart messageBodyPart =  new MimeBodyPart();
			    messageBodyPart.setText(emailMsg,"UTF-8");
			    
			    Multipart multipart = new MimeMultipart();
			    multipart.addBodyPart(messageBodyPart);
			    
			    if(addressBook.getQrCode()!=null && addressBook.getQrCode().length > 0){
					DataSource source = new  ByteArrayDataSource(addressBook.getQrCode(),"application/octet-stream");
					MimeBodyPart attachmentPart = new MimeBodyPart();
					attachmentPart.setDataHandler(new DataHandler(source));
					attachmentPart.setFileName("QR_CODE_IMAGE.jpeg");
					multipart.addBodyPart(attachmentPart);
			    }
			    if(profileMaterial!=null &&  profileMaterial.getMaterial().length > 0){
					DataSource source = new  ByteArrayDataSource(profileMaterial.getMaterial(),"application/octet-stream");
					MimeBodyPart attachmentPart = new MimeBodyPart();
					attachmentPart.setDataHandler(new DataHandler(source));
					attachmentPart.setFileName(pFileName);
					multipart.addBodyPart(attachmentPart);
			    }
			    if(topicMaterial!=null &&  topicMaterial.getMaterial().length > 0){
					DataSource source = new  ByteArrayDataSource(topicMaterial.getMaterial(),"application/octet-stream");
					MimeBodyPart attachmentPart = new MimeBodyPart();
					attachmentPart.setDataHandler(new DataHandler(source));
					attachmentPart.setFileName(tFileName);
					multipart.addBodyPart(attachmentPart);
			    }
	            msg.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse("jay.gagnani@quix.com.sg", false));
	            msg.setContent(multipart);
	    	    Transport.send(msg);
	          
	            log.log(Level.INFO, "sending succesfull"); 
		}
        catch(Exception e)
        {
        	log.log(Level.SEVERE, e.getMessage()); 
            e.printStackTrace();
            LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("EmailNotification",Level.SEVERE+"",errors.toString());
        }
		
	}

	public static void sendInterviewRegEmailNotification(InterviewCandidate candidate,String emailId,AamData aamData) {
		log.log(Level.INFO, "Interview Registration Notification "); 
		String emailMsg ="";
		
		try{
			InterviewMaintenance interviewMain = new InterviewMaintenance();
			
			
			AddressBookMaintenance addressBookMaintenance = new AddressBookMaintenance();
			AddressBook addressBook = new AddressBook();
			addressBook.setAddressCode(Integer.parseInt(candidate.getInterviewCandidateCode()));
			addressBook = addressBookMaintenance.getAddressBook(addressBook);
			
			
			Interview interview = interviewMain.getInterviewBasedOnInterviewCode(candidate.getInterviewCode());
			InterviewMaterial material=interviewMain.getInterviewMaterial(interview.getInterview_code());
			String interviewDate = interview.getInterviewDate()!=null ? LMSUtil.converDateIntoDD_MMM_YYYY(interview.getInterviewDate()).replaceAll("-", " ") : "";
			String startTime = interview.getStartTime()!=null?LMSUtil.converDateIntoHHMMAMPM(interview.getStartTime()):"";
			//String endTime = interview.getEndTime()!=null?LMSUtil.converDateIntoHHMMAMPM(interview.getEndTime()):"";
			String fname = ""; 
			 if(interview.getAttachmentPath()!=null && interview.getAttachmentPath().length() > 0){
				 fname = interview.getAttachmentPath().substring(interview.getAttachmentPath().lastIndexOf('/') + 1);
			 }
			String gender = "";
			if("F".equalsIgnoreCase(candidate.getGender().trim()))
				gender = "小姐";
			else
				gender="先生";
			
			String genderAgent = "";
			if("F".equalsIgnoreCase(aamData.getAgentSex().trim()))
				genderAgent = "小姐";
			else
				genderAgent="先生";
			 	Session session = null;
	            session = getProps();
	            MimeMessage msg = new MimeMessage(session);
		          
	            msg.setFrom(new InternetAddress(FROM));
	         
	            msg.setSubject("面试注册成功","UTF-8");
	            emailMsg ="尊敬的 " +candidate.getCandidateName() + " "+ gender+","+"\n\n"+
	            					"恭喜您报名成功我们的公司面试，不见不散哦！\n\n"+
	            					"面试名称："+interview.getInterviewSessionName()+"\n"+
	            					"面试日期："+interviewDate+"\n"+
	            					"活动开始时间："+startTime+"\n"+
	            					//"结束 时间:"+endTime+"\n"+
	            					"地点：" + interview.getLocation()+"\n"+
	            					"面试资料："+interview.getInterviewType()+"\n"+
	            					"附件："+ fname+"\n\n"+
	            					"此为系统邮件，请勿直接回复。若有需要请联系您的营销员 " +aamData.getAgentName().replaceAll("^\\s+|\\s+$", "")+""+genderAgent+", "+ aamData.getTel().trim() + " \n\n"+
	        						"祝您：身体健康 万事如意"+"\n\n"
	        						+ "AIA CHINA" ;
	        	            log.log(Level.INFO, "Mail Text : "+emailMsg);

	        				// create the message part 
	        			    MimeBodyPart messageBodyPart =  new MimeBodyPart();
	        			    messageBodyPart.setText(emailMsg,"UTF-8");
	        			    
	        			    Multipart multipart = new MimeMultipart();
	        			    multipart.addBodyPart(messageBodyPart);
	        			  if(addressBook.getQrCode()!=null && addressBook.getQrCode().length > 0){
	        				DataSource source = new  ByteArrayDataSource(addressBook.getQrCode(),"application/octet-stream");
	        				MimeBodyPart attachmentPart = new MimeBodyPart();
	        				attachmentPart.setDataHandler(new DataHandler(source));
	        				attachmentPart.setFileName("QR_CODE_IMAGE.jpeg");
	        				multipart.addBodyPart(attachmentPart);
	        			  }	
	        			  
	        		if(null != material){
	        				
	        			  if(null !=material.getMaterial() && material.getMaterial().length > 0){
		        				DataSource source = new  ByteArrayDataSource(material.getMaterial(),"application/octet-stream");
		        				MimeBodyPart attachmentPart = new MimeBodyPart();
		        				attachmentPart.setDataHandler(new DataHandler(source));
		        				attachmentPart.setFileName(fname);
		        				multipart.addBodyPart(attachmentPart);
		        			  }	
	        			  }
	            msg.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(emailId, false));
                msg.setContent(multipart);

	    	    Transport.send(msg);
	          
	            log.log(Level.INFO, "sending succesfull"); 
		}
        catch(Exception e)
        {
        	log.log(Level.SEVERE, e.getMessage()); 
            e.printStackTrace();
            LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("EmailNotification",Level.SEVERE+"",errors.toString());
        }
		
	}
	public static void sendEopUpdateEmailNotification(Event event,HttpServletRequest requestParameters) {
		log.log(Level.INFO, "Updates to EOP Notification "); 
		String emailMsg ="";
		User userObj = (User)requestParameters.getSession().getAttribute("currUserObj");
		try{
			EopMaintenance eopMaintenance = new EopMaintenance();
			String eventDate = event.getEventDate()!=null ? LMSUtil.converDateIntoDD_MMM_YYYY(event.getEventDate()).replaceAll("-", " ") : "";
			String startTime = event.getStartTime()!=null?LMSUtil.converDateIntoHHMMAMPM(event.getStartTime()):"";
			//String endTime = event.getEndTime()!=null?LMSUtil.converDateIntoHHMMAMPM(event.getEndTime()):"";
			
			String pFileName = "",tFileName="";
			if(event.getProfilePath()!=null && event.getProfilePath().length() > 0){
				pFileName = event.getProfilePath().substring(event.getProfilePath().lastIndexOf('/') + 1);
			 }
			if(event.getTopicPath()!=null && event.getTopicPath().length() > 0){
				tFileName = event.getTopicPath().substring(event.getTopicPath().lastIndexOf('/') + 1);
			 }
			List<Object[]> mailIds = eopMaintenance.getRegisteredEmailAddressForParticularEvent(event.getEvent_code());
			 String candidateEmail = "";
			 String candidateName = "";
			 String candidateGender = "";
			 String candidateAgentId = "";
			 String candidateCoBranch = "";
			 Integer addressCode = 0;
			 AamData aamData = null;
			 AamDataMaintenance aamDataMaintenance = new AamDataMaintenance();
			 byte[] qrCode = null;
			 if(mailIds!=null && mailIds.size()>0){
		        for(Object[] arrayObjs : mailIds){    
		            candidateEmail = (String) arrayObjs[0];
		            addressCode = (Integer) arrayObjs[1];
		            candidateAgentId = (String) arrayObjs[2]; 
		            candidateName = (String) arrayObjs[3];
		            candidateGender = (String) arrayObjs[4];
		            qrCode = (byte[]) arrayObjs[5];
		            candidateCoBranch = (String) arrayObjs[6];
		            
		            aamData = aamDataMaintenance.retrieveDataToModel(candidateAgentId, candidateCoBranch);
		            
		            String gender = "";
		            if(null != candidateGender){
		            	candidateGender.trim();
		            }
					if("F".equalsIgnoreCase(candidateGender.trim()))
						gender = "小姐";
					else
						gender="先生";
					
					String genderAgent = "";
					if("F".equalsIgnoreCase(aamData.getAgentSex().trim()))
						genderAgent = "小姐";
					else
						genderAgent="先生";
					
				 	Session session = null;
		            session = getProps();
		            MimeMessage msg = new MimeMessage(session);
			          
		            msg.setFrom(new InternetAddress(FROM));
		            msg.setSubject("EOP更新","UTF-8");
									
		            emailMsg ="尊敬的  " +candidateName + gender+","+"\n\n"+
		            		"您报名的活动进行了更新，请重新查看活动信息，若有需要请联系您的营销员  "+aamData.getAgentName().replaceAll("^\\s+|\\s+$", "")+" "+genderAgent +", "+ aamData.getTel().trim() +","+"\n\n"+
	    					"活动名称："+event.getEventName()+"\n"+
	    					"活动日期："+eventDate+"\n"+
	    					"活动开始时间："+startTime+"\n"+
	    					//"结束 时间:"+endTime+"\n"+
	    					"主讲人：" +event.getSpeaker()+"\n"+
	    					"地点：" + event.getLocation()+"\n"+
	    					"描述：" + event.getEopDescription()+"\n"+
	    					"附件："+ pFileName + ", " + tFileName +"\n\n"+
//	    					"报名的 : \n\n\n"+myString.replaceAll("^\\s+|\\s+$", "")
	    					"此为系统邮件，请勿直接回复。 \n\n"+
							"祝您：身体健康 万事如意"+"\n\n"
							+ "AIA CHINA" ;
		            
		            
		            ArrayList<EventMaterial> list=new ArrayList<EventMaterial>();
		            
		            list=eopMaintenance.getMaterialForMail(event.getEvent_code());
		            MimeBodyPart messageBodyPart =  new MimeBodyPart();
				    messageBodyPart.setText(emailMsg,"utf-8");
				    
				    Multipart multipart = new MimeMultipart();
				    multipart.addBodyPart(messageBodyPart);
		            if(list!=null){
		            	for (EventMaterial material:list ) {
						
		 				DataSource source = new  ByteArrayDataSource(material.getMaterial(),"application/octet-stream");
		 				MimeBodyPart attachmentPart = new MimeBodyPart();
		 				attachmentPart.setDataHandler(new DataHandler(source));
		 				attachmentPart.setFileName(material.getMaterialName());
		 				multipart.addBodyPart(attachmentPart);
		            	}
		            }
		            if(qrCode!=null && qrCode.length > 0){
						DataSource source = new  ByteArrayDataSource(qrCode,"application/octet-stream");
						MimeBodyPart attachmentPart = new MimeBodyPart();
						attachmentPart.setDataHandler(new DataHandler(source));
						attachmentPart.setFileName("QR_CODE_IMAGE.jpeg");
						multipart.addBodyPart(attachmentPart);
				    }
				    
		            msg.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse("jay.gagnani@quix.com.sg", false));
		            msg.setContent(multipart);
		    	    Transport.send(msg);
		        }
		          
	            log.log(Level.INFO, "sending succesfull"); 
			 }else{
		
				  log.log(Level.INFO, "No Recipient Email Address Found"); 
			 }
		}
        catch(Exception e)
        {
        	log.log(Level.SEVERE, e.getMessage()); 
            e.printStackTrace();
            LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("EmailNotification",Level.SEVERE+"",errors.toString());
        }
		
	}
	public static void sendInterviewUpdateEmailNotification(Interview interview,HttpServletRequest requestParameters) {
		log.log(Level.INFO, "Interview Updates Notification "); 
		String emailMsg ="";
		User userObj = (User)requestParameters.getSession().getAttribute("currUserObj");
		try{
			
			InterviewMaintenance interviewMain = new InterviewMaintenance();
			String interviewDate = interview.getInterviewDate()!=null ? LMSUtil.converDateIntoDD_MMM_YYYY(interview.getInterviewDate()).replaceAll("-", " ") : "";
			String startTime = interview.getStartTime()!=null?LMSUtil.converDateIntoHHMMAMPM(interview.getStartTime()):"";
			String endTime = interview.getEndTime()!=null?LMSUtil.converDateIntoHHMMAMPM(interview.getEndTime()):"";
			
            InterviewMaterial material=interviewMain.getInterviewMaterial(interview.getInterview_code());

			String fname = ""; 
			 if(interview.getAttachmentPath()!=null && interview.getAttachmentPath().length() > 0){
				 fname = interview.getAttachmentPath().substring(interview.getAttachmentPath().lastIndexOf('/') + 1);
			 }
			 List<Object[]> mailIds = interviewMain.getRegisteredEmailAddressForParticularInterview(interview.getInterview_code());String candidateEmail = "";
			 String candidateName = "";
			 String candidateGender = "";
			 String candidateAgentId = "";
			 String candidateCoBranch = "";
			 Integer addressCode = 0;
			 AamData aamData = null;
			 AamDataMaintenance aamDataMaintenance = new AamDataMaintenance();
			 byte[] qrCode = null;
			 if(mailIds!=null && mailIds.size()>0){
		        for(Object[] arrayObjs : mailIds){    
		            candidateEmail = (String) arrayObjs[0];
		            addressCode = (Integer) arrayObjs[1];
		            candidateAgentId = (String) arrayObjs[2]; 
		            candidateName = (String) arrayObjs[3];
		            candidateGender = (String) arrayObjs[4];
		            qrCode = (byte[]) arrayObjs[5];
		            candidateCoBranch = (String) arrayObjs[6];
		            
		            aamData = aamDataMaintenance.retrieveDataToModel(candidateAgentId, candidateCoBranch);
		            
		            String gender = "";
		            if(null != candidateGender){
		            	candidateGender.trim();
		            }
					if("F".equalsIgnoreCase(candidateGender.trim()))
						gender = "小姐";
					else
						gender="先生";
					
					String genderAgent = "";
					if("F".equalsIgnoreCase(aamData.getAgentSex().trim()))
						genderAgent = "小姐";
					else
						genderAgent="先生";
		            
				 	Session session = null;
		            session = getProps();
		            MimeMessage msg = new MimeMessage(session);
			          
		            msg.setFrom(new InternetAddress(FROM));
		            
		            msg.setSubject("面试更新","UTF-8");
		            emailMsg ="尊敬的 " +candidateName + " "+ gender+","+"\n\n"+
		            		"您报名的活动进行了更新，请重新查看活动信息，若有需要请联系您的营销员  "+aamData.getAgentName().replaceAll("^\\s+|\\s+$", "")+" "+genderAgent +", "+ aamData.getTel().trim() +","+"\n\n"+
		            					"面试名称："+interview.getInterviewSessionName()+"\n"+
		            					"面试日期："+interviewDate+"\n"+
		            					"活动开始时间："+startTime+"\n"+
		            					"地点：" + interview.getLocation()+"\n"+
		            					"面试资料："+interview.getInterviewType()+"\n"+
		            					"附件："+ fname+"\n\n"+
		            					"此为系统邮件，请勿直接回复。 \n\n"+
		    							"祝您：身体健康 万事如意"+"\n\n"
		    							+ "AIA CHINA" ;
		            
		            
		            MimeBodyPart messageBodyPart =  new MimeBodyPart();
				    messageBodyPart.setText(emailMsg,"UTF-8");
				    
				    Multipart multipart = new MimeMultipart();
				    multipart.addBodyPart(messageBodyPart);
				    if(qrCode!=null && qrCode.length > 0){
						DataSource source = new  ByteArrayDataSource(qrCode,"application/octet-stream");
						MimeBodyPart attachmentPart = new MimeBodyPart();
						attachmentPart.setDataHandler(new DataHandler(source));
						attachmentPart.setFileName("QR_CODE_IMAGE.jpeg");
						multipart.addBodyPart(attachmentPart);
				    }
				   
				    if(material !=null && material.getMaterial().length > 0){
	    				DataSource source = new  ByteArrayDataSource(material.getMaterial(),"application/octet-stream");
	    				MimeBodyPart attachmentPart = new MimeBodyPart();
	    				attachmentPart.setDataHandler(new DataHandler(source));
	    				attachmentPart.setFileName(fname);
	    				multipart.addBodyPart(attachmentPart);
	    			  }	
		            msg.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(candidateEmail, false));
		            msg.setContent(multipart);
		    	    Transport.send(msg);
	    	    
		        }
	          
	            log.log(Level.INFO, "sending succesfull"); 
			 }else{
				 
				  log.log(Level.INFO, "No Recipient Email Address Found"); 
			 }
		}
        catch(Exception e)
        {
        	log.log(Level.SEVERE, e.getMessage()); 
            e.printStackTrace();
            LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("EmailNotification",Level.SEVERE+"",errors.toString());
        }
		
	}
	public static void sendEopDeleteEmailNotification(Event event,HttpServletRequest requestParameters) {
		log.log(Level.INFO, "Delete EOP Notification "); 
		String emailMsg ="";
		User userObj = (User)requestParameters.getSession().getAttribute("currUserObj");
		try{
			EopMaintenance eopMaintenance = new EopMaintenance();
			String eventDate = event.getEventDate()!=null ? LMSUtil.converDateIntoDD_MMM_YYYY(event.getEventDate()).replaceAll("-", " ") : "";
			String startTime = event.getStartTime()!=null?LMSUtil.converDateIntoHHMMAMPM(event.getStartTime()):"";
			String endTime = event.getEndTime()!=null?LMSUtil.converDateIntoHHMMAMPM(event.getEndTime()):"";
			
			EventCandidate candidate=eopMaintenance.getEopCandidateCode(event.getEvent_code());
			AddressBookMaintenance addressBookMaintenance = new AddressBookMaintenance();
			AddressBook addressBook = new AddressBook();
			addressBook.setAddressCode(Integer.parseInt(candidate.getEventCandidateCode()));
			addressBook = addressBookMaintenance.getAddressBook(addressBook);
			
			String pFileName = "",tFileName="";
			if(event.getProfilePath()!=null && event.getProfilePath().length() > 0){
				pFileName = event.getProfilePath().substring(event.getProfilePath().lastIndexOf('/') + 1);
			 }
			if(event.getTopicPath()!=null && event.getTopicPath().length() > 0){
				tFileName = event.getTopicPath().substring(event.getTopicPath().lastIndexOf('/') + 1);
			 }
			
			List<Object[]> mailIds = eopMaintenance.getRegisteredEmailAddressForParticularEvent(event.getEvent_code());
			 String candidateEmail = "";
			 String candidateName = "";
			 String candidateGender = "";
			 String candidateAgentId = "";
			 String candidateCoBranch = "";
			 Integer addressCode = 0;
			 AamData aamData = null;
			 AamDataMaintenance aamDataMaintenance = new AamDataMaintenance();
			 byte[] qrCode = null;
			 if(mailIds!=null && mailIds.size()>0){
		        for(Object[] arrayObjs : mailIds){    
		            candidateEmail = (String) arrayObjs[0];
		            addressCode = (Integer) arrayObjs[1];
		            candidateAgentId = (String) arrayObjs[2]; 
		            candidateName = (String) arrayObjs[3];
		            candidateGender = (String) arrayObjs[4];
		            qrCode = (byte[]) arrayObjs[5];
		            candidateCoBranch = (String) arrayObjs[6];
		            
		            aamData = aamDataMaintenance.retrieveDataToModel(candidateAgentId, candidateCoBranch);
		            
		            String gender = "";
		            if(null != candidateGender){
		            	candidateGender.trim();
		            }
					if("F".equalsIgnoreCase(candidateGender.trim()))
						gender = "小姐";
					else
						gender="先生";
					
					String genderAgent = "";
					if("F".equalsIgnoreCase(aamData.getAgentSex().trim()))
						genderAgent = "小姐";
					else
						genderAgent="先生";
					
				 	Session session = null;
		            session = getProps();
		            MimeMessage msg = new MimeMessage(session);
			          
		            msg.setFrom(new InternetAddress(FROM));
		            msg.setSubject("EOP 取消","UTF-8");
		            
		            emailMsg ="尊敬的 " +candidate.getCandidateName()+" "+ gender+","+"\n\n"+
		            		"您报名的活动已经取消，给您造成不便敬请谅解，若有需求请联系您的营销员 " +aamData.getAgentName().replaceAll("^\\s+|\\s+$", "")+" "+genderAgent +", "+ aamData.getTel().trim() +","+"\n\n"+
	    					"活动名称："+event.getEventName()+"\n"+
	    					"活动日期："+eventDate+"\n"+
	    					"活动开始时间："+startTime+"\n"+
	    					//"结束 时间:"+endTime+"\n"+
	    					"主讲人：" +event.getSpeaker()+"\n"+
	    					"地点：" + event.getLocation()+"\n"+
	    					"描述：" + event.getEopDescription()+"\n"+
	    					"附件："+ pFileName + "," + tFileName +"\n\n"+
//	    					"报名的 : \n\n\n"+
	    					//"此为系统邮件，请勿直接回复。若有需要请联系您的营销员 "+  aamData.getAgentName()+ "\n\n"+
	    					"此为系统邮件，请勿直接回复。 \n\n"+
							"祝您：身体健康 万事如意"+"\n\n"	
							+ "AIA CHINA" ;	            
		            
		            ArrayList<EventMaterial> list=new ArrayList<EventMaterial>();
		            
		            list=eopMaintenance.getMaterialForMail(event.getEvent_code());
		            MimeBodyPart messageBodyPart =  new MimeBodyPart();
				    messageBodyPart.setText(emailMsg,"utf-8");
				    
				    Multipart multipart = new MimeMultipart();
				    multipart.addBodyPart(messageBodyPart);
		            if(list!=null){
		            	for (EventMaterial material:list ) {
						
		 				DataSource source = new  ByteArrayDataSource(material.getMaterial(),"application/octet-stream");
		 				MimeBodyPart attachmentPart = new MimeBodyPart();
		 				attachmentPart.setDataHandler(new DataHandler(source));
		 				attachmentPart.setFileName(material.getMaterialName());
		 				multipart.addBodyPart(attachmentPart);
		            	}
		            }
		            if(qrCode!=null && qrCode.length > 0){
						DataSource source = new  ByteArrayDataSource(qrCode,"application/octet-stream");
						MimeBodyPart attachmentPart = new MimeBodyPart();
						attachmentPart.setDataHandler(new DataHandler(source));
						attachmentPart.setFileName("QR_CODE_IMAGE.jpeg");
						multipart.addBodyPart(attachmentPart);
				    }
				    
		            msg.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse("jay.gagnani@quix.com.sg", false));
		            msg.setContent(multipart);
		    	    Transport.send(msg);
		        }
		          
	            log.log(Level.INFO, "sending succesfull"); 
			 }else{
				 
				  log.log(Level.INFO, "No Recipient Email Address Found"); 
			 }
		}
        catch(Exception e)
        {
        	log.log(Level.SEVERE, e.getMessage()); 
            e.printStackTrace();
            LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("EmailNotification",Level.SEVERE+"",errors.toString());
        }
		
	}
	public static void sendInterviewDeleteEmailNotification(Interview interview,HttpServletRequest requestParameters) {
		log.log(Level.INFO, "Interview Delete Notification "); 
		String emailMsg ="";
		 User userObj = (User)requestParameters.getSession().getAttribute("currUserObj");
		try{
			InterviewMaintenance interviewMain = new InterviewMaintenance();
			String interviewDate = interview.getInterviewDate()!=null ? LMSUtil.converDateIntoDD_MMM_YYYY(interview.getInterviewDate()).replaceAll("-", " ") : "";
			String startTime = interview.getStartTime()!=null?LMSUtil.converDateIntoHHMMAMPM(interview.getStartTime()):"";
			String endTime = interview.getEndTime()!=null?LMSUtil.converDateIntoHHMMAMPM(interview.getEndTime()):"";
			
			InterviewMaterial material=interviewMain.getInterviewMaterial(interview.getInterview_code());
			
			String fname = ""; 
			 if(interview.getAttachmentPath()!=null && interview.getAttachmentPath().length() > 0){
				 fname = interview.getAttachmentPath().substring(interview.getAttachmentPath().lastIndexOf('/') + 1);
			 }
			 
			 List<Object[]> mailIds = interviewMain.getRegisteredEmailAddressForParticularInterview(interview.getInterview_code());String candidateEmail = "";
			 String candidateName = "";
			 String candidateGender = "";
			 String candidateAgentId = "";
			 String candidateCoBranch = "";
			 Integer addressCode = 0;
			 AamData aamData = null;
			 AamDataMaintenance aamDataMaintenance = new AamDataMaintenance();
			 byte[] qrCode = null;
			 if(mailIds!=null && mailIds.size()>0){
		        for(Object[] arrayObjs : mailIds){    
		            candidateEmail = (String) arrayObjs[0];
		            addressCode = (Integer) arrayObjs[1];
		            candidateAgentId = (String) arrayObjs[2]; 
		            candidateName = (String) arrayObjs[3];
		            candidateGender = (String) arrayObjs[4];
		            qrCode = (byte[]) arrayObjs[5];
		            candidateCoBranch = (String) arrayObjs[6];
		            
		            aamData = aamDataMaintenance.retrieveDataToModel(candidateAgentId, candidateCoBranch);
		            
		            String gender = "";
		            if(null != candidateGender){
		            	candidateGender.trim();
		            }
					if("F".equalsIgnoreCase(candidateGender.trim()))
						gender = "小姐";
					else
						gender="先生";
					
					String genderAgent = "";
					if("F".equalsIgnoreCase(aamData.getAgentSex().trim()))
						genderAgent = "小姐";
					else
						genderAgent="先生";
		            
				 	Session session = null;
		            session = getProps();
		            MimeMessage msg = new MimeMessage(session);
			          
		            msg.setFrom(new InternetAddress(FROM));
		            msg.setSubject("面试更新","UTF-8");
		            emailMsg ="尊敬的 " +candidateName + " "+ gender+","+"\n\n"+
		            		"您报名的活动已经取消，给您造成不便敬请谅解，若有需求请联系您的营销员  "+aamData.getAgentName().replaceAll("^\\s+|\\s+$", "")+" "+genderAgent +", "+ aamData.getTel().trim() +","+"\n\n"+
		            		"面试名称："+interview.getInterviewSessionName()+"\n"+
	    					"面试日期："+interviewDate+"\n"+
	    					"活动开始时间："+startTime+"\n"+
	    					"地点：" + interview.getLocation()+"\n"+
	    					"面试资料："+interview.getInterviewType()+"\n"+
	    					"附件："+ fname+"\n\n"+
	    					"此为系统邮件，请勿直接回复。 \n\n"+
							"祝您：身体健康 万事如意"+"\n\n"
							+ "AIA CHINA" ;
		            
		            
		            MimeBodyPart messageBodyPart =  new MimeBodyPart();
				    messageBodyPart.setText(emailMsg,"UTF-8");
				    
				    Multipart multipart = new MimeMultipart();
				    multipart.addBodyPart(messageBodyPart);
				    if(qrCode!=null && qrCode.length > 0){
						DataSource source = new  ByteArrayDataSource(qrCode,"application/octet-stream");
						MimeBodyPart attachmentPart = new MimeBodyPart();
						attachmentPart.setDataHandler(new DataHandler(source));
						attachmentPart.setFileName("QR_CODE_IMAGE.jpeg");
						multipart.addBodyPart(attachmentPart);
				    }
				   if(material!=null){
				    if(material.getMaterial() !=null && material.getMaterial().length > 0){
	    				DataSource source = new  ByteArrayDataSource(material.getMaterial(),"application/octet-stream");
	    				MimeBodyPart attachmentPart = new MimeBodyPart();
	    				attachmentPart.setDataHandler(new DataHandler(source));
	    				attachmentPart.setFileName(fname);
	    				multipart.addBodyPart(attachmentPart);
	    			  }	
				    }
		            msg.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(candidateEmail, false));
		            msg.setContent(multipart);
		    	    Transport.send(msg);
	    	    
		        }
	          
	            log.log(Level.INFO, "sending succesfull"); 
			 }else{
				  log.log(Level.INFO, "No Recipient Email Address Found"); 
			 }
		}
		 catch(Exception e)
	        {
	        	log.log(Level.SEVERE, e.getMessage()); 
	            e.printStackTrace();
	            LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("EmailNotification",Level.SEVERE+"",errors.toString());
	        }
		
	}
	/*	  public static void main(String str[])
		  {
			  
			  try
			  {
			  Session session = null;
	            session = getProps();
	            Message msg = new MimeMessage(session);
	            
	            msg.setFrom(new InternetAddress("my.premieracademy@aia.com"));
	            msg.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse("jay.gagnani@quix.com.sg", false));
	            msg.setSubject("LMS - Registration Confirmation ");
	            msg.setText("hi");
	            Transport.send(msg);
			  }
			  catch(Exception e)
			  {
				  e.printStackTrace();
			  }
	            
		  }*/

}
