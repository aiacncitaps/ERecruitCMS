<%--
  - Author(s):          Nibedita
  - Date:               21-Dec-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:        Adding  Eapplication form page
--%>
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
InterviewAttendanceMaintenance interviewMaint=new InterviewAttendanceMaintenance();
String interviewCandidateCode=interviewMaint.getInterviewCandidateCode(Integer.parseInt(request.getParameter("candidateCode")));
AddressBook addressbook=new AddressBook();
addressbook.setAddressCode(Integer.parseInt(interviewCandidateCode));
addressbook=addressMain.getAddressBook(addressbook);
SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy");
InterviewMaintenance interviewMaintenance = new InterviewMaintenance();
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
						<td><label>Candidate's Name:<%=addressbook.getName() %> <br>姓名:<span style="color: #ec2028;">(*)</span></label> 
						</td>
						<td><label>NRIC:<%=addressbook.getNric() %><br>证件号码:<span style="color: #ec2028;">(*)</span></label> </td>
					</tr>
					<tr>
						<td><label>Date of Birth:<%=addressbook.getBirthDate()!=null ? "" : format.format(addressbook.getBirthDate())%><br>出生日期:<span style="color: #ec2028;">(*) </span> </label>
						</td>
						<td><label>Gender:
						<%
						String gen = "";
						if("M".equalsIgnoreCase( addressbook.getGender()))
							gen = "Male";
						else if("F".equalsIgnoreCase( addressbook.getGender()))
							gen = "Female";
						%>
						<%=gen %>
						<br>性别:<span style="color: #ec2028;">(*) </span> </label></td>
					</tr>

					<tr>
						<td><label>Age:<%=addressbook.getAge()!=null ? addressbook.getAge()!=null : "" %><br>年龄:<span style="color: #ec2028;">(*)</span> </label>
						</td>
						<td><label>Registered Address:<%=addressbook.getRegisteredAddress2()+","+addressbook.getRegisteredAddress3()%><br>注册地址:</label></td>
					</tr>
					<tr>
						<td> <label>Education:<%=addressbook.getEducation()!=null ? addressbook.getEducation() : ""%><br>最高学历<span style="color: #ec2028;">(*)</span></label>
						</td>
					
						<td><label>Maritial Status:<%=addressbook.getMarritalStatus()!=null ?  addressbook.getMarritalStatus() : ""%><br>未婚:<span style="color: #ec2028;">(*) </span></label></td>
					</tr>

					<tr>
						<td > <label>Annual Income:<%=addressbook.getYearlyIncome()!=null ? addressbook.getYearlyIncome() : "" %><br>年收入:<span style="color: #ec2028;">(*) </span></label>
						</td>
						<td> <label>Work Experience:<%=addressbook.getWorkingYearExperience()!=null ?  addressbook.getWorkingYearExperience() : ""%><br>本地工作时间:<span style="color: #ec2028;">(*) </span></label></td>
					</tr>

					<tr>
						<td><label> Address:<%=addressbook.getResidentialAddress1() +","+addressbook.getResidentialAddress2()+"," +addressbook.getResidentialAddress3()%><br>居住地址:<span style="color: #ec2028;">(*) </span></label>
						</td>
						<td></td>
					</tr>
					<%-- <tr>
						<td><label>Postal Code:<%=addressbook.getResidentialPostalCode()!=null ? addressbook.getResidentialPostalCode() : "" %><br>居住地址<span style="color:#ec2028;">(*) </span></label>
						</td>
						<td></td>
					</tr> --%>
					<tr>
						<td><label>Mobile Number:<%=addressbook.getMobilePhoneNo()!=null ? addressbook.getMobilePhoneNo() : "" %><br>手机号:</label>
						</td>
						<td>
						</td>
					</tr>
					<tr>
						<td><label>Email Address:<%=addressbook.geteMailId() !=null ? addressbook.geteMailId() : "" %><br>电子邮件:</label>
						</td>
						<td>
						</td>
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
						<td><label>Name:<%=candidateFamilyInfo.getName()!=null ? candidateFamilyInfo.getName() : ""%><br>名字:</label>
						</td>
						<td><label>Unit:<%=candidateFamilyInfo.getUnit()!=null ?  candidateFamilyInfo.getUnit() : ""%><br>单元:</label>
						</td>
					</tr>
					<tr>
						<td><label>Position:<%=candidateFamilyInfo.getPosition()!=null ? candidateFamilyInfo.getPosition()  : "" %><br>位置:</label>
						</td>
						<td><label>Relationship:<%=candidateFamilyInfo.getRelationship()!=null? candidateFamilyInfo.getRelationship() : ""%><br>关系:</label>
						</td>
					</tr>
				 	 <tr>
						<td><label>Occupation:<%=candidateFamilyInfo.getOccupation()!=null ? candidateFamilyInfo.getOccupation() : ""%><br>占用:</label>
						</td>
						<td><label>Phone:<%=candidateFamilyInfo.getPhoneNo()!=null ? candidateFamilyInfo.getPhoneNo() : ""%><br>电话:</label>
						</td>
					</tr>
					<%} %>
					<tr>
						<td colspan="2"></td>
					</tr>
					<tr>
						<!-- <td colspan="2" style=" border: 1px solid gray;background-color:lightgrey;"><label>WORK EXPERIENCE</label></td> -->
						<td colspan="2" style=" border: 1px solid gray;background-color:lightgrey;"><label>工作经验</label></td>
					</tr>
					<%for(Iterator itr  = addressbook.getCandidateWorkExperiences().iterator() ; itr.hasNext() ; ){
						CandidateWorkExperience candidateWorkExp=(CandidateWorkExperience)itr.next();%>
					<tr>
						<td><label>Start Date:<%=candidateWorkExp.getStartDate()!=null ? format.format(candidateWorkExp.getStartDate()) : ""%><br>开始 日期:</label>
						</td>
						<td><label>End Date:<%=candidateWorkExp.getEndDate()!=null ? format.format(candidateWorkExp.getEndDate()) : ""%><br>结束  日期:</label>
						</td>
					</tr>
					<tr>
					<td><label>Witness:<%=candidateWorkExp.getWitness()!=null ? candidateWorkExp.getWitness() : ""%><br>见证:</label></td>
					<td><label>Unit:<%=candidateWorkExp.getUnit()!=null ? candidateWorkExp.getUnit() : "" %><br>单元:</label></td>
					</tr>
				  <tr>
						<td><label>Occupation:<%=candidateWorkExp.getOccupation()!=null ? candidateWorkExp.getOccupation() : ""%><br>占用:</label></td>
						<td><label>Witness Contact Number :<%=candidateWorkExp.getWitnessContactNo()!=null ? candidateWorkExp.getWitnessContactNo() : "" %><br>见证 联系 号码:</label>	</td>
					</tr>
					  <tr>
						<td><label>Income:<%=candidateWorkExp.getIncome()!=null ? candidateWorkExp.getIncome() : ""%><br>收入:</label></td>
						<td><label>Position:<%=candidateWorkExp.getPosition()!=null ? candidateWorkExp.getPosition() : ""%><br>位置:</label></td>
						
					</tr>
					<%} %>
					<tr>
						<td colspan="2"></td>
					</tr>
					<tr>
						<!-- <td colspan="2" style=" border: 1px solid gray;background-color:lightgrey;"><label>PERSONAL CERTIFICATION</label></td> -->
						<td colspan="2" style=" border: 1px solid gray;background-color:lightgrey;"><label>个人认证</label></td>
					</tr>
					<%	for(Iterator itr  = addressbook.getCandidateProfessionalCertifications().iterator() ; itr.hasNext() ; ){
						CandidateProfessionalCertification procertification=(CandidateProfessionalCertification)itr.next();%>
					<tr>
						<td><label>Certificate Name:<%=procertification.getCertificateName()!=null ? procertification.getCertificateName() : ""%><br>证书 名称:</label>
						</td>
						<td><label>Charter Agency:<%=procertification.getCharterAgency()!=null ? procertification.getCharterAgency() : ""%><br>宪章 代理:</label>
						</td>
					</tr>
					<tr>
					<td><label>Charter Date:<%=procertification.getCharterDate()!=null?format.format(procertification.getCharterDate()) : ""%><br>宪章 日期:</label></td>
					<td></td>
					</tr>
					<%} %>
					
					<tr>
						<td colspan="2"></td>
					</tr>
					<tr>
						<!-- <td colspan="2" style=" border: 1px solid gray;background-color:lightgrey;"><label>EDUCATION</label></td> -->
						 <td colspan="2" style=" border: 1px solid gray;background-color:lightgrey;"><label>学历</label></td>
					</tr>
					<%	for(Iterator itr  = addressbook.getCandidateEducations().iterator(); itr.hasNext() ; ){
						CandidateEducation candidateEducation= (CandidateEducation)itr.next();%>
					<tr>
						<td><label>Start Date:<%=candidateEducation.getStartDate()!=null? format.format(candidateEducation.getStartDate()):""%><br>开始 日期:</label>
						</td>
						<td><label>End Date:<%=candidateEducation.getEndDate()!=null ? format.format(candidateEducation.getEndDate()) : ""%><br>结束  日期:</label>
						</td>
					</tr>
					<tr>
					<td><label>Witness:<%=candidateEducation.getWitness()!=null ? candidateEducation.getWitness() : ""%><br>见证:</label></td>
					<td><label>Education:<%=candidateEducation.getEducation()!=null ? candidateEducation.getEducation() : ""%><br>学历:</label></td>
					</tr>
					<tr>
					<td><label>Education Level:<%=candidateEducation.getEducationLevel()!=null ? candidateEducation.getEducationLevel() : ""%><br>教育程度:</label></td>
					<td><label>School:<%=candidateEducation.getSchool()!=null ? candidateEducation.getSchool() : ""%><br>学校:</label></td>
					</tr>
					<tr>
					<td><label>Witness Contact Number:<%=candidateEducation.getWitnessContactNo()!=null ? candidateEducation.getWitnessContactNo() : ""%><br>见证 联系 号码:</label></td>
					<td></td>
					</tr>
					<%} %>
					<tr>
						<td colspan="2"></td>
					</tr>
					<tr>
						<!-- <td colspan="2" style=" border: 1px solid gray;background-color:lightgrey;"><label>E-singnature</label></td> -->
						<td colspan="2" style=" border: 1px solid gray;background-color:lightgrey;"><label>E- singnature</label></td>
					</tr>
					<%
					CandidateESignature esignature=new CandidateESignature();
			 		if(addressbook.getCandidateESignatures().size()>0){
			 			Iterator itr=addressbook.getCandidateESignatures().iterator();
			 			esignature=(CandidateESignature)itr.next();
			 		}%>
					<tr>
						<td><label>Branch:<%=esignature.getBranch()%><br>支公司:</label>
						</td>
						<td><label>Servicing Department:<%=esignature.getServiceDepartment()!=null ? esignature.getServiceDepartment() : ""%><br>维修部:</label>
						</td>
					</tr>
					<tr>
						<td><label>City:<%=esignature.getCity()!=null ? esignature.getCity() : ""%><br>市:</label>
						</td>
						<td><label>Agent Code:<%=esignature.getAgentId()!=null ? esignature.getAgentId() : ""%><br>代理 代码:</label>
						</td>
					</tr>
						<tr>
						<!-- <td colspan="2">Presently attached with another insurance Company ? Yes No</td> -->
						<td colspan="2">目前连接与另一家保险公司 ? 是  没有</td>
						<td></td>
					</tr>
					<tr>
						<!-- <td colspan="2">Presently in contact with any other AIA'S servicing Department ? Yes No</td> -->
						<td colspan="2">目前与任何其他友邦保险的维修部联系 ? 是  没有</td>
						<td></td>
					</tr>
					<tr>
						<!-- <td colspan="2">Taken LOMBRA occupational test or PSP test in the past ? If Yes, Yes No</td> -->
						<td colspan="2">在过去采取LOMBRA职业测试或PSP测试 ? 如是，是  没有</td>
						<td></td>
					</tr>
						<tr>
						<!-- <td colspan="2">Please provide the result.</td> -->
						<td colspan="2">请提供结果.</td>
						<td></td>
					</tr>
					<tr>
						<td colspan="2">Applicant's Declaration<br>申请人的声明</td>
						<td></td>
					</tr>
						<tr>
						<td>Application Date:<br>申请日期:<br><%=esignature.getApplicationDate()!=null ? format.format(esignature.getApplicationDate()) : ""%>
						</td>
						<td>Applicant/Candidate Name:<br>您的姓名:<br><%=esignature.getCandidateName()!=null?esignature.getCandidateName():"" %></td>
					</tr>
					
					<tr>
						<td>E-Signature :
						</td>
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
