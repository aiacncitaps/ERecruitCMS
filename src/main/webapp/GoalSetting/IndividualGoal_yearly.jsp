<%--
  - Author(s):          Abinas
  - Date:               20-May-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:         individual goal  page created
--%>
<%@page import="com.quix.aia.cn.imo.data.goalsetting.Individual_Goal"%>
<%@page import="com.quix.aia.cn.imo.constants.SessionAttributes"%>
<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>
<%@page import="com.quix.aia.cn.imo.constants.RequestAttributes"%>
<%
LocaleObject localeObj = (LocaleObject)session.getAttribute(SessionAttributes.LOCALE_OBJ);
Individual_Goal goalObj=new Individual_Goal();
goalObj=(Individual_Goal)request.getAttribute("goal_object");
%>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/interface/Individual_Goal.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<style>
#greay{
    border: 2px solid #a1a1a1;
    padding: 10px 40px; 
    background:#dddddd;
    width:50px;
    border-radius: 25px;
   color:gray;
    text-decoration: none !important;
}
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

#submit {
    border: 2px solid #a1a1a1;
    padding: 10px 40px; 
    background:#d31145;
    width:50px;
    border-radius: 25px;
    color: white;
    text-decoration: none !important;
}

#I_Goal {
    border: 2px solid #a1a1a1;
    padding: 10px 40px; 
    background:#d31145;
    width:50px;
    border-radius: 25px;
    color: white;
    text-decoration: none !important;
}
#G_Break {
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



</style>
<script type="text/javascript">
function getTotalValue(obj){
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
function calculateTotal(obj){
	var ga =   $('#ga').val();
	var ha =   $('#ha').val();
	var gen_y =   $('#gen_y').val();
	var sa =   $('#sa').val();
	if(ga=='' || ga==""){
		alert("Please enter value for GA");
		document.getElementById("ga").focus();
	}
	else if(ha=='' || ha==""){
		alert("Please enter value for HA");
		document.getElementById("ha").focus();
	}	
	else if(gen_y=='' || gen_y==""){
		alert("Please enter value for GEN Y");
		document.getElementById("gen_y").focus();
	}
	else if(sa=='' || sa==""){
		alert("Please enter value for SA");
		document.getElementById("sa").focus();
	}else{
		var total = parseFloat(ga) + parseFloat(ha) +parseFloat(gen_y) + parseFloat(sa);
		$('#total').val(total);
		$('#total3').html(total);
		$('#total2').html(total);
	}
}

function submitGoal(){
	var agent_code="123";//temp value
	var ga =   $('#ga').val();
	var ha =   $('#ha').val();
	var gen_y =   $('#gen_y').val();
	var sa =   $('#sa').val();
	var for_year=$('#for_year').val();
	var msg="";
	Individual_Goal.insertIndividualGoal(agent_code,ga,ha,gen_y,sa,for_year,'<%=request%>', {
		callback : function(str) {
			msg=str.msg;
			//$('#msg').html(msg);
			window.location.href = "ContentManager?key=IndividualGoal_Y&msg="+msg;
		}
	});
}
</script>

   	<div id="maincontainer">	
   		
   					<div class="head"><h2 align="center"><%=localeObj.getTranslatedText("Goal Setting")%></h2></div>  	
   							<div class="content" style="background-color:#ffffff;" >	 
                           				
                                       				
          <div class="clear"></div>

                                	  <br>
                                	<div style="border: none;color:red;" >
                                	   <% if(request.getParameter("msg")!=null){ %>
                                	  <%=request.getParameter("msg")%>
                                	
                                	  <%} %>
                                	  </div>
                                	 <div class="form-item MT25 ML10" style="/* margin-left: -700px; */float:left;">
                            		
                            				<a href="ContentManager?key=IndividualGoal_Y" id="I_Goal"><%=localeObj.getTranslatedText("Individual Goal")%></a>
                            				<%
                            				if(goalObj.getIndividual_id()!=0){
                            				%>
                                			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="ContentManager?key=GoalBreakDown_Y" id="G_Break"><%=localeObj.getTranslatedText("Goal Breakdown")%></a>
                                			<%} %>
                                			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="ContentManager?key=TeamGoal_y" id="greay"><%=localeObj.getTranslatedText("Team Goal")%></a>
                          			
                                	 </div> 
                                	 <div class="clear"></div>
                                	 <div class="form-item MT25" style="/* margin-left: 4px; */text-align: left;">
                            		 <table width="90%" style="border: none;" >
                            	     <tr>                         
                                     	<td  style="border: none;text-align:left;"   width="50%">                                    				
	                             		 <label> <%=localeObj.getTranslatedText("Year")%></label> 
                                     	</td>
                                     	<td  style="border: none;"    >  
						                  <select name="for_year" id="for_year"  class="comboObj" onChange="getListByAgentCode();" style="margin-left:-300px;" >
							                 <option value="2015">2015</option>
							                 <option value="2016">2016</option>
							                 <option value="2017">2017</option>
						                 </select>
                                    	 </td>                                     			   
                       			    </tr>
                          			</table>
                                	</div>
                               
                 <div class="data_table  MT20 MB20" style="width:100%" >
           		<table class="subFormDesign" >
					<thead>
						<tr ><th colspan="9"  height="30" style="background: #2F4F4F;">
							
								<h3 align="left"><b><%=localeObj.getTranslatedText("Goal Setting")%></b></h3>
							
							</th>
						</tr>

                        
                          <tr >
						  <th  rowspan="2" colspan="2" width="20%" style="text-align:center;border-top:1px solid;">&nbsp;</th>
						  <th  width="10%" style="text-align:center;border-top:1px solid;" colspan="6"><b><%=localeObj.getTranslatedText("Yearly Contracted Candidate Goal")%></b></th>
						  
						 
							    
					     </tr>
						 <tr >
						  
						  <th  width="10%" style="text-align:center;border-top:1px solid;background-color:#4aa1ce"><label><%=localeObj.getTranslatedText("GA")%></label></th>
						  <th  width="10%" style="text-align:center;border-top:1px solid;background-color:#4aa1ce"><label><%=localeObj.getTranslatedText("HA")%></label></th>
						  <th  width="10%" style="text-align:center;border-top:1px solid;background-color:#4aa1ce"><b><%=localeObj.getTranslatedText("Gen Y")%></b></th>
						  <th  width="10%" style="text-align:center;border-top:1px solid;background-color:#4aa1ce"><label><%=localeObj.getTranslatedText("SA")%></label></th>
						  <th  width="10%" style="text-align:center;border-top:1px solid;background-color:#4aa1ce"><b><%=localeObj.getTranslatedText("Total")%></b></th>
						   <th  width="20%" style="text-align:center;border-top:1px solid;background-color:#4aa1ce"><b><%=localeObj.getTranslatedText("Recruitment Progress")%></b></th>
						 
							    
					     </tr>
					</thead>
				
						 <tr >
						
							<td  colspan="2" style="text-align: center;"><b><%=localeObj.getTranslatedText("Self Target")%></b></td>
							<td style="text-align: center;"><input style="text-align:center" name="ga" id="ga" type="text" class="textObj" maxlength="3" value="<%=goalObj.getGa() %>" onKeyup="javascript:getTotalValue(this)"  onBlur="javascript:getTotalValue(this);calculateTotal(this);" /></td>
							<td style="text-align: center;"><input  style="text-align:center" name="ha" id="ha" type="text" class="textObj" maxlength="3" value="<%=goalObj.getHa() %>" onKeyup="javascript:getTotalValue(this)"  onBlur="javascript:getTotalValue(this);calculateTotal(this);" /></td>
							<td style="text-align: center;"><input style="text-align:center" name="gen_y" id="gen_y" type="text" class="textObj" maxlength="3" value="<%=goalObj.getGen_y() %>" onKeyup="javascript:getTotalValue(this)"  onBlur="javascript:getTotalValue(this);calculateTotal(this);" /></td>
							<td style="text-align: center;"><input style="text-align:center" name="sa" id="sa" type="text" class="textObj" maxlength="3" value="<%=goalObj.getSa() %>" onKeyup="javascript:getTotalValue(this)"  onBlur="javascript:getTotalValue(this);calculateTotal(this);" /></td>
							<td style="text-align: center;"><input style="text-align:center"  name="total" id="total" type="text" class="textObj" value="<%=goalObj.getTotal() %>"  readonly="readonly"/></td>
							<td style="text-align: center;"><span id="yu">0</span>/<span id=total3><%=goalObj.getTotal() %></span></td>
							
							
						</tr>
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
				        <table style="width: 100%; border: none;" border="0" cellspacing="0" cellpadding="0"  >
				        <tr >
						
							<td style="text-align: center; border: none;">
							<table style="border: none;">
							<tr ><td style="border: none;"><b><%=localeObj.getTranslatedText("Total ")%></b></td><td style="border: none;"></td></tr>
							<tr><td style="border: none; padding-right: 0px;"><div id=total1>0</div></td><td style="border: none; padding-left: 0px;"><div id=total2><%=goalObj.getTotal() %></div></td></tr>
							</table></td>
							<td style="text-align: center; border: none;">&nbsp;</td>
							<td style="text-align: center; border: none;">&nbsp;</td>
							<td style="text-align: center; border: none;">&nbsp;</td>
							<td style="text-align: center; border: none;">&nbsp;</td>
							<td style="text-align: center; border: none;">&nbsp;</td>
							<td style="text-align: center; border: none;"><a href="#"  class="btn1" onClick="javascript:submitGoal();"><%=localeObj.getTranslatedText("Submit")%></a></td>
							
						</tr>
				
					</table>
           
				</div>
			
    </div>
     