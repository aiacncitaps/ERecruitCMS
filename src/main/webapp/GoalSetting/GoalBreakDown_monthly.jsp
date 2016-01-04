<%--
  - Author(s):          Abinas
  - Date:               20-May-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:         monthly goal breakdown page created
--%>
<%@page import="com.quix.aia.cn.imo.constants.SessionAttributes"%>
<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>
<%@page import="com.quix.aia.cn.imo.data.goalsetting.GoalBreakDown"%>
<%@page import="com.quix.aia.cn.imo.data.goalsetting.Individual_Goal"%>
<%@page import="java.util.*"%>
<%@page import="com.quix.aia.cn.imo.utilities.LMSUtil"%>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/interface/Individual_Goal.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
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
//all month text field before submission month of agent should b readonly 
String readonlyDec="",readonlyJan="",readonlyFeb="",readonlyMar="",readonlyApr="",readonlyMay="",readonlyJun="",readonlyJul="",readonlyAug="",readonlySep="",readonlyOct="",readonlyNov="";
/*int created_month=0;//(Integer.parseInt((LMSUtil.convertDateToString(goalObj.getCreated_date())).substring(3,5))+1);
if(created_month==1){readonlyDec="";readonlyJan="";readonlyFeb="";readonlyMar="";readonlyApr="";readonlyMay="";readonlyJun="";readonlyJul="";readonlyAug="";readonlySep="";readonlyOct="";readonlyNov="";}
else if(created_month==2){readonlyJan="";readonlyFeb="";readonlyMar="";readonlyApr="";readonlyMay="";readonlyJun="";readonlyJul="";readonlyAug="";readonlySep="";readonlyOct="";readonlyNov="";}
else if(created_month==3){readonlyFeb="";readonlyMar="";readonlyApr="";readonlyMay="";readonlyJun="";readonlyJul="";readonlyAug="";readonlySep="";readonlyOct="";readonlyNov="";}
else if(created_month==4){readonlyMar="";readonlyApr="";readonlyMay="";readonlyJun="";readonlyJul="";readonlyAug="";readonlySep="";readonlyOct="";readonlyNov="";}
else if(created_month==5){readonlyApr="";readonlyMay="";readonlyJun="";readonlyJul="";readonlyAug="";readonlySep="";readonlyOct="";readonlyNov="";}
else if(created_month==6){readonlyMay="";readonlyJun="";readonlyJul="";readonlyAug="";readonlySep="";readonlyOct="";readonlyNov="";}
else if(created_month==7){readonlyJun="";readonlyJul="";readonlyAug="";readonlySep="";readonlyOct="";readonlyNov="";}
else if(created_month==8){readonlyJul="";readonlyAug="";readonlySep="";readonlyOct="";readonlyNov="";}
else if(created_month==9){readonlyAug="";readonlySep="";readonlyOct="";readonlyNov="";}
else if(created_month==10){readonlySep="";readonlyOct="";readonlyNov="";}
else if(created_month==11){readonlyOct="";readonlyNov="";}
else if(created_month==12){readonlyNov="";}*/
//readonly code end
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
function chkForBlank(obj){
	if(obj.value=='')
		obj.value='0';
	
}
function submitGoalBreakDownMonth(){
	 var agent_code="123";//temp value
		var for_year='2015';
	 var selectedVal= new Array();
	 var errorMsg='';var total=0;
	 //phase_1
	 selectedVal.push($('#phase_1_dec').val()); 
	 selectedVal.push($('#phase_1_jan').val());
	 selectedVal.push($('#phase_1_feb').val());
	 selectedVal.push($('#phase_1_mar').val());
	 selectedVal.push($('#phase_1_apr').val());
	 selectedVal.push($('#phase_1_may').val());
	 selectedVal.push($('#phase_1_jun').val());
	 selectedVal.push($('#phase_1_jul').val());
	 selectedVal.push($('#phase_1_aug').val());
	 selectedVal.push($('#phase_1_sep').val());
	 selectedVal.push($('#phase_1_oct').val());
	 selectedVal.push($('#phase_1_nov').val());
	 
	 //phase_2
	  selectedVal.push($('#phase_2_dec').val()); 
	 selectedVal.push($('#phase_2_jan').val());
	 selectedVal.push($('#phase_2_feb').val());
	 selectedVal.push($('#phase_2_mar').val());
	 selectedVal.push($('#phase_2_apr').val());
	 selectedVal.push($('#phase_2_may').val());
	 selectedVal.push($('#phase_2_jun').val());
	 selectedVal.push($('#phase_2_jul').val());
	 selectedVal.push($('#phase_2_aug').val());
	 selectedVal.push($('#phase_2_sep').val());
	 selectedVal.push($('#phase_2_oct').val());
	 selectedVal.push($('#phase_2_nov').val());
	
	 //phase_3
	  selectedVal.push($('#phase_3_dec').val()); 
	 selectedVal.push($('#phase_3_jan').val());
	 selectedVal.push($('#phase_3_feb').val());
	 selectedVal.push($('#phase_3_mar').val());
	 selectedVal.push($('#phase_3_apr').val());
	 selectedVal.push($('#phase_3_may').val());
	 selectedVal.push($('#phase_3_jun').val());
	 selectedVal.push($('#phase_3_jul').val());
	 selectedVal.push($('#phase_3_aug').val());
	 selectedVal.push($('#phase_3_sep').val());
	 selectedVal.push($('#phase_3_oct').val());
	 selectedVal.push($('#phase_3_nov').val());
	 
	 //phase_4
	  selectedVal.push($('#phase_4_dec').val()); 
	 selectedVal.push($('#phase_4_jan').val());
	 selectedVal.push($('#phase_4_feb').val());
	 selectedVal.push($('#phase_4_mar').val());
	 selectedVal.push($('#phase_4_apr').val());
	 selectedVal.push($('#phase_4_may').val());
	 selectedVal.push($('#phase_4_jun').val());
	 selectedVal.push($('#phase_4_jul').val());
	 selectedVal.push($('#phase_4_aug').val());
	 selectedVal.push($('#phase_4_sep').val());
	 selectedVal.push($('#phase_4_oct').val());
	 selectedVal.push($('#phase_4_nov').val());
	 
	 //phase_5
	  selectedVal.push($('#phase_5_dec').val()); 
	 selectedVal.push($('#phase_5_jan').val());
	 selectedVal.push($('#phase_5_feb').val());
	 selectedVal.push($('#phase_5_mar').val());
	 selectedVal.push($('#phase_5_apr').val());
	 selectedVal.push($('#phase_5_may').val());
	 selectedVal.push($('#phase_5_jun').val());
	 selectedVal.push($('#phase_5_jul').val());
	 selectedVal.push($('#phase_5_aug').val());
	 selectedVal.push($('#phase_5_sep').val());
	 selectedVal.push($('#phase_5_oct').val());
	 selectedVal.push($('#phase_5_nov').val());
	
	 //phase_6
	  selectedVal.push($('#phase_6_dec').val()); 
	 selectedVal.push($('#phase_6_jan').val());
	 selectedVal.push($('#phase_6_feb').val());
	 selectedVal.push($('#phase_6_mar').val());
	 selectedVal.push($('#phase_6_apr').val());
	 selectedVal.push($('#phase_6_may').val());
	 selectedVal.push($('#phase_6_jun').val());
	 selectedVal.push($('#phase_6_jul').val());
	 selectedVal.push($('#phase_6_aug').val());
	 selectedVal.push($('#phase_6_sep').val());
	 selectedVal.push($('#phase_6_oct').val());
	 selectedVal.push($('#phase_6_nov').val());
	 
	 //phase_7
	  selectedVal.push($('#phase_7_dec').val()); 
	 selectedVal.push($('#phase_7_jan').val());
	 selectedVal.push($('#phase_7_feb').val());
	 selectedVal.push($('#phase_7_mar').val());
	 selectedVal.push($('#phase_7_apr').val());
	 selectedVal.push($('#phase_7_may').val());
	 selectedVal.push($('#phase_7_jun').val());
	 selectedVal.push($('#phase_7_jul').val());
	 selectedVal.push($('#phase_7_aug').val());
	 selectedVal.push($('#phase_7_sep').val());
	 selectedVal.push($('#phase_7_oct').val());
	 selectedVal.push($('#phase_7_nov').val());
	 
	 //phase_8
	  selectedVal.push($('#phase_8_dec').val()); 
	 selectedVal.push($('#phase_8_jan').val());
	 selectedVal.push($('#phase_8_feb').val());
	 selectedVal.push($('#phase_8_mar').val());
	 selectedVal.push($('#phase_8_apr').val());
	 selectedVal.push($('#phase_8_may').val());
	 selectedVal.push($('#phase_8_jun').val());
	 selectedVal.push($('#phase_8_jul').val());
	 selectedVal.push($('#phase_8_aug').val());
	 selectedVal.push($('#phase_8_sep').val());
	 selectedVal.push($('#phase_8_oct').val());
	 selectedVal.push($('#phase_8_nov').val());
	
//phase_9
selectedVal.push($('#phase_9_dec').val()); 
selectedVal.push($('#phase_9_jan').val());
selectedVal.push($('#phase_9_feb').val());
selectedVal.push($('#phase_9_mar').val());
selectedVal.push($('#phase_9_apr').val());
selectedVal.push($('#phase_9_may').val());
selectedVal.push($('#phase_9_jun').val());
selectedVal.push($('#phase_9_jul').val());
selectedVal.push($('#phase_9_aug').val());
selectedVal.push($('#phase_9_sep').val());
selectedVal.push($('#phase_9_oct').val());
selectedVal.push($('#phase_9_nov').val());

total=parseFloat($('#phase_1_dec').val()) + parseFloat($('#phase_1_jan').val()) + parseFloat($('#phase_1_feb').val()) + parseFloat($('#phase_1_mar').val()) + parseFloat($('#phase_1_apr').val()) + parseFloat($('#phase_1_may').val()) + parseFloat($('#phase_1_jun').val())
+ parseFloat($('#phase_1_jul').val())+parseFloat($('#phase_1_aug').val())+parseFloat($('#phase_1_sep').val())+parseFloat($('#phase_1_oct').val()) + parseFloat($('#phase_1_nov').val());
if(total== parseFloat($('#total_phase_1').val())){}else{errorMsg="Month sum should be equal to Total of Phase_1";}

total=parseFloat($('#phase_2_dec').val()) + parseFloat($('#phase_2_jan').val()) + parseFloat($('#phase_2_feb').val()) + parseFloat($('#phase_2_mar').val()) + parseFloat($('#phase_2_apr').val()) + parseFloat($('#phase_2_may').val()) + parseFloat($('#phase_2_jun').val())
+ parseFloat($('#phase_2_jul').val())+parseFloat($('#phase_2_aug').val())+parseFloat($('#phase_2_sep').val())+parseFloat($('#phase_2_oct').val()) + parseFloat($('#phase_2_nov').val());
if(total== parseFloat($('#total_phase_2').val())){}else{errorMsg=errorMsg+"\n"+"Month sum should be equal to Total of Phase_2";}

total=parseFloat($('#phase_3_dec').val()) + parseFloat($('#phase_3_jan').val()) + parseFloat($('#phase_3_feb').val()) + parseFloat($('#phase_3_mar').val()) + parseFloat($('#phase_3_apr').val()) + parseFloat($('#phase_3_may').val()) + parseFloat($('#phase_3_jun').val())
+ parseFloat($('#phase_3_jul').val())+parseFloat($('#phase_3_aug').val())+parseFloat($('#phase_3_sep').val())+parseFloat($('#phase_3_oct').val()) + parseFloat($('#phase_3_nov').val());
if(total== parseFloat($('#total_phase_3').val())){}else{errorMsg=errorMsg+"\n"+"Month sum should be equal to Total of Phase_3";}

total=parseFloat($('#phase_4_dec').val()) + parseFloat($('#phase_4_jan').val()) + parseFloat($('#phase_4_feb').val()) + parseFloat($('#phase_4_mar').val()) + parseFloat($('#phase_4_apr').val()) + parseFloat($('#phase_4_may').val()) + parseFloat($('#phase_4_jun').val())
+ parseFloat($('#phase_4_jul').val())+parseFloat($('#phase_4_aug').val())+parseFloat($('#phase_4_sep').val())+parseFloat($('#phase_4_oct').val()) + parseFloat($('#phase_4_nov').val());
if(total== parseFloat($('#total_phase_4').val())){}else{errorMsg=errorMsg+"\n"+"Month sum should be equal to Total of Phase_4";}

total=parseFloat($('#phase_5_dec').val()) + parseFloat($('#phase_5_jan').val()) + parseFloat($('#phase_5_feb').val()) + parseFloat($('#phase_5_mar').val()) + parseFloat($('#phase_5_apr').val()) + parseFloat($('#phase_5_may').val()) + parseFloat($('#phase_5_jun').val())
+ parseFloat($('#phase_5_jul').val())+parseFloat($('#phase_5_aug').val())+parseFloat($('#phase_5_sep').val())+parseFloat($('#phase_5_oct').val()) + parseFloat($('#phase_5_nov').val());
if(total== parseFloat($('#total_phase_5').val())){}else{errorMsg=errorMsg+"\n"+"Month sum should be equal to Total of Phase_5";}

total=parseFloat($('#phase_6_dec').val()) + parseFloat($('#phase_6_jan').val()) + parseFloat($('#phase_6_feb').val()) + parseFloat($('#phase_6_mar').val()) + parseFloat($('#phase_6_apr').val()) + parseFloat($('#phase_6_may').val()) + parseFloat($('#phase_6_jun').val())
+ parseFloat($('#phase_6_jul').val())+parseFloat($('#phase_6_aug').val())+parseFloat($('#phase_6_sep').val())+parseFloat($('#phase_6_oct').val()) + parseFloat($('#phase_6_nov').val());
if(total== parseFloat($('#total_phase_6').val())){}else{errorMsg=errorMsg+"\n"+"Month sum should be equal to Total of Phase_6";}

total=parseFloat($('#phase_7_dec').val()) + parseFloat($('#phase_7_jan').val()) + parseFloat($('#phase_7_feb').val()) + parseFloat($('#phase_7_mar').val()) + parseFloat($('#phase_7_apr').val()) + parseFloat($('#phase_7_may').val()) + parseFloat($('#phase_7_jun').val())
+ parseFloat($('#phase_7_jul').val())+parseFloat($('#phase_7_aug').val())+parseFloat($('#phase_7_sep').val())+parseFloat($('#phase_7_oct').val()) + parseFloat($('#phase_7_nov').val());
if(total== parseFloat($('#total_phase_7').val())){}else{errorMsg=errorMsg+"\n"+"Month sum should be equal to Total of Phase_7";}

total=parseFloat($('#phase_8_dec').val()) + parseFloat($('#phase_8_jan').val()) + parseFloat($('#phase_8_feb').val()) + parseFloat($('#phase_8_mar').val()) + parseFloat($('#phase_8_apr').val()) + parseFloat($('#phase_8_may').val()) + parseFloat($('#phase_8_jun').val())
+ parseFloat($('#phase_8_jul').val())+parseFloat($('#phase_8_aug').val())+parseFloat($('#phase_8_sep').val())+parseFloat($('#phase_8_oct').val()) + parseFloat($('#phase_8_nov').val());
if(total== parseFloat($('#total_phase_8').val())){}else{errorMsg=errorMsg+"\n"+"Month sum should be equal to Total of Phase_8";}

total=parseFloat($('#phase_9_dec').val()) + parseFloat($('#phase_9_jan').val()) + parseFloat($('#phase_9_feb').val()) + parseFloat($('#phase_9_mar').val()) + parseFloat($('#phase_9_apr').val()) + parseFloat($('#phase_9_may').val()) + parseFloat($('#phase_9_jun').val())
+ parseFloat($('#phase_9_jul').val())+parseFloat($('#phase_9_aug').val())+parseFloat($('#phase_9_sep').val())+parseFloat($('#phase_9_oct').val()) + parseFloat($('#phase_9_nov').val());
if(total== parseFloat($('#total_phase_9').val())){}else{errorMsg=errorMsg+"\n"+"Month sum should be equal to Total of Phase_9";}

	if(errorMsg==''){
	 Individual_Goal.insertMonthlyGoalBreakDown(agent_code,for_year,selectedVal,'<%=request%>', {
				callback : function(str) {
					msg=str.msg;
					//$('#msg').html(msg);
					window.location.href = "ContentManager?key=GoalBreakDown_M&msg="+msg;
				}
			});
	}else{
		alert(errorMsg);
		$('#msg').html('');
	}
}
function goToYearBreakDown(){
	window.location.href="ContentManager?key=GoalBreakDown_Y";
}
function goToMonthBreakDown(){
	window.location.href="ContentManager?key=GoalBreakDown_M";
}
$(function(){
	$(".data_table tr:odd").addClass("odd");
    $(".data_table tr:not(.odd)").addClass("even");  
});
function chkPhaseTotal(phase,phase_total,month){
	//alert(phase+"_"+phase_total+"_"+month);
	var total=0;
	total=parseFloat($('#phase_'+phase+'_dec').val() )+ parseFloat($('#phase_'+phase+'_jan').val() )+parseFloat( $('#phase_'+phase+'_feb').val()) +parseFloat( $('#phase_'+phase+'_mar').val() )+ parseFloat($('#phase_'+phase+'_apr').val()) 
		  + parseFloat($('#phase_'+phase+'_may').val()) + parseFloat($('#phase_'+phase+'_jun').val()) + parseFloat($('#phase_'+phase+'_jul').val()) + parseFloat($('#phase_'+phase+'_aug').val()) + parseFloat($('#phase_'+phase+'_sep').val())
		  +parseFloat( $('#phase_'+phase+'_oct').val() )+ parseFloat($('#phase_'+phase+'_nov').val());
	if(total==phase_total){}else{
		alert("Sum of month value should be matched with phase total value.");
		$('#phase_'+phase+'_'+month).focus();
	}
	//alert(total);
}
</script>

   	<div id="maincontainer">	
   					<div class="head"><h2 align="center"><%=localeObj.getTranslatedText("GOAL SETTING")%></h2></div>  	
   							<div class="content" style="background-color:#ffffff;"> 
                                       				
                                                     <div class="clear"></div>

                                	  <br>
                                	  <div style="border: none;color:red;" id="msg">
                                	   <% if(request.getParameter("msg")!=null){ %>
                                	  <%=request.getParameter("msg")%>
                                	
                                	  <%} %>
                                	  </div>
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
	                            	     <tr >                         
		                                     <td  style="border: none; padding-right:15px;width:35%;" align="left">                                    				
			                            		<input type="radio" name="icons" value="1" onClick="javascript:goToYearBreakDown();"> <%=localeObj.getTranslatedText("Yearly Goal")%>
		                                     </td>
		                                     <td  style="border: none; padding-left:25px;" colspan="10">  
												<input type="radio" name="icons" value="1" checked="checked" onClick="javascript:goToMonthBreakDown();" > <%=localeObj.getTranslatedText("Monthly Goal")%>
		                                     </td>   
	                       			      </tr>
                          			</table>
                                	</div>
                                      
                                 <br><br><br><br><br>
           		<div class="data_table  MT20 MB20 PT20" style="width:100%" >
           		<table class="subFormDesign" >
					<thead>
						<tr ><th colspan="18"  height="30" style="background: #2F4F4F;">
								<h3 align="left" ><b><%=localeObj.getTranslatedText("Monthly Goal Breakdown")%></b></h3>
							</th>
						</tr>

                        
                          <tr >
						  <th  rowspan="2" width="20%" ><b><%=localeObj.getTranslatedText("Target No. of Candidates in Phase 1-8")%></th>
						  <th  width="60%"  colspan="12" style="text-align:center;"><b><%=localeObj.getTranslatedText("Month")%></th>
						  <th  rowspan="2" width="20%" ><b><%=localeObj.getTranslatedText("Total")%></th>
						  
						 
							    
					     </tr>
						 <tr >
						  
						  <th  width="5%" style="background-color:#4aa1ce"><b><%=localeObj.getTranslatedText("December")%></th>
						  <th  width="5%" style="background-color:#4aa1ce"><b><%=localeObj.getTranslatedText("January")%></th>
						  <th  width="5%" style="background-color:#4aa1ce"><b><%=localeObj.getTranslatedText("February")%></th>
						  <th  width="5%" style="background-color:#4aa1ce"><b><%=localeObj.getTranslatedText("March")%></th>
						  <th  width="5%" style="background-color:#4aa1ce"><b><%=localeObj.getTranslatedText("April")%></th>
						  <th  width="5%" style="background-color:#4aa1ce"><b><%=localeObj.getTranslatedText("May")%></th>
						  <th  width="5%" style="background-color:#4aa1ce"><b><%=localeObj.getTranslatedText("June")%></th>
						  <th  width="5%" style="background-color:#4aa1ce"><b><%=localeObj.getTranslatedText("July")%></th>
						  <th  width="5%" style="background-color:#4aa1ce"><b><%=localeObj.getTranslatedText("August")%></th>
						  <th  width="5%"style="background-color:#4aa1ce" ><b><%=localeObj.getTranslatedText("September")%></th>
						  <th  width="5%"style="background-color:#4aa1ce" ><b><%=localeObj.getTranslatedText("October")%></th>
						  <th  width="5%" style="background-color:#4aa1ce"><b><%=localeObj.getTranslatedText("November")%></th>
						   
						 
							    
					     </tr>
					     </thead>
				<%	
				//int dec=0,jan=0,feb=0,mar=0,apr=0,may=0,jun=0,jul=0,aug=0,sept=0,oct=0,nov=0;
				//String created_month="";
				int phase=0;
				for(int i=0;i<goal_list.size();i++){
					//String row="";
					phase++;
					GoalBreakDown obj=goal_list.get(i);%>
						<tr >
							<td style="text-align: center;"><%=obj.getPhase_name() %></td>
							<td style="text-align: center;"><input style="text-align:center" name="phase_<%=phase%>_dec" id="phase_<%=phase%>_dec" type="text" class="textObj"  value="<%=obj.getDec() %>"  onKeyup="javascript:getNumberOnly(this);"  onBlur="javascript:chkForBlank(this);" <%=readonlyDec %>  /></td>
							<td style="text-align: center;"><input style="text-align:center" name="phase_<%=phase%>_jan" id="phase_<%=phase%>_jan" type="text" class="textObj"  value="<%=obj.getJan() %>"  onKeyup="javascript:getNumberOnly(this);" onBlur="javascript:chkForBlank(this);"  <%=readonlyJan %> /></td>
							<td style="text-align: center;"><input style="text-align:center" name="phase_<%=phase%>_feb" id="phase_<%=phase%>_feb" type="text" class="textObj"  value="<%=obj.getFeb()%>"  onKeyup="javascript:getNumberOnly(this);" onBlur="javascript:chkForBlank(this);"  <%=readonlyFeb %>/></td>
							<td style="text-align: center;"><input style="text-align:center" name="phase_<%=phase%>_mar" id="phase_<%=phase%>_mar" type="text" class="textObj"  value="<%=obj.getMar()%>"  onKeyup="javascript:getNumberOnly(this);" onBlur="javascript:chkForBlank(this);"  <%=readonlyMar %>/></td>
							<td style="text-align: center;"><input style="text-align:center" name="phase_<%=phase%>_apr" id="phase_<%=phase%>_apr" type="text" class="textObj"  value="<%=obj.getApr()%>"  onKeyup="javascript:getNumberOnly(this);" onBlur="javascript:chkForBlank(this);"  <%=readonlyApr %>/></td>
							<td style="text-align: center;"><input style="text-align:center" name="phase_<%=phase%>_may" id="phase_<%=phase%>_may" type="text" class="textObj"  value="<%=obj.getMay() %>"  onKeyup="javascript:getNumberOnly(this);" onBlur="javascript:chkForBlank(this);"  <%=readonlyMay %>/></td>
							<td style="text-align: center;"><input style="text-align:center" name="phase_<%=phase%>_jun" id="phase_<%=phase%>_jun" type="text" class="textObj"  value="<%=obj.getJune()%>"  onKeyup="javascript:getNumberOnly(this);" onBlur="javascript:chkForBlank(this);"  <%=readonlyJun %>/></td>
							<td style="text-align: center;"><input style="text-align:center" name="phase_<%=phase%>_jul" id="phase_<%=phase%>_jul" type="text" class="textObj"  value="<%=obj.getJuly()%>"  onKeyup="javascript:getNumberOnly(this);" onBlur="javascript:chkForBlank(this);"  <%=readonlyJul %>/></td>
							<td style="text-align: center;"><input style="text-align:center" name="phase_<%=phase%>_aug" id="phase_<%=phase%>_aug" type="text" class="textObj"  value="<%=obj.getAug()%>"  onKeyup="javascript:getNumberOnly(this);" onBlur="javascript:chkForBlank(this);"  <%=readonlyAug %>/></td>
							<td style="text-align: center;"><input style="text-align:center" name="phase_<%=phase%>_sep" id="phase_<%=phase%>_sep" type="text" class="textObj"  value="<%=obj.getSept()%>" onKeyup="javascript:getNumberOnly(this);" onBlur="javascript:chkForBlank(this);"  <%=readonlySep %>/></td>
							<td style="text-align: center;"><input style="text-align:center" name="phase_<%=phase%>_oct" id="phase_<%=phase%>_oct" type="text" class="textObj"  value="<%=obj.getOct()%>" onKeyup="javascript:getNumberOnly(this);" onBlur="javascript:chkForBlank(this);"  <%=readonlyOct %>/></td>
							<td style="text-align: center;"><input style="text-align:center" name="phase_<%=phase%>_nov" id="phase_<%=phase%>_nov" type="text" class="textObj"  value="<%=obj.getNov()%>"  onKeyup="javascript:getNumberOnly(this);" onBlur="javascript:chkForBlank(this);"  <%=readonlyNov %>/></td>
							<td style="text-align: center;"><%=obj.getTotal() %><input type="hidden" name="total_phase_<%=phase%>" id="total_phase_<%=phase%>" value="<%=obj.getTotal()%>"/></td>
						</tr>
						<%} %>
					
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
						</tr>
				        </table>
				        </div>
				         <div style="margin-right:1100px;border: none;">
				       <a href="#" >Transforming Rate Description</a>
						</div>	
				       <div style="margin-left: 65%; margin-bottom:20px;">
				       <%if(goal_list.size()>0){ %>
				       <a href="#" id="update" class="btn1" onClick="javascript:submitGoalBreakDownMonth();"><%=localeObj.getTranslatedText("Update")%></a>&nbsp;<a href="ContentManager?key=GoalBreakDown_M&reset=true" id="reset" class="btn1"><%=localeObj.getTranslatedText("Reset to Default")%></a>
				       <%} %>
						</div>	
						
           
					
				</div>
			
    </div>
 
     