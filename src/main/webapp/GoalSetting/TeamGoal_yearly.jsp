<%--
  - Author(s):          Abinas
  - Date:               20-May-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:         yearly team  goal  page created
--%>
<%@page import="com.quix.aia.cn.imo.constants.SessionAttributes"%>
<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>
<%@page import="com.quix.aia.cn.imo.data.goalsetting.Individual_Goal"%>
<%@page import="java.util.*"%>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/interface/Individual_Goal.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<%
LocaleObject localeObj = (LocaleObject)session.getAttribute(SessionAttributes.LOCALE_OBJ);
ArrayList<Individual_Goal> goal_list=new ArrayList<Individual_Goal>();
Individual_Goal goalObj=null;
if(request.getAttribute("year_team_goal_object")!=null){
	goal_list=(ArrayList<Individual_Goal>)request.getAttribute("year_team_goal_object");
}
int ga_total=0,ha_total=0,gen_y_total=0,sa_total=0,total_total=0;
for(int i=0;i<goal_list.size();i++){
	
goalObj=new Individual_Goal();
goalObj=goal_list.get(i);
ga_total+=goalObj.getGa();
					ha_total+=goalObj.getHa();
					gen_y_total+=goalObj.getGen_y();
					sa_total+=goalObj.getSa();
					total_total+=goalObj.getTotal();
					System.out.println(goalObj.getCreated_date());
}
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
function goToYearTeamGoal(){
	window.location.href="ContentManager?key=TeamGoal_y";
}
function goToMonthTeamGoal(){
	window.location.href="ContentManager?key=TeamGoal_M";
}
function goToApproveReject(individual_id,agent_code,ga,ha,gen_y,sa,total,status){
	Individual_Goal.ApproveRejectAgent(individual_id,agent_code,ga,ha,gen_y,sa,total,status,'<%=request%>', {
		callback : function(str) {
			msg=str.msg;
			//$('#msg').html(msg);
			window.location.href = "ContentManager?key=TeamGoal_y&msg="+msg;
		}
	});
}


</script>

   	<div id="maincontainer">	
   		
   					<div class="head"><h2 align="center"><%=localeObj.getTranslatedText("GOAL SETTING")%></h2></div>  	
   							<div class="content" style="background-color:#ffffff;">  
   												  
                           				
                                       				
                                                    

                                	  <br>
                                	  <div style="border: none;color:red;" >
                                	  <% if(request.getParameter("msg")!=null){ %>
                                	  <%=request.getParameter("msg")%>
                                	
                                	  <%} %>
                                	  </div>
                                	 <div class="form-item MT25 ML10" style="/* margin-left: -700px; */float:left;">
                            		
                            				<a href="ContentManager?key=IndividualGoal_Y" id="greay" /><%=localeObj.getTranslatedText("Individual Goal")%></a>
                            				<%
                            				if(goal_list.size()!=0){
                            				%>
                                			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="ContentManager?key=GoalBreakDown_Y" id="greay" /><%=localeObj.getTranslatedText("Goal BreakDown")%></a>
                                			<%} %>
                          			        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="ContentManager?key=TeamGoal_y" id="red" /><%=localeObj.getTranslatedText("Team Goal")%></a>
                                	 </div> 
                                	  <div class="clear"></div>
                                	  <div class="clear"></div>
                                	  <br/>
                                	  <br/>
                                	  <br/>
                                	  <div>
                                	 <table width="35%" style="border: none;" align="left"  >
                            	     <tr>                         
                                     <td  style="border: none; padding-right:15px;width:35%;" align="left">                                    				
	                            		<input type="radio" name="icons" checked="checked" value="1" onClick="javascript:goToYearTeamGoal();" > <%=localeObj.getTranslatedText("Yearly Goal")%>
                                     </td>
                                     <td  style="border: none; padding-left:25px;" colspan="10" align="left">  
										<input type="radio" name="icons" value="1"  onClick="javascript:goToMonthTeamGoal();" > <%=localeObj.getTranslatedText("Monthly Goal")%>
                                      </td>   
                       			    </tr>
                          		  </table>
                               </div>
                               <br><br>
				<div class="data_table  MT20 MB20 PT20" style="width:60%;float:left;" > 
           		<table class="subFormDesign" >
           		<thead>
						<tr ><th colspan="6"  height="30" style="background: #2F4F4F;">
								<h3 align="left" ><b><%=localeObj.getTranslatedText("Yearly Team Goal")%></h3>
							</th>
						</tr>
                        <tr >
						   <th  rowspan="2" width="30%" ></th>
						   <th  width="10%"  colspan="5"><b><%=localeObj.getTranslatedText("Yearly Contracted Candidate Goal")%></th>
					    </tr>
						<tr >
						  <th  width="5%" style="background-color:#4aa1ce"><%=localeObj.getTranslatedText("GA")%></th>
						  <th  width="5%" style="background-color:#4aa1ce"><%=localeObj.getTranslatedText("HA")%></th>
						  <th  width="5%" style="background-color:#4aa1ce"><%=localeObj.getTranslatedText("Gen Y")%></th>
						  <th  width="5%" style="background-color:#4aa1ce"><%=localeObj.getTranslatedText("SA")%></th>
						  <th  width="5%" style="background-color:#4aa1ce"><%=localeObj.getTranslatedText("Total")%></th>
					    </tr>
					    </thead>
						<tr>
							<td style="text-align: center;" >Yearly Approved Contracted Candidate Team Goal</td>
							<td style="text-align: center;"><%=ga_total %></td>
							<td style="text-align: center;"><%=ha_total %></td>
							<td style="text-align: center;"><%=gen_y_total %></td>
							<td style="text-align: center;"><%=sa_total %></td>
							<td style="text-align: center;"><%=total_total %></td>
						</tr>
					</table>
                 </div>               	
                                	
                                      
                                 <br><br><br><br><br> <br><br><br><br><br> <br><br><br><br><br>
           		<div class="data_table  MT20 MB20 PT20" style="width:100%" >
           		<table class="subFormDesign" >
					
						

                        
                         <thead> 
						 <tr >
						  <th  rowspan="2" width="10%" ><b><%=localeObj.getTranslatedText("Agent Code")%></th>
						   <th rowspan="2" width="10%" ><b><%=localeObj.getTranslatedText("Agent Name")%></th>
						    <th  rowspan="2" width="10%" ><b><%=localeObj.getTranslatedText("Agent Status")%></th>
							<th  colspan="5" width="10%" ><b><%=localeObj.getTranslatedText("Yearly Contracted Candidate Goal")%></th>
						  <th  rowspan="2" width="5%" ><b><%=localeObj.getTranslatedText("Goal Status")%></th>
						  <th  colspan="3" width="5%" ><b><%=localeObj.getTranslatedText("Actions")%></th>
						 
						 
							    
					     </tr>
					     <tr >
					     <th  width="5%" style="background-color:#4aa1ce" ><%=localeObj.getTranslatedText("GA")%></th>
						  <th  width="5%" style="background-color:#4aa1ce"><%=localeObj.getTranslatedText("HA")%></th>
						  <th  width="5%" style="background-color:#4aa1ce"><%=localeObj.getTranslatedText("Gen Y")%></th>
						  <th  width="5%" style="background-color:#4aa1ce"><%=localeObj.getTranslatedText("SA")%></th>
						  <th  width="5%" style="background-color:#4aa1ce"><%=localeObj.getTranslatedText("Total")%></th>
						   <th  width="5%" ><%=localeObj.getTranslatedText("Approve")%></th>
						  <th  width="5%" ><%=localeObj.getTranslatedText("Reject")%></th>
						  <th  width="5%" ><%=localeObj.getTranslatedText("History")%></th>
						 
					     </tr>
					     </thead>
					<%
					
					for(int i=0;i<goal_list.size();i++){ 
					goalObj=new Individual_Goal();
					goalObj=goal_list.get(i);
					%>
					<%if(i%2==0){ %>
						<tr style="background-color: #9A9090;">
						<%}else{ %>
						<tr >
						<%} %>
						
							<td style="text-align: center;"><%=goalObj.getAgent_code() %></td>
							<td style="text-align: center;">XIEFENG</td>
							<td style="text-align: center;">Active</td>
							<td style="text-align: center;"><%=goalObj.getGa() %></td>
							<td style="text-align: center;"><%=goalObj.getHa() %></td>
							<td style="text-align: center;"><%=goalObj.getGen_y() %></td>
							<td style="text-align: center;"><%=goalObj.getSa()%></td>
							<td style="text-align: center;"><%=goalObj.getTotal() %></td>
							<td style="text-align: center;"><%=goalObj.getGoal_status()%></td>
							<%if(goalObj.getApprove_reject()==null){ %>
							<td style="text-align: center;"><a href="#" onClick="javascript:goToApproveReject('<%=goalObj.getIndividual_id() %>','<%=goalObj.getAgent_code() %>','<%=goalObj.getGa() %>','<%=goalObj.getHa() %>','<%=goalObj.getGen_y()%>','<%=goalObj.getSa() %>','<%=goalObj.getTotal() %>','Approved')"><image src="images/true.jpg" height="10%" width="30%"></a></td>
							<td style="text-align: center;"><a href="#" onClick="javascript:goToApproveReject('<%=goalObj.getIndividual_id() %>','<%=goalObj.getAgent_code() %>','<%=goalObj.getGa() %>','<%=goalObj.getHa() %>','<%=goalObj.getGen_y()%>','<%=goalObj.getSa() %>','<%=goalObj.getTotal() %>','Rejected')"><image src="images/flase.jpg" height="10%" width="30%"></a></td>
							<%}else{%>
							<td style="text-align: center;"></td>
							<td style="text-align: center;"></td>
							
							<%} %>
							<td style="text-align: center;"><a href="ContentManager?key=history&agent_code=<%=goalObj.getAgent_code()%>">Check</a></td>
						</tr>
					<%} %>
						 
						 <tr >
						
							<td style="text-align: center;"></td>
							<td style="text-align: center;background-color: #D3D3D3;" >Yearly Total Contracted Candidate Team Goal</td>
							<td style="text-align: center;background-color: #D3D3D3;"></td>
							<td style="text-align: center;background-color: #D3D3D3;"><%=ga_total %></td>
							<td style="text-align: center;background-color: #D3D3D3;"><%=ha_total %></td>
							<td style="text-align: center;background-color: #D3D3D3;"><%=gen_y_total %></td>
							<td style="text-align: center;background-color: #D3D3D3;"><%=sa_total %></td>
							<td style="text-align: center;background-color: #D3D3D3;"><%=total_total %></td>
							<td style="text-align: center;"></td>
							<td style="text-align: center;"></td>
							<td style="text-align: center;"></td>
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
							<td style="text-align: center; border: none;">&nbsp;</td>
							<td style="text-align: center; border: none;">&nbsp;</td>
							<td style="text-align: center; border: none;">&nbsp;</td>
							<td style="text-align: center; border: none;">&nbsp;</td>
							<td style="text-align: center; border: none;">&nbsp;</td>
						</tr>
				        </table>
				        </div>
				        <div style="margin-left:85%;margin-bottom:20px;">
				       <a href="#" id="Export" class="btn1"><%=localeObj.getTranslatedText("Export")%></a>
						</div>
					
				</div>
			
    </div>
 
     