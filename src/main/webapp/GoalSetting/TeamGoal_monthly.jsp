<%--
  - Author(s):          Abinas
  - Date:               20-May-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:         monthly team goal  page created
--%>
<%@page import="com.quix.aia.cn.imo.constants.SessionAttributes"%>
<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>
<%@page import="com.quix.aia.cn.imo.data.goalsetting.Individual_Goal"%>
<%@page import="java.util.*"%>
<%@page import="com.quix.aia.cn.imo.utilities.LMSUtil"%>
<%
LocaleObject localeObj = (LocaleObject)session.getAttribute(SessionAttributes.LOCALE_OBJ);
ArrayList<Individual_Goal> goal_list=new ArrayList<Individual_Goal>();
Individual_Goal goalObj=null;
if(request.getAttribute("year_team_goal_object")!=null){
	goal_list=(ArrayList<Individual_Goal>)request.getAttribute("year_team_goal_object");
}
int ga_total=0,ha_total=0,gen_y_total=0,sa_total=0,total_total=0;

%>

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
$(function(){
	$(".data_table tr:odd").addClass("odd");
    $(".data_table tr:not(.odd)").addClass("even");  
});
function getListByAgentCode(){
	var agent_code=$('#agent_code').val();
	var sort_by=$('#sort_by').val();
	window.location.href="ContentManager?key=TeamGoal_M&agent_code="+agent_code+"&sort_by="+sort_by;
}
function goToYearTeamGoal(){
	window.location.href="ContentManager?key=TeamGoal_y";
}
function goToMonthTeamGoal(){
	window.location.href="ContentManager?key=TeamGoal_M";
}

</script>
   	<div id="maincontainer">	
   		
   					<div class="head"><h2 align="center"><%=localeObj.getTranslatedText("GOAL SETTING")%></h2></div>  	
   							<div class="content" style="background-color:#ffffff;"> 
   												  
                                       				
                                                    

                                	  <br>
                                	 <div class="form-item MT25 ML10" style="/* margin-left: -700px; */float:left;">
                            		
                            				<a href="ContentManager?key=IndividualGoal_Y" id="greay"><%=localeObj.getTranslatedText("Individual Goal")%></a>
                            				<%
                            				if(goal_list.size()!=0){
                            				%>
                                			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="ContentManager?key=GoalBreakDown_Y" id="greay" ><%=localeObj.getTranslatedText("Goal BreakDown")%></a>
                                			<%} %>
                          			        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="ContentManager?key=TeamGoal_y" id="red"><%=localeObj.getTranslatedText("Team Goal")%></a>
                                	 </div> 
                                	  <div class="clear"></div>
                                	  <div class="clear"></div>
                                	  <br/>
                                	  <br/>
                                	  <br/>
                                	 <div>
                            		 <table width="35%" style="border: none;" align="left"  >
	                            	     <tr >                         
	                                     <td  style="border: none; padding-right:15px;width:35%;" align="left">                                    				
		                           			 <input type="radio" name="icons" value="1" onClick="javascript:goToYearTeamGoal();" > <%=localeObj.getTranslatedText("Yearly Goal")%>
	                                     </td>
	                                     <td  style="border: none; padding-left:25px;" colspan="10" align="left">  
											<input type="radio" name="icons" value="1" checked="checked" onClick="javascript:goToMonthTeamGoal();" > <%=localeObj.getTranslatedText("Monthly Goal")%>
	                                      </td>   
	                       			     </tr>
                          			 </table>
                                	</div>
                                	
                                	 <div class="clear"></div>
                                	 <div class="form-item MT25" style="/* margin-left: 4px; */text-align: left;">
                            		 <table width="90%" style="border: none;" >
                            	     <tr >                         
                                     <td  style="border: none;text-align: left;"   Width="50%" >                                    				
	                              <label> <%=localeObj.getTranslatedText("Team Member")%></label> 
                                     </td>
                                     <td  style="border: none;"  Width="20%"  >  
                  						<select name="agent_code" id="agent_code"  class="comboObj" onChange="getListByAgentCode();" style="margin-left:-500px;" >
                  											<option value="%">ALL</option>
                                       						<%for(int i=0;i<goal_list.size();i++){ 
																goalObj=new Individual_Goal();
																goalObj=goal_list.get(i);%>
                                       								<option value="<%=goalObj.getAgent_code() %>"><%=goalObj.getAgent_code() %></option>
                                       						<%} %>
                                       	 				 </select>
                                       	 			</td>                                     			   
                       			       </tr>
                       			      <%--
                       			         <tr >                         
                                     <td  style="border: none;"   Width="50%" >                                    				
	                              <label> <%=localeObj.getTranslatedText("Sort By")%></label> 
                                     </td>
                                     <td  style="border: none;"  Width="50%"  >  
                  <select name="sort_by" id="sort_by"  class="comboObj" onChange="getListByAgentCode();"  style="margin-left:-430px;">
                                       						<option value="agent_code">Agent Code</option>
                                       						<!-- <<option value="agent_name">Agent Name</option>	                                        	
                                       						<option value="status">Status</option>-->
                                       	 				 </select>
                                       	 			</td>                                     			   
                       			       </tr> --%>
                          			          </table>
                                	</div>
                                      
                                 
           		<div class="data_table  MT20 MB20 PT20" style="width:100%" >
           		<table class="subFormDesign" >	
						<thead>
                        
						 <tr >
						  <th  rowspan="2" width="10%" ><b><%=localeObj.getTranslatedText("Agent Code")%></th>
						   <th  rowspan="2" width="10%" ><b><%=localeObj.getTranslatedText("Agent Name")%></th>
						    <th  rowspan="2" width="10%" ><b><%=localeObj.getTranslatedText("Agent Status")%></th>
						    <th colspan="12" width="10%" "><b><%=localeObj.getTranslatedText("Month")%></th>
						 <th  rowspan="2" width="10%" ><b><%=localeObj.getTranslatedText("Goal Status")%></th>
					     </tr>
					     <tr>
					      <th  width="5%" style="background-color:#4aa1ce  ;"><%=localeObj.getTranslatedText("December")%></th>
						  <th  width="5%" style="background-color:#4aa1ce  ;"><%=localeObj.getTranslatedText("January")%></th>
						  <th  width="5%" style="background-color:#4aa1ce  ;"><%=localeObj.getTranslatedText("February")%></th>
						  <th  width="5%" style="background-color:#4aa1ce  ;"><%=localeObj.getTranslatedText("March")%></th>
						  <th  width="5%" style="background-color:#4aa1ce  ;"><%=localeObj.getTranslatedText("April")%></th>
						  <th  width="5%" style="background-color:#4aa1ce  ;" ><%=localeObj.getTranslatedText("May")%></th>
						  <th  width="5%" style="background-color:#4aa1ce  ;"><%=localeObj.getTranslatedText("June")%></th>
						  <th  width="5%" style="background-color:#4aa1ce  ;"><%=localeObj.getTranslatedText("July")%></th>
						  <th  width="5%" style="background-color:#4aa1ce  ;"><%=localeObj.getTranslatedText("August")%></th>
						  <th  width="5%" style="background-color:#4aa1ce  ;"><%=localeObj.getTranslatedText("September")%></th>
						  <th  width="5%" style="background-color:#4aa1ce  ;"><%=localeObj.getTranslatedText("October")%></th>
						  <th  width="5%" style="border-right:1px solid;background-color:#4aa1ce  ;"><%=localeObj.getTranslatedText("November")%></th>
						   
					     </tr>
					     </thead>
					<%
					int dec=0,jan=0,feb=0,mar=0,apr=0,may=0,jun=0,jul=0,aug=0,sept=0,oct=0,nov=0;
					int total_dec=0,total_jan=0,total_feb=0,total_mar=0,total_apr=0,total_may=0,total_jun=0,total_jul=0,total_aug=0,total_sept=0,total_oct=0,total_nov=0;
					String created_month="";
					for(int i=0;i<goal_list.size();i++){ 
					goalObj=new Individual_Goal();
					goalObj=goal_list.get(i);
					
					created_month=(Integer.parseInt((LMSUtil.convertDateToString(goalObj.getCreated_date())).substring(3,5))+1)+"";
					System.out.println(created_month);
					int extra=0,result=0,total=0,extra_month=0;
					 total=goalObj.getTotal();
					int divider=(12-Integer.parseInt(created_month))+1;
					System.out.println(divider);
					result=total/divider;
					extra=total-(result*divider);
					System.out.println(extra);
					if(created_month.equals("1")){
						if(extra!=0){
						extra_month=extra/extra;
						}
						dec=result;jan=result;feb=result;mar=result;apr=result;may=result;jun=result;jul=result;aug=result;sept=result;oct=result;nov=result+extra_month;
						extra--;
						if(extra!=0){
							dec=result;jan=result;feb=result;mar=result;apr=result;may=result;jun=result;jul=result;aug=result;sept=result;oct=result+extra_month;
							extra--;
						} if(extra!=0)	{
							dec=result;jan=result;feb=result;mar=result;apr=result;may=result;jun=result;jul=result;aug=result;sept=result+extra_month;
							extra--;
						} if(extra!=0)	{
							dec=result;jan=result;feb=result;mar=result;apr=result;may=result;jun=result;jul=result;aug=result+extra_month;
								extra--;
						} if(extra!=0)	{
							dec=result;jan=result;feb=result;mar=result;apr=result;may=result;jun=result;jul=result+extra_month;
							extra--;
						} if(extra!=0)	{
							dec=result;jan=result;feb=result;mar=result;apr=result;may=result;jun=result+extra_month;
							extra--;
						} if(extra!=0)	{
							dec=result;jan=result;feb=result;mar=result;apr=result;may=result+extra_month;
							extra--;
						} if(extra!=0){	
							dec=result;jan=result;feb=result;mar=result;apr=result+extra_month;
							extra--;
						}if(extra!=0){	
							dec=result;jan=result;feb=result;mar=result+extra_month;
							extra--;
						}if(extra!=0){	
							dec=result;jan=result;feb=result+extra_month;
							extra--;
						}if(extra!=0){	
							dec=result;jan=result+extra_month;
							extra--;
						}
						if(extra!=0){	
							dec=result+extra_month;
							extra--;
						}
					}
					if(created_month.equals("2")){
						if(extra!=0){
							extra_month=extra/extra;
							}
						jan=result;feb=result;mar=result;apr=result;may=result;jun=result;jul=result;aug=result;sept=result;oct=result;nov=result+extra_month;
						extra--;
						if(extra!=0){
							jan=result;feb=result;mar=result;apr=result;may=result;jun=result;jul=result;aug=result;sept=result;oct=result+extra_month;
							extra--;
						} if(extra!=0)	{
							jan=result;feb=result;mar=result;apr=result;may=result;jun=result;jul=result;aug=result;sept=result+extra_month;
							extra--;
						} if(extra!=0)	{
							jan=result;feb=result;mar=result;apr=result;may=result;jun=result;jul=result;aug=result+extra_month;
								extra--;
						} if(extra!=0)	{
							jan=result;feb=result;mar=result;apr=result;may=result;jun=result;jul=result+extra_month;
							extra--;
						} if(extra!=0)	{
							jan=result;feb=result;mar=result;apr=result;may=result;jun=result+extra_month;
							extra--;
						} if(extra!=0)	{
							jan=result;feb=result;mar=result;apr=result;may=result+extra_month;
							extra--;
						} if(extra!=0){	
							jan=result;feb=result;mar=result;apr=result+extra_month;
							extra--;
						}if(extra!=0){	
							jan=result;feb=result;mar=result+extra_month;
							extra--;
						}if(extra!=0){	
							jan=result;feb=result+extra_month;
							extra--;
						}if(extra!=0){	
							jan=result+extra_month;
							extra--;
						}
						
					}
					if(created_month.equals("3")){
						if(extra!=0){
							extra_month=extra/extra;
							}
						feb=result;mar=result;apr=result;may=result;jun=result;jul=result;aug=result;sept=result;oct=result;nov=result+extra_month;
						extra--;
						if(extra!=0){
							feb=result;mar=result;apr=result;may=result;jun=result;jul=result;aug=result;sept=result;oct=result+extra_month;
							extra--;
						} if(extra!=0)	{
							feb=result;mar=result;apr=result;may=result;jun=result;jul=result;aug=result;sept=result+extra_month;
							extra--;
						} if(extra!=0)	{
							feb=result;mar=result;apr=result;may=result;jun=result;jul=result;aug=result+extra_month;
								extra--;
						} if(extra!=0)	{
							feb=result;mar=result;apr=result;may=result;jun=result;jul=result+extra_month;
							extra--;
						} if(extra!=0)	{
							feb=result;mar=result;apr=result;may=result;jun=result+extra_month;
							extra--;
						} if(extra!=0)	{
							feb=result;mar=result;apr=result;may=result+extra_month;
							extra--;
						} if(extra!=0){	
							feb=result;mar=result;apr=result+extra_month;
							extra--;
						}if(extra!=0){	
							feb=result;mar=result+extra_month;
							extra--;
						}if(extra!=0){	
							feb=result+extra_month;
							extra--;
						}
					}
					if(created_month.equals("4")){
						if(extra!=0){
							extra_month=extra/extra;
							}
						mar=result;apr=result;may=result;jun=result;jul=result;aug=result;sept=result;oct=result;nov=result+extra_month;
						extra--;
						if(extra!=0){
							mar=result;apr=result;may=result;jun=result;jul=result;aug=result;sept=result;oct=result+extra_month;
							extra--;
						} if(extra!=0)	{
							mar=result;apr=result;may=result;jun=result;jul=result;aug=result;sept=result+extra_month;
							extra--;
						} if(extra!=0)	{
							mar=result;apr=result;may=result;jun=result;jul=result;aug=result+extra_month;
								extra--;
						} if(extra!=0)	{
							mar=result;apr=result;may=result;jun=result;jul=result+extra_month;
							extra--;
						} if(extra!=0)	{
							mar=result;apr=result;may=result;jun=result+extra_month;
							extra--;
						} if(extra!=0)	{
							mar=result;apr=result;may=result+extra_month;
							extra--;
						} if(extra!=0){	
							mar=result;apr=result+extra_month;
							extra--;
						}if(extra!=0){	
							mar=result+extra_month;
							extra--;
						}
					}
					if(created_month.equals("5")){
						if(extra!=0){
							extra_month=extra/extra;
							}
						apr=result;may=result;jun=result;jul=result;aug=result;sept=result;oct=result;nov=result+extra_month;
						extra--;
						if(extra!=0){
							apr=result;may=result;jun=result;jul=result;aug=result;sept=result;oct=result+extra_month;
							extra--;
						} if(extra!=0)	{
							apr=result;may=result;jun=result;jul=result;aug=result;sept=result+extra_month;
							extra--;
						} if(extra!=0)	{
								apr=result;may=result;jun=result;jul=result;aug=result+extra_month;
								extra--;
						} if(extra!=0)	{
							apr=result;may=result;jun=result;jul=result+extra_month;
							extra--;
						} if(extra!=0)	{
							apr=result;may=result;jun=result+extra_month;
							extra--;
						} if(extra!=0)	{
							apr=result;may=result+extra_month;
							extra--;
						} if(extra!=0){	
							apr=result+extra_month;
						}
					}
					if(created_month.equals("6")){
						if(extra!=0){
							extra_month=extra/extra;
							}
						may=result;jun=result;jul=result;aug=result;sept=result;oct=result;nov=result+extra_month;
						extra--;
						if(extra!=0){
							may=result;jun=result;jul=result;aug=result;sept=result;oct=result+extra_month;
							extra--;
						} if(extra!=0)	{
							may=result;jun=result;jul=result;aug=result;sept=result+extra_month;
							extra--;
						} if(extra!=0)	{
							may=result;jun=result;jul=result;aug=result+extra_month;
								extra--;
						} if(extra!=0)	{
							may=result;jun=result;jul=result+extra_month;
							extra--;
						} if(extra!=0)	{
							may=result;jun=result+extra_month;
							extra--;
						} if(extra!=0)	{
							may=result+extra_month;
							extra--;
						}
					}
					if(created_month.equals("7")){
						if(extra!=0){
							extra_month=extra/extra;
							}
						jun=result;jul=result;aug=result;sept=result;oct=result;nov=result+extra_month;
						extra--;
						if(extra!=0){
							jun=result;jul=result;aug=result;sept=result;oct=result+extra_month;
							extra--;
						} if(extra!=0)	{
							jun=result;jul=result;aug=result;sept=result+extra_month;
							extra--;
						} if(extra!=0)	{
							jun=result;jul=result;aug=result+extra_month;
								extra--;
						} if(extra!=0)	{
							jun=result;jul=result+extra_month;
							extra--;
						} if(extra!=0)	{
							jun=result+extra_month;
							extra--;
						} 
					}
					if(created_month.equals("8")){
						if(extra!=0){
							extra_month=extra/extra;
							}
						jul=result;aug=result;sept=result;oct=result;nov=result+extra_month;
						extra--;
						if(extra!=0){
							jul=result;aug=result;sept=result;oct=result+extra_month;
							extra--;
						} if(extra!=0)	{
							jul=result;aug=result;sept=result+extra_month;
							extra--;
						} if(extra!=0)	{
							jul=result;aug=result+extra_month;
								extra--;
						} if(extra!=0)	{
							jul=result+extra_month;
							extra--;
						}	
						
					}
					if(created_month.equals("9")){
						if(extra!=0){
							extra_month=extra/extra;
							}
						aug=result;sept=result;oct=result;nov=result+extra_month;
						extra--;
						if(extra!=0){
							aug=result;sept=result;oct=result+extra_month;
							extra--;
						} if(extra!=0)	{
							aug=result;sept=result+extra_month;
							extra--;
						} if(extra!=0)	{
							aug=result+extra_month;
								extra--;
						} 
					}
					if(created_month.equals("10")){
						if(extra!=0){
							extra_month=extra/extra;
							}
						sept=result;oct=result;nov=result+extra_month;
						extra--;
						if(extra!=0){
							sept=result;oct=result+extra_month;
							extra--;
						} if(extra!=0)	{
							sept=result+extra_month;
							extra--;
						} 
					}
					if(created_month.equals("11")){
						if(extra!=0){
							extra_month=extra/extra;
							}
						oct=result;nov=result+extra_month;
						extra--;
						if(extra!=0){
							oct=result+extra_month;
							extra--;
						} 
						
					}
					if(created_month.equals("12")){
						if(extra!=0){
							extra_month=extra/extra;
							}
						nov=result+extra_month;
						extra--;
						
					}
					
					total_dec+=dec;
					total_jan+=jan;
					total_feb+=feb;
					total_mar+=mar;
					total_apr+=apr;
					total_may+=may;
					total_jun+=jun;
					total_jul+=jul;
					total_aug+=aug;
					total_sept+=sept;
					total_oct+=oct;
					total_nov+=nov;
					
					
					%>
					<%if(i%2==0){ %>
						<tr style="background-color: #9A9090;">
						<%}else{ %>
						<tr >
						<%} %>
				        
							<td style="text-align: center;"><%=goalObj.getAgent_code() %></td>
							<td style="text-align: center;">XIEFENG</td>
							<td style="text-align: center;">Active</td>
							<td style="text-align: center;"><%=dec %></td>
							<td style="text-align: center;"><%=jan %></td>
							<td style="text-align: center;"><%=feb %></td>
							<td style="text-align: center;"><%=mar %></td>
							<td style="text-align: center;"><%=apr %></td>
							<td style="text-align: center;"><%=may %></td>
							<td style="text-align: center;"><%=jun %></td>
							<td style="text-align: center;"><%=jul %></td>
							<td style="text-align: center;"><%=aug %></td>
							<td style="text-align: center;"><%=sept %></td>
							<td style="text-align: center;"><%=oct %></td>
							<td style="text-align: center;"><%=nov %></td>
							<td style="text-align: center;"><%=goalObj.getGoal_status() %></td>
							
							
						</tr>
				<%} %>
						 
				
						 
						 
						 <tr>
						
							<td style="text-align: center;"></td>
							<td style="text-align: center;background-color: #D3D3D3;" colspan="2">Monthly Contracted Candidate Team Goal</td>
							<td style="text-align: center;background-color: #D3D3D3;"><%=total_dec %></td>
							<td style="text-align: center;background-color: #D3D3D3;"><%=total_jan %></td>
							<td style="text-align: center;background-color: #D3D3D3;"><%=total_feb %></td>
							<td style="text-align: center;background-color: #D3D3D3;"><%=total_mar %></td>
							<td style="text-align: center;background-color: #D3D3D3;"><%=total_apr %></td>
							<td style="text-align: center;background-color: #D3D3D3;"><%=total_may %></td>
							<td style="text-align: center;background-color: #D3D3D3;"><%=total_jun %></td>
							<td style="text-align: center;background-color: #D3D3D3;"><%=total_jul %></td>
							<td style="text-align: center;background-color: #D3D3D3;"><%=total_aug%></td>
							<td style="text-align: center;background-color: #D3D3D3;"><%=total_sept %></td>
							<td style="text-align: center;background-color: #D3D3D3;"><%=total_oct %></td>
							<td style="text-align: center;background-color: #D3D3D3;"><%=total_nov %></td>
							<td style="text-align: center;"></td>
							
							
						</tr>
						 
						 
						 
						<tr >
						
							<td style="text-align: center;border: none;">&nbsp;</td>
							<td style="text-align: center; border: none;">&nbsp;</td>
							<td style="text-align: center; border: none;">&nbsp;</td>
							<td style="text-align: center; border: none;">&nbsp;</td>
							<td style="text-align: center; border: none;">&nbsp;</td>
							<td style="text-align: center; border: none;">&nbsp;</td>
							<td style="text-align: center; border: none;">&nbsp;</td>
							<td style="text-align: center;border: none;">&nbsp;</td>
							<td style="text-align: center; border: none;">&nbsp;</td>
							<td style="text-align: center; border: none;">&nbsp;</td>
							<td style="text-align: center; border: none;">&nbsp;</td>
							<td style="text-align: center; border: none;">&nbsp;</td>
							<td style="text-align: center; border: none;">&nbsp;</td>
							<td style="text-align: center; border: none;">&nbsp;</td>
							<td style="text-align: center; border: none;">&nbsp;</td>
							<td style="text-align: center; border: none;">&nbsp;</td>
							
						</tr>
				        </table>
				        </div>
				        <div style="margin-left:60%;margin-bottom:20px;">
			  <a href="#" id="update" class="btn1"><%=localeObj.getTranslatedText("Export")%></a>&nbsp;
              <a href="ContentManager?key=TeamGoal_y" id="reset" class="btn1"><%=localeObj.getTranslatedText("Back To Yearly Goal")%></a>
							
						
           
					</div>
				
			</div>
		
 </div>
 
     