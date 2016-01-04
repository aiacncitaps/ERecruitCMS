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
* Date                       Developer           Description
* 5-January-2015             khyati             Added Copy rights
***************************************************************************** */


package com.quix.aia.cn.imo.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LMSUtil
{
    static Logger log = Logger.getLogger(LMSUtil.class.getName());
	
    
   public static void main(String []args) throws ParseException{
	   System.out.println(""+yyyymmddHHmmssdashed.parse("1970-01-01 17:00:00"));
   }
	
    public LMSUtil()
    {
    }

    public static Date convertToDate(String s)
	{
		try
		{
			java.util.Date dt = mmddyyyy.parse(s);
			java.util.Date dt1 = dd_MM_yyyy.parse(dd_MM_yyyy.format(dt));
			return dt1;
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
    public static Date convertoDateHHMMAMPM(String d)
   	{
   		try
   		{
   			java.util.Date dt = HH_MM.parse(d);
   			String s= HH_MM.format(dt);
   			java.util.Date dt1 = HH_MM.parse(s);
   			return dt1;
   		
   		}
   		catch(Exception e)
   		{
   			log.log(Level.SEVERE, e.getMessage());
   			e.printStackTrace();
   		}
   		return null;
   	}
    
    public static Date convertoDateHHMMAMPM1(String d)
   	{
   		try
   		{
   			java.util.Date dt = hhmma.parse(d);
   			String s= hhmma.format(dt);
   			java.util.Date dt1 = hhmma.parse(s);
   			return dt1;
   		
   		}
   		catch(Exception e)
   		{
   			log.log(Level.SEVERE, e.getMessage());
   			e.printStackTrace();
   		}
   		return null;
   	}
    
    public static Date converExcelTimetoDateHHMMAMPM1(String d)
   	{
   		try
   		{
   			DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
   			Date result=formatter.parse(d);
			System.out.println("Time "+result);
   			return result;
   		
   		}
   		catch(Exception e)
   		{
   			log.log(Level.SEVERE, e.getMessage());
   			e.printStackTrace();
   		}
   		return null;
   	}
    
    
    
    public static Date convertoDateHHMM(String d)
   	{
   		try
   		{
   			
   			SimpleDateFormat formate=new SimpleDateFormat("HH:mm");
   			java.util.Date dt = formate.parse(d);
   			return dt;
   		
   		}
   		catch(Exception e)
   		{
   			log.log(Level.SEVERE, e.getMessage());
   			e.printStackTrace();
   		}
   		return null;
   	}
    
    public static String convertToGivenFormat(Date s,String format)
   	{
       	SimpleDateFormat mdy = new SimpleDateFormat(format);
   		try
   		{
   			String dt = mdy.format(s);
   			return dt;
   		}
   		catch(Exception e)
   		{
   			log.log(Level.SEVERE, e.getMessage());
   			e.printStackTrace();
   		}
   		return null;
   	}
    public static String converDateIntoHHMMAMPM(java.util.Date d)
   	{
   		try
   		{
   		return hhmma.format(d);
   		}
   		catch(Exception e)
   		{
   			log.log(Level.SEVERE, e.getMessage());
   			e.printStackTrace();
   		}
   		return "";
   	}
    
    public static boolean validInt(String strIn)
    {
        try
        {
            int number = Integer.parseInt(strIn);
            return number > -1;
        }
        catch(Exception e)
        {
        	log.log(Level.SEVERE, e.getMessage());
   			e.printStackTrace();
            return false;
        }
    }
    
    public static Date convertToDate1(String s)
	{
		try
		{
			java.util.Date dt = ddmmyyyy.parse(s);
			java.util.Date dt1 = dd_MM_yyyy.parse(dd_MM_yyyy.format(dt));
			return dt1;
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
   			e.printStackTrace();
		}
		return null;
	}
    
    public static Date convertExcelDateToDate(String s)
	{
		try
		{
			
				Date date = ddmmyyyy.parse(s);
			return date;
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
   			e.printStackTrace();
		}
		return null;
	}
    
    
    public static String appendZero(String str, int totalLength)
    {
    	String formatted = String.format("%" + totalLength+"s", new String[] {str}).replace(" ", "0");
    	return formatted;
    }
    public static String convertDateToyyyymmddhhmmss(Date d)
   	{
    	try
		{
		return yyyymmddhhmmss.format(d);
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
   			e.printStackTrace();
		}
		return "";
   	}
    public static String convertDateToddmmyyyyhhmmss(Date d)
   	{
    	try
		{
		return ddmmyyyyhhmmss.format(d);
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
   			e.printStackTrace();
		}
		return "";
   	}
    public static Date convertDateToyyyymmddhhmmss(String s)
   	{
    	try
		{
		return yyyymmddhhmmss.parse(s);
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
   			e.printStackTrace();
		}
		return null;
   	}
    public static Date convertDateToyyyymmddhhmmssDashed(String s)
   	{
    	try
		{
		return yyyymmddhhmmssdashed.parse(s);
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
   			e.printStackTrace();
		}
		return null;
   	}
    public static String convertDateToyyyymmddhhmmssDashedString(Date date)
   	{
    	try
		{
		return yyyymmddhhmmssdashed.format(date);
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
   			e.printStackTrace();
		}
		return null;
   	}
    public static String parseByteString(String in, String seperator)
    {
        StringTokenizer st = new StringTokenizer(in + seperator, seperator);
        byte b[] = new byte[st.countTokens()];
        int m = 0;
        for(int len2 = b.length; m < len2; m++)
            try
            {
                b[m] = (byte)Integer.parseInt(st.nextToken());
            }
            catch(Exception e)
            {
            	log.log(Level.SEVERE, e.getMessage());
       			e.printStackTrace();
                return null;
            }

        String result = new String(b);
        return result;
    }

    public static String convertByteToString(byte b[])
    {
        String result = "";
        if(b != null && b.length > 0)
        {
            int i = 0;
            for(int len = b.length; i < len; i++)
                result = result + b[i] + " ";

        }
        return result;
    }

    
    public static Date convertDateToyyyy_mm_dd(String s)
   	{
    	try
		{
		return yyyy_MM_dd.parse(s);
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
   			e.printStackTrace();
		}
		return null;
   	}
    
    public static String convertDatetoDD_MM_YYYY(Date d)
    {
    	try
		{
			return dd_MM_yyyy.format(d);
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
   			e.printStackTrace();
		}
		return null;
    }
    public static String convertDatetoDD_MM_YYYY1(Date d)
    {
    	try
		{
			return dd_MM_yyyy.format(d);
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
   			e.printStackTrace();
		}
		return null;
    }
    public static String convertDatetoHHMM(Date d)
    {
    	try
		{
			return HH_MM.format(d);
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
		return null;
    }
    
    
    
    public static String convertDateToString(Date dt)
  	{
    	String dtStr = "";
  		try
  		{
  			dtStr = ddmmyyyy.format(dt);
  			return dtStr;
  		}
  		catch(Exception e)
  		{
  			log.log(Level.SEVERE, e.getMessage());
  			e.printStackTrace();
  		}
  		return "";
  	}
   
    //hh:mm:ss
    public static Date convertoDateHHMMSSAMPM1(String d)
   	{
   		try
   		{
   			java.util.Date dt = hhmmass.parse(d);
   			String s= hhmmass.format(dt);
   			java.util.Date dt1 = hhmmass.parse(s);
   			return dt1;
   		
   		}
   		catch(Exception e)
   		{
   			log.log(Level.SEVERE, e.getMessage());
   			e.printStackTrace();
   		}
   		return null;
   	}
    
    public static boolean emailValidation(String emil){
    	String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";  
    	Boolean b = emil.matches(EMAIL_REGEX);
        return b;
    } 
    
    public static String getRendomPassword(){
    	String uuid = UUID.randomUUID().toString();
		uuid=uuid.substring(0,uuid.indexOf("-"));
		return uuid;
    } 
    
    public static String getRendomToken(){
    	String uuid = UUID.randomUUID().toString();
		uuid=uuid.substring(0,uuid.indexOf("-"));
		return uuid;
    } 
    
    public static Date convertStringToDate(String s)
  		{
  			try
  			{
  				Date dt = ddmmyyyy.parse(s);
  				return dt;
  			}
  			catch(Exception e)
  			{
  				log.log(Level.SEVERE, e.getMessage());
  	   			e.printStackTrace();
  			}
  			return null;
  		}
    
    
    public static String blankIfNull(String str)
    {
    	if(str != null)
    		return str;
    	else 
    		return "";
    }
    /**
   	 * <p>Validate DD/MM/YYYY date format </p>
   	 * @param date
   	 * @return flag True/False
   	 */
    public boolean validateDDMMYYYY(final String date){
   	 pattern = Pattern.compile(DATE_PATTERN);

      matcher = pattern.matcher(date);

      if(matcher.matches()){

   	 matcher.reset();

   	 if(matcher.find()){

         String day = matcher.group(1);
   	     String month = matcher.group(2);
   	     int year = Integer.parseInt(matcher.group(3));

   	     if (day.equals("31") && 
   		  (month.equals("4") || month .equals("6") || month.equals("9") ||
                   month.equals("11") || month.equals("04") || month .equals("06") ||
                   month.equals("09"))) {
   			return false; // only 1,3,5,7,8,10,12 has 31 days
   	     } else if (month.equals("2") || month.equals("02")) {
                   //leap year
   		  if(year % 4==0){
   			  if(day.equals("30") || day.equals("31")){
   				  return false;
   			  }else{
   				  return true;
   			  }
   		  }else{
   		         if(day.equals("29")||day.equals("30")||day.equals("31")){
   				  return false;
   		         }else{
   				  return true;
   			  }
   		  }
   	      }else{				 
   		return true;				 
   	      }
   	   }else{
     	      return false;
   	   }		  
      }else{
   	  return false;
      }			    
    }
 
    /**
	 * <p>This method performs call for validation
	 * of Phone number </p>
	 * @param PhoneNo 
	 * @param req Servlet Request Parameter
	 * @return flag True/False
	 */
	public static boolean validatePhoneNumber(String phoneNo) {
		log.log(Level.SEVERE,"UserMaintenance --> validatePhoneNumber");
        //validate phone numbers of format "1234567890"
        if (phoneNo.matches("\\d{10}")) return true;
        else if(phoneNo.matches("\\d{12}")) return true;
        else return false;
         
    }
	
	/**
	 * <p>This method performs call for validation
	 * of Phone number </p>
	 * @param PhoneNo 
	 * @param req Servlet Request Parameter
	 * @return flag True/False
	 */
	public static boolean validateExtensionNumber(String phoneNo) {
		log.log(Level.SEVERE,"UserMaintenance --> validateExtensionNumber");
        //validate phone numbers of format "1234567890"
        if (phoneNo.matches("\\d{10}")) return true;
       // else if(phoneNo.matches("\\d{3}")) return true;
        else return false;
         
    }
	
	
	public static String converDateIntoDD_MMM_YYYY(java.util.Date d){
		try
		{
		return dd_mmm_yyyy.format(d);
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
   			e.printStackTrace();
		}
		return "";
	}
    public static SimpleDateFormat ddmmyyyy = new SimpleDateFormat("dd/MM/yyyy");
    public static SimpleDateFormat mmddyyyy = new SimpleDateFormat("MM/dd/yyyy");
    public static  SimpleDateFormat dd_MM_yyyy = new SimpleDateFormat("dd-MM-yyyy");
    public static SimpleDateFormat hhmma = new SimpleDateFormat("hh:mm a");
    public static  SimpleDateFormat HH_MM = new SimpleDateFormat("HH:mm");
    public static SimpleDateFormat yyyymmddhhmmss = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
    public static SimpleDateFormat yyyymmddhhmmssdashed = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    public static SimpleDateFormat yyyymmddHHmmssdashed = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat ddmmyyyyhhmmss = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
    public static  SimpleDateFormat yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat hhmmass = new SimpleDateFormat("hh:mm:ss");
    public static SimpleDateFormat dd_mmm_yyyy = new SimpleDateFormat("dd-MMM-yyyy");
    public static  SimpleDateFormat HH_MM_SS = new SimpleDateFormat("HH:mm:ss");
    private static final String DATE_PATTERN = 
	          "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";
 
    private Pattern pattern;
	private Matcher matcher;


	public static String convertExcelDateToString(String interviewDate) {
		// TODO Auto-generated method stub
		SimpleDateFormat parseFormat = 
			    new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
			Date date;
			try {
				date = parseFormat.parse(interviewDate);
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				String result = format.format(date);
				return result;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		return null;
	}
}

