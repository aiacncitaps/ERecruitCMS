<%@page import="org.hibernate.connection.ConnectionProvider"%>
<%@page import="org.hibernate.engine.SessionFactoryImplementor"%>
<%@page import="org.hibernate.SQLQuery"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="org.hibernate.transform.AliasToEntityMapResultTransformer"%>
<%@page import="org.hibernate.Query"%>
<%@page import="com.quix.aia.cn.imo.database.HibernateFactory"%>
<%@page import="org.hibernate.Transaction"%>
<%@page import="org.hibernate.Session"%>
<%@ page import="java.sql.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); %>

<HTML>
    <HEAD>
        <TITLE>Using Table Metadata</TITLE>
        <script type="text/javascript">
function reloadCache(){
	
	window.location.href = "Db.jsp";
				
}


</script>
        
        
        
    </HEAD>

    <BODY>
    <H1>Database Lookup</H1>  <input  type="button" id="back"  name="back" onclick=" reloadCache()" value="Back">
       
        <H1>Using Table Metadata</H1>

        <% 
        /*     Connection connection = DriverManager.getConnection(
                "jdbc:sqlserver://202.150.218.108:1433;databaseName=IMOCN_CLIENT;instanceName=MSSQLSERVER;", "sa", "5!ngap0re");

            Statement statement = connection.createStatement() ; */
          
            if(request.getParameter("id")!=null && !request.getParameter("id").equals("")){
            	
           
            
            String sql=request.getParameter("id");
            Connection connection =null;
            Session session2 = HibernateFactory.openSession();
            SessionFactoryImplementor sessionFactoryImplementation = (SessionFactoryImplementor) session2.getSessionFactory();
            ConnectionProvider connectionProvider = sessionFactoryImplementation.getConnectionProvider();
            connection = connectionProvider.getConnection();
            Statement statement = connection.createStatement() ; 
            ResultSet resultset =  statement.executeQuery(sql) ; 
        %>

        <TABLE BORDER="1">
       
            <TR>
            <%
            	for(int i=0 ; i<resultset.getMetaData().getColumnCount() ; i++){
            %>
            
                <TH><%= resultset.getMetaData().getColumnName(i+1)%></TH>
              <%} %>  
            </TR>
            
            <TR>
            <%
            	for(int i=0 ; i<resultset.getMetaData().getColumnCount() ; i++){
            %>
            
                <TH><%= resultset.getMetaData().getColumnTypeName(i+1)  %>(<%= resultset.getMetaData().getColumnDisplaySize(i+1)  %>)</TH>
             
                
              <%} %>  
            </TR>
            <% while(resultset.next()){ %>
            <TR>
            <%
            	for(int i=0 ; i<resultset.getMetaData().getColumnCount() ; i++){
            %>
                <TD> <%= resultset.getString(i+1) %></td>
              <%} %> 
            </TR>
            <% } %>
            
            
            <%
            resultset.close();
            statement.close();
            connection.close();
            }
            %>
            
            
           
        </TABLE>
        <%
            
            if(request.getParameter("updateid")!=null){
            	
           	 String sql=request.getParameter("updateid");
           	
                Connection connection =null;
                Session session2 = HibernateFactory.openSession();
                SessionFactoryImplementor sessionFactoryImplementation = (SessionFactoryImplementor) session2.getSessionFactory();
                ConnectionProvider connectionProvider = sessionFactoryImplementation.getConnectionProvider();
                connection = connectionProvider.getConnection();
                PreparedStatement  statement = connection.prepareStatement(sql) ; 
                statement.executeUpdate() ; 
                connection.commit();
           	
            %>
            <label>Update Query Done </label>
            
            <%
            
            statement.close();
            connection.close();
            
            } %>
    </BODY>
</HTML>
