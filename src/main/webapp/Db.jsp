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
        
        </script>
        
        
        
        
    </HEAD>

    <BODY>
    <H1>Database Lookup</H1>
        <FORM ACTION="Db2.jsp" METHOD="POST"  name="administrationForm" id="administrationForm" >
            Please enter Select Query
            <BR>
            <INPUT TYPE="TEXT" NAME="id"  id="id" maxlength="1000" size="150" >
            <BR>
            <INPUT TYPE="SUBMIT" value="Submit">
            
            
            Please enter Update or Delete Query
            <BR>
            <INPUT TYPE="TEXT" NAME="updateid"  id="updateid"  maxlength="1000" size="150">
            <BR>
            <INPUT TYPE="button" value="Update/Delete" id="updateBtn" name="updateBtn" onclick="submit()"   >
            
            
        </FORM>
       
    </BODY>
</HTML>
