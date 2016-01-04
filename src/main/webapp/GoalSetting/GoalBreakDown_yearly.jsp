<%--
  - Author(s):          Jay
  - Date:               07-May-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:        GUI to search Announcement
--%>
<%@page import="com.quix.aia.cn.imo.mapper.GoalConfigMaintenance"%>
<%@page import="com.quix.aia.cn.imo.data.goalsetting.GoalConfig"%>
<%@page import="com.quix.aia.cn.imo.constants.SessionAttributes"%>
<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>
<%@page import="com.quix.aia.cn.imo.data.goalsetting.Individual_Goal"%>
<%@page import="com.quix.aia.cn.imo.data.goalsetting.GoalBreakDown"%>
<%@page import="java.util.*"%>
<%
LocaleObject localeObj = (LocaleObject)session.getAttribute(SessionAttributes.LOCALE_OBJ);
Individual_Goal goalObj=new Individual_Goal();
if(request.getAttribute("goal_object")!=null){
	goalObj=(Individual_Goal)request.getAttribute("goal_object");
}
request.removeAttribute("goal_object");
ArrayList<GoalBreakDown> goal_list=null;
if(request.getAttribute("breakDownobject")!=null){
	goal_list=(ArrayList<GoalBreakDown>)request.getAttribute("breakDownobject");
			}
request.removeAttribute("breakDownobject");
Map<String, GoalConfig> map = new HashMap();
GoalConfigMaintenance goalConfigMaintenance = new GoalConfigMaintenance();
map = goalConfigMaintenance.getConfigData();

GoalConfig goalConfig = new GoalConfig();
goalConfig = (GoalConfig)map.get("goalConfigation");

int phase_8=0,phase_7=0,phase_6=0,phase_5=0,phase_4=0,phase_3=0,phase_2=0,phase_1=0;
if(goal_list.size()==0){
	phase_8=Math.round(((float)goalObj.getTotal()*100)/goalConfig.getAttendedTraining());
	phase_7=Math.round(((float)phase_8*100)/goalConfig.getPassALE());
	phase_6=Math.round(((float)phase_7*100)/goalConfig.getCompanyInterview());
	phase_5=Math.round(((float)phase_6*100)/goalConfig.getCcAssessment());
	phase_4=Math.round(((float)phase_5*100)/goalConfig.getEopAttendance());
	phase_3=Math.round(((float)phase_4*100)/goalConfig.getFirstInterview());
	phase_2=Math.round(((float)phase_3*100)/goalConfig.getEopRegistration());
	phase_1=Math.round(((float)phase_2*100)/goalConfig.getPotentialCandidate());
	
}
%>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/interface/Individual_Goal.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<style>

#total1 {
    border: 2px solid #a1a1a1;
    padding: 10px 40px; 
    background:gray;
    width:50px;
     border-top-left-radius: 2em;
     border-bottom-left-radius: 2em;
}
#total2 {
    border: 2px solid #a1a1a1;
    padding: 10px 40px; 
    background: #dddddd;
    width:50px;
     border-top-right-radius: 2em;
     border-bottom-right-radius: 2em;
}

#submit_b {
    border: 2px solid #a1a1a1;
    padding: 10px 40px; 
    background:#dddddd;
    width:50px;
    border-radius: 25px;
    color:gray;
    text-decoration: none !important;
}

#red{
    border: 2px solid #a1a1a1;
    padding: 10px 40px; 
    background:#d31145;
    width:50px;
    border-radius: 25px;
    color: white;
    text-decoration: none !important;
}
#greay{
    border: 2px solid #a1a1a1;
    padding: 10px 40px; 
    background:#dddddd;
    width:50px;
    border-radius: 25px;
   color:gray;
    text-decoration: none !important;
}
input[type='radio'] {
    -webkit-appearance:none;
    width:20px;
    height:20px;
    border:1px solid green;
    border-radius:50%;
    outline:none;
    box-shadow:0 0 5px 0px gray inset;
}
input[type='radio']:hover {
    box-shadow:0 0 5px 0px orange inset;
}
input[type='radio']:before {
    content:'';
    display:block;
    width:60%;
    height:60%;
    margin: 20% auto;    
    border-radius:50%;    
}
input[type='radio']:checked:before {
    background:green;
}
</style>
<script>
//css for row odd even
$(function(){
	$(".data_table tr:odd").addClass("odd");
    $(".data_table tr:not(.odd)").addClass("even");  
});
function getNumberOnly(obj){
	//var total="";
	var filter = /^[0-9-+]+$/;
	if (isNaN(obj.value))
	{
		obj.value="";
	}
    if (filter.test(obj.value)) {
	}else{
		obj.value="";
	}
   
	
}
/*function getTotalValue(obj){
	//var total="";
	var filter = /^[0-9-+]+$/;
	if (isNaN(obj.value))
	{
		obj.value="";
	}
    if (filter.test(obj.value)) {
	}else{
		obj.value="";
	}
   
	
}*/
 function submitGoalBreakDownYear(){
	 var agent_code="123";//temp value
		var for_year='2015';
	 var selectedVal = new Array();
	 selectedVal.push($('#phase_1').val());
	 selectedVal.push($('#phase_2').val());
	 selectedVal.push($('#phase_3').val());
	 selectedVal.push($('#phase_4').val());
	 selectedVal.push($('#phase_5').val());
	 selectedVal.push($('#phase_6').val());
	 selectedVal.push($('#phase_7').val());
	 selectedVal.push($('#phase_8').val());
	 selectedVal.push($('#phase_9').val());
	 Individual_Goal.insertGoalBreakDown(agent_code,for_year,selectedVal,'<%=request%>', {
				callback : function(str) {
					msg=str.msg;
					$('#msg').html(msg);
				}
			});
}
function goToYearBreakDown(){
	window.location.href="ContentManager?key=GoalBreakDown_Y";
}
function goToMonthBreakDown(){
	window.location.href="ContentManager?key=GoalBreakDown_M";
}

</script>


   	<div id="maincontainer">	
   		
   				
   					<div class="head"><h2 align="center"><%=localeObj.getTranslatedText("GOAL SETTING")%></h2></div>  	
   							<div class="content" style="background-color:#ffffff;">	 
   												  
                           				
                                       				
                                                     <div class="clear"></div>

                                	  <br>
                                	  <div id="msg" style="border: none;color:red;" ></div>
                                	 <div class="form-item MT25 ML10" style="/* margin-left: -700px; */float:left;">
                            		
                            				<a href="ContentManager?key=IndividualGoal_Y" id="greay"><%=localeObj.getTranslatedText("Individual Goal")%></a>
                                			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="ContentManager?key=GoalBreakDown_Y" id="red" /><%=localeObj.getTranslatedText("Goal BreakDown")%></a>
                          			        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="ContentManager?key=TeamGoal_y" id="greay" /><%=localeObj.getTranslatedText("Team Goal")%></a>
                                	 </div> 
                                	  <div class="clear"></div>
                                	  <br/>
                                	  <br/>
                                	   <div>
                                	 <table width="35%" style="border: none;" align="left"  >
                            	     <tr>                         
                                     <td  style="border: none; padding-right:15px;width:35%;" align="left">                                    				
	                          		  <input type="radio" name="icons" value="1" checked="checked" onClick="javascript:goToYearBreakDown();"> <%=localeObj.getTranslatedText("Yearly Goal")%>
                                     </td>
                                     <td  style="border: none; padding-left:25px;" colspan="10" align="left">  
										<input type="radio" name="icons" value="1" onClick="javascript:goToMonthBreakDown();"  > <%=localeObj.getTranslatedText("Monthly Goal")%>
                                     </td>   
                                       	 			 
                                       	 			                                 			   
                       			       </tr>
                          			          </table>
                                	</div>

                                      
                                 <br><br><br><br><br>
           	<div class="data_table  MT20 MB20 PT20" style="width:100%" >
           		<table class="subFormDesign" style="width:100%">
           		<thead>
						<tr ><th colspan="8"  height="30" style="background: #2F4F4F;" >
								<h3 align="left" >	<b><%=localeObj.getTranslatedText("Yearly Goal Breakdown")%></b></h3>
							</th>
						</tr>

                        
                          <tr >
						  <th  rowspan="2" width="20%" style="text-align:center;border-top:1px solid;">&nbsp;</th>
						  <th  width="40%"  colspan="4" style="text-align:center;border-top:1px solid;"><b><%=localeObj.getTranslatedText("Recruitment Programs")%></b></font></th>
						   <th  rowspan="2" colspan="2" width="10%" style="text-align:center"><b><%=localeObj.getTranslatedText("Total No. of Candidates")%></b></font></th>
					     </tr>
					     
					     
						 <tr  >
						  <th  width="10%" style="border-top:1px solid;background-color:#4aa1ce" ><b><%=localeObj.getTranslatedText("GA")%></b></font></th>
						  <th  width="10%" style="border-top:1px solid;background-color:#4aa1ce"><b><%=localeObj.getTranslatedText("HA")%></b></font></th>
						  <th  width="10%" style="border-top:1px solid;background-color:#4aa1ce"><b><%=localeObj.getTranslatedText("Gen Y")%></b></font></th>
						  <th  width="10%" style="border-top:1px solid;background-color:#4aa1ce;border-right:1px solid;"><b><%=localeObj.getTranslatedText("SA")%></b></font></th>
					     </tr>
					     
					</thead>
					<%int phase=0;
				if(goal_list.size()>0){
				for(int i=0;i<goal_list.size();i++){
					GoalBreakDown obj=goal_list.get(i);
					phase++;
					String row="";
					if(phase==9){%>
					<tr >
						<td style="text-align: left;">Sign Contract (Target Set in Goal Setting)</td>
							<td style="text-align: center;"><%=goalObj.getGa() %></td>
							<td style="text-align: center;"><%=goalObj.getHa() %></td>
							<td style="text-align: center;"><%=goalObj.getGen_y() %></td>
							<td style="text-align: center;"><%=goalObj.getSa() %></td>
							
						<td colspan="2" style="text-align: center;width:25px;"><input style="text-align:center" name="phase_9" id="phase_9" type="text" class="textObj" value="<%=goalObj.getTotal() %>"  readonly=readonly /></td>
							<%--<input name="phase_9" id="phase_9" type="hidden" class="textObj" value="<%=goalObj.getTotal() %>" style="text-align:center"  /> --%>
						</tr>
						<%}else{ 
						
						String label = "";
						int phaseValue = 0;
						if(phase==1)
						{
							label="Potential Candidate";
							phaseValue=phase_1;
						}
						else if(phase==2)
						{
							label="EOP Registration";
							phaseValue=phase_2;
						}
						else if(phase==3)
						{
							label="First Interview";
							phaseValue=phase_3;
						}
						else if(phase==4)
						{
							label="EOP Attendance";
							phaseValue=phase_4;
						}
						else if(phase==5)
						{
							label="CC Assessment";
							phaseValue=phase_5;
						}
						else if(phase==6)
						{
							label="Company Interview";
							phaseValue=phase_6;
						}
						else if(phase==7)
						{
							label="Pass ALE";
							phaseValue=phase_7;
						}
						else if(phase==8)
						{
							label="Attended Training";
							phaseValue=phase_8;
						}
						
						%>
						<tr >
							<td style="text-align: left;width:15px;"><%=label%></td>
							<td style="text-align: center;width:15px;"></td>
							<td style="text-align: center;width:15px;"></td>
							<td style="text-align: center;width:15px;"></td>
							<td style="text-align: center;width:15px;"></td>
							<td colspan="2" style="text-align: center;width:25px;"><input style="text-align:center" name="phase_<%=phase%>" id="phase_<%=phase%>" type="text" class="textObj" value="<%=obj.getTotal()%>" onKeyup="javascript:getNumberOnly(this)"  /></td>
						</tr>
						<%}}}%>
						<tr >
						
							<td style="text-align: center;border: none;">&nbsp;</td>
							<td style="text-align: center; border: none;">&nbsp;</td>
							<td style="text-align: center; border: none;">&nbsp;</td>
							<td style="text-align: center; border: none;">&nbsp;</td>
							<td style="text-align: center; border: none;">&nbsp;</td>
							<td style="text-align: center; border: none;">&nbsp;</td>
							<td style="text-align: center; border: none;">&nbsp;</td>
							
						</tr>
           		</table>	
           		</div>
				        <div style="float: left;margin-left: 10px;border: none;">
				       <a href="#" >Transforming Rate Description</a>
						</div>	
				         <div style="margin-left: 65%;border: none;margin-bottom:20px;" >
				       <a href="#" id="update" class="btn1" onClick="javascript:submitGoalBreakDownYear();"><%=localeObj.getTranslatedText("Update")%></a>&nbsp;<a href="ContentManager?key=GoalBreakDown_Y&reset=true" id="update" class="btn1"><%=localeObj.getTranslatedText("Reset to Default")%></a>
						</div>	
				</div>
    </div>

     