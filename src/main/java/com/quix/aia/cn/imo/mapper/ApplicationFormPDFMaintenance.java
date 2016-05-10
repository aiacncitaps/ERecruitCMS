/*******************************************************************************
* -----------------------------------------------------------------------------
* <br>
* <p><b>Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.</b> 
* <br>
* <br>
* This SOURCE CODE FILE, which has been provided by Quix as part
* of a Quix Creations product for use ONLY by licensed users of the product,
* includes CONFIDENTIAL and PROPRIETARY information of Quix Creations.
* <br>
* USE OF THIS SOFTWARE IS GOVERNED BY THE TERMS AND CONDITIONS
* OF THE LICENSE STATEMENT AND LIMITED WARRANTY FURNISHED WITH
* THE PRODUCT.<br>
* <br>
* </p>
* -----------------------------------------------------------------------------
* <br>
* <br>
* Modification History:
* Date              Developer          Change Description
* 07-May-2015       Jay          
* 
****************************************** *********************************** */
package com.quix.aia.cn.imo.mapper;
/**
 * <p>Logic to create Application form .</p>
 * 
 * @author Nibedita
 * @version 1.0
 *
 */

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.quix.aia.cn.imo.data.addressbook.AddressBook;
import com.quix.aia.cn.imo.data.addressbook.CandidateESignature;
import com.quix.aia.cn.imo.data.addressbook.CandidateEducation;
import com.quix.aia.cn.imo.data.addressbook.CandidateFamilyInfo;
import com.quix.aia.cn.imo.data.addressbook.CandidateProfessionalCertification;
import com.quix.aia.cn.imo.data.addressbook.CandidateWorkExperience;
import com.quix.aia.cn.imo.data.interview.InterviewCandidateMaterial;
import com.quix.aia.cn.imo.database.HibernateFactory;

import sun.font.FontManager;

public class ApplicationFormPDFMaintenance {

	public static void main(String args[]){
	//	pdf();
		 DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		 Date d1=new Date();
		 String time=(d1.getTime()+"").trim();
		System.out.println("date   --> "+df.format(new Date())+time);
		
	}
	static Font font = new Font(FontFamily.TIMES_ROMAN, 13, Font.NORMAL, BaseColor.BLACK);
	
	static Logger log = Logger.getLogger(ApplicationFormPDFMaintenance.class.getName());
	static SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy");
	
	
	
	
	
	public static InterviewCandidateMaterial pdf(HttpServletRequest request, AddressBook addressbook) {
		// TODO Auto-generated method stub
		log.log(Level.INFO,"ApplicationFormPDFMaintenance --> pdf ");
		Document document = new Document(PageSize.A4, 50, 50, 50, 50);
		//Font myAdobeTypekit = FontFactory.getFont(request.getRealPath(File.separator)+"resources"+File.separator+"HDZB_35.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		 
		try
	      {
			//String fontPath = "C:/Windows/Fonts";
			
			String fontPath=request.getRealPath("/")+"resources"+File.separator+"hxb-meixinti";
			File f2 = new File(fontPath+"/hxb-meixinti.ttf");
			BaseFont bf1=null;
	        if(f2.exists())
	        {
	        	bf1 = BaseFont.createFont(fontPath+"/hxb-meixinti.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
	        }else
	        {
	        	f2 = new File(fontPath+"/hxb-meixinti.ttf");
	
		        if(f2.exists())
		        {
		        	System.out.println("File existed");
		        	bf1 = BaseFont.createFont(fontPath+"hxb-meixinti.ttf", BaseFont.IDENTITY_V, BaseFont.EMBEDDED);
		        }else
		        {
		        	bf1 = BaseFont.createFont(FontManager.getFontPath( true )+"/hxb-meixinti.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		        }
	        }
	        
	        Font normalFontCH = new Font(bf1, 15);
	       
			
			
			 String fileName=request.getRealPath("/")+"resources"+File.separator+"upload"+File.separator;
			 DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			 Date d1=new Date();
			 String time=(d1.getTime()+"").trim();
			 String str=df.format(new Date())+time;
			 
	         PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName+"ApplicationForm_"+str+".pdf"));
	         InterviewCandidateMaterial material=new InterviewCandidateMaterial();
	         material.setMaterialFileName("ApplicationForm_"+str+".pdf");
	         material.setCandidateCode(Integer.parseInt(request.getParameter("candidateCode")) );
	         material.setInterviewCode(Integer.parseInt(request.getParameter("interviewCode")));

	         // PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("E://applicatonForm2.pdf"));
	         	document.open();
	         	
				 addTitle(document, writer, "formHeaderTitle","Application Form");
		         document= personalInformation(document, writer,addressbook,normalFontCH);
		         document=familyInformation(document, writer,addressbook);
		         document=workExperience(document,writer,addressbook);
		         document=Education(document,writer,addressbook);
				 document=personalCertification(document,writer,addressbook);
				 document=ESingnature(document,writer,addressbook);
			
	         document.close();
	         
	         ByteArrayOutputStream bos = new ByteArrayOutputStream();
	         File file = new File(fileName+"ApplicationForm_"+str+".pdf");
	         FileInputStream fis = new FileInputStream(file);
	         byte[] buf = new byte[1024];
	             for (int readNum; (readNum = fis.read(buf)) != -1;) {
	                 bos.write(buf, 0, readNum); //no doubt here is 0
	             }
	             material.setFormContent (bos.toByteArray());
	             material.setCreatedDate(new Date());
	             material.setMaterialDescrption("AppicationForm");
	            
	       //  applicationForm.setFormContent(bos.toByteArray());
	         
	         
	        return material; 
	      } catch (Exception e)
	      {
	    	 log.log(Level.INFO,"ApplicationFormPDFMaintenance --> pdf --> Exception  "+e.getMessage());
	         e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("ApplicationFormPDFMaintenance",Level.SEVERE+"",errors.toString());
	      } 
		
		return null;
		
	}
	
	
	private static Document ESingnature(Document document, PdfWriter writer, AddressBook addressbook) {
		// TODO Auto-generated method stub
		log.log(Level.INFO,"ApplicationFormPDFMaintenance --> ESingnature ");
			try {
				
			document.newPage();
			
			
			PdfPTable table = new PdfPTable(2);
	 		table.setSpacingBefore(10);
	 		table.setWidthPercentage(100f);
			
	 		PdfPCell  c1 = new PdfPCell(new Phrase("E-singnature "));
		 		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		 		c1.setColspan(4);
		 		c1.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
		 		c1.setFixedHeight(30f);
		 		c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
				c1.setBorder(Rectangle.BOX);
				c1.setFixedHeight(25f);
		 		table.addCell(c1);
		 		table.setHeaderRows(1);
		 		
		 		for (int i = 0; i < 2; i++) {
		 			c1 = new PdfPCell(new Phrase(" ",font));
			 		c1.setBorder(Rectangle.NO_BORDER);
					c1.setLeading(4f, 0f);
					c1.setFixedHeight(15f);
					table.addCell(c1);
				}
		 		
		 		Iterator itr=null;
		 		CandidateESignature esignature=new CandidateESignature();
		 		if(addressbook.getCandidateESignatures().size()>0){
		 			itr=addressbook.getCandidateESignatures().iterator();
		 			esignature=(CandidateESignature)itr.next();
		 		}
		 		
				c1 = new PdfPCell(new Phrase("Branch : "+esignature.getBranch(),font));
		 		c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				c1.setBorder(Rectangle.BOTTOM);
				table.addCell(c1);
				
				c1 = new PdfPCell(new Phrase("Servicing Department : "+esignature.getServiceDepartment(),font));
		 		c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				c1.setBorder(Rectangle.BOTTOM);
				table.addCell(c1);
				
				
				
				for (int i = 0; i < 2; i++) {
		 			c1 = new PdfPCell(new Phrase(" ",font));
			 		c1.setBorder(Rectangle.NO_BORDER);
					c1.setLeading(4f, 0f);
					c1.setFixedHeight(15f);
					table.addCell(c1);
				}
				
			
				c1 = new PdfPCell(new Phrase("City : "+esignature.getCity(),font));
		 		c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				c1.setBorder(Rectangle.BOTTOM);
				table.addCell(c1);
				
				c1 = new PdfPCell(new Phrase("Agent Code : "+esignature.getAgentId(),font));
		 		c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				c1.setBorder(Rectangle.BOTTOM);
				table.addCell(c1);
				
				
				for (int i = 0; i < 4; i++) {
		 			c1 = new PdfPCell(new Phrase(" ",font));
			 		c1.setBorder(Rectangle.NO_BORDER);
					c1.setLeading(4f, 0f);
					c1.setFixedHeight(15f);
					table.addCell(c1);
				}
				
				document.add(table);
				
				
				
				table = new PdfPTable(2);
		 		table.setSpacingBefore(10);
		 		table.setWidthPercentage(100f);
		 		table.setWidths(new int[] {80,20});
		 		
		 		
				c1 = new PdfPCell(new Phrase("Presently attached with another insurance Company ?  ",font));
		 		c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				table.addCell(c1);
				
				c1 = new PdfPCell(new Phrase("Yes \t\t\t\t\t\t\t\t  No",font));
		 		c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				table.addCell(c1);
				
				for (int i = 0; i < 2; i++) {
					c1 = new PdfPCell(new Phrase(" ",font));
			 		c1.setBorder(Rectangle.NO_BORDER);
					c1.setLeading(4f, 0f);
					c1.setFixedHeight(15f);
					table.addCell(c1);
					
				}
				
				c1 = new PdfPCell(new Phrase("Presently in contact with any other AIA'S servicing Department ?   ",font));
		 		c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				table.addCell(c1);
				
				c1 = new PdfPCell(new Phrase("Yes \t\t\t\t\t\t\t\t  No",font));
		 		c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				table.addCell(c1);
				
				
				
				for (int i = 0; i < 2; i++) {
					c1 = new PdfPCell(new Phrase(" ",font));
			 		c1.setBorder(Rectangle.NO_BORDER);
					c1.setLeading(4f, 0f);
					c1.setFixedHeight(15f);
					table.addCell(c1);
					
				}
				
				
				c1 = new PdfPCell(new Phrase("Taken LOMBRA occupational test or PSP test in the past ?  If Yes, ",font));
		 		c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				table.addCell(c1);
				
				c1 = new PdfPCell(new Phrase("Yes \t\t\t\t\t\t\t\t  No",font));
		 		c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				table.addCell(c1);
				
				for (int i = 0; i < 2; i++) {
					c1 = new PdfPCell(new Phrase(" ",font));
			 		c1.setBorder(Rectangle.NO_BORDER);
					c1.setLeading(4f, 0f);
					c1.setFixedHeight(15f);
					table.addCell(c1);
					
				}
				
				c1 = new PdfPCell(new Phrase("Please provide the result.  ",font));
		 		c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				table.addCell(c1);
				
				for(int i=0 ; i<5 ; i++){
					c1 = new PdfPCell(new Phrase(" ",font));
			 		c1.setBorder(Rectangle.NO_BORDER);
					c1.setLeading(4f, 0f);
					c1.setFixedHeight(15f);
					table.addCell(c1);
				}
				document.add(table);
				
				
				Paragraph para1 = new Paragraph();
				para1.add(new Chunk("Applicant's Declaration ", font));
				para1.setAlignment(Element.ALIGN_LEFT);
				para1.setSpacingAfter(5f);
				document.add(para1);
				
				table = new PdfPTable(2);
		 		table.setSpacingBefore(10);
		 		table.setWidthPercentage(100f);
		 		
		 		for(int i=0 ; i<4 ; i++){
					c1 = new PdfPCell(new Phrase(" ",font));
			 		c1.setBorder(Rectangle.NO_BORDER);
					c1.setLeading(4f, 0f);
					c1.setFixedHeight(15f);
					table.addCell(c1);
					
					c1 = new PdfPCell(new Phrase(" ",font));
			 		c1.setBorder(Rectangle.NO_BORDER);
					c1.setLeading(4f, 0f);
					c1.setFixedHeight(15f);
					table.addCell(c1);
				}
			
				
				c1 = new PdfPCell(new Phrase("Application Date",font));
		 		c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				table.addCell(c1);
				
				c1 = new PdfPCell(new Phrase("Applicant/Candidate Name  :",font));
		 		c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				table.addCell(c1);
				
				if(esignature.getApplicationDate()!=null){
					c1 = new PdfPCell(new Phrase(format.format(esignature.getApplicationDate()),font));
			 		c1.setBorder(Rectangle.NO_BORDER);
					c1.setLeading(4f, 0f);
					c1.setFixedHeight(15f);
					table.addCell(c1);
				}else{
					c1 = new PdfPCell(new Phrase("",font));
			 		c1.setBorder(Rectangle.NO_BORDER);
					c1.setLeading(4f, 0f);
					c1.setFixedHeight(15f);
					table.addCell(c1);
				}
				
				
				
				c1 = new PdfPCell(new Phrase(esignature.getCandidateName(),font));
		 		c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				table.addCell(c1);
				
				for(int i=0 ; i<4; i++){
					c1 = new PdfPCell(new Phrase(" ",font));
			 		c1.setBorder(Rectangle.NO_BORDER);
					c1.setLeading(4f, 0f);
					c1.setFixedHeight(15f);
					table.addCell(c1);
					
					c1 = new PdfPCell(new Phrase(" ",font));
			 		c1.setBorder(Rectangle.NO_BORDER);
					c1.setLeading(4f, 0f);
					c1.setFixedHeight(15f);
					table.addCell(c1);
				}
				document.add(table);
				
				para1 = new Paragraph();
				para1.add(new Chunk("E-Signature  : ", font));
				para1.setAlignment(Element.ALIGN_LEFT);
				para1.setSpacingAfter(5f);
				document.add(para1);
				
				c1 = new PdfPCell(new Phrase(" ",font));
		 		c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				table.addCell(c1);
				
				if(esignature.geteSignaturePhoto()!=null){
					Image image = Image.getInstance(esignature.geteSignaturePhoto());
					para1 = new Paragraph();
					para1.add(image);
					para1.setAlignment(Element.ALIGN_LEFT);
					para1.setSpacingAfter(5f);
					document.add(para1);
				}else{
					para1 = new Paragraph();
					para1.add("");
					para1.setAlignment(Element.ALIGN_LEFT);
					para1.setSpacingAfter(5f);
					document.add(para1);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.log(Level.INFO,"ApplicationFormPDFMaintenance --> ESingnature "+e.getMessage());
				e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("ApplicationFormPDFMaintenance",Level.SEVERE+"",errors.toString());
			}
			
			
		return document;
	}


	private static Document personalCertification(Document document,
			PdfWriter writer, AddressBook addressbook) {
		// TODO Auto-generated method stub
		log.log(Level.INFO,"ApplicationFormPDFMaintenance --> personalCertification ");
			try {
				
			/*  New Page   */
			document.newPage();
			
			PdfPTable table = new PdfPTable(2);
	 		table.setSpacingBefore(10);
	 		table.setWidthPercentage(100f);
	 		int i=0;

		for(Iterator itr  = addressbook.getCandidateProfessionalCertifications().iterator() ; itr.hasNext() ; ){
				CandidateProfessionalCertification procertification=(CandidateProfessionalCertification)itr.next();
				if(i!=0){
					PdfPCell c1 = new PdfPCell(new Phrase(" ",font));
			 		c1.setBorder(Rectangle.NO_BORDER);
					c1.setLeading(4f, 0f);
					c1.setFixedHeight(15f);
					table.addCell(c1);
					
					
					c1 = new PdfPCell(new Phrase(" ",font));
			 		c1.setBorder(Rectangle.NO_BORDER);
					c1.setLeading(4f, 0f);
					c1.setFixedHeight(15f);
					table.addCell(c1);
				}
				i++;
				PdfPCell c1 = new PdfPCell(new Phrase("PERSONAL CERTIFICATION"));
		 		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		 		c1.setColspan(4);
		 		c1.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
		 		c1.setFixedHeight(30f);
		 		c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
				c1.setBorder(Rectangle.BOX);
				c1.setFixedHeight(25f);
		 		table.addCell(c1);
		 		table.setHeaderRows(1);
		 		
		 		
		 		for (int j = 0; j < 2; j++) {
					c1 = new PdfPCell(new Phrase(" ",font));
			 		c1.setBorder(Rectangle.NO_BORDER);
					c1.setLeading(4f, 0f);
					c1.setFixedHeight(15f);
					table.addCell(c1);
					
				}
			
		 		
		 		c1 = new PdfPCell(new Phrase("Certificate Name: "+procertification.getCertificateName(),font));
		 		c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				c1.setBorder(Rectangle.BOTTOM);
				table.addCell(c1);
				
				c1 = new PdfPCell(new Phrase("Chrater Agency: "+procertification.getCharterAgency(),font));
		 		c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				c1.setBorder(Rectangle.BOTTOM);
				table.addCell(c1);
				
				
				for (int j = 0; j < 2; j++) {
					c1 = new PdfPCell(new Phrase(" ",font));
			 		c1.setBorder(Rectangle.NO_BORDER);
					c1.setLeading(4f, 0f);
					c1.setFixedHeight(15f);
					table.addCell(c1);
					
				}
		 		
		 		c1 = new PdfPCell(new Phrase("Charter Date : "+format.format(procertification.getCharterDate()) ,font));
		 		c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				c1.setBorder(Rectangle.BOTTOM);
				table.addCell(c1);
				
				
				c1 = new PdfPCell(new Phrase(" ",font));
		 		c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				//c1.setBorder(Rectangle.BOTTOM);
				table.addCell(c1);
				
				
			}
			
				document.add(table);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.log(Level.INFO,"ApplicationFormPDFMaintenance --> personalCertification "+e.getMessage());
			e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("ApplicationFormPDFMaintenance",Level.SEVERE+"",errors.toString());
		}
		
		return document;
	}


	private static Document Education(Document document, PdfWriter writer, AddressBook addressbook) {
		// TODO Auto-generated method stub
		log.log(Level.INFO,"ApplicationFormPDFMaintenance --> Education ");
		try {
		
			/*  New Page   */
			document.newPage();
			
			PdfPTable table = new PdfPTable(2);
	 		table.setSpacingBefore(10);
	 		table.setWidthPercentage(100f);
	 		
	 		int i=0;
				for(Iterator itr  = addressbook.getCandidateEducations().iterator(); itr.hasNext() ; ){
					CandidateEducation candidateEducation= (CandidateEducation)itr.next();
				if(i!=0){
					PdfPCell c1 = new PdfPCell(new Phrase(" ",font));
			 		c1.setBorder(Rectangle.NO_BORDER);
					c1.setLeading(4f, 0f);
					c1.setFixedHeight(15f);
					table.addCell(c1);
					
					
					c1 = new PdfPCell(new Phrase(" ",font));
			 		c1.setBorder(Rectangle.NO_BORDER);
					c1.setLeading(4f, 0f);
					c1.setFixedHeight(15f);
					table.addCell(c1);
				}
				
				i++;
				PdfPCell c1 = new PdfPCell(new Phrase("EDUCATION"));
		 		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		 		c1.setColspan(4);
		 		c1.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
		 		c1.setFixedHeight(30f);
		 		c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
				c1.setBorder(Rectangle.BOX);
				c1.setFixedHeight(25f);
		 		table.addCell(c1);
		 		table.setHeaderRows(1);
		 		
		 		
		 		for (int j = 0; j < 2; j++) {
					c1 = new PdfPCell(new Phrase(" ",font));
			 		c1.setBorder(Rectangle.NO_BORDER);
					c1.setLeading(4f, 0f);
					c1.setFixedHeight(15f);
					table.addCell(c1);
					
				}
				
			
		 		
		 		c1 = new PdfPCell(new Phrase("Start Date: "+format.format(candidateEducation.getStartDate()),font));
		 		c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				c1.setBorder(Rectangle.BOTTOM);
				table.addCell(c1);
				
				c1 = new PdfPCell(new Phrase("End Date: "+format.format(candidateEducation.getEndDate()),font));
		 		c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				c1.setBorder(Rectangle.BOTTOM);
				table.addCell(c1);
				
			
				
				for (int j = 0; j < 2; j++) {
					c1 = new PdfPCell(new Phrase(" ",font));
			 		c1.setBorder(Rectangle.NO_BORDER);
					c1.setLeading(4f, 0f);
					c1.setFixedHeight(15f);
					table.addCell(c1);
					
				}
		 		
		 		c1 = new PdfPCell(new Phrase("Witness : "+candidateEducation.getWitness(),font));
		 		c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				c1.setBorder(Rectangle.BOTTOM);
				table.addCell(c1);
				
				c1 = new PdfPCell(new Phrase("Education : "+candidateEducation.getEducation(),font));
		 		c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				c1.setBorder(Rectangle.BOTTOM);
				table.addCell(c1);
				
				
				for (int j = 0; j < 2; j++) {
					c1 = new PdfPCell(new Phrase(" ",font));
			 		c1.setBorder(Rectangle.NO_BORDER);
					c1.setLeading(4f, 0f);
					c1.setFixedHeight(15f);
					table.addCell(c1);
					
				}
		 		
		 		c1 = new PdfPCell(new Phrase("Education Level : "+candidateEducation.getEducationLevel(),font));
		 		c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				c1.setBorder(Rectangle.BOTTOM);
				table.addCell(c1);
				
				c1 = new PdfPCell(new Phrase("School : "+candidateEducation.getSchool(),font));
		 		c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				c1.setBorder(Rectangle.BOTTOM);
				table.addCell(c1);
				
				for (int j = 0; j < 2; j++) {
					c1 = new PdfPCell(new Phrase(" ",font));
			 		c1.setBorder(Rectangle.NO_BORDER);
					c1.setLeading(4f, 0f);
					c1.setFixedHeight(15f);
					table.addCell(c1);
					
				}
				
				
				c1 = new PdfPCell(new Phrase("Witness Contect Number : "+candidateEducation.getWitnessContactNo(),font));
				c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				c1.setBorder(Rectangle.BOTTOM);
				table.addCell(c1);
				
				c1 = new PdfPCell(new Phrase(" ",font));
		 		c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				//c1.setBorder(Rectangle.BOTTOM);
				table.addCell(c1);
			
		}
			document.add(table);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.log(Level.INFO,"ApplicationFormPDFMaintenance --> Education "+e.getMessage());
			e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("ApplicationFormPDFMaintenance",Level.SEVERE+"",errors.toString());
		}
		
		return document;
	}


	private static Document workExperience(Document document, PdfWriter writer, AddressBook addressbook) {
		// TODO Auto-generated method stub
		log.log(Level.INFO,"ApplicationFormPDFMaintenance --> workExperience ");
		try {
				
				document.newPage();
				
				PdfPTable table = new PdfPTable(3);
				table.setSpacingBefore(10);
				table.setWidthPercentage(100f);
				
				int i=0;
					
			for(Iterator itr  = addressbook.getCandidateWorkExperiences().iterator() ; itr.hasNext() ; ){
				CandidateWorkExperience candidateWorkExp=(CandidateWorkExperience)itr.next();
				if(i!=0){
					
					PdfPCell c1 = new PdfPCell(new Phrase(" ",font));
					c1.setBorder(Rectangle.NO_BORDER);
					c1.setLeading(4f, 0f);
					c1.setFixedHeight(15f);
					table.addCell(c1);
					
					
					c1 = new PdfPCell(new Phrase(" ",font));
					c1.setBorder(Rectangle.NO_BORDER);
					c1.setLeading(4f, 0f);
					c1.setFixedHeight(15f);
					table.addCell(c1);
					
					c1 = new PdfPCell(new Phrase(" ",font));
					c1.setBorder(Rectangle.NO_BORDER);
					c1.setLeading(4f, 0f);
					c1.setFixedHeight(15f);
					table.addCell(c1);
				}
				i++;
				PdfPCell c1 = new PdfPCell(new Phrase("WORK EXPERIENCE"));
				c1.setHorizontalAlignment(Element.ALIGN_LEFT);
				c1.setColspan(4);
				c1.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
				c1.setFixedHeight(30f);
				c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
				c1.setBorder(Rectangle.BOX);
				c1.setFixedHeight(25f);
				table.addCell(c1);
				table.setHeaderRows(1);
				
				
				for (int j = 0; j < 3; j++) {
					c1 = new PdfPCell(new Phrase(" ",font));
			 		c1.setBorder(Rectangle.NO_BORDER);
					c1.setLeading(4f, 0f);
					c1.setFixedHeight(15f);
					table.addCell(c1);
					
				}
				
				c1 = new PdfPCell(new Phrase("Start Date: "+format.format(candidateWorkExp.getStartDate()),font));
				c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				c1.setBorder(Rectangle.BOTTOM);
				table.addCell(c1);
				
				c1 = new PdfPCell(new Phrase("End Date: "+format.format(candidateWorkExp.getEndDate()),font));
				c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				c1.setBorder(Rectangle.BOTTOM);
				table.addCell(c1);
				
				c1 = new PdfPCell(new Phrase("Witness: "+candidateWorkExp.getWitness(),font));
				c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				c1.setBorder(Rectangle.BOTTOM);
				table.addCell(c1);
				
				
				for (int j = 0; j < 3; j++) {
					c1 = new PdfPCell(new Phrase(" ",font));
			 		c1.setBorder(Rectangle.NO_BORDER);
					c1.setLeading(4f, 0f);
					c1.setFixedHeight(15f);
					table.addCell(c1);
					
				}
				
				c1 = new PdfPCell(new Phrase("Unit : "+candidateWorkExp.getUnit(),font));
				c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				c1.setBorder(Rectangle.BOTTOM);
				table.addCell(c1);
				
				c1 = new PdfPCell(new Phrase("Occupation : "+candidateWorkExp.getOccupation(),font));
				c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				c1.setBorder(Rectangle.BOTTOM);
				table.addCell(c1);
				
				c1 = new PdfPCell(new Phrase("Witness Contect Number : \n\n\n  \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+candidateWorkExp.getWitnessContactNo(),font));
				c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(25f);
				//c1.setPaddingBottom(10f);
				c1.setBorder(Rectangle.BOTTOM);
				
				table.addCell(c1);
				
				for (int j = 0; j < 3; j++) {
					c1 = new PdfPCell(new Phrase(" ",font));
			 		c1.setBorder(Rectangle.NO_BORDER);
					c1.setLeading(4f, 0f);
					c1.setFixedHeight(15f);
					table.addCell(c1);
					
				}
				c1 = new PdfPCell(new Phrase("Income : "+candidateWorkExp.getIncome(),font));
				c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				c1.setBorder(Rectangle.BOTTOM);
				table.addCell(c1);
				
				c1 = new PdfPCell(new Phrase("Possition : "+candidateWorkExp.getPosition(),font));
				c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				c1.setBorder(Rectangle.BOTTOM);
				table.addCell(c1);
				
				c1 = new PdfPCell(new Phrase(" ",font));
				c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				c1.setBorder(Rectangle.BOTTOM);
				table.addCell(c1);
				
				
				
				}
				
					document.add(table);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					log.log(Level.INFO,"ApplicationFormPDFMaintenance --> workExperience "+e.getMessage());
					e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					logsMain.insertLogs("ApplicationFormPDFMaintenance",Level.SEVERE+"",errors.toString());
				}
		return document;
	}


	private static Document familyInformation(Document document,
			PdfWriter writer, AddressBook addressbook) {
		// TODO Auto-generated method stub
		log.log(Level.INFO,"ApplicationFormPDFMaintenance --> familyInformation ");
			try {
				
				
			/*  New Page   */
			document.newPage();
			
			PdfPTable table = new PdfPTable(3);
	 		table.setSpacingBefore(10);
	 		table.setWidthPercentage(100f);
	 		//Set familylist=addressbook.getCandidateFamilyInfos();

	 		int i=0;
			for(Iterator itr  = addressbook.getCandidateFamilyInfos().iterator() ; itr.hasNext() ; ){
				CandidateFamilyInfo candidateFamilyInfo = (CandidateFamilyInfo) itr.next();
				if(i!=0){
					PdfPCell c1 = new PdfPCell(new Phrase(" ",font));
			 		c1.setBorder(Rectangle.NO_BORDER);
					c1.setLeading(4f, 0f);
					c1.setFixedHeight(15f);
					table.addCell(c1);
					
					
					c1 = new PdfPCell(new Phrase(" ",font));
			 		c1.setBorder(Rectangle.NO_BORDER);
					c1.setLeading(4f, 0f);
					c1.setFixedHeight(15f);
					table.addCell(c1);
					
					c1 = new PdfPCell(new Phrase(" ",font));
			 		c1.setBorder(Rectangle.NO_BORDER);
					c1.setLeading(4f, 0f);
					c1.setFixedHeight(15f);
					table.addCell(c1);
				}
		 		
				i++;
				PdfPCell c1 = new PdfPCell(new Phrase("FAMILY INFORMATION"));
		 		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		 		c1.setColspan(4);
		 		c1.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
		 		c1.setFixedHeight(30f);
		 		c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
				c1.setBorder(Rectangle.BOX);
				c1.setFixedHeight(25f);
		 		table.addCell(c1);
		 		table.setHeaderRows(1);
		 		
		 		
		 		for (int j = 0; j < 3; j++) {
					c1 = new PdfPCell(new Phrase(" ",font));
			 		c1.setBorder(Rectangle.NO_BORDER);
					c1.setLeading(4f, 0f);
					c1.setFixedHeight(15f);
					table.addCell(c1);
					
				}
		 		
		 		c1 = new PdfPCell(new Phrase("Name: "+candidateFamilyInfo.getName() ,font));
		 		c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				c1.setBorder(Rectangle.BOTTOM);
				table.addCell(c1);
				
				c1 = new PdfPCell(new Phrase("Unit: "+candidateFamilyInfo.getUnit(),font));
		 		c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				c1.setBorder(Rectangle.BOTTOM);
				table.addCell(c1);
				
				c1 = new PdfPCell(new Phrase("Possition: "+candidateFamilyInfo.getPosition(),font));
		 		c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				c1.setBorder(Rectangle.BOTTOM);
				table.addCell(c1);
				
				
				for (int j = 0; j < 3; j++) {
					c1 = new PdfPCell(new Phrase(" ",font));
			 		c1.setBorder(Rectangle.NO_BORDER);
					c1.setLeading(4f, 0f);
					c1.setFixedHeight(15f);
					table.addCell(c1);
					
				}
				
		 		c1 = new PdfPCell(new Phrase("Relationship : "+candidateFamilyInfo.getRelationship(),font));
		 		c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				c1.setBorder(Rectangle.BOTTOM);
				table.addCell(c1);
				
				c1 = new PdfPCell(new Phrase("Occupation : "+candidateFamilyInfo.getOccupation(),font));
		 		c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				c1.setBorder(Rectangle.BOTTOM);
				table.addCell(c1);
				
				c1 = new PdfPCell(new Phrase("Phone : "+candidateFamilyInfo.getPhoneNo(),font));
		 		c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				c1.setBorder(Rectangle.BOTTOM);
				table.addCell(c1);
				
				
				
			}
			
			
				document.add(table);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.log(Level.INFO,"ApplicationFormPDFMaintenance --> familyInformation "+e.getMessage());
			e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("ApplicationFormPDFMaintenance",Level.SEVERE+"",errors.toString());
		}
		return document;
	}


	private static Document personalInformation(Document document, PdfWriter writer, AddressBook addressbook, Font normalFontCH) {
		// TODO Auto-generated method stub
		log.log(Level.INFO,"ApplicationFormPDFMaintenance --> personalInformation ");
		try {
			
			PdfPTable table = new PdfPTable(2);
	 		table.setSpacingBefore(10);
	 		table.setWidthPercentage(100f);
	 		
	        PdfPCell c1 = new PdfPCell(new Phrase("PERSONAL INFORMATION"));
	 		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
	 		c1.setColspan(4);
	 		c1.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
	 		c1.setFixedHeight(30f);
	 		c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
			c1.setBorder(Rectangle.BOX);
			c1.setFixedHeight(25f);
	 		table.addCell(c1);
	 		table.setHeaderRows(1);
	 		
	 		
	 		for (int j = 0; j < 2; j++) {
				c1 = new PdfPCell(new Phrase(" ",font));
		 		c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				table.addCell(c1);
				
			}
	 		
	 		c1 = new PdfPCell(new Phrase("Candidate's Name: "+addressbook.getName()  ,font));
	 		c1.setBorder(Rectangle.NO_BORDER);
			c1.setLeading(4f, 0f);
			c1.setFixedHeight(15f);
			//c1.setPaddingLeft(30f);
			c1.setBorder(Rectangle.NO_BORDER);
			table.addCell(c1);
			
	
			
			c1 = new PdfPCell(new Phrase("NRIC: "+addressbook.getNric() ,font));
			c1.setFixedHeight(15f);
			c1.setBorder(Rectangle.NO_BORDER);
			c1.setLeading(4f, 0f);
			table.addCell(c1);
			
			
			c1 = new PdfPCell(new Phrase("姓名（*）  ",normalFontCH));
			c1.setBorder(Rectangle.NO_BORDER);
			c1.setLeading(4f, 0f);
			c1.setFixedHeight(15f);
			c1.setBorder(Rectangle.BOTTOM);
			table.addCell(c1);
			
			
			c1 = new PdfPCell(new Phrase("证件号码(*) ",normalFontCH));
			c1.setBorder(Rectangle.NO_BORDER);
			c1.setLeading(4f, 0f);
			c1.setFixedHeight(15f);
			c1.setBorder(Rectangle.BOTTOM);
			table.addCell(c1);
			
			for (int j = 0; j < 2; j++) {
				c1 = new PdfPCell(new Phrase(" ",font));
		 		c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				table.addCell(c1);
				
			}
			
			
			c1 = new PdfPCell(new Phrase("Date of Birth: "+format.format(addressbook.getBirthDate()),font));
	 		c1.setBorder(Rectangle.NO_BORDER);
			c1.setLeading(4f, 0f);
			c1.setFixedHeight(15f);
			//c1.setPaddingLeft(30f);
			c1.setBorder(Rectangle.NO_BORDER);
			table.addCell(c1);
			
	
			
			c1 = new PdfPCell(new Phrase("Gender: "+addressbook.getGender(),font));
			c1.setFixedHeight(15f);
			c1.setBorder(Rectangle.NO_BORDER);
			c1.setLeading(4f, 0f);
			table.addCell(c1);
			
			
			c1 = new PdfPCell(new Phrase("出生日期（*）",normalFontCH));
			c1.setBorder(Rectangle.NO_BORDER);
			c1.setLeading(4f, 0f);
			c1.setFixedHeight(15f);
			c1.setBorder(Rectangle.BOTTOM);
			table.addCell(c1);
			
			
			c1 = new PdfPCell(new Phrase("性别（*）",normalFontCH));
			c1.setBorder(Rectangle.NO_BORDER);
			c1.setLeading(4f, 0f);
			c1.setFixedHeight(15f);
			c1.setBorder(Rectangle.BOTTOM);
			table.addCell(c1);
			
			for (int j = 0; j < 2; j++) {
				c1 = new PdfPCell(new Phrase(" ",font));
		 		c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				table.addCell(c1);
				
			}
			
			c1 = new PdfPCell(new Phrase("Age: "+addressbook.getAge(),font));
	 		c1.setBorder(Rectangle.NO_BORDER);
			c1.setLeading(4f, 0f);
			c1.setFixedHeight(15f);
			//c1.setPaddingLeft(30f);
			c1.setBorder(Rectangle.NO_BORDER);
			table.addCell(c1);
			
	
			
			c1 = new PdfPCell(new Phrase("Place of Birth: "+addressbook.getBirthPlace(),font));
			c1.setFixedHeight(15f);
			c1.setBorder(Rectangle.NO_BORDER);
			c1.setLeading(4f, 0f);
			table.addCell(c1);
			
			
			
			c1 = new PdfPCell(new Phrase("年龄(*)",normalFontCH));
			c1.setLeading(4f, 0f);
			c1.setFixedHeight(15f);
			c1.setBorder(Rectangle.BOTTOM);
			table.addCell(c1);
			
			
			c1 = new PdfPCell(new Phrase("",normalFontCH));
			c1.setLeading(4f, 0f);
			c1.setFixedHeight(15f);
			c1.setBorder(Rectangle.BOTTOM);
			table.addCell(c1);
			
			for (int j = 0; j < 2; j++) {
				c1 = new PdfPCell(new Phrase(" ",font));
		 		c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				table.addCell(c1);
				
			}
			
			c1 = new PdfPCell(new Phrase("Education: "+addressbook.getEducation(),font));
	 		c1.setBorder(Rectangle.NO_BORDER);
			c1.setLeading(4f, 0f);
			c1.setFixedHeight(15f);
			//c1.setPaddingLeft(30f);
			c1.setBorder(Rectangle.NO_BORDER);
			table.addCell(c1);
			
	
			
			c1 = new PdfPCell(new Phrase("Maritial Status: "+addressbook.getMarritalStatus(),font));
			c1.setFixedHeight(15f);
			c1.setBorder(Rectangle.NO_BORDER);
			c1.setLeading(4f, 0f);
			table.addCell(c1);
			
			c1 = new PdfPCell(new Phrase("最高学历（*）",normalFontCH));
			c1.setLeading(4f, 0f);
			c1.setFixedHeight(15f);
			c1.setBorder(Rectangle.BOTTOM);
			table.addCell(c1);
			
			
			c1 = new PdfPCell(new Phrase("婚姻状况（*）",normalFontCH));
			c1.setLeading(4f, 0f);
			c1.setFixedHeight(15f);
			c1.setBorder(Rectangle.BOTTOM);
			table.addCell(c1);
			
			for (int j = 0; j < 2; j++) {
				c1 = new PdfPCell(new Phrase(" ",font));
		 		c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				table.addCell(c1);
				
			}
			
			c1 = new PdfPCell(new Phrase("Annual Income: "+addressbook.getYearlyIncome(),font));
	 		c1.setBorder(Rectangle.NO_BORDER);
			c1.setLeading(4f, 0f);
			c1.setFixedHeight(15f);
			//c1.setPaddingLeft(30f);
			c1.setBorder(Rectangle.NO_BORDER);
			table.addCell(c1);
			
	
			
			c1 = new PdfPCell(new Phrase("Work Experience:  "+addressbook.getWorkingYearExperience(),font));
			c1.setFixedHeight(15f);
			c1.setBorder(Rectangle.NO_BORDER);
			c1.setLeading(4f, 0f);
			table.addCell(c1);
			
			
			c1 = new PdfPCell(new Phrase("年收入（*）",normalFontCH));
			c1.setLeading(4f, 0f);
			c1.setFixedHeight(15f);
			c1.setBorder(Rectangle.BOTTOM);
			table.addCell(c1);
			
			
			c1 = new PdfPCell(new Phrase("本地工作时间（*）",normalFontCH));
			c1.setLeading(4f, 0f);
			c1.setFixedHeight(15f);
			c1.setBorder(Rectangle.BOTTOM);
			table.addCell(c1);
			
			for (int j = 0; j < 2; j++) {
				c1 = new PdfPCell(new Phrase(" ",font));
		 		c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				table.addCell(c1);
				
			}
			
			
			c1 = new PdfPCell(new Phrase("Address : "+addressbook.getResidentialAddress1() +" ,"+addressbook.getResidentialAddress2()+" , "+addressbook.getResidentialAddress3(),font));
	 		c1.setBorder(Rectangle.NO_BORDER);
			c1.setLeading(4f, 0f);
			c1.setFixedHeight(15f);
			//c1.setPaddingLeft(30f);
			c1.setBorder(Rectangle.NO_BORDER);
			table.addCell(c1);
			
			c1 = new PdfPCell(new Phrase(" ",font));
	 		c1.setBorder(Rectangle.NO_BORDER);
			c1.setLeading(4f, 0f);
			c1.setFixedHeight(15f);
			table.addCell(c1);
			
			c1 = new PdfPCell(new Phrase("居住地址（*）",normalFontCH));
			c1.setLeading(4f, 0f);
			c1.setFixedHeight(15f);
			c1.setBorder(Rectangle.BOTTOM);
			table.addCell(c1);
			
			c1 = new PdfPCell(new Phrase(" ",normalFontCH));
			c1.setLeading(4f, 0f);
			c1.setFixedHeight(15f);
			c1.setBorder(Rectangle.BOTTOM);
			table.addCell(c1);
			
			
			
			for (int j = 0; j < 2; j++) {
				c1 = new PdfPCell(new Phrase(" ",font));
		 		c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				table.addCell(c1);
				
			}
			
			
			c1 = new PdfPCell(new Phrase("Postal Code: "+addressbook.getResidentialPostalCode(),font));
	 		c1.setBorder(Rectangle.NO_BORDER);
			c1.setLeading(4f, 0f);
			c1.setFixedHeight(15f);
			//c1.setPaddingLeft(30f);
			c1.setBorder(Rectangle.NO_BORDER);
			table.addCell(c1);
			
	
			
			c1 = new PdfPCell(new Phrase("Mobile Number: "+addressbook.getMobilePhoneNo(),font));
			c1.setFixedHeight(15f);
			c1.setBorder(Rectangle.NO_BORDER);
			c1.setLeading(4f, 0f);
			table.addCell(c1);
			
			c1 = new PdfPCell(new Phrase("居住地址（*）",normalFontCH));
			c1.setLeading(4f, 0f);
			c1.setFixedHeight(15f);
			c1.setBorder(Rectangle.BOTTOM);
			table.addCell(c1);
			
			c1 = new PdfPCell(new Phrase(" ",normalFontCH));
			c1.setLeading(4f, 0f);
			c1.setFixedHeight(15f);
			c1.setBorder(Rectangle.BOTTOM);
			table.addCell(c1);
			
			
			for (int j = 0; j < 2; j++) {
				c1 = new PdfPCell(new Phrase(" ",font));
		 		c1.setBorder(Rectangle.NO_BORDER);
				c1.setLeading(4f, 0f);
				c1.setFixedHeight(15f);
				table.addCell(c1);
				
			}
			
			c1 = new PdfPCell(new Phrase("Email Address: "+addressbook.geteMailId(),font));
	 		c1.setBorder(Rectangle.NO_BORDER);
			c1.setLeading(4f, 0f);
			c1.setFixedHeight(15f);
			//c1.setPaddingLeft(30f);
			c1.setBorder(Rectangle.BOTTOM);
			table.addCell(c1);
			
	
			
			c1 = new PdfPCell(new Phrase(" ",font));
			c1.setFixedHeight(15f);
			c1.setBorder(Rectangle.BOTTOM);
			c1.setLeading(4f, 0f);
			table.addCell(c1);
			
			
				document.add(table);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.log(Level.INFO,"ApplicationFormPDFMaintenance --> personalInformation "+e.getMessage());
				e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("ApplicationFormPDFMaintenance",Level.SEVERE+"",errors.toString());
			}
		return document;
	}


	private static void addTitle(Document document, PdfWriter writer,
			String fieldName, String headerText) throws Exception,
			IOException, BadElementException {
		log.log(Level.INFO,"ApplicationFormPDFMaintenance --> addTitle ");
		PdfPTable table = new PdfPTable(2);
		PdfPCell c1 = new PdfPCell(new Phrase(headerText));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setVerticalAlignment(Element.ALIGN_CENTER);
		//c1.setBackgroundColor(BaseColor.RED);
		c1.setBorder(Rectangle.BOX);
		c1.setFixedHeight(25f);
		c1.setColspan(2);
		table.addCell(c1);
		table.setWidthPercentage(100f);

		document.add(table);
	}


	public void insertPdf(HttpServletRequest request,
			InterviewCandidateMaterial material) {
		// TODO Auto-generated method stub
		log.log(Level.INFO,"ApplicationFormPDFMaintenance --> insertPdf ");
		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();
			ArrayList listData=null;
			 Criteria criteria = session.createCriteria(InterviewCandidateMaterial.class);
			 criteria.add(Restrictions.eq("candidateCode",material.getCandidateCode()));
			 criteria.add(Restrictions.eq("interviewCode",material.getInterviewCode()));
			 listData = (ArrayList) criteria.list();
			 if(listData.size() ==0){
				 session.save(material);
				tx.commit();
			 }
			
			
			
		}catch(Exception e){
			log.log(Level.INFO,"ApplicationFormPDFMaintenance --> insertPdf "+e.getMessage());
			e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("ApplicationFormPDFMaintenance",Level.SEVERE+"",errors.toString());
		}finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.INFO,"ApplicationFormPDFMaintenance --> insertPdf "+e.getMessage());
				e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("ApplicationFormPDFMaintenance",Level.SEVERE+"",errors.toString());
				e.printStackTrace();
			}
		}
		
		
	}

}
