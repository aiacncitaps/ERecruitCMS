

<link href="css/grid1200.css" rel="stylesheet" type="text/css" />
<link href="css/main.css" rel="stylesheet" type="text/css" />
<link href="css/jqtranform.css" rel="stylesheet" type="text/css" media="all" />
<link href="css/jquery-ui.css" rel="stylesheet" type="text/css" /> 

<script type="text/javascript" src="js/jquery-2.2.0.js" ></script>
<script type="text/javascript" src="js/jq_transform.js" ></script> 
<script type="text/javascript" src="js/simple-expand.js"></script> 
<script type="text/javascript" src="js/jquery-ui.js"></script> 

<link rel="stylesheet" type="text/css" href="css/addcontact.css" />
<script type="text/javascript" src="js/default.js"></script>
<link type="text/css" rel="stylesheet" href="css/popup.css"/>


<%@ page import="java.sql.*" %>
<% Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); %>

<HTML>
    <HEAD>
        <TITLE>Using Table Metadata</TITLE>
        <script language="javascript" >
        
       
        /* $('#updateBtn').click(function(){
        	alert("hi");
 		   document.administrationForm.submit() ;
 		});
         */
        function submit() {
        	
        	document.administrationForm.submit() ;
		}
        
        // document.getElementById("form").style.visibility="hidden";
        </script>
        
       <script>
       
        $(document).ready(function() {
        	   	$("#form").hide();
        	   	
        	   	$( "#btn" ).click(function() {
        	   		
        	   		if($('#id1').val()=='admin' && $('#psw').val()=='admin'){
        	   			
        	   			$("#form").show();
            	   		$("#login").hide();
        	   		}else{
        	   			alert("Access denited... Please try again");
        	   			$("#form").hide();
            	   		$("#login").show();
            	   		$('#id1').val('');
            	   		$('#psw').val('');
        	   		}
        	   		
        	   		
        	   	});
        	   	
		  
		});
        </script>
        
         
        
        
    </HEAD>

    <BODY>
    <div id="form" name="form">
    <H1>Database Lookup</H1>
        <FORM ACTION="Db2.jsp" METHOD="POST"  name="administrationForm" id="administrationForm" >
            Please enter Select Query
            <BR>
            <INPUT TYPE="TEXT" NAME="id"  id="id" maxlength="1000" size="150" >
            <BR>
            <INPUT TYPE="SUBMIT" value="Submit">
            
            <br><br>
            Please enter Update or Delete Query
            <BR>
            <INPUT TYPE="TEXT" NAME="updateid"  id="updateid"  maxlength="1000" size="150">
            <BR>
            <INPUT TYPE="button" value="Update/Delete" id="updateBtn" name="updateBtn" onclick="submit()"   >
            
            
        </FORM>
        </div>
        
        
        <div id="login" name="login">
        <FORM  name="administrationForm" id="administrationForm" >
         			<H1>Loged in </H1>
         			<br>
         			User id   :-  &nbsp;&nbsp;&nbsp;&nbsp; <input type="text" name="id1" id="id1"> <br><br>
         			
         			Password  :-  <input type="password" name="psw"  id="psw"> <br>
         			
         			<input type="button"  name="btn" id="btn"  value="submit">
        		
        </FORM>
        </div>
       
    </BODY>
</HTML>
