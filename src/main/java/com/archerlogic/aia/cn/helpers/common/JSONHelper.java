package com.archerlogic.aia.cn.helpers.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONHelper
{
    //* ------------------------------------------------------------------------
    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException
    {
        InputStream is = null;
        JSONObject result = null;

        try
        {
            is = new URL(url).openStream();
            InputStreamReader isr = new InputStreamReader(is, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(isr);
            
            String jsonText = null;
            StringBuilder sb = new StringBuilder();
            
            String line = reader.readLine();
            while (line != null)
            {
                sb.append(line);
                line = reader.readLine();
            }
            
            jsonText = sb.toString();
            
            if (jsonText == null || jsonText.isEmpty())
            {
                result = null;
            }
            else
            {
                result = new JSONObject(jsonText);
            }
        }
        finally
        {
            if (is != null)
            {
                is.close();
            }
        }
        
        return result;
    }
}
