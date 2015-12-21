<%--
  - Author(s):          Nibedita
  - Date:               21-Dec-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:        Adding  Eapplication form page
--%>
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
		</div>
		<div class="content" style="background-color: #ffffff;">
			<form name="interviewForm" method="post" action="FormManager"
				class="PT20">
				<table class="formDesign" style=" width: 50%;">
					<tr>
						<td colspan="2" style=" border: 1px solid gray;background-color:lightgrey;"><label>PERSONAL INFORMATION</label></td>
					</tr>
					<tr>
						<td><label>Candidate's Name :<%=addressbook.getName() %> <br></label>姓名<span style="color: #ec2028;">(*)</span> 
						</td>
						<td><label>NRIC:<%=addressbook.getNric() %><br>证件号码<span style="color: #ec2028;">(*)</span></label> </td>
					</tr>
					<tr>
						<td><label>Date of Birth:<%=addressbook.getBirthDate()!=null ? "" : format.format(addressbook.getBirthDate())%><br>出生日期<span style="color: #ec2028;">(*) </span> </label>
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
						<br>性别<span style="color: #ec2028;">(*) </span> </label></td>
					</tr>

					<tr>
						<td><label>Age:<%=addressbook.getAge() %><br>年龄<span style="color: #ec2028;">(*)</span> </label>
						</td>
						<td><label>Place of Birth:<%=addressbook.getBirthPlace()!=null?addressbook.getBirthPlace() : ""%></label></td>
					</tr>
					<tr>
						<td> <label>Education:<%=addressbook.getEducation()!=null ? addressbook.getEducation() : ""%><br>最高学历<span style="color: #ec2028;">(*)</span></label>
						</td>
					
						<td><label>Maritial Status:<%=addressbook.getMarritalStatus()!=null ?  addressbook.getMarritalStatus() : ""%><br>婚姻状况<span style="color: #ec2028;">(*) </span></label></td>
					</tr>

					<tr>
						<td > <label>Annual Income:<%=addressbook.getYearlyIncome()!=null ? addressbook.getYearlyIncome() : "" %><br>年收入<span style="color: #ec2028;">(*) </span></label>
						</td>
						<td> <label>Work Experience:<%=addressbook.getWorkingYearExperience()!=null ?  addressbook.getWorkingYearExperience() : ""%><br>本地工作时间<span style="color: #ec2028;">(*) </span></label></td>
					</tr>

					<tr>
						<td><label> Address:<%=addressbook.getResidentialAddress1() +","+addressbook.getResidentialAddress2()+"," +addressbook.getResidentialAddress3()%><br>居住地址<span style="color: #ec2028;">(*) </span></label>
						</td>
						<td></td>
					</tr>
					<tr>
						<td><label>Postal Code:<%=addressbook.getResidentialPostalCode()!=null ? addressbook.getResidentialPostalCode() : "" %><br>居住地址<span style="color:#ec2028;">(*) </span></label>
						</td>
						<td></td>
					</tr>
					<tr>
						<td><label>Mobile Number:<%=addressbook.getMobilePhoneNo()!=null ? addressbook.getMobilePhoneNo() : "" %><br></label>
						</td>
						<td>
						</td>
					</tr>
					<tr>
						<td><label>Email Address:<%=addressbook.geteMailId() !=null ? addressbook.geteMailId() : "" %></label>
						</td>
						<td>
						</td>
					</tr>
					<tr>
						<td colspan="2"></td>
					</tr>
					<tr>
						<td colspan="2" style=" border: 1px solid gray;background-color:lightgrey;"><label>FAMILY INFORMATION</label></td>
					</tr>
					<%for(Iterator itr  = addressbook.getCandidateFamilyInfos().iterator() ; itr.hasNext() ; ){
				     CandidateFamilyInfo candidateFamilyInfo = (CandidateFamilyInfo) itr.next();%>
					<tr>
						<td><label>Name:<%=candidateFamilyInfo.getName()!=null ? candidateFamilyInfo.getName() : ""%></label>
						</td>
						<td><label>Unit:<%=candidateFamilyInfo.getUnit()!=null ?  candidateFamilyInfo.getUnit() : ""%></label>
						</td>
					</tr>
					<tr>
						<td><label>Position:<%=candidateFamilyInfo.getPosition()!=null ? candidateFamilyInfo.getPosition()  : "" %></label>
						</td>
						<td><label>Relationship:<%=candidateFamilyInfo.getRelationship()!=null? candidateFamilyInfo.getRelationship() : ""%></label>
						</td>
					</tr>
				  <tr>
						<td><label>Occupation:<%=candidateFamilyInfo.getOccupation()!=null ? candidateFamilyInfo.getOccupation() : ""%></label>
						</td>
						<td><label>Phone:<%=candidateFamilyInfo.getPhoneNo()!=null ? candidateFamilyInfo.getPhoneNo() : ""%></label>
						</td>
					</tr>
					<%} %>
					<tr>
						<td colspan="2"></td>
					</tr>
					<tr>
						<td colspan="2" style=" border: 1px solid gray;background-color:lightgrey;"><label>WORK EXPERIENCE</label></td>
					</tr>
					<%for(Iterator itr  = addressbook.getCandidateWorkExperiences().iterator() ; itr.hasNext() ; ){
						CandidateWorkExperience candidateWorkExp=(CandidateWorkExperience)itr.next();%>
					<tr>
						<td><label>Start Date:<%=candidateWorkExp.getStartDate()!=null ? format.format(candidateWorkExp.getStartDate()) : ""%></label>
						</td>
						<td><label>End Date:<%=candidateWorkExp.getEndDate()!=null ? format.format(candidateWorkExp.getEndDate()) : ""%></label>
						</td>
					</tr>
					<tr>
					<td><label>Witness:<%=candidateWorkExp.getWitness()!=null ? candidateWorkExp.getWitness() : ""%></label></td>
					<td><label>Unit:<%=candidateWorkExp.getUnit()!=null ? candidateWorkExp.getUnit() : "" %></label></td>
					</tr>
				  <tr>
						<td><label>Occupation:<%=candidateWorkExp.getOccupation()!=null ? candidateWorkExp.getOccupation() : ""%></label></td>
						<td><label>Witness Contact Number :<%=candidateWorkExp.getWitnessContactNo()!=null ? candidateWorkExp.getWitnessContactNo() : "" %></label>	</td>
					</tr>
					  <tr>
						<td><label>Income:<%=candidateWorkExp.getIncome()!=null ? candidateWorkExp.getIncome() : ""%></label></td>
						<td><label>Position:<%=candidateWorkExp.getPosition()!=null ? candidateWorkExp.getPosition() : ""%></label></td>
						
					</tr>
					<%} %>
					<tr>
						<td colspan="2"></td>
					</tr>
					<tr>
						<td colspan="2" style=" border: 1px solid gray;background-color:lightgrey;"><label>PERSONAL CERTIFICATION</label></td>
					</tr>
					<%	for(Iterator itr  = addressbook.getCandidateProfessionalCertifications().iterator() ; itr.hasNext() ; ){
						CandidateProfessionalCertification procertification=(CandidateProfessionalCertification)itr.next();%>
					<tr>
						<td><label>Certificate Name:<%=procertification.getCertificateName()!=null ? procertification.getCertificateName() : ""%></label>
						</td>
						<td><label>Charter Agency:<%=procertification.getCharterAgency()!=null ? procertification.getCharterAgency() : ""%></label>
						</td>
					</tr>
					<tr>
					<td><label>Charter Date:<%=procertification.getCharterDate()!=null?format.format(procertification.getCharterDate()) : ""%></label></td>
					<td></td>
					</tr>
					<%} %>
					<tr>
						<td colspan="2"></td>
					</tr>
						<tr>
						<td colspan="2" style=" border: 1px solid gray;background-color:lightgrey;"><label>E-singnature </label></td>
					</tr>
					<%
					CandidateESignature esignature=new CandidateESignature();
			 		if(addressbook.getCandidateESignatures().size()>0){
			 			Iterator itr=addressbook.getCandidateESignatures().iterator();
			 			esignature=(CandidateESignature)itr.next();
			 		}%>
					<tr>
						<td><label>Branch :<%=esignature.getBranch()%></label>
						</td>
						<td><label>Servicing Department :<%=esignature.getServiceDepartment()!=null ? esignature.getServiceDepartment() : ""%></label>
						</td>
					</tr>
					<tr>
						<td><label>City :<%=esignature.getCity()!=null ? esignature.getCity() : ""%></label>
						</td>
						<td><label>Agent Code :<%=esignature.getAgentId()!=null ? esignature.getAgentId() : ""%></label>
						</td>
					</tr>
					<tr>
						<td colspan="2"></td>
					</tr>
				<tr>
						<td colspan="2" style=" border: 1px solid gray;background-color:lightgrey;"><label>EDUCATION</label></td>
					</tr>
					<%	for(Iterator itr  = addressbook.getCandidateEducations().iterator(); itr.hasNext() ; ){
						CandidateEducation candidateEducation= (CandidateEducation)itr.next();%>
					<tr>
						<td><label>Start Date:<%=candidateEducation.getStartDate()!=null? format.format(candidateEducation.getStartDate()):""%></label>
						</td>
						<td><label>End Date:<%=candidateEducation.getEndDate()!=null ? format.format(candidateEducation.getEndDate()) : ""%></label>
						</td>
					</tr>
					<tr>
					<td><label>Witness:<%=candidateEducation.getWitness()!=null ? candidateEducation.getWitness() : ""%></label></td>
					<td><label>Education:<%=candidateEducation.getEducation()!=null ? candidateEducation.getEducation() : ""%></label></td>
					</tr>
					<tr>
					<td><label>Education Level:<%=candidateEducation.getEducationLevel()!=null ? candidateEducation.getEducationLevel() : ""%></label></td>
					<td><label>School:<%=candidateEducation.getSchool()!=null ? candidateEducation.getSchool() : ""%></label></td>
					</tr>
					<tr>
					<td><label>Witness Contact Number:<%=candidateEducation.getWitnessContactNo()!=null ? candidateEducation.getWitnessContactNo() : ""%></label></td>
					<td></td>
					</tr>
					<%} %>
						<tr>
						<td colspan="2">Presently attached with another insurance Company ? Yes No
						</td>
						<td></td>
					</tr>
					<tr>
						<td colspan="2">Presently in contact with any other AIA'S servicing Department ? Yes No
						</td>
						<td></td>
					</tr>
					<tr>
						<td colspan="2">Taken LOMBRA occupational test or PSP test in the past ? If Yes, Yes No
						</td>
						<td></td>
					</tr>
						<tr>
						<td colspan="2">Please provide the result.
						</td>
						<td></td>
					</tr>
					<tr>
						<td colspan="2">Applicant's Declaration	</td>
						<td></td>
					</tr>
						<tr>
						<td>Application Date:<br><%=esignature.getApplicationDate()!=null ? format.format(esignature.getApplicationDate()) : ""%>
						</td>
						<td>Applicant/Candidate Name :<br><%=esignature.getCandidateName() %></td>
					</tr>
					
					<tr>
						<td>E-Signature :
						</td>
						<td>
						<%
						String path = interviewMaintenance.getmaterialFile(esignature,request);
						%>
						<img src=<%=path %> style="width:90px;" /></td>
					</tr>
				</table>
				<br>
			</form>
		</div>
	</div>

