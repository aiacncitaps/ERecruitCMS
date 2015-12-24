package com.archerlogic.aia.cn.helpers.iCalendar;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import com.archerlogic.aia.cn.helpers.common.ConfigHelper;
import com.archerlogic.aia.cn.helpers.iCalendar.iCalendarEventClass.EventType;

public class iCalendarHelper
{

    //* ------------------------------------------------------------------------
    private String _callService(JSONObject jsonRequest, String ssoSessionId)
    {
        String result = "";

        try
        {
            ConfigHelper configHelper = new ConfigHelper();
            String serviceUrl = configHelper.getICalendarServiceUrl();
            URL url = new URL(serviceUrl);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            String boundary = "--AIACalendar--";
            con.setRequestProperty("Content-Type", "multipart/form-data;charset=UTF-8;boundary=" + boundary);

            con.setDoOutput(true);
            con.setDoInput(true);
            con.setAllowUserInteraction(false);
            con.setUseCaches(false);

            DataOutputStream out = new DataOutputStream(con.getOutputStream());

            String data = jsonRequest.toString();

            // hash the json data to get the signature
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashedBytes = md.digest(data.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (int i = 0; i < hashedBytes.length; i++)
            {
                if ((0xff & hashedBytes[i]) < 0x10)
                {
                    hexString.append("0" + Integer.toHexString((0xFF & hashedBytes[i])));
                }
                else
                {
                    hexString.append(Integer.toHexString(0xFF & hashedBytes[i]));
                }
            }

            String signature = hexString.toString();

            // build the POST request
            StringBuilder sb = new StringBuilder();
            sb.append(_constructPostParam("SessionId", ssoSessionId, boundary));
            sb.append(_constructPostParam("Service", "OneCalendarEventService", boundary));
            sb.append(_constructPostParam("Signature", signature, boundary));
            sb.append(_constructPostParam("Encoding", "UTF-8", boundary));
            sb.append(_constructPostParam("Data", data, boundary));
            sb.append("--").append(boundary).append("--").append("\r\n");
            out.write(sb.toString().getBytes("UTF-8"));
            out.close();

            System.out.println("Sending iCalendar request: " + data);

            // read the result input from iCalendar
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String line = in.readLine();
            while (line != null)
            {
                result += line;
                line = in.readLine();
            }

            in.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();;
        }

        return result;
    }

    //* ------------------------------------------------------------------------
    private String _constructPostParam(String name, String value, String boundary)
    {
        StringBuilder sb = new StringBuilder("");
        sb.append("--").append(boundary);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data; name=\"").append(name).append("\"");
        sb.append("\r\n\r\n");
        sb.append(value);
        sb.append("\r\n");
        return sb.toString();
    }

    //* ------------------------------------------------------------------------
    // NOT USED, for development testing purpose only
    public void query(String ssoSessionId)
    {
        /*
         Sample Query JSON
        
         {
         "version": "2.0.1",
         "option":"Q",  // Use Q for Query {A:Add; D:Delete; U:Update; Q:Query}
         "from":"03"ï¼Œ// eRecruitment will use 03 { 01:AIATouch; 02:ATMS, 03:eRecruitment, 04:Master Plan}
         "events":[
         {
         â€œoriginâ€�ï¼šâ€œ-1â€�, //if origin =-1ï¼Œit means that origin does not match the â€œfromâ€� value, if there is no origin valueï¼Œit will be default assume that the origin is â€œfromâ€� value
         "co":"0986",   //can be empty, means search all company, company code
         "agentcode":"00000084", //can be empty, means search all agent, agent code
         "typeId":"08", //can be empty means search all, activity type code
         â€œtypeNameâ€�:â€�é�¢è¯•â€�,   //can be empty means search all, activity type
         "startTime":"2015-07-01 15:30:00", //can be empty means no restriction, this will search using >=, activity start time
         "endTime":"2015-07-01 16:00:00" //can be empty means no restriction, this will search using <, activity end time
         }
         }

         */

        try
        {
            JSONObject json = new JSONObject();
            json.put("version", "2.0.1");
            json.put("option", "Q");
            json.put("from", "03");

            HashMap<String, String> eventMap = new HashMap<String, String>();
            eventMap.put("origin", "");
            eventMap.put("co", "");
            eventMap.put("agentcode", "");
            eventMap.put("typeId", "08");
            eventMap.put("typeName", "");
            eventMap.put("startTime", "2015-12-17 00:00:00");
            eventMap.put("endTime", "2015-12-19 00:00:00");

            ArrayList<HashMap> eventList = new ArrayList<HashMap>();
            eventList.add(eventMap);

            json.put("events", eventList);

            String result = _callService(json, ssoSessionId);
            System.out.println(result);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    //* ------------------------------------------------------------------------
    // NOT USED, for development testing purpose only
    public void insert(String referenceId, String agentCode, String ssoSessionId)
    {
        /*
         Sample Insert JSON
         {
         "version": "2.0.1",
         "option":"A",   // Use A for Add {A:Add; D:Delete; U:Update; Q:Query}
         "from":"03",  // eRecruitment will use 03 { 01:AIATouch; 02:ATMS, 03:eRecruitment, 04:Master Plan}
         "events":[
         {
         "referenceId":"001", //3rd party system own unique identifier for record
         "co":"0986", //company code
         "agentcode":"00000084", //agent code           
         "typeId":"08", //activity type code
         â€œtypeNameâ€�:â€�é�¢è¯•â€�, //activity type
         "subject":"Interview with Jim", //activity title
         "description":"This is a interview with Jim!", //activity description
         "address":"æ—¥æœˆå…‰11F Meetting Room1", //activity address
         "startTime":"2015-07-01 15:30:00", //activity start time
         "endTime":"2015-07-01 16:00:00", //activity end time
         "openFlag":"Y" //Y â€“ AIATouch able to edit, N â€“ not able to edit
         },
         {
         "referenceId":"002", //3rd party system own unique identifier for record
         "co":"0986", //company code
         "agentcode":"00000071",  //agent code           
         "typeId":"08", //activity type code
         â€œtypeNameâ€�:â€�é�¢è¯•â€�, //activity type 
         "subject":"Interview with Jerry", //activity title
         "description":"This is a interview with Jerry!", //activity description
         "address":"æ—¥æœˆå…‰11F Meetting Room2", //activity address
         "startTime":"2015-07-02 15:30:00", //activity start time
         "endTime":"2015-07-02 16:00:00", //activity end time
         "openFlag":"Y" //Y â€“ AIATouch able to edit, N â€“ not able to edit
         }
         ]
         }

         */
        try
        {
            JSONObject json = new JSONObject();
            json.put("version", "2.0.1");
            json.put("option", "A");
            json.put("from", "03");

            HashMap<String, String> eventMap = new HashMap<String, String>();
            eventMap.put("referenceId", referenceId);
            eventMap.put("co", "0986");
            eventMap.put("agentcode", agentCode);
            eventMap.put("typeId", "08");
            eventMap.put("typeName", "Interview");
            eventMap.put("subject", "test subject");
            eventMap.put("description", "test descrption");
            eventMap.put("address", "test address");
            eventMap.put("startTime", "2015-12-18 09:00:00");
            eventMap.put("endTime", "2015-12-18 18:00:000");
            eventMap.put("openFlag", "N");

            ArrayList<HashMap> eventList = new ArrayList<HashMap>();
            eventList.add(eventMap);

            json.put("events", eventList);

            String result = _callService(json, ssoSessionId);
            System.out.println(result);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    //* ------------------------------------------------------------------------
    public iCalendarResponseClass update(List<iCalendarEventClass> events, String ssoSessionId)
    {
        /*
         Sample update query
         {
         "version": "2.0.1",
         "option":"U",  // Use U for Update {A:Add; D:Delete; U:Update; Q:Query}
         "from":"03",  // eRecruitment will use 03 { 01:AIATouch; 02:ATMS, 03:eRecruitment, 04:Master Plan}
         "events":[
         {
         â€œreferenceIdâ€�:â€�1001â€�, //3rd party system own unique identifier for record
         "co":"0986",  //company code
         "agentcode":"00000071", //agent code
         "typeId":"08", //activity type code
         â€œtypeNameâ€�:â€�é�¢è¯•â€�, //activity type
         "subject":"Interview with Jim", //activity title
         "description":"This is a interview with Jim!", //activity description
         "address":"æ—¥æœˆå…‰11F Meetting Room1", //activity address
         "startTime":"2015-07-01 15:30:00", //activity start time
         "endTime":"2015-07-01 16:00:00" //activity end time
         },
         {
         â€œreferenceIdâ€�:â€�1002â€�, //3rd party system own unique identifier for record
         "co":"0986",  //company code
         "agentcode":"00000084", //agent code
         "typeId":"08",  //activity type code
         â€œtypeNameâ€�:â€�é�¢è¯•â€�, //activity type
         "subject":"Interview with Jerry", //activity title
         "description":"This is a interview with Jerry!", //activity description
         "address":"æ—¥æœˆå…‰11F Meetting Room2", //activity address
         "startTime":"2015-07-02 15:30:00", //activity start time
         "endTime":"2015-07-02 16:00:00" //activity end time
           
         }
         ]
         }

         */
        iCalendarResponseClass response = null;

        try
        {
            JSONObject json = new JSONObject();
            json.put("version", "2.0.1");
            json.put("option", "U");
            json.put("from", "03");

            ArrayList<HashMap> eventList = new ArrayList<HashMap>();
            for (iCalendarEventClass event : events)
            {
                HashMap<String, String> eventMap = new HashMap<String, String>();
                eventMap.put("referenceId", event.getReferenceId());
                eventMap.put("co", event.getCompanyCode());
                eventMap.put("agentcode", event.getAgentCode());

                if (event.getEventType() == EventType.INTERVIEW)
                {
                    eventMap.put("typeId", "08");
                    eventMap.put("typeName", "Interview");
                }
                else if (event.getEventType() == EventType.EOP)
                {
                    eventMap.put("typeId", "09");
                    eventMap.put("typeName", "EOP");
                }

                eventMap.put("subject", event.getSubject());
                eventMap.put("description", event.getDescription());
                eventMap.put("address", event.getAddress());

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                eventMap.put("startTime", dateFormat.format(event.getStartDate()));
                eventMap.put("endTime", dateFormat.format(event.getEndDate()));
                eventMap.put("openFlag", "N");
                eventList.add(eventMap);
            }

            json.put("events", eventList);

            String result = _callService(json, ssoSessionId);
            System.out.println(result);

            if (result != null)
            {
                JSONObject resultJson = new JSONObject(result);
                response = new iCalendarResponseClass();

                // sample response
                // {"ResponseMessage":{"returnCode":"00","returnMessage":"Successful!"}}
                JSONObject responseMessage = (JSONObject) resultJson.get("ResponseMessage");
                response.setReturnCode((String) responseMessage.get("returnCode"));
                response.setReturnMessage((String) responseMessage.get("returnMessage"));
            }
        }
        catch (Exception ex)
        {
            response = null;
            ex.printStackTrace();
        }

        return response;
    }

    //* ------------------------------------------------------------------------
    public iCalendarResponseClass delete(int interviewCode, int companyCode, List<String> agentCodes, String ssoSessionId)
    {
        /*
         Sample delete JSON
         {
         "version": "2.0.1",
         "option":"D",   // Use D for Delete {A:Add; D:Delete; U:Update; Q:Query}
         "from":"03"ï¼Œ // eRecruitment will use 03 { 01:AIATouch; 02:ATMS, 03:eRecruitment, 04:Master Plan}
         "events":[
         {
         â€œreferenceIdâ€�:â€�1001â€�, //3rd party system own unique identifier for record
         "co":"0986", //company code
         "agentcode":"00000084" //agent code
         },
         {
         â€œreferenceIdâ€�:â€�1002â€�, //3rd party system own unique identifier for record
         "co":"0986", //company code
         "agentcode":"00000071" //agent code

         }
         ]
         }
         */
        iCalendarResponseClass response = null;

        try
        {
            JSONObject json = new JSONObject();
            json.put("version", "2.0.1");
            json.put("option", "D");
            json.put("from", "03");

            ArrayList<HashMap> eventList = new ArrayList<HashMap>();
            for (String agentCode : agentCodes)
            {
                HashMap<String, String> eventMap = new HashMap<String, String>();
                String referenceId = String.valueOf(interviewCode) + "-" + agentCode;
                eventMap.put("referenceId", referenceId);
                eventMap.put("co", String.valueOf(companyCode));
                eventMap.put("agentcode", agentCode);
                eventList.add(eventMap);
            }

            json.put("events", eventList);

            String result = _callService(json, ssoSessionId);
            System.out.println(result);

            if (result != null)
            {
                JSONObject resultJson = new JSONObject(result);
                response = new iCalendarResponseClass();

                // sample response
                // {"ResponseMessage":{"returnCode":"00","returnMessage":"Successful!"}}
                JSONObject responseMessage = (JSONObject) resultJson.get("ResponseMessage");
                response.setReturnCode((String) responseMessage.get("returnCode"));
                response.setReturnMessage((String) responseMessage.get("returnMessage"));
            }
        }
        catch (Exception ex)
        {
            response = null;
            ex.printStackTrace();
        }

        return response;
    }
}
