<%--
  - Author(s):          Hemraj
  - Date:               20-July-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:        View Candidate Interview Details
--%>

<%@page import="com.quix.aia.cn.imo.mapper.AddressBookMaintenance"%>
<%@page import="com.quix.aia.cn.imo.data.addressbook.CandidateFirstInterview"%>
<%@page import="com.quix.aia.cn.imo.mapper.CandidateFirstInterviewMaintenance"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.StringWriter"%>
<%@page import="com.quix.aia.cn.imo.mapper.LogsMaintenance"%>
<%@page import="com.quix.aia.cn.imo.data.addressbook.AddressBook"%>
<%@page import="java.io.File"%>
<%@page import="com.quix.aia.cn.imo.data.interview.InterviewCandidateMaterial"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.quix.aia.cn.imo.data.interview.Interview"%>
<%@page import="com.quix.aia.cn.imo.mapper.InterviewMaintenance"%>
<%@page import="com.quix.aia.cn.imo.utilities.LMSUtil"%>
<%@page import="com.quix.aia.cn.imo.data.interview.InterviewCandidate"%>
<%@page import="com.quix.aia.cn.imo.mapper.InterviewAttendanceMaintenance"%>
<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>
<%@ page import='com.quix.aia.cn.imo.data.user.*' %>
<%@ page import='com.quix.aia.cn.imo.constants.SessionAttributes'%>



<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/interface/InterviewAttendance.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>

<%try{ %>
<%
User user = ((com.quix.aia.cn.imo.data.user.User)request.getSession().getAttribute(SessionAttributes.CURR_USER_OBJ));
LocaleObject localeObj = (LocaleObject)session.getAttribute(SessionAttributes.LOCALE_OBJ);
InterviewAttendanceMaintenance attendanceMaintenance = new InterviewAttendanceMaintenance();
InterviewMaintenance interviewMaintenance = new InterviewMaintenance();
String iCode = request.getParameter("interviewCode");
iCode = iCode==null||iCode.equals("")?"0":iCode;

String candidateCode = request.getParameter("candidateCode");
candidateCode = candidateCode==null||candidateCode.equals("")?"0":candidateCode;

InterviewCandidate interviewCandidate = attendanceMaintenance.getAttendanceCandidateDetail1(request, Integer.parseInt(iCode),Integer.parseInt(candidateCode));
AddressBookMaintenance addrBookMain = new AddressBookMaintenance();
String nric = addrBookMain.getNric(Integer.parseInt(interviewCandidate.getInterviewCandidateCode()));
interviewCandidate.setNric(nric!=null ? nric : "");
String sourcOfRefereal=InterviewAttendanceMaintenance.SOURCE_0F_REFERRAL(interviewCandidate.getSourceOfReferal());
Interview interview = interviewMaintenance.getInterviewBasedOnInterviewCode(Integer.parseInt(iCode));
AddressBook address=interviewMaintenance.getInterviewCandidateCCTest(interview,Integer.parseInt(candidateCode));
String ccResult="",ccDate="";
User userObj = (User)request.getSession().getAttribute("currUserObj");
if(address!=null){
	if(address.getCcTestResult()==null){
		ccResult="";
	}else{
		ccResult=address.getCcTestResult();
	}
	if(address.getCcTestResultDate()!=null)
		ccDate=LMSUtil.convertDateToString(address.getCcTestResultDate());
	
}

//InterviewCandidateMaterial material=interviewMaintenance.getApplicationForm(Integer.parseInt(iCode),request,candidateCode);


String birthDate = "";
if(interviewCandidate.getDob()!=null)
	birthDate = LMSUtil.convertDateToString(interviewCandidate.getDob());

String substr ="";
StringBuffer contactNumber = new StringBuffer(interviewCandidate.getContactNumber()==null?"":interviewCandidate.getContactNumber());
	if(interviewCandidate.getContactNumber()!=null && !interviewCandidate.getContactNumber().equals("") )
	{	
		String number=interviewCandidate.getContactNumber()==null?"":interviewCandidate.getContactNumber();
		substr = number.substring(0,3);
		/* if(contactNumber.length()>=3)
			contactNumber.setCharAt(3, '*');
		if(contactNumber.length()>=4)
			contactNumber.setCharAt(4, '*');
		if(contactNumber.length()>=5)
			contactNumber.setCharAt(5, '*');
		if(contactNumber.length()>=6)
			contactNumber.setCharAt(6, '*'); */
	}
	
	String stFilePath = "resources"+"/"+"material";
	CandidateFirstInterview candidateFirstInterview  = new CandidateFirstInterviewMaintenance().getCandidateFirstinterview(interviewCandidate.getServicingAgent(),interviewCandidate.getInterviewCandidateCode());
	if(candidateFirstInterview == null){
		candidateFirstInterview = new CandidateFirstInterview();
		candidateFirstInterview.setRecruitmentPlan("");
		candidateFirstInterview.setRemarks("");
		candidateFirstInterview.setInterviewResult("");
	}
%>


<script language="javascript" type="text/javascript" >
        window.history.forward();
</script>

   <div id="maincontainer">
   		 <div class="content" style="background-color:#ffffff;">   	
                      <form method="post" action="FormManager?key=online_admin" name="newUserForm">
							<input type="hidden" name="token" id="token" value="<%=request.getSession().getAttribute("Token")+"" %>">
							<input type="hidden" name="<%=com.quix.aia.cn.imo.constants.FormInfo.HIDDEN_ACTION%>"/>
							<input type="hidden" id="candidateCode" name="candidateCode" value="<%=candidateCode%>"/>
							<input type="hidden" id="interviewCode" name="interviewCode" value="<%=iCode%>"/>
							<input type="hidden" id="allCandidateCode" name="allCandidateCode" value=""/>
							<input type="hidden" name="token" id="token" value="<%=request.getSession().getAttribute("Token")+"" %>">
							
                        	<div class="add_new_class"> 
                        		<div class="head">
                                	 <h2><%=localeObj.getTranslatedText("View Candidate's Interview Detail")%></h2>
                                </div>
                                <!--  -->
                                  <div class="">
                                	<!-- <h4 class="P4S_25">Main Details</h4> -->                               
                                	<div class="form clearfix">
                                
											
                                	<table width="100%" style="  border: none;"  >
                                		
                                		<!--     Interview Session Name    -->     
                                		<tr> 
                                		<td colspan="4"> 
                                		<div align="left" style="width: 30%;background: #d31145;padding: 15px 10px;position: relative;">
                                		<h3><%=localeObj.getTranslatedText("Interview Session Name")%></h3>    
                                		</div>
                                		</td>
                                		</tr>
                                		 
                                		<tr>                  
                                    	<td style="border: none;" width="25%" >	                                    				
	                                    	<label ><%=localeObj.getTranslatedText("BU")%>:</label>
                                    	</td>
                                       <td style="border: none;" width="25%" >	
                                        	<input name="buName" id="buName"  type="text" class="text" readonly="readonly" value="<%=interviewCandidate.getBuName()==null?"":interviewCandidate.getBuName() %>"  />
                                       	</td>  
                                       	<td style="border: none;" width="25%" >		                                    				
	                                    	<label ><%=localeObj.getTranslatedText("Interview Type")%>:</label>
                                    	</td>
                                    	
                                    	<%
                                    			String str=interview.getInterviewType();
                                     	if(interview.getInterviewType().equals("2nd")){
                                    		str=localeObj.getTranslatedText("2nd Interview");
                                    	}else{
                                    		str=localeObj.getTranslatedText("3rd Interview");
                                    	} 
                                    	
                                    	%>
                                    	
                                    	
                                       <td style="border: none;" width="25%" >	
                                        	<input name="interviewType" id="interviewType" type="text" class="text" readonly="readonly" value="<%=str %>"  />
                                       	</td>                                     			   
                                      	</tr>
                                      	
                                      	<tr>                  
                                    	<td style="border: none;" width="25%" >	                                    				
	                                    	<label ><%=localeObj.getTranslatedText("District")%>:</label>
                                    	</td>
                                       <td style="border: none;" width="25%" >	
                                        	<input name="district" id="district" type="text" class="text" readonly="readonly" value="<%=interviewCandidate.getDistName()==null?"":interviewCandidate.getDistName() %>"  />
                                       	</td>  
                                       	<td style="border: none;" width="25%" >		                                    				
	                                    	<label ><%=localeObj.getTranslatedText("Date & Time")%>:</label>
                                    	</td>
                                    	<%String interviewDate = "";
                                    	if(interview.getInterviewDate()!=null)
                                    		interviewDate = LMSUtil.convertDateToString(interview.getInterviewDate());
                                    	
                                    	%>
                                       <td style="border: none;" width="25%" >	
                                        	<input name="dateTime" id="dateTime" type="text" class="text" readonly="readonly" value="<%=interviewDate %>"  />
                                       	</td>                                     			   
                                      	</tr>
                                      	
                                      	<tr>                  
                                    	<td style="border: none;" width="25%" >	                                    				
	                                    	<label ><%=localeObj.getTranslatedText("City")%>:</label>
                                    	</td>
                                       <td style="border: none;" width="25%" >	
                                        	<input name="city" id="city" type="text" class="text" readonly="readonly" value="<%=interviewCandidate.getCityName()==null?"":interviewCandidate.getCityName()%>"  />
                                       	</td>  
                                       	<td style="border: none;" width="25%" >		                                    				
	                                    	<label ><%=localeObj.getTranslatedText("Interviewer")%>:</label>
                                    	</td>
                                    	
                                       <td style="border: none;" width="25%" >	
                                        	<input name="interviewer" id="interviewer" type="text" class="text" readonly="readonly" value="<%=interviewCandidate.getInterviwer_name()==null?"":interviewCandidate.getInterviwer_name()%>"  />
                                       	</td>                                     			   
                                      	</tr>
                                      	
                                      	<tr>                  
                                    	<td style="border: none;" width="25%" >	                                    				
	                                    	<label ><%=localeObj.getTranslatedText("SSC")%>:</label>
                                    	</td>
                                       <td style="border: none;" width="25%" >	
                                        	<input name="ssc" id="ssc" type="text" class="text" readonly="readonly" value="<%=interviewCandidate.getSscName()==null?"":interviewCandidate.getSscName()%>"  />
                                       	</td>  
                                       	<td style="border: none;" width="25%" >		                                    				
	                                    	<label ><%=localeObj.getTranslatedText("Office")%>:</label>
                                    	</td>
                                       <td style="border: none;" width="25%" >	
                                        	<input name="office" id="office" type="text" class="text" readonly="readonly" value="<%=interviewCandidate.getOfficeName()==null?"":interviewCandidate.getOfficeName()%>"  />
                                       	</td>                                     			   
                                      	</tr>
                                		
                                		 
                                		
                                		<!--     Candidate Details & Interview History    --> 	
                                		<tr> 
                                		<td colspan="4"> 
                                		<div align="left" style="width: 30%;background: #d31145;padding: 15px 10px;position: relative;">
                                		<h3><%=localeObj.getTranslatedText("Candidate Details & Interview History")%></h3>    
                                		</div>
                                		</td>
                                		</tr>
                                		 
                                		<tr>                  
                                    	<td style="border: none;" width="25%" >	                                    				
	                                    	<label ><%=localeObj.getTranslatedText("Candidate Name")%>:</label>
                                    	</td>
                                       <td style="border: none;" width="25%" >	
                                        	<input name="name" id="name" type="text" class="text" readonly="readonly" value="<%=interviewCandidate.getCandidateName()==null?"":interviewCandidate.getCandidateName() %>"  />
                                       	</td>  
                                       	<td style="border: none;" width="25%" >		                                    				
	                                    	<label ><%=localeObj.getTranslatedText("Servicing Agent")%>:</label>
                                    	</td>
                                    	<%
                                    		if(interviewCandidate.getServiceingAgentName()!=null){
                                    			if(interviewCandidate.getServiceingAgentName().equals("null")){
                                    				interviewCandidate.setServiceingAgentName("");
                                    			}
                                    		}else{
                                    			interviewCandidate.setServiceingAgentName("");
                                    		}
                                    	%>
                                       <td style="border: none;" width="25%" >	
                                        	<input name="servingAgent" id="servingAgent" type="text" class="text" readonly="readonly" value="<%=interviewCandidate.getServiceingAgentName()%>"  />
                                       	</td>                                     			   
                                      	</tr>
                                      	
                                      	
                                      	<tr>                         
                                    	<td style="border: none;"  >	                                    				
	                                    	<label ><%=localeObj.getTranslatedText("Age")%>:</label>
                                    	</td>
                                        <td style="border: none;"  >
                                        	<input name="age" id="age" type="text" class="text" readonly="readonly" value="<%=interviewCandidate.getAge() %>"  />
                                       	</td>  
                                       	<td style="border: none;"  >	                                    				
	                                    	<label ><%=localeObj.getTranslatedText("Source of Referral")%>:</label>
                                    	</td>
                                        <td style="border: none;"  >
                                        	<input name="referalSource" id="referalSource" type="text" class="text" readonly="readonly" value="<%=sourcOfRefereal==null?"":localeObj.getTranslatedText(sourcOfRefereal) %>"  />
                                       	</td>                                     			   
                                      	</tr>
                                      	
                                      	
                                      	<tr>                         
                                    	<td style="border: none;"  >	                                    				
	                                    	<label ><%=localeObj.getTranslatedText("Date of Birth")%>:</label>
                                    	</td>
                                        <td style="border: none;"  >
                                        	<input name="birthDate" id="birthDate" type="text" class="text" readonly="readonly" value="<%=birthDate%>"  />
                                       	</td>  
                                       	<td style="border: none;"  >	                                    				
	                                    	<label ><%=localeObj.getTranslatedText("Gender")%>:</label>
                                    	</td>
                                        <td style="border: none;"  >
                                        	<input name="gender" id="gender" type="text" class="text" readonly="readonly" value="<%=interviewCandidate.getGender().equals("M")?localeObj.getTranslatedText("Male"):localeObj.getTranslatedText("Female") %>"  />
                                       	</td>                                     			   
                                      	</tr>
                            		 	
                            		 	
                            		 	
                            		 	<tr>                         
                                    	<td style="border: none;"  >	                                    				
	                                    	<label ><%=localeObj.getTranslatedText("No. of attended 2nd interview")%>:</label>
                                    	</td>
                                        <td style="border: none;"  >
                                        	<input name="secIntCount" id="secIntCount" type="text" class="text" value="0"  />
                                       	</td>  
                                       	<td style="border: none;"  >	                                    				
	                                    	<label ><%=localeObj.getTranslatedText("Contact number")%>:</label>
                                    	</td>
                                        <td style="border: none;"  >
                                        	<input name="contactNumber" id="contactNumber" type="text" class="text" readonly="readonly" value="<%=substr%>"  />
                                       	</td>                                     			   
                                      	</tr>	
                                      	
                                      	
                                      	<tr>                         
                                    	<td style="border: none;"  >	                                    				
	                                    	<label ><%=localeObj.getTranslatedText("No. of attended 3rd interview")%>:</label>
                                    	</td>
                                        <td style="border: none;"  >
                                        	<input name="thirdIntCount" id="thirdIntCount" type="text" class="text" value="0"  />
                                       	</td>  
                                       	<td style="border: none;"  >	                                    				
	                                    	
                                    	</td>
                                        <td style="border: none;"  >
                                        	
                                       	</td>                                     			   
                                      	</tr>			
                		 				
                		 				<!--     Preview Application Form    --> 	
                		 				<tr> 
                                		<td colspan="4"> 
                                		<div align="left" style="width: 30%;background: #d31145;padding: 18px 12px;position: relative;">
                                		<h3><%=localeObj.getTranslatedText("Preview Application Form")%></h3>    
                                		</div>
                                		</td>
                                		</tr>
                                		 
                                		<tr>                  
                                    	<td style="border: none;" width="25%" >	                                    				
	                                    	<label ><%=localeObj.getTranslatedText("Material Description")%>:</label>
                                    	</td>
                                       <td style="border: none;" width="25%" >	
                                        	<input name="materialDesc" id="materialDesc" type="text" class="text" value=""  />
                                       	</td>  
                                       	
                                       <td style="border: none;text-align:left" width="25%" colspan="2" >	
                                        	<input name="material" id="material" type="file"  />  <progress value="22" max="100" id="progress1" style="border: 4px solid #CCCCCC; margin-left:400px; margin-top:-24px;" ></progress> <a href="javascript:uploadFile()" class="btn1 ML10"><%=localeObj.getTranslatedText("Upload")%></a></br><label> <%=localeObj.getTranslatedText("Maximum 5MB")%></label>  
                                       	</td>                                     			   
                                      	</tr>
                                      	
                                      	<tr>
                                      		<td style="border: none;" width="25%" >	                                    				
	                                    	<label ></label>
                                    	</td>
                                       <td style="border: none;" width="25%" >	
                                        	
                                       	</td>  
                                     <%--   	<%
                                       	
                                       	String materialPath="";
                                       	if(material!=null){
                                       		materialPath="resources"+File.separator+"upload"+File.separator+material.getMaterialFileName();
                                       	}else{
                                       		materialPath="#";
                                       	}
                                       	
                                       	if(material!=null){ %>
                                       <td style="border: none;text-align:left" width="25%" colspan="2" >	
                                        	<a href="<%=materialPath %>" target="_new" id="applicationName" name="applicationName" ><%=localeObj.getTranslatedText("Application Form")%></a> 
                                       	</td>  
                                      	</tr>
                                      	<%} %> --%>
                                      	  <td style="border: none;text-align:left" width="25%" colspan="2" >	
                                        	<a href="ContentManager?key=ViewEapplicationForm&interviewCode=<%=interviewCandidate.getInterviewCode()%>&candidateCode=<%=interviewCandidate.getCandidateCode() %>" target="_new" id="applicationName" name="applicationName" ><%=localeObj.getTranslatedText("Application Form")%></a> 
                                       	</td>  
                                      	</tr>
                		 				<tr> 
                                		<td colspan="4"> 
                                		<div align="left" style="width: 30%;background: #d31145;padding: 15px 10px;position: relative;">
                                		<h3><%=localeObj.getTranslatedText("First Interview Record")%></h3>    
                                		</div>
                                		</td>
                                		</tr>
                                		 <%
                                		 Date date=new Date();
                                		String dt="";
                                		 SimpleDateFormat formate=new SimpleDateFormat("dd/MM/yyyy");
                                		 		if(request.getSession().getAttribute("interviewDate")!=null){
                                		 			date=(Date)request.getSession().getAttribute("interviewDate");
                                		 			dt=formate.format(date);
                                		 		}
                                		 %>
                                		 
                                		<tr>                  
                                    	<td style="border: none;" width="25%" >	                                    				
	                                    	<label ><%=localeObj.getTranslatedText("Completion Date")%>:</label>
                                    	</td>
                                       <td style="border: none;" width="25%" >	
                                        	<input name="completionDate" id="completionDate" type="text" class="text"  value="<%=dt %>" readonly="readonly" />
                                       	</td>  
                                       	<td style="border: none;" width="25%" >		                                    				
	                                    	
                                    	</td>
                                       <td style="border: none;" width="25%" >	
                                        	 
                                       	</td>                                     			   
                                      	</tr>
                            		 	<tr>                  
                                    	<td style="border: none;" width="25%" >	                                    				
	                                    	<label ><%=localeObj.getTranslatedText("Recruitment Scheme")%>:</label>
                                    	</td>
                                       <td style="border: none;" width="25%" >	
                                        	<input name="completionDate" id="completionDate" type="text" class="text"  value="<%=candidateFirstInterview.getRecruitmentPlan() %>" readonly="readonly" />
                                       	</td>  
                                       	<td style="border: none;" width="25%" >		                                    				
	                                    	
                                    	</td>
                                       <td style="border: none;" width="25%" >	
                                        	 
                                       	</td>                                     			   
                                      	</tr>
                            		 	<tr>                  
                                    	<td style="border: none;" width="25%" >	                                    				
	                                    	<label ><%=localeObj.getTranslatedText("Remarks")%>:</label>
                                    	</td>
                                       <td style="border: none;" width="25%" >	
                                        	<input name="completionDate" id="completionDate" type="text" class="text"  value="<%=candidateFirstInterview.getRemarks() %>" readonly="readonly" />
                                       	</td>  
                                       	<td style="border: none;" width="25%" >		                                    				
	                                    	
                                    	</td>
                                       <td style="border: none;" width="25%" >	
                                        	 
                                       	</td>                                     			   
                                      	</tr>
                                      	<tr>                  
                                    	<td style="border: none;" width="25%" >	                                    				
	                                    	<label ><%=localeObj.getTranslatedText("Results")%>:</label>
                                    	</td>
                                       <td style="border: none;" width="25%" >	
                                        	<input name="completionDate" id="completionDate" type="text" class="text"  value="<%=localeObj.getTranslatedText(candidateFirstInterview.getInterviewResult()) %>" readonly="readonly" />
                                       	</td>  
                                       	<td style="border: none;" width="25%" >		                                    				
	                                    	
                                    	</td>
                                       <td style="border: none;" width="25%" >	
                                        	 
                                       	</td>                                     			   
                                      	</tr>
                            		 	<tr> 
                                		<td colspan="4"> 
                                		<div align="left" style="width: 30%;background: #d31145;padding: 15px 10px;position: relative;">
                                		<h3><%=localeObj.getTranslatedText("CC Test Result")%></h3>    
                                		</div>
                                		</td>
                                		</tr>
                                		
                                		<tr>                  
                                    	<td style="border: none;" width="25%" >	                                    				
	                                    	<label ><%=localeObj.getTranslatedText("CC Test")%>:</label>
                                    	</td>
                                       <td style="border: none;" width="25%" >	
                                        	<input name="testResult" id="testResult" type="text" class="text"  value="<%=localeObj.getTranslatedText(ccResult) %>" readonly="readonly" />
                                       	</td>  
                                       	<td style="border: none;" width="25%" >	                                    				
	                                    	<label ><%=localeObj.getTranslatedText("CC Test Date")%>:</label>
                                    	</td>
                                       <td style="border: none;" width="25%" >	
                                        	<input name="testResultDate" id="testResultDate" type="text" class="text"  value="<%=ccDate %>" readonly="readonly" />
                                       	</td>                                     			   
                                      	</tr>
                                			
                                       </table>
                                       
                                       	
                                      	<!--     2nd Interview Records    --> 	
                                     	
                <table id="secondInterview" width="100%" border="0" cellspacing="0" cellpadding="0" >
						<thead>
						<tr> 
                            		<td colspan="8" style="border-right:none"> 
                            		<div align="left" style="width: 30%;background: #d31145;padding: 15px 10px;position: relative;">
                            		<h3><%=localeObj.getTranslatedText("2nd Interview Records")%></h3>    
                            		</div>
                            		</td>
                         </tr>
				
		             <tr class="head">
		        		<th valign=top align="left" style="border-right: 1px solid #e7acbc;border-bottom: 1px solid #e7acbc;text-align:center;color:white;" width="7%"><%=localeObj.getTranslatedText("Interview session name")%></th>
		        		<th valign=top align="left" style="border-right: 1px solid #e7acbc;border-bottom: 1px solid #e7acbc;text-align:center;color:white;" width="7%"><%=localeObj.getTranslatedText("Date & Time")%></th>
		        		<th valign=top align="center" style="border-right: 1px solid #e7acbc;border-bottom: 1px solid #e7acbc;text-align:center;color:white;" width="10%"><%=localeObj.getTranslatedText("Location")%></th>
		        		<th valign=top align="left" style="border-right: 1px solid #e7acbc;border-bottom: 1px solid #e7acbc;text-align:center;color:white;" width="10%"><%=localeObj.getTranslatedText("Recruitment Scheme")%></th>
		        		<th valign=top align="left" style="border-right: 1px solid #e7acbc;border-bottom: 1px solid #e7acbc;text-align:center;color:white;" width="7%"><%=localeObj.getTranslatedText("Interviewer")%></th>
		        		<th valign=top align="left" style="border-right: 1px solid #e7acbc;border-bottom: 1px solid #e7acbc;text-align:center;color:white;" width="7%"><%=localeObj.getTranslatedText("Interview Result")%></th>
		        		<th valign=top align="left" style="border-right: 1px solid #e7acbc;border-bottom: 1px solid #e7acbc;text-align:center;color:white;" width="7%"><%=localeObj.getTranslatedText("Remarks")%></th>
		        		<th valign=top align="left" style="border-right: 1px solid #e7acbc;border-bottom: 1px solid #e7acbc;text-align:center;color:white;" width="7%"><%=localeObj.getTranslatedText("View Document")%></th>
					</tr>
				</thead>
				<tbody>
					<!-- <tr style="background: #9A9090;" style="border: 1px solid #e7acbc;">
						<td  style="border-right: 1px solid #e7acbc;" width="7%" ></td>
						<td  style="border-right: 1px solid #e7acbc;" width="7%"></td>
						<td  style="border-right: 1px solid #e7acbc;text-align: center;" width="10%"></td>
						<td  style="border-right: 1px solid #e7acbc;text-align: center;" width="10%"></td>
						<td  style="border-right: 1px solid #e7acbc;" width="7%"></td>
						<td  style="border-right: 1px solid #e7acbc;" width="7%"></td>
						<td  style="border-right: 1px solid #e7acbc;" width="7%"></td>
						<td><a href="" width="7%">File 1 | File 2 | File 3 | File 4 | File 5</a></td>
					</tr>
						
					<tr style="border: 1px solid #e7acbc;">
						<td  style="border-right: 1px solid #e7acbc;" width="7%" ></td>
						<td  style="border-right: 1px solid #e7acbc;" width="7%"></td>
						<td  style="border-right: 1px solid #e7acbc;text-align: center;" width="10%"></td>
						<td  style="border-right: 1px solid #e7acbc;text-align: center;" width="10%"></td>
						<td  style="border-right: 1px solid #e7acbc;" width="7%"></td>
						<td  style="border-right: 1px solid #e7acbc;" width="7%"></td>
						<td  style="border-right: 1px solid #e7acbc;" width="7%"></td>
						<td><a href="" width="7%">File 1 | File 2 | File 3 | File 4 | File 5</a></td>
					</tr> -->
				</tbody>
	    </table>
	    
	    <!--     3rd Interview Records    --> 	
                                     	
                <table id="thirdInterview" width="100%" border="0" cellspacing="0" cellpadding="0" >
				  <thead>	
					<tr> 
                        <td colspan="8"> 
                        <div align="left" style="width: 30%;background: #d31145;padding: 15px 10px;position: relative;">
                        <h3><%=localeObj.getTranslatedText("3rd Interview Records")%></h3>    
                        </div>
                        </td>
               		</tr>
			
		             <tr class="head">
		        		<th valign=top align="left" style="border-right: 1px solid #e7acbc;border-bottom: 1px solid #e7acbc;text-align:center;color:white;" width="7%"><%=localeObj.getTranslatedText("Interview session name")%></th>
		        		<th valign=top align="left" style="border-right: 1px solid #e7acbc;border-bottom: 1px solid #e7acbc;text-align:center;color:white;" width="7%"><%=localeObj.getTranslatedText("Date & Time")%></th>
		        		<th valign=top align="center" style="border-right: 1px solid #e7acbc;border-bottom: 1px solid #e7acbc;text-align:center;color:white;" width="10%"><%=localeObj.getTranslatedText("Location")%></th>
		        		<th valign=top align="left" style="border-right: 1px solid #e7acbc;border-bottom: 1px solid #e7acbc;text-align:center;color:white;" width="10%"><%=localeObj.getTranslatedText("Recruitment Scheme")%></th>
		        		<th valign=top align="left" style="border-right: 1px solid #e7acbc;border-bottom: 1px solid #e7acbc;text-align:center;color:white;" width="7%"><%=localeObj.getTranslatedText("Interviewer")%></th>
		        		<th valign=top align="left" style="border-right: 1px solid #e7acbc;border-bottom: 1px solid #e7acbc;text-align:center;color:white; width="7%"><%=localeObj.getTranslatedText("Interview Result")%></th>
		        		<th valign=top align="left" style="border-right: 1px solid #e7acbc;border-bottom: 1px solid #e7acbc;text-align:center;color:white;" width="7%"><%=localeObj.getTranslatedText("Remarks")%></th>
		        		<th valign=top align="left" style="border-right: 1px solid #e7acbc;border-bottom: 1px solid #e7acbc;text-align:center;color:white;" width="7%"><%=localeObj.getTranslatedText("View Document")%></th>
					</tr>
					</thead>
				<tbody>	
					<!-- <tr style="background: #9A9090;" style="border: 1px solid #e7acbc;">
						<td  style="border-right: 1px solid #e7acbc;" width="7%" ></td>
						<td  style="border-right: 1px solid #e7acbc;" width="7%"></td>
						<td  style="border-right: 1px solid #e7acbc;text-align: center;" width="10%"></td>
						<td  style="border-right: 1px solid #e7acbc;text-align: center;" width="10%"></td>
						<td  style="border-right: 1px solid #e7acbc;" width="7%"></td>
						<td  style="border-right: 1px solid #e7acbc;" width="7%"></td>
						<td  style="border-right: 1px solid #e7acbc;" width="7%"></td>
						<td><a href="" width="7%">File 1 | File 2 | File 3 | File 4 | File 5</a></td>
					</tr>
						
					<tr style="border: 1px solid #e7acbc;">
						<td  style="border-right: 1px solid #e7acbc;" width="7%" ></td>
						<td  style="border-right: 1px solid #e7acbc;" width="7%"></td>
						<td  style="border-right: 1px solid #e7acbc;text-align: center;" width="10%"></td>
						<td  style="border-right: 1px solid #e7acbc;text-align: center;" width="10%"></td>
						<td  style="border-right: 1px solid #e7acbc;" width="7%"></td>
						<td  style="border-right: 1px solid #e7acbc;" width="7%"></td>
						<td  style="border-right: 1px solid #e7acbc;" width="7%"></td>
						<td><a href=""  width="7%">File 1 | File 2 | File 3 | File 4 | File 5</a></td>
					</tr> -->
				</tbody>
	    </table>
	    
	    
             <div class="clear"></div>
           <table style="border: none;width:100%"  >
            		<tr>
            			<td style="text-align:center">
            				<a href="ContentManager?key=InterviewAttendanceDetails&interview_code=<%=iCode%>" class="btn1" ><%=localeObj.getTranslatedText("Back")%></a>
            				<%-- <a href="#" class="btn1"><%=localeObj.getTranslatedText("Cancel")%></a> --%>
            			</td>
            		</tr> 
            </table>
 			</div>
          </div> 
          </div>
          </form>
          </div>
        </div>  
               

<script type="text/javascript">

//<![CDATA[
           var m = '<%=localeObj.getTranslatedText("No Record Found")%>';
        function uploadFile()
        {
       // var candidateCodes = $("#allCandidateCode").val();
         var candidateCode = $("#candidateCode").val();
		 $('#ajaxLoader').find(".lightbox").show();
		 if($('input[type=file]').get(0).files[0] !=undefined){
			var topicFile = $('input[type=file]').get(0).files[0];
			var file_extension=topicFile.name;
			if((file_extension.indexOf('.pdf')>-1) || (file_extension.indexOf('.PDF')>-1)
					|| (file_extension.indexOf('.doc')>-1) || (file_extension.indexOf('.DOC')>-1)
					|| (file_extension.indexOf('.docx')>-1)|| (file_extension.indexOf('.DOCX')>-1)){
			//	showProgress();
			var fd = new FormData();
			fd.append('CandidateMaterial', topicFile);
			var fileName=topicFile.name;
			var materialDesc=$("#materialDesc").val();
			var candidateCode=$("#candidateCode").val();
			var interviewCode=$("#interviewCode").val();
			var size=$('input[type=file]').get(0).files[0].size;
			if(size<=5242880){
			  $.ajax({
					url : 'UploadMaterial?image_name='+fileName,
					type: "POST",
					data: fd,
				   	processData: false,
				   	contentType: false,
				}).done(function(respond){
					 InterviewAttendance.insertCandidateMaterialDWR(candidateCode,fileName,materialDesc,{
							callback : function(str) {
								if((str-0)>0)
								{
										alert("Material Added Successfully");
										window.location.href = 'ContentManager?key=ViewCandidateInterviewDetail&interviewCode='+interviewCode+'&candidateCode='+candidateCode;
								}
								else
									alert("Material Not Added");
							}
					 });
				});
			}else{
				$('#ajaxLoader').find(".lightbox").hide();
				alert("Please Upload File Less then 5 MB");
				$("#material").val('');
			} 
			 // hideProgress();
			 // alert("file Uploaded Successfully");
			 
			}
			else{
				alert("Please Upload a PDF or Documents File");
			}
	   }
	  else{
			alert("Please Upload a file");
	   }
		$('#ajaxLoader').find(".lightbox").hide();
	 }
</script>
<script>
$(function() {
	$( ".datepicker" ).datepicker({
	showOn: "button",
	buttonImage: "images/calendar_icon.gif",
	buttonImageOnly: true,
	dateFormat:"dd/mm/yy"
	});
 });
$(document).ready(function() {
	 $("#progress").hide();
	 $("#progress1").hide();
	 $("#progress2").hide();
	
	 

	  $('#secondInterview tbody').html("<tr style='background: #fffff;border: 1px solid #e7acbc;'><td  style='border-right: 1px solid #e7acbc;font-weight:bold' colspan=8>"+m+"</td></tr>");
	  $('#thirdInterview tbody').html("<tr style='background: #fffff;border: 1px solid #e7acbc;'><td  style='border-right: 1px solid #e7acbc;font-weight:bold' colspan=8>"+m+"</td></tr>");

	 <%if(interviewCandidate.getNric()!=null && !interviewCandidate.getNric().equals(""))
 	{%>	
 	 $('#ajaxLoader').find(".lightbox").show();
 	var  cnt1 = 0;
	var cnt2 = 0;
	  InterviewAttendance.getAttendanceCandidateDetailsDWR('<%=interviewCandidate.getNric()%>',{
			callback : function(str) {

				var candidateList = str.list;
				var interviewCandList = str.interviewCandidateList;
			
					var flag2=false;
					var flag3=false;
					 $('#secondInterview tbody').html("");
					 $('#thirdInterview tbody').html("");
					var secondString="";
					var thirdString="";  
					var iDate = "";
					var candidateCodesList = "";
					for(var i=0;i<candidateList.length;i++)
					{
						iDate = new Date(interviewCandList[i].interviewDate);	
						var inDate = iDate.getDate()+"-"+(iDate.getMonth()+1)+"-"+iDate.getFullYear();
						var result = "";		
						if(candidateList[i].interviewResult=="P")
							result="<%=localeObj.getTranslatedText("PASS") %>";
						if(candidateList[i].interviewResult=="F")
							result="<%=localeObj.getTranslatedText("FAIL") %>";		
  
						candidateCodesList +=  candidateList[i].candidateCode+"|";
						var fileNameLink = 	"";
						
						if(candidateList[i].fileNameList!='')
						{	
							var fileName = candidateList[i].fileNameList.split("|");
							for(var j=0;j<fileName.length;j++)
							{
								
									fileNameLink+="<a href='<%=stFilePath%>/"+fileName[j]+"' target='_new'>"+fileName[j]+"</a> | ";
							}		
						}
						if(fileNameLink!="")
							fileNameLink=fileNameLink.substr(0,fileNameLink.length-2);
						
						if(interviewCandList[i].interviewType=="2nd")
						{
							cnt1 = cnt1 + 1;
							if(flag2)
							{
								secondString+="<tr style='background-color: #dad7d8;border: 1px solid #e7acbc;'>";
								secondString+="<td  style='border-right: 1px solid #e7acbc;' width='7%' >"+interviewCandList[i].interviewSessionName+"</td>";
								secondString+="<td  style='border-right: 1px solid #e7acbc;' width='7%'>"+inDate+"</td>";
	  							secondString+="<td  style='border-right: 1px solid #e7acbc;text-align: center;' width='10%'>"+interviewCandList[i].location+"</td>";
	  							secondString+="<td  style='border-right: 1px solid #e7acbc;text-align: center;' width='10%'>"+candidateList[i].recruitmentScheme+"</td>";
	  							secondString+="<td  style='border-right: 1px solid #e7acbc;' width='7%'></td>";
	  							secondString+="<td  style='border-right: 1px solid #e7acbc;' width='7%'>"+result+"</td>";
	  							secondString+="<td  style='border-right: 1px solid #e7acbc;' width='7%'>"+candidateList[i].remarks+"</td>";
	  							secondString+="<td  width='7%'>"+fileNameLink+"</td>";
	  							secondString+="</tr>";
								flag2=false;
							}
							else
							{
								secondString+="<tr style='border: 1px solid #e7acbc;'>";
								secondString+="<td  style='border-right: 1px solid #e7acbc;' width='7%' >"+interviewCandList[i].interviewSessionName+"</td>";
								secondString+="<td  style='border-right: 1px solid #e7acbc;' width='7%'>"+inDate+"</td>";
	  							secondString+="<td  style='border-right: 1px solid #e7acbc;text-align: center;' width='10%'>"+interviewCandList[i].location+"</td>";
	  							secondString+="<td  style='border-right: 1px solid #e7acbc;text-align: center;' width='10%'>"+candidateList[i].recruitmentScheme+"</td>";
	  							secondString+="<td  style='border-right: 1px solid #e7acbc;' width='7%'></td>";
	  							secondString+="<td  style='border-right: 1px solid #e7acbc;' width='7%'>"+result+"</td>";
	  							secondString+="<td  style='border-right: 1px solid #e7acbc;' width='7%'>"+candidateList[i].remarks+"</td>";
	  							secondString+="<td  width='7%'>"+fileNameLink+"</td>";
	  							secondString+="</tr>";
								flag2=true;
							}		
						}
						if(interviewCandList[i].interviewType=="3rd")
						{
							cnt2 = cnt2 + 1;
							if(flag3)
							{
								thirdString+="<tr style='background-color: #dad7d8;border: 1px solid #e7acbc;'>";
								thirdString+="<td  style='border-right: 1px solid #e7acbc;' width='7%' >"+interviewCandList[i].interviewSessionName+"</td>";
								thirdString+="<td  style='border-right: 1px solid #e7acbc;' width='7%'>"+inDate+"</td>";
	  							thirdString+="<td  style='border-right: 1px solid #e7acbc;text-align: center;' width='10%'>"+interviewCandList[i].location+"</td>";
	  							thirdString+="<td  style='border-right: 1px solid #e7acbc;text-align: center;' width='10%'>"+candidateList[i].recruitmentScheme+"</td>";
	  							thirdString+="<td  style='border-right: 1px solid #e7acbc;' width='7%'></td>";
	  							thirdString+="<td  style='border-right: 1px solid #e7acbc;' width='7%'>"+result+"</td>";
	  							thirdString+="<td  style='border-right: 1px solid #e7acbc;' width='7%'>"+candidateList[i].remarks+"</td>";
	  							thirdString+="<td  width='7%'>"+fileNameLink+"</td>";
	  							thirdString+="</tr>";
								flag3=false;
							}
							else
							{
								thirdString+="<tr style='border: 1px solid #e7acbc;'>";
								thirdString+="<td  style='border-right: 1px solid #e7acbc;' width='7%' >"+interviewCandList[i].interviewSessionName+"</td>";
								thirdString+="<td  style='border-right: 1px solid #e7acbc;' width='7%'>"+inDate+"</td>";
	  							thirdString+="<td  style='border-right: 1px solid #e7acbc;text-align: center;' width='10%'>"+interviewCandList[i].location+"</td>";
	  							thirdString+="<td  style='border-right: 1px solid #e7acbc;text-align: center;' width='10%'>"+candidateList[i].recruitmentScheme+"</td>";
	  							thirdString+="<td  style='border-right: 1px solid #e7acbc;' width='7%'></td>";
	  							thirdString+="<td  style='border-right: 1px solid #e7acbc;' width='7%'>"+result+"</td>";
	  							thirdString+="<td  style='border-right: 1px solid #e7acbc;' width='7%'>"+candidateList[i].remarks+"</td>";
	  							thirdString+="<td  width='7%'>"+fileNameLink+"</td>";
	  							thirdString+="</tr>";
								flag3=true;
							}		
						}
					}
					$('#secondInterview tbody').html(secondString);
					$('#thirdInterview tbody').html(thirdString);
			
					$('#allCandidateCode').val(candidateCodesList);
					$('#secIntCount').val(cnt1);
					$('#thirdIntCount').val(cnt2);
				//$('#thirdInterview tbody').html("<tr style='background: #fffff;border: 1px solid #e7acbc;'><td  style='border-right: 1px solid #e7acbc;font-weight:bold' colspan=8>No Record Found</td></tr>");
				
				$('#ajaxLoader').find(".lightbox").hide();
			}
		});
	<%}%>
	});

</script>
<%}catch(Exception e){
	
	
	e.printStackTrace();
	LogsMaintenance logsMain=new LogsMaintenance();
	StringWriter errors = new StringWriter();
	e.printStackTrace(new PrintWriter(errors));
	logsMain.insertLogs("ViewCandidateInterviewDetails jsp page ","SEVERE"+"",errors.toString());
	
} %>