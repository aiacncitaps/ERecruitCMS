<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@ page import="net.sf.json.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="javax.sql.*"%>
<%-- <%@ page import="org.springframework.context.*"%> --%>
<%-- <%@ page import="org.springframework.context.support.*"%> --%>
<%-- <%@ page import="com.tohours.tookeen.constants.*"%> --%>
<%@ page import="java.io.*"%>
<%@ page import="java.net.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.util.zip.*"%>
<%@ page import="javax.servlet.*"%>
<%@ page import="javax.servlet.http.*"%>
<%@ page import="org.apache.commons.fileupload.*"%>
<%@ page import="org.apache.commons.fileupload.disk.*"%>
<%@ page import="org.apache.commons.fileupload.servlet.*"%>
<%@ page import="org.apache.commons.io.output.*"%>
<%@ page import="org.apache.commons.fileupload.util.*"%>

<%!
public void unZipIt(String zipFile, String outputFolder) throws Exception{
    byte[] buffer = new byte[1024];
    try {
        //create output directory is not exists
        File folder = new File(outputFolder);
        if (!folder.exists()) {
            folder.mkdir();
        }

        //get the zip file content
        ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
        //get the zipped file list entry
        ZipEntry ze = zis.getNextEntry();

        while (ze != null) {

            String fileName = ze.getName();
            File newFile = new File(outputFolder + File.separator + fileName);

            if(ze.isDirectory() == false){
	            System.out.println("file unzip : " + newFile.getAbsoluteFile());
	
	            //create all non exists folders
	            //else you will hit FileNotFoundException for compressed folder
	            new File(newFile.getParent()).mkdirs();
	
	            FileOutputStream fos = new FileOutputStream(newFile);
	
	            int len;
	            while ((len = zis.read(buffer)) > 0) {
	                fos.write(buffer, 0, len);
	            }
	
	            fos.close();
            }
            ze = zis.getNextEntry();
        }

        zis.closeEntry();
        zis.close();

        System.out.println("Done");

    } catch(IOException ex) {
    	ex.printStackTrace();
    	throw ex;
    }
}
%>
<%
try{
if(ServletFileUpload.isMultipartContent(request)){
	String realPath = request.getRealPath("/"); 
	List multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
	File zip = null;
	if(multiparts.size() > 0){
		FileItem item =(FileItem) multiparts.get(0);
		if(!item.isFormField()){
			zip = new File(realPath + File.separator + item.getName());
			item.write(zip);
		}
	}
	
	if(zip != null && zip.exists()){
		unZipIt(zip.getAbsolutePath(), realPath);
		zip.delete();
		out.print("update success");
	}
	
}else{
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>File Upload</title>
</head>

<body>
	<div>
		<h3>
			<%out.print(java.net.InetAddress.getLocalHost().getHostAddress());%>
		</h3>
		<h3>Choose File to Upload in Server</h3>
		<form action="folder.jsp" method="post" enctype="multipart/form-data">
			<input type="file" name="file" /> <input type="submit"
				value="upload" />
		</form>
	</div>

</body>
</html>
<%
}
}catch(Exception e){
	StringWriter sw = new StringWriter();  
	e.printStackTrace(new PrintWriter(sw, true));
	out.print(sw);
}
%>
