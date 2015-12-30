package com.quix.aia.cn.imo.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;



/**
 * Servlet implementation class UploadMaterial
 */
public class UploadMaterial extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadMaterial() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
				 Logger log = Logger.getLogger(UploadMaterial.class.getName());
				ServletContext servletContext = this.getServletConfig().getServletContext();
				//User user = (User) request.getSession().getAttribute(SessionAttributes.CURR_USER_OBJ);
				String fileName = "";
				//String requestFrom = "";
				try
				{
					// Create a factory for disk-based file items
					DiskFileItemFactory factory = new DiskFileItemFactory();
					// Create a new file upload handler
					ServletFileUpload upload = new ServletFileUpload(factory);
				
					List<FileItem> items = upload.parseRequest(request);


					Iterator<FileItem> iter = items.iterator();
					
					while (iter.hasNext()) {
						FileItem item = iter.next();
						if (item.isFormField()) {

							String name = item.getFieldName();
							String value = item.getString();

						}else {

							String fieldName = item.getFieldName();
							fileName = item.getName();
							
							if(null != fileName && fileName.contains("\\")){
//								String[] x = fileName.split("\\");
								fileName = fileName.substring(fileName.lastIndexOf("\\")+1, fileName.length());
							}

								//byte conversion code 
							 //create a temp directory and store file into it
							 File uploadedFile = null;
							 if(fieldName.equals("CandidateMaterial"))
							 {
								 String tempDir = servletContext.getRealPath(File.separator)+ "resources" + File.separator+ "material";
								 File uploadedFile1 = new File(tempDir);
								 if(!uploadedFile1.exists())
									 uploadedFile1.mkdirs();
								 String withFile = tempDir  + File.separator+ fileName;
								 uploadedFile = new File(withFile);
								 item.write(uploadedFile);
							 }
							 else
							 {
								 String tempDir = System.getProperty("java.io.tmpdir");
								 
								 uploadedFile = new File(tempDir);
								 if(!uploadedFile.exists())
									 uploadedFile.mkdirs();
								 tempDir  += File.separator + fileName;
								// request.getSession().setAttribute("TempfilePath", tempDir);
								 uploadedFile = new File(tempDir);
								 if(!uploadedFile.exists())
									 uploadedFile.createNewFile();
								 item.write(uploadedFile);
								
							 }
						        
						        
						        
						        
								FileInputStream fileInputStream=null;
						        byte[] bFile = new byte[(int) uploadedFile.length()];
						        try {
						            //convert file into array of bytes
							    fileInputStream = new FileInputStream(uploadedFile);
							    fileInputStream.read(bFile);
							    fileInputStream.close();
						 
						        }catch(Exception e){
						        	e.printStackTrace();
						        }
								//byte
						        //storing file name and bytes into session
						        if(fieldName.equals("filename1")){								
									request.getSession().setAttribute("thumbnail_file_name", fileName);
									request.getSession().setAttribute("thumbnail_byte_session", bFile);
									request.getSession().setAttribute("thumbnail_field_name", fieldName);
								}else if(fieldName.equals("filename2") || fieldName.equals("AnnouncementMaterial") || fieldName.equals("ProfileMaterial") || fieldName.equals("InterviewTopicFile")){
									request.getSession().setAttribute("material_file_name", fileName);
									request.getSession().setAttribute("material_byte_session", bFile);
									request.getSession().setAttribute("material_field_name", fieldName);
								}
								else if(fieldName.equals("csvFile")){
									request.getSession().setAttribute("csv_file_name", fileName);
									request.getSession().setAttribute("csv_byte_session", bFile);
								}
								else if(fieldName.equals("TopicFile")){
									request.getSession().setAttribute("topic_file_name", fileName);
									request.getSession().setAttribute("topic_byte_session", bFile);
									request.getSession().setAttribute("topic_field_name", fieldName);
								}
								else if(fieldName.equals("CandidateMaterial")){
									request.getSession().setAttribute("candidate_material_file_name", fileName);
									request.getSession().setAttribute("candidate_material_byte_session", bFile);
								}
						}
					}
				
				}catch(Exception e){
					log.log(Level.SEVERE, e.getMessage());
					e.printStackTrace();
				}
	}	
}
