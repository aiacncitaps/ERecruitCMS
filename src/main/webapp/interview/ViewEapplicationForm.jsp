<%--
  - Author(s):          Nibedita
  - Date:               21-Dec-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:        Adding  Eapplication form page
--%>
<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>
<%@page import="com.quix.aia.cn.imo.utilities.ImoUtilityData"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.StringWriter"%>
<%@page import="com.quix.aia.cn.imo.mapper.LogsMaintenance"%>
<%@page import="com.quix.aia.cn.imo.mapper.InterviewAttendanceMaintenance"%>
<%@page import="com.quix.aia.cn.imo.data.addressbook.CandidateEducation"%>
<%@page import="com.quix.aia.cn.imo.data.addressbook.CandidateESignature"%>
<%@page import="com.quix.aia.cn.imo.data.addressbook.CandidateProfessionalCertification"%>
<%@page import="com.quix.aia.cn.imo.data.addressbook.CandidateWorkExperience"%>
<%@page import="com.quix.aia.cn.imo.data.addressbook.CandidateFamilyInfo"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.quix.aia.cn.imo.mapper.AddressBookMaintenance"%>
<%@page import="com.quix.aia.cn.imo.data.addressbook.AddressBook"%>
<%@page import="com.quix.aia.cn.imo.mapper.InterviewMaintenance"%>
<%@ page import='com.quix.aia.cn.imo.constants.SessionAttributes'%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<script type="text/javascript">
$(document).ready(function(){
});
$(function(){
	
});
 
</script>
<%try{ %>


<%
AddressBookMaintenance addressMain=new AddressBookMaintenance();
LocaleObject localeObj = (LocaleObject)session.getAttribute(SessionAttributes.LOCALE_OBJ);
InterviewAttendanceMaintenance interviewMaint=new InterviewAttendanceMaintenance();
String interviewCandidateCode=interviewMaint.getInterviewCandidateCode(Integer.parseInt(request.getParameter("candidateCode")));
AddressBook addressbook=new AddressBook();
addressbook.setAddressCode(Integer.parseInt(interviewCandidateCode));
addressbook=addressMain.getAddressBook(addressbook);
SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy");
InterviewMaintenance interviewMaintenance = new InterviewMaintenance();

int years = 0;
int months = 0;
int days = 0;

%>
<style>
/* td{
   border-bottom: 1px solid gray;
    border-collapse: collapse;
}
table{
  border-collapse: collapse;
} */
</style>
<div id="header">
		<div class="container_12">
 					<div class="grid_2" >
         				<!-- <a href="#" class="MT08"> --><img src="images/aia_logo.png" width="153" height="65" alt="" /><!-- </a> -->
          			</div>
         			<div class="grid_3">
          				<font color="white"><b><div align="left" style="margin-top:10px"></div><div align="left"></div> </b></font>
          			</div>
			 </div>
</div>
	<div id="maincontainer">
		<div class="head">
			 <h2 align="center">
				Application Form
			</h2> 
			<!-- <h2 align="center">
				申请表
			</h2> -->
		</div>
		<div class="content" style="background-color: #ffffff;">
			<form name="interviewForm" method="post" action="FormManager"
				class="PT20">
				<table class="formDesign" style=" width: 50%;">
					<tr>
						<!-- <td colspan="2" style=" border: 1px solid gray;background-color:lightgrey;"><label>PERSONAL INFORMATION</label></td> -->
						<td colspan="2" style=" border: 1px solid gray;background-color:lightgrey;"><label>个人信息</label></td>
					</tr>
					<tr>
						<td><label>姓名:<%=addressbook.getName() %> </label> 
						</td>
						<%
							String idType=ImoUtilityData.IDTYPE(addressbook.getIdType());
						%>
						
						<td><label>证件类型:<%=idType  !=null ? idType : ""%></label></td>
						
						
					</tr>
					<tr>
						<td><label>出生日期:<%=addressbook.getBirthDate()!=null ? format.format(addressbook.getBirthDate()) : "" %> </label>
						</td>
						
						<td><label>证件号码:<%=addressbook.getNric() %></label> </td>
						
						
					</tr>

					<tr>
						<td>
						<%
						int age = 0;
						if(addressbook.getBirthDate()!=null){
							Calendar dob = Calendar.getInstance();  
							dob.setTime(addressbook.getBirthDate());  
							Calendar today = Calendar.getInstance();  
							 age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);  
							if (today.get(Calendar.MONTH) < dob.get(Calendar.MONTH)) {
							  age--;  
							} else if (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH)
							    && today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
							  age--;  
							}
						}
						%>
						<label>年龄:<%=age==0 ? "":age%></label>
						</td>
						<td><label>性别:
						<%
						String gen = "";
						if("M".equalsIgnoreCase( addressbook.getGender()))
							gen = "男";
						else if("F".equalsIgnoreCase( addressbook.getGender()))
							gen = "女";
						%>
						<%=gen %>
						</label></td>
						
						
					</tr>
					
					<tr>
								<%
							String education="";
					if(addressbook.getEducation()!=null){
						if(addressbook.getEducation().equalsIgnoreCase("1")){
							education="初中或以下";
						}else if(addressbook.getEducation().equalsIgnoreCase("2")){
							education="大专";
						}else if(addressbook.getEducation().equalsIgnoreCase("3")){
							education="中专或高中";
						}else if(addressbook.getEducation().equalsIgnoreCase("4")){
							education="大学";
						}else if(addressbook.getEducation().equalsIgnoreCase("5")){
							education="研究生或以上";
						}else if(addressbook.getEducation().equalsIgnoreCase("6")){
							education="不详";
						}
					}
					%>
					
						<td> <label>最高学历:<%=education!=null ? education : ""%></label>
						</td>
					<td><label>固定电话:<%=addressbook.getFixedLineNO() %></label> </td>
					</tr>
					
					<tr>
					
					<%
						String marStatus="";
					if(addressbook.getMarritalStatus()!=null){
						if(addressbook.getMarritalStatus().equalsIgnoreCase("s")){
							marStatus="未婚";
						}else if(addressbook.getMarritalStatus().equalsIgnoreCase("m")){
							marStatus="已婚";
						}else if(addressbook.getMarritalStatus().equalsIgnoreCase("p")){
							marStatus="离异";
						}else if(addressbook.getMarritalStatus().equalsIgnoreCase("w")){
							marStatus="丧偶";
						}
					}
					
					%>
						<td><label>婚姻状况:<%=marStatus!=null ?  marStatus : ""%></label></td>
						<td><label>手机:<%=addressbook.getMobilePhoneNo()!=null ? addressbook.getMobilePhoneNo() : "" %></label>
					</tr>
					
					<tr>
						<td><label>邮箱:<%=addressbook.geteMailId() !=null ? addressbook.geteMailId() : "" %></label>
						<td> <label>本地工作时间:<%=addressbook.getWorkingYearExperience()!=null ?  addressbook.getWorkingYearExperience() +"年" : ""%></label></td>
				
					</tr>
						<tr>
						<td > <label>年收入（万元):<%=addressbook.getYearlyIncome()!=null ? addressbook.getYearlyIncome() : "" %></label></td>
						<td></td>
						
					</tr>
					
					<tr>
						 <td><label>居住地址:<%=addressbook.getRegisteredAddress1()+","+addressbook.getRegisteredAddress2()+","+addressbook.getRegisteredAddress3()%></label></td>
						 <td></td>
					</tr>
					
					<tr>
					<%
					
					String sourcOfRefereal=InterviewAttendanceMaintenance.SOURCE_0F_REFERRAL(addressbook.getReferalSource());
					%>
					 <td><label>来源渠道:<%=sourcOfRefereal !=null ? localeObj.getTranslatedText(sourcOfRefereal) : ""  %></label> </td>
					 <%
					 	String newcomer=ImoUtilityData.NEWCOMER(addressbook.getNewComerSource());
					 %>
					  <td><label>新人来源:<%=newcomer !=null ? newcomer : "" %></label>
						</td>
					
					</tr>
					
					<tr>
					 <td><label>是否购买过保险:<%=addressbook.getPurchasedAnyInsurance() !=null ? addressbook.getPurchasedAnyInsurance() : "" %></label>
					 
					 <%
					 
					// System.out.println("**************************************************"+addressbook.getSalesExperience()); %>
					 
					  <td><label>是否有销售经验:<%=addressbook.getSalesExperience() !=null ? "是" : "否" %></label>
					</tr>
					
					
					<tr>
						<td colspan="2"></td>
					</tr>
					<tr>
						<!-- <td colspan="2" style=" border: 1px solid gray;background-color:lightgrey;"><label>FAMILY INFORMATION</label></td> -->
						<td colspan="2" style=" border: 1px solid gray;background-color:lightgrey;"><label>家庭信息</label></td>
					</tr>
					<%for(Iterator itr  = addressbook.getCandidateFamilyInfos().iterator() ; itr.hasNext() ; ){
				     CandidateFamilyInfo candidateFamilyInfo = (CandidateFamilyInfo) itr.next();%>
					<tr>
						<td><label>家庭成员:<%=candidateFamilyInfo.getName()!=null ? candidateFamilyInfo.getName() : ""%></label>
						</td>
						<td><label>单位:<%=candidateFamilyInfo.getUnit()!=null ?  candidateFamilyInfo.getUnit() : ""%></label>
						</td>
					</tr>
					<tr>
						<td><label>职位:<%=candidateFamilyInfo.getPosition()!=null ? candidateFamilyInfo.getPosition()  : "" %></label>
						</td>
						<td><label>关系:<%=candidateFamilyInfo.getRelationship()!=null? candidateFamilyInfo.getRelationship() : ""%></label>
						</td>
					</tr>
				 	 <tr>
						<td><label>职业:<%=candidateFamilyInfo.getOccupation()!=null ? candidateFamilyInfo.getOccupation() : ""%></label>
						</td>
						<td><label>电话:<%=candidateFamilyInfo.getPhoneNo()!=null ? candidateFamilyInfo.getPhoneNo() : ""%></label>
						</td>
					</tr>
					<%} %>
					<tr>
						<td colspan="2"></td>
					</tr>
					<tr>
						<!-- <td colspan="2" style=" border: 1px solid gray;background-color:lightgrey;"><label>WORK EXPERIENCE</label></td> -->
						<td colspan="2" style=" border: 1px solid gray;background-color:lightgrey;"><label>工作经历</label></td>
					</tr>
					<%for(Iterator itr  = addressbook.getCandidateWorkExperiences().iterator() ; itr.hasNext() ; ){
						CandidateWorkExperience candidateWorkExp=(CandidateWorkExperience)itr.next();%>
					<tr>
						<td><label>开始日期:<%=candidateWorkExp.getStartDate()!=null ? format.format(candidateWorkExp.getStartDate()) : ""%></label>
						</td>
						<td><label>结束日期:<%=candidateWorkExp.getEndDate()!=null ? format.format(candidateWorkExp.getEndDate()) : ""%></label>
						</td>
					</tr>
					<tr>
					<td><label>证明人:<%=candidateWorkExp.getWitness()!=null ? candidateWorkExp.getWitness() : ""%></label></td>
					<td><label>单位:<%=candidateWorkExp.getUnit()!=null ? candidateWorkExp.getUnit() : "" %></label></td>
					</tr>
				  <tr>
						<td><label>职业:<%=candidateWorkExp.getOccupation()!=null ? candidateWorkExp.getOccupation() : ""%></label></td>
						<td><label>证明人电话 :<%=candidateWorkExp.getWitnessContactNo()!=null ? candidateWorkExp.getWitnessContactNo() : "" %></label>	</td>
					</tr>
					  <tr>
						<td><label>收入:<%=candidateWorkExp.getIncome()!=null ? candidateWorkExp.getIncome() : ""%></label></td>
						<td><label>职位:<%=candidateWorkExp.getPosition()!=null ? candidateWorkExp.getPosition() : ""%></label></td>
						
					</tr>
					<%} %>
					<tr>
						<td colspan="2"></td>
					</tr>
					<tr>
						<!-- <td colspan="2" style=" border: 1px solid gray;background-color:lightgrey;"><label>EDUCATION</label></td> -->
						 <td colspan="2" style=" border: 1px solid gray;background-color:lightgrey;"><label>教育经历</label></td>
					</tr>
					<%	for(Iterator itr  = addressbook.getCandidateEducations().iterator(); itr.hasNext() ; ){
						CandidateEducation candidateEducation= (CandidateEducation)itr.next();%>
					<tr>
						<td><label>开始日期:<%=candidateEducation.getStartDate()!=null? format.format(candidateEducation.getStartDate()):""%></label>
						</td>
						<td><label>结束日期:<%=candidateEducation.getEndDate()!=null ? format.format(candidateEducation.getEndDate()) : ""%></label>
						</td>
					</tr>
					<tr>
					<td><label>证明人:<%=candidateEducation.getWitness()!=null ? candidateEducation.getWitness() : ""%></label></td>
					<td><label>最高学历:<%=candidateEducation.getEducation()!=null ? candidateEducation.getEducation() : ""%></label></td>
					</tr>
					<tr>
					<td><label>学位:<%=candidateEducation.getEducationLevel()!=null ? candidateEducation.getEducationLevel() : ""%></label></td>
					<td><label>学校:<%=candidateEducation.getSchool()!=null ? candidateEducation.getSchool() : ""%></label></td>
					</tr>
					<tr>
					<td><label>证明人电话:<%=candidateEducation.getWitnessContactNo()!=null ? candidateEducation.getWitnessContactNo() : ""%></label></td>
					<td></td>
					</tr>
					<%} %>
					<tr>
						<td colspan="2"></td>
					</tr>
					<tr>
						<!-- <td colspan="2" style=" border: 1px solid gray;background-color:lightgrey;"><label>PERSONAL CERTIFICATION</label></td> -->
						<td colspan="2" style=" border: 1px solid gray;background-color:lightgrey;"><label>专业认证</label></td>
					</tr>
					<%	for(Iterator itr  = addressbook.getCandidateProfessionalCertifications().iterator() ; itr.hasNext() ; ){
						CandidateProfessionalCertification procertification=(CandidateProfessionalCertification)itr.next();%>
					<tr>
						<td><label>证书名称:<%=procertification.getCertificateName()!=null ? procertification.getCertificateName() : ""%></label>
						</td>
						<td><label>授证机构:<%=procertification.getCharterAgency()!=null ? procertification.getCharterAgency() : ""%></label>
						</td>
					</tr>
					<tr>
					<td><label>授证日期:<%=procertification.getCharterDate()!=null?format.format(procertification.getCharterDate()) : ""%></label></td>
					<td></td>
					</tr>
					<%} %>
					
					<tr>
						<td colspan="2"></td>
					</tr>
					<tr>
						<!-- <td colspan="2" style=" border: 1px solid gray;background-color:lightgrey;"><label>E-singnature</label></td> -->
						<td colspan="2" style=" border: 1px solid gray;background-color:lightgrey;"><label>电子签名</label></td>
					</tr>
					<%
					CandidateESignature esignature=new CandidateESignature();
			 		if(addressbook.getCandidateESignatures().size()>0){
			 			Iterator itr=addressbook.getCandidateESignatures().iterator();
			 			esignature=(CandidateESignature)itr.next();
			 		}%>
					<tr>
						<td><label>分公司:<%=esignature.getBranch()%><br></label>
						</td>
						<td><label>服务部:<%=esignature.getServiceDepartment()!=null ? esignature.getServiceDepartment() : ""%></label>
						</td>
					</tr>
					<tr>
						<td><label>城市:<%=esignature.getCity()!=null ? esignature.getCity() : ""%></label>
						</td>
						<td><label>代码:<%=esignature.getAgentId()!=null ? esignature.getAgentId() : ""%></label>
						</td>
					</tr>
						<tr>
						<%
						String ins = "";
						if(esignature.getAtachedWithInsuranceCo()!=null && esignature.getAtachedWithInsuranceCo())
							ins = "是";
						else
							ins = "否";
						%>
						<!-- <td colspan="2">Presently attached with another insurance Company ? Yes No</td> -->
						<td colspan="2">目前是否正同时与其他保险公司联络?(<%=ins %>)</td>
						<td></td>
					</tr>
					<tr>
					<%if(esignature.getContactWithAia()!=null && esignature.getContactWithAia())
							ins = "是";
						else
							ins = "否";
						%>
						<!-- <td colspan="2">Presently in contact with any other AIA'S servicing Department ? Yes No</td> -->
						<td colspan="2">目前是否正同时与友邦保险其他业务主管接洽?（若是，请写明姓名） (<%=ins %>)</td>
						<td></td>
					</tr>
					<tr>
					<%if(esignature.getTakenPspTest()!=null && esignature.getTakenPspTest())
							ins = "是";
						else
							ins = "否";
						%>
						<!-- <td colspan="2">Taken LOMBRA occupational test or PSP test in the past ? If Yes, Yes No</td> -->
						<td colspan="2">过去是否曾经接受过LIMRA职业选择（CC）测评或个人风格（PSP）测评？（若是，请写明结果）(<%=ins %>)</td>
						<td></td>
					</tr>
					<!-- 	<tr>
						 <td colspan="2">Please provide the result.</td>
						<td colspan="2">请提供结果.</td>
						<td></td>
					</tr> -->
					<tr>
						<td colspan="2">申请人声明</td>
						<td></td>
					</tr>
						<tr>
						<td>申请日期:<%=esignature.getApplicationDate()!=null ? format.format(esignature.getApplicationDate()) : ""%>
						</td>
						<td>申请人:<%=esignature.getCandidateName()!=null?esignature.getCandidateName():"" %></td>
					</tr>
					
					<tr>
						<td>电子签名:</td>
						<td>
						<%
						String path = interviewMaintenance.getmaterialFile(esignature,request);
							if(!path.equals("#")){
							
						%>
						<img src=<%=path %> style="width:90px;" />
						<%} %>
						</td>
					</tr>
				</table>
				<br>
			</form>
		</div>
	</div>
<%}catch(Exception e){
	
	e.printStackTrace();
	LogsMaintenance logsMain=new LogsMaintenance();
	StringWriter errors = new StringWriter();
	e.printStackTrace(new PrintWriter(errors));
	logsMain.insertLogs("ViewEApplicationForm jsp page ","SEVERE"+"",errors.toString());
	
} %>
